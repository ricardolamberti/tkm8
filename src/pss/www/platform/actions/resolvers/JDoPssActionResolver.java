/*
 * Created on 24-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.resolvers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import pss.common.security.BizUsuario;
import pss.core.services.fields.JObject;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;
import pss.core.tools.JMessageInfo;
import pss.core.tools.JPair;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActBack;
import pss.core.win.submits.JActDelete;
import pss.core.win.submits.JActDrop;
import pss.core.win.submits.JActExternalLink;
import pss.core.win.submits.JActFileGenerate;
import pss.core.win.submits.JActGroupSubmit;
import pss.core.win.submits.JActModify;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActQueryActive;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.win.tools.orders.BizWinsColumn;
import pss.core.win.tools.orders.GuiWinsColumns;
import pss.core.winUI.lists.JFormFiltro;
import pss.www.platform.actions.IControlToBD;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.actions.requestBundle.JWebActionDataField;
import pss.www.platform.actions.results.JWebActionResult;
import pss.www.platform.applications.JHistory;
import pss.www.platform.applications.JHistoryProvider;
import pss.www.platform.users.history.BizUserHistory;

/**
 * Resolves and executes actions originating from a {@link JWebRequest}.
 * <p>
 * The resolver is the core of the request lifecycle: it locates the target
 * window, determines the {@link BizAction} to execute and delegates the
 * processing of the underlying {@link JAct} to a chain of
 * {@link ActionResolverStrategy} implementations. Each strategy decides how to
 * handle the submit (e.g. redirects, back navigation, regular submits).
 * </p>
 * In addition, the resolver manages the registration of objects in the session,
 * keeps track of modal windows and stores user statistics about the executed
 * action.
 */
public class JDoPssActionResolver extends JIndoorsActionResolver implements IControlToBD {

	boolean resetRegisteredObjects = true;
	boolean bNoSubmit = false;
	boolean bBack = false;
	private final List<ActionResolverStrategy> actionResolvers = Arrays.asList(new RedirectActionResolver(), new LinkActionResolver(), new BackActionResolver(), new SubmitActionResolver(), new QueryActionResolver());

	public boolean resetOldObjects() throws Exception {
		return true;
	}

	@Override
	protected JWebActionResult perform() throws Throwable {
//		PssLogger.logDebug("THREAD:----------------------------> REQUEST "+getRequest().getPssIdDictionary());
//		request++;

		this.addObjectToResult("__requestid", "" + getRequest().getIdRequestJS());
//		this.addObjectToResult("__dictionary", ""+this.getSession().winRegisteredObjects(getRequest().getPssIdDictionary(),!this.isResetRegisteredObjects(),resetOldObjects()));
		JWebActionFactory.getCurrentRequest().winRegisteredObjects(winFactory, !this.isResetRegisteredObjects(), resetOldObjects());
//		this.addObjectToResult("__dictionary", JWebActionFactory.getCurrentRequest().getPack());
		JBaseWin actionOwner = this.resolveActionOwner();
		BizAction action = this.resolveAction(actionOwner);
//		this.checkMultipleActionConflict(actionOwner,action);

		this.storeContext(actionOwner, action);
		this.storeAction(actionOwner, action);

		JWebActionResult nextStep = this.performAction(actionOwner, action);

		this.endResolver();

		if (nextStep != null)
			return nextStep;
		return this.goOn();
	}

//	private void createCurrentHistory() throws Exception {
//		if (!this.isActionHistoryAffected()) return;
//		JHistory h = new JHistory();
//		this.getHistoryManager().addHistory(h);
//	}

	// cheque si se esta llamando como multiple a una accion NO multiple
//	private void checkMultipleActionConflict(JBaseWin actionOwner,BizAction action) throws Exception {
//		if (action!=null && actionOwner!=null && !(action.isMulti()||action.isGroup()) && !( (action.getObjSubmit() instanceof JActWins) || (action.getObjSubmit() instanceof JActNew) ) && actionOwner instanceof JWins) throw new Exception("accion no acepta multiples objetivos");

//	}

	private void storeAction(JBaseWin owner, BizAction action) throws Exception {
		if (this.getSession() == null)
			return;

		BizUserHistory last = this.getSession().getStadistic(getRequest().getIdRequestJS());
		if (last == null)
			return;
		last.storeAction(owner, action);
	}

	public boolean hasInfoColumnsOrder(JBaseWin owner) {
		return owner instanceof GuiWinsColumns;
	}

