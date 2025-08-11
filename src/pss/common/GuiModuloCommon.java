package pss.common;

import pss.common.moduleConfigMngr.GuiModulesConfigMngr;
import pss.common.regions.company.GuiCompanies;
import pss.common.regions.divitions.GuiPaises;
import pss.common.remotedesktop.GuiRemoteDesktop;
import pss.common.security.BizUsuario;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActQueryActive;
import pss.core.win.submits.JActWins;

public class GuiModuloCommon extends GuiModulo {

	public GuiModuloCommon() throws Exception {
		super();
		SetModuleName("Configuración");
		SetNroIcono(10035);
		GetcDato().pOrden.setValue(10);
	}

	@Override
	public BizAction createDynamicAction() throws Exception {
		return this.addAction(1, "Configuración", null, 10035, true, true, true, "Group");
	}

	@Override
	public void createActionMap() throws Exception {
		addAction(12, "Paises", null, 1, true, false, true, "Group");
		addAction(10, "Empresas", null, 1102, true, false, true, "Group");
		addAction(20, "Configuración Módulos", null, 1, true, false, true, "Group");
		addAction(30, "Remote desktop", null, 1102, true, false, true, "Group");
		this.loadDynamicActions(null);
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 1)
			return new JActQuery(this);
		if (a.getId() == 10)
			return new JActWins(new GuiCompanies());
		if (a.getId() == 12)
			return new JActWins(new GuiPaises());
		if (a.getId() == 20)
			return new JActWins(getModulesConfigMngr());
		if (a.getId() == 30)
			return new JActQueryActive(getObjRemoteDesktop());
		return null;
	}

	GuiRemoteDesktop objRemoteDesktop;
	public GuiRemoteDesktop getObjRemoteDesktop() throws Exception{
		if (objRemoteDesktop!=null) return objRemoteDesktop;
		return objRemoteDesktop = new GuiRemoteDesktop();
	}
	protected GuiModulesConfigMngr getModulesConfigMngr() throws Exception {
		GuiModulesConfigMngr oMCM = new GuiModulesConfigMngr();
		if (BizUsuario.getUsr().getCompany() != null && BizUsuario.getUsr().getCompany().isEmpty() == false
				&& BizUsuario.getUsr().getCompany().equalsIgnoreCase("DEFAULT") == false) {
			oMCM.GetBaseDato().addFilter("company", BizUsuario.getUsr().getCompany());
		}
		if (BizUsuario.getUsr().getCountry() != null && BizUsuario.getUsr().getCountry().isEmpty() == false) {
			oMCM.GetBaseDato().addFilter("country", BizUsuario.getUsr().getCountry());
		}
		if (BizUsuario.getUsr().getNodo() != null && BizUsuario.getUsr().getNodo().isEmpty() == false) {
			oMCM.GetBaseDato().addFilter("store", BizUsuario.getUsr().getNodo());
		}

		return oMCM;
	}

}
