package pss.www.platform.actions.resolvers;

import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFieldSwapWins;
import pss.core.win.submits.JActModify;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActWins;
import pss.core.win.submits.JActWinsSelect;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.responsiveControls.JFormWinLOVResponsive;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.applications.JHistoryProvider;

public class JDoWinLovActionResolver extends JDoPssActionResolver {

	JAct submitOriginal;
	JAct submit;
	JAct act;
	JFormControl ctrlLov;
	JBaseWin actionOwner; 
	BizAction action;
	
	@Override
	protected String getBaseActionName() {
		return "winLov";
	}

	@Override
	protected boolean isResetRegisteredObjects() {
		return false;
	}

	@Override
	protected boolean isActionHistoryAffected() throws Exception {
		return true;
	}

	@Override
	protected boolean isNewRecordEnabled() {
		return false;
	}

	private String controlId;
	private String sWinLovRowId;

	@Override
	protected boolean isAjax() {
		return true;
	}

	@Override
	public BizAction resolveAction(JBaseWin zActionOwner) throws Exception {
		return null;
	}

	public void controlsToBD(JWebActionData data, JRecord record) throws Exception {
		String sourceId = this.getRequest().getArgument("dg_formLov_id");
//		controlId = sourceId.substring(sourceId.lastIndexOf('-') + 1);
		sWinLovRowId = sourceId.indexOf("-") == -1 ? "" : sourceId.substring(0, sourceId.lastIndexOf("-"));
		controlId = sourceId.indexOf("-") == -1 ? sourceId : sourceId.substring(sourceId.lastIndexOf("-") + 1);

		if (sWinLovRowId.indexOf("_row_") == -1 && sWinLovRowId.indexOf("_card_") == -1)
			sWinLovRowId = null;

		super.controlsToBD(data, record);
	}

	protected JAct getAct() throws Exception {
	//	if (getSession().getIdRequest() == 0) {
			return (JAct) getSession().getHistoryManager().getLastSubmit();
//		}
//		return (JAct) getSession().getRegisteredRequestObject(getSession().getIdRequest(), "action");
	}
	
//	protected void isOldWinLovForWin() throws Exception {
//		JWinLOV winLov = (JWinLOV) ctrlLov;
//		boolean multi = this.getRequest().getArgument("dg_multiple").equals("true");
//		JWin listener = submit.getWinResult();
//
//		JActModify lastAction = (JActModify) submit;
//		lastAction.setResult(actionOwner);
//		lastAction.markFieldChanged(null, sWinLovRowId, controlId);
//		listener.setDropControlIdListener(lastAction);
//
//		JActModify lastActionOriginal = (JActModify) submitOriginal;
//		listener.setSubmitListener(lastActionOriginal);
//
//		if (actionOwner instanceof JWin)
//			winLov.getControlWins().setRecord(((JWin) actionOwner).getRecord());
//
//		JWins list = winLov.getControlWins().getRecords(false);
//		list.setModeWinLov(true);
//		act = new JActWinsSelect(list, listener, multi);
//		act.getActionSource().setObjOwner(list);
//		act.getActionSource().setModal(submitOriginal.getActionSource().isModal());
//
//	}
	
	protected void isWinLovResponsiveForWin() throws Exception {
		JFormWinLOVResponsive winLov = (JFormWinLOVResponsive) ctrlLov;
		boolean multi = this.getRequest().getArgument("dg_multiple").equals("true");
		JWin listener = submit.getWinResult();

		JActModify lastAction = (JActModify) submit;
		lastAction.setResult(actionOwner);
		lastAction.markFieldChanged(null, sWinLovRowId, controlId);
		listener.setDropControlIdListener(lastAction);

//		JActModify lastActionOriginal = (JActModify) submitOriginal;
//		listener.setSubmitListener(lastActionOriginal);

		JWins list;
		if (winLov.getControlWins() != null) {
			if (actionOwner instanceof JWin)
				winLov.getControlWins().setRecord(((JWin) actionOwner).getRecord());
			list = winLov.getControlWins().getRecords(ctrlLov,false);
		} else
			list = winLov.getWinsFromAction();
		list.setModeWinLov(true);
		act = new JActWinsSelect(list, listener, multi);
		act.getActionSource().setObjOwner(list);
		act.getActionSource().setModal(submitOriginal.getActionSource().isModal());

	}
	
//	protected void isOldWinLovForWins(JFormFiltro filtros) throws Exception {
//		JWinLOV winLov = (JWinLOV) ctrlLov;
//		boolean multi = this.getRequest().getArgument("dg_multiple").equals("true");
//		JWins listener = submit.getWinsResult();
//
//		JActWins lastAction = (JActWins) submit;
//		lastAction.setResult(actionOwner);
//		lastAction.markFieldChanged(null, sWinLovRowId, controlId);
//		listener.setDropControlIdListener(lastAction);
//
//		if (actionOwner instanceof JWin)
//			winLov.getControlWins().setRecord(((JWin) actionOwner).getRecord());
//		else
//			filtros.applyFilterMap(act.getActionSource(), false);
//
//		JWins list = winLov.getControlWins().getRecords(ctrlLov,false);
//		list.setModeWinLov(true);
//		act = new JActWinsSelect(list, listener, multi);
//		act.getActionSource().setObjOwner(list);
//		act.getActionSource().setModal(submitOriginal.getActionSource().isModal());
//
//	}		
	
