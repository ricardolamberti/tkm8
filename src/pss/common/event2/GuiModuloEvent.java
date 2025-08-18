package pss.common.event2;

import pss.common.event2.telegram.GuiTelegramUserChannel;
import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActUpdate;

public class GuiModuloEvent extends GuiModulo {

	public GuiModuloEvent() throws Exception {
		super();
		SetModuleName("Eventos");
		SetNroIcono(5072);
	}

	@Override
	public BizAction createDynamicAction() throws Exception {
		return this.addAction(1, "Eventos", null, 5072, true, true, true, "Group");
	}

	@Override
	public void createActionMap() throws Exception {

		this.addAction(9000, "Confirmar vinculación con Telegram", null, 5058, true, true, true, "Group");

		this.loadDynamicActions(null);

	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 9000) {
			JAct upd = new JActUpdate(this.getTelegramChannel(), JWin.ACTION_UPDATE);
			upd.setExitOnOk(true);
			return upd;

		}

		return null;
	}

	private JWin getTelegramChannel() throws Exception {
		GuiTelegramUserChannel t = new GuiTelegramUserChannel();
		t.getRecord().addFilter("company", BizUsuario.getUsr().getCompany());
		t.getRecord().addFilter("userid", BizUsuario.getUsr().GetUsuario());
		t.getRecord().dontThrowException(true);
		t.getRecord().read();
		return t;
	}

}
