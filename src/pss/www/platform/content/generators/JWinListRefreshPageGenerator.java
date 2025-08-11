/*
 * Created on 05-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.generators;

import java.io.IOException;
import java.util.Map;

import org.apache.avalon.framework.parameters.Parameters;
import org.apache.cocoon.ProcessingException;
import org.apache.cocoon.environment.SourceResolver;
import org.xml.sax.SAXException;

import pss.core.win.JBaseWin;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.www.platform.applications.JHistoryProvider;
import pss.www.ui.JWebPage;
import pss.www.ui.JWebView;
import pss.www.ui.JWebWinGenericResponsive;
import pss.www.ui.JWebWinListJSonResponsive;
import pss.www.ui.JWebWinListResponsive;
import pss.www.ui.JWebWinMatrixResponsive;
import pss.www.ui.processing.JWebCanvas;
import pss.www.ui.skins.JLayoutGenerator;
import pss.www.ui.views.JCanvasResponsiveEmptyView;

public class JWinListRefreshPageGenerator extends JIndoorsPageGenerator {

	@Override
	protected String getBaseContentName() {
		return "console.winlist.refresh";
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setup(SourceResolver resolver, Map objectModel, String src, Parameters par) throws ProcessingException, SAXException, IOException {
		super.setup(resolver, objectModel, src, par);
		getRequest().setAjax(true);
	}



	@Override
	protected JWebPage createPage() throws Exception {
		JWebPage oPage=new JWebPage();
		oPage.setLayoutStereotype(this.getPageLayoutStereotype());
		oPage.addHeaderComponent(new JSessionInfoGenerator());
		oPage.addHeaderComponent(new JLayoutGenerator());
		return oPage;
	}
	
	protected JWebWinListResponsive makeWinList(JAct act,  JHistoryProvider hp) throws Exception {
		JWebWinListResponsive webList=new JWebWinListResponsive(act.getActionSource(), false);
		webList.setBuild(JWebWinListResponsive.BUILD_COMPLETE);
		webList.setPreviewFlag(this.findPreviewFlag(hp));
		webList.setToolbar(this.findToolbarFlag(hp));
//		webList.setRenderPreview(this.findRenderPreview());
		webList.setEmbedded(this.findEmbeddedFlag(hp));
		webList.setTitleDefault(act.getTitle());
//		webList.setForceSelected(webList.isRenderPreview());
		JActWins oAct = (JActWins) act;
		webList.setMultipleSelection(oAct.isMultiple());
		webList.setLineSelect(oAct.isLineSelect());
		webList.setData(oAct.getData());
		if (getRequest().hasTableProvider()) webList.setName(getRequest().getTableProvider());
		return webList;
	}
	
	protected JWebWinListJSonResponsive makeWinListJSon(JAct act, JHistoryProvider hp) throws Exception {
		JWebWinListJSonResponsive webList=new JWebWinListJSonResponsive(act.getActionSource(), false);
		webList.setBuild(JWebWinListResponsive.BUILD_COMPLETE);
		webList.setPreview(this.isPreview(hp));
		webList.setPreviewFlag(this.findPreviewFlag(hp));
		webList.setToolbar(this.findToolbarFlag(hp));
		webList.setEmbedded(this.findEmbeddedFlag(hp));
		webList.setTitleDefault(act.getTitle());
		JActWins oAct = (JActWins) act;
		webList.setMultipleSelection(oAct.isMultiple());
		webList.setLineSelect(oAct.isLineSelect());
		if (getRequest().hasTableProvider()) webList.setName(getRequest().getTableProvider());
		return webList;
	}
	@Override
	protected JWebWinGenericResponsive createWinList(JAct act, JHistoryProvider hp) throws Exception {
		JWebWinListResponsive webList=this.makeWinList(act, hp);
		webList.ensureIsBuilt();
		return webList;
	}
	protected JWebWinGenericResponsive createWinListJSon(JAct oAct, JHistoryProvider hp) throws Exception {
		JWebWinListJSonResponsive oWinList=this.makeWinListJSon(oAct, hp);
		oWinList.ensureIsBuilt();
		return oWinList;
	}

	
	protected JWebWinGenericResponsive createWinMatrix(JAct oAct, JHistoryProvider hp) throws Exception {
		JWebWinMatrixResponsive oWinMatrix = new JWebWinMatrixResponsive(oAct.getActionSource(), true);
		oWinMatrix.setBuild(JWebWinListResponsive.BUILD_COMPLETE);
		JActWins act = (JActWins) oAct;
		oWinMatrix.setMultipleSelection(act.isMultiple());
		oWinMatrix.setLineSelect(act.isLineSelect());
		oWinMatrix.setPreviewFlag(this.findPreviewFlag(hp));
		oWinMatrix.setToolbar(this.findToolbarFlag(hp));
		oWinMatrix.setPreview(this.isPreview(hp));
		oWinMatrix.setEmbedded(this.findEmbeddedFlag(hp));
		oWinMatrix.setClassResponsive("xxx");
		oWinMatrix.setTitleDefault(act.getTitle());

		oWinMatrix.setShowFilterBar(true);
//		oWinMatrix.setShowFilterBar(isHideStatusBar() );
		oWinMatrix.ensureIsBuilt();
		return oWinMatrix;
	}


	@Override
	protected JWebView createView() throws Exception {
		JWebView oView=super.createView();
		oView.setSize(getRequest().getContainerWidth(), getRequest().getContainerHeight());
		
		return oView;
	}

	protected JWebView embedInView(JBaseWin win, JWebCanvas canvas) throws Exception {
		return new JCanvasResponsiveEmptyView(canvas);
	}
	@Override
	protected String getSkinStereotypeView() throws Exception {
		return null;
	}

}