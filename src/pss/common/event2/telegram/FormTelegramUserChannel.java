package pss.common.event2.telegram;

import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.core.winUI.responsiveControls.JFormEditResponsive;

public class FormTelegramUserChannel extends JBaseForm {


private static final long serialVersionUID = 1586180232148L;



  /**
   * Constructor de la Clase
   */
  public FormTelegramUserChannel() throws Exception {
  }

  public GuiTelegramUserChannel getWin() { return (GuiTelegramUserChannel) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
		JFormEditResponsive edit;
		edit = AddItemEdit( "Id",  UINT, OPT, "ID" );
		edit.setHide(true);
		edit = AddItemEdit( "CHANNEL_ID",  CHAR, OPT, "CHANNEL_ID" );
		edit.setHide(true);
		edit = AddItemEdit( "Empresa",  CHAR, OPT, "COMPANY" );
		edit.setHide(true);
		edit = AddItemEdit( "Usuario",  CHAR, OPT, "USERID" );
		edit.setHide(true);
		edit.setSizeColumns(6);
		JFormCheckResponsive chk = AddItemCheck("Confirma su vinculación?", REQ, "CONFIRMED");
		chk.setSizeColumns(6);

  } 
} 
