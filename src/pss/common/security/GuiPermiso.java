package  pss.common.security;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiPermiso extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiPermiso() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizPermiso(); }
  @Override
	public int GetNroIcono()       throws Exception { return 1; }
  @Override
	public String GetTitle()       throws Exception { return "Permisos de Logueo"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormPermiso.class; }
  @Override
	public String getKeyField()   throws Exception { return "cod_permiso"; }
  @Override
	public String getDescripField()                  { return "tipo_permiso"; }





  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionQuery( 1, "Consultar");
    addActionUpdate( 2, "Modificar");
    addActionDelete ( 3, "Eliminar" );
  }




  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizPermiso GetcDato() throws Exception{
    return (BizPermiso) this.getRecord();
  }


}
