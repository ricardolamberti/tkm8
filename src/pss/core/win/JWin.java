package pss.core.win;

import java.awt.event.KeyEvent;
import java.util.StringTokenizer;

import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.structure.IOrderByControl;
import pss.core.data.interfaces.structure.ROrderBy;
import pss.core.graph.Graph;
import pss.core.maps.GeoPositionable;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JObject;
import pss.core.services.records.JBaseRecord;
import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActChildForm;
import pss.core.win.submits.JActDelete;
import pss.core.win.submits.JActDrop;
import pss.core.win.submits.JActFieldWins;
import pss.core.win.submits.JActNewSubmit;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActReport;
import pss.core.win.submits.JActUpdate;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.forms.JBaseKeys;
import pss.core.winUI.icons.GuiIcon;
import pss.core.winUI.icons.GuiIconos;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.actions.requestBundle.JWebActionDataField;

/**
 * Clase base para todas las ventanas del framework. Proporciona la lógica
 * común para trabajar con {@link JRecord} y coordinar los formularios
 * derivados de {@link JBaseForm}. Las ventanas concretas extienden esta clase
 * para implementar comportamientos específicos de cada módulo de la
 * aplicación.
 */
public abstract class JWin extends JBaseWin {

	private transient Class<? extends JBaseForm> oWFormBase;

//	private transient JMap<Class, JBaseForm> aFormActivos=null;

	private transient JBaseForm oLocalForm=null;
//	private boolean bModoConsultaHabilitado=false;

	public static final int ACTION_QUERY=1;
	public static final int ACTION_UPDATE=2;
	public static final int ACTION_DELETE=3;
	public static final int ACTION_DROP=4;
	public static final int ACTION_EXECUTE=5;
	public static final int ACTION_APPLY=0;
	public static final int ACTION_CANCEL=6;

	//	 * A map of BizAction objects associated with properties' names' Strings It is initialized on demand and it is rarely used
//	 */
//	private JMap<String, BizAction> oPropertyAction=null;

	public String getModeView() throws Exception {
		return JBaseWin.MODE_FORM;
	}
	private transient Object oObjetoPropagado;
	public String getDireccionMapa() throws Exception {
		return null;
	}
	public String getNombreMapa() throws Exception {
		return null;
	}
	public String getIconoMapa() throws Exception {
		return GetIconFile(GuiIconos.SIZE_32x32);
	}
	public boolean isWordWrap(String zColName)  throws Exception {
		return false;
	}
	public boolean isMarked(String zColName)  throws Exception{
		return false;
	}
  @Override
	public String getTitleRight() throws Exception {
		return "";
	}

	public String getImageIfTrue(String zColName)  throws Exception{
		return "fa fa-check green";
	}
	public String getImageIfFalse(String zColName)  throws Exception{
		return "";
	}

	public int getHeightRow() throws Exception {
		return 20;
	}
	public int getWidthCol(String col)  throws Exception {
		return 0;
	}
	public Object getObjetoPropagado() {
		return this.oObjetoPropagado;
	}

	public void setObjetoPropagado(Object zValue) {
		this.oObjetoPropagado=zValue;
	}

	public JWin() {
		super();
	}

	// --------------------------------------------------------------------------
	// //
	// Manejo de la propiedad oDato
	// --------------------------------------------------------------------------
	// //
	public JRecord getRecord() throws Exception {
		return (JRecord) GetBaseDato();
	} 

	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return oWFormBase;
	}

	public Class<? extends JBaseForm> getFormUpdate() throws Exception {
		return getFormBase();
	}

	public Class<? extends JBaseForm> getFormNew() throws Exception {
		return getFormBase();
	}

	public Class<? extends JBaseForm> getFormEmbedded() throws Exception {
		return getFormBase();
	}
	
	public Class<? extends JBaseForm> getFormFlat() throws Exception {
		return getFormBase();
	}


	public Class<? extends JBaseForm> getFormMobile() throws Exception {
		return getFormBase();
	}

	public Class<? extends JBaseForm> getFormNewMobile() throws Exception {
		return getFormNew();
	}
	public Class<? extends JBaseForm> getFormEmbeddedMobile() throws Exception {
		return getFormEmbedded();
	}

	public Class<? extends JBaseForm> getFormUpdateMobile() throws Exception {
		return getFormUpdate();
	}
	
	public Class<? extends JBaseForm> getFormFlatMobile() throws Exception {
		return getFormFlat();
	}
	
	
	public void setRecord(JBaseRecord zDato) throws Exception {
		SetBaseDato(zDato);
	}

	public int getCorteControlCantLevels() throws Exception {
		return getRecord().getCorteControl().size();
	}
	public String getCorteControlDescription(String orderBy) throws Exception {
		IOrderByControl control = getCorteControl(orderBy);
		if (control==null) {
			ROrderBy order = getCorteControlOrder(orderBy);
			return order==null?"":this.getRecord().getPropAsString(order.GetCampo());
		}
		return control.getDescription(this.getRecord());
	}
	public String getCorteControlDescription(int level) throws Exception {
		IOrderByControl control = getCorteControl(level);
		if (control==null) {
			ROrderBy order = getCorteControlOrder(level);
			return order==null?"":this.getRecord().getPropAsString(order.GetCampo());
		}
		return control.getDescription(this.getRecord());
	}
	public String getCorteControlID(String orderBy) throws Exception {
		IOrderByControl control = getCorteControl(orderBy);
		if (control==null) {
			ROrderBy order = getCorteControlOrder(orderBy);
			return order==null?"":this.getRecord().getPropAsString(order.GetCampo());
		}
		return control.getID(this.getRecord());
	}
	public String getCorteControlID(int level) throws Exception {
		IOrderByControl control = getCorteControl(level);
		if (control==null) {
			ROrderBy order = getCorteControlOrder(level);
			return order==null?"":this.getRecord().getPropAsString(order.GetCampo());
		}
		return control.getID(this.getRecord());
	}
	
	public IOrderByControl getCorteControl(int level) throws Exception {
		if (level>=getRecord().getCorteControl().size()) return null;
		ROrderBy order = getRecord().getCorteControl().getElementAt(level);
		if (order==null) return null;
		if (order.getControl()==null) return null;
		return order.getControl();
	}
	public IOrderByControl getCorteControl(String orderBy) throws Exception {
		JIterator<ROrderBy> it = getRecord().getCorteControl().getIterator();
		while (it.hasMoreElements()) {
			ROrderBy order = it.nextElement();
			if (order.GetCampo().equals(orderBy)) {
				if (order.getControl()==null) return null;
				return order.getControl();
			}
		}
		return null;
	}
	public ROrderBy getCorteControlOrder(int level) throws Exception {
		if (level>=getRecord().getCorteControl().size()) return null;
		ROrderBy order = getRecord().getCorteControl().getElementAt(level);
		return order;
	}
	public ROrderBy getCorteControlOrder(String orderBy) throws Exception {
		JIterator<ROrderBy> it = getRecord().getCorteControl().getIterator();
		while (it.hasMoreElements()) {
			ROrderBy order = it.nextElement();
			if (order.GetCampo().equals(orderBy)) {
				return order;
			}
		}
		return null;
	}
	/**
	 * @deprecated
	 */
	@Deprecated
	public void SetFormBase(Class<? extends JBaseForm> zForm) {
		oWFormBase=zForm;
	}

	public void SetFormBase(Class<? extends JBaseForm> zForm, boolean zModal) {
		oWFormBase=zForm;
	}

