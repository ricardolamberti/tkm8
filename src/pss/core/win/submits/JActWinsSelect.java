package pss.core.win.submits;

import pss.core.win.JBaseWin;
import pss.core.win.JWins;


public class JActWinsSelect extends JActWins {
	

	public JActWinsSelect(JWins zWins, JBaseWin listener, boolean zMulti) throws Exception {
		super(zWins, zMulti);
		zWins.assignDropListener(listener);
	}

	public JActWinsSelect(JWins zWins, JBaseWin listener) throws Exception {
		this(zWins, listener, false);
	}
	

	
	@Override
	public boolean isHistoryAction() throws Exception {
		return true;
	}
	public boolean isTargetAction(JAct submit) throws Exception {
		if (this.historyTarget) return true;
		if (submit instanceof JActDrop) {
//			return !getWinsResult().hasDropListener();// ya lo seleccionaron, me salteo la lista
			JActDrop drop = (JActDrop)submit;
			if (getWinsResult().equals(drop.getDropListener())) return true;
			return  getWinsResult().getClass().equals(drop.getDropListener().getClass());
		}
		return true;
	}
	
}
