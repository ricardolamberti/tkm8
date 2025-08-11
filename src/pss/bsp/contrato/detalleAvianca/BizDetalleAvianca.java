package pss.bsp.contrato.detalleAvianca;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.nivel.BizNivel;
import pss.bsp.contrato.detalle.nivel.JTipoNivelNormal;
import pss.bsp.contrato.detalle.nivel.JTipoPremioPuntos;
import pss.bsp.contrato.detalleAvianca.objetivos.BizObjetivosAvianca;
import pss.bsp.contrato.detalleAvianca.objetivos.BizObjetivosAviancas;
import pss.bsp.contrato.detalleAvianca.objetivos.GuiObjetivosAviancas;
import pss.bsp.contrato.detalleDatamining.BizDetalleDatamining;
import pss.bsp.contrato.logica.JContratoAvianca;
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
import pss.core.winUI.lists.JWinList;
import pss.tourism.pnr.GuiBookings;
import pss.tourism.pnr.GuiPNRTickets;

public class BizDetalleAvianca extends BizDetalle {

  
  public BizDetalleAvianca() throws Exception {
		super();
	}


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  
  @Override
  public void processInsertClon() throws Exception {
  	if (pDescripcion.isNull()) pDescripcion.setValue("MS por ruta "+getIdAerolinea());
  	setLogicaContrato(JContratoAvianca.class.getName());

  	super.processInsertClon();
  }
  @Override
  public void processInsert() throws Exception {
  	if (pDescripcion.isNull()) pDescripcion.setValue("MS por ruta "+getIdAerolinea());
  	setLogicaContrato(JContratoAvianca.class.getName());

  	super.processInsert();
  	
  	autogenerar();
  }
  
  public String imprimirResumen() throws Exception {
		JRecord[]recs = new JRecord[4];
		recs[0]=BizBSPCompany.getObjBSPCompany(this.getCompany());
		recs[1]=getObjContrato();
		recs[2]=this;
		recs[3]=null;
		
		String tempfile =BizPlantilla.generateListadoTemporario(this.getCompany(),true,recs,"sys_contrato_detalle_grilla");
		return tempfile;
	}
  @Override
  public void processUpdate() throws Exception {
  	if (pDescripcion.isNull()) pDescripcion.setValue("MS por ruta "+getIdAerolinea());
  	setLogicaContrato(JContratoAvianca.class.getName());
  	super.processUpdate();
  	autogenerar();

  }
  
