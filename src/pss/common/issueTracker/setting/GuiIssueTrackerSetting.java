package pss.common.issueTracker.setting;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.forms.JBaseForm;

public class GuiIssueTrackerSetting extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiIssueTrackerSetting() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizIssueTrackerSetting(); }
  @Override
	public int GetNroIcono()       throws Exception { return 10055; }
  @Override
	public String GetTitle()       throws Exception { return "Configuración"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormIssueTrackerSetting.class; }
  @Override
	public String getKeyField()   throws Exception { return "company"; }
  @Override
	public String getDescripField()                  { return "handler_user"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionQuery( 1, "Consultar" );
    addActionUpdate( 2, "Modificar" );
    addActionDelete ( 3, "Eliminar"  );
    addAction(10, "Marcar Default", null, 49, false, true).setConfirmMessage(true);	
  }

  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizIssueTrackerSetting GetcDato() throws Exception {
    return (BizIssueTrackerSetting) this.getRecord();
  }

  public JAct getSubmitFor(BizAction a) throws Exception {
  	return super.getSubmitFor(a);
  }
  
  
	public boolean OkAction(BizAction zAct) throws Exception {
		if (zAct.getId()==10) return !GetcDato().isDefault();
		return true;
	}

	@Override
	public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId()==10) return new JActSubmit(this, a.getId()) {
			public void submit() throws Exception {
				GetcDato().execDefault();
			}
		};
		return super.getSubmit(a);
	}
	
	
	
}
