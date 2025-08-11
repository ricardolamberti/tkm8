package pss.bsp.contrato.detalleCopaPorRutas;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.nivel.BizNivel;
import pss.bsp.contrato.detalle.nivel.JTipoNivelNormal;
import pss.bsp.contrato.detalle.nivel.JTipoPremioPorcentajeDelTotal;
import pss.bsp.contrato.detalleCopaPorRutas.objetivos.BizObjetivosCopasPorRutas;
import pss.bsp.contrato.detalleCopaPorRutas.objetivos.GuiObjetivosCopasPorRutas;
import pss.bsp.contrato.detalleDatamining.BizDetalleDatamining;
import pss.bsp.contrato.logica.JContratoCopaPorRutas;
import pss.bsp.event.BizBSPSqlEvent;
import pss.common.event.sql.BizSqlEvent;
import pss.common.event.sql.datos.BizSqlEventDato;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.regions.multilanguage.JLanguage;
import pss.core.graph.Graph;
import pss.core.graph.implementations.GraphScriptMedioReloj;
import pss.core.graph.implementations.GraphScriptPie;
import pss.core.graph.model.ModelVector;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;
import pss.tourism.pnr.GuiBookings;
import pss.tourism.pnr.GuiPNRTickets;

public class BizDetalleCopaPorRutas  extends BizDetalle {

  
  public BizDetalleCopaPorRutas() throws Exception {
		super();
	}


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void autogenerar() throws Exception {
  	//ver borrado
		if (!getMercados().equals("")) {
	  	StringTokenizer toks = new StringTokenizer(getMercados()," .,;");
	  	while (toks.hasMoreTokens()) {
	  		String ruta = toks.nextToken().trim();

	  		BizDetalleDatamining dd = new BizDetalleDatamining();
	  		dd.dontThrowException(true);
	  		boolean updated = dd.readByAutogenerado(getCompany(),getId(),ruta);
	  		BizSqlEvent event= new BizBSPSqlEvent();
	  		
	  		if (updated) {
	  			event.dontThrowException(true);
		  		event.read(dd.getVariable());
	  		}
	  		String sqlOld= event.getConsulta();
	
	  		
	  		Calendar calDesde = Calendar.getInstance();
	  		calDesde.setTime(getFDesde());
	  		Calendar calHasta = Calendar.getInstance();
	  		calHasta.setTime(getFHasta());
	  		
	  		Calendar dif =Calendar.getInstance();
	  		dif.setTimeInMillis(calHasta.getTime().getTime() - calDesde.getTime().getTime());
	  		int dias = dif.get(Calendar.DAY_OF_YEAR);
	  		String strFechas = " AND date_part('year',TUR_PNR_BOLETO.creation_date) = date_part('year',now())"	;  		
	  		String strFechasB = " AND date_part('year',b.creation_date) = date_part('year',now())"	;  		
	  		String strFechasD = " AND date_part('year',TUR_PNR_BOLETO.departure_date) = date_part('year',now())"	;  		
	  		if (dias <32) {// mensual
	  			strFechas+= " AND date_part('month'::text,now())=date_part('month'::text,TUR_PNR_BOLETO.creation_date)";
	  			strFechasB+= " AND date_part('month'::text,now())=date_part('month'::text,b.creation_date)";
	  			strFechasD+= " AND date_part('month'::text,now())=date_part('month'::text,TUR_PNR_BOLETO.departure_date)";
	  		}
	  		else if (dias <63) {// bimestral
	  			strFechas+= " AND (floor(((extract (month from TUR_PNR_BOLETO.creation_date))-1 ) / 2) +1 = floor(((extract (month from now()))-1 ) / 2) +1)";
	  			strFechasB+= " AND (floor(((extract (month from b.creation_date))-1 ) / 2) +1 = floor(((extract (month from now()))-1 ) / 2) +1)";
	  			strFechasD+= " AND (floor(((extract (month from TUR_PNR_BOLETO.departure_date))-1 ) / 2) +1 = floor(((extract (month from now()))-1 ) / 2) +1)";
	  		}	 else if (dias <94) {// trimestral
	  			strFechas+= " AND  (floor(((extract (month from TUR_PNR_BOLETO.creation_date))-1 ) / 3) +1 = floor(((extract (month from now()))-1 ) / 3) +1) ";
	  			strFechasB+= " AND  (floor(((extract (month from b.creation_date))-1 ) / 3) +1 = floor(((extract (month from now()))-1 ) / 3) +1) ";
	  			strFechasD+= " AND  (floor(((extract (month from TUR_PNR_BOLETO.departure_date))-1 ) / 3) +1 = floor(((extract (month from now()))-1 ) / 3) +1) ";
	  		}	 else if (dias <125) {// cuatrimestral
	  			strFechas+= " AND  (extract(QUARTER  from  TUR_PNR_BOLETO.creation_date)  = extract(QUARTER  from now())) ";
	  			strFechasB+= " AND  (extract(QUARTER  from  b.creation_date)  = extract(QUARTER  from now())) ";
	  			strFechasD+= " AND  (extract(QUARTER  from  TUR_PNR_BOLETO.departure_date)  = extract(QUARTER  from now())) ";
	  		}	 else if (dias <187) {//semestral
	  			strFechas+= " AND  (floor(((extract (month from  TUR_PNR_BOLETO.creation_date))-1 ) / 6) +1 = floor(((extract (month from now()))-1 ) / 6) +1) ";
	  			strFechasB+= " AND  (floor(((extract (month from  b.creation_date))-1 ) / 6) +1 = floor(((extract (month from now()))-1 ) / 6) +1) ";
	  			strFechasD+= " AND  (floor(((extract (month from  TUR_PNR_BOLETO.departure_date))-1 ) / 6) +1 = floor(((extract (month from now()))-1 ) / 6) +1) ";
	  		}	   		
	  		
	  		String rutaSQL = ruta;
	  		String rutaB="";
	  		String rutaBoleto="";
	  		String iatas = "";
	  		String iatasB = "";
	  		if (ruta.indexOf("|")==-1) {
//		  		if (ruta.length()==6) rutaSQL="BOK-"+ruta;
//		  		else if(ruta.startsWith("BOK")) rutaSQL= ruta.substring(1);
		  		rutaSQL=ruta;
	  			rutaB=" b.market like '%"+rutaSQL+"%' ";
	  			rutaBoleto=" TUR_PNR_BOLETO.market like '%"+rutaSQL+"%' ";
	  		} else {
	  			StringTokenizer toksR = new StringTokenizer(rutaSQL,"|");
	  			rutaB = "(";
	  			rutaBoleto = "(";
	  	  	while (toksR.hasMoreTokens()) {
	  	  		String rutaA = toksR.nextToken().trim();
//	  	  		if (rutaA.length()==6) rutaSQL="BOK-"+rutaA;
//	  	  		else if(rutaA.startsWith("BOK")) rutaSQL= rutaA.substring(1);
	  	  		rutaSQL=rutaA;
	  	  		rutaB+= (rutaB.equals("(")?"":" OR ")+" (b.market like '%"+rutaSQL+"%') ";
		  			rutaBoleto+=(rutaBoleto.equals("(")?"":" OR ")+" (TUR_PNR_BOLETO.market like '%"+rutaSQL+"%') ";
  	  		
	  	  	}
	  			rutaB += ")";
	  			rutaBoleto += ")";

	  		}
	  		if (hasIatas()) {

	  			iatas +=" AND TUR_PNR_BOLETO.nro_iata in ("+getIatas()+")";
	  			iatasB +=" AND b.nro_iata in ("+getIatas()+")";
	  		}
 
	  		String operacion="";
	  		if (isPax()) {
		  			operacion= "COALESCE(count(*),0)";
	  		} else {
	  			operacion= "COALESCE(sum(neto_factura_usa),0)";
		  			
	  		}
		  	String sql="select     ";
		  	sql+= " ( case (";
	  		sql+= " ( select  "+operacion+"   from TUR_PNR_BOLETO b WHERE (b.company=  TUR_PNR_BOLETO.company)    AND  b.is_ticket = 'S' AND  b.void = 'N'   AND "+rutaB+"  ";
	  		sql+= strFechasB;
	  		sql+= iatasB;
 				sql+= " )) ";
	  		sql+= " when 0 then 0 else ((100.0* ";
	  		sql+= " "+operacion+" ";
	  		sql+= " / ( ";
	  		sql+= " ( select  "+operacion+"   from TUR_PNR_BOLETO b WHERE (b.company=  TUR_PNR_BOLETO.company)    AND  b.is_ticket = 'S' AND  b.void = 'N'   AND "+rutaB+" ";
	  		sql+= strFechasB;
	  		sql+= iatasB;
	  		sql+= "))))end) as PORC_COUNT_TUR_PNR_BOLETO_COUNT ";

	  		sql+= " from  TUR_PNR_BOLETO   ";
	  		sql+= " WHERE (TUR_PNR_BOLETO.company= '"+getCompany()+"')   ";
	  		sql+= " AND  TUR_PNR_BOLETO.void = 'N' AND  TUR_PNR_BOLETO.is_ticket = 'S'  ";
	  		sql+= strFechas;
	  		if (getAerolineas()!=null&&!getAerolineas().trim().equals(""))
	  			sql+= " AND TUR_PNR_BOLETO.codigoAEROLINEA in ( "+getAerolineas()+") AND  "+rutaBoleto;
	  		else
	  			sql+= " AND TUR_PNR_BOLETO.codigoAEROLINEA = '"+getIdAerolinea()+"' AND "+rutaBoleto;
	  		sql+= iatas;
	  		sql+= " GROUP BY TUR_PNR_BOLETO.company ";
	  		event.setConsulta(sql);
	  		
	  		
	  		String sqlg="select     ";
	  		sqlg+= " *";
	  		sqlg+= " from  TUR_PNR_BOLETO   ";
	  		sqlg+= " WHERE (TUR_PNR_BOLETO.company= '"+getCompany()+"')   ";
	  		sqlg+= " AND  TUR_PNR_BOLETO.void = 'N' AND  TUR_PNR_BOLETO.is_ticket = 'S' ";
	  		sqlg+= strFechas;
	  		if (getAerolineas()!=null&&!getAerolineas().trim().equals(""))
	  			sqlg+= " AND TUR_PNR_BOLETO.codigoAEROLINEA in ( "+getAerolineas()+") AND "+rutaBoleto;
	  		else
	  			sqlg+= " AND TUR_PNR_BOLETO.codigoAEROLINEA = '"+getIdAerolinea()+"' AND  "+rutaBoleto;
	  		sqlg += iatas;
	  		event.setConsultaDetalle(sqlg);
	  		event.setDescripcion("Market share ruta "+ruta);
	  		event.setCompany(getCompany());
	  		event.setActivo(true);
	  		event.setCampo("PORC_COUNT_TUR_PNR_BOLETO_COUNT");
	  		event.setInvisible(true);
	  		event.setClassDetalle(GuiPNRTickets.class.getName());
	  		if (!updated || !sqlOld.equals(event.getConsulta()))
	  			event.setEstado(BizSqlEvent.REPROCESAR);
	  		event.processUpdateOrInsertWithCheck();

	  		
	  		BizSqlEvent eventGanancia= new BizBSPSqlEvent();
	  		if (updated) {
	  			eventGanancia.dontThrowException(true);
	  			eventGanancia.read(dd.getVariableGanancia());
	  		}
	  		sqlOld= eventGanancia.getConsulta();
	  		
	  		String sql2="select     ";
	  		sql2+= " sum(TUR_PNR_BOLETO.tarifa) as PORC_SUM_TUR_PNR_BOLETO_TARIFA";
	  		sql2+= " from  TUR_PNR_BOLETO   ";
	  		sql2+= " WHERE (TUR_PNR_BOLETO.company= '"+getCompany()+"')   ";
	  		sql2+= " AND  TUR_PNR_BOLETO.void = 'N' AND  TUR_PNR_BOLETO.is_ticket = 'S' ";
	  		StringTokenizer toksTC = new StringTokenizer(getTourcodeExcludes(),",;");
	    	while (toksTC.hasMoreTokens()) {
	    		String s = toksTC.nextToken();
	    		sql2+= " AND (TUR_PNR_BOLETO.tour_code <> '"+s.trim()+"') ";
	    	}
	    	StringTokenizer tok2s = new StringTokenizer(getClasesExcludes(),",;");
	    	while (tok2s.hasMoreTokens()) {
	    		String s = tok2s.nextToken();
	    		sql2+= " AND (TUR_PNR_BOLETO.clase <> '"+s.trim()+"') ";
	    	}  	

	  		
	  		sql2+= strFechasD;
	  		if (getAerolineas()!=null&&!getAerolineas().trim().equals(""))
	  			sql2+= " AND TUR_PNR_BOLETO.codigoAEROLINEA in ( "+getAerolineas()+")  AND "+rutaBoleto;
	  		else
	  			sql2+= " AND TUR_PNR_BOLETO.codigoAEROLINEA = '"+getIdAerolinea()+"' AND "+rutaBoleto;
	  		sql2+=iatas;
	  		eventGanancia.setConsulta(sql2);
	  		
	  		
	  		
	  		String sql2g="select     ";
	  		sql2g+= " * ";
	  		sql2g+= " from  TUR_PNR_BOLETO   ";
	  		sql2g+= " WHERE TUR_PNR_BOLETO.company= '"+getCompany()+"'  ";
	  		sql2g+= " AND  TUR_PNR_BOLETO.void = 'N' AND  TUR_PNR_BOLETO.is_ticket = 'S' ";
	    	StringTokenizer toksTC2 = new StringTokenizer(getTourcodeExcludes(),",;");
	    	while (toksTC2.hasMoreTokens()) {
	    		String s = toksTC2.nextToken();
	    		sql2g+= " AND (TUR_PNR_BOLETO.tour_code <> '"+s.trim()+"') ";
	    	}
	    	StringTokenizer tok2s2 = new StringTokenizer(getClasesExcludes(),",;");
	    	while (tok2s2.hasMoreTokens()) {
	    		String s = tok2s2.nextToken();
	    		sql2g+= " AND (TUR_PNR_BOLETO.clase <> '"+s.trim()+"') ";
	    	}  
	    	sql2g+= strFechasD;
	  		if (getAerolineas()!=null&&!getAerolineas().trim().equals(""))
	  			sql2g+= " AND TUR_PNR_BOLETO.codigoAEROLINEA in ( "+getAerolineas()+") AND "+rutaBoleto;
	  		else
	  			sql2g+= " AND TUR_PNR_BOLETO.codigoAEROLINEA = '"+getIdAerolinea()+"' AND "+rutaBoleto;
	  		sql2g += iatas;
	  		eventGanancia.setConsultaDetalle(sql2g);
	  		eventGanancia.setCampo("PORC_SUM_TUR_PNR_BOLETO_TARIFA");
	  		eventGanancia.setDescripcion("Acumulado ruta "+ruta);
	  		eventGanancia.setCompany(getCompany());
	  		eventGanancia.setActivo(true);
	  		eventGanancia.setInvisible(true);
	  		eventGanancia.setClassDetalle(GuiPNRTickets.class.getName());
	  		if (!updated || !sqlOld.equals(eventGanancia.getConsulta()))
	  			eventGanancia.setEstado(BizSqlEvent.REPROCESAR);

	  		eventGanancia.processUpdateOrInsertWithCheck();

	  		dd.setCompany(getCompany());
	  		dd.setId(getId());
	  		dd.setDescripcion("Ruta "+ruta);
	  		dd.setKicker(true);
	  		dd.setDefaultTipoObjetivo(JTipoNivelNormal.class.getName());
	  		dd.setDefaultTipoPremio(JTipoPremioPorcentajeDelTotal.class.getName());
	  		dd.setVariable(event.getId());
	  		dd.setAutogenerado(ruta);
	  		dd.setVariableGanancia(eventGanancia.getId());
	  		if (updated)
	  			dd.processUpdate();
	  		else
	  			dd.processInsert();
	  	}
		}

  }
  
