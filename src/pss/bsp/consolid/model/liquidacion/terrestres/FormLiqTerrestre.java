package pss.bsp.consolid.model.liquidacion.terrestres;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormLiqTerrestre extends JBaseForm {


private static final long serialVersionUID = 1477827540739L;



  /**
   * Constructor de la Clase
   */
  public FormLiqTerrestre() throws Exception {
  }

  public GuiLiqTerrestre getWin() { return (GuiLiqTerrestre) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "liquidacion_id" ).setHide(true);

    AddItemFile( "Archivo xlsx o zip", CHAR, REQ, "filename" );
    AddItemCheck("Limpiar",REQ,"clean" ).SetValorDefault(true);
  } 
  
}  //  @jve:decl-index=0:visual-constraint="14,3"