	protected void restoreErrorSituation(Throwable e) {
		if (this.getSession() == null)
			return;
		this.getSession().restoreErrorSituation();
		this.statsError(e);
	}

	protected boolean isModal() throws Exception {
//		getSession().getHistoryManager().getLastHistory().isJSModal()
		String ajaxcontainer = this.getRequest().getArgument("ajaxContainer");
		if (ajaxcontainer == null)
			return false;
		return ajaxcontainer.startsWith("modal_") || getRequest().inModal();
	}

	protected boolean isResetRegisteredObjects() throws Exception {
		return !this.getRequest().inModal() && resetRegisteredObjects && !JWebActionFactory.isPreserveObjectRegistered(getRequest());
	}

	protected JWebActionResult performAction(JBaseWin actionOwner, BizAction zAction) throws Throwable {
		JAct submit = this.getSubmit(actionOwner, zAction);
		JWebActionResult result = this.performAction(submit);
		adjustModalMode(zAction);
		this.addObjectToResult("transformer", this.getTransformer());
		this.addObjectToResult("serializer", this.getSerializer());
		return result;
	}

	protected void adjustModalMode(BizAction zAction) throws Exception {
		if (zAction != null)
			zAction.setModal(this.calculeIsModal());
	}

	protected JWebActionResult performAction(JAct submit) throws Throwable {

		for (ActionResolverStrategy strategy : actionResolvers) {
			if (strategy.supports(this, submit)) {
				return strategy.handle(this, submit);
			}
		}
		return this.goOn();
	}

	protected boolean isBackPageOnDelete() {
		return false;
	}

	protected boolean hasToSubmit(JAct action) throws Exception {
		if (!this.isNewRecordEnabled())
			return false;
		if (action.isOnlySubmit())
			return true;
		if (action.isQueryAction())
			return false;
		if (this.bNoSubmit)
			return false;
		if (isSubmit())
			return true;
//		String tableProvider="win_form";
//		if (getRequest().hasTableProvider()) tableProvider=getRequest().getTableProvider();
//		if (!this.getRequest().getFormData(tableProvider).isNull()) return true;
		return false;
	}

	public boolean isSubmit() throws Exception {
		if (getRequest().getArgument("issubmit") == null)
			return false;
		return getRequest().getArgument("issubmit").equals("true");
	}

	public boolean isBackAfterSubmit() throws Exception {
		if (getRequest().getArgument("isbackaftersubmit") == null)
			return true;
		return getRequest().getArgument("isbackaftersubmit").equals("true");
	}

	public void setBack(boolean value) throws Exception {
		bBack = value;
	}

	public boolean isBack() throws Exception {
		return bBack;
	}

//	protected void assignStoredTab(JHistory target) throws Exception {
////		if (!this.getRequest().getSession().getHistoryManager().hasHistory()) return;
////		this.getRequest().addModelObject("Pss.selectedTab", this.getRequest().getSession().getHistoryManager().getLastHistory().getSelectedTab());
//		this.getRequest().addModelObject("Pss.selectedTab", target.getSubAction());
//	}
//	protected void assignStatusBar() throws Exception {
//	this.getRequest().addModelObject("Pss.hideStatusBar", hasFilterPane());
//}
	private boolean backAfterSubmit() throws Exception {
		return isBackAfterSubmit();
	}

	private JAct gotoHistoryTarget(JAct submit) throws Exception {
		// this.checkSkeepHistory(submit);
		JHistory last = this.getSession().getHistoryManager().getLastHistory();
		if (backAfterSubmit() && submit.backAfterSubmit(last.getMainSumbit())) {
			last = this.getSession().getHistoryManager().backToTargetHistory(submit);
			setBack(submit.isBack());
		}
		last.clearAllSubmits();

		JAct action = last.getMainSumbit();
		action.setInHistory(true);
		checkMarkFieldChanged(submit, action);
		action.setMessage(submit.getMessage());
		this.getSession().getHistoryManager().setLastSubmit(action);

		this.getRequest().setRegisteredRequestObject("modalmustback", submit.isExitOnOk() ? "onlyClose" : "closeAndOpen");
		this.getRequest().setRegisteredRequestObject("clearChangeInputs", submit.isClearChangeInputs());

		return submit;
	}

