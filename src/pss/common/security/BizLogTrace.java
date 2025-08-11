package  pss.common.security;

import java.util.Date;

import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.crypt.AESCrypt;

public class BizLogTrace extends JRecord {


  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  JLong   pNrotrace    = new JLong  ();
  JString pUsuario     = new JString();
  JDateTime pFechaHora = new JDateTime(true);
  JString pOperacion   = new JString();
  JString pDatos       = new JString();
  JString pLoginSource = new JString();
  JString pCompany       = new JString();
  JString pOutAccess       = new JString();
  JString pNombre       = new JString() {
  	public void preset() throws Exception {
  		pNombre.setValue(getNombre());
  	};
  };
  JString pOutAccessDetail       = new JString() {
  	public void preset() throws Exception {
  		pOutAccessDetail.setValue(getOutAccessDetail());
  	};
  };

  public static final String C_LOGIN      = "LOGIN";
  public static final String C_LOGINPERSISTENT = "LOGINPERSISTENT";
  public static final String C_LOGOUT     = "LOGOUT";
  public static final String C_LOG_FAILED = "LOGFAILED";

  // ---------------------------------------- //
  // CONSTANTES PARA EL LOGUEO DE ACTIVIDADES //
  // ---------------------------------------- //
  public static final String C_LOGUEAR_TODO       =  "T";
  public static final String C_LOGUEAR_NADA       =  "N";

  public String getUsuario() throws Exception { return pUsuario.getValue();}
  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizLogTrace() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    addItem( "NROTRACE",  pNrotrace );
    addItem( "USUARIO", pUsuario );
    addItem( "FECHA", pFechaHora );
    addItem( "OPERACION", pOperacion);
    addItem( "DATOS", pDatos );
    addItem( "ORIGEN_LOGIN",pLoginSource );
    addItem( "company"       ,pCompany );
    addItem( "out_access",pOutAccess );
    addItem( "nombre",pNombre );
    addItem( "out_access_detail",pOutAccessDetail );
  }// end method

  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem( KEY, "NROTRACE", "Nrotrace", false, false, 18 );
    addFixedItem( FIELD, "USUARIO", "Usuario", true, true, 15 );
    addFixedItem( FIELD, "FECHA", "Fecha/Hora", true, true, 10 );
    addFixedItem( FIELD, "OPERACION", "Operacion", true, true, 80 );
    addFixedItem( FIELD, "DATOS", "Actividad", true, false, 4000 );
    addFixedItem( FIELD, "ORIGEN_LOGIN", "Origen", true,true,50);
    addFixedItem( FIELD, "company"       , "Empresa", true,false,80);
    addFixedItem( FIELD, "out_access"     , "Acceso remoto", true,false,1);
    addFixedItem( VIRTUAL, "nombre"     , "Nombre", true,false,200);
    addFixedItem( VIRTUAL, "out_access_detail"     , "Tipo acceso", true,false,200);
  }


  @Override
	public String GetTable() {return "SEG_LOGTRACE"; }

  //-------------------------------------------------------------------------//
  // Registro en la tabla de Trace
  //-------------------------------------------------------------------------//
  public static BizLogTrace register( String zOperacion, String data, String key) throws Exception {
  	return register(zOperacion,data, JTools.isOutAccess( BizUsuario.getUsr().getLoginSource())?"S":"N",key);
  }
  
  // rjl, eto estaba comentado y descontinuado, lo reactivo para hacer la persistencia
 public static BizLogTrace register( String zOperacion, String data, String outAccess, String key) throws Exception {
	 BizLogTrace oTrace = null;

	 if (zOperacion.equals(C_LOGINPERSISTENT)) {
		  oTrace = new BizLogTrace();
	    oTrace.pOperacion.setValue(zOperacion);
	    oTrace.pDatos.setValue( key!=null? JTools.encryptMessage(key):"" );
	    oTrace.pUsuario.setValue(BizUsuario.getCurrentUser());
	    oTrace.pCompany.setValue(BizUsuario.getUsr().getCompany());
	    oTrace.pFechaHora.setValue( new Date());
	    oTrace.pLoginSource.setValue( BizUsuario.getUsr().getLoginSource() );
	    oTrace.pOutAccess.setValue(outAccess);
	    oTrace.processInsert();
	    
	    return oTrace;		 
	 }
	 
	 if ( BizUsuario.getUsr().hasToLog()) {
    oTrace = new BizLogTrace();
    oTrace.pOperacion.setValue(zOperacion);
    oTrace.pDatos.setValue(data);
    oTrace.pUsuario.setValue(BizUsuario.getCurrentUser());
    oTrace.pCompany.setValue(BizUsuario.getUsr().getCompany());
    oTrace.pFechaHora.setValue( BizUsuario.getUsr().todayGMT());
    oTrace.pLoginSource.setValue( BizUsuario.getUsr().getLoginSource() );
    oTrace.pOutAccess.setValue(outAccess);
    oTrace.processInsert();
   
   }
	 return oTrace;

  }// end method
  
 	public String getKey() throws Exception {
 		return pDatos.getValue();
 	}
 	
 	public long getNroTrace() throws Exception {
 		return pNrotrace.getValue();
 	}
 	
  public boolean read(String key) throws Exception {
  	addFilter("DATOS",key);
  	return read();
  }
 
  BizUsuario objUsuario;
  public BizUsuario getObjUsuario() throws Exception {
  	if (pUsuario.isNull()) return null;
  	if (objUsuario!=null) return objUsuario;
  	BizUsuario u = new BizUsuario();
  	u.dontThrowException(true);
  	if (!u.Read(pUsuario.getValue())) return null;
  	return objUsuario=u;
  	
  }
  public String getNombre() throws Exception {
  	BizUsuario usu = getObjUsuario();
  	if (usu==null)return "";
  	return usu.getDescrUsuario();
  }
  public String getOutAccessDetail() throws Exception {
  	if (pOutAccess.isNull()) return "INTRANET";
  	if (pOutAccess.getValue().equals("N")) return "INTRANET";
  	if (pOutAccess.getValue().equals("S")) return "WEB";
  	if (pOutAccess.getValue().equals("M")) return "MOVIL";
  	return "";
  }
}// end class
