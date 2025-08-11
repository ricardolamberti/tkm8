package pss.common.customAutoReports.scheduler;

import java.util.Calendar;
import java.util.Date;

import pss.common.customAutoReports.config.BizReportList;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JHour;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.PssLogger;

public class BizAutoReportSchedule extends JRecord {
	
	public static String TYPE_DAILY = "DAILY";
	public static String TYPE_WEEKLY = "WEEKLY";
	public static String TYPE_MONTHLY = "MONTHLY";
	
	public static String SUBTYPE_MONTHLY_START = "MONTHLY_START";
	public static String SUBTYPE_MONTHLY_END = "MONTHLY_END";
	
	
  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//

	private JString pCompany = new JString();
	private JLong 	pReportId = new JLong();
	private JString pType = new JString();
	private JString pSubType = new JString();
	private JBoolean pMonday = new JBoolean();
	private JBoolean pTuesday = new JBoolean();
	private JBoolean pWednesday = new JBoolean();
	private JBoolean pThursday = new JBoolean();
	private JBoolean pFriday = new JBoolean();
	private JBoolean pSaturday = new JBoolean();
	private JBoolean pSunday = new JBoolean();
	private JHour		pTimetorun = new JHour();
	private JDateTime pLastrun = new JDateTime();
	private JString pOutputPath = new JString();
	private JBoolean pActive = new JBoolean();
	JString pDescrTipo= new JString() {
		@Override
		public void preset() throws Exception {
			ObtenerDescrTipo();
		}
	};
	JString pDescrSubTipo= new JString() {
		@Override
		public void preset() throws Exception {
			ObtenerDescrSubTipo();
		}
	};
	private Date pLastRunned=null;
	private Date pNow=null;
	private JBoolean pShouldRun= new JBoolean();
	private static JRecords<BizVirtual> tiposSchedule = null;
	private BizReportList reportList;
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getCompany() throws Exception { return pCompany.getValue();  }
  public void setIdReport(long zValue) throws Exception {    pReportId.setValue(zValue);  }
  public long getIdReport() throws Exception {     return pReportId.getValue();  }
	
  public void setShouldRun(Boolean zValue) throws Exception {    pShouldRun.setValue(zValue) ;  }
  public Boolean getShouldRun() throws Exception { return pShouldRun.getValue();  }
  public String getOutputPath() throws Exception { return pOutputPath.getValue();  }
  public Boolean isActive() throws Exception { return pActive.getValue();  }
  public Date getLastrun() throws Exception { return pLastrun.getValue();  }
  public void setLastrun(Date zValue) throws Exception { pLastrun.setValue(zValue);  }
  public Date getNow() throws Exception { return pNow;  }
  
  public void setType(String zValue) throws Exception {    pType.setValue(zValue);  }
  public String getType() throws Exception {     return pType.getValue();  }
  public void setSubType(String zValue) throws Exception {    pSubType.setValue(zValue);  }
  public String getSubType() throws Exception {     return pSubType.getValue();  }
  
