package pss.core.winUI.lists;

import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.structure.RFilter;
import pss.core.services.fields.JObject;
import pss.core.services.records.JFilterMap;
import pss.core.services.records.JProperty;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.win.IControl;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormControles;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormButtonResponsive;
import pss.core.winUI.responsiveControls.JFormCDatetimeResponsive;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.core.winUI.responsiveControls.JFormComboResponsive;
import pss.core.winUI.responsiveControls.JFormEditFromToResponsive;
import pss.core.winUI.responsiveControls.JFormEditResponsive;
import pss.core.winUI.responsiveControls.JFormIntervalCDatetimeResponsive;
import pss.core.winUI.responsiveControls.JFormLabelDataResponsive;
import pss.core.winUI.responsiveControls.JFormLabelResponsive;
import pss.core.winUI.responsiveControls.JFormRadioResponsive;
import pss.core.winUI.responsiveControls.JFormWinLOVResponsive;

public class JFormFiltro {

	private static final long serialVersionUID = -4167483528757098182L;
	JWins oWins=null;
	JFormControles oControles=null;
	boolean autoResize=false;


	public JWin getWin() throws Exception {
		return this.oWins.getWinRef();
	}
	
	public JFormControles GetControles() {
		return oControles;
	}

	public void setControles(JFormControles value) {
		oControles=value;
	}

	public JFormFiltro(JWins zWins) throws Exception {
		oWins=zWins;
//		oWin=zWins.createJoinWin(null);
//		oWin=zWins.createWin(null);
		oControles=new JFormControles(null,null);
//		this.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
	}

	public void initialize() throws Exception {
//		this.removeAll();
		oWins.ConfigurarFiltros(this);
		this.hidePreasignedFilters();
	}
	
	
	public void asignDefaultData() throws Exception {
		JIterator<JFormControl> oControlsIt=oControles.GetItems();
		while (oControlsIt.hasMoreElements()) {
			JFormControl oFormControl=(JFormControl) oControlsIt.nextElement();
			if (oFormControl.getFixedProp()==null) continue;
			oFormControl.asignDefaultData();
			oFormControl.edit("A");
		}
	}

//	public void convertToResponsive() throws Exception {
//		boolean needReinsertion=false;
//		JIterator<JFormControl> oControlsItCheck=oControles.GetItems();
//		while (oControlsItCheck.hasMoreElements()) {
//			JFormControl oFormControl=(JFormControl) oControlsItCheck.nextElement();
//			if (!oFormControl.isResponsive()) {
//				needReinsertion=true;
//				break;
//			}
//		}
//		if (!needReinsertion) return;
//		
//		JIterator<JFormControl> oControlsIt=oControles.GetItems();
//		ArrayList<JFormControl> newCtrls = new ArrayList<JFormControl>();
//		while (oControlsIt.hasMoreElements()) {
//			JFormControl oFormControl=(JFormControl) oControlsIt.nextElement();
//			if (oFormControl.isResponsive()) {
//				oControlsIt.remove();
//				newCtrls.add(oFormControl);
//				continue;
//			}
//			
//			JFormControlResponsive newComp = oFormControl.getResponsiveVersion();
//			if (newComp==null) continue;
//			needReinsertion=true;
//			oControlsIt.remove();
//			newCtrls.add(newComp);
//		}
//		for(JFormControl ctrl:newCtrls) {
//			GetControles().AddControl(ctrl);
//		}
//	}
	
