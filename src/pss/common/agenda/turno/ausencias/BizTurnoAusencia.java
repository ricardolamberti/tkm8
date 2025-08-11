package  pss.common.agenda.turno.ausencias;

import java.util.Date;

import pss.common.security.BizUsuario;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizTurnoAusencia extends JRecord {

  private JString pCompany = new JString();
  private JLong pIdAgenda = new JLong();
  private JString pRazon = new JString();
  private JLong pId = new JLong();
  private JDateTime pHoraDesde = new JDateTime();
  private JDateTime pHoraHasta = new JDateTime();
 
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 


  public void setIdAgenda(long zValue) throws Exception {    pIdAgenda.setValue(zValue);  }
  public long getIdAgenda() throws Exception {     return pIdAgenda.getValue();  }
  public boolean isNullIdAgenda() throws Exception { return  pIdAgenda.isNull(); } 
  public void setNullToIdAgenda() throws Exception {  pIdAgenda.setNull(); } 
  public void setHoraDesde(Date zValue) throws Exception {    pHoraDesde.setValue(zValue);  }
  public Date getHoraDesde() throws Exception {     return pHoraDesde.getValue();  }
  public boolean isNullHoraDesde() throws Exception { return  pHoraDesde.isNull(); } 
  public void setNullToHoraDesde() throws Exception {  pHoraDesde.setNull(); } 
  public void setHoraHasta(Date zValue) throws Exception {    pHoraHasta.setValue(zValue);  }
  public Date getHoraHasta() throws Exception {     return pHoraHasta.getValue();  }
  public boolean isNullHoraHasta() throws Exception { return  pHoraHasta.isNull(); } 
  public void setNullToHoraHasta() throws Exception {  pHoraHasta.setNull(); } 

 
  /**
   * Constructor de la Clase
   */
  public BizTurnoAusencia() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "id", pId );
    this.addItem( "id_agenda", pIdAgenda );
    this.addItem( "razon", pRazon );
    this.addItem( "fecha_desde", pHoraDesde );
    this.addItem( "fecha_hasta", pHoraHasta );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "id_agenda", "Id agenda", true, false, 64 );
    this.addFixedItem( KEY, "id", "Id", false, false, 64 );
    this.addFixedItem( FIELD, "razon", "Razones", true, true, 5018 );
    this.addFixedItem( FIELD, "fecha_desde", "Fecha desde", true, true, 18 );
    this.addFixedItem( FIELD, "fecha_hasta", "Fecha hasta", true, true, 18 );
}
  /**
   * Returns the table name
   */
  public String GetTable() { return "agd_manejturnos_ausen"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( String zCompany ,String zUsuario, long id) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "id_usuario",  zUsuario ); 
    addFilter( "id",  id ); 
    return read(); 
  } 
  
  @Override
  public void processInsert() throws Exception {
  	if (pCompany.isNull()) pCompany.setValue(BizUsuario.getUsr().getCompany());
  	super.processInsert();
  }
  
  


}
