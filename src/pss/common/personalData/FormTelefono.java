package  pss.common.personalData;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;

public class FormTelefono extends JBaseForm {

  public FormTelefono() throws Exception {
  }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "persona", UINT, REQ, "persona" );
    AddItemCombo( "tipo_tel", UINT, REQ, "tipo_tel", JWins.CreateVirtualWins(BizTelefono.getTiposTelefonos()));
    AddItemEdit( "numero", CHAR, REQ, "numero" );
    AddItemArea( "Observación", CHAR, OPT, "observacion" );

  }
  
} 
