package pss.common.event.device;

import pss.core.win.JWin;
import pss.core.win.JWins;

public class GuiDevices  extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiDevices() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 900; } 
  public String  GetTitle()    throws Exception  { return "Dispositivo"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiDevice.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
   // addActionNew( 1, "Nuevo Registro" );
  }



}
