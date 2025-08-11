package pss.www.ui;

import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;
import pss.core.winUI.controls.JFormControl;
import pss.www.platform.actions.JWebActionFactory;

public class JWebHelp extends JWebView {

	
	public JWebHelp() {
		super();
	}

	@Override
	public String getComponentType() {
		return "help";
	}

	@Override
	protected void generateHistoryBarActions() throws Exception {
	}
	
	@Override
	protected void build() throws Exception {
//		this.setLayout(null);
		BizUsuario.retrieveSkinGenerator().setPositionHelp(this);
		if (!BizPssConfig.getPssConfig().withTree()) {
			JWebForm oForm=new JWebForm();
			oForm.setLocation(this.getLocation());
			oForm.setSize(this.getSize());
//			oForm.setLayout(new JWebBorderLayout());
			oForm.setSkinStereotype(this.getSkinStereotypeView());
			this.add("help_form", oForm);

			JWebButton oRequestHelp=new JWebButton();
			oRequestHelp.setSkinStereotype("help_embedded");
			oRequestHelp.setWebAction(JWebActionFactory.createViewAreaAntTitleAction("pss.common.help.GuiModuloHelp_10"));
			oForm.add("oRequestHelp", oRequestHelp);
		} else {
			JWebForm oForm=new JWebForm();
//			JWebFlowLayout flowlayout=new JWebFlowLayout(ORIENTATION_VERTICAL, JFormControl.HALIGN_CENTER, JFormControl.VALIGN_MIDDLE, JFormControl.HALIGN_CENTER);
//			flowlayout.setHGap(15);
			oForm.setLocation(this.getLocation());
			oForm.setSize(this.getSize());
//			oForm.setLayout(flowlayout);
			oForm.setSkinStereotype(this.getSkinStereotypeView());
			oForm.setMargins(1, 2, 5, 3);
			this.add("help_form", oForm);

			JWebLabel oLabel=new JWebLabel("Ayuda");
			oLabel.setSize(40, 25);
			oLabel.setHAlignment(JFormControl.HALIGN_LEFT);
			oForm.add("help_label", oLabel);

			JWebButton oRequestHelp=new JWebButton();
			oRequestHelp.setSize(40, 25);
			oRequestHelp.setLabel("?");
			oRequestHelp.setWebAction(JWebActionFactory.createViewAreaAntTitleAction("pss.common.help.GuiModuloHelp_10"));
			oForm.add("oRequestHelp", oRequestHelp);
/*
			JWebButton oHideHelp=new JWebButton();
			oHideHelp.setSize(30, 25);
			oHideHelp.setLabel("X");
			oHideHelp.setWebAction(JWebActionFactory.createViewAreaAction("pss.common.help.GuiModuloHelp_11"));
			oForm.add("oHideHelp", oHideHelp);*/
		}
	}

	@Override
	protected void generateNavigationBarActions() throws Exception {
	}

	protected String getSkinStereotypeView() throws Exception {
		return getSkinStereotype();
	}

}
