package pss.www.ui;

import java.awt.Dimension;
import java.text.DateFormat;
import java.util.Date;

import pss.core.tools.JDateTools;
import pss.core.winUI.responsiveControls.JFormCDatetimeResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.input.JWebDateInputFormat;
import pss.www.ui.input.JWebDatetimeInputFormat;
import pss.www.ui.input.JWebInputFormat;

public class JWebDateTimeChooserResponsive  extends JWebTextComponent  implements JWebActionable, JWebOptions {

	protected Date oValue;
	private String dbFormat=null;
  public String options;
  
  
  public boolean withDate;
  public boolean withSecond;
	public boolean withTime;
 	public boolean isWithSecond() {
 		return withSecond;
 	}

 	public void setWithSecond(boolean withSecond) {
 		this.withSecond = withSecond;
 	}	
 	
 	public boolean isWithDate() {
		return withDate;
	}

	public void setWithDate(boolean withDate) {
		this.withDate = withDate;
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
	public JWebDateTimeChooserResponsive() {
	}

	//
	// PUBLIC API
	//
	
	public void setDbFormat(String v) {
		this.dbFormat=v;
	}
	
	public String getDbFormat() {
		if (this.dbFormat != null)
			return this.dbFormat;
		if (isWithTime() && !isWithDate() && isWithSecond())
			return "HH:mm:ss";
		if (isWithTime() && !isWithDate())
			return "HH:mm";
		if (isWithTime() && isWithSecond())
			return "dd/MM/yyyy HH:mm:ss";
		if (isWithTime())
			return "dd/MM/yyyy HH:mm";
		return "dd/MM/yyyy";
	}

	public String getDatePattern() throws Exception {
		return this.getInputFormat().getPattern();
	}

	public void setDatePattern(String zDatePattern) throws Exception {
		this.oInputFormat=JWebInputFormat.date(zDatePattern);
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String ops) {
		this.options = ops;
	}

	@Override
	protected JWebInputFormat resolveDefaultInputFormat() throws Exception {
		if (isWithTime())
			return JWebInputFormat.datetime(((JFormCDatetimeResponsive)this.getControl()).getFormatDate());
		return JWebInputFormat.date(((JFormCDatetimeResponsive)this.getControl()).getFormatDate());
	}

	@Override
	public void destroy() {
		this.oValue=null;
		this.oInputFormat=null;
		super.destroy();
	}

	@Override
	public String getComponentType() {
		return "date_chooser_responsive";
	}

	@Override
	public Dimension getDefaultSize() {
		return new Dimension(200, 20);
	}

	//
	// INTERNAL IMPLEMENTATION
	//

	protected String getJSFormat() throws Exception {
		this.getInputFormat();
		if (this.oInputFormat instanceof JWebDatetimeInputFormat) 
			return ((JWebDatetimeInputFormat) this.oInputFormat).getCalendarJSFormat();
		return ((JWebDateInputFormat) this.oInputFormat).getCalendarJSFormat();
	}

	private DateFormat getDateFormat() throws Exception {
		return (DateFormat) this.getInputFormat().getFormatter();
	}
	//
	// generation
	//

	private boolean usesCalendarWidget() throws Exception {
		return this.getJSFormat()!=null&&this.getJSFormat().length()>0;
	}

	@Override
	protected void widgetToXML(JXMLContent zContent) throws Exception {
		// If the object is readonly we do not display the calendar object
		if (this.usesCalendarWidget()&&this.isEditable()==true) {
			zContent.setAttribute("js_date_format", this.getJSFormat());
		}
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
			return JDateTools.DateToString(this.oValue,((JFormCDatetimeResponsive)getControl()).getFormatDate());
		}
	}

	@Override
	public void setValueFromUIString(String zValue) throws Exception {
		String sDateString=zValue;
		if (sDateString==null||(sDateString=sDateString.trim()).length()<1) {
			this.setValue((Date)null);
		} else {
			this.setValue( JDateTools.StringToDate(zValue,((JFormCDatetimeResponsive)getControl()).getFormatDate()));
		}
	}

	@Override
	public String getValueAsDBString() throws Exception {
		if (this.oValue==null) {
			return null;
		}
		return JDateTools.DateToString(this.oValue,this.getDbFormat());
	}

	@Override
	public void setValueFromDBString(String zDBString) throws Exception {
		if (zDBString==null||zDBString.trim().length()<1) {
			this.oValue=null;
		} else {
			if (isWithTime() && !isWithDate())
				this.oValue=JDateTools.StringToTime(zDBString, this.getDbFormat());
			else
				this.oValue=JDateTools.StringToDate(zDBString, this.getDbFormat());
		}
	}

	@Override
	public Object getValue() {
		return this.oValue;
	}

	@Override
	public void setValue(Object date) {
		this.oValue=(Date) date;
	}

	public static JWebDateTimeChooserResponsive create(JWebViewComposite parent, JFormCDatetimeResponsive control) throws Exception {
		JWebDateTimeChooserResponsive webDate=new JWebDateTimeChooserResponsive();
		webDate.takeAttributesFormControlResponsive(control);
		webDate.setWithTime(control.isWithTime());
		webDate.setWithDate(control.isWithDate());
		webDate.setWithSecond(control.isWithSeconds());
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
