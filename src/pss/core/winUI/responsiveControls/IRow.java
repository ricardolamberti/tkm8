package pss.core.winUI.responsiveControls;

import pss.core.win.JWin;
import pss.core.winUI.controls.IFormTable;
import pss.core.winUI.forms.JBaseForm;

public interface IRow {
	public JBaseForm GetForm();
	public String getProvider();
	public IFormTable getTable();
	public long getIdRow();
	public JWin getWin();


}
