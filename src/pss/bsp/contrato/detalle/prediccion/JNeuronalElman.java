package pss.bsp.contrato.detalle.prediccion;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.neural.pattern.ElmanPattern;
import org.encog.util.arrayutil.NormalizeArray;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.series.calculado.BizSerieCalculada;
import pss.common.event.sql.datos.BizSqlEventDato;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.collections.JIterator;

public class JNeuronalElman  implements IPrediccion {
	public final static double MAX_ERROR = 0.01;


	public BasicNetwork createNetwork()
	{
		ElmanPattern pattern = new ElmanPattern();
		pattern.setInputNeurons(1);
		pattern.addHiddenLayer(10);
		pattern.setOutputNeurons(1);
		pattern.setActivationFunction(new ActivationSigmoid());
		return (BasicNetwork)pattern.generate();
	}
	
  public  void processPopulate(BizDetalle detalle,Date fechaEsti, long variable) throws Exception {
  	Calendar fatras = Calendar.getInstance();
  	fatras.setTime(new Date());
  	int diasHist = 365;
  	fatras.setTime(JDateTools.getFirstDayOfYear(new Date()));
  	//fatras.add(Calendar.DAY_OF_YEAR, -diasHist);
  	Calendar fdesde = Calendar.getInstance();
  	fdesde.setTime(detalle.getObjContrato().getFechaDesde());
  	Calendar fhasta = Calendar.getInstance();
  	fhasta.setTime(detalle.getObjContrato().getFechaHasta());
  	double acumul1 = 0;
  	Calendar finEstimacion = Calendar.getInstance();
  	finEstimacion.setTime(fechaEsti==null?new Date():fechaEsti);// en verdad deberia ser ultimo archivo GDS
  	detalle.processEliminarSerieCalculada(variable);
		BasicNetwork network = createNetwork();


		// llenar el array deda la tabla de datos conocidos
		JRecords<BizSqlEventDato> datos = new JRecords<BizSqlEventDato>(BizSqlEventDato.class);
		datos.addFilter("id_evento", variable);
		datos.setTop(diasHist);
		datos.addFilter("fecha",fatras.getTime(),">=");
		datos.addOrderBy("fecha","ASC");
		int size = (int)datos.selectCount();
		double[] array = new double[size];
		int i = 0;
		
		JIterator<BizSqlEventDato> it = datos.getStaticIterator();
		while (it.hasMoreElements()) {
			BizSqlEventDato dato = it.nextElement();
			double valor = dato.getValue();
//			if (valor==0) continue;
			array[i++] = valor;
		}
		size=i-1;

		
		NormalizeArray norm = new NormalizeArray();
		norm.setNormalizedHigh(0.9);
		norm.setNormalizedLow(0.1);

		// create arrays to hold the normalized
		double[] normalized = norm.process(array);
		double[] normalizedExpand = new double[normalized.length+365];
		MLDataSet result = new BasicMLDataSet();
		
		for(int day = 0;day<size;day++)
		{
			MLData inputData = new BasicMLData(1);
			MLData idealData = new BasicMLData(1);
			inputData.setData(0,normalized[day]);
			idealData.setData(0,normalized[day+1]);
			result.add(inputData,idealData);
			normalizedExpand[day]=normalized[day];
		}

		final Train train = new ResilientPropagation(network, result);

		do {
			train.iteration();
		} while(train.getError() > MAX_ERROR);
		
		NumberFormat f = NumberFormat.getNumberInstance();
		f.setMaximumFractionDigits(4);
		f.setMinimumFractionDigits(4);
		
		long antPeriodo=0;
		
		BasicNetwork regular = (BasicNetwork)network.clone();
		
		regular.clearContext();
		long sizeOriginal=size;

		for (int day = 1; fdesde.before(fhasta); day++) {
			BizSerieCalculada serie = new BizSerieCalculada();
			serie.setModelo(detalle.getModelo());
			serie.setCompany(detalle.getCompany());
			serie.setAnio(fdesde.get(Calendar.YEAR));
			serie.setMes(fdesde.get(Calendar.MONTH));
			serie.setDia(fdesde.get(Calendar.DAY_OF_YEAR));
			serie.setFecha(fdesde.getTime());
			// calculate based on actual data
			MLData input = new BasicMLData(1);
			input.setData(0, normalizedExpand[day-1]);
			
			MLData output = regular.compute(input);
			double prediction = output.getData(0);
			if (day >= size) {
				normalizedExpand[size++] = prediction;
			}
			
			fatras.setTime(fdesde.getTime());
			fatras.add(Calendar.YEAR, -1);
			serie.setVariable(variable);
			serie.setValor(detalle.getRealData(fdesde, variable, finEstimacion));
			serie.setValorAnt(detalle.getRealData(fatras, variable, null));

			double valorEstimado = norm.getStats().deNormalize(prediction);

			serie.setValorEstimado(valorEstimado);
			
			if (detalle.getAcumulativo())
				acumul1 +=  day >= sizeOriginal ? valorEstimado : serie.getValor();
			else
				acumul1 =  day >= sizeOriginal ? valorEstimado : serie.getValor();
			
			long newPeriodo=0;
			if (detalle.getPeriodo().equals(BizDetalle.BIMESTRAL)) newPeriodo = (int)Math.floor((fdesde.get(Calendar.MONTH))/2)+1;
			if (detalle.getPeriodo().equals(BizDetalle.TRIMESTRAL)) newPeriodo = (int)Math.floor((fdesde.get(Calendar.MONTH))/3)+1;
			if (detalle.getPeriodo().equals(BizDetalle.CUATRIMESTRAL)) newPeriodo = (int)Math.floor((fdesde.get(Calendar.MONTH))/4)+1;
			if (detalle.getPeriodo().equals(BizDetalle.SEMESTRAL)) newPeriodo = (int)Math.floor((fdesde.get(Calendar.MONTH))/6)+1;
			if (detalle.getPeriodo().equals(BizDetalle.ANUAL)) newPeriodo = 1;
			if (antPeriodo!=newPeriodo) acumul1=0;
			antPeriodo=newPeriodo;
			serie.setAcumulado(acumul1);
			serie.setModelo(detalle.getModelo());
			serie.processInsert();

			fdesde.add(Calendar.DAY_OF_YEAR, 1);
		}
  }

}
