package pss.core.win.actions;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormAction extends JBaseForm {

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public FormAction() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); }
  }


  //-------------------------------------------------------------------------//
  // Inicializacion Grafica
  //-------------------------------------------------------------------------//
  protected void jbInit() throws Exception {


  }

  public GuiAction GetWin() {
    return (GuiAction) getBaseWin();
  }

  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    this.AddItemEdit( "Accion ", CHAR, REQ, "accion" );
    this.AddItemEdit( "Descripcion  ", CHAR, REQ, "descripcion" );
    this.AddItemEdit( "Icono        ", CHAR, REQ, "nro_icono" );
    AddItemCheck( "Restringido"       , CHAR, REQ, "restringido", "S", "N");
    AddItemTabPanel().AddItemTab(20);
//    bIcono.setIcon(GuiIconos.GetGlobal().buscarIcono(((GuiAction)zWin).GetcDato().pNroIcono.getValue()).GetIconImage());
  }

}  //  @jve:decl-index=0:visual-constraint="10,10"

