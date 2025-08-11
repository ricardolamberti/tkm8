package  pss.common.logviewer;

import java.util.Date;

import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.PssLogger;

public class BizLogRecord extends JRecord {


  private JDateTime pTimestamp = new JDateTime(true);
  private JString pType = new JString();
  private JString pThread = new JString();
  private JString pMessage = new JString();

  private JBoolean pShowError = new JBoolean();
  private JBoolean pShowInfo = new JBoolean();
  private JBoolean pShowDebug = new JBoolean();
  private JBoolean pShowSQL = new JBoolean();

  public BizLogRecord() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    addItem( "timestamp", pTimestamp);
    addItem( "type", pType);
    addItem( "thread", pThread);
    addItem( "message", pMessage);
    addItem( "show_error", pShowError);
    addItem( "show_info", pShowInfo);
    addItem( "show_debug", pShowDebug);
    addItem( "show_sql", pShowSQL);
  }

  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem( FIELD, "timestamp", "Fecha", true, false, 50 );
    addFixedItem( FIELD, "type", "Tipo", true, false, 50 );
    addFixedItem( FIELD, "thread", "Thread", true, false, 500 );
    addFixedItem( FIELD, "message", "Mensaje", true, false, 500 );

    addFixedItem( FIELD, "show_error", "Mostrar Errores", true, false, 50 );
    addFixedItem( FIELD, "show_info", "Mostrar Infos", true, false, 50 );
    addFixedItem( FIELD, "show_debug", "Mostrar Debug", true, false, 50 );
    addFixedItem( FIELD, "show_sql", "Mostrar SQL", true, false, 50 );

  }

  @Override
	public String GetTable() { return ""; }


  public void setTimestamp(Date zTimestamp) throws Exception {
    this.pTimestamp.setValue(zTimestamp);
  }
  public void setType(String zType) throws Exception {
    this.pType.setValue(zType);
  }
  public void setThread(String zThread) throws Exception {
    this.pThread.setValue(zThread);
  }
  public void setMessage(String zMessage) throws Exception {
    this.pMessage.setValue(zMessage);
  }
  public void appendLineToMessage(String zMessage) throws Exception {
    this.pMessage.setValue(this.pMessage.getValue() + "\n" + zMessage);
  }

  public int getLogLevel() throws Exception {
    return PssLogger.getLevelForPrefix(this.pType.getValue());
  }




}// end class
