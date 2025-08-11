package pss.www.ui;

import pss.common.security.BizUsuario;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.winUI.controls.JFormLocalForm;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabResponsive;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebServerAction;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;

public class JWebTabPanelResponsive extends JWebPanelResponsive implements JWebControlInterface  {

	JMap<String, JFormTabResponsive> tabs;
	String oDato;
	boolean preview;
	private String sContainerPreview;
	public String getContainerPreview() {
		return sContainerPreview;
	}
	public void setContainerPreview(String sContainerPreview) {
		this.sContainerPreview = sContainerPreview;
	}

	public boolean isPreview() {
		return preview;
	}
	public void setPreview(boolean preview) {
		this.preview = preview;
	}
	public String getActive() {
		return oDato;
	}
	public void setActive(String active) {
		this.oDato = active;
	}
	public JWebTabPanelResponsive() {
		super();
	}
	@Override
	public void destroy() {

		super.destroy();
	}
	@Override
	public String getObjectName() {
		return getName();
	}
	
	public static JWebTabPanelResponsive create(JWebViewComposite parent,  JFormTabPanelResponsive control,String onlyThisControl) throws Exception {
		JWebTabPanelResponsive table = new JWebTabPanelResponsive();
		table.takeAttributesFormControlResponsive(control);
		 if (parent!=null)
    	parent.addChild(table.getObjectName(), table);
		table.setPreview(parent.findJWebWinForm().isPreview());
		table.setContainerPreview(parent.findJWebWinForm().getContainerPreview());
		table.setValue(control.getValue());
		table.setTabs(control.getTabs());
		table.setActive(control.getActive());
		table.addAllComponentes(control, onlyThisControl);

		return table;
	}
	
	public void setTabs(JMap<String, JFormTabResponsive> zTabs) throws Exception {
		this.tabs=zTabs;
		
	}
	public JMap<String, JFormTabResponsive> getTabs() {
		return tabs;
	}

	@Override
	public String getComponentType() {
		return "tabpanel_responsive";
	}
	

	@Override
	public void setEditable(boolean editable) throws Exception {
		
	}
	@Override
	public void clear() throws Exception {
		oDato=null;
	}
	@Override
	public String getSpecificValue() throws Exception {
		return oDato;
	}
	@Override
	public String getDisplayValue() throws Exception {
		return oDato;
	}
	@Override
	public void setValue(String zVal) throws Exception {
		oDato=zVal;
	}
	@Override
	public void setValueFromUIString(String zVal) throws Exception {
		oDato=zVal;
	}
	
	@Override
	public void setController(JWebFormControl control) {
		
	}
	@Override
	public void onShow(String modo) throws Exception {
		
	}
	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
		
	}


	boolean isActive(String tab) throws Exception {
		if (oDato==null) return false;
		return oDato.equals(tab);
	}
	
	public void verifyTabActive() throws Exception {
		String first=null;
		JIterator<String> iter=this.getTabs().getKeyIterator();
		while (iter.hasMoreElements()) {
			String key = iter.nextElement();
			if (oDato!=null && this.oDato.equals(key)) return;
			if (first==null) first=key;
		}		
		// ninguna de las tabs posibles es la activa. activo el primer tab
		this.setValue(first);
	}

	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		JIterator<String> oKeyIt=this.getTabs().getKeyIterator();
		if (this.getForm() != null)
			zContent.setAttribute("form_name", this.getForm().getFormName());
		this.verifyTabActive();
		zContent.setAttribute("tab_select", getActive());
		while (oKeyIt.hasMoreElements()) {
			String key = oKeyIt.nextElement();
			JFormTabResponsive tab = tabs.getElement(key);
			
			zContent.startNode("tab");
			zContent.setAttribute("id", tab.getIdControl());
			zContent.setAttribute("ondemand", tab.isOnDemand());
			zContent.setAttributeNLS("title", tab.getTitle());
	
			if (!(tab instanceof JFormLocalForm)) {
				if (tab.isOnDemand() && !isActive(key)) {
					JWebLink link = new JWebLink();
					JWebServerAction action = null;
					if (isPreview()) {
						action = JWebActionFactory.createPartialRefreshPreviewFormAction(tab.getAction(), this.findJWebWinForm(), null, getContainerPreview(), this.getObjectProvider().getProviderName(), "tab", "");
					} else
						action = JWebActionFactory.createPartialRefreshFormAction(tab.getAction(), this.findJWebWinForm(), null, this.getObjectProvider().getProviderName(), "tab", "");
					action.setSubmit(false);
					link.setWebAction(action);
					link.toXML(zContent);

				} else if (isActive(key)) {
					if (BizUsuario.getUsr().getObjBusiness().useMaximizeTabs()) {
						JWebLink link=new JWebLink();
						link.setWebAction(JWebActionFactory.createMaximizeAction(tab.getAction(), this.getObjectProvider(), this.getForm().isAlta(),tab.GetDisplayName()));
						link.toXML(zContent);
					}
				}
			}
//			if (isActive(key))
//				zContent.setAttribute("active", true);
			zContent.endNode("tab");
			
		}
		super.componentToXML(zContent);
	}

	@Override
	protected void componentToXMLposLayout(JXMLContent zContent) throws Exception {
		// generate children XML
		JIterator<JWebViewComponent> oChildrenIt=this.getChildren();
		JWebViewComponent oComp;
		while (oChildrenIt.hasMoreElements()) {
			oComp=oChildrenIt.nextElement();
//			if (oComp instanceof JWebTabResponsive) {
//				JWebTabResponsive tab = (JWebTabResponsive)oComp;
//				if (!tab.getId().equals(getActive()))
//					continue;
//			}
			if (oComp.isInTable()) continue;
			oComp.toXML(zContent);
		}
	}

}
