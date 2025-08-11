package pss.bsp.bspBusiness;

import pss.common.security.BizUsuario;
import pss.common.security.GuiRoles;
import pss.common.security.type.GuiUsuarioTipos;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormBSPUsuario extends JBaseForm {

	public GuiBSPUsuario getWin() {
		return (GuiBSPUsuario) getBaseWin();
	}
	@Override
	public void InicializarPanel(JWin zBase) throws Exception {
		JFormPanelResponsive ppal = AddItemRow();
		SetExitOnOk(true);
		ppal.setLabelLeft();
		ppal.AddItemEdit(null, CHAR, OPT, "pais").SetValorDefault(BizBSPCompany.getObjBSPCompany(getWin().GetcDato().getCompany()).getPais()).setHide(true);
		ppal.AddItemEdit(null, CHAR, OPT, "birth_country").SetValorDefault(BizBSPCompany.getObjBSPCompany(getWin().GetcDato().getCompany()).getPais()).setHide(true);
			
		JFormPanelResponsive row = ppal.AddItemRow();
		row.AddItemEdit("Usuario", CHAR, REQ, "usuario_pre").setSizeLabels(2).setSizeColumns(3);
		row.AddItemEdit(null, CHAR, OPT, "usuario").setHide(true);
		row.AddItemLabel("."+getWin().GetcDato().getCompany().toUpperCase()).setSizeColumns(1);
		ppal.AddItemRow().AddItemEdit("Descripción", CHAR, REQ, "descripcion").setSizeLabels(2).setSizeColumns(4);
		ppal.AddItemCheck(null, CHAR, OPT, "activo", "S", "N").SetValorDefault("S").setHide(true);
		ppal.AddItemCombo(null, CHAR, OPT, "tipo_usuario", new GuiUsuarioTipos()).setHide(true);
		ppal.AddItemCheck(null, CHAR, OPT, "is_system_user", "S", "N").SetValorDefault("N").setHide(true);
		ppal.AddItemEdit(null, CHAR, OPT, "company").SetValorDefault(BizUsuario.getUsr().getCompany()).setHide(true);
		ppal.AddItemEdit(null, CHAR, OPT, "nodo").setHide(true);
		ppal.AddItemEdit(null, CHAR, OPT, "oldusuario_rol").setHide(true);
//		ppal.AddItemCombo("Rol", CHAR, OPT, "usuario_rol", new JControlCombo() {
//			@Override
//			public JWins getRecords(boolean one) throws Exception {
//				GuiRoles roles = new GuiRoles();
//				roles.getRecords().addFilter("company", getWin().GetcDato().getCompany());
//				roles.getRecords().addFilter("tipo", "N");
//				return roles;
//			}
//		}).setFirstOcur(true).setSizeColumns(3);
//		
		JFormTabPanelResponsive tabs = AddItemTabPanel();
		tabs.AddItemList(7);
	}
}
