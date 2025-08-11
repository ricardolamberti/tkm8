package pss.common.publicity;

import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;

public class GuiModuloPublicity extends GuiModulo {

	public GuiModuloPublicity() throws Exception {
		super();
		SetModuleName("Publicidad");
		SetNroIcono(755);
	}

	@Override
	public BizAction createDynamicAction() throws Exception {
		return addAction(1, "Publicidad", null, 755, true, true, true, "Group");
	}

	@Override
	public void createActionMap() throws Exception {
		this.addAction(10, "Campañas publicitarias", null, 755, true, false, true, "Group");
		this.addAction(11, "Ocultar publicidad", null, 755, true, false, true, "Group");
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)
			return new JActWins(this.getCampaigns());
	  	if (a.getId()==11) 
	  		return new JActSubmit(this, a.getId()) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			@Override
			public void submit() throws Exception {BizCampaign.clearCampaign();}};
		return null;
	}

	private JWins getCampaigns() throws Exception {
		GuiCampaigns records = new GuiCampaigns();
		return records;
	}

}