	protected JAct getBackAct(JActBack submit) throws Exception {
		int step = submit.getBackStep();
		JHistory target = this.getSession().getHistoryManager().backOneHistory();
		if (step == 2)
			target = this.getSession().getHistoryManager().backOneHistory();
		JAct act = target.getMainSumbit();
		act.setInHistory(true);
		act.setMessage(submit.getMessage());

		Boolean clearChangeInputs = (Boolean) getRequest().getRegisteredRequestObject("clearChangeInputs");
		if (clearChangeInputs == null) {
			act.setClearChangeInputs(submit.isClearChangeInputs());
			this.getRequest().setRegisteredRequestObject("clearChangeInputs", act.isClearChangeInputs());
		}
		setBack(true);
		return act;
	}

	protected JWebActionResult processSubmit(JAct action) throws Throwable {
//		JAct nextAction=action.doSubmit(true);
//
//		if (nextAction!=null) {
//			if (action.backAfterDropControl(this.getRequest().getSession().getHistoryManager().getLastHistoryAction()))
//				this.getSession().getHistoryManager().backToTargetHistory();
//			this.bNoSubmit=true; // black flag
//			return this.performAction(nextAction);
//		} else {
//			this.gotoHistoryTarget(action);
//		}
//		return this.goOn();

		JAct nextAction = action.doSubmit(true);
		if (nextAction != null) {
			this.gotoHistoryTarget(action);
			this.bNoSubmit = true; // black flag
			return this.performAction(nextAction);
		} else {
			this.gotoHistoryTarget(action);
			if (isBackAction(action))
				setBack(true);
		}

		return this.goOn();
	}

	private boolean isBackAction(JAct action) throws Exception {
		if (action instanceof JActSubmit)
			return false;
		if (action instanceof JActGroupSubmit)
			return action.isExitOnOk();
		if (action instanceof JActDelete)
			return action.isExitOnOk();
		return true;
	}

	public void controlsToBD(JWebActionData data, JRecord record) throws Exception {

		JIterator<JWebActionDataField> iter = data.getFieldIterator();
		while (iter.hasMoreElements()) {
			JWebActionDataField field = iter.nextElement();
			if (field == null)
				continue;
			if (record == null)
				continue;
			JObject<?> prop = record.getPropDeep(field.getName(), false);
			if (prop == null) {
				record.addExtraData(field.getName(), field.getValue());
				continue;
			}
//			if (field.getName().equals("previousPriceQuantity"))
//			PssLogger.logDebug(field.getName()+"="+field.getValue());

			String value = field.getValue();
			Object obj = null;
			if (value.startsWith("obj_")) {
				if (value.startsWith("obj_t_")) {
					obj = winFactory.getRegisterObjectTemp(value.substring(6));
				} else {
					obj = JWebActionFactory.getCurrentRequest().getRegisterObject(value);
				}
			}
			if (prop.isRecord()) {
				if (obj != null && obj instanceof JWin)
					prop.setValue(record.getPropValue(field.getName(), prop, ((JWin) obj).getRecord()));
				else
					prop.setNull();
			} else if (obj != null && obj instanceof JWin)
				prop.setValue(record.getPropValue(field.getName(), prop, ((JWin) obj).GetValorItemClave()));
			else
				prop.setValueFormUI(record.getPropValue(field.getName(), prop, JTools.decodeURLIfNecesary(value)));
		}
	}

	private boolean isDrop(JAct submit) throws Exception {
		return (submit instanceof JActDrop);
	}

	protected JWebActionResult processRedirectLink(JAct submit) throws Throwable {
		JActExternalLink action = (JActExternalLink) submit;
		this.setResetRegisteredObjects(false);
		action.doSubmit(true);
		return this.getRedirector().goExternalLink(action.getUrl());
	}

	private void buildFiltros(JAct submit) throws Throwable {
		try {
			if ((submit instanceof JActFileGenerate) && !submit.getResult().isWin()) {
				JFormFiltro filtros = new JFormFiltro(submit.getWinsResult());
				filtros.initialize();
				filtros.applyFilterMap(submit.getActionSource(), false);
			}
		} catch (Exception e) {
			PssLogger.logError(e);
		}
	}

	protected JWebActionResult processRedirect(JAct submit) throws Throwable {
		JActFileGenerate action = (JActFileGenerate) submit.getFinalSubmit();
		// action.setFileName("file["+this.getSession().getId().replace(':', '-')+"]");
		this.setResetRegisteredObjects(false);
		buildFiltros(submit);
		JAct nextAction = submit.doSubmit(true);
		if (nextAction != null) {
			BizAction nextAct = nextAction.getActionSource();
			nextAction.setQueryAction(true);
			getRequest().getSession().getHistoryManager().addHistory(nextAction.getActionSource());
		}
		// this.processSubmit(action);
		this.endRedirect();
		return this.getRedirector().goWebFile(action.getProvider() + "/" + action.getFileName());
	}

