package  pss.common.security;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;

public class GuiOperacionRol extends JWin {

	//-------------------------------------------------------------------------//
	// Constructor de la Clase
	//-------------------------------------------------------------------------//
	public GuiOperacionRol() throws Exception {
	}

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizOperacionRol(); }
  @Override
	public String GetTitle()       throws Exception { return "Rol"; }
//  @Override
//	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormOperacionRol.class; }
  @Override
	public String getDescripField()                  { return "descr_oper"; }
  @Override
	public int GetNroIcono() throws Exception {
    if (GetVision().equals(BizOperacionRol.VISION_ROL))
      return this.getObjRol().GetNroIcono();
    else
      return this.getObjOperacion().GetNroIcono();
  }

	public BizOperacionRol GetcDato() throws Exception {
		return (BizOperacionRol)getRecord();
	}

	@Override
	public void createActionMap() throws Exception {
		addActionQuery(1, "Consultar");
		addActionDelete(3, "Eliminar");
//		addAction(10, "Pedir Clave Supervisor", null, 46, true, true); 
//		addAction(12, "Eliminar Clave Supervisor", null, 51, true, true); 
	}
	
	@Override
	public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId()==1) return new JActQuery(this.getVisionWin());
//		if (a.getId()==10) return new JActSubmit(this, a.getId()) {
//			public void submit() throws Exception {
//				GetcDato().execPermitirClaveSuperv();
//			}
//		};
//		if (a.getId()==12) return new JActSubmit(this, a.getId()) {
//			public void submit() throws Exception {
//				GetcDato().execEliminarClaveSuperv();
//			}
//		};
		return super.getSubmit(a);
	}

	@Override
	public boolean OkAction(BizAction zAct) throws Exception {
//		if (zAct.getId() == 10) return !this.GetcDato().ifClaveSupervisor();
//		if (zAct.getId() == 12) return this.GetcDato().ifClaveSupervisor();
/*    if (zAct.GetId() == 9) {
      return this.GetcDato().isTemporaryLoginRequired();
    }
    if (zAct.GetId() == 10) {
      return !this.GetcDato().isTemporaryLoginRequired();
    }
    if (zAct.GetId() == 11) {
      return this.GetcDato().isTemporaryLoginPasswordRequired();
    }
    if (zAct.GetId() == 12) {
      return !this.GetcDato().isTemporaryLoginPasswordRequired();
    }*/
		return true;
	}

	public JWin getVisionWin() throws Exception {
		if (GetVision().equals("Rol")) {
			return this.getObjRol();
		} 
		return this.getObjOperacion();
	}

//	public void WebDetalle(JPssSession session, JSAXWrapper wrapper, JPssRequest request) throws Exception {
//		if (GetVision().equals("Rol")) {
//			GuiRol oRol = new GuiRol();
//			oRol.GetcDato().Read(GetcDato().pRol.getValue());
//			oRol.WebDetalle(session, wrapper, request);
//		} else {
//			GuiOperacion oOperacion = new GuiOperacion();
//			oOperacion.GetcDato().Read(GetcDato().pOperacion.getValue());
//			oOperacion.WebDetalle(session, wrapper, request);
//		}
//	}

//	@Override
//	public JBaseForm showNewForm(boolean zShow) throws Exception {
//		JBaseForm oForm = super.showNewForm(zShow);
//		oForm.SetExitOnOk(true);
//		return oForm;
//	}


	public GuiRol getObjRol() throws Exception {
		GuiRol record = new GuiRol();
		record.GetcDato().Read(GetcDato().pCompany.getValue(), GetcDato().pRol.getValue());
		return record;
	}

	public GuiOperacion getObjOperacion() throws Exception {
		GuiOperacion record = new GuiOperacion();
		record.GetcDato().Read(GetcDato().pCompany.getValue(), GetcDato().pOperacion.getValue());
		return record;
	}

	//	public void FormPermitirClaveSuperv() throws Exception {
//    if (!confirm()) return;
// 		JExec oExec = new JExec(GetcDato(), "PermitirClaveSupervisor") {
//			@Override
//			public void Do() throws Exception {
//				GetcDato().PermitirClaveSupervisor();
//			}
//		};
//		oExec.execute();
//	}
//
//	public void FormEliminarClaveSuperv() throws Exception {
//		if (!confirm()) return;
//		JExec oExec = new JExec(GetcDato(), "PermitirEliminarClaveSupervisor") {
//			@Override
//			public void Do() throws Exception {
//				GetcDato().EliminarClaveSupervisor();
//			}
//		};
//		oExec.execute();
//	}
/*
  protected void formAllowTemporaryLoginPassword() throws Exception{
    if (!confirm()) return;
    JExec oExec = new JExec(GetcDato(), "allowTemporaryLogin") {
      public void Do() throws Exception {
        GetcDato().allowTemporaryLoginPassword();
      }
    };
    oExec.Procesar();
  }

  protected void formDeleteTemporaryLoginPassword() throws Exception{
    if (!confirm()) return;
    JExec oExec = new JExec(GetcDato(), "deleteTemporaryLoginPassword") {
      public void Do() throws Exception {
        GetcDato().deleteTemporaryLoginPassword();
      }
    };
    oExec.Procesar();
  }

  protected void formAllowTemporaryLogin() throws Exception{
    if (!confirm()) return;
    JExec oExec = new JExec(GetcDato(), "allowTemporaryLogin") {
      public void Do() throws Exception {
        GetcDato().allowTemporaryLogin();
      }
    };
    oExec.Procesar();
  }

  protected void formDeleteTemporaryLogin() throws Exception{
    if (!confirm()) return;
    JExec oExec = new JExec(GetcDato(), "deleteTemporaryLogin") {
      public void Do() throws Exception {
        GetcDato().deleteTemporaryLogin();
      }
    };
    oExec.Procesar();
  }
*/
//  private boolean confirm() {
//    return UITools.showConfirmation("Confirmación", "¿Esta Ud. seguro?");
//  }
}
