package pss.common.terminals.core;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import pss.core.tools.XML.JXMLElementFactory;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class JTerminalPool {

	private String sCompany;
	private String sNodo;
	private long lPoolId;
	private String sMAcAddress;
	private JMap<String, JTerminal> terminals = null;
	
	public JTerminalPool() {
	}
	
	public void setCompany(String value) {
		this.sCompany=value;
	}
	public void setNodo(String value) {
		this.sNodo=value;
	}
	public String getCompany() {
		return this.sCompany;
	}
	public String getNodo() {
		return this.sNodo;
	}
	public long getPoolId() {
		return this.lPoolId;
	}
	public void setPoolId(long value) {
		this.lPoolId=value;
	}
	public void setMacAddress(String value) {
		this.sMAcAddress=value;
	}
	public String getMacAddress() {
		return this.sMAcAddress;
	}
	
	public void addTerminal(JTerminal terminal) throws Exception {
		if (this.terminals==null) this.terminals = JCollectionFactory.createMap();
		this.terminals.addElement(String.valueOf(terminal.getTerminalId()), terminal);
	}

	public JTerminal getTerminalById(long terminalId) throws Exception {
		return this.terminals.getElement(String.valueOf(terminalId));
	}
	
	public String serialize() throws Exception {
		StringBuffer xml = new StringBuffer();
		xml.append("<pool pool_id=\""+lPoolId+"\" >");
		JIterator<JTerminal> iter = this.terminals.getValueIterator();
		while (iter.hasMoreElements()) {
			JTerminal terminal = iter.nextElement();
			xml.append("<terminal class=\""+terminal.getClass().getName()+"\" ");
			xml.append(" terminal_id=\""+terminal.getTerminalId()+"\" ");
			xml.append(" connection_string=\""+terminal.getConnectinString()+"\" ");
			xml.append(" company=\""+terminal.getCompany()+"\" ");
			xml.append(" type=\""+terminal.getType()+"\" >");
			JIterator<String> iter2 = terminal.getAvailableDrivers().getKeyIterator();
			while (iter2.hasMoreElements()) {
				String driverType = iter2.nextElement();
				xml.append("<driver id=\""+driverType+"\"/>");
			}
			xml.append("</terminal>");
		}
		xml.append("</pool>");
		return xml.toString();
	}
	
	public static JTerminalPool unserialize(String xml) throws Exception {
		Element root = JXMLElementFactory.getInstance().createElementFromString(xml);
		long poolId = Long.parseLong(root.getAttribute("pool_id"));
		JTerminalPool pool = new JTerminalPool();
		pool.setPoolId(poolId);
		NodeList terminals = root.getElementsByTagName("terminal");
		int len = terminals.getLength();
		for (int i=0; i<len; i++) {
			Element eTerminal = (Element)terminals.item(i);
			String className = eTerminal.getAttribute("class");
			JTerminal terminal =  (JTerminal)Class.forName(className).newInstance();
			terminal.setTerminalId(Long.parseLong(eTerminal.getAttribute("terminal_id")));
			terminal.setConnectionString(eTerminal.getAttribute("connection_string"));
			terminal.setCompany(eTerminal.getAttribute("company"));			
			terminal.setType(eTerminal.getAttribute("type"));
			pool.addTerminal(terminal);
			NodeList drivers = eTerminal.getElementsByTagName("driver");
	  	int len2 = drivers.getLength();
	  	for (int j=0; j<len2; j++) {
	  		Element eDriver = (Element)drivers.item(j);
	  		int driver = Integer.parseInt(eDriver.getAttribute("id"));
	  		terminal.enableDriver(driver);
	  	}
		}
		return pool;
	}
		
}
