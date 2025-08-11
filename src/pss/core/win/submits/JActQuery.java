package pss.core.win.submits;

import pss.core.tools.PssLogger;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

/**
 * Action used to display the data of a record without modifying it.
 * <p>
 * Generates a read-only form from the target window and marks the action as
 * part of the navigation history so the user can return to previous screens.
 * </p>
 */
public class JActQuery extends JAct {

	public JActQuery(JWin zResult) {
		super(zResult);
		historyAction=true;
	}

//	@Override
//	public void Do() throws Exception {
//		this.getForm().Show();
//	}
	@Override
	public JBaseForm getForm() throws Exception {
		if (this.getWinResult()==null) {
			PssLogger.logDebug("error");
			throw new Exception("bad request");

		}
		return this.getWinResult().createQueryForm(this, !canReReadForm());
	}

	public JBaseForm getFormFlat() throws Exception {
		return this.getWinResult().createQueryFormFlat(this);
  }


	@Override
	public boolean isQueryAction() throws Exception {
		return true;
	}
	public boolean isTargetAction() throws Exception {
		return true;
	}

}
