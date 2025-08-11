package pss.core.winUI.controls;

import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabResponsive;



public class JFormForm extends JFormTabResponsive {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JBaseForm oForm;
	private BizAction oAction;
	private JFormTabPanelResponsive oTabPane;
	private JWin oWin;
	private boolean bFilterBar=true;
	private boolean bToolBar=true;
	private boolean bListChanged=false;
	boolean diferido;
	boolean withActions = true;
	
	public boolean isWithActions() {
		return withActions;
	}

	public JFormForm setWithActions(boolean withActions) {
		this.withActions = withActions;
		return this;
	}

	public boolean isDiferido() {
		return diferido;
	}

	public JFormForm setDiferido(boolean diferido) {
		this.diferido = diferido;
		return this;
	}
	public JFormControles getControlesAction() throws Exception {
		JAct act=getWin().getSubmit(oAction);
		if (act==null) return null;
		if (act.isWins()) return null;
		act.markFieldChanged(null,oForm.getOwnerAction().getWinLovRowId(), oForm.getOwnerAction().getFieldChanged());
		JBaseForm form =  act.getFormEmbedded();
		form.build();

		
		form.Detalle();
		return form.getControles();
	}
	// -------------------------------------------------------------------------//
	// Metodos de acceso a las Propiedades de la Clase
	// -------------------------------------------------------------------------//
	public void setWin(JWin zValue) {
		oWin=zValue;
	}

	public JWin getWin() {
		return oWin;
	}

	public void setTabPane(JFormTabPanelResponsive zValue) {
		oTabPane=zValue;
	}


	public void setAction(BizAction zValue) {
		oAction=zValue;
	}

	public void setForm(JBaseForm form) {
		oForm=form;
	}

	public JBaseForm GetForm() {
		return oForm;
	}
	
//	public JPanel GetFormPanel() {
//		return oForm.getRootPanel();
//	}


	public BizAction getAction() {
		return this.oAction;
	}

	// -------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------//
	public JFormForm() {
	}

	// -------------------------------------------------------------------------//
	// Crear Lista
	// -------------------------------------------------------------------------//

	// -------------------------------------------------------------------------//
	// Blanqueo el campo
	// -------------------------------------------------------------------------//
	@Override
	public void clear() {
	}

	// -------------------------------------------------------------------------//
	// Protejo el campo
	// -------------------------------------------------------------------------//
	@Override
	public void disable() throws Exception {
		if (oForm == null)
			return ;
//		oForm.setEnabled(true);
	}

	// -------------------------------------------------------------------------//
	// Seteo el foco en el campo
	// -------------------------------------------------------------------------//
//	@Override
//	public void SetFocus() {
//	}

	// -------------------------------------------------------------------------//
	// Edito el campo
	// -------------------------------------------------------------------------//
	@Override
	public void edit(String zModo) throws Exception {
		if (zModo==MODO_ALTA) {
			if (!(oTabPane==null||!oTabPane.hasTabs())) {
				if (!this.addPanelToTab()) return;
				oForm.SetModo(zModo);
				return;
			}
		}
		oForm.SetModo(zModo);

	}

	// -------------------------------------------------------------------------//
	// Obtengo el valor del campo
	// -------------------------------------------------------------------------//
	@Override
	public String getSpecificValue() {
		return null;
	}

	// -------------------------------------------------------------------------//
	// Obtengo el valor del campo
	// -------------------------------------------------------------------------//
	@Override
	public boolean hasValue() {
		return true;
	}

	public BizAction getCheckedAction() throws Exception {
		return oWin.findActionByUniqueId(oAction.getIdAction());
	}

	private boolean addPanelToTab() throws Exception {
		oTabPane.removeTab(oAction.getIdAction());
		BizAction action=this.getCheckedAction();
		if (action==null) return false;
		oTabPane.addTab(oAction.getIdAction(), this);

		return true;
	}

	// -------------------------------------------------------------------------//
	// De la base al control
	// -------------------------------------------------------------------------//
	@Override
	public void BaseToControl(String zModo, boolean userRequest) throws Exception {
		if (!this.addPanelToTab()) return;
		if (!userRequest) return;
		oForm.SetModo(zModo);
		oForm.Refresh(userRequest);
	}

	@Override
	public void Remove() throws Exception {
	}
}
