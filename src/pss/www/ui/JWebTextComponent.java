/*
 * Created on 12-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import java.awt.Dimension;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.SimpleDateFormat;

import pss.core.services.fields.JObject;
import pss.core.tools.formatters.JRegionalDataFormatter;
import pss.core.tools.formatters.JRegionalFormatterFactory;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormEditResponsive;
import pss.core.winUI.responsiveControls.JFormLabelDataResponsive;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.input.JWebInputFormat;

public abstract class JWebTextComponent extends JWebViewEditComponent {

	//
	// STATIC VARIABLES
	//
	private static DecimalFormatSymbols DB_NUMBER_FORMAT_SYMBOLS;
	private static final DecimalFormat DB_FORMAT_FLOAT = new DecimalFormat("##############################.##############################;-##############################.##############################", getNumberSymbols());
	private static final DecimalFormat DB_FORMAT_INTEGER = new DecimalFormat("############################################################;-############################################################", getNumberSymbols());
	private static final SimpleDateFormat DB_FORMAT_DATE = new SimpleDateFormat("dd/MM/yyyy");
	private static final SimpleDateFormat DB_FORMAT_TIME = new SimpleDateFormat("HH:mm:ss");
	private static final SimpleDateFormat DB_FORMAT_DATETIME=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");

	//
	// INSTANCE VARIABLES
	//
	private String sText;
//	private int iTextHAlignment = JFormControl.ALIGN_DEFAULT;
//	private int iTextVAlignment = JFormControl.ALIGN_DEFAULT;
	JWebInputFormat oInputFormat;
	private int precision = -1; // -1: auto


	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int presicion) {
		this.precision = presicion;
	}
	
	//
	// static
	//
	private static DecimalFormatSymbols getNumberSymbols() {
		if (DB_NUMBER_FORMAT_SYMBOLS == null) {
			DB_NUMBER_FORMAT_SYMBOLS = new DecimalFormatSymbols();
			DB_NUMBER_FORMAT_SYMBOLS.setDecimalSeparator('.');
		}
		return DB_NUMBER_FORMAT_SYMBOLS;
	}

	//
	// PUBLIC API
	//

	@Override
	public void destroy() {
		this.sText = null;
		this.oInputFormat = null;
		super.destroy();
	}

	@Override
	public JWebInputFormat getInputFormat() throws Exception {
		if (this.oInputFormat == null) {
			this.oInputFormat = this.resolveDefaultInputFormat();
		}
		return this.oInputFormat;
	}

	public Format getFormatter() throws Exception {
		if (this.getInputFormat() != null) {
			return this.oInputFormat.getFormatter();
		} else {
			return null;
		}
	}

	public JRegionalDataFormatter getRegionalFormatter() throws Exception {
		return JRegionalFormatterFactory.getRegionalFormatter();
	}

	@Override
	public Dimension getDefaultSize() throws Exception {
		if (this.getController() != null) {
			int iLength = this.getController().getFixedProp().getSize();
			if (iLength > 0) {
				return new Dimension(10 * iLength, 24);
			}
		}
		return new Dimension(100, 24);
	}

//	public int getHAlignment() {
//		return this.iTextHAlignment;
//	}
//
//	public int getVAlignment() {
//		return this.iTextVAlignment;
//	}
//
//	public void setHTextAlignment(int i) {
//		this.iTextHAlignment = i;
//	}
//
//	public void setVTextAlignment(int i) {
//		this.iTextVAlignment = i;
//	}

	public String getText() throws Exception {
		return this.getValueAsUIString();
	}

	public void setText(String zText) throws Exception {
		this.setValueFromUIString(zText);
	}

	public boolean isEmpty() throws Exception {
		return this.getText().length() < 1;
	}

	public boolean isEmptyOrSpaces() throws Exception {
		return this.getText().trim().length() < 1;
	}
	
	//
	// generation implementation
	//

	@Override
	protected void widgetToXML(JXMLContent zContent) throws Exception {
		
		String sTextValue = this.getTextForGeneration();
		if (sTextValue.length() > 0) {
			zContent.addTextNode("text", sTextValue);

		}
	}

	protected String getTextForGeneration() throws Exception {
		return this.getText();
	}

	//
	// value providing
	//

	@Override
	public String getValueAsUIString() throws Exception {
		if (this.sText == null) {
			this.sText = "";
		}
		return this.sText;
	}

	@Override
	public void setValueFromUIString(String zValue) throws Exception {
		this.sText = zValue;
	}

	@Override
	public String getValueAsDBString() throws Exception {
		Object value = this.getValue();
		if (value == null) {
			return "";
		} else if (this.getController() != null) {
			return this.objectToDbString(value, this.getController().GetObjectType());
		} else {
			return value.toString();
		}
	}

	@Override
	public void setValueFromDBString(String zDBString) throws Exception {
		if (this.getController() != null) {
			this.setValue(this.dbStringToObject(zDBString, this.getController().GetObjectType()));
		} else {
			this.sText = zDBString;
		}
	}

	@Override
	public Object getValue() throws Exception {
		try {
			String sTheText = this.getText().trim();
			if (this.getFormatter() != null) {
				if (sTheText.length() < 1) {
					return null;
				}
				
				return this.getFormatter().parseObject(sTheText);
			} else {
				return sTheText;
			}
		} catch (Exception e) {
			return new Long(0);
		}
	}

	@Override
	public void setValue(Object zObject) throws Exception {

		if (zObject == null) {
			this.sText = null;
		} else if (isFormatAllowed()&&this.getFormatter()!=null) {
			this.sText=this.getFormatter().format(zObject);
		} else  {
			this.sText = zObject.toString();
		}
	}

	public boolean isFormatAllowed() throws Exception {
		return true;
	}

	//
	// DB value parsing
	//
	private Format getDBFormat(String zDataType) {
		if (zDataType.equals(JObject.JFLOAT) || zDataType.equals(JObject.JCURRENCY)) {
			return DB_FORMAT_FLOAT;
		} else if (zDataType.equals(JObject.JINTEGER) || zDataType.equals(JObject.JLONG)) {
			return DB_FORMAT_INTEGER;
		} else if (zDataType.equals(JObject.JHOUR)) {
			return DB_FORMAT_TIME;
		} else if (zDataType.equals(JObject.JDATE)) {
			return DB_FORMAT_DATE;
		} else if (zDataType.equals(JObject.JSTRING)) {
			return null;
		} else if (zDataType.equals(JObject.JDATETIME)) {
			 return DB_FORMAT_DATETIME;
		} else {
			return null;
		}
	}

	private String objectToDbString(Object zValue, String zDataType) throws Exception {
		Format oFormat = this.getDBFormat(zDataType);
		if (oFormat != null) {
			return oFormat.format(zValue);
		} else {
			return zValue.toString();
		}
	}

	private Object dbStringToObject(String zDBString, String zDataType) throws Exception {
//		if ( zDataType.equals(JObject.JDATE) ) {
//			 if ( zDBString.equals("01-01-1900") )return "";
//		}
//		JObject<?> obj = JObjectFactory.createObject(zDataType, zDBString);
//		return obj.toFormattedString();

		
		Format oFormat = this.getDBFormat(zDataType);
		if (oFormat != null) {
			if (zDBString == null || zDBString.trim().length() < 1) {
				return null;
			}
			return oFormat.parseObject(zDBString);
		} else {
			return zDBString;
		}
	}
	public void takeAttributesFormControlResponsive(JFormControlResponsive comp) throws Exception {
		if (comp instanceof JFormEditResponsive) {
			JFormEditResponsive compE = (JFormEditResponsive) comp;
//			if (compE.getTextHAlignment()!=JFormControl.ALIGN_DEFAULT)
//				this.setHTextAlignment(compE.getTextHAlignment());
//			if (compE.getTextVAlignment()!=JFormControl.ALIGN_DEFAULT)
//				this.setVTextAlignment(compE.getTextVAlignment());
			this.setOutstanding(compE.isOutstanding());
			this.setAutocomplete(compE.isAutocomplete());
		}
		if (comp instanceof JFormLabelDataResponsive) {
			JFormLabelDataResponsive compE = (JFormLabelDataResponsive) comp;
//			if (compE.getTextHAlignment()!=JFormControl.ALIGN_DEFAULT)
//				this.setHTextAlignment(compE.getTextHAlignment());
//			if (compE.getTextVAlignment()!=JFormControl.ALIGN_DEFAULT)
//				this.setVTextAlignment(compE.getTextVAlignment());
			this.setOutstanding(compE.isOutstanding());
		}
		this.setPrecision(comp.getPrecision());
		this.setBlockOverSize(comp.getBlockOverSize());
		super.takeAttributesFormControlResponsive(comp);
	}
	//
	// input format implementation
	//
	protected int resolvePrecision() throws Exception {
		int p =  this.getPrecision(); 
		if (p!=-1) return p;
		if (this.getController().getProp()!=null) {
			p=this.getController().getProp().getPrecision(); // los JCurrency la precicion depende del objecto
			if (p!=-1) return p;
		}
		return this.getController().getFixedProp().GetPrecision();
	}
	protected JWebInputFormat resolveDefaultInputFormat() throws Exception {
		if (this.getController() == null) return null;
		if (!this.getController().hasFixedProp()) return null;
		return this.resolveDefaultInputFormat(this.getController().GetObjectType(), this.getController().getFixedProp().getSize(), resolvePrecision(), this.getController().getFixedProp().GetAtributo());
	}

	private JWebInputFormat resolveDefaultInputFormat(String zDataType, int zLength, int zScale, String zInputAttributes) throws Exception {
		if (zDataType.equals(JObject.JCURRENCY)) {
//			return JWebInputFormat.number(zLength, JRegionalFormatterFactory.getAbsoluteBusinessFormatter().getCurrencyFractionDigits(), getGroupingSeparator(), getDecimalSeparator());
			return JWebInputFormat.number(zLength, zScale, getGroupingSeparator(), getDecimalSeparator());
		} else if (zDataType.equals(JObject.JFLOAT)) {
			return JWebInputFormat.number(zLength, zScale, getGroupingSeparator(), getDecimalSeparator());
		} else if (zDataType.equals(JObject.JINTEGER) || zDataType.equals(JObject.JLONG)) {
			return JWebInputFormat.number(zLength, 0, getGroupingSeparator(), getDecimalSeparator());
		} else if (zDataType.equals(JObject.JHOUR)) {
			return JWebInputFormat.time(this.getRegionalFormatter().getLongTimePattern());
		} else if (zDataType.equals(JObject.JDATE)) {
			return JWebInputFormat.date(this.getRegionalFormatter().getShortDatePattern());
		} else if (zDataType.equals(JObject.JDATETIME)) {
			return JWebInputFormat.datetime(this.getRegionalFormatter().getDateTimePattern());
		} else if (zDataType.equals(JObject.JINTERVALDATETIME)) {
			return JWebInputFormat.string(50,zInputAttributes);
		} else if (zDataType.equals(JObject.JINTERVALDATE)) {
			return JWebInputFormat.string(50,zInputAttributes);
		} else if (zDataType.equals(JObject.JSTRING)|| zDataType.equals(JObject.JIMAGE) || zDataType.equals(JObject.JCOLOUR) || zDataType.equals(JObject.JPASSWORD)) {
			return JWebInputFormat.string(zLength, zInputAttributes);
		} else {
			return null;
		}
	}

	protected char getGroupingSeparator() throws Exception {
		return this.getRegionalFormatter().getGroupingSeparator();
	}

	protected char getDecimalSeparator() throws Exception {
		return this.getRegionalFormatter().getDecimalSeparator();
	}

	@Override
	public String getComponentType() {
		// TODO Auto-generated method stub
		return null;
	}

	// protected boolean isModoConsulta() throws Exception {
	// if (this.getForm() instanceof JWebWinForm) return ((JWebWinForm)
	// this.getForm()).getBaseForm().isConsulta();
	// else return false;
	// }

	// protected boolean isModoAlta() throws Exception {
	// if (this.getForm() instanceof JWebWinForm) return ((JWebWinForm)
	// this.getForm()).getBaseForm().isAlta();
	// else return false;
	// }

	@Override
	protected String getState() throws Exception {
		return this.getForm()==null ?null:(this.getForm().isModoConsulta() || !isEditable()) ? null : "edit";
	}

//	@Override
//	protected void sizeToXML(JXMLContent zContent) throws Exception {
//		this.generateOnCalculate(zContent);
//		super.sizeToXML(zContent);
//	}
	
//	protected void generateOnCalculate(JXMLContent zContent) throws Exception {
//		if ( this.getForm().isModoConsulta()) return ;
//		if (this.getController()==null) return ;
//		if (this.getController().getProp()==null) return ;
//		if (!getController().getProp().hasScript()) return ;
//		String onCalculate="";
//		String formatLinea = "";
//		JWebEditComponentContainer form = this.getForm();
////		JScript oScript=getController().getProp().getObjScript();
//		JScript oScript=getController().getProp().getScript();
//		if (oScript==null) return ;
//		LinkedHashMap<String, String> map= new LinkedHashMap<String, String>();
//		JIterator<String> i = oScript.getBind().getKeyIterator();
//		while (i.hasMoreElements()) {
//			String key = i.nextElement();
//			String value = (String)oScript.getBind().getElement(key);
//			JFormControl c = getController().getControls().findControl(value);
//			if (c==null) continue;
//			map.put(key+"=", "document.getElementById('dgf_"+form.getFormName()+"_fd."+value+"').value=" );
//			map.put(key, "resolve('dgf_"+form.getFormName()+"_fd."+value+"')");
//			formatLinea+="formatear('dgf_"+form.getFormName()+"_fd."+value+"');";
//		}
//		if (oScript.isCalculeOthersFields())
//			onCalculate=oScript.getFormulaInContext(map)+";"+formatLinea;
//		else
//		  onCalculate="document.getElementById('dgf_"+form.getFormName()+"_fd."+this.getName()+"').value="+oScript.getFormulaInContext(map)+";";
//		zContent.setAttribute("onCalculate", onCalculate);
//		if (oScript.isCalculeOthersFields()) zContent.setAttribute("isCalculeOthersFields", oScript.isCalculeOthersFields());
//		if (oScript.isCalculeOnStart()) zContent.setAttribute("isCalculeOnStart", oScript.isCalculeOnStart());
//	}
	
}
