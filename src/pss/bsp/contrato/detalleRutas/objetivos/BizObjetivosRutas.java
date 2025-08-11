package pss.bsp.contrato.detalleRutas.objetivos;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.nivel.BizNivel;
import pss.bsp.contrato.detalleDatamining.BizDetalleDatamining;
import pss.bsp.interfaces.copa.cabecera.BizCopaCabecera;
import pss.common.regions.company.BizCompany;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JOrderedMap;
import pss.tourism.pnr.BizPNRTicket;

public class BizObjetivosRutas extends JRecords<BizObjetivosRuta> {
;
	public Class<BizObjetivosRuta> getBasedClass() {
		return BizObjetivosRuta.class;
	}
	
	
	String conclusion;

	Double voladoAHoy;


	Double totalTicket;
	Double totalTicktesEsperados;
	Double porcAlcanzado;


	public Double getPorcAlcanzado() {
		return porcAlcanzado;
	}

	public void setPorcAlcanzado(Double porcAlcanzado) {
		this.porcAlcanzado = porcAlcanzado;
	}


	Double volado;
	Double totalPremiosAHoy;
	Double totalPremios;
	Double totalPremiosExtra;
	public Double getTotalPremiosAHoy() {
		return totalPremiosAHoy;
	}

	public void setTotalPremiosAHoy(Double totalPremiosAHoy) {
		this.totalPremiosAHoy = totalPremiosAHoy;
	}
	
	public Double getTotalPremiosExtra() {
		return totalPremiosExtra;
	}

	public void setTotalPremiosExtra(Double totalPremiosExtra) {
		this.totalPremiosExtra = totalPremiosExtra;
	}

	public Double getTotalPremios() {
		return totalPremios;
	}

	public void setTotalPremios(Double totalPremios) {
		this.totalPremios = totalPremios;
	}
	public String getConclusion() {
		return conclusion;
	}

	public Double getTotalTicket() {
		return totalTicket;
	}

	public void setTotalTicket(Double totalTicket) {
		this.totalTicket = totalTicket;
	}

	public Double getTotalTicktesEsperados() {
		return totalTicktesEsperados;
	}

	public void setTotalTicktesEsperados(Double totalTicktesEsperados) {
		this.totalTicktesEsperados = totalTicktesEsperados;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}
	public Double getVoladoAHoy() {
		return voladoAHoy;
	}

	public void setVoladoAHoy(Double voladoAHoy) {
		this.voladoAHoy = voladoAHoy;
	}

	public Double getVolado() {
		return volado;
	}

	public void setVolado(Double volado) {
		this.volado = volado;
	}


