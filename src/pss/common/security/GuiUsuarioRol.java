package pss.common.security;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.winUI.forms.JBaseForm;

public class GuiUsuarioRol extends JWin {

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiUsuarioRol() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizUsuarioRol(); }
  @Override
	public int GetNroIcono()       throws Exception {
  	if (this.GetVision().equals(BizUsuarioRol.VISION_ROL)) {
  		return this.GetcDato().pRol.isNotNull()?getObjRol().GetNroIcono():43;
  	} else {
  		return 45;
  	}
  }
  @Override
	public String GetTitle()       throws Exception {
  	if (this.GetVision().equals(BizUsuarioRol.VISION_ROL))
  		return "Perfiles"; 
  	else
  		return "Usuarios";
  }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormUsuarioRol.class; }
  @Override
	public String getKeyField()   throws Exception { return "rol"; }
  @Override
	public String getDescripField()                  { return "rol"; }


  @Override
	public void createActionMap() throws Exception {
    addActionQuery(1, "Consultar");
    addActionDelete (3, "Eliminar" );
  }
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (this.GetVision().equals(BizUsuarioRol.VISION_ROL)) return true;
  	return false;
  }

  public BizUsuarioRol GetcDato() throws Exception {
    return (BizUsuarioRol) this.getRecord();
  }
  
  @Override
  public JWin getRelativeWin() throws Exception {
  	if (this.GetVision().equals(BizUsuarioRol.VISION_ROL))
  		return getObjRol();
  	else
  		return getObjUsuario();
  }
  
  public GuiRol getObjRol() throws Exception {
	  GuiRol oRol = new GuiRol();
	  oRol.GetcDato().Read(GetcDato().getCompany(), GetcDato().getRol());
	  return oRol;
  }
  
  public GuiUsuario getObjUsuario() throws Exception {
	  GuiUsuario g = new GuiUsuario();
	  g.GetcDato().Read(GetcDato().getUsuario());
	  return g;
  }
  
  
}
