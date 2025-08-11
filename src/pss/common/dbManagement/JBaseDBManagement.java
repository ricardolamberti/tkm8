package  pss.common.dbManagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStream;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import pss.JPath;
import pss.core.data.BizPssConfig;
import pss.core.data.files.JStreamFile;
import pss.core.data.interfaces.connections.JBDato;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.connections.JBaseJDBC;
import pss.core.tools.JEncryptation;
import pss.core.tools.JExcepcion;
import pss.core.tools.JNativeToolConstants;
import pss.core.tools.JNativeTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;

public abstract class JBaseDBManagement {

  protected static final String SETUP_DATABASE_IMPL = "SETUP_DATABASE_IMPL";
  protected static boolean bCopyToDisk = false;
  protected static String sDriveDestionation = "";
  private static JBaseDBManagement oImplementantion;

  protected JBDato oBDato = null; // apunta a la Base Config (Master)
  protected StringBuffer pStrBuffer = new StringBuffer();
  //  protected static JBDato sBDato = null;   // apunta a la Base Config (Master)

  protected static String sPhisicalDataDestination = "c:/PssData/Pss.mdf";
  protected static String sPhisicalLogDestination  = "c:/PssData/Pss_log.ldf";
  protected static String sLogicalDataDestination  = "Pss_DAT";
  protected static String sLogicalLogDestination   = "Pss_LOG";

  public static void setPhisicalDataDestination(String zName) { sPhisicalDataDestination = zName; }
  public static void setPhisicalLogDestination(String zName)  { sPhisicalLogDestination  = zName; }
  public static void setLogicalDataDestination(String zName) { sLogicalDataDestination = zName; }
  public static void setLogicalLogDestination(String zName)  { sLogicalLogDestination  = zName; }


  public void setBase(JBDato zValor) {
    oBDato = zValor;
  }
  public JBDato getBase() {
    return oBDato;
  }
  protected void onFinish() throws Exception {
    this.getBase().close();
  }
  protected void createDefaultDatabase(JBDato zDatabase) throws Exception {
  }

  //-------------------------------------------------------------------------//
  //                          Metodos Estaticos
  //-------------------------------------------------------------------------//

  public static void initialize() throws Exception {
    oImplementantion = VirtualCreate();
  }

  /**
   * Agrega un usuario a la base de datos default
   * Notas:
   *  - La autenticación es a través del motor de la base y no por Windows.
   *  - El usuario pertenecerá al role de sistema sysadmin.
   *  - El usuario tendrá acceso a la base default.
   */
  /*  public static void addUserMarconiToBDefault() throws Exception {
      JStringTokenizer oTok = JBaseJDBC.readUserPasswd(JBaseJDBC.COMMON_USER);
      String sUserName = oTok.nextToken();
      String sPassword = oTok.nextToken();
      JBaseDBManagement.addUser(sUserName, sPassword, JBDatos.ReadDatabaseNameDefaultFromConfig().getDatabaseName());
    }

    /**
     * Agrega un usuario a la base de datos default
     * Notas:
     *  - La autenticación es a través del motor de la base y no por Windows.
     *  - El usuario pertenecerá al role de sistema sysadmin.
     *  - El usuario tendrá acceso a la base default.
     */
  /*  public static void addUserToDBDefault(String zUserName, String zPasswd) throws Exception {
      JBaseDBManagement.addUser(zUserName, zPasswd, JBDatos.ReadDatabaseNameDefaultFromConfig().getDatabaseName());
    }

    /**
     * Agrega un usuario a la base de datos especificada
     * Notas:
     *  - La autenticación es a través del motor de la base y no por Windows.
     *  - El usuario pertenecerá al role de sistema sysadmin.
     *  - El usuario tendrá acceso a la base especificada.
     */
  /*  public static void addUser(String zUserName, String zPasswd, String zDatabase) throws Exception {
      JBaseDBManagement.VirtualCreate().addUserToDatabase(zUserName, zPasswd, zDatabase);
    }

    /**
     * A implementar... Por ahora no se va a usar.
     */
  public void addUserAuthenticated(String zUserName, String zDatabase) throws Exception {
  }

