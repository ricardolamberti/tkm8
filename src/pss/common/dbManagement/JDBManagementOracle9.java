package  pss.common.dbManagement;

import pss.JPath;
import pss.core.data.interfaces.connections.JBDato;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.tools.JDateTools;
import pss.core.tools.JOSCommand;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;


public class JDBManagementOracle9 extends JBaseDBManagement { 
  
  public JDBManagementOracle9() throws Exception {
  	this.setBase(JBDatos.getBaseMaster());
  }
  
  /**
   * Chequea si existe la base de datos pasada por parametros existe.
   */
  @Override
	protected boolean existDatabase(JBDato zDefaultDatabase) throws Exception {
    boolean bResult = false;
    this.initString();
    String sUsername = zDefaultDatabase.GetUsername();
    this.pStrBuffer.append("select username from sys.dba_users where username = '");
    this.pStrBuffer.append(sUsername.toUpperCase());
    this.pStrBuffer.append("'");
    PssLogger.logDebugSQL("Ejecutando... [ " + pStrBuffer.toString() + " ]");
    JBaseRegistro oRow = this.getBase().createRowInterface();
    
    try {
      oRow.ExecuteQuery(pStrBuffer.toString());
      bResult = oRow.next();
      
    } finally {
      if (oRow != null) {
        oRow.close();
      }
    }
    //this.onFinish();
    return bResult;
  }  
  
  
  
  @Override
	protected void createDefaultDatabase(JBDato zDatabase) throws Exception {
    JBaseRegistro oDmlStatement = this.getBase().createRowInterface();
    JBaseRegistro oSqlStatement = this.getBase().createRowInterface();
    
    String sSelectTableSpace = "SELECT TABLESPACE_NAME FROM USER_TABLESPACES WHERE TABLESPACE_NAME = 'USERS'";
    String sPath = JPath.PssPathData();
    String sDataFiles = "'"+sPath+"/users01.dbf'";
    String sCreateTableSpaceSQL = "CREATE TABLESPACE USERS DATAFILE " + sDataFiles +" size 1000M REUSE AUTOEXTEND ON ";
    String sUserSQL = "CREATE USER \"" + zDatabase.GetUsername() + "\" PROFILE \"DEFAULT\" IDENTIFIED BY " + 
                  "\"" + zDatabase.getPassword() + "\" DEFAULT TABLESPACE \"USERS\" ACCOUNT UNLOCK";
    String sGrantConnectSQL = "GRANT \"CONNECT\" TO \"" + zDatabase.GetUsername() + "\"";
    String sGrantPermissionSQL = "GRANT \"DBA\" TO \"" + zDatabase.GetUsername() + "\""; 
    
    try {
      this.getBase().beginTransaction();
      oSqlStatement.ExecuteQuery(sSelectTableSpace);
      boolean founded = oSqlStatement.next();
      if (!founded) {
        oDmlStatement.Execute(sCreateTableSpaceSQL.toUpperCase());
      }
      oDmlStatement.Execute(sUserSQL.toUpperCase());
      PssLogger.logDebug("DB MANAGEMENT: User created ok.");
      oDmlStatement.Execute(sGrantConnectSQL.toUpperCase());
      PssLogger.logDebug("DB MANAGEMENT: User connection granted ok.");
      oDmlStatement.Execute(sGrantPermissionSQL.toUpperCase());
      PssLogger.logDebug("DB MANAGEMENT: User permission granted ok.");
      this.getBase().commit();
    } catch (Exception e) {
      this.getBase().rollback();
      throw e;      
    } finally {
      oSqlStatement.close();
      oDmlStatement.close();
    }
  }


  @Override
	public void optimizeDatabase(String zEsquema) throws Exception {
    //begin DBMS_UTILITY.ANALYZE_SCHEMA( 'Pss_ADMIN','COMPUTE'); end; 
    JBaseRegistro oDmlStatement = this.getBase().createRowInterface();

    String sOptimize = "begin DBMS_UTILITY.ANALYZE_SCHEMA( '"+zEsquema+"','COMPUTE'); end;";
    try {
      if (JBDatos.GetBases().getPrivateCurrentDatabase().isTransactionInProgress()){
        oDmlStatement.Execute(sOptimize.toUpperCase());
      }else {
        this.oBDato.beginTransaction();
        oDmlStatement.Execute(sOptimize.toUpperCase());
        this.oBDato.commit();
      }
      PssLogger.logDebug("DB MANAGEMENT: "+zEsquema+" Optimize DB ok.");
    } catch (Exception e) {
      this.oBDato.rollback();
      throw e;      
    } finally {
      oDmlStatement.close();
    }
  }

