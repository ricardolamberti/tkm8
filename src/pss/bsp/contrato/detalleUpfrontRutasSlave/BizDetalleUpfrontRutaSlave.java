package pss.bsp.contrato.detalleUpfrontRutasSlave;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import pss.bsp.contrato.JAutogenerador;
import pss.bsp.contrato.detalle.mercado.BizDetalleMercado;
import pss.bsp.contrato.detalle.nivel.BizNivel;
import pss.bsp.contrato.detalle.nivel.JTipoNivelUpfront;
import pss.bsp.contrato.detalle.nivel.JTipoPremioPorcentajeDelTotal;
import pss.bsp.contrato.detalleDatamining.BizDetalleDatamining;
import pss.bsp.contrato.logica.JContratoUpfrontRuta;
import pss.bsp.contrato.series.calculado.GuiSerieCalculadas;
import pss.bsp.event.BizBSPSqlEvent;
import pss.common.event.sql.BizSqlEvent;
import pss.core.graph.Graph;
import pss.core.graph.analize.Dataset;
import pss.core.graph.implementations.GraphScriptMedioReloj;
import pss.core.graph.implementations.GraphScriptSerieTemporal;
import pss.core.graph.model.ModelGrid;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;
import pss.tourism.pnr.BizPNRTicket;
import pss.tourism.pnr.GuiPNRTickets;

public class BizDetalleUpfrontRutaSlave extends BizDetalleDatamining {

	public BizDetalleUpfrontRutaSlave() throws Exception {
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

		JAutogenerador autogenerador = new JAutogenerador(this, JAutogenerador.META); // es base se  uso  meta, porque 	 fue  el  primero  y 	 no  estaba  dividido en  varios

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
		if (pValorFinContrato.isNotNull()) {
			return pValorFinContrato.getValue();
		}
		Calendar desde = Calendar.getInstance();
		desde.setTime(hasFechaDesdeCalculo() ? getFechaDesdeCalculo() :getObjContrato().getFechaDesde());
		Calendar hoy = Calendar.getInstance();
		hoy.setTime(hasFechaCalculo() ? getFechaCalculo() : getObjContrato().getFechaHasta());
		if (getAcumulativo())
			return calculeAcumulado(desde,hoy);

		return getObjEventGanancia().getValor(desde,hoy);
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
