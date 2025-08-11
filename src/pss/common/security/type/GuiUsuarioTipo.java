package  pss.common.security.type;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiUsuarioTipo extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiUsuarioTipo() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizUsuarioTipo(); }
  @Override
	public int GetNroIcono()       throws Exception { return 1; }
  @Override
	public String GetTitle()       throws Exception { return "Tipos de usuario"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormUsuarioTipo.class; }
  @Override
	public String getKeyField()   throws Exception { return "tipo_usuario"; }
  @Override
	public String getDescripField()                  { return "descripcion"; }





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
  public BizUsuarioTipo GetcDato() throws Exception{
    return (BizUsuarioTipo) this.getRecord();
  }
  
  


}
