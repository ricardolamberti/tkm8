package  pss.common.regions.divitions;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormCiudadDepartamento extends JBaseForm {

  public FormCiudadDepartamento() throws Exception {
  }

  public GuiCiudadDepartamento getWin() { return (GuiCiudadDepartamento) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemCombo( "pais", CHAR, REQ, "pais", new GuiPaises() );
    AddItemCombo( "provincia", CHAR, REQ, "provincia", new GuiProvincias() );
    AddItemCombo( "ciudad_id", CHAR, REQ, "ciudad_id" , new GuiCiudades());
    AddItemCombo( "departamento_id", UINT, REQ, "departamento_id"  , new  GuiDepartamentos());
    AddItemEdit( "id", UINT, OPT, "id" ).hide();
  } 
  
} 
