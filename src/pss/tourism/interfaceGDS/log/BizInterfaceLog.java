package pss.tourism.interfaceGDS.log;

import java.util.Date;

import pss.bsp.gds.BizInterfazNew;
import pss.common.regions.nodes.BizNodo;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizInterfaceLog extends JRecord {
	private JString pCompany = new JString();
	private JString pStore = new JString();
	private JString pId = new JString();
	private JDateTime pLastEcho = new JDateTime(true);
	private JString pLastFile = new JString();
	private JString pLastIP = new JString();
	private JString pLastServer = new JString();
	private JString pLastDirectory = new JString();
	private JLong pVersion = new JLong();
	private JString pHost2 = new JString();
	private JDateTime pLastTransfer = new JDateTime(true);
	private JDateTime pLastReject = new JDateTime(true);
	private JBoolean hasTickets = new JBoolean();
	
	private JString pDescStore = new JString() {
		public void preset() throws Exception {
			BizNodo nod = new BizNodo();
			nod.dontThrowException(true);
			if (nod.Read(pCompany.getValue(), pStore.getValue()))
				pDescStore.setValue(nod.GetDescrip());
			else
				pDescStore.setValue(pStore.getValue());
		}
	};

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setVersion(long zValue) throws Exception {
		pVersion.setValue(zValue);
	}

	public long getVersion() throws Exception {
		return pVersion.getValue();
	}
	
	public void setHasTickets(boolean zValue) throws Exception {
		hasTickets.setValue(zValue);
	}

	public boolean hasTickets() throws Exception {
		return hasTickets.getValue();
	}


	public void setHost2(String zValue) throws Exception {
		pHost2.setValue(zValue);
	}

	public String getHost2() throws Exception {
		return pHost2.getValue();
	}

	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public void setStore(String zValue) throws Exception {
		pStore.setValue(zValue);
	}

	public String getStore() throws Exception {
		return pStore.getValue();
	}

	public void setId(String zValue) throws Exception {
		pId.setValue(zValue);
	}

	public String getId() throws Exception {
		return pId.getValue();
	}

	public void setLastEcho(Date zValue) throws Exception {
		pLastEcho.setValue(zValue);
	}

	public Date getLastEcho() throws Exception {
		return (pLastEcho.getValue());
	}

	public void setLastRejected(Date zValue) throws Exception {
		pLastReject.setValue(zValue);
	}

	public Date getLastRejected() throws Exception {
		return (pLastReject.getValue());
	}

	public void setLastFile(String zValue) throws Exception {
		pLastFile.setValue(zValue);
	}

	public String getLastFile() throws Exception {
		return pLastFile.getValue();
	}

	public void setLastTransfer(Date zValue) throws Exception {
		pLastTransfer.setValue(zValue);
	}

	public Date getLastTransfer() throws Exception {
		return (pLastTransfer.getValue());
	}

	public void setLastIP(String zValue) throws Exception {
		pLastIP.setValue(zValue);
	}

	public String getLastIP() throws Exception {
		return pLastIP.getValue() + "";
	}

	public void setLastDirectory(String zValue) throws Exception {
		pLastDirectory.setValue(zValue);
	}

	public String getLastDirectory() throws Exception {
		return pLastDirectory.getValue();
	}

	public void setLastServer(String zValue) throws Exception {
		pLastServer.setValue(zValue);
	}

	public String getLastServer() throws Exception {
		return pLastServer.getValue();
	}
	public Date getLastTicket() throws Exception {
		BizInterfazNew tkt = new BizInterfazNew();
		tkt.addFilter("company", getCompany());
		tkt.dontThrowException(true);
		if ( tkt.read() == false ) 
			return null;
		return tkt.getLastPnr();
	}
	


	/**
	 * Constructor de la Clase
	 */
	public BizInterfaceLog() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("store", pStore);
		this.addItem("id", pId);
		this.addItem("lastfile", pLastFile);
		this.addItem("lastecho", pLastEcho);
		this.addItem("last_reject", pLastReject);
		this.addItem("version", pVersion);
		this.addItem("lasttransfer", pLastTransfer);
		this.addItem("last_server", pLastServer);
		this.addItem("last_directory", pLastDirectory);
		this.addItem("last_ip", pLastIP);

		this.addItem("host2", pHost2);
		this.addItem("desc_store", pDescStore);
		this.addItem("has_tickets", hasTickets);
	}

	/**
	 * Adds the fixed object properties
	 */
	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Empresa", true, true, 50);
		this.addFixedItem(KEY, "store", "Sucursal", true, true, 50);
		this.addFixedItem(KEY, "id", "Id", true, true, 250);
		this.addFixedItem(FIELD, "lastecho", "Ultima Conexion", true, true, 50);
		this.addFixedItem(FIELD, "lastfile", "Ultimo Archivo", true, false, 50);
		this.addFixedItem(FIELD, "lasttransfer", "Ultimo Envio", true, false, 50);
		this.addFixedItem(FIELD, "last_reject", "Ultimo Rechazo", true, false, 50);
		this.addFixedItem(FIELD, "last_server", "Servidor Local", true, false, 250);
		this.addFixedItem(FIELD, "last_ip", "Servidor IP", true, false, 250);
		this.addFixedItem(FIELD, "last_directory", "Directorio Local", true, false, 250);

		this.addFixedItem(FIELD, "version", "Version", true, false, 50);
		this.addFixedItem(FIELD, "host2", "Host2", true, false, 50);
		this.addFixedItem(VIRTUAL, "desc_store", "Sucursal", true, false, 250);
		this.addFixedItem(VIRTUAL, "has_tickets", "Tiene boletos", true, false, 250);
	}

	/**
	 * Returns the table name
	 */
	@Override
	public String GetTable() {
		return "TUR_INTERFACE_LOG";
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default Read() method
	 */
	public boolean read(String company, String store, String id) throws Exception {
		clearFilters();
		addFilter("company", company);
		addFilter("store", store);
		addFilter("id", id);
		return read();
	}
}
