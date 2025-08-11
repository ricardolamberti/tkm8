package pss.bsp.chat.conversation;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiConversations  extends JWins  {



  /**
   * Constructor de la Clase
   */
  public GuiConversations() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15011; } 
  public String  GetTitle()    throws Exception  { return "Chat"; }
  public Class<? extends JWin>  GetClassWin()    { return GuiConversation.class; }

  
  @Override
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {  	

			zLista.addColumnWinAction("Flat", 1);
  }
  
  @Override
	public Boolean isWithFooter() {
		return false;
	}
  
  @Override
  public Boolean isWithHeader() {
		return false;
  }
 
  @Override
  public boolean isForceHideActions() {
  	return true;
  }
  @Override
  public boolean isShowWinToolBar() throws Exception {
  	return false;
  }
  
	public Long getMinLineas() {
		return new Long(0);
	}
}
