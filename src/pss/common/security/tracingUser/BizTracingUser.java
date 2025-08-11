package  pss.common.security.tracingUser;

import java.util.Date;

import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizTracingUser extends JRecord {

	private JLong pIdTracing = new JLong();
  private JString pEmail = new JString();
  private JString pEmpresa = new JString();
  private JString pPais = new JString();
  private JString pClave = new JString();
  private JString pNombre = new JString();
	private JDateTime pFechaRegistro = new JDateTime();
	private JDateTime pFechaBackRegistro = new JDateTime();
	private JDateTime pFechaFirstLogin = new JDateTime();
  private JBoolean pSendMail = new JBoolean();
  private JString pUsuario = new JString();
  private JString pCompany = new JString();
   private JString pProblems = new JString();
   private JLong pIntentos = new JLong();
   

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setIdTracing(long zValue) throws Exception {    pIdTracing.setValue(zValue);  }
  public long getIdTracing() throws Exception {     return pIdTracing.getValue();  }
  public boolean isNullIdTracing() throws Exception { return  pIdTracing.isNull(); } 
  public void setNullToIdTracing() throws Exception {  pIdTracing.setNull(); } 

  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 

  public void setClave(String zValue) throws Exception {    pClave.setValue(zValue);  }
  public String getClave() throws Exception {     return pClave.getValue();  }
  public boolean isNullClave() throws Exception { return  pClave.isNull(); } 
  public void setNullToClave() throws Exception {  pClave.setNull(); } 

  public void setPais(String zValue) throws Exception {    pPais.setValue(zValue);  }
  public String getPais() throws Exception {     return pPais.getValue();  }
  public boolean isNullPais() throws Exception { return  pPais.isNull(); } 
  public void setNullToPais() throws Exception {  pPais.setNull(); } 

  public void setUsuario(String zValue) throws Exception {    pUsuario.setValue(zValue);  }
  public String getUsuario() throws Exception {     return pUsuario.getValue();  }
  public boolean isNullUsuario() throws Exception { return  pUsuario.isNull(); } 
  public void setNullToUsuario() throws Exception {  pUsuario.setNull(); } 

  public void setEmail(String zValue) throws Exception {    pEmail.setValue(zValue);  }
  public String getEmail() throws Exception {     return pEmail.getValue();  }
  public boolean isNullEmail() throws Exception { return  pEmail.isNull(); } 
  public void setNullToEmail() throws Exception {  pEmail.setNull(); } 

  public void setNombre(String zValue) throws Exception {    pNombre.setValue(zValue);  }
  public String getNombre() throws Exception {     return pNombre.getValue();  }
  public boolean isNullNombre() throws Exception { return  pNombre.isNull(); } 
  public void setNullToNombre() throws Exception {  pNombre.setNull(); } 

  public void setEmpresa(String zValue) throws Exception {    pEmpresa.setValue(zValue);  }
  public String getEmpresa() throws Exception {     return pEmpresa.getValue();  }
  public boolean isNullEmpresa() throws Exception { return  pEmpresa.isNull(); } 
  public void setNullToEmpresa() throws Exception {  pEmpresa.setNull(); } 

  public void setFechaRegistro(Date zValue) throws Exception {    pFechaRegistro.setValue(zValue);  }
  public Date getFechaRegistro() throws Exception {     return pFechaRegistro.getValue();  }
  public boolean isNullFechaRegistro() throws Exception { return  pFechaRegistro.isNull(); } 
  public void setNullToFechaRegistro() throws Exception {  pFechaRegistro.setNull(); } 

  public void setFechaVueltaRegistro(Date zValue) throws Exception {    pFechaBackRegistro.setValue(zValue);  }
  public Date getFechaVueltaRegistro() throws Exception {     return pFechaBackRegistro.getValue();  }
  public boolean isNullFechaVueltaRegistro() throws Exception { return  pFechaBackRegistro.isNull(); } 
  public void setNullToFechaVueltaRegistro() throws Exception {  pFechaBackRegistro.setNull(); } 

  public void setFechaFirstLogin(Date zValue) throws Exception {    pFechaFirstLogin.setValue(zValue);  }
  public Date getFechaFirstLogin() throws Exception {     return pFechaFirstLogin.getValue();  }
  public boolean isNullFechaFirstLogin() throws Exception { return  pFechaFirstLogin.isNull(); } 
  public void setNullToFechaFirstLogin() throws Exception {  pFechaFirstLogin.setNull(); } 

  public void setSendMail(boolean zValue) throws Exception {    pSendMail.setValue(zValue);  }
  public boolean getSendMail() throws Exception {     return pSendMail.getValue();  }
  public boolean isNullSendMail() throws Exception { return  pSendMail.isNull(); } 
  public void setNullToSendMail() throws Exception {  pSendMail.setNull(); } 

  public void setProblems(String zValue) throws Exception {    pProblems.setValue(zValue);  }
  public String getProblems() throws Exception {     return pProblems.getValue();  }
  public boolean isNullProblems() throws Exception { return  pProblems.isNull(); } 
  public void setNullToProblems() throws Exception {  pProblems.setNull(); } 

  public void setIntentos(long zValue) throws Exception {    pIntentos.setValue(zValue);  }
  public long getIntentos() throws Exception {     return pIntentos.getValue();  }
  public boolean isNullIntentos() throws Exception { return  pIntentos.isNull(); } 
  public void setNullToIntentos() throws Exception {  pIntentos.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizTracingUser() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id_tracing", pIdTracing );
    this.addItem( "email", pEmail );
    this.addItem( "empresa", pEmpresa );
    this.addItem( "Nombre", pNombre );
    this.addItem( "date_register", pFechaRegistro);
    this.addItem( "date_backregister", pFechaBackRegistro);
    this.addItem( "date_login", pFechaFirstLogin );
    this.addItem( "send_mail", pSendMail );
    this.addItem( "company", pCompany );
    this.addItem( "id_usuario", pUsuario );
    this.addItem( "pais", pPais );
    this.addItem( "clave", pClave );
    this.addItem( "intentos", pIntentos );
    this.addItem( "problems", pProblems );
   }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_tracing", "Id track", false, false, 64 );
    this.addFixedItem( FIELD, "company", "Company", true, false, 15 );
    this.addFixedItem( FIELD, "id_usuario", "Usuario", true, false, 15 );
    this.addFixedItem( FIELD, "intentos", "Intentos", true, false, 18 );
    this.addFixedItem( FIELD, "email", "Email", true, true, 400 );
    this.addFixedItem( FIELD, "empresa", "empresa", true, false, 400 );
    this.addFixedItem( FIELD, "nombre", "nombre", true, false, 400 );
    this.addFixedItem( FIELD, "pais", "pais", true, false, 400 );
    this.addFixedItem( FIELD, "clave", "clave", true, false, 800 );
    this.addFixedItem( FIELD, "problems", "problemas", true, false, 4000 );
    this.addFixedItem( FIELD, "date_register", "fecha registro", true, false, 18 );
    this.addFixedItem( FIELD, "date_backregister", "fecha vuelta registro", true, false, 18 );
    this.addFixedItem( FIELD, "date_login", "fecha login", true, false, 18 );
    this.addFixedItem( FIELD, "send_mail", "envio mail", true, false, 1 );
  }

   /**
   * Returns the table name
   */
  public String GetTable() { return "seg_tracinguser"; }

  /**
   * Default read() method
   */
  public boolean read(long zidTracing ) throws Exception { 
    addFilter( "id_tracing",  zidTracing ); 
    return read(); 
  } 
  public boolean readByEmail(String zEmail ) throws Exception { 
    addFilter( "email",  zEmail ); 
    return read(); 
  } 

  public static BizTracingUser registerSecond(String sEmail,String sKey) throws Exception {
  	BizTracingUser newtr = new BizTracingUser();
  	newtr.dontThrowException(true);
  	if (newtr.readByEmail(sEmail)) {
  		newtr.setClave(sKey);
  		newtr.setFechaVueltaRegistro(new Date());
  		newtr.execProcessUpdate();
  	 	return newtr;
  	}
  	return null;
  }
  
  public void registerThird(String sEmail,String sCompany,String sUsuario) throws Exception {
  		setFechaFirstLogin(new Date());
  		setUsuario(sUsuario);
  		setCompany(sCompany);
  		execProcessUpdate();
  }
  public static BizTracingUser registerIncial(String sUsername,String sCompany,String sEmail,String sPais) throws Exception {
  	BizTracingUser newtr = new BizTracingUser();
  	newtr.dontThrowException(true);
  	if (newtr.readByEmail(sEmail)) {
    	newtr.setIntentos(newtr.getIntentos()+1);
  	} else {
    	newtr = new BizTracingUser();
    	newtr.setIntentos(1);
    	newtr.setProblems("");
  	}
  	newtr.setEmail(sEmail);
  	newtr.setNombre(sUsername);
  	newtr.setPais(sPais);
  	newtr.setEmpresa(sCompany);
  	newtr.setFechaRegistro(new Date());
  	newtr.setIntentos(1);
  	newtr.execProcessUpdateOrInsert();
  	return newtr;
  }

  public void registerSend() throws Exception {
  	this.setSendMail(true);
  	execProcessUpdate();
  }

  public void registerProblem(String problem) throws Exception {
  	this.setProblems(this.getProblems()+"|"+problem);
  	execProcessUpdate();
  }

  
}
