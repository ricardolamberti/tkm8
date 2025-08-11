package pss.bsp.contrato.detalleUpfrontRutas;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.contrato.JAutogenerador;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.mercado.BizDetalleMercado;
import pss.bsp.contrato.detalle.nivel.BizNivel;
import pss.bsp.contrato.detalle.nivel.JTipoNivelNormal;
import pss.bsp.contrato.detalle.nivel.JTipoNivelUpfront;
import pss.bsp.contrato.detalle.nivel.JTipoPremioPorcentajeDelTotal;
import pss.bsp.contrato.detalleDatamining.BizDetalleDatamining;
import pss.bsp.contrato.detalleRutas.objetivos.BizObjetivosRutas;
import pss.bsp.contrato.detalleRutas.objetivos.GuiObjetivosRutas;
import pss.bsp.contrato.logica.JContratoBackendRuta;
import pss.bsp.contrato.logica.JContratoRuta;
import pss.bsp.contrato.logica.JContratoUpfrontRuta;
import pss.bsp.contrato.series.calculado.GuiSerieCalculadas;
import pss.bsp.event.BizBSPSqlEvent;
import pss.common.event.sql.BizSqlEvent;
import pss.common.event.sql.datos.BizSqlEventDato;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.regions.multilanguage.JLanguage;
import pss.core.graph.Graph;
import pss.core.graph.analize.Dataset;
import pss.core.graph.implementations.GraphScriptMedioReloj;
import pss.core.graph.implementations.GraphScriptPie;
import pss.core.graph.implementations.GraphScriptSerieTemporal;
import pss.core.graph.model.ModelGrid;
import pss.core.graph.model.ModelVector;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;
import pss.tourism.pnr.BizPNRTicket;
import pss.tourism.pnr.GuiBookings;
import pss.tourism.pnr.GuiPNRTickets;

public class BizDetalleUpfrontRuta extends BizDetalleDatamining {

	public BizDetalleUpfrontRuta() throws Exception {
		super();
	}

