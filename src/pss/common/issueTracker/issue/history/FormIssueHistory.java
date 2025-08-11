package pss.common.issueTracker.issue.history;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;


public class FormIssueHistory extends JBaseForm {

	private static final long serialVersionUID=1L;

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormIssueHistory() throws Exception {

	}

	public GuiIssueHistory GetWin() {
		return (GuiIssueHistory) this.getBaseWin();
	}

	

	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		SetExitOnOk(true);
		JFormControlResponsive oCtrl;
		
		JFormPanelResponsive panel = this.AddItemColumn(6);
		panel.setFormatFields(JFormPanelResponsive.FIELD_LABEL_HORIZONTAL_LEFT);
		panel.setSizeLabels(3);
		panel.AddItemEdit("company", CHAR, REQ, "company").hide();
		panel.AddItemEdit("Nro. de Issue", ULONG, OPT, "issue_id").SetReadOnly(true);
		
		oCtrl =panel.AddItemArea("Descripción", CHAR, REQ, "description");
		oCtrl.setHeight(200);

		panel.AddItemEdit("Usuario", CHAR, OPT, "descr_usuario").SetReadOnly(true);
	}
	
	
} // @jve:decl-index=0:visual-constraint="10,10"
