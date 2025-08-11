package pss.common.customDashboard2;

import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.tools.PssLogger;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiDashBoardConfig extends JWin {

	/**
	 * Constructor de la Clase
	 */
	public GuiDashBoardConfig() throws Exception {
	}

	public JRecord ObtenerDato() throws Exception {
		return new BizDashBoardConfig();
	}

	// public int GetNroIcono() throws Exception { return 92; }
	public String GetTitle() throws Exception {
		return "Config Dashboard";
	}

	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormDashBoardConfig.class;
	}

	public String getKeyField() throws Exception {
		return "company";
	}

	public String getDescripField() {
		return "company";
	}

	public BizDashBoardConfig GetcDato() throws Exception {
		return (BizDashBoardConfig) this.getRecord();
	}

	public int GetNroIcono() throws Exception {
		if (this.GetcDato().getExcluded())
			return 800;
		else
			return 801;
	}

	@Override
	public boolean OkAction(BizAction zAct) throws Exception {

		switch (zAct.getId()) {
		case 35:
			return this.GetcDato().getExcluded() == false;
		case 36:
			return this.GetcDato().getExcluded();
		case 45:
			return this.GetcDato().isOnlyAdmin() == false && BizUsuario.IsAdminCompanyUser();
		case 46:
			return this.GetcDato().isOnlyAdmin() && BizUsuario.IsAdminCompanyUser();
		}

		return super.OkAction(zAct);

	}

	@Override
	public void createActionMap() throws Exception {
		BizAction a = this.addAction(35, "Excluir", null, 48, true, true);
		a.setMulti(true);
		BizAction b = this.addAction(36, "Incluir", null, 3, true, true);
		b.setMulti(true);
		BizAction c = this.addAction(45, "Solo Admin", null, 3, true, true);
		c.setMulti(true);
		BizAction d = this.addAction(46, "Sacar Admin", null, 48, true, true);
		d.setMulti(true);

		// this.addAction(40, "Subir", null, 15056, true,
		// true).setPostFunction("goUp(\"provider_xpss.ho.dashboard.GuiHOConsola_400\");");
		// this.addAction(50, "Bajar", null, 15057, true,
		// true).setPostFunction("goDown(\"provider_xpss.ho.dashboard.GuiHOConsola_400\");");

	}

	protected void addConfigureButton() throws Exception {
		this.addAction(400, "Configurar Dashboard", null, 10023, false, true);
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 400)
			return new JActWins(getDashboardConfig());

		if (a.getId() == 35)
			return new JActSubmit(this, 35) {
				public void submit() throws Exception {
					GetcDato().execProcessExclude();
				}
			};
		if (a.getId() == 36)
			return new JActSubmit(this, 36) {
				public void submit() throws Exception {
					GetcDato().execProcessInclude();
				}
			};

		if (a.getId() == 45)
			return new JActSubmit(this, 35) {
				public void submit() throws Exception {
					GetcDato().execProcessSetAdmin();
				}
			};
		if (a.getId() == 46)
			return new JActSubmit(this, 36) {
				public void submit() throws Exception {
					GetcDato().execProcessUnsetAdmin();
				}
			};

		if (a.getId() == 40)
			return new JActSubmit(this, 40) {
				public void submit() throws Exception {
					GetcDato().execProcessUp();
				}
			};
		if (a.getId() == 50)
			return new JActSubmit(this, 50) {
				public void submit() throws Exception {
					GetcDato().execProcessDown();
				}
			};

		return null;
	}

	public boolean acceptDrop(String zone) throws Exception {
		return true;
	}

	@Override
	public JBaseWin getObjectDrageable(String zone) throws Exception {

		return this;
	}

	@Override
	public JAct Drop(JBaseWin zBaseWin) throws Exception {
		if (zBaseWin instanceof GuiDashBoardConfig) {
			GuiDashBoardConfig eje = (GuiDashBoardConfig) zBaseWin;
			PssLogger.logDebug("Pos 1:" + eje.GetcDato().getDashOrder() + ", " + eje.GetcDato().getDashDescription());
			PssLogger.logDebug("Pos 2:" + this.GetcDato().getDashOrder() + ", " + this.GetcDato().getDashDescription());
			boolean up = true;
			if (eje.GetcDato().getDashOrder() > this.GetcDato().getDashOrder())
				up = true;
			else
				up = false;
			eje.GetcDato().execProcessReOrder(this.GetcDato(), up);
		}
		return null;
	}

	protected JWins getDashboardConfig() throws Exception {

		BizDashBoardConfig cfg = new BizDashBoardConfig();
		cfg.initConfig();

		GuiDashBoardConfigs dbc = new GuiDashBoardConfigs();
		dbc.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		dbc.getRecords().addFilter("userid", BizUsuario.getUsr().GetUsuario());
		dbc.getRecords().addFilter("dash_name", this.GetcDato().getDashBoardName());
		dbc.getRecords().addOrderBy("dash_order");

		return dbc;
	}

	public int GetDobleClick() throws Exception {
		if (this.GetcDato().getExcluded())
			return 36;
		else
			return 35;
	}

}