  /**
   * Crea una nueva base de datos.  El path del .MDF es el que le asigne
   * la base, que es donde esta instalado.
   */
  public static void createDatabase(JBDato zDatabase) throws Exception {
    //    JBaseDBManagement.VirtualCreate().createDatabase(JTools.getDefaultDatabaseWithoutConn(), null, null );
    oImplementantion.createDefaultDatabase(zDatabase);
  }

  /**
   * Crea una base de datos con el nombre lógico y el nombre físico ( es el
   * path completo ) pasados por parametro.
   */
  //  public static void create(String sDatabaseName, String sLogicalName, String sPhisicalName) throws Exception {
  //    oImplementantion.createDatabase(sDatabaseName, sLogicalName, sPhisicalName);
  //  }
  /**
   * Chequea si existe la base de datos default.  El nombre de la base de datos default
   * lo obtiene parseando el string de la conexion.  Tener cuidado si cambia el driver
   */
  public static boolean existDefault() throws Exception {
    return exist(JBDatos.createDatabaseDefaultFromConfig());
  }

  /**
   * Chequea si existe la base de datos pasada por parametros existe.
   */
  public static boolean exist(JBDato zDefaultDatabase) throws Exception {
    return oImplementantion.existDatabase(zDefaultDatabase);
  }
  /*
   * Realiza un backup de la base de datos configurada default en el Pss.ini
   * El nombre lógico es BD_AAMMDD_HHMMSS donde:
   *    BD=Base de datos default; AAMMDD=Anio Mes y Dia ; HHHMMSS=Hora Minuto Segundo
   * El nombre del archivo fisico es el nombre lógico con extension 'bak'.
   */
  public static String backupDefault() throws Exception {
    openDatabaseDefault();
    return oImplementantion.backupDatabaseDefault();
  }

  /**
   * Realiza un backup de la base de datos configurada default en el Pss.ini.
   * El nombre del archivo fisico es el nombre lógico con extension 'bak'.
   * @param zLogicalName nombre logico del backup
   */
  public static void backupDefault(String zLogicalName) throws Exception {
    openDatabaseDefault();
    oImplementantion.backupDatabaseDefault(zLogicalName);
  }

  /**
   * Realiza un backup de la base de datos
   * El nombre lógico es BD_AAMMDD_HHMMSS donde:
   *    BD=Base de datos ; AAMMDD=Anio Mes y Dia ; HHHMMSS=Hora Minuto Segundo
   * El nombre del archivo fisico es el nombre lógico con extension 'bak'.
   * @param zDBName nombre de la base de datos a realizar el backup
   * @return El nombre lógico del backup
   */
  public static String backup(String zDBName) throws Exception {
    openDatabaseDefault();
    return oImplementantion.backupDatabase(zDBName);
  }

  /**
   * Realiza un backup de la base de datos
   * El nombre del archivo fisico es zLogicalDBName con extension 'bak'.
   * @param zDBName      nombre de la base de datos a realizar el backup
   * @param zLogicalName nombre logico del backup
   */
  public static void backup(String zDBName, String zLogicalName) throws Exception {
    openDatabaseDefault();
    oImplementantion.backupDatabase(zDBName, zLogicalName, JPath.PssPathBackup() + "/" + zLogicalName + ".bak");
  }

  /**
   * Realiza un backup de la base de datos
   * @param zDBName      nombre de la base de datos
   * @param zLogicalName nombre logico del backup
   * @param zFileBName   nombre del archivo backup
   */
  public static void backup(String zDBName, String zLogicalName, String zFileName) throws Exception {
    openDatabaseDefault();
    oImplementantion.backupDatabase(zDBName, zLogicalName, zFileName);
  }

  /**
   * Realiza un restore de la base de datos del nombre lógico a la base default
   * configurada en el archivo Pss.ini
   * @param zNewDatabase nombre de la base de datos que será restaurada
   * @param zLogicalName nombre logico del backup
   */
  public static void restoreToDefault(String zLogicalName) throws Exception {
    oImplementantion.restoreDatabaseDefault(zLogicalName);
  }

  /**
   * Realiza un restore de la base de datos
   * @param zNewDatabase nombre de la base de datos que será restaurada
   * @param zLogicalName nombre logico del backup
   */
  public static void restore(String zNewDatabase, String zLogicalName) throws Exception {
    oImplementantion.restoreDatabase(zNewDatabase, zLogicalName);
  }

