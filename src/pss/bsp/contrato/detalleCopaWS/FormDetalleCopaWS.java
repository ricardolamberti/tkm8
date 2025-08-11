package pss.bsp.contrato.detalleCopaWS;

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
import pss.bsp.event.GuiBSPSqlEvents;
import pss.common.event.sql.GuiSqlEvents;
import pss.common.security.BizUsuario;
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
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormFieldsetResponsive;
import pss.core.winUI.responsiveControls.JFormImageCardResponsive;
import pss.core.winUI.responsiveControls.JFormImageResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;
import pss.www.ui.JWebIcon;

public class FormDetalleCopaWS extends JBaseForm {


private static final long serialVersionUID = 1446860278358L;

/**
   * Constructor de la Clase
   */
  public FormDetalleCopaWS() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiDetalleCopaWS getWin() { return (GuiDetalleCopaWS) getBaseWin(); }

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
//    AddItem( variable, CHAR, OPT, "variable" );
//    AddItem( variable1, CHAR, OPT, "variable_ganancia" );
//    AddItem( textField_3, CHAR, OPT, "periodo_detalle" ).SetReadOnly(true);
//       
//    JFormControl c=AddItem( getJCheckBox(), OPT,"acumulativo");
//    c.SetValorDefault(false);
//    AddItem( getJComboBox(), CHAR, OPT, "periodo" , JWins.createVirtualWinsFromMap(BizDetalleCopaWS.getPeriodos()));
////    AddItem( getValorActual(), FLOAT, OPT, "valor_actual" ).SetReadOnly(true);
//    AddItem( getValorActualFC(), FLOAT, OPT, "valor_fcontrato" ).SetReadOnly(true);
////    AddItem( getValorObjetivo(), FLOAT, OPT, "valor_total" ).setVisible(true);
//    AddItem( getValorObjetivoFC(), FLOAT, OPT, "valor_totalcontrato" ).setVisible(true);
//    AddItem( getEvalua(), CHAR, OPT, "conclusion" ).SetReadOnly(true);
//    AddItem( getJDescr(), CHAR, OPT, "descripcion" ).SetReadOnly(true);
////    AddItem( getNivel1(), CHAR, OPT, "nivel_alcanzado" ).SetReadOnly(true);
////    AddItem( getEvalua1(), FLOAT, OPT, "ganancia" ).SetReadOnly(true);
//    AddItem( getNivel2(), CHAR, OPT, "nivel_alcanzado_estimada" ).SetReadOnly(true);
//    AddItem( getEvalua2(), FLOAT, OPT, "ganancia_estimada" ).SetReadOnly(true);
//    JFormImage i=AddItem( getJTextArea(), "imagen1" );
//    i.setSource(JPssImage.SOURCE_SCRIPT);
//
//
//    i=AddItem( getJTextArea1(), "imagen2" );
//    i.setSource(JPssImage.SOURCE_SCRIPT);
//
//    AddItem(getJCheckBox1(),OPT,"kicker");
//    JFormLista l=AddItem(getJTabbedPane(),36);
//    l=AddItem(getJTabbedPane(),35);
//    
		setAutoRefresh(true, 10000, "DASHBOARD");
		AddItemEdit( null, CHAR, REQ, "company" ).setHide(true).SetValorDefault(BizUsuario.getUsr().getCompany());
    AddItemEdit( null, UINT, OPT, "id" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "linea" ).setHide(true);
   
    AddItemEdit( null, CHAR, REQ, "variable_ganancia").setHide(true);
    
    JFormControl c=AddItemCheck( null, OPT,"acumulativo").setHide(true).SetValorDefault(false);

//    JFormImageCardResponsive ic = null;
//    ic = AddImageCard("Tickets Prorrateo" , JWebIcon.getResponsiveIcon("fa fa-ticket-alt fa-3x"), "Tickets prorrateo", 200);
//    if (ic!=null) ic.setResponsiveClass("panel-red").setSizeColumns(2);
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
//    ic = AddImageCard("Como se calcula objetivo?"    , JWebIcon.getResponsiveIcon("fa fa-question-circle fa-3x"), "Como se calcula objetvo?", 111);
//    if (ic!=null) ic.setResponsiveClass("panel-green").setSizeColumns(2);
//    ic = AddImageCard("Extra Info?"    , JWebIcon.getResponsiveIcon("fa fa-info-circle fa-3x"), "Mas información asociada?", 100);
//    if (ic!=null) ic.setResponsiveClass("panel-danger").setSizeColumns(2);

