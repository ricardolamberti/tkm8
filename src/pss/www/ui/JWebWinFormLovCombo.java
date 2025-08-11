/*
 * Created on 23-ago-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;

public class JWebWinFormLovCombo extends JWebWinForm implements JWebActionOwnerProvider {

	String comboId;

	public JWebWinFormLovCombo(JBaseForm zBaseForm) {
		super(zBaseForm);
	}

	public static JWebWinFormLovCombo createCombo(JBaseForm zBaseForm, String zComboId) throws Exception {
		if (zBaseForm==null) return null;
		JWebWinFormLovCombo oWebForm=new JWebWinFormLovCombo(zBaseForm);
		oWebForm.setComboId(zComboId);
		oWebForm.ensureIsBuilt();
		// oWebForm.setParent(null);
		return oWebForm;
	}

	@Override
	public String getFormName() throws Exception {
		return getBaseForm().getName()==null?super.getFormName():getBaseForm().getName();
	}
	
	public String getProviderName() throws Exception {
		return getBaseForm().getName()==null?super.getFormName():getBaseForm().getName();
  }
	public String getComboId() {
		return comboId;
	}

	public void setComboId(String comboId) {
		this.comboId=comboId;
	}
	public boolean isMainPanel() {
		return false;
	}
	@Override
	protected void build() throws Exception {
		this.createControls(this.comboId);
		oBaseForm.setControles(this.getControls());

		JWebViewZoneRow oRootPanel=new JWebViewZoneRow();
		oRootPanel.setName("combo_root_panel");
		oRootPanel.addChild("win_form", this);

		setRootPanel(oRootPanel);

	}

	@Override
	protected JWebViewComposite getWebComponentParentForCreate(JFormControl winControl) {
		if (winControl.getFixedProp()==null) return null;
		if (winControl.getFixedProp().GetCampo().equals(comboId)) return this;
		else return null;

	}

	@Override
	protected boolean canCreateControl(JFormControl formControl) throws Exception {
		return !(formControl.getComponent() instanceof JTabbedPane);
	}

	protected void createLabels(JPanel panel) throws Exception {
	}

}
