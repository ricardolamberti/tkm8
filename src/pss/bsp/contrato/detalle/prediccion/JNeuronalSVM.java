package pss.bsp.contrato.detalle.prediccion;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.svm.SVM;
import org.encog.ml.svm.training.SVMTrain;
import org.encog.util.arrayutil.NormalizeArray;
import org.encog.util.arrayutil.TemporalWindowArray;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.variaciones.BizVariacionPaticular;
import pss.bsp.contrato.series.calculado.BizSerieCalculada;
import pss.bsp.contrato.series.variaciones.BizVariacion;
import pss.common.event.sql.datos.BizSqlEventDato;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;

public class JNeuronalSVM  implements IPrediccion {
	public final static double MAX_ERROR = 0.01;
	public final static int WINDOW_SIZE = 30;


	public SVM createNetwork()
	{
		SVM network = new SVM(WINDOW_SIZE,true);
		return network;
	}
	
  public  void processPopulate(BizDetalle detalle,Date fechaEsti, long variable) throws Exception {
  	Calendar fatras = Calendar.getInstance();
  	fatras.setTime(new Date());
  	//fatras.add(Calendar.DAY_OF_YEAR, -diasHist);
  	Calendar fdesde = Calendar.getInstance();
  	fdesde.setTime(detalle.getObjContrato().getFechaDesde());
  	Calendar fhasta = Calendar.getInstance();
  	fhasta.setTime(detalle.getObjContrato().getFechaHasta());
  	if (fhasta.get(Calendar.YEAR)!=fdesde.get(Calendar.YEAR))
  		fhasta.set(fdesde.get(Calendar.YEAR), 12, 30, 23, 59, 59);
  	fatras.setTime(JDateTools.getFirstDayOfYear(fdesde.getTime()));
  	double acumul1 = 0;
  	int diasHist = 365+JDateTools.getDaysBetween(detalle.getObjContrato().getFechaDesde(),detalle.getObjContrato().getFechaHasta());
  	Calendar finEstimacion = Calendar.getInstance();
  	finEstimacion.setTime(fechaEsti==null?new Date():fechaEsti);// en verdad deberia ser ultimo archivo GDS
  	detalle.processEliminarSerieCalculada(variable);
		SVM network = createNetwork();

	//	fatras.add(Calendar.YEAR, -1);

		// llenar el array deda la tabla de datos conocidos
		JRecords<BizSqlEventDato> datos = new JRecords<BizSqlEventDato>(BizSqlEventDato.class);
		datos.addFilter("id_evento", variable);
		datos.setTop(diasHist);
		datos.addFilter("fecha",fatras.getTime(),">=");
		datos.addFilter("fecha",finEstimacion.getTime(),"<=");
		datos.addOrderBy("fecha","ASC");
		int size = (int)datos.selectCount();
		double[] array = new double[diasHist+1];
		int i = 0;
		double firstVal=0;
		int firstDay=0;
		double lastVal=0;
		double lastAsc=0;
		boolean ascendente=true;
		long lastDay=0;
		double difPromedio=0;
		Calendar fechaIt = Calendar.getInstance();
		fechaIt.setTime(fatras.getTime());
		JIterator<BizSqlEventDato> it = datos.getStaticIterator();
		while (it.hasMoreElements()) {
			BizSqlEventDato dato = it.nextElement();
			double valor = dato.getValue();
//			if (valor==0) continue;
			int diaSuc = fechaIt.get(Calendar.DAY_OF_YEAR);
			Calendar calDato = Calendar.getInstance();
			calDato.setTime(dato.getFecha());
			int diaDato = calDato.get(Calendar.DAY_OF_YEAR);
			for (int j=diaSuc;j<diaDato-1;j++)
				array[i++] =lastVal;
			array[i++] = valor;
	  	Calendar fechaHist = Calendar.getInstance();
	  	fechaHist.setTime(calDato.getTime());
	  	fechaHist.add(Calendar.YEAR, -1);
	  	//fechaHist.set(Calendar.DAY_OF_YEAR, i);

			if (firstVal==0) {
				firstVal=valor;
				firstDay = fechaHist.get(Calendar.DAY_OF_YEAR);
			} 
			lastVal=valor;
			lastDay = fechaHist.get(Calendar.DAY_OF_YEAR);

			Double data = detalle.getRealData(fechaHist,variable, null);
  		if (data!=null) {
  			difPromedio=valor-data;
    		if (JDateTools.between(calDato.getTime(), fdesde.getTime(), fhasta.getTime())){
    			ascendente &= lastAsc<=data;
    			lastAsc=data;
    		}
  		}
	  	fechaIt = calDato;
		}
		int realSize=i-1;
	//	if (realSize>0) difPromedio/=realSize;
		// aca deberia aplicar distorsiones
		for (;i<=diasHist;i++) {
	  	Calendar fechaHist = Calendar.getInstance();
	  	fechaHist.setTime(fatras.getTime());
	  	fechaHist.add(Calendar.YEAR, -1);
	  	fechaHist.set(Calendar.DAY_OF_YEAR, i);
	  	Calendar fechaReal = Calendar.getInstance();
	  	fechaReal.setTime(fatras.getTime());
	  	fechaReal.set(Calendar.DAY_OF_YEAR, i);
	  	Double dato = detalle.getRealData(fechaHist, variable, null);
	  	if (dato==null) {
	  		long day = fechaReal.get(Calendar.DAY_OF_YEAR); 
	  		dato = ((lastDay-firstDay)==0?lastVal:((lastVal-firstVal)/(lastDay-firstDay))*day+firstVal);
	  	}
			array[i]=addDistorsion(detalle,fechaReal,dato,difPromedio);
		}
		size=i-1;

		
		NormalizeArray norm = new NormalizeArray();
		norm.setNormalizedHigh(0.99);
		norm.setNormalizedLow(0.01);

		// create arrays to hold the normalized
		double[] normalized = norm.process(array);
		double[] normalizedExpand = new double[normalized.length+diasHist];
		for (int j = 0; j < size; j++) {
			normalizedExpand[j]=normalized[j];
		}

		MLDataSet result = new BasicMLDataSet();
		


		TemporalWindowArray temp = new TemporalWindowArray(WINDOW_SIZE, 1);
		temp.analyze(normalized);
		result = temp.process(normalized);


		SVMTrain train = new SVMTrain(network, result);
		train.iteration();
		
		NumberFormat f = NumberFormat.getNumberInstance();
		f.setMaximumFractionDigits(4);
		f.setMinimumFractionDigits(4);
		
		long antPeriodo=0;
		double error = 0;
		if (fdesde.after(finEstimacion)) realSize=0;
		long sizeOriginal=size;
		Double dataLast=0.0;
		Double estiLast=0.0;
		int diaInicial =fdesde.get(Calendar.DAY_OF_YEAR);
		Double oldDataAnt=0.0;
		for (int day = diaInicial; fdesde.before(fhasta)||fdesde.equals(fhasta); day++) {// fdesde.getTime()
			BizSerieCalculada serie = new BizSerieCalculada();
			serie.setModelo(detalle.getModelo());
			serie.setCompany(detalle.getCompany());
			serie.setAnio(fdesde.get(Calendar.YEAR));
			serie.setMes(fdesde.get(Calendar.MONTH));
			serie.setDia(fdesde.get(Calendar.DAY_OF_YEAR));
			serie.setFecha(fdesde.getTime());
			// calculate based on actual data

			Double data = detalle.getRealData(fdesde, variable, finEstimacion);
			Double dataAnt = detalle.getRealData(fatras, variable, null);

			if (data==null)
				data=dataLast;
				
			if (data!=0)
				dataLast=data;

			double valorEstimado =0;
			MLData input = new BasicMLData(WINDOW_SIZE);
//			if (((day-diaInicial)+1) - WINDOW_SIZE >= 0) {
			if ((day - WINDOW_SIZE)>= 0) {
				for (i = 0; i < input.size(); i++) {
					int posData = (int) ((day - WINDOW_SIZE) + i);
					if (posData>normalizedExpand.length) continue;
					input.setData(i, normalizedExpand[posData]);
				}
				MLData output = network.compute(input);
				double prediction = output.getData(0)+(day >= realSize?error:0);
				if (detalle.getAcumulativo()) prediction=Math.abs(prediction);
				valorEstimado = norm.getStats().deNormalize(prediction);
				if (Double.isNaN(valorEstimado))
					valorEstimado=0;
				if (day >= realSize) {
					normalizedExpand[size++] = prediction;
					if (valorEstimado<dataLast) {
						serie.setValorEstimado(dataLast);
						valorEstimado=estiLast;
					} else if ( (valorEstimado<estiLast && ascendente)) {
						serie.setValorEstimado(estiLast);
						valorEstimado=estiLast;
					} else
						serie.setValorEstimado(valorEstimado);
				} 
				else {
					error = -prediction +normalizedExpand[day];
					serie.setNullToValorEstimado();
				}
			} else
				serie.setNullToValorEstimado();

			fatras.setTime(fdesde.getTime());
			fatras.add(Calendar.YEAR, -1);
			serie.setVariable(variable);
			estiLast = serie.getValorEstimado();
			if (data!=null) serie.setValor(data);
			if (dataAnt!=null) serie.setValorAnt(dataAnt);
			if (dataAnt!=null && data!=null && data==0)
				dataLast=estiLast+(dataAnt-oldDataAnt);

			if (detalle.getAcumulativo())
				acumul1 +=  day >= realSize ? valorEstimado : serie.getValor();
			else
				acumul1 =  day >= realSize ? valorEstimado : serie.getValor();
			
			long newPeriodo=0;
			if (detalle.getPeriodo().equals(BizDetalle.BIMESTRAL)) newPeriodo = (int)Math.floor((fdesde.get(Calendar.MONTH))/2)+1;
			else if (detalle.getPeriodo().equals(BizDetalle.TRIMESTRAL)) newPeriodo = (int)Math.floor((fdesde.get(Calendar.MONTH))/3)+1;
			else if (detalle.getPeriodo().equals(BizDetalle.CUATRIMESTRAL)) newPeriodo = (int)Math.floor((fdesde.get(Calendar.MONTH))/4)+1;
			else if (detalle.getPeriodo().equals(BizDetalle.SEMESTRAL)) newPeriodo = (int)Math.floor((fdesde.get(Calendar.MONTH))/6)+1;
			else if (detalle.getPeriodo().equals(BizDetalle.ANUAL)) newPeriodo = 1;
			else newPeriodo = 1;
			if (antPeriodo!=newPeriodo) acumul1=0;
			antPeriodo=newPeriodo;
			serie.setAcumulado(acumul1);
			serie.setModelo(detalle.getModelo());
			serie.processInsert();
			if (dataAnt!=null && dataAnt!=0)
				oldDataAnt=dataAnt;
			
			fdesde.add(Calendar.DAY_OF_YEAR, 1);
		}
  }
  
  public double addDistorsion(BizDetalle detalle,Calendar cal, double data,double ajuste) throws Exception {
  		double valor = data+ajuste;
  		JRecords<BizVariacionPaticular> var = new JRecords<BizVariacionPaticular>(BizVariacionPaticular.class);
  		var.addFilter("fecha_desde", cal.getTime(),">=");
  		var.addFilter("id_contrato", detalle.getId());
  		var.addFilter("linea_contrato", detalle.getLinea());
//  		var.addFilter("fecha_hasta", cal.getTime(),"<=");
  		JIterator<BizVariacionPaticular> it = var.getStaticIterator();
  		while (it.hasMoreElements()) {
  			BizVariacionPaticular v = it.nextElement();
  			valor = valor +(valor * v.getVariacion()/100);
  		}
  		return valor;
  }

}