	private void endRedirect() throws Exception {
		if (this.getSession() == null)
			return;
		BizUserHistory last = this.getSession().getStadistic(getRequest().getIdRequestJS());
		if (last == null)
			return;
		last.setFinalize(true);
	}

	protected boolean isNewRecordEnabled() {
		return true;
	}

	protected boolean isActionHistoryAffected() throws Exception {
		return JWebActionFactory.isHistoryObjectRegistered(this.getRequest());
	}

	@Override
	protected String getBaseActionName() {
		return "Pss.action";
	}

	protected void checkMarkFieldChanged(JAct submit, JAct action) throws Exception {
		if (submit.desactiveReread(action)) {
			// action.markFieldChanged(null, null, null);// clean by swap & drops
			action.setReRead(false);
//			return;
		}
		boolean isActSubmitInForm = submit.forceMarkFieldChange(action);
		String sourceId = this.getRequest().getArgument("dg_source_control_id");
		if (sourceId == null || sourceId.equals("")) {
			if (isActSubmitInForm) // es un boton dentro de un form. que refresca sobre el mismo form
				action.markFieldChanged(null, null, submit.getActionUniqueId());

			return;
		}
		String controlId = sourceId.substring(sourceId.lastIndexOf('-') + 1);
		String sWinLovRowId = sourceId.indexOf("-") == -1 ? "" : sourceId.substring(0, sourceId.indexOf("-"));
		controlId = sourceId.indexOf("-") == -1 ? sourceId : sourceId.substring(sourceId.indexOf("-") + 1);
		if (action instanceof JActModify)
			((JActModify) action).markFieldChanged(null, sWinLovRowId, controlId);
	}

	protected JAct getSubmit(JBaseWin actionOwner, BizAction action) throws Exception {
		JAct submit;
		if (actionOwner != null)
			actionOwner.preRefresh();
		if (action != null) {
			return action.getObjSubmit();
		}

		if (actionOwner instanceof JWin)
			submit = new JActQuery((JWin) actionOwner);
		else
			submit = new JActWins(actionOwner);

		if (actionOwner == null)
			JExcepcion.SendError("ERROR en getSubmit: null null ERROR");
		submit.setMessage((JMessageInfo) null);
		submit.setData(resolveData());
		return submit;
	}

	public JBaseWin resolveActionOwner() throws Exception {
		JBaseWin actionOwner = this.resolveMainActionOwner();
		actionOwner = this.verifyMultipleAction(actionOwner);
		if (actionOwner != null && this.getRequest().hasSelectedCell())
			actionOwner.setSelectedCell(this.getRequest().getSelectedCell());
		return actionOwner;
	}

	public JHistoryProvider findHistoryProvider() throws Exception {
		if (getRequest().getTableProvider() == null)
			PssLogger.logDebug("check ppoint");
		return this.getSession().getHistoryManager().getLastHistory().findProviderWithinLevel(this.getRequest().getTableProvider());
	}

	protected JBaseWin verifyMultipleAction(JBaseWin baseWin) throws Exception {
		if (!this.getRequest().hasMultipleObjectOwnerList())
			return baseWin;
		if (baseWin instanceof JWin)
			JExcepcion.SendError("Función no habilida para selección multiple");
		JWins newWin = (JWins) baseWin.getClass().newInstance();
		JHistoryProvider hp = this.findHistoryProvider();
		if (hp == null)
			return baseWin;
		newWin.SetEstatico(true);
		if (getRequest().getClearSelect())
			hp.setMultipleSelect(null);
		hp.setMultipleSelect(newWin);
		newWin = hp.getMultipleSelect();
		newWin.setDropListener(baseWin.getDropListener());
		newWin.SetVision(baseWin.GetVision());
		this.addMultipleActionOwners(newWin);
		hp.setMultipleSelect(null);
		return newWin;
	}

	public boolean isReturnWinLov() throws Exception {
		if (this.getRequest().getArgument("dg_action") == null)
			return false;
		if (this.getRequest().getArgument("dg_action").endsWith("_4,"))
			return true;
		return false;
	}