	public String getImagen1(boolean printerVersion) throws Exception {
		GuiObjetivosAviancas w = new GuiObjetivosAviancas();
		BizObjetivosAviancas r = getObjetivos();
		r.addFilter("mensaje",  BizObjetivosAvianca.FULL_PUNTOS);
		r.setParent(this);
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
		gr.setTitle("Rutas por puntos");
		ModelVector mg=new ModelVector();
		
		mg.addColumn("ruta", ModelVector.GRAPH_FUNCTION_DATASET);
		mg.addColumn("puntos", ModelVector.GRAPH_FUNCTION_VALUE);
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
	public void autogenerarBookings(String ruta) throws Exception {
		BizDetalleDatamining dd = new BizDetalleDatamining();
		dd.dontThrowException(true);
		boolean updated = dd.readByAutogenerado(getCompany(),getId(),ruta);
		BizSqlEvent event= new BizBSPSqlEvent();
		if (updated) {
			event.dontThrowException(true);
  		event.read(dd.getVariable());
		}
		String sqlOld= event.getConsulta();
		
		String rutaBusqueda = ruta.startsWith("BOK")?ruta.substring(1):ruta;
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
		String sql="select     ";
		sql+= " ( case (";
		sql+= " ( select  COALESCE(count(*),0)   from TUR_PNR_BOOKING b WHERE (b.company=  TUR_PNR_BOOKING.company) AND  b.mercado like '%"+rutaBusqueda+"%' ";
		sql+= strFechasB;
			sql+= " )) ";
		sql+= " when 0 then 0 else ((100.0* ";
		sql+= " COALESCE(count(*),0) ";
		sql+= " / ( ";
		sql+= " ( select  COALESCE(count(*),0)   from TUR_PNR_BOOKING b WHERE (b.company=  TUR_PNR_BOOKING.company)  AND  b.mercado like '%"+rutaBusqueda+"%' ";
		sql+= strFechasB;
		sql+= "))))end) as PORC_COUNT_TUR_PNR_BOLETO_COUNT ";
		

		sql+= " from  TUR_PNR_BOOKING   ";
		sql+= " WHERE (TUR_PNR_BOOKING.company= '"+getCompany()+"')   ";
		sql+= strFechas;
		sql+= " AND TUR_PNR_BOOKING.Carrier_placa = '"+getIdAerolinea()+"' AND TUR_PNR_BOOKING.mercado like '%"+rutaBusqueda+"%'  ";
		sql+= " GROUP BY TUR_PNR_BOOKING.company ";
		event.setConsulta(sql);
		String sqlg="select     ";
		sqlg+= " *";
		sqlg+= " from  TUR_PNR_BOOKING   ";
		sqlg+= " WHERE (TUR_PNR_BOOKING.company= '"+getCompany()+"')   ";
		sqlg+= strFechas;
		sqlg+= " AND TUR_PNR_BOOKING.Carrier_placa = '"+getIdAerolinea()+"' AND TUR_PNR_BOOKING.mercado like '%"+rutaBusqueda+"%'  ";

		event.setConsultaDetalle(sqlg);
		event.setDescripcion("Market share ruta "+ruta);
		event.setCompany(getCompany());
		event.setActivo(true);
		event.setClassDetalle(GuiBookings.class.getName());
		event.setCampo("PORC_COUNT_TUR_PNR_BOLETO_COUNT");
		event.setInvisible(true);
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
  	
  	sql2+= " AND  TUR_PNR_BOLETO.void = 'N' AND  TUR_PNR_BOLETO.is_ticket = 'S' ";
		sql2+= strFechasD;
		sql2+= " AND TUR_PNR_BOLETO.codigoAEROLINEA = '"+getIdAerolinea()+"' AND TUR_PNR_BOLETO.market like '%"+rutaBusqueda+"%'  ";

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
		sql2g+= " AND TUR_PNR_BOLETO.codigoAEROLINEA = '"+getIdAerolinea()+"' AND TUR_PNR_BOLETO.market like '%"+rutaBusqueda+"%'  ";
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
		dd.setDefaultTipoPremio(JTipoPremioPuntos.class.getName());
		dd.setVariable(event.getId());
		dd.setAutogenerado(ruta);
		dd.setVariableGanancia(eventGanancia.getId());
		if (updated)
			dd.processUpdate();
		else
			dd.processInsert();
	}
	public void autogenerarTickets(String ruta) throws Exception {
		BizDetalleDatamining dd = new BizDetalleDatamining();
		dd.dontThrowException(true);
		boolean updated = dd.readByAutogenerado(getCompany(),getId(),ruta);
		BizSqlEvent event= new BizBSPSqlEvent();
		if (updated) {
			event.dontThrowException(true);
  		event.read(dd.getVariable());
		}
		String sqlOld= event.getConsulta();
	
		String rutaBusqueda = ruta.startsWith("BOK")?ruta.substring(1):ruta;
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
		String sql="select     ";
		sql+= " ( case (";
		sql+= " ( select  COALESCE(count(*),0)   from TUR_PNR_BOLETO b WHERE (b.company=  TUR_PNR_BOLETO.company)    AND  b.is_ticket = 'S' AND  b.void = 'N'  AND  b.market like '%"+rutaBusqueda+"%' ";
		sql+= strFechasB;
		if (this.hasIatas())
  		sql+= " AND b.nro_iata in ("+this.getIatas()+")  ";
		sql+= " )) ";
		sql+= " when 0 then 0 else ((100.0* ";
		sql+= " COALESCE(count(*),0) ";
		sql+= " / ( ";
		sql+= " ( select  COALESCE(count(*),0)   from TUR_PNR_BOLETO b WHERE (b.company=  TUR_PNR_BOLETO.company)    AND  b.is_ticket = 'S' AND  b.void = 'N'  AND  b.market like '%"+rutaBusqueda+"%' ";
		sql+= strFechasB;
		if (this.hasIatas())
  		sql+= " AND b.nro_iata in ("+this.getIatas()+")  ";
		sql+= "))))end) as PORC_COUNT_TUR_PNR_BOLETO_COUNT ";
		

		sql+= " from  TUR_PNR_BOLETO   ";
		sql+= " WHERE (TUR_PNR_BOLETO.company= '"+getCompany()+"')   ";
		sql+= " AND  TUR_PNR_BOLETO.void = 'N' AND  TUR_PNR_BOLETO.is_ticket = 'S'  ";
		sql+= strFechas;
		sql+= " AND TUR_PNR_BOLETO.codigoAEROLINEA = '"+getIdAerolinea()+"' AND TUR_PNR_BOLETO.market like '%"+rutaBusqueda+"%'  ";
		if (this.hasIatas())
  		sql+= " AND TUR_PNR_BOLETO.nro_iata in ("+this.getIatas()+")  ";
		sql+= " GROUP BY TUR_PNR_BOLETO.company ";
		event.setConsulta(sql);
		String sqlg="select     ";
		sqlg+= " *";
		sqlg+= " from  TUR_PNR_BOLETO   ";
		sqlg+= " WHERE (TUR_PNR_BOLETO.company= '"+getCompany()+"')   ";
		sqlg+= " AND  TUR_PNR_BOLETO.void = 'N' AND  TUR_PNR_BOLETO.is_ticket = 'S' ";
		sqlg+= strFechas;
		sqlg+= " AND TUR_PNR_BOLETO.codigoAEROLINEA = '"+getIdAerolinea()+"' AND TUR_PNR_BOLETO.market like '%"+rutaBusqueda+"%'  ";
		if (this.hasIatas())
  		sqlg+= " AND TUR_PNR_BOLETO.nro_iata in ("+this.getIatas()+")  ";
		event.setConsultaDetalle(sqlg);
		event.setDescripcion("Market share ruta "+ruta);
		event.setCompany(getCompany());
		event.setActivo(true);
		event.setClassDetalle(GuiPNRTickets.class.getName());
		event.setCampo("PORC_COUNT_TUR_PNR_BOLETO_COUNT");
		event.setInvisible(true);
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
  	
  	sql2+= " AND  TUR_PNR_BOLETO.void = 'N' AND  TUR_PNR_BOLETO.is_ticket = 'S' ";
		sql2+= strFechasD;
		sql2+= " AND TUR_PNR_BOLETO.codigoAEROLINEA = '"+getIdAerolinea()+"' AND TUR_PNR_BOLETO.market like '%"+rutaBusqueda+"%'  ";
		if (this.hasIatas())
  		sql2+= " AND TUR_PNR_BOLETO.nro_iata in ("+this.getIatas()+")  ";

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
		sql2g+= " AND TUR_PNR_BOLETO.codigoAEROLINEA = '"+getIdAerolinea()+"' AND TUR_PNR_BOLETO.market like '%"+rutaBusqueda+"%'  ";
		if (this.hasIatas())
  		sql2g+= " AND TUR_PNR_BOLETO.nro_iata in ("+this.getIatas()+")  ";
		eventGanancia.setConsultaDetalle(sql2g);
		eventGanancia.setCampo("PORC_SUM_TUR_PNR_BOLETO_TARIFA");
		eventGanancia.setDescripcion("Acumulado ruta "+ruta);
		eventGanancia.setCompany(getCompany());
		eventGanancia.setActivo(true);
		eventGanancia.setInvisible(true);
		eventGanancia.setClassDetalle(GuiPNRTickets.class.getName());
		if (!updated || !sqlOld.equals(event.getConsulta()))
			eventGanancia.setEstado(BizSqlEvent.REPROCESAR);
		eventGanancia.processUpdateOrInsertWithCheck();

		dd.setCompany(getCompany());
		dd.setId(getId());
		dd.setDescripcion("Ruta "+ruta);
		dd.setKicker(true);
		dd.setDefaultTipoObjetivo(JTipoNivelNormal.class.getName());
		dd.setDefaultTipoPremio(JTipoPremioPuntos.class.getName());
		dd.setVariable(event.getId());
		dd.setAutogenerado(ruta);
		dd.setVariableGanancia(eventGanancia.getId());
		if (updated)
			dd.processUpdate();
		else
			dd.processInsert();
	}

	public void autogenerar() throws Exception {
		//ver el borrado
		if (!getMercados().equals("")) {
	  	StringTokenizer toks = new StringTokenizer(getMercados()," .,;");
	  	while (toks.hasMoreTokens()) {
	  		String ruta = toks.nextToken().trim();
	  		boolean bookingCalc= (ruta.charAt(0)>='a'&&ruta.charAt(0)<='z');
	  		ruta = ruta.toUpperCase();
	  		if (bookingCalc)
	  			autogenerarBookings(ruta);
	  		else
	  			autogenerarTickets(ruta);
	  	}
		}

	}
	
	@Override
	public void processDelete() throws Exception {
		// TODO Auto-generated method stub
		super.processDelete();
	}
	public BizObjetivosAviancas getObjetivos()  throws Exception {
		BizObjetivosAviancas w = new BizObjetivosAviancas();
		w.addFilter("company", getCompany());
		w.addFilter("contrato", getId());
		w.addFilter("detalle", getLinea());
		return w;
	}
	
  public void calcule(boolean update)  throws Exception {
  	BizObjetivosAviancas objs=getObjetivos();
		objs.readAll();


	  pValorObjetivo.setValue(getCalculeValorObjetivo());

	  pValorFinContrato.setValue(objs.getTotalPuntos());
	  pValorTotalFinContrato.setValue(objs.getVolado());
	  pNivelAlcanzadoEstimada.setValue(getCalculeNivelAlcanzado());
	  pGananciaEstimada.setValue(getCalculeGanancia());

		
	  pSigValorObjetivo.setValue(getCalculeSigValorObjetivo());
	  pConclusion.setValue(getConclusion(objs.getConclusion()));
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
  	  	conclusion+=JLanguage.translate("Se llego al "+nivel+" de premio");
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
		desde.setTime(getObjContrato().getFechaDesde());
		desde.add(Calendar.YEAR, -1);
		
		Calendar hasta = Calendar.getInstance();
		hasta.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
		hasta.add(Calendar.YEAR, -1);

  	if (getAcumulativo()) return calculeAcumulado(null,hasta);
		
		BizSqlEventDato d = getObjEvent().getValorAnterior(null, hasta);
		if (d!=null) return d.getValue();
		
		return getObjEvent().getValor(null,hasta); 
  }  

	public double getCalculeValorFinContrato() throws Exception {
		if (getObjEvent() == null)
			return 0;
		Calendar hoy = Calendar.getInstance();
		hoy.setTime(hasFechaCalculo() ? getFechaCalculo() : getObjContrato().getFechaHasta());
		Calendar desde = Calendar.getInstance();
		desde.setTime(hasFechaDesdeCalculo() ? getFechaDesdeCalculo() : getObjContrato().getFechaDesde());
		if (getAcumulativo())
			return calculeAcumulado(desde,hoy);
		return getObjEvent().getValor(desde, hoy);
	}
  

}
