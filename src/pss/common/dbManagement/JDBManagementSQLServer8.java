package  pss.common.dbManagement;

import java.io.File;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import pss.JPath;
import pss.core.data.BizPssConfig;
import pss.core.data.interfaces.connections.JBDato;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.connections.JBaseJDBC;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JStringTokenizer;

/**
 * 
 *
 */
public class JDBManagementSQLServer8 extends JBaseDBManagement {

  /**
   * Constructor
   * @throws Exception 
   */
  public JDBManagementSQLServer8() throws Exception {
  	this.setBase(JBDatos.getBaseMaster());
  }

  /**
   * Constructor
   */
  public JDBManagementSQLServer8(JBDato zBase) throws Exception {
    //   Es la base Master con el usuario Admin. La base ya esta abierta.
    this.setBase(zBase);
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
   * Adds a user to database
   */
  private void addUserToDatabase( String zUserName, String zPasswd, String zDatabase ) throws Exception {
    // lee y abre la base default con usuario ADMIN
    JBDato oBaseDef = JBDatos.createBaseFromConfig(null, JBaseJDBC.ADMIN_USER);
    if (oBaseDef == null) JExcepcion.SendError("No existe una Base Default");
    oBaseDef.open();

    this.addLogin(zUserName, zPasswd, zDatabase);
    this.grantAccessDB(zUserName, zUserName, oBaseDef);
    this.grantSystemRole(zUserName, "sysadmin", oBaseDef);
    this.grantDatabaseRole(zUserName, "db_owner", oBaseDef);
//    this.grantDatabaseRole(zUserName, "db_datareader", oBaseDef);
//    this.grantDatabaseRole(zUserName, "db_datawriter", oBaseDef);
    oBaseDef.close();
  }

  /**
   * Adds an authenticated user to database
   */
  protected void addUserAuthenticatedToDatabase(String zUserName, String zDatabase) throws Exception {
    // lee y abre la base default con usuario ADMIN
    JBDato oBaseDef = JBDatos.createBaseFromConfig(null, JBaseJDBC.ADMIN_USER);
    String sWinUser = oBaseDef.getHostName() + "\\" + zUserName;
    if (oBaseDef == null) JExcepcion.SendError("No existe una Base Default");
    oBaseDef.open();

    this.grantLogin(sWinUser);
    this.setDefaultDatabase(sWinUser, zDatabase);
    this.setCompatibility(zDatabase);
    //    this.grantAccessDB(sWinUser, zUserName, oBaseDef);
    //    this.grantSystemRole(zUserName, "sysadmin", oBaseDef);
    //    this.grantDatabaseRole(zUserName, "db_datareader", oBaseDef);
    //    this.grantDatabaseRole(zUserName, "db_datawriter", oBaseDef);
    oBaseDef.close();
  }

  @SuppressWarnings("unused")
	private void onBegin() throws Exception {
  // this.getBase().Abrir();
  // me aseguro que no esta dentro de un Begin Transaction, porque daria error
  //    this.getBase().Commit();
  }

  protected void restoreDatabaseDefault(String zLogicalDBName) throws Exception {
    this.restoreDatabase(JBDatos.createDatabaseDefaultFromConfig().getDatabaseName(), zLogicalDBName);
  }

  protected void restoreDatabaseDefaultFromFile(String zFileName) throws Exception {
    this.restoreDatabaseFromFile(JBDatos.createDatabaseDefaultFromConfig().getDatabaseName(), zFileName);
  }

  /**
   * *
   */
  protected void restoreDatabase(String zNewDatabase, String zLogicalDBName) throws Exception {
    boolean bOldAutoCommit = this.getAutoCommit();
    this.setAutoCommit(true);

    // Como se pide hacer un restore por medio del nombre lógico del backup y
    // no del archivo, entonces averigua el nombre del archivo y se fija si fue
    // comprimido
    String sPhName = this.getPhysicalName(zLogicalDBName);
    String sPhNameZip = sPhName + ".zip";
    File oFile = new File(sPhNameZip);
    if (oFile.isFile()) JBaseDBManagement.unzipFile(sPhNameZip);

    this.initString();
    pStrBuffer.append("restore database [");
    pStrBuffer.append(zNewDatabase);
    pStrBuffer.append("] from [");
    pStrBuffer.append(zLogicalDBName);
    pStrBuffer.append("] with recovery, ");
    pStrBuffer.append("replace, password='");
    pStrBuffer.append(JBaseDBManagement.getPassword());
    pStrBuffer.append("'");
    JBaseRegistro oBR = JBaseRegistro.VirtualCreate(this.getBase());
    try {
      oBR.executeWithoutTransaction(pStrBuffer.toString());
    } finally {
      if (oBR != null) oBR.close();
      this.setAutoCommit(bOldAutoCommit);
      if (oFile.isFile())
      // si existía el zip, entonces borra el archivo backup que fue
          // descomprimido
          // para restaurar la base
          (new File(sPhName)).delete();
    }
    try {
      String sDbName = BizPssConfig.getPssConfig().GetDatabaseNameDefault();
      JBDato bDato = JBDatos.createBaseFromConfig(sDbName, JBaseJDBC.ADMIN_USER);
      bDato.open();
      JStringTokenizer oTok = ((JBaseJDBC) this.getBase()).readUserPasswd(JBaseJDBC.COMMON_USER);
      String sUserName = oTok.nextToken();
      dropUser( sUserName, bDato );
    } catch (SQLException e) {
      PssLogger.logInfo("No se pudo eliminar el usuario, intentando crearlo nuevamente...");
    }

    createDefaultUser();
    this.onFinish();
  }

  protected void restoreDatabaseMoveTo(String zNewDatabase, String zFileName, String zPathMoveTo) throws Exception {

  if (JBaseDBManagement.isZipFile(zFileName)) {
    zFileName = JBaseDBManagement.unzipFile(zFileName);
  }

  String restoreFileListOnly = "restore filelistonly from disk='" + zFileName + "'";
  String dropSql = "drop database [" + zNewDatabase + "]";

  JBaseRegistro oDropStatement = JBaseRegistro.VirtualCreate(this.getBase());
  JBaseRegistro oRestoreStatement = JBaseRegistro.VirtualCreate(this.getBase());
  JBaseRegistro oFileListOnlyStatement = JBaseRegistro.VirtualCreate(this.getBase());

  try {
    oFileListOnlyStatement.ExecuteQuery(restoreFileListOnly);
  } catch (SQLException e) {
  	PssLogger.logInfo("No se pudo obtener el nombre logico de la Base de datos " + zFileName);
  }

  oFileListOnlyStatement.next();
  String oLogicalName = oFileListOnlyStatement.CampoAsStr("LogicalName");
  oFileListOnlyStatement.next();
  String oLogicalLogName = oFileListOnlyStatement.CampoAsStr("LogicalName");

  String restoreSQL = "restore database [" + zNewDatabase + "] from disk='" + zFileName + "' with recovery, replace ";
  restoreSQL = restoreSQL + ", MOVE '" + oLogicalName +  "'  TO '" + zPathMoveTo + "\\Pss.mdf'";
  restoreSQL = restoreSQL + ", MOVE '" + oLogicalLogName +  "'  TO '" + zPathMoveTo + "\\Pss.log'";

  try {
    oDropStatement.executeWithoutTransaction(dropSql);
  } catch (SQLException e) {
    PssLogger.logInfo("No se pudo eliminar la base, intentando hacer restore...");
  }
  try {
    oRestoreStatement.executeWithoutTransaction(restoreSQL);
  } finally {
    if (oDropStatement != null) {
      oDropStatement.close();
      oRestoreStatement.close();
    }
  }

  JBaseRegistro oBR = JBaseRegistro.VirtualCreate(this.getBase());
  try { oBR.executeWithoutTransaction( "sp_dropuser 'Pss_admin'" );  } catch (Exception e) { System.out.println("Error : " + e.getMessage());  }
  oBR.close();
  try { oBR.executeWithoutTransaction( "sp_configure 'allow updates', '1'"                 );  } catch (Exception e) { System.out.println("Error : " + e.getMessage());  }
  oBR.close();
  try { oBR.executeWithoutTransaction( "sp_configure 'show advanced options','1'"          );  } catch (Exception e) { System.out.println("Error : " + e.getMessage());  }
  oBR.close();
  try { oBR.executeWithoutTransaction( "sp_configure 'min server memory','32768'"          );  } catch (Exception e) { System.out.println("Error : " + e.getMessage());  }
  oBR.close();
  try { oBR.executeWithoutTransaction( "sp_configure 'max server memory','49152'"          );  } catch (Exception e) { System.out.println("Error : " + e.getMessage());  }
  oBR.close();
  try { oBR.executeWithoutTransaction( "reconfigure with override"                         );  } catch (Exception e) { System.out.println("Error : " + e.getMessage());  }
  oBR.close();


  try {
    String sDbName = BizPssConfig.getPssConfig().GetDatabaseNameDefault();
    JBDato bDato = JBDatos.createBaseFromConfig(sDbName, JBaseJDBC.ADMIN_USER);
    bDato.open();
    JStringTokenizer oTok = ((JBaseJDBC) this.getBase()).readUserPasswd(JBaseJDBC.COMMON_USER);
    String sUserName = oTok.nextToken();
    dropUser( sUserName, bDato );
  } catch (SQLException e) {
    PssLogger.logInfo("No se pudo eliminar el usuario, intentando crearlo nuevamente...");
  }
  createDefaultUser();
  this.onFinish();
}

  /**
   * Restore from file
   */

  protected void restoreDatabaseFromFile(String zNewDatabase, String zFileName) throws Exception {

    if (JBaseDBManagement.isZipFile(zFileName)) {
      zFileName = JBaseDBManagement.unzipFile(zFileName);
    }

    String dropSql = "drop database [" + zNewDatabase + "]";
    String restoreSQL = "restore database [" + zNewDatabase + "] from disk='" + zFileName + "' with recovery, replace";

    JBaseRegistro oDropStatement = JBaseRegistro.VirtualCreate(this.getBase());
    JBaseRegistro oRestoreStatement = JBaseRegistro.VirtualCreate(this.getBase());
    try {
      oDropStatement.executeWithoutTransaction(dropSql);
    } catch (SQLException e) {
    	PssLogger.logInfo("No se pudo eliminar la base, intentando hacer restore...");
    }
    try {
      oRestoreStatement.executeWithoutTransaction(restoreSQL);
    } finally {
      if (oDropStatement != null) {
        oDropStatement.close();
        oRestoreStatement.close();
      }
    }

    JBaseRegistro oBR = JBaseRegistro.VirtualCreate(this.getBase());
    try { oBR.executeWithoutTransaction( "sp_configure 'allow updates', '1'"                 );  } catch (Exception e) { System.out.println("Error : " + e.getMessage());  }
    oBR.close();
    try { oBR.executeWithoutTransaction( "sp_configure 'show advanced options','1'"          );  } catch (Exception e) { System.out.println("Error : " + e.getMessage());  }
    oBR.close();
    try { oBR.executeWithoutTransaction( "sp_configure 'min server memory','32768'"          );  } catch (Exception e) { System.out.println("Error : " + e.getMessage());  }
    oBR.close();
    try { oBR.executeWithoutTransaction( "sp_configure 'max server memory','49152'"          );  } catch (Exception e) { System.out.println("Error : " + e.getMessage());  }
    oBR.close();
    try { oBR.executeWithoutTransaction( "reconfigure with override"                         );  } catch (Exception e) { System.out.println("Error : " + e.getMessage());  }
    oBR.close();

    try {
      String sDbName = BizPssConfig.getPssConfig().GetDatabaseNameDefault();
      JBDato bDato = JBDatos.createBaseFromConfig(sDbName, JBaseJDBC.ADMIN_USER);
      bDato.open();
      JStringTokenizer oTok = ((JBaseJDBC) this.getBase()).readUserPasswd(JBaseJDBC.COMMON_USER);
      String sUserName = oTok.nextToken();
      dropUser( sUserName, bDato );
    } catch (SQLException e) {
      PssLogger.logInfo("No se pudo eliminar el usuario, intentando crearlo nuevamente...");
    }
    createDefaultUser();
    this.onFinish();
  }

 /* protected void restoreDatabaseFromFile(String zNewDatabase, String zFileName) throws Exception {
    boolean bOldAutoCommit = this.getAutoCommit();
    this.setAutoCommit(true);

    if (JBaseDBManagement.isZipFile(zFileName))
      zFileName = JBaseDBManagement.unzipFile(zFileName);

    this.initString();
    pStrBuffer.append("restore database [");
    pStrBuffer.append(zNewDatabase);
    pStrBuffer.append("] from disk='");
    pStrBuffer.append(zFileName);
    pStrBuffer.append("' with recovery, replace");
    //pStrBuffer.append(JBaseDBManagement.getPassword());
    //pStrBuffer.append("'");
    JBaseRegistro oBR = JBaseRegistro.VirtualCreate(this.getBase());
    try {
      oBR.executeWithoutTransaction(pStrBuffer.toString());
    } finally {
      if (oBR != null)
        oBR.Close();
      this.setAutoCommit(bOldAutoCommit);
    }


    try { oBR.executeWithoutTransaction( "sp_configure 'allow updates', '1'"                 );  } catch (Exception e) { System.out.println("Error : " + e.getMessage());  }
    oBR.Close();
    try { oBR.executeWithoutTransaction( "sp_configure 'show advanced options','1'"          );  } catch (Exception e) { System.out.println("Error : " + e.getMessage());  }
    oBR.Close();
    try { oBR.executeWithoutTransaction( "sp_configure 'min server memory','32768'"          );  } catch (Exception e) { System.out.println("Error : " + e.getMessage());  }
    oBR.Close();
    try { oBR.executeWithoutTransaction( "sp_configure 'max server memory','49152'"          );  } catch (Exception e) { System.out.println("Error : " + e.getMessage());  }
    oBR.Close();
    try { oBR.executeWithoutTransaction( "reconfigure with override"                         );  } catch (Exception e) { System.out.println("Error : " + e.getMessage());  }
    oBR.Close();
    try { oBR.executeWithoutTransaction( "use " + zNewDatabase                               );  } catch (Exception e) { System.out.println("Error : " + e.getMessage());  }
    oBR.Close();
    try { oBR.executeWithoutTransaction( "delete from sysusers where name = 'Pss_admin'"    );  } catch (Exception e) { System.out.println("Error : " + e.getMessage());  }
    oBR.Close();
    try { oBR.executeWithoutTransaction( "sp_droplogin 'Pss_admin'"                         );  } catch (Exception e) { System.out.println("Error : " + e.getMessage());  }
    oBR.Close();
    try { oBR.executeWithoutTransaction( "sp_revokedbaccess 'Pss_admin'"                    );  } catch (Exception e) { System.out.println("Error : " + e.getMessage());  }
    oBR.Close();
    try { oBR.executeWithoutTransaction( "sp_addlogin 'Pss_admin', 'Pss01', 'Pss'"        );  } catch (Exception e) { System.out.println("Error : " + e.getMessage());  }
    oBR.Close();
    try { oBR.executeWithoutTransaction( "sp_adduser 'Pss_admin', 'Pss_admin', 'db_owner'" );  } catch (Exception e) { System.out.println("Error : " + e.getMessage());  }
    oBR.Close();
    this.onFinish();
  }
*/

  protected String backupDatabaseDefault() throws Exception {
    return backupDatabase(JBDatos.createDatabaseDefaultFromConfig().getDatabaseName());
  }

  protected void backupDatabaseDefault(String zLogicalName) throws Exception {
    backupDatabase(JBDatos.createDatabaseDefaultFromConfig().getDatabaseName(), zLogicalName);
  }

  /**
   * Realiza un backup de la base de datos especificada
   * @return El nombre lógico del backup
   */
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
   * Crea una nueva base de datos. El path del .MDF es el que le asigne la base, que es donde esta instalado. Tambien el nombre lógico del log es el
   * nombre de la base + "_log" y el path es el mismo de la base de datos
   */
  protected void createDefaultDatabase(JBDato zDefaultDatabase) throws Exception {
    this.initString();
    String sDatabaseName = zDefaultDatabase.getDatabaseName();
    this.pStrBuffer.append("CREATE DATABASE ");
    this.pStrBuffer.append(sDatabaseName);
    String sLogicalName = sDatabaseName + "_DAT";
    String sPhisicalName = JPath.PssPathData() + "/" + sDatabaseName + ".MDF";
    if (sLogicalName != null && sLogicalName != null) {
      pStrBuffer.append(" ON ( NAME = ");
      pStrBuffer.append(sLogicalName);
      pStrBuffer.append(", FILENAME = '");
      pStrBuffer.append(sPhisicalName);
      pStrBuffer.append("' ) LOG ON ( NAME='");
      pStrBuffer.append(sDatabaseName);
      pStrBuffer.append("_LOG', FILENAME='");
      String sLog = sPhisicalName.substring(0, sPhisicalName.lastIndexOf("/") + 1) + sDatabaseName + ".LDF";
      pStrBuffer.append(sLog);
      pStrBuffer.append("' )");
    }

    //    boolean bOldAutoCommit = this.getAutoCommit();
    //    this.setAutoCommit(true);
    JBaseRegistro oReg = JBaseRegistro.VirtualCreate(this.getBase());
    try {
      oReg.executeWithoutTransaction(pStrBuffer.toString().replace('/', '\\'));
    } finally {
      if (oReg != null) {
        oReg.close();
      }
      //      this.setAutoCommit(bOldAutoCommit);
    }
    this.createDefaultUser();
    return;
  }

  /**
   * Creates the default user // TODO Arreglar si es de jdataconnect o de otro driver
   */
  private void createDefaultUser() throws Exception {

    boolean bSystemAuthenticated = ((JBaseJDBC) this.getBase()).getDriver().isSystemAuthenticated();
    if (bSystemAuthenticated) {
      String sUser = this.getBase().GetUserService();
      this.addUserAuthenticated(sUser, this.getBase().GetName());
    } else {
      JStringTokenizer oTok = ((JBaseJDBC) this.getBase()).readUserPasswd(JBaseJDBC.COMMON_USER);
      String sUserName = oTok.nextToken();
      String sPassword = oTok.nextToken();
      this.addUserToDatabase(sUserName, sPassword, JBDatos.createDatabaseDefaultFromConfig().getDatabaseName());
    }
  }

  /**
   * Open the master database in sqlserver 8
   */

  /**
   * Chequea si existe la base de datos pasada por parametros existe.
   */
  protected boolean existDatabase(JBDato zDefaultDatabase) throws Exception {
    boolean bResul = false;
    initString();
    pStrBuffer.append("select name from sysdatabases where name = '");
    pStrBuffer.append(zDefaultDatabase.getDatabaseName());
    pStrBuffer.append("'");
    PssLogger.logDebugSQL("Ejecutando... [ " + pStrBuffer.toString() + " ]");
    JBaseRegistro oReg = getBase().createRowInterface();
    try {
      oReg.ExecuteQuery(pStrBuffer.toString());
      bResul = oReg.next();
    } finally {
      if (oReg != null) oReg.close();
    }
    //    this.onFinish();
    return bResul;
  }

  /**
   * Realiza un backup de la base de datos y nombre lógico especificados. El nombre del archivo backup es el mismo que el nomrbe lógico.
   */
  protected void backupDatabase(String zDBName, String zLogicalName) throws Exception {
    JTools.MakeDirectory(JPath.PssPathData());
    JTools.MakeDirectory(JPath.PssPathBackup());
    backupDatabase(zDBName, zLogicalName, JPath.PssPathBackup() + "\\" + zLogicalName + ".bak");
  }


  protected void truncateLog(String zDatabaseName) throws Exception {
    JBaseRegistro oLogNameQuery = JBaseRegistro.VirtualCreate();
    JBaseRegistro oTruncateExec = JBaseRegistro.VirtualCreate();
    JBaseRegistro oShrinkfileExec = JBaseRegistro.VirtualCreate();
    try {
      // blakeada para obtener el name del archivo de transacciones / puede
      // fallar , no se..
      oLogNameQuery.ExecuteQuery("select name from sysfiles where filename LIKE '%.ldf%'");
      oLogNameQuery.next();
      String sLogName = oLogNameQuery.CampoAsStr("name").trim();
      oTruncateExec.executeWithoutTransaction("backup log " + zDatabaseName + " with truncate_only");
      oShrinkfileExec.executeQueryWithoutTransaction("DBCC SHRINKFILE(" + sLogName + ",0)");
    } catch (Exception e) {
    	PssLogger.logError("Falló la limpieza del Transaction LOG");
      PssLogger.logDebug(e);
    } finally {
      oLogNameQuery.close();
      oTruncateExec.close();
      oShrinkfileExec.close();
    }
  }

  /**
   * Informa si existe un disposivito en la base de datos
   * @param zLogicalName Nombre logico del dispositivo
   * @param zFileName Nombre del archivo fisico
   * @return true Si existe el disposivito; false si no existe
   */
  private boolean existsDevice(String zLogicalName, String zFileName) throws Exception {
    boolean bResult, bEncontro = false;
    String sStr = "select name,phyname from master.dbo.sysdevices " + " where name='" + zLogicalName + "' or phyname='" + zFileName + "'";
    JBaseRegistro oSetUpd = JBaseRegistro.VirtualCreate(this.getBase());
    try {
      oSetUpd.ExecuteQuery(sStr);
      while (bResult = oSetUpd.next()) {
        String name = oSetUpd.CampoAsStr("name");
        String phyname = oSetUpd.CampoAsStr("phyname");
        if (zLogicalName.equalsIgnoreCase(name) && zFileName.equalsIgnoreCase(phyname)) {
          oSetUpd.close();
          return true; // sale ok porque encontro device igual.
        }
        bEncontro = true; // indica si encontro al menos un dispositivo o
        // archivo
      }
      if (bEncontro) {
        JExcepcion.SendError("El nombre logico o el nombre del archivo del backup a realizar ya existe^: (" + zLogicalName + ")-(" + zFileName + ")");
      }
    } finally {
      if (oSetUpd != null) {
        oSetUpd.close();
      }
    }
    return bResult;
  }

  private void addDevice(String zLogicalDBName, String zFileName) throws Exception {
    try {
      JBaseRegistro oStatement = JBaseRegistro.VirtualCreate(this.getBase());
      oStatement.executeWithoutTransaction("exec sp_addumpdevice 'disk','" + zLogicalDBName + "','" + zFileName + "'");
    } catch (SQLException e) {
    	PssLogger.logError("Error al ejecutar sentencia AddDevice [" + e.getMessage() + "]");
      throw e;
    }
  }

  //    boolean bOldAutoCommit = this.getAutoCommit();
  //    this.setAutoCommit(true);
  //    try {
  //      oSetUpd.Execute("exec sp_addumpdevice 'disk','" + zLogicalDBName + "','" +
  // zFileName + "'");
  //    } catch (Exception e) {
  //      PssLogger.logError("Error al ejecutar sentencia AddDevice [" +
  // e.getMessage() + "]");
  //      throw (e);
  //    } finally {
  //      if (oSetUpd != null)
  //        oSetUpd.Close();
  //      this.setAutoCommit(bOldAutoCommit);
  //    }

  /**
   * Executes the database backup
   * @param zDatabaseName
   * @param zLogicalDBName
   * @throws Exception
   */
  private void executeBackupDatabase(String zDatabaseName, String zLogicalDBName) throws Exception {
    //    boolean bOldAutoCommit = this.getAutoCommit();
    this.setAutoCommit(true);
    JBaseRegistro oStatement = JBaseRegistro.VirtualCreate(this.getBase());
    String sSQL = "backup database " + zDatabaseName + " to " + zLogicalDBName + " with password='" + JBaseDBManagement.getPassword() + "'";
    PssLogger.logDebug("Sentencia Backup...Comienzo");
    try {
      oStatement.executeWithoutTransaction(sSQL);
    } catch (Exception e) {
    	PssLogger.logError("Error al ejecutar sentencia Backup [" + e.getMessage() + "]");
      throw (e);
    } finally {
      if (oStatement != null) {
        oStatement.close();
      }
      //this.setAutoCommit(bOldAutoCommit);
    }
    this.setAutoCommit(false);
    PssLogger.logDebug("Sentencia Backup...Fin.");

  }

  private void dropDevice(String zLogicalDBName, boolean zDeleteFile) throws Exception {
    JBaseRegistro oSetUpd = JBaseRegistro.VirtualCreate(this.getBase());
    try {
      oSetUpd.executeWithoutTransaction("exec sp_dropdevice '" + zLogicalDBName + "'" + (zDeleteFile ? ",'delfile'" : ""));
    } finally {
      if (oSetUpd != null) oSetUpd.close();
    }
  }

  /**
   * Crea un login en el motor SQL Server
   */
  private void addLogin(String zUserName, String zPasswd, String zDatabase) throws Exception {
    //    boolean bAutoCommit = false;
    this.initString();
    pStrBuffer.append("exec sp_addlogin @loginame='");
    pStrBuffer.append(zUserName);
    pStrBuffer.append("', @passwd='");
    pStrBuffer.append(zPasswd);
    pStrBuffer.append("', @defdb='");
    pStrBuffer.append(zDatabase);
    pStrBuffer.append("'");
    PssLogger.logDebugSQL("Ejecutando... [ " + pStrBuffer.toString() + " ]");
    JBaseRegistro oBR = JBaseRegistro.VirtualCreate(this.getBase());
    try {
      // se pone en autocommit porque no puede estar en dentro de una
      // transaccion
      //// bAutoCommit = this.getAutoCommit();
      //      this.setAutoCommit(true);

      oBR.executeWithoutTransaction(pStrBuffer.toString());
      PssLogger.logInfo("OK. Creación Login. Usuario: " + zUserName + " , Base de Datos: " + zDatabase);
    } catch (SQLException eSQL) {
      PssLogger.logError("ERROR. Creación Login. Usuario: " + zUserName + " , Base de Datos: " + zDatabase + " [ " + eSQL.getMessage() + " ]");
    } finally {
      if (oBR != null) oBR.close();
      //      this.setAutoCommit(bAutoCommit);
    }
  }

  /**
   * Le da al usuario permiso de acceso a la base Nota: no puede ejecutarse dentro de una transaccion
   */
  private void grantAccessDB(String zLoginName, String zUserDB, JBDato zBaseDefault) throws Exception {
    //    boolean bAutoCommit = false;
    this.initString();
    pStrBuffer.append("exec sp_grantdbaccess '");
    pStrBuffer.append(zLoginName);
    pStrBuffer.append("', '");
    pStrBuffer.append(zUserDB);
    //      pStrBuffer.append("dbo");
    pStrBuffer.append("'");
    PssLogger.logDebugSQL("Ejecutando... [ " + pStrBuffer.toString() + " ]");
    JBaseRegistro oBR = JBaseRegistro.VirtualCreate(zBaseDefault);
    try {
      // se pone en autocommit porque no puede estar en dentro de una
      // transaccion
      //      bAutoCommit = zBaseDefault.GetConnection().getAutoCommit();
      //      zBaseDefault.GetConnection().setAutoCommit(true);

      oBR.executeWithoutTransaction(pStrBuffer.toString());
      PssLogger.logInfo("OK. Acceso concedido. Usuario: " + zLoginName + " , Base de Datos: " + zBaseDefault.getDatabaseName());
    } catch (SQLException eSQL) {
    	PssLogger.logError("Error. Acceso no concedido. Usuario: " + zLoginName + " , Base de Datos: " + zBaseDefault.getDatabaseName() + " [ " + eSQL.getMessage() + " ]");
    } finally {
      if (oBR != null) oBR.close();
      //      zBaseDefault.GetConnection().setAutoCommit(bAutoCommit);
    }
  }

  private void grantSystemRole(String zUserName, String zSystemRole, JBDato zBaseDefault) throws Exception {
    //    boolean bAutoCommit = false;
    this.initString();
    pStrBuffer.append("exec sp_addsrvrolemember @loginame='");
    pStrBuffer.append(zUserName);
    pStrBuffer.append("', @rolename='");
    pStrBuffer.append(zSystemRole);
    pStrBuffer.append("'");
    PssLogger.logDebugSQL("Ejecutando... [ " + pStrBuffer.toString() + " ]");
    JBaseRegistro oBR = JBaseRegistro.VirtualCreate(zBaseDefault);
    try {
      // se pone en autocommit porque no puede estar en dentro de una
      // transaccion
      //      bAutoCommit = zBaseDefault.GetConnection().getAutoCommit();
      //      zBaseDefault.GetConnection().setAutoCommit(true);

      oBR.executeWithoutTransaction(pStrBuffer.toString());
      PssLogger.logInfo("OK. Rol de sistema '" + zSystemRole + "' concedido. Usuario: " + zUserName + " , Base de Datos: " + zBaseDefault.getDatabaseName());
    } catch (SQLException eSQL) {
    	PssLogger.logError("ERROR. Rol de sistema '" + zSystemRole + "'. Usuario: " + zUserName + " , Base de Datos: " + zBaseDefault.getDatabaseName() + " [ " + eSQL.getMessage() + " ]");
    } finally {
      if (oBR != null) oBR.close();
      //      zBaseDefault.GetConnection().setAutoCommit(bAutoCommit);
    }
  }

  /**
   * Da permisos al usuario como sysadmin. OJO con este permiso !
   */
  private void grantDatabaseRole(String zUserName, String zRole, JBDato zBaseDefault) throws Exception {
    //    boolean bAutoCommit = false;
    this.initString();
    pStrBuffer.append("exec sp_addrolemember @rolename='");
    pStrBuffer.append(zRole);
    pStrBuffer.append("', @membername='");
    pStrBuffer.append(zUserName);
    //    pStrBuffer.append("dbo");
    pStrBuffer.append("'");
    PssLogger.logDebugSQL("Ejecutando... [ " + pStrBuffer.toString() + " ]");
    JBaseRegistro oBR = JBaseRegistro.VirtualCreate(zBaseDefault);
    try {
      // se pone en autocommit porque no puede estar en dentro de una
      // transaccion
      //      bAutoCommit = zBaseDefault.GetConnection().getAutoCommit();
      //      zBaseDefault.GetConnection().setAutoCommit(true);

      oBR.executeWithoutTransaction(pStrBuffer.toString());
      PssLogger.logInfo("Rol '" + zRole + "' concedido OK. Usuario: " + zUserName + " , Base de Datos: " + zBaseDefault.getDatabaseName());
    } finally {
      if (oBR != null) oBR.close();
      //      zBaseDefault.GetConnection().setAutoCommit(bAutoCommit);
    }
  }

  /**
   * Permite al usuario de windows zUserName a acceder al motor SQL Server por por autenticación
   */
  private void grantLogin(String zUserName) throws Exception {
    //    boolean bAutoCommit = false;
    this.initString();
    pStrBuffer.append("exec sp_grantlogin '");
    pStrBuffer.append(zUserName);
    pStrBuffer.append("'");
    PssLogger.logDebugSQL("Ejecutando... [ " + pStrBuffer.toString() + " ]");
    JBaseRegistro oBR = JBaseRegistro.VirtualCreate(this.getBase());
    try {
      // se pone en autocommit porque no puede estar en dentro de una
      // transaccion
      //      bAutoCommit = this.getAutoCommit();
      //      this.setAutoCommit(true);

      oBR.executeWithoutTransaction(pStrBuffer.toString());
      PssLogger.logInfo("Login creado OK. Usuario: " + zUserName);
    } finally {
      if (oBR != null) oBR.close();
      //      this.setAutoCommit(bAutoCommit);
    }
  }

  /**
   * Permite al usuario de windows zUserName a acceder al motor SQL Server por por autenticación
   */
  private void setDefaultDatabase(String zUserName, String zDatabase) throws Exception {
    //    boolean bAutoCommit = false;
    this.initString();
    pStrBuffer.append("exec sp_defaultdb '");
    pStrBuffer.append(zUserName);
    pStrBuffer.append("','");
    pStrBuffer.append(zDatabase);
    pStrBuffer.append("'");
    PssLogger.logDebugSQL("Ejecutando... [ " + pStrBuffer.toString() + " ]");
    JBaseRegistro oBR = JBaseRegistro.VirtualCreate(this.getBase());
    try {
      // se pone en autocommit porque no puede estar en dentro de una
      // transaccion
      //      bAutoCommit = this.getAutoCommit();
      //      this.setAutoCommit(true);

      oBR.executeWithoutTransaction(pStrBuffer.toString());
      PssLogger.logInfo("Base default configurada OK. Usuario: " + zUserName + ", Base:" + zDatabase);
    } finally {
      if (oBR != null) oBR.close();
      //      this.setAutoCommit(bAutoCommit);
    }
  }

  /**
   * Sets certain database behaviors to be compatible with the specified earlier version of SQL Server
   */
  private void setCompatibility(String zDatabase) throws Exception {
    //    boolean bAutoCommit = false;
    this.initString();
    pStrBuffer.append("exec sp_dbcmptlevel '");
    pStrBuffer.append(zDatabase);
    pStrBuffer.append("'");
    PssLogger.logDebugSQL("Ejecutando... [ " + pStrBuffer.toString() + " ]");
    JBaseRegistro oBR = JBaseRegistro.VirtualCreate(this.getBase());
    try {
      // se pone en autocommit porque no puede estar en dentro de una
      // transaccion
      //      bAutoCommit = this.getAutoCommit();
      //      this.setAutoCommit(true);

      oBR.executeWithoutTransaction(pStrBuffer.toString());
      PssLogger.logInfo("Compatibilidad configurada OK. Base:" + zDatabase);
    } finally {
      if (oBR != null) oBR.close();
      //      this.setAutoCommit(bAutoCommit);
    }
  }

  private boolean getAutoCommit() throws Exception {
    return this.getBase().GetConnection().getAutoCommit();
  }

  private void setAutoCommit(boolean zValue) throws Exception {
    this.getBase().GetConnection().setAutoCommit(zValue);
  }

  private String getPhysicalName(String zLogicalName) throws Exception {
    String phyname = "";
    String sStr = "select phyname from master.dbo.sysdevices where name='" + zLogicalName + "'";
    JBaseRegistro oSetUpd = JBaseRegistro.VirtualCreate(this.getBase());
    try {
      oSetUpd.ExecuteQuery(sStr);
      if (!oSetUpd.next()) JExcepcion.SendError("No se encontró el nombre del archivo físico backup " + "correspondiente al nombre lógico^ " + zLogicalName);
      phyname = oSetUpd.CampoAsStr("phyname");
    } finally {
      if (oSetUpd != null) oSetUpd.close();
    }
    return phyname;
  }

  public void purgeDatabase( int expirationHours ) throws Exception {
    Vector<String> oVector = new Vector<String>();
    PssLogger.logDebug( "ELIMINANDO PROCESOS DE SQL SERVER CON MAS DE " + expirationHours
                          + " HORAS SIN EJECUTAR TRANSACCIONES" );
    JBaseRegistro oQuery = JBaseRegistro.VirtualCreate();
    String sSelect = "select * from master.dbo.sysprocesses";
    sSelect += " where login_time != last_batch ";
    sSelect += " and datediff( hour, last_batch, getdate() ) > " + expirationHours;
    oQuery.ExecuteQuery( sSelect );
    int iCant = 0;
    while( oQuery.next() ) {
      iCant++;
      String sPid = oQuery.CampoAsStr( "spid" );
      PssLogger.logDebug( "=====>>>>>>>>>> DEPURANDO PROCESO " + sPid );
      PssLogger.logDebug( "LOGUEO " + oQuery.CampoAsStr( "login_time" ) );
      PssLogger.logDebug( "ULTIMA TRANSACCION " + oQuery.CampoAsStr( "last_batch" ) );
      PssLogger.logDebug( "TERMINAL " + oQuery.CampoAsStr( "hostname" ) );
      PssLogger.logDebug( "APLICACION " + oQuery.CampoAsStr( "program_name" ) );
      oVector.add( sPid );
    }
    Iterator<String> oIt = oVector.iterator();
    while( oIt.hasNext() ) {
      String sPid = oIt.next();
      JBaseRegistro oKill = JBaseRegistro.VirtualCreate();
      oKill.executeWithoutTransaction( "kill " + sPid );
    }
    PssLogger.logDebug( "PROCESOS DEPURADOS: " + iCant );
  }

  private void dropUser( String zLoginName, JBDato zBaseDefault ) throws Exception {
    //    boolean bAutoCommit = false;
    this.initString();
    pStrBuffer.append( "exec sp_dropuser '" );
    pStrBuffer.append( zLoginName );
    pStrBuffer.append( "'" );
    PssLogger.logDebugSQL( "Ejecutando... [ " + pStrBuffer.toString() + " ]" );
    JBaseRegistro oBR = JBaseRegistro.VirtualCreate( zBaseDefault );
    try {
      oBR.executeWithoutTransaction(pStrBuffer.toString());
    } finally {
      oBR.close();
    }
  }


  /**
   * Borra un login en el motor SQL Server
   */
  @SuppressWarnings("unused")
	private void dropLogin(String zUserName, String zPasswd, String zDatabase) throws Exception {
    this.initString();
    pStrBuffer.append("exec sp_droplogin @loginame='");
    pStrBuffer.append(zUserName);
    pStrBuffer.append("', @passwd='");
    pStrBuffer.append(zPasswd);
    pStrBuffer.append("', @defdb='");
    pStrBuffer.append(zDatabase);
    pStrBuffer.append("'");
    PssLogger.logDebugSQL("Ejecutando... [ " + pStrBuffer.toString() + " ]");
    JBaseRegistro oBR = JBaseRegistro.VirtualCreate(this.getBase());
    try {
      oBR.executeWithoutTransaction(pStrBuffer.toString());
      PssLogger.logInfo("OK. Borrado Login. Usuario: " + zUserName + " , Base de Datos: " + zDatabase);
    } catch (SQLException eSQL) {
    	PssLogger.logError("ERROR. Borrado Login. Usuario: " + zUserName + " , Base de Datos: " + zDatabase + " [ " + eSQL.getMessage() + " ]");
    } finally {
      if (oBR != null)
        oBR.close();
    }
  }

//  FUTU - NO BORRAR ESTO, FUNCIONES DE OPTIMIZACION, A VERIFICAR SI SIRVEN
//  public void optimizeDatabase( String zEsquema ) throws Exception {
//    JList oList = BizCompInstalados.getAllTables("Pss");
//    Iterator oIt = oList.iterator();
//    while( oIt.hasNext() ) {
//      Thread.sleep(30000);
//      String sTable = (String)oIt.next();
//      JBaseRegistro oBR = JBaseRegistro.VirtualCreate();
//      oBR.executeWithoutTransaction( "UPDATE STATISTICS " + sTable + " WITH SAMPLE 25 PERCENT");
//    }
//  }
//
//  public void dropStatistics() throws Exception {
//    String s = "SELECT 'DROP STATISTICS [' + su.name + '].[' + so.name + '].[' + si.name + ']' AS Expr1 FROM sysindexes si INNER JOIN sysobjects so ON si.id = so.id INNER JOIN sysusers su ON su.uid = so.uid WHERE (INDEXPROPERTY(si.id, si.name, 'IsStatistics') = 1) AND (si.name NOT LIKE '_WA_Sys%') AND (OBJECTPROPERTY(so.id, 'IsUserTable') = 1) ORDER BY so.name, si.name";
//    JBaseRegistro oSetUpd = JBaseRegistro.VirtualCreate();
//    oSetUpd.ExecuteQuery(s);
//    while( oSetUpd.Next() ) {
//      String phyname = oSetUpd.CampoAsStr("Expr1");
//      JBaseRegistro oBR = JBaseRegistro.VirtualCreate();
//      oBR.executeWithoutTransaction( phyname );
//    }
//  }
//
//  public void createStatistics() throws Exception {
//    JBaseRegistro oBR = JBaseRegistro.VirtualCreate();
//    oBR.executeWithoutTransaction( "SP_CREATESTATS" );
//  }
//
//  public void updateStatistics() throws Exception {
//    JBaseRegistro oBR = JBaseRegistro.VirtualCreate();
//    oBR.executeWithoutTransaction( "SP_UPDATESTATS" );
//  }

}