	public String getImagen1(boolean printerVersion) throws Exception {
		GuiSerieCalculadas w = new GuiSerieCalculadas();
		Date fdesde = getFDesde();
		Date fhasta = getFHasta();
		if (fdesde == null)
			fdesde = getObjContrato().getFechaDesde();
		if (fhasta == null)
			fhasta = getObjContrato().getFechaHasta();
		if (getObjEventGanancia() == null)
			return null;
		if (!getObjEventGanancia().isOK())
			return "<div class=\"panel panel-default\" style=\"height: 100%;width: 100%; text-align: center;\"><b>Aguardando recalculo del indicador.<br/>Estado actual: " + getObjEventGanancia().getEstado() + "</b></div>";

		String sql = "";

		sql += " select * from bsp_serie_calculada ";
		sql += " where modelo = " + getModelo() + " and variable=" + getVariableGanancia() + " and company='" + getCompany() + "' ";
		sql += " and fecha between '" + fdesde + "' and '" + fhasta + "' ";
		// sql+=" select company,max(fecha) as fecha,0 as id,modelo, 0 as mes , dia
		// as dia, 0 as anio, max(valor_ant) as valor_ant, max(valor) as valor,
		// max(valor_estimado) as valor_estimado,max(acumulado) as acumulado,
		// variable from bsp_serie_calculada ";
		// sql+=" where modelo = "+getModelo()+" and variable="+getVariable()+" and
		// company='"+getCompany()+"' ";
		// sql+=" and fecha between '"+fdesde+"' and '"+fhasta+"' ";
		// sql+=" group by company,modelo,variable, dia ";
		// sql+=" order by company,modelo,variable, dia ";
		w.getRecords().SetSQL(sql);
		JWinList wl = new JWinList(w);
		w.AddColumnasDefault(wl);
		// Graph gr = new GraphXYLine();
		GraphScriptSerieTemporal gr = new GraphScriptSerieTemporal();

		gr.addAtributtes(Graph.GRAPH_ATTR_ROTATENAMES, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_DECIMALPRECISON, "2");
		gr.addAtributtes(Graph.GRAPH_ATTR_NUMBERPREFIX, "");
		gr.addAtributtes(Graph.GRAPH_ATTR_EXPORTENABLED, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_XAXISNAME, "Tiempo");
		gr.setWithFocus(!printerVersion);
		gr.addAtributtes(Graph.GRAPH_ATTR_YAXISNAME, getObjEventGanancia().getDescripcion());
		gr.setTitle("Analisis " + getObjEventGanancia().getDescripcion());
		// gr.setImage("html/images/fondochart.png");
		ModelGrid mg = new ModelGrid();

		mg.addColumn("dia", ModelGrid.GRAPH_FUNCTION_CATEGORIE);
		mg.addColumn(new Dataset("Datos actuales"), "valor", ModelGrid.GRAPH_FUNCTION_VALUE);
		mg.addColumn(new Dataset("Estimacion"), "valor_estimado", ModelGrid.GRAPH_FUNCTION_VALUE);
		mg.addColumn(new Dataset("año anterior"), "valor_ant", ModelGrid.GRAPH_FUNCTION_VALUE);
		if (getAcumulativo())
			mg.addColumn(new Dataset("Acumulado"), "acumulado", ModelGrid.GRAPH_FUNCTION_VALUE);
		// else
		// mg.addColumn(new Dataset("Acumulado"),"acumulado",
		// ModelGrid.GRAPH_FUNCTION_VALUE);
		gr.setModel(mg);

		// gr.setMax(getValorObjetivo());
		// gr.setLeyendaMax("Valor Objetivo");
		// gr.setColorMax("#00ff00");
		wl.addGrafico(gr);
		w.ConfigurarFiltrosLista(wl);

		Graph g = wl.getGrafico(1);
		if (g != null) {
			g.localFill(wl, null, null);
			g.setRefresh(1);
			return g.getImage(615, 552).replace("script:", "");
		}
		return null;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean isRequiredAnd() throws Exception {
		boolean hasOrigen = hasOrigenAeropuerto() || hasOrigenContinente() || hasOrigenPais() || hasOrigenZona();
		boolean hasDestino = hasDestinoAeropuerto() || hasDestinoContinente() || hasDestinoPais() || hasDestinoZona();
		if (hasOrigen && hasDestino)
			return isOrigenNegado() && isDestinoNegado();
		if (hasOrigen)
			return isOrigenNegado();
		if (hasDestino)
			return isDestinoNegado();
		return false;
	}

	public void autogenerar() throws Exception {
		BizSqlEvent eventG = autogenerarBase();

		String id = JTools.getValidFilename(getObjContrato().getDescripcion() + getObjContrato().getId() + getLinea() + eventG.getDescripcion().substring(8));
		id = id.length() > 50 ? id.substring(0, 50) : id;
		this.setCompany(getCompany());
		this.setId(getId());
		if (this.getDescripcion().equals(""))
			this.setDescripcion("Upfront " + eventG.getDescripcion());
		this.setLogicaContrato(JContratoUpfrontRuta.class.getName());
		this.setDefaultTipoObjetivo(JTipoNivelUpfront.class.getName());
		this.setDefaultTipoPremio(JTipoPremioPorcentajeDelTotal.class.getName());
		this.setVariableGanancia(eventG.getId());
		this.setAutogenerado(id);
	}

	public BizSqlEvent autogenerarBase() throws Exception {
		// ver borrado
		BizSqlEvent eventG = new BizBSPSqlEvent();
		eventG.dontThrowException(true);
		boolean read = eventG.read(getVariableGanancia());
		String sqlOld = eventG.getConsulta();

		JAutogenerador autogenerador = new JAutogenerador(this, JAutogenerador.META); // es
																																									// base
																																									// pero
																																									// se
																																									// uso
																																									// meta,
																																									// porque
																																									// fue
																																									// el
																																									// primero
																																									// y
																																									// no
																																									// estaba
																																									// dividido
																																									// en
																																									// varios

		eventG.setConsulta(autogenerador.getOutputSql());
		eventG.setConsultaHistorico(autogenerador.getOutputSqlHistorico());
		eventG.setConsultaDetalle(autogenerador.getOutputSqlDetalle());
		eventG.setConsultaAux1(autogenerador.getOutputConsultaAux1());
		eventG.setConsultaAux2(autogenerador.getOutputConsultaAux2());
		eventG.setDescrConsultaAux1(autogenerador.getOutputDescrConsultaAux1());
		eventG.setDescrConsultaAux2(autogenerador.getOutputDescrConsultaAux2());
		
		eventG.setDescripcion(autogenerador.getOutputTitle());
		eventG.setCompany(getCompany());
		eventG.setActivo(true);
		eventG.setCampo(autogenerador.getOutputField());
		eventG.setInvisible(true);
		eventG.setClassDetalle(autogenerador.getOutputClass());
		if (!read || !sqlOld.equals(eventG.getConsulta()))
			eventG.setEstado(BizSqlEvent.REPROCESAR);
		eventG.processUpdateOrInsertWithCheck();

		setConsultaReservaBase(autogenerador.getOutputSqlReserva());

		buildReports(autogenerador, 1);

		return eventG;

	}

	// public void autogenerarBoletos() throws Exception {
	// // ver borrado
	// BizSqlEvent event = new BizBSPSqlEvent();
	// event.dontThrowException(true);
	// boolean updated=event.read(getVariableGanancia());
	// String sqlOld= event.getConsulta();
	//
	// Calendar calDesde = Calendar.getInstance();
	// calDesde.setTime(getFDesde());
	// Calendar calHasta = Calendar.getInstance();
	// calHasta.setTime(getFHasta());
	//
	// Calendar dif = Calendar.getInstance();
	// dif.setTimeInMillis(calHasta.getTime().getTime() -
	// calDesde.getTime().getTime());
	// int dias = dif.get(Calendar.DAY_OF_YEAR);
	// String strFechasB = " AND date_part('year',TUR_PNR_BOLETO.creation_date) =
	// date_part('year',now())";
	// if (dias < 32) {// mensual
	// strFechasB += " AND
	// date_part('month'::text,now())=date_part('month'::text,TUR_PNR_BOLETO.creation_date)";
	// } else if (dias < 63) {// bimestral
	// strFechasB += " AND (floor(((extract (month from
	// TUR_PNR_BOLETO.creation_date))-1 ) / 2) +1 = floor(((extract (month from
	// now()))-1 ) / 2) +1)";
	// } else if (dias < 94) {// trimestral
	// strFechasB += " AND (floor(((extract (month from
	// TUR_PNR_BOLETO.creation_date))-1 ) / 3) +1 = floor(((extract (month from
	// now()))-1 ) / 3) +1) ";
	// } else if (dias < 125) {// cuatrimestral
	// strFechasB += " AND (extract(QUARTER from TUR_PNR_BOLETO.creation_date) =
	// extract(QUARTER from now())) ";
	// } else if (dias < 187) {// semestral
	// strFechasB += " AND (floor(((extract (month from
	// TUR_PNR_BOLETO.creation_date))-1 ) / 6) +1 = floor(((extract (month from
	// now()))-1 ) / 6) +1) ";
	// }
	//
	// String sql = "select ";
	// String rutaDef = "";
	// String field =(isYQ()?(!isDolares()?"tarifa_facturada_yq"
	// :"tarifa_facturada_yq_usa"):(!isDolares()?"tarifa_factura"
	// :"tarifa_factura_usa"));
	//// sql += " sum(" +
	// (isYQ()?(!isDolares()?"TUR_PNR_BOLETO.tarifa_facturada_yq"
	// :"TUR_PNR_BOLETO.tarifa_facturada_yq_usa"):(!isDolares()?"TUR_PNR_BOLETO.tarifa_factura"
	// :"TUR_PNR_BOLETO.tarifa_factura_usa"))+") ";
	// sql += " sum( TUR_PNR_BOLETO."+field+") ";
	// sql += " as SUM_"+field;
	// // sql += " sum(TUR_PNR_BOLETO.neto_factura) as SUM_TUR_PNR_BOLETO_TARIFA
	// ";
	// sql += " from TUR_PNR_BOLETO ";
	// if
	// (hasOrigenContinente()||hasOrigenZona()||(isViceversa()&&(hasDestinoContinente()||hasDestinoZona())))
	// {
	// sql+=" JOIN TUR_AIRPORT ae_origen ON
	// ae_origen.code=TUR_PNR_BOLETO.aeropuerto_origen ";
	// if
	// (hasOrigenContinente()||hasOrigenZona()||(isViceversa()&&(hasDestinoContinente()||hasDestinoZona())))
	// {
	// sql+= " JOIN reg_pais airorpais ON ae_origen.country=airorpais.pais ";
	// }
	// }
	// if
	// (hasDestinoContinente()||hasDestinoZona()||(isViceversa()&&(hasOrigenContinente()||hasOrigenZona())))
	// {
	// sql+=" JOIN TUR_AIRPORT ae_destino ON
	// ae_destino.code=TUR_PNR_BOLETO.aeropuerto_destino ";
	// if
	// (hasDestinoContinente()||hasDestinoZona()||(isViceversa()&&(hasOrigenContinente()||hasOrigenZona())))
	// {
	// sql+= " JOIN reg_pais airdestpais ON ae_destino.country=airdestpais.pais ";
	// }
	// }
	//
	// sql += " WHERE (TUR_PNR_BOLETO.company= '" + getCompany() + "') ";
	// sql += " AND TUR_PNR_BOLETO.void = 'N' AND TUR_PNR_BOLETO.is_ticket = 'S'
	// ";
	// sql += strFechasB;
	//
	// if (hasMultiAerolineas())
	// sql += " AND TUR_PNR_BOLETO.codigoAEROLINEA in ( " + getAerolineas() + ")
	// ";
	// else
	// sql += " AND TUR_PNR_BOLETO.codigoAEROLINEA = '" + getIdAerolinea() + "' ";
	// if (hasMultiAerolineasPlaca())
	// sql += " AND TUR_PNR_BOLETO.codigoAEROLINEA in ( " + getAerolineasPlaca() +
	// ") ";
	//
	// String sqlAND = "";
	// String sqlOROrigen = "";
	// String sqlORDestino = "";
	// String sqlANDViceversa = "";
	// String sqlOROrigenViceversa = "";
	// String sqlORDestinoViceversa = "";
	//
	// if (hasIatas())
	// sql += " AND TUR_PNR_BOLETO.nro_iata in ( " + getIatas() + ") ";
	// if (!isInterlineal()) {
	// if (hasMultiAerolineas())
	// sql += "and not EXISTS (select carrier from TUR_PNR_SEGMENTOAEREO where
	// TUR_PNR_SEGMENTOAEREO.interface_id=TUR_PNR_BOLETO.interface_id and
	// TUR_PNR_SEGMENTOAEREO.carrier not in ( "+getAerolineas()+"))";
	// else
	// sql += " AND TUR_PNR_BOLETO.is_interlineal ='N' ";
	// }
	// if (hasDomesticInternacional()) {
	// if (isDomestic())
	// sql+= (sql.equals("") ? "" : " AND ") + "(internacional_descr='Domestic')";
	// if (isInternational())
	// sql+= (sql.equals("") ? "" : " AND ") +
	// "(internacional_descr='International')";
	// }
	//
	// if (hasTourCodeExcluded()) {
	// String sqlTC = "";
	// StringTokenizer toksTC = new StringTokenizer(getTourcodeExcludes(), ",;");
	// while (toksTC.hasMoreTokens()) {
	// String s = toksTC.nextToken();
	// if (s.trim().indexOf("%")!=-1)
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.tour_code not
	// like '" + s.trim() + "') ";
	// else
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.tour_code <>
	// '" + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlTC.equals(""))
	// sqlAND += (sqlAND.equals("") ? "" : " AND ") + sqlTC;
	// }
	// if (hasFareBasicIncluded()) {
	// String sqlTC = "";
	// String select = " EXISTS (select TUR_PNR_BOOKING.interface_id from
	// TUR_PNR_BOOKING where
	// TUR_PNR_BOOKING.interface_id=TUR_PNR_BOLETO.interface_id and ";
	//
	// StringTokenizer toksTC = new StringTokenizer(getFareBasicIncludes(), ",;");
	// while (toksTC.hasMoreTokens()) {
	// String s = toksTC.nextToken();
	// if (s.trim().indexOf("%")!=-1)
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.fare_basic
	// like '" + s.trim() + "') ";
	// else
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.fare_basic =
	// '" + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlTC.equals(""))
	// sqlAND += (sqlAND.equals("") ? "" : " AND ") + select+"("+ sqlTC+"))";
	// }
	// if (hasFareBasicExcluded()) {
	// String sqlTC = "";
	// String select = " not EXISTS (select TUR_PNR_BOOKING.interface_id from
	// TUR_PNR_BOOKING where
	// TUR_PNR_BOOKING.interface_id=TUR_PNR_BOLETO.interface_id and ";
	// StringTokenizer toksTC = new StringTokenizer(getFareBasicExcludes(), ",;");
	// while (toksTC.hasMoreTokens()) {
	// String s = toksTC.nextToken();
	// if (s.trim().indexOf("%")!=-1)
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.fare_basic
	// like '" + s.trim() + "') ";
	// else
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.fare_basic =
	// '" + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlTC.equals(""))
	// sqlAND += (sqlAND.equals("") ? "" : " AND ") + select+"("+ sqlTC+"))";;
	// }
	// if (hasTourCodeIncluded()) {
	// String sqlTC = "";
	// StringTokenizer toksTC = new StringTokenizer(getTourcodeIncludes(), ",;");
	// while (toksTC.hasMoreTokens()) {
	// String s = toksTC.nextToken();
	// if (s.trim().indexOf("%")!=-1)
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.tour_code
	// like '" + s.trim() + "') ";
	// else
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.tour_code =
	// '" + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlTC.equals(""))
	// sqlAND += (sqlAND.equals("") ? "" : " AND ") + sqlTC;
	// }
	//
	// if (hasTipoPasajeroExcluded()) {
	// String sqlTC = "";
	// StringTokenizer toksTC = new StringTokenizer(getTipoPasajeroExcludes(),
	// ",;");
	// while (toksTC.hasMoreTokens()) {
	// String s = toksTC.nextToken();
	// if (s.trim().indexOf("%")!=-1)
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.tipo_pasajero
	// not like '" + s.trim() + "') ";
	// else
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.tipo_pasajero
	// <> '" + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlTC.equals(""))
	// sqlAND += (sqlAND.equals("") ? "" : " AND ") + sqlTC;
	// }
	// if (hasTipoPasajeroIncluded()) {
	// String sqlTC = "";
	// StringTokenizer toksTC = new StringTokenizer(getTipoPasajeroIncludes(),
	// ",;");
	// while (toksTC.hasMoreTokens()) {
	// String s = toksTC.nextToken();
	// if (s.trim().indexOf("%")!=-1)
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.tipo_pasajero
	// like '" + s.trim() + "') ";
	// else
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.tipo_pasajero
	// = '" + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlTC.equals(""))
	// sqlAND += (sqlAND.equals("") ? "" : " AND ") + sqlTC;
	// }
	//
	//
	// if (hasClasesExcluded()) {
	// String sqlTC = "";
	// StringTokenizer toksTC = new StringTokenizer(getClasesExcludes(), ",;");
	// while (toksTC.hasMoreTokens()) {
	// String s = toksTC.nextToken();
	// int pos = s.indexOf(":");
	// if (pos!=-1) {
	// String a = s.substring(0,pos);
	// String b = s.substring(pos+1);
	// String aerolinea =a.trim();
	// String clase=b.trim();
	// if (b.length()==2) {
	// aerolinea=b;
	// clase=a;
	// }
	// sqlTC += (sqlTC.equals("") ? "" : " AND ") + "
	// (TUR_PNR_BOLETO.codigoAEROLINEA <> '" + aerolinea + "' OR
	// (TUR_PNR_BOLETO.codigoAEROLINEA = '" + aerolinea + "' AND
	// TUR_PNR_BOLETO.clase <> '" + clase + "')) ";
	// } else {
	// sqlTC += (sqlTC.equals("") ? "" : " AND ") + " (TUR_PNR_BOLETO.clase <> '"
	// + s.trim() + "') ";
	// }
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlTC.equals(""))
	// sqlAND += (sqlAND.equals("") ? "" : " AND ") + "("+sqlTC+")";
	// }
	// if (hasClasesIncluded()) {
	// String sqlOR = "";
	//
	// StringTokenizer toksTC = new StringTokenizer(getClasesIncluded(), ",;");
	// while (toksTC.hasMoreTokens()) {
	// String s = toksTC.nextToken();
	// int pos = s.indexOf(":");
	// if (pos!=-1) {
	// String a = s.substring(0,pos);
	// String b = s.substring(pos+1);
	// String aerolinea =a.trim();
	// String clase=b.trim();
	// if (b.length()==2) {
	// aerolinea=b;
	// clase=a;
	// }
	// sqlOR += (sqlOR.equals("") ? "" : " OR ") + "
	// ((TUR_PNR_BOLETO.codigoAEROLINEA = '" + aerolinea + "' AND
	// TUR_PNR_BOLETO.clase ='" + clase + "')) ";
	// } else {
	// sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.clase ='" +
	// s.trim() + "') ";
	// }
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	//
	// }
	// if (!sqlOR.equals(""))
	// sqlAND += (sqlAND.equals("") ? "" : " AND ") + "("+sqlOR+")";
	// }
	//
	// if (hasMercado()) {
	// String sqlOR = "";
	// StringTokenizer toksTC = new StringTokenizer(getMercados(), ",;");
	// while (toksTC.hasMoreTokens()) {
	// String s = toksTC.nextToken();
	// String rutaSQL = s.trim();
	// if (rutaSQL.length() == 6)
	// rutaSQL = "BOK-" + rutaSQL;
	// else if (rutaSQL != null && rutaSQL.startsWith("BOK"))
	// rutaSQL = rutaSQL.substring(1);
	//
	// sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.market like
	// '%" + rutaSQL + "%') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlOR.equals(""))
	// sqlAND += (sqlAND.equals("") ? "" : " AND ") + "("+sqlOR+")";
	// }
	// if (hasMercadoBase()) { // negado
	// String sqlOR = "";
	// StringTokenizer toksTC = new StringTokenizer(getMercadosBase(), ",;");
	// while (toksTC.hasMoreTokens()) {
	// String s = toksTC.nextToken();
	// String rutaSQL = s.trim();
	// if (rutaSQL.length() == 6)
	// rutaSQL = "BOK-" + rutaSQL;
	// else if (rutaSQL != null && rutaSQL.startsWith("BOK"))
	// rutaSQL = rutaSQL.substring(1);
	//
	// sqlOR += (sqlOR.equals("") ? "" : " AND ") + " (TUR_PNR_BOLETO.market like
	// '%" + rutaSQL + "%') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlOR.equals(""))
	// sqlAND += (sqlAND.equals("") ? "" : " AND ") + " NOT ("+sqlOR+")";
	// }
	//
	// if (hasOrigenAeropuerto()) {
	// String sqlOR = "";
	// String sqlORVic = "";
	// JIterator<String> it = getOrigenAeropuerto().getIterator();
	// while (it.hasMoreElements()) {
	// String s = it.nextElement();
	// sqlOR += (sqlOR.equals("") ? "" : " OR ") + "
	// (TUR_PNR_BOLETO.aeropuerto_origen = '" + s.trim() + "') ";
	// if (isViceversa())
	// sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + "
	// (TUR_PNR_BOLETO.aeropuerto_destino = '" + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	//
	// }
	// if (!sqlOR.equals(""))
	// sqlOROrigen += (sqlOROrigen.equals("") ? "" : " OR ") + "("+sqlOR+")";
	// if (!sqlORVic.equals(""))
	// sqlOROrigenViceversa += (sqlOROrigenViceversa.equals("") ? "" : " OR ") +
	// "("+sqlORVic+")";
	// }
	// if (hasOrigenContinente()) {
	// String sqlOR = "";
	// String sqlORVic = "";
	// JIterator<String> it = getOrigenContinente().getIterator();
	// while (it.hasMoreElements()) {
	// String s = it.nextElement();
	// sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (airorpais.continente = '" +
	// s.trim() + "') ";
	// if (isViceversa())
	// sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (airdestpais.continente
	// = '" + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlOR.equals(""))
	// sqlOROrigen += (sqlOROrigen.equals("") ? "" : " OR ") + "("+sqlOR+")";
	// if (!sqlORVic.equals(""))
	// sqlOROrigenViceversa += (sqlOROrigenViceversa.equals("") ? "" : " OR ") +
	// "("+sqlORVic+")";
	// }
	// if (hasOrigenZona()) {
	// String sqlOR = "";
	// String sqlORVic = "";
	// JIterator<String> it = getOrigenZona().getIterator();
	// while (it.hasMoreElements()) {
	// String s = it.nextElement();
	// sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (airorpais.region = '" +
	// s.trim() + "') ";
	// if (isViceversa())
	// sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (airdestpais.region =
	// '" + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlOR.equals(""))
	// sqlOROrigen += (sqlOROrigen.equals("") ? "" : " OR ") + "("+sqlOR+")";
	// if (!sqlORVic.equals(""))
	// sqlOROrigenViceversa += (sqlOROrigenViceversa.equals("") ? "" : " OR ") +
	// "("+sqlORVic+")";
	// }
	// if (hasOrigenPais()) {
	// String sqlOR = "";
	// String sqlORVic = "";
	// JIterator<String> it = getOrigenPais().getIterator();
	// while (it.hasMoreElements()) {
	// String s = it.nextElement();
	// sqlOR += (sqlOR.equals("") ? "" : " OR ") + " ( TUR_PNR_BOLETO.pais_origen
	// = '" + s.trim() + "') ";
	// if (isViceversa())
	// sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + "
	// (TUR_PNR_BOLETO.pais_destino = '" + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlOR.equals(""))
	// sqlOROrigen += (sqlOROrigen.equals("") ? "" : " OR ") + "("+sqlOR+")";
	// if (!sqlORVic.equals(""))
	// sqlOROrigenViceversa += (sqlOROrigenViceversa.equals("") ? "" : " OR ") +
	// "("+sqlORVic+")";
	// }
	//
	// if (!sqlOROrigen.equals(""))
	// sqlAND+= (sqlAND.equals("") ? "" : " AND ") + (isOrigenNegado()?" NOT
	// ":"")+"("+sqlOROrigen+")";
	// if (!sqlOROrigenViceversa.equals(""))
	// sqlANDViceversa+= (sqlANDViceversa.equals("") ? "" : " AND ") +
	// (isOrigenNegado()?" NOT ":"")+"("+sqlOROrigenViceversa+")";
	//
	// if (hasDestinoAeropuerto()) {
	// String sqlOR = "";
	// String sqlORVic = "";
	// JIterator<String> it = getDestinoAeropuerto().getIterator();
	// while (it.hasMoreElements()) {
	// String s = it.nextElement();
	// sqlOR += (sqlOR.equals("") ? "" : " OR ") + "
	// (TUR_PNR_BOLETO.aeropuerto_destino = '" + s.trim() + "') ";
	// if (isViceversa())
	// sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + "
	// (TUR_PNR_BOLETO.aeropuerto_origen = '" + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlOR.equals(""))
	// sqlORDestino += (sqlORDestino.equals("") ? "" : " OR ") + "("+sqlOR+")";
	// if (!sqlORVic.equals(""))
	// sqlORDestinoViceversa += (sqlORDestinoViceversa.equals("") ? "" : " OR ") +
	// "("+sqlORVic+")";
	// }
	// if (hasDestinoContinente()) {
	// String sqlOR = "";
	// String sqlORVic = "";
	// JIterator<String> it = getDestinoContinente().getIterator();
	// while (it.hasMoreElements()) {
	// String s = it.nextElement();
	// sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (airdestpais.continente = '"
	// + s.trim() + "') ";
	// if (isViceversa())
	// sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (airorpais.continente =
	// '" + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlOR.equals(""))
	// sqlORDestino += (sqlORDestino.equals("") ? "" : " OR ") + "("+sqlOR+")";
	// if (!sqlORVic.equals(""))
	// sqlORDestinoViceversa += (sqlORDestinoViceversa.equals("") ? "" : " OR ") +
	// "("+sqlORVic+")";
	// }
	// if (hasDestinoZona()) {
	// String sqlOR = "";
	// String sqlORVic = "";
	// JIterator<String> it = getDestinoZona().getIterator();
	// while (it.hasMoreElements()) {
	// String s = it.nextElement();
	// sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (airdestpais.region = '" +
	// s.trim() + "') ";
	// if (isViceversa())
	// sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (airorpais.region = '"
	// + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlOR.equals(""))
	// sqlORDestino += (sqlORDestino.equals("") ? "" : " OR ") + "("+sqlOR+")";
	// if (!sqlORVic.equals(""))
	// sqlORDestinoViceversa += (sqlORDestinoViceversa.equals("") ? "" : " OR ") +
	// "("+sqlORVic+")";
	// }
	// if (hasDestinoPais()) {
	// String sqlOR = "";
	// String sqlORVic = "";
	// JIterator<String> it = getDestinoPais().getIterator();
	// while (it.hasMoreElements()) {
	// String s = it.nextElement();
	// sqlOR += (sqlOR.equals("") ? "" : " OR ") + " ( TUR_PNR_BOLETO.pais_destino
	// = '" + s.trim() + "') ";
	// if (isViceversa())
	// sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + "
	// (TUR_PNR_BOLETO.pais_origen = '" + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlOR.equals(""))
	// sqlORDestino += (sqlORDestino.equals("") ? "" : " OR ") + "("+sqlOR+")";
	// if (!sqlORVic.equals(""))
	// sqlORDestinoViceversa += (sqlORDestinoViceversa.equals("") ? "" : " OR ") +
	// "("+sqlORVic+")";
	// }
	// if (!sqlORDestino.equals(""))
	// sqlAND+= (sqlAND.equals("") ? "" : " AND ") + (isDestinoNegado()?" NOT
	// ":"")+"("+sqlORDestino+")";
	// if (!sqlORDestinoViceversa.equals(""))
	// sqlANDViceversa+= (sqlANDViceversa.equals("") ? "" : " AND ") +
	// (isDestinoNegado()?" NOT ":"")+"("+sqlORDestinoViceversa+")";
	//
	//
	// if (!sqlANDViceversa.equals("") && isRequiredAnd())
	// sql += " AND ((" + sqlAND + ") AND (" + sqlANDViceversa + "))";
	// else if (!sqlANDViceversa.equals(""))
	// sql += " AND ((" + sqlAND + ") OR (" + sqlANDViceversa + "))";
	// else if (!sqlAND.equals(""))
	// sql += " AND (" + sqlAND + ")";
	//
	// sql += " GROUP BY TUR_PNR_BOLETO.company ";
	// event.setConsulta(sql);
	//
	// String sqlg = "select ";
	// sqlg += " *";
	// sqlg += " from TUR_PNR_BOLETO ";
	// if
	// (hasOrigenContinente()||hasOrigenZona()||(isViceversa()&&(hasDestinoContinente()||hasDestinoZona())))
	// {
	// sqlg+=" JOIN TUR_AIRPORT ae_origen ON
	// ae_origen.code=TUR_PNR_BOLETO.aeropuerto_origen ";
	// if
	// (hasOrigenContinente()||hasOrigenZona()||(isViceversa()&&(hasDestinoContinente()||hasDestinoZona())))
	// {
	// sqlg+= " JOIN reg_pais airorpais ON ae_origen.country=airorpais.pais ";
	// }
	// }
	// if
	// (hasDestinoContinente()||hasDestinoZona()||(isViceversa()&&(hasOrigenContinente()||hasOrigenZona())))
	// {
	// sqlg+=" JOIN TUR_AIRPORT ae_destino ON
	// ae_destino.code=TUR_PNR_BOLETO.aeropuerto_destino ";
	// if
	// (hasDestinoContinente()||hasDestinoZona()||(isViceversa()&&(hasOrigenContinente()||hasOrigenZona())))
	// {
	// sqlg+= " JOIN reg_pais airdestpais ON ae_destino.country=airdestpais.pais
	// ";
	// }
	// }
	// sqlg += " WHERE (TUR_PNR_BOLETO.company= '" + getCompany() + "') ";
	// sqlg += " AND TUR_PNR_BOLETO.void = 'N' AND TUR_PNR_BOLETO.is_ticket = 'S'
	// ";
	// sqlg += strFechasB;
	// if (getAerolineas() != null && !getAerolineas().trim().equals(""))
	// sqlg += " AND TUR_PNR_BOLETO.codigoAEROLINEA in ( " + getAerolineas() + ")
	// ";
	// else
	// sqlg += " AND TUR_PNR_BOLETO.codigoAEROLINEA = '" + getIdAerolinea() + "'
	// ";
	//
	// if (hasIatas())
	// sqlg += " AND TUR_PNR_BOLETO.nro_iata in ( " + getIatas() + ") ";
	// if (!isInterlineal()) {
	// if (hasMultiAerolineas())
	// sqlg += "and not EXISTS (select carrier from TUR_PNR_SEGMENTOAEREO where
	// TUR_PNR_SEGMENTOAEREO.interface_id=TUR_PNR_BOLETO.interface_id and
	// TUR_PNR_SEGMENTOAEREO.carrier not in ( "+getAerolineas()+"))";
	// else
	// sqlg += " AND TUR_PNR_BOLETO.is_interlineal ='N' ";
	// }
	// if (hasDomesticInternacional()) {
	// if (isDomestic())
	// sqlg+= (sqlg.equals("") ? "" : " AND ") +
	// "(internacional_descr='Domestic')";
	// if (isInternational())
	// sqlg+= (sqlg.equals("") ? "" : " AND ") +
	// "(internacional_descr='International')";
	// }
	// if (!sqlANDViceversa.equals("") && isRequiredAnd())
	// sqlg += " AND ((" + sqlAND + ") AND (" + sqlANDViceversa + "))";
	// else if (!sqlANDViceversa.equals(""))
	// sqlg += " AND ((" + sqlAND + ") OR (" + sqlANDViceversa + "))";
	// else if (!sqlAND.equals(""))
	// sqlg += " AND (" + sqlAND + ")";
	// event.setConsultaDetalle(sqlg);
	//
	// event.setDescripcion("Upfront ruta" + rutaDef);
	// event.setCompany(getCompany());
	// event.setActivo(true);
	// event.setCampo("SUM_"+field);
	// event.setInvisible(true);
	// event.setClassDetalle(GuiPNRTickets.class.getName());
	// if (!updated || !sqlOld.equals(event.getConsulta()))
	// event.setEstado(BizSqlEvent.REPROCESAR);
	// event.processUpdateOrInsertWithCheck();
	// String id = JTools.getValidFilename(getObjContrato().getDescripcion() +
	// getObjContrato().getId()+getLinea()+rutaDef);
	// id=id.length()>50?id.substring(0, 50):id;
	// this.setCompany(getCompany());
	// this.setId(getId());
	// if (this.getDescripcion().equals(""))
	// this.setDescripcion("Upfront " + rutaDef);
	// this.setKicker(false);
	// this.setLogicaContrato(JContratoUpfrontRuta.class.getName());
	// this.setDefaultTipoObjetivo(JTipoNivelUpfront.class.getName());
	// this.setDefaultTipoPremio(JTipoPremioPorcentajeDelTotal.class.getName());
	// this.setVariableGanancia(event.getId());
	// this.setAutogenerado(id);
	//
	//
	// }

