package pss.tourism.pnr;


import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizTicketsFaltantes extends JRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2578980448291696798L;
	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JLong pId=new JLong();
	private JString pCompany=new JString();
	private JString pNroBoleto=new JString();
	private JBoolean pReprocesar=new JBoolean();

	public void setNroBoleto(String value) throws Exception {
		pNroBoleto.setValue(value);
	}
	public void setCompany(String value) throws Exception {
		pCompany.setValue(value);
	}
	public void setReprocesar(boolean value) throws Exception {
		pReprocesar.setValue(value);
	}
	public String getCompany() throws Exception {
		return pCompany.getValue();
	}
	public String getNroBoleto() throws Exception {
		return pNroBoleto.getValue();
	}

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizTicketsFaltantes() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("id", pId);
		addItem("company", pCompany);
		addItem("nro_boleto", pNroBoleto);
		addItem("reprocesar", pReprocesar);
	}

	@Override
	public void createFixedProperties() throws Exception {

		addFixedItem(KEY, "id", "id", false, false, 64);
		addFixedItem(FIELD, "company", "company", true, true, 15);
		addFixedItem(FIELD, "nro_boleto", "nro_boleto", true, true, 50);
		addFixedItem(FIELD, "reprocesar", "reprocesar", true, true, 1);
	}

 

	@Override
	public String GetTable() {
		return "TUR_FALTANTES";
	}


	public boolean Read(String company,String nroBoleto) throws Exception {
		addFilter("company", company);
		addFilter("nro_boleto", nroBoleto);
		return this.read();
	}

	

}
