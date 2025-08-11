package pss.core.winUI.responsiveControls;

import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormControles;
import pss.core.winUI.forms.JBaseForm;
import pss.www.platform.actions.JWebActionFactory;

public class JFormDropDownWinLovResponsive extends JFormWinLOVResponsive{
	public JFormDropDownWinLovResponsive(boolean zMultiple, boolean zShowsInMultipleLines) {
		super(zMultiple, zShowsInMultipleLines);
	}
	JWin win;
	


	public JWin getWin() {
		return win;
	}

	public void setWin(JWin win) {
		this.win = win;
	}
	String idActionNew;
	String idActionUpdate;
	public String getIdActionUpdate() {
		return idActionUpdate;
	}

	public JFormDropDownWinLovResponsive setIdActionUpdate(String idActionUpdate) {
		this.idActionUpdate = idActionUpdate;
		return this;
	}

	public String getIdActionNew() {
		return idActionNew;
	}
	
	

	long sizeRows=10;
	boolean withNew = true;
	boolean withUpdate = true;

	boolean isOpen = false;
	Boolean isOpenInternal;
	String zobject;

	public String getZObject() {
		return zobject;
	}

	public boolean getIsOpen() {
		if (isOpenInternal!=null)
			return isOpenInternal;
		return isOpen;
	}

	public JFormDropDownWinLovResponsive setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
		return this;
	}

	public boolean isWithNew() {
		return withNew;
	}
	public boolean isWithUpdate() {
		return withUpdate;
	}

	public JFormDropDownWinLovResponsive setWithUpdate(boolean withUpdate) {
		this.withUpdate = withUpdate;
		return this;
	}
	public JFormDropDownWinLovResponsive setWithNew(boolean withAlta) {
		this.withNew = withAlta;
		return this;
	}
	public long getSizeRows() {
		return sizeRows;
	}

	public JFormDropDownWinLovResponsive setSizeRows(long sizeRows) {
		this.sizeRows = sizeRows;
		return this;
	}



	public JFormDropDownWinLovResponsive setIdActionNew(String action) {
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
		JWins wins = buildWins();
		BizAction a=wins.findActionByUniqueId(idActionNew);
		return actionNew=a;
	}
	
	public JWins buildWins() throws Exception {
		JWins aWins;
		if (getControlWins()==null) {
			aWins = (JWins)getWinsFromAction();
		}
		else {
			aWins= (JWins)getControlWins().getRecords(false);
		}
		
		return aWins;
	}
	
	public void onAddToForm() throws Exception {
		String saved =getWin().getRecord().getExtraData(getName()+"_saved");
		if (saved!=null&& saved.equals("S")) {
			isOpenInternal=false;
			zobject=null;
			return;
		}
		String localObject = getWin().getRecord().getExtraData(getName()+"_zobject");
		if (!(localObject==null || localObject.equals("")))
			zobject=localObject;

		String isOpen = getWin().getRecord().getExtraData(getName()+"_isopen");
		if (!(isOpen==null || isOpen.equals("")))
			isOpenInternal=(isOpen.equals("S"));
	}
	JAct objAct;
	public JAct getActNew() throws Exception {
		if (objAct!=null && objAct.getResult()!=null) return objAct;
		JWins wins =  buildWins();
		JAct act=wins.getSubmit(getActionNew());
		if (act==null) return null;
		if (getZObject()!=null) {
			JWin win = (JWin)JWebActionFactory.getCurrentRequest().getRegisterObject(getZObject());
			if (win!=null)
				act.setResult(win);
		}
		act.setActionSource(getActionNew());
		return objAct=act;
	}

	public JWin getWinNew() throws Exception {
		JWin win = null;
		if (getZObject()!=null)
			win = ((JWin)JWebActionFactory.getCurrentRequest().getRegisterObject(getZObject()));
		if (win!=null) return win;
		win = (JWin)getActNew().getResult();
		win.getRecord().setRowId(getBaseWin().getRecord().getRowId());
		return win;
	}
	
	
	public JFormControl findControl(String row,String zName) throws Exception {
		JFormControl c = super.findControl(row,zName);
		if (c!=null) return c;
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getControlesAction().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			JFormControl find = oCon.findControl(row,zName);
			if (find!=null) return find;
		}
		return null;
	}
	JBaseForm localForm;
	public JFormControles getControlesAction() throws Exception {
		if (localForm!=null) return localForm.getControles() ;
		JAct act=getActNew();
		if (act==null) return null;
		if (act.isWins()) return null;
		act.setForceExitOnOk(true);
		act.markFieldChanged(null,getForm().getOwnerAction().getWinLovRowId(), getForm().getOwnerAction().getFieldChanged());
		act.getActionSource().setRow(getForm().getIdRow());
		JBaseForm form =act.getFormEmbedded();
		form.build();
	  form.setIdRow(getForm().getIdRow());
		form.Do(JBaseForm.MODO_ALTA);
		localForm=form;
		return form.getControles();
	}
	public JBaseForm getDropDownForm() throws Exception {
		return localForm;
	}

}
