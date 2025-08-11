package pss.common.tableGenerator;

import pss.common.dbManagement.synchro.base.JBaseSystemDBTable;
import pss.common.dbManagement.synchro.xml.JXmlSystemDBTable;
import pss.core.data.files.JStreamFile;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActUpdate;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIcon;

public class GuiTable extends JWin {

	
  public JRecord ObtenerDato()   throws Exception { 
		JBaseSystemDBTable tbl = JBaseSystemDBTable.VirtualCreate();
		return tbl;
  }
  public int GetNroIcono()   throws Exception { return GuiIcon.ALTER_TABLE_ICON; }
  public String GetTitle()   throws Exception {return "Generador"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { 	return FormTable.class; }
  public String  getKeyField() throws Exception { return "list_id"; }
  public String  getDescripField() { return "table_name"; }
  public JBaseSystemDBTable GetcDato() throws Exception { return (JBaseSystemDBTable) this.getRecord(); }
  

	//-------------------------------------------------------------------------//
	// Constructor de la Clase
	//-------------------------------------------------------------------------//
	public GuiTable() throws Exception {}
	
	public void createActionMap() throws Exception {
		addAction(10, "Generación Automática", null, 121, true, true);
	}
	
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==10) return new JActUpdate(getFormGeneration(), 2);
		return super.getSubmitFor(a);
	}
	
	
	public JWin getFormGeneration() throws Exception {
		GuiTableGenerator owP = new GuiTableGenerator();
		BizTableGenerator obP = (BizTableGenerator)owP.getRecord();
		obP.setTableName(this.GetcDato().getTableName());
		return owP;
	}
// 
	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}
}
