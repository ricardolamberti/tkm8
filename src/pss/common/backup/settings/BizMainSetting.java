package  pss.common.backup.settings;

import java.util.Calendar;
import java.util.Date;

import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JHour;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;

public class BizMainSetting extends JRecord {
	
	
  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//

	private JString pCompany = new JString();
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
	

	private Date pLastRunned=null;
	private Date pNow=null;
	private JBoolean pShouldRun= new JBoolean();
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getCompany() throws Exception { return pCompany.getValue();  }
  public void setShouldRun(Boolean zValue) throws Exception {    pShouldRun.setValue(zValue) ;  }
  public Boolean getShouldRun() throws Exception { return pShouldRun.getValue();  }
  public String getOutputPath() throws Exception { return pOutputPath.getValue();  }
  public Boolean isActive() throws Exception { return pActive.getValue();  }
  public Date getLastrun() throws Exception { return pLastrun.getValue();  }
  public void setLastrun(Date zValue) throws Exception { pLastrun.setValue(zValue);  }
  public Date getNow() throws Exception { return pNow;  }
  
  
  /**
   * Constructor de la Clase
   */
  public BizMainSetting() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
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
  }
  /**
   * Adds the fixed object properties
   */
  @Override
	public void createFixedProperties() throws Exception {
  	this.addFixedItem( KEY, "company", "Empresa", true, true, 15 );
  	this.addFixedItem( FIELD, "monday", "Lunes", true, true, 1 );
  	this.addFixedItem( FIELD, "tuesday", "Martes", true, true, 1 );
  	this.addFixedItem( FIELD, "wednesday", "Miércoles", true, true, 1 );
  	this.addFixedItem( FIELD, "thursday", "Jueves", true, true, 1);
    this.addFixedItem( FIELD, "friday", "Viernes", true, true, 1 );
    this.addFixedItem( FIELD, "saturday", "Sábado", true, true, 1 );
    this.addFixedItem( FIELD, "sunday", "Domingo", true, true, 1 );
    this.addFixedItem( FIELD, "time_to_run", "Hora de ejecución", true, true, 8 );
    this.addFixedItem( FIELD, "last_run", "Fecha/Hora ult. ejec.", true, false, 20 );
    this.addFixedItem( FIELD, "output_path", "Ruta de BackUp", true, false, 200 );
    this.addFixedItem( FIELD, "active", "Activo", true, true, 1 );
  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return "BCK_MAIN_SETTING"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean Read(String zCompany) throws Exception {
  	addFilter( "company",  zCompany );
    return read(); 
  } 
  
  public boolean hasToRun() throws Exception {
  	boolean mustRun = false;
  	Date t_now = JDateTools.StringToDateTime(JDateTools.CurrentDateTime());
  	String only_date = JDateTools.CurrentDate();
  	
  	Date t_LastRunned = this.pLastrun.getValue();
  	Date t_TodayforRun = JDateTools.StringToDate(JDateTools.CurrentDateTime());
  	Calendar TodayAndTimeToRun = Calendar.getInstance();
  	TodayAndTimeToRun.set(Integer.parseInt(only_date.substring(6,10)) , 
  												Integer.parseInt(only_date.substring(3,5))-1, 
  												Integer.parseInt(only_date.substring(0,2)), 
  												Integer.parseInt(this.pTimetorun.GetHour()),  
  												Integer.parseInt(this.pTimetorun.GetMinute()), 
  												Integer.parseInt(this.pTimetorun.GetSecond()));
  	t_TodayforRun = TodayAndTimeToRun.getTime();
  	this.pNow = t_now;
  	this.pLastRunned = t_LastRunned;
  	if (t_now.compareTo(t_TodayforRun) >= 0 ) {
  		if (t_TodayforRun.compareTo(t_LastRunned) >= 0 ) {
  			mustRun = true;
  		}
  	}
  	return mustRun;
  }
  
	public void Update() throws Exception {
		this.setLastrun(this.getNow());
		super.execProcessUpdate();
	}
   
}
