package pss.www.ui;

import pss.core.winUI.responsiveControls.JFormImageCardResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.content.generators.JXMLContent;

public class JWebImageCardResponsive extends JWebImageResponsive implements JWebActionable,JWebIconHolder,JWebLabelHolder {

	JWebIcon icon;
	JWebIcon maximizeIcon;
	String label;
	boolean content;

	public boolean isContent() {
		return content;
	}

	public void setContent(boolean content) {
		this.content = content;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public JWebIcon getIcon() {
		return icon;
	}

	public void setIcon(JWebIcon icon) {
		this.icon = icon;
	}
	public JWebIcon getMaximizeIcon() {
		return maximizeIcon;
	} 

	public void setMaximizeIcon(JWebIcon icon) {
		this.maximizeIcon = icon;
	}
	@Override
	public String getLabel() {
		return label;
	}
	@Override
	public String getComponentType() {
		return "imagecard_responsive";
	}

	public static JWebImageCardResponsive create(JWebViewComposite parent,  JFormImageCardResponsive zEdit) throws Exception {
		JWebImageCardResponsive webText=new JWebImageCardResponsive();
		webText.initialize(zEdit.getSource(), null, null);
		webText.takeAttributesFormControlResponsive(zEdit);
//		webText.setLabelLateral();
		webText.setLabelLink(zEdit.getLabelLink());
		webText.setIcon(zEdit.getIcon());
		webText.setMaximizeIcon(zEdit.getMaximizeIcon());
		webText.setActionSource(zEdit.getActionSource());
		webText.setLabel(zEdit.getLabel());
		webText.setIdGraph(zEdit.getIdGraph());
		webText.setGraph(zEdit.getGraph());
		webText.setActionSource(zEdit.getActionSource());
		webText.setType(zEdit.getType());
		webText.setContent(zEdit.isContent());
		webText.setBackgroundImageFile(zEdit.getBackgroundImageFile());
		webText.setImageBackground(zEdit.getImageBackground());
		webText.setActionLink(zEdit.getActionLink());
		webText.setMinHeightResponsive(zEdit.getHeight());
		webText.setClassResponsive("panel "+webText.getClassResponsive());
		webText.setLabelLateral(null);
		webText.setLabelFontSize(zEdit.getLabelFontSize()); 
		webText.setLabelFontStyle(zEdit.getLabelFontStyle());
		webText.setLabelFontWeigth(zEdit.getLabelFontWeigth());
		if (parent != null && webText.hasImage())
			parent.addChild(zEdit.getName(), webText);
		return webText;
	}
	
	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		zContent.setAttribute("content", isContent());
		super.componentToXML(zContent);
	}


  public JWebAction getWebAction() throws Exception {
   	if (getActionLink()==null) {
    	if (!this.hasToRefreshForm()) return null;
    	JWebAction a= this.getObjectProvider().getWebSourceAction();
    	a.setActionParent(a);
    	return a;
   	}
		if (getForm()!=null&&!getForm().isModoConsulta())
			getActionLink().setUploadData(true);

//  	return JWebActionFactory.createViewAreaAndTitleAction(getActionLink(), getObjectProvider), false, getObjectProvider().getRegisteredObjectId());
  	JWebAction action =  JWebActionFactory.createViewAreaAndTitleAction(getActionLink(), getObjectProviderWithCard(), false, getObjectProviderWithCard().getRegisteredObjectId());
		if (((JFormImageCardResponsive) getControl()).getData()!=null)
			action.setData(((JFormImageCardResponsive) getControl()).getData());
		return action;
  
  }

}

