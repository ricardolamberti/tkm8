package  pss.common.agenda.historia;

import java.util.Date;

import pss.core.services.fields.JDate;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizEventoHistoria extends JRecord {

  private JString pUsuario = new JString();
  private JString pEstado = new JString();
  private JString pTexto = new JString();
  private JLong pId = new JLong();
  private JLong pIdevento = new JLong();
  private JString pCompany = new JString();
  private JDateTime pFecha = new JDateTime();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setUsuario(String zValue) throws Exception {    pUsuario.setValue(zValue);  }
  public String getUsuario() throws Exception {     return pUsuario.getValue();  }
  public boolean isNullUsuario() throws Exception { return  pUsuario.isNull(); } 
  public void setNullToUsuario() throws Exception {  pUsuario.setNull(); } 
  public void setEstado(String zValue) throws Exception {    pEstado.setValue(zValue);  }
  public String getEstado() throws Exception {     return pEstado.getValue();  }
  public boolean isNullEstado() throws Exception { return  pEstado.isNull(); } 
  public void setNullToEstado() throws Exception {  pEstado.setNull(); } 
  public void setTexto(String zValue) throws Exception {    pTexto.setValue(zValue);  }
  public String getTexto() throws Exception {     return pTexto.getValue();  }
  public boolean isNullTexto() throws Exception { return  pTexto.isNull(); } 
  public void setNullToTexto() throws Exception {  pTexto.setNull(); } 
  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public boolean isNullId() throws Exception { return  pId.isNull(); } 
  public void setNullToId() throws Exception {  pId.setNull(); } 
  public void setIdevento(long zValue) throws Exception {    pIdevento.setValue(zValue);  }
  public long getIdevento() throws Exception {     return pIdevento.getValue();  }
  public boolean isNullIdevento() throws Exception { return  pIdevento.isNull(); } 
  public void setNullToIdevento() throws Exception {  pIdevento.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizEventoHistoria() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "usuario", pUsuario );
    this.addItem( "estado", pEstado );
    this.addItem( "texto", pTexto );
    this.addItem( "fecha", pFecha );
    this.addItem( "id", pId );
    this.addItem( "id_evento", pIdevento );
    this.addItem( "company", pCompany );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "Id", false, false, 64 );
    this.addFixedItem( FIELD, "usuario", "Usuario", true, true, 15 );
    this.addFixedItem( FIELD, "estado", "Estado", true, false, 50 );
    this.addFixedItem( FIELD, "texto", "Texto", true, true, 4000 );
    this.addFixedItem( FIELD, "fecha", "Fecha", true, true, 16 );
    this.addFixedItem( FIELD, "id_evento", "Id evento", true, true, 18 );
    this.addFixedItem( FIELD, "company", "Company", true, true, 15 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "agd_evento_historia"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( long zId, long zIdevento, String zCompany ) throws Exception { 
    addFilter( "id",  zId ); 
    addFilter( "id_evento",  zIdevento ); 
    addFilter( "company",  zCompany ); 
    return read(); 
  } 
  
  @Override
  public void processInsert() throws Exception {
  	if (pFecha.isNull())
  		pFecha.setValue(new Date());
  	super.processInsert();
  }
}
