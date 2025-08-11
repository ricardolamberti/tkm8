package pss.www.ui;


public class JWebWinActionBarForm extends JWebWinActionBar {

	public JWebWinActionBarForm(JWebActionOwnerProvider zObjectProvider, boolean zForForm, String toolbar, boolean bEmbedded) {
		super(zObjectProvider, zForForm, toolbar, bEmbedded);
	}
//	public String getName() {
//		return "win_form_action_bar";
//	}
	public boolean isForm() throws Exception {
		return true;
	}
	
}