  public void autogenerarBooking() throws Exception {
  	//ver borrado
		if (!getMercados().equals("")) {
	  	StringTokenizer toks = new StringTokenizer(getMercados()," .,;");
	  	while (toks.hasMoreTokens()) {
	  		String ruta = toks.nextToken().trim();

	  		BizDetalleDatamining dd = new BizDetalleDatamining();
	  		dd.dontThrowException(true);
	  		boolean updated = dd.readByAutogenerado(getCompany(),getId(),ruta);
	  		BizSqlEvent event= new BizBSPSqlEvent();
	  		if (updated) {
	  			event.dontThrowException(true);
		  		event.read(dd.getVariable());
	  		}
	  		String sqlOld= event.getConsulta();
	  		
	  		Calendar calDesde = Calendar.getInstance();
	  		calDesde.setTime(getFDesde());
	  		Calendar calHasta = Calendar.getInstance();
	  		calHasta.setTime(getFHasta());
	  		
	  		Calendar dif =Calendar.getInstance();
	  		dif.setTimeInMillis(calHasta.getTime().getTime() - calDesde.getTime().getTime());
	  		int dias = dif.get(Calendar.DAY_OF_YEAR);
	  		String strFechas = " AND date_part('year',TUR_PNR_BOOKING.fecha_emision) = date_part('year',now())"	;  		
	  		String strFechasB = " AND date_part('year',b.fecha_emision) = date_part('year',now())"	;  		
	  		String strFechasD = " AND date_part('year',TUR_PNR_BOLETO.departure_date) = date_part('year',now())"	;  		
	  		if (dias <32) {// mensual
	  			strFechas+= " AND date_part('month'::text,now())=date_part('month'::text,TUR_PNR_BOOKING.fecha_emision)";
	  			strFechasB+= " AND date_part('month'::text,now())=date_part('month'::text,b.fecha_emision)";
	  			strFechasD+= " AND date_part('month'::text,now())=date_part('month'::text,TUR_PNR_BOLETO.departure_date)";
	  		}
	  		else if (dias <63) {// bimestral
	  			strFechas+= " AND (floor(((extract (month from TUR_PNR_BOOKING.fecha_emision))-1 ) / 2) +1 = floor(((extract (month from now()))-1 ) / 2) +1)";
	  			strFechasB+= " AND (floor(((extract (month from b.fecha_emision))-1 ) / 2) +1 = floor(((extract (month from now()))-1 ) / 2) +1)";
	  			strFechasD+= " AND (floor(((extract (month from TUR_PNR_BOLETO.departure_date))-1 ) / 2) +1 = floor(((extract (month from now()))-1 ) / 2) +1)";
	  		}	 else if (dias <94) {// trimestral
	  			strFechas+= " AND  (floor(((extract (month from TUR_PNR_BOOKING.fecha_emision))-1 ) / 3) +1 = floor(((extract (month from now()))-1 ) / 3) +1) ";
	  			strFechasB+= " AND  (floor(((extract (month from b.fecha_emision))-1 ) / 3) +1 = floor(((extract (month from now()))-1 ) / 3) +1) ";
	  			strFechasD+= " AND  (floor(((extract (month from TUR_PNR_BOLETO.departure_date))-1 ) / 3) +1 = floor(((extract (month from now()))-1 ) / 3) +1) ";
	  		}	 else if (dias <125) {// cuatrimestral
	  			strFechas+= " AND  (extract(QUARTER  from  TUR_PNR_BOOKING.fecha_emision)  = extract(QUARTER  from now())) ";
	  			strFechasB+= " AND  (extract(QUARTER  from  b.fecha_emision)  = extract(QUARTER  from now())) ";
	  			strFechasD+= " AND  (extract(QUARTER  from  TUR_PNR_BOLETO.departure_date)  = extract(QUARTER  from now())) ";
	  		}	 else if (dias <187) {//semestral
	  			strFechas+= " AND  (floor(((extract (month from  TUR_PNR_BOOKING.fecha_emision))-1 ) / 6) +1 = floor(((extract (month from now()))-1 ) / 6) +1) ";
	  			strFechasB+= " AND  (floor(((extract (month from  b.fecha_emision))-1 ) / 6) +1 = floor(((extract (month from now()))-1 ) / 6) +1) ";
	  			strFechasD+= " AND  (floor(((extract (month from  TUR_PNR_BOLETO.departure_date))-1 ) / 6) +1 = floor(((extract (month from now()))-1 ) / 6) +1) ";
	  		}	   		
	  		
	  		String rutaSQL = ruta;
//	  		if (ruta.length()==6) rutaSQL="BOK-"+ruta;
//	  		else if(ruta.startsWith("BOK")) rutaSQL= ruta.substring(1);

	  		
	  		String sql="select  coalesce(count(*),0)  as PORC_COUNT_TUR_PNR_BOLETO_COUNT ";	

	  		sql+= " from  TUR_PNR_BOOKING   ";
	  		sql+= " WHERE (TUR_PNR_BOOKING.company= '"+getCompany()+"')   ";
	  			sql+= strFechas;
	  		if (getAerolineas()!=null&&!getAerolineas().trim().equals(""))
	  			sql+= " AND TUR_PNR_BOOKING.carrier in ( "+getAerolineas()+") ";
	  		else
	  			sql+= " AND TUR_PNR_BOOKING.carrier = '"+getIdAerolinea()+"' ";
				if (rutaSQL.indexOf("|")!=-1) {
					StringTokenizer toksOR = new StringTokenizer(rutaSQL, "|");
					String s ="";
					while (toksOR.hasMoreTokens()) {
						String rutaOr = toksOR.nextToken();
//			  		if (rutaOr.length()==6) rutaOr="BOK-"+rutaOr;
//			  		else if(rutaOr.startsWith("BOK")) rutaOr= rutaOr.substring(1);
						
						s+= (s.equals("")?"":" OR ")+" TUR_PNR_BOOKING.mercado like '%"+rutaOr+"%' ";
					}
					if (!s.equals(""))
						sql += " AND ("+s+") ";
	
	  		} else
	  			sql+=" AND TUR_PNR_BOOKING.mercado like '%"+rutaSQL+"%' ";
//	  		StringTokenizer toksTC = new StringTokenizer(getTourcodeExcludes(),",;");
//	    	while (toksTC.hasMoreTokens()) {
//	    		String s = toksTC.nextToken();
//	    		sql+= " AND (TUR_PNR_BOOKING.tour_code <> '"+s.trim()+"') ";
//	    	}
//	    	StringTokenizer tok2s = new StringTokenizer(getClasesExcludes(),",;");
//	    	while (tok2s.hasMoreTokens()) {
//	    		String s = tok2s.nextToken();
//	    		sql+= " AND (TUR_PNR_BOOKING.clase <> '"+s.trim()+"') ";
//	    	}  
	  		sql+= " GROUP BY TUR_PNR_BOOKING.company ";
	  		event.setConsulta(sql);
	  		
	  		String sqlg="select     ";
	  		sqlg+= " *";
	  		sqlg+= " from  TUR_PNR_BOOKING   ";
	  		sqlg+= " WHERE (TUR_PNR_BOOKING.company= '"+getCompany()+"')   ";
	  		sqlg+= strFechas;

	  		if (getAerolineas()!=null&&!getAerolineas().trim().equals(""))
	  			sqlg+= " AND TUR_PNR_BOOKING.carrier in ( "+getAerolineas()+") ";
	  		else
	  			sqlg+= " AND TUR_PNR_BOOKING.carrier = '"+getIdAerolinea()+"' ";
				if (rutaSQL.indexOf("|")!=-1) {
					StringTokenizer toksOR = new StringTokenizer(rutaSQL, "|");
					String s ="";
					while (toksOR.hasMoreTokens()) {
						String rutaOr = toksOR.nextToken();
//			  		if (rutaOr.length()==6) rutaOr="BOK-"+rutaOr;
//			  		else if(rutaOr.startsWith("BOK")) rutaOr= rutaOr.substring(1);
		
						s+= (s.equals("")?"":" OR ")+" TUR_PNR_BOOKING.mercado like '%"+rutaOr+"%' ";
					}
					if (!s.equals(""))
						sqlg += " AND ("+s+") ";
	
	  		} else
	  			sqlg+=" AND TUR_PNR_BOOKING.mercado like '%"+rutaSQL+"%' ";	  		
	  		
	  		//  		toksTC = new StringTokenizer(getTourcodeExcludes(),",;");
//    	while (toksTC.hasMoreTokens()) {
//    		String s = toksTC.nextToken();
//    		sql+= " AND (TUR_PNR_BOOKING.tour_code <> '"+s.trim()+"') ";
//    	}
//    	tok2s = new StringTokenizer(getClasesExcludes(),",;");
//    	while (tok2s.hasMoreTokens()) {
//    		String s = tok2s.nextToken();
//    		sql+= " AND (TUR_PNR_BOOKING.clase <> '"+s.trim()+"') ";
//    	} 
	  		event.setConsultaDetalle(sqlg);
	  		
	  		event.setDescripcion("Pax por ruta "+ruta);
	  		event.setCompany(getCompany());
	  		event.setActivo(true);
	  		event.setCampo("PORC_COUNT_TUR_PNR_BOLETO_COUNT");
	  		event.setInvisible(true);
	  		event.setClassDetalle(GuiBookings.class.getName());
	  		if (!updated || !sqlOld.equals(event.getConsulta()))
	  			event.setEstado(BizSqlEvent.REPROCESAR);
	  		event.processUpdateOrInsertWithCheck();

	  		BizSqlEvent eventGanancia= new BizBSPSqlEvent();
	  		if (updated) {
	  			eventGanancia.dontThrowException(true);
	  			eventGanancia.read(dd.getVariableGanancia());
	  		}
	  		sqlOld= eventGanancia.getConsulta();
	  		
	  		
	  		String sql2="select     ";
	  		sql2+= " sum(TUR_PNR_BOLETO.tarifa) as PORC_SUM_TUR_PNR_BOLETO_TARIFA";
	  		sql2+= " from  TUR_PNR_BOLETO   ";
	  		sql2+= " WHERE (TUR_PNR_BOLETO.company= '"+getCompany()+"')   ";
	  		sql2+= " AND  TUR_PNR_BOLETO.void = 'N' AND  TUR_PNR_BOLETO.is_ticket = 'S' ";
	  		StringTokenizer toksTC = new StringTokenizer(getTourcodeExcludes(),",;");
	    	while (toksTC.hasMoreTokens()) {
	    		String s = toksTC.nextToken();
	    		sql2+= " AND (TUR_PNR_BOLETO.tour_code <> '"+s.trim()+"') ";
	    	}
	    	StringTokenizer tok2s = new StringTokenizer(getClasesExcludes(),",;");
	    	while (tok2s.hasMoreTokens()) {
	    		String s = tok2s.nextToken();
	    		sql2+= " AND (TUR_PNR_BOLETO.clase <> '"+s.trim()+"') ";
	    	}  	

	  		
	  		sql2+= strFechasD;
	   		if (getAerolineas()!=null&&!getAerolineas().trim().equals(""))
	  			sql2+= " AND TUR_PNR_BOLETO.codigoAEROLINEA in ( "+getAerolineas()+") ";
	  		else
	  			sql2+= " AND TUR_PNR_BOLETO.codigoAEROLINEA = '"+getIdAerolinea()+"' ";
				if (rutaSQL.indexOf("|")!=-1) {
					StringTokenizer toksOR = new StringTokenizer(rutaSQL, "|");
					String s ="";
					while (toksOR.hasMoreTokens()) {
						String rutaOr = toksOR.nextToken();
//			  		if (rutaOr.length()==6) rutaSQL="BOK-"+rutaOr;
//			  		else if(rutaOr.startsWith("BOK")) rutaOr= rutaOr.substring(1);
		
						s+= (s.equals("")?"":" OR ")+" TUR_PNR_BOLETO.market like '%"+rutaOr+"%' ";
					}
					if (!s.equals(""))
						sql2 += " AND ("+s+") ";
	
	  		} else
	  			sql2+=" AND TUR_PNR_BOLETO.market like '%"+rutaSQL+"%' ";

	  		eventGanancia.setConsulta(sql2);
	  		
	  		
	  		String sql2g="select     ";
	  		sql2g+= " * ";
	  		sql2g+= " from  TUR_PNR_BOLETO   ";
	  		sql2g+= " WHERE TUR_PNR_BOLETO.company= '"+getCompany()+"'  ";
	  		sql2g+= " AND  TUR_PNR_BOLETO.void = 'N' AND  TUR_PNR_BOLETO.is_ticket = 'S' ";
	    	StringTokenizer toksTC2 = new StringTokenizer(getTourcodeExcludes(),",;");
	    	while (toksTC2.hasMoreTokens()) {
	    		String s = toksTC2.nextToken();
	    		sql2g+= " AND (TUR_PNR_BOLETO.tour_code <> '"+s.trim()+"') ";
	    	}
	    	StringTokenizer tok2s2 = new StringTokenizer(getClasesExcludes(),",;");
	    	while (tok2s2.hasMoreTokens()) {
	    		String s = tok2s2.nextToken();
	    		sql2g+= " AND (TUR_PNR_BOLETO.clase <> '"+s.trim()+"') ";
	    	}  
	    	sql2g+= strFechasD;
	  		if (getAerolineas()!=null&&!getAerolineas().trim().equals(""))
	  			sql2g+= " AND TUR_PNR_BOLETO.codigoAEROLINEA in ( "+getAerolineas()+") ";
	  		else
	  			sql2g+= " AND TUR_PNR_BOLETO.codigoAEROLINEA = '"+getIdAerolinea()+"' ";
				if (rutaSQL.indexOf("|")!=-1) {
					StringTokenizer toksOR = new StringTokenizer(rutaSQL, "|");
					String s ="";
					while (toksOR.hasMoreTokens()) {
						String rutaOr = toksOR.nextToken();
//			  		if (rutaOr.length()==6) rutaOr="BOK-"+rutaOr;
//			  		else if(rutaOr.startsWith("BOK")) rutaOr= rutaOr.substring(1);
		
						s+= (s.equals("")?"":" OR ")+" TUR_PNR_BOLETO.market like '%"+rutaOr+"%' ";
					}
					if (!s.equals(""))
						sql2g += " AND ("+s+") ";
	
	  		} else
	  			sql2g+=" AND TUR_PNR_BOLETO.market like '%"+rutaSQL+"%' ";
	  		eventGanancia.setConsultaDetalle(sql2g);
	  		eventGanancia.setCampo("PORC_SUM_TUR_PNR_BOLETO_TARIFA");
	  		eventGanancia.setDescripcion("Acumulado ruta "+ruta);
	  		eventGanancia.setCompany(getCompany());
	  		eventGanancia.setActivo(true);
	  		eventGanancia.setInvisible(true);
	  		eventGanancia.setClassDetalle(GuiPNRTickets.class.getName());
	  		if (!updated || !sqlOld.equals(eventGanancia.getConsulta()))
	  			eventGanancia.setEstado(BizSqlEvent.REPROCESAR);
	
	  		eventGanancia.processUpdateOrInsertWithCheck();

	  		dd.setCompany(getCompany());
	  		dd.setId(getId());
	  		dd.setDescripcion("Ruta "+ruta);
	  		dd.setKicker(true);
	  		dd.setDefaultTipoObjetivo(JTipoNivelNormal.class.getName());
	  		dd.setDefaultTipoPremio(JTipoPremioPorcentajeDelTotal.class.getName());
	  		dd.setVariable(event.getId());
	  		dd.setAutogenerado(ruta);
	  		dd.setVariableGanancia(eventGanancia.getId());
	  		if (updated)
	  			dd.processUpdate();
	  		else
	  			dd.processInsert();
	  	}
		}

  }
  