	public void applyFilterMap(BizAction a, boolean firstAccess) throws Exception {
		if (a==null) return;

//		if (a.isFilterMapAssigned()) return;
		if (!a.hasFilterMap()) return;
		JFilterMap map = a.getFilterMap();
		JIterator<JFormControl> iter = this.GetControles().GetItems();
		while (iter.hasMoreElements()) {
			JFormControl c = iter.nextElement();
			if (c.isDisplayOnly()) 
				continue;
			if (c.getIdControl()==null)
				continue;

			if (!map.hasFilter(c.getIdControl().toLowerCase()))
				continue;
			if (!c.isPersistente() && firstAccess) 
				continue;
			boolean acceptURL = c.isAcceptURL();
			
			Object obj = map.getFilterValue(c.getIdControl().toLowerCase(),acceptURL);
			if (obj instanceof String) {
				c.setValue((String) obj);
			} else if (obj instanceof JWin) {
				c.setValue((JWin) obj);
			} else if (obj instanceof JWins) {
				c.setValue((JWins) obj);
			}
		}

	}

	
	public void hidePreasignedFilters() throws Exception {
		if (oWins.getRecords().getFilters()==null) return;
			JIterator<RFilter> iter=this.oWins.getRecords().getFilters().getIterator();
		while (iter.hasMoreElements()) {
			RFilter filter=iter.nextElement();
			JFormControl c=this.GetControles().findControlByField(filter.getField(), filter.getOperator());
			if (c==null) continue;
			if (!oWins.getRecords().analizeFilter(filter,c)) continue;
			if (filter.getValue()==null) continue;
			if (filter.getObjValue()!=null)
				c.SetValorDefault(filter.getObjValue());
			else if (!filter.getValue().equals("null"))
				c.SetValorDefault(filter.getValue());
		}
	}
	public boolean hasFilterNeverHide() throws Exception  {
		boolean anyToShow = false;
		JIterator<JFormControl> iter=this.GetControles().GetItems();
		while (iter.hasMoreElements()) {
			JFormControl c=iter.nextElement();
			if (!c.isVisible()) continue;
			if (c.isFilterNeverHide()) 	return true;
		}
		return anyToShow;
	}
	public boolean hideFiltersWithinData(boolean modeConFiltrosVisibles) throws Exception  {
		boolean anyToShow = false;
		JIterator<JFormControl> iter=this.GetControles().GetItems();
		while (iter.hasMoreElements()) {
			JFormControl c=iter.nextElement();
			if (!c.isVisible()) continue;
			if (c.isFilterNeverHide()) {
				anyToShow|=true;
				continue;
			}
//			if (c.hasValue() || c.hasDefaultValue()) {
//					if (!modeConFiltrosVisibles) c.disable();
			if (c.hasDefaultValue()) {
				anyToShow|=true;
			} else
				c.hide();
		}
		return anyToShow;
	}
	public void disableReadOnlyFilters() throws Exception {
		JIterator<JFormControl> iter=this.GetControles().GetItems();
		while (iter.hasMoreElements()) {
			JFormControl c=iter.nextElement();
			if (c.ifReadOnly()) 
				c.disable();
		}
	}

	public void ArmarFiltrosBar() throws Exception {
		this.initialize();
		this.asignDefaultData();
		this.disableReadOnlyFilters();
//		UITools.hierarchy().whenShownInHierarchyDo(this, new JAct() {
//
//			@Override
//			public void Do() {
//				pack();
//			}
//		});
	}

	private void pack() {
		this.autoResize=true;
//		UITools.layout().pack(this);
	}

//	@Override
//	public void reshape(int x, int y, int w, int h) {
//		if (this.autoResize) {
//			boolean changed=this.getX()!=x||this.getY()!=y||this.getWidth()!=w||this.getHeight()!=h;
//			super.reshape(x, y, w, h);
//			if (changed) {
//				this.pack();
//			}
//		} else {
//			super.reshape(x, y, w, h);
//		}
//	}

	public boolean hasAnyFilterVisible() throws Exception {
		JIterator<JFormControl> iter = this.GetControles().GetItems();
		while (iter.hasMoreElements()) {
			if (iter.nextElement().isVisible()) return true;
		}
		return false;
	}
	
	public boolean isEmpty() throws Exception {
		return this.GetControles().Cantidad()==0;
	}

