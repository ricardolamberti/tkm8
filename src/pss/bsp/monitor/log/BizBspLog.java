package pss.bsp.monitor.log;

import java.util.Calendar;
import java.util.Date;

import pss.bsp.consolid.model.liquidacion.detail.BizLiqDetail;
import pss.bsp.consolid.model.liquidacion.errors.BizLiqError;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.scheduler.SchedulerHealth;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.JExec;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class BizBspLog extends JRecord {

	public static final String BSPLOG_LOG = "LOG";
	public static final String BSPLOG_ERROR = "ERROR";
	public static final String BSPLOG_MODULO_CONTRATO = "CONTRATO";
	public static final String BSPLOG_MODULO_INDICADOR = "INDICADOR";
	public static final String BSPLOG_MODULO_MENSAJERIA = "MENSAJE";
	public static final String BSPLOG_MODULO_GENERAL = "GENERAL";
	public static final String BSPLOG_MODULO_TIMES = "TIMES";
	public static final String BSPLOG_MODULO_TIMESCOMPANY = "TIMES_COMP";
	public static final String BSPLOG_MODULO_TIMESFULL= "TIMES_FULL";

	private JString pCompany = new JString();
	private JString pThread = new JString();
	private JString pModulo = new JString();
	private JDateTime pDate = new JDateTime();
	private JString pType = new JString();
	private JString pLog = new JString();
	private JLong pId = new JLong();
	private JString pParam1 = new JString();
	private JString pParam2 = new JString();
	private JString pParam3 = new JString();
	private JLong pParam4 = new JLong();
	private JLong pParam5 = new JLong();
	private JLong pParam6 = new JLong();

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public boolean isNullCompany() throws Exception {
		return pCompany.isNull();
	}

	public void setNullToCompany() throws Exception {
		pCompany.setNull();
	}

	public void setDate(Date zValue) throws Exception {
		pDate.setValue(zValue);
	}

	public Date getDate() throws Exception {
		return pDate.getValue();
	}

	public boolean isNullDate() throws Exception {
		return pDate.isNull();
	}

	public void setNullToDate() throws Exception {
		pDate.setNull();
	}

	public void setModulo(String zValue) throws Exception {
		pModulo.setValue(zValue);
	}

	public String getModulo() throws Exception {
		return pModulo.getValue();
	}
	public void setThread(String zValue) throws Exception {
		pThread.setValue(zValue);
	}

	public String getThread() throws Exception {
		return pThread.getValue();
	}

	public boolean isNullThread() throws Exception {
		return pThread.isNull();
	}

	public void setNullToThread() throws Exception {
		pThread.setNull();
	}

	public void setType(String zValue) throws Exception {
		pType.setValue(zValue);
	}

	public String getType() throws Exception {
		return pType.getValue();
	}

	public boolean isNullType() throws Exception {
		return pType.isNull();
	}

	public void setNullToType() throws Exception {
		pType.setNull();
	}

	public void setLog(String zValue) throws Exception {
		pLog.setValue(zValue);
	}

	public String getLog() throws Exception {
		return pLog.getValue();
	}

	public boolean isNullLog() throws Exception {
		return pLog.isNull();
	}

	public void setNullToLog() throws Exception {
		pLog.setNull();
	}

	public void setParam1(String zValue) throws Exception {
		pParam1.setValue(zValue);
	}

	public String getParam1() throws Exception {
		return pParam1.getValue();
	}

	public boolean isNullParam1() throws Exception {
		return pParam1.isNull();
	}

	public void setNullToParam1() throws Exception {
		pParam1.setNull();
	}

	public void setParam2(String zValue) throws Exception {
		pParam2.setValue(zValue);
	}

	public String getParam2() throws Exception {
		return pParam2.getValue();
	}

	public boolean isNullParam2() throws Exception {
		return pParam2.isNull();
	}

	public void setNullToParam2() throws Exception {
		pParam2.setNull();
	}

	public void setParam3(String zValue) throws Exception {
		pParam3.setValue(zValue);
	}

	public String getParam3() throws Exception {
		return pParam3.getValue();
	}

	public boolean isNullParam3() throws Exception {
		return pParam3.isNull();
	}

	public void setNullToParam3() throws Exception {
		pParam3.setNull();
	}

	public void setParam4(long zValue) throws Exception {
		pParam4.setValue(zValue);
	}

	public long getParam4() throws Exception {
		return pParam4.getValue();
	}

	public boolean isNullParam4() throws Exception {
		return pParam4.isNull();
	}

	public void setNullToParam4() throws Exception {
		pParam4.setNull();
	}

	public void setParam5(long zValue) throws Exception {
		pParam5.setValue(zValue);
	}

	public long getParam5() throws Exception {
		return pParam5.getValue();
	}

	public boolean isNullParam5() throws Exception {
		return pParam5.isNull();
	}

	public void setNullToParam5() throws Exception {
		pParam5.setNull();
	}

	public void setParam6(long zValue) throws Exception {
		pParam6.setValue(zValue);
	}

	public long getParam6() throws Exception {
		return pParam6.getValue();
	}

	public boolean isNullParam6() throws Exception {
		return pParam6.isNull();
	}

	public void setNullToParam6() throws Exception {
		pParam6.setNull();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizBspLog() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("id", pId);
		this.addItem("company", pCompany);
		this.addItem("log_thread", pThread);
		this.addItem("log_module", pModulo);
		this.addItem("log_date", pDate);
		this.addItem("log_type", pType);
		this.addItem("log", pLog);
		this.addItem("value", pLog);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "id", "Id", false, false, 64);
		this.addFixedItem(FIELD, "company", "Company", true, false, 50);
		this.addFixedItem(FIELD, "log_thread", "Thread", true, false, 200);
		this.addFixedItem(FIELD, "log_module", "Module", true, false, 200);
		this.addFixedItem(FIELD, "log_date", "Date", true, false, 50);
		this.addFixedItem(FIELD, "log_type", "Log Type", true, false, 10);
		this.addFixedItem(FIELD, "log", "Log", true, false, 2000);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "BSP_LOG";
	}

	public static void log(String company, String thread,String module, String type, String log, String param1, String param2, String param3, long param4, long param5, long param6) {
		try {
			if (type.equals(BizBspLog.BSPLOG_ERROR )) {
				PssLogger.logDebug("log point");
			}
			if (param1 != null) {
				log = log.replace("%1", param1);
			}
			if (param2 != null) {
				log = log.replace("%2", param2);
			}
			if (param3 != null) {
				log = log.replace("%3", param3);
			}
			log = log.replace("%4", String.valueOf(param4));
			log = log.replace("%5", String.valueOf(param5));
			log = log.replace("%6", String.valueOf(param6));

			BizBspLog rlog = new BizBspLog();
			rlog.setCompany(company);
			rlog.setThread(thread);
			rlog.setType(type);
			rlog.setLog(log); 
			rlog.setDate(new Date());
			rlog.setParam1(param1);
			rlog.setParam2(param2);
			rlog.setParam3(param3);
			rlog.setParam4(param4);
			rlog.setParam5(param5);
			rlog.setParam6(param6);
			if (!type.equals(BizBspLog.BSPLOG_MODULO_TIMES )&&!type.equals(BizBspLog.BSPLOG_MODULO_TIMESCOMPANY)&&!type.equals(BizBspLog.BSPLOG_MODULO_TIMESFULL)) {
				SchedulerHealth.setProcessActivity( Thread.currentThread().getId(), log);
			}
			if (JBDatos.GetBases().getPrivateCurrentDatabase().isTransactionInProgress())
				rlog.processInsert();
			else
				rlog.execProcessInsert();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(long id) throws Exception {
		addFilter("id", id);
		return read();
	}
	
	public static void clear() throws Exception {
		new BizBspLog().execProcLimpiar();
	}
	
	public void execProcLimpiar() throws Exception {
		JExec oExec = new JExec(this, "procLimpiar") {

			@Override
			public void Do() throws Exception {
				procLimpiar();
			}
		};
		oExec.execute();
	}
	public void procLimpiar() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -3);
		BizBspLog cons = new BizBspLog();
		cons.addFilter("log_date", cal.getTime(),"<");
		cons.deleteMultiple();
	}
	
	protected static JMap<String,String> getTipos() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement(BizBspLog.BSPLOG_LOG, JLanguage.translate("Log"));
		map.addElement(BizBspLog.BSPLOG_ERROR, JLanguage.translate("Error"));
		return map;
	}
}