  @Override
  public void processInsert() throws Exception {
  	if (pDescripcion.isNull()) pDescripcion.setValue("Copa por ruta "+getIdAerolinea());
  	setLogicaContrato(JContratoCopaPorRutas.class.getName());

  	super.processInsert();
  	if (pPax.getValue())
  		autogenerarBooking();
  	else
  		autogenerar();
  }
  
  @Override
  public void processUpdate() throws Exception {
  	if (pDescripcion.isNull()) pDescripcion.setValue("Copa por ruta "+getIdAerolinea());
  	setLogicaContrato(JContratoCopaPorRutas.class.getName());
  	super.processUpdate();
  	if (pPax.getValue())
  		autogenerarBooking();
  	else
  		autogenerar();
  }
  
	public String getImagen1(boolean printerVersion) throws Exception {
		GuiObjetivosCopasPorRutas w = new GuiObjetivosCopasPorRutas();
		BizObjetivosCopasPorRutas r = getObjetivos();
		w.setRecords(r);

		JWinList wl = new JWinList(w);
		w.AddColumnasDefault(wl);
		GraphScriptPie gr = new GraphScriptPie();
		gr.addAtributtes(Graph.GRAPH_ATTR_ROTATENAMES, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_DECIMALPRECISON, "2");
		gr.addAtributtes(Graph.GRAPH_ATTR_NUMBERPREFIX, "");
		gr.addAtributtes(Graph.GRAPH_ATTR_EXPORTENABLED, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_XAXISNAME, "Cumplimiento");
		gr.addAtributtes(Graph.GRAPH_ATTR_YAXISNAME, "Objetivos");
		gr.setTitle("Rutas por rendimiento");
		ModelVector mg=new ModelVector();
		
		mg.addColumn("ruta", ModelVector.GRAPH_FUNCTION_DATASET);
		if (getPax())
			mg.addColumn("cantidad", ModelVector.GRAPH_FUNCTION_VALUE);
		else
			mg.addColumn("ms", ModelVector.GRAPH_FUNCTION_VALUE);
		gr.setModel(mg);
  	wl.addGrafico(gr);
    w.ConfigurarFiltrosLista(wl);
    
		Graph g=wl.getGrafico(1);
		if (g!=null) {
			g.localFill(wl,null,null);
			g.setRefresh(1);
			return g.getImage(300, 300).replace("script:", "");
		}
		return null;
	}
	
