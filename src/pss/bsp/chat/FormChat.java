package pss.bsp.chat;

import java.awt.Color;

import pss.common.security.BizUsuario;
import pss.core.tools.ChatGPT;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormChat extends JBaseForm {


private static final long serialVersionUID = 1446860154249L;

  /**
   * Constructor de la Clase
   */
  public FormChat() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiChat getWin() { return (GuiChat) getBaseWin(); }

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
   	
  	JFormPanelResponsive panelPrincipal=AddItemPanel("Chat");
   	
  	JFormPanelResponsive panel=panelPrincipal.AddItemFieldset("Pregunta");
  	JFormPanelResponsive panelHist =AddItemPanel("Conversación");
  	panelHist.setBackground(new Color(176,216,230));
  	panelHist.setPadding(3, 3,3, 3);
  	 	
  	panel.AddItemEdit( null, CHAR, OPT, "question" ).setSizeColumns(10).setRefreshForm(true);
  	panel.AddItemButton("Preguntar",-1,true).setSubmit(true).setSizeColumns(1);
  	panel.AddItemButton("Limpiar",-1,true).setSizeColumns(1);
  	panel.setBackground(new Color(176,216,230));
  	panel.setPadding(3, 3,3, 3);
  	
  	panelHist.AddItemList(10);
   // AddItemArea( "Respuesta", CHAR, OPT, "respuesta" );

  } 
  
  @Override
  	public void checkControls(String sControlId) throws Exception {
  		if (sControlId.equals("Preguntar")) {
  			this.getWin().GetcDato().setResponse( ChatGPT.chatSQLBoletos(getWin().GetcDato().getCompany(),BizUsuario.getUsr().GetUsuario(), this.getWin().GetcDato().getQuestion(), this.getWin().GetcDato().getClearHistory()));
  			this.getWin().GetcDato().addConversation();
  		}
  		if (sControlId.equals("Limpiar")) {
  			this.getWin().GetcDato().limpiarConversation();
  			
  		}
  		super.checkControls(sControlId);
  	}
} 
