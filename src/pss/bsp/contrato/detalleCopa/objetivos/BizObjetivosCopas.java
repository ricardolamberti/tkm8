package pss.bsp.contrato.detalleCopa.objetivos;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.nivel.BizNivel;
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

public class BizObjetivosCopas extends JRecords<BizObjetivosCopa> {
;
	public Class<BizObjetivosCopa> getBasedClass() {
		return BizObjetivosCopa.class;
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
 
    JRecords<BizCopaCabecera> min = new JRecords<BizCopaCabecera>(BizCopaCabecera.class);
		min.addFilter("company", idcompany);
		min.addFilter("periodoDesde",detalle.getFHasta(),"<=");
		min.addOrderBy("periodoDesde","desc");
    min.setTop(1);
    JIterator<BizCopaCabecera> it = min.getStaticIterator();
    if (!it.hasMoreElements()) {
      JRecords<BizCopaCabecera> min2 = new JRecords<BizCopaCabecera>(BizCopaCabecera.class);
    	min2.addFilter("company", company.getParentCompany());
  		min2.addFilter("periodoDesde",detalle.getFHasta(),"<=");
  		min2.addOrderBy("periodoDesde","desc");
      min2.setTop(1);
      it = min2.getStaticIterator();
      if (!it.hasMoreElements()) {
      	conclusion="FALTA INTERFAZ FMS";
      	setConclusion(conclusion);
      	return false;
      }
   
    }
    
    BizCopaCabecera cabecera = it.nextElement();
    int id=0;
    
    // grabar fecha generacion
    // fecha vigencia
    
    Calendar periodo = Calendar.getInstance();
    periodo.setTime(detalle.getFDesde());
    
    String rutaFilter = getFilterValue("ruta");
    String cumpleFilter = getFilterValue("mensaje");
    
    


		JBaseRegistro regs=JBaseRegistro.recordsetFactory();
		String sql="select  TUR_PNR_BOOKING.company as company, ROW_NUMBER() OVER() as linea, '"+detalle.getIdAerolinea()+"'  as aerolinea,BSP_COPA_DETALLE.ORIGEN||BSP_COPA_DETALLE.destino  as ruta,COALESCE(count(*),0) as total,  ";
		sql+= " ( select  COALESCE(count(*),0)   from TUR_PNR_BOOKING b WHERE (b.company=  TUR_PNR_BOOKING.company) AND  ";
		sql+= " b.carrier = '"+detalle.getIdAerolinea()+"'   ";
		sql+= " AND B.origen = BSP_COPA_DETALLE.origen AND B.DESTINO = BSP_COPA_DETALLE.destino   ";
//		if (rutaFilter!=null) {
//			sql+= " AND BSP_COPA_DETALLE.marketing_id ilike '%"+rutaFilter+"%'";
//		}
		sql+= " AND  b.FechaDespegue between '"+JDateTools.DateToString(detalle.getFDesde())+"' and '"+JDateTools.DateToString(detalle.getFHasta())+"' ) as cantidad,MAX(BSP_COPA_DETALLE.fms) as fms ";
		sql+= " ,MAX(BSP_COPA_DETALLE.minimo) as minimo from  TUR_PNR_BOOKING  JOIN BSP_COPA_DETALLE   ON BSP_COPA_DETALLE.company='"+cabecera.getCompany()+"' AND idpdf='"+cabecera.getIdpdf()+"' ";
		//sql+= " AND  TUR_PNR_BOLETO.market like '%BOK-'||BSP_COPA_DETALLE.marketing_id||'%'  ";
		sql+= " AND ( TUR_PNR_BOOKING.origen = BSP_COPA_DETALLE.origen AND TUR_PNR_BOOKING.DESTINO = BSP_COPA_DETALLE.destino)  ";
			if (rutaFilter!=null) {
			sql+= " AND BSP_COPA_DETALLE.marketing_id ilike  '%"+rutaFilter+"%'";
		}
		if (!detalle.getMercados().equals("")) {
			String rutalista="";
	  	StringTokenizer toks = new StringTokenizer(detalle.getMercados()," .,;");
	  	while (toks.hasMoreTokens()) {
	  		String s = toks.nextToken();
	  		rutalista+=(rutalista.equals("")?"":",")+"'"+s.trim()+"'";
	  	}
	  	sql+=" AND BSP_COPA_DETALLE.marketing_id in ("+rutalista+") ";
		}
		sql+= " WHERE (TUR_PNR_BOOKING.company= '"+idcompany+"')   ";
		sql+= " AND ";
		sql+= " TUR_PNR_BOOKING.FechaDespegue between '"+JDateTools.DateToString(detalle.getFDesde())+"' and '"+JDateTools.DateToString(detalle.getFHasta())+"'  ";
		sql+= " group by TUR_PNR_BOOKING.company,BSP_COPA_DETALLE.ORIGEN,BSP_COPA_DETALLE.DESTINO";
		regs.ExecuteQuery(sql);

		JOrderedMap<String,Double> treeZonas = JCollectionFactory.createOrderedMap();
		int i=1;
		String zonas="";
		String zonaLabels="";
		JIterator<BizNivel> itN = detalle.getObjNiveles().getStaticIterator();
		while (itN.hasMoreElements()) {
			BizNivel nivel = itN.nextElement();
			nivel.getValor();
			String label = nivel.getValorPerc((double)nivel.getValor(), (double)i);
			treeZonas.addElement(JTools.LPad(""+i, 3, "0")+(label.equals("")?"nivel "+i:label), nivel.getValor());
			i++;
		}

		boolean hayVerdes=false;
		long tkt=0;
		long destacado=0;
		double volado=0;
		double voladoAHoy=0;
		double acumCant=0;
		double acumCantEsp=0;
		double acum=0;
		double acumAHoy=0;
		double premioExtra=0;
  	while (regs.next()) {
			BizObjetivosCopa obj = new BizObjetivosCopa();
			obj.pCompany.setValue(regs.CampoAsStr("company"));
			obj.pId.setValue(detalle.getId());
			obj.pLinea.setValue(regs.CampoAsLong("linea"));
			obj.pRuta.setValue(regs.CampoAsStr("ruta"));
			obj.pCantidad.setValue(regs.CampoAsLong("cantidad"));
	    obj.pTotal.setValue(regs.CampoAsLong("total"));
			obj.pFMS.setValue(regs.CampoAsFloat("fms"));
			obj.pMinimo.setValue(regs.CampoAsFloat("minimo")==null?0:regs.CampoAsFloat("minimo"));
			obj.pCantEsperada.setValue(getCantidadEsperada(detalle,obj.pTotal.getValue(),obj.pFMS.getValue()));
			obj.pCumple.setValue(getCumple(detalle,obj.pCantidad.getValue(),obj.pTotal.getValue(),obj.pCantEsperada.getValue(),obj.pFMS.getValue(),obj.pMinimo.getValue()));
			obj.pCumplible.setValue(getCumplible(detalle,obj.pCantEsperada.getValue(),obj.pFMS.getValue()));
			obj.pIdPDF.setValue(cabecera.getIdpdf());
			obj.pCompanyPDF.setValue(cabecera.getCompany());
			obj.pIdContrato.setValue(idcontrato);
			obj.pIdDetalle.setValue(iddetalle);
			
			if (obj.pCumple.getValue()) hayVerdes=true;
			if (cumpleFilter!=null) {
		    if (cumpleFilter.equals(BizObjetivosCopa.SOLO_CUMPLE) && !obj.pCumple.getValue()) continue;
		    if (cumpleFilter.equals(BizObjetivosCopa.SOLO_NO_CUMPLE) && obj.pCumple.getValue()) continue;
		    if (cumpleFilter.equals(BizObjetivosCopa.FALTA_CANTIDAD) && (!obj.pCumplible.getValue())) continue;
		    if (cumpleFilter.equals(BizObjetivosCopa.FALTA_CANTIDAD) && (obj.pCantEsperada.getValue()>=obj.pMinimo.getValue())) continue;
		  	
			}
			obj.pMS.setValue(getMS(regs.CampoAsLong("cantidad"), (regs.CampoAsLong("total")) ));
			
			zonas="";
			zonaLabels="";
			double lastVal=0;
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
			obj.pIngresoAHoy.setValue(getTicketsVoladoAHoy(detalle,obj.pRuta.getValue()));
			obj.pIngreso.setValue(getTicketsVolado(detalle,obj.pRuta.getValue()));
			obj.pPorcentaje.setValue(calculoPorcentaje(obj.pMS.getValue(),obj.pFMS.getValue()));
//			obj.pTicketPromedio.setValue(getTicketsVoladoPromedio(detalle,obj.pRuta.getValue()));
	    obj.pFaltante.setValue(0);
			if (!obj.pCumple.getValue()) {
				if (obj.pCumplible.getValue() ) {
			    long cantTotalTicketMinimo = (long)Math.ceil(obj.pMinimo.getValue()*obj.pFMS.getValue())/100;
			    obj.pFaltante.setValue((long)(obj.pMinimo.getValue()-obj.pTotal.getValue()>cantTotalTicketMinimo-obj.pCantidad.getValue()?obj.pMinimo.getValue()-obj.pTotal.getValue():cantTotalTicketMinimo-obj.pCantidad.getValue()));
				}
			}

			if (cumpleFilter!=null) {
		    if (cumpleFilter.equals(BizObjetivosCopa.DESTACADOS) && !obj.pCumplible.getValue()) continue;
		    if (cumpleFilter.equals(BizObjetivosCopa.DESTACADOS) && !obj.pCumplible.getValue()) continue;
		    if (cumpleFilter.equals(BizObjetivosCopa.DESTACADOS) && obj.pFaltante.getValue()>10) continue;
		    if (cumpleFilter.equals(BizObjetivosCopa.DESTACADOS) && !obj.pCumple.getValue()) destacado++;
		  	
			}
			if (obj.pCumple.getValue() ) {
				acumCant+=obj.pCantidad.getValue();
				acumCantEsp+=obj.pCantEsperada.getValue();
			}
			volado+=obj.pIngreso.getValue();
			voladoAHoy+=obj.pIngresoAHoy.getValue();

			tkt+=obj.pFaltante.getValue();
			obj.pMensaje.setValue(getAnalisis(detalle,obj,obj.pCantEsperada.getValue(),obj.pFMS.getValue(),obj.pMinimo.getValue()));
			obj.pGrafico.setValue(getHtmlData(obj));

	    
			addItem(obj);
			acumAHoy+=getPremio(detalle,obj.pIngresoAHoy.getValue(),obj.pPorcentaje.getValue());
		}
  	
  	setTotalPremios(acum);
   	setTotalPremiosAHoy(acumAHoy);
    setTotalPremiosExtra(premioExtra);
    setVolado(volado);
    setTotalTicket(acumCant);
    setTotalTicktesEsperados(acumCantEsp);
    setVoladoAHoy(voladoAHoy);
    
    setPorcAlcanzado(acumCantEsp==0/*||acumCant<acumCantEsp*/?0:(acumCant*100.0)/acumCantEsp);
    
    if (hayVerdes) {
    	if ((acumCantEsp-acumCant)>0) {
  	    conclusion="Se necesita vender "+((long)Math.ceil(acumCantEsp-acumCant))+" tickets en las rutas verdes para llegar a la meta";
    		
    	} else {
  	    conclusion="Se ha vendido la cantidad esperada";
    		
    	}
    } else {
	    conclusion="Se necesita vender "+tkt+" tickets en las rutas amarillas para maximizar la ganancia";
    }
    setConclusion(conclusion);
		this.Ordenar("cumple;faltante;-cantidad");

    return true;
		
	}

