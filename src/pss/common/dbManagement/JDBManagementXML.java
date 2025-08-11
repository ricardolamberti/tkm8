package pss.common.dbManagement;

import pss.core.data.interfaces.connections.JBDato;
import pss.core.data.interfaces.connections.JBDatos;


public class JDBManagementXML extends JBaseDBManagement {

  /**
   * Constructor
   */
  public JDBManagementXML() throws Exception {
  	this.setBase(JBDatos.getBaseMaster());
  }

  /**
   * Constructor
   */
  public JDBManagementXML(JBDato zBase) throws Exception {
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

  }

  /**
   * Adds an authenticated user to database
   */
  protected void addUserAuthenticatedToDatabase(String zUserName, String zDatabase) throws Exception {
  }

  @SuppressWarnings("unused")
	private void onBegin() throws Exception {
  }

  protected void restoreDatabaseDefault(String zLogicalDBName) throws Exception {
  }

  protected void restoreDatabaseDefaultFromFile(String zFileName) throws Exception {
  }

  /**
   * *
   */
  protected void restoreDatabase(String zNewDatabase, String zLogicalDBName) throws Exception {
  }

  protected void restoreDatabaseMoveTo(String zNewDatabase, String zFileName, String zPathMoveTo) throws Exception {
}

  /**
   * Restore from file
   */

  protected void restoreDatabaseFromFile(String zNewDatabase, String zFileName) throws Exception {
  }


  /**
   * Realiza un backup de la base de datos especificada
   * @return El nombre lógico del backup
   */
  protected String backupDatabase(String zDBName) throws Exception {
    return "";
  }

  /**
   * Crea una nueva base de datos. El path del .MDF es el que le asigne la base, que es donde esta instalado. Tambien el nombre lógico del log es el
   * nombre de la base + "_log" y el path es el mismo de la base de datos
   */
  protected void createDefaultDatabase(JBDato zDefaultDatabase) throws Exception {
  }


  /**
   * Chequea si existe la base de datos pasada por parametros existe.
   */
  protected boolean existDatabase(JBDato zDefaultDatabase) throws Exception {
    return true;
  }

  /**
   * Realiza un backup de la base de datos y nombre lógico especificados. El nombre del archivo backup es el mismo que el nomrbe lógico.
   */
  protected void backupDatabase(String zDBName, String zLogicalName) throws Exception {
  }

  /**
   * Realiza un backup de una base de datos
   * @param zDBName = nombre de la base de datos
   * @param zLogicalName = nombre logico del backup
   * @param zFileBName = nombre del archivo backup
   */
  protected void backupDatabase(String zDBName, String zLogicalName, String zFileName) throws Exception {
  }

  protected void truncateLog(String zDatabaseName) throws Exception {
  }

  /**
   * Obtiene la configuración del backup
   */
  public void readBackupConfiguration() throws Exception {
  }
  
}