	public boolean needLoadData() throws Exception {
		if (this.getRequest().hasMultipleObjectOwnerList())
			return false;
//		if (this.getRequest().getPssObjectOwner().indexOf("_anonimus_")!=-1) return false;
		// if (isReturnWinLov()) return false;
		if (this.getRequest().getURI().indexOf("do-SwapListRefreshAction") != -1)
			return false;
		if (this.getRequest().getURI().indexOf("do-WinListRefreshAction") != -1)
			return false;
		if (this.getRequest().getURI().indexOf("do-PreviewAreaAction") != -1)
			return false;
		if (this.getRequest().getURI().indexOf("modaldo-ViewAreaAction") != -1)
			return false;
		return true;
	}

	public JBaseWin resolveMainActionOwner() throws Exception {
		if (this.getRequest().hasPssObjectOwner()) {
			JBaseWin actionOwner = this.processObjectRegistered(needLoadData());
			if (actionOwner != null)
				return actionOwner;
		}
// nunca pasaba por aca asi que lo saque y vemos que pasa HGK 
//		JWebActionData bundle=this.loadWinBundle();
//		if (bundle==null) return null;
//		return this.getBaseWinFromBundle(bundle,true);
		return null;
	}

	public void addMultipleActionOwnersDest(JWins baseWin) throws Exception {
		if (this.getRequest().getMultipleObjectOwnerListDest().equals(""))
			return;
		baseWin.clearItems();
		buildWinsFromField(baseWin, this.getRequest().getMultipleObjectOwnerListDest());
	}

	public void addMultipleActionOwners(JBaseWin baseWin) throws Exception {
		buildWinsFromField(baseWin, this.getRequest().getMultipleObjectOwnerList());
	}

	public void buildWinsFromField(JBaseWin baseWin, String fields) throws Exception {
		JWins wins = (JWins) baseWin;
		wins.SetEstatico(true);
		String objectList = fields;
		StringTokenizer st = new StringTokenizer(objectList, ";,");
		while (st.hasMoreTokens()) {
			String idObject = st.nextToken();
			JWin win = null;
			Object obj = this.getRequest().getRegisterObject(idObject);
			if (obj == null)
				continue; // HGK en seleccion multimple vienen rows que son sub-rows y da error, corregir
									// en el .js para que no vengan
			if (obj instanceof String) {
				String resolveString = (String) obj;
//				this.getRequest().addDataBundle("act_owner", resolveString);
//				JWebActionData winBundle=this.getRequest().getData("act_owner");
				win = (JWin) winFactory.getBaseWinFromBundle(resolveString, true, null);
			} else {
				if (!obj.getClass().isAssignableFrom(wins.GetClassWin()))
					continue; // lo mismo que arriba, me aseguro que no se meta un row que no va, truchex!!!
				win = (JWin) obj;
			}

//			wins.AddItem(win, 0);
			wins.addRecord(win);
		}
		return;
	}

	protected String loadWinBundle() throws Exception {
		return getRequest().getArgument("act_owner");
	}

	protected JBaseWin processObjectRegisteredDest(boolean loadData) throws Exception {
		return processObjectRegisteredById(this.getRequest().getPssObjectOwnerDest(), loadData);
	}

	protected JBaseWin processObjectRegistered(boolean loadData) throws Exception {
		return processObjectRegisteredById(this.getRequest().getPssObjectOwner(), loadData);
	}

	protected JBaseWin processObjectRegisteredSelect(boolean loadData) throws Exception {
		return processObjectRegisteredById(this.getRequest().getPssObjectSelect(), loadData);
	}

	protected JBaseWin processObjectRegisteredById(String id, boolean loadData) throws Exception {
		Object obj = this.getRequest().getRegisterObject(id);
//		PssLogger.logDebug("|------------------------------------------------> TRY FIND "+id);
		if (obj instanceof JBaseWin) {
//			PssLogger.logDebug("|------------------------------------------------> FIND WIN "+id);
			JBaseWin actionOwner = (JBaseWin) obj;
			JBaseWin contextAtionOwner = actionOwner;
			if (this.getRequest().getPssObjectOwnerContext() != null)
				contextAtionOwner = (JBaseWin) this.getRequest().getRegisterObject(this.getRequest().getPssObjectOwnerContext());
			if (loadData)
				winFactory.loadData(actionOwner, true, getRequest().hasTableProvider() ? getRequest().getTableProvider() : null, contextAtionOwner);
			return actionOwner;
		}
		if (obj instanceof String) {
//			PssLogger.logDebug("|------------------------------------------------> FIND STRING "+id);
//			this.getRequest().addDataBundle("act_owner", (String) obj);
//			JWebActionData bundle=this.loadWinBundle();
//			if (bundle==null) return null;
			return winFactory.getBaseWinFromBundle((String) obj, true, id);
		}
//		PssLogger.logDebug("|------------------------------------------------> NO FIND STRING "+id);

		return null;
	}