  public String getHtmlData( BizObjetivosCopa obj) throws Exception {
	  String defin= "{\"title\":'"+obj.pRuta.getValue()+"',\"subtitle\":'%',\"ranges\":"+obj.pZonas.getValue()+",\"rangeLabels\":"+
	  		obj.pLabelZonas.getValue()+",\"measures\":["+
	  		obj.pMS.getValue()+"],\"measureLabels\":['MS'],\"markers\":["+obj.pFMS.getValue()+"],\"markerLabels\":['FMS']}";

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
  public boolean getCumple(BizDetalle detalle,double cantidad,double total,double esperada,double fms,double minimo) throws Exception {
  	return cantidad>=esperada && total>=minimo && fms>=detalle.getFMSMin() && fms<=detalle.getFMSMax() && esperada>=detalle.getLimiteMinTkt();
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
  public String getAnalisis(BizDetalle detalle,BizObjetivosCopa obj,double cantidad,double fms, double minimo) throws Exception {
  	String error = "";
  	if (cantidad<minimo) error+=(error.equals("")?"":", ")+"cantidad esperada boletos menor a "+minimo;
  	if (fms<detalle.getFMSMin()) error+=(error.equals("")?"":", ")+"FMS por debajo de "+detalle.getFMSMin();
  	if (fms>detalle.getFMSMax()) error+=(error.equals("")?"":", ")+"FMS por encima de "+detalle.getFMSMax();
  	if (!obj.pCumple.getValue()) return "El objetivo no llega a los parametros minimos: "+error;
//    if (obj.pIngreso.getValue()<=(obj.pTicketPromedio.getValue()*(detalle.getLimiteMinTkt()-1))) return "Cumple objetivo, pero el volado es bajo";
  	return "Cumple objetivo";
  }
  
  // optimizar los tres a un sql
  public double getTicketsVolado(BizDetalle detalle,String ruta) throws Exception {
  	BizPNRTicket tkt = new BizPNRTicket();
  	tkt.addFilter("company", detalle.getCompany());
  	tkt.addFilter("codigoaerolinea",  detalle.getIdAerolinea());
  	tkt.addFilter("void", false);
  	tkt.addFilter("market", "BOK-"+ruta,"like");
  	StringTokenizer toks = new StringTokenizer(detalle.getTourcodeExcludes(),",;");
  	while (toks.hasMoreTokens()) {
  		String s = toks.nextToken();
  		if (!s.startsWith("IATA:")) {
  			tkt.addFilter("tour_code", s.trim(),"<>");
  		} else
  			tkt.addFilter("nro_iata", s.trim().substring(5),"<>");
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
  public double getTicketsVoladoPromedio(BizDetalle detalle,String ruta) throws Exception {
  	BizPNRTicket tkt = new BizPNRTicket();
  	tkt.addFilter("company", detalle.getCompany());
  	tkt.addFilter("codigoaerolinea",  detalle.getIdAerolinea());
  	StringTokenizer toks = new StringTokenizer(detalle.getTourcodeExcludes(),",;");
  	while (toks.hasMoreTokens()) {
  		String s = toks.nextToken();
  		if (!s.startsWith("IATA:")) {
  			tkt.addFilter("tour_code", s.trim(),"<>");
  		} else
  			tkt.addFilter("nro_iata", s.trim().substring(5),"<>");
  	}
  	StringTokenizer tok2s = new StringTokenizer(detalle.getClasesExcludes(),",;");
  	while (tok2s.hasMoreTokens()) {
  		String s = tok2s.nextToken();
    	tkt.addFilter("clase", s.trim(),"<>");
  	}
  	tkt.addFilter("void", false);
  	tkt.addFilter("market", "BOK-"+ruta,"like");
  	tkt.addFilter("departure_date",  detalle.getFDesde(),">=");
  	tkt.addFilter("departure_date",  detalle.getFHasta(),"<=");
  	return tkt.SelectAvgDouble("tarifa");
  } 
  
  public double getTicketsVoladoAHoy(BizDetalle detalle,String ruta) throws Exception {
  	BizPNRTicket tkt = new BizPNRTicket();
  	tkt.addFilter("company", detalle.getCompany());
  	tkt.addFilter("codigoaerolinea",  detalle.getIdAerolinea());
  	StringTokenizer toks = new StringTokenizer(detalle.getTourcodeExcludes(),",;");
  	while (toks.hasMoreTokens()) {
  		String s = toks.nextToken();
  		if (!s.startsWith("IATA:")) {
  			tkt.addFilter("tour_code", s.trim(),"<>");
  		} else
  			tkt.addFilter("nro_iata", s.trim().substring(5),"<>");
  	}
  	StringTokenizer tok2s = new StringTokenizer(detalle.getClasesExcludes(),",;");
  	while (tok2s.hasMoreTokens()) {
  		String s = tok2s.nextToken();
    	tkt.addFilter("clase", s.trim(),"<>");
  	}
  	
  	tkt.addFilter("void", false);
  	tkt.addFilter("market", "BOK-"+ruta,"like");
  	tkt.addFilter("departure_date",  detalle.getFDesde(),">=");
  	tkt.addFilter("departure_date",  new Date(),"<=");
  	return tkt.SelectSumDouble("tarifa");
  } 
  
}