	@Override
	public boolean readAll() throws Exception {
		String conclusion="";
		this.endStatic();
    this.setStatic(true);
    String idcompany = getFilterValue("company");
    long idcontrato = Long.parseLong(getFilterValue("contrato"));
    long iddetalle = Long.parseLong(getFilterValue("detalle"));
    JOrderedMap<String, Double> acumPremios = JCollectionFactory.createOrderedMap();
    BizDetalle detalle = new BizDetalle();
    detalle.dontThrowException(true);
    if (!detalle.read(iddetalle)) return false;
    
    BizCompany company =BizBSPCompany.getCompany(idcompany);
 
    
    Calendar periodo = Calendar.getInstance();
    periodo.setTime(detalle.getFDesde());
    
    String rutaFilter = getFilterValue("ruta");
    String cumpleFilter = getFilterValue("mensaje");
    
    
  	StringTokenizer toks = new StringTokenizer(detalle.getMercados()," .,;");
  	while (toks.hasMoreTokens()) {
  		String ruta = toks.nextToken();
  		BizDetalleDatamining detalleRuta = new BizDetalleDatamining();
  		detalleRuta.dontThrowException(true);
  		if (!detalleRuta.readByAutogenerado(detalle.getCompany(), detalle.getId(), ruta))
  			continue;

  		JOrderedMap<String,Double> treeZonas = JCollectionFactory.createOrderedMap();
  		int i=1;
  		String zonas="";
  		String zonaLabels="";
  		double nextLevel=0;
  		JIterator<BizNivel> itN = detalleRuta.getObjNiveles().getStaticIterator();
  		while (itN.hasMoreElements()) {
  			BizNivel nivel = itN.nextElement();
  			if (nextLevel==0) nextLevel=nivel.getValor();
  			String label = nivel.getValorPerc((double)nivel.getValor(), (double)i);
  			treeZonas.addElement(JTools.LPad(""+i, 3, "0")+(label.equals("")?"nivel "+i:label), nivel.getValor());
  			i++;

  		
  		}
			BizObjetivosRuta obj = new BizObjetivosRuta();
			obj.pCompany.setValue(idcompany);
			obj.pId.setValue(detalle.getId());
			obj.pIdDetalle.setValue(iddetalle);
			obj.pLinea.setValue(detalleRuta.getLinea());
			obj.pRuta.setValue(ruta);
			obj.pFaltante.setValue(nextLevel);
			if (detalle.getPax())
				obj.pCantidad.setValue(detalleRuta.getValorActual());
			else
				obj.pCantidad.setValue(getTicketsCantidad(detalle, ruta));
			if (detalle.getPax())
				obj.pTotal.setValue(0);
			else
				obj.pTotal.setValue(getTicketsTotal(detalle, ruta));
			double ms = obj.pCantidad.getValue()==0?0:((double)obj.pCantidad.getValue() * 100.0)/(double)obj.pTotal.getValue();
			obj.pMS.setValue(ms);
			obj.pFMS.setValue(detalleRuta.getSigValorObjetivo());
			obj.pIngreso.setValue(detalleRuta.getCalculeValorTotalFinContrato());
			
			
			zonas="";
			zonaLabels="";
			double lastVal=0;
			if (detalle.getPax()) {
				JIterator<String> itz = treeZonas.getKeyIterator();
				while (itz.hasMoreElements()) {
					String keyZona = itz.nextElement();
					Double valZona = treeZonas.getElement(keyZona);
					if (valZona==0) continue;
					lastVal=valZona;
					zonas += (zonas.equals("")?"":", ")+((long)lastVal);
					zonaLabels="'"+keyZona.substring(3)+"'"+(zonaLabels.equals("")?"":", "+zonaLabels);
				}
				zonas += (zonas.equals("")?"":", ")+(long)lastVal;
				zonaLabels+=",'Sin premio'";
				obj.pZonas.setValue("["+zonas+"]");
				obj.pLabelZonas.setValue("["+zonaLabels+"]");
				obj.pGrafico.setValue(getHtmlDataPAX(obj));
				
			}else{
				JIterator<String> itz = treeZonas.getKeyIterator();
				while (itz.hasMoreElements()) {
					String keyZona = itz.nextElement();
					Double valZona = treeZonas.getElement(keyZona);
					if (valZona==0) continue;
					lastVal=(obj.pFMS.getValue()*(valZona/100.0));
					zonas += (zonas.equals("")?"":", ")+lastVal;
					zonaLabels="'"+keyZona.substring(3)+"'"+(zonaLabels.equals("")?"":", "+zonaLabels);
				}
				zonas += (zonas.equals("")?"":", ")+(((((long)lastVal)/100)+1)*100);
				zonaLabels+=",'Sin premio'";
				
				obj.pZonas.setValue("["+zonas+"]");
				obj.pLabelZonas.setValue("["+zonaLabels+"]");
				obj.pGrafico.setValue(getHtmlData(obj));
			}


			addItem(obj);

  	}
    setConclusion("");
		this.Ordenar("cumple;-cantidad");

    return true;
		
	}
  public String getHtmlDataPAX( BizObjetivosRuta obj) throws Exception {
	  String defin= "{\"title\":'"+obj.pRuta.getValue()+"',\"subtitle\":'%',\"ranges\":"+obj.pZonas.getValue()+",\"rangeLabels\":"+
	  		obj.pLabelZonas.getValue()+",\"measures\":["+
	  		obj.pMS.getValue()+"],\"measureLabels\":['MS'],\"markers\":["+obj.pCantidad.getValue()+"],\"markerLabels\":['PAXs']}";

	  String s="";
  	s+="<div style=\"display:block;width:400px\">";
		s+="<div id=\"span_bullet_"+obj.hashCode()+"\"><svg style='height:40px'> </svg></div>";
		s+="<script>addBullet('span_bullet_"+obj.hashCode()+"',"+defin+",400,40);</script>";
  	s+="</div>";
  	return s;
  }
  public String getHtmlData( BizObjetivosRuta obj) throws Exception {
	  String defin= "{\"title\":'"+obj.pRuta.getValue()+"',\"subtitle\":'%',\"ranges\":"+obj.pZonas.getValue()+",\"rangeLabels\":"+
	  		obj.pLabelZonas.getValue()+",\"measures\":["+
	  		obj.pMS.getValue()+"],\"measureLabels\":['MS'],\"markers\":["+obj.pFMS.getValue()+"],\"markerLabels\":['OBJETIVO']}";

	  String s="";
  	s+="<div style=\"display:block;width:400px\">";
		s+="<div id=\"span_bullet_"+obj.hashCode()+"\"><svg style='height:40px'> </svg></div>";
		s+="<script>addBullet('span_bullet_"+obj.hashCode()+"',"+defin+",400,40);</script>";
  	s+="</div>";
  	return s;
  }

