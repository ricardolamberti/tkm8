package pss.www.ui;

import java.text.DecimalFormat;

import pss.core.services.fields.JObject;
import pss.core.winUI.responsiveControls.JFormEditResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.content.generators.JXMLContent;

public class JWebEditResponsive  extends JWebTextComponent  implements JWebActionable {

	boolean bPopUpIcon = true;

	public void setPopupIcon(boolean b) {
		this.bPopUpIcon=b;
	}

	@Override
	public String getComponentType() {
		return "text_field_responsive";
	}

	public static JWebEditResponsive create(JWebViewComposite parent,  JFormEditResponsive zEdit) throws Exception {
		JWebEditResponsive webText=new JWebEditResponsive();
		webText.takeAttributesFormControlResponsive(zEdit);
		if (parent!=null) parent.addChild(zEdit.getName(), webText);
		webText.setPopupIcon(zEdit.hasPopupIcon());
		return webText;
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
	@Override
	protected void sizeToXML(JXMLContent zContent) throws Exception {
		this.addPopupIcon(zContent);
		super.sizeToXML(zContent); // SAX does not replace existing attributes
	}

	private void addPopupIcon(JXMLContent zContent) throws Exception {
		if (!this.hasPopupIcon()) return;
		zContent.setAttribute("pop_up_icon", "fa fa-calculator");
	}

	protected boolean hasPopupIcon() throws Exception {
		if (!this.bPopUpIcon) return false;
		String type=this.getController().GetObjectType();
		if (type.equals(JObject.JFLOAT)) return true;
		if (type.equals(JObject.JINTEGER)) return true;
		if (type.equals(JObject.JCURRENCY)) return true;
		if (type.equals(JObject.JLONG)) return true;
		if (type.equals(JObject.JUNSIGNED)) return true;
		return false;
	}
  public JWebAction getWebAction() throws Exception {
  	if (!this.hasToRefreshForm()) return null;
  	return this.getObjectProvider().getWebSourceAction();
  }
}