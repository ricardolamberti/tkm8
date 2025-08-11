package pss.www.ui;

import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;

public class JWebTabResponsive extends JWebViewInternalComposite implements JWebControlInterface  {
	String title;

	String id;
	boolean preview;
	boolean onDemand;


	public boolean isOnDemand() {
		return onDemand;
	}
	public void setOnDemand(boolean onDemand) {
		this.onDemand = onDemand;
	}
	public JWebTabResponsive() {
		super();
	}
	@Override
	public void destroy() {

		super.destroy();
	}
	
	public static JWebTabResponsive create(JWebViewComposite parent, JFormTabPanelResponsive control) throws Exception {
		JWebTabResponsive table = new JWebTabResponsive();
		table.takeAttributesFormControlResponsive(control);
		if (parent != null) {
			table.setPreview(parent.findJWebWinForm().isPreview());
			parent.addChild(table.getObjectName(), table);
		}

		return table;
	}
	
	public boolean isPreview() {
		return preview;
	}
	public void setPreview(boolean preview) {
		this.preview = preview;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}	
	@Override
	public String getComponentType() {
		return "tab_responsive";
	}
	
	@Override
	public void setEditable(boolean editable) throws Exception {
		
	}
	@Override
	public void clear() throws Exception {
		
	}
	@Override
	public String getSpecificValue() throws Exception {
		return null;
	}
	@Override
	public String getDisplayValue() throws Exception {
		return null;
	}
	@Override
	public void setValue(String zVal) throws Exception {
		
	}
	@Override
	public void setValueFromUIString(String zVal) throws Exception {
		
	}
	@Override
	public void setController(JWebFormControl control) {
		
	}
	@Override
	public void onShow(String modo) throws Exception {
		
	}
	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
		zContent.setAttribute("id", id);

	}
	
	

}
