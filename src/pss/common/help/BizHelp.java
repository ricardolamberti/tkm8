package  pss.common.help;

import java.util.TreeMap;

import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;

public class BizHelp extends JRecord {

	private JLong pSecuencia=new JLong();
	private JString pCompany=new JString();
	private JString pPage=new JString();
	private JString pAction=new JString();
	private JString pStatus=new JString();
	private JString pId=new JString();
	private JString pHelp=new JString();

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	public void setPage(String zValue) throws Exception {
		pPage.setValue(zValue);
	}

	public String getPage() throws Exception {
		return pPage.getValue();
	}

	public void setAction(String zValue) throws Exception {
		pAction.setValue(zValue);
	}

	public String getAction() throws Exception {
		return pAction.getValue();
	}

	public void setStatus(String zValue) throws Exception {
		pStatus.setValue(zValue);
	}

	public String getStatus() throws Exception {
		return pStatus.getValue();
	}



	public void setId(String zValue) throws Exception {
		pId.setValue(zValue);
	}

	public String getId() throws Exception {
		return pId.getValue();
	}

	public void setHelp(String zValue) throws Exception {
		pHelp.setValue(zValue);
	}

	public String getHelp() throws Exception {
		return pHelp.getValue();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizHelp() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		this.addItem("secuencia", pSecuencia);
		this.addItem("company", pCompany);
		this.addItem("page", pPage);
		this.addItem("action", pAction);
		this.addItem("status", pStatus);
		this.addItem("id", pId);
		this.addItem("Help", pHelp);
	}

	/**
	 * Adds the fixed object properties
	 */
	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "secuencia", "Secuencia", false, false, 64);
		this.addFixedItem(FIELD, "company", "Company", true, false, 50);
		this.addFixedItem(FIELD, "page", "Pagina", true, false, 256);
		this.addFixedItem(FIELD, "action", "Accion", true, false, 256);
		this.addFixedItem(FIELD, "status", "Estado", true, false, 256);
		this.addFixedItem(FIELD, "id", "Identificador", true, false, 100);
		this.addFixedItem(FIELD, "Help", "Ayuda", true, false, 1000);
	}

	/**
	 * Returns the table name
	 */
	@Override
	public String GetTable() {
		return "HELP_DYNAMIC";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default Read() method
	 */
	public boolean Read(long sec) throws Exception {
		addFilter("secuencia", sec);
		return read();
	}

	public boolean read(String company,String act,String status, String win,String id) throws Exception {
		addFilter("company", company);
		addFilter("page", act);
		addFilter("action", status);
		addFilter("status", win);
		addFilter("id", id);
		return read();
	}

	public static JRecords<BizHelp> ReadByPage(String zPage) throws Exception {
		JRecords<BizHelp> detalles=new JRecords<BizHelp>(BizHelp.class);
		detalles.addFilter("page", zPage);
		detalles.readAll();
		return detalles;
	}
	static  TreeMap<String,String> map;
	static TreeMap<String,String> getMap() throws Exception {
		if (map!=null) return map;
		TreeMap<String, String> localmap=new TreeMap<String, String>();
		
		JIterator<BizHelp> it = new JRecords<BizHelp>(BizHelp.class).getStaticIterator();
		while (it.hasMoreElements()) {
			BizHelp help = it.nextElement();
			String key = help.getPage()+"_"+help.getAction()+"_"+help.getStatus()+"_"+help.getId();
			localmap.put(key, help.getHelp());
		}
		
		return map=localmap;
	}

	
}
