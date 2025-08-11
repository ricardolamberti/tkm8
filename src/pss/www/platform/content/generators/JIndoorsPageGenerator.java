/*
 * Created on 05-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.generators;

import org.apache.cocoon.environment.Response;

import pss.common.help.BizQuestion;
import pss.common.help.BizQuestionDetail;
import pss.common.publicity.BizCampaign;
import pss.common.publicity.BizSpot;
import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.data.BizPssConfig;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.win.JBaseWin;
import pss.core.win.JWins;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFieldSwapWins;
import pss.core.win.submits.JActOptions;
import pss.core.win.submits.JActWins;
import pss.core.win.submits.JActWinsSelect;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIconos;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.applications.JHistoryProvider;
import pss.www.ui.JWebHelp;
import pss.www.ui.JWebHelpBox;
import pss.www.ui.JWebIcon;
import pss.www.ui.JWebPublicity;
import pss.www.ui.JWebRootPane;
import pss.www.ui.JWebTitledComponent;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewsConstants;
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
import pss.www.ui.processing.JWebCanvas;
import pss.www.ui.views.JCanvasResponsiveView;

public class JIndoorsPageGenerator  extends JXMLPageGenerator {

	JList<JWebHelpBox> listHelpBox;

	JList<JWebPublicity> listPublicity;

	@Override
	protected String getBaseContentName() {
		return "responsive.page";
	}

	@Override
	protected String getPageLayoutStereotype() {
		return JWebViewsConstants.PAGE_LAYOUT_APP_INDOORS;
	}

	protected JAct getAct() throws Exception {
//		if (getSession().getIdRequest()==0) {
//			return (JAct)getSession().getHistoryManager().getLastSubmit();
//		}
		return (JAct)JWebActionFactory.getCurrentRequest().getRegisteredRequestObject("action");
	} 

	protected JBaseWin getBaseWin(JAct oAct) throws Exception {
		if (oAct==null) return null;
		return oAct.getResult();
	}

	// TODO: optimizar para cachear la busqueda
	@Override
	protected JList<JWebPublicity> createArrayPublicity() throws Exception {
		return listPublicity;
	}

	// TODO: optimizar para cachear la busqueda
	@Override
	protected JList<JWebHelpBox> createArrayHelpBox() throws Exception {
		return listHelpBox;
	}

	@Override
	protected JWebHelp createHelp() throws Exception {
		return null;//new JWebHelp(zSkin);
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
	protected JWebView createView() throws Exception {
		JAct submit = this.getAct();
		JBaseWin baseWin = this.getBaseWin(submit);
		JHistoryProvider hp = this.getSession()==null?null:this.getSession().getHistoryManager().getLastHistory().findProvider(submit);
	//	BizUsuario.retrieveSkinGenerator().clearEventInterfaz();
		
		this.setSaveHelp((baseWin==null)?"":baseWin.getClass().toString(), (submit==null)?"":submit.getClass().toString(),(baseWin==null)?"":baseWin.getStatusHelp());
		
		try {
			JWebCanvas oCanvas = null;
			if ((baseWin==null) || (baseWin.getModeView()==null) || baseWin.getModeView().equals(JBaseWin.MODE_FORM)) {
				JWebWinForm oEditForm = this.createWebForm(submit, hp);
				oCanvas = (oEditForm == null) ? new JWebRootPane() : oEditForm.getRootPanel();
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
					oList = this.createOptions(submit, hp);
				else
					oList = this.createWinList(submit, hp);
				oCanvas = oList.getRootPanel();
			}
			
			
			JWebView oWebView = this.embedInView(baseWin, oCanvas);
			listHelpBox = null;
			listPublicity = null;
			this.addingExtraViews(oWebView, baseWin, submit);
			this.confirmedHistory();
			return oWebView;
			
		} catch (Exception e) {
			this.backToConfirmedHistory();
			throw e;
		}
	}
	
	private void confirmedHistory() throws Exception {
		if (this.getSession()==null) return;
		this.getSession().getHistoryManager().getLastHistory().setConfirmHistory(true);
	}
	
	private void backToConfirmedHistory() throws Exception {
		if (this.getSession()==null) return;
		this.getSession().getHistoryManager().backToConfirmedHistory();
	}


	public void redirectTabSelected(JHistoryProvider hp, JAct submit) throws Exception {
//		if (hp==null) return;
//		BizAction tab = hp.getSelectedTab();
//		if (tab==null) return;
//		tab.setObjOwner(submit.getResult());
//		tab.clearSubmit();
	}

	public JWebWinForm createWebForm(JAct submit, JHistoryProvider hp) throws Exception {
		JBaseForm form=submit.getForm();
		if (form==null) return null;
		form.setSourceAction(submit.getActionSource());

		this.redirectTabSelected(hp, submit);
		
		JWebWinForm oWebForm=new JWebWinForm(form);
//		oWebForm.getNotebook().assignHistoryProvider(hp);
		
		oWebForm.setSourceAction(submit.getActionSource());
		oWebForm.setToolbar(this.getToolbarPos(hp));
		oWebForm.setPreview(this.isPreview(hp));
		oWebForm.setContainerPreview(this.getContainerPreview(hp));
		oWebForm.setEmbedded(this.findEmbeddedFlag(hp));
		oWebForm.setWithHeader(this.findWithHeaderFlag(hp)&&form.isWithHeader() );
		oWebForm.setMinHeightResponsive(form.getSize()==null?0:form.getSize().height);
		oWebForm.ensureIsBuilt();
		oWebForm.setPreview(this.isPreview(hp));
		oWebForm.setAutoRefresh(form.isAutoRefresh());
		oWebForm.setTimerAutoRefresh(form.getTimerAutoRefresh());
		oWebForm.setMarcaAutoRefresh(form.getMarcaAutoRefresh());
		String title = this.findTitle(hp);
		if (title!=null) {
			oWebForm.setForceTitle(title);
		}
		return oWebForm;
	}
	
	public String getToolbarPos(JHistoryProvider hp) throws Exception {
		if (hp!=null) {
			JWebActionData nav = hp.getNavigation();
			if (nav!=null && nav.hasField("toolbar")) return nav.get("toolbar");
		}
		JWebActionData nav = getRequest().getData("win_list_nav");
		if (nav.hasField("hide_act_bar"))
			return JBaseWin.TOOLBAR_NONE;
		String toolbar = null;
		if (BizUsuario.getUsr()!=null)
			toolbar= BizUsuario.getUsr().getObjBusiness().getFormToolbarPosDefault();
		if (toolbar!=null) return toolbar;
		return JBaseWin.TOOLBAR_LEFT;
	}


//	public boolean isEmbedded() {
//		return false;
//	}

	protected void addingExtraViews(JWebView oWebView, JBaseWin oBWin, JAct oAct) throws Exception {
		if (oWebView == null) return;
		oWebView.setHelp(oBWin.getHelpTag());
		listHelpBox = findQuickHelp(oBWin, oAct);
		listPublicity = findPublicity(oBWin, oAct);
	}

	private JList<JWebPublicity> findPublicity(JBaseWin oBWin, JAct oAct) throws Exception {
		JList<JWebPublicity> listPublicity = JCollectionFactory.createList();
		JWebPublicity publ = null;
		BizCampaign campaign = JAplicacion.GetApp().getPublicityCampaign();
		if (campaign != null) {
			JRecords<BizSpot> list = BizSpot.ReadCampaign(campaign.getId());
			list.firstRecord();
			while (list.nextRecord()) {
				BizSpot detail = list.getRecord();
				if (   (detail.getPage().equals("*") || (oBWin != null && oBWin.getClass().toString().equals(detail.getPage()))) && 
						   (detail.getAction().equals("*") || (oAct != null && oAct.getClass().toString().equals(detail.getAction()))) &&
						   ((detail.getStatus().isEmpty() || detail.getStatus().equals("*")) || (oBWin.getStatusHelp() != null && oBWin.getStatusHelp().equals(detail.getStatus())))
					)
				{
					publ = new JWebPublicity();
					publ.setResource(detail.getImage());
					publ.setLink(detail.getLink());
					publ.setPosition(detail.getTypePos());
					publ.setLocation((int) detail.getX(), (int) detail.getY());
					publ.setZ((int)detail.getZ());
					publ.setSize((int) detail.getWidth(), (int) detail.getHeight());
					publ.setLocalization(detail.getLocalization());
					publ.setRelativePosition(detail.getIdhtml());
					publ.setType(detail.getType());
					listPublicity.addElement(publ);
				}
			}
		}
		return listPublicity;
	}

	private boolean compareStatus(String cadena1,String cadena2YSimbolo) {
		char r = cadena2YSimbolo.charAt(0);
		if (r=='=' || r=='~') {
			String cadena2 = cadena2YSimbolo.substring(1);
			if (r=='=') return cadena1.equals(cadena2);
			return !cadena1.equals(cadena2);
		}
		return cadena1.equals(cadena2YSimbolo);
	}
	private JList<JWebHelpBox> findQuickHelp(JBaseWin oBWin, JAct oAct) throws Exception {
		JList<JWebHelpBox> listHelpBox = JCollectionFactory.createList();
		JWebHelpBox help = null;
		BizQuestion questionHelp = JAplicacion.GetApp().getQuestionHelp();
		if (questionHelp == null) return listHelpBox;
		JRecords<BizQuestionDetail> list = BizQuestionDetail.ReadByPage(questionHelp.getIdquestion(), oBWin.getClass().toString());
		JIterator<BizQuestionDetail> it = list.getStaticIterator();
		while (it.hasMoreElements()) {
			BizQuestionDetail detail = it.nextElement();
			if (!(detail.getAction().equals("*") || (oAct != null && oAct.getClass().toString().equals(detail.getAction())))) continue;
			if (!(detail.getStatus().isEmpty() || detail.getStatus().equals("*") || (oBWin.getStatusHelp() != null && compareStatus(oBWin.getStatusHelp(),detail.getStatus())))) continue;
			help = new JWebHelpBox();
			help.setStringHelp(detail.getHelp());
			help.setPosition(detail.getTypePos());
			help.setZ((int)detail.getZ());
			help.setLocation((int) detail.getX(), (int) detail.getY());
			help.setSize((int) detail.getWidth(), (int) detail.getHeight());
		//	String action = JWebActionFactory.getCurrentRequest().getSession().getNameDictionary(detail.getId().replace(".", "_"));
	
			help.setRelativePosition(detail.getId());
			help.setType(detail.getType());
			listHelpBox.addElement(help);
		}
		return listHelpBox;
	}

	protected JWebView embedInView(JBaseWin win, JWebCanvas canvas) throws Exception {
		String sViewTitle = win.GetTitle();

		if ( BizPssConfig.getPssConfig().isWinTitleDebug() )
			sViewTitle += " ("+win.getClass().getName()+")";
		JWebIcon oIcon = JWebIcon.getPssIcon(GuiIconos.RESPONSIVE, win.GetNroIcono());

		JWebView oView = new JCanvasResponsiveView(canvas);
		

		oView.setSkinStereotype(this.getSkinStereotypeView());
		if (!oView.hasTitle()) {
			if (canvas instanceof JWebTitledComponent) {
				String sCanvasTitle = ((JWebTitledComponent) canvas).getTitle();
				if (sCanvasTitle != null && sCanvasTitle.length() > 0) {
					sViewTitle = sCanvasTitle;
				}
			}
			oView.setTitle(sViewTitle);
		}
		if (oView.getIcon() == null) {
			oView.setIcon(oIcon);
		}

		return oView;
	}

	protected String getSkinStereotypeView() throws Exception {
		return "canvas";
	}


	public String getPreviewFlag() throws Exception {
//		if (this.isEmbedded()) return false;
		return JWins.PREVIEW_SI;
	}
	public String getContainerPreview(JHistoryProvider hp) {
		if (hp==null) return null;
		JWebActionData nav = hp.getNavigation();
		if (nav==null) return null;
		if (!nav.hasField("container_preview")) return null;
		return nav.get("container_preview");
	}
	public boolean isPreview(JHistoryProvider hp) {
		if (hp==null) return false;
		JWebActionData nav = hp.getNavigation();
		if (nav==null) return false;
		if (!nav.hasField("is_preview")) return false;
		return nav.get("is_preview").equals("true");
	}
	public long findExtraRows(JHistoryProvider hp) throws Exception {
		if (hp==null) return 10;
		JWebActionData nav = hp.getNavigation();
		if (nav==null) return 10; 
		return JTools.getLongNumberEmbedded(nav.get("extraRows"));
	}
	public long findMinimusRows(JHistoryProvider hp) throws Exception {
		if (hp==null) return 10;
		JWebActionData nav = hp.getNavigation();
		if (nav==null) return 10; 
		return JTools.getLongNumberEmbedded(nav.get("minimusRows"));
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

	public boolean findWithHeaderFlag(JHistoryProvider hp) throws Exception {
		if (hp==null) return true;
		JWebActionData nav = hp.getNavigation();
		if (nav==null) return true;
		if (!nav.hasField("with_header")) return true;
		return nav.get("with_header").equals("true");
	}
	
	public String findTitle(JHistoryProvider hp) throws Exception {
		if (hp==null) return null;
		JWebActionData nav = hp.getNavigation();
		if (nav==null) return null;
		if (!nav.hasField("title")) return null;
		return nav.get("title");
	}
	protected JWebWinSwapListResponsive createSwap(JAct oAct, JHistoryProvider hp) throws Exception {
		JActFieldSwapWins act = (JActFieldSwapWins) oAct;
		JWebWinSwapListResponsive oWinList = new JWebWinSwapListResponsive(act);
		oWinList.setTitleDefault(oAct.getTitle());
		oWinList.setFieldKeyOptions(act.getFieldKeyOptions());
		oWinList.setFieldKeySource(act.getFieldKeySource());
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
		oWinList.setMinimusRows(this.findMinimusRows(hp));
		oWinList.setExtraRows(this.findExtraRows(hp));
		oWinList.setPreviewFlag(this.findPreviewFlag(hp));
		oWinList.setEmbedded(this.findEmbeddedFlag(hp));
		oWinList.setToolbar(this.findToolbarFlag(hp));
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
		oWinList.setTitleDefault(oAct.getTitle());
		oWinList.setData(oAct.getData());
		
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
		oWinMatrix.setTitleDefault(oAct.getTitle());
		oWinMatrix.setPreview(this.isPreview(hp));
		oWinMatrix.setShowFilterBar(true);
//		oWinMatrix.setShowFilterBar(isHideStatusBar() );
		oWinMatrix.ensureIsBuilt();
		return oWinMatrix;
	}
	protected JWebWinOptionsResponsive createOptions(JAct oAct, JHistoryProvider hp) throws Exception {
		JActOptions act = (JActOptions) oAct;
		act.setForceModal(true);
		act.getActionSource().setModal(true);
		JWebWinOptionsResponsive oWinList = new JWebWinOptionsResponsive(act);
		oWinList.setQuestion(act.getQuestion());
		oWinList.setOptions(act.getWinsResult());
		oWinList.ensureIsBuilt();
		return oWinList;
	}

	@Override
	protected boolean isSessionDependent() {
		return true;
	}

	@Override
	protected void cleanUp() {
		if (this.getSession()!=null) 
			this.getSession().cleanUpToLeaveInMemory();
		// this.getAct().cleanAction();
		super.cleanUp();
	}
	
	protected void setResponseHeaders(Response zResponse) throws Exception {
		String title=JWebActionFactory.getTitleFor(this.getRequest());
		if (title != null ) { 
		   zResponse.addHeader("Content-disposition", "attachment; filename="+title.replaceAll(" ", ""));
		}
	  super.setResponseHeaders(zResponse);
	}

}
