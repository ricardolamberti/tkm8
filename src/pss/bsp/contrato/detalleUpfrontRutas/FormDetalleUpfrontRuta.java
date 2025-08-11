package pss.bsp.contrato.detalleUpfrontRutas;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import pss.bsp.contrato.detalleBackendDobleRutas.BizDetalleBackendDobleRuta;
import pss.bsp.contrato.detalleUpfront.BizDetalleUpfront;
import pss.bsp.contrato.detalleUpfront.GuiDetalleUpfront;
import pss.bsp.event.GuiBSPSqlEvents;
import pss.common.event.sql.GuiSqlEvents;
import pss.common.security.BizUsuario;
import pss.core.ui.components.JPssCalendarEdit;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssImage;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormImage;
import pss.core.winUI.controls.JFormLista;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormEditResponsive;
import pss.core.winUI.responsiveControls.JFormFieldsetResponsive;
import pss.core.winUI.responsiveControls.JFormImageCardResponsive;
import pss.core.winUI.responsiveControls.JFormImageResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormRadioResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;
import pss.tourism.carrier.GuiCarriers;
import pss.www.ui.JWebIcon;

public class FormDetalleUpfrontRuta extends JBaseForm {

	private static final long serialVersionUID = 1446860278358L;

	/**
	 * Constructor de la Clase
	 */
	public FormDetalleUpfrontRuta() throws Exception {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public GuiDetalleUpfrontRuta getWin() {
		return (GuiDetalleUpfrontRuta) getBaseWin();
	}

	/**
	 * Inicializacion Grafica
	 */
	protected void jbInit() throws Exception {

	}

	/**
	 * Linkeo los campos con la variables del form
	 */
	public void InicializarPanel(JWin zWin) throws Exception {
		setAutoRefresh(true, 10000, "DASHBOARD");
		AddItemEdit(null, CHAR, REQ, "company").setHide(true).SetValorDefault(BizUsuario.getUsr().getCompany());
		AddItemEdit(null, UINT, OPT, "id").setHide(true);
		AddItemEdit(null, UINT, OPT, "linea").setHide(true);

		AddItemEdit(null, CHAR, REQ, "variable_ganancia").setHide(true);

		JFormControl c = AddItemCheck(null, OPT, "acumulativo").setHide(true).SetValorDefault(false);

		JFormPanelResponsive botonera = AddItemRow();
		JFormImageCardResponsive ic = null;
		ic = botonera.AddImageCard("Tickets Prorrateo", JWebIcon.getResponsiveIcon("fa fa-ticket-alt fa-3x"), "Tickets prorrateo", 200);
		if (ic != null)
			ic.setResponsiveClass("panel-red").setSizeColumns(2);
		ic = botonera.AddImageCard("Tickets Objetivo", JWebIcon.getResponsiveIcon("fa fa-ticket-alt fa-3x"), "Tickets Objetivo", 50);
		if (ic != null)
			ic.setResponsiveClass("panel-green").setSizeColumns(2);
		ic = botonera.AddImageCard("Tickets B.Comis.", JWebIcon.getResponsiveIcon("fa fa-ticket-alt fa-3x"), "Tickets B.Comis.", 60);
		if (ic != null)
			ic.setResponsiveClass("panel-yellow").setSizeColumns(2);
		ic = botonera.AddImageCard("Tickets Aux.", JWebIcon.getResponsiveIcon("fa fa-ticket-alt fa-3x"), "Tickets Aux.", 65);
		if (ic != null)
			ic.setResponsiveClass("panel-primary").setSizeColumns(2);
		// ic = AddImageCard("Ver ind. Objetivo", JWebIcon.getResponsiveIcon("fa
		// fa-chart-bar fa-3x"), "Ver ind. Objetivo", 10);
		// if (ic!=null) ic.setResponsiveClass("panel-green").setSizeColumns(2);
		// ic = AddImageCard("Ver ind. B.Comis.", JWebIcon.getResponsiveIcon("fa
		// fa-chart-bar fa-3x"), "Ver ind. B.Comis.", 15);
		// if (ic!=null) ic.setResponsiveClass("panel-yellow").setSizeColumns(2);
		// ic = AddImageCard("Ver ind. Aux." , JWebIcon.getResponsiveIcon("fa
		// fa-chart-bar fa-3x"), "Ver ind. Aux.", 17);
		// if (ic!=null) ic.setResponsiveClass("panel-primary").setSizeColumns(2);
		ic = botonera.AddImageCard("Como se calcula objetivo?", JWebIcon.getResponsiveIcon("fa fa-question-circle fa-3x"), "Como se calcula objetvo?", 111);
		if (ic != null)
			ic.setResponsiveClass("panel-green").setSizeColumns(2);
		ic = botonera.AddImageCard("Extra Info?", JWebIcon.getResponsiveIcon("fa fa-info-circle fa-3x"), "Mas información asociada?", 100);
		if (ic != null)
			ic.setResponsiveClass("panel-danger").setSizeColumns(2);
		if (getWin().GetcDato().hasConsultaReservaBase())
			botonera.AddInfoCard("Reservas", CHAR, "reservas_base", JWebIcon.getResponsiveIcon("fa fa-bookmark fa-3x"), "Reservas.", 601).setResponsiveClass("border-left-success shadow ").setSizeColumns(2);

		JFormColumnResponsive column1 = AddItemColumn(6);
		JFormColumnResponsive column2 = AddItemColumn(6);
		JFormFieldsetResponsive panelM = column1.AddItemFieldset("Resumen", 12);
		panelM.AddItemEdit("emisión desde", CHAR, OPT, "fecha_emision_desde").setSizeColumns(6).SetReadOnly(true);
		panelM.AddItemEdit("Emision hasta", CHAR, OPT, "fecha_emision_hasta").setSizeColumns(6).SetReadOnly(true);
		panelM.AddItemEdit("Vuelo desde", CHAR, OPT, "fecha_vuelo_desde").setSizeColumns(6).SetReadOnly(true);
		panelM.AddItemEdit("Vuelo hasta", CHAR, OPT, "fecha_vuelo_hasta").setSizeColumns(6).SetReadOnly(true);
		panelM.AddItemEdit("Blockout desde", CHAR, OPT, "fecha_blockout_desde").setSizeColumns(6).SetReadOnly(true);
		panelM.AddItemEdit("Blockout hasta", CHAR, OPT, "fecha_blockout_hasta").setSizeColumns(6).SetReadOnly(true);
    panelM.AddItemCombo( "Aerolinea", CHAR, REQ, "id_aerolinea" , new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getAerolineas(one);
    	}
    }).setShowKey(true).setSizeColumns(3).setReadOnly(true);
    panelM.AddItemEdit( "Aerolineas Placa", CHAR, OPT, "aerolineas_placa" ).setSizeColumns(5).setReadOnly(true);;
    panelM.AddItemEdit( "Aerolineas Op.", CHAR, OPT, "aerolineas" ).setSizeColumns(4).setReadOnly(true);;
    panelM.AddItemEdit( "Aerolineas Op. exclu.", CHAR, OPT, "no_aerolineas" ).setSizeColumns(4).setReadOnly(true);;
    panelM.AddItemEdit( "Moneda", CHAR,  OPT,"moneda_consolidada" ).setSizeColumns(4).SetReadOnly(true);;
    panelM.AddItemCheck( "Con YQ", CHAR, "yq" ).setAlign(JFormCheckResponsive.LABEL_TOP).setSizeColumns(4).SetReadOnly(true);;
    panelM.AddItemEdit( "Iatas", CHAR, OPT, "iatas" ).setSizeColumns(12).SetReadOnly(true);;
    panelM.AddItemEdit( "Clases incluidas", CHAR, OPT, "class_included" ).setSizeColumns(6).SetReadOnly(true);;
    panelM.AddItemEdit( "Clases excluidas", CHAR, OPT, "class_excluded" ).setSizeColumns(6).SetReadOnly(true);;
    panelM.AddItemEdit( "Tourcode incluidos", CHAR, OPT, "tourcode_included" ).setSizeColumns(6).SetReadOnly(true);;
    panelM.AddItemEdit( "Tourcode excluidos", CHAR, OPT, "tourcode_excluded" ).setSizeColumns(6).SetReadOnly(true);;
    panelM.AddItemEdit( "Fam.Tarifaria incluidos", CHAR, OPT, "farebasic_included" ).setSizeColumns(6).SetReadOnly(true);;
    panelM.AddItemEdit( "Fam.Tarifaria excluidos", CHAR, OPT, "farebasic_excluded" ).setSizeColumns(6).SetReadOnly(true);;
    panelM.AddItemEdit( "Tipo pasajero incluidos", CHAR, OPT, "tipopasajero_included" ).setSizeColumns(6).SetReadOnly(true);;
    panelM.AddItemEdit( "Tipo pasajero excluidos", CHAR, OPT, "tipopasajero_excluded" ).setSizeColumns(6).SetReadOnly(true);;
    panelM.AddItemEdit( "Mercados incluidos", CHAR, OPT, "mercados" ).setSizeColumns(6).SetReadOnly(true);;
    panelM.AddItemEdit( "Mercados excluidos", CHAR, OPT, "no_mercados" ).setSizeColumns(6).SetReadOnly(true);;
    panelM.AddItemEdit( "Vuelos incluidos", CHAR, OPT, "vuelos" ).setSizeColumns(6).SetReadOnly(true);;
    panelM.AddItemEdit( "Vuelos excluidos", CHAR, OPT, "no_vuelos" ).setSizeColumns(6).SetReadOnly(true);;
    panelM.AddItemEdit( "Rutas", CHAR, OPT, "rutas" ).setSizeColumns(12).SetReadOnly(true);;
    panelM.AddItemEdit( "Origen", CHAR, OPT, "origen_consolidado" ).setSizeColumns(12).SetReadOnly(true);;
    panelM.AddItemEdit( "Destino", CHAR, OPT, "destino_consolidado" ).setSizeColumns(12).SetReadOnly(true);;
    panelM.AddItemCheck( "Viceversa", CHAR, "viceversa" ).setSizeColumns(3).SetReadOnly(true);
    panelM.AddItemCheck( "Bookings", CHAR, "pax" ).setSizeColumns(3).SetReadOnly(true);
    panelM.AddItemCheck( "Primera emisión", CHAR, "primera" ).setSizeColumns(3).SetReadOnly(true);
    panelM.AddItemThreeCheck( "Tipo", CHAR, OPT, "domestic" , "S",JFormRadioResponsive.NO_FILTER,"N","Domestico","-","Internacional").setSizeColumns(6).SetReadOnly(true);
    panelM.AddItemThreeCheck( "Interlineal", CHAR, OPT,"interlineal" , "S",JFormRadioResponsive.NO_FILTER,"N","Solo Multi","Interlineal","Solo una").setSizeColumns(3).SetReadOnly(true);
    panelM.AddItemEdit( "Premio",FLOAT,REQ, "premio_upfront").setSizeColumns(3).SetReadOnly(true);
    
		
		JFormFieldsetResponsive panelR = column1.AddItemFieldset("Resultados", 12);
		// JFormImageResponsive i=panelR.AddItemImage( "", "imagen2" );
		// i.setSource(JPssImage.SOURCE_SCRIPT);
		//

