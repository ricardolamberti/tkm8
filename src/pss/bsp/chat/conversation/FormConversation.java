package pss.bsp.chat.conversation;

import pss.common.security.BizUsuario;
import pss.core.tools.ChatGPT;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormConversation extends JBaseForm {


private static final long serialVersionUID = 1446860154249L;

  /**
   * Constructor de la Clase
   */
  public FormConversation() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiConversation getWin() { return (GuiConversation) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
  	AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
  	AddItemArea( "Pregunta", CHAR, OPT, "question" ).setSizeColumns(11).setRefreshForm(true);
  	AddItemButton("Preguntar",-1,false).setSizeColumns(1);
    AddItemArea( "Respuesta", CHAR, OPT, "respuesta" );
 

  } 
  
  @Override
  	public void checkControls(String sControlId) throws Exception {
  		if (sControlId.equals("Preguntar")) {
  			this.getWin().GetcDato().setResponse( ChatGPT.chatSQLBoletos(getWin().GetcDato().getCompany(),BizUsuario.getUsr().GetUsuario(), this.getWin().GetcDato().getQuestion(),true));
  		}
  		super.checkControls(sControlId);
  	}
} 
