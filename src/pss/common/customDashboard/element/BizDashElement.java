package pss.common.customDashboard.element;

import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizDashElement extends JRecord {

	private JLong pId = new JLong();
	private JString pDescription = new JString();
	private JString pModule = new JString();
	


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
	public void setDescription(String value) throws Exception {    pDescription.setValue(value);  }
	public void setModule(String value) throws Exception {    pModule.setValue(value);  }

  /**
   * Constructor de la Clase
   */
  public BizDashElement() throws Exception {
  }


	public void createProperties() throws Exception {
		addItem( "id", pId );
		addItem( "description", pDescription);
		addItem( "module", pModule );
  }
  /**
   * Adds the fixed object properties
   */
	public void createFixedProperties() throws Exception {
		addFixedItem( KEY, "ID", "Id", false,false, 18 );
		addFixedItem( FIELD, "description", "Descripción", true, true, 100);
		addFixedItem( FIELD, "module", "Modulo", true,false, 20 );
  }
	
  /**
   * Returns the table name
   */
	public String GetTable() { return ""; }

}
