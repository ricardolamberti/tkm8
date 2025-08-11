package pss.common.mail.mailing;

import pss.core.winUI.lists.JWinList;

public class GuiEnviados extends GuiMails {



  /**
   * Constructor de la Clase
   */
  public GuiEnviados() throws Exception {
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("Fecha", "msg_date_creation");
    zLista.AddColumnaLista("Urg", "msg_urgente");
    zLista.AddColumnaLista("To", "destinatarios");
    zLista.AddColumnaLista("Asunto", "msg_title");
    zLista.AddColumnaLista("Mensaje", "message_trunc");
  }
  	
	
}
