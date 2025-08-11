package pss.core.win.submits;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.win.JWins;


public class JActMultiSubmit extends JAct {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6166414369116254357L;
	JList<JAct> actionsList = null; 
	JAct nextAction=null;
	
	public JActMultiSubmit(JWins zResult) {
		super(zResult, -1);
	}
	
	public void addAction(JAct action) throws Exception {
		if (this.actionsList==null) this.actionsList = JCollectionFactory.createList();
		actionsList.addElement(action);
	}
	@Override
	public void Do() throws Exception { 
		JAct next = this.doSubmit();
		if (next!=null) next.Do();
	};
	
	@Override
	public void submit() throws Exception {
		if (actionsList==null) return;
		JIterator<JAct> iter = actionsList.getIterator();
		while (iter.hasMoreElements()) {
			JAct action = iter.nextElement();
			if (action.isMultiProcess()) {
				action.setResult(this.getResult());
				nextAction = action.doSubmit(this.isWeb());
				return;
			}
			action.doSubmit(this.isWeb());
		}
  }

	@Override
	public JAct nextAction() throws Exception {
		return nextAction;
	}
	
  @Override
	public boolean isOnlySubmit() { return true;}

  public boolean backAfterSubmit() throws Exception {
		if (actionsList==null) return true;
		return actionsList.getElementAt(0).backAfterSubmit();
  }
	
	public boolean isBack() {
		 return true;
	}
  
}
