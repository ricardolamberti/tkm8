package  pss.bsp.contrato;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JCheckBox;
import javax.swing.JTabbedPane;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.common.security.BizUsuario;
import pss.core.ui.components.JPssCalendarEdit;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssImage;
import pss.core.ui.components.JPssLabel;
import pss.core.ui.components.JScrollableTextArea;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.winUI.controls.JFormLista;
import pss.core.winUI.controls.JFormRow;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormFieldsetResponsive;
import pss.core.winUI.responsiveControls.JFormImageCardResponsive;
import pss.core.winUI.responsiveControls.JFormImageResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormRowGridResponsive;
import pss.www.ui.JWebIcon;

public class FormContrato extends JBaseForm {


private static final long serialVersionUID = 1446860215215L;




  /**
   * Constructor de la Clase
   */
  public FormContrato() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiContrato getWin() { return (GuiContrato) getBaseWin(); }

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
		JFormPanelResponsive row1 = (JFormPanelResponsive)AddItemRow().setSizeColumns(12);
		JFormPanelResponsive row2 = (JFormPanelResponsive)AddItemRow().setSizeColumns(12);
/*		JFormColumnResponsive col1 = (JFormColumnResponsive)row1.AddItemColumn("col-xs-12 col-sm-4").setResponsiveClass("panel");
		JFormColumnResponsive col2 = (JFormColumnResponsive)row1.AddItemColumn("col-xs-12 col-sm-8").setResponsiveClass("panel");
		col1.setLabelRight();
    if (!getWin().GetcDato().isNew())
    	col1.AddItemCheck( "Estado", OPT, "active" ).setDescripcionValorCheck("Activo").setDescripcionValorUnCheck("Inactivo").setSizeColumns(12);
    else
    	col1.addButton("Activar contrato", 30).bold();

    JFormImageResponsive i= col1.AddItemImage("Cumplimiento", "imagen1" );
    i.setSource(JPssImage.SOURCE_SCRIPT);

    col2.AddItemEdit( "id", UINT, OPT, "id" ).setVisible(false);
    col2.AddItemEdit( "company", CHAR, OPT, "company" ).setVisible(false);
    col2.AddItemDateTime( "Fecha Desde",DATE, OPT, "fecha_desde" ).setSizeColumns(6);
    col2.AddItemDateTime( "Fecha Hasta",DATE, OPT, "fecha_hasta" ).setSizeColumns(6);
    if (!isConsulta())
    	col2.AddItemEdit( "Descripción", CHAR, REQ, "descripcion" );
    col2.AddItemRow().AddItemArea( "Observaciones",CHAR, OPT, "observaciones" ).setHeight(100);
    JFormImageCardResponsive ic = null;
    if (isConsulta())
    	col2.AddInfoCard("Reservas", CHAR, "reservas", JWebIcon.getResponsiveIcon("fa fa-bookmark fa-5x"),"Lista de reservas", 120).setResponsiveClass("border-left-success shadow ").setComplexColumnClass("clearfix col-xl-6 col-lg-6 col-md-6 col-sm-12 col-xs-12");
*/
		JFormColumnResponsive col1 = (JFormColumnResponsive)row1.AddItemColumn("col-xs-12 col-sm-8").setResponsiveClass("panel");
		JFormColumnResponsive col2 = (JFormColumnResponsive)row1.AddItemColumn("col-xs-12 col-sm-4").setResponsiveClass("panel");
	
		col1.AddItemEdit( "id", UINT, OPT, "id" ).setVisible(false);
		col1.AddItemEdit( "company", CHAR, OPT, "company" ).setVisible(false);
		col1.AddItemEdit( "tipo_contrato", CHAR, OPT, "tipo_contrato" ).setVisible(false);
	  if (!getWin().GetcDato().isNew())
	  	col1.AddItemCheck( "Estado", OPT, "active" ).setDescripcionValorCheck("Activo").setDescripcionValorUnCheck("Inactivo").setSizeColumns(2);
    else 
    	if (!BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) 
    		col1.addButton("Activar contrato", 30).setSizeColumns(2).bold();
	  JFormPanelResponsive rowr = col1.AddItemRow();
	  rowr.AddItemDateTime( "Fecha Desde",DATE, OPT, "fecha_desde" ).setSizeColumns(4);
	  rowr.AddItemDateTime( "Fecha Hasta",DATE, OPT, "fecha_hasta" ).setSizeColumns(4);
	  rowr.AddItemDateTime( "Fecha Hasta Replicacion",DATE, OPT, "fecha_hasta_rep" ).setSizeColumns(4);
    if (!isConsulta())
    	col1.AddItemEdit( "Descripción", CHAR, REQ, "descripcion" );
    col1.AddItemRow().AddItemArea( "Observaciones",CHAR, OPT, "observaciones" ).setHeight(100);
    JFormImageCardResponsive ic = null;
    if (isConsulta()&& getWin().GetcDato().isContrato()) {
 	    JFormFieldsetResponsive tot= col1.AddItemFieldset("Totales");
	    tot.setSizeColumns(12);
	   	JFormLista l=tot.AddItemList(150);
     	l.setNavigationBar(false);
     	l.setMinimusRows(1);
     	l.setExtraRows(0);
     	l.setToolBar(JBaseWin.TOOLBAR_NONE);
     	l.setMode(JFormLista.MODE_VERTICAL);
    }
    if (isConsulta() && getWin().GetcDato().isContrato()) {
    	 col1.AddInfoCard("Reservas", CHAR, "reservas", JWebIcon.getResponsiveIcon("fa fa-bookmark fa-5x"),"Lista de reservas", 120).setResponsiveClass("border-left-success shadow ").setComplexColumnClass("clearfix col-xl-6 col-lg-6 col-md-6 col-sm-12 col-xs-12");
 
    }
//    tot.AddItemEdit("Ganancia",FLOAT,OPT,"ganancia").setReadOnly(true);
//    tot.AddItemEdit("Ganancia u$s",FLOAT,OPT,"ganancia_dolares").setReadOnly(true);
//    tot.AddItemEdit("B.Comis.",FLOAT,OPT,"base_comisionable").setReadOnly(true);
//    tot.AddItemEdit("B.Comis. u$s",FLOAT,OPT,"base_comisionable_dolares").setReadOnly(true);
    JFormFieldsetResponsive panelUso= col2.AddItemFieldset("");
  	JFormImageResponsive i= panelUso.AddItemImage("Cumplimiento", "imagen1" );
    i.setSource(JPssImage.SOURCE_SCRIPT);
    i.setHeight(400);
 	  row2.AddItemList(10);

    
    

  }


}  //  @jve:decl-index=0:visual-constraint="-20,-10"