		panelR.AddItemEdit("Evaluación al fin del contrato", CHAR, OPT, "conclusion").SetReadOnly(true);
		// panelR.AddItemEdit( "Valor del indicador objetivo", FLOAT, OPT,
		// "valor_fcontrato" ).setSizeColumns(6).SetReadOnly(true);
		panelR.AddItemEdit("Valor Base comisionable", FLOAT, OPT, "valor_totalcontrato").setSizeColumns(6).setVisible(true).SetReadOnly(true);
		// panelR.AddItemEdit( "Nivel alcanzado", CHAR, OPT,
		// "nivel_alcanzado_estimada" ).setSizeColumns(6).SetReadOnly(true);
		// panelR.AddItemEdit( "Valor auxiliar", FLOAT, OPT, "ganancia_auxiliar"
		// ).setSizeColumns(6).SetReadOnly(true);
    JFormEditResponsive s = panelR.AddItemEdit("Ganancia/Comisión", FLOAT, OPT, "ganancia_estimada");
    s.setSizeColumns(6).SetReadOnly(true);
    s.setSkinStereotype("big_label");
    s.setFontSize(25);
    s.setFontWeigth(JFormEditResponsive.FONT_WEIGHT_BOLD);

		JFormFieldsetResponsive panelG1 = column2.AddItemFieldset("Gráficas", 12);
    panelG1.setBackground("#e6e6fa");
		JFormImageResponsive i = panelG1.AddItemImage("", "imagen1");
		i.setSource(JPssImage.SOURCE_SCRIPT);
		i.setHeight(480);

