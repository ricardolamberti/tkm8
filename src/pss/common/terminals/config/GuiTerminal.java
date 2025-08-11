package pss.common.terminals.config;

import pss.common.terminals.core.JTerminal;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiTerminal extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiTerminal() throws Exception {
  }


  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizTerminal(); }
  @Override
	public int GetNroIcono()   throws Exception { return 700; }
  @Override
	public String GetTitle()   throws Exception { return "Terminal"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormTerminal.class; }
  @Override
	public String  getKeyField() throws Exception { return "terminal_id"; }
  @Override
	public String  getDescripField() { return "descr_terminal"; }
  public BizTerminal GetcDato() throws Exception { return (BizTerminal) this.getRecord(); }

  @Override
	public void createActionMap() throws Exception {
  	super.createActionMap();
  	this.addAction(10, "Drivers", null, 77, false, false, true, "Detail");
//    this.addAction(15, "Datos Impresora", null, 555, true, true);
    this.addAction(25, "Test", null, 2, true, true);
  }
  
  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActWins(this.getDrivers());
//  	if (a.getId()==15) return new JActNew(this.getAdicPrinter(), 0, true);
   	if (a.getId()==25) 
  		return new JActSubmit(this, a.getId()) {
   			public void submit() throws Exception {((BizTerminal)GetcDato()).runTest();}
   		};

		return null;
  }
  
  @Override
	public boolean OkAction( BizAction zAct ) throws Exception {
  	if (zAct.getId()==15) return GetcDato().hasDriverConfig(JTerminal.D_PRINTER);
  	return true;
  }
  
  
  public JWins getDrivers() throws Exception {
  	GuiTerminalDrivers wins = new GuiTerminalDrivers();
  	wins.getRecords().addFilter("company", GetcDato().getCompany());
  	wins.getRecords().addFilter("nodo", GetcDato().getNodo());
//  	wins.getRecords().addFilter("terminal_pool", GetcDato().getTerminalPool());
  	wins.getRecords().addFilter("terminal_id", GetcDato().getTerminalId());
  	return wins;
  }

  
}