	public JFormEditResponsive addEditResponsive(String zLabel, String zTipoDato, String zProp, String zOperator, boolean bVisible) throws Exception {
		return this.addEditResponsive(zLabel, zTipoDato, zProp, this.getWin().getRecord().getPropDeepOnlyRef(zProp), this.getWin().getRecord().getFixedProp(zProp), zOperator, bVisible);
	}
	public JFormEditResponsive addEditResponsive(String zLabel, String zTipoDato,String field, JObject obj, JProperty prop, String zOperator, boolean bVisible) throws Exception {
		JFormEditResponsive oEdit=new JFormEditResponsive();
		oEdit.SetDisplayName(zLabel);
		oEdit.setFieldProp(field);
		oEdit.setProp(obj);
		oEdit.setFixedProp(prop);
		oEdit.SetTipoDato(zTipoDato);
		oEdit.setOperator(zOperator);
		oEdit.setLabel(zLabel);
		oEdit.setVisible(bVisible);
		oEdit.setDetectChanges(false);
		oControles.AddControl(oEdit);
		return oEdit;
	}
	public JFormEditFromToResponsive addEditFromToResponsive(String zLabel, String zTipoDato,String fieldFrom, String fieldTo) throws Exception {
		return addEditFromToResponsive(zLabel, zTipoDato, fieldFrom, fieldTo,true);
	}
	public JFormEditFromToResponsive addEditFromToResponsive(String zLabel, String zTipoDato,String fieldFrom, String fieldTo, boolean bVisible) throws Exception {
		JFormEditFromToResponsive oEdit=new JFormEditFromToResponsive();
		oEdit.SetDisplayName(zLabel);
		oEdit.setFieldPropFrom(fieldFrom);
		oEdit.setPropFrom(this.getWin().getRecord().getPropDeepOnlyRef(fieldFrom));
		oEdit.setFixedPropFrom( this.getWin().getRecord().getFixedProp(fieldFrom));
		oEdit.setFieldPropTo(fieldTo);
		oEdit.setPropTo(this.getWin().getRecord().getPropDeepOnlyRef(fieldTo));
		oEdit.setFixedPropTo( this.getWin().getRecord().getFixedProp(fieldTo));
		oEdit.SetTipoDato(zTipoDato);
		oEdit.setIdControl(fieldFrom+"_"+fieldTo);
		oEdit.setOperator("=");
		oEdit.setLabel(zLabel);
		oEdit.setVisible(bVisible);
		oEdit.setDetectChanges(false);
		oControles.AddControl(oEdit);
		return oEdit;
	}
	public JFormLabelResponsive addLabelResponsive(String zLabel) throws Exception {
		JFormLabelResponsive control = new JFormLabelResponsive();
		control.setLabel(zLabel);
		control.setForcedValue(zLabel);
		control.setIdControl("label-" + control.hashCode());
//		control.setPreferredWidth(200);
		control.setDetectChanges(false);
		oControles.AddControl(control);
		return control;
	}

//	public JFormEditResponsive addEditResponsive(String zLabel, String zTipoDato, String zProp, int zPreferredWidth) throws Exception {
//		return this.addEditResponsive(zLabel, zTipoDato, zProp, "=", zPreferredWidth);
//	}
//	
//	public JFormEditResponsive addEditResponsive(String zLabel, String zTipoDato, String zProp, String zOperator, int zPreferredWidth) throws Exception {
//		JFormEditResponsive oEdit=this.addEditResponsive(zLabel, zTipoDato, zProp, zOperator);
//		oEdit.setPreferredWidth(zPreferredWidth);
//		return oEdit;
//	}

	public JFormEditResponsive addEditResponsive(String zLabel, String zProp) throws Exception {
		return this.addEditResponsive(zLabel, JBaseForm.CHAR, zProp);
	}
	public JFormEditResponsive addEditResponsive(String zLabel, String zTipoDato, String zProp) throws Exception {
		return this.addEditResponsive(zLabel, zTipoDato, zProp, "=" );
	}
	public JFormEditResponsive addGenericResponsive(String zLabel, String zMultiField) throws Exception {
		JFormEditResponsive oEdit=new JFormEditResponsive();
		oEdit.SetDisplayName(zLabel);
		oEdit.setLabel(zLabel);
		oEdit.setProp(null);
		oEdit.setFixedProp(null);
		oEdit.SetTipoDato(null);
		oEdit.setOperator(null);
		oEdit.setLabel(zLabel);
		oEdit.setVisible(true);
		String multiField=zMultiField;
		if (zMultiField==null||zMultiField.equals("")) {
			JIterator<String> it=getWin().getRecord().getFixedProperties().getKeyIterator();
			while (it.hasMoreElements()) {
				String e = it.nextElement();
				multiField+=(multiField.equals("")?"":",")+e;
			}
		}
		oEdit.setMultiField(multiField);
		oEdit.setId(JTools.getValidFilename(zLabel));
		oEdit.setDetectChanges(false);
		oControles.AddControl(oEdit);
		return oEdit;
	}
	
