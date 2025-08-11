package pss.bsp.contrato.detalleObjetivo;

import pss.bsp.contrato.detalleDatamining.BizDetalleDatamining;
import pss.common.security.BizUsuario;
import pss.core.ui.components.JPssImage;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormLista;
import pss.core.winUI.controls.JFormLocalForm;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormEditResponsive;
import pss.core.winUI.responsiveControls.JFormFieldsetResponsive;
import pss.core.winUI.responsiveControls.JFormImageCardResponsive;
import pss.core.winUI.responsiveControls.JFormImageResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;
import pss.www.ui.JWebIcon;

public class FormDetalleObjetivo extends JBaseForm {


private static final long serialVersionUID = 1446860278358L;

/**
   * Constructor de la Clase
   */
  public FormDetalleObjetivo() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiDetalleObjetivo getWin() { return (GuiDetalleObjetivo) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {

  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
		setAutoRefresh(true, 10000, "DASHBOARD");
		AddItemEdit( null, CHAR, REQ, "company" ).setHide(true).SetValorDefault(BizUsuario.getUsr().getCompany());
    AddItemEdit( null, UINT, OPT, "id" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "linea" ).setHide(true);
   
    AddItemEdit( null, CHAR, REQ, "variable" ).setHide(true);
    AddItemEdit( null, CHAR, REQ, "variable_ganancia").setHide(true);
    
    JFormControl c=AddItemCheck( null, OPT,"acumulativo").setHide(true).SetValorDefault(false);


    JFormPanelResponsive botonera = AddItemRow();
    JFormImageCardResponsive ic = null;
    ic = botonera.AddImageCard("Tickets Objetivo" , JWebIcon.getResponsiveIcon("fa fa-ticket-alt fa-3x"), "Tickets Objetivo", 50);
    if (ic!=null) ic.setResponsiveClass("panel-green").setSizeColumns(2);
    ic = botonera.AddImageCard("Como se calcula objetivo?"    , JWebIcon.getResponsiveIcon("fa fa-question-circle fa-3x"), "Como se calcula objetvo?", 111);
    if (ic!=null) ic.setResponsiveClass("panel-green").setSizeColumns(2);
    ic = botonera.AddImageCard("Extra Info?"    , JWebIcon.getResponsiveIcon("fa fa-info-circle fa-3x"), "Mas información asociada?", 100);
    if (ic!=null) ic.setResponsiveClass("panel-danger").setSizeColumns(2);
   	if (getWin().GetcDato().hasConsultaReservaMeta())
   		botonera.AddInfoCard("Reservas Objetivo", CHAR, "reservas_meta", JWebIcon.getResponsiveIcon("fa fa-bookmark fa-3x"),"Reservas para objetivo", 600).setResponsiveClass("border-left-success shadow ").setSizeColumns(2);
   	if (getWin().GetcDato().hasConsultaReservaBase())
   		botonera.AddInfoCard("Reservas B.Comis", CHAR, "reservas_base", JWebIcon.getResponsiveIcon("fa fa-bookmark fa-3x"),"Reservas para B.Comis.", 601).setResponsiveClass("border-left-success shadow ").setSizeColumns(2);
 
    JFormColumnResponsive column1 = AddItemColumn(6);
    JFormColumnResponsive column2 = AddItemColumn(6);
  
    JFormFieldsetResponsive panelR=column1.AddItemFieldset("Resultados",12);
    JFormImageResponsive i=panelR.AddItemImage( "", "imagen2" );
    i.setSource(JPssImage.SOURCE_SCRIPT);
    panelR.AddItemEdit( "Evaluación al fin del objetivo", CHAR, OPT, "conclusion" ).SetReadOnly(true);
    JFormEditResponsive s = panelR.AddItemEdit( "Valor del indicador objetivo", FLOAT, OPT, "valor_fcontrato" );
    s.setSizeColumns(6).SetReadOnly(true);
    s.setSkinStereotype("big_label");
    s.setFontSize(25);
    s.setFontWeigth(JFormEditResponsive.FONT_WEIGHT_BOLD);
    panelR.AddItemEdit( "Nivel alcanzado", CHAR, OPT, "nivel_alcanzado_estimada" ).setSizeColumns(6).SetReadOnly(true);

    if (!getWin().GetcDato().getDescrSqlAux1Meta().equals(""))
    	panelR.AddItemEdit( getWin().GetcDato().getDescrSqlAux1Meta(), CHAR, OPT, "sql_aux1_meta" ).setSizeColumns(6).right().SetReadOnly(true);
    if (!getWin().GetcDato().getDescrSqlAux2Meta().equals(""))
       panelR.AddItemEdit( getWin().GetcDato().getDescrSqlAux2Meta(), CHAR, OPT, "sql_aux2_meta" ).setSizeColumns(6).right().SetReadOnly(true);

    if (!getWin().GetcDato().getDescrSqlAux1Base().equals(""))
       panelR.AddItemEdit( getWin().GetcDato().getDescrSqlAux1Base(), CHAR, OPT, "sql_aux1_base" ).setSizeColumns(6).right().SetReadOnly(true);
    if (!getWin().GetcDato().getDescrSqlAux2Base().equals(""))
      panelR.AddItemEdit( getWin().GetcDato().getDescrSqlAux2Base(), CHAR, OPT, "sql_aux2_base" ).setSizeColumns(6).right().SetReadOnly(true);


    JFormTabPanelResponsive tabsI = column2.AddItemTabPanel();
    JFormLocalForm tab1 = tabsI.AddItemLocalForm("Meta");
    JFormPanelResponsive panelG1 = tab1.getRootPanel();
    //JFormLocalForm panelG1=column2.AddItemFieldset("Gráficas",12);
    panelG1.setBackground("#e6e6fa");
    i=panelG1.AddItemImage("", "imagen1" );
    i.setHeight(680);
    i.setSource(JPssImage.SOURCE_SCRIPT);
    i.setSizeColumns(10);
    
    //JFormFieldsetResponsive panelG2=column2.AddItemFieldset("Gráficas Base comisionable",12);
    
   ;
    
    JFormPanelResponsive row = AddItemRow();
    JFormFieldsetResponsive panelI=row.AddItemFieldset("Contrato",6);
	  panelI.AddItemEdit( "período", CHAR, OPT, "periodo_detalle" ).setSizeColumns(8).SetReadOnly(true);
	
	  panelI.AddItemCombo( null, CHAR, OPT, "periodo" , JWins.createVirtualWinsFromMap(BizDetalleDatamining.getPeriodos())).setHide(true);
	  panelI.AddItemCheck("Extra/Subobjetivo",OPT,"kicker").setSizeColumns(4).SetReadOnly(true);

	  panelI.AddItemEdit( "FMS global/Pax expected", FLOAT, OPT, "fms_global" ).setSizeColumns(3).SetReadOnly(true);
	  panelI.AddItemEdit( "Target Share gap", FLOAT, OPT, "sharegap_global" ).setSizeColumns(3).SetReadOnly(true);
	  panelI.AddItemEdit( "Valor ref. global", FLOAT, OPT, "valor_global" ).setSizeColumns(3).SetReadOnly(true);
	  panelI.AddItemEdit( "Reembolso", FLOAT, OPT, "valor_reembolso" ).setSizeColumns(3).SetReadOnly(true);

	  panelI.AddItemDateTime( "Fecha desde cálculo", DATE, OPT, "fecha_desde_calculo" ).SetValorDefault(getWin().GetcDato().getObjContrato().getFechaDesde()).setRefreshForm(true);
	  panelI.AddItemDateTime( "Fecha hasta cálculo", DATE, OPT, "fecha_calculo" ).SetValorDefault(getWin().GetcDato().getObjContrato().getFechaHasta()).setRefreshForm(true);


    
 



    JFormTabPanelResponsive tabs = row.AddItemTabPanel();
    tabs.setSizeColumns(6);
    
    JFormLista l= tabs.AddItemList(35);
    l= tabs.AddItemList(30);
    l.setToolBar(JBaseWin.TOOLBAR_NONE);

  }
  @Override
  	public void OnPostShow() throws Exception {
  		checkControls("");
  		super.OnPostShow();
  	}

  @Override
  	public void checkControls(String sControlId) throws Exception {
  		if (sControlId.equals("variable")) {
  			getWin().GetcDato().clean();
  		}
   		if (sControlId.equals("fecha_desde_calculo"))
  			getWin().GetcDato().calcule(false);
   		if (sControlId.equals("fecha_calculo"))
  			getWin().GetcDato().calcule(false);
  		getControles().BaseToControl(GetModo(), true);	
  		if (getWin().GetccDato().needFMSGLobal()) {
  			getControles().findControl("fms_global").edit();
  			getControles().findControl("fms_global").disable();
 // 			pssLabel.setVisible(true);
  		} else {
  			getControles().findControl("fms_global").hide();
  	//		pssLabel.setVisible(false);
  		}
   		if (getWin().GetccDato().needShareGapGLobal()) {
  			getControles().findControl("sharegap_global").edit();
  			getControles().findControl("sharegap_global").disable();
  		//	ShareGapGLobal.setVisible(true);
  		} else {
  			getControles().findControl("sharegap_global").hide();
  			//ShareGapGLobal.setVisible(false);
  		}
   		if (getWin().GetccDato().needValorRefGLobal()) {
  			getControles().findControl("valor_global").edit();
  			getControles().findControl("valor_global").disable();
  			//pssLabel_1.setVisible(true);
  		} else {
  			getControles().findControl("valor_global").hide();
  			//pssLabel_1.setVisible(false);
  		}
//  		if (GetControles().findControl("acumulativo").getValue().equals("S")) {
//  			GetControles().findControl("periodo").edit(GetModo());
//  			//GetControles().findControl("valor").edit(GetModo());
//  		} else {
//  			GetControles().findControl("periodo").disable();
//  			//GetControles().findControl("valor").disable();
//  			GetControles().findControl("periodo").setValue("");
//  			//GetControles().findControl("valor").setValue(""+getWin().GetcDato().getValor());
//  		}
//  		if (sControlId.equals("")) {
//  			getWin().GetcDato().calcule();
//  			this.GetControles().findControl("valor").setValue(""+getWin().GetcDato().getObjEvent().getValor());
//  		}
  		super.checkControls(sControlId);
  	}

}  //  @jve:decl-index=0:visual-constraint="7,4"
