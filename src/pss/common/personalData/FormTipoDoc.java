package  pss.common.personalData;

import pss.common.regions.divitions.GuiPaises;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormTipoDoc extends JBaseForm {


  public FormTipoDoc() throws Exception {
  }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	SetExitOnOk(true);
    AddItemCombo( "pais", CHAR, REQ, "pais", new GuiPaises() );
    AddItemEdit( "tipo_doc", CHAR, REQ, "tipo_doc" );
    AddItemEdit( "descripcion", CHAR, REQ, "descripcion" );

  }
}
