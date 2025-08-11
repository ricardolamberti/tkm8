package pss.core.winUI.responsiveControls;

import java.util.Date;

import pss.common.security.BizUsuario;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JIntervalDate;
import pss.core.services.fields.JIntervalDateTime;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JObjectFactory;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.tools.formatters.JRegionalFormatterFactory;
import pss.core.winUI.controls.JFormControl;

public class JFormIntervalCDatetimeResponsive  extends JFormTwoPropertiesResponsive {

	public static final String POSLEFT = "left"; 
	public static final String POSRIGHT = "right"; 
	public static final String POSCENTER = "center"; 
	boolean withTime=false;

	boolean alwaysShowCalendar=true;
	boolean showWeekNumber=true;
	boolean showDropdowns=true;
	JMap<String,JIntervalDateTime> range; 
	
	String posicion = POSCENTER; 

	boolean endWeekDisabled = false;// [0,6] deshabilita 
	boolean withSeconds = false; 

	
	public JMap<String,JIntervalDateTime> getRanges() throws Exception{
		if (range!=null) return range;
		return range = new JCollectionFactory().createOrderedMap();
	}

	public JMap<String,JIntervalDateTime> getRangesDefault() throws Exception{
		JMap<String,JIntervalDateTime> map = new JCollectionFactory().createOrderedMap();
		map.addElement("Hoy", new JIntervalDateTime(JDateTools.getDateStartDay(JDateTools.getNow()),JDateTools.getDateEndDay(JDateTools.getNow())));
		map.addElement("Ayer", new JIntervalDateTime(JDateTools.getDateStartDay(JDateTools.getYesterday()),JDateTools.getDateEndDay(JDateTools.getYesterday())));
		map.addElement("Los últimos 7 días", new JIntervalDateTime(JDateTools.getDateStartDay(JDateTools.getBackDays(7)),JDateTools.getDateEndDay(JDateTools.getNow())));
		map.addElement("Los últimos 30 días", new JIntervalDateTime(JDateTools.getDateStartDay(JDateTools.getBackDays(30)),JDateTools.getDateEndDay(JDateTools.getNow())));
		map.addElement("Este mes", new JIntervalDateTime(JDateTools.getDateStartDay(JDateTools.getFirstDayOfMonth(JDateTools.getNow())),JDateTools.getDateEndDay(JDateTools.getLastDayOfMonth(JDateTools.getNow()))));
		map.addElement("Anterior mes", new JIntervalDateTime(JDateTools.getDateStartDay(JDateTools.getFirstDayOfMonth(JDateTools.getLastDayPreviousMonth())),JDateTools.getDateEndDay(JDateTools.getLastDayOfMonth(JDateTools.getLastDayPreviousMonth()))));
		map.addElement("Este Año", new JIntervalDateTime(JDateTools.getDateStartDay(JDateTools.getFirstDayOfYear(JDateTools.getNow())),JDateTools.getDateEndDay(JDateTools.getLastDayOfYear(JDateTools.getNow()))));
		return map;
	}

	public boolean isWithSeconds() {
		return withSeconds;
	}
	
	public void addRange(String label, Date desde, Date hasta)  throws Exception{
	 getRanges().addElement(label, new JIntervalDateTime(desde, hasta));
	}

	protected JObject getPropDefault() throws Exception {
		if (propDefault==null) {
			if (isWithTime())
				propDefault=JObjectFactory.virtualCreate(JObject.JINTERVALDATETIME);
			else
				propDefault=JObjectFactory.virtualCreate(JObject.JINTERVALDATE);
		}
		return this.propDefault;
	}

	public void setWithSeconds(boolean withSeconds) {
		this.withSeconds = withSeconds;
	}
	public boolean isAlwaysShowCalendar() {
		return alwaysShowCalendar;
	}

	public void setAlwaysShowCalendar(boolean alwaysShowCalendar) {
		this.alwaysShowCalendar = alwaysShowCalendar;
	}
	public boolean isShowWeekNumber() {
		return showWeekNumber;
	}

