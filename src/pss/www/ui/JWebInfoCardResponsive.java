package pss.www.ui;

import java.text.DecimalFormat;

import pss.core.win.actions.BizAction;
import pss.core.winUI.responsiveControls.JFormInfoCardResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.content.generators.JXMLContent;

public class JWebInfoCardResponsive   extends JWebTextComponent  implements JWebActionable,JWebIconHolder,JWebLabelHolder {

	JWebIcon icon;
	BizAction action;
	String label;
	String directLink;
	String dataparent;
	String classHeaderImage;
	String classHeaderText;
	boolean submit;


	public boolean isSubmit() {
		return submit;
	}

	public void setSubmit(boolean submit) {
		this.submit = submit;
	}

	public String getClassHeaderImage() {
		return classHeaderImage;
	}

	public void setClassHeaderImage(String classHeaderImage) {
		this.classHeaderImage = classHeaderImage;
	}

	public String getClassHeaderText() {
		return classHeaderText;
	}

	public void setClassHeaderText(String classHeaderText) {
		this.classHeaderText = classHeaderText;
	}
	
	public String getDataparent() {
		return dataparent;
	}

	public void setDataparent(String dataparent) {
		this.dataparent = dataparent;
	}

	public String getDirectLink() {
		return directLink;
	}

	public void setDirectLink(String directLink) {
		this.directLink = directLink;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public BizAction getAction() {
		return action;
	}

	public void setAction(BizAction action) {
		this.action = action;
	}

	public JWebIcon getIcon() {
		return icon;
	}

	public void setIcon(JWebIcon icon) {
		this.icon = icon;
	}
	
  public JWebIcon getMaximizeIcon() {
		return null;
	}
	@Override
	public String getLabel() {
		return label;
	}
	@Override
	public String getComponentType() {
		return "infocard_responsive";
	}

	public static JWebInfoCardResponsive create(JWebViewComposite parent,  JFormInfoCardResponsive zEdit) throws Exception {
		JWebInfoCardResponsive webText=new JWebInfoCardResponsive();
		webText.takeAttributesFormControlResponsive(zEdit);
		webText.setLabelLink(zEdit.getLabelLink());
		webText.setIcon(zEdit.getImagen());
		webText.setAction(zEdit.getAction());
		webText.setLabel(zEdit.getLabel());
		webText.setDirectLink(zEdit.getDirectLink());
		webText.setLabelLateral(null);
		webText.setDataparent(zEdit.getDataparent());
		webText.setSubmit(zEdit.isSubmit());
		webText.setClassResponsive("panel "+webText.getClassResponsive());
		webText.setClassHeaderImage(zEdit.getClassHeaderImage());
		webText.setClassHeaderText(zEdit.getClassHeaderText());
		if (parent!=null) 
			parent.addChild(zEdit.getName(), webText);
		return webText;
	}

	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		if (directLink!=null && !directLink.startsWith("newsession:")) 
				zContent.setAttribute("directlink", this.getDirectLink());
		if (getDataparent()!=null) 
			zContent.setAttribute("dataparent", this.getDataparent());
		zContent.setAttribute("class_header_image", getClassHeaderImage());
		zContent.setAttribute("class_header_text", getClassHeaderText());
		super.componentToXML(zContent);
	}

	@Override
	public boolean isFormatAllowed() throws Exception {
		if (this.getParent()!=null && this.getForm().isModoConsulta()) return true;
		if (this.getFormatter() instanceof DecimalFormat) return false;
		return true;
	}

	@Override
	protected char getGroupingSeparator() throws Exception {
		if (this.getParent()!=null && this.getForm().isModoConsulta()) return super.getGroupingSeparator();
		return ',';
	}

	@Override
	protected char getDecimalSeparator() throws Exception {
		if (this.getParent()!=null && this.getForm().isModoConsulta()) return super.getDecimalSeparator();
		return '.';
	}
	protected void componentToHelpXML(JXMLContent zContent) throws Exception {
		if (!hasHelp()) return;
		zContent.setAttribute("title", getLabelLink());
		zContent.setAttribute("copete", getHelp());

		super.componentToHelpXML(zContent);
	
	}
  public JWebAction getWebAction() throws Exception {
  	if (directLink!=null && directLink.startsWith("newsession:"))
  		return JWebActionFactory.createNewSession(JWebActionFactory.getCurrentRequest().getUICoordinator().getSession(),directLink.substring(11));
  	if (action==null) return null;
  	return JWebActionFactory.createViewAreaAndTitleAction(action, getObjectProviderWithCard(), isSubmit(), getObjectProviderWithCard().getRegisteredObjectId());
  }


}