	protected void isWinLovResponsiveForWinsMultiple(JFormFiltro filtros,JFormWinLOVResponsive winLov,boolean multi) throws Exception {
		JWins listener = submit.getWinsResult();
		JHistoryProvider prov = this.getSession().getHistoryManager().getLastHistory().findProvider(getRequest().getTableProvider());

		if (submit instanceof JActFieldSwapWins) {
			JActFieldSwapWins lastAction = (JActFieldSwapWins) submit;
			lastAction.setResult(actionOwner);
			lastAction.setSelecteds(((JActFieldSwapWins) submit).getSelecteds());
			lastAction.markFieldChanged(null, sWinLovRowId, controlId);
			listener.setDropControlIdListener(lastAction);
		} else if (submit instanceof JActWins) {
			JActWins lastAction = (JActWins) submit;
			lastAction.setResult(actionOwner);
			lastAction.markFieldChanged(null, sWinLovRowId, controlId);
			listener.setDropControlIdListener(lastAction);
		} else {
			JActQuery lastAction = (JActQuery) submit;
//	  	lastAction.setResult(actionOwner);
			lastAction.markFieldChanged(prov.getAction(), sWinLovRowId, controlId);
			listener.setDropControlIdListener(lastAction);

		}
		filtros.applyFilterMap(prov.getActionSubmit().getActionSource(), false);
	  

		JWins list;
		if (winLov.getControlWins() != null) {
			if (actionOwner instanceof JWin)
				winLov.getControlWins().setRecord(((JWin) actionOwner).getRecord());
			list = winLov.getControlWins().getRecords(ctrlLov,false);
		} else
			list = winLov.getWinsFromAction();
		
		list.setModeWinLov(true);
		act = new JActWinsSelect(list, listener, multi);
		act.getActionSource().setObjOwner(list);
		act.getActionSource().setModal(submitOriginal.getActionSource().isModal());
	}
	
	protected void isWinLovResponsiveForWinsSingle(JFormFiltro filtros,JFormWinLOVResponsive winLov,boolean multi) throws Exception {
		JBaseWin listener = submit.getWinResult();
		JAct lastAction = submit;

		JHistoryProvider prov = this.getSession().getHistoryManager().getLastHistory().findProvider(getRequest().getTableProvider());

		if (prov != null) {
			JAct lastTabAction = prov.getActionSubmit();
			lastTabAction.setResult(actionOwner);
			lastTabAction.markFieldChanged(prov.getAction(), sWinLovRowId, controlId);
			listener = lastTabAction.getWinsResult();
			listener.setDropControlIdListener(lastTabAction);
		} else {
			lastAction.setResult(actionOwner);
			lastAction.markFieldChanged(prov.getAction(), sWinLovRowId, controlId);
			listener.setDropControlIdListener(lastAction);
		}
		listener.setSubmitListener(submitOriginal);

		filtros.applyFilterMap(prov.getActionSubmit().getActionSource(), false);

		JWins list;
		if (winLov.getControlWins() != null) {
			if (actionOwner instanceof JWin)
				winLov.getControlWins().setRecord(((JWin) actionOwner).getRecord());

			list = winLov.getControlWins().getRecords(ctrlLov,false);
		} else
			list = winLov.getWinsFromAction();
		
		
		list.setModeWinLov(true);
		act = new JActWinsSelect(list, listener, multi);
		act.getActionSource().setObjOwner(list);
		act.getActionSource().setModal(submitOriginal.getActionSource().isModal());

	}
	
	
	
	
	protected void isWinLovResponsiveForWins(JFormFiltro filtros) throws Exception {
		JFormWinLOVResponsive winLov = (JFormWinLOVResponsive) ctrlLov;
		boolean multi = this.getRequest().getArgument("dg_multiple").equals("true");
		if (submit.getResult() instanceof JWins) {
			isWinLovResponsiveForWinsMultiple(filtros,winLov,multi);
		} else {
			isWinLovResponsiveForWinsSingle(filtros,winLov,multi);
		}
	}
	protected void isWin() throws Exception {
		submit.setResult(actionOwner);
		JBaseForm baseForm = submit.getForm();
		baseForm.build();
		if (sWinLovRowId!=null)
			baseForm.generate();
		//	baseForm.getControles().BaseToControl(baseForm.GetModo(), false);

		ctrlLov = (JFormControl) baseForm.getControles().findControl(sWinLovRowId, controlId);
//		if (ctrlLov instanceof JWinLOV) {
//			isOldWinLovForWin();
//		} else {
			isWinLovResponsiveForWin();
//		}

	}
	protected void isWins() throws Exception {
		String sourceId = this.getRequest().getArgument("dg_formLov_id");
		controlId = sourceId.substring(sourceId.lastIndexOf('-') + 1);

		JFormFiltro filtros = new JFormFiltro((JWins) actionOwner);
		filtros.initialize();
//		filtros.convertToResponsive();
		ctrlLov = filtros.GetControles().findControl(sWinLovRowId, controlId);

//		if (ctrlLov instanceof JWinLOV) {
//			isOldWinLovForWins(filtros);
//		} else {
			isWinLovResponsiveForWins(filtros);
//		}

	}

	@Override
	protected JAct getSubmit(JBaseWin zactionOwner, BizAction zaction) throws Exception {
		submitOriginal = (JAct) this.getSession().getHistoryManager().getLastHistorySubmit();
		submit = (JAct) submitOriginal.clone();

		act = null;
		actionOwner= zactionOwner;
		action=zaction;
	
		if (actionOwner.isWin()) {
			isWin();
		} else {
			isWins();
		}

		return act;
	}
}
