package  pss.bsp.bspBusiness;

import pss.bsp.consola.config.GuiBSPConfig;
import pss.common.regions.company.GuiCompany;
import pss.common.security.BizUsuario;
import pss.common.security.BizUsuarioRol;
import pss.common.security.GuiUsuario;
import pss.common.security.GuiUsuarios;
import pss.common.security.license.license.GuiLicenses;
import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActUpdate;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiBSPCompany extends GuiCompany {

	// public static final String VISION_IN_COUNTRY_FORM = "VISION_IN_COUNTRY_FORM";

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiBSPCompany() throws Exception {
	}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizBSPCompany();
	}

	@Override
	public Class<? extends JBaseForm> getFormBase() {
		return FormBSPCompany.class;
	}

	@Override
	public String GetTitle() {
			return "BSP";
	}

	@Override
	public void createActionMap() throws Exception {
		this.createActionQuery();
		this.createActionUpdate();
		this.createActionDelete();
		this.addAction(10, "Usuarios", null, 10074, true, false, true, "Group");
		this.addAction(15, "Nuevo Usuario", null, 10074, true, true).setOnlyInForm(true);
		this.addAction(40, "Datos Extra", null, 5000, true, true, true, "Group").setOnlyInForm(true);;
		this.addAction(45, "Cambiar Config", null, 5000, true, true, true, "Group").setOnlyInForm(true);;
		this.addAction(50, "Renovar Licencia", null, 5133, true, true, true, "Group").setMulti(true);;
		this.addAction(18, "Cambiar Logo", null, 5060, true, true, true, "Group" );
		this.addAction(100, "Borrar tickets", null, 5060, true, true, true, "Group" ).setConfirmMessage(true);;
		this.addAction(105, "Borr.t.especial", null, 5060, true, true, true, "Group" ).setConfirmMessage(true);;
		this.addAction(110, "Remigrar", null, 5060, true, true, true, "Group" ).setConfirmMessage(true);;
		this.addAction(120, "regenerar detalle", null, 5060, true, true, true, "Group" ).setConfirmMessage(true);;
		this.addAction(60, "Concentrador", null, 14, true, false, true, "Group");
			}

	@Override
	public boolean OkAction(BizAction zAct) throws Exception {
		return super.OkAction(zAct);
	}
	
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==10) return new JActWins(this.getCompanyUsers());
		if (a.getId()==15) return new JActNew(this.getNewUser(), 4);
		if ( a.getId()==18 ) return new JActUpdate(this.getLogo(), JWin.ACTION_UPDATE);
		if (a.getId()==40) return new JActQuery(this.getExtraData());
		if (a.getId()==45) return new JActQuery(this.getConfigData());
		if (a.getId()==50) return new JActSubmit(this){
			@Override
			public void submit() throws Exception {
				GetccDato().execRenovarLicencia();
			};
		};
		if (a.getId()==60) return new JActWins(getHijos());
		if (a.getId()==100) return new JActSubmit(this){
			@Override
			public void submit() throws Exception {
				GetccDato().execBorrarTickets();
			};
		};
		if (a.getId()==105) return new JActSubmit(this){
			@Override
			public void submit() throws Exception {
				GetccDato().execBorrarIataTickets();
			};
		};
		if (a.getId()==110) return new JActSubmit(this){
			@Override
			public void submit() throws Exception {
				GetccDato().execProcessRemigrar();
			};
		};
		if (a.getId()==120) return new JActSubmit(this){
			@Override
			public void submit() throws Exception {
				GetccDato().execProcessRegenerar();
			};
		};
		return super.getSubmitFor(a);
	}
	public JWins getHijos() throws Exception {
		GuiBSPChildCompanies lic = new GuiBSPChildCompanies();
		lic.getRecords().addFilter("company", GetcDato().getCompany());
		return lic;
	}
	
	@Override
	public String getFieldForeground(String zColName) throws Exception {
		if (zColName.equals("licencias_calc")) {
			if (GetccDato().getLicenciasCalculadas()>GetccDato().getLicencias())
				return "FF0000";
			if (GetccDato().getLicenciasCalculadas()<GetccDato().getLicencias())
				return "00CC00";
		} 
		return super.getFieldForeground(zColName);
	}
  

	public BizBSPCompany GetccDato() throws Exception {
		return (BizBSPCompany) this.getRecord();
	}
	
	public GuiCompanyExtraData getExtraData() throws Exception {
		GuiCompanyExtraData c = new GuiCompanyExtraData();
		c.setRecord(GetccDato().getObjExtraData());
		return c;
	}
	public GuiBSPConfig getConfigData() throws Exception {
		GuiBSPConfig c = new GuiBSPConfig();
		c.setRecord(BizBSPCompany.getConfigView(this.GetcDato().getCompany()));
		return c;
	}
	public GuiUsuario getNewUser() throws Exception {
		GuiUsuario newUser = new GuiUsuario();
		newUser.getRecord().addFilter("company", this.GetcDato().getCompany());
		return newUser;
	}
	public JWins getLicencias() throws Exception {
		GuiLicenses wins = new GuiLicenses();
		wins.getRecords().addFilter("company", this.GetcDato().getCompany());
		return wins;
	}

	public JWins getCompanyUsers() throws Exception {
		GuiBSPUsuarios wins = new GuiBSPUsuarios();
		wins.getRecords().addFilter("company", this.GetcDato().getCompany());
		return wins;
	}
	
	@Override
	public JAct Drop(JBaseWin zBaseWin) throws Exception {
		if (zBaseWin instanceof GuiUsuario) {
			GuiUsuario usr = (GuiUsuario)zBaseWin;
			usr.GetcDato().processInsert();
			BizUsuarioRol rol = new BizUsuarioRol();
			rol.setCompany(usr.GetcDato().getCompany());
			rol.SetUsuario(usr.GetcDato().GetUsuario());
			rol.setRol(3);
			rol.execProcessInsert();
			
		}
		return super.Drop(zBaseWin);
	}
	
}
