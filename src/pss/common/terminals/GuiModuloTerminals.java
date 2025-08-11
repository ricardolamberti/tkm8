package pss.common.terminals;

import pss.common.terminals.config.shadows.BizTerminalShadow;
import pss.common.terminals.config.shadows.GuiTerminalShadows;
import pss.common.terminals.connection.server.JTerminalPoolServer;
import pss.common.terminals.connection.server.JTerminalPoolShadow;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;

public class GuiModuloTerminals extends GuiModulo {

	public GuiModuloTerminals() throws Exception {
		super();
		SetModuleName("Terminales");
		SetNroIcono(731);
	}

	@Override
	public BizAction createDynamicAction() throws Exception {
		return addAction(1, "Terminales", null, 731, true, true, true);
	}

	@Override
	public void createActionMap() throws Exception {
		this.addAction(40, "Start Server", null, 1103, true, true);
		this.addAction(45, "Stop Server", null, 1103, true, true);
		this.addAction(50, "Shadows", null, 731, true, true);
	}

	public boolean OkAction(BizAction a) throws Exception {
		// if ( a.getId() == 40 ) return BizUsuario.getUsr().isAdminUser();
		return true;
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 1) return new JActQuery(this);
		if (a.getId() == 40) return new JActSubmit(this, 40) {
			public void submit() throws Exception {
				JTerminalPoolServer.listen();
			}
		};
		if (a.getId() == 45) return new JActSubmit(this, 40) {
			public void submit() throws Exception {
				JTerminalPoolServer.stop();
			}
		};
		if (a.getId()==50) return new JActWins(this.getTerminalShadows());
		return null;
	}


	private JWins getTerminalShadows() throws Exception {
		GuiTerminalShadows wins = new GuiTerminalShadows();
		wins.getRecords().setStatic(true);
		JMap<String, JTerminalPoolShadow> map = JTerminalPoolServer.getRemoteTerminals();
		if (map==null) return wins;
		JIterator<String> iter = map.getKeyIterator();
		while (iter.hasMoreElements()) {
			String mac = iter.nextElement();
			BizTerminalShadow t = new BizTerminalShadow();
			t.setMacAddress(mac);
			t.setTerminalShadow(map.getElement(mac));
			wins.getRecords().addItem(t);
		}
		return wins;
	}

}
