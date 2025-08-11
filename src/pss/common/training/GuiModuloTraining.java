package pss.common.training;

import pss.common.training.level1.GuiLevels1;
import pss.common.training.level2.GuiLevels2;
import pss.common.training.level3.GuiLevels3;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActWins;

public class GuiModuloTraining extends GuiModulo  {
	public GuiModuloTraining() throws Exception {
		super();
		SetModuleName("Training");
		SetNroIcono(731);
	}

	@Override
	public BizAction createDynamicAction() throws Exception {
		return addAction(1, "Training", null, 731, true, true, true);
	}

	@Override
	public void createActionMap() throws Exception {
		this.addAction(10, "level 1", null, 1103, true, true);
		this.addAction(20, "level 2", null, 1103, true, true);
		this.addAction(30, "level 3", null, 731, true, true);
	}

	public boolean OkAction(BizAction a) throws Exception {
		// if ( a.getId() == 40 ) return BizUsuario.getUsr().isAdminUser();
		return true;
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 1) return new JActQuery(this);
		if (a.getId() == 10) return new JActWins(new GuiLevels1());
		if (a.getId() == 20) return new JActWins(new GuiLevels2());
		if (a.getId() == 30) return new JActWins(new GuiLevels3());
		return null;
	}


}
