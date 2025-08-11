package pss.common.documentos.docEmail;

import pss.core.win.JWin;
import pss.core.win.actions.BizAction;

public class GuiMailDetails extends GuiDocEmails {


	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiMailDetail.class;
	}
	
  public GuiMailDetails() throws Exception {
  }
  
  @Override
  public void createActionMap() throws Exception {
  }


}
