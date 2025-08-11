package  pss.common.components;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiClase extends JWin {

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiClase() throws Exception {
  }
  

  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizClase(); }
  @Override
	public int GetNroIcono()   throws Exception { return 90; }
  @Override
	public String GetTitle()   throws Exception { return "Clase"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormClase.class; }
  @Override
	public String  getKeyField() throws Exception { return "archivo"; }
  @Override
	public String  getDescripField() { return "archivo"; }
  

  @Override
	public boolean canExpandTree() throws Exception { return false; }
  
  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    this.addActionQuery( 1, "Consultar");
  }


  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizClase GetcDato() throws Exception {
    return (BizClase) this.getRecord();
  }



}
