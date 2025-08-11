package pss.core.winUI.responsiveControls;

import java.util.Calendar;
import java.util.Date;

import pss.common.regions.multilanguage.JLanguage;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.formatters.JRegionalFormatterFactory;
import pss.core.winUI.controls.JFormControl;

public class JFormCDatetimeResponsive extends JFormControlResponsive {

	public static final String VIEWMODE_DECADES= "decades";
	public static final String VIEWMODE_YEARS = "years";
	public static final String VIEWMODE_MONTHS = "months";
	public static final String VIEWMODE_DAYS = "days";

	boolean withTime=false;
	boolean withDate=true;
	boolean onlyMonthYear=false;
	boolean sideBySide=false;

	boolean todayBtn=true;
	String viewMode="days";
	String idioma=null;
	boolean endWeekDisabled = false;// [0,6] deshabilita 
	boolean inline = false; 
	boolean withSeconds = false; 
	boolean autoOpen = false; 

	public boolean isAutoOpen() {
		return autoOpen;
	}

	public JFormCDatetimeResponsive setAutoOpen(boolean autoOpen) {
		this.autoOpen = autoOpen;
		return this;
	}

	public boolean isWithSeconds() {
		return withSeconds;
	}

	public JFormCDatetimeResponsive setWithSeconds(boolean withSeconds) {
		this.withSeconds = withSeconds;
		return this;
	}

	String options;
	public String getFormatDate() throws Exception{
		char sep=JDateTools.validateSeparator(JRegionalFormatterFactory.getRegionalFormatter().getShortDatePattern());
		if (isWithDate() && isWithTime() && isWithSeconds())
			return JRegionalFormatterFactory.getRegionalFormatter().getShortDatePattern()+" HH:mm:ss";
		if (isWithDate() && isWithTime())
			return JRegionalFormatterFactory.getRegionalFormatter().getShortDatePattern()+" HH:mm";
	  if (isOnlyMonthYear())
			return "MM"+sep+"YYYY";
		if (isWithTime() && !isWithDate() && isWithSeconds())
			return "HH:mm:ss";
		if (isWithTime() && !isWithDate() && !isWithSeconds())
			return "HH:mm";
		if (!isWithTime() && isWithDate())
			return JRegionalFormatterFactory.getRegionalFormatter().getShortDatePattern();
		return JRegionalFormatterFactory.getRegionalFormatter().getShortDatePattern();
	}
	public String getFormatDateJS(String date) throws Exception{
		String out = JTools.replace(date, "y", "Y");
		out = JTools.replace(out, "d", "D");
		return out;
	}
	
	public String getOptions() throws Exception{
		String ops = "";
		char sep=JDateTools.validateSeparator(JRegionalFormatterFactory.getRegionalFormatter().getShortDatePattern());
		ops = (ops.equals("") ? "" : ",") + "locale: '" + getIdioma() + "'";
		
		if (isAutoOpen())
			ops += (ops.equals("") ? "" : ",") + "allowInputToggle: true";
		else
			ops += (ops.equals("") ? "" : ",") + "allowInputToggle: false";

		ops += (ops.equals("") ? "" : ",") + "format:'"+getFormatDateJS(getFormatDate())+"'";
		if (isOnlyMonthYear())
			ops += (ops.equals("") ? "" : ",") + "viewMode:'years'";
		if (isTodayBtn())
			ops += (ops.equals("") ? "" : ",") + "showTodayButton:true";
		if (isEndWeekDisabled())
			ops += (ops.equals("") ? "" : ",") + "daysOfWeekDisabled: [0, 6]";
		if (isSideBySide() || isWithTime())
			ops += (ops.equals("") ? "" : ",") + "sideBySide:true";
		if (isInline())
			ops += (ops.equals("") ? "" : ",") + "inline: true, sideBySide: true";
		if (!isWithTime())
			ops += (ops.equals("") ? "" : ",") + "viewMode: '" + getViewMode()+"'";
		if (options != null)
			ops += (ops.equals("") ? "" : ",") + options;
		return ops;
	}

	public void setExtraOptions(String options) {
		this.options = options;
	}
	
	public boolean isInline() {
		return inline;
	}


	public boolean isSideBySide() {
		return sideBySide;
	}