  /**
   * Constructor de la Clase
   */
  public BizAutoReportSchedule() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "report_id", pReportId );
    this.addItem( "rep_type", pType );
    this.addItem( "rep_subtype", pSubType );
    this.addItem( "monday", pMonday );
    this.addItem( "tuesday", pTuesday );
    this.addItem( "wednesday", pWednesday );
    this.addItem( "thursday", pThursday );
    this.addItem( "friday", pFriday );
    this.addItem( "saturday", pSaturday );
    this.addItem( "sunday", pSunday );
    this.addItem( "time_to_run", pTimetorun );
    this.addItem( "last_run", pLastrun );
    this.addItem( "output_path", pOutputPath );
    this.addItem( "active", pActive );
    this.addItem( "descr_tipo", pDescrTipo );
    this.addItem( "descr_subtipo", pDescrSubTipo );
  }
  /**
   * Adds the fixed object properties
   */
  @Override
	public void createFixedProperties() throws Exception {
  	this.addFixedItem( KEY, "company", "Empresa", true, true, 15 );
  	this.addFixedItem( KEY, "report_id", "Reporte", true, true, 18);
  	this.addFixedItem( FIELD, "rep_type", "Tipo", true, true, 30 );
  	this.addFixedItem( FIELD, "rep_subtype", "Sub Tipo", true, false, 30 );
  	this.addFixedItem( FIELD, "monday", "Lunes", true, false, 1 );
  	this.addFixedItem( FIELD, "tuesday", "Martes", true, false, 1 );
  	this.addFixedItem( FIELD, "wednesday", "Miércoles", true, false, 1 );
  	this.addFixedItem( FIELD, "thursday", "Jueves", true, false, 1);
    this.addFixedItem( FIELD, "friday", "Viernes", true, false, 1 );
    this.addFixedItem( FIELD, "saturday", "Sábado", true, false, 1 );
    this.addFixedItem( FIELD, "sunday", "Domingo", true, false, 1 );
    this.addFixedItem( FIELD, "time_to_run", "Hora de ejecución", true, true, 8 );
    this.addFixedItem( FIELD, "last_run", "Fecha/Hora ult. ejec.", true, false, 20 );
    this.addFixedItem( FIELD, "output_path", "Ruta de BackUp", true, false, 200 );
    this.addFixedItem( FIELD, "active", "Activo", true, true, 1 );
    this.addFixedItem( VIRTUAL, "descr_tipo", "Tipo", true, false, 100 );
    this.addFixedItem( VIRTUAL, "descr_subtipo", "Sub Tipo", true, false, 100 );
  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return "REPORT_AUTO_SCHEDULE"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean Read(String zCompany, long zReportId) throws Exception {
  	addFilter( "company",  zCompany );
  	addFilter( "report_id",  zReportId ); 
    return read(); 
  } 
  
	public synchronized static JRecords<BizVirtual> getTiposSchedule() throws Exception {
		if (tiposSchedule != null)
			return tiposSchedule;
		tiposSchedule = JRecords.createVirtualBDs();
		tiposSchedule.addItem(JRecord.virtualBD(TYPE_DAILY, "Diario", 1));
		tiposSchedule.addItem(JRecord.virtualBD(TYPE_WEEKLY, "Semanal", 1));
		tiposSchedule.addItem(JRecord.virtualBD(TYPE_MONTHLY, "Mensual", 1));
		return tiposSchedule;
	}
	private void ObtenerDescrTipo() throws Exception {
		pDescrTipo.setValue((getTiposSchedule().findVirtualKey(String.valueOf(pType.getValue())).getDescrip()));
	}

	public synchronized static JRecords<BizVirtual> getSubTiposSchedule(String tipo) throws Exception {
		JRecords<BizVirtual> subTiposSchedule =  JRecords.createVirtualBDs();
		subTiposSchedule = JRecords.createVirtualBDs();
		if (tipo.equals(TYPE_MONTHLY)) {
			subTiposSchedule.addItem(JRecord.virtualBD(SUBTYPE_MONTHLY_START, "Inicio Mes", 1));
			subTiposSchedule.addItem(JRecord.virtualBD(SUBTYPE_MONTHLY_END, "Fin Mes", 1));
		}
		return subTiposSchedule;
	}

	private void ObtenerDescrSubTipo() throws Exception {
		BizVirtual a = getSubTiposSchedule(pType.getValue()).findVirtualKey(String.valueOf(pSubType.getValue()));
		if (a!=null)
			pDescrSubTipo.setValue(a.getDescrip());
	}
	
  public boolean hasToRun() throws Exception {
  	boolean mustRun = false;
  	Date ahora = JDateTools.StringToDateTime(JDateTools.CurrentDateTime());
  	Date supossedDatetoRun = null;
  	supossedDatetoRun = this.getSupposedDateToRun(ahora);
  	if (supossedDatetoRun==null) return false ;
  	
		if (this.pLastrun.isNotNull() && this.pLastrun.getValue().after(supossedDatetoRun)) {  
			mustRun = false ;
		} else {
			if (ahora.after(supossedDatetoRun)) 
				mustRun = true ;
		} 	
  	return mustRun;
  }

  private Date getSupposedDateToRun(Date ahora) throws Exception {
  	String only_date = JDateTools.DateToString(ahora, "yyyyMMdd");
  	String hour_to_run = this.pTimetorun.GetHour() + this.pTimetorun.GetMinute() + this.pTimetorun.GetSecond();
  	String date_to_run = "";
  	Date supossedDatetoRun = null;
  	if (pType.equals(TYPE_MONTHLY)) {
  		if (pSubType.equals(SUBTYPE_MONTHLY_START)) {
  			date_to_run = only_date.substring(0,4) + only_date.substring(4,6) + "01";
  			supossedDatetoRun = JDateTools.StringToDate(date_to_run + " " + hour_to_run, "yyyyMMdd HHmmss");
  		}
  		if (pSubType.equals(SUBTYPE_MONTHLY_END)) {
  			Calendar cal_endofMonth = Calendar.getInstance();
  			Date endofMonth = JDateTools.getLastDayOfMonth(ahora);
  			cal_endofMonth.setTime(endofMonth);
  			date_to_run = only_date.substring(0,4) + only_date.substring(4,6) + cal_endofMonth.get(Calendar.DAY_OF_MONTH);
  			supossedDatetoRun = JDateTools.StringToDate(date_to_run + " " + hour_to_run, "yyyyMMdd HHmmss");
  		}
  	}
  	if (pType.equals(TYPE_DAILY)) {
  		boolean have_to_check = false;
			Calendar cal = Calendar.getInstance();
			cal.setTime(ahora);
			int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
			if (day_of_week==Calendar.SUNDAY && this.pSunday.getValue()) have_to_check = true ;
			if (day_of_week==Calendar.MONDAY && this.pMonday.getValue()) have_to_check = true ;
			if (day_of_week==Calendar.TUESDAY && this.pTuesday.getValue()) have_to_check = true ;
			if (day_of_week==Calendar.WEDNESDAY && this.pWednesday.getValue()) have_to_check = true ;
			if (day_of_week==Calendar.THURSDAY && this.pThursday.getValue()) have_to_check = true ;
			if (day_of_week==Calendar.FRIDAY && this.pFriday.getValue()) have_to_check = true ;
			if (day_of_week==Calendar.SATURDAY && this.pSaturday.getValue()) have_to_check = true ;
			if (have_to_check){ 	
				supossedDatetoRun = JDateTools.StringToDate(only_date + " " + hour_to_run, "yyyyMMdd HHmmss");
			}
  	}
  	if (pType.equals(TYPE_WEEKLY)) {
  		boolean have_to_check = false;
			Calendar cal = Calendar.getInstance();
			cal.setTime(ahora);
			int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
			if (day_of_week==Calendar.SUNDAY && this.pSunday.getValue()) have_to_check = true ;
			if (day_of_week==Calendar.MONDAY && this.pMonday.getValue()) have_to_check = true ;
			if (day_of_week==Calendar.TUESDAY && this.pTuesday.getValue()) have_to_check = true ;
			if (day_of_week==Calendar.WEDNESDAY && this.pWednesday.getValue()) have_to_check = true ;
			if (day_of_week==Calendar.THURSDAY && this.pThursday.getValue()) have_to_check = true ;
			if (day_of_week==Calendar.FRIDAY && this.pFriday.getValue()) have_to_check = true ;
			if (day_of_week==Calendar.SATURDAY && this.pSaturday.getValue()) have_to_check = true ;
			if (have_to_check){ 	
				supossedDatetoRun = JDateTools.StringToDate(only_date + " " + hour_to_run, "yyyyMMdd HHmmss");
			}
  	}
  	return supossedDatetoRun;
 
  }
  
	public void UpdateWithLastRun() throws Exception {
		if (this.getNow()!=null)
			this.setLastrun(this.getNow());
		else
			this.setLastrun(new Date());
		this.processUpdate();
	}
	
  public void execProcessReport(final boolean actualizaLastRun) throws Exception {
  	JExec exec = new JExec(null,"processReport") {
  		public void Do() throws Exception {
  			processReport(true, actualizaLastRun);
  		}
  	};
  	exec.execute();
  }
  
	public void processReport(boolean vulneraHasToRun, boolean actualizaLastRun) throws Exception {
		try {
			if (!this.isActive()) return ;
			this.pNow =  new Date();
      boolean hasToRun = (vulneraHasToRun)?true:this.hasToRun();
			if (hasToRun) {
				this.getObjReportList().ejecutarReporteyEnviarMails();
				if (actualizaLastRun)
					this.UpdateWithLastRun();
			} else {
				PssLogger.logInfo("No Corresponde Ejecutar"); //$NON-NLS-1$
			}
		}catch  (Exception e) {
			throw e;
		}
	}  
	
	public BizReportList getObjReportList() throws Exception {
		if (this.reportList != null) return this.reportList;
		BizReportList r = new BizReportList();
		r.read(this.pCompany.getValue(), this.pReportId.getValue());
		return (this.reportList = r);
	}

   
}
