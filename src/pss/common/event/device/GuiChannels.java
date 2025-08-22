package pss.common.event.device;

import pss.core.win.JWin;
import pss.core.win.JWins;

public class GuiChannels  extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiChannels() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10006; } 
  public String  GetTitle()    throws Exception  { return "Channels"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiChannel.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
   // addActionNew( 10, "Nuevo canal" );
  }
  
//  @Override
//  public JAct getSubmit(BizAction a) throws Exception {
//  	if (a.getId()==1) return JActNew(getNewChannel());
//  	return super.getSubmit(a);
//  }



}
