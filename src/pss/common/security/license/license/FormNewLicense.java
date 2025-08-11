package  pss.common.security.license.license;

import pss.common.security.BizUsuario;
import pss.common.security.license.typeLicense.GuiTypeLicenses;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;

public class FormNewLicense extends JBaseForm {

	private static final long serialVersionUID = 1245199349219L;

	/**
	 * Constructor de la Clase
	 */
	public FormNewLicense() throws Exception {

	}

	public GuiLicense getWin() {
		return (GuiLicense) getBaseWin();
	}

	/**
	 * Linkeo los campos con la variables del form
	 */
	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemEdit(null, CHAR, REQ, "company").setHide(true).SetValorDefault( BizUsuario.getUsr().getCompany());
		AddItemEdit("Cantidad", CHAR, REQ, "cantidad").SetValorDefault(1);
		AddItemCombo("Tipo licencia", CHAR, REQ, "id_typeLicense", new JControlCombo() {
			@Override
			public JWins getRecords(boolean one) throws Exception {
				return getTiposLicencias(one);
			}
		});

	}

	public JWins getTiposLicencias(boolean one) throws Exception {
		GuiTypeLicenses guis = new GuiTypeLicenses();
		if (one) {
			guis.getRecords().addFilter("company", this.getWin().GetcDato().getCompany());
			guis.getRecords().addFilter("id_typeLicense", this.getWin().GetcDato().getIdtypelicense());
		} else {
			guis.getRecords().addFilter("company",this.getWin().GetcDato().getCompany());
		}

		return guis;
	}

}