	public String getImagen2() throws Exception {     
		GraphScriptMedioReloj gr = new GraphScriptMedioReloj();
		gr.setMax((long)getValorObjetivo());
		if (getValorObjetivo()<1) return "";
		gr.setSize(260);
		gr.setValor(((long)getValorObjetivo()<=(long)getValorActual())?(long)getValorObjetivo():(long)getValorActual());
  	gr.setTitle("OBJETIVO A HOY");
		gr.setLeyenda(""+gr.getValor());
		return gr.getImage(300, 300).replace("script:", "");
	}


	public BizObjetivosCopasPorRutas getObjetivos()  throws Exception {
		BizObjetivosCopasPorRutas w = new BizObjetivosCopasPorRutas();
		w.addFilter("company", getCompany());
		w.addFilter("contrato", getId());
		w.addFilter("detalle", getLinea());
		return w;
	}
	
  public void calcule(boolean update)  throws Exception {
  	BizObjetivosCopasPorRutas objs=getObjetivos();
		objs.readAll();
		if (objs.isObjetivoLocal()) {
		  pValorObjetivo.setValue(getCalculeValorObjetivo());



		  pValorFinContrato.setValue(objs.getPorcAlcanzado());
		  pValorTotalFinContrato.setValue(objs.getVolado());
		  pNivelAlcanzadoEstimada.setValue(getCalculeNivelAlcanzado());
		  pGananciaEstimada.setValue(getCalculeGananciaLocal());

		  pSigValorObjetivo.setValue(getCalculeSigValorObjetivo());
		  pConclusion.setValue(getConclusion());
		  return;
		}

		pValorObjetivo.setValue(getCalculeValorObjetivo());

	  pValorFinContrato.setValue(objs.getPorcAlcanzado());
	  pValorTotalFinContrato.setValue(objs.getVolado());
	  pNivelAlcanzadoEstimada.setValue("No aplica");
	  pGananciaEstimada.setValue(getCalculeGanancia());

	  pSigValorObjetivo.setValue(0);
	  pConclusion.setValue(getConclusion(objs.getConclusion()));
  }
  public double getCalculeGanancia() throws Exception { 
  	double val = pValorTotalFinContrato.getValue();
  	double porc = pValorFinContrato.getValue();
  	return JTools.rd((val*porc)/100,2); 
  }
  public double getCalculeGananciaLocal() throws Exception { 
  	double val = pValorTotalFinContrato.getValue();
  	double porc = JTools.getDoubleNumberEmbedded(pNivelAlcanzadoEstimada.getValue());
  	return JTools.rd((val*porc)/100,2); 
  }
  public String getConclusion(String solucion) throws Exception {
   	Calendar hoy = Calendar.getInstance();
  	hoy.setTime(new Date());
  	Double data=getValorActual();
  	String error=getErrorNiveles();
  	String conclusion="";
  	if (error !=null) 
  		conclusion=error;
  	else{
    	String nivel=getCalculeNivelAlcanzado();
    	if (!nivel.equals("Ninguno")) {
  	  	conclusion+=JTools.getDoubleNumberEmbedded(nivel)==0?"Se llego al objetivo":JLanguage.translate("Se llego al "+nivel+" de premio");
    	} else {
    		conclusion = solucion;
    	}
  	}
  	
  	return conclusion;
  }

