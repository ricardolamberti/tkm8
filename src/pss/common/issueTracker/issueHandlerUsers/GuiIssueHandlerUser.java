package pss.common.issueTracker.issueHandlerUsers;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.forms.JBaseForm;

public class GuiIssueHandlerUser extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiIssueHandlerUser() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizIssueHandlerUser(); }
  @Override
	public int GetNroIcono()       throws Exception { return 15; }
  @Override
	public String GetTitle()       throws Exception { return "Usuario"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormIssueHandlerUser.class; }
  @Override
	public String getKeyField()   throws Exception { return "usuario"; }
  @Override
	public String getDescripField()                  { return "usuario"; }


 

  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizIssueHandlerUser GetcDato() throws Exception {
    return (BizIssueHandlerUser) this.getRecord();
  }

  
  @Override
	public void createActionMap() throws Exception {
    addActionQuery( 1, "Consultar" );
    addActionUpdate(2, "Modificar");
		addAction(10, "Activar", null, 49, false, true).setConfirmMessage(true);		
		addAction(20, "DesActivar", null, 5136, false, true).setConfirmMessage(true);    
  }
  
	public boolean OkAction(BizAction zAct) throws Exception {
		if (zAct.getId()==10) return !GetcDato().isActive();
		if (zAct.getId()==20) return GetcDato().isActive();
		return true;
	}
	
	@Override
	public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId()==10) return new JActSubmit(this, a.getId()) {
			public void submit() throws Exception {
				GetcDato().activar();
			}
		};
		if (a.getId()==20) return new JActSubmit(this, a.getId()) {
			public void submit() throws Exception {
				GetcDato().desactivar();
			}
		};
		return super.getSubmit(a);
	}
	
	
}
