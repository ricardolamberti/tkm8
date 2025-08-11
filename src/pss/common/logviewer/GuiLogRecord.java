package  pss.common.logviewer;

import pss.core.services.records.JRecord;
import pss.core.tools.PssLogger;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIcon;

public class GuiLogRecord extends JWin {

  public GuiLogRecord() throws Exception {
  }

  @Override
	public void createActionMap() throws Exception {
    addActionQuery( 1, "Consultar" );
  }

  public BizLogRecord GetcDato() throws Exception {
    return (BizLogRecord) getRecord();
  }


  @Override
	public JRecord ObtenerDato() throws Exception {
    return new BizLogRecord();
  }
  @Override
	public int GetNroIcono() throws Exception {
    switch (this.GetcDato().getLogLevel()) {
      case PssLogger.LOG_ERROR: return GuiIcon.ERROR_MESSAGE_ICON;
      case PssLogger.LOG_INFO: return GuiIcon.INFO_MESSAGE_ICON;
      case PssLogger.LOG_WAIT: return GuiIcon.WAIT_MESSAGE_ICON;
      case PssLogger.LOG_DEBUG: return GuiIcon.DEBUG_MESSAGE_ICON;
      case PssLogger.LOG_DEBUG_SQL: return GuiIcon.SQL_DEBUG_MESSAGE_ICON;
      case PssLogger.LOG_DEBUG_XML: return GuiIcon.XML_DEBUG_MESSAGE_ICON;
      default: return 1085;
    }
  }
  @Override
	public String GetTitle() throws Exception {
    return "Registro de Log de Pss";
  }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception {
    return FormLogRecord.class;
  }
  @Override
	public String  getKeyField() throws Exception {
    return "";
  }
  @Override
	public String  getDescripField() {
    return "";
  }

}
