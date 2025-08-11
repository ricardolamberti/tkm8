package  pss.common.agenda.participantes;

import pss.common.agenda.evento.BizEvento;
import pss.common.agenda.evento.tipo.GuiTipoEventos;
import pss.common.agenda.historia.BizEventoHistoria;
import pss.common.agenda.rol.BizEventoRol;
import pss.common.agenda.rol.GuiEventoRoles;
import pss.common.personalData.BizPersona;
import pss.common.personalData.GuiPersonas;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JPair;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;

public class BizEventoParticipante extends JRecord {

  private JLong pRol = new JLong();
  private JString pEstado = new JString();
  private JLong pIdpersona = new JLong();
  private JLong pIdevento = new JLong();
  private JString pCompany = new JString();
  private JString pNombreCompleto = new JString() {
  	public void preset() throws Exception {
  		pNombreCompleto.setValue(getNombreCompleto());
  	};
  };
  private JString pDescrEstado = new JString() {
  	public void preset() throws Exception {
  		pDescrEstado.setValue(getDescrEstado());
  	};
  };
  private JString pDescrRol = new JString() {
  	public void preset() throws Exception {
  		pDescrRol.setValue(getDescrRol());
  	};
  };

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setRol(long zValue) throws Exception {    pRol.setValue(zValue);  }
  public long getRol() throws Exception {     return pRol.getValue();  }
  public boolean isNullRol() throws Exception { return  pRol.isNull(); } 
  public void setNullToRol() throws Exception {  pRol.setNull(); } 
  public void setEstado(String zValue) throws Exception {    pEstado.setValue(zValue);  }
  public String getEstado() throws Exception {     return pEstado.getValue();  }
  public boolean isNullEstado() throws Exception { return  pEstado.isNull(); } 
  public void setNullToEstado() throws Exception {  pEstado.setNull(); } 
  public void setIdpersona(long zValue) throws Exception {    pIdpersona.setValue(zValue);  }
  public long getIdpersona() throws Exception {     return pIdpersona.getValue();  }
  public boolean isNullIdpersona() throws Exception { return  pIdpersona.isNull(); } 
  public void setNullToIdpersona() throws Exception {  pIdpersona.setNull(); } 
  public void setIdevento(long zValue) throws Exception {    pIdevento.setValue(zValue);  }
  public long getIdevento() throws Exception {     return pIdevento.getValue();  }
  public boolean isNullIdevento() throws Exception { return  pIdevento.isNull(); } 
  public void setNullToIdevento() throws Exception {  pIdevento.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public String getNombreCompleto() throws Exception { 
  	if (getObjPersona()==null) return "Desconocido";
  	return getObjPersona().getNombreCompleto();  
  }
  public String getDescrRol() throws Exception { 
  	if (pRol.isNull()) return "";
  	if (getObjRol()==null) return "Desconocido";
  	return getObjRol().getDescripcion();  
  }
  public String getDescrEstado() throws Exception { 
  	if (pEstado.isNull()) return "";
  	return  getEstados().getElement(getEstado());  
  }

  BizPersona objPersona;
  public BizPersona getObjPersona() throws Exception {
  	if (objPersona!=null) return objPersona;
  	BizPersona p = new BizPersona();
  	p.dontThrowException(true);
  	if (!p.Read(getIdpersona())) return null;
  	return objPersona = p;
  }  
  BizEventoRol objRol;
  public BizEventoRol getObjRol() throws Exception {
  	if (objRol!=null) return objRol;
  	BizEventoRol p = new BizEventoRol();
  	p.dontThrowException(true);
  	if (!p.read(getRol(),getCompany())) return null;
  	return objRol = p;
  }
  
  /**
   * Constructor de la Clase
   */
  public BizEventoParticipante() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "rol", pRol );
    this.addItem( "estado", pEstado );
    this.addItem( "id_persona", pIdpersona );
    this.addItem( "id_evento", pIdevento );
    this.addItem( "company", pCompany );
    this.addItem( "nombre", pNombreCompleto );
    this.addItem( "descr_estado", pDescrEstado );
    this.addItem( "descr_rol", pDescrRol );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_persona", "Id persona", true, true, 18 );
    this.addFixedItem( KEY, "id_evento", "Id evento", true, true, 18 );
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( FIELD, "rol", "Rol", true, false, 18 );
    this.addFixedItem( FIELD, "estado", "Estado", true, false, 50 );
    this.addFixedItem( VIRTUAL, "nombre", "Participante", true, false, 250 );
    this.addFixedItem( VIRTUAL, "descr_Estado", "Estado", true, false, 250 );
    this.addFixedItem( VIRTUAL, "descr_rol", "Rol", true, false, 250 );
  }
  
  @Override
  public void createControlProperties() throws Exception {
  	this.addControlsProperty("id_persona", createControlWin(BizUsuario.getUsr().getObjBusiness().getPersonasClass(),"persona", new JPair<String, String>("company","company") ));
  	this.addControlsProperty("rol", createControlWin(GuiEventoRoles.class,"id_rol", new JPair<String, String>("company","company") ));
  	this.addControlsProperty("estado", createControlCombo(JWins.createVirtualWinsFromMap(BizEventoParticipante.getEstados()),null, null) );
  	super.createControlProperties();
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "agd_evento_participantes"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( long zIdpersona, long zIdevento, String zCompany ) throws Exception { 
    addFilter( "id_persona",  zIdpersona ); 
    addFilter( "id_evento",  zIdevento ); 
    addFilter( "company",  zCompany ); 
    return read(); 
  } 
  
  public static final String NO_INVITADO = "NO_INVITADO";
  public static final String INVITADO = "INVITADO";
  public static final String CONFIRMADO = "CONFIRMADO";
  public static final String CANCELAR = "CANCELAR";
  public static final String NO_ASISTIRA = "NO_ASISTIRA";
  
  static JMap<String,String> estados;
  public static JMap<String,String> getEstados() throws Exception {
  	if (estados!=null) return estados;
  	JMap<String,String> maps = JCollectionFactory.createMap();
  	maps.addElement(NO_INVITADO, "Invitacion pendiente");
  	maps.addElement(INVITADO, "Invitado");
  	maps.addElement(CONFIRMADO, "Confirmó presencia");
  	maps.addElement(CANCELAR, "Canceló");
  	maps.addElement(NO_ASISTIRA, "No asistirá");
  	return estados=maps;
  }
  
  
  @Override
  public void processInsert() throws Exception {
  	if (isNullIdevento())
  		((BizEvento)this.getParent().getParent()).getObjParticipantes().getStaticItems().addElement(this);
  	else 
  		processInsertTable();
  }
  @Override
  public void processInsertTable() throws Exception {
  	super.processInsertTable();

  	BizEventoHistoria h = new BizEventoHistoria();
		h.setIdevento(getIdevento());
		h.setUsuario(BizUsuario.getUsr().GetUsuario());
		h.setEstado(getEstado());
		h.setCompany(getCompany());
		h.setTexto("Invitacion a ["+getNombreCompleto()+ "] en estado ["+getDescrEstado()+"] y rol ["+getDescrRol()+"]" );
		h.processInsert();
  }

  @Override
  public void processUpdate() throws Exception {
  	BizEventoHistoria h = new BizEventoHistoria();
		h.setIdevento(getIdevento());
		h.setUsuario(BizUsuario.getUsr().GetUsuario());
		h.setEstado(getEstado());
		h.setCompany(getCompany());
		h.setTexto("Modificacion Invitacion a ["+getNombreCompleto()+ "] en estado ["+getDescrEstado()+"] y rol ["+getDescrRol()+"]" );
		h.processInsert();
  	super.processUpdate();
  }

  @Override
  public void processDelete() throws Exception {
  	BizEventoHistoria h = new BizEventoHistoria();
		h.setIdevento(getIdevento());
		h.setUsuario(BizUsuario.getUsr().GetUsuario());
		h.setEstado(getEstado());
		h.setCompany(getCompany());
		h.setTexto("Se quito a ["+getNombreCompleto()+ "]" );
		h.processInsert();
  	super.processDelete();
  }
}
