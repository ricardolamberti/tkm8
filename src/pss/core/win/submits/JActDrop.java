package pss.core.win.submits;

import pss.core.win.JBaseWin;
import pss.core.win.actions.BizAction;


public class JActDrop extends JAct {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5672707536896804453L;
	private JBaseWin oDropListener;


	private JAct nextAction;
	private boolean dropControlId;

	public JActDrop(JBaseWin zResult, JBaseWin listener, int actionId) {
		super(zResult, actionId);
		oDropListener = listener;
		dropControlId=false;
	}
	
//	public boolean isDropAction() { return true;}
	
	@Override
	public void Do() throws Exception {
		JAct nextAction = doSubmit();
		if (nextAction!=null) nextAction.Do();
  }
	
	@Override
	public void submit() throws Exception {
		if (oDropListener==null) return;
  	if (this.getResult().canEjecuteDrop()) { // viene de un formLov
  		this.nextAction = oDropListener.Drop(this.getResult(), this.getActionSource(), this.getData());
  	}
  	if (oDropListener.canEjecuteDropControl(this.nextAction)) {
			this.dropControlId=true;
			nextAction = oDropListener.DropControl(this);
		} 
//		else {
//			this.nextAction = oDropListener.Drop(this.getResult(), this.getActionSource(), this.getData());
//		}
		if (nextAction!=null&&nextAction instanceof JActWinsSelect)
				nextAction.setInHistory(true);

  }
	
	@Override
	public JAct nextAction() throws Exception {
		return nextAction;
	}

	@Override
	public boolean isMultiProcess() throws Exception {
		return true;
	}
	
  @Override
	public boolean isOnlySubmit() { return true;}
  
  @Override
	public boolean backAfterSubmit() { 
  	return true;
  }
  
	public boolean backAfterDropControl(JAct act) throws Exception {
		return dropControlId  && (act.getResult().hasDropControlIdListener());
	}
	
	public JBaseWin getDropListener() {
		return oDropListener;
	}
  public boolean desactiveReread(JAct action) throws Exception {
  	return false;//!hasIdControlListener();
  }
  
	
	public boolean isBack() {
		 return true;
	}
}
