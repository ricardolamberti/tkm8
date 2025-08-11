package  pss.common.components;

import pss.core.services.fields.JString;
import pss.core.services.records.JBaseRecord;
import pss.core.services.records.JRecord;

public class BizClase extends JRecord {


  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  JString pComponente   = new JString();
  JString pClase        = new JString();
  JString pArchivo      = new JString();
  JString pDescrip  = new JString() {@Override
	public void preset() throws Exception {pDescrip.setValue(pArchivo.getValue());}} ;

  JRecord oInstance=null;

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizClase() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    addItem( "componente"  , pComponente );
    addItem( "archivo"     , pArchivo );
    addItem( "clase"       , pClase );
    addItem( "descripcion" , pDescrip );
  }

  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem( KEY  , "componente"  , "Componente", true, true, 100 );
    addFixedItem( KEY  , "archivo"     , "Archivo", true, true, 100 );
    addFixedItem( FIELD  , "clase"       , "Clase", true, true, 100 );
    addFixedItem( VIRTUAL, "descripcion" , "Descripcion", true, true, 100 );
  }

  @Override
	public String GetTable() {return "";}



  public JRecord ObtenerInstanciaBD() throws Exception {
    if ( oInstance != null ) return oInstance;
    if ( pClase.getValue().indexOf("Biz") == -1) return null;
    try {
      Object oInst = Class.forName(pClase.getValue()).newInstance();
      if ( oInst instanceof JRecord )
        oInstance = (JRecord)oInst;
      else
        oInstance = null;
    } catch ( Throwable e ) {
      return null;
    }
    return oInstance;
  }

  public String GetTablaAsociada() throws Exception {
    String sEmptyTableName = "";
    JBaseRecord oInst = ObtenerInstanciaBD();
    String sTable = ( oInst == null || !oInst.isTableIncludedInGeneration() ) ? sEmptyTableName : oInst.GetTable();
    return (sTable==null) ? sEmptyTableName : sTable;
  }

}
