package pss.core.win.actions;

import javax.swing.ImageIcon;

import pss.common.security.GuiOperacionRoles;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIconGalerys;
import pss.core.winUI.icons.GuiIconos;


public class GuiAction extends JWin  {

	GuiAction nextAction;
	
  public GuiAction() throws Exception {
  }
  @Override
	public JRecord ObtenerDato() throws Exception { return new BizAction(); }
  @Override
	public Class<? extends JBaseForm> getFormBase() { return FormAction.class;  }
  @Override
	public String GetTitle() throws Exception { return "Accion"; }
  @Override
	public String getDescripField() { return "descripcion"; }
  @Override
	public String getKeyField() { return "accion"; }

  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }
  
  @Override
	public boolean acceptsSecurityAction() {
    return false;
  }
  
  public void setNextAction(GuiAction zValue) {
  	nextAction=zValue;
  }
  
  public GuiAction getNextAction() {
  	return nextAction;
  }



  @Override
	public void createActionMap() throws Exception {
    addActionQuery(1, "Consultar"  );
//    addAction(10, "Cambiar Icono", null, 50, true, true);
    addAction(13, "Restringir", null, 48, true, true);
    addAction(14, "Liberar Seguridad", null, 49, true, true).setConfirmMessage(true);
    addAction(20, "Roles",  null, 43, false, false);
    addAction(30, "Sub-Acciones",  null, 43, true, true);
  }
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
//  	if (a.getId()==10) return new JActQuery(getIcono());
  	if (a.getId()==13) return new JActSubmit(this, a.getId()) {
  		public void submit() throws Exception {
  			GetcDato().execHabilitarPermisos();
  		}
  	};
  	if (a.getId()==14) return new JActSubmit(this, a.getId()) {
  		public void submit() throws Exception {
  			GetcDato().execDeshabilitarPermisos();
  		}
  	};

  	if (a.getId()==20) return new JActWins(getRoles());
  	if (a.getId()==30) return new JActWins(getSubAcciones());
  	return super.getSubmitFor(a);
  }

  @Override
	public boolean OkAction( BizAction zAct ) throws Exception {
    if ( zAct.getId() == 1 ) return  !this.GetcDato().isPermisosHabilitados();
    if ( zAct.getId() == 13 ) return this.GetcDato().isPermisosHabilitados();
    if ( zAct.getId() == 14 ) return !this.GetcDato().isPermisosHabilitados();
    if ( zAct.getId() == 30 ) return this.GetcDato().hasSubActions();
    return true;
  }
  
//  public int GetNroIcono() throws Exception {
//    int Nro = GetcDato().pNroIcono.getValue();
//    if ( Nro == -1 ) return 47;
//    return Nro ;
//  }

//  public GuiAction AddSubAction( GuiAction zAction) throws Exception {
//    this.GetcDato().AddSubAction(zAction.GetcDato());
//    return zAction;
//  }

  public BizAction GetcDato() throws Exception {
     return (BizAction) super.getRecord();
  }
  
  public JWin getIcono() throws Exception {
  	return GuiIconos.GetGlobal().buscarIcono(this.GetcDato().GetNroIcono());
  }

//  public void FormCambioIcono() throws Exception {
//    GuiIconos.GetGlobal().buscarIcono(((BizAction)getRecord()).pNroIcono.getValue()).showQueryForm();
//  }
//
//  public void FormHabilitarPermisos() throws Exception {
//    if ( ! UITools.showConfirmation( "Confirmación", "¿Está Ud. seguro?" ) ) return;
//    JExec oExec = new JExec(GetcDato(), "HabilitarPermisos") {@Override
//		public void Do() throws Exception {((BizAction)getRecord()).HabilitarPermisos();}};
//    oExec.execute();
//  }
//
//  public void FormDesHabilitarPermisos() throws Exception {
//    if ( ! UITools.showConfirmation( "Confirmación", "¿Está Ud. seguro?" ) ) return;
//    JExec oExec = new JExec(GetcDato(), "DeshabilitarPermisos") {@Override
//		public void Do() throws Exception {(GetcDato()).DeshabilitarPermisos();}};
//    oExec.execute();
//  }


/*  public void AddArbol(String zTipo, String zFKey, JBaseWin zBaseWin) throws Exception {
    GetcDato().AddArbol( zTipo, zFKey, zBaseWin);
  }
*/
  public void AgregarSubAcciones(GuiActions oActions) throws Exception {
    oActions.PasarADatos();
    GetcDato().SetSubAcciones(oActions.GetcDatos());
  }


  public JWins getRoles() throws Exception {
    GuiOperacionRoles oOperacionRoles = new GuiOperacionRoles();
    oOperacionRoles.SetVision("Rol");
    oOperacionRoles.getRecords().addFilter("company", GetcDato().getCompany());
    oOperacionRoles.getRecords().addFilter("operacion", GetcDato().pAccion.getValue());
    return oOperacionRoles;
  }

//  public void setWebParams( boolean zEnWeb ) throws Exception {
//    GetcDato().setWebParams( zEnWeb );
//  }
//
//  public void setWebParams( boolean zEnWeb, String zTipoBindeo, String zTipoAccion, String zConfirmar, String zMensajePantallaEspera, boolean zNuevaVentana ) throws Exception {
//    GetcDato().setWebParams( zEnWeb, zTipoBindeo, zTipoAccion, zConfirmar, zMensajePantallaEspera, zNuevaVentana );
//  }
  
  @Override
	public String GetIconFile() throws Exception {
  	return GetcDato().getIconFile();
  }
  
  
	public static ImageIcon getIconImage(BizAction act) throws Exception {
		if (act.pNroIcono.isNull())
			return null;
		if (GuiIconos.GetGlobal() == null)
			return null;
	  return GuiIconGalerys.GetGlobal().getIcon(act.getIconFile());
	}  
	
	public JWins getSubAcciones() throws Exception {
		GuiActions a = new GuiActions();
		a.setRecords(this.GetcDato().GetSubAcciones());
		return a;
	}
  
}

