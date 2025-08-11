package  pss.common.security;

import pss.common.regions.company.JCompanyBusiness;
import pss.common.regions.company.JCompanyBusinessModules;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormWebUserProfile extends JBaseForm {

	/**
	 * Constructor de la Clase
	 */
	public FormWebUserProfile() throws Exception {
	}

	public GuiWebUserProfile GetWin() {
		return (GuiWebUserProfile) getBaseWin();
	}


	/**
	 * Linkeo los campos con la variables del form
	 */
	public void InicializarPanel(JWin zWin) throws Exception {
		JFormPanelResponsive col = this.AddItemColumn(4);
		col.setLabelLeft(4);
		col.AddItemEdit(getMessage("Usuario pref."), CHAR, REQ, "USUARIO");
		col.AddItemCombo(getMessage("Skin"), CHAR, REQ, "SKIN", getSkins());
		col.AddItemCombo(getMessage("Skin Mobile"), CHAR, OPT, "SKIN_MOBILE", getSkins());
		// if ( this.recs == null )
		// AddItem( home, CHAR, OPT, "HOME_PAGE" );
		// else
		if (BizUsuario.getUsr().getObjCompany() != null) {
			col.AddItemCombo(getMessage("Página Inicial"), CHAR, OPT, "HOME_PAGE", new JControlCombo() {
				public JWins getRecords(boolean one) throws Exception {
					return getHomePages();
				}
			});
		}
		col.AddItemCombo(getMessage("Líneas por pág."), UINT, OPT, "DEFAULT_PAGESIZE", getPageLengths());
		col.AddItemEdit(getMessage("Max.Reg.Matriz"), UINT, OPT, "MAX_MATRIX").SetValorDefault(5000);
	}

	@SuppressWarnings("unchecked")
	private JWins getPageLengths() throws Exception {
		JRecords pages = JRecords.createVirtualBDs();
		pages.addItem(JRecord.virtualBD("10", "10 Filas", 1));
		pages.addItem(JRecord.virtualBD("25", "25 Filas", 1));
		pages.addItem(JRecord.virtualBD("50", "50 Filas", 1));
		pages.addItem(JRecord.virtualBD("75", "75 Filas", 1));
		pages.addItem(JRecord.virtualBD("100", "100 Filas", 1));
		pages.addItem(JRecord.virtualBD("125", "125 Filas", 1));
		pages.addItem(JRecord.virtualBD("150", "150 Filas", 1));
		pages.addItem(JRecord.virtualBD("175", "175 Filas", 1));
		pages.addItem(JRecord.virtualBD("200", "200 Filas", 1));
		JWins wins = JWins.CreateVirtualWins(pages);
		return wins;
	}

	private JWins getHomePages() throws Exception {
		if (BizUsuario.getUsr().getObjCompany() == null)
			return null;
		JCompanyBusiness bus = JCompanyBusinessModules.getInstanceFor(BizUsuario.getUsr().getObjCompany().getBusiness());
		return JWins.createVirtualWinsFromMap(bus.getBusinessHomePages());
	}

	private JWins getSkins() throws Exception {
  	return BizWebUserProfile.getSkins();
  }

} // @jve:decl-index=0:visual-constraint="10,10"
