package pss.common.documentos.docElectronico;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;

public class FormDocElectronico extends JBaseForm {




  /**
   * Constructor de la Clase
   */
  public FormDocElectronico() throws Exception {
  }

  public GuiDocElectronico GetWin() { 
  	return (GuiDocElectronico) getBaseWin(); 
  }


	  /**
	   * Linkeo los campos con la variables del form
	   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "Nombre", CHAR, REQ, "titulo");

  }
	
	public JWins getSubtipos(boolean one) throws Exception {
		return JWins.createVirtualWinsFromMap(BizDocElectronico.getTipos());
	}
}  //  @jve:decl-index=0:visual-constraint="10,10" 
