package pss.www.ui;

import java.text.DecimalFormat;

import pss.core.services.fields.JObject;
import pss.core.winUI.responsiveControls.JFormEditFromToResponsive;
import pss.core.winUI.responsiveControls.JFormTwoPropertiesResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.content.generators.JXMLContent;

public class JWebEditFromToResponsive extends JWebEditResponsive {

	boolean bPopUpIcon = true;

	public void setPopupIcon(boolean b) {
		this.bPopUpIcon=b;
	}

	@Override
	public String getComponentType() {
		return "fromto_field_responsive";
	}

	public static JWebEditFromToResponsive create(JWebViewComposite parent,  JFormEditFromToResponsive zEdit) throws Exception {
		JWebEditFromToResponsive webText=new JWebEditFromToResponsive();
		webText.takeAttributesFormControlResponsive(zEdit);
		if (parent!=null) parent.addChild(zEdit.getName(), webText);
		webText.setPopupIcon(zEdit.hasPopupIcon());
		return webText;
	}

	@Override
	protected void widgetToXML(JXMLContent zContent) throws Exception {
		zContent.setAttribute("two_prop", ((JFormTwoPropertiesResponsive)getControl()).useTwoFields());
		zContent.setAttribute("propname_to", ((JFormTwoPropertiesResponsive)getControl()).getFieldPropTo());
		zContent.setAttribute("propname_from", ((JFormTwoPropertiesResponsive)getControl()).getFieldPropFrom());
		super.widgetToXML(zContent);
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
