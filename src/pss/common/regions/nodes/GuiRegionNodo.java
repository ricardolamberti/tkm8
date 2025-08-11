package pss.common.regions.nodes;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiRegionNodo extends JWin {


  GuiNodo oNodo = null;

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiRegionNodo() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizRegionNodo(); }
  @Override
	public int GetNroIcono()       throws Exception { return 90; }
  @Override
	public String GetTitle()       throws Exception { return "Región Nodo"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormRegionNodo.class; }
  @Override
	public String getKeyField()   throws Exception { return "region"; }
  @Override
	public String getDescripField()                  { return "descr_nodo"; }



  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionQuery( 1, "Consultar") ;
    addActionDelete ( 3, "Eliminar" ) ;
  }




  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizRegionNodo GetcDato() throws Exception {
    return (BizRegionNodo) this.getRecord();
  }

  public GuiNodo ObtenerNodo() throws Exception {
    if ( oNodo != null ) return oNodo;
    oNodo = new GuiNodo();
    oNodo.GetcDato().Read( GetcDato().pCompany.getValue(), GetcDato().pNodo.getValue());
    return oNodo;
  }

  @Override
	public JWin getRelativeWin() throws Exception {
    return ObtenerNodo();
  }

  @Override
	public void showQueryForm() throws Exception {
    ObtenerNodo().showQueryForm();
  }

}
