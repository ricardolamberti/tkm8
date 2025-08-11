package pss.common.documentos.hitos;

import java.util.Date;

import pss.common.customList.config.relation.JRelations;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class BizHito extends JRecord {
	
	public static final String CREAR = "CREAR";
	public static final String ESCANEAR = "ESCANEAR";
	public static final String ELIMINAR = "ELIMINAR";
	public static final String MODIFICAR = "MODIFICAR";

  private JLong pIdHito = new JLong();
  private JLong pIddoc = new JLong();
  private JString pUsuario = new JString();
  private JDateTime pFecha = new JDateTime();
  private JString pObservaciones = new JString();
  private JString pIdtipo_hito = new JString();
  private JString pDescrTipoHito = new JString() {
  	@Override
  	public void preset() throws Exception {
  		pDescrTipoHito.setValue(getTipoHitos().getElement(getTipoHito()));
  	}
  };


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setIddoc(long zValue) throws Exception {    pIddoc.setValue(zValue);  }
  public long getIddoc() throws Exception {     return pIddoc.getValue();  }
  public void setIdHito(long zValue) throws Exception {    pIdHito.setValue(zValue);  }
  public long getIdHito() throws Exception {     return pIdHito.getValue();  }
  public void setIdoperador(String zValue) throws Exception {    pUsuario.setValue(zValue);  }
  public String getIdoperador() throws Exception {     return pUsuario.getValue();  }
  public void setFecha(Date zValue) throws Exception {    pFecha.setValue(zValue);  }
  public Date getFecha() throws Exception {     return pFecha.getValue();  }
  public void setObservaciones(String zValue) throws Exception {    pObservaciones.setValue(zValue);  }
  public String getObservaciones() throws Exception {     return pObservaciones.getValue();  }
  public void setTipoHito(String zValue) throws Exception {    pIdtipo_hito.setValue(zValue);  }
  public String getTipoHito() throws Exception {     return pIdtipo_hito.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizHito() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id_hito", pIdHito );
    this.addItem( "id_doc", pIddoc );
    this.addItem( "usuario", pUsuario );
    this.addItem( "fecha", pFecha );
    this.addItem( "observaciones", pObservaciones );
    this.addItem( "tipo_hito", pIdtipo_hito );
    this.addItem( "descr_tipohito", pDescrTipoHito );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_hito", "Id Hito", false, false, 18 );
    this.addFixedItem( FIELD, "id_doc", "Id doc", true, true, 18 );
    this.addFixedItem( FIELD, "usuario", "Usuario", true, true, 15 );
    this.addFixedItem( FIELD, "fecha", "Fecha", true, true, 10 );
    this.addFixedItem( FIELD, "observaciones", "Observaciones", true, false, 500 );
    this.addFixedItem( FIELD, "tipo_hito", "tipo hito", true, true, 20 );
    this.addFixedItem( VIRTUAL, "descr_tipohito", "Tipo hito", true, true, 50 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "DOC_HITO"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void processInsert() throws Exception {
		this.setIdoperador(BizUsuario.getCurrentUser());
		this.setFecha(new Date());
		super.processInsert();
	}

  /**
   * Default Read() method
   */
  public boolean Read( long zIddoc, long zIdmovim ) throws Exception { 
    addFilter( "id_doc",  zIddoc ); 
    addFilter( "id_movim",  zIdmovim ); 
    return read(); 
  } 
  
	private static JMap<String,String> tipoHitos;
  public static JMap<String,String> getTipoHitos() {
	 	if (tipoHitos!=null) return tipoHitos;
	 	JMap<String,String> m = JCollectionFactory.createMap();
	 	m.addElement(CREAR,"Creación documento");
	 	m.addElement(ELIMINAR,"Eliminar documento");
	 	m.addElement(ESCANEAR,"Escanear documento");
	 	m.addElement(MODIFICAR,"Modificar documento");
	 	return (tipoHitos = m);
 }


}