	protected JBaseWin processObjectRegisteredByIdForCard(String id) throws Exception {
		Object obj = this.getRequest().getRegisterObject(id);
		if (obj instanceof JBaseWin) {
			JBaseWin actionOwner = (JBaseWin) obj;
			return actionOwner;
		}
		if (obj instanceof String) {
//			this.getRequest().addDataBundle("act_card", (String) obj);
//			JWebActionData bundle=winFactory.loadWinBundle("act_card");
//			if (bundle==null) return null;
			return winFactory.getBaseWinFromBundle((String) obj, false, id);
		}

		return null;
	}

	private boolean isExport() throws Exception {
		JWebActionData p = this.getRequest().getData("export");
		if (p == null)
			return false;
		return p.get("range") != null;
	}

	private void endResolver() throws Exception {
		if (this.getSession() == null)
			return;
		if (BizUsuario.getUsr() == null)
			return;
		BizUserHistory last = this.getSession().getStadistic(getRequest().getIdRequestJS());
		if (last == null)
			return;
		last.setTimeResolver(last.getDelta());
	}

	private void statsError(Throwable e) {
		try {
			this.endResolver();
			BizUserHistory last = this.getSession().getStadistic(getRequest().getIdRequestJS());
			if (last == null)
				return;
			last.processError(e);
		} catch (Exception ignore) {
			PssLogger.logError(e);
		}
	}

	protected JHistoryProvider findFirstProvider() throws Exception {
		if (!getRequest().hasTableProvider())
			return null;
		JHistory h = this.getSession().getHistoryManager().getLastHistory();
		return h.getFirstProvider();
	}

	protected JHistoryProvider findReqProvider() throws Exception {
		if (!getRequest().hasTableProvider())
			return null;
		JHistory h = this.getSession().getHistoryManager().getLastHistory();
		return h.findProvider(this.getRequest().getTableProvider());
	}

	protected JHistoryProvider findReqProvider(String provider) throws Exception {
		if (provider == null)
			return null;
		JHistory h = this.getSession().getHistoryManager().getLastHistory();
		return h.findProvider(provider);
	}

	protected JHistoryProvider storeContext(JBaseWin actionOwner, BizAction action) throws Exception {
		this.storeMeta(action);
		this.storeOtherProviders(actionOwner, action);
		this.storeScroller();
		this.storeOrder(actionOwner);
		JHistoryProvider hp = this.findReqProvider();
		if (hp == null)
			return null;

		this.storeModal(hp);
		this.storeScroller(hp);
		this.storeSelectedItem(hp);
		this.storeNavigation(hp);
		this.storeFilterMap(hp);

		if (action != null)
			action.setObjFilterMap(hp.getAction().getFilterMap());
		return hp;
	}

	protected void storeOrder(JBaseWin actionOwner) throws Exception {
		if (this.getSession() == null)
			return;

		if (hasInfoColumnsOrder(actionOwner)) {
			String provider = ((GuiWinsColumns) actionOwner.getDropListener()).getActionProvider();
			if (provider != null) {
				JHistoryProvider prov = this.getSession().getHistoryManager().findHistoryProvider(provider);
				((GuiWinsColumns) actionOwner).copyInfo(((GuiWinsColumns) actionOwner.getDropListener()));
				if (prov != null) {
					List<JPair<String, String>> list = new ArrayList<JPair<String, String>>();
					JIterator<BizWinsColumn> it = actionOwner.getRecords().getStaticIterator();
					while (it.hasMoreElements()) {
						BizWinsColumn col = it.nextElement();
						list.add(new JPair<String, String>(col.getField(), col.getOrdenAsc() ? "asc" : "desc"));
					}
					prov.setColumnsOrder(list);
				}
			}
		}
	}

	protected void storeScroller() throws Exception {
		if (this.findFirstProvider() == null)
			return;
		this.findFirstProvider().setScroller(this.getRequest().getScroller());
	}

	protected void storeScroller(JHistoryProvider hp) throws Exception {
		hp.setScroller(this.getRequest().getScroller());
	}

