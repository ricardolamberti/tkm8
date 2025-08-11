package  pss.common.customMenu;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiMenuGroup extends JWin {

	public GuiMenuGroup() throws Exception {
	}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizMenuGroup();
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 904;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Menu Grupo";
	}

	@Override
	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormMenuGroup.class;
	}

	/**
	 * Maps the acciones with the operations
	 */
	@Override
	public void createActionMap() throws Exception {
		addActionQuery(1, "Consultar");
		addActionUpdate(2, "Modificar");
		addActionDelete(3, "Eliminar");
		addAction(10, "Items", null, 984, false, false, true, "Detail");
		addAction(12, "Sub Menús", null, 984, false, false, true, "Detail");
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==10) return new JActWins(this.getSubItems());
		if (a.getId()==12) return new JActWins(this.getSubGroups());
		return null;
	}

	private JWins getSubGroups() throws Exception {
		GuiMenuGroups oItems=new GuiMenuGroups();
		oItems.getRecords().addFilter("id_menu", this.GetcDato().getIdMenu());
		oItems.getRecords().addFilter("id_group_parent", this.GetcDato().getIdGroup());
		oItems.getRecords().addOrderBy("orden");
		return oItems;
	}

	private JWins getSubItems() throws Exception {
		GuiMenuItems oItems=new GuiMenuItems();
		oItems.getRecords().addFilter("id_menu", this.GetcDato().getIdMenu());
		oItems.getRecords().addFilter("id_group_parent", this.GetcDato().getIdGroup());
		oItems.getRecords().addOrderBy("orden");
		return oItems;
	}

	/**
	 * Returns the casted data
	 */
	public BizMenuGroup GetcDato() throws Exception {
		return (BizMenuGroup) this.getRecord();
	}

	@Override
	public String GetIconFile() throws Exception {
		String file=this.GetcDato().findIconFile(0);
		if (file!=null) return file;
		return super.GetIconFile();
	}
	
	@Override
	public boolean isCleanHistory() throws Exception {
		return false;
	}

}
