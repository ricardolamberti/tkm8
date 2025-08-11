package pss.common.regions.entidad.nodos;

import pss.common.regions.divitions.BizPaisLista;
import pss.common.regions.nodes.BizNodo;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JMap;


public class BizEntidadNodo extends JRecord {
	
  private JLong pIdEntidad = new JLong();
  private JString pNodo = new JString();
  private JString pCompany = new JString();
  private JString pDescrNodo = new JString() {
  	public void preset() throws Exception {
  		setValue(getDescrNodo());;
  	};
  };

  public String getNodo() throws Exception { return this.pNodo.getValue();}

  /**
   * Constructor de la Clase
   */
  public BizEntidadNodo() throws Exception {
  }
  
  @Override
  public void createProperties() throws Exception {
    this.addItem( "id_entidad", pIdEntidad );
    this.addItem( "nodo", pNodo );
    this.addItem( "company", pCompany );
    this.addItem( "descr_nodo", pDescrNodo);
  }
  
  @Override
	public void createFixedProperties() throws Exception {
  	this.addFixedItem( KEY, "id_entidad", "Id Entidad", true, false, 18 );
    this.addFixedItem( KEY, "Nodo", "Nodo", true, true, 15 );
    this.addFixedItem( FIELD, "company", "Company", true, true, 100 );
    this.addFixedItem( VIRTUAL, "descr_nodo", "Nodo", true, true, 100 );
  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return "NOD_ENTIDAD_NODO"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default Read() method
   */
  public boolean read(long id, String nodo) throws Exception { 
    addFilter( "id",  id ); 
    addFilter( "nodo",  nodo ); 
    return this.read(); 
  } 
  
  private BizNodo nodo;
  public BizNodo getObjNodo() throws Exception {
  	if (this.nodo!=null) return this.nodo;
  	BizNodo n = BizNodo.getObjNodo(this.pCompany.getValue(), this.pNodo.getValue());
  	return (this.nodo=n);
  }
  
  public String getDescrNodo() throws Exception {
  	return this.getObjNodo().GetDescrip();
  }
  
}
