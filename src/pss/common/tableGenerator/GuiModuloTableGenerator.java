package pss.common.tableGenerator;

import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;

public class GuiModuloTableGenerator extends GuiModulo {

	public GuiModuloTableGenerator() throws Exception {
		super();
		SetModuleName("Generaci�n Autom�tica");
		SetNroIcono(77);
	}

	@Override
	public BizAction createDynamicAction() throws Exception {
	  return addAction( 1, "Generaci�n Autom�tica", null, 77, true, false, true, "Group" );
	}

	@Override
	public void createActionMap() throws Exception {
	  addAction( 2, "Generaci�n Autom�tica", null ,77 ,true, false, true, "Group" );
	}
	
  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	GuiTables tbls = new GuiTables();
  	if ( a.getId()==2) return new JActWins(tbls);
  	return null;
  }
	
}
 