	protected void storeSelectedItem(JHistoryProvider hp) throws Exception {
		hp.setSelectedItem(this.getRequest().getRowSelected());
	}

	public void storeOtherProviders(JBaseWin zActionOwner, BizAction zAction) throws Exception {
		JHistory h = getHistoryManager().getLastHistory();
		Iterator<String> en = h.getProviders().keySet().iterator();
		while (en.hasNext()) {
			String tableProvider = en.next();
			if (("form_" + tableProvider).equals(getRequest().getTableProvider()))
				continue;
			updatetOtherProvider(getActOtherProvider(zActionOwner, zAction, h, tableProvider), tableProvider);
		}
	}

	private JAct getActOtherProvider(JBaseWin zActionOwnerOrig, BizAction zActionOrig, JHistory h, String tableProvider) throws Exception {
		JHistoryProvider provider = h.getProviders().get(tableProvider);
		BizAction action = provider.getAction();
		if (action == null)
			return null; // no hay accion asoc
		storeMeta(action, provider);
		JWebActionData data = this.getRequest().getFormData("form_" + tableProvider);
		if (data.isNull())
			return null; // no tiene datos
		if (!action.hasSubmit())
			return null;// no es submit
		JAct submit = action.getObjSubmit();
		if (submit == null)
			return null;// no tiene jact
		if (submit instanceof JActQuery)
			return null; // es query
		if (submit instanceof JActQueryActive)
			return null; // es query
//		JBaseWin win = submit.getResult();
//		if (win!=null) {
//			if (win.GetBaseDato().equals(zActionOwnerOrig.GetBaseDato())) 
//				return null;//no pises lo que ya subi en el principal
//		}
		return submit;
	}

	private void updatetOtherProvider(JAct submit, String tableProvider) throws Exception {
		if (submit == null)
			return;
		String sourceId = this.getRequest().getArgument("dg_source_control_id");
		if (sourceId != null && !sourceId.equals("") && submit instanceof JActModify)
			((JActModify) submit).markFieldChanged(null, null, sourceId);
		winFactory.loadData(submit.getResult(), true, "form_" + tableProvider, null);
//		processSwapElement(submit,"form_"+tableProvider);
	}

	protected void storeNavigation(JHistoryProvider hp) throws Exception {
		hp.setNavigation(this.getRequest().getNavigation());
	}

	protected void storeFilterMap(JHistoryProvider hp) throws Exception {
		hp.setFilterMap(this.createFilterMap().getElement("filter_pane_" + hp.getAction().getProviderName()));
	}

	protected void storeModal(JHistoryProvider hp) throws Exception {
		this.getSession().getHistoryManager().getLastHistory().setJSModal(isModal());
	}

	public void setResetRegisteredObjects(boolean resetRegisteredObjects) {
		this.resetRegisteredObjects = resetRegisteredObjects;
	}

	public Serializable resolveData() throws Exception {
		String data = getRequest().getArgument("data_asoc");
		if (data == null || data.equals(""))
			return null;
		return getRequest().getRegisterObject(data);
	}

	public BizAction resolveAction(JBaseWin owner) throws Exception {
		BizAction action = this.findAction(owner);
		if (action != null)
			action.clearSubmit();
		if (action == null)
			action = this.createVirtualAction(owner);
		if (action != null) {
			action.setFirstAccess(this.isFirstAccess()); // magia pero funca
			action.setExport(this.isExport());
			action.setSubmitedByUser(this.isSearchButtonPress());
			action.clearFilterMap(); // se tienen que setear de nuevo, sino queda basura en las acciones de menu
		}
		action.setData(this.resolveData());
//		action.setObjFilterMap(this.createFilterMap().getElement(action.getProviderName()));
		return action;
	}

	public boolean isSearchButtonPress() throws Exception {
		return getRequest().getNavigation().get("button_search") != null;
	}

	protected BizAction createVirtualAction(JBaseWin owner) throws Exception {
		BizAction a = new BizAction();
		a.setObjOwner(owner);
		a.SetIdAction("anonimus_" + owner.hashCode());
		if (owner instanceof JWins) {
			a.setObjSubmit(new JActWins(owner, detectVirtualActionIsMultiple()));
		} else {
			a.setObjSubmit(new JActQuery((JWin) owner));
		}
		return a;
	}

	public boolean detectVirtualActionIsMultiple() throws Exception {
		JWebActionData data = getRequest().getData("export");
		if (data == null)
			return false;
		String dato = data.get("multiple");
		if (dato == null)
			return false;
		return dato.equals("true");

	}