  public double getCalculeValorObjetivo() throws Exception { 
  	BizNivel nivel = getObjNivelFavorito();
  	if (nivel==null) {
  		nivel =  getObjNivelMayor();
  		if (nivel==null) return 0;
  		
  	}
  	return nivel.getValor();
  };   
  
  public double getCalculeValorActual() throws Exception { 
  	if (getObjEvent()==null) return 0;
  	Calendar hoy = Calendar.getInstance();
		hoy.setTime(getFechaActual());
		
	 	Calendar desde = Calendar.getInstance();
			desde.setTime(hasFechaDesdeCalculo()?getFechaDesdeCalculo():getObjContrato().getFechaDesde());

		Calendar hasta = Calendar.getInstance();
		hasta.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
		
		if (hoy.before(desde)){
	  	if (getAcumulativo()) return calculeAcumulado(null,desde);
	  	return getObjEvent().getValor(null,desde); 
		}
		if (hoy.after(hasta)) {
	  	if (getAcumulativo()) return calculeAcumulado(null,hasta);
	  	return getObjEvent().getValor(null,hasta); 
		}
  	if (getAcumulativo()) return calculeAcumulado(desde,hoy);
  	return getObjEvent().getValor(desde,hoy); 
  }
  public double getCalculeValorAnoAnterior() throws Exception { 
  	if (getObjEvent()==null) return 0;

   	Calendar desde = Calendar.getInstance();
  		desde.setTime(hasFechaDesdeCalculo()?getFechaDesdeCalculo():getObjContrato().getFechaDesde());
		desde.add(Calendar.YEAR, -1);
		
		Calendar hasta = Calendar.getInstance();
		hasta.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
		hasta.add(Calendar.YEAR, -1);

  	if (getAcumulativo()) return calculeAcumulado(desde,hasta);
		BizSqlEventDato d = getObjEvent().getValorAnterior(desde, hasta);
		if (d!=null) return d.getValue();
		
  	return getObjEvent().getValor(desde,hasta); 
  }  
  public double getCalculeValorFinContrato() throws Exception { 
  	if (getObjEvent()==null) return 0;
   	Calendar desde = Calendar.getInstance();
  		desde.setTime(hasFechaDesdeCalculo()?getFechaDesdeCalculo():getObjContrato().getFechaDesde());
  	Calendar hoy = Calendar.getInstance();
		hoy.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
  	if (getAcumulativo()) return calculeAcumulado(desde,hoy);
  	return getObjEvent().getValor(desde,hoy); 
  }
  public String imprimirResumen() throws Exception {
		JRecord[]recs = new JRecord[4];
		recs[0]=BizBSPCompany.getObjBSPCompany(this.getCompany());
		recs[1]=getObjContrato();
		recs[2]=this;
		recs[3]=null;
		
		String tempfile =BizPlantilla.generateListadoTemporario(this.getCompany(),true,recs,"sys_contrato_detalle_copa_rutas");
		return tempfile;
	}
  public JWins getDetalleGanancia() throws Exception {
		Calendar hoy = Calendar.getInstance();
		hoy.setTime(new Date());
		Calendar hasta = Calendar.getInstance();
		hasta.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());

