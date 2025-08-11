package pss.common.restJason.apiUser;

import pss.common.restJason.apiUser.apiUserDetails.GuiApiUserDetails;
import pss.common.security.BizUsuario;
import pss.common.security.GuiUsuario;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiApiUser extends JWin {

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiApiUser() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizApiUser(); }
  @Override
	public int GetNroIcono()       throws Exception { return 10013; }
  @Override
	public String GetTitle()       throws Exception { return "Api User"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormApiUser.class; }
  @Override
	public String getKeyField()   throws Exception { return "usuario"; }
  @Override
	public String getDescripField()                  { return "descr_usuario"; }


  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizApiUser GetcDato() throws Exception {
    return (BizApiUser) this.getRecord();
  }

  @Override
	public void createActionMap() throws Exception {
    this.createActionQuery();
    this.createActionUpdate();
    this.createActionDelete();
    addAction(20, "Ver Usuario", null, 10074, true, true);
    this.addAction(40, "Habilitar Acceso", null, 5145, false, true).setConfirmMessage(true);		
    this.addAction(50, "Deshabilitar Acceso", null, 5136, false, true).setConfirmMessage(true);      
    this.addAction(100, "Detalles", null, 721, false, false);
  }
  
  
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if ( a.getId()==20) return new JActQuery(this.getUsuario());
	if (a.getId()==40) return new JActSubmit(this, a.getId()) {
		@Override
		public void execSubmit() throws Exception {
			GetcDato().marcarActivo(true);
		}
	};
	if (a.getId()==50) return new JActSubmit(this, a.getId()) {
		@Override
		public void execSubmit() throws Exception {
			GetcDato().marcarActivo(false);
		}
	};	 
	if ( a.getId()==100) return new JActWins(this.getApiUserDetails());
  	return null;
  }
  

	public boolean OkAction(BizAction zAct) throws Exception {
		if (zAct.getId()==40) return !GetcDato().isActive();
		if (zAct.getId()==50) return GetcDato().isActive();
		return true;
	}
  
  public GuiUsuario getUsuario() throws Exception {
  	BizUsuario user = this.GetcDato().getObjUsuario();
  	if (user==null) JExcepcion.SendError(" No tiene asociado ningún usuario para Login");
  	GuiUsuario t = new GuiUsuario();
  	t.setRecord(user);
  	return t;
  }
  
  public JWins getApiUserDetails() throws Exception {
	  GuiApiUserDetails wins = new GuiApiUserDetails();
	  wins.setRecords(this.GetcDato().getApiUBizApiUserDetailows());
	  return wins;
  }


}
