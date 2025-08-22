package  pss.common.event.mailing.type;

import pss.common.event.mailing.type.detail.BizMailingPersonaTypeDetail;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;

public class BizMailingPersonaType extends JRecord {
	public final static String TODOS = "TODOS";
	public final static String NO_INCLUIDS = "NO_INCLUIDS";
	public final static String INCLUIDOS = "INCLUIDOS";
	public final static String TODOS_NO_INCLUIDOS = "TODOS_NO_INCLUIDOS";


  
  static JMap<String,String> gTipos;
  public static JMap<String,String> getTipos() throws Exception {
  	if (gTipos!=null) return gTipos;
  	gTipos=JCollectionFactory.createMap();
  	gTipos.addElement(TODOS, "Todos");
  	gTipos.addElement(TODOS_NO_INCLUIDOS, "No incluidos en ninguna lista");
  	gTipos.addElement(NO_INCLUIDS, "No en la lista");
   	gTipos.addElement(INCLUIDOS, "En la lista");
 
  	return gTipos;
  }
  private JLong pIdtipo = new JLong();
  private JString pCompany = new JString();
  private JString pDescripcion = new JString();
  private JString pTipo = new JString();
  
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setIdtipo(long zValue) throws Exception {    pIdtipo.setValue(zValue);  }
  public long getIdtipo() throws Exception {     return pIdtipo.getValue();  }
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public void setTipo(String zValue) throws Exception {    pTipo.setValue(zValue);  }
  public String getTipo() throws Exception {     return pTipo.getValue();  }
 

  public BizMailingPersonaType() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany);
    this.addItem( "id_tipo", pIdtipo);
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "tipo", pTipo );
    
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_tipo", "Id", false, false, 64 );
    this.addFixedItem( FIELD, "company", "Company", true, true, 50 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, false, 250 );
    this.addFixedItem( FIELD, "tipo", "tipo", true, false, 50 );
 }
  
  @Override
  public void createControlProperties() throws Exception {
  	this.addControlsProperty("tipo", createControlCombo(JWins.createVirtualWinsFromMap(getTipos()),null, null) );
  	super.createControlProperties();
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_PERSONA_TYPES"; }

  public void processInsert() throws Exception {
   	super.processInsert();
  }
  public void processUpdate() throws Exception {
   	super.processUpdate();
  };

  /**
   * Default Read() method
   */
  public boolean Read( long zIdtipo) throws Exception { 
    this.addFilter( "id_tipo",  zIdtipo ); 
    return this.read(); 
  } 
  
	public JRecords<BizMailingPersonaTypeDetail> getObjDetalle() throws Exception {
		JRecords<BizMailingPersonaTypeDetail> dets= new JRecords<BizMailingPersonaTypeDetail>(BizMailingPersonaTypeDetail.class);
		dets.addFilter("company", getCompany());
		dets.addFilter("id_tipo", getIdtipo());
		return dets;
	}
 }