//	public void setModoConsultaHabilitado(boolean zValor) {
//		bModoConsultaHabilitado=zValor;
//	}
//
//	public boolean isModoConsultaHabilitado() {
//		return bModoConsultaHabilitado;
//	}
//
	public void doQuery() throws Exception {
//		if (!this.isModoConsultaHabilitado()) return;
		if (this.findAction(1)==null) return;
		if (this.getFormBase()==null) return;
		this.showQueryForm();
	}

	/**
	 * Executed for every row in a list
	 * 
	 */
	public boolean okItemInList() {
		return true;
	}

	// --------------------------------------------------------------------------
	// //
	// Form local al objeto para editar registros en grillas //
	// --------------------------------------------------------------------------
	// //
	public void setLocalForm(JBaseForm zValue) {
		oLocalForm=zValue;
	}

	public JBaseForm getLocalForm() {
		return oLocalForm;
	}

	// --------------------------------------------------------------------------
	// //
	// Devuelvo la descripcion de un item para el arbol
	// --------------------------------------------------------------------------
	// //
	@Override
	public String getDescripFieldValue() throws Exception {
		JWin oWin=this.getRelativeWin();
		String sDesc;

		JObject oPropiedad=oWin.getRecord().getPropDeep(oWin.getDescripField());
		if (oPropiedad==null) {
			sDesc="";
		} else
			sDesc=oPropiedad.toFormattedString();
		return sDesc;
	}
	
	public String getFullDescripFieldValue(boolean withKey) throws Exception {
		String descr= "";
		try {
			JWin oWin=this.getRelativeWin();
			StringTokenizer t = new StringTokenizer(getSearchFields(withKey), ";");
			while (t.hasMoreTokens()) {
				String f = t.nextToken();
				descr+=(descr.equals("")?"":" - ")+getRecord().getProp(f).toString();
			}
		} catch (Exception e) {
			descr= getDescripFieldValue();
		}
		return descr;
	}
	public boolean isHtmlDescriptionValue() throws Exception {
		JWin oWin=this.getRelativeWin();
		String sDesc;

		JObject oPropiedad=oWin.getRecord().getPropDeep(oWin.getDescripField());
		if (oPropiedad==null) return false;
		return oPropiedad.isHtml();
	}
	public boolean addItemSeparator() throws Exception {
		return false;
	}
	public String GetValorItemClave() throws Exception {
		if (this.getKeyField()==null) return "";
		JObject obj=getRecord().getProp(this.getKeyField());
		if (obj==null) JExcepcion.SendError("No existe el campo '"+this.getKeyField()+"' en "+getRecord().getClass().getName());
		// return obj.AsString();
		return obj.toString();
	}
	
	
	public boolean GetValorTreeSelectionable() throws Exception {
		return  true;
	}

	public String GetValorTreeItemClave() throws Exception {
		return  GetValorItemClave();
	}

	public String GetValorTreeParentClave() throws Exception {
		JBaseWin w = getParent();
		if (w==null) return "";
		if (!(w instanceof JWin)) return "";
		return ((JWin)w).GetValorTreeItemClave();
	}

	public JWin getRelativeWin() throws Exception {
		return this;
	}


	// --------------------------------------------------------------------------
	// //
	// SetEnlazado
	// --------------------------------------------------------------------------
	// //

