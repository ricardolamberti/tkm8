package pss.common.documentos.docEmail;

import pss.core.win.actions.BizAction;

public class GuiMailDetail extends GuiDocEmail {

  public GuiMailDetail() throws Exception {
  }
  
  @Override
  public void createActionMap() throws Exception {
  	// TODO Auto-generated method stub
  	super.createActionMap();
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==3) return false;
  	return super.OkAction(a);
  }


}
