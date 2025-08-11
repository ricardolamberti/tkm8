package  pss.common.security.license.typeLicense;

import pss.common.customMenu.GuiMenus;
import pss.common.regions.company.GuiCompanies;
import pss.common.security.BizRoles;
import pss.common.security.BizUsuario;
import pss.common.security.GuiRoles;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;

public class FormTypeLicense extends JBaseForm {

	private static final long serialVersionUID=1246199622406L;



	/**
	 * Constructor de la Clase
	 */
	public FormTypeLicense() throws Exception {
	}

	public GuiTypeLicense getWin() {
		return (GuiTypeLicense) getBaseWin();
	}

	/**
	 * Linkeo los campos con la variables del form
	 */
	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemCombo("Empresa", CHAR, REQ, "company",new GuiCompanies()).SetValorDefault(BizUsuario.getUsr().getCompany());
		AddItemEdit("Tipo licencia", CHAR, REQ, "id_typeLicense");
		AddItemEdit("Description", CHAR, REQ, "description");
		AddItemCombo("Id custom menu", CHAR, OPT, "id_custom_menu", getCustomMenu());
		AddItemCombo("Rol", UINT, OPT, "rol", new JControlCombo() {

			public JWins getRecords(boolean one) throws Exception {
				return getRoles(one);
			}
		});
		AddItemCombo("Clases", CHAR, REQ, "id_clase", getClases());
		AddItemCheck("Upgradeable", CHAR, "is_upgradeable");
		AddItemEdit("Precio", FLOAT, OPT, "monto");
		autoBuildTabs(getInternalPanel(),zWin);
	}

	private JWins getCustomMenu() throws Exception {
		return new GuiMenus();
	}

	public JWins getRoles(boolean one) throws Exception {
		GuiRoles guis=new GuiRoles();
		if (one) {
			guis.getRecords().addFilter("company", this.getWin().GetcDato().getCompany());
			guis.getRecords().addFilter("rol", this.getWin().GetcDato().getRol());
			guis.getRecords().addFilter("tipo", BizRoles.JERARQUIA);
			guis.SetVision(BizRoles.JERARQUIA);
		} else {
			guis.getRecords().addFilter("company", this.findControl("company").getValue());
			guis.getRecords().addFilter("tipo", BizRoles.JERARQUIA);
			guis.SetVision(BizRoles.JERARQUIA);
		}

		return guis;
	}

	private JWins getClases() throws Exception {
		return JWins.createVirtualWinsFromMap(BizTypeLicense.getLicenseClass());
	}


} // @jve:decl-index=0:visual-constraint="10,10"