  /**
   * Realiza un backup de la base de datos configurada default en el Pss.ini El nombre lógico es BD_AAMMDD_HHMMSS donde: BD=Base de datos default;
   * AAMMDD=Anio Mes y Dia ; HHHMMSS=Hora Minuto Segundo El nombre del archivo fisico es el nombre lógico con extension 'bak'.
   */
  public static String backupDefault() throws Exception {
    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
    return JBaseDBManagement.VirtualCreate().backupDatabaseDefault();
  }

  /**
   * Realiza un backup de la base de datos especificada
   * @return El nombre lógico del backup
   */
  @Override
	protected String backupDatabase(String zDBName) throws Exception {
    JTools.MakeDirectory(JPath.PssPathData());
    JTools.MakeDirectory(JPath.PssPathBackup());
    this.purgeFileBackup(zDBName, 9);
    this.initString();
    // pStrBuffer = nombre de la base + fecha + hora
    this.pStrBuffer.append(zDBName);
    this.pStrBuffer.append("_");
    this.pStrBuffer.append(JDateTools.CurrentDate("yyyyMMdd"));
    this.pStrBuffer.append("_");
    this.pStrBuffer.append(JDateTools.CurrentTime("HHmmss"));
    this.backupDatabase(zDBName, this.pStrBuffer.toString(), JPath.PssPathBackup() + "/" + this.pStrBuffer.toString() + ".bak");
    return pStrBuffer.toString();
  }
  
  /**
   * Realiza un backup de la base de datos y nombre lógico especificados. El nombre del archivo backup es el mismo que el nomrbe lógico.
   */
  @Override
	protected void backupDatabase(String zDBName, String zLogicalName) throws Exception {
    JTools.MakeDirectory(JPath.PssPathData());
    JTools.MakeDirectory(JPath.PssPathBackup());
    backupDatabase(zDBName, zLogicalName, JPath.PssPathBackup() + "\\" + zLogicalName + ".bak");
  }
  

  @Override
	protected String backupDatabaseDefault() throws Exception {
    return backupDatabase(JBDatos.createDatabaseDefaultFromConfig().getDatabaseName());
  }
  
  @Override
	protected void backupDatabaseDefault(String zLogicalName) throws Exception {
    backupDatabase(JBDatos.createDatabaseDefaultFromConfig().getDatabaseName(), zLogicalName);
  }

  
  /**
   * Executes the database backup
   * @param zDatabaseName
   * @param zLogicalDBName
   * @throws Exception
   */
  private void executeBackupDatabase(String zDatabaseName, String zLogicalDBName) throws Exception {
    String sUserName = JBDatos.getBaseMaster().GetUsername();
    String sPassword = JBDatos.getBaseMaster().getPassword();
    
    
    
    try {
    String sCommand =  "c:/oracle/ora81/bin/Exp.exe " + sUserName.trim()+"/"+sPassword+"@"+zDatabaseName.trim() +
    " FILE="+ JPath.PssPathBackup()+"/"+zLogicalDBName +".dmp";
    PssLogger.logDebug("Sentencia Backup...Comienzo");
    
    sCommand += " noout";
    
    final String sFinalCommand = sCommand;
    Thread oThread = new Thread() {
      @Override
			public void run() {
        try {
          
            JOSCommand oCommand = new JOSCommand( sFinalCommand );
            oCommand.executeBackground();
          
        } catch( Exception e ) {
          PssLogger.logDebug(e);
        }
      }
    };
    oThread.setPriority( Thread.MIN_PRIORITY );
    oThread.start();
  } catch (Exception ex) {
    PssLogger.logError("Error en Ejecucion de Bakup");
    PssLogger.logError(ex);
  }

    
    
    
    PssLogger.logDebug("Sentencia Backup...Fin.");

  }


  @SuppressWarnings("unused")
	private void setAutoCommit(boolean zValue) throws Exception {
    this.getBase().GetConnection().setAutoCommit(zValue);
  }

   
}
