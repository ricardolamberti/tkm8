package  pss.common.dbManagement.restore;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class FormDBRestore extends JBaseForm {

  public FormDBRestore() throws Exception {
  }


/*  private GuiDBRestore GetWin() {
    return (GuiDBRestore) this.GetBaseWin();
  }
*/
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "database_default", CHAR, OPT, "database_default"    );
    AddItemCombo( "logical_name", CHAR, OPT, "logical_name",GuiDBRestore.ObtenerDispositivosBackup());
    AddItemCombo( "file_name"   , CHAR, OPT, "file_name"   ,GuiDBRestore.ObtenerArchivosBackup());
    AddItemEdit( "Tipo", CHAR, OPT, "tipo_restore");
//    oControl4.AddValor(BizDBRestore.DEVICE_RESTORE_TYPE);
//    oControl4.AddValor(BizDBRestore.FILE_RESTORE_TYPE);
//    oControl4.SetValorDefault(BizDBRestore.DEVICE_RESTORE_TYPE);
    this.getControles().findControl("file_name").disable();
  }

}