    JFormColumnResponsive column1 = AddItemColumn(6);
    JFormColumnResponsive column2 = AddItemColumn(6);

    JFormFieldsetResponsive panelR=column1.AddItemFieldset("Resultados",12);
//    JFormImageResponsive i=panelR.AddItemImage( "", "imagen2" );
//    i.setSource(JPssImage.SOURCE_SCRIPT);
// 
    
    panelR.AddItemEdit( "Evaluación al fin del contrato", CHAR, OPT, "conclusion" ).SetReadOnly(true);
    panelR.AddItemEdit( "Valor del indicador objetivo", FLOAT, OPT, "valor_fcontrato" ).setSizeColumns(6).SetReadOnly(true);
    panelR.AddItemEdit( "Valor Base comisionable", FLOAT, OPT, "valor_totalcontrato" ).setSizeColumns(6).setVisible(true).SetReadOnly(true);
    panelR.AddItemEdit( "Nivel alcanzado", CHAR, OPT, "nivel_alcanzado_estimada" ).setSizeColumns(6).SetReadOnly(true);
    panelR.AddItemEdit( "Ganancia/Comisión", FLOAT, OPT, "ganancia_estimada" ).setSizeColumns(6).SetReadOnly(true);
    panelR.AddItemEdit( "Valor auxiliar", FLOAT, OPT, "ganancia_auxiliar" ).setSizeColumns(6).SetReadOnly(true);

    JFormFieldsetResponsive panelG1=column2.AddItemFieldset("Gráficas",12);
    panelG1.setBackground("#e6e6fa");
    JFormImageResponsive i=panelG1.AddItemImage("", "imagen1" );
    i.setSource(JPssImage.SOURCE_SCRIPT);
    i.setHeight(480);

    
    JFormFieldsetResponsive panelI=AddItemFieldset("Contrato",6);
//  panelI.AddItemEdit( "Descripción", CHAR, OPT, "descripcion" ).SetReadOnly(true);
	  panelI.AddItemEdit( "período", CHAR, OPT, "periodo_detalle" ).setSizeColumns(8).SetReadOnly(true);
	
	  panelI.AddItemCombo( null, CHAR, OPT, "periodo" , JWins.createVirtualWinsFromMap(BizDetalleBackendDobleRuta.getPeriodos())).setHide(true);
	  panelI.AddItemCheck("Extra/Subobjetivo",OPT,"kicker").setSizeColumns(4).SetReadOnly(true);

	  panelI.AddItemEdit( "FMS global/Pax expected", FLOAT, OPT, "fms_global" ).setSizeColumns(3).SetReadOnly(true);
	  panelI.AddItemEdit( "Target Share gap", FLOAT, OPT, "sharegap_global" ).setSizeColumns(3).SetReadOnly(true);
	  panelI.AddItemEdit( "Valor ref. global", FLOAT, OPT, "valor_global" ).setSizeColumns(3).SetReadOnly(true);
	  panelI.AddItemEdit( "Reembolso", FLOAT, OPT, "valor_reembolso" ).setSizeColumns(3).SetReadOnly(true);

	  panelI.AddItemDateTime( "Fecha desde cálculo", DATE, OPT, "fecha_desde_calculo" ).SetValorDefault(getWin().GetcDato().getObjContrato().getFechaDesde()).setRefreshForm(true);
	  panelI.AddItemDateTime( "Fecha hasta cálculo", DATE, OPT, "fecha_calculo" ).SetValorDefault(getWin().GetcDato().getObjContrato().getFechaHasta()).setRefreshForm(true);


    
  


    JFormTabPanelResponsive tabs = AddItemTabPanel();
    tabs.setSizeColumns(6);
    
    JFormLista l= tabs.AddItemList(36);
   l= tabs.AddItemList(35);
 
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
