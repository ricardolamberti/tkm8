package  pss.common.customList.config.field.campo;

import java.awt.Color;
import java.awt.Dimension;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormImageCardResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.www.ui.JWebIcon;

public class FormCampoOR extends JBaseForm {


private static final long serialVersionUID = 1226426966039L;


/**
   * Constructor de la Clase
   */
  public FormCampoOR() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiCampo getWin() { return (GuiCampo) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(493, 298));


  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null, UINT, REQ, "list_id" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "secuencia" ).setHide(true);
		AddItemEdit( null, CHAR, OPT, "serial_deep").setHide(true);
    AddItemEdit( null, CHAR, OPT, "record_owner" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "record_source" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "rel_id").setHide(true);
    AddItemEdit( null, CHAR, OPT, "id_gallery").setHide(true);
    AddItemEdit( null, UINT, OPT, "orden_padre" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "orden" ).setHide(true);
    AddItemEdit( null, CHAR, REQ, "operacion" ).setHide(true);
    JFormImageCardResponsive ic = null;

    AddItemCombo( "Tipo", CHAR, REQ, "negado", JWins.createVirtualWinsFromMap(BizCampo.getTipoNegaciones()) ).setSizeColumns(6).SetValorDefault(BizCampo.TIPO_TRUE);
    AddItemCheck( "No incluir en totalizador de porcentajes", CHAR,"no_incluir" ).setSizeColumns(3);
    AddItemCheck( "Solo incluir en totalizador de porcentajes", CHAR,"solo_tot" ).setSizeColumns(3);;
//    AddItemListTable( "Consultas", "detalle", GuiCampos.class);
    JFormPanelResponsive row=AddItemRow();
    JFormColumnResponsive col3=row.AddItemColumn("col-sm-6");
    col3.setBackground(new Color(176,216,230));
    col3.setPadding(3, 3,3, 3);
    JFormColumnResponsive col4=row.AddItemColumn("col-sm-6");
    col3.AddItemTableExpand("Campos", "detalle", GuiCampos.class).setSizeRow("col-sm-12").setMinHeight(400);
    ic = col4.AddImageCard(getWin().GetcDato().isOperOR()?"Agregar Filtros O":"Agregar Filtros Y" , JWebIcon.getResponsiveIcon("fa fa-plus-circle fa-3x"),getWin().GetcDato().isOperOR()? "Agregar condiciones 'O'":"Agregar condiciones 'Y' ", 530);
    if (ic!=null) ic.setResponsiveClass("panel-primary").setComplexColumnClass("clearfix col-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12");
    ic = col4.AddImageCard(getWin().GetcDato().isOperOR()?"Agregar Filtros Y":"Agregar Filtros O" , JWebIcon.getResponsiveIcon("fa fa-folder-plus fa-3x"),getWin().GetcDato().isOperOR()? "Agregar condiciones 'Y' ":"Agregar condiciones 'O'", 531);
    if (ic!=null) ic.setResponsiveClass("panel-primary").setComplexColumnClass("clearfix col-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-xs-12");
  }
  

	
}  //  @jve:decl-index=0:visual-constraint="20,9" 
