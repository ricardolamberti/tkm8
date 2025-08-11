package pss.core.win.submits;

import pss.core.win.JBaseWin;
import pss.core.winUI.forms.JBaseForm;

/**
 * Action responsible for inserting a new record.
 * <p>
 * Depending on the state of the backing record it can display either a new
 * form or, if the record already exists, an update form. The behaviour can be
 * customised through several constructor flags.
 * </p>
 */
public class JActNew extends JActModify {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8861951660654597358L;
	private boolean updIfRead=false;
	private boolean bNewForm=false;
	private boolean bNoWas=true;
//	private boolean alreadyExist=false;

	// private JBaseForm form;

	public void setNoWas(boolean bNoWas) {
		this.bNoWas = bNoWas;
	}
	public boolean refreshOnSubmit() throws Exception {
		return false;
	}

	public JActNew(JBaseWin zResult, int zActionId) {
		super(zResult, zActionId);
	}
	public JActNew(JBaseWin zResult, String zActionId) {
		super(zResult, zActionId);
	}

	public JActNew(JBaseWin zResult, int zActionId, JAct submit) {
		super(zResult, zActionId);
		zResult.setSubmitListener(submit);
	}
	public JActNew(JBaseWin zResult, String zActionId, JAct submit) {
		super(zResult, zActionId);
		zResult.setSubmitListener(submit);
	}
	// public JActNew(JBaseWin zResult, int zActionId, Throwable zThrowable) {
	// super(zResult, zActionId, zThrowable);
	// }
	public JActNew(JBaseWin zResult, int zActionId, boolean zUpdIfRead) {
		super(zResult, zActionId);
		updIfRead=zUpdIfRead;
	}
	public JActNew(JBaseWin zResult, String zActionId, boolean zUpdIfRead) {
		super(zResult, zActionId);
		updIfRead=zUpdIfRead;
	}
	public JActNew(JBaseWin zResult, int zActionId, boolean zUpdIfRead,boolean zNoWas) {
		super(zResult, zActionId);
		updIfRead=zUpdIfRead;
		bNoWas=zNoWas;
	}
	public JActNew(JBaseWin zResult, String zActionId, boolean zUpdIfRead,boolean zNoWas) {
		super(zResult, zActionId);
		updIfRead=zUpdIfRead;
		bNoWas=zNoWas;
	}
	public JActNew(JBaseWin zResult, int zActionId, boolean zUpdIfRead,boolean zNoWas,boolean isNew) {
		super(zResult, zActionId);
		updIfRead=zUpdIfRead;
		bNoWas=zNoWas;
		bNewForm=isNew;
	}	
	public JActNew(JBaseWin zResult, String zActionId, boolean zUpdIfRead,boolean zNoWas,boolean isNew) {
		super(zResult, zActionId);
		updIfRead=zUpdIfRead;
		bNoWas=zNoWas;
		bNewForm=isNew;
	}
	@Override
	public JBaseForm getForm() throws Exception {
		// if (this.form!=null) return this.form;
		// JBaseForm f=null;
		if (bNoWas && this.getWinResult().getRecord().wasRead()) {
			JBaseForm f;
			if (updIfRead) {
				f = this.getUpdateForm();
				return f;
			} else {
				f = this.getWinResult().createQueryForm(this, !canReReadForm());
			}
//			f.getBaseWin().setCanConvertToURL(false);
			if (!isExitOnOk()) f.SetExitOnOk(isExitOnOk());
			return f;
		} else {
			this.bNewForm=true;
			JBaseForm f = this.getNewForm();
//			f.getBaseWin().setCanConvertToURL(false);
			if (!isExitOnOk()) f.SetExitOnOk(isExitOnOk());
			return f;
		}
	}
		
	public boolean isExitOnOk()throws Exception {
		return !getResult().isKeepFormOnNew();
	}

	
	@Override
	public void submit() throws Exception {
		this.getWinResult().getRecord().execProcessInsert();
	}

	@Override
	public boolean isHistoryAction() throws Exception {
		return historyAction;
//		if (this.bNewForm) return false;
//	  return this.getWinResult().isHistoryAction();
//		return true;
//		return !this.getForm().hasToExitOnOk();
	}

	public boolean isTargetAction() throws Exception {
//		if (this.getForm().build().hasListEmbedded()) return true; // mejorar esto!!! HGK
//		if (this.getWinResult().hasDropControlIdListener()) return true;
//		return false;
		return true;
	}
	// public void cleanAction() {
	// this.form=null;
	// }
	//  

}
