package pss.common.issueTracker.issue;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;


public class FormIssue extends JBaseForm {

	private static final long serialVersionUID=1L;

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormIssue() throws Exception {

	}

	public GuiIssue GetWin() {
		return (GuiIssue) this.getBaseWin();
	}


	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		JFormControlResponsive oCtrl;

		JFormPanelResponsive panel = this.AddItemColumn(12);
		panel.setFormatFields(JFormPanelResponsive.FIELD_LABEL_HORIZONTAL_LEFT);
		panel.setSizeLabels(3);
		JFormPanelResponsive col1 = this.AddItemColumn(6);
		col1.setFormatFields(JFormPanelResponsive.FIELD_LABEL_HORIZONTAL_LEFT);
		col1.setSizeLabels(3);
		JFormPanelResponsive col2 = this.AddItemColumn(6);
		col2.setFormatFields(JFormPanelResponsive.FIELD_LABEL_HORIZONTAL_LEFT);
		col2.setSizeLabels(3);

		
		col1.AddItemEdit("issue_id", ULONG, OPT, "issue_id").hide();
		col1.AddItemEdit("company", CHAR, REQ, "company").hide();

		col1.AddItemEdit("Título",CHAR, REQ, "summary");
		oCtrl =col1.AddItemArea("Descripción", CHAR, REQ, "description");
		oCtrl.setHeight(200);
		oCtrl = col1.AddItemEdit("Creado por", CHAR, OPT, "usuario");
		oCtrl.SetValorDefault(BizUsuario.getUsr().GetUsuario());
		oCtrl.SetReadOnly(true);

		col2.AddItemEdit("Estado", CHAR, REQ, "descr_status").setReadOnly(true);
		oCtrl =col2.AddItemArea("Ultima Nota", CHAR, OPT, "last_note").setReadOnly(true);
		oCtrl.setHeight(200);
		col2.AddItemEdit("Responsable", CHAR, OPT, "assigned_to").setReadOnly(true);
		
		//panel.AddItemEdit("Severidad", CHAR, REQ, "severity");
		
		JFormPanelResponsive row = this.AddItemRow();
		row.setHeight(30);
		
		JFormPanelResponsive paneltab = this.AddItemColumn(12);
		JFormTabPanelResponsive tab = paneltab.AddItemTabPanel();
		tab.AddItemListOnDemand( 200);    
		tab.AddItemListOnDemand( 210);
		tab.AddItemListOnDemand( 220);
	}
	

  
	
} // @jve:decl-index=0:visual-constraint="10,10"
