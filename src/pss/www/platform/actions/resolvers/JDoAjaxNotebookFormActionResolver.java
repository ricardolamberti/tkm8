package pss.www.platform.actions.resolvers;


public class JDoAjaxNotebookFormActionResolver extends JDoPssActionResolver {

	@Override
	protected String getBaseActionName() {
		return "tab_form_action";
	}

	@Override
	protected boolean isAjax() {return true;}

  @Override
	protected boolean isBackPageOnDelete() {return true;}

	

}