		JFormPanelResponsive row = AddItemRow();

		JFormFieldsetResponsive panelI = row.AddItemFieldset("Contrato", 6);
		// panelI.AddItemEdit( "Descripción", CHAR, OPT, "descripcion"
		// ).SetReadOnly(true);
		panelI.AddItemEdit("período", CHAR, OPT, "periodo_detalle").setSizeColumns(8).SetReadOnly(true);

		panelI.AddItemCombo(null, CHAR, OPT, "periodo", JWins.createVirtualWinsFromMap(BizDetalleBackendDobleRuta.getPeriodos())).setHide(true);
		panelI.AddItemCheck("Extra/Subobjetivo", OPT, "kicker").setSizeColumns(4).SetReadOnly(true);

//		panelI.AddItemEdit("FMS global/Pax expected", FLOAT, OPT, "fms_global").setSizeColumns(3).SetReadOnly(true);
//		panelI.AddItemEdit("Target Share gap", FLOAT, OPT, "sharegap_global").setSizeColumns(3).SetReadOnly(true);
//		panelI.AddItemEdit("Valor ref. global", FLOAT, OPT, "valor_global").setSizeColumns(3).SetReadOnly(true);
//		panelI.AddItemEdit("Reembolso", FLOAT, OPT, "valor_reembolso").setSizeColumns(3).SetReadOnly(true);

