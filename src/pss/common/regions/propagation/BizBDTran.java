package  pss.common.regions.propagation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizBDTran extends JRecord {


  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  JString pClase = new JString();
  JString pMetodo = new JString();
  JString pData = new JString();
  JString pNodoOrigen = new JString();

  public void SetClase(String Class)   throws Exception { this.pClase.setValue(Class);  }
  public void SetMetodo(String Method) throws Exception { this.pMetodo.setValue(Method); }
  public void SetData(String Data)     throws Exception { this.pData.setValue(Data); }
  public void setOrigen(String zValue) throws Exception { this.pNodoOrigen.setValue(zValue); }

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizBDTran() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    addItem( "clase" , pClase );
    addItem( "metodo"  , pMetodo );
    addItem( "data"  , pData );
    addItem( "mje_origen"  , pNodoOrigen );
  }

  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem( FIELD, "clase" , "Clase"   , true, true, 40 );
    addFixedItem( FIELD, "metodo"  , "Metodo"    , true, true, 40 );
    addFixedItem( FIELD, "data"  , "Data"    , true, true, 1000 );
    addFixedItem( FIELD, "mje_origen"  , "Nodo Origen"    , true, true, 10 );
  }

  @Override
	public String GetTable() { return ""; }


  @Override
	public String TipoMje() {
    return "BDTRAN";
  }

  @Override
	public void processInsert() throws Exception {
  try {
    JRecord oBD = (JRecord) Class.forName(pClase.getValue()).newInstance();
    oBD.unSerialize(pData.getValue());
    Method oMethod = oBD.getClass().getMethod(pMetodo.getValue(), (Class<?>[])null);
    oMethod.invoke(oBD, (Object[])null);

    /*---------------------------------*/
    /* Propagacion del Mensaje         */
   /*---------------------------------*/
    BizPropagarMje oPropagarMje = new BizPropagarMje();
    oPropagarMje.SetOrigen(pNodoOrigen.getValue());
    oPropagarMje.SetObjBD(oBD);
    oPropagarMje.SetMetodo(pMetodo.getValue());
    oPropagarMje.processInsert();


  } catch ( InvocationTargetException ex) {
    throw (Exception) ex.getTargetException();
  }
  }

  public boolean propagateChildren() {return true;}
  public boolean propagateParent() {return true;}
  public boolean propagateMaster() {return true;}

}