  /**
   * Realiza un restore de la base de datos del archivo a la base default
   * configurada en el archivo Pss.ini
   * @param zFileName  nombre del archivo backup desde el cual se toma el restore
   */
  public static void restoreToDefaultFromFile(String zFileName) throws Exception {
    oImplementantion.restoreDatabaseDefaultFromFile(zFileName);
  }

  /**
   * Realiza un restore de la base de datos
   * @param zNewDatabase = nombre de la base de datos que será restaurada
   * @param zFileName    = nombre del archivo backup desde el cual se toma el restore
   */
  public static void restoreFromFile(String zNewDatabase, String zFileName) throws Exception {
    oImplementantion.restoreDatabaseFromFile(zNewDatabase, zFileName);
  }

  /**
   * Realiza un restore de la base de datos y lo guarda en el path pasado como parametro
   * @param zNewDatabase = nombre de la base de datos que será restaurada
   * @param zFileName    = nombre del archivo backup desde el cual se toma el restore
   * @param zPathRestore = path donde se al almacena el mdf ya restaurado
   */
  public static void restoreFromFileMoveTo(String zNewDatabase, String zFileName, String zPathRastore) throws Exception {
    oImplementantion.restoreDatabaseMoveTo(zNewDatabase, zFileName, zPathRastore);
  }


  /**
   * Crea una instancia de la subclase JBaseDBManagement, que corresponde al
   * motor de base de datos configurado
   * @return JDBManagementSQLServer si el motor de base de datos es SQL Server
   * @return JDBManagementOracle    si el motor de base de datos es Oracle
   * @return null                   si no hay motor especificado
   */
  public static JBaseDBManagement VirtualCreate() throws Exception {
    BizPssConfig oConfig = BizPssConfig.getPssConfig();
    String sDatabaseBlock = oConfig.getCachedStrictValue("GENERAL", JBDato.DATABASE_DEFAULT);
    String sDatabaseManagementImpl = oConfig.getCachedStrictValue(sDatabaseBlock, SETUP_DATABASE_IMPL);
    return (JBaseDBManagement)Class.forName(sDatabaseManagementImpl).newInstance();

    /*    PssLogger.logDebug("Intenta leer configuracion de CONFIG");
        JBDato oBaseMaster = JBDatos.getBaseMaster();
        PssLogger.logDebug("Intenta abrir base Master");
        oBaseMaster.Abrir();
        PssLogger.logDebug("Base Master abierta OK.");
        /*
            if ( ! JBDatos.ifBasesAbiertas() ) {
              JBDato oDefault = JBDatos.ReadBaseFromConfig("", JBaseJDBC.ADMIN_USER);
              oDefault.Abrir();
              JBDatos.GetBases().AddUniqueBase(oDefault);
              JBDatos.GetBases().AddItem(oBaseMaster);
            }
        */
    /*    if (oBaseMaster == null) {
          //JExcepcion.SendError("No existe la Base MASTER");
          PssLogger.logInfo("No hay base MASTER para esta configuración.");
        }*/

    /*    if (oBaseMaster.ifSQLServer())
          return new JDBManagementSQLServer(oBaseMaster);
        if (oBaseMaster.ifOracle())
          return new JDBManagementOracle();*/
    //    return null;
  }

  /**
   * Copia un archivo backup a un drive externo indicado por la configuración,
   * y únicamente en caso que esté seleccionada la opción "copiar a disco externo"
   * @param  zBackupFileName Nombre del archivo backup a ser copiado (sin incluir path)
   * @throws JException      Si la configuracion de backup indica que se copie a
   *                         a un disco externo pero no está especificado ninguno
   * @throws JException      Si falla la copia.
   */

  /**
   * Indica si el disco esta insertado en el dispositivo
   */

  private boolean validReadDriveSize(int reply) {
    switch (reply) {
      case JNativeToolConstants.TRUE :
        return true;
      case JNativeToolConstants.ERROR_NOT_READY :
        return false;
      default :
      	PssLogger.logError("Error: (" + reply + ") al leer dispositivo externo al hacer backup");
        return false;
    }
  }

