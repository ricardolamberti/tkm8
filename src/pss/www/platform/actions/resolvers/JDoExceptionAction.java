package pss.www.platform.actions.resolvers;

public class JDoExceptionAction extends JDoPssActionResolver {

	@Override
	protected String getBaseActionName() {
		return "viewArea";
	}
 
	@Override
	protected boolean isAjax() {return true;}

  @Override
	protected boolean isBackPageOnDelete() {return true;}
	
  @Override
  protected boolean isResetRegisteredObjects() throws Exception {
  	return false;
  }

//	public JBaseWin resolveActionOwner() throws Exception {
//		JBaseWin actionOwner=this.resolveMainActionOwner();
//		actionOwner.setDropListener(resolveMainActionDrop());
//		return actionOwner;
//	}
//
//	public BizAction resolveAction(JBaseWin owner) throws Exception {
//		owner.SetVision((String)this.getRequest().get("dg_action"));
//		BizAction action = ((JWin)owner).addActionDrop(BizAction.DROP,"DROP");
//		getSession().getHistoryManager().addHistory(action);
//		return action;
//	}
//	public JBaseWin resolveMainActionDrop() throws Exception {
//		if (this.getRequest().hasPssObjectSelect()) {
//			JBaseWin actionOwner=this.processObjectRegisteredSelect(true);
//			if (actionOwner!=null) return actionOwner;
//		}
//		return null;
//	}

}
