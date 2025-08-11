package pss.www.ui;

import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.winUI.controls.JFormControles;
import pss.core.winUI.forms.JBaseForm;

public interface IWebWinForm extends JWebActionOwnerProvider {
	public JBaseForm getBaseForm() throws Exception;
	public String getProviderName() throws Exception;
	public JWin getWin();
	public boolean isPreview();
	public String getContainerPreview();
	public BizAction getSourceAction();
	public JFormControles getControls() throws Exception;
	public JWebViewComponent findComponentByControl( String zIdControl) throws Exception;
}
