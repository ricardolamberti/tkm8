package pss.bsp.contrato.detalle.prediccion;

import java.util.Calendar;
import java.util.Date;

import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.temporal.TemporalDataDescription;
import org.encog.ml.data.temporal.TemporalMLDataSet;
import org.encog.ml.data.temporal.TemporalPoint;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.util.arrayutil.NormalizeArray;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.series.calculado.BizSerieCalculada;
import pss.common.event.sql.datos.BizSqlEventDato;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.collections.JIterator;

public class JNeuronalStandart implements IPrediccion {
  public final static double MAX_ERROR = 0.01;
	public final static int WINDOW_SIZE = 30;
	


	
	public BasicNetwork createNetwork()
	{
		BasicNetwork network = new BasicNetwork();
		network.addLayer(new BasicLayer(WINDOW_SIZE));
		network.addLayer(new BasicLayer(10));
		network.addLayer(new BasicLayer(1));
		network.getStructure().finalizeStructure();
		network.reset();
		return network;
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

		TemporalMLDataSet result = new TemporalMLDataSet(WINDOW_SIZE, 1);

		TemporalDataDescription desc = new TemporalDataDescription(TemporalDataDescription.Type.RAW, true, true);
		result.addDescription(desc);

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
//			Calendar newFecha = Calendar.getInstance();
//			newFecha.setTime(dato.getFecha());
//			if (newFecha.get(Calendar.YEAR)<fdesde.get(Calendar.YEAR)) 
//				newFecha.add(Calendar.YEAR,1);
//	  	BizVariacion varia = new BizVariacion();
//	  	varia.dontThrowException(true);
//	  	if (varia.read(newFecha)) {
//	  		valor += ((valor*varia.getVariacion())/100);
//	  	}
//			if (valor==0) continue;
			array[i++] = valor;
		}
		size=i-1;
		int j = 0;
		int news=0;
		NormalizeArray norm = new NormalizeArray();
		norm.setNormalizedHigh(0.9);
		norm.setNormalizedLow(0.1);

		// create arrays to hold the normalized
		double[] normalized = norm.process(array);

		double[] normalizedExpand = new double[normalized.length+365];

		for (j = 0; j < size; j++) {
			TemporalPoint point = new TemporalPoint(1);
			point.setSequence(j);
			point.setData(0, normalized[j]);
			result.getPoints().add(point);
			normalizedExpand[j]=normalized[j];
		}

		result.generate();
		final Train train = new ResilientPropagation(network, result);
		do {
			train.iteration();
		} while (train.getError() > MAX_ERROR);
		
		long antPeriodo=0;
		
		for (int day = 1; fdesde.before(fhasta); day++) {
			BizSerieCalculada serie = new BizSerieCalculada();
			serie.setModelo(detalle.getModelo());
			serie.setCompany(detalle.getCompany());
			serie.setAnio(fdesde.get(Calendar.YEAR));
			serie.setMes(fdesde.get(Calendar.MONTH));
			serie.setDia(fdesde.get(Calendar.DAY_OF_YEAR));
			serie.setFecha(fdesde.getTime());
			// calculate based on actual data
			MLData input = new BasicMLData(WINDOW_SIZE);

			boolean estimado = false;
			if (day - WINDOW_SIZE >= 0) {
				for (i = 0; i < input.size(); i++) {
					int posData = (int) ((day - WINDOW_SIZE) + i);
					input.setData(i, normalizedExpand[posData]);
				}
				estimado = true;
			}

			fatras.setTime(fdesde.getTime());
			fatras.add(Calendar.YEAR, -1);
			serie.setVariable(variable);
			serie.setValor(detalle.getRealData(fdesde, variable, finEstimacion));
			serie.setValorAnt(detalle.getRealData(fatras, variable, null));

			if (estimado) {
				MLData output = network.compute(input);
				double prediction = output.getData(0);


				double valorEstimado =norm.getStats().deNormalize(prediction);
				
				serie.setValorEstimado(valorEstimado);
				if (day >= size) {
					normalizedExpand[size++] = prediction;
				}
				if (detalle.getAcumulativo())
					acumul1 += fdesde.after(finEstimacion) ? valorEstimado : serie.getValor();
				else
					acumul1 = fdesde.after(finEstimacion) ? valorEstimado : serie.getValor();
			} else {
				serie.setNullToValorEstimado();
				if (detalle.getAcumulativo())
					acumul1 += fdesde.after(finEstimacion) ? 0 : serie.getValor();
				else
					acumul1 = fdesde.after(finEstimacion) ? 0 : serie.getValor();
			}

			
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
