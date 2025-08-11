package pss.common.customList.advanced;

import pss.common.customList.config.relation.JRelations;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizAdvancedSQL extends JRecord {
	
	private JString pField1 = new JString();
	private JString pField2 = new JString();
	private JString pField3= new JString();
	private JString pField4 = new JString();
	private JString pField5 = new JString();
	private JString pField6 = new JString();
	private JString pField7 = new JString();
	private JString pField8 = new JString();
	private JString pField9 = new JString();


	/**
	 * Constructor de la Clase
	 */
	public BizAdvancedSQL() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("field1", pField1);
		this.addItem("field2", pField2);
		this.addItem("field3", pField3);
		this.addItem("field4", pField4);
		this.addItem("field5", pField5);
		this.addItem("field6", pField6);
		this.addItem("field7", pField7);
		this.addItem("field8", pField8);
		this.addItem("field9", pField9);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(FIELD, "field1", "Campo 1", true, true, 1);
		this.addFixedItem(FIELD, "field2", "Campo 2", true, true, 1);
		this.addFixedItem(FIELD, "field3", "Campo 3", true, true, 1);
		this.addFixedItem(FIELD, "field4", "Campo 4", true, true, 1);
		this.addFixedItem(FIELD, "field5", "Campo 5", true, true, 1);
		this.addFixedItem(FIELD, "field6", "Campo 6", true, true, 1);
		this.addFixedItem(FIELD, "field7", "Campo 7", true, true, 1);
		this.addFixedItem(FIELD, "field8", "Campo 8", true, true, 1);
		this.addFixedItem(FIELD, "field9", "Campo 9", true, true, 1);
	}
	

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "SQL";
	}

	public void attachRelationMap(JRelations rels) throws Exception {
  }
	
	public String getRecordName() throws Exception {
		return "SQL Avanzado";
	}

}
