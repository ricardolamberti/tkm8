/*
 * Created on 23-ago-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import javax.swing.JPanel;

import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;
import pss.www.platform.applications.JHistoryProvider;


public class JWebWinFormNotebook extends JWebWinForm implements JWebActionOwnerProvider {

	
	public JWebWinFormNotebook(JBaseForm zBaseForm) {
		super(zBaseForm);
	}

	public static JWebWinFormNotebook createFormNotebook(JHistoryProvider hp) throws Exception {
		JBaseForm form = hp.getAction().getObjSubmit().getForm();
		if (form==null) return null;
    form.setEmbedded(true);
    JWebWinFormNotebook oWebForm = new JWebWinFormNotebook(form);
    oWebForm.setSourceAction(hp.getAction());
    oWebForm.ensureIsBuilt();
    return oWebForm;
  }

	private String sProviderName=null;
	public void setProviderName(String value) {
		sProviderName=value;
	}
	@Override
	public String getProviderName() throws Exception {
		if (this.sProviderName!=null) return this.sProviderName;
		return super.getProviderName();
	}


  @Override
	protected boolean canCreateControl(JFormControl formControl) throws Exception {
  	return formControl instanceof JFormTabPanelResponsive;
  }
	
 
  protected void createLabels(JPanel panel) throws Exception {  }
	
}