		if (hoy.after(hasta))
			hoy = hasta;
		return getTicketsVolado();
	}
  public GuiPNRTickets getTicketsVolado() throws Exception {
  	GuiPNRTickets tkt = new GuiPNRTickets();
  	BizDetalle detalle = this;
  	tkt.getRecords().addFilter("company", detalle.getCompany());
		if (detalle.getAerolineas()!=null &&!detalle.getAerolineas().trim().equals(""))
			tkt.getRecords().addFilter("codigoaerolinea",  "("+detalle.getAerolineas()+")","in");
		else
			tkt.getRecords().addFilter("codigoaerolinea",  detalle.getIdAerolinea());
		tkt.getRecords().addFilter("void", false);
		tkt.getRecords().addFilter("reemitted", false);
  	
//  	tkt.addFilter("market", rutaSQL,"like");
  	StringTokenizer toks = new StringTokenizer(detalle.getTourcodeExcludes(),",;");
  	while (toks.hasMoreTokens()) {
  		String s = toks.nextToken();
  		tkt.getRecords().addFilter("tour_code", s.trim(),"<>");
  	}
  	StringTokenizer tok2s = new StringTokenizer(detalle.getClasesExcludes(),",;");
  	while (tok2s.hasMoreTokens()) {
  		String s = tok2s.nextToken();
  		tkt.getRecords().addFilter("clase", s.trim(),"<>");
  	}  	
  	tkt.getRecords().addFilter("departure_date",  detalle.getFDesde(),">=");
  	tkt.getRecords().addFilter("departure_date",  detalle.getFHasta(),"<=");
		tkt.SetVision("neto_usa");
		tkt.setShowFilters(false);
  	return tkt;
  } 

}

