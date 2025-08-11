package pss.common.terminals.config;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiTerminalGroup extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiTerminalGroup() throws Exception {
  }



  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizTerminalGroup(); }
  @Override
	public int GetNroIcono()   throws Exception { return 890; }
  @Override
	public String GetTitle()   throws Exception { return "Grupo de Terminales"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormTerminalGroup.class; }
  @Override
	public String  getKeyField() throws Exception { return "terminal_group"; }
  @Override
	public String  getDescripField() { return "description"; }
  public BizTerminalGroup GetcDato() throws Exception { return (BizTerminalGroup) this.getRecord(); }

  @Override
	public void createActionMap() throws Exception {
  	super.createActionMap();
    this.addAction(10, "Teminales", null, 77, false, false);
  }
  
  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActWins(this.getTerminals());
  	return null;
  }
  
  public JWins getTerminals() throws Exception {
  	GuiGroupTerminals wins = new GuiGroupTerminals();
  	wins.getRecords().addFilter("company", GetcDato().getCompany());
  	wins.getRecords().addFilter("nodo", GetcDato().getNodo());
  	wins.getRecords().addFilter("terminal_group", GetcDato().getTerminalGroup());
  	return wins;
  }
  
 }