	public JFormEditResponsive addEditResponsive(String zLabel, String zTipoDato, String zProp, String zOperator) throws Exception {
		return this.addEditResponsive(zLabel, zTipoDato, zProp, zOperator, true);
	}


	
	public JFormLabelDataResponsive AddItemLabelData(String label, String zTipoDato,String sCampo, boolean estatico, long size) throws Exception {
		JFormLabelDataResponsive l = this.AddItemLabelData(label, zTipoDato, sCampo, estatico);
		l.setSizeColumns(size);
		return l;
	}
	
	public JFormLabelDataResponsive AddItemLabelData(String label, String zTipoDato,String sCampo, boolean estatico) throws Exception {
		JFormLabelDataResponsive oEdit;

		oEdit = new JFormLabelDataResponsive();
		oEdit.initialize();
		oEdit.SetDisplayName(label);
		oEdit.setFieldProp(sCampo);
		oEdit.setEstatico(estatico);
		if (!estatico) {
			oEdit.setProp(this.getWin().getRecord().getPropDeepOnlyRef(sCampo));
			oEdit.setFixedProp(this.getWin().getRecord().getFixedProp(sCampo));
		}
		oEdit.SetTipoDato(zTipoDato);
		oEdit.setOperator("=");
		oEdit.setLabel(label);
		oEdit.setVisible(true);
		oEdit.setDetectChanges(false);
		oControles.AddControl(oEdit);
	
		return oEdit;

	}

//	public JFormComboResponsive addComboResponsive(String zName, String zProp, JWins zRefWins, int zPreferredWidth) throws Exception {
//		JFormComboResponsive oCombo=this.addComboResponsive(zName, zProp, zRefWins);
//		oCombo.setPreferredWidth(zPreferredWidth);
//		return oCombo;
//	}
	public JFormComboResponsive addComboResponsive(String zName, String zProp) throws Exception {
		return addComboResponsive(zName, zProp, (JControlCombo)null, false);
	}
	public JFormComboResponsive addComboResponsive(String zName, String zProp, JWins zRefWins) throws Exception {
		return addComboResponsive(zName, zProp, zRefWins, true);
	}
	public JFormComboResponsive addComboResponsive(String zName, String zProp,  JObject prop, JProperty fixedProp,JWins zRefWins) throws Exception {
		return addComboResponsive(zName, zProp, prop, fixedProp, zRefWins, false, true);
	}
//	public JFormComboResponsive addComboResponsive(String zName, String zProp, JWins zRefWins, boolean zbPermitirTodo, int zPreferredWidth) throws Exception {
//		JFormComboResponsive oCombo=this.addComboResponsive(zName, zProp, zRefWins, zbPermitirTodo);
//		oCombo.setPreferredWidth(zPreferredWidth);
//		return oCombo;
//	}

