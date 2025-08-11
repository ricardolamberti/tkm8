package pss.common.terminals.config;

import pss.common.layout.JFieldInterface;
import pss.common.layout.JFieldReq;
import pss.common.terminals.core.JTerminal;
import pss.common.terminals.messages.requires.common.JTerminalControl;
import pss.common.terminals.messages.requires.print.JPrintDoc;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;

public class BizTerminal extends JRecord implements JFieldInterface {

	private JString pCompany = new JString();
	private JString pNodo = new JString();
	private JLong pTerminalId = new JLong();
	private JString pTerminalType = new JString();
	private JString pConnectionString = new JString();
	private JLong pTerminalPool = new JLong();
	private JString pNroSerie = new JString();
	private JString pDescrTerminal = new JString() {
		@Override
		public void preset() throws Exception {
			pDescrTerminal.setValue(getDescrTerminal());
		}
	};
	private JString pDescrTerminalType = new JString() {
		@Override
		public void preset() throws Exception {
			pDescrTerminalType.setValue(getDescrTerminalType());
		}
	};

	// private JRecords drivers=null;
	private JTerminal terminal = null;
	private BizTerminalPool terminalPool = null;

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public void setNodo(String zValue) throws Exception {
		pNodo.setValue(zValue);
	}

	public String getNodo() throws Exception {
		return pNodo.getValue();
	}

	public void setTerminalPool(long zValue) throws Exception {
		pTerminalPool.setValue(zValue);
	}

	public long getTerminalPool() throws Exception {
		return pTerminalPool.getValue();
	}

	public void setTerminalId(long zValue) throws Exception {
		pTerminalId.setValue(zValue);
	}

	public long getTerminalId() throws Exception {
		return pTerminalId.getValue();
	}

	public void setTerminalType(String zValue) throws Exception {
		pTerminalType.setValue(zValue);
	}

	public String getTerminalType() throws Exception {
		return pTerminalType.getValue();
	}

	public void setConnectionString(String zValue) throws Exception {
		pConnectionString.setValue(zValue);
	}

	public String getConnectionString() throws Exception {
		return pConnectionString.getValue();
	}

	public String getNroSerie() throws Exception {
		return pNroSerie.getValue();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizTerminal() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("nodo", pNodo);
		this.addItem("terminal_id", pTerminalId);
		this.addItem("terminal_type", pTerminalType);
		this.addItem("connection_string", pConnectionString);
		this.addItem("terminal_pool", pTerminalPool);
		this.addItem("nro_serie", pNroSerie);
		this.addItem("descr_terminal", pDescrTerminal);
		this.addItem("descr_terminal_type", pDescrTerminalType);
	}

