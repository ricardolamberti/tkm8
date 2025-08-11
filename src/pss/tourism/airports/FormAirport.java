package pss.tourism.airports;

import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormAirport extends JBaseForm {

	private static final long serialVersionUID=1L;

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormAirport() throws Exception {

	}

	public GuiAirport GetWin() {
		return (GuiAirport) GetBaseWin();
	}


	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		SetExitOnOk(true);
		JFormPanelResponsive row = AddItemRow();
		JFormColumnResponsive colL =  row.AddItemColumn(6);
		JFormColumnResponsive colR =  row.AddItemColumn(6);
		colL.AddItemRow().AddItemEdit("Codigo", CHAR, REQ, "code").setSizeColumns(3);
		colL.AddItemRow().AddItemEdit("Descricpion", CHAR, REQ, "description");
		colL.AddItemRow().AddItemCombo("Pais", CHAR, REQ, "country", new JControlWin() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return getPaises(zOneRow);
			}
		});
		colL.AddItemRow().AddItemEdit("Ciudad", CHAR, OPT, "city");
		colR.AddItemMap("Mapa", CHAR, OPT, "geo_position");
		colL.AddItemRow().AddItemEdit("Iata area Metropolitana", CHAR, OPT, "iata_area");
		// AddItem(calculado, CHAR, OPT, "calculado");
	}

	
	protected JWins getPaises(boolean zOneRow) throws Exception {
		GuiPaisesLista wins=new GuiPaisesLista();
		if (zOneRow) {
			wins.getRecords().addFilter("pais", GetWin().GetcDato().getCountry());
		}
		return wins;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
