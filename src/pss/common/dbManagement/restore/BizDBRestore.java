package  pss.common.dbManagement.restore;

import pss.JPath;
import pss.common.dbManagement.JBaseDBManagement;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;

public class BizDBRestore extends JRecord {

  public static String FILE_RESTORE_TYPE = "file";
  public static String DEVICE_RESTORE_TYPE = "device";

  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  //  public JString pDatabase = new JString();
  public JString pDatabaseDefault = new JString();
  public JString pLogicalName = new JString();
  public JString pFileName = new JString();
  public JString pTipoRestore = new JString();

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizDBRestore() throws Exception {}

  @Override
	public void createProperties() throws Exception {
    //    AddItem("database" , pDatabase );
    addItem("database_default", pDatabaseDefault);
    addItem("logical_name", pLogicalName);
    addItem("file_name", pFileName);
    addItem("tipo_restore", pTipoRestore);
  }

  @Override
	public void createFixedProperties() throws Exception {
    //    AddItemFijo( CAMPO, "database" , "database" , true, false, 100);
    addFixedItem( FIELD, "database_default", "database_default", true, false, 100);
    addFixedItem( FIELD, "logical_name", "logical_name", true, false, 100);
    addFixedItem( FIELD, "file_name", "file_name", true, false, 100);
    addFixedItem( FIELD, "tipo_restore", "tipo_restore", true, false, 100);
  }

  @Override
	public String GetTable() {
    return "";
  }

  /*****************************************************************************
   * Ejecuta el restore de la base de datos
   * @author CJG
   ****************************************************************************/
  @Override
	public void processInsert() throws Exception {
    JBaseDBManagement.initialize();
    //    if (pDatabase.GetValor().equals("")) JExcepcion.SendError("Debe ingresar
    // un nombre para la Base de Datos destino.");
    if (JBDatos.isDatabaseOpen()) /// &&
        // pDatabase.GetValor().equalsIgnoreCase(pDatabaseDefault.GetValor()
        JExcepcion.SendError("No se puede hacer un RESTORE a la base actualmente en uso.");

    if (this.isDeviceRestore()) {
      if (pLogicalName.getValue().equals("")) JExcepcion.SendError("Debe seleccionar un disposivito lógico.");
      JBaseDBManagement.restore(pDatabaseDefault.getValue(), pLogicalName.getValue());
    }
    else if (this.isFileRestore()) {
      if (pFileName.getValue().equals("")) JExcepcion.SendError("Debe seleccionar un archivo.");
      JBaseDBManagement.restoreFromFile(pDatabaseDefault.getValue(), JPath.PssPathBackup() + "/" + pFileName.getValue());
    }
//    UITools.MostrarMensaje("Restore", "El restore se ha realizado exitosamente.");
  }

  public boolean isDeviceRestore() throws Exception {
    return pTipoRestore.getValue().equalsIgnoreCase(BizDBRestore.DEVICE_RESTORE_TYPE);
  }

  public boolean isFileRestore() throws Exception {
    return pTipoRestore.getValue().equalsIgnoreCase(BizDBRestore.FILE_RESTORE_TYPE);
  }

}
