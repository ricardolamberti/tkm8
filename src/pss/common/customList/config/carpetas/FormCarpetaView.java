package pss.common.customList.config.carpetas;

import java.awt.Dimension;

import pss.core.ui.components.JPssEdit;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormLista;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormImageCardResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;
import pss.www.ui.JWebIcon;

public class FormCarpetaView  extends JBaseForm {


private static final long serialVersionUID = 1226426905817L;


	/**
   * Constructor de la Clase
   */
  public FormCarpetaView() throws Exception {
   }

  public GuiCarpeta getWin() { return (GuiCarpeta) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
   

  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null,  ULONG, OPT, "secuencia" ).setHide(true);
    AddItemEdit( null,  CHAR, OPT, "descripcion" ).setHide(true);
    AddItemEdit( null,  CHAR, OPT, "orden" ).setHide(true);
    AddItemEdit( null,  LONG, OPT, "padre" ).setHide(true);
    AddItemEdit( null,  LONG, OPT, "customlist" ).setHide(true);
  	
		JFormPanelResponsive row1=AddItemRow();
    JFormPanelResponsive row2=AddItemRow();
    JFormImageCardResponsive ic = null;
    
    ic = row1.AddImageCard("Modificar" , JWebIcon.getResponsiveIcon("fa fa-angle-double-right fa-3x"), "Modificar nombre", 2);
    if (ic!=null) ic.setResponsiveClass("panel-green").setComplexColumnClass("clearfix col-3 col-xl-3 col-lg-3 col-md-4 col-sm-6 col-xs-12");
    ic = row1.AddImageCard("Agregar contenido" , JWebIcon.getResponsiveIcon("fa fa-angle-double-right fa-3x"), "Incorporar DM y/o informes", 901);
    if (ic!=null) ic.setResponsiveClass("panel-primary").setComplexColumnClass("clearfix col-3 col-xl-3 col-lg-3 col-md-4 col-sm-6 col-xs-12");
    ic = row1.AddImageCard("Imprimir" , JWebIcon.getResponsiveIcon("fa fa-clone fa-3x"),"Imprimir todos los reportes", 55);
    if (ic!=null) ic.setResponsiveClass("panel-danger").setComplexColumnClass("clearfix col-3 col-xl-3 col-lg-3 col-md-4 col-sm-6 col-xs-12");
    ic = row1.AddImageCard("Compartir" , JWebIcon.getResponsiveIcon("fa fa-share-alt-square fa-3x"), "Compartir todos los reportes", 50);
    if (ic!=null) ic.setResponsiveClass("panel-green").setComplexColumnClass("clearfix col-3 col-xl-3 col-lg-3 col-md-4 col-sm-6 col-xs-12");

    JFormTabPanelResponsive tabs = row2.AddItemTabPanel();
    tabs.AddItemList(900).setPreviewFlag(JWins.PREVIEW_NO);

  }
  @Override
  	public void OnPostShow() throws Exception {
  		super.OnPostShow();
  	}

}  //  @jve:decl-index=0:visual-constraint="-1,16"
