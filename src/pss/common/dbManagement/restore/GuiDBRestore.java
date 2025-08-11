package  pss.common.dbManagement.restore;

import java.io.File;

import pss.JPath;
import pss.core.data.interfaces.connections.JBDato;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIcon;

public class GuiDBRestore extends JWin {

	//-------------------------------------------------------------------------//
	// Constructor de la Clase
	//-------------------------------------------------------------------------//
	public GuiDBRestore() throws Exception {
	}
	
  @Override
	public JRecord     ObtenerDato() throws Exception  { return new BizDBRestore(); }
  @Override
	public int     GetNroIcono() throws Exception  { return GuiIcon.DATABASE_ICON; }
  @Override
	public String  GetTitle()    throws Exception  { return "Restore de una Base de Datos"; }
  @Override
	public Class<? extends JBaseForm>   getFormBase() throws Exception  { return FormDBRestore.class; }
  

	public BizDBRestore GetcDato() throws Exception {
		return (BizDBRestore) this.getRecord();
	}

	public static JWins ObtenerDispositivosBackup() throws Exception {
		// JBDato oBaseMaster = JBDatos.ReadBaseFromConfig( "CONFIG" , JBaseJDBC.ADMIN_USER);
		JBDato oBaseMaster = JBDatos.getBaseMaster();
		oBaseMaster.open();

		JRecords<BizVirtual> oBKs = JRecords.createVirtualBDs();
		JBaseRegistro oSet = JBaseRegistro.VirtualCreate(oBaseMaster);
		oSet.ExecuteQuery("select name from sysdevices where cntrltype = 2");
		while (oSet.next()) {
			oBKs.addItem(JRecord.virtualBD(oSet.CampoAsStr("name"), oSet.CampoAsStr("name"), GuiIcon.REFRESH_ICON));
		}

		oBaseMaster.close();
		return JWins.CreateVirtualWins(oBKs);
	}

  public static JWins ObtenerArchivosBackup() throws Exception {
    JRecords<BizVirtual> oBKs = JRecords.createVirtualBDs();
    File oFile = new File(JPath.PssPathBackup() + "/");
    File aFile[] = oFile.listFiles();
    if (aFile == null)
      JExcepcion.SendError("No existe el directorio de Backup^: " + JPath.PssPathBackup());
    for (int i = 0; i < aFile.length; i++) {
      File file = aFile[i];
      if (file.isDirectory()) continue;
      if (!isAllowedFileType(file)) continue;
      oBKs.addItem(JRecord.virtualBD(file.getName(), file.getName(), GuiIcon.REFRESH_ICON));
    }
    return JWins.CreateVirtualWins(oBKs);
  }

  /**
   * @param file
   * @return
   */
  private static boolean isAllowedFileType(File file) {
    if (file.getName().endsWith(".bak")) return true;
    if (file.getName().endsWith(".zip")) return true;
    return false;
  }

//	@Override
//	public JBaseForm showNewForm(boolean zShow) throws Exception {
//		this.GetcDato().pDatabaseDefault.setValue(
//			JBDatos.createDatabaseDefaultFromConfig().getDatabaseName());
//		JBaseForm oForm = super.showNewForm(zShow);
//		oForm.SetExitOnOk(true);
//		return oForm;
//	}

}
