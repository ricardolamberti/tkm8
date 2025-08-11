package  pss.common.layoutWysiwyg.permisos;

import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizOwnerPlantilla extends JRecord {
  public static final String VISTA_EXPEDIENTE = "1";
  public static final String VISTA_PLANTILLA = "2";
	
  private JString pIdtipo = new JString();
  private JString pIdOwner = new JString();
  private JBoolean pModify = new JBoolean();
  private JBoolean pDelete = new JBoolean();
  
  private JString pDescripcion = new JString() {
  	public void preset() throws Exception {
  		pDescripcion.setValue(getDescripcionPlantilla());
  	};
  };
  private JString pDescripcionOwner = new JString() {
  	public void preset() throws Exception {
  		pDescripcionOwner.setValue(getNombreCompleto());
  	};
  };

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setIdtipo(String zValue) throws Exception {    pIdtipo.setValue(zValue);  }
  public String getIdtipo() throws Exception {     return pIdtipo.getValue();  }
  public void setModificable(boolean zValue) throws Exception {    pModify.setValue(zValue);  }
  public boolean isModificable() throws Exception {     return pModify.getValue();  }
  public void setBorrable(boolean zValue) throws Exception {    pDelete.setValue(zValue);  }
  public boolean isBorrable() throws Exception {     return pDelete.getValue();  }
  public void setOwner(String zValue) throws Exception {    pIdOwner.setValue(zValue);  }
  public String getOwner() throws Exception {     return pIdOwner.getValue();  }

  /**
   * Constructor de la Clase
   */
  public BizOwnerPlantilla() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id_plantilla", pIdtipo);
    this.addItem( "id_owner", pIdOwner);
    this.addItem( "modificar", pModify );
    this.addItem( "borrar", pDelete );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "nombre", pDescripcionOwner );

  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_plantilla", "Id plantilla", true, true, 50 );
    this.addFixedItem( KEY, "id_owner", "Id usuario", true, true, 50 );
    this.addFixedItem( FIELD, "modificar", "Puede modificar", true, false, 250 );
    this.addFixedItem( FIELD, "borrar", "Puede borrar", true, false, 250 );
    this.addFixedItem( VIRTUAL, "descripcion", "Descr.Plantilla", true, false, 250 );
    this.addFixedItem( VIRTUAL, "nombre", "Usuario", true, false, 250 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "LYT_PLANTILLA_PERMISOS"; }

  public void processInsert() throws Exception {
  	if (pIdOwner.isNull())
  		setOwner("TODOS");
   	super.processInsert();
  }
  public void processUpdate() throws Exception {
   	if (pIdOwner.isNull())
  		setOwner("TODOS");
   	super.processUpdate();
  };
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  BizUsuario objUsuario;
  BizPlantilla objLocal;
  
  
  public BizUsuario getObjUsuario() throws Exception {
  	if (objUsuario!=null) return objUsuario;
  	BizUsuario t = new BizUsuario();
  	t.Read(getOwner());
  	return objUsuario=t;
  } 
  public BizPlantilla getObjLocal() throws Exception {
  	if (objLocal!=null) return objLocal;
  	BizPlantilla t = new BizPlantilla();
  	t.Read(getIdtipo());
  	return objLocal=t;
  } 

 

  public String getDescripcionPlantilla() throws Exception {
  	return getObjLocal().getDescripcion();
  }
  public String getNombreCompleto() throws Exception {
  	if (getOwner().equals("TODOS")) return "TODOS";
  	return getObjUsuario().getDescrUsuario();
  }
  /**
   * Default Read() method
   */
  public boolean Read( String zIdtipo, String zOwner) throws Exception { 
    this.addFilter( "id_plantilla",  zIdtipo ); 
    this.addFilter( "id_owner",  zOwner ); 
     return this.read(); 
  } 
 }