//	public JBaseForm showNewForm() throws Exception {
//		return showNewForm(true);
//	}

//	public JBaseForm showNewForm(boolean zShow) throws Exception {
//		JBaseForm oForm=this.createNewForm();
//		if (zShow) oForm.Show();
//		return oForm;
//	}
	
	public JBaseForm showNewForm() throws Exception {
		return generateNewForm();
	}
	public JBaseForm showNewForm(boolean zShow) throws Exception {
		return generateNewForm();
	}
	
	public JBaseForm generateNewForm() throws Exception {
		JBaseForm oForm=this.createNewForm();
		oForm.generate();
		return oForm;
	}
	
	
	public JBaseForm createQueryFormFlat(JAct actionOwner) throws Exception {
		JBaseForm form;
		if (isInMobile())
			form = this.createForm(this.getFormFlatMobile(), JBaseForm.MODO_CONSULTA, actionOwner, null, false);
		else
			form = this.createForm(this.getFormFlat(), JBaseForm.MODO_CONSULTA, actionOwner, null, false);
		form.setReRead(false);
		return form;
	}
	
	public JBaseForm createQueryForm(JAct actionOwner, boolean parialRefreshFormAction) throws Exception {
		if (isInMobile())
			return this.createForm((embedded)?this.getFormEmbeddedMobile():this.getFormMobile(), JBaseForm.MODO_CONSULTA, actionOwner, null, parialRefreshFormAction);
		return this.createForm((embedded)?this.getFormEmbedded():this.getFormBase(), JBaseForm.MODO_CONSULTA, actionOwner, null, parialRefreshFormAction);
	}

	public JBaseForm createUpdateForm(JAct actionOwner, JAct actionSubmit, boolean parialRefreshFormAction) throws Exception {
		if (isInMobile())
			return this.createForm((embedded)?this.getFormEmbeddedMobile():this.getFormUpdateMobile(), JBaseForm.MODO_MODIFICACION, actionOwner, actionSubmit,parialRefreshFormAction);
		return this.createForm((embedded)?this.getFormEmbedded():this.getFormUpdate(), JBaseForm.MODO_MODIFICACION, actionOwner, actionSubmit,parialRefreshFormAction);
	}

	public JBaseForm createQueryActiveForm(JAct actionOwner, JAct actionSubmit, boolean parialRefreshFormAction) throws Exception {
		if (isInMobile())
			return this.createForm((embedded)?this.getFormEmbeddedMobile():this.getFormMobile(), JBaseForm.MODO_CONSULTA_ACTIVA, actionOwner, actionSubmit,parialRefreshFormAction);
		return this.createForm((embedded)?this.getFormEmbedded():this.getFormBase(), JBaseForm.MODO_CONSULTA_ACTIVA, actionOwner, actionSubmit,parialRefreshFormAction);
	}


	
	public final JBaseForm createNewForm() throws Exception {
		return this.createNewForm(null, this.getActionSubmitNew());
	}

	public JAct getActionSubmitNew() throws Exception {
		// return new JActNewSubmit(this, 0);
		// return this.getSubmit(0);
		return this.findAction(0).getSubmit();
	}

	public final JBaseForm createNewForm(JAct actionOwner, JAct actionSubmit) throws Exception {
		if (isInMobile())
			return this.createForm((embedded)?this.getFormEmbeddedMobile(): this.getFormNewMobile(), JBaseForm.MODO_ALTA, actionOwner, actionSubmit, actionOwner.getFieldChanged()!=null);
		return this.createForm((embedded)?this.getFormEmbedded(): this.getFormNew(), JBaseForm.MODO_ALTA, actionOwner, actionSubmit, actionOwner.getFieldChanged()!=null);
	}

	public final JBaseForm createEnterQueryForm() throws Exception {
		if (isInMobile())
			return this.createForm((embedded)?this.getFormEmbeddedMobile():this.getFormMobile(), JBaseForm.MODO_ENTERQUERY, null, null, false);
		return this.createForm((embedded)?this.getFormEmbedded():this.getFormBase(), JBaseForm.MODO_ENTERQUERY, null, null, false);
	}

	public JBaseForm generateQueryForm(JAct actionOwner) throws Exception {
		JBaseForm oForm=this.createQueryForm(actionOwner, false);
		oForm.generate();
		return oForm;
	}

