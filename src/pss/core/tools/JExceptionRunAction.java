package pss.core.tools;

import pss.core.win.JBaseWin;
import pss.core.win.actions.BizAction;

public class JExceptionRunAction extends Exception {

	String action;
		JBaseWin win;


	public JBaseWin getWin() {
		return win;
	}



	public void setWin(JBaseWin win) {
		this.win = win;
	}



	
	public String getAction() {
		return action;
	}



	public void setAction(String action) {
		this.action = action;
	}

	public JExceptionRunAction(int action) {
		this.action = ""+action;
	}

	public JExceptionRunAction(String action) {
		this.action = action;
	}
	public BizAction getBizAction() throws Exception {
		return 	getWin().findActionByUniqueId(getAction());
	}

			 
}
