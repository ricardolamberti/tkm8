package pss.core.win.submits;

import pss.core.tools.JExceptionOnlyMessage;
import pss.core.win.JBaseWin;
import pss.core.winUI.forms.JBaseForm;

/**
 * Base class for actions that modify the underlying record.
 * <p>
 * Provides helper methods to obtain either a new form or an update form and to
 * notify controls about changes triggered by the user. Concrete subclasses
 * implement the specific behaviour for insert, update or delete operations.
 * </p>
 */
public abstract class JActModify extends JAct {


	/**
	 * 
	 */
//	private JWin winRef;
	
	

	public JActModify(JBaseWin zResult, int zActionId) {
		super(zResult, zActionId);
	}
	public JActModify(JBaseWin zResult, String zActionId) {
		super(zResult, zActionId);
	}

	protected JBaseForm getUpdateForm() throws Exception {
		return this.getWinResult().createUpdateForm(this, this.getSubmitById(), !canReReadForm());

	}
	
	protected JBaseForm getNewForm() throws Exception {
		return this.getWinResult().createNewForm(this, this.getSubmitById());
	}

	public void notifyAction(Object value) throws Exception {
//		if (this.fieldChanged==null) return;
		JBaseForm form = (JBaseForm) value;
		try {
			form.checkControls(this.fieldChanged==null?"":this.fieldChanged); // va "" cuando arranca, util para no duplicar funcones ya ue si se ponen en el inicializar panel se terminan duplicando la logica cuando se modifica el campo HGK
		} catch (Exception e) {
			if (e instanceof JExceptionOnlyMessage)
				setMessage(((JExceptionOnlyMessage) e).getMsg());
			else
				throw e;
		}
	}
	
  @Override
  public JAct doSubmit(boolean zWeb) throws Exception {
  	JAct act= super.doSubmit(zWeb);
		this.setClearChangeInputs(true);
  	return act;
  }
}
