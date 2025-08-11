/*
 * Created on 05-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.generators;

import java.io.Serializable;

import org.w3c.dom.NodeList;

import pss.core.data.BizPssConfig;
import pss.www.ui.skins.JWebSkin;

/**
 * A generator which takes the suitable skin for the active session (or a default if there is no active session) and builds the XML according to it.
 * 
 * Created on 05-jun-2003
 * 
 * @author PSS
 */

public class JSkinGenerator extends JXMLComponentGenerator {

	@Override
	protected void doGenerate() throws Exception {
		JWebSkin oSkin=this.getSkinProvider().getSkinFor(this.getSession());

		this.startNode("skin");
		  this.setAttribute("base_path", "skin/"+oSkin.createGenerator().getSkinPath());
//		this.setAttribute("base_path", "pss");
		this.setAttribute("url_prefix", BizPssConfig.getPssConfig().getAppURLPrefix());

		this.embed(new JSessionInfoGenerator());
 
		// take children nodes (<layout> and <styles>) from the skin XML file
		NodeList oSkinChildrenDOMs=oSkin.getRootElement().getChildNodes();
		for(int i=0; i<oSkinChildrenDOMs.getLength(); i++) {
			this.embed(oSkinChildrenDOMs.item(i));
		}

		this.endNode("skin");
	}

	@Override
	protected String getBaseContentName() {
		return "skin";
	}

	@Override
	public Serializable getKey() {
		// Esto es para forzar el uso del cache independientemente del cambio de Id de Session
		try {
			return this.getSkinProvider().getSkinFor(this.getSession()).createGenerator().getSkinPath();
		} catch (Exception e) {
			return this.getSession().getId();
		}
		}


}
