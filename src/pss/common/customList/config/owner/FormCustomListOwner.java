package pss.common.customList.config.owner;

import pss.common.security.GuiUsuarios;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;

public class FormCustomListOwner extends JBaseForm {

	private static final long serialVersionUID = 1226426905817L;

	/**
	 * Constructor de la Clase
	 */
	public FormCustomListOwner() throws Exception {
	}

	public GuiCustomListOwner getWin() {
		return (GuiCustomListOwner) getBaseWin();
	}

	/**
	 * Linkeo los campos con la variables del form
	 */
	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemEdit(null, CHAR, REQ, "company").setHide(true);
		AddItemEdit(null, UINT, REQ, "list_id").setHide(true);
		AddItemEdit(null, UINT, OPT, "secuencia").setHide(true);
		AddItemCombo("Usuario", CHAR, REQ, "usuario", new JControlCombo() {
			public JWins getRecords(boolean one) throws Exception {
				return getUsuario(one);
			}
		});

	}

	@Override
	public void OnPostShow() throws Exception {
		super.OnPostShow();
	}

	private JWins getUsuario(boolean one) throws Exception {
		return new GuiUsuarios().addFilterAdHoc("company", getWin().GetcDato().getCompany());
	}

} // @jve:decl-index=0:visual-constraint="-1,16"
