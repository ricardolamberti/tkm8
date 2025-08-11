package  pss.common.security;

import pss.core.services.JExec;
import pss.core.services.records.JRecords;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.lists.JWinList;

public class GuiSegConfiguraciones extends JWins {

	private static final int ACTION_ID_FORBID_TO_ALL=10;
	private static final int ACTION_ID_ALLOW_TO_ALL=11;
	private static final int ACTION_ID_CONFIGURE_ALLOWED_ROLES=12;

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiSegConfiguraciones() throws Exception {
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 909;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Configuración";
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiSegConfiguracion.class;
	}

	// -------------------------------------------------------------------------//
	// Mapeo las acciones con las operaciones
	// -------------------------------------------------------------------------//
	@Override
	public void createActionMap() throws Exception {
		addActionNew(1, "Nuevo Registro");

		addAction(ACTION_ID_FORBID_TO_ALL, "Restringir configuración de permisos", new JAct() {

			@Override
			public void Do() throws Exception {
				formForbidConfigurationToAll();
			}
		}, 48, true, true, false, "");

		addAction(ACTION_ID_ALLOW_TO_ALL, "Permitir a todos configuración de permisos", new JAct() {

			@Override
			public void Do() throws Exception {
				formAllowConfigurationToAll();
			}
		}, 49, true, true, false, "");

		addAction(ACTION_ID_CONFIGURE_ALLOWED_ROLES, "Roles para configuración de permisos", new JAct() {

			// public void Do() throws Exception { GetWins().CrearFormLista(); }
			@Override
			public JBaseWin GetBWin() throws Exception {
				return getConfigurationOperationRoles();
			}
		}, 43, true, true, false, "");

	}

	// -------------------------------------------------------------------------//
	// Configuro las columnas que quiero mostrar en la grilla
	// -------------------------------------------------------------------------//
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("desc_company");
		zLista.AddColumnaLista("long_max_user");
		zLista.AddColumnaLista("long_min_user");
		zLista.AddColumnaLista("user_idle_time");
		zLista.AddColumnaLista("long_min_password");
		zLista.AddColumnaLista("long_max_password");
	}

	/*
	 * public static JWins ObtenerPaisesInexistentes( String sModo, String sPais ) throws Exception { if ( sModo == JBaseForm.MODO_CONSULTA || sModo == JBaseForm.MODO_MODIFICACION ) { return GetWinPais(sPais); } GuiPaises oPaises = new GuiPaises(); JRecords oSegConfiguraciones = new JRecords(BizSegConfiguracion.class); oSegConfiguraciones.SetNoExcSelect(true); if ( !oSegConfiguraciones.readAll() ) { return oPaises; }
	 *  // oSegConfiguraciones.PasarAEstatico(); // oSegConfiguraciones.FirstRecord(); while ( oSegConfiguraciones.nextRecord() ) { BizSegConfiguracion oConfig = (BizSegConfiguracion)oSegConfiguraciones.getRecord(); oPaises.GetBaseDato().addFilter("pais",oConfig.GetPais(),"<>"); } oPaises.readAll(); oSegConfiguraciones = null; return oPaises; }// end method
	 */
	/*
	 * public static JWins GetWinPais(String sPais) throws Exception { GuiPaises oPaises = new GuiPaises(); oPaises.GetBaseDato().addFilter("pais",sPais); oPaises.readAll(); return oPaises; }
	 */

	// public boolean ifAcceptRefresh(JWinEvent e, boolean zGlobal) throws Exception {
	// if (e.GetArgs().GetBaseDato() instanceof BizOperacion &&
	// ((BizOperacion)e.GetArgs().GetBaseDato()).GetOperacion().equals(BizOperacion.getConfigureSecurityOperation().GetOperacion())) {
	// return true;
	// }
	// return super.ifAcceptRefresh(e, zGlobal);
	// }

	private JWin getConfigurationOperationRoles() throws Exception {
		// JEnlace oEnlace = new JEnlace();
		// oEnlace.AddValor("accion", "operacion", BizOperacion.getConfigureSecurityOperation().GetOperacion());
		// GuiOperacionRoles oOperacionRoles = new GuiOperacionRoles();
		// oOperacionRoles.SetVision("Rol");
		// oOperacionRoles.SetEnlazado(oEnlace);
		// return oOperacionRoles;
		GuiOperacion oOper=new GuiOperacion();
		oOper.setRecord(BizOperacion.getConfigureSecurityOperation());
		return oOper;
	}

	private void formForbidConfigurationToAll() throws Exception {
//		if (!UITools.showConfirmation("Confirmación", "¿Está Ud. seguro?")) return;
		JExec oExec=new JExec(BizOperacion.getConfigureSecurityOperation(), "forbidConfigurationToAll") {

			@Override
			public void Do() throws Exception {
				forbidConfigurationToAll();
			}
		};
		oExec.execute();
	}

	public void formAllowConfigurationToAll() throws Exception {
//		if (!UITools.showConfirmation("Confirmación", "¿Está Ud. seguro?")) return;
		JExec oExec=new JExec(BizOperacion.getConfigureSecurityOperation(), "allowConfigurationToAll") {

			@Override
			public void Do() throws Exception {
				allowConfigurationToAll();
			}
		};
		oExec.execute();
	}

	private void forbidConfigurationToAll() throws Exception {
		BizOperacion oOperation=new BizOperacion();
		oOperation.SetOperacion(BizOperacion.getConfigureSecurityOperation().GetOperacion());
		oOperation.SetDescrip(BizOperacion.getConfigureSecurityOperation().GetDescrip());
		oOperation.processInsert();
		oOperation.notifyUpdateOK();
	}

	private void allowConfigurationToAll() throws Exception {
		BizOperacion oOperation=new BizOperacion();
		oOperation.SetOperacion(BizOperacion.getConfigureSecurityOperation().GetOperacion());
		oOperation.SetDescrip(BizOperacion.getConfigureSecurityOperation().GetDescrip());
		JRecords<BizOperacionRol> oOpeRoles=new JRecords<BizOperacionRol>(BizOperacionRol.class);
		oOpeRoles.addFilter("operacion", oOperation.GetOperacion());
		oOpeRoles.readAll();
		while (oOpeRoles.nextRecord()) {
			BizOperacionRol oOpeRol=oOpeRoles.getRecord();
			oOpeRol.processDelete();
		}
		oOperation.processDelete();
		oOperation.notifyUpdateOK();
	}

//	@Override
//	public boolean ifAcceptRefresh(JWinEvent e, boolean zGlobal) throws Exception {
//		if (e.ifRefreshModifOk()&&e.GetArgs().GetBaseDato() instanceof BizOperacion&&((BizOperacion) e.GetArgs().GetBaseDato()).GetOperacion().equals(BizOperacion.getConfigureSecurityOperation().GetOperacion())) {
//			return true;
//		}
//		return super.ifAcceptRefresh(e, zGlobal);
//	}

	@Override
	public boolean OkAction(BizAction zAct) throws Exception {
		int iAct=zAct.getId();
		if (iAct==ACTION_ID_FORBID_TO_ALL) {
			return !BizOperacion.getConfigureSecurityOperation().checkRecordExists();
		} else if (iAct==ACTION_ID_ALLOW_TO_ALL||iAct==ACTION_ID_CONFIGURE_ALLOWED_ROLES) {
			return BizOperacion.getConfigureSecurityOperation().checkRecordExists();
		}
		return true;
	}

}
