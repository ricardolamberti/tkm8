/*
 * Created on 10-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.generators;

import java.io.Serializable;

import org.w3c.dom.Node;

import pss.core.win.JBaseWin;
import pss.www.ui.processing.JXMLContentGenerator;

public class JXMLContent {

	private JBasicXMLContentGenerator oGenerator;

	public JXMLContent(JBasicXMLContentGenerator zGenerator) {
		this.oGenerator=zGenerator;
	}

	//
	// JXMLContent implementation
	//

	public void discard() {
		this.oGenerator=null;
	}

	public void endNode(String zNode) throws Exception {
		this.oGenerator.endNode(zNode);
	}

	public void setAttribute(String zAttrName, String zValue) throws Exception {
		this.oGenerator.setAttribute(zAttrName, zValue);
	}
	public void setAttributeEscape(String zAttrName, String zValue) throws Exception {
		this.oGenerator.setAttributeEscape(zAttrName, zValue);
	}

	public void setAttributeNLS(String zAttrName, String zValue) throws Exception {
		this.oGenerator.setAttributeNLS(zAttrName, zValue);
	}

	public void setAttribute(String zAttrName, boolean zValue) throws Exception {
		this.oGenerator.setAttribute(zAttrName, zValue);
	}

	public void setAttribute(String zAttrName, long zValue) throws Exception {
		this.oGenerator.setAttribute(zAttrName, zValue);
	}

	public void setAttribute(String zAttrName, int zValue) throws Exception {
		this.oGenerator.setAttribute(zAttrName, zValue);
	}

	public void setAttribute(String zAttrName, double zValue) throws Exception {
		this.oGenerator.setAttribute(zAttrName, zValue);
	}

	public void setAttribute(String zAttrName, float zValue) throws Exception {
		this.oGenerator.setAttribute(zAttrName, zValue);
	}

	public void startNode(String zNode) throws Exception {
		this.oGenerator.startNode(zNode);
	}

	public void addTextNode(String zNode, String zValue) throws Exception {
		this.oGenerator.addTextNode(zNode, zValue);
	}

	public void addTextNodeNLS(String zNode, String zValue) throws Exception {
		this.oGenerator.addTextNodeNLS(zNode, zValue);
	}

	public void setNodeText(String zValue) throws Exception {
		this.oGenerator.setNodeText(zValue);
	}

	public JXMLContentGenerator getGenerator() {
		return this.oGenerator;
	}


	public void embed(Node zNode) throws Exception {
		this.oGenerator.embed(zNode);
	}

	public String addObject(JBaseWin zObject) throws Exception {
		if (!this.oGenerator.isPageGenerator()) {
			throw new RuntimeException("Only page generators can register objects to be sent to client");
		}
		return ((JXMLPageGenerator) this.oGenerator).registerObject(zObject);
	}
	public String addObject(String pos,JBaseWin zObject) throws Exception {
		if (!this.oGenerator.isPageGenerator()) {
			throw new RuntimeException("Only page generators can register objects to be sent to client");
		}
		return ((JXMLPageGenerator) this.oGenerator).registerObject(pos,zObject);
	}
	public String addObjectObjTemp(Serializable zObject) throws Exception {
		if (!this.oGenerator.isPageGenerator()) {
			throw new RuntimeException("Only page generators can register objects to be sent to client");
		}
		return ((JXMLPageGenerator) this.oGenerator).registerObjectObjTemp(zObject);
	}
	public String addObjectObj(Serializable zObject) throws Exception {
		if (!this.oGenerator.isPageGenerator()) {
			throw new RuntimeException("Only page generators can register objects to be sent to client");
		}
		return ((JXMLPageGenerator) this.oGenerator).registerObjectObj(zObject);
	}
	public Object getObject(String pos) throws Exception {
		if (!this.oGenerator.isPageGenerator()) {
			throw new RuntimeException("Only page generators can register objects to be sent to client");
		}
		return ((JXMLPageGenerator) this.oGenerator).getRegisterObjectObj(pos);	
	}

	
	
}