package  pss.common.regions.nodes;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiNodoUsuario extends JWin {


  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiNodoUsuario() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizNodoUsuario(); }
  @Override
	public int GetNroIcono()       throws Exception { return 77; }
  @Override
	public String GetTitle()       throws Exception { return "Sucursal"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormNodoUsuario.class; }
  @Override
	public String getKeyField()   throws Exception { return "nodo"; }
  @Override
	public String getDescripField()                  { return "descr_nodo"; }

  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    //SetAccionConsultar( 1, JLanguage.Translate("Consultar"));
    //Esta accion no debe estar (DEF500040)
    //SetAccionModificar( 2, JLanguage.Translate("Modificar") );
    addActionDelete ( 3, "Eliminar" );
  }

  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizNodoUsuario GetcDato() throws Exception {
    return (BizNodoUsuario) this.getRecord();
  }

//  public void WebDetalle( JPssSession session, JSAXWrapper wrapper, JPssRequest request ) throws Exception {
//    GuiUsuario usuario = new GuiUsuario();
//    usuario.WebBind( session, null );
//    usuario.GetcDato().Read( GetcDato().pUsuario.getValue() );
//    usuario.WebDetalle( session, wrapper, request );
//  }


}