	public JFormComboResponsive addComboResponsive(String zName, String zProp, JWins zRefWins, boolean zbPermitirTodo) throws Exception {
		return addComboResponsive(zName, zProp, zRefWins, zbPermitirTodo, true);
	}
	public JFormComboResponsive addComboResponsive(String zName, String zProp, JControlCombo zCC, boolean zbPermitirTodo) throws Exception {
		return addComboResponsive(zName, zProp, this.getWin().getRecord().getPropDeepOnlyRef(zProp),this.getWin().getRecord().getFixedPropDeep(zProp),zCC,zbPermitirTodo);

	}
	public JFormComboResponsive addComboResponsive(String zName, String zProp, JObject prop, JProperty fixedProp, JControlCombo zCC, boolean zbPermitirTodo) throws Exception {
		JFormComboResponsive oFormCombo=new JFormComboResponsive();
		oFormCombo.SetDisplayName(zName);
		oFormCombo.setFieldProp(zProp);
		oFormCombo.setProp(prop);
		oFormCombo.setFixedProp(fixedProp);
		if (zCC==null)
			oFormCombo.SetControlWin(fixedProp.getControl(null));
		else
			oFormCombo.SetControlWin(zCC);
		oFormCombo.SetPermitirTodo(zbPermitirTodo);
		oFormCombo.setPermiteVacio(zbPermitirTodo);
//		oFormCombo.setDependiente(dependiente);
		oFormCombo.setDetectChanges(false);
		oFormCombo.setLabel(zName);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormFiltro(oFormCombo);
		oControles.AddControl(oFormCombo);
		oFormCombo.edit("A");
		return oFormCombo;
	}

	
	
	public JFormWinLOVResponsive addWinLovResponsive(String zName, String zProp, final JWins zRefWins) throws Exception {
		return addWinLovResponsive(zName, zProp, new JControlWin() {

			@Override
			public JWins getRecords(boolean zOneRow) throws Exception {
				return zRefWins.getRecords().isStatic()?zRefWins: zRefWins.createClone();
			}
		});

	}
	public JFormWinLOVResponsive addWinLovResponsive(String zName, String zProp, int action) throws Exception {
		JFormWinLOVResponsive lov = this.addWinLovResponsive(zName, zProp,(IControl)null);
		lov.setAction(this.getWin(), this.getWin().findAction(action));
		return lov;
	}
	
	public JFormWinLOVResponsive addWinLovResponsive(String zName, JObject<?> obj, JProperty prop, IControl zCC,String title) throws Exception {
		JFormWinLOVResponsive oFormLov=new JFormWinLOVResponsive(true, false);
		oFormLov.SetDisplayName(zName);
		oFormLov.setProp(obj);
		oFormLov.setFixedProp(prop);
		oFormLov.setControlWins(zCC);
		oFormLov.setSearcheable(true);
		if (title!=null) oFormLov.setTitle(title);
		oFormLov.setOperator("IN");
		oFormLov.setLabel(zName);
		oFormLov.setDetectChanges(false);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormFiltro(oFormLov);

		oControles.AddControl(oFormLov);
		oFormLov.edit("A");
		return oFormLov;

	}
  

	public JFormWinLOVResponsive addWinLovResponsive(String zName, String zProp, IControl zCC) throws Exception {
		return this.addWinLovResponsive(zName, zProp, this.getWin().getRecord().getPropDeepOnlyRef(zProp), this.getWin().getRecord().getFixedPropDeep(zProp), zCC, null);
	}
	public JFormWinLOVResponsive addWinLovResponsive(String zName, String zProp,JObject<?> obj, JProperty prop, IControl zCC,String title) throws Exception {
		JFormWinLOVResponsive oFormLov=new JFormWinLOVResponsive(true, false);
		oFormLov.SetDisplayName(zName);
		oFormLov.setFieldProp(zProp);
		oFormLov.setProp(obj);
		oFormLov.setFixedProp(prop);
		oFormLov.setControlWins(zCC);
		oFormLov.setSearcheable(true);
		if (title!=null) oFormLov.setTitle(title);
		oFormLov.setOperator("IN");
		oFormLov.setLabel(zName);
		oFormLov.setDetectChanges(false);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormFiltro(oFormLov);

		oControles.AddControl(oFormLov);
		oFormLov.edit("A");
		return oFormLov;
	}
//	public JFormComboResponsive addComboResponsive(String zName, String zProp, JWins zRefWins, boolean zbPermitirTodo, boolean zbPrecargar, int zPreferredWidth) throws Exception {
//		JFormComboResponsive oFormCombo=this.addComboResponsive(zName, zProp, zRefWins, zbPermitirTodo, zbPrecargar);
//		oFormCombo.setPreferredWidth(zPreferredWidth);
//		return oFormCombo;
//	}

