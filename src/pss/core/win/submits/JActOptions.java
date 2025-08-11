package pss.core.win.submits;

import pss.core.win.JBaseWin;
import pss.core.win.JWins;

public class JActOptions extends JActWins {
	
	String question;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String message) {
		this.question = message;
	}

	public JActOptions(String message, JWins zWins, JBaseWin listener) throws Exception {
		super(zWins, false);
		zWins.assignDropListener(listener);
		zWins.readAll();
		setQuestion(message);
		if (zWins.getRecords().getStaticItems().size()<=12)
			zWins.setModeView(JBaseWin.MODE_OPTIONS);
		getActionSource().setModal(true);
	}
	
	public JActOptions(JWins zWins, JBaseWin listener) throws Exception {
		super(zWins, false);
		zWins.assignDropListener(listener);
		getActionSource().setModal(true);
	}
	
//	@Override
//	public void Do() throws Exception {
//    JListForm oForm = new JListForm(this.getWinsResult(), this.createControlWin());
//    oForm.Mostrar();
//  }

	
	@Override
	public boolean isHistoryAction() throws Exception {
		return true;
	}
	public boolean isTargetAction() throws Exception {
		if (this.historyTarget) return true;
		return !getWinsResult().hasDropListener();
	}
	
}
