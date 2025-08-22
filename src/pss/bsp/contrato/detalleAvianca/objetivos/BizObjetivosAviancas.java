package pss.bsp.contrato.detalleAvianca.objetivos;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.nivel.BizNivel;
import pss.common.regions.company.BizCompany;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JOrderedMap;
import pss.tourism.pnr.BizPNRTicket;

public class BizObjetivosAviancas extends JRecords<BizObjetivosAvianca> {
;
	public Class<BizObjetivosAvianca> getBasedClass() {
		return BizObjetivosAvianca.class;
	}
	
	
	String conclusion;

	Double voladoAHoy;


	Double totalTicket;
	Double totalPuntos;
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

	public Double getTotalPuntos() {
		return totalPuntos;
	}

	public void setTotalPuntos(Double totalTicktesEsperados) {
		this.totalPuntos = totalTicktesEsperados;
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
    int id=0;
    
    // grabar fecha generacion
    // fecha vigencia
    
    Calendar periodo = Calendar.getInstance();
    periodo.setTime(detalle.getFDesde());
    
    String rutaFilter = getFilterValue("ruta");
    String cumpleFilter = getFilterValue("mensaje");
    
    
    double puntosMin=0;
		JOrderedMap<String,Double> treeZonas = JCollectionFactory.createOrderedMap();
		int i=1;
		String zonas="";
		String zonaLabels="";
		JIterator<BizNivel> itN = detalle.getObjNiveles().getStaticIterator();
		while (itN.hasMoreElements()) {
			BizNivel nivel = itN.nextElement();
			double pm = nivel.getValor();
			if (puntosMin==0||pm<puntosMin)
				puntosMin=pm;
			String label = nivel.getValorPerc((double)nivel.getValor(), (double)i);
			treeZonas.addElement(JTools.LPad(""+i, 3, "0")+(label.equals("")?"nivel "+i:label), nivel.getValor());
			i++;
		}

		long tktAm=0;
		long tktRj=0;
		long pfAm=0;
		long pfRj=0;
		double volado=0;
		double voladoAHoy=0;
		double acumCant=0;
		double acumPuntos=0;
		double acum=0;
		double acumAHoy=0;
		double premioExtra=0;
		long datos=0;
		boolean bookingCalc=false;
		JBaseRegistro regs=JBaseRegistro.recordsetFactory();
		if (!detalle.getMercados().equals("")) {
			String rutalista="";
	  	StringTokenizer toks = new StringTokenizer(detalle.getMercados()," .,;");
	  	while (toks.hasMoreTokens()) {
	  		String ruta = toks.nextToken().trim();
	  		bookingCalc= (ruta.charAt(0)>='a'&&ruta.charAt(0)<='z');
	  		ruta = ruta.toUpperCase();
	  		String rutaBusqueda=ruta.startsWith("BOK")?ruta.substring(1):ruta;
	  		BizDetalle detalleRuta = new BizDetalle();
	  		detalleRuta.dontThrowException(true);
	  		if (!detalleRuta.readByAutogenerado(detalle.getCompany(), detalle.getId(), ruta))
	  			continue;
	  		
				double maxPuntos=getMaxPuntos(detalleRuta);

				String sql="select     ";
				if (bookingCalc) {
		  		sql+= " ( select  COALESCE(count(*),0)   from TUR_PNR_BOOKING b WHERE (b.company=  TUR_PNR_BOOKING.company)   "
		  				+ "AND  b.mercado like '%"+rutaBusqueda+"%' AND  b.fecha_emision between '"+JDateTools.DateToString(detalle.getFDesde())+"' and '"+
		  				JDateTools.DateToString(detalle.getFHasta())+"' ";
		  		sql+= " ) ";
		  		sql+= " as total, ";
		  		sql+= " COALESCE(count(*),0)  as cantidad ";
		  		sql+= " from  TUR_PNR_BOOKING   ";
		  		sql+= " WHERE (TUR_PNR_BOOKING.company= '"+detalle.getCompany()+"')   ";
		  	  sql+= " AND TUR_PNR_BOOKING.fecha_emision between '"+JDateTools.DateToString(detalle.getFDesde())+"' and '"+JDateTools.DateToString(detalle.getFHasta())+"'  ";
		  		sql+= " AND TUR_PNR_BOOKING.Carrier_placa = '"+detalle.getIdAerolinea()+"' AND TUR_PNR_BOOKING.mercado like '%"+rutaBusqueda+"%'  ";
//		  		if (detalle.hasIatas())
//			  		sql+= " AND TUR_PNR_BOLETO.nro_iata in ("+detalle.getIatas()+")  ";
		  		sql+= " GROUP BY TUR_PNR_BOOKING.company ";
					
				} else {
					sql += " ( select  COALESCE(count(*),0)   from TUR_PNR_BOLETO b WHERE (b.company=  TUR_PNR_BOLETO.company)    AND  b.is_ticket = 'S' AND  b.void = 'N'  " + "AND  b.market like '%" + rutaBusqueda + "%' AND  b.creation_date between '" + JDateTools.DateToString(detalle.getFDesde()) + "' and '" + JDateTools.DateToString(detalle.getFHasta()) + "' ";
		  		if (detalle.hasIatas())
			  		sql+= " AND b.nro_iata in ("+detalle.getIatas()+")  ";
					sql += " ) ";
					sql += " as total, ";
					sql += " COALESCE(count(*),0) as cantidad ";
					sql += " from  TUR_PNR_BOLETO   ";
					sql += " WHERE (TUR_PNR_BOLETO.company= '" + detalle.getCompany() + "')   ";
					sql += " AND TUR_PNR_BOLETO.void = 'N' AND  TUR_PNR_BOLETO.is_ticket = 'S' ";
					sql += " AND TUR_PNR_BOLETO.creation_date between '" + JDateTools.DateToString(detalle.getFDesde()) + "' and '" + JDateTools.DateToString(detalle.getFHasta()) + "'  ";
					sql += " AND TUR_PNR_BOLETO.codigoAEROLINEA = '" + detalle.getIdAerolinea() + "' AND TUR_PNR_BOLETO.market like '%" + rutaBusqueda + "%'  ";
		  		if (detalle.hasIatas())
			  		sql+= " AND TUR_PNR_BOLETO.nro_iata in ("+detalle.getIatas()+")  ";
					sql += " GROUP BY TUR_PNR_BOLETO.company ";
					
				}

	  		regs.ExecuteQuery(sql);
	  		boolean find=false;
	    	while (regs.next()) {
	    		find=true;
	    		datos++;
	  			BizObjetivosAvianca obj = new BizObjetivosAvianca();
	  			obj.pCompany.setValue(detalle.getCompany());
	  			obj.pId.setValue(detalle.getId());
	  			obj.pLinea.setValue(i++);
	  			obj.pRuta.setValue(ruta);
	  			obj.pIdDetalleRuta.setValue(detalleRuta.getLinea());
	  			obj.setObjDetalleRuta(detalleRuta);
	 				obj.pCantidad.setValue(regs.CampoAsLong("cantidad"));
	  	    obj.pTotal.setValue(regs.CampoAsLong("total"));
	  			obj.pFMS.setValue(getFMS(obj.pCantidad.getValue(), (regs.CampoAsLong("total")),detalle.getLimiteMinTkt() ));
	  			obj.pMS.setValue(getMS(obj.pCantidad.getValue(), (regs.CampoAsLong("total")) ));
	  			obj.pIdContrato.setValue(idcontrato);
	  			obj.pIdDetalle.setValue(iddetalle);	  			
	  			if (detalle.isPuntos()) {
	  				obj.pPuntos.setValue(obj.pTotal.getValue()<detalle.getLimiteMinTkt()?0:getCantidadPuntos(detalleRuta,obj.pMS.getValue()));
		  			obj.pCumple.setValue(getCumple(detalleRuta,obj.pTotal.getValue(),obj.pPuntos.getValue(),maxPuntos,detalle.getLimiteMinTkt()));
		  			obj.pConPuntos.setValue(obj.pPuntos.getValue()!=0);
		  			if (cumpleFilter!=null) {
		  		    if (cumpleFilter.equals(BizObjetivosAvianca.FULL_PUNTOS) && !obj.pCumple.getValue()) continue;
		  		    if (cumpleFilter.equals(BizObjetivosAvianca.CON_ALGUNOS_PUNTOS) && obj.pPuntos.getValue()==0) continue;
		  		    if (cumpleFilter.equals(BizObjetivosAvianca.SIN_PUNTOS) && obj.pPuntos.getValue()!=0) continue;
		  			}
		  		} else {
	  				obj.pPuntos.setValue(0);
		  			obj.pCumple.setValue(getCumpleSinPuntos(detalle,obj.pMS.getValue()));
		  			obj.pConPuntos.setValue(true);
	  			
		  		}


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
	  			obj.pTicketPromedio.setValue(getTicketsVoladoPromedio(detalle,obj.pRuta.getValue()));
	  				
	  			volado+=obj.pIngreso.getValue();
	  			voladoAHoy+=obj.pIngresoAHoy.getValue();
	  	    obj.pFaltante.setValue(0);
	  	    acumCant+=obj.pCantidad.getValue();
	  	    acumPuntos+=obj.pPuntos.getValue();
			    obj.pNextObj.setValue(calculePorcALlegar(detalleRuta,obj.pCantidad.getValue(),obj.pTotal.getValue(),detalle.getLimiteMinTkt()));
	  			if (!obj.pCumple.getValue()) {
	  			    obj.pFaltante.setValue(calculeFaltante(detalleRuta,obj.pCantidad.getValue(),obj.pTotal.getValue(),detalle.getLimiteMinTkt()));
	  			}
	  			if (obj.pPuntos.getValue()!=0) {
	  				tktAm+=obj.pFaltante.getValue();
	  				pfAm+=maxPuntos-obj.pPuntos.getValue();
	  			}	if (obj.pPuntos.getValue()==0) {
	  				tktRj+=obj.pFaltante.getValue();
	  				pfRj+=maxPuntos-obj.pPuntos.getValue();
	  			}
	  			obj.pPremio.setValue(getPremio(detalleRuta,obj.pIngreso.getValue(),obj.pMS.getValue()));
	  			obj.pMensaje.setValue(getAnalisis(detalle,detalleRuta,obj,obj.pCantidad.getValue(),maxPuntos,detalle.getLimiteMinTkt(),obj.pTotal.getValue()));
	  			obj.pGrafico.setValue(getHtmlData(detalle,obj));

	  	    
	  			addItem(obj);
	  			acum+=obj.getPremio();
	  			acumAHoy+=getPremio(detalle,obj.pIngresoAHoy.getValue(),obj.pMS.getValue());

	    	}
	    	if (!find) {
	  			BizObjetivosAvianca obj = new BizObjetivosAvianca();
	  			obj.pCompany.setValue(detalle.getCompany());
	  			obj.pId.setValue(detalle.getId());
	  			obj.pLinea.setValue(i++);
	  			obj.pRuta.setValue(ruta);
	  			obj.pCantidad.setValue(0);
	  	    obj.pTotal.setValue(0);
	  			obj.pFMS.setValue(0);
	  			obj.pMS.setValue(0);
	  			obj.pPuntos.setValue(0);
	  			obj.pCumple.setValue(false);
	  			obj.pIdContrato.setValue(idcontrato);
	  			obj.pIdDetalle.setValue(iddetalle);
	  			obj.pIdDetalleRuta.setValue(detalleRuta.getLinea());
	  			obj.pIngresoAHoy.setValue(0);
	  			obj.pIngreso.setValue(0);
	  			obj.pTicketPromedio.setValue(0);
	  	    obj.pFaltante.setValue(0);
	  			obj.pZonas.setValue("[]");
	  			obj.pLabelZonas.setValue("[]");
	  			obj.pGrafico.setValue(getHtmlData(detalle,obj));
	  			obj.pMensaje.setValue("No hay ticket relacionados");
	  			addItem(obj);
	  			
	    	}
	  	}
		}

			
  	
  	setTotalPremios(acum);
   	setTotalPremiosAHoy(acumAHoy);
    setTotalPremiosExtra(premioExtra);
    setVolado(getTicketsVoladoFinal(detalle));
    setTotalTicket(acumCant);
    setTotalPuntos(acumPuntos);
    setVoladoAHoy(getTicketsVoladoAHoyFinal(detalle));
    
    if (detalle.isPuntos()) {
      if (puntosMin==0||datos<2) 
        conclusion="Hay muy poca información para determinar objetivos";
      else {
      	if (acumPuntos>puntosMin){
      		conclusion="Hay suficientes puntos";
      	}else
      	if (acumPuntos+pfAm>=puntosMin)
      		conclusion="Hay "+pfAm+" puntos vendiendo "+tktAm+" tickets solo de la aerolinea en las rutas amarillas";
      	else
      		conclusion="Hay "+(pfAm+pfRj)+" puntos vendiendo "+(tktAm+tktRj)+" tickets solo de la aerolinea";
      }  	
    } else {
      conclusion="";
      
    }

    setConclusion(conclusion);
  	
		this.Ordenar("cumple;-cantidad");

    return true;
		
	}

