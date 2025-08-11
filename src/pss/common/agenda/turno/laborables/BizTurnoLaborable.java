package pss.common.agenda.turno.laborables;

import pss.common.security.BizUsuario;
import pss.core.services.fields.JHour;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class BizTurnoLaborable extends JRecord {

  private JString pCompany = new JString();
  private JLong pIdAgenda = new JLong();
  private JLong pId = new JLong();
  private JHour pHoraDesde = new JHour();
  private JHour pHoraHasta = new JHour();
  private JLong pDia = new JLong();
  private JLong pDuracion = new JLong();
  private JLong pSeparacion = new JLong();
  private JString pDescripcion = new JString() {
  	public void preset() throws Exception {
  		pDescripcion.setValue(getDescripcion());
  	};
  };
  
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
  public void setHoraDesde(String zValue) throws Exception {    pHoraDesde.setValue(zValue);  }
  public String getHoraDesde() throws Exception {     return pHoraDesde.getValue();  }
  public boolean isNullHoraDesde() throws Exception { return  pHoraDesde.isNull(); } 
  public void setNullToHoraDesde() throws Exception {  pHoraDesde.setNull(); } 
  public void setHoraHasta(String zValue) throws Exception {    pHoraHasta.setValue(zValue);  }
  public String getHoraHasta() throws Exception {     return pHoraHasta.getValue();  }
  public boolean isNullHoraHasta() throws Exception { return  pHoraHasta.isNull(); } 
  public void setNullToHoraHasta() throws Exception {  pHoraHasta.setNull(); } 
  public void setDia(long zValue) throws Exception {    pDia.setValue(zValue);  }
  public long getDia() throws Exception {     return pDia.getValue();  }

  public void setDuracion(long zValue) throws Exception {    pDuracion.setValue(zValue);  }
  public long getDuracion() throws Exception {     return pDuracion.getValue();  }
  public void setSeparacion(long zValue) throws Exception {    pSeparacion.setValue(zValue);  }
  public long getSeparacion() throws Exception {     return pSeparacion.getValue();  }

  public String getDescripcion() throws Exception {
  	
  	return getDias().getElement(""+pDia.getValue());
  }
  /**
   * Constructor de la Clase
   */
  public BizTurnoLaborable() throws Exception {
  }

  static JMap<String,String> dias;
  public static JMap<String,String> getDias() throws Exception {
  	if (dias!=null) return dias;
  	dias = JCollectionFactory.createOrderedMap();
  	dias.addElement("2", "Lunes");
  	dias.addElement("3", "Martes");
  	dias.addElement("4", "Miercoles");
  	dias.addElement("5", "Jueves");
  	dias.addElement("6", "Viernes");
  	dias.addElement("7", "Sabado");
  	dias.addElement("1", "Domingo");
  	return dias;
  }

  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "id", pId );
    this.addItem( "id_agenda", pIdAgenda );
    this.addItem( "dia", pDia );
    this.addItem( "hora_desde", pHoraDesde );
    this.addItem( "hora_hasta", pHoraHasta );
    this.addItem( "duracion", pDuracion );
    this.addItem( "separacion", pSeparacion );
    this.addItem( "descripcion", pDescripcion );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "id_agenda", "Id agenda", true, false, 64 );
    this.addFixedItem( KEY, "id", "Id", false, false, 64 );
    this.addFixedItem( FIELD, "hora_desde", "Hora desde", true, true, 18 );
    this.addFixedItem( FIELD, "hora_hasta", "Hora hasta", true, true, 18 );
    this.addFixedItem( FIELD, "duracion", "Duracion", true, false, 18 );
    this.addFixedItem( FIELD, "separacion", "Separacion", true, false, 18 );
    this.addFixedItem( FIELD, "dia", "Dia semana", true, false, 18 );
    this.addFixedItem( VIRTUAL, "descripcion", "Descripcion", true, false, 250 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "agd_manejturnos_hora_lab"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( String zCompany ,long agenda, long id) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "id_Agenda",  agenda ); 
    addFilter( "id",  id ); 
    return read(); 
  } 
  
  @Override
  public void processInsert() throws Exception {
  	if (pCompany.isNull()) pCompany.setValue(BizUsuario.getUsr().getCompany());
  	super.processInsert();
  }
  
  


}