	  panelI.AddItemDateTime( "Fecha desde cálculo", DATE, OPT, "fecha_desde_calculo" ).SetValorDefault(getWin().GetcDato().getObjContrato().getFechaDesde()).setRefreshForm(true);
	  panelI.AddItemDateTime( "Fecha hasta cálculo", DATE, OPT, "fecha_calculo" ).SetValorDefault(getWin().GetcDato().getObjContrato().getFechaHasta()).setRefreshForm(true);

		JFormTabPanelResponsive tabs = row.AddItemTabPanel();
		tabs.setSizeColumns(6);

		JFormLista l = tabs.AddItemList(35);
		l.setToolBar(JBaseWin.TOOLBAR_NONE);
		l = tabs.AddItemList(30);
		l.setToolBar(JBaseWin.TOOLBAR_NONE);
		l = tabs.AddItemList(37);

	}

	@Override
	public void OnPostShow() throws Exception {
		checkControls("");
		super.OnPostShow();
	}
  private JWins getAerolineas(boolean one) throws Exception {
  	GuiCarriers g = new GuiCarriers();
  	if (one) {
  		g.getRecords().addFilter("carrier", getWin().GetcDato().getIdAerolinea());
  	} 
  	g.addOrderAdHoc("carrier", "ASC");
  	return g;
  }
