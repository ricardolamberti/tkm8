package  pss.common.logviewer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pss.core.data.files.JStreamFile;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.PssLogger;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiLog extends JWin {

  private static final SimpleDateFormat LOG_MESSAGE_TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");

  public GuiLog() throws Exception {
  }

  @Override
	public void createActionMap() throws Exception {
//    addActionQuery( 1, "Consultar" );
    addAction( 10,  "Registros", null, 1085 , true, true, false, "Detail" );
  }
  
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActWins(getRecords(a));
  	return super.getSubmitFor(a);
  }

  public BizLog GetcDato() throws Exception {
    return (BizLog) getRecord();
  }


  @Override
	public JRecord ObtenerDato() throws Exception {
    return new BizLog();
  }
  @Override
	public int GetNroIcono() throws Exception {
    return 1012;
  }
  @Override
	public String GetTitle() throws Exception {
    if (this.GetcDato()==null) {
      return "Log de Pss";
    } else {
      return "Log de Pss: " + this.GetcDato().getFileName();
    }
  }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception {
    return FormLog.class;
  }
  @Override
	public String  getKeyField() throws Exception {
    return "file_name";
  }
  @Override
	public String  getDescripField() {
    return "file_name";
  }

  private JWins getRecords(BizAction a) throws Exception {
    GuiLogRecords g =  new GuiLogRecords();
    g.setLog(this);
    g.setRecords(this.getLines(a));
    return g;
  }
  
  private JRecords<BizLogRecord> getLines(BizAction a) throws Exception {
    JStreamFile file = new JStreamFile();
    try {
    	file.Open(this.GetcDato().getFullFileName());
      JRecords<BizLogRecord> lines = this.findLinesInFile(file, a);
      file.Close();
      return lines;
    } catch (Exception ex) {
      file.Close();
      throw ex;
    }
  }
  
  private JRecords<BizLogRecord> findLinesInFile(JStreamFile file, BizAction a) throws Exception {
//    String sFromDate = this.getFilterValue("from_date");
//    String sToDate = this.getFilterValue("to_date");
//    String sFromTime = this.getFilterValue("from_time");
//    String sToTime = this.getFilterValue("to_time");
    //SimpleDateFormat oFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
  	JRecords<BizLogRecord> lines = new JRecords<BizLogRecord>(BizLogRecord.class);
  	lines.setStatic(true);
    boolean bShowError = a.getFilterMapValue("show_error", "S").equals("S");
    boolean bShowInfo = a.getFilterMapValue("show_info", "S").equals("S");
    boolean bShowDebug = a.getFilterMapValue("show_debug", "S").equals("S");
    boolean bShowSQL = a.getFilterMapValue("show_sql", "S").equals("S");
    String desde = a.getFilterMapValue("desde", null);
    if (desde!=null) desde = desde.replace(",", " ").trim();
    String hasta = a.getFilterMapValue("hasta", null);
    if (hasta!=null) hasta = hasta.replace(",", " ").trim();
//    boolean bShowXML = this.getBoolean(this.getFilterValue("show_xml"));
//    boolean bShowOther = this.getBoolean(this.getFilterValue("show_other"));
//    if (sFromTime==null || sFromTime.length() < 8) {
//      sFromTime = "00:00:00";
//    }
//    if (sToTime==null || sToTime.length() < 8) {
//      sToTime = "23:59:59";
//    }
//    Date oFromDate = null;
//    Date oToDate = null;
//    if (sFromDate != null && sFromDate.length() > 0) {
//      oFromDate = oFormat.parse(sFromDate + " " + sFromTime);
//    }
//    if (sToDate != null && sToDate.length() > 0) {
//      oToDate = oFormat.parse(sToDate + " " + sToTime);
//    }
    


    // Esto es para leer de atrás para adelante
    // aunque por ahora lo dejo comentado. MRO 18 de Dic 2008.
    // fecha en que Zanoni no quiere firmar.
    
   /* ArrayList<String> TempArrayOfLines=new ArrayList<String>();
    while(true) {
    	String tcurrLine = file.ReadLine();
    	if (tcurrLine==null) break;
    	TempArrayOfLines.add(tcurrLine);
    }
    int count = TempArrayOfLines.size();
    for (int i=count-1;i >= 0; i--) {
      String currLine = TempArrayOfLines.get(i) */
    
    
    while (true) {
    	String currLine = file.ReadLine();
      if (currLine==null) break;
      Date timestamp = this.parseRecordTimestamp(currLine);
      String sThread = this.parseRecordThread(currLine);
      String sType = this.parseRecordType(currLine);
      int iType = PssLogger.getLevelForPrefix(sType);
      if (timestamp != null) {
        if (desde!=null && !desde.equals("") && timestamp.before(JDateTools.StringToDateTime(desde)) ||
        		hasta!=null && !hasta.equals("") && timestamp.after(JDateTools.StringToDateTime(hasta)))
          continue;
       }
      switch (iType) {
        case PssLogger.LOG_ERROR:	if (!bShowError) continue;
        case PssLogger.LOG_INFO: if (!bShowInfo) continue;
//          case JDebugPrint.LOG_WAIT: if (!bShowWait) { oLastRecord = null; continue; } else { break; }
        case PssLogger.LOG_DEBUG: if (!bShowDebug) continue;
        case PssLogger.LOG_DEBUG_SQL: if (!bShowSQL) continue;
//          case JDebugPrint.LOG_DEBUG_XML: if (!bShowXML) { oLastRecord = null; continue; } else { break; }
//          default: if (!bShowOther) { oLastRecord = null; continue; } else { break; }

      }
      lines.addItem(this.createRecordLog(currLine, sType, timestamp, sThread));
    }
    return lines;
  }

  private Date parseRecordTimestamp(String zFileLine) throws Exception {
    if (zFileLine.trim().length() < 1) return null;

    int iDatePatternLength = LOG_MESSAGE_TIMESTAMP_FORMAT.toPattern().length();
    int iLineLength = zFileLine.length();
    if (iLineLength > iDatePatternLength) {
      try {
        return LOG_MESSAGE_TIMESTAMP_FORMAT.parse(zFileLine.substring(0, iDatePatternLength));
      } catch (ParseException ex) {}
    }

    return null;
  }

  private String parseRecordThread(String zFileLine) throws Exception {
    int iDatePatternLength = LOG_MESSAGE_TIMESTAMP_FORMAT.toPattern().length();
    int iTypeBeginIndex = zFileLine.indexOf('[', iDatePatternLength);
    int iTypeEndIndex = zFileLine.indexOf(']', iTypeBeginIndex);
    if (iTypeBeginIndex > iDatePatternLength && iTypeEndIndex > iTypeBeginIndex) {
      return zFileLine.substring(iTypeBeginIndex + 1, iTypeEndIndex);
    } else {
      return null;
    }
  }

  private String parseRecordType(String zFileLine) throws Exception {
    int iTypeBeginIndex = zFileLine.indexOf(']');
    if (iTypeBeginIndex > 0) {
      return zFileLine.substring(iTypeBeginIndex + 2, iTypeBeginIndex + 8).trim();
    } else {
      return null;
    }
  }

  private BizLogRecord createRecordLog(String line, String type, Date zTimestamp, String thread) throws Exception {
    BizLogRecord oRecord = new BizLogRecord();
    // set the timestamp
    oRecord.setTimestamp(zTimestamp);
    oRecord.setThread(thread);
    oRecord.setType(type);
    // set the message
    int iTypeBeginIndex = line.indexOf(']')+8;
    if (line.length()>iTypeBeginIndex)
    	oRecord.setMessage(line.substring(iTypeBeginIndex));
    else
    	oRecord.setMessage(line);
    //
    return oRecord;
  }



}
