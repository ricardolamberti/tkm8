package pss.common.event.device;

import pss.core.win.JWin;
import pss.core.win.JWins;

public class GuiQueueMessages  extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiQueueMessages() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 6011; } 
  public String  GetTitle()    throws Exception  { return "Cola Dispositivo"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiQueueMessage.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
   // addActionNew( 1, "Nuevo Registro" );
  }



}
