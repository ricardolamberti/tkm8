package  pss.common.customMenu;

import pss.core.services.records.JRecord;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiMenu extends JWin {

	public GuiMenu() throws Exception {
	}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizMenu();
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 10070;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Menu";
	}

	@Override
	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormMenu.class;
	}

	@Override
	public String getKeyField() throws Exception {
		return "id_menu";
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
		addAction(10, "Sub Menús", null, 10072, false, false, true, "Group");
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==10) return new JActWins(this.getMenuGroups());
		return null;
	}

	private JWins getMenuGroups() throws Exception {
		GuiMenuGroups oItems=new GuiMenuGroups();
		oItems.getRecords().addFilter("id_menu", this.GetcDato().getIdMenu());
		oItems.getRecords().addFilter("id_group_parent", 0);
		oItems.getRecords().addOrderBy("orden");
		return oItems;
	}

	/**
	 * Returns the casted data
	 */
	public BizMenu GetcDato() throws Exception {
		return (BizMenu) this.getRecord();
	}

	public BizAction generateActionMenu(boolean okAction, int size) throws Exception {
		BizAction action=new BizAction();
		JIterator<BizMenuGroup> iter=this.GetcDato().getObjMenuGroups().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizMenuGroup group=(BizMenuGroup) iter.nextElement();
			group.generateActionMenu(action, okAction, size);
		}
		return action;
	}


}
