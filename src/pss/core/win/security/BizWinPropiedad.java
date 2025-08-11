package pss.core.win.security;

import pss.common.security.BizListOper;
import pss.common.security.BizOperacionRol;
import pss.common.security.BizRol;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.win.actions.BizAction;
import pss.core.win.actions.BizActions;


public class BizWinPropiedad extends JRecord {

  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  JString     pNombreObj  = new JString();
  JString     pClase      = new JString();
  JInteger    pNroIcono   = new JInteger();
  BizActions   oObjAcciones  = null;
  
  JRecords<BizRol> cargos;

  public BizActions GetObjAcciones() { return oObjAcciones; }
  public void SetObjAcciones(BizActions zValue) { oObjAcciones = zValue; }
  public void setClase(String zValue) {pClase.setValue(zValue);}
  public void setObjName(String zValue) {pNombreObj.setValue(zValue);}
  public void setNroIcono(int zValue) {pNroIcono.setValue(zValue);}
  public String getClase() throws Exception {return pClase.getValue();}

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizWinPropiedad() throws Exception {
    oObjAcciones = new BizActions();
  }

  @Override
	public void createProperties() throws Exception {
    this.addItem("nombre"     , pNombreObj ) ;
    this.addItem("clase"      , pClase    ) ;
    this.addItem("nro_icono"  , pNroIcono  ) ;
  }

  @Override
	public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY , "nombre"     , "Nombre"      , true, true, 100 ) ;
    this.addFixedItem( FIELD , "clase"      , "Clase"       , true, true, 100 ) ;
    this.addFixedItem( FIELD , "nro_icono"  , "Nro Icono"   , true, true, 3 ) ;
  }

  @Override
	public String GetTable() { return "";}
  
  public JRecords<BizRol> getCargos() throws Exception {
  	if (cargos!=null) return cargos;
		JRecords<BizRol> records=new JRecords<BizRol>(BizRol.class);
		records.addFilter("company", BizUsuario.getUsr().getCompany());
		records.addFilter("tipo", BizUsuario.getUsr().getObjBusiness().hasRolesAplicacion() ? BizRol.NORMAL : BizRol.JERARQUICO);
		records.readAll();
		records.toStatic();
		return (cargos=records);
  }
  

  public JRecords<BizListOper> getAccionesLista() throws Exception {
  	this.cargos=null;
  	JRecords<BizListOper> lista = new JRecords<BizListOper>(BizListOper.class);
		lista.addFilter("company", BizUsuario.getUsr().getCompany());
		lista.setStatic(true);
		this.llenarAcciones(lista, oObjAcciones);
		lista.firstRecord();
		return lista;
  }

  public void llenarAcciones(JRecords<BizListOper> lista, BizActions zAcciones) throws Exception {
     if ( zAcciones == null ) return;
     zAcciones.firstRecord();
     while ( zAcciones.nextRecord() ) {
       BizAction action = (BizAction) zAcciones.getRecord();
       if (action.GetOwner()!=null && !action.GetOwner().equals(pClase.getValue())) continue;
       BizListOper oper = new BizListOper();
       oper.createFixedProperties();
       oper.createProperties();
       oper.setCompany(BizUsuario.getUsr().getCompany());
       oper.SetOperacion(action.getIdAction());
       oper.SetDescrip(action.getOperDescrip());
       oper.setNroIcono(action.GetNroIcono());
       JIterator<BizRol> iter = this.getCargos().getStaticIterator();
       while (iter.hasMoreElements()) {
 				BizRol rol=iter.nextElement();
				oper.addItem(String.valueOf(rol.getRol()), new JBoolean());
				oper.addFixedItem(BizListOper.FIELD, String.valueOf(rol.getRol()), rol.GetDescrip(), true, true, 60);
				boolean permisoOk=false;
 				if (!oper.hasSecurity()) {
 					permisoOk=true;
 				} else {
 					BizOperacionRol opeRol=rol.getAllOperations().getElement(oper.GetOperacion());
 					permisoOk=(opeRol!=null);
 				}
				oper.getProp(String.valueOf(rol.getRol())).setValue(permisoOk);
       }
       lista.addItem(oper);
       this.llenarAcciones(lista, action.GetSubAcciones());
     }
  }


}