//	public JBaseForm FormEnterQuery() throws Exception {
//		return FormEnterQuery(true);
//	}
//
//	public JBaseForm FormEnterQuery(boolean zShow) throws Exception {
//		JBaseForm oForm=this.createEnterQueryForm();
//		if (zShow) oForm.Show();
//		return oForm;
//	}

	/**
	 * Answers wether this object should persist with the configuration given to it at the time of "procesar alta", and represent itself over the basis of that configuration, if it is available, at the time of "generar alta"
	 * 
	 * The implementation for storing the configuration should store it with the user accessing it as part of the key.
	 */
	protected boolean keepHistory() throws Exception {
		return false;
	}

	public BizAction addActionDrop(int zId, String zDesc) throws Exception {
//		if (!this.hasDropListener()) return null;
		BizAction action=this.addAction(zId, zDesc, KeyEvent.VK_ENTER, null, this.getDropIcono(), true, true);
		action.setMulti(true);
		action.setCancelable(false);
		action.setHelp("Seleccionar este "+GetTitle());
		action.setBackAction(false);
		if (hasDropControlIdListener() && getDropControlIdListener().getActionSource().isModal())
			action.setModal(true);
		return action;
	}

	public BizAction addActionQuery(int zId, String zDesc) throws Exception {
//		setModoConsultaHabilitado(true);
		return addAction(zId, zDesc, KeyEvent.VK_ENTER, null, GuiIcon.CONSULTAR_ICON, true, true).setHelp("Permite abrir el formulario de consulta de "+GetTitle());
	}

	protected BizAction addActionDelete(int zId, String zDesc) throws Exception {
		BizAction action=addAction(zId, zDesc, this.getKeyDeleteMask(), null, GuiIcon.MENOS_ICON, true, true);
		action.setMulti(true);
		action.setHelp("Permite eliminar "+GetTitle());
		action.setConfirmMessage(true);
		action.setConfirmMessageDescription("¿Está seguro que desea borrar el registro?");
		return action;
	}

	protected BizAction addActionUpdate(int zId, String zDesc) throws Exception {
		return this.addAction(zId, zDesc, this.getKeyUpdateMask(), null, GuiIcon.MODIFICAR_ICON, true, true, false).setHelp("Permite abrir el formulario de modificación de "+GetTitle());
	}

	protected BizAction addActionNewSubmit(int zId, String descr) throws Exception {
		BizAction a = this.addAction(zId, descr, 0, null, GuiIcon.APLICAR_ICON, false, false, false);
		a.setOkAction(false);
		return a;
	}

	protected BizAction addActionReportSubmit(int zId, String descr) throws Exception {
		BizAction a =  this.addAction(zId, descr, 0, null, GuiIcon.APLICAR_ICON, false, false);
		a.setNuevaVentana(true);
		a.setOkAction(false);
		return a;
	}

	@Override
	public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId()==0) return new JActNewSubmit(this, a.getId(), this.isKeepFormOnNew()||isModeWinLov());
		if (a.getId()==1) return new JActQuery(this.getRelativeWin());
		if (a.getId()==2) return new JActUpdate(this.getRelativeWin(), a.getId());
		if (a.getId()==3) return new JActDelete(this, a.getId());
		if (a.getId()==4) return new JActDrop(this, this.getDropListener(), a.getId());
		if (a.getId()==5) return new JActReport(this, a.getId());
	return this.getSubmitFor(a);
	}

 

	protected int getKeyDeleteMask() throws Exception {
		return KeyEvent.VK_DELETE;
//		return KeyEvent.VK_F4;
	}
	protected int getKeyUpdateMask() throws Exception {
		return JBaseKeys.Pss_CTRL_MASK|KeyEvent.VK_ENTER;
//		return KeyEvent.VK_F4;
	}
	public JBaseForm createForm(Class<? extends JBaseForm> zFormBase, String zModo, JAct actionOwner, JAct actionSubmit, boolean parialRefreshFormAction) throws Exception {
		if (zFormBase==null) return null;

//		if (zModo==JBaseForm.MODO_MODIFICACION||zModo==JBaseForm.MODO_CONSULTA||zModo==JBaseForm.MODO_CONSULTA_ACTIVA) {
//			JBaseForm oForm=this.GetFormActivo(zFormBase);
//			if (oForm!=null) {
//				oForm.setEmbedded(embedded);
//				oForm.SetModo(zModo);
//				oForm.setSubmitAction(actionSubmit);
//				oForm.setReRead(!parialRefreshFormAction);
//				oForm.setOwnerAction(actionOwner);
//				return oForm;
//			}
//		}

		JBaseForm oBaseForm=zFormBase.newInstance();
		oBaseForm.setSubmitAction(actionSubmit);
		oBaseForm.setOwnerAction(actionOwner);
		oBaseForm.setReRead(!parialRefreshFormAction);
		oBaseForm.setEmbedded(embedded);
		oBaseForm.setPartialRefreshFormAction(parialRefreshFormAction);
		oBaseForm.setModal(actionOwner.getActionSource().isModal());
		oBaseForm.SetWin(this);
		oBaseForm.SetModo(zModo);
//		oBaseForm.build(this, zModo);
//		if (!JAplicacion.GetApp().ifAppFrontEndWeb()) this.AddFormActivo(oBaseForm);

		return oBaseForm;
	}

	public JBaseForm createLocalForm() throws Exception {
		if (oLocalForm!=null) return oLocalForm;
		String modo=!getRecord().wasRead() ? JBaseForm.MODO_ALTA : JBaseForm.MODO_MODIFICACION;
		oLocalForm=buildLocalForm(modo);
//		if (!JAplicacion.GetApp().ifAppFrontEndWeb())
//			JDesktopSkin.getDefault().configureComponent(oLocalForm);
//		
//		
		return oLocalForm;

	}
	public JBaseForm buildLocalForm(String modo) throws Exception {
		Class<? extends JBaseForm> form=(modo.equals(JBaseForm.MODO_ALTA)) ? this.getFormNew() : this.getFormBase();
		JBaseForm f=form.newInstance();
		f.setVisibleMode(false);
//		f.setModal(actionOwner.getActionSource().isModal());
//		f.setShowError(false);
		f.SetWin(this.getRelativeWin());
		f.SetModo(modo);
		f.build();
		f.Do(modo);

//		if (modo.equals(JBaseForm.MODO_ALTA)) {
//			f.Alta();
//		} else if (modo.equals(JBaseForm.MODO_MODIFICACION)) {
//			f.Modificar();
//		}else {
//			f.Detalle();
//		}

		return f;
	}
	public void forgetLocalForm() throws Exception {
		if (oLocalForm!=null) {
			oLocalForm.cleanUp();
			oLocalForm=null;
		}
	}

	public void cleanUpToLeaveInMemory() throws Exception {
		forgetLocalForm();
		super.cleanUpToLeaveInMemory();
	}
	// --------------------------------------------------------------------------
	// //
	// Devuelvo la descripcion de un item para el arbol
	// --------------------------------------------------------------------------
	// //
	@Override
	public String toString() {
		try {
			return getDescripFieldValue();
		} catch (Exception e) {
			return "Error";
		}
	}

