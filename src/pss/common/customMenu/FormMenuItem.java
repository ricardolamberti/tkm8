package  pss.common.customMenu;

import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIconGalerys;

public class FormMenuItem extends JBaseForm {

	public FormMenuItem() throws Exception {
	}
	public GuiMenuItem GetWin() {
		return (GuiMenuItem)getBaseWin();
	}
  @Override
	public void InicializarPanel(JWin zWin) throws Exception {
		this.AddItemEdit("idMenu", CHAR, REQ, "id_menu").hide();
    this.AddItemEdit("idItem", CHAR, "OPT", "id_item").hide();
    this.AddItemEdit("idGroupParent", UINT, "OPT", "id_group_parent").hide();
    this.AddItemEdit("Description", CHAR, OPT, "description");
    this.AddItemEdit("Action", CHAR, REQ, "id_action");
    this.AddItemEdit("Orden", UINT, REQ, "orden");
		this.AddItemCombo("Icono", CHAR, OPT, "icon_file", new JControlWin() { @Override
		public JWins getRecords() throws Exception { return GuiIconGalerys.GetGlobal();}});
//    String sFilter = this.GetWin().GetcDato().getIdMenu();
//    this.AddItem(this.oSubmenuCombo, CHAR, REQ, "child", GuiMenus.getAvailablesMenus());
	}
  
} 
