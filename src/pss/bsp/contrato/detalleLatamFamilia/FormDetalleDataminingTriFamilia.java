package pss.bsp.contrato.detalleLatamFamilia;

import pss.bsp.contrato.detalleDatamining.BizDetalleDatamining;
import pss.bsp.event.GuiBSPSqlEvents;
import pss.common.event.sql.GuiSqlEvents;
import pss.common.security.BizUsuario;
import pss.core.ui.components.JPssImage;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormLista;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormFieldsetResponsive;
import pss.core.winUI.responsiveControls.JFormImageCardResponsive;
import pss.core.winUI.responsiveControls.JFormImageResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;
import pss.www.ui.JWebIcon;

public class FormDetalleDataminingTriFamilia extends JBaseForm {


private static final long serialVersionUID = 1446860278358L;

/**
   * Constructor de la Clase
   */
  public FormDetalleDataminingTriFamilia() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiDetalleDataminingTriFamilia getWin() { return (GuiDetalleDataminingTriFamilia) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
   
 
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
//		setAutoRefresh(true, 10000, "DASHBOARD");
//		AddItem( company, CHAR, REQ, "company" ).SetValorDefault(BizUsuario.getUsr().getCompany());
//    AddItem( id, UINT, OPT, "id" );
//    AddItem( linea, UINT, OPT, "linea" );
//    AddItem( textField, CHAR, OPT, "periodo_detalle" ).SetReadOnly(true);;
//    AddItem( variable, CHAR, REQ, "variable" , new JControlCombo() {
//    	@Override
//    	public JWins getRecords(boolean one) throws Exception {
//    		return getIndicador(one);
//    	}
//    }).setRefreshForm(true).SetReadOnly(true);;
//    AddItem( variable1, CHAR, REQ, "variable_ganancia" , new JControlCombo() {
//    	@Override
//    	public JWins getRecords(boolean one) throws Exception {
//    		return getIndicadorGanancia(one);
//    	}
//    }).setRefreshForm(true).SetReadOnly(true);;
//    AddItem( comboBox, CHAR, REQ, "variable_aux" , new JControlCombo() {
//    	@Override
//    	public JWins getRecords(boolean one) throws Exception {
//    		return getIndicadorAuxiliar(one);
//    	}
//    }).setRefreshForm(true).SetReadOnly(true);;
//    
//    JFormControl c=AddItem( getJCheckBox(), OPT,"acumulativo");
//    c.SetValorDefault(false);
//    c.SetReadOnly(true);
////    c.setRefreshForm(true);
//    AddItem( getJComboBox(), CHAR, OPT, "periodo" , JWins.createVirtualWinsFromMap(BizDetalleDataminingTriFamilia.getPeriodos())).SetReadOnly(true);;
////    AddItem( getValorActual(), FLOAT, OPT, "valor_actual" ).SetReadOnly(true);
//    AddItem( getValorActualFC(), FLOAT, OPT, "valor_fcontrato" ).SetReadOnly(true);
////    AddItem( getValorObjetivo(), FLOAT, OPT, "valor_total" ).setVisible(true);
//    AddItem( getValorObjetivoFC(), FLOAT, OPT, "valor_totalcontrato" ).SetReadOnly(true);
//    AddItem( getEvalua(), CHAR, OPT, "conclusion" ).SetReadOnly(true);
//    AddItem( getJDescr(), CHAR, OPT, "descripcion" ).SetReadOnly(true);
////    AddItem( getNivel1(), CHAR, OPT, "nivel_alcanzado" ).SetReadOnly(true);
////    AddItem( getEvalua1(), FLOAT, OPT, "ganancia" ).SetReadOnly(true);
//    AddItem( getNivel2(), CHAR, OPT, "nivel_alcanzado_estimada" ).SetReadOnly(true);
//    AddItem( getEvalua2(), FLOAT, OPT, "ganancia_estimada" ).SetReadOnly(true);
//    AddItem( pssEdit, FLOAT, OPT, "ganancia_auxiliar" ).SetReadOnly(true);
//
//    AddItem( FMSGlobal, FLOAT, OPT, "fms_global" ).SetReadOnly(true);;
//    AddItem( pssSharegapglobal, FLOAT, OPT, "sharegap_global" ).SetReadOnly(true);;
//    AddItem( pssValorRegGlobal, FLOAT, OPT, "valor_global" ).SetReadOnly(true);;
//    AddItem( pssValorReembolso, FLOAT, OPT, "valor_reembolso" ).SetReadOnly(true);;
//    AddItem( pssEdit_1, FLOAT, OPT, "ponderado" ).SetReadOnly(true);;
//    
//    
//    JFormImage i=AddItem( getJTextArea(), "imagen1" );
//    i.setSource(JPssImage.SOURCE_SCRIPT);
//
//    i.setKeepHeight(true);
//    i.setKeepWidth(true);
//
//    i=AddItem( getJTextArea1(), "imagen2" );
//    i.setSource(JPssImage.SOURCE_SCRIPT);
//
//    AddItem(getJCheckBox1(),OPT,"kicker");
//    JFormLista l=AddItem(getJTabbedPane(),35);
////    l.setKeepHeight(false);
//    l.setKeepWidth(false);
////    l.setToolBar(false);
//    l=AddItem(getJTabbedPane(),30);
////    l.setKeepHeight(false);
//    l.setKeepWidth(false);
//    l.setToolBar(JBaseWin.TOOLBAR_NONE);
////    l=AddItem(getJTabbedPane(),50);
////    l.setKeepHeight(false);
////    l.setKeepWidth(false);
//    l=AddItem(getJTabbedPane(),190);
//    
//    JFormImageCardResponsive ic = null;
//    ic = AddImageCard("Tickets Objetivo" , JWebIcon.getResponsiveIcon("fa fa-ticket-alt fa-3x"), "Tickets Objetivo", 50);
//    if (ic!=null) ic.setResponsiveClass("panel-green").setSizeColumns(2);
//    ic = AddImageCard("Tickets B.Comis." , JWebIcon.getResponsiveIcon("fa fa-ticket-alt fa-3x"), "Tickets B.Comis.", 60);
//    if (ic!=null) ic.setResponsiveClass("panel-yellow").setSizeColumns(2);
//    ic = AddImageCard("Tickets Aux."     , JWebIcon.getResponsiveIcon("fa fa-ticket-alt fa-3x"), "Tickets Aux.", 65);
//    if (ic!=null) ic.setResponsiveClass("panel-primary").setSizeColumns(2);
//    ic = AddImageCard("Ver ind. Objetivo", JWebIcon.getResponsiveIcon("fa fa-chart-bar fa-3x"), "Ver ind. Objetivo", 10);
//    if (ic!=null) ic.setResponsiveClass("panel-green").setSizeColumns(2);
//    ic = AddImageCard("Ver ind. B.Comis.", JWebIcon.getResponsiveIcon("fa fa-chart-bar fa-3x"), "Ver ind. B.Comis.", 15);
//    if (ic!=null) ic.setResponsiveClass("panel-yellow").setSizeColumns(2);
//    ic = AddImageCard("Ver ind. Aux."    , JWebIcon.getResponsiveIcon("fa fa-chart-bar fa-3x"), "Ver ind. Aux.", 17);
//    if (ic!=null) ic.setResponsiveClass("panel-primary").setSizeColumns(2);
//    ic = AddImageCard("Como se calcula objetvo?"    , JWebIcon.getResponsiveIcon("fa fa-question-circle fa-3x"), "Como se calcula objetvo?", 110);
//    if (ic!=null) ic.setResponsiveClass("panel-green").setSizeColumns(2);
//    ic = AddImageCard("Como se calcula b.comisionable?"    , JWebIcon.getResponsiveIcon("fa fa-question-circle fa-3x"), "Como se calcula objetvo?", 120);
//    if (ic!=null) ic.setResponsiveClass("panel-yellow").setSizeColumns(2);
//    ic = AddImageCard("Como se calcula Auxiliar?"    , JWebIcon.getResponsiveIcon("fa fa-question-circle fa-3x"), "Como se calcula objetvo?", 130);
//    if (ic!=null) ic.setResponsiveClass("panel-primary").setSizeColumns(2);

		setAutoRefresh(true, 10000, "DASHBOARD");
		AddItemEdit( null, CHAR, REQ, "company" ).setHide(true).SetValorDefault(BizUsuario.getUsr().getCompany());
    AddItemEdit( null, UINT, OPT, "id" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "linea" ).setHide(true);
   
    AddItemEdit( null, CHAR, REQ, "variable" ).setHide(true);
    AddItemEdit( null, CHAR, REQ, "variable_ganancia").setHide(true);
    
    JFormControl c=AddItemCheck( null, OPT,"acumulativo").setHide(true).SetValorDefault(false);


    JFormImageCardResponsive ic = null;
    ic = AddImageCard("Tickets Objetivo" , JWebIcon.getResponsiveIcon("fa fa-ticket-alt fa-3x"), "Tickets Objetivo", 50);
    if (ic!=null) ic.setResponsiveClass("panel-green").setSizeColumns(2);
    ic = AddImageCard("Tickets B.Comis." , JWebIcon.getResponsiveIcon("fa fa-ticket-alt fa-3x"), "Tickets B.Comis.", 60);
    if (ic!=null) ic.setResponsiveClass("panel-yellow").setSizeColumns(2);
    ic = AddImageCard("Tickets Aux."     , JWebIcon.getResponsiveIcon("fa fa-ticket-alt fa-3x"), "Tickets Aux.", 65);
    if (ic!=null) ic.setResponsiveClass("panel-primary").setSizeColumns(2);
    ic = AddImageCard("Ver ind. Objetivo", JWebIcon.getResponsiveIcon("fa fa-chart-bar fa-3x"), "Ver ind. Objetivo", 10);
    if (ic!=null) ic.setResponsiveClass("panel-green").setSizeColumns(2);
    ic = AddImageCard("Ver ind. B.Comis.", JWebIcon.getResponsiveIcon("fa fa-chart-bar fa-3x"), "Ver ind. B.Comis.", 15);
    if (ic!=null) ic.setResponsiveClass("panel-yellow").setSizeColumns(2);
    ic = AddImageCard("Ver ind. Aux."    , JWebIcon.getResponsiveIcon("fa fa-chart-bar fa-3x"), "Ver ind. Aux.", 17);
    if (ic!=null) ic.setResponsiveClass("panel-primary").setSizeColumns(2);
    ic = AddImageCard("Como se calcula objetvo?"    , JWebIcon.getResponsiveIcon("fa fa-question-circle fa-3x"), "Como se calcula objetvo?", 110);
    if (ic!=null) ic.setResponsiveClass("panel-green").setSizeColumns(2);
    ic = AddImageCard("Como se calcula b.comisionable?"    , JWebIcon.getResponsiveIcon("fa fa-question-circle fa-3x"), "Como se calcula objetvo?", 120);
    if (ic!=null) ic.setResponsiveClass("panel-yellow").setSizeColumns(2);
    ic = AddImageCard("Como se calcula Auxiliar?"    , JWebIcon.getResponsiveIcon("fa fa-question-circle fa-3x"), "Como se calcula objetvo?", 130);
    if (ic!=null) ic.setResponsiveClass("panel-primary").setSizeColumns(2);

    JFormColumnResponsive column1 = AddItemColumn(6);
    JFormColumnResponsive column2 = AddItemColumn(6);

    JFormFieldsetResponsive panelR=column1.AddItemFieldset("Resultados",12);
    JFormImageResponsive i=panelR.AddItemImage( "", "imagen2" );
    i.setSource(JPssImage.SOURCE_SCRIPT);
    panelR.AddItemEdit( "Evaluación al fin del contrato", CHAR, OPT, "conclusion" ).SetReadOnly(true);
    panelR.AddItemEdit( "Valor del indicador objetivo", FLOAT, OPT, "valor_fcontrato" ).setSizeColumns(6).SetReadOnly(true);
    panelR.AddItemEdit( "Valor Base comisionable", FLOAT, OPT, "valor_totalcontrato" ).setSizeColumns(6).setVisible(true).SetReadOnly(true);
    panelR.AddItemEdit( "Nivel alcanzado", CHAR, OPT, "nivel_alcanzado_estimada" ).setSizeColumns(6).SetReadOnly(true);
    panelR.AddItemEdit( "Ganancia/Comisión", FLOAT, OPT, "ganancia_estimada" ).setSizeColumns(6).SetReadOnly(true);
    panelR.AddItemEdit( "Ganancia Auxiliar", FLOAT, OPT, "ganancia_auxiliar" ).setSizeColumns(6).SetReadOnly(true);

    JFormFieldsetResponsive panelG1=column2.AddItemFieldset("Gráficas",12);
    panelG1.setBackground("#e6e6fa");
    i=panelG1.AddItemImage("", "imagen1" );
    i.setHeight(680);
    i.setSource(JPssImage.SOURCE_SCRIPT);
    i.setSizeColumns(10);
    
    JFormFieldsetResponsive panelI=AddItemFieldset("Contrato",6);
	  panelI.AddItemEdit( "período", CHAR, OPT, "periodo_detalle" ).setSizeColumns(8).SetReadOnly(true);
	
	  panelI.AddItemCombo( null, CHAR, OPT, "periodo" , JWins.createVirtualWinsFromMap(BizDetalleDatamining.getPeriodos())).setHide(true);
	  panelI.AddItemCheck("Extra/Subobjetivo",OPT,"kicker").setSizeColumns(4).SetReadOnly(true);
	  panelI.AddItemCombo( "Indicador Objetivo", CHAR, REQ, "variable" , new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getIndicador(one);
    	}
    }).setRefreshForm(true).SetReadOnly(true);
	  panelI.AddItemCombo( "Indicador base com.", CHAR, REQ, "variable_ganancia" , new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getIndicadorGanancia(one);
    	}
    }).setRefreshForm(true).SetReadOnly(true);
	  panelI.AddItemCombo( "Indicador Auxiliar", CHAR, REQ, "variable_aux" , new JControlCombo() {
			@Override
			public JWins getRecords(boolean one) throws Exception {
				return getIndicadorAuxiliar(one);
			}
		}).setRefreshForm(true).SetReadOnly(true);;
	  panelI.AddItemEdit( "FMS global/Pax expected", FLOAT, OPT, "fms_global" ).setSizeColumns(3).SetReadOnly(true);
	  panelI.AddItemEdit( "Target Share gap", FLOAT, OPT, "sharegap_global" ).setSizeColumns(3).SetReadOnly(true);
	  panelI.AddItemEdit( "Valor ref. global", FLOAT, OPT, "valor_global" ).setSizeColumns(3).SetReadOnly(true);
	  panelI.AddItemEdit( "Reembolso", FLOAT, OPT, "valor_reembolso" ).setSizeColumns(3).SetReadOnly(true);

	  panelI.AddItemDateTime( "Fecha desde cálculo", DATE, OPT, "fecha_desde_calculo" ).SetValorDefault(getWin().GetcDato().getObjContrato().getFechaDesde()).setRefreshForm(true);
	  panelI.AddItemDateTime( "Fecha hasta cálculo", DATE, OPT, "fecha_calculo" ).SetValorDefault(getWin().GetcDato().getObjContrato().getFechaHasta()).setRefreshForm(true);

    
 



    JFormTabPanelResponsive tabs = AddItemTabPanel();
    tabs.setSizeColumns(6);
    
    JFormLista l= tabs.AddItemList(35);
		l=tabs.AddItemList(30);
		l.setToolBar(JBaseWin.TOOLBAR_NONE);
		l=tabs.AddItemList(190);

   }
  @Override
  	public void OnPostShow() throws Exception {
  		checkControls("");
  		super.OnPostShow();
  	}

  private JWins getIndicador(boolean one) throws Exception {
  	GuiSqlEvents g = new GuiBSPSqlEvents();
  	if (one) {
  		g.getRecords().addFilter("id", getWin().GetcDato().getVariable());
  	} else {
  		g.getRecords().addFilter("company", getControles().findControl("company").getValue());
  	}
  	return g;
  }
  private JWins getIndicadorGanancia(boolean one) throws Exception {
  	GuiSqlEvents g = new GuiBSPSqlEvents();
  	if (one) {
  		g.getRecords().addFilter("id", getWin().GetcDato().getVariableGanancia());
  	} else {
  		g.getRecords().addFilter("company", getControles().findControl("company").getValue());
  	}
  	return g;
  }
  private JWins getIndicadorAuxiliar(boolean one) throws Exception {
  	GuiSqlEvents g = new GuiBSPSqlEvents();
  	if (one) {
  		g.getRecords().addFilter("id", getWin().GetcDato().getVariableAuxiliar());
  	} else {
  		g.getRecords().addFilter("company", getControles().findControl("company").getValue());
  	}
  	return g;
  }

  @Override
  	public void checkControls(String sControlId) throws Exception {
  		if (sControlId.equals("variable")) {
  			getWin().GetcDato().clean();
  		}
  		
  		if (getWin().GetccDato().needFMSGLobal()) {
  			getControles().findControl("fms_global").edit();
  			getControles().findControl("fms_global").disable();
//  			pssLabel.setVisible(true);
  		} else {
  			getControles().findControl("fms_global").hide();
//  			pssLabel.setVisible(false);
  		}
   		if (getWin().GetccDato().needShareGapGLobal()) {
  			getControles().findControl("sharegap_global").edit();
  			getControles().findControl("sharegap_global").disable();
//  			ShareGapGLobal.setVisible(true);
  		} else {
  			getControles().findControl("sharegap_global").hide();
//  			ShareGapGLobal.setVisible(false);
  		}
   		if (getWin().GetccDato().needValorRefGLobal()) {
  			getControles().findControl("valor_global").edit();
  			getControles().findControl("valor_global").disable();
//  			pssLabel_1.setVisible(true);
  		} else {
  			getControles().findControl("valor_global").hide();
//  			pssLabel_1.setVisible(false);
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
