package pss.www.ui;


public class JWebWinActionBarFormLov extends JWebWinActionBarWinList {

	public JWebWinActionBarFormLov(JWebActionOwnerProvider zObjectProvider, boolean zForForm, String toolbar, boolean bEmbedded) {
		super(zObjectProvider, zForForm, toolbar, bEmbedded);
	}

//	public String getName() {
//		return "win_list_action_bar";
//	}
	
	public boolean isWithLabels() throws Exception {
		return true;
	}
	
	public String getStereotypeBar(boolean bforForm, boolean btoolbarTop, boolean bembedded) throws Exception {
		return "formlov_bar";
	}
	
	public String getStereotypeLinks(boolean isWin,long v, long importance) throws Exception {
		return "formlov_bar_link";
	}

	public void clearUnusedActions() throws Exception {
	}

	public boolean isFormLov() throws Exception {
		return true;
	}
}
