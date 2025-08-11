package pss.core.winUI.responsiveControls;

import java.io.Serializable;

import pss.core.services.fields.JObjBD;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JObject;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormControles;
import pss.core.winUI.forms.JBaseForm;
import pss.www.platform.actions.JWebActionFactory;

public class JFormCardResponsive extends JFormPanelResponsive  {

	BizAction sourceAction;
	boolean diferido;
	boolean withActions = true;
	boolean withHeader = true;
	String zobject;
	String field;
	Class classField;
	String fieldActionId;
  private Serializable data; //datos libre para enviar dentro de un submit
	
	public Serializable getData() {
		return data;
	}

	public JFormCardResponsive setData(Serializable data) {
		this.data = data;
		return this;
	}


	public String getFieldActionId() {
		return fieldActionId;
	}

	public void setFieldActionId(String fieldActionId) {
		this.fieldActionId = fieldActionId;
	}

	public String getZObject() {
		return zobject;
	}


	JBaseForm localForm;

	public boolean isWithActions() {
		return withActions;
	}

	public JFormCardResponsive setWithActions(boolean withActions) {
		this.withActions = withActions;
		return this;
	}

	public boolean isDiferido() {
		return diferido;
	}

	public JFormCardResponsive setDiferido(boolean diferido) {
		this.diferido = diferido;
		return this;
	}

	public BizAction getAction() {
		return sourceAction;
	}
	
	public boolean isWithHeader() {
		return withHeader;
	}

	public JFormCardResponsive setWithHeader(boolean withHeader) {
		this.withHeader = withHeader;
		return this;
	}

	public JFormCardResponsive setAction(BizAction action) {
		this.sourceAction = action;
		return this;
	}

	// -------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------//
	public JFormCardResponsive() {
		setResponsiveClass("");
	}
	
	public JWins getWins() throws Exception {
		JAct act=getBaseWin().getSubmit(sourceAction);
		if (act==null) return null;
		if (act.isWin()) return null;
		JWins wins =  (JWins)act.getResult();
		return wins;
	}

	public JWin getWinCard() throws Exception {
		JAct act=getBaseWin().getSubmit(sourceAction);
		if (act==null) return null;
		if (act.isWins()) return null;
		JWin win = (JWin)act.getResult();
		win.getRecord().setRowId(getBaseWin().getRecord().getRowId());
		return win;
	}
	
	public JFormControl findControl(String row,String zName) throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getControlesAction().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			JFormControl find = oCon.findControl(row,zName);
			if (find!=null) return find;
		}
		return null;
	}
	
	public String getField() {
		return field;
	}

	public JFormCardResponsive setField(String field) {
		this.field = field;
		return this;
	}
	
	public Class getClassField() {
		return classField;
	}

	public JFormCardResponsive setClassField(Class classField) {
		this.classField = classField;
		return this;
	}

	@Override
	public JWin getBaseWin() {
		return super.getBaseWin();
	}
	boolean isNew=false;
	public JAct buildAct() throws Exception {
		JAct act = null;

		if (getField()==null) {
			if (sourceAction==null) return null;
			if (getData()!=null) {
				sourceAction= sourceAction.createClone();
				sourceAction.setData(getData());
			}
			act = getBaseWin().getSubmit(sourceAction);
			if (act==null || act.getResult()==null) return null;
			if (getZObject()!=null) 
				act.setResult((JWin)JWebActionFactory.getCurrentRequest().getRegisterObject(getZObject()));
		} else {
			JWin win = (JWin)getClassField().newInstance();
			JObject obj = getBaseWin().getRecord().getProp(getField());
			JRecord rec;
			if (obj.isRecords()) {
				JRecords recs = ((JObjBDs)obj).getValue();
				JIterator it=recs.getStaticIterator();
				if (it.hasMoreElements())
					rec = (JRecord)it.nextElement();
				else
					return null;
			} else
				rec = ((JObjBD)getBaseWin().getRecord().getProp(getField())).getValue();
			if (rec!=null)	win.setRecord(rec);
			isNew = win.GetValorItemClave().equals("");//truco
			BizAction action = win.findActionByUniqueId(getFieldActionId());
			if (getData()!=null) {
				action = action.createClone();
				action.setData(getData());
			}
			act= action.getObjSubmit();
			if (act==null) return null;
			act.setActionSource(action);
			sourceAction=action;
  	}
		if (act.isWins()) return null;
		act.setActionSource(sourceAction);
		act.markFieldChanged(null,getForm().getOwnerAction().getWinLovRowId(), getForm().getOwnerAction().getFieldChanged());
		act.getActionSource().setRow(getForm().getIdRow());
		return act;
	}
	
	public JFormControles getControlesAction() throws Exception {
		if (localForm!=null) return localForm.getControles() ;
		
		JAct act=buildAct();
		if (act==null) return null;
		JBaseForm form =act.getFormEmbedded();
		form.setReRead(getForm().hasToRead());
		form.SetModo(getModo());
		form.build();
		form.setIdRow(getForm().getIdRow());
		if (isDisplayOnly()) {
			form.Detalle();
		} else {
			form.Do(isNew?JBaseForm.MODO_ALTA:getForm().GetModo());
		}
		localForm=form;
		return form.getControles();
	}


	public String getName()  {
		try {
			if (sIdControl==null) {
				if (getField()!=null)
					 return getField();
				else
					 return "card_"+sourceAction.getProviderName()+getData();
			}
			return sIdControl;
		} catch (Exception e) {
			return super.getName();
		}
	}

	public void onAddToForm() throws Exception {
		String localObject = this.getBaseWin().getRecord().getExtraData(getName()+"_zobject");
		if (!(localObject==null || localObject.equals("")))
			zobject=localObject;

	}
}