	// public void autogenerarBooking() throws Exception {
	// // ver borrado
	// String id = JTools.getValidFilename(getObjContrato().getDescripcion() +
	// getObjContrato().getId());
	// id=id.length()>50?id.substring(0, 50):id;
	// BizSqlEvent event = new BizBSPSqlEvent();
	// event.dontThrowException(true);
	// boolean updated=event.read(getVariableGanancia());
	// String sqlOld= event.getConsulta();
	//
	// Calendar calDesde = Calendar.getInstance();
	// calDesde.setTime(getFDesde());
	// Calendar calHasta = Calendar.getInstance();
	// calHasta.setTime(getFHasta());
	//
	// Calendar dif = Calendar.getInstance();
	// dif.setTimeInMillis(calHasta.getTime().getTime() -
	// calDesde.getTime().getTime());
	// int dias = dif.get(Calendar.DAY_OF_YEAR);
	// String strFechas = " AND date_part('year',TUR_PNR_BOOKING.fecha_emision) =
	// date_part('year',now())";
	// if (dias < 32) {// mensual
	// strFechas += " AND
	// date_part('month'::text,now())=date_part('month'::text,TUR_PNR_BOOKING.fecha_emision)";
	// } else if (dias < 63) {// bimestral
	// strFechas += " AND (floor(((extract (month from
	// TUR_PNR_BOOKING.fecha_emision))-1 ) / 2) +1 = floor(((extract (month from
	// now()))-1 ) / 2) +1)";
	// } else if (dias < 94) {// trimestral
	// strFechas += " AND (floor(((extract (month from
	// TUR_PNR_BOOKING.fecha_emision))-1 ) / 3) +1 = floor(((extract (month from
	// now()))-1 ) / 3) +1) ";
	// } else if (dias < 125) {// cuatrimestral
	// strFechas += " AND (extract(QUARTER from TUR_PNR_BOOKING.fecha_emision) =
	// extract(QUARTER from now())) ";
	// } else if (dias < 187) {// semestral
	// strFechas += " AND (floor(((extract (month from
	// TUR_PNR_BOOKING.fecha_emision))-1 ) / 6) +1 = floor(((extract (month from
	// now()))-1 ) / 6) +1) ";
	// }
	//
	// String sql = "select ";
	// String rutaDef = "";
	// String field="baseyq_usa";
	// String bigfield="boletos.baseyq_usa";
	// if (isYQ()) {
	// if (isDolares()) {
	// if (isViceversa()) {
	// field="baseyq_usa";
	// bigfield="boletos."+field;
	// }else{
	// field="baseyq_usa";
	// bigfield="boletos."+field;
	// }
	// } else {
	// if (isViceversa()){
	// field="tarifa_yq";
	// bigfield="boletos."+field;
	// } else {
	// field="tarifa_yq";
	// bigfield="boletos."+field;
	// }
	// }
	// }else {
	// if (isDolares()) {
	// if (isViceversa()){
	// field="monto_orig";
	// bigfield="TUR_PNR_BOOKING."+field;
	// } else {
	// field="monto_round_usa";
	// bigfield="TUR_PNR_BOOKING."+field;
	// }
	// } else {
	// if (isViceversa()) {
	// field="monto";
	// bigfield="TUR_PNR_BOOKING."+field;
	// } else {
	// field="monto_round";
	// bigfield="TUR_PNR_BOOKING."+field;
	// }
	// }
	//
	// }
	// sql +=" sum("+bigfield+") as SUM_"+field+" ";
	//
	// sql += " from TUR_PNR_BOOKING ";
	// if (hasTipoPasajeroExcluded() || hasTipoPasajeroIncluded()
	// ||hasTourCodeExcluded() || hasTourCodeIncluded() || hasIatas()
	// ||(!isInterlineal()/*&&!hasMultiAerolineas()*/) || isYQ() ||
	// bigfield.indexOf("boletos.")!=-1) {
	// sql+=" JOIN TUR_PNR_BOLETO boletos ON
	// TUR_PNR_BOOKING.interface_id=boletos.interface_id AND
	// TUR_PNR_BOOKING.company=boletos.company ";
	// }
	// if
	// (hasOrigenContinente()||hasOrigenZona()||(isViceversa()&&(hasDestinoContinente()||hasDestinoZona())))
	// {
	// sql+= " JOIN reg_pais airorpais ON
	// TUR_PNR_BOOKING.origen_pais=airorpais.pais ";
	// }
	// if
	// (hasDestinoContinente()||hasDestinoZona()||(isViceversa()&&(hasOrigenContinente()||hasOrigenZona())))
	// {
	// sql+= " JOIN reg_pais airdestpais ON
	// TUR_PNR_BOOKING.destino_pais=airdestpais.pais ";
	// }
	// sql += " WHERE (TUR_PNR_BOOKING.company= '" + getCompany() + "') ";
	// sql += strFechas;
	//// Carrier_placa
	// if (hasMultiAerolineasPlaca())
	// sql += " AND TUR_PNR_BOOKING.carrier_placa in ( " + getAerolineasPlaca() +
	// ") ";
	// if (hasMultiAerolineas())
	// sql += " AND TUR_PNR_BOOKING.carrier in ( " + getAerolineas() + ") ";
	// else
	// sql += " AND TUR_PNR_BOOKING.carrier = '" + getIdAerolinea() + "' ";
	// if (hasIatas())
	// sql += " AND boletos.nro_iata in ( " + getIatas() + ") ";
	// if (!isInterlineal()) {
	//// if (hasMultiAerolineas())
	//// sql += "and not EXISTS (select carrier from TUR_PNR_SEGMENTOAEREO where
	// TUR_PNR_SEGMENTOAEREO.interface_id=TUR_PNR_BOOKING.interface_id and
	// TUR_PNR_SEGMENTOAEREO.carrier not in ( "+getAerolineas()+"))";
	//// else
	// sql += " AND boletos.is_interlineal ='N' ";
	// }
	// if (hasDomesticInternacional()) {
	// if (isDomestic())
	// sql+= (sql.equals("") ? "" : " AND ") +
	// "(internationaldomestic='Domestic')";
	// if (isInternational())
	// sql+= (sql.equals("") ? "" : " AND ") +
	// "(internationaldomestic='International')";
	// }
	//
	// String sqlAND = "";
	// String sqlOROrigen = "";
	// String sqlORDestino = "";
	// String sqlANDViceversa = "";
	// String sqlOROrigenViceversa = "";
	// String sqlORDestinoViceversa = "";
	// if (hasTourCodeExcluded()) {
	// String sqlOR = "";
	// String sqlTC = "";
	// StringTokenizer toksTC = new StringTokenizer(getTourcodeExcludes(), ",;");
	// while (toksTC.hasMoreTokens()) {
	// String s = toksTC.nextToken();
	// if (s.trim().indexOf("%")!=-1)
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (boletos.tour_code not like
	// '" + s.trim() + "') ";
	// else
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (boletos.tour_code <> '" +
	// s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	//
	// }
	// if (!sqlTC.equals(""))
	// sqlAND += (sqlAND.equals("") ? "" : " AND ") + sqlTC;
	// }
	// if (hasTourCodeIncluded()) {
	// String sqlOR = "";
	// String sqlTC = "";
	// StringTokenizer toksTC = new StringTokenizer(getTourcodeIncludes(), ",;");
	// while (toksTC.hasMoreTokens()) {
	// String s = toksTC.nextToken();
	// if (s.trim().indexOf("%")!=-1)
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (boletos.tour_code like '" +
	// s.trim() + "') ";
	// else
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (boletos.tour_code = '" +
	// s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	//
	// }
	// if (!sqlTC.equals(""))
	// sqlAND += (sqlAND.equals("") ? "" : " AND ") + sqlTC;
	// }
	// if (hasFareBasicExcluded()) {
	// String sqlOR = "";
	// String sqlTC = "";
	// StringTokenizer toksTC = new StringTokenizer(getFareBasicExcludes(), ",;");
	// while (toksTC.hasMoreTokens()) {
	// String s = toksTC.nextToken();
	// if (s.trim().indexOf("%")!=-1)
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.fare_basic
	// not like '" + s.trim() + "') ";
	// else
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.fare_basic
	// <> '" + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	//
	// }
	// if (!sqlTC.equals(""))
	// sqlAND += (sqlAND.equals("") ? "" : " AND ") + sqlTC;
	// }
	// if (hasFareBasicIncluded()) {
	// String sqlOR = "";
	// String sqlTC = "";
	// StringTokenizer toksTC = new StringTokenizer(getFareBasicIncludes(), ",;");
	// while (toksTC.hasMoreTokens()) {
	// String s = toksTC.nextToken();
	// if (s.trim().indexOf("%")!=-1)
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.fare_basic
	// like '" + s.trim() + "') ";
	// else
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.fare_basic =
	// '" + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	//
	// }
	// if (!sqlTC.equals(""))
	// sqlAND += (sqlAND.equals("") ? "" : " AND ") + sqlTC;
	// }
	// if (hasTipoPasajeroExcluded()) {
	// String sqlOR = "";
	// String sqlTC = "";
	// StringTokenizer toksTC = new StringTokenizer(getTipoPasajeroExcludes(),
	// ",;");
	// while (toksTC.hasMoreTokens()) {
	// String s = toksTC.nextToken();
	// if (s.trim().indexOf("%")!=-1)
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (boletos.tipo_pasajero not
	// like '" + s.trim() + "') ";
	// else
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (boletos.tipo_pasajero <> '"
	// + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	//
	// }
	// if (!sqlTC.equals(""))
	// sqlAND += (sqlAND.equals("") ? "" : " AND ") + sqlTC;
	// }
	// if (hasTipoPasajeroIncluded()) {
	// String sqlOR = "";
	// String sqlTC = "";
	// StringTokenizer toksTC = new StringTokenizer(getTipoPasajeroIncludes(),
	// ",;");
	// while (toksTC.hasMoreTokens()) {
	// String s = toksTC.nextToken();
	// if (s.trim().indexOf("%")!=-1)
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (boletos.tipo_pasajero like
	// '" + s.trim() + "') ";
	// else
	// sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (boletos.tipo_pasajero = '" +
	// s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	//
	// }
	// if (!sqlTC.equals(""))
	// sqlAND += (sqlAND.equals("") ? "" : " AND ") + sqlTC;
	// }
	//
	// if (hasClasesExcluded()) {
	// String sqlOR = "";
	// String sqlTC = "";
	// StringTokenizer toksTC = new StringTokenizer(getClasesExcludes(), ",;");
	// while (toksTC.hasMoreTokens()) {
	// String s = toksTC.nextToken();
	// int pos = s.indexOf(":");
	// if (pos!=-1) {
	// String a = s.substring(0,pos);
	// String b = s.substring(pos+1);
	// String aerolinea =a.trim();
	// String clase=b.trim();
	// if (b.length()==2) {
	// aerolinea=b;
	// clase=a;
	// }
	// sqlTC += (sqlTC.equals("") ? "" : " AND ") + " (TUR_PNR_BOOKING.carrier <>
	// '" + aerolinea + "' OR ( TUR_PNR_BOOKING.carrier = '" + aerolinea + "' AND
	// TUR_PNR_BOOKING.clase <> '" + clase + "')) ";
	// } else {
	// sqlTC += (sqlTC.equals("") ? "" : " AND ") + " (TUR_PNR_BOOKING.clase <> '"
	// + s.trim() + "') ";
	// }
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlTC.equals(""))
	// sqlAND += (sqlAND.equals("") ? "" : " AND ") + "("+ sqlTC+")";
	// }
	// if (hasClasesIncluded()) {
	// String sqlOR = "";
	// StringTokenizer toksTC = new StringTokenizer(getClasesIncluded(), ",;");
	// while (toksTC.hasMoreTokens()) {
	// String s = toksTC.nextToken();
	// int pos = s.indexOf(":");
	// if (pos!=-1) {
	// String a = s.substring(0,pos);
	// String b = s.substring(pos+1);
	// String aerolinea =a.trim();
	// String clase=b.trim();
	// if (b.length()==2) {
	// aerolinea=b;
	// clase=a;
	// }
	// sqlOR += (sqlOR.equals("") ? "" : " OR ") + " ( (TUR_PNR_BOOKING.carrier =
	// '" + aerolinea + "' AND TUR_PNR_BOOKING.clase ='" + clase + "')) ";
	// } else {
	// sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.clase ='" +
	// s.trim() + "') ";
	// }
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	//
	// }
	// if (!sqlOR.equals(""))
	// sqlAND += (sqlAND.equals("") ? "" : " AND ") + "("+sqlOR+")";
	//
	// }
	// if (hasMercado()) {
	// String sqlOR = "";
	// StringTokenizer toksTC = new StringTokenizer(getMercados(), ",;");
	// while (toksTC.hasMoreTokens()) {
	// String s = toksTC.nextToken();
	// String rutaSQL = s.trim();
	// if (rutaSQL.length() == 6)
	// rutaSQL = "BOK-" + rutaSQL;
	// else if (rutaSQL != null && rutaSQL.startsWith("BOK"))
	// rutaSQL = rutaSQL.substring(1);
	//
	// sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.mercado like
	// '%" + rutaSQL + "%') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlOR.equals(""))
	// sqlAND += (sqlAND.equals("") ? "" : " AND ") + "("+sqlOR+")";
	// }
	// if (hasMercadoBase()) { // negado
	// String sqlOR = "";
	// StringTokenizer toksTC = new StringTokenizer(getMercadosBase(), ",;");
	// while (toksTC.hasMoreTokens()) {
	// String s = toksTC.nextToken();
	// String rutaSQL = s.trim();
	// if (rutaSQL.length() == 6)
	// rutaSQL = "BOK-" + rutaSQL;
	// else if (rutaSQL != null && rutaSQL.startsWith("BOK"))
	// rutaSQL = rutaSQL.substring(1);
	//
	// sqlOR += (sqlOR.equals("") ? "" : " AND ") + " (TUR_PNR_BOLETO.market like
	// '%" + rutaSQL + "%') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlOR.equals(""))
	// sqlAND += (sqlAND.equals("") ? "" : " AND ") + " NOT ("+sqlOR+")";
	// }
	//
	// if (hasOrigenAeropuerto()) {
	// String sqlOR = "";
	// String sqlORVic = "";
	// JIterator<String> it = getOrigenAeropuerto().getIterator();
	// while (it.hasMoreElements()) {
	// String s = it.nextElement();
	// sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.despegue =
	// '" + s.trim() + "') ";
	// if (isViceversa())
	// sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.arrivo
	// = '" + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlOR.equals(""))
	// sqlOROrigen += (sqlOROrigen.equals("") ? "" : " OR ") + "("+sqlOR+")";
	// if (!sqlORVic.equals(""))
	// sqlOROrigenViceversa += (sqlOROrigenViceversa.equals("") ? "" : " OR ") +
	// "("+sqlORVic+")";
	// }
	// if (hasOrigenContinente()) {
	// String sqlOR = "";
	// String sqlORVic = "";
	// JIterator<String> it = getOrigenContinente().getIterator();
	// while (it.hasMoreElements()) {
	// String s = it.nextElement();
	// sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (airorpais.continente = '" +
	// s.trim() + "') ";
	// if (isViceversa())
	// sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (airdestpais.continente
	// = '" + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlOR.equals(""))
	// sqlOROrigen += (sqlOROrigen.equals("") ? "" : " OR ") + "("+sqlOR+")";
	// if (!sqlORVic.equals(""))
	// sqlOROrigenViceversa += (sqlOROrigenViceversa.equals("") ? "" : " OR ") +
	// "("+sqlORVic+")";
	// }
	// if (hasOrigenZona()) {
	// String sqlOR = "";
	// String sqlORVic = "";
	// JIterator<String> it = getOrigenZona().getIterator();
	// while (it.hasMoreElements()) {
	// String s = it.nextElement();
	// sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (airorpais.region = '" +
	// s.trim() + "') ";
	// if (isViceversa())
	// sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (airdestpais.region =
	// '" + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlOR.equals(""))
	// sqlOROrigen += (sqlOROrigen.equals("") ? "" : " OR ") + "("+sqlOR+")";
	// if (!sqlORVic.equals(""))
	// sqlOROrigenViceversa += (sqlOROrigenViceversa.equals("") ? "" : " OR ") +
	// "("+sqlORVic+")";
	// }
	// if (hasOrigenPais()) {
	// String sqlOR = "";
	// String sqlORVic = "";
	// JIterator<String> it = getOrigenPais().getIterator();
	// while (it.hasMoreElements()) {
	// String s = it.nextElement();
	// sqlOR += (sqlOR.equals("") ? "" : " OR ") + " ( TUR_PNR_BOOKING.Origen_pais
	// = '" + s.trim() + "') ";
	// if (isViceversa())
	// sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + "
	// (TUR_PNR_BOOKING.Destino_pais = '" + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlOR.equals(""))
	// sqlOROrigen += (sqlOROrigen.equals("") ? "" : " OR ") + "("+sqlOR+")";
	// if (!sqlORVic.equals(""))
	// sqlOROrigenViceversa += (sqlOROrigenViceversa.equals("") ? "" : " OR ") +
	// "("+sqlORVic+")";
	// }
	// if (!sqlOROrigen.equals(""))
	// sqlAND+= (sqlAND.equals("") ? "" : " AND ") +"("+sqlOROrigen+")";
	// if (!sqlOROrigenViceversa.equals(""))
	// sqlANDViceversa+= (sqlANDViceversa.equals("") ? "" : " AND ") +
	// (isOrigenNegado()?" NOT ":"")+"("+sqlOROrigenViceversa+")";
	//
	// if (hasDestinoAeropuerto()) {
	// String sqlOR = "";
	// String sqlORVic = "";
	// JIterator<String> it = getDestinoAeropuerto().getIterator();
	// while (it.hasMoreElements()) {
	// String s = it.nextElement();
	// sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.arrivo = '"
	// + s.trim() + "') ";
	// if (isViceversa())
	// sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + "
	// (TUR_PNR_BOOKING.despegue = '" + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlOR.equals(""))
	// sqlORDestino += (sqlORDestino.equals("") ? "" : " OR ") + "("+sqlOR+")";
	// if (!sqlORVic.equals(""))
	// sqlORDestinoViceversa += (sqlORDestinoViceversa.equals("") ? "" : " OR ") +
	// "("+sqlORVic+")";
	// }
	// if (hasDestinoContinente()) {
	// String sqlOR = "";
	// String sqlORVic = "";
	// JIterator<String> it = getDestinoContinente().getIterator();
	// while (it.hasMoreElements()) {
	// String s = it.nextElement();
	// sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (airdestpais.continente = '"
	// + s.trim() + "') ";
	// if (isViceversa())
	// sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (airorpais.continente=
	// '" + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlOR.equals(""))
	// sqlORDestino += (sqlORDestino.equals("") ? "" : " OR ") + "("+sqlOR+")";
	// if (!sqlORVic.equals(""))
	// sqlORDestinoViceversa += (sqlORDestinoViceversa.equals("") ? "" : " OR ") +
	// "("+sqlORVic+")";
	// }
	// if (hasDestinoZona()) {
	// String sqlOR = "";
	// String sqlORVic = "";
	// JIterator<String> it = getDestinoZona().getIterator();
	// while (it.hasMoreElements()) {
	// String s = it.nextElement();
	// sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (airdestpais.region = '" +
	// s.trim() + "') ";
	// if (isViceversa())
	// sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (airorpais.region= '" +
	// s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlOR.equals(""))
	// sqlORDestino += (sqlORDestino.equals("") ? "" : " OR ") + "("+sqlOR+")";
	// if (!sqlORVic.equals(""))
	// sqlORDestinoViceversa += (sqlORDestinoViceversa.equals("") ? "" : " OR ") +
	// "("+sqlORVic+")";
	// }
	// if (hasDestinoPais()) {
	// String sqlOR = "";
	// String sqlORVic = "";
	// JIterator<String> it = getDestinoPais().getIterator();
	// while (it.hasMoreElements()) {
	// String s = it.nextElement();
	// sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (
	// TUR_PNR_BOOKING.Destino_pais = '" + s.trim() + "') ";
	// if (isViceversa())
	// sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + "
	// (TUR_PNR_BOOKING.Origen_pais = '" + s.trim() + "') ";
	// rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	// }
	// if (!sqlOR.equals(""))
	// sqlORDestino += (sqlORDestino.equals("") ? "" : " OR ") + "("+sqlOR+")";
	// if (!sqlORVic.equals(""))
	// sqlORDestinoViceversa += (sqlORDestinoViceversa.equals("") ? "" : " OR ") +
	// "("+sqlORVic+")";
	// }
	//
	// if (!sqlORDestino.equals(""))
	// sqlAND+= (sqlAND.equals("") ? "" : " AND ") +"("+sqlORDestino+")";
	// if (!sqlORDestinoViceversa.equals(""))
	// sqlANDViceversa+= (sqlANDViceversa.equals("") ? "" : " AND ")
	// +"("+sqlORDestinoViceversa+")";
	//
	//
	// if (!sqlANDViceversa.equals("") && isRequiredAnd())
	// sql += " AND ((" + sqlAND + ") AND (" + sqlANDViceversa + "))";
	// else if (!sqlANDViceversa.equals(""))
	// sql += " AND ((" + sqlAND + ") OR (" + sqlANDViceversa + "))";
	// else if (!sqlAND.equals(""))
	// sql += " AND (" + sqlAND + ")";
	//
	// sql += " GROUP BY TUR_PNR_BOOKING.company ";
	// event.setConsulta(sql);
	//
	// String sqlg = "select ";
	// sqlg += " *";
	// sqlg += " from TUR_PNR_BOOKING ";
	// if
	// (hasTipoPasajeroExcluded()||hasTipoPasajeroIncluded()||hasTourCodeExcluded()||hasTourCodeIncluded()||(!isInterlineal()/*&&!hasMultiAerolineas()*/))
	// {
	// sqlg+=" JOIN TUR_PNR_BOLETO boletos ON
	// TUR_PNR_BOOKING.interface_id=boletos.interface_id AND
	// TUR_PNR_BOOKING.company=boletos.company ";
	// }
	// if
	// (hasOrigenContinente()||hasOrigenZona()||(isViceversa()&&(hasDestinoContinente()||hasDestinoZona())))
	// {
	// sqlg+= " JOIN reg_pais airorpais ON
	// TUR_PNR_BOOKING.origen_pais=airorpais.pais ";
	// }
	// if
	// (hasDestinoContinente()||hasDestinoZona()||(isViceversa()&&(hasOrigenContinente()||hasOrigenZona())))
	// {
	// sqlg+= " JOIN reg_pais airdestpais ON
	// TUR_PNR_BOOKING.destino_pais=airdestpais.pais ";
	// }
	// sqlg += " WHERE (TUR_PNR_BOOKING.company= '" + getCompany() + "') ";
	// sqlg += strFechas;
	// if (hasMultiAerolineasPlaca())
	// sqlg += " AND TUR_PNR_BOOKING.carrier_placa in ( " + getAerolineasPlaca() +
	// ") ";
	// if (hasMultiAerolineas())
	// sqlg += " AND TUR_PNR_BOOKING.carrier in ( " + getAerolineas() + ") ";
	// else
	// sqlg += " AND TUR_PNR_BOOKING.carrier = '" + getIdAerolinea() + "' ";
	// if (!isInterlineal()) {
	// /* if (hasMultiAerolineas())
	// sql += " and not EXISTS (select carrier from TUR_PNR_SEGMENTOAEREO where
	// TUR_PNR_SEGMENTOAEREO.interface_id=TUR_PNR_BOOKING.interface_id and
	// TUR_PNR_SEGMENTOAEREO.carrier not in ( "+getAerolineas()+"))";
	// else*/
	// sql += " AND boletos.is_interlineal ='N' ";
	// }
	// if (hasDomesticInternacional()) {
	// if (isDomestic())
	// sqlg+= (sqlg.equals("") ? "" : " AND ") +
	// "(internationaldomestic='Domestic')";
	// if (isInternational())
	// sqlg+= (sqlg.equals("") ? "" : " AND ") +
	// "(internationaldomestic='International')";
	// }
	//
	// if (!sqlANDViceversa.equals("") && isRequiredAnd())
	// sqlg += " AND ((" + sqlAND + ") AND (" + sqlANDViceversa + "))";
	// else if (!sqlANDViceversa.equals(""))
	// sqlg += " AND ((" + sqlAND + ") OR (" + sqlANDViceversa + "))";
	// else if (!sqlAND.equals(""))
	// sqlg += " AND (" + sqlAND + ")";
	// event.setConsultaDetalle(sqlg);
	//
	// event.setDescripcion("Upfront ruta" + rutaDef);
	// event.setCompany(getCompany());
	// event.setActivo(true);
	// event.setCampo("SUM_"+field);
	// event.setInvisible(true);
	// event.setClassDetalle(GuiBookings.class.getName());
	// if (!updated || !sqlOld.equals(event.getConsulta()))
	// event.setEstado(BizSqlEvent.REPROCESAR);
	// event.processUpdateOrInsertWithCheck();
	//
	// this.setCompany(getCompany());
	// this.setId(getId());
	// if (this.getDescripcion().equals(""))
	// this.setDescripcion("Upfront booking " + rutaDef);
	// this.setKicker(false);
	// this.setLogicaContrato(JContratoUpfrontRuta.class.getName());
	// this.setDefaultTipoObjetivo(JTipoNivelUpfront.class.getName());
	// this.setDefaultTipoPremio(JTipoPremioPorcentajeDelTotal.class.getName());
	// this.setVariableGanancia(event.getId());
	// this.setAutogenerado(id);
	//
	// }

	@Override
	public void processInsert() throws Exception {
		if (pDescripcion.isNull())
			pDescripcion.setValue("Upfront " + getIdAerolinea());
		setLogicaContrato(JContratoUpfrontRuta.class.getName());
		super.processInsert();
		autogenerar();
		super.processUpdate();
		BizNivel n1 = new BizNivel();
		n1.setCompany(getCompany());
		n1.setId(getId());
		n1.setLinea(getLinea());
		n1.setTipoObjetivo(JTipoNivelUpfront.class.getName());
		n1.setTipoPremio(JTipoPremioPorcentajeDelTotal.class.getName());
		n1.setPremio(getPorcUpfront());
		n1.processInsert();
	}

	@Override
	public void processUpdate() throws Exception {
		// if (pDescripcion.isNull())
		// pDescripcion.setValue("MS por ruta " + getIdAerolinea());
		setLogicaContrato(JContratoUpfrontRuta.class.getName());
		autogenerar();
		super.processUpdate();

		JRecords<BizNivel> n1 = new JRecords<BizNivel>(BizNivel.class);
		n1.addFilter("company", getCompany());
		n1.addFilter("id", getId());
		n1.addFilter("linea", getLinea());
		n1.dontThrowException(true);
		n1.toStatic();
		BizNivel n = n1.nextRecord() ? n1.getRecord() : new BizNivel();
		n.setCompany(getCompany());
		n.setId(getId());
		n.setLinea(getLinea());
		n.setTipoObjetivo(JTipoNivelUpfront.class.getName());
		n.setTipoPremio(JTipoPremioPorcentajeDelTotal.class.getName());
		n.setPremio(getPorcUpfront());
		n.processUpdateOrInsertWithCheck();
	}

	public String getImagen2() throws Exception {
		GraphScriptMedioReloj gr = new GraphScriptMedioReloj();
		gr.setMax((long) getValorObjetivo());
		if (getValorObjetivo() < 1)
			return "";
		gr.setSize(260);
		gr.setValor(((long) getValorObjetivo() <= (long) getValorActual()) ? (long) getValorObjetivo() : (long) getValorActual());
		gr.setTitle("OBJETIVO A HOY");
		gr.setLeyenda("");
		return gr.getImage(300, 300).replace("script:", "");
	}

	public void calcule(boolean update) throws Exception {
		pValorObjetivo.setValue(0);

		pValorFinContrato.setValue(0);
		pValorTotalFinContrato.setValue(getCalculeValorTotalFinContrato());
		pNivelAlcanzadoEstimada.setValue(null);
		pGananciaEstimada.setValue(getCalculeGananciaEstimada());
		pComisionBase.setValue(getCalculeComisionBase());

		pSigValorObjetivo.setValue(0);
		pConclusion.setValue(getConclusion());
		pAnalisis.setValue(getAnalisis());
//		new BizPNRTicket().fillDetalle(this);

	}

	public double getCalculeGanancia() throws Exception {
		double val = pValorTotalFinContrato.getValue();
		double s = 0;
		JRecords<BizNivel> niveles = getObjNiveles();
		JIterator<BizNivel> it = niveles.getStaticIterator();
		while (it.hasMoreElements()) {
			BizNivel n = it.nextElement();
			s += n.getValorPremio(val, 0);
		}
		return s;
	}

	public double getCalculeGananciaEstimada() throws Exception {
		double val = pValorTotalFinContrato.getValue();
		double s = 0;
		JRecords<BizNivel> niveles = getObjNiveles();
		JIterator<BizNivel> it = niveles.getStaticIterator();
		while (it.hasMoreElements()) {
			BizNivel n = it.nextElement();
			s += n.getValorPremio(val, 0);
		}
		return s;
	}

	public String getDetalleNivelCumplimiento() throws Exception {
		return "-";
	}

	@Override
	public String getConclusion() throws Exception {
		String error = "";

		String errores = getErrorNiveles();
		if (errores != null)
			return error;

		return "Upfront";
	}

	public String getAnalisis() throws Exception {
		String analisis = "";
		// cuantos tickets faltan?
		String unidad = "";
		if (getObjEventGanancia() == null)
			return "";
		String campo = getObjEventGanancia().getCampoDescr().toLowerCase();

		if (campo.indexOf("porcentaje") != -1)
			unidad = "%";
		else if (campo.indexOf("boletos") != -1)
			unidad = "tickets";
		else if (campo.indexOf("dolares") != -1)
			unidad = "dolares";
		else if (campo.indexOf("neto") != -1 || campo.indexOf("tarifa") != -1 || campo.indexOf("total") != -1 || campo.indexOf("iva") != -1 || campo.indexOf("impuesto") != -1 || campo.indexOf("comision") != -1)
			unidad = "pesos";
		// BizMoneda.getMonedaLocal(BizBSPCompany.getObjBSPCompany(getCompany()).getPais());

		String analisisHtml = "Contrato upfront";

		analisisHtml = "<html><body> ";
		analisisHtml += "<p><font size=\"4\" color=\"#000000\" face=\"arial, verdana, helvetica\">Que vender? </font></p><br><p><font size=\"3\" color=\"#000000\" face=\"arial, verdana, helvetica\">";
		analisisHtml += analisis;
		analisisHtml += "</font></p><br><br><br><br><br></body></html>";

		return analisisHtml;
	}

	public double getCalculeValorTotalFinContrato() throws Exception {
		if (getObjEventGanancia() == null) {
			return pValorFinContrato.getValue();
		}
		Calendar desde = Calendar.getInstance();
		desde.setTime(hasFechaDesdeCalculo() ? getFechaDesdeCalculo() :getObjContrato().getFechaDesde());
		Calendar hoy = Calendar.getInstance();
		hoy.setTime(hasFechaCalculo() ? getFechaCalculo() : getObjContrato().getFechaHasta());
		if (getAcumulativo())
			return calculeAcumulado(desde,hoy);

		// Calendar desde = Calendar.getInstance();
		// desde.setTime(getObjContrato().getFechaDesde());
		//
		// Calendar hasta = Calendar.getInstance();
		// hasta.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
		//
		// if (hoy.before(desde)) return 0.00;
		if (!getObjEventGanancia().isOK() || hasFechaCalculo() || hasFechaDesdeCalculo())
			return getObjEventGanancia().getValor(desde,hoy);

		BizSqlEventDato dato = getObjEventGanancia().getValorAnterior(desde, hoy);
		return (dato == null ? 0 : dato.getValue());

		// return getObjEventGanancia().getValor(hoy);

	}

	public void processMercado() throws Exception {
		// se puede buscar en el detalle todos los mercados

		if (!getObjEventGanancia().getClassDetalle().equals(GuiPNRTickets.class.getName()))
			return;

		Calendar hoy = Calendar.getInstance();
		hoy.setTime(new Date());
		Calendar desde = Calendar.getInstance();
		desde.setTime(hasFechaDesdeCalculo()?getFechaDesdeCalculo():getObjContrato().getFechaDesde());
		Calendar hasta = Calendar.getInstance();
		hasta.setTime(getObjContrato().getFechaHasta());

		JMap<String, String> aerolineas = JCollectionFactory.createMap();
		if (hoy.after(hasta))
			hoy = hasta;

		JWins wins = getObjEventGanancia().getDetalles(desde,hoy, getObjEventGanancia() != null ? getObjEventGanancia().getCampo() : null);
		JRecords<BizPNRTicket> tickets = (JRecords<BizPNRTicket>) wins.getRecords();
		JIterator<BizPNRTicket> it = tickets.getStaticIterator();
		while (it.hasMoreElements()) {
			BizPNRTicket t = it.nextElement();
			if (aerolineas.getElement(t.getCarrier()) == null) {
				aerolineas.addElement(t.getCarrier(), t.getCarrier());
			}

			StringTokenizer toks = new StringTokenizer(t.getMarket(), ",");
			while (toks.hasMoreTokens()) {
				boolean insert = false;
				String mrk = toks.nextToken();
				if (!mrk.startsWith("BOK-"))
					continue;
				String mercado = mrk.substring(4);
				BizDetalleMercado detMerc = new BizDetalleMercado();
				detMerc.dontThrowException(true);
				if (!detMerc.read(getCompany(), getIdAerolinea(), mercado)) {
					detMerc.setCompany(getCompany());
					detMerc.setIdContrato(getId());
					detMerc.setIdDetalle(getLinea());
					detMerc.setIdAerolinea(t.getCarrier());
					detMerc.setMarketingID(mercado);
					detMerc.setOrigen(t.getAeropuertoOrigen());
					detMerc.setDestino(t.getAeropuertoDestino());
					insert = true;
				}

				// buscar FMS
				if (detMerc.getFMSEdited() != 0) {
					detMerc.setFMS(detMerc.getFMSEdited());
				} else {
					detMerc.setFMS(getFMS(getCompany(), getIdAerolinea(), mercado));
				}

				if (insert)
					detMerc.processInsert();
				else
					detMerc.processUpdate();

			}

			String straer = "";
			JIterator<String> ita = aerolineas.getValueIterator();
			while (ita.hasMoreElements()) {
				straer += (straer.equals("") ? "" : ", ") + "'" + ita.nextElement() + "'";
			}

			setIdAerolinea(straer);
		}

	}

}
