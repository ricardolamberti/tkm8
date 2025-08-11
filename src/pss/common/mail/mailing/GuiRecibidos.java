package pss.common.mail.mailing;

import pss.core.winUI.lists.JWinList;

public class GuiRecibidos extends GuiMails {



  /**
   * Constructor de la Clase
   */
  public GuiRecibidos() throws Exception {
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("Fecha", "msg_date_creation");
    zLista.AddColumnaLista("De", "msg_sender");
    zLista.AddColumnaLista("Urg", "msg_urgente");
    zLista.AddColumnaLista("Asunto", "msg_title");
    zLista.AddColumnaLista("Mensaje", "message_trunc");
  }
  	
	
}
