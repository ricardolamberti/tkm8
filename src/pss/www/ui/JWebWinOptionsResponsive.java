package pss.www.ui;

import pss.common.security.BizUsuario;
import pss.core.tools.collections.JIterator;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JActOptions;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.applications.JHistory;
import pss.www.platform.content.generators.JXMLContent;

public class JWebWinOptionsResponsive  extends JWebWinGenericResponsive implements JAbsolutelyNamedWebViewComponent, JWebControlInterface{

	JWins options;
	String question;
	public JWins getOptions() {
		return options;
	}


	public void setOptions(JWins options) {
		this.options = options;
	}



	public String getQuestion() {
		return question;
	}


	public void setQuestion(String question) {
		this.question = question;
	}


	//
	// CONSTRUCTOR
	//
	public JWebWinOptionsResponsive(JActOptions action) throws Exception {
		this.sourceAction = action.getActionSource();
		this.options =action.GetWins();
		}
	

	public String getPresentation() throws Exception {
		return JBaseWin.MODE_OPTIONS;
	};

	public JWebViewComposite getRootPanel() {
		return this.oRootPanel;
	}


	@Override
	protected void build() throws Exception {
		this.regiterObjects();
		oRootPanel = BizUsuario.retrieveSkinGenerator().buildOptions(this);
	
	}

	//
	// SUPERCLASS OVERRIDING
	//
	@Override
	public void validate() throws Exception {

	}

	@Override
	public void destroy() {
		if (!hasWins()) {
			releaseWins();
		}
	
		super.destroy();
	}

	public String getAbsoluteName() {
		return this.getName();
	}

	@SuppressWarnings("unchecked")
	public Class<?> getAbsoluteClass() {
		return JWebWinOptionsResponsive.class;
	}

	@Override
	public String getComponentType() {
		return "options_responsive";
	}
	
	// protected void componentToXML(JXMLContent zContent) throws Exception {
	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
		zContent.setAttribute("zobject", winsObjectId);
		zContent.setAttribute("obj_provider", getProviderName());
		zContent.setAttribute("form_name", "form_"+getProviderName());
		zContent.setAttribute("question", getQuestion());
		
		long size = 12/getOptions().getRecords().getStaticItems().size();
		JIterator<JWin> it = getOptions().getStaticIterator();
		while (it.hasMoreElements()) {
			JWin win = it.nextElement();
			zContent.startNode("option");
			String id = zContent.addObject(win);
			zContent.setAttribute("id", id);
			zContent.setAttribute("size_button", "col-sm-"+size);
			zContent.setAttribute("class_responsive", BizUsuario.retrieveSkinGenerator().buildClassOptions(this, win));
			zContent.setAttributeEscape("description", win.getDescripFieldValue());
			BizAction action =win.findAction(JWin.ACTION_DROP);
			JWebActionFactory.createViewOptionsAction(action, this.getParentProvider(), true, id, isModalParent(action)).toXML(zContent);;
			zContent.endNode("option");
			
		}

	}
	public boolean isModalParent(BizAction action) throws Exception { //RJL. mejorar, necesito saber si tiene que volver a un modal o a una no modal.
		JHistory actThis = 	JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().getLastHistory();
		if (action.isImprimir()) return false;
		if (/*actThis.getMainSumbit().isExitOnOk()*/action.isDropAction()/* || (actThis.getMainSumbit().isOnlySubmit() && actThis.getMainSumbit().isExitOnOk())*/) {
			if (JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().getLastLastHistory()==null)
				return false;
			return JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().getLastLastHistory().getFirstAction().isModal();
		}
		return action.isModal() || actThis.getFirstAction().isModal();
	}


	@Override
	public JWebAction getWebSourceAction() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void onShow(String modo) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
