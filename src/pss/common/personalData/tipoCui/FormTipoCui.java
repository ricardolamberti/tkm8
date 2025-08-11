package  pss.common.personalData.tipoCui;

import pss.common.regions.divitions.GuiPaises;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormTipoCui extends JBaseForm {

  public FormTipoCui() throws Exception {
  }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	SetExitOnOk(true);
    AddItemEdit( "tipo_doc", CHAR, REQ, "tipo_cui" );
    AddItemEdit( "descripcion", CHAR, REQ, "descripcion" );
    AddItemCombo( "pais", CHAR, REQ, "pais", new GuiPaises() );
    AddItemEdit( "tipo", CHAR, OPT, "tipo_persona");
  }
} 