//	public void AddFormActivo(JBaseForm zForm) {
//		if (aFormActivos==null) aFormActivos=JCollectionFactory.createMap();
//		aFormActivos.addElement(zForm.getClass(), zForm);
//	}
//
//	public JBaseForm GetFormActivo(Class<? extends JBaseForm> zFormClass) {
//		if (aFormActivos==null) return null;
//		return aFormActivos.getElement(zFormClass);
//	}
//
//	public void RemoveFormActivo(JBaseForm zForm) {
//		if (aFormActivos==null) return;
//		aFormActivos.removeElement(zForm.getClass());
//	}

	@Override
	public int GetNroIcono() throws Exception {
		if (this!=this.getRelativeWin()) return this.getRelativeWin().GetNroIcono();
		return super.GetNroIcono();
	}

//	public boolean ifAcceptEmbeddedListsRefresh(JWinEvent e) throws Exception {
//		return false;
//	}

//	@Override
//	public boolean ifAcceptRefresh(JWinEvent e, boolean zGlobal) throws Exception {
//		if (zGlobal) return false;
//		if (!(e.GetArgs() instanceof JWin)) return false;
//
//		JWin oWin1=this;
//		JWin oWin2=(JWin) e.GetArgs();
//		if (e.ifRefreshDeleteOk()) return oWin1.ifIgual(oWin2);
//		if (e.ifRefreshModifOk()&&oWin1.ifIgual(oWin2)) {
//			JWin newWin=oWin1.getClass().newInstance();
//			newWin.setRecord(oWin2.getRecord());
//			e.SetArgs(newWin);
//			this.clearActions();
//			return true;
//		}
//		if (e.ifRefreshAltaOk()&&oWin1.getRecord().wasRead()&&oWin1.ifIgual(oWin2)) {
//			JWin newWin=oWin1.getClass().newInstance();
//			newWin.setRecord(oWin2.getRecord());
//			e.SetArgs(newWin);
//			this.clearActions();
//			return true;
//		}
//		if (e.ifRefreshDropOk()&&oWin1.ifIgual(oWin2)) return true;
//		return false;
//	}

	public boolean ifIgual(JWin zWin) throws Exception {
		if (this.getRecord()==null) return false;
		if (zWin.getRecord()==null) return false;
		return this.getRecord().isEqual(zWin.getRecord());
	}

	@SuppressWarnings("unchecked")
	public int compareTo(Object other) {
		try {
			JWin OtherJWin=(JWin) other;
			Object a=this.getRecord(); // .InfoPropiedad(this.szSortCriteria);
			Object b=OtherJWin.getRecord(); // .InfoPropiedad(this.szSortCriteria);
			if (a instanceof Comparable) {
				return ((Comparable<Object>) a).compareTo(b);
			} else return 0;
		} catch (Exception E) {
			return 0;
		}
	}

	public JWin GetThis() {
		return this;
	}

//	public JWins GetWinsQuery(JFormControles zControles) throws Exception {
//		JWins oWins=new JWins();
//		oWins.SetClassWin(this.getClass());
//
//		JIterator oIterator=zControles.GetItems();
//		while (oIterator.hasMoreElements()) {
//			JFormControl oControl=(JFormControl) oIterator.nextElement();
//			if (oControl.hasValue()&&oControl.getFixedProp().isTableBased()) oWins.getRecords().addFilter(oControl.getFixedProp().GetCampo(), oControl.getValue(), " like ");
//		}
//		return oWins;
//	}

	@Override
	public JBaseRecord ObtenerBaseDato() throws Exception {
		return ObtenerDato();
	}

	public JRecord ObtenerDato() throws Exception {
		return null;
	}

	public String GetHTMLObjectIdentification() throws Exception {
		String html="";
		JIterator<JProperty> oEnum=this.getRecord().getFixedProperties().getValueIterator();
		while (oEnum.hasMoreElements()) {
			JProperty oProp=oEnum.nextElement();
			if (!oProp.isKey()) continue;
			// html = html + oProp.GetCampo() + "_f" + "=" +
			// GetDato().getProp(oProp.GetCampo()).AsString() + "&";
			html=html+oProp.GetCampo()+"_f"+"="+getRecord().getProp(oProp.GetCampo()).toString()+"&";
		}

		return html;
	}

	public static JWin createVirtualWin(final String zValor, final String zDescrip, final int zIcono) throws Exception {
		JWin oWin=new JWin() {

			@Override
			public String getKeyField() throws Exception {
				return "valor";
			}

			@Override
			public String getDescripField() {
				return "descripcion";
			}

			@Override
			public int GetNroIcono() {
				return zIcono;
			}

			@Override
			public JRecord ObtenerDato() throws Exception {
				return JRecord.virtualBD(zValor, zDescrip, zIcono);
			}
		};
		return oWin;
	}

