package  pss.common.customMenu;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiMenuItem extends JWin {

	public GuiMenuItem() throws Exception {}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizMenuItem();
	}
	@Override
	public int GetNroIcono() throws Exception {
		return 10071;
	}
	@Override
	public String GetTitle() throws Exception {
		return "Menu item";
	}
	@Override
	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormMenuItem.class;
	}
	@Override
	public String getKeyField() throws Exception {
		return "id_item";
	}
	@Override
	public String getDescripField() {
		return "description";
	}
  /**
   * Maps the acciones with the operations
   */
	@Override
	public void createActionMap() throws Exception {
		addActionQuery(1, "Consultar");
		addActionUpdate(2, "Modificar");
		addActionDelete(3, "Eliminar");
//		addAction(11, "Subitems", new JAct() {
//			public JBaseWin GetBWin() throws Exception {
//				return getMenuChilds();
//			}
//		}, 984, false, false, true, "Detail");
	}

//  private JWins getMenuChilds() throws Exception {
//    JEnlace oEnlace = new JEnlace();
//    oEnlace.AddValor("id_menu", "id_menu", this.GetcDato().getIdMenu());
//    GuiMenuItems oItems = new GuiMenuItems();
//    oItems.getRecords().addOrderBy("description");
//    oItems.SetEnlazado(oEnlace);
//    return oItems;
//  }
  /**
   * Returns the casted data
   */
	public BizMenuItem GetcDato() throws Exception {
		return (BizMenuItem)this.getRecord();
	}
	
	@Override
	public String GetIconFile() throws Exception {
		return this.GetcDato().findIconFile(0);
	}

	public boolean isCleanHistory() throws Exception {
		return false;
	}


}
