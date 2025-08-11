package pss.common.help;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormEditResponsive;

public class FormHelpVideo extends JBaseForm {


private static final long serialVersionUID = 1545231010007L;



  /**
   * Constructor de la Clase
   */
  public FormHelpVideo() throws Exception {
  }

  public GuiHelpVideo getWin() { return (GuiHelpVideo) getBaseWin(); }

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
		edit.setSizeColumns(6);
		edit = AddItemEdit( "BUSINESS",  CHAR, OPT, "BUSINESS" );
		edit.setHide(true);
		edit.setSizeColumns(6);
		edit = AddItemEdit( "Descripcion",  CHAR, OPT, "DESCRIPTION" );
		edit.setSizeColumns(6);
		edit = AddItemEdit( "LINK",  CHAR, OPT, "LINK" );
		edit.setSizeColumns(6);

  } 
} 
