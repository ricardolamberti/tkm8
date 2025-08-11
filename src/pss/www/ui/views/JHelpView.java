package pss.www.ui.views;

import pss.core.win.JBaseWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFieldSwapWins;
import pss.core.win.submits.JActOptions;
import pss.core.win.submits.JActWins;
import pss.core.win.submits.JActWinsSelect;
import pss.core.winUI.forms.JBaseForm;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.applications.JHistoryProvider;
import pss.www.platform.applications.JWebApplicationSession;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.JWebHResponsive;
import pss.www.ui.JWebView;
import pss.www.ui.JWebWinForm;
import pss.www.ui.JWebWinGenericResponsive;
import pss.www.ui.JWebWinGridBigDataResponsive;
import pss.www.ui.JWebWinListExpandResponsive;
import pss.www.ui.JWebWinListJSonResponsive;
import pss.www.ui.JWebWinListResponsive;
import pss.www.ui.JWebWinMatrixResponsive;
import pss.www.ui.JWebWinOptionsResponsive;
import pss.www.ui.JWebWinSwapListResponsive;
import pss.www.ui.JWebWinTreeResponsive;

public class JHelpView  extends JWebView {

	private int iType;
	JWebHResponsive title;
	JWebHResponsive oLabel;
	JWebApplicationSession session;
	
	JWebApplicationSession getSession() throws Exception {
		return session;
	}
	@Override
	protected void build() throws Exception {
	
		
			
	}
	
	public boolean isMatrix(JBaseWin baseWin) throws Exception {
		JWebActionData mode = this.getRequest().getData("presentation");
		if (mode.get("mode")!=null) return mode.get("mode").equals(JBaseWin.MODE_MATRIX);
		return baseWin.getModeView().equals(JBaseWin.MODE_MATRIX);
	}
	public boolean isJson(JBaseWin baseWin) throws Exception {
		JWebActionData mode = this.getRequest().getData("presentation");
		if (mode.get("mode")!=null) return mode.get("mode").equals(JBaseWin.MODE_JSON);
		return baseWin.getModeView().equals(JBaseWin.MODE_JSON);
	}	
	public boolean isBigData(JBaseWin baseWin) throws Exception {
		JWebActionData mode = this.getRequest().getData("presentation");
		if (mode.get("mode")!=null) return mode.get("mode").equals(JBaseWin.MODE_BIGDATA);
		return baseWin.getModeView().equals(JBaseWin.MODE_BIGDATA);
	}
	public boolean isLista(JBaseWin baseWin) throws Exception {
		JWebActionData mode = this.getRequest().getData("presentation");
		if (mode.get("mode")!=null) return mode.get("mode").equals(JBaseWin.MODE_LIST);
		return baseWin.getModeView().equals(JBaseWin.MODE_LIST);
	}
	public boolean isTree(JBaseWin baseWin) throws Exception {
		JWebActionData mode = this.getRequest().getData("presentation");
		if (mode.get("mode")!=null) return mode.get("mode").equals(JBaseWin.MODE_TREE);
		return baseWin.getModeView().equals(JBaseWin.MODE_TREE);
	}
	public boolean isExpand(JBaseWin baseWin) throws Exception {
		JWebActionData mode = this.getRequest().getData("presentation");
		if (mode.get("mode")!=null) return mode.get("mode").equals(JBaseWin.MODE_EXPAND);
		return baseWin.getModeView().equals(JBaseWin.MODE_EXPAND);
	}
	public boolean isSwap(JBaseWin baseWin) throws Exception {
		JWebActionData mode = this.getRequest().getData("presentation");
		if (mode.get("mode")!=null) return mode.get("mode").equals(JBaseWin.MODE_SWAP);
		 return baseWin.getModeView().equals(JBaseWin.MODE_SWAP);
	}
	public boolean isOptions(JBaseWin baseWin) throws Exception {
		JWebActionData mode = this.getRequest().getData("presentation");
		if (mode.get("mode")!=null) return mode.get("mode").equals(JBaseWin.MODE_OPTIONS);
		 return baseWin.getModeView().equals(JBaseWin.MODE_OPTIONS);
	}

	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
	  session = getRequest().getSession();
			BizAction action=(BizAction)session.getHistoryManager().getLastHistoryAction();
			JAct submit = action.getSubmit();
			JHistoryProvider hp = this.getSession()==null?null:this.getSession().getHistoryManager().getLastHistory().findProvider(submit);
			JBaseWin baseWin = action.getResult();
				
