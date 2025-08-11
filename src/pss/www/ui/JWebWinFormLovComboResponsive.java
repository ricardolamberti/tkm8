package pss.www.ui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import pss.core.win.submits.JAct;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormControles;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.lists.JFormFiltro;
import pss.www.platform.content.generators.JXMLContent;

public class JWebWinFormLovComboResponsive  extends JWebWinForm implements JWebActionOwnerProvider {

	String comboId;

	public JWebWinFormLovComboResponsive(JBaseForm zBaseForm) {
		super(zBaseForm);
	}
	public static JWebWinFormLovComboResponsive createCombo(JFormFiltro filters, JAct submit, String zComboId) throws Exception {
	  JBaseForm f = new JBaseForm();
	  f.setName("filter_pane_"+filters.getName());
	  f.SetModo(JBaseForm.MODO_MODIFICACION);
	  f.setControles(filters.GetControles());
		JWebWinFormLovComboResponsive oWebForm=new JWebWinFormLovComboResponsive(f);
    oWebForm.setSourceAction(submit.getActionSource());
		oWebForm.setComboId(zComboId);
		oWebForm.ensureIsBuilt();
		return oWebForm;
	}
	public static JWebWinFormLovComboResponsive createCombo(JBaseForm zBaseForm, JAct submit, String zComboId) throws Exception {
		JWebWinFormLovComboResponsive oWebForm=new JWebWinFormLovComboResponsive(zBaseForm);
    oWebForm.setSourceAction(submit.getActionSource());
		oWebForm.setComboId(zComboId);
		oWebForm.ensureIsBuilt();
		return oWebForm;
	}
	public static JWebWinFormLovComboResponsive createCombo(JFormControles zBaseForm,JAct submit, String zComboId) throws Exception {
	  JBaseForm f = new JBaseForm();
	  f.setName("temporal_form");
	  f.SetModo(JBaseForm.MODO_MODIFICACION);
	  f.setControles(zBaseForm);
		JWebWinFormLovComboResponsive oWebForm=new JWebWinFormLovComboResponsive(f);
    oWebForm.setSourceAction(submit.getActionSource());
		oWebForm.setComboId(zComboId);
		oWebForm.ensureIsBuilt();
		return oWebForm;
	}

	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
		zContent.setAttribute("obj_provider", getProviderName());
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
		return this;

	}

	@Override
	protected boolean canCreateControl(JFormControl formControl) throws Exception {
		return !(formControl.getComponent() instanceof JTabbedPane);
	}

	protected void createLabels(JPanel panel) throws Exception {
	}

}
