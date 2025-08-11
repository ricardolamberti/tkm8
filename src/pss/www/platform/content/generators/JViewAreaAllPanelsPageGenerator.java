package pss.www.platform.content.generators;

import java.io.IOException;
import java.util.Map;

import org.apache.avalon.framework.parameters.Parameters;
import org.apache.cocoon.ProcessingException;
import org.apache.cocoon.environment.SourceResolver;
import org.xml.sax.SAXException;

import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.tools.JMessageInfo;
import pss.core.tools.JTools;
import pss.core.win.JBaseWin;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.ui.JWebView;
import pss.www.ui.processing.JWebCanvas;
import pss.www.ui.views.JCanvasResponsivePartialView;

public class JViewAreaAllPanelsPageGenerator extends JIndoorsPageGenerator {

	@Override
	protected String getBaseContentName() {
		return "console.viewarea";
	}
	
	@Override
	protected void doGenerate() throws Exception {
		super.doGenerate();
		JMessageInfo msg= this.getAct().getMessage();
		if (msg!=null) {
			;
			this.startNode("message");
			this.setAttributeNLS("title", JTools.encodeStringfull(msg.getTitle()==null?BizUsuario.getMessage("Información", null):msg.getTitle()));
			this.setAttribute("type",msg.getType()==null?JMessageInfo.TYPE_INFO:msg.getType());
			this.addTextNode("text", JTools.encodeStringfull(JLanguage.translate(msg.getMessage())));
			this.endNode("message");			
			return;
		}
		JWebActionData oData=this.oRequest.getData("msg_attrs");
		String sMessage=oData.get("msg");
		String sType = oData.get("type");
		String sTitle = oData.get("title");
		String sReload = oData.get("reload");
		if (sMessage!=null && !sMessage.equals("")) {
			this.startNode("message");
			this.setAttributeNLS("title", JTools.encodeStringfull(sTitle));
			this.setAttribute("type", sType);
			this.addTextNode("text", JTools.encodeStringfull(JLanguage.translate(sMessage)));
			this.endNode("message");			
		}
		

	}

	@Override
	@SuppressWarnings("unchecked")
	public void setup(SourceResolver resolver, Map objectModel, String src, Parameters par) throws ProcessingException, SAXException, IOException {
		super.setup(resolver, objectModel, src, par);
		getRequest().setAjax(true);
	}

	protected JWebView embedInView(JBaseWin win, JWebCanvas canvas) throws Exception {
		return new JCanvasResponsivePartialView(canvas);
	}
}
