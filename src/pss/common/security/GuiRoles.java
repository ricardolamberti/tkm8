package  pss.common.security;

import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiRoles extends JWins {

	public GuiRoles() throws Exception {
		getRecords().addOrderBy("rol");
	}

	@Override
	public JRecords<? extends JRecord> ObtenerDatos() throws Exception {
		return new BizRoles();
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiRol.class;
	}

	@Override
	public int GetNroIcono() throws Exception {
		return GetVision().equals(BizRoles.NORMAL) ? 43 : 83;
	}

	@Override
	public String GetTitle() throws Exception {
		return GetVision().equals(BizRoles.JERARQUIA) ? "Roles Jerarquicos" : "Roles de Usuario";
	}

	@Override
	public String getDescripFieldValue() {
		if (GetVision().equals(BizRoles.JERARQUIA)) return "Roles Jerárquicos";
		return "Roles de Aplicación";
	}

	@Override
	public void createActionMap() throws Exception {
		addActionNew(1, "Nuevo Perfil");
	}

	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
//		zLista.AddColumnaLista("rol");
		zLista.AddColumnaLista("descripcion").setActionOnClick(1);
	}



	public static JWins getRolesJerarquicos(String company) throws Exception {
		GuiRoles oRoles=new GuiRoles();
		oRoles.SetVision(BizRoles.JERARQUIA);
		oRoles.getRecords().addFilter("company", company);
		oRoles.getRecords().addFilter("tipo", BizRoles.JERARQUIA);
		return oRoles;
	}

	public static JWins getRolesNoJerarquicos(String company) throws Exception {
		GuiRoles oRoles=new GuiRoles();
		oRoles.SetVision(BizRoles.NORMAL);
		oRoles.getRecords().addFilter("company", company);
		oRoles.getRecords().addFilter("tipo", BizRoles.NORMAL);
		return oRoles;
	}

	public static JWins getRolesPermitidos(String company) throws Exception {
		if (BizUsuario.IsAdminUser()) 
			return GuiRoles.getRolesNoJerarquicos(company);
		return GuiRoles.getRolesJerarquicos(company);
	}

}
