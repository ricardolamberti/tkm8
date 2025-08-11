package  pss.common.security;

import pss.common.components.JSetupParameters;
import pss.common.regions.company.BizCompany;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;

public class BizSegConfiguracion extends JRecord {

  public static final int C_MIN_PASSWORD_LEN = 4;
  public static final int C_MAX_PASSWORD_LEN = 15;
  public static final int C_MIN_USERNAME_LENGTH = 4;
  public static final int C_MAX_USERNAME_LENGTH = 50;
  public static final int C_MAX_ACTIVE_USERS = 5;
  
  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  private JString pCompany = new JString();
  private JLong pLongMax_user = new JLong();
  private JLong pLongMin_user = new JLong();
  private JLong pUserIdle_time = new JLong();
  private JString pDescCompany = new JString(){ @Override
	public void preset() throws Exception {pDescCompany.setValue(asignDescCompany());}};
  private JLong pLongMin_password = new JLong();
  private JLong pLongMax_password = new JLong();
  private JLong pMaxPasswordRetries = new JLong();
  private JLong pPasswordExpired = new JLong();
  private JLong pMaxActiveUsers = new JLong(); //Si es 0, es ilimitado
  
  private BizCompany company;

  public void   SetLongMax_user(long zVal ) throws Exception { pLongMax_user.setValue( zVal ); }
  public long GetLongMax_user() throws Exception { return pLongMax_user.getValue(); }
  public void   SetLongMin_user(long zVal ) throws Exception { pLongMin_user.setValue( zVal ); }
  public long GetLongMin_user() throws Exception { return pLongMin_user.getValue(); }
  public void   SetUserIdle_time(long zVal ) throws Exception { pUserIdle_time.setValue( zVal ); }
  public long GetUserIdle_time() throws Exception { return pUserIdle_time.getValue(); }
  public void   setCompany(String zVal ) throws Exception { pCompany.setValue( zVal ); }
  public String getCompany() throws Exception { return pCompany.getValue(); }
  public void   SetLongMin_password(long zVal ) throws Exception { pLongMin_password.setValue( zVal ); }
  public long getLongMinPassword() throws Exception { return pLongMin_password.getValue(); }
  public void   SetLongMax_password(long zVal ) throws Exception { pLongMax_password.setValue( zVal ); }
  public long getLongMaxPassword() throws Exception { return pLongMax_password.getValue(); }
  public long getMaxPasswordRetries() throws Exception { return pMaxPasswordRetries.getValue(); }
  public long getPasswordexpired() throws Exception { return pPasswordExpired.getValue(); }
  public void   setMaxActiveUsers(long zVal ) throws Exception { pMaxActiveUsers.setValue( zVal ); }
  public long getMaxActiveUsers() throws Exception { return pMaxActiveUsers.getValue(); }
  


  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizSegConfiguracion() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    addItem( "company", pCompany );
    addItem( "long_max_user", pLongMax_user );
    addItem( "long_min_user", pLongMin_user );
    addItem( "user_idle_time", pUserIdle_time );
    addItem( "long_min_password", pLongMin_password );
    addItem( "long_max_password", pLongMax_password );
    addItem( "max_password_retries", pMaxPasswordRetries );
    addItem( "password_expired", pPasswordExpired );
    addItem( "max_active_users", pMaxActiveUsers );
    addItem( "desc_company", pDescCompany);
  }

  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem( KEY, "company", "Empresa", true, true, 15 );
    addFixedItem( FIELD, "long_max_user", "Long max usuario", true, true, 9 );
    addFixedItem( FIELD, "long_min_user", "Long min usuario", true, true, 9 );
    addFixedItem( FIELD, "user_idle_time", "Tiempo espera (segundos)", true, true, 9 );
    addFixedItem( FIELD, "long_min_password", "Long min Clave", true, true, 9 );
    addFixedItem( FIELD, "long_max_password", "Long max Clave", true, true, 9 );
    addFixedItem( FIELD, "max_password_retries", "Max reintentos clave", true, true, 5 );
    addFixedItem( FIELD, "password_expired", "Caducidad clave", true, true, 5 );
    addFixedItem( FIELD, "max_active_users", "Maximos Usuarios Activos", true, false, 5 );
    addFixedItem( VIRTUAL, "desc_company","Empresa",true,true,50);
  }

  @Override
	public String GetTable() {
    return "SEG_CONFIG";
  }
  

  public boolean Read(String zCompany) throws Exception {
    this.addFilter("company", zCompany);
    return this.Read();
  }

  @Override
	public void validateConstraints() throws Exception {
    if ( pLongMin_user.getValue() > pLongMax_user.getValue() ) {
      JExcepcion.SendError("La longitud minima del usuario no debe ser mayor a la maxima");
    }
    if ( pLongMin_password.getValue() > pLongMax_password.getValue() ) {
      JExcepcion.SendError("La longitud minima de la clave no debe ser mayor a la maxima");
    }
    if ( pLongMin_password.getValue() < C_MIN_PASSWORD_LEN ) {
      JExcepcion.SendError("La longitud minima de la clave no debe ser menor a " + C_MIN_PASSWORD_LEN );
    }
    if ( pLongMax_password.getValue() > C_MAX_PASSWORD_LEN ) {
      JExcepcion.SendError("La longitud maxima de la clave no debe ser mayor a " + C_MAX_PASSWORD_LEN );
    }
    if ( pLongMax_password.getValue() == 0  ) {
      JExcepcion.SendError("La longitud maxima de la clave no debe ser cero");
    }
    if ( pLongMax_user.getValue() == 0 ) {
      JExcepcion.SendError("La longitud maxima del usuario no debe ser cero");
    }
    if ( pLongMax_user.getValue() > 50 ) {
      JExcepcion.SendError("La longitud maxima del usuario no debe ser mayor a " + 50 );
    }
    if ( pLongMin_user.getValue() < 4 ) {
      JExcepcion.SendError("La longitud minima del usuario no debe ser menor a " + 4 );
    }
  }

  public String asignDescCompany() throws Exception {
    BizCompany company = new BizCompany();
    company.Read(this.getCompany());
    return company.getDescription();
  }

  @Override
	protected void setupConfig(JSetupParameters zParams) throws Exception {
    zParams.setExportData(zParams.isLevelCountry());
    zParams.setTruncateData(zParams.isLevelCountry());
  }
  
  public static BizSegConfiguracion getDefault() throws Exception {
  	BizSegConfiguracion config = new BizSegConfiguracion();
  	config.pLongMax_password.setValue(C_MAX_PASSWORD_LEN);
  	config.pLongMin_password.setValue(C_MIN_PASSWORD_LEN);
  	config.pUserIdle_time.setValue(60);
  	config.pLongMax_user.setValue(C_MAX_USERNAME_LENGTH);
  	config.pLongMin_user.setValue(C_MIN_USERNAME_LENGTH);
  	config.pMaxActiveUsers.setValue(C_MAX_ACTIVE_USERS);
  	return config;
  }
  
	public JRecords<BizRol> getRolesJerarquicos() throws Exception {
		JRecords<BizRol> records=new JRecords<BizRol>(BizRol.class);
		records.addFilter("company", this.getCompany());
		records.addFilter("tipo", BizRoles.JERARQUIA);
		records.readAll();
		return records;
	}

	public JRecords<BizRol> getRolesAplicacion() throws Exception {
		JRecords<BizRol> records=new JRecords<BizRol>(BizRol.class);
		records.addFilter("company", this.getCompany());
		records.addFilter("tipo", BizRoles.NORMAL);
		records.readAll();
		return records;
	}

	public JRecords<BizOperacion> getOperaciones() throws Exception {
		JRecords<BizOperacion> records=new JRecords<BizOperacion>(BizOperacion.class);
		records.addFilter("company", this.getCompany());
		records.readAll();
		return records;
	}

	public void processDelete() throws Exception {
		this.getRolesJerarquicos().processDeleteAll();
		this.getRolesAplicacion().processDeleteAll();
		this.getOperaciones().processDeleteAll();
		super.processDelete();
	}
	
	public BizCompany getObjCompany() throws Exception {
		if (this.company!=null) return this.company;
		BizCompany c = new BizCompany();
		c.Read(this.pCompany.getValue());
		return (this.company=c);
	}
	
}
