package pss.www.ui;

import pss.www.platform.content.generators.JXMLContent;


public class JWebSplit extends JWebViewComposite {

	public static final String ORIENT_VERTICAL = "V";
	public static final String ORIENT_HORIZONAL = "H";
	
	String   	  posSplitUser = null;
	String  	  literalPosSplit;


	String 			orientation = ORIENT_VERTICAL;
	JWebViewComposite[] panels = new JWebViewComposite[2];
	
	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		this.containerToXML(zContent);

		zContent.startNode("panel_a");
		panels[0].toXML(zContent);
		zContent.endNode("panel_a");
		
		zContent.startNode("panel_b");
		panels[1].toXML(zContent);
		zContent.endNode("panel_b");
				
	}	
	
	@Override
	protected void containerToXML(JXMLContent content) throws Exception {
		content.setAttribute("pos_split", getLiteralPosSplit());
		content.setAttribute("orientation", orientation);
	}

	@Override
	public String getComponentType() {
		return "split";
	}

	
	public String getPosSplit() {
		return posSplitUser;
	}

	
	public void setPosSplit(String posSplit) {
		this.posSplitUser=posSplit;
	}

	
	public String getOrientation() {
		return orientation;
	}

	
	public void setOrientation(String orientation) {
		this.orientation=orientation;
	}

	public String getLiteralPosSplit() {
		if (getPosSplit()!=null) {
			return getPosSplit();
		}
		return literalPosSplit;
	}

	public void setLiteralPosSplit(String literalPosSplit) {
		this.literalPosSplit = literalPosSplit;
	}
	
	public JWebViewComposite[] getPanels() {
		return panels;
	}

	
	public void setPanelA(String name,JWebViewComposite panels) throws Exception {
		this.panels[0]=panels;
		addChild(name==null?"panel_a":name,panels);
	}

	public void setPanelB(String name,JWebViewComposite panels) throws Exception {
		this.panels[1]=panels;
		addChild(name==null?"panel_a":name,panels);
	}
	

	
	
}
