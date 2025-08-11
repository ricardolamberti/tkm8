package  pss.common.regions.cities;

import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;

public class FormCity extends JBaseForm {

	public FormCity() throws Exception {
	}

	public GuiCity GetWin() {
		return (GuiCity) getBaseWin();
	}

	/**
	 * Linkeo los campos con la variables del form
	 */
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemEdit("city", CHAR, OPT, "city");
		AddItemEdit("descripcion", CHAR, REQ, "descripcion");
		AddItemCombo("Pais", CHAR, REQ, "country", new JControlWin() {
			@Override
			public JWins getRecords(boolean zOneRow) throws Exception {
				return getPaises(zOneRow);
			}
		});
		AddItemEdit("codigo", CHAR, OPT, "codigo");

	}

	protected JWins getPaises(boolean zOneRow) throws Exception {
		GuiPaisesLista wins = new GuiPaisesLista();
		if (zOneRow) {
			wins.getRecords().addFilter("pais", GetWin().GetcDato().getCountry());
		}
		return wins;
	}

}
