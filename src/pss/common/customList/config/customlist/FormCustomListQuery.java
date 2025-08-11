package pss.common.customList.config.customlist;

import java.awt.Dimension;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormImageCardResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.www.ui.JWebIcon;

public class FormCustomListQuery extends JBaseForm {


private static final long serialVersionUID = 1226426806993L;

/**
   * Constructor de la Clase
   */
  public FormCustomListQuery() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiCustomList getWin() { return (GuiCustomList) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    this.setSize(new Dimension(1160, 603));
    this.setLayout(null);

    
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "list_id" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "description" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "record_owner" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "modelo" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "rel_id" ).setHide(true);
		AddItemEdit( null, UINT, OPT, "totalizar" ).setHide(true);
		AddItemEdit( null, UINT, OPT, "subtotalizar" ).setHide(true);
		AddItemEdit( null, UINT, OPT, "limite" ).setHide(true);
		AddItemEdit( null, UINT, OPT, "agrupado" ).setHide(true);
		AddItemEdit(null, CHAR, OPT, "view_table").setVisible(false);
    AddItemEdit( null, CHAR, OPT, "invisible" ).setVisible(false);

		JFormPanelResponsive row1=AddItemRow();
    JFormPanelResponsive row2=AddItemRow();
//    JFormPanelResponsive panelzoom= row1.AddItemPanel(null);
//    panelzoom.setZoomtofit(1000);
    JFormImageCardResponsive ic = null;
    
    if (getWin().GetcDato().isSystemProtect()) {
      ic = row1.AddImageCard( "Edición protegida!" , JWebIcon.getResponsiveIcon("fa fa-edit fa-3x"), "Obtener DM modificable", 500);
      if (ic!=null) ic.setResponsiveClass("panel-green").setComplexColumnClass("clearfix col-2 col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12");
    	
    } else {
      ic = row1.AddImageCard( "Editar" , JWebIcon.getResponsiveIcon("fa fa-edit fa-3x"), "Editar el datamining", 2);
      if (ic!=null) ic.setResponsiveClass("panel-green").setComplexColumnClass("clearfix col-2 col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12");
    	
    }
    ic = row1.AddImageCard("Ejecutar" , JWebIcon.getResponsiveIcon("fa fa-angle-double-right fa-3x"), "Ver resultado completo", 20);
    if (ic!=null) ic.setResponsiveClass("panel-primary").setComplexColumnClass("clearfix col-2 col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12");
    ic = row1.AddImageCard("Permisos" , JWebIcon.getResponsiveIcon("fa fa-key fa-3x"), "Cambiar permisos DM", 80);
    if (ic!=null) ic.setResponsiveClass("panel-yellow").setComplexColumnClass("clearfix col-2 col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12");
    ic = row1.AddImageCard("Incluir Dashboard" , JWebIcon.getResponsiveIcon("fa fa-eye fa-3x"), "Incluir DM en Dashboard", 60);
    if (ic!=null) ic.setResponsiveClass("panel-yellow").setComplexColumnClass("clearfix col-2 col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12");
    ic = row1.AddImageCard("Quitar Dashboard" , JWebIcon.getResponsiveIcon("fa fa-eye-slash fa-3x"), "Quitar DM en Dashboard", 70);
    if (ic!=null) ic.setResponsiveClass("panel-yellow").setComplexColumnClass("clearfix col-2 col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12");
    ic = row1.AddImageCard("Clonar" , JWebIcon.getResponsiveIcon("fa fa-clone fa-3x"),"Generar nueva versión", 500);
    if (ic!=null) ic.setResponsiveClass("panel-danger").setComplexColumnClass("clearfix col-2 col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12");
    ic = row1.AddImageCard("Compartir" , JWebIcon.getResponsiveIcon("fa fa-share-alt-square fa-3x"), "Enviar email, url y otros", 50);
    if (ic!=null) ic.setResponsiveClass("panel-green").setComplexColumnClass("clearfix col-2 col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12");

    row2.AddCardPanel(22);//.setDiferido(true);

   }
	
}  //  @jve:decl-index=0:visual-constraint="4,-13"