//	@Override
//	public boolean ifOkOrigenAction(BizAction zAct, Object zSource) throws Exception {
//		if ((zSource instanceof JBaseForm)&&zAct.getId()==1) return false;
//		return true;
//	}
//
//	@Override
//	public void doWithOkAction(BizAction zAction, boolean bOk) throws Exception {
//		if (zAction.getId()==1) this.setModoConsultaHabilitado(bOk);
//	}

//	public void FormDetalleOrAlta() throws Exception {
//		if (this.getRecord().wasRead()) showQueryForm();
//		else showNewForm();
//	}

	// public void WebDetalleOrAlta(JStromboli zParams) throws Exception {
	// if (this.GetDato().getDatosLeidos())
	// this.WebDetalle(zParams.oSession, zParams.oWrapper, zParams.oRequest);
	// else
	// this.WebAlta(zParams.oSession, zParams.oWrapper, zParams.oRequest,
	// false);
	// }

//	@Override
//	public void showQueryForm() throws Exception {
//		this.createQueryForm().Show();
//	}

//	/**
//	 * Answers with an action associated to the name of a property This way youy can associate actions with a sole cell.
//	 */
//
//	protected BizAction getPropertyAction(String zPropertyName) throws Exception {
//		if (oPropertyAction!=null) 
//			return oPropertyAction.getElement(zPropertyName);
//		return null;
//	}
//
//	// The map is initialized on-demand
//	protected void addPropertyAction(String zPropertyName, BizAction oAction) throws Exception {
//		if (oPropertyAction==null) oPropertyAction=JCollectionFactory.createMap();
//		oPropertyAction.addElement(zPropertyName, oAction);
//	}

	/**
	 * @deprecated
	 */
	@Deprecated
	@Override
	public void SetTitle(String zValue) throws Exception {
		super.SetTitle(zValue);
//		JBaseForm oActiveForm=this.GetFormActivo(this.getFormBase());
//		if (oActiveForm!=null) {
//			oActiveForm.setTitle(zValue);
//		}
	}
	
	public int getDropIcono() throws Exception {
		return GuiIcon.APLICAR_ICON;
	}
	
	public String getDropDescrip() throws Exception {
		return "Confirmar";
	}

	@Override
	public void createGenericActionMap() throws Exception {
		this.addActionDrop(JWin.ACTION_DROP, this.getDropDescrip());
		this.addActionReportSubmit(JWin.ACTION_EXECUTE, "Ejecutar").setCancelable(false);
		this.addActionNewSubmit(JWin.ACTION_APPLY, "Aplicar").setImportance(1).setCancelable(false);
	}
	
	protected boolean checkAction(BizAction a) throws Exception {
		if (a.getId()==JWin.ACTION_DROP) return this.hasDropListener();
		if (!super.checkAction(a)) return false;
		if (this.hasDropListener()) return this.checkActionOnDrop(a);
		return true;
	}
	
	protected boolean checkActionOnDrop(BizAction a) throws Exception {
//		if (a.getId()==JWin.ACTION_APPLY) return false;
//		if (a.getId()==JWin.ACTION_EXECUTE) return false;
//		if (a.isPanel()) return false;
//		if (!a.ifToolBar()) return true; 
//		if (!a.ifToolBar()) return true; 
//		return false;
//		
		// RJL, si solo retorna true se rompen los winLov, porque aparecen acciones que no estan preparadas.
		return true;
	}
	
	public boolean checkActionOnGlobal(BizAction a) throws Exception {
		// chequeo del JWin cuando depende del JWins (winRef), evitar el okAction cq el winref no tiene jrecord
		if (a.getId()==JWin.ACTION_DROP) return this.hasDropListener();
		if (a.getId()==JWin.ACTION_APPLY) return false;
		if (a.getId()==JWin.ACTION_EXECUTE) return false;
		if (this.hasDropListener()) return this.checkActionOnDrop(a);
		return true;
	}

