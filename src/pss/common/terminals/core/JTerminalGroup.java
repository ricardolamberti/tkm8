package pss.common.terminals.core;

import pss.common.terminals.interfaces.JDataEncriptInterface;
import pss.common.terminals.interfaces.JPrinterInterface;
import pss.common.terminals.messages.requires.cashDrawer.JCashDrawerIsOpen;
import pss.common.terminals.messages.requires.cashDrawer.JCashDrawerOpen;
import pss.common.terminals.messages.requires.display.JClientDisplay;
import pss.common.terminals.messages.requires.display.JOperDisplay;
import pss.common.terminals.messages.requires.print.JPrintClose;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;

public class JTerminalGroup {

	private String sCompany = null;
	private String sNodo = null;
	private long groupId = 0L;
	private JList<JTerminal> terminals = null;

	public JTerminalGroup() {
	}
	
	
	public void setCompany(String value) {
		this.sCompany = value;
	}
	public void setNodo(String value) {
		this.sNodo = value;
	}
	public String getCompany() {
		return this.sCompany;
	}
	public String getNodo() {
		return this.sNodo;
	}
	public long getGroupId() {
		return this.groupId;
	}
	public void setGroupId(long value) {
		this.groupId = value;
	}
	
	public void addTerminal(JTerminal terminal) throws Exception {
		if (this.terminals==null) this.terminals = JCollectionFactory.createList();
		this.terminals.addElement(terminal);
	}
	
	public boolean hasTerminals() {
		return this.terminals!=null;
	}

  public JIterator<JTerminal> getTerminalIterator() throws Exception {
  	return this.terminals.getIterator();
  }

	public void startPollingAll() throws Exception {
		JIterator<JTerminal> iter = this.getTerminalIterator();
		while (iter.hasMoreElements()) {
			JTerminal terminal = iter.nextElement();
			terminal.startPolling();
		}
	}
	public void stopPollingAll() throws Exception {
		JIterator<JTerminal> iter = this.getTerminalIterator();
		while (iter.hasMoreElements()) {
			JTerminal terminal = iter.nextElement();
			terminal.stopPolling();
		}
	}
	public JTerminal getOperatorDisplay() throws Exception {
		return this.getTerminalByDriver(JTerminal.D_OPERATOR_DISPLAY);
	}
	public JTerminal getClientDisplay() throws Exception {
		return this.getTerminalByDriver(JTerminal.D_CLIENT_DISPLAY);
	}
	public JDataEncriptInterface getDataEncrypt() throws Exception {
		return (JDataEncriptInterface)this.getTerminalByDriver(JTerminal.D_CRYPTO);
	}

	public JTerminal getCashDrawer() throws Exception {
		return this.getTerminalByDriver(JTerminal.D_CASH_DRAWER);
	}
	
	
//	public JEvent dataEncrypt(JDes des) throws Exception {
//		JIterator iter = this.getTerminalIterator();
//		while (iter.hasMoreElements()) {
//			JTerminal terminal = (JTerminal) iter.nextElement();
//			if (terminal.hasAvailableDriverFor(JTerminal.D_CRYPTO)) continue;
//			JDataEncriptInterface inter = (JDataEncriptInterface)iter.nextElement();
//			return inter.dataEncrypt(des);
//		}
//		return null;
//	}

	public JPrinterInterface getPrinter(int id) throws Exception {
		return (JPrinterInterface)this.getPrinters().getElement(String.valueOf(id));
	}
	public JMap<String, JTerminal> getPrinters() throws Exception {
		return this.getTerminalsByDriver(JTerminal.D_PRINTER);
	}	
	
	public boolean hasAvailableDriverFor(int driver) throws Exception {
		return this.getTerminalByDriver(driver)!=null;
	}
	public JTerminal getTerminalByDriver(int driver) throws Exception {
		if (!this.hasTerminals()) return null;
		JIterator<JTerminal> iter = this.getTerminalIterator();
		while (iter.hasMoreElements()) {
			JTerminal terminal = iter.nextElement();
			if (!terminal.hasAvailableDriverFor(driver)) continue;
			return terminal;
		}
		return null;
	}
	
	public JTerminal getTerminalById(long terminalId) throws Exception {
		JIterator<JTerminal> iter = this.getTerminalIterator();
		while (iter.hasMoreElements()) {
			JTerminal terminal = iter.nextElement();
			if (terminal.getTerminalId()!=terminalId) continue;
			return terminal;
		}
		return null;
	}

	public JMap<String, JTerminal> getTerminalsByDriver(int driver) throws Exception {
		JMap<String, JTerminal> map = JCollectionFactory.createMap();
		JIterator<JTerminal> iter = this.getTerminalIterator();
		while (iter.hasMoreElements()) {
			JTerminal terminal = iter.nextElement();
			if (driver!=JTerminal.D_ALL && !terminal.hasAvailableDriverFor(driver)) continue;
			map.addElement(terminal.toString(), terminal);
		}
		return map;
	}
	
	public int getCount() throws Exception {
		return this.terminals.size();
	}
	
	public void process(JCashDrawerOpen require) throws Exception {
		JTerminal terminal = this.getCashDrawer();
		if (terminal == null) return;
		terminal.process(require);
	}
	public boolean process(JCashDrawerIsOpen require) throws Exception {
		JTerminal terminal = this.getCashDrawer();
		if (terminal == null) return false;
		return terminal.processAsBoolean(require);
	}
	public void process(JClientDisplay require) throws Exception {
		JTerminal terminal = this.getClientDisplay();
		if (terminal == null) return;
		terminal.process(require);
	}
	public void process(JOperDisplay require) throws Exception {
		JTerminal terminal = this.getOperatorDisplay();
		if (terminal == null) return;
		terminal.process(require);
	}

	public void process(JPrintClose require) throws Exception {
		JIterator<JTerminal> iter = this.getPrinters().getValueIterator();
		while (iter.hasMoreElements()) {
			JTerminal terminal = iter.nextElement();
			JPrintClose printClose = new JPrintClose(require.getType());
			terminal.process(printClose);
		}
	}
	
}
