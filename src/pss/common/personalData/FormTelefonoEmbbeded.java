package pss.common.personalData;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormTelefonoEmbbeded extends JBaseForm {

  public GuiTelefono GetWin() { return (GuiTelefono) getBaseWin(); }


  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, UINT, OPT, "persona" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "tipo_tel").setHide(true);
    AddItemEdit( GetWin().GetccDato().getDescripcionTipo(), CHAR, OPT, "numero" );
    AddItemEdit( null, CHAR, OPT, "observacion" ).setHide(true);

  }
}
