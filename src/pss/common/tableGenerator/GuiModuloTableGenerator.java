package pss.common.tableGenerator;

import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;

public class GuiModuloTableGenerator extends GuiModulo {

	public GuiModuloTableGenerator() throws Exception {
		super();
		SetModuleName("Generación Automática");
		SetNroIcono(77);
	}

	@Override
	public BizAction createDynamicAction() throws Exception {
	  return addAction( 1, "Generación Automática", null, 77, true, false, true, "Group" );
	}

	@Override
	public void createActionMap() throws Exception {
	  addAction( 2, "Generación Automática", null ,77 ,true, false, true, "Group" );
	}
	
  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	GuiTables tbls = new GuiTables();
  	if ( a.getId()==2) return new JActWins(tbls);
  	return null;
  }
	
}
 