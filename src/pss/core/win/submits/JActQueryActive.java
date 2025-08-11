package pss.core.win.submits;

import pss.core.win.JBaseWin;
import pss.core.winUI.forms.JBaseForm;

public class JActQueryActive  extends JActModify {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4568131727914487531L;


	public JActQueryActive(JBaseWin zResult) {
		super(zResult,-1);
		historyAction=true;
	}
	
	@Override
	public JBaseForm getForm() throws Exception {
		JBaseForm f =this.getUpdateForm();
		f.SetExitOnOk(isExitOnOk());
		return f;
	}
	
	public JBaseForm getFormFlat() throws Exception {
		return this.getWinResult().createQueryFormFlat(this);
  }



  @Override
	public boolean backAfterSubmit() { 
  	return true;
  }
	

	public boolean isTargetAction() throws Exception {
		return true;
	}
	

	public boolean isQueryAction() throws Exception {
		return true;
	}

	protected JBaseForm getUpdateForm() throws Exception {
		return this.getWinResult().createQueryActiveForm(this, null, this.getFieldChanged()!=null);
	}
	


	
}
