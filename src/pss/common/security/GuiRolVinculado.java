package  pss.common.security;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;

public class GuiRolVinculado extends JWin {
  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  GuiRol oRolVinc = null;

  public GuiRolVinculado() throws Exception {
  }

  @Override
	public JRecord ObtenerDato() throws Exception { return new BizRolVinculado(); }
  @Override
	public String GetTitle() throws Exception { return "Rol Vinculado"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormRolVinculado.class; }
  @Override
	public String getKeyField() throws Exception { return "rol_vinculado"; }
  @Override
	public String getDescripField() { return "descr_rolvinc"; }
  @Override
	public int GetNroIcono() throws Exception {
  	return 43;
    //return ObtenerRolVinculado().GetNroIcono();
  }


  @Override
	public void createActionMap() throws Exception {
    addActionQuery (1, "Consultar");
    addActionDelete  (3, "Eliminar Vinculación" );
//    addAction(10, "Roles Vinculados", null, 1, false, false, true, "Detail" );
  }
  
  public JAct getSubmitFor(BizAction a) throws Exception {
//  	if (a.getId()==10) return new JActWins(this.getRolesVinc());
  	return this.getSubmitFor(a);
  }
  
	public JWin getRelativeWin() throws Exception {
		return this.getObjRolVinculado();
	}


  //-------------------------------------------------------------------------//
  // Devuelve el Dato casteado
  //-------------------------------------------------------------------------//
  public BizRolVinculado GetcDato() throws Exception {
    return (BizRolVinculado) this.getRecord();
  }

  //-------------------------------------------------------------------------//
  // Representacion del win
  //-------------------------------------------------------------------------//
/*  public JWin GetWinRepresentado() throws Exception {
    if ( pRol == null ) {
      pRol = new GuiRol();
      pRol.GetcDato().Read(this.GetcDato().pRolVinculado.GetValor());
    }
    return pRol;
  }
  */

	public GuiRol getObjRolVinculado() throws Exception {
    GuiRol oRol = new GuiRol();
    oRol.GetcDato().Read(GetcDato().getCompany(), GetcDato().getRolVinculado());
    return oRol;
  }

//  public void WebDetalle( JPssSession session, JSAXWrapper wrapper, JPssRequest request ) throws Exception {
//    GuiRol rol = new GuiRol();
//    rol.WebBind( session, null );
//    rol.GetcDato().Read( GetcDato().pRolVinculado.getValue() );
//    rol.WebDetalle( session, wrapper, request );
//  }


//  public GuiRol ObtenerRolVinculado() throws Exception {
//    if ( oRolVinc != null ) return oRolVinc;
//    oRolVinc = new GuiRol();
//    oRolVinc.GetcDato().Read(GetcDato().pRolVinculado.getValue()) ;
//    return oRolVinc;
//  }

  public JWins getRolesVinc() throws Exception {
    GuiRolesVinculados oVincs = new GuiRolesVinculados();
    oVincs.getRecords().addFilter("rol", GetcDato().getRolVinculado());
    return oVincs;
  }

  public JWins getRolesPosibles() throws Exception {
    String sTipo = GetcDato().getObjRol().GetTipo();
    GuiRoles oRoles = new GuiRoles();
    if ( sTipo.equals("N") ) oRoles.SetVision("Normal");
    return oRoles;

  }


}
