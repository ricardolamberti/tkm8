package pss.bsp.contrato.detalleCopaPorRutas.objetivos;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.nivel.BizNivel;
import pss.bsp.contrato.detalleDatamining.BizDetalleDatamining;
import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JIntervalDate;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JOrderedMap;
import pss.tourism.pnr.BizBooking;
import pss.tourism.pnr.BizPNRTicket;

public class BizObjetivosCopasPorRutas extends JRecords<BizObjetivosCopaPorRutas> {
;
	public Class<BizObjetivosCopaPorRutas> getBasedClass() {
		return BizObjetivosCopaPorRutas.class;
	}
	
	
	String conclusion;

	Double voladoAHoy;


	Double totalTicket;
	Double totalTicketsAerolinea;
	Double porcAlcanzado;
  boolean isObjetivoLocal;

	public boolean isObjetivoLocal() {
		return isObjetivoLocal;
	}

	public void setObjetivoLocal(boolean isObjetivoLocal) {
		this.isObjetivoLocal = isObjetivoLocal;
	}

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
		isObjetivoLocal=false;
		this.endStatic();
    this.setStatic(true);
    String idcompany = getFilterValue("company");
    long idcontrato = Long.parseLong(getFilterValue("contrato"));
    long iddetalle = Long.parseLong(getFilterValue("detalle"));
    BizDetalle detalle = new BizDetalle();
    detalle.dontThrowException(true);
    if (!detalle.read(iddetalle)) return false;
    
    BizCompany company =BizBSPCompany.getCompany(idcompany);
 
    
    Calendar periodo = Calendar.getInstance();
    periodo.setTime(detalle.getFDesde());
    
    String rutaFilter = getFilterValue("ruta");
    String cumpleFilter = getFilterValue("mensaje");
    String fechaFilter = getFilterValue("fecha");
    String fdesde = JDateTools.DateToString(detalle.getFDesde());
    String fhasta = JDateTools.DateToString(detalle.getFHasta());
    double sumCant,sumResto;
    sumCant=0;
    sumResto=0;
    if (fechaFilter!=null && !fechaFilter.equals("")) {
    	JIntervalDate idf = new JIntervalDate();
    	idf.setValueFormUI(fechaFilter);
      fdesde = JDateTools.DateToString(idf.getStartDateValue());
      fhasta = JDateTools.DateToString(idf.getEndDateValue());
    }
    int kk =0;
		BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Analizando contrato", 0, 100, false, null);
    double porcAlc = 0;
  	StringTokenizer toks = new StringTokenizer(detalle.getMercados()," .,;");
  	while (toks.hasMoreTokens()) {
  		String ruta = toks.nextToken();
  		BizDetalleDatamining detalleRuta = new BizDetalleDatamining();
  		detalleRuta.dontThrowException(true);
  		if (!detalleRuta.readByAutogenerado(detalle.getCompany(), detalle.getId(), ruta))
  			continue;
  		int i=1;
  		BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Evaluando ruta "+ruta, ++kk, toks.countTokens(), false, null);
  		double nextLevel=0;
 
			BizObjetivosCopaPorRutas obj = new BizObjetivosCopaPorRutas();
			obj.pCompany.setValue(idcompany);
			obj.pId.setValue(detalle.getId());
			obj.pIdDetalle.setValue(iddetalle);
			obj.pLinea.setValue(detalleRuta.getLinea());
			obj.pRuta.setValue(ruta);
			double cant = getTicketsCantidad(detalle, ruta);
			double resto = getTicketsTotal(detalle, ruta);
//			if (detalle.getPax())
//				obj.pCantidad.setValue(detalleRuta.getValorActual());
//			else
			obj.pCantidad.setValue(cant);
			obj.pTotal.setValue(/*cant+*/resto);
	    sumCant+=cant;
	    sumResto+=resto;

			double ms = obj.pCantidad.getValue()==0?0:((double)obj.pCantidad.getValue() * 100.0)/(double)obj.pTotal.getValue();
			obj.pMS.setValue(ms);
	 		JIterator<BizNivel> itN = detalleRuta.getObjNiveles().getStaticIterator();
  		while (itN.hasMoreElements()) {
  			BizNivel nivel = itN.nextElement();
  			if (obj.pMS.getValue()<nivel.getValor())
  				break;
  			nextLevel=nivel.getValor();
  			
  			i++;
  		}
  		obj.pFaltante.setValue(nextLevel);
			obj.pIngreso.setValue(JTools.getDoubleNumberEmbedded(detalleRuta.getNivelAlcanzado()));
			porcAlc+=obj.pIngreso.getValue();
	
			addItem(obj);

  	}
		BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Presentando contrato", 100, 100, false, null);
  	double nextLevel2=0;
  	int j=1;
  	