  /**
   * Copia un archivo backup a un drive especificado.
   * @param  zBackupFileName Nombre del archivo backup a ser copiado (sin incluir path)
   * @param  zDrive          Drive donde será copiado el archivo backup
   * @throws JException      Si el archivo es mas grande que la capacidad del disco
   * @throws JException      Si se han borrado todos los archivos backup del disco destino
   *                         y el archivo backup no cabe en el disco.
   * @throws JException      Si falla la copia.
   */
  private void copyBackupToDrive(String zBackupFileName, String zDrive) throws Exception {
    PssLogger.logDebug("COMIENZA Copia de Backup a disco '" + zDrive + "' ...");
    JList<String> oOrderedFilesBackup = null;
    File oFile = null;
    File oFiles[] = null;

    String sBackupFullPathName = JPath.PssPathBackup() + "\\" + zBackupFileName;
    File oFileBackup = new File(sBackupFullPathName);

    char[] freeSpace = new char[20];
    char[] diskSize = new char[20];
    int reply = JNativeTools.readDriveSize(zDrive, diskSize, freeSpace);
    if (!validReadDriveSize(reply)) {
      JExcepcion.SendError("Error al intentar grabar el backup a unidad externa");
    }
    long lDiskTotalSize = Long.parseLong(new String(freeSpace));
    long lDiskSizeAvailable = Long.parseLong(new String(diskSize));

    // tamaño backup > tamaño disco ?
    if (oFileBackup.length() > lDiskTotalSize)
      JExcepcion.SendError("El backup de la base de datos supera el tamaño del disco destino");

    PssLogger.logDebug("Archivo backup " + zBackupFileName + " : " + String.valueOf(oFileBackup.length()) + " bytes");

    // si no hay lugar en disco, ordeno los archivos que son backup para
    // poder borrar los mas viejos
    if (oFileBackup.length() > lDiskSizeAvailable) {
      oFile = new File(zDrive + ":\\");
      oFiles = oFile.listFiles();
      if (oFiles == null || oFiles.length == 0)
        JExcepcion.SendError("No se pudo realizar el backup en la unidad^ " + zDrive + ":");
      // Ordeno la lista de archivos backups
      oOrderedFilesBackup = JCollectionFactory.createList();
      String sNombreBase = zBackupFileName.substring(0, zBackupFileName.indexOf("_20") + 3);
      for (int i = 0; i < oFiles.length; i++) {
        if (!oFiles[i].getName().startsWith(sNombreBase))
          continue;
        oOrderedFilesBackup.addElement(oFiles[i].getName());
      }
      oOrderedFilesBackup.sort();
    }

    if (oOrderedFilesBackup != null) {
      // mientras no haya lugar en disco, borra los archivos viejos.
      JIterator<String> oIterator = oOrderedFilesBackup.getIterator();
      while (oFileBackup.length() > lDiskSizeAvailable && oIterator.hasMoreElements()) {
        PssLogger.logDebug("Espacio disponible en disco " + zDrive + ": " + lDiskTotalSize + " bytes");
        File oFileToRemove = new File(zDrive + ":\\" + oIterator.nextElement());
        PssLogger.logDebug("Borrando archivo backup " + oFileToRemove.getAbsolutePath() + " ...");
        oFileToRemove.delete();
        PssLogger.logDebug("Archivo backup borrado ! " + oFileToRemove.getAbsolutePath());
        reply = JNativeTools.readDriveSize(zDrive, diskSize, freeSpace);
        if (!validReadDriveSize(reply))
          return;
        lDiskSizeAvailable = Long.parseLong(new String(freeSpace));
      }

      if (!oIterator.hasMoreElements() && oFileBackup.length() > lDiskSizeAvailable)
        JExcepcion.SendError(
          "BACKUP NO REALIZADO. Se han borrado todos los backups viejos del disco " + "y aún no se puede grabar. Revise el disco.");
    }

    PssLogger.logDebug("Espacio disponible en disco " + zDrive + ": " + lDiskTotalSize + " bytes");
    PssLogger.logDebug("Copiando el archivo backup... ");
    try {
      JTools.copyFile(sBackupFullPathName, zDrive + zBackupFileName);
      PssLogger.logDebug("Backup copiado con éxito ! ");
    } catch (Exception e) {
      JExcepcion.SendError("ERROR al copiar el archivo al disco^ '" + zDrive + "'");
    } finally {
      PssLogger.logDebug("FIN Copia de Backup a disco '" + zDrive + "'");
    }

  }

