package pss.common.pki.ejbca;

import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;


public class BizPKICertificado  extends JRecord {

  private JString pUsuario = new JString();
  private JString pFecha = new JString();
  private JString pEstado = new JString();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setUsuario(String zValue) throws Exception {    pUsuario.setValue(zValue);  }
  public String getUsuario() throws Exception {     return pUsuario.getValue();  }
  public void setFecha(String zValue) throws Exception {    pFecha.setValue(zValue);  }
  public String getFecha() throws Exception {     return pFecha.getValue();  }
  public void setEstado(String zValue) throws Exception {    pEstado.setValue(zValue);  }
  public String getEstado() throws Exception {     return pEstado.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizPKICertificado() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "usuario", pUsuario );
    this.addItem( "fecha", pFecha );
    this.addItem( "estado", pEstado );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "usuario", "Usuario", true, true, 50 );
    this.addFixedItem( FIELD, "fecha", "Fecha", true, true, 50 );
    this.addFixedItem( FIELD, "estado", "Estado", true, true, 50 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return null; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public boolean isVigente() throws Exception {
  	 return getEstado().equals("VIGENTE");
   }
   public boolean isRevocado() throws Exception {
  	 return getEstado().equals("REVOCADO");
   }
   public boolean isPendiente() throws Exception {
  	 return getEstado().equals("PENDIENTE");
   }
  /**
   * Default read() method
   */
  public boolean read( String zUsuario ) throws Exception { 
  	return BizPKIConfiguracion.statusUser(this,zUsuario);
    
  } 

 

}