	public BizAction findAction(JBaseWin zActionOwner) throws Exception {
		JWebActionData oData = this.getRequest().getData("action");
		String idAction = oData.get("act");
		if (idAction == null || idAction.equals(""))
			return null;

		if (zActionOwner == null || zActionOwner instanceof GuiModulo) {
			return JWebActionFactory.getPssAction(idAction);
		}

		if (this.getRequest().hasPssObjectOwnerDest()) {
			JWins winsDest = (JWins) this.processObjectRegisteredDest(true);
			JWins wins = (JWins) zActionOwner;
			addMultipleActionOwnersDest(winsDest);
			wins.setDropListener(winsDest.getDropListener());// para el caso de que no se seleccione ninguno, como el drop viene en los
																												// elegidos (y en este caso no hay ninguno), lo pongo en este para sacarlo de
																												// ahi.
			BizAction action = winsDest.getWinRefWithActions().findActionByUniqueId(idAction);
			if (action == null) {
				action = winsDest.findActionByUniqueId(idAction + "", false, false);// refresh
			} else {
				action.setSwap(true);// seleccion
			}
			action.setObjOwner(wins);
			return action;

		}

		if (this.getRequest().hasMultipleObjectOwnerList() && zActionOwner instanceof JWins) {
			JWins wins = (JWins) zActionOwner;
//		BizAction action =  wins.getActionMapMulti().GetcDatos().findActionByUniqueId(idAction);
			BizAction action = wins.getWinRefWithActions().findActionByUniqueId(idAction);
			if (!action.isMulti())
				return null;
			action.setObjOwner(wins);
			return action;
		}

//		zActionOwner.clearActions();
//		zActionOwner.generateFullActionMap();
		BizAction a = zActionOwner.findActionByUniqueId(idAction, true, false);
		// ya lo hace la funcion anterior
//		if (a==null) a=zActionOwner.findAction(Integer.parseInt(JTools.clearNonNumbers(idAction)));
		String row = oData.get("row");
		if (row != null)
			a.setRow(row);
		return a;
	}

	public boolean calculeIsModal() throws Exception {
		return false;
	}

	protected String getTransformer() throws Exception {
		String sRequestedOutput = JWebActionFactory.getSerializerFor(this.getRequest());
		if (sRequestedOutput.equalsIgnoreCase("html"))
			return "resources/stylesheets/html/responsive_page.xsl";
		if (sRequestedOutput.equalsIgnoreCase("excel"))
			return "resources/stylesheets/export/page.xls.filter.xsl";
		if (sRequestedOutput.equalsIgnoreCase("csv"))
			return "resources/stylesheets/export/page.csv.filter.xsl";
		if (sRequestedOutput.equalsIgnoreCase("graph"))
			return "resources/stylesheets/export/page.img.filter.xsl";
		if (sRequestedOutput.equalsIgnoreCase("report"))
			return "resources/stylesheets/export/page.report.filter.xsl";
		if (sRequestedOutput.equalsIgnoreCase("map"))
			return "resources/stylesheets/export/page.map.filter.xsl";
		if (sRequestedOutput.equalsIgnoreCase("json"))
			return "resources/stylesheets/export/page.json.filter.xsl";
		if (sRequestedOutput.equalsIgnoreCase("mobile"))
			return "resources/stylesheets/mobile/page.xsl";
		throw new RuntimeException("Invalid transformer: " + sRequestedOutput);
	}

	protected String getSerializer() throws Exception {
		String sRequestedOutput = JWebActionFactory.getSerializerFor(this.getRequest());
		if (sRequestedOutput.equalsIgnoreCase("htmlfull"))
			return "htmlfull";
		if (sRequestedOutput.equalsIgnoreCase("html"))
			return "html";
		if (sRequestedOutput.equalsIgnoreCase("excel"))
			return "excel";
		if (sRequestedOutput.equalsIgnoreCase("graph"))
			return "graph";
		if (sRequestedOutput.equalsIgnoreCase("csv"))
			return "csv";
		if (sRequestedOutput.equalsIgnoreCase("report"))
			return "report";
		if (sRequestedOutput.equalsIgnoreCase("map"))
			return "map";
		if (sRequestedOutput.equalsIgnoreCase("json"))
			return "json";
		if (sRequestedOutput.equalsIgnoreCase("mobile"))
			return "mobile";
		throw new RuntimeException("Invalid serializer: " + sRequestedOutput);
	}

}
