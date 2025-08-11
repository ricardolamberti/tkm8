package pss.core.winUI.responsiveControls;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormControles;
import pss.core.winUI.forms.JBaseForm;

public class JFormDropDownComboResponsive extends JFormComboResponsive {

	public JFormDropDownComboResponsive() throws Exception {
		super();
	}

	String idActionNew;
	public String getIdActionNew() {
		return idActionNew;
	}

	long sizeRows=10;
	boolean withNew = true;

	public boolean isWithNew() {
		return withNew;
	}

	public JFormDropDownComboResponsive setWithNew(boolean withAlta) {
		this.withNew = withAlta;
		return this;
	}
	public long getSizeRows() {
		return sizeRows;
	}

	public JFormDropDownComboResponsive setSizeRows(long sizeRows) {
		this.sizeRows = sizeRows;
		return this;
	}

	JBaseForm localForm;

	public JFormDropDownComboResponsive setIdActionNew(String action) {
		this.idActionNew = action;
		return this;
	}

	// -------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------//

	public JWin getBaseWin() throws Exception {
		return getControls().getBaseForm().getBaseWin();
	}
	public JBaseForm getForm() throws Exception {
		return getControls().getBaseForm();
	}

	BizAction actionNew;
	public BizAction getActionNew() throws Exception {
		if (actionNew!=null) return actionNew;
		JWins wins = getWins(true);
		BizAction a=wins.findActionByUniqueId(idActionNew);
		return actionNew=a;
	}
	
	
	public JAct getAct() throws Exception {
		JWins wins = getWins(false);
		JAct act=wins.getSubmit(getActionNew());
		if (act==null) return null;
		act.setActionSource(actionNew);
		return act;
	}

	public JWin getWin() throws Exception {
		JWin win = (JWin)getAct().getResult();
		win.getRecord().setRowId(getBaseWin().getRecord().getRowId());
		return win;
	}
	
	public JFormControl findControl(String row,String zName) throws Exception {
		return super.findControl(row, zName);
	}

	public JFormControles getControlesAction() throws Exception {
		if (localForm!=null) return localForm.getControles() ;
		
		JAct act=getAct();
		if (act==null) return null;
		if (act.isWins()) return null;
		act.markFieldChanged(null,getForm().getOwnerAction().getWinLovRowId(), getForm().getOwnerAction().getFieldChanged());
		act.getActionSource().setRow(getForm().getIdRow());
		JBaseForm form =act.getFormEmbedded();
		form.build();
	//	form.getInternalPanel().AddItemButton("Save", getIdAction(), true);
		form.setIdRow(getForm().getIdRow());
		form.Do(JBaseForm.MODO_ALTA);
		localForm=form;
		return form.getControles();
	}
	
	public JBaseForm getDropDownForm() throws Exception {
		return localForm;
	}
}