  	double sumPorc=getMS(sumCant,sumResto);
  	JIterator<BizNivel> itN = detalle.getObjNiveles().getStaticIterator();
		while (itN.hasMoreElements()) {
			BizNivel nivel = itN.nextElement();
			isObjetivoLocal=true;
			if (sumPorc<nivel.getValor())
				break;
			nextLevel2=nivel.getValor();
			j++;
		}
		
		if (isObjetivoLocal) {
			setPorcAlcanzado(sumPorc);

		} else {
			setPorcAlcanzado(porcAlc);
		
		}
    setConclusion("");

		this.Ordenar("cumple;-cantidad");

	  volado = getTicketsVolado(detalle);
    return true;
		
	}
  public double getMS(double cantidad,double total) throws Exception {
  	return total==0?0:(cantidad * 100.0)/total;
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
//		if (ruta.length()==6) rutaSQL="BOK-"+ruta;
//		else if(ruta.startsWith("BOK")) rutaSQL= ruta.substring(1);
		if (detalle.hasIatas()) {
			tkt.addFilter("nro_iata", detalle.getRawIatas(), "in");
//			iatas +=" AND TUR_PNR_BOLETO.nro_iata in ("+getIatas()+")";
		}
  	tkt.addFilter("market", ""+rutaSQL,"like");
//  	StringTokenizer toks = new StringTokenizer(detalle.getTourcodeExcludes(),",;");
//  	while (toks.hasMoreTokens()) {
//  		String s = toks.nextToken();
//    	tkt.addFilter("tour_code", s.trim(),"<>");
//  	}
//  	StringTokenizer tok2s = new StringTokenizer(detalle.getClasesExcludes(),",;");
//  	while (tok2s.hasMoreTokens()) {
//  		String s = tok2s.nextToken();
//    	tkt.addFilter("clase", s.trim(),"<>");
//  	}  	
  	tkt.addFilter("creation_date",  detalle.getFDesde(),">=");
  	tkt.addFilter("creation_date",  detalle.getFHasta(),"<=");
  	if (!detalle.getPax())
  		return tkt.SelectSumDouble("neto_factura_usa");
  	return  tkt.selectCount();
  } 
  public double getTicketsTotal(BizDetalle detalle,String ruta) throws Exception {
  	BizPNRTicket tkt = new BizPNRTicket();
		String rutaSQL = ruta;
//		if (ruta.length()==6) rutaSQL="BOK-"+ruta;
//		else if(ruta.startsWith("BOK")) rutaSQL= ruta.substring(1);
//		if (detalle.getAerolineas()!=null &&!detalle.getAerolineas().trim().equals(""))
//			tkt.addFilter("codigoaerolinea",  "("+detalle.getAerolineas()+")","not in");
//		else
//			tkt.addFilter("codigoaerolinea",  detalle.getIdAerolinea(), "<>");
//	
		tkt.addFilter("company", detalle.getCompany());
//  	tkt.addFilter("codigoaerolinea",  detalle.getIdAerolinea());
  	tkt.addFilter("void", false);
  	tkt.addFilter("market", rutaSQL,"like");
		if (detalle.hasIatas()) {
			tkt.addFilter("nro_iata", detalle.getRawIatas(), "in");
//			iatas +=" AND TUR_PNR_BOLETO.nro_iata in ("+getIatas()+")";
		}
//  	StringTokenizer toks = new StringTokenizer(detalle.getTourcodeExcludes(),",;");
//  	while (toks.hasMoreTokens()) {
//  		String s = toks.nextToken();
//    	tkt.addFilter("tour_code", s.trim(),"<>");
//  	}
//  	StringTokenizer tok2s = new StringTokenizer(detalle.getClasesExcludes(),",;");
//  	while (tok2s.hasMoreTokens()) {
//  		String s = tok2s.nextToken();
//    	tkt.addFilter("clase", s.trim(),"<>");
//  	}  	
  	tkt.addFilter("is_ticket", true);

  	tkt.addFilter("creation_date",  detalle.getFDesde(),">=");
  	tkt.addFilter("creation_date",  detalle.getFHasta(),"<=");
   	if (!detalle.getPax())
  		return tkt.SelectSumDouble("neto_factura_usa");
  	return tkt.selectCount();
  } 

  public double getTicketsCantidadBok(BizDetalle detalle,String ruta) throws Exception {
  	BizBooking tkt = new BizBooking();
  	tkt.addFilter("company", detalle.getCompany());
		if (detalle.getAerolineas()!=null &&!detalle.getAerolineas().trim().equals(""))
			tkt.addFilter("Carrier",  "("+detalle.getAerolineas()+")","in");
		else
			tkt.addFilter("Carrier",  detalle.getIdAerolinea());
  	String rutaSQL = ruta;
		if (ruta.length()==6) rutaSQL="BOK-"+ruta;
		else if(ruta.startsWith("BOK")) rutaSQL= ruta.substring(1);

  	tkt.addFilter("mercado", ""+rutaSQL,"like");
  	StringTokenizer toks = new StringTokenizer(detalle.getTourcodeExcludes(),",;");
//  	while (toks.hasMoreTokens()) {
//  		String s = toks.nextToken();
//    	tkt.addFilter("tour_code", s.trim(),"<>");
//  	}
//  	StringTokenizer tok2s = new StringTokenizer(detalle.getClasesExcludes(),",;");
//  	while (tok2s.hasMoreTokens()) {
//  		String s = tok2s.nextToken();
//    	tkt.addFilter("clase", s.trim(),"<>");
//  	}  	
  	tkt.addFilter("is_ticket", true);

  	tkt.addFilter("fecha_emision",  detalle.getFDesde(),">=");
  	tkt.addFilter("fecha_emision",  detalle.getFHasta(),"<=");
  	return tkt.SelectSumDouble("roundtrip");
  } 
  public double getTicketsTotalBok(BizDetalle detalle,String ruta) throws Exception {
  	BizBooking tkt = new BizBooking();
		String rutaSQL = ruta;
		if (ruta.length()==6) rutaSQL="BOK-"+ruta;
		else if(ruta.startsWith("BOK")) rutaSQL= ruta.substring(1);
		if (detalle.getAerolineas()!=null &&!detalle.getAerolineas().trim().equals(""))
			tkt.addFilter("Carrier",  "("+detalle.getAerolineas()+")","not in");
		else
			tkt.addFilter("Carrier",  detalle.getIdAerolinea(), "<>");
	
		tkt.addFilter("company", detalle.getCompany());
//  	tkt.addFilter("codigoaerolinea",  detalle.getIdAerolinea());
  	tkt.addFilter("mercado", rutaSQL,"like");
//  	StringTokenizer toks = new StringTokenizer(detalle.getTourcodeExcludes(),",;");
//  	while (toks.hasMoreTokens()) {
//  		String s = toks.nextToken();
//    	tkt.addFilter("tour_code", s.trim(),"<>");
//  	}
//  	StringTokenizer tok2s = new StringTokenizer(detalle.getClasesExcludes(),",;");
//  	while (tok2s.hasMoreTokens()) {
//  		String s = tok2s.nextToken();
//    	tkt.addFilter("clase", s.trim(),"<>");
//  	}  	
  	tkt.addFilter("fecha_emision",  detalle.getFDesde(),">=");
  	tkt.addFilter("fecha_emision",  detalle.getFHasta(),"<=");
  	return tkt.SelectSumDouble("roundtrip");
  }   
  // optimizar los tres a un sql
  public double getTicketsVolado(BizDetalle detalle) throws Exception {
  	BizPNRTicket tkt = new BizPNRTicket();
//		String rutaSQL = ruta;
//		if (ruta.length()==6) rutaSQL="BOK-"+ruta;
//		else if(ruta.startsWith("BOK")) rutaSQL= ruta.substring(1);
  	tkt.addFilter("company", detalle.getCompany());
		if (detalle.getAerolineas()!=null &&!detalle.getAerolineas().trim().equals(""))
			tkt.addFilter("codigoaerolinea",  "("+detalle.getAerolineas()+")","in");
		else
			tkt.addFilter("codigoaerolinea",  detalle.getIdAerolinea());
  	tkt.addFilter("void", false);
  	tkt.addFilter("reemitted", false);
  	
//  	tkt.addFilter("market", rutaSQL,"like");
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
  	return tkt.SelectSumDouble("neto_usa");
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