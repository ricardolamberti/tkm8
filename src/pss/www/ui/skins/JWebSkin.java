/*
 * Created on 19-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.skins;

import java.awt.Insets;
import java.io.File;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.xpath.XPathAPI;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pss.JPath;
import pss.core.tools.JExcepcion;
import pss.core.tools.XML.JXMLElementFactory;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.www.ui.processing.JXMLRepresentable;

public class JWebSkin {

	private Element oRootElement;
	private String sSkinBasePath;
	private String sLayoutGeneratorPath;
	private boolean bNeedsLoading;
	private long lLastModifTime;
	private Insets oViewAreaOffsets;
	private JMap<String, Icon> oResources;
	private JMap<String, Node> hStyles;
	private JMap<String, Node> hStylesChild;

	public JWebSkin( String zSkinBasePath,String zLayoutGeneratorPath) {
		this.sSkinBasePath=JPath.normalizePath(zSkinBasePath);
		sLayoutGeneratorPath = zLayoutGeneratorPath;
		this.bNeedsLoading=true;
		this.oResources=JCollectionFactory.createMap();
	}
	
//  static JMap<String,String> skinsKnown = null;
//  static JMap<String,String> getSkinsKnown() {
//  	if( skinsKnown!=null) return skinsKnown;
//  	JMap<String,String> cc = JCollectionFactory.createMap();
//  	cc.addElement("skin_default", "skin por defecto");
//  	cc.addElement("skin_baires", "skin Baires");
//  	cc.addElement("skin_spring", "skin Spring");
//  	cc.addElement("skin_sirac", "skin para SIRAC");
//  	cc.addElement("skin_simple", "skin simple");
//  	cc.addElement("skin_blue", "skin blue");
//  	cc.addElement("skin_red", "skin red");
//  	cc.addElement("skin_tkm", "skin TKM");
//  	cc.addElement("skin_orange", "skin orange");
//  	cc.addElement("skin_green", "skin green");
//  	cc.addElement("skin_compact", "skin compact");
//  	return cc;
//  }
//  public static JWins  getSkins() throws Exception {
//    return  JWins.createVirtualWinsFromMap(getSkinsKnown());
//  }


	//
	// PUBLIC API
	//  

	public JXMLRepresentable toXML() {
		return new JWebSkinXMLRepresentation(this);
	}

	public Element getRootElement() throws Exception {
		this.loadIfNeeded();
		return this.oRootElement;
	}

	public String getRelativeBasePath() {
		String sPssPath=JPath.normalizePath(JPath.PssMainPath());
		if (this.sSkinBasePath.toLowerCase().startsWith(sPssPath.toLowerCase())) {
			return JPath.normalizePath(this.sSkinBasePath.substring(sPssPath.length()));
		} else {
			throw new RuntimeException("Path is not a Pss child path: "+this.sSkinBasePath);
		}
	}

	public Node getStyle(String zStyleId) throws Exception {
		if (!createGenerator().needOldCss()) return null;
		if (hStyles==null) hStyles=JCollectionFactory.createMap();
		if (hStyles.containsKey(zStyleId)) return hStyles.getElement(zStyleId);

		XObject oObj=XPathAPI.eval(this.getRootElement(), "styles/style[@id='"+zStyleId+"']");
		if (oObj==null) {
			hStyles.addElement(zStyleId, null);
			return null;
		}
		NodeList oNodes=oObj.nodelist();
		if (oNodes.getLength()==0) {
			hStyles.addElement(zStyleId, null);
			return null;
		}
		if (oNodes.getLength()>1) {
			Node node;
			for(int i=0;i<oNodes.getLength();i++) {
				node=oNodes.item(i);
				if (node.getAttributes().getNamedItem("action")!=null) continue;
				if (node.getAttributes().getNamedItem("inner")!=null) continue;
	 			hStyles.addElement(zStyleId, node);
	 			return node;
			}
 			hStyles.addElement(zStyleId, null);
			return null;
		}
		Node node=oNodes.item(0);
		hStyles.addElement(zStyleId, node);
		return node;
	}

	public Node getStyleChild(String zStyleId, String zRelativeXPath) throws Exception {
		if (!createGenerator().needOldCss()) return null;
		if (hStylesChild==null) hStylesChild=JCollectionFactory.createMap();
		String key=zStyleId+"-"+zRelativeXPath;
		if (hStylesChild.containsKey(key)) return hStylesChild.getElement(key);
		Node oStyle=this.getStyle(zStyleId);
		if (oStyle==null) {
			hStylesChild.addElement(key, null);
			return null;
		}
		XObject oObj=XPathAPI.eval(oStyle, zRelativeXPath);
		if (oObj==null) {
			hStylesChild.addElement(key, null);
			return null;
		}
		NodeList oNodes=oObj.nodelist();
		if (oNodes.getLength()!=1) {
			hStylesChild.addElement(key, null);
			return null;
		}
		Node node=oNodes.item(0);
		hStylesChild.addElement(key, node);
		return node;
	}
	

	public Insets getViewAreaOffsets() throws Exception {
		if (this.oViewAreaOffsets!=null) {
			return this.oViewAreaOffsets;
		}
		this.loadIfNeeded();
		int iTopOffset=0, iBottomOffset=0, iLeftOffset=0, iRightOffset=0;
		ILayoutGenerator layout = createGenerator();
		iTopOffset= layout.getTopMarginCanvas();
		iLeftOffset= layout.getLeftMarginCanvas();
		iBottomOffset= layout.getBottomMarginCanvas();
		iRightOffset= layout.getRightMarginCanvas();
		this.oViewAreaOffsets=new Insets(iTopOffset, iLeftOffset, iBottomOffset, iRightOffset);
		return this.oViewAreaOffsets;
	}
	



	//
	// RESOURCE PROVIDING API
	//

	public Icon getIcon(Element zIconDOM) throws Exception {
		Icon oIcon=null;
		if (zIconDOM.hasAttribute("file")) {
			String sPath=JPath.normalizePath(zIconDOM.getAttribute("file"));
			oIcon=this.oResources.getElement(sPath);
			if (oIcon==null) {
				oIcon=new ImageIcon(this.getAbsoluteResource(sPath));
				this.oResources.addElement(sPath, oIcon);
			}
		}
		return oIcon;
	}

	/**
	 * Answers the path relative to Pss for the given icon file name.
	 */
	/*
	 * public String getRelativePathForIcon(String zIconFileName) { String sKey = "pss_icon/" + zIconFileName; if (!this.oResources.containsKey(sKey)) { String sPath; if (new File(this.sSkinBasePath + "/" + sKey).exists()) { String sSkinName; int iLastSep = this.sSkinBasePath.lastIndexOf('/'); if (iLastSep < 0) { sSkinName = this.sSkinBasePath; } else { sSkinName = this.sSkinBasePath.substring(iLastSep+1); } sPath = "www/ui/skins/" + sSkinName + "/pss_icon/" + zIconFileName; } else { sPath = null; } this.oResources.addElement(sKey, sPath); return sPath; } return (String) this.oResources.getElement(sKey); }
	 */
	/*
	 * public JWebIcon getPssIcon(String zFileName) throws Exception { String sKey = "pss_icons/" + zFileName; if (!this.oResources.containsKey(sKey)) { if (new File(this.sSkinBasePath + "/" + sKey).exists()) { this.oResources.addElement(sKey, null); } else { this.oResources.addElement(sKey, null); } } return (JWebIcon) this.oResources.getElement(sKey); }
	 */

	//
	// INTERNAL METHODS
	//  
	private synchronized void loadIfNeeded() throws Exception {
		if (this.bNeedsLoading) {
			this.load();
			this.bNeedsLoading=false;
		}
	}

	long getLastModificationTime() {
		return this.lLastModifTime;
	}

	void setNeedsReloading() {
		this.bNeedsLoading=true;
		this.oViewAreaOffsets=null;
	}

	ILayoutGenerator lg;
	public ILayoutGenerator createGenerator() throws Exception {
		if (lg!=null) return lg;
		lg = (ILayoutGenerator) Class.forName(sLayoutGeneratorPath).newInstance();
		return lg;
	}

	
	protected void load() throws Exception {
		JXMLElementFactory factory=JXMLElementFactory.getInstance();
		String sFileURL=this.sSkinBasePath+"/skin.xml";
		this.oRootElement=factory.parseURIToElement(sFileURL);
		if (this.oRootElement==null) {
			JExcepcion.SendError("No root node found in URL '"+sFileURL+"'");
		}
		this.lLastModifTime=new File(sFileURL).lastModified();
		this.oViewAreaOffsets=null;
	}

	private URL getAbsoluteResource(String zNormalizedRelativePath) throws Exception {
		URL resource=null;
		String sPath=this.sSkinBasePath+"/"+zNormalizedRelativePath;
		try {
			URL url=new URL("file", "localhost", 0, sPath);
			Object content=url.getContent();
			if (content==null) {
				JExcepcion.SendError("Resource not found: "+sPath);
			}
			resource=url;
		} catch (Exception ex) {
			JExcepcion.SendError("Resource not found: "+sPath);
		}
		return resource;
	}
	

}
