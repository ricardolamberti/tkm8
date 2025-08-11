package pss.core.win.submits;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class JActChildForm extends JActModify {
	
//private JBaseForm form;
	JWin parent;
/**
 * 
 */
private static final long serialVersionUID = 2799749949633327989L;

public JActChildForm(JWin zparent, JWin zResult, int zActionId) {
	super(zResult, zActionId);
	historyAction=true;
	parent = zparent;
}

@Override
public JBaseForm getForm() throws Exception {
	
	JBaseForm f =this.getUpdateForm();
	f.SetExitOnOk(isExitOnOk());
	return f;
}


@Override
public void submit() throws Exception {
//	this.getWinResult().getRecord().execProcessUpdate();
}

@Override
public boolean backAfterSubmit() { 
	return true;
}

@Override
public boolean isHistoryAction() throws Exception {
	return true;
}
//public void cleanAction() {
//	this.form=null;
//}

public boolean isTargetAction() throws Exception {
	return true;
}

}
