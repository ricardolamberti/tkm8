package pss.bsp.bspBusiness;

import pss.common.security.BizUsuario;
import pss.common.security.GuiUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActUpdate;
import pss.core.winUI.forms.JBaseForm;

public class GuiBSPUsuario extends GuiUsuario {

	public GuiBSPUsuario() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizBSPUser();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8399177387724260131L;

	@Override
	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormBSPUsuario.class;
	}

	@Override
	public JAct getSubmit(BizAction a) throws Exception {
		return super.getSubmit(a);
	}
	@Override
	public boolean OkAction(BizAction zAct) throws Exception {
		if (GetcDato().GetUsuario().startsWith("ADMIN.") && !BizUsuario.getUsr().isAnyAdminUser()) {
			return false;
		}
		if (BizUsuario.getUsr().isAdminUser())
			return super.OkAction(zAct);

		if (zAct.getId() == 1) return true;
		if (zAct.getId() == 2) return true;
		if (zAct.getId() == 3) return true;
		if (zAct.getId() == 7) return true;

		if (zAct.getId() == 14)
			return super.OkAction(zAct);
		if (zAct.getId() == 25)
			return super.OkAction(zAct);
		if (zAct.getId() == 8)
			return super.OkAction(zAct);
		;
		if (zAct.getId() == 9)
			return super.OkAction(zAct);

		return false;
	}
	
	

}