	public JFormComboResponsive addComboResponsive(String zName, String zProp, final JWins zRefWins, boolean zbPermitirTodo, boolean zbPrecargar) throws Exception {
		return addComboResponsive(zName, zProp, this.getWin().getRecord().getPropDeepOnlyRef(zProp), this.getWin().getRecord().getFixedPropDeep(zProp), zRefWins, zbPermitirTodo, zbPrecargar);
	}
	public JFormComboResponsive addComboResponsive(String zName, String zProp, JObject<?> obj, JProperty prop, final JWins zRefWins, boolean zbPermitirTodo, boolean zbPrecargar) throws Exception {
		return addComboResponsive(zName, zProp, obj, prop, new JControlCombo() {

			@Override
			public JWins getRecords(boolean zOneRow) throws Exception {
				return zRefWins;
			}
		}, zbPermitirTodo);

	}
	
	public JFormButtonResponsive addBotonResponsive(String zName, int zActionSource, boolean submit) throws Exception {
		  JFormButtonResponsive oButton = new JFormButtonResponsive();
			oButton.setDescripcion(zName);
			if (zActionSource!=-1) {
				BizAction oAction = oWins.findAction(zActionSource);
				oButton.setAction(oAction);	
				oButton.setSubmit(submit);
			} 
			oButton.setForcedValue(zName);
			oButton.setIdControl("label-" + zName.hashCode());

//			this.addFilter(null, oButton);
			oControles.AddControl(oButton);
			return oButton;
	}
		

//	public JFormCheck NuevoCheck(String zName, String zProp, int zPreferredWidth) throws Exception {
//		JFormCheck oFormCheck=this.NuevoCheck(zName, zProp);
//		oFormCheck.setPreferredWidth(zPreferredWidth);
//		return oFormCheck;
//	}
	public JFormRadioResponsive addCheckThreeResponsive(String zName, String zProp) throws Exception {
		return addCheckThreeResponsive(zName,zProp,"S",JFormRadioResponsive.NO_FILTER,"N","Si","-","No");
	}
	public JFormRadioResponsive addCheckThreeResponsive(String zName, String zProp,String trueDescr,String nullDescr,String falseDescr) throws Exception {
		return addCheckThreeResponsive(zName, zProp, this.getWin().getRecord().getPropDeepOnlyRef(zProp), this.getWin().getRecord().getFixedProp(zProp),"S",JFormRadioResponsive.NO_FILTER,"N",trueDescr,nullDescr,falseDescr);
	}
	public JFormRadioResponsive addCheckThreeResponsive(String zName, String zProp,String trueValue,String nullValue,String falseValue,String trueDescr,String nullDescr,String falseDescr) throws Exception {
		return addCheckThreeResponsive(zName, zProp,this.getWin().getRecord().getPropDeepOnlyRef(zProp), this.getWin().getRecord().getFixedProp(zProp),trueValue,nullValue,falseValue,trueDescr,nullDescr,falseDescr);
	}
	public String getMessage(String zMsg) throws Exception {
		String msg = 	BizUsuario.retrieveSkinGenerator().getMessage(zMsg, null);
		return msg;
	}

	public JFormRadioResponsive addCheckThreeResponsive(String zName, String fieldProp, JObject obj, JProperty prop,String trueValue,String nullValue,String falseValue,String trueDescr,String nullDescr,String falseDescr) throws Exception {
		JFormRadioResponsive oFormCheck=new JFormRadioResponsive();
		oFormCheck.SetDisplayName(zName);
		oFormCheck.setFieldProp(fieldProp);
		oFormCheck.AddValor(trueDescr,getMessage(trueValue));
		oFormCheck.AddValor(nullDescr.equals("")?"-":nullDescr,getMessage(nullValue));
		oFormCheck.AddValor(falseDescr,getMessage(falseValue));
		oFormCheck.setProp(obj);
		oFormCheck.setFixedProp(prop);
		oFormCheck.setDetectChanges(false);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormFiltro(oFormCheck);

		if (zName!=null)
			oFormCheck.setLabel(JLanguage.translate(zName));
		oControles.AddControl(oFormCheck);
		oFormCheck.SetValorDefault(nullValue);
		return oFormCheck;
	}