  /*
    public static String getDatabaseNameDefault() throws Exception {
  //    String sDBDef = ((JBaseJDBC)JBDatos.GetBases().GetBaseByName(JBDatos.GetBases().GetDatabaseDefault())).GetConnectionName();
  //    return sDBDef.substring( sDBDef.lastIndexOf("/") +1);
  //    BizPssConfig oParam = new BizPssConfig();
  //    String sBaseDefault = oParam.GetDatabaseNameDefault();
      return JBDatos.GetBases().ReadBaseFromConfig("").getDatabaseName();
   }
  */

  public static String getPassword() throws Exception {
    String sData = "";
    try {
      String key = new String("4C412D434C415645"); //"la-clave"
      key.toCharArray();
      JEncryptation password = new JEncryptation();
      password.HDesSetKey(key);
      String sFile = JBaseDBManagement.class.getResource("").getPath() + "backup.dat";
      JStreamFile oFile = new JStreamFile();
      oFile.Open(sFile);
      String sLine = oFile.ReadLine();
      sData = password.DesEncryptString(JTools.HexStringToBin(sLine, sLine.length())); //DesEncriptacion
    } catch (Exception e) {
      JExcepcion.SendError("Error al desencriptar la clave");
    }
    return sData;
  }

  protected static void generatePassword() throws Exception {
    String key = new String("98765779ABBCDFF3"); // "la-clave"
    //    String key = new String("4C412D434C415645");  // "la-clave"
    key.toCharArray();
    JEncryptation password = new JEncryptation();
    password.HDesSetKey(key);
    String encr = password.EncryptString("sa=gilbadmin");
    //    String encr = password.EncryptString("gilbbackup");
    JStreamFile oFile = new JStreamFile();
    oFile.CreateNewFile(JBaseDBManagement.class.getResource("").getPath() + "authadm.dat");
    //    oFile.CreateNewFile(JBaseDBManagement.class.getResource("").getPath()+"backup.dat");
    oFile.Write(JTools.BinToHexString(encr, encr.length()));
    oFile.Close();
  }
  /**
   * Clears the string buffer
   */
  protected void initString() {
    pStrBuffer.delete(0, pStrBuffer.length());
  }
  /**
   * Comprime un archivo
   * @param zFileName Nombre del archivo a comprimir
   */
  public static void zipFile(String zFileName) throws Exception {
    byte[] buf = new byte[1024]; // Create a buffer for reading the files

    // Create the ZIP file
    ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zFileName + ".zip"));
    FileInputStream in = new FileInputStream(zFileName);
    out.putNextEntry(new ZipEntry(zFileName)); // Add ZIP entry to output stream.

    // Transfer bytes from the file to the ZIP file
    int len;
    while ((len = in.read(buf)) > 0) {
      out.write(buf, 0, len);
    }
    out.closeEntry(); // Complete the entry
    in.close();
    out.close(); // Complete the ZIP file
  }

  /**
   * Descomprime un archivo
   * @param zZipFileName Nombre del archivo comprimido
   */
  public static String unzipFile(String zZipFileName) throws Exception {
    String sOut = zZipFileName.substring(0, zZipFileName.length() - 4);
    unzipFile(zZipFileName, sOut);
    return sOut;
  }

  /**
   * Descomprime un archivo
   * @param zZipFileName Nombre del archivo comprimido
   * @param zOutputFileName Nombre del archivo que quedará descomprimido
   */
  public static void unzipFile(String zZipFileName, String zOutputFileName) throws Exception {
    // Open the ZIP file
    ZipInputStream in = new ZipInputStream(new FileInputStream(zZipFileName));

    // Get the first entry
    @SuppressWarnings("unused")
		ZipEntry entry = in.getNextEntry();

    // Open the output file
    OutputStream out = new FileOutputStream(zOutputFileName);

    // Transfer bytes from the ZIP file to the output file
    byte[] buf = new byte[1024];
    int len;
    while ((len = in.read(buf)) > 0) {
      out.write(buf, 0, len);
    }

    // Close the streams
    out.close();
    in.close();
  }

  /**
   * Informa si el archivo tiene extension .zip
   * @return true  si tiene extension .zip
   * @return false si no tiene extension .zip
   */
  public static boolean isZipFile(String zFileName) throws Exception {
    int size = zFileName.length();
    if (size < 5)
      return false; // no es .zip
    String extension = zFileName.substring(size - 4);
    return (extension.equalsIgnoreCase(".zip"));
  }

  /**
   * Abre la base de datos default, si no esta abierta
   */
  public static void openDatabaseDefault() throws Exception {
//    if (JBDatos.isDatabaseOpen()) {
//    	PssLogger.logDebug("Las Bases estaban abiertas");
//      return;
//    }
    JBDato oDefault = JBDatos.createBaseFromConfig(null, JBaseJDBC.COMMON_USER);
    oDefault.open();
    JBDatos.GetBases().addDatabase(oDefault, true);
  }

  public static void optimizeDatabaseDefault(String zEsquema) throws Exception {
    initialize();
    oImplementantion.optimizeDatabase(zEsquema);
  }

  public static void purgeDatabaseDefault( int expirationHours ) throws Exception {
    initialize();
    oImplementantion.purgeDatabase( expirationHours );
  }

    // Metodos que se sobreescriben en las subclases de SQLServer, Oracle, etc.//
  public void optimizeDatabase(String zEsquema) throws Exception {
  }

  public void purgeDatabase( int expirationHours ) throws Exception {
  }

  protected String backupDatabaseDefault() throws Exception {
    return "";
  }
  protected void backupDatabaseDefault(String zLogicalName) throws Exception {
  }
  protected String backupDatabase(String zDBName) throws Exception {
    return "";
  }
  protected void backupDatabase(String zDBName, String zLogicalName) throws Exception {
  }
  protected void backupDatabase(String zDBName, String zLogicalName, String zFileName) throws Exception {
  }
  protected void restoreDatabase(String zNewDatabase, String zLogicalDBName) throws Exception {
  }
  protected void restoreDatabaseFromFile(String zNewDatabase, String zFileName) throws Exception {
  }
  protected void restoreDatabaseMoveTo(String zNewDatabase, String zFileName, String zPathMoveTo) throws Exception {
  }
  protected void restoreDatabaseDefault(String zLogicalDBName) throws Exception {
  }
  protected void restoreDatabaseDefaultFromFile(String zFileName) throws Exception {
  }
  protected void createDatabase(String sDatabaseName, String sLogicalName, String sPhisicalName) throws Exception {
  }
  protected void createDatabase(JBDato zDatabase, String sLogicalName, String sPhisicalName) throws Exception {
  }
  protected boolean existDatabase(JBDato zDefaultDatabase) throws Exception {
    return false;
  }
  protected void truncateLog(String zDatabaseName) throws Exception {
  }
  /*  protected void addUserToDatabase(String zUserName, String zPasswd, String zDatabase) throws Exception {}
    protected void addUserToDatabaseDefault(String zUserName, String zPasswd) throws Exception {}
    protected void addUserAuthenticatedToDatabase(String zUserName, String zDatabase) throws Exception {}*/

  protected void purgeFileBackup(final String sDBName, int iHistory) throws Exception {
    File oDirectory = new File(JPath.PssPathBackup());
    FilenameFilter filter = new FilenameFilter() {
      public boolean accept(File dir, String file) {
        return file.toUpperCase().startsWith(sDBName.toUpperCase());
      }
    };
    String[] oFiles = oDirectory.list(filter);
    TreeSet<String> oTree = new TreeSet<String>();

    for (int iCont = 0; iCont < oFiles.length; iCont++) {
      oTree.add(oFiles[iCont]);
    }

    Object[] oFilesSorted = oTree.toArray();

    for (int iCont = 0; iCont < oFilesSorted.length - iHistory; iCont++) {
      JNativeTools.deleteFile(JPath.PssPathBackup() + "/" + ((String)oFilesSorted[iCont]));
    }
  }
}