  public double getCantidadEsperada(BizDetalle detalle,double total,double fms) throws Exception {
  	return total * (fms/100.0);
  }
  public boolean getCumple(BizDetalle detalle,double cantidad,double fms) throws Exception {
  	return cantidad>=detalle.getLimiteMinTkt() && fms>=detalle.getFMSMin() && fms<=detalle.getFMSMax();
  }
  public boolean getCumplible(BizDetalle detalle,double cantidad,double fms) throws Exception {
  	return fms>=detalle.getFMSMin() && fms<=detalle.getFMSMax();
  }
  public double getMS(double cantidad,double total) throws Exception {
  	return total==0?0:(cantidad * 100.0)/total;
  }
  public double calculoPorcentaje(double ms,double fms) throws Exception {
  	return fms==0?0:(ms * 100.0)/fms;
  }
  public String getPorcPremio(BizDetalle detalle,double ingreso,double porc) throws Exception {
  	BizNivel nivel=detalle.getObjNivelAlcanzado(porc);
  	if (nivel==null) return "0%";
  	return nivel.getValorPerc(ingreso, porc);
  }
  public double getPremio(BizDetalle detalle,double ingreso,double porc) throws Exception {
  	BizNivel nivel=detalle.getObjNivelAlcanzado(porc);
  	if (nivel==null) return 0;
  	return nivel.getValorPremio(ingreso, porc);
  }
  public String getAnalisis(BizDetalle detalle,BizObjetivosRuta obj,double cantidad,double fms) throws Exception {
  	String error = "";
  	if (cantidad<detalle.getLimiteMinTkt()) error+=(error.equals("")?"":", ")+"cantidad esperada boletos menor a "+detalle.getLimiteMinTkt();
  	if (fms<detalle.getFMSMin()) error+=(error.equals("")?"":", ")+"FMS por debajo de "+detalle.getFMSMin();
  	if (fms>detalle.getFMSMax()) error+=(error.equals("")?"":", ")+"FMS por encima de "+detalle.getFMSMax();
  	if (!obj.pCumple.getValue()) return "El objetivo no llega a los parametros minimos: "+error;
    if (obj.pIngreso.getValue()<=(obj.pTicketPromedio.getValue()*(detalle.getLimiteMinTkt()-1))) return "Cumple objetivo, pero el volado es bajo";
  	return "Cumple objetivo";
  }

  public double getTicketsCantidad(BizDetalle detalle,String ruta) throws Exception {
  	BizPNRTicket tkt = new BizPNRTicket();
  	tkt.addFilter("company", detalle.getCompany());
		if (detalle.getAerolineas()!=null &&!detalle.getAerolineas().trim().equals(""))
			tkt.addFilter("codigoaerolinea",  "("+detalle.getAerolineas()+")","in");
		else
			tkt.addFilter("codigoaerolinea",  detalle.getIdAerolinea());
  	tkt.addFilter("void", false);
		String rutaSQL = ruta;
		if (ruta.length()==6) rutaSQL="BOK-"+ruta;
		else if(ruta.startsWith("BOK")) rutaSQL= ruta.substring(1);

  	tkt.addFilter("market", ""+rutaSQL,"like");
  	StringTokenizer toks = new StringTokenizer(detalle.getTourcodeExcludes(),",;");
  	while (toks.hasMoreTokens()) {
  		String s = toks.nextToken();
    	tkt.addFilter("tour_code", s.trim(),"<>");
  	}
  	StringTokenizer tok2s = new StringTokenizer(detalle.getClasesExcludes(),",;");
  	while (tok2s.hasMoreTokens()) {
  		String s = tok2s.nextToken();
    	tkt.addFilter("clase", s.trim(),"<>");
  	}  	
  	tkt.addFilter("creation_date",  detalle.getFDesde(),">=");
  	tkt.addFilter("creation_date",  detalle.getFHasta(),"<=");
  	return tkt.SelectSumDouble("cant_roundtrip");
  } 
  public double getTicketsTotal(BizDetalle detalle,String ruta) throws Exception {
  	BizPNRTicket tkt = new BizPNRTicket();
		String rutaSQL = ruta;
		if (ruta.length()==6) rutaSQL="BOK-"+ruta;
		else if(ruta.startsWith("BOK")) rutaSQL= ruta.substring(1);
  	
		tkt.addFilter("company", detalle.getCompany());
//  	tkt.addFilter("codigoaerolinea",  detalle.getIdAerolinea());
  	tkt.addFilter("void", false);
  	tkt.addFilter("market", rutaSQL,"like");
  	StringTokenizer toks = new StringTokenizer(detalle.getTourcodeExcludes(),",;");
  	while (toks.hasMoreTokens()) {
  		String s = toks.nextToken();
    	tkt.addFilter("tour_code", s.trim(),"<>");
  	}
  	StringTokenizer tok2s = new StringTokenizer(detalle.getClasesExcludes(),",;");
  	while (tok2s.hasMoreTokens()) {
  		String s = tok2s.nextToken();
    	tkt.addFilter("clase", s.trim(),"<>");
  	}  	
  	tkt.addFilter("creation_date",  detalle.getFDesde(),">=");
  	tkt.addFilter("creation_date",  detalle.getFHasta(),"<=");
  	return tkt.SelectSumDouble("cant_roundtrip");
  } 
    
