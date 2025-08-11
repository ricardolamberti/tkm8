package pss.bsp.contrato.detalleCopaWS.objetivos;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.nivel.BizNivel;
import pss.bsp.interfaces.copa.cabecera.BizCopaCabecera;
import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.fields.JIntervalDate;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JOrderedMap;
import pss.tourism.pnr.BizPNRTicket;

public class BizObjetivosCopasWS extends JRecords<BizObjetivosCopaWS> {
;
	public Class<BizObjetivosCopaWS> getBasedClass() {
		return BizObjetivosCopaWS.class;
	}
	
	
	String conclusion;

	Double voladoAHoy;


	Double totalTicket;
	Double totalTicketsAerolinea;
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

	public Double getTotalTicktesAerolinea() {
		return totalTicketsAerolinea;
	}

	public void setTotalTicktesAerolinea(Double ta) {
		this.totalTicketsAerolinea = ta;
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
    String fechaFilter = getFilterValue("fecha");
    String fdesde = JDateTools.DateToString(detalle.getFDesde());
    String fhasta = JDateTools.DateToString(detalle.getFHasta());
    if (fechaFilter!=null && !fechaFilter.equals("")) {
    	JIntervalDate idf = new JIntervalDate();
    	idf.setValueFormUI(fechaFilter);
      fdesde = JDateTools.DateToString(idf.getStartDateValue());
      fhasta = JDateTools.DateToString(idf.getEndDateValue());
    }
    
		BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Consultando la base", 0, 100, false, null);

    


		JBaseRegistro regs=JBaseRegistro.recordsetFactory();
		if (!detalle.getPax()) {// booking
			String sql="select  TUR_PNR_BOOKING.company as company, ROW_NUMBER() OVER() as linea, '"+detalle.getIdAerolinea()+"'  as aerolinea,BSP_COPA_DETALLE.ORIGEN||BSP_COPA_DETALLE.destino  as ruta,COALESCE(sum(monto_orig),0) as total, COALESCE(sum(roundtrip),0) as total_tkt, ";
			sql+= " ( select  COALESCE(sum(monto_orig),0)   from TUR_PNR_BOOKING b WHERE (b.company=  TUR_PNR_BOOKING.company) AND  ";
			sql+= " b.carrier = '"+detalle.getIdAerolinea()+"'   ";
			sql+= " AND B.origen = BSP_COPA_DETALLE.origen AND B.DESTINO = BSP_COPA_DETALLE.destino   ";
			sql+= " AND  b.fecha_emision between '"+fdesde+"' and '"+fhasta+"' ) as cantidad, ";
			sql+= " ( select  COALESCE(sum(roundtrip),0)   from TUR_PNR_BOOKING b WHERE (b.company=  TUR_PNR_BOOKING.company) AND  ";
			sql+= " b.carrier = '"+detalle.getIdAerolinea()+"'   ";
			sql+= " AND B.origen = BSP_COPA_DETALLE.origen AND B.DESTINO = BSP_COPA_DETALLE.destino   ";
			sql+= " AND  b.fecha_emision between '"+fdesde+"' and '"+fhasta+"' ) as cantidad_tkt ";
			sql+= " from  TUR_PNR_BOOKING  JOIN BSP_COPA_DETALLE   ON BSP_COPA_DETALLE.company='"+cabecera.getCompany()+"' AND idpdf='"+cabecera.getIdpdf()+"' ";
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
			sql+= " TUR_PNR_BOOKING.fecha_emision between '"+fdesde+"' and '"+fhasta+"'  ";
			sql+= " group by TUR_PNR_BOOKING.company,BSP_COPA_DETALLE.ORIGEN,BSP_COPA_DETALLE.DESTINO";
			regs.ExecuteQuery(sql);
		} else {
			String sql="select  TUR_PNR_BOLETO.company as company, ROW_NUMBER() OVER() as linea, '"+detalle.getIdAerolinea()+"'  as aerolinea,BSP_COPA_DETALLE.ORIGEN||BSP_COPA_DETALLE.destino  as ruta,COALESCE(sum(neto_factura_usa),0) as total, COALESCE(count(*),0) as total_tkt, ";
			sql+= " ( select  COALESCE(sum(neto_factura_usa),0)   from TUR_PNR_BOLETO b WHERE (b.company=  TUR_PNR_BOLETO.company) AND  ";
			sql+= " b.CodigoAerolinea = '"+detalle.getIdAerolinea()+"'   ";
			sql+= " AND B.aeropuerto_origen = BSP_COPA_DETALLE.origen AND B.aeropuerto_destino = BSP_COPA_DETALLE.destino   ";
			sql+= " AND b.void = 'N' AND  b.is_ticket = 'S' ";
			sql+= " AND  b.creation_date between '"+fdesde+"' and '"+fhasta+"' ) as cantidad, ";
			sql+= " ( select  COALESCE(count(*),0)   from TUR_PNR_BOLETO b WHERE (b.company=  TUR_PNR_BOLETO.company) AND  ";
			sql+= " b.CodigoAerolinea = '"+detalle.getIdAerolinea()+"'   ";
			sql+= " AND B.aeropuerto_origen = BSP_COPA_DETALLE.origen AND B.aeropuerto_destino = BSP_COPA_DETALLE.destino   ";
			sql+= " AND b.void = 'N' AND  b.is_ticket = 'S' ";
			sql+= " AND  b.creation_date between '"+fdesde+"' and '"+fhasta+"' ) as cantidad_tkt ";
			sql+= " from  TUR_PNR_BOLETO  JOIN BSP_COPA_DETALLE   ON BSP_COPA_DETALLE.company='"+cabecera.getCompany()+"' AND idpdf='"+cabecera.getIdpdf()+"' ";
			//sql+= " AND  TUR_PNR_BOLETO.market like '%BOK-'||BSP_COPA_DETALLE.marketing_id||'%'  ";
			sql+= " AND ( TUR_PNR_BOLETO.aeropuerto_origen = BSP_COPA_DETALLE.origen AND TUR_PNR_BOLETO.aeropuerto_destino = BSP_COPA_DETALLE.destino)  ";
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
			sql+= " WHERE (TUR_PNR_BOLETO.company= '"+idcompany+"')  AND ";
			sql+= " TUR_PNR_BOLETO.void = 'N' AND  TUR_PNR_BOLETO.is_ticket = 'S' AND ";
			
			sql+= " TUR_PNR_BOLETO.creation_date between '"+fdesde+"' and '"+fhasta+"'  ";
			sql+= " group by TUR_PNR_BOLETO.company,BSP_COPA_DETALLE.ORIGEN,BSP_COPA_DETALLE.DESTINO";
			regs.ExecuteQuery(sql);
		}
		double acumCant=0;
		double acum=0;
		int kk=0;
  	while (regs.next()) {
			BizObjetivosCopaWS obj = new BizObjetivosCopaWS();
			obj.pCompany.setValue(regs.CampoAsStr("company"));
			obj.pId.setValue(detalle.getId());
			obj.pLinea.setValue(regs.CampoAsLong("linea"));
			obj.pRuta.setValue(regs.CampoAsStr("ruta"));
			obj.pCantidad.setValue(regs.CampoAsLong("cantidad"));
	    obj.pTotal.setValue(regs.CampoAsLong("total"));
	    obj.pCantidadTkt.setValue(regs.CampoAsLong("cantidad_tkt"));
	    obj.pTotalTkt.setValue(regs.CampoAsLong("total_tkt"));
			obj.pIdPDF.setValue(cabecera.getIdpdf());
			obj.pCompanyPDF.setValue(cabecera.getCompany());
			obj.pIdContrato.setValue(idcontrato);
			obj.pIdDetalle.setValue(iddetalle);
	  	BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Presentando ruta "+obj.pRuta.getValue(), +kk, 1000, false, null);
			
			obj.pMS.setValue(getMS(regs.CampoAsLong("cantidad"), (regs.CampoAsLong("total")) ));
	
//			obj.pIngresoAHoy.setValue(getTicketsVoladoAHoy(detalle,obj.pRuta.getValue()));
//			obj.pIngreso.setValue(getTicketsVolado(detalle,obj.pRuta.getValue()));
//			obj.pPorcentaje.setValue(calculoPorcentaje(obj.pMS.getValue(),obj.pFMS.getValue()));
//			obj.pTicketPromedio.setValue(getTicketsVoladoPromedio(detalle,obj.pRuta.getValue()));
	
//			volado+=obj.pIngreso.getValue();
//			voladoAHoy+=obj.pIngresoAHoy.getValue();

			obj.pMensaje.setValue("");
			acumCant+=obj.pCantidad.getValue();
			acum+=obj.pTotal.getValue();
			if (obj.pCantidad.getValue()!=0 || obj.pTotal.getValue()!=0)
				addItem(obj);
		}
  	
//  	setTotalPremios(acum);
//   	setTotalPremiosAHoy(acumAHoy);
//    setTotalPremiosExtra(premioExtra);
    setTotalTicktesAerolinea(acumCant);  
  	setTotalTicket(acum);
  	setPorcAlcanzado(getMS(acumCant, acum));
    setVolado(getTicketsVolado(detalle));
    setVoladoAHoy(getTicketsVoladoAHoy(detalle));
  	BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Presentando información", 100, 100, false, null);

  	    
   
	  conclusion="";
    setConclusion(conclusion);
		this.Ordenar("-total");

    return true;
		
	}
  public double getMS(double cantidad,double total) throws Exception {
  	return total==0?0:(cantidad * 100.0)/total;
  }

  
  // optimizar los tres a un sql
  public double getTicketsVolado(BizDetalle detalle) throws Exception {
  	BizPNRTicket tkt = new BizPNRTicket();
  	tkt.addFilter("company", detalle.getCompany());
  	tkt.addFilter("codigoaerolinea",  detalle.getIdAerolinea());
  	tkt.addFilter("void", false);
  	tkt.addFilter("reemitted", false);
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
  public double getTicketsVoladoPromedio(BizDetalle detalle) throws Exception {
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
  	tkt.addFilter("departure_date",  detalle.getFDesde(),">=");
  	tkt.addFilter("departure_date",  detalle.getFHasta(),"<=");
  	return tkt.SelectAvgDouble("tarifa");
  } 
  
  public double getTicketsVoladoAHoy(BizDetalle detalle) throws Exception {
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
  	tkt.addFilter("departure_date",  detalle.getFDesde(),">=");
  	tkt.addFilter("departure_date",  new Date(),"<=");
  	return tkt.SelectSumDouble("tarifa");
  } 
  
}