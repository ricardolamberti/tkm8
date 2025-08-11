/*
 * Created on 24-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.generators;

import java.io.Serializable;

import pss.common.regions.multilanguage.JLanguage;
import pss.core.tools.JTools;
import pss.www.platform.actions.requestBundle.JWebActionData;

public class JMessageGeneratorAjax extends JXMLComponentGenerator {

	@Override
	protected String getBaseContentName() {
		return "show.message.ajax";
	}

	@Override
	protected void doGenerate() throws Exception {

		JWebActionData oData=this.oRequest.getData("msg_attrs");
		String sMessage=oData.get("msg");
		String sType = oData.get("type");
		String sTitle = oData.get("title");
		String sReload = oData.get("reload");

		this.startNode("message");
		this.setAttribute("title", JTools.encodeString(sTitle));
		this.setAttribute("type", sType);
		this.addTextNode("text", JLanguage.translate(sMessage));
		this.endNode("message");
		if (sReload!=null&&sReload.equals("S")) {
			this.startNode("code");
				this.startNode("source");
				this.addTextNode("text", "goRefresh()");
				this.endNode("source");
			this.endNode("code");
			
		}
	}

	@Override
	public Serializable getKey() {
		return null;
	}

	@Override
	protected void postEndDocument() throws Exception {
	}

	
}