	public void setSideBySide(boolean sideBySide) {
		this.sideBySide = sideBySide;
	}

	public void setInline(boolean inline) {
		this.inline = inline;
	}
	public boolean isEndWeekDisabled() {
		return endWeekDisabled;
	}

	public void setEndWeekDisabled(boolean endWeekDisabled) {
		this.endWeekDisabled = endWeekDisabled;
	}

	public boolean isWithTime() {
		return withTime;
	}
	public boolean isOnlyMonthYear() {
		return onlyMonthYear;
	}

	public void setOnlyMonthYear(boolean onlyMonthYear) {
		this.onlyMonthYear = onlyMonthYear;
	}

	public JFormControl setWithTime(boolean withTime) {
		this.withTime = withTime;
		return this;
	}

	public boolean isWithDate() {
		return withDate;
	}

	public void setWithDate(boolean withDate) {
		this.withDate = withDate;
	}

	public String getIdioma() {
		if (idioma==null)
			return JLanguage.translate("language.datepicker");
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}	
	
	
	public boolean isTodayBtn() {
		return todayBtn;
	}

	public void setTodayBtn(boolean todayBtn) {
		this.todayBtn = todayBtn;
	}

	

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	// -------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------//
	public JFormCDatetimeResponsive() {
	}

	// -------------------------------------------------------------------------//
	// Obtengo el valor del campo
	// -------------------------------------------------------------------------//
	@Override
	public String getSpecificValue() {
		try {

			this.getProp().preset(); // HGK 
			Object oVal = getProp().getObjectValue();
			if (oVal==null)
					return "";
			Calendar oCal = null;
			if (oVal instanceof Calendar) {
				oCal = (Calendar) oVal;
			} else if (oVal instanceof Date) {
				oCal = Calendar.getInstance();
				oCal.setTime((Date) oVal);
			} else {
				String time = oVal.toString();
				return time;
			}

			String sFecha = JTools.LPad(String.valueOf(oCal.get(Calendar.DAY_OF_MONTH)), 2, "0") + "/" + JTools.LPad(String.valueOf(oCal.get(Calendar.MONTH) + 1), 2, "0") + "/" + String.valueOf(oCal.get(Calendar.YEAR));
			if (this.isWithTime())
				sFecha += " " + JTools.LPad(String.valueOf(oCal.get(Calendar.HOUR_OF_DAY)), 2, "0") + ":" + JTools.LPad(String.valueOf(oCal.get(Calendar.MINUTE)), 2, "0") + ":" + JTools.LPad(String.valueOf(oCal.get(Calendar.SECOND)), 2, "0");

			return sFecha;
		} catch (Exception E) {
			return "";
		}
	}

	public String getTime() {
		try {

			Object oVal = getProp().getObjectValue();
			if (oVal==null) return "";
			Calendar oCal = null;
			if (oVal instanceof Calendar) {
				oCal = (Calendar) oVal;
			} else if (oVal instanceof Date) {
				oCal = Calendar.getInstance();
				oCal.setTime((Date) oVal);
			}

			String sFecha = JTools.LPad(String.valueOf(oCal.get(Calendar.HOUR)), 2, "0") + ":" + JTools.LPad(String.valueOf(oCal.get(Calendar.MINUTE) + 1), 2, "0") + ":" + JTools.LPad(String.valueOf(oCal.get(Calendar.SECOND)), 2, "0");

			return sFecha;
		} catch (Exception E) {
			return "";
		}
	}

	public String getDate() {
		try {

			Object oVal = getProp().getObjectValue();
			if (oVal==null) return "";

			Calendar oCal = null;
			if (oVal instanceof Calendar) {
				oCal = (Calendar) oVal;
			} else if (oVal instanceof Date) {
				oCal = Calendar.getInstance();
				oCal.setTime((Date) oVal);
			}

			String sFecha = JTools.LPad(String.valueOf(oCal.get(Calendar.DAY_OF_MONTH)), 2, "0") + "/" + JTools.LPad(String.valueOf(oCal.get(Calendar.MONTH) + 1), 2, "0") + "/" + String.valueOf(oCal.get(Calendar.YEAR));

			return sFecha;
		} catch (Exception E) {
			return "";
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


	}