@Override
public void checkControls(String sControlId) throws Exception {
	if (sControlId.equals("fecha_desde_calculo"))
		getWin().GetcDato().calcule(false);
	if (sControlId.equals("fecha_calculo"))
		getWin().GetcDato().calcule(false);	
	if (getWin().GetcDato().isNullFechaEmisionDesde()) findControl("fecha_emision_desde").hide();
	if (getWin().GetcDato().isNullFechaEmisionHasta()) findControl("fecha_emision_hasta").hide();
	if (getWin().GetcDato().isNullFechaVueloDesde()) findControl("fecha_vuelo_desde").hide();
	if (getWin().GetcDato().isNullFechaVueloHasta()) findControl("fecha_vuelo_hasta").hide();
	if (getWin().GetcDato().isNullFechaBlockoutDesde()) findControl("fecha_blockout_desde").hide();
	if (getWin().GetcDato().isNullFechaBlockoutHasta()) findControl("fecha_blockout_hasta").hide();
	if (!getWin().GetcDato().getAerolineas().equals("")) findControl("id_aerolinea").hide();
	if (getWin().GetcDato().getAerolineas().equals("")) findControl("aerolineas").hide();
	if (!getWin().GetcDato().getNoAerolineas().equals("")) findControl("no_aerolineas").hide();
	if (getWin().GetcDato().getAerolineasPlaca().equals("")) findControl("aerolineas_placa").hide();
	if (getWin().GetcDato().getIatas().equals("")) findControl("iatas").hide();
	if (getWin().GetcDato().getClasesIncluded().equals("")) findControl("class_included").hide();
	if (getWin().GetcDato().getClasesExcludes().equals("")) findControl("class_excluded").hide();
	if (getWin().GetcDato().getTourcodeIncludes().equals("")) findControl("tourcode_included").hide();
	if (getWin().GetcDato().getTourcodeExcludes().equals("")) findControl("tourcode_excluded").hide();
	if (getWin().GetcDato().getFareBasicIncludes().equals("")) findControl("farebasic_included").hide();
	if (getWin().GetcDato().getFareBasicExcludes().equals("")) findControl("farebasic_excluded").hide();
	if (getWin().GetcDato().getTipoPasajeroIncludes().equals("")) findControl("tipopasajero_included").hide();
	if (getWin().GetcDato().getTipoPasajeroExcludes().equals("")) findControl("tipopasajero_excluded").hide();
	if (getWin().GetcDato().getMercados().equals("")) findControl("mercados").hide();
	if (getWin().GetcDato().getNoMercados().equals("")) findControl("no_mercados").hide();
	if (getWin().GetcDato().getVuelos().equals("")) findControl("vuelos").hide();
	if (getWin().GetcDato().getNoVuelos().equals("")) findControl("no_vuelos").hide();
	if (getWin().GetcDato().getRutas().equals("")) findControl("rutas").hide();
	if (getWin().GetcDato().getOrigenConsolidado().equals("")) findControl("origen_consolidado").hide();
	if (getWin().GetcDato().getDestinoConsolidado().equals("")) findControl("destino_consolidado").hide();
	
	if (!getWin().GetcDato().isDomestic()&&!getWin().GetcDato().isInternational()) findControl("domestic").hide();
	if (!getWin().GetcDato().isYQ()) findControl("yq").hide();
	if (!getWin().GetcDato().isViceversa()) findControl("viceversa").hide();
	if (!getWin().GetcDato().isPrimera()) findControl("primera").hide();
	if (!getWin().GetcDato().isPax()) findControl("pax").hide();
	if (!getWin().GetcDato().isInterlineal()&&!getWin().GetcDato().isSoloUna()) findControl("interlineal").hide();

	super.checkControls(sControlId);
}

} // @jve:decl-index=0:visual-constraint="7,4"
