package pss.www.ui;

import java.awt.Dimension;

import pss.core.services.fields.JObject;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.winUI.controls.JControlTree;
import pss.core.winUI.responsiveControls.JFormTreeComponentResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.content.generators.JXMLContent;

public class JWebTreeResponsive  extends JWebAbstractValueChooser implements JWebActionable {

	private String sOnChangeEvent;
  private String title;	
  private String icon;	
  public String getIcon() {
		return icon;
	}

	public void setIcon(String Icon) {
		this.icon =Icon;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public JWebTreeResponsive() {
		sOnChangeEvent="";
		bConvertForeignCharsForWeb = true;
	}

	@Override
	public String getComponentType() {
		return "treecomponent_responsive";
	}

	@Override
	protected Dimension getDefaultSize() {
		return new Dimension(150, 24);
	}

	public void setOnChangeEvent(String zOnChangeEvent) {
		sOnChangeEvent=zOnChangeEvent;
	}


	public void setController(JObject<?> zDataType, JWebLabel zLabel, boolean zRequired, int zMaxLength, String zInputAttrs, JControlTree zControlCombo) throws Exception {
		super.setController(zDataType, zLabel,  zRequired,  zMaxLength,  zInputAttrs);
		JWebValueChooserModel zModel=new JControlTreeValueChooserModel(getController(), zControlCombo);
		setModel(zModel);
	}
	
	public static JWebTreeResponsive create(JWebViewComposite parent, JFormTreeComponentResponsive control) throws Exception {
		JWebValueChooserModel zModel=new JControlTreeValueChooserModel(control, control.getControlTree());
		JWebTreeResponsive webTree=new JWebTreeResponsive();
		webTree.setModel(zModel);
		webTree.setResponsive(control.isResponsive());
		webTree.setTitle(control.getTitle());
		webTree.setIcon(control.getIcon());
		webTree.takeAttributesFormControlResponsive(control);
		if (control.ifReadOnly()) 
			webTree.setEditable(false);
		if (parent!=null) 
			parent.addChild(control.getName(), webTree);
		return webTree;
	}

	@Override
	protected String getState() throws Exception {
		return (getForm().isModoConsulta()||!isEditable()) ? null : "edit";
	}
	
  public JWebAction getWebAction() throws Exception {
  	if (!this.hasToRefreshForm()) return null;
  	return this.getObjectProvider().getWebSourceAction();
  }
	protected void showAll(JXMLContent zContent) throws Exception {
		JIterator<String> oValueIt=this.getModel().getValues(false).getKeyIterator();
		String sValue;
		while (oValueIt.hasMoreElements()) {
			sValue=oValueIt.nextElement();
		//	if (sValue.equals("")) continue;
			if (sValue.equals("root_")) continue;
			zContent.startNode("item");
			zContent.setAttribute("id", this.getModel().getId(sValue));
			zContent.setAttribute("id_tree", this.getModel().getIdTree(sValue));
			zContent.setAttribute("parent", this.getModel().getParent(sValue));
			zContent.setAttribute("elegible", this.getModel().getElegible(sValue));
		//	System.out.println(this.getModel().getId(sValue)+"-"+this.getModel().getParent(sValue)+"-"+this.getModel().getIdTree(sValue));
			if (bConvertForeignCharsForWeb)
				zContent.setAttributeNLS("description", JTools.replaceForeignCharsForWeb(this.getModel().getDescription(sValue)));
			else
				zContent.setAttributeEscape("description", this.getModel().getDescription(sValue));

			zContent.setAttributeNLS("icon", this.getModel().getIcon(sValue).getURL());
			zContent.setAttributeNLS("icon_open", this.getModel().getIconOpen(sValue).getURL());

			if (sValue.equals(this.sSelectedValue)) zContent.setAttribute("selected", true);
		
			zContent.endNode("item");
		}
	}  
	protected void showSelected(JXMLContent zContent,String value, boolean first) throws Exception {
		JIterator<String> oValueIt=this.getModel().getValues(false).getKeyIterator();
		String sValue;
		while (oValueIt.hasMoreElements()) {
			sValue=oValueIt.nextElement();
			if (first) {
				if ( !this.getModel().getId(sValue).equals(value)) continue;
			} else {
				if ( !this.getModel().getIdTree(sValue).equals(value)) continue;
			}
			showSelected(zContent, this.getModel().getParent(sValue),false);
			zContent.startNode("item");
			zContent.setAttribute("id", this.getModel().getId(sValue));
			zContent.setAttribute("id_tree", this.getModel().getIdTree(sValue));
			zContent.setAttribute("parent", this.getModel().getParent(sValue));
			zContent.setAttribute("elegible", false);

			if (bConvertForeignCharsForWeb)
				zContent.setAttributeNLS("description", JTools.replaceForeignCharsForWeb(this.getModel().getDescription(sValue)));
			else
				zContent.setAttributeNLS("description", this.getModel().getDescription(sValue));

			zContent.setAttributeNLS("icon", this.getModel().getIcon(sValue).getURL());
			zContent.setAttributeNLS("icon_open", this.getModel().getIconOpen(sValue).getURL());

			if (sValue.equals(this.sSelectedValue)) {
				zContent.setAttribute("selected", true);
			}
			zContent.endNode("item");
		}
	}  
	@Override
	protected void widgetToXML(JXMLContent zContent) throws Exception {
		zContent.setAttribute("onchange_event", sOnChangeEvent);
		zContent.setAttribute("title", getTitle());
		zContent.setAttribute("value", this.getSelectedValue());
		zContent.setAttribute("editable", this.isEditable());
		zContent.setAttributeNLS("icon", "pss_icon/"+getIcon());
			
		if (isEditable()) 
			showAll(zContent);
		else showSelected(zContent,this.getSelectedValue(),true);
	}
	
}
