package pss.common.event.device;

import pss.core.win.JWin;
import pss.core.win.JWins;

public class GuiTypeDevices  extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiTypeDevices() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 900; } 
  public String  GetTitle()    throws Exception  { return "Tipos de Dispositivos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiTypeDevice.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Tipo" );
  }



}