	public JFormCheckResponsive addCheckResponsive(String zName, String zProp) throws Exception {
		return this.nuevoCheckResponsive(zName, zProp, this.getWin().getRecord().getPropDeepOnlyRef(zProp), this.getWin().getRecord().getFixedProp(zProp));
	}
	public JFormCheckResponsive addCheckResponsive(String zName, String zProp,String si, String no) throws Exception {
		return this.nuevoCheckResponsive(zName, zProp, this.getWin().getRecord().getPropDeepOnlyRef(zProp), this.getWin().getRecord().getFixedProp(zProp),si,no);
	}
	public JFormCheckResponsive nuevoCheckResponsive(String zName, String field, JObject obj, JProperty prop) throws Exception {
		return nuevoCheckResponsive(zName,field,obj,prop,"Si","No");
	}
	
	public JFormCheckResponsive nuevoCheckResponsive(String zName, String field, JObject obj, JProperty prop,String si, String no) throws Exception {
		JFormCheckResponsive oFormCheck=new JFormCheckResponsive();
		oFormCheck.setFieldProp(field);
		oFormCheck.SetDisplayName(zName);
		oFormCheck.SetValorCheck("S");
		oFormCheck.SetValorUnCheck("N");
		oFormCheck.setDescripcionValorCheck(si);
		oFormCheck.setDescripcionValorUnCheck(no);
		oFormCheck.setProp(obj);
		oFormCheck.setFixedProp(prop);
//		oFormCheck.setMode(JWebCheckResponsive.MODE_TOGGLE);
		oFormCheck.setLabel(zName);
		oFormCheck.setDetectChanges(false);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormFiltro(oFormCheck);

		oControles.AddControl(oFormCheck);
		return oFormCheck;
	}

	public JFormCDatetimeResponsive addCDateTimeResponsive(String zName, String zProp) throws Exception {
		return this.nuevoCDateTimeResponsive(zName, zProp,this.getWin().getRecord().getPropDeepOnlyRef(zProp), this.getWin().getRecord().getFixedProp(zProp));
	}
	public JFormCDatetimeResponsive nuevoCDateTimeResponsive(String zName, String field, JObject obj, JProperty prop) throws Exception {		
		JFormCDatetimeResponsive oFormCDate=new JFormCDatetimeResponsive();
		oFormCDate.setFieldProp(field);
		oFormCDate.SetDisplayName(zName);
		oFormCDate.setProp(obj);
		oFormCDate.setFixedProp(prop);
		oFormCDate.setOperator("=");
		oFormCDate.setLabel(zName);
		oFormCDate.setDetectChanges(false);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormFiltro(oFormCDate);

		oControles.AddControl(oFormCDate);
		return oFormCDate;
	}



	
	public JFormCDatetimeResponsive addCDateResponsive(String zName, String zProp) throws Exception {
		return this.addCDateResponsive(zName, zProp, this.getWin().getRecord().getPropDeepOnlyRef(zProp), this.getWin().getRecord().getFixedProp(zProp));
	}
	public JFormCDatetimeResponsive addCDateResponsive(String zName, String field, JObject obj, JProperty prop) throws Exception {
		JFormCDatetimeResponsive oFormCDate=new JFormCDatetimeResponsive();
		oFormCDate.setFieldProp(field);
		oFormCDate.SetDisplayName(zName);
		oFormCDate.setProp(obj);
		oFormCDate.setWithTime(false);
		oFormCDate.setFixedProp(prop);
		oFormCDate.setOperator("=");
		oFormCDate.setLabel(zName);
		oFormCDate.setDetectChanges(false);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormFiltro(oFormCDate);

		oControles.AddControl(oFormCDate);
		return oFormCDate;
	}
	
