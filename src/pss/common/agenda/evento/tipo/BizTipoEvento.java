package  pss.common.agenda.evento.tipo;

import pss.common.security.BizUsuario;
import pss.core.services.fields.JColour;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class BizTipoEvento extends JRecord {

  private JString pCompany = new JString();
  private JString pLogica = new JString();
  private JColour pColor = new JColour();
  private JString pDescripcion = new JString();
  private JLong pIdtipoevento = new JLong();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setLogica(String zValue) throws Exception {    pLogica.setValue(zValue);  }
  public String getLogica() throws Exception {     return pLogica.getValue();  }
  public boolean isNullLogica() throws Exception { return  pLogica.isNull(); } 
  public void setNullToLogica() throws Exception {  pLogica.setNull(); } 
  public void setColor(String zValue) throws Exception {    pColor.setValue(zValue);  }
  public String getColor() throws Exception {     return pColor.getValue();  }
  public boolean isNullColor() throws Exception { return  pColor.isNull(); } 
  public void setNullToColor() throws Exception {  pColor.setNull(); } 
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public boolean isNullDescripcion() throws Exception { return  pDescripcion.isNull(); } 
  public void setNullToDescripcion() throws Exception {  pDescripcion.setNull(); } 
  public void setIdtipoevento(long zValue) throws Exception {    pIdtipoevento.setValue(zValue);  }
  public long getIdtipoevento() throws Exception {     return pIdtipoevento.getValue();  }
  public boolean isNullIdtipoevento() throws Exception { return  pIdtipoevento.isNull(); } 
  public void setNullToIdtipoevento() throws Exception {  pIdtipoevento.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizTipoEvento() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "logica", pLogica );
    this.addItem( "color", pColor );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "id_tipoevento", pIdtipoevento );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_tipoevento", "Id tipoevento", false, false, 64 );

    this.addFixedItem( FIELD, "company", "Company", true, true, 15 );
    this.addFixedItem( FIELD, "logica", "Logica", true, false, 50 );
    this.addFixedItem( FIELD, "color", "Color", true, true, 10 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, true, 250 );
  
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "agd_evento_tipo"; }
  public static final String HACER_NADA = "HACER_NADA";
//  public static final String CREAR_EXP = "CREAR_EXP";

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  static JMap<String,String> logicas;
  public static JMap<String,String> getLogicas() throws Exception {
  	if (logicas!=null) return logicas;
  	JMap<String,String> maps = JCollectionFactory.createMap();
  	return logicas=BizUsuario.getUsr().getObjBusiness().eventoLogicasClasesConocidasMap(maps);
  }
  
  public static Class getLogicasClass(String id) throws Exception {
  	return BizUsuario.getUsr().getObjBusiness().eventoLogicasClasesConocidas(id);
  }
  /**
   * Default read() method
   */
  public boolean read( String zCompany, long zIdtipoevento ) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "id_tipoevento",  zIdtipoevento ); 
    return read(); 
  } 
  public boolean read( long zIdtipoevento ) throws Exception { 
    addFilter( "id_tipoevento",  zIdtipoevento ); 
    return read(); 
  } 
}