  public String getHtmlData(BizDetalle detalle, BizObjetivosAvianca obj) throws Exception {
	  String defin= "{\"title\":'"+obj.pRuta.getValue()+"',\"subtitle\":'%',\"ranges\":"+obj.pZonas.getValue()+",\"rangeLabels\":"+
	  		obj.pLabelZonas.getValue()+",\"measures\":["+
	  		obj.pMS.getValue()+"],\"measureLabels\":['MS'],\"markers\":["+obj.pFMS.getValue()+"],\"markerLabels\":['"+detalle.getLimiteMinTkt()+" tkt']}";

	  String s="";
  	s+="<div style=\"display:block;width:400px\">";
		s+="<div id=\"span_bullet_"+obj.hashCode()+"\"><svg style='height:40px'> </svg></div>";
		s+="<script>addBullet('span_bullet_"+obj.hashCode()+"',"+defin+",400,40);</script>";
  	s+="</div>";
  	return s;
  }
  public double calculePorcALlegar(BizDetalle detalle,long cantidad,long total,long minimo) throws Exception {
  	BizNivel n=detalle.getObjNivelSiguiente();
  	if (n==null)return 0;
  	double porcALLegar=n.getValor();
  	if (porcALLegar==0) return 0;
  	return porcALLegar;
  }
  public double calculeFaltante(BizDetalle detalle,long cantidad,long total,long minimo) throws Exception {
  	double faltanteMinimo=minimo-total;
  	BizNivel n=detalle.getObjNivelMayor();
  	if (n==null)return 0;
  	double porcALLegar=n.getValor();
  	if (porcALLegar==0) return 0;
  	if (((100/porcALLegar)-1)==0) return 0;
  	double faltanteMS = Math.ceil((total-((100*cantidad)/porcALLegar))/(((100/porcALLegar)-1)));
  	double faltante =  faltanteMS<faltanteMinimo?faltanteMinimo:faltanteMS;
  	return faltante<0?0:faltante;
  }
  public double getMaxPuntos(BizDetalle detalle) throws Exception {
  	BizNivel n=detalle.getObjNivelMayor();
  	if (n==null)return 0;
  	return n.getParam4();
  }
  public double getCantidadPuntos(BizDetalle detalle,double ms) throws Exception {
  	BizNivel n=detalle.getObjNivelAlcanzado(ms);
  	if (n==null)return 0;
  	double puntos= n.getParam4();
  	return puntos;
  	
  }
  public boolean getCumpleSinPuntos(BizDetalle detalle,double ms) throws Exception {
  	BizNivel n=detalle.getObjNivelAlcanzado(ms);
  	if (n==null)return false;
  	return true;
  	
  }
  public boolean getCumple(BizDetalle detalle,double cantidad,double puntos,double max,double minimo) throws Exception {
  	if (cantidad<minimo) return false;
  	return puntos>=max ;
  }
  public double getMS(double cantidad,double total) throws Exception {
  	return total==0?0:(cantidad * 100.0)/total;
  }
  public double getFMS(double cantidad,double total,long cantidadMin) throws Exception {
  	return total==0?0:(cantidadMin * 100.0)/total;
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
  public String getAnalisis(BizDetalle detalle,BizDetalle detalleRuta,BizObjetivosAvianca obj,double cantidad,double maxPuntos,double minPuntos,double total) throws Exception {
  	if (detalle.isPuntos()) {
	  	if (total<minPuntos) return "cantidad boletos menor a min.req. "+minPuntos;
	  	if (!obj.pCumple.getValue()) return "Se obtuvieron "+obj.getPuntos()+" puntos, falta "+obj.getFaltante()+" tkts (si solo se vendiera de la aerolinea) para llegar a "+maxPuntos+" puntos";
	  	return "Cumple objetivo";
	  }
   	if (!obj.pCumple.getValue()) return "No se cumple objetivo";
	  return "Cumple objetivo";
 	}
  
  // optimizar los tres a un sql
  public double getTicketsVolado(BizDetalle detalle,String ruta) throws Exception {
  	BizPNRTicket tkt = new BizPNRTicket();
  	tkt.addFilter("company", detalle.getCompany());
  	tkt.addFilter("codigoaerolinea",  detalle.getIdAerolinea());
  	tkt.addFilter("void", false);
  	tkt.addFilter("market", ruta,"like");
		if (detalle.hasIatas())
	  	tkt.addFilter("nro_iata", detalle.getRawIatas(),"in");
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
  public double getTicketsVoladoPromedio(BizDetalle detalle,String ruta) throws Exception {
  	BizPNRTicket tkt = new BizPNRTicket();
  	tkt.addFilter("company", detalle.getCompany());
  	tkt.addFilter("codigoaerolinea",  detalle.getIdAerolinea());
		if (detalle.hasIatas())
	  	tkt.addFilter("nro_iata", detalle.getRawIatas(),"in");
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
  	tkt.addFilter("market", ruta,"like");
  	tkt.addFilter("departure_date",  detalle.getFDesde(),">=");
  	tkt.addFilter("departure_date",  detalle.getFHasta(),"<=");
  	return tkt.SelectAvgDouble("tarifa");
  } 
  
  public double getTicketsVoladoAHoy(BizDetalle detalle,String ruta) throws Exception {
  	BizPNRTicket tkt = new BizPNRTicket();
  	tkt.addFilter("company", detalle.getCompany());
  	tkt.addFilter("codigoaerolinea",  detalle.getIdAerolinea());
		if (detalle.hasIatas())
	  	tkt.addFilter("nro_iata", detalle.getRawIatas(),"in");
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
  	tkt.addFilter("market", ruta,"like");
  	tkt.addFilter("departure_date",  detalle.getFDesde(),">=");
  	tkt.addFilter("departure_date",  new Date(),"<=");
  	return tkt.SelectSumDouble("tarifa");
  } 
  
  public double getTicketsVoladoAHoyFinal(BizDetalle detalle) throws Exception {
  	BizPNRTicket tkt = new BizPNRTicket();
  	tkt.addFilter("company", detalle.getCompany());
  	tkt.addFilter("codigoaerolinea",  detalle.getIdAerolinea());
		if (detalle.hasIatas())
	  	tkt.addFilter("nro_iata", detalle.getRawIatas(),"in");
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
  	tkt.addFilter("departure_date",  detalle.getFDesde(),">=");
  	tkt.addFilter("departure_date",  new Date(),"<=");
  	return tkt.SelectSumDouble("tarifa");
  } 
  public double getTicketsVoladoFinal(BizDetalle detalle) throws Exception {
  	BizPNRTicket tkt = new BizPNRTicket();
  	tkt.addFilter("company", detalle.getCompany());
  	tkt.addFilter("codigoaerolinea",  detalle.getIdAerolinea());
  	tkt.addFilter("void", false);
  	tkt.addFilter("reemitted", false);
		if (detalle.hasIatas())
	  	tkt.addFilter("nro_iata", detalle.getRawIatas(),"in");
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
  
}