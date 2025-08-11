package pss.core.win.submits;

import pss.core.tools.JMessageInfo;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

/**
 * Action that updates an existing record.
 * <p>
 * It generates an update form for the target window and performs the
 * persistence logic when submitted.
 * </p>
 */
public class JActUpdate extends JActModify {
	
//	private JBaseForm form;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2799749949633327989L;

	public JActUpdate(JWin zResult, int zActionId) {
		super(zResult, zActionId);
		historyAction=true;
	}
	public JActUpdate(JWin zResult, String zActionId) {
		super(zResult, zActionId);
		historyAction=true;
	}
	public JActUpdate(JWin zResult, String zActionId,boolean reread) {
		super(zResult, zActionId);
		historyAction=true;
		setReRead(reread);
	}
	public JActUpdate(JWin zResult, int zActionId,boolean reread) {
		super(zResult, zActionId);
		historyAction=true;
		setReRead(reread);
	}
	public JActUpdate(JWin zResult, int zActionId, JBaseWin listener) throws Exception {
		super(zResult, zActionId);
		zResult.assignDropListener(listener);
	}
	@Override
	public JBaseForm getForm() throws Exception {
		JBaseForm f =this.getUpdateForm();
//		f.getBaseWin().setCanConvertToURL(false);
		f.SetExitOnOk(isExitOnOk());
		return f;
	}


	@Override
	public void submit() throws Exception {
		this.getWinResult().getRecord().execProcessUpdate();
  	setMessage(new JMessageInfo(this.getWinResult().getRecord().getMessageUpdateOk()));
	}
	
  @Override
	public boolean backAfterSubmit() { 
  	return true;
  }
	
	@Override
	public boolean isHistoryAction() throws Exception {
		return true;
	}
//	public void cleanAction() {
//		this.form=null;
//	}

	public boolean isTargetAction() throws Exception {
		return true;
	}

}