			try {
				if ((baseWin==null) || (baseWin.getModeView()==null) || baseWin.getModeView().equals(JBaseWin.MODE_FORM)) {
					JBaseForm form = submit.getForm().build();
					JWebWinForm oEditForm = this.createWebForm(submit, hp);
					oEditForm.toHelpXML(zContent);
					 
				} else {
					JWebWinGenericResponsive oList;
					if (isMatrix(baseWin))
						oList =  this.createWinMatrix(submit, hp);
					else if (isJson(baseWin))
						oList = this.createWinListJSon(submit, hp);
					else if (isBigData(baseWin))
						oList = this.createWinGridBigData(submit, hp);
					else if (isTree(baseWin))
						oList = this.createTree(submit, hp);
					else if (isExpand(baseWin))
						oList = this.createExpand(submit, hp);
					else if (isSwap(baseWin))
						oList = this.createSwap(submit, hp);
					else if (isOptions(baseWin))
						oList = this.createWinList(submit, hp);
					else
						oList = this.createWinList(submit, hp);
					oList.toHelpXML(zContent);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		super.componentToXML(zContent);
	}

	public String getPreviewFlag() throws Exception {
//		if (this.isEmbedded()) return false;
		return JWins.PREVIEW_SI;
	}
	
	public boolean isPreview(JHistoryProvider hp) {
		if (hp==null) return false;
		JWebActionData nav = hp.getNavigation();
		if (nav==null) return false;
		if (!nav.hasField("is_preview")) return false;
		return nav.get("is_preview").equals("true");
	}

	
	public String findPreviewFlag(JHistoryProvider hp) throws Exception {
		if (hp==null) return null;
		JWebActionData nav = hp.getNavigation();
		if (nav==null) return null; 
		return nav.get("with_preview");
	}
	
	public String findToolbarFlag(JHistoryProvider hp) throws Exception {
		if (hp==null) return null;
		JWebActionData nav = hp.getNavigation();
		if (nav==null) return null; 
		return nav.get("toolbar");
	}

	public boolean findEmbeddedFlag(JHistoryProvider hp) throws Exception {
		if (hp==null) return false;
		JWebActionData nav = hp.getNavigation();
		if (nav==null) return false;
		if (!nav.hasField("embedded")) return false;
		return nav.get("embedded").equals("true");
	}
	protected JWebWinSwapListResponsive createSwap(JAct oAct, JHistoryProvider hp) throws Exception {
		JActFieldSwapWins act = (JActFieldSwapWins) oAct;
		JWebWinSwapListResponsive oWinList = new JWebWinSwapListResponsive(act);
		oWinList.setTitle(oAct.getTitle());
		oWinList.setFieldKeyOptions(act.getFieldKeyOptions());
		oWinList.setFieldKeySource(act.getFieldKeySource());
		oWinList.ensureIsBuilt();
		return oWinList;
	}
	protected JWebWinOptionsResponsive createOptions(JAct oAct, JHistoryProvider hp) throws Exception {
		JActOptions act = (JActOptions) oAct;
		JWebWinOptionsResponsive oWinList = new JWebWinOptionsResponsive(act);
		oWinList.setTitle(oAct.getTitle());
		oWinList.setOptions(act.GetWins());
		oWinList.ensureIsBuilt();
		return oWinList;
	}
	protected JWebWinTreeResponsive createTree(JAct oAct, JHistoryProvider hp) throws Exception {
		JWebWinTreeResponsive oWinList = new JWebWinTreeResponsive(oAct.getActionSource(), false);
		JActWins act = (JActWins) oAct;
		oWinList.setMultipleSelection(act.isMultiple());
		oWinList.setLineSelect(act.isLineSelect());
		oWinList.setToolbar(this.findToolbarFlag(hp));
		oWinList.setPreview(this.isPreview(hp));
		oWinList.setPreviewFlag(this.findPreviewFlag(hp));
		oWinList.setEmbedded(this.findEmbeddedFlag(hp));
		oWinList.ensureIsBuilt();
		return oWinList;
	}
	protected JWebWinListExpandResponsive createExpand(JAct oAct, JHistoryProvider hp) throws Exception {
		JWebWinListExpandResponsive oWinList = new JWebWinListExpandResponsive(oAct.getActionSource(), false);
		JActWins act = (JActWins) oAct;
		oWinList.setMultipleSelection(act.isMultiple());
		oWinList.setLineSelect(act.isLineSelect());
		oWinList.setToolbar(this.findToolbarFlag(hp));
		oWinList.setPreview(this.isPreview(hp));
		oWinList.setPreviewFlag(this.findPreviewFlag(hp));
		oWinList.setEmbedded(this.findEmbeddedFlag(hp));
		oWinList.ensureIsBuilt();
		return oWinList;
	}
	protected JWebWinGenericResponsive createWinGridBigData(JAct oAct, JHistoryProvider hp) throws Exception {
		JWebWinGridBigDataResponsive oWinList = new JWebWinGridBigDataResponsive(oAct.getActionSource(), false);
		JActWins act = (JActWins) oAct;
		oWinList.setMultipleSelection(act.isMultiple());
		oWinList.setLineSelect(act.isLineSelect());
		oWinList.setToolbar(this.findToolbarFlag(hp));
		oWinList.setPreview(this.isPreview(hp));
		oWinList.setPreviewFlag(this.findPreviewFlag(hp));
//		oWinList.setRenderPreview(this.findRenderPreview());
		oWinList.setEmbedded(this.findEmbeddedFlag(hp));
		

//		oWinList.setShowFilterBar(isHideStatusBar() );
		oWinList.ensureIsBuilt();
		return oWinList;
	}

	protected JWebWinGenericResponsive createWinListJSon(JAct oAct, JHistoryProvider hp) throws Exception {
		JWebWinListJSonResponsive oWinList = new JWebWinListJSonResponsive(oAct.getActionSource(), false);
		JActWins act = (JActWins) oAct;
		oWinList.setMultipleSelection(act.isMultiple());
		oWinList.setLineSelect(act.isLineSelect());
		oWinList.setToolbar(this.findToolbarFlag(hp));
		oWinList.setPreview(this.isPreview(hp));
		oWinList.setPreviewFlag(this.findPreviewFlag(hp));
//		oWinList.setRenderPreview(this.findRenderPreview());
		oWinList.setEmbedded(this.findEmbeddedFlag(hp));
		

//		oWinList.setShowFilterBar(isHideStatusBar() );
		oWinList.ensureIsBuilt();
		return oWinList;
	}
	protected JWebWinGenericResponsive createWinList(JAct oAct, JHistoryProvider hp) throws Exception {
		JWebWinListResponsive oWinList = new JWebWinListResponsive(oAct.getActionSource(), false);
		JActWins act = (JActWins) oAct;
		oWinList.setMultipleSelection(act.isMultiple());
		oWinList.setLineSelect(act.isLineSelect());
		oWinList.setToolbar(this.findToolbarFlag(hp));
		oWinList.setPreview(this.isPreview(hp));
		oWinList.setPreviewFlag((oAct instanceof JActWinsSelect)?JWins.PREVIEW_NO:this.findPreviewFlag(hp));
		oWinList.setTitle(oAct.getTitle());
		
//		oWinList.setRenderPreview(this.findRenderPreview());
		oWinList.setEmbedded(this.findEmbeddedFlag(hp));

//		oWinList.setShowFilterBar(isHideStatusBar() );
		oWinList.ensureIsBuilt();
		return oWinList;
	}
	protected JWebWinGenericResponsive createWinMatrix(JAct oAct, JHistoryProvider hp) throws Exception {
		JWebWinMatrixResponsive oWinMatrix = new JWebWinMatrixResponsive(oAct.getActionSource(), true);
		JActWins act = (JActWins) oAct;
		oWinMatrix.setMultipleSelection(act.isMultiple());
		oWinMatrix.setLineSelect(act.isLineSelect());
		oWinMatrix.setPreview(this.isPreview(hp));
//		oWinMatrix.setEmbedded(this.isEmbedded());
		oWinMatrix.setPreviewFlag(this.findPreviewFlag(hp));
		oWinMatrix.setToolbar(this.findToolbarFlag(hp));
		oWinMatrix.setTitle(oAct.getTitle());
		oWinMatrix.setPreview(this.isPreview(hp));
		oWinMatrix.setShowFilterBar(true);
//		oWinMatrix.setShowFilterBar(isHideStatusBar() );
		oWinMatrix.ensureIsBuilt();
		return oWinMatrix;
	}

	public JWebWinForm createWebForm(JAct submit, JHistoryProvider hp) throws Exception {
		JBaseForm form=submit.getForm();
		if (form==null) return null;

		JWebWinForm oWebForm=new JWebWinForm(form);
		
		oWebForm.setSourceAction(submit.getActionSource());
		oWebForm.setAutoRefresh(form.isAutoRefresh());
		oWebForm.setTimerAutoRefresh(form.getTimerAutoRefresh());
		oWebForm.setMarcaAutoRefresh(form.getMarcaAutoRefresh());
		oWebForm.setMinHeightResponsive(form.getSize().height);
		oWebForm.ensureIsBuilt();
		return oWebForm;
	}
	
	@Override
	protected void setUp(JWebRequest zRequest) throws Exception {
	
	}
	@Override
	protected void containerToHelpXML(JXMLContent zContent) throws Exception {
		// TODO Auto-generated method stub
		
	}



}