//	public void generateActionsForToolbar(boolean check, boolean form) throws Exception {
////		this.clearActions(); 
//		BizActions actions = new BizActions();
//		actions.setStatic(true);
//		JIterator<BizAction> iter = this.getActionMap().getStaticIterator();
//		while (iter.hasMoreElements()) {
//			boolean habilitado=true;
//			BizAction action = iter.nextElement();
//			if (!action.isAccessToDetail() && !action.ifToolBar()) habilitado=false;
//			else if (!form && action.isOnlyInForm()) habilitado=false;
//			else if (!this.verifyAction(action, check, check)) habilitado=false;
//			
//			if (habilitado)
//				actions.addItem(action);
//			else
//				this.getNoActionMapToolbar().addItem(action);
//		}
//		this.setActionMapToolbar(actions);
//	}

	@Override
	public void createActionMap() throws Exception {
		this.createActionQuery();
		this.createActionUpdate();
		this.createActionDelete();
	}

	public BizAction createActionQuery() throws Exception {
		return this.addActionQuery(JWin.ACTION_QUERY, "Consultar").setHelp("Permite abrir el formulario para visualizar "+GetTitle());
	}

	public BizAction createActionUpdate() throws Exception {
		return this.addActionUpdate(JWin.ACTION_UPDATE, "Modificar").setHelp("Permite abrir el formulario de modificación de "+GetTitle());
	}

	public BizAction createActionDelete() throws Exception {
		return this.addActionDelete(JWin.ACTION_DELETE, "Eliminar").setHelp("Permite eliminar un "+GetTitle());
	}

	public void stopRefresh() throws Exception {
		JBDatos.GetBases().setNotifyEvents(false);
	}
	
	public void forceRefresh() throws Exception {
		JBDatos.GetBases().setNotifyEvents(true);
		((JRecord) this.GetBaseDato()).notifyUpdateOK();
		JBDatos.notifyCommit();
	}

	public void forzeDeleteRefresh() throws Exception {
		JBDatos.GetBases().setNotifyEvents(true);
		((JRecord) this.GetBaseDato()).notifyDeleteOK();
		JBDatos.notifyCommit();
	}

	public void forzeInsertRefresh() throws Exception {
		JBDatos.GetBases().setNotifyEvents(true);
		((JRecord) this.GetBaseDato()).notifyInsertOK();
		JBDatos.notifyCommit();
	}

	public JList<BizAction> getTreeActions() throws Exception {
		JList<BizAction> oItemsArbol=JCollectionFactory.createList();
		JIterator iter=this.getActionMap().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizAction oAction=(BizAction) iter.nextElement();
			if (oAction.isPanel()) oItemsArbol.addElement(oAction);
		}
		return oItemsArbol;
	}

	public boolean isGeoPositionable() {
		try {
			if (this.getRecord() instanceof GeoPositionable) {
				return true;
			}
		} catch (InstantiationException e) {
			PssLogger.logError(e);
		} catch (IllegalAccessException e) {
			PssLogger.logError(e);
		} catch (Exception e) {
			PssLogger.logError(e);
		}
		return false;
	}
	// alteradores del contenido de los graficos
	public String getCategorieDescription(Graph gr,String defa) {
		return defa;
	}
	public String getDatasetDescription(Graph gr,String defa) {
		return defa;
	}
	public String getGraphValue(Graph gr,String defa) {
		return defa;
	}
	public JMap<String,String> getGraphValueAttributes(Graph gr,JMap<String,String>  defa) {
		return defa;
	}
	public JMap<String,String> getGraphDatasetAttributes(Graph gr,JMap<String,String>  defa) {
		return defa;
	}
	public JMap<String,String> getGraphCategorieAttributes(Graph gr,JMap<String,String>  defa) {
		return defa;
	}
	

	public String getFieldForeground(String zColName) throws Exception {
		return null;
	}
	public String getFieldBackground(String zColName) throws Exception {
		return null;
	}
	public String getFieldTooltip(String zColName) throws Exception {
		return null;
	}
	public String getRowGridMode() throws Exception {
		return null;
	}
	public String getLastFieldInGrid() throws Exception {
		return null;
	}
	
	public int getActionDrop() throws Exception {
		return -1;
	}
	public int getFieldColspan(String zColName) throws Exception {
		return 1;
	}
	public JWins getWinsDetail() throws Exception {
		JWins wins = null;
		JIterator<BizAction> iter = this.getActionMap().getStaticIterator();		
		while (iter.hasMoreElements()) {			
			BizAction action = iter.nextElement();			
			if (!action.isAccessToDetail()) continue;
			JAct act = this.getSubmit(action);
		//	act.Do();
			wins = act.getWinsResult();
			if (wins==null) continue;
			wins.setParent(this);
		}
		
		return wins;
	}

	public boolean hasToFilterRecordInGrid() throws Exception {		return true;	}	
	

	public void verifyActionWinRef() throws Exception {
		JIterator<BizAction> iter = this.getActionMap().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizAction a = iter.nextElement();
			a.setOkAction(this.checkActionOnGlobal(a));
		}
	}

	public boolean isHistoryAction() throws Exception {
		return this.getRecord().wasRead();
	}

	public void assignDropListener(JBaseWin listener) throws Exception {
		this.setDropListener(listener);
	}

	public void controlsToBD(JWebActionData data) throws Exception {
		JRecord record = getRecord();
		JIterator<JWebActionDataField> iter=data.getFieldIterator();
		while (iter.hasMoreElements()) {
			JWebActionDataField field=iter.nextElement();
			JObject<?> prop=record.getProp(field.getName());
			if (prop==null) continue;
			prop.setValueFormUI(field.getValue());
		}
	}

	public JAct DropControl(JAct zAct) throws Exception {
		setDropControl(getRecord(),zAct.getResult());
		return super.DropControl(zAct);
	}
	
	public String extractFieldRow(String name) throws Exception { //bugui
		if (name==null || name.equals("")) return "win_form";
		String out = name;
		int pos = out.indexOf("dgf_");
		if (pos==-1) return null;
		int pos3;
		int pos2 = out.indexOf("__l");
		if (pos2==-1) {
			pos2 = out.indexOf("_p_k");
			if (pos2!=-1) {
				pos3 = out.indexOf("_",pos2+4);
				if (pos3==-1) 
					pos3=pos+3;
			} else
				pos3=pos+3;
			
		} else {
			pos3 = out.indexOf("_",pos2+2);
			if (pos3==-1) return null;
			
		}
		int pos1 = out.indexOf("_row_");
		if (pos1==-1) return null;
		if (pos3+1>pos1) {
			return  out.substring(pos+4,pos2);
		}
		
		return out.substring(pos3+1,pos1);
	}
	public int extractRow(String name) throws Exception {
		if (name==null || name.equals("")) return -1;
		String out = name;
		int pos = out.indexOf("_row_");
		if (pos==-1) return -1;
		int pos1 = out.indexOf("_fd");
		if (pos1==-1) return -1;
		return Integer.parseInt(JTools.getNumberEmbedded(out.substring(pos+5,pos1)));
	}
	//reimplementar para clave multiple
	public void setDropControl(JRecord record, JBaseWin zBaseWin) throws Exception {
		JRecord realRecord = record;
		if (this.getDropControlIdListener().getFieldChangeProvider()!=null) {
			if (zBaseWin.isWin()) {
					this.getDropControlIdListener().getFieldChangeProvider().addFilterMap(getDropControlIdListener().getFieldChanged(),((JWin)zBaseWin));
			} else {
//				String keys="";
//				JIterator<JWin> it = ((JWins)zBaseWin).getStaticIterator();
//				while (it.hasMoreElements()) {
//					JWin win = it.nextElement();
//					keys+=(keys.equals("")?"":",")+(((JWin)win).getRecord().getPropAsString((win.getKeyField())));;
//				}
//				this.getDropControlIdListener().getActionSource().addFilterMap(getDropControlIdListener().getFieldChanged(),keys);
				this.getDropControlIdListener().getFieldChangeProvider().addFilterMap(getDropControlIdListener().getFieldChanged(),((JWins)zBaseWin));
			}
			return;
		} 
		
		if (hasRowId()) { 
			if (isCard()) {
				realRecord=getRecordCard();
				
			} else {
				String campo = extractFieldRow(getDropControlIdListener().getWinLovRowId());
				int row = extractRow(getDropControlIdListener().getWinLovRowId());
				realRecord=((JObjBDs) record.getProp(campo)).getRawValue().getStaticElement(row);
				
			}
		}
		JObject<?> prop = realRecord.getPropDeep(getDropControlIdListener().getFieldChanged(),false);
		if (prop==null) return;
		prop.setModifiedOnServer(true);
		if (zBaseWin.isWin()) {
			if (prop.isRecord())
				prop.setValue(((JWin)zBaseWin).getRecord());
			else
				prop.setValueFormUI(((JWin)zBaseWin).getRecord().getPropAsString((zBaseWin.getKeyField())));
		}
		else {
			if (prop.isRecords())
				prop.setValue(((JWins)zBaseWin).getRecord());
			else {
				String keys="";
				JIterator<JWin> it = ((JWins)zBaseWin).getStaticIterator();
				while (it.hasMoreElements()) {
					JWin win = it.nextElement();
					keys+=(keys.equals("")?"":",")+(((JWin)win).getRecord().getPropAsString((win.getKeyField())));;
				}
				prop.setValueFormUI(keys);
			}
		}
	}
	

	
	public boolean checkActionComponentType(BizAction action, boolean isForm) throws Exception {
		if (action.getId()==JWin.ACTION_QUERY && isForm) return false;
		return true;
	}

	public static BizAction createActionSecurity() throws Exception {
		BizAction a = new BizAction();
		a.SetIdAction("SECURITY");
//		a.setOwner(this.getClass().getName());
		a.setDescrip("Seguridad");
		a.SetTitle("Seguridad");
		a.SetNroIcono(GuiIcon.SECURITY_ICON);
		a.setInMenu(true);
		a.setInToolbar(true);
		return a;
	}

	public boolean hasBackAction() throws Exception {
		return true;
	}
		public boolean forceAppendActions() throws Exception {
		return false;
	}

	public boolean isWin() {
		return true;
	}
		
	public boolean isOrigenControl(String field) throws Exception {
		if (!this.hasDropControlIdListener()) return false;
		return this.getDropControlIdListener().getFieldChanged().equals(field);
	}
	public Boolean checkStaticFiltersInQuickSearchColumn(String field, String valueField,String oper, String search) throws Exception {
		return null;
	}

	public boolean checkStaticFiltersInQuickSearch(String field, String value) throws Exception {
		boolean check = false;
		String search = value.trim().toLowerCase();
		if (getRecord().getPropDeep(field,false)==null) return true; //no existe la propiedad
		if (getRecord().getPropDeep(field,false).isString())
			check |= getRecord().getPropDeep(field).toString().toLowerCase().indexOf(search) != -1;
		else if (JTools.isNumber(value)) {
			check |= getRecord().getPropDeep(field).toString().toLowerCase().equals(search);
		}
		return check;
	}

	public boolean hasPotentialTabs() throws Exception {
	 	JIterator<BizAction> itac= getActionMap().getStaticIterator();
	 	while(itac.hasMoreElements()) {
	 		BizAction action = itac.nextElement();
			if (action.getId()==0) continue;
			if (action.ifMenu()) continue;
			if (action.ifToolBar()) continue;
			JAct act = this.getSubmit(action);
		  if (act instanceof JActFieldWins) {
			  return true;
		  } else if (act instanceof JActWins) { 
			  return true;
			} else if (act instanceof JActChildForm) 
				return true;
			
	 		}
			return false;
	 }	
			 
//	public String getCancelButtomDescription() throws Exception {
//		return "Cancelar";
//	}
		 
	public boolean isHide() throws Exception {
		return false;
	}


		public boolean checkManualFilter(String field,String sPropFormatted,String value) throws Exception {
			return false;
		}
public String getHelpFor(String campo) throws Exception {
		JProperty prop =  this.getRecord().getFixedProp(campo);
		if (prop==null) 
			return "--";
		return prop.GetDescripcion();
	}
		
}
