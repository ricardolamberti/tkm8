package  pss.common.customMenu;

import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIconGalerys;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormMenuGroup extends JBaseForm {

	public FormMenuGroup() throws Exception {
	}
	public GuiMenuGroup GetWin() {
		return (GuiMenuGroup)getBaseWin();
	}
  @Override
	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemEdit("idMenu", CHAR, REQ, "id_menu");
		AddItemEdit("idGroup", CHAR, OPT, "id_group");
		AddItemEdit("idGroupParent", CHAR, REQ, "id_group_parent");
		AddItemEdit("Description", CHAR, OPT, "description");
		AddItemCombo("Icono", CHAR, OPT, "icon_file", new JControlWin() { @Override
		public JWins getRecords() throws Exception { return GuiIconGalerys.GetGlobal();}});
		AddItemEdit("Orden", UINT, REQ, "orden");
		AddItemEdit("Action", CHAR, OPT, "id_action");
		JFormTabPanelResponsive tab= this.AddItemTabPanel();
		tab.AddItemTab(10);
		tab.AddItemTab(12);
	}
}
