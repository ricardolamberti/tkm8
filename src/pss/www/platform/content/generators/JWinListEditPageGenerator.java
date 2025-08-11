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
import pss.www.ui.JWebWinListResponsive;
import pss.www.ui.skins.JLayoutGenerator;

public class JWinListEditPageGenerator extends JIndoorsPageGenerator {

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

	@Override
	protected JWebWinListResponsive createWinList(JAct act, JHistoryProvider hp) throws Exception {
		JWebWinListResponsive oWinList=new JWebWinListResponsive(act.getActionSource(),false);
		oWinList.setToolbar(JBaseWin.TOOLBAR_TOP);
		oWinList.setBackBotton(false);
		JActWins oAct = (JActWins) act;
		oWinList.setMultipleSelection(oAct.isMultiple());
		oWinList.setLineSelect(oAct.isLineSelect());
		oWinList.setData(oAct.getData());
		
//		oWinList.setShowFilterBar(isHideStatusBar() ); 
		if (getRequest().hasTableProvider()) oWinList.setName(getRequest().getTableProvider());
		oWinList.ensureIsBuilt();
		return oWinList;
	}

	@Override
	protected JWebView createView() throws Exception {
		JWebView oView=super.createView();
		oView.setSize(getRequest().getContainerWidth(), getRequest().getContainerHeight());
		return oView;
	}

	@Override
	protected String getSkinStereotypeView() throws Exception {
		return null;
	}

}