  // optimizar los tres a un sql
  public double getTicketsVolado(BizDetalle detalle,String ruta) throws Exception {
  	BizPNRTicket tkt = new BizPNRTicket();
		String rutaSQL = ruta;
		if (ruta.length()==6) rutaSQL="BOK-"+ruta;
		else if(ruta.startsWith("BOK")) rutaSQL= ruta.substring(1);
  	tkt.addFilter("company", detalle.getCompany());
		if (detalle.getAerolineas()!=null &&!detalle.getAerolineas().trim().equals(""))
			tkt.addFilter("codigoaerolinea",  "("+detalle.getAerolineas()+")","in");
		else
			tkt.addFilter("codigoaerolinea",  detalle.getIdAerolinea());
  	tkt.addFilter("void", false);
  	tkt.addFilter("market", rutaSQL,"like");
  	StringTokenizer toks = new StringTokenizer(detalle.getTourcodeExcludes(),",;");
  	while (toks.hasMoreTokens()) {
  		String s = toks.nextToken();
    	tkt.addFilter("tour_code", s.trim(),"<>");
  	}
  	StringTokenizer tok2s = new StringTokenizer(detalle.getClasesExcludes(),",;");
  	while (tok2s.hasMoreTokens()) {
  		String s = tok2s.nextToken();
    	tkt.addFilter("clase", s.trim(),"<>");
  	}  	
  	tkt.addFilter("departure_date",  detalle.getFDesde(),">=");
  	tkt.addFilter("departure_date",  detalle.getFHasta(),"<=");
  	return tkt.SelectSumDouble("tarifa");
  } 
  
  public double getTicketsVoladoAHoy(BizDetalle detalle,String ruta) throws Exception {
  	BizPNRTicket tkt = new BizPNRTicket();
		String rutaSQL = ruta;
		if (ruta.length()==6) rutaSQL="BOK-"+ruta;
		else if(ruta.startsWith("BOK")) rutaSQL= ruta.substring(1);

		tkt.addFilter("company", detalle.getCompany());
		if (detalle.getAerolineas()!=null &&!detalle.getAerolineas().trim().equals(""))
			tkt.addFilter("codigoaerolinea",   "("+detalle.getAerolineas()+")","in");
		else
			tkt.addFilter("codigoaerolinea",  detalle.getIdAerolinea());
  	StringTokenizer toks = new StringTokenizer(detalle.getTourcodeExcludes(),",;");
  	while (toks.hasMoreTokens()) {
  		String s = toks.nextToken();
    	tkt.addFilter("tour_code", s.trim(),"<>");
  	}
  	StringTokenizer tok2s = new StringTokenizer(detalle.getClasesExcludes(),",;");
  	while (tok2s.hasMoreTokens()) {
  		String s = tok2s.nextToken();
    	tkt.addFilter("clase", s.trim(),"<>");
  	}
  	
  	tkt.addFilter("void", false);
  	tkt.addFilter("market", rutaSQL,"like");
  	tkt.addFilter("departure_date",  detalle.getFDesde(),">=");
  	tkt.addFilter("departure_date",  new Date(),"<=");
  	return tkt.SelectSumDouble("tarifa");
  } 
  
}