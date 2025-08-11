package pss.core.win.submits;

import pss.core.win.JBaseWin;

/**
 * Basic action that simply submits the current window without opening any
 * additional UI.
 * <p>
 * It is commonly used for operations that only execute server side logic and
 * then return to the same context.
 * </p>
 */
public class JActSubmit extends JAct {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -3316292953453369454L;

	public JActSubmit(JBaseWin zResult, int zActionId) {
		super(zResult, zActionId);
	}
	public JActSubmit(JBaseWin zResult) {
		super(zResult);
	}

//	
//	@Override
//	public void Do() throws Exception {
////		JAct submit = this;
//		if (this.hasActionId()) {
//			BizAction action = this.getResult().findAction(this.getActionId());
//			if (action.hasConfirmMessage() &&	!UITools.showConfirmation("Confirmación", action.getConfirmMessageDescription()))
//				return;
////			submit = action.getSubmit();
//		}
////		JAct nextAction = submit.doSubmit();
//		JAct nextAction = this.doSubmit();
//		if ( nextAction!=null) nextAction.Do();
//	}	
// 
  @Override
	public boolean isOnlySubmit() { return true;}	
  
  @Override
  public boolean isExitOnOk() throws Exception {
  	return false;
  }
  
  public boolean forceMarkFieldChange(JAct action) throws Exception {
  	return action instanceof JActModify;
  }
}
