package  pss.common.regions.propagation;

import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizPropagatesDetail extends JRecord {
  private JString pIdpropagate = new JString();
  private JString pBdclass = new JString();
  private JBoolean pPropagate = new JBoolean();
  private JBoolean pFromChildren = new JBoolean();
  private JBoolean pToChildren = new JBoolean();
  private JBoolean pToMaster = new JBoolean();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setIdpropagate(String zValue) throws Exception {    pIdpropagate.setValue(zValue);  }
  public String getIdpropagate() throws Exception {     return pIdpropagate.getValue();  }
  public void setBdclass(String zValue) throws Exception {    pBdclass.setValue(zValue);  }
  public String getBdclass() throws Exception {     return pBdclass.getValue();  }
  public void setPropagate(boolean zValue) throws Exception {    pPropagate.setValue(zValue);  }
  public boolean isPropagate() throws Exception {     return pPropagate.getValue();  }
  public void setFromChildren(boolean zValue) throws Exception {    pFromChildren.setValue(zValue);  }
  public boolean acceptFromChildren() throws Exception {     return pFromChildren.getValue();  }
  public void setToChildren(boolean zValue) throws Exception {    pToChildren.setValue(zValue);  }
  public boolean sendToChildren() throws Exception {     return pToChildren.getValue();  }
  public void setToMaster(boolean zValue) throws Exception {    pToMaster.setValue(zValue);  }
  public boolean sendToMaster() throws Exception {     return pToMaster.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizPropagatesDetail() throws Exception {
  }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Properties methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Adds the object properties
   */
  @Override
	public void createProperties() throws Exception {
    this.addItem( "id_propagate", pIdpropagate );
    this.addItem( "bdClass", pBdclass );
    this.addItem( "propagate", pPropagate );
    this.addItem( "fromChildren", pFromChildren );
    this.addItem( "toChildren", pToChildren );
    this.addItem( "toMaster", pToMaster );
  }
  /**
   * Adds the fixed object properties
   */
  @Override
	public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_propagate", "Id propagate", true, true, 50 );
    this.addFixedItem( KEY, "bdClass", "Bdclass", true, true, 100 );
    this.addFixedItem( FIELD, "propagate", "Propagate", true, true, 1 );
    this.addFixedItem( FIELD, "fromChildren", "Acepta mensaje de los hijos", true, true, 1 );
    this.addFixedItem( FIELD, "toChildren", "Envia mensaje de los hijos", true, true, 1 );
    this.addFixedItem( FIELD, "toMaster", "Envia mensajes al BD master", true, true, 1 );
  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return "PRO_PROPAGATE_DETAIL"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default Read() method
   */
  public boolean Read( String zIdpropagate, String zBdclass ) throws Exception {
    addFilter( "id_propagate",  zIdpropagate );
    addFilter( "bdClass",  zBdclass );
    return Read();
  }


  @Override
	public void processInsert() throws Exception {
    BizPropagate.expire();
    super.processInsert();
  }

  @Override
	public void processUpdate() throws Exception {
    BizPropagate.expire();
    super.processUpdate();
  }

  @Override
	public void processDelete() throws Exception {
    BizPropagate.expire();
    super.processDelete();
  }

}