	/**
	 * Adds the fixed object properties
	 */
	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Company", true, true, 15);
		this.addFixedItem(KEY, "terminal_id", "Terminal id", true, true, 5);
		this.addFixedItem(FIELD, "nodo", "Nodo", true, true, 15);
		this.addFixedItem(FIELD, "terminal_type", "Terminal type", true, true, 20);
		this.addFixedItem(FIELD, "connection_string", "Connection string", true, true, 120);
		this.addFixedItem(FIELD, "terminal_pool", "Terminal pool", true, true, 8);
		this.addFixedItem(FIELD, "nro_serie", "Nro Serie", true, false, 30);
		this.addFixedItem(VIRTUAL, "descr_terminal", "Terminal", true, false, 100);
		this.addFixedItem(VIRTUAL, "descr_terminal_type", "Tipo Terminal", true, false, 100);
	}

	/**
	 * Returns the table name
	 */
	@Override
	public String GetTable() {
		return "TER_TERMINAL";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default Read() method
	 */
	public boolean Read(String zCompany,  long zTerminalId) throws Exception {
		addFilter("company", zCompany);
	//	addFilter("nodo", zNodo);
		// addFilter( "terminal_pool", zTerminalPool );
		addFilter("terminal_id", zTerminalId);
		return this.read();
	}
	public boolean Read(String zCompany, String zNodo, long zTerminalId) throws Exception {
		addFilter("company", zCompany);
	//	addFilter("nodo", zNodo);
		// addFilter( "terminal_pool", zTerminalPool );
		addFilter("terminal_id", zTerminalId);
		return this.read();
	}

	public JTerminal getTerminal() throws Exception {
		// BizType type = BizType.createTerminal(pTerminalType.getValue());
		JTerminal terminal = BizType.createTerminal(pTerminalType.getValue());
		return terminal;
	}

	public BizTerminalPool getObjTerminalPool() throws Exception {
		if (this.terminalPool != null) return this.terminalPool;
		BizTerminalPool record = new BizTerminalPool();
		record.Read(pCompany.getValue(), pNodo.getValue(), pTerminalPool.getValue());
		return (this.terminalPool = record);
	}

	public void setObjTerminalPointer(JTerminal value) {
		this.terminal = value;
	}

	public JTerminal getTerminalPointer() throws Exception {
		if (this.terminal != null) return this.terminal;
		JTerminal term = this.getTerminal();
		term.setTerminalId(this.getTerminalId());
		term.setType(this.getTerminalType());
		term.setMacAddress(this.getObjTerminalPool().getMacAddress());
		term.setTerminalPool(this.getTerminalPool());
		term.setCompany(this.getCompany());
		term.setConnectionString(this.getConnectionString());
		// if (term.getPrintAdapter().hasNroRollo())
		// term.getPrintAdapter().setNroRollo(this.getAdicPrinter().getNroRollo());
		JIterator<BizTerminalDriver> iter = this.getDrivers().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizTerminalDriver driver = iter.nextElement();
			term.enableDriver(driver.getDriverType());
		}
		return (this.terminal = term);
	}

	public boolean hasDriverConfig(int driverType) throws Exception {
		return this.getDrivers().findInHash(String.valueOf(driverType)) != null;
	}

	public JRecords<BizTerminalDriver> getDrivers() throws Exception {
		JRecords<BizTerminalDriver> records = new JRecords<BizTerminalDriver>(BizTerminalDriver.class);
		records.addFilter("company", pCompany.getValue());
		records.addFilter("nodo", pNodo.getValue());
		// records.addFilter("terminal_pool", pTerminalPool.getValue());
		records.addFilter("terminal_id", pTerminalId.getValue());
		records.readAll();
		records.toStatic();
		records.convertToHash("driver_type");
		return records;
	}

	public JRecords<BizGroupTerminal> getGroupTerminals() throws Exception {
		JRecords<BizGroupTerminal> records = new JRecords<BizGroupTerminal>(BizGroupTerminal.class);
		records.addFilter("company", pCompany.getValue());
		records.addFilter("nodo", pNodo.getValue());
		records.addFilter("terminal_id", pTerminalId.getValue());
		records.readAll();
		return records;
	}

	@Override
	public void processInsert() throws Exception {
		if (pTerminalId.isNull()) {
			BizTerminal max = new BizTerminal();
			max.addFilter("company", pCompany.getValue());
			max.addFilter("nodo", pNodo.getValue());
			// max.addFilter("terminal_pool", pTerminalPool.getValue());
			pTerminalId.setValue(max.SelectMaxLong("terminal_id") + 1);
		}
		JIterator<String> iter = this.getTerminal().getAllDrivers().getKeyIterator();
		while (iter.hasMoreElements()) {
			String driver = iter.nextElement();
			BizTerminalDriver termDriver = new BizTerminalDriver();
			termDriver.setCompany(pCompany.getValue());
			termDriver.setNodo(pNodo.getValue());
			// termDriver.setTerminalPool(pTerminalPool.getValue());
			termDriver.setTerminalId(pTerminalId.getValue());
			termDriver.setDriverType(Integer.parseInt(driver));
			termDriver.insert();
		}
		super.processInsert();
	}

	@Override
	public void processDelete() throws Exception {
		this.getDrivers().processDeleteAll();
//		this.getAdicPrinter().processDelete();
		this.getGroupTerminals().processDeleteAll();
		super.processDelete();
	}

	public String getDescrTerminal() throws Exception {
		return this.getDescrTerminalType() + " (" + pConnectionString.getValue() + ") ->" + this.getObjTerminalPool().getDescription();
	}

	public String getDescrTerminalType() throws Exception {
		return BizType.getTypes().getElement(pTerminalType.getValue());
	}

	public void runTest() throws Exception {
		JPrintDoc printDoc = new JPrintDoc();
		printDoc.setMode("");
		printDoc.setFont("");
		printDoc.setFontSize(12);
		printDoc.setLineSpacing(0);
		printDoc.setBackground("");
		printDoc.setPrinterDocType("");
		printDoc.setDocNumber(0);
		printDoc.setXML("<xml><section id=\"LINE\" line=\"HOLA\" /></xml>");

		JTerminalControl open = new JTerminalControl(JTerminalControl.OPEN);
		JTerminalControl close = new JTerminalControl(JTerminalControl.CLOSE);

		this.getTerminalPointer().process(open);
		this.getTerminalPointer().process(printDoc);
		this.getTerminalPointer().process(close);
	}

	public Object getField(JFieldReq req) throws Exception {
		return null;
	}

}
