package pss.www.ui;

import java.awt.Dimension;

import pss.core.services.fields.JIntervalDate;
import pss.core.services.fields.JIntervalDateTime;
import pss.core.services.fields.JObject;
import pss.core.tools.JDateTools;
import pss.core.winUI.responsiveControls.JFormIntervalCDatetimeResponsive;
import pss.core.winUI.responsiveControls.JFormTwoPropertiesResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.input.JWebInputFormat;

public class JWebIntervalDateTimeChooserResponsive  extends JWebTextComponent  implements JWebActionable, JWebOptions {

	protected String oValue;
  public String options;
  public boolean withTime;
  public String outFormatjs;
  public String outFormat;
  
  public String getOutFormatJS() {
		return outFormatjs;
	}

	public void setOutFormatJS(String outFormat) {
		this.outFormatjs = outFormat;
	}
  public String getOutFormat() {
		return outFormat;
	}

	public void setOutFormat(String outFormat) {
		this.outFormat = outFormat;
	}

	public boolean isWithTime() {
		return withTime;
	}

	public void setWithTime(boolean withTime) {
		this.withTime = withTime;
	}

	//
	// CONSTRUCTORS
	//
	public JWebIntervalDateTimeChooserResponsive() {
	}

	//
	// PUBLIC API
	//
	
	@Override
	public String getValueAsDBString() throws Exception {
		Object value = this.getValue();
		if (value == null) {
			return "";
		} else {
			return value.toString();
		}
	}
	
	public String getOptions() {
		return options;
	}

	public void setOptions(String ops) {
		this.options = ops;
	}

	@Override
	protected JWebInputFormat resolveDefaultInputFormat() throws Exception {
		return JWebInputFormat.string(50,null);
	}

	@Override
	public void destroy() {
		this.oValue=null;
		this.oInputFormat=null;
		super.destroy();
	}

	@Override
	public String getComponentType() {
		return "interval_date_chooser_responsive";
	}

	@Override
	public Dimension getDefaultSize() {
		return new Dimension(200, 20);
	}

	//
	// INTERNAL IMPLEMENTATION
	//

	//
	// generation
	//


	@Override
	protected void widgetToXML(JXMLContent zContent) throws Exception {
		zContent.setAttribute("out_format", getOutFormatJS());
		zContent.setAttribute("with_time", isWithTime());
		zContent.setAttribute("two_prop", ((JFormTwoPropertiesResponsive)getControl()).useTwoFields());
		zContent.setAttribute("propname_to", ((JFormTwoPropertiesResponsive)getControl()).getFieldPropTo());
		zContent.setAttribute("propname_from", ((JFormTwoPropertiesResponsive)getControl()).getFieldPropFrom());
		super.widgetToXML(zContent);
	}

	//
	// value providing
	//

	@Override
	public String getValueAsUIString() throws Exception {
		if (this.oValue==null) {
			return "";
		} else {
			return this.oValue;
		}
	}

	@Override
	public void setValueFromUIString(String zValue) throws Exception {
		String sDateString=zValue;
		this.setValue(sDateString);
		
	}
	
  public void setValue(JObject zVal) throws Exception {
	 if (zVal==null)
			this.clear();
		else
			if (zVal instanceof JIntervalDateTime) {
				JIntervalDateTime prop = (JIntervalDateTime) zVal;
				this.setValue(JDateTools.DateToString(prop.getStartDateValue(),getOutFormat())+" - "+JDateTools.DateToString(prop.getEndDateValue(),getOutFormat()));
			} else if (zVal instanceof JIntervalDate){
				JIntervalDate prop = (JIntervalDate) zVal;
				this.setValue(JDateTools.DateToString(prop.getStartDateValue(),getOutFormat())+" - "+JDateTools.DateToString(prop.getEndDateValue(),getOutFormat()));
			}
}



	@Override
	public void setValueFromDBString(String zDBString) throws Exception {
		if (zDBString==null||zDBString.trim().length()<1) {
			this.oValue=null;
		} else {
			this.oValue=zDBString;
		}
	}

	@Override
	public Object getValue() {
		return this.oValue;
	}

	@Override
	public void setValue(Object date) {
		this.oValue= (String)date;
	}

	public static JWebIntervalDateTimeChooserResponsive create(JWebViewComposite parent, JFormIntervalCDatetimeResponsive control) throws Exception {
		JWebIntervalDateTimeChooserResponsive webDate=new JWebIntervalDateTimeChooserResponsive();
		webDate.takeAttributesFormControlResponsive(control);
		webDate.setWithTime(control.isWithTime());
		webDate.setOutFormatJS(control.getOutFormatJS());
		webDate.setOutFormat(control.getOutFormat());
		if (parent!=null) parent.addChild(control.getName(), webDate);
		webDate.setOptions(control.getOptions());
		return webDate;
	}

  public JWebAction getWebAction() throws Exception {
  	if (!this.hasToRefreshForm()) return null;
  	return this.getObjectProvider().getWebSourceAction();
  }

	@Override
	protected String getState() throws Exception {
		return (this.getForm().isModoConsulta()||!isEditable()) ? null : "edit";
	}
	

}