	public JFormIntervalCDatetimeResponsive  addIntervalCDateResponsive(String zName, String zProp) throws Exception {
		return this.addIntervalCDateResponsive(zName, zProp, this.getWin().getRecord().getPropDeepOnlyRef(zProp), this.getWin().getRecord().getFixedProp(zProp));
	}
	public JFormIntervalCDatetimeResponsive addIntervalCDateResponsive(String zName, String field,JObject obj, JProperty prop) throws Exception {
		JFormIntervalCDatetimeResponsive oFormCDate=new JFormIntervalCDatetimeResponsive();
		oFormCDate.setFieldProp(field);
		oFormCDate.SetDisplayName(zName);
		oFormCDate.setLabel(zName);
		oFormCDate.setProp(obj);
		oFormCDate.setFixedProp(prop);
		oFormCDate.setWithTime(false);
		oFormCDate.setOperator("=");
//		this.addFilter(new JPssLabel("  "+zName+" "), oFormCDate);
		oFormCDate.setLabel(zName);
		oFormCDate.setDetectChanges(false);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormFiltro(oFormCDate);

		oControles.AddControl(oFormCDate);
		return oFormCDate;
	}

	public JFormIntervalCDatetimeResponsive addIntervalCDateTimeResponsive(String zName, String zProp) throws Exception {
		return this.addIntervalCDateTimeResponsive(zName, zProp, this.getWin().getRecord().getPropDeepOnlyRef(zProp), this.getWin().getRecord().getFixedProp(zProp));
	}
	public JFormIntervalCDatetimeResponsive addIntervalCDateTimeResponsive(String zName, String field,JObject obj, JProperty prop) throws Exception {		
		JFormIntervalCDatetimeResponsive oFormCDate=new JFormIntervalCDatetimeResponsive();
		oFormCDate.setWithTime(true);
		oFormCDate.SetDisplayName(zName);
		oFormCDate.setFieldProp(field);
		oFormCDate.setProp(obj);
		oFormCDate.setFixedProp(prop);
//		oFormCDate.setWidth(250);
		oFormCDate.setOperator("=");
		oFormCDate.setLabel(zName);
		oFormCDate.setDetectChanges(false);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormFiltro(oFormCDate);

//		this.addFilter(new JPssLabel("  "+zName+" "), oFormCDate);
		oControles.AddControl(oFormCDate);
		return oFormCDate;
	}

	public void Remove() throws Exception {
		oControles.RemoveAll();
//		UITools.hierarchy().cleanUpFromHierarchy(this);
//		oWin=null;
		oWins=null;
		oControles=null;
	}

	public void addFilter(JFormFiltro filtros) throws Exception {
		JIterator<JFormControl> iter = filtros.GetControles().GetItems();
		while(iter.hasMoreElements()) {
			JFormControl control = iter.nextElement();
			String sc = control.toString();
			if (sc.equals("")) continue;
//			this.addFilter(control.getLinkedLabel(), control);
			oControles.AddControl(control);
		}
	}
	
	public JFormControl findControl(String control) throws Exception {
		return this.GetControles().findControl(control);
	}
	
	@Override
	public String toString() {
		String tostring = "";
		JIterator<JFormControl> iter = this.GetControles().GetItems();
		while(iter.hasMoreElements()) {
			JFormControl control = iter.nextElement();
			String sc = control.toString();
			if (sc.equals("")) continue;
			tostring += control.toString()+"; ";
		}
		return tostring;
	}
	
	
	public String swapInfo() throws Exception {
		String tostring = "";
		JIterator<JFormControl> iter = this.GetControles().GetItems();
		while(iter.hasMoreElements()) {
			JFormControl control = iter.nextElement();
			if (!control.hasToRefreshForm()) continue;
			String sc = control.getValue();
			if (sc.equals("")) continue;
			tostring += sc+"; ";
		}
		return tostring;
	}
		
	String name;
	public void setName(String v) {
		this.name=v;
	}
	public String getName() {
		if (name==null) return this.getClass().getName();
		return this.name;
	}




}
