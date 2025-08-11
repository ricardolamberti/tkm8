package  pss.common.security;

import pss.common.security.license.license.GuiLicenses;
import pss.common.security.license.typeLicense.GuiTypeLicenses;
import pss.common.security.tracingUser.GuiTracingUsers;
import pss.common.security.type.GuiUsuarioTipos;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;

public class GuiModuloSeguridad extends GuiModulo {

	public GuiModuloSeguridad() throws Exception {
		super();
		SetModuleName("Seguridad");
		SetNroIcono(26);
	}

	@Override
	public BizAction createDynamicAction() throws Exception {
		return addAction(1, "Seguridad", null, 26, true, true, true, "Group");
	}

	@Override
	public void createActionMap() throws Exception {
		this.addAction(2, "Cambio de Clave", null, 5023, true, false);
		this.addAction(3, "Usuarios", null, 45, true, true, true, "Group");
		this.addAction(5, "Roles", null, 43, true, false, true, "Group");
		this.addAction(7, "Cargos", null, 83, true, false, true, "Group");
		this.addAction(8, "Jerarquias", null, 83, true, false, true, "Group");
		this.addAction(9, "Matriz Operaciones", null, 83, true, false, true, "Group");
		this.addAction(12, "Login", null, 46, true, true);
		this.addAction(19, "Logout", null, 46, true, true);
		this.addAction(13, "Configuracion", null, 909, true, true);
		this.addAction(16, "Ident. Electronicas", null, 351, true, false,	false, "");
		this.addAction(14, "Operaciones", null, 43, true, false, false, "");
		this.addAction(20, "Log", null, 10003, true, true, true, "Group");
		this.addAction(30, "Seguridad Empresa", null, 26, true, true);
		this.addAction(40, "Tipo Usuarios", null, 45, true, true, true, "Group");
		this.addAction(50, "Tipo Licencias", null, 45, true, true, true, "Group");
		this.addAction(60, "Licencias", null, 45, true, true, true, "Group");
		this.addAction(70, "Usuarios pre-registro", null, 45, true, true, true, "Group");
			this.loadDynamicActions(null);
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 1) return new JActQuery(this);
		if (a.getId() == 2) return new JActNew(this.getNewCambioPassword(), 0);
		if (a.getId() == 3) return new JActWins(this.getUsuarios());
		if (a.getId() == 5) return new JActWins(this.getRolesNoJerarquicos());
		if (a.getId() == 7) return new JActWins(this.getCargos());
		if (a.getId() == 8) return new JActWins(this.getRolesJerarquicos());
		if (a.getId() == 9) return new JActWins(new GuiListOpers());
		if (a.getId() == 12) return new JActNew(this.getNewLogin(), 0);
		if (a.getId() == 13) return new JActNew(this.getConfigSecurity(), 0);
		if (a.getId() == 16) return new JActWins(this.getUserElectronicIds());
		if (a.getId() == 14) return new JActWins(new GuiOperaciones());
		if (a.getId() == 19) return new JActSubmit(this.getLogout(), 0);
		if (a.getId() == 20) return new JActWins(this.getLogTraces());
		if (a.getId() == 30) return new JActNew(this.getSecurityConfig(), 0);
		if (a.getId() == 40) return new JActWins(new GuiUsuarioTipos());
		if (a.getId() == 50) return new JActWins(new GuiTypeLicenses());
		if (a.getId() == 60) return new JActWins(new GuiLicenses());
		if (a.getId() == 70) return new JActWins(new GuiTracingUsers());
			return null;
	}
	
  public GuiTracingUsers getTracingUsers() throws Exception {
  	GuiTracingUsers wins = new GuiTracingUsers();
  	wins.setPreviewFlag(JWins.PREVIEW_NO);
  	return wins;
  }

	
	
  public JWin getSecurityConfig() throws Exception {
  	GuiSegConfiguracion win = new GuiSegConfiguracion();
  	win.GetcDato().dontThrowException(true);
  	win.GetcDato().Read(BizUsuario.getUsr().getCompany());
  	return win;
  }

	private JBaseWin getLogTraces() throws Exception {
		GuiLogTraces w = new GuiLogTraces()	;
		if ( BizUsuario.getUsr().getObjCompany() != null ) {
		  w.getRecords().addFilter("company", BizUsuario.getUsr().getObjCompany().getCompany() );	
		}
		w.setRefreshOnlyOnUserRequest(true);
		return w;
	}

	private JWin getNewCambioPassword() throws Exception {
	    return GuiUsuario.getUsr().getNewCambioPassword();
//    JWin oCambioPassword = BizUsuario.getUsr().getObjBusiness().buildCambioClave(BizUsuario.getCurrentUser(),BizUsuario.getCurrentUser(),BizUsuario.getUsr().getPasswordDecrypt(),null,null);
//		GuiCambioPassword oCambioPassword = new GuiCambioPassword();
//		oCambioPassword.GetcDato().addFilter("login",	BizUsuario.getCurrentUser());
//		oCambioPassword.GetcDato().addFilter("descrip",	BizUsuario.getCurrentUser());
//		oCambioPassword.GetcDato().addFilter("passactual", BizUsuario.getUsr().getPasswordDecrypt());
//		return oCambioPassword;
	}

	private GuiSegConfiguracion getConfigSecurity() throws Exception {
		GuiSegConfiguracion record = new GuiSegConfiguracion();
		record.GetcDato().dontThrowException(true);
		record.GetcDato().Read(BizUsuario.getUsr().getCompany());
		return record;
	}

	@Override
	public boolean OkAction(BizAction zAct) throws Exception {

		return true;
	}

	public JWins getCargos() throws Exception {
		GuiRoles g=new GuiRoles();
		g.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		g.getRecords().addFilter("tipo", BizRoles.JERARQUIA);
		g.SetVision(BizRoles.JERARQUIA);
		return g;
	}

	public JWins getRolesJerarquicos() throws Exception {
		GuiRoles g=new GuiRoles();
		g.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		g.getRecords().addFilter("tipo", BizRoles.JERARQUIA);
		g.SetVision(BizRoles.JERARQUIA);
		return g;
	}

	
	public JWins getUsuarios() throws Exception {
		GuiUsuarios g=new GuiUsuarios();
		g.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		g.getRecords().addOrderBy("descripcion");
		return g;
	}

	public JWins getRolesNoJerarquicos() throws Exception {
		GuiRoles g=new GuiRoles();
		g.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		g.getRecords().addFilter("tipo", BizRoles.NORMAL);
		g.SetVision(BizRoles.NORMAL);
		return g;
	}

	public GuiLoginTrace getNewLogin() throws Exception {
		GuiLoginTrace oLoginTrace = new GuiLoginTrace();
		return oLoginTrace;
	}

	public GuiLoginTrace getLogout() throws Exception {
		return GuiLoginTrace.createLoginGuest();
	}

	private JWins getUserElectronicIds() throws Exception {
		GuiUserElectronicIds oUserElecs = new GuiUserElectronicIds();
		oUserElecs.SetVision("ALL");
		return oUserElecs;
	}

}