	public void setShowWeekNumber(boolean showWeekNumber) {
		this.showWeekNumber = showWeekNumber;
	}
	public String getPosicion() {
		return posicion;
	}

	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}

	public boolean isShowDropdowns() {
		return showDropdowns;
	}

	public void setShowDropdowns(boolean showDropdowns) {
		this.showDropdowns = showDropdowns;
	}
	String outFormat;
	String outFormatJS;

	public String getOutFormat() throws Exception {
		if (outFormat==null) {
			if (isWithTime()) {
				if (isWithSeconds())
					return JRegionalFormatterFactory.getRegionalFormatter().getShortDatePattern()+" "+JRegionalFormatterFactory.getRegionalFormatter().getLongTimePattern();
				else
					return JRegionalFormatterFactory.getRegionalFormatter().getShortDatePattern()+" "+JRegionalFormatterFactory.getRegionalFormatter().getShortTimePattern();
			} else
				return JRegionalFormatterFactory.getRegionalFormatter().getShortDatePattern();
		}
		return outFormat;
	}
	public String getOutFormatJS() throws Exception {
		if (outFormatJS==null) {
			if (isWithTime()) {
				if (isWithSeconds())
					return JRegionalFormatterFactory.getRegionalFormatter().getShortDatePattern().toUpperCase()+" "+JRegionalFormatterFactory.getRegionalFormatter().getLongTimePattern();
				else
					return JRegionalFormatterFactory.getRegionalFormatter().getShortDatePattern().toUpperCase()+" "+JRegionalFormatterFactory.getRegionalFormatter().getShortTimePattern();
			} else
				return JRegionalFormatterFactory.getRegionalFormatter().getShortDatePattern().toUpperCase();
		}
		return outFormatJS;
	}
	public String getOutFormatJSSimplify() throws Exception {
		return JTools.replace(getOutFormatJS(),"YYYY","YY");
	}
	public void setOutFormat(String outFormat) throws Exception {
		this.outFormat = outFormat;
	}
	String options;
	

	public String getOptions() throws Exception{
		char sep=JDateTools.validateSeparator(getOutFormat());
    String formatoFecha = getOutFormat();
    String formatoFechaJS = getOutFormatJS();
    String formatoFechaJSSimplify = getOutFormatJSSimplify();
  	String ops = "";
		ops += "  \"showDropdowns\": "+(isShowDropdowns()?"true":"false")+", ";
		ops += "  \"showWeekNumbers\": "+(isShowWeekNumber()?"true":"false")+", ";
		ops += "  \"showISOWeekNumbers\": false, ";
		ops += "  \"autoUpdateInput\": false, ";
		ops += "  \"minDate\": \"01"+sep+"01"+sep+"2000\", ";
		ops += "  \"maxDate\": \"31"+sep+"12"+sep+"2999\", ";
		ops += "  \"minYear\": 1990, ";
		ops += "  \"maxYear\": 2100, ";
		ops += "  \"timePicker\": "+(isWithTime()?"true":"false")+", ";
		ops += "  \"linkedCalendars\": false, ";
		ops += "  \"timePicker24Hour\": true, ";
		ops += "  \"timePickerSeconds\": "+(isWithSeconds()?"true":"false")+", ";
		JMap<String,JIntervalDateTime> range = getRanges().size()>0?getRanges():getRangesDefault();
		String strRanges="";
		JIterator<String> it = range.getKeyIterator();
		while (it.hasMoreElements()) {
			String key = it.nextElement();
			JIntervalDateTime interval = range.getElement(key);
			String newLine = "      \""+JTools.replaceForeignCharsForWeb(BizUsuario.getMessage(key, null))+"\": [ ";
			newLine += "          \""+JDateTools.DateToString(interval.getStartDateValue(),formatoFecha)+"\", ";
			newLine += "          \""+JDateTools.DateToString(interval.getEndDateValue(),formatoFecha)+"\", ";
			newLine += "      ]";
			strRanges += (strRanges.equals("")?"":",")+newLine;
		}
		if (!strRanges.equals("")) {
			ops += "  \"ranges\": { ";
			ops += strRanges;
			ops += "  }, ";
		}
		ops += "  \"locale\": { ";
		ops += "      \"direction\": \"ltr\", ";
		ops += "      \"format\": \""+formatoFechaJS+"\", ";
		ops += "      \"formatSimple\": \""+formatoFechaJSSimplify+"\", ";
		ops += "      \"separator\": \" - \", ";
		ops += "      \"applyLabel\": \""+BizUsuario.getMessage("Aplicar", null)+"\", ";
		ops += "      \"cancelLabel\": \""+BizUsuario.getMessage("Cancelar", null)+"\", ";
		ops += "      \"fromLabel\": \""+BizUsuario.getMessage("Desde", null)+"\", ";
		ops += "      \"toLabel\": \""+BizUsuario.getMessage("Hasta", null)+"\", ";
		ops += "      \"customRangeLabel\": \""+BizUsuario.getMessage("Custom", null)+"\", ";
		ops += "      \"daysOfWeek\": [ ";
		ops += "          \""+BizUsuario.getMessage("Do", null)+"\", ";
		ops += "          \""+BizUsuario.getMessage("Lu", null)+"\", ";
		ops += "          \""+BizUsuario.getMessage("Ma", null)+"\", ";
		ops += "          \""+BizUsuario.getMessage("Mi", null)+"\", ";
		ops += "          \""+BizUsuario.getMessage("Ju", null)+"\", ";
		ops += "          \""+BizUsuario.getMessage("Vi", null)+"\", ";
		ops += "          \""+BizUsuario.getMessage("Sa", null)+"\" ";
		ops += "      ], ";
		ops += "      \"monthNames\": [ ";
		ops += "         \""+BizUsuario.getMessage("Enero", null)+"\", ";
		ops += "         \""+BizUsuario.getMessage("Febrero", null)+"\", ";
		ops += "          \""+BizUsuario.getMessage("Marzo", null)+"\", ";
		ops += "          \""+BizUsuario.getMessage("Abril", null)+"\", ";
		ops += "          \""+BizUsuario.getMessage("Mayo", null)+"\", ";
	  ops += "          \""+BizUsuario.getMessage("Junio", null)+"\", ";
	  ops += "          \""+BizUsuario.getMessage("Julio", null)+"\", ";
	  ops += "          \""+BizUsuario.getMessage("Agosto", null)+"\", ";
	  ops += "          \""+BizUsuario.getMessage("Septiembre", null)+"\", ";
	  ops += "          \""+BizUsuario.getMessage("Octubre", null)+"\", ";
	  ops += "          \""+BizUsuario.getMessage("Noviembre", null)+"\", ";
	  ops += "          \""+BizUsuario.getMessage("Diciembre", null)+"\" ";
	  ops += "      ], ";
	  ops += "      \"firstDay\": 1 ";
	  ops += "  }, ";
	  ops += "  \"alwaysShowCalendars\": "+(isAlwaysShowCalendar()?"true":"false")+", ";
//	  ops += "  \"minDate\": \"DD"+sep+"MM"+sep+"YYYY\", ";
//	  ops += "  \"maxDate\": \"DD"+sep+"MM"+sep+"YYYY\", ";
	  ops += "  \"opens\": \""+getPosicion()+"\" ";
		return ops;
	}

	public void setExtraOptions(String options) {
		this.options = options;
	}
	
	public boolean isWithTime() {
		return withTime;
	}

	public JFormControl setWithTime(boolean withTime) {
		this.withTime = withTime;
		return this;
	}

	// -------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------//
	public JFormIntervalCDatetimeResponsive() {
	}

	// -------------------------------------------------------------------------//
	// Obtengo el valor del campo
	// -------------------------------------------------------------------------//


	public void setProp(JObject zProp) {
		if (zProp instanceof JIntervalDate || zProp instanceof JIntervalDateTime) {
			oProp = zProp;
		} else if (isWithTime()) {
			oProp=new JIntervalDateTime();
		} else
			oProp=new JIntervalDate();
	}
	public Date getStartDate() {
		try {
			if (isWithTime())
				return ((JIntervalDateTime) getProp()).getStartDateValue();
	  	return ((JIntervalDate) getProp()).getStartDateValue();
		} catch (Exception E) {
			return null;
		}
	}

	public Date getEndDate() {
		try {
			if (isWithTime())
				return ((JIntervalDate) getProp()).getEndDateValue();
	  	return ((JIntervalDateTime) getProp()).getEndDateValue();
		} catch (Exception E) {
			return null;
		}
	}
	// -------------------------------------------------------------------------//
	// Obtengo el valor del campo
	// -------------------------------------------------------------------------//
	@Override
	public boolean hasValue() throws Exception {
		if (getValue().equalsIgnoreCase(""))
			return false;
		else
			return true;
	}
	public JFormControl SetValorDefault(Date startDate, Date endDate) throws Exception {
		if (startDate==null) return this;
		if (endDate==null) return this;
		if (isWithTime())
			this.SetValorDefault(new JIntervalDateTime(startDate,endDate));
		else
			this.SetValorDefault(new JIntervalDate(startDate,endDate));
		return this;
	}
	
	public JObject getProp() {
		if (oProp==null) {
			if (isWithTime())
				oProp = new JIntervalDateTime();
			else
				oProp = new JIntervalDate();
		}
  	try {
			if (useTwoFields()) {
				if (isWithTime())
					oProp = new JIntervalDateTime();
				else
					oProp = new JIntervalDate();
				oProp.setValue(getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oProp;
	}

  @Override
	public void setValue( String zVal ) throws Exception {
  	if (useTwoFields()) {
  		if(zVal==null) {
      	getPropFrom().setNull();
   	  	getPropTo().setNull();
  			return;
  		}
    	int pos = zVal.indexOf(" - ");
    	if (pos==-1) {
      	getPropFrom().setNull();
   	  	getPropTo().setNull();
      	return;
    	}
    	String startDate = zVal.substring(0,pos);
    	String endDate = zVal.substring(pos+3);
    	Date sd = JDateTools.StringToDate(startDate, getOutFormat());
    	Date ed = JDateTools.StringToDate(endDate, getOutFormat());
  		if (isWithTime()) {
   	  	getPropFrom().setValue(JDateTools.DateToString(sd, ((JDateTime) getPropFrom()).getDbFormat()));
   	  	getPropTo().setValue(JDateTools.DateToString(ed, ((JDateTime) getPropTo()).getDbFormat()));
  		}	else {
  	  	getPropFrom().setValue(JDateTools.DateToString(sd, ((JDate) getPropFrom()).getDbFormat()));
   	  	getPropTo().setValue(JDateTools.DateToString(ed, ((JDate) getPropTo()).getDbFormat()));
   		}
  		return;
  	}
		if(zVal==null) {
			getProp().setNull();
 			return;
		}
  	int pos = zVal.indexOf(" - ");
  	if (pos==-1) {
  		getProp().setNull();
  		return;
  	}
  	String startDate = zVal.substring(0,pos);
  	String endDate = zVal.substring(pos+3);
  	Date sd = JDateTools.StringToDate(startDate, getOutFormat());
  	Date ed = JDateTools.StringToDate(endDate, getOutFormat());
		if (isWithTime())
	  	getProp().setValue(JDateTools.DateToString(sd, ((JIntervalDateTime) getProp()).getDbFormat())+" - "+JDateTools.DateToString(ed, ((JIntervalDateTime) getProp()).getDbFormat()));
		else
	  	getProp().setValue(JDateTools.DateToString(sd, ((JIntervalDate) getProp()).getDbFormat())+" - "+JDateTools.DateToString(ed, ((JIntervalDate) getProp()).getDbFormat()));

  }

  @Override
  public String getSpecificValue() throws Exception {
  	if (useTwoFields()) {
  		if (getPropTo()==null||getPropFrom()==null) return "";
  		if (isWithTime()) {
  			JDateTime dts = (JDateTime) getPropFrom();
  			JDateTime dte = (JDateTime) getPropTo();
  			return JDateTools.DateToString(dts.getValue(),getOutFormat())+" - "+JDateTools.DateToString(dte.getValue(),getOutFormat());
  		} else {
  			JDate dte = (JDate) getPropFrom();
  			JDate dts = (JDate) getPropTo();
  			return JDateTools.DateToString(dte.getValue(),getOutFormat())+" - "+JDateTools.DateToString(dts.getValue(),getOutFormat());
  		}
   	}
		if (getProp()==null) return "";
		if (getProp().getObjectValue()==null) return "";
		if (isWithTime()) {
			JIntervalDateTime dt = (JIntervalDateTime) getProp();
			return JDateTools.DateToString(dt.getStartDateValue(),getOutFormat())+" - "+JDateTools.DateToString(dt.getEndDateValue(),getOutFormat());
		} else {
			JIntervalDate dt = (JIntervalDate) getProp();
			return JDateTools.DateToString(dt.getStartDateValue(),getOutFormat())+" - "+JDateTools.DateToString(dt.getEndDateValue(),getOutFormat());
		}
  }

	}
