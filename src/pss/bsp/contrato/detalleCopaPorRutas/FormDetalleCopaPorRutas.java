package pss.bsp.contrato.detalleCopaPorRutas;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import pss.bsp.contrato.detalleDatamining.BizDetalleDatamining;
import pss.bsp.contrato.detalleRutas.BizDetalleRuta;
import pss.bsp.event.GuiBSPSqlEvents;
import pss.common.event.sql.GuiSqlEvents;
import pss.common.security.BizUsuario;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssImage;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.win.JWins;
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

public class FormDetalleCopaPorRutas extends JBaseForm {

  public  FormDetalleCopaPorRutas() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }
private static final long serialVersionUID = 1446860278358L;

 
/**
   * Constructor de la Clase
   */


  public GuiDetalleCopaPorRutas getWin() { return (GuiDetalleCopaPorRutas) getBaseWin(); }

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
//    AddItem( id, UINT, OPT, "id" ).SetReadOnly(true);
//    AddItem( linea, UINT, OPT, "linea" ).SetReadOnly(true);
//    AddItem( variable, CHAR, OPT, "variable" ).SetReadOnly(true);
//    AddItem( variable1, CHAR, OPT, "variable_ganancia" ).SetReadOnly(true);
//    AddItem( textFieldb, CHAR, OPT, "fms_max" ).SetReadOnly(true);
//    AddItem( textFieldb_1, CHAR, OPT, "fms_min" ).SetReadOnly(true);
//    AddItem( textFieldb_2, CHAR, OPT, "Limite" ).SetReadOnly(true);
//    AddItem( textField_3, CHAR, OPT, "periodo_detalle" ).SetReadOnly(true);
//       
//    JFormControl c=AddItem( getJCheckBox(), OPT,"acumulativo");
//    c.SetValorDefault(false);
//    AddItem( getJComboBox(), CHAR, OPT, "periodo" , JWins.createVirtualWinsFromMap(BizDetalleRuta.getPeriodos()));
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
//    JFormImage i=AddItem( getJTextArea(), "imagen1" );
//    i.setSource(JPssImage.SOURCE_SCRIPT);


//    i=AddItem( getJTextArea1(), "imagen2" );
//    i.setSource(JPssImage.SOURCE_SCRIPT);
//
//    AddItem(getJCheckBox1(),OPT,"kicker").SetReadOnly(true);
//    JFormLista l=AddItem(getJTabbedPane(),38);
//    l=AddItem(getJTabbedPane(),35);
//    
//    JFormImageCardResponsive ic = null;
//    ic = AddImageCard("Tickets B.Comis." , JWebIcon.getResponsiveIcon("fa fa-ticket-alt fa-3x"), "Tickets B.Comis.", 60);
//    if (ic!=null) ic.setResponsiveClass("panel-yellow").setSizeColumns(2);
//   
    
		setAutoRefresh(true, 10000, "DASHBOARD");
		AddItemEdit( null, CHAR, REQ, "company" ).setHide(true).SetValorDefault(BizUsuario.getUsr().getCompany());
    AddItemEdit( null, UINT, OPT, "id" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "linea" ).setHide(true);
   
    AddItemEdit( null, CHAR, REQ, "variable" ).setHide(true);
    AddItemEdit( null, CHAR, REQ, "variable_ganancia").setHide(true);
    
    JFormControl c=AddItemCheck( null, OPT,"acumulativo").setHide(true).SetValorDefault(false);

    
    JFormImageCardResponsive ic = null;
    ic = AddImageCard("Tickets B.Comis." , JWebIcon.getResponsiveIcon("fa fa-ticket-alt fa-3x"), "Tickets B.Comis.", 60);
    if (ic!=null) ic.setResponsiveClass("panel-yellow").setSizeColumns(2);
    ic = AddItemRow().AddImageCard("Extra Info?"    , JWebIcon.getResponsiveIcon("fa fa-info-circle fa-3x"), "Mas información asociada?", 100);
    if (ic!=null) ic.setResponsiveClass("panel-danger").setSizeColumns(2);

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

//	  panelI.AddItemEdit( "FMS global/Pax expected", FLOAT, OPT, "fms_global" ).setSizeColumns(3).SetReadOnly(true);
//	  panelI.AddItemEdit( "Target Share gap", FLOAT, OPT, "sharegap_global" ).setSizeColumns(3).SetReadOnly(true);
//	  panelI.AddItemEdit( "Valor ref. global", FLOAT, OPT, "valor_global" ).setSizeColumns(3).SetReadOnly(true);
//	  panelI.AddItemEdit( "Reembolso", FLOAT, OPT, "valor_reembolso" ).setSizeColumns(3).SetReadOnly(true);
	  panelI.AddItemEdit( "FMS Max", FLOAT, OPT, "fms_max" ).setSizeColumns(3).SetReadOnly(true);
	  panelI.AddItemEdit( "FMS Min", FLOAT, OPT, "fms_min" ).setSizeColumns(3).SetReadOnly(true);
	  panelI.AddItemEdit( "Min.Cantidad", FLOAT, OPT, "Limite" ).setSizeColumns(3).SetReadOnly(true);


	  panelI.AddItemDateTime( "Fecha desde cálculo", DATE, OPT, "fecha_desde_calculo" ).SetValorDefault(getWin().GetcDato().getObjContrato().getFechaDesde()).setRefreshForm(true);
	  panelI.AddItemDateTime( "Fecha hasta cálculo", DATE, OPT, "fecha_calculo" ).SetValorDefault(getWin().GetcDato().getObjContrato().getFechaHasta()).setRefreshForm(true);


    
 



    JFormTabPanelResponsive tabs = AddItemTabPanel();
    tabs.setSizeColumns(6);
    
    JFormLista l= tabs.AddItemList(38);
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

