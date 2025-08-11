package pss.core.winUI.responsiveControls;

import java.io.Serializable;

import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;
import pss.core.graph.Graph;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JScript;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JMatrix;
import pss.core.ui.graphics.JImageIcon;
import pss.core.win.IControl;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormControles;
import pss.core.winUI.controls.JFormForm;
import pss.core.winUI.controls.JFormLista;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIcon;
import pss.core.winUI.icons.GuiIconos;
import pss.core.winUI.lists.JPlantilla;
import pss.core.winUI.lists.JWinBigData;
import pss.core.winUI.lists.JWinGridExpand;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.lists.JWinMatrix;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.ui.JWebIcon;

public class JFormPanelResponsive extends JFormControlResponsive  {
	
	public static final String FIELD_LABEL_VERTICAL = "V";
	public static final String FIELD_LABEL_HORIZONTAL_LEFT = "HL";
	public static final String FIELD_LABEL_HORIZONTAL_RIGHT = "HR";
	public static final String FIELD_LABEL_HORIZONTAL_CENTER = "HC";
	public static final String CLASSDEFAULT = "XXX_CLASSDEFAULT";
	
	private JFormControles oControles;
	private JWin oWin;
	private JBaseForm oForm;
	private JFormPanelResponsive oParent;
	private boolean gutter;
	private boolean equals;
	private boolean toBottom;
	private long zoomtofit;
	private String dataparent;
	private long organizeColumns=1L;

	private String formatFields=FIELD_LABEL_VERTICAL;
	private int sizeLabels=5;
	public long getOrganizeColumns() {
		return organizeColumns;
	}

	public JFormPanelResponsive setOrganizeColumns(long organizeColumns) {
		this.organizeColumns = organizeColumns;
		return this;
	}

	public boolean isEqualHieghtPanels() {
		return equals;
	}

	public JFormPanelResponsive setEqualHeightPanels(boolean equals) {
		this.equals = equals;
		return this;
	}
	public String getDataparent() {
		return dataparent;
	}

	public void setDataparent(String dataparent) {
		this.dataparent = dataparent;
	}

	public long getZoomtofit() {
		return zoomtofit;
	}
	public boolean isToBottom() {
		return toBottom;
	}

	public JFormPanelResponsive setToBottom(boolean toBottom) {
		this.toBottom = toBottom;
		return this;
	}
	public JFormPanelResponsive setZoomtofit(long zoomtofit) {
		this.zoomtofit = zoomtofit;
		return this;
	}

	public void setLayout() {
	}
	public boolean isGutter() {
		return gutter;
	}

	public JFormPanelResponsive setGutter(boolean gutter) {
		this.gutter = gutter;
		return this;
	}

	public JWin getBaseWin() {
		if (oWin!=null) return oWin;
//		return oParent.getBaseWin();
		if (oParent!=null) return oParent.getBaseWin();
		return oWin;
	}

	public void setWin(JWin oWin) {
		this.oWin = oWin;
	}

	public JBaseForm getForm() {
		if (oForm!=null) return oForm;
//		return oParent.getForm();
		if (oParent!=null) return oParent.getForm();
		return oForm;
	}

	public void setForm(JBaseForm zForm) {
		this.oForm = zForm;
	}

	public JFormPanelResponsive getParent() {
		return oParent;
	}

	public void setParent(JFormPanelResponsive oParent) {
		this.oParent = oParent;
	}

	public JFormControles getControles() {
		if (oControles==null) oControles= new JFormControles(getForm(),this);
		return oControles;
	}

	public void setControles(JFormControles zValue) {
		oControles = zValue;
	}

  //-------------------------------------------------------------------------//
  // Constructor
  //-------------------------------------------------------------------------//
  public JFormPanelResponsive() {
 	}
  
  @Override
  public String getResponsiveClass() {
  	if (responsiveClass==null) return CLASSDEFAULT;
  	return super.getResponsiveClass();
  }
  
  
  
  
  
	public void ControlToBaseWithinCheck() throws Exception {
		JFormControl oCon = null;

		JIterator<JFormControl> oIterator = getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			oCon.ControlToBaseWithinCheck();
		}
		super.ControlToBaseWithinCheck();
	}  
  
	public void ControlToBase() throws Exception {
		JFormControl oCon = null;

		JIterator<JFormControl> oIterator = getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			oCon.ControlToBase();
		}
		super.ControlToBase();
	}

	public void addControl(JMatrix<Long,Long,JList<JFormControlResponsive>> newCtrls,Long y,Long x,JFormControlResponsive control) {
		JList<JFormControlResponsive> list = newCtrls.getElement(y, x);
		if (list==null) {
				list = JCollectionFactory.createList();
				newCtrls.addElement(y, x, list);
		}
		list.addElement(control);
	}

	// -------------------------------------------------------------------------//
	// Mando todo al control
	// -------------------------------------------------------------------------//
	
	public void BaseToControl(String zModo, boolean userRequest) throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			try {
				oCon.BaseToControl(zModo, userRequest);
			} catch (Exception e) {
				System.out.println("Error FormControl = "+oCon.getName());
				throw e;
			}
		}
		super.BaseToControl(zModo, userRequest);
	}

	// -------------------------------------------------------------------------//
	// Blanquea todos los controles
	// -------------------------------------------------------------------------//
	public void clear() throws Exception {
//		JFormControl oCon = null;
//		JIterator<JFormControl> oIterator = getControles().GetItems();
//		while (oIterator.hasMoreElements()) {
//			oCon = oIterator.nextElement();
//			oCon.clear();
//		}
		super.clear();
	}

	// -------------------------------------------------------------------------//
	// Proteger todos los controles
	// -------------------------------------------------------------------------//
	public void disable() throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			oCon.disable();
		}
		super.disable();
	}

	// -------------------------------------------------------------------------//
	// Editar todos los controles
	// -------------------------------------------------------------------------//
	public void edit(String zModo) throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			oCon.edit(zModo);
		}
		super.edit(zModo);
	}

	// -------------------------------------------------------------------------//
	// findControl
	// -------------------------------------------------------------------------//
	public JFormControl findControl(String row,String zName) throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			JFormControl find = oCon.findControl(row,zName);
			if (find!=null) return find;
		}
		return null;
	}

	public JFormCardResponsive findCard(String row,String zName) throws Exception {
		if (this instanceof JFormCardResponsive)
			return (JFormCardResponsive)this;
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			JFormCardResponsive find = oCon.findCard(row,zName);
			if (find!=null) return  find;
		}
		return null;
	}
	// -------------------------------------------------------------------------//
	// recarga los scripts
	// -------------------------------------------------------------------------//


	public void Remove() throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			oCon.Remove();
			oCon = null;
		}
		super.Remove();
	}

	public void internalBuild() throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			oCon.internalBuild();
		}
	}
	// -------------------------------------------------------------------------//
	// Editar Campos Ingresables
	// -------------------------------------------------------------------------//
	public void editIfPosible(String zModo, boolean partialRefresh) throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			oCon.editIfPosible(zModo, partialRefresh);
		}
//		super.editIfPosible(zModo);
	}
	// -------------------------------------------------------------------------//
	// SetearFiltros
	// -------------------------------------------------------------------------//

	public void setearFiltros(JRecord zBD) throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			oCon.setearFiltros(zBD);
		}
		super.setearFiltros(zBD);
	}
	
	// -------------------------------------------------------------------------//
	// findControlByField
	// -------------------------------------------------------------------------//

	public JFormControl findControlByField(String zName, String operador) throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			JFormControl finder = oCon.findControlByField(zName,operador);
			if (finder!= null) return finder;
		}

		return null;

	}
	// -------------------------------------------------------------------------//
	// recarga los scripts
	// -------------------------------------------------------------------------//
	public void refreshScripts() throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			oCon.refreshScripts();
		}
	}
	// -------------------------------------------------------------------------//
	// recordDataToDefault
	// -------------------------------------------------------------------------//

	public void recordDataToDefault() throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			oCon.recordDataToDefault();
		}
	}
	// -------------------------------------------------------------------------//
	// Fuerzo los datos preasignados
	// -------------------------------------------------------------------------//
	public void asignDefaultData() throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			oCon.asignDefaultData();
		}
		super.asignDefaultData();
	}
	
	public void setForcedDefault(boolean a)  {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			oCon.setForcedDefault(a);
		}
		super.setForcedDefault(a);
	}
	
	
	public void clearDefault() throws Exception {
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			oCon.clearDefault();
		}
		super.clearDefault();
	}

  // additems
	public JFormIntervalCDatetimeResponsive AddItemIntervalDateTime(String label, String zTipoDato, String zRequer, String zCampoFrom, String zCampoTo) throws Exception {
		JFormIntervalCDatetimeResponsive oDate;

		oDate = new JFormIntervalCDatetimeResponsive();
		oDate.initialize();
		oDate.setPropFrom(getBaseWin().getRecord().getPropDeep(zCampoFrom));
		oDate.setFixedPropFrom(getBaseWin().getRecord().getFixedPropDeep(zCampoFrom));
		oDate.setFieldPropFrom(zCampoFrom);
		oDate.setPropTo(getBaseWin().getRecord().getPropDeep(zCampoTo));
		oDate.setFixedPropTo(getBaseWin().getRecord().getFixedPropDeep(zCampoTo));
		oDate.setFieldPropTo(zCampoTo);
		oDate.setLabel(label);
		oDate.setIdControl(zCampoFrom+zCampoTo);
		oDate.SetRequerido(zRequer);
		oDate.SetTipoDato(zTipoDato);
		if (zTipoDato.equals(JBaseForm.DATETIME))
			oDate.setWithTime(true);
		oDate.setResponsive(true);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oDate);

		getControles().AddControl(oDate,findByField(zCampoFrom));//,findByField(zCampoTo));
		return oDate;
	}

	
	public JFormIntervalCDatetimeResponsive AddItemIntervalDateTime(String label, String zTipoDato, String zRequer, String zCampo) throws Exception {
		JFormIntervalCDatetimeResponsive oDate;

		oDate = new JFormIntervalCDatetimeResponsive();
		oDate.initialize();
		oDate.setProp(getBaseWin().getRecord().getPropDeep(zCampo));
		oDate.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(zCampo));
		oDate.setFieldProp(zCampo);
		oDate.setLabel(label);
		oDate.setIdControl(zCampo);
		oDate.SetRequerido(zRequer);
		oDate.SetTipoDato(zTipoDato);
		if (zTipoDato.equals(JBaseForm.DATETIME))
			oDate.setWithTime(true);
		oDate.setResponsive(true);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oDate);

		getControles().AddControl(oDate,findByField(zCampo));
		return oDate;
	}
  
	public JFormCDatetimeResponsive AddItemDateTime(String label, String zTipoDato, String zRequer, String zCampo, long size) throws Exception {
		JFormCDatetimeResponsive c = this.AddItemDateTime(label, zTipoDato, zRequer, zCampo);
		c.setSizeColumns(size);
		return c;
	}
	public JFormCDatetimeResponsive AddItemDateTime(String label, String zTipoDato, String zRequer, String zCampo) throws Exception {
		JFormCDatetimeResponsive oDate;

		oDate = new JFormCDatetimeResponsive();
		oDate.initialize();
		oDate.setProp(getBaseWin().getRecord().getPropDeep(zCampo));
		oDate.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(zCampo));
		oDate.setLabel(label);
		oDate.SetRequerido(zRequer);
		oDate.SetTipoDato(zTipoDato);
		if (zTipoDato.equals(JBaseForm.DATETIME))
			oDate.setWithTime(true);
		if (zTipoDato.equals(JBaseForm.HOUR)) {
			oDate.setWithTime(true);
			oDate.setWithDate(false);
		}
		oDate.setResponsive(true);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oDate);
		getControles().AddControl(oDate,findByField(zCampo));
		return oDate;
	}
	public JFormLineResponsive AddItemLine() throws Exception {
		JFormLineResponsive line = new JFormLineResponsive();
		line.setWithLine(true);
  	getControles().AddControl(line);

		return line;
	}
	public JFormButtonResponsive AddItemButton(String label,int idAction, boolean submit) throws Exception {
		return AddItemButton(label, null, idAction,submit, null, null, false);
	}
	public JFormButtonResponsive AddItemButton(String label,int idAction, boolean submit, Serializable data) throws Exception {
		return AddItemButton(label, null, idAction,submit, null, data);
	}
	public JFormButtonResponsive AddItemButton(String label,String zActionSource, boolean submit) throws Exception {
		return AddItemButton(label, (JWebIcon) null, zActionSource,submit,(Serializable) null);
	}
	public JFormButtonResponsive AddItemButton(String label,String zActionSource, boolean submit, Serializable data) throws Exception {
		return AddItemButton(label, null, zActionSource,submit, null, data);
	}
	public JFormButtonResponsive AddItemButtonBack(String label) throws Exception {
		return AddItemButtonBack(label, null);
	}
	public JFormButtonResponsive AddItemButtonBack(String label,JWebIcon icon) throws Exception {
		JFormButtonResponsive oButton= new JFormButtonResponsive();
		oButton.initialize();
		oButton.setLabel(label);
		oButton.setVisible(true);
		oButton.setImagen(icon);
		oButton.setResponsiveClass("btn btn-default");
		oButton.setBack(true);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oButton);
		getControles().AddControl(oButton);

		return oButton;
	}
	public JFormButtonResponsive AddItemButton(String label,JScript script) throws Exception {
		return AddItemButton(label, null, -1,false, script, null, false);

	}
	public JFormButtonResponsive AddItemButton(String label,JWebIcon icon,int idAction, boolean submit) throws Exception {
		return AddItemButton(label, icon, idAction,submit, null, null, false);
	}
	public JFormButtonResponsive AddItemButton(String label,JWebIcon icon,String idAction, boolean submit) throws Exception {
		return AddItemButton(label, icon, idAction,submit, null, null, false);
	}

	public JFormButtonResponsive AddItemButton(String label,JWebIcon icon,JScript script) throws Exception {
		return AddItemButton(label, icon, -1,false, script, null, false);

	}
	public JFormButtonResponsive AddItemButton(String label,JWebIcon icon,String zActionSource, boolean submit, Serializable data) throws Exception {
		return AddItemButton(label, icon,zActionSource,submit,null,data);
	}
	public JFormButtonResponsive AddItemButton(String label,JWebIcon icon,String zActionSource, boolean submit,String alignContent) throws Exception {
		return AddItemButton(label, icon,zActionSource,submit,null,null,alignContent);
	}
	
	public JFormButtonResponsive AddItemButton(String label,JWebIcon icon,String zActionSource, boolean submit,JScript script, Serializable data,String alignContent) throws Exception {
		return AddItemButton(label, icon, ""+zActionSource,submit,script,data,alignContent,true);
	}
	
	public JFormButtonResponsive AddItemButton(String label,JWebIcon icon,int zActionSource, boolean submit,JScript script) throws Exception {
		return AddItemButton(label, icon,zActionSource,submit,script,null);
	}
	public JFormButtonResponsive AddItemButton(String label,JWebIcon icon,int zActionSource, boolean submit, Serializable data) throws Exception {
		return AddItemButton(label, icon,zActionSource,submit,null,data);
	}
	public JFormButtonResponsive AddItemButton(String label,JWebIcon icon,int zActionSource, boolean submit,String alignContent) throws Exception {
		return AddItemButton(label, icon,zActionSource,submit,null,null,alignContent);
	}
	public JFormButtonResponsive AddItemButton(String label,JWebIcon icon,int zActionSource, boolean submit,JScript script, Serializable data) throws Exception {
		return AddItemButton(label, icon,zActionSource,submit,script,data,null);
	}
	
	public JFormButtonResponsive AddItemButton(String label,JWebIcon icon,int zActionSource, boolean submit,JScript script, Serializable data,String alignContent) throws Exception {
		return AddItemButton(label, icon, ""+zActionSource,submit,script,data,alignContent,true);
	}

//	public JFormButtonResponsive AddItemButton(String label,JWebIcon icon,BizAction action, boolean submit,JScript script) throws Exception {
//		return AddItemButton(label,icon,action,submit,script,null);
//	}
//	public JFormButtonResponsive AddItemButton(String label,JWebIcon icon,BizAction action, boolean submit,JScript script, Serializable data) throws Exception {
//		return AddItemButton(label, icon,action.getId(),action,submit,script,data,null,true);
//	}
	final static String ACTION_REFRESHFORM = "-1";


	private boolean isActionRestricted(BizAction a) throws Exception {
		// acciones que no deberian estan en botones, pongo el control por las dudas ya que poner un cero por ejemplo podria estaria mal (me paso) HGK
		if (a.getId()==0) return true;
		return false;
	}

	public JFormButtonResponsive AddItemButton(String label,JWebIcon icon,int idAction, boolean submit, JScript script, boolean checkAction) throws Exception {

		return this.AddItemButton(label, icon, idAction, submit, script);
	}
	public void addGap(int len) throws Exception {
		this.AddItemLabel("", 0).setMinWidth(len);
	}

	public void addVerticalGap(int len) throws Exception {
		this.AddItemColumn().setMinHeight(len);
	}
//	public JFormButtonResponsive addButton(int nroIcon, String idAction, boolean checkAction) throws Exception {
//    return this.AddItemButton( null, JWebIcon.findIcon(nroIcon), idAction, false, null, null, checkAction);
//  }
	
	public JFormButtonResponsive addButton(int nroIcon, int idAction, boolean checkAction, int size) throws Exception {
		return this.addButton(nroIcon, ""+idAction, checkAction, size);
	}
	public JFormButtonResponsive addButton(int nroIcon, String idAction, boolean checkAction, int size) throws Exception {
		JFormButtonResponsive b = this.AddItemButton( null, JWebIcon.findIcon(nroIcon), idAction, false, null, checkAction);
		if (b==null) return null;
		b.setSizeColumns(size);
		return b;
	}
	public JFormButtonResponsive addButton(int nroIcon, String idAction, boolean checkAction) throws Exception {
		return this.AddItemButton( null, JWebIcon.findIcon(nroIcon), idAction, false, null, null, checkAction);
	}
	public JFormButtonResponsive addButton(int nroIcon, int idAction, boolean checkAction) throws Exception {
		return this.AddItemButton( null, JWebIcon.findIcon(nroIcon), idAction, false, null, checkAction);
	}
	public JFormButtonResponsive addButton(String label,int idAction, boolean checkAction) throws Exception {
		return AddItemButton(label, null, idAction,false, null, null, checkAction);
	}
	public JFormButtonResponsive addButton(String label,int idAction) throws Exception {
		return this.addButton(label, ""+idAction);
	}
	public JFormButtonResponsive addButton(String label,String idAction) throws Exception {
		return AddItemButton(label, null, idAction,false, null, false);
	}
	public JFormButtonResponsive addButton(int nroIcon, int idAction) throws Exception {
		return this.AddItemButton( null, JWebIcon.findIcon(nroIcon), idAction, false);
	}
	public JFormButtonResponsive addButton(int nroIcon, String idAction) throws Exception {
		return this.AddItemButton( null, JWebIcon.findIcon(nroIcon), idAction, false, null, true);
	}
	public JFormButtonResponsive addButton(int nroIcon, int idAction, int size) throws Exception {
		return this.addButton(nroIcon, ""+idAction, size);
	}
	public JFormButtonResponsive addButton(int nroIcon, String idAction, int size) throws Exception {
		JFormButtonResponsive b = this.AddItemButton( null, JWebIcon.findIcon(nroIcon), idAction, false, null, true);
		if (b==null) return null;
		b.setSizeColumns(size);
		return b;
	}

	public JFormButtonResponsive addButtonScript(int nroIcon, JScript sc) throws Exception {
		return this.AddItemButton("", JWebIcon.findIcon(nroIcon), null, true, sc, null, false);
	}

	public JFormButtonResponsive addButtonScript(String label, JScript sc) throws Exception {
		return this.AddItemButton(label, null, null, true, sc, null, false);
	}

	public JFormButtonResponsive addButtonScript(String label, int icon, JScript sc) throws Exception {
		return this.AddItemButton(label, JWebIcon.findIcon(icon), null, true, sc, null, false);
	}

	public JFormButtonResponsive addButton(String label, int idAction, int size) throws Exception {
		return this.addButton(label, ""+idAction, size);
	}
	public JFormButtonResponsive addButton(String label, String idAction, int size) throws Exception {
		JFormButtonResponsive b = this.AddItemButton(label, null, idAction, false, null, false);
		if (b==null) return null;
		b.setSizeColumns(size);
		return b;
	}
	public JFormButtonResponsive addButton(String label, int idAction, boolean checkAction, int size) throws Exception {
		JFormButtonResponsive b = this.AddItemButton(label, null, idAction, false, null, null, checkAction);
		if (b==null) return null;
		b.setSizeColumns(size);
		return b;
	}
	public JFormButtonResponsive AddItemButton(String label,JWebIcon icon,String zActionSource, boolean submit,JScript script) throws Exception {
		return this.AddItemButton(label, icon, zActionSource, submit, script, null);
	}
	
	public JFormButtonResponsive AddItemButton(String label,JWebIcon icon,String zActionSource, boolean submit,JScript script, Serializable data) throws Exception {
		return AddItemButton(label,icon,zActionSource,submit,script,data, true);
	}
		
	public JFormButtonResponsive AddItemButton(String label,JWebIcon icon,String idAction, boolean submit,JScript script, Serializable data, boolean checkAction) throws Exception {
		return this.AddItemButton(label, icon, idAction, submit, script, data,null,checkAction);
	}

	public JFormButtonResponsive AddItemButton(String label,JWebIcon icon,int idAction, boolean submit, JScript script, Serializable data, boolean checkAction) throws Exception {
		return this.AddItemButton(label, icon, idAction+"", submit, script, data, null, checkAction);
	}

	public JFormButtonResponsive AddItemButton(String label,JWebIcon icon,String idAction, boolean submit,JScript script, boolean checkAction, int size) throws Exception {
		JFormButtonResponsive b = this.AddItemButton(label, icon, idAction, submit, script, checkAction);
		if (b==null) return null;
		b.setSizeColumns(size);
		return b;
	}
	public JFormButtonResponsive AddItemButton(String label,JWebIcon icon,String idAction, boolean submit,JScript script, Serializable data,String alignContent, boolean checkAction) throws Exception {
		BizAction action=null;
		boolean ok=(idAction!=null);
		if (idAction!=null && !idAction.equals(ACTION_REFRESHFORM)) {
			action = this.getBaseWin().findActionByUniqueId(idAction, false, false);
			ok=(action!=null && action.isOkAll());
			if (checkAction) {
				if (action==null) return null;
				if(!ok) return null;
			} 
		}	
		
		JFormButtonResponsive oButton= new JFormButtonResponsive();
		oButton.initialize();
		oButton.setLabel(label);
		oButton.setVisible(true);
		oButton.setImagen(icon);
		oButton.setResponsiveClass("btn btn-default");
		if (alignContent!=null) 
			JExcepcion.SendError("deprecado, no usar mas. use: .left(). .right() . center()");
//			oButton.setAlignContent(alignContent);
		oButton.setData(data);
		if (action!=null && ok) {
			if (this.isActionRestricted(action)) 
				JExcepcion.SendError("Acción no permitida");
			BizAction oAction = action;
			oButton.setAction(oAction);	
			oButton.setSubmit(submit);
			oButton.setToolTip(oAction.GetDescr());
		} else if (idAction!=null&&idAction.equals("-1")) {
			oButton.setRefreshForm(true);
			ok=true;
		}
		oButton.setScript(script);
		if(!ok) oButton.setReadOnly(true);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oButton);
		getControles().AddControl(oButton);

    return oButton;
  }
	
	public JFormControlResponsive addButtonLeft(String texto, int nroIcon, int idAction, boolean any) throws Exception {
		return this.addButtonLeftOk(null, texto, nroIcon,  ""+idAction, any);
	}
	public JFormControlResponsive addButtonLeft(String texto, int nroIcon, String idAction, boolean any) throws Exception {
		return this.addButtonLeftOk(null, texto, nroIcon,  idAction, any);
	}
	public JFormControlResponsive addButtonLeft(String title, String texto, int idAction, boolean any) throws Exception {
		return this.addButtonLeftOk(title, texto, 0, ""+idAction, any);
	}
	public JFormControlResponsive addButtonLeft(String title, String texto, String idAction, boolean any) throws Exception {
		return this.addButtonLeftOk(title, texto, 0, idAction, any);
	}
	private JFormControlResponsive addButtonLeftOk(String title, String texto, int nroIcon, String action, boolean any) throws Exception {
		JFormControlResponsive c;
		if (any) {
			if (title!=null) this.AddItemLabel(title, 0);
			if (action==null && nroIcon==0) {
				c = this.AddItemLabel(texto, 0);
			} else {
				c = this.AddItemButton(texto, JWebIcon.findIcon(nroIcon), action, false);
				if (c!=null) {
					JFormButtonResponsive b = (JFormButtonResponsive)c;
					b.setImagePadding(0, 0, 0, 10);
				} else {
					c=this.AddItemLabel(texto); // no tienen permiso pongo label
				}
//				else (b.seti)
				c.setSizeColumns(0);
			}
		} else { // pongo como boton el titulo para poder darlo de alta
			if (action==null) return null;
			c = this.AddItemButton(title, JWebIcon.findIcon(nroIcon), action, false);
			if (c==null) c=this.AddItemLabel(title, 0);
			c.setSizeColumns(0);
		}
		return c;
	}

	public JFormDropDownButtonResponsive AddItemDropDownButton(String button) throws Exception {
		return AddItemDropDownButton(null,button,null);
	}
	public JFormDropDownButtonResponsive AddItemDropDownButton(String label,String button) throws Exception {
		return AddItemDropDownButton(label,button,null);
	}
	
	public JFormDropDownButtonResponsive AddItemDropDownButton(String label,String button,JWebIcon icon) throws Exception {
		JFormDropDownButtonResponsive oButton= new JFormDropDownButtonResponsive();
		oButton.initialize();
		oButton.setLabel(label);
		oButton.setLabelButton(button);
		oButton.setVisible(true);
		if (icon!=null)
			oButton.setImagen(icon);
		oButton.setParent(this);
	
		oButton.setResponsiveClass("btn-group btn-default");
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oButton);
		getControles().AddControl(oButton);

		return oButton;
	}
	public String getMessage(String zMsg) throws Exception {
		String msg = 	BizUsuario.retrieveSkinGenerator().getMessage(zMsg, null);
		return msg;
	}
	public JFormCheckResponsive AddItemCheck(String label, String zTipoDato, String zRequer, String sCampo, String zValorCheck, String zValorUnCheck) throws Exception {
		return AddItemCheck(label, zTipoDato, zRequer, sCampo, zValorCheck, zValorUnCheck,"Si","No");
	}	
	public JFormCheckResponsive AddItemCheck(String label, String zTipoDato, String zRequer, String sCampo, String zValorCheck, String zValorUnCheck,String descrCheck,String descrUncheck) throws Exception {
		return AddItemCheck(label, zTipoDato, zRequer,sCampo,zValorCheck,zValorUnCheck,descrCheck,descrUncheck,null);
	}
	public JFormCheckResponsive AddItemCheck(String label, String zTipoDato, String zRequer, String sCampo, String zValorCheck, String zValorUnCheck,String descrCheck,String descrUncheck,JScript script) throws Exception {
		JFormCheckResponsive oCheck;

		oCheck = new JFormCheckResponsive();
		oCheck.initialize();
		oCheck.setProp(getBaseWin().getRecord().getPropDeep(sCampo));
		oCheck.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));
		oCheck.SetValorCheck(zValorCheck);
		oCheck.SetValorUnCheck(zValorUnCheck);
		oCheck.SetRequerido(zRequer);
		oCheck.SetTipoDato(zTipoDato);
		oCheck.setDescripcionValorCheck(getMessage(descrCheck));
		oCheck.setDescripcionValorUnCheck(getMessage(descrUncheck));
		oCheck.setScript(script);
//		oCheck.setMode(JWebCheckResponsive.MODE_TOGGLE);

		oCheck.setLabel(label);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oCheck);
		getControles().AddControl(oCheck,findByField(sCampo));
		return oCheck;
	}

	public JFormCheckResponsive AddItemCheck(String label, String zRequer, String sCampo, long size) throws Exception {
		JFormCheckResponsive c = this.AddItemCheck(label, zRequer, sCampo);
		c.setSizeColumns(size);
		return c;
	}
	public JFormCheckResponsive AddItemCheck(String label, String zRequer, String sCampo) throws Exception {
		return AddItemCheck(label, JBaseForm.CHAR, zRequer, sCampo, "S", "N");
	}
	public JFormCheckResponsive AddItemCheck(String label, String zRequer, String sCampo,JScript script) throws Exception {
		return AddItemCheck(label, JBaseForm.CHAR, zRequer, sCampo, "S", "N","Si","No",script);
	}
	public JFormRadioResponsive AddItemThreeCheck(String label, String zTipoDato, String zRequer, String sCampo ) throws Exception {
		return AddItemThreeCheck(label, zTipoDato, zRequer, sCampo, "S",JFormRadioResponsive.NO_FILTER,"N","Si","-","No");
	}
	public JFormRadioResponsive AddItemThreeCheck(String label, String zTipoDato, String zRequer, String sCampo, String trueValue,String nullValue,String falseValue,String trueDescr,String nullDescr,String falseDescr) throws Exception {
	
		JFormRadioResponsive oCheck=new JFormRadioResponsive();
		oCheck.initialize();
		oCheck.SetDisplayName(label);
		oCheck.AddValor(trueDescr,trueValue);
		oCheck.AddValor(nullDescr.equals("")?"-":nullDescr,nullValue);
		oCheck.AddValor(falseDescr,falseValue);
		oCheck.setProp(getBaseWin().getRecord().getPropDeep(sCampo));
		oCheck.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));
		oCheck.setLabel(JLanguage.translate(label));
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oCheck);
		getControles().AddControl(oCheck,findByField(sCampo));
		oCheck.SetValorDefault(nullValue);
		
		return oCheck;
	}


	public JFormColorPickerResponsive AddItemColor(String zEdit, String zTipoDato, String zRequer) throws Exception {
		return AddItemColor(zEdit, zTipoDato, zRequer, null);
	}
	public JFormColorPickerResponsive AddItemColor(String zEdit, String zTipoDato, String zRequer, String sCampo) throws Exception {
		JFormColorPickerResponsive oEdit;

		oEdit = new JFormColorPickerResponsive();
		oEdit.initialize();
		
		if (sCampo != null) {
			JObject<?> prop = getBaseWin().getRecord().getPropDeep(sCampo);
			oEdit.setProp(prop);
			oEdit.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));
		}
		oEdit.SetRequerido(zRequer);
		oEdit.SetTipoDato(zTipoDato);
		oEdit.setLabel(zEdit);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oEdit);
		getControles().AddControl(oEdit,findByField(sCampo));

		return oEdit;

	}
	public JFormControl removeField( JFormControl sCampo) throws Exception {
		if (sCampo==null) return null;
		JFormControl oCon = null;
		JIterator<JFormControl> oIterator = getControles().GetItems();
		while (oIterator.hasMoreElements()) {
			oCon = oIterator.nextElement();
			JFormControl finder = oCon.removeField(sCampo);
			if (finder!= null) {
				 getControles().Remove(sCampo);
				 return sCampo;
			}
		}

		return null;
	}
	public int removeField( String sCampo) throws Exception {
		if (sCampo==null) return -1;
		return getForm().getInternalPanel().getControles().removeById(sCampo);
	}
	public JFormControl findByField( String sCampo) throws Exception {
		if (sCampo==null) return null;
		return getForm().getInternalPanel().findControlByField(sCampo,null);
	}
	public JFormComboResponsive AddItemCombo(String zCombo, String zTipoDato, String zRequer, String sCampo, JWins zWins, long size) throws Exception {
		JFormComboResponsive c = this.AddItemCombo(zCombo, zTipoDato, zRequer, sCampo);
		c.setSizeColumns(size);
		return c;
	}
	public JFormComboResponsive AddItemCombo(String zCombo, String zTipoDato, String zRequer, String sCampo, JWins zWins) throws Exception {
		JFormComboResponsive oCombo;

		oCombo = new JFormComboResponsive();
		oCombo.initialize();
		oCombo.setProp(getBaseWin().getRecord().getPropDeep(sCampo));
		oCombo.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));

		oCombo.SetWinsAsociado(zWins);
		oCombo.SetRequerido(zRequer);
		// oCombo.SetFiltrosFKey ( zFKey );

		oCombo.SetTipoDato(zTipoDato);
		oCombo.setLabel(zCombo);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oCombo);

		getControles().AddControl(oCombo,findByField(sCampo));
		return oCombo;
	}
	public JFormComboResponsive AddItemCombo(String zCombo, String zTipoDato, String zRequer, String sCampo) throws Exception {
		return AddItemCombo(zCombo, zTipoDato, zRequer, sCampo,getBaseWin().getRecord().getFixedPropDeep(sCampo).getControl(getBaseWin().getRecord()));
	}
	public JFormComboResponsive AddItemCombo(String zCombo, String zTipoDato, String zRequer, String sCampo, IControl zCC, long size) throws Exception {
		JFormComboResponsive c = this.AddItemCombo(zCombo, zTipoDato, zRequer, sCampo, zCC);
		c.setSizeColumns(size);
		return c;
	}
	public JFormComboResponsive AddItemCombo(String zCombo, String zTipoDato, String zRequer, String sCampo, IControl zCC) throws Exception {
		JFormComboResponsive oCombo;

		oCombo = new JFormComboResponsive();
		oCombo.initialize();
		oCombo.setProp(getBaseWin().getRecord().getPropDeep(sCampo));
		oCombo.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));
		oCombo.SetControlWin(zCC);
		oCombo.SetRequerido(zRequer);
		oCombo.SetTipoDato(zTipoDato);
		oCombo.setLabel(zCombo);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oCombo);
		getControles().AddControl(oCombo,findByField(sCampo));
		return oCombo;
	}
	
	public JFormDropDownComboResponsive AddItemDropDownCombo(String zDropDown, String zTipoDato, String zRequer, String sCampo, JWins zWins, long size) throws Exception {
		JFormDropDownComboResponsive c = this.addItemDropDownCombo(zDropDown, zTipoDato, zRequer, sCampo);
		c.setSizeColumns(size);
		return c;
	}
	public JFormDropDownComboResponsive AddItemDropDownCombo(String zDropDown, String zTipoDato, String zRequer, String sCampo, JWins zWins) throws Exception {
		JFormDropDownComboResponsive oDropDown;

		oDropDown = new JFormDropDownComboResponsive();
		oDropDown.initialize();
		oDropDown.setProp(getBaseWin().getRecord().getPropDeep(sCampo));
		oDropDown.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));

		oDropDown.SetWinsAsociado(zWins);
		oDropDown.SetRequerido(zRequer);
		// oDropDown.SetFiltrosFKey ( zFKey );

		oDropDown.SetTipoDato(zTipoDato);
		oDropDown.setLabel(zDropDown);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oDropDown);

		getControles().AddControl(oDropDown,findByField(sCampo));
		return oDropDown;
	}
	public JFormDropDownComboResponsive addItemDropDownCombo(String zDropDown, String zTipoDato, String zRequer, String sCampo) throws Exception {
		return AddItemDropDownCombo(zDropDown, zTipoDato, zRequer, sCampo,getBaseWin().getRecord().getFixedPropDeep(sCampo).getControl(getBaseWin().getRecord()));
	}
	public JFormDropDownComboResponsive AddItemDropDownCombo(String zDropDown, String zTipoDato, String zRequer, String sCampo, IControl zCC, long size) throws Exception {
		JFormDropDownComboResponsive c = this.AddItemDropDownCombo(zDropDown, zTipoDato, zRequer, sCampo, zCC);
		c.setSizeColumns(size);
		return c;
	}
	public JFormDropDownComboResponsive AddItemDropDownCombo(String zDropDown, String zTipoDato, String zRequer, String sCampo, IControl zCC) throws Exception {
		JFormDropDownComboResponsive oDropDown;

		oDropDown = new JFormDropDownComboResponsive();
		oDropDown.initialize();
		oDropDown.setProp(getBaseWin().getRecord().getPropDeep(sCampo));
		oDropDown.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));
		oDropDown.SetControlWin(zCC);
		oDropDown.SetRequerido(zRequer);
		oDropDown.SetTipoDato(zTipoDato);
		oDropDown.setLabel(zDropDown);
		oDropDown.setIdActionNew("1");
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oDropDown);
		getControles().AddControl(oDropDown,findByField(sCampo));
		return oDropDown;
	}
	public JFormDivResponsive AddDivPanel( ) throws Exception {
		JFormDivResponsive oPanel = new JFormDivResponsive();
		oPanel.initialize();
		oPanel.setForm(getForm());
		oPanel.setWin(getBaseWin());
		getControles().AddControl(oPanel);
		return oPanel;
	}		

	public JFormDivResponsive AddInLinePanel( ) throws Exception {
		JFormDivResponsive panel = this.AddDivPanel();
		panel.setResponsiveClass("inline-row");
		return panel;
	}		

	public JFormPanelResponsive AddItemPanel(String zTitle) throws Exception {
		JFormPanelResponsive  oPanel;

		oPanel = new JFormPanelResponsive();
		oPanel.initialize();
		oPanel.setParent(this);
		oPanel.setFormatFields(this.getFormatFields());

		getControles().AddControl(oPanel);
		return oPanel;
	}
	public JFormPanelResponsive AddItemRow() throws Exception {
		JFormPanelResponsive  oPanel;

		oPanel = new JFormPanelResponsive();
		oPanel.initialize();
		oPanel.setParent(this);
		oPanel.setWin(getBaseWin());
		oPanel.setResponsiveClass("row");
		oPanel.setFormatFields(this.getFormatFields());

		getControles().AddControl(oPanel);
		return oPanel;
	}

	public JFormControl AddItemRow(JFormControl control ) throws Exception {
		return getControles().AddControl(control);
	
	}
	public JFormFieldsetResponsive addItemFieldset(String zTitle, int size) throws Exception {
		JFormFieldsetResponsive p = this.AddItemFieldset(zTitle);
		p.setSizeColumns(size);
		return p;
	}
	public JFormFieldsetResponsive AddItemFieldset(String zTitle, long size) throws Exception {
		JFormFieldsetResponsive p = AddItemFieldset(zTitle);
		p.setSizeColumns(size);
		return p;
	}

	public JFormFieldsetResponsive AddItemFieldset(String zTitle) throws Exception {
		JFormFieldsetResponsive  oPanel;

		oPanel = new JFormFieldsetResponsive();
		oPanel.initialize();
		oPanel.setParent(this);
		oPanel.setWin(getBaseWin());
		oPanel.setFormatFields(this.getFormatFields());
		if (zTitle!=null) oPanel.setTitle(zTitle);
		getControles().AddControl(oPanel);
		return oPanel;
	}
	public JFormFieldsetExpandedResponsive AddItemFieldsetExpanded(String zTitle) throws Exception {
		JFormFieldsetExpandedResponsive  oPanel;

		oPanel = new JFormFieldsetExpandedResponsive();
		oPanel.initialize();
		oPanel.setParent(this);
		oPanel.setWin(getBaseWin());
		oPanel.setFormatFields(this.getFormatFields());
		if (zTitle!=null) oPanel.setTitle(zTitle);
		getControles().AddControl(oPanel);
		return oPanel;
	}
	public JFormColumnResponsive AddItemColumn() throws Exception {
		return AddItemColumn(null);
	}
	
	public JFormColumnResponsive AddImageContainer(int size) throws Exception {
		JFormColumnResponsive col = this.AddItemColumn(null,size);
		col.setResponsiveClass("pss-image");
		return col;
	}

	public JFormPanelResponsive addPanelHalf(boolean preview) throws Exception {
		JFormPanelResponsive panel =  this.AddItemColumn(12);
		panel.setComplexColumnClass(this.getResponsiveHalf(preview));
		return panel;
	}
	public JFormTabPanelResponsive addTabHalf(boolean preview) throws Exception {
		JFormTabPanelResponsive tab =  this.AddItemTabPanel();
		tab.setComplexColumnClass(this.getResponsiveHalf(preview));
		return tab;
	}

	public JFormColumnResponsive AddItemColumn(String classResponsive) throws Exception {
		return AddItemColumn(classResponsive,0);
	}
	public JFormColumnResponsive AddItemColumn(int size) throws Exception {
		return AddItemColumn(null,size);
	}
	public JFormColumnResponsive AddItemColumn(String classResponsive,int size) throws Exception {
		JFormColumnResponsive  oPanel;

		oPanel = new JFormColumnResponsive();
		oPanel.initialize();
		oPanel.setParent(this);
		if (classResponsive!=null) oPanel.setComplexColumnClass(classResponsive);
		if (size!=0) oPanel.setSizeColumns(size);
		getControles().AddControl(oPanel);
		return oPanel;
	}

	public JFormEditResponsive AddItemEdit(String zEdit, String zTipoDato, String zRequer) throws Exception {
		return AddItemEdit(zEdit, zTipoDato, zRequer, null);
	}
	public JFormEditResponsive AddItemEdit(String zEdit, String zTipoDato, String zRequer, String sCampo, long size) throws Exception {
		JFormEditResponsive e = this.AddItemEdit(zEdit, zTipoDato, zRequer, sCampo);
		e.setSizeColumns(size);
		return e;
	}

	public JFormEditResponsive AddItemEdit(String zEdit, String zTipoDato, String zRequer, String sCampo) throws Exception {
		return AddItemEdit(zEdit, null, zTipoDato, zRequer, sCampo, null);
	}

	public JFormEditResponsive AddItemEdit(String zEdit, String zTipoDato, String zRequer, String sCampo, long size, JScript script) throws Exception {
		JFormEditResponsive e = this.AddItemEdit(zEdit, zTipoDato, zRequer, sCampo, script);
		e.setSizeColumns(size);
		return e;
	}

	public JFormEditResponsive AddItemEdit(String zEdit, String zTipoDato, String zRequer, String sCampo, JScript script) throws Exception {
		return AddItemEdit(zEdit, null, zTipoDato, zRequer, sCampo,script);
	}
	public JFormEditResponsive AddItemEdit(String zEdit,JWebIcon zLabelIcon, String zTipoDato, String zRequer, String sCampo) throws Exception {
		return AddItemEdit(zEdit, zTipoDato, zRequer,null);
	}
	public JFormEditResponsive AddItemEdit(String zEdit,JWebIcon zLabelIcon, String zTipoDato, String zRequer, String sCampo, JScript script) throws Exception {
		JFormEditResponsive oEdit;

		oEdit = new JFormEditResponsive();
		oEdit.initialize();
		oEdit.setLabel(zEdit);
		oEdit.setLabelIcon(zLabelIcon);
    
		if (sCampo != null) {
			JObject<?> prop = getBaseWin().getRecord().getPropDeep(sCampo);
			if (script!=null) prop.setScript(script);
			oEdit.setProp(prop);
			oEdit.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));
//			getForm().getInternalPanel().getControles().removeById(sCampo);
		}
		oEdit.setScript(script);
		oEdit.SetRequerido(zRequer);
		oEdit.SetTipoDato(zTipoDato);
		getControles().AddControl(oEdit,findByField(sCampo));

		return oEdit;

	}
	
	public JFormPasswordResponsive AddItemPassword(String zEdit, String zTipoDato, String zRequer, String sCampo) throws Exception {
		JFormPasswordResponsive oEdit;

		oEdit = new JFormPasswordResponsive();
		oEdit.initialize();
		oEdit.setLabel(zEdit);

		if (sCampo != null) {
			JObject<?> prop = getBaseWin().getRecord().getPropDeep(sCampo);
			oEdit.setProp(prop);
			oEdit.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));
			getForm().getInternalPanel().getControles().removeById(sCampo);
		}
		oEdit.SetRequerido(zRequer);
		oEdit.SetTipoDato(zTipoDato);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oEdit);

		getControles().AddControl(oEdit,findByField(sCampo));

		return oEdit;

	}


	public JFormLabelDataResponsive AddItemLabelData( String zTipoDato,String sCampo) throws Exception {
		JFormLabelDataResponsive oEdit;

		oEdit = new JFormLabelDataResponsive();
		oEdit.initialize();

		JObject<?> prop = getForm().getBaseWin().getRecord().getPropDeep(sCampo);
		oEdit.setProp(prop);
		oEdit.setFixedProp( getForm().getBaseWin().getRecord().getFixedPropDeep(sCampo));
		getForm().getInternalPanel().getControles().removeById(sCampo);

		oEdit.SetRequerido(JBaseForm.OPT);
		oEdit.SetTipoDato(zTipoDato);
//		oEdit.setLabel(prop==null?"":prop.toFormattedString());
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oEdit);

		getControles().AddControl(oEdit,findByField(sCampo));

		return oEdit;

	}
	
	public JFormLabelDataResponsive AddItemLabelData(String label, String zTipoDato,String sCampo, long size) throws Exception {
		JFormLabelDataResponsive l = this.AddItemLabelData(label, zTipoDato, sCampo);
		l.setSizeColumns(size);
		return l;
	}
	
	public JFormLabelDataResponsive AddItemLabelData(String label, String zTipoDato,String sCampo) throws Exception {
		JFormLabelDataResponsive oEdit;

		oEdit = new JFormLabelDataResponsive();
		oEdit.initialize();

		JObject<?> prop = getForm().getBaseWin().getRecord().getPropDeep(sCampo);
		oEdit.setProp(prop);
		oEdit.setFixedProp( getForm().getBaseWin().getRecord().getFixedPropDeep(sCampo));
		getForm().getInternalPanel().getControles().removeById(sCampo);

		oEdit.SetRequerido(JBaseForm.OPT);
		oEdit.SetTipoDato(zTipoDato);
		oEdit.setLabel(label);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oEdit);

		getControles().AddControl(oEdit,findByField(sCampo));

		return oEdit;

	}
	public JFormEditResponsive AddItemInfo(String zEdit, String zTipoDato, String sCampo) throws Exception {
		JFormEditResponsive oEdit;

		oEdit = new JFormEditResponsive();
		oEdit.initialize();
		oEdit.setLabel(zEdit);

		if (sCampo != null) {
			JObject<?> prop = getBaseWin().getRecord().getPropDeep(sCampo);
			oEdit.setProp(prop);
			oEdit.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));
			getForm().getInternalPanel().getControles().removeById(sCampo);
		}
		oEdit.SetTipoDato(zTipoDato);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oEdit);

		getControles().AddControl(oEdit,findByField(sCampo));

		return oEdit;

	}

	public JFormFileResponsive AddItemFile(String label, String zTipoDato, String zRequer, String sCampo) throws Exception {
		JFormFileResponsive file = new JFormFileResponsive();
		file.initialize();
		file.setLabel(label);

		JObject<?> prop = getBaseWin().getRecord().getPropDeep(sCampo);
		file.setProp(prop);
		file.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));
		file.SetRequerido(zRequer);
		file.SetTipoDato(zTipoDato);
		getForm().getInternalPanel().getControles().removeById(sCampo);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(file);

		getControles().AddControl(file,findByField(sCampo));

		return file;

	}

	public JFormButtonPayResponsive AddItemButtonPay(String mode,String pais,String moneda,String estado,double monto,String sCampo) throws Exception {
		JFormButtonPayResponsive buttonpay = new JFormButtonPayResponsive();
		buttonpay.initialize();
		
		JObject<?> prop = getBaseWin().getRecord().getPropDeep(sCampo);
		buttonpay.setMode(mode);
		buttonpay.setProp(prop);
		buttonpay.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));
		buttonpay.setCountry(pais);
		buttonpay.setCurrency(moneda);
		buttonpay.setStatus(estado);
		buttonpay.setPrice(monto);
		getForm().getInternalPanel().getControles().removeById(sCampo);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(buttonpay);

		getControles().AddControl(buttonpay,findByField(sCampo));

		return buttonpay;

	}


	public JFormImageResponsive AddItemImage(String zLabel, int action, Graph graph) throws Exception {
		return AddItemImage(zLabel,action,-1, graph,"",false,JImageIcon.PAINT_SCALED);
	}
	public JFormImageResponsive AddItemImage(String zLabel, int action, int graph) throws Exception {
		return AddItemImage(zLabel,action,-1, graph,"",false,JImageIcon.PAINT_SCALED);
	}
	public JFormImageResponsive AddItemImage(String zLabel, int actionSource, int actionLink,int graph) throws Exception {
		return AddItemImage(zLabel,actionSource,actionLink, graph,"",false,JImageIcon.PAINT_SCALED);
	}
	public JFormImageResponsive AddItemImage(String zLabel, String sCampo) throws Exception {
			return AddItemImage(zLabel,-1,-1,0,sCampo,false,JImageIcon.PAINT_SCALED);
	}
	public JFormImageResponsive AddItemImage(String zLabel, String sCampo,boolean expand,int type) throws Exception {
		return AddItemImage(zLabel,-1,-1,0,sCampo,false,JImageIcon.PAINT_SCALED);
	}
	public JFormImageResponsive addItemImage(int nroIcon, int size) throws Exception {
		JFormImageResponsive img = this.AddItemImage(JWebIcon.findIcon(nroIcon));
//		img.setSizeColumns(size);
		return img;
	}
	public JFormImageResponsive AddItemImage(JWebIcon icon) throws Exception {
		return AddItemImage(icon,false,JImageIcon.PAINT_SCALED);
	}
	
	
	public JFormImageResponsive AddItemImage(JWebIcon icon,boolean expand,int type) throws Exception {
		JFormImageResponsive oImage;

		oImage = new JFormImageResponsive();
		oImage.initialize();
		oImage.setIcon(icon);
		getControles().AddControl(oImage);
		oImage.setExpand(expand);
		oImage.setType(type);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oImage);

		return oImage;
	}
	public JFormImageResponsive AddItemImage(String zLabel, int zActionSource, int zActionLink, Graph graph, String sCampo,boolean expand,int type) throws Exception {
		JFormImageResponsive oImage;

		oImage = new JFormImageResponsive();
		oImage.initialize();
		oImage.setLabel(zLabel);
		if (zActionSource!=-1) {
			BizAction oAction = getBaseWin().findAction(zActionSource);
			oImage.setGraph(graph);
			oImage.setActionSource(oAction);
		} else {
			if (!sCampo.equals("")) {
				oImage.setProp(getBaseWin().getRecord().getPropDeep(sCampo));
				oImage.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));
				getForm().getInternalPanel().getControles().removeById(sCampo);
			}
		}
		if (zActionLink!=-1)  oImage.setActionLink(getBaseWin().findAction(zActionLink));
		getControles().AddControl(oImage,findByField(sCampo));
		oImage.setExpand(expand);
		oImage.setType(type);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oImage);

		return oImage;
	}
	

	public JFormImageResponsive AddItemImage(String zLabel, int zActionSource, int zActionLink, int graph, String sCampo,boolean expand,int type) throws Exception {
		JFormImageResponsive oImage;

		oImage = new JFormImageResponsive();
		oImage.initialize();
		oImage.setLabel(zLabel);
		if (zActionSource!=-1) {
			BizAction oAction = getBaseWin().findAction(zActionSource);
			oImage.setIdGraph(graph);
			oImage.setActionSource(oAction);
		} else {
			if (!sCampo.equals("")) {
				oImage.setProp(getBaseWin().getRecord().getPropDeep(sCampo));
				oImage.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));
//				getForm().getInternalPanel().getControles().removeById(sCampo);
//				findByField(sCampo);
			}
		}
		if (zActionLink!=-1)  oImage.setActionLink(getBaseWin().findAction(zActionLink));
		getControles().AddControl(oImage,findByField(sCampo));
		oImage.setExpand(expand);
		oImage.setType(type);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oImage);
		return oImage;
  }
	public JFormCardResponsive AddCardPanel(String title, String zId ) throws Exception {
		BizAction oAction = getBaseWin().findActionByUniqueId(zId);
		return AddCardPanel(null,null,null,oAction,title);
	}		
	public JFormCardResponsive AddCardPanel(String title, int zId ) throws Exception {
		BizAction oAction = getBaseWin().findAction(zId);
		return AddCardPanel(null,null,null,oAction,title);
	}		
	public JFormCardResponsive AddCardPanel( String zId ) throws Exception {
		BizAction oAction = getBaseWin().findActionByUniqueId(zId);
		String tit = oAction!=null? this.getBaseWin().getDescrAction(oAction): null;
		return AddCardPanel(null,null,null,oAction,tit);
	}		
	public JFormCardResponsive AddCardPanel( int zId ) throws Exception {
		BizAction oAction = getBaseWin().findAction(zId);
		String tit = oAction!=null? this.getBaseWin().getDescrAction(oAction): null;
		return AddCardPanel(null,null,null,oAction,tit);
	}		
	public JFormCardResponsive AddCardPanel( BizAction action, String title) throws Exception {
		return AddCardPanel(null,null,null,action,title);
	}
	
	public JFormCardResponsive AddCardPanel(String field, Class zClass, String zActionId) throws Exception {
		return AddCardPanel(field,zClass,zActionId,null,null);
	}
	
	public JFormCardResponsive AddCardPanel(String field, Class zClass, String zActionId,String title) throws Exception {
		return AddCardPanel(field,zClass,zActionId,null,title);
	}
	public JFormCardResponsive AddCardPanel(String field, Class zClass, int zActionId) throws Exception {
		return AddCardPanel(field,zClass,""+zActionId,null,null);
	}
	
	public JFormCardResponsive AddCardPanel(String field, Class zClass, int zActionId,String title) throws Exception {
		return AddCardPanel(field,zClass,""+zActionId,null,title);
	}
	public JFormCardResponsive AddCardPanel( String field, Class zClass, String zActionId, BizAction action, String title) throws Exception {
		JFormCardResponsive oPanel = new JFormCardResponsive();
		oPanel.initialize();
		oPanel.setParent(this);
		oPanel.setWin(getBaseWin());
		oPanel.setAction(action);
		oPanel.setTitle(title);
		oPanel.setField(field);
		oPanel.setClassField(zClass);
		oPanel.setFieldActionId(zActionId);
	
		getControles().AddControl(oPanel);
		return oPanel;
	}		

	
	public JFormInfoCardResponsive AddInfoCard(String zLabel, String zTipoDato, String sCampo, int icon,String labelLink,String zaction) throws Exception {
		return this.AddInfoCard(zLabel, zTipoDato, sCampo, JWebIcon.getPssIcon(GuiIconos.RESPONSIVE, icon), labelLink, zaction);
	}
	public JFormInfoCardResponsive AddInfoCard(String zLabel, String zTipoDato, String sCampo,JWebIcon image,String labelLink,int zaction) throws Exception {
		BizAction action = null;
		if (zaction!=-1) {
			 action = getBaseWin().findAction(zaction);
			if (action==null) return new JFormInfoCardResponsive(); //lo crea pero no lo agrega  a ningun lado, solo para no tener que controlar el null
		}
		return AddInfoCard(zLabel, zTipoDato, sCampo,image,labelLink, action, null);
	}
	public JFormInfoCardResponsive AddInfoCard(String zLabel, String zTipoDato, String sCampo,JWebIcon image,String labelLink,String zaction) throws Exception {
		BizAction action = null;
		if (zaction!=null) {
			action = getBaseWin().findActionByUniqueId(zaction);
			if (action==null) return new JFormInfoCardResponsive();//lo crea pero no lo agrega  a ningun lado, solo para no tener que controlar el null
		}
		return AddInfoCard(zLabel, zTipoDato, sCampo,image,labelLink, action, null);
	}
	public JFormInfoCardResponsive AddInfoCard(String zLabel, String zTipoDato, String sCampo,JWebIcon image,String labelLink,BizAction action, String directLink) throws Exception {
		return AddInfoCard(zLabel, zTipoDato, sCampo, image, labelLink, action, directLink, true);
	}
	
	public JFormInfoCardResponsive AddInfoCard(String zLabel, String zTipoDato, String sCampo,JWebIcon image,String labelLink,BizAction action, String directLink,boolean submit) throws Exception {
		JFormInfoCardResponsive oEdit= new JFormInfoCardResponsive();
		oEdit.initialize();

		oEdit.setLabel(zLabel);

		if (sCampo != null) {
			JObject<?> prop = getBaseWin().getRecord().getPropDeep(sCampo);
			oEdit.setProp(prop);
			oEdit.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));
			getForm().getInternalPanel().getControles().removeById(sCampo);
		}
		oEdit.SetTipoDato(zTipoDato);
		if (action!=null)
			oEdit.setAction(action);
		oEdit.setImagen(image);
		oEdit.setLabelLink(labelLink);
		oEdit.setSubmit(submit);
		oEdit.setDirectLink(directLink);
		oEdit.setResponsiveClass("card ");
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oEdit);
		getControles().AddControl(oEdit,findByField(sCampo));
		return oEdit;
	}

	public JFormImageCardResponsive AddImageCard(String zLabel, JWebIcon image,String labelLink,int zaction) throws Exception {
		BizAction action = null;
		if (zaction!=-1) 
			action = getBaseWin().findAction(zaction);
		return AddImageCard(zLabel,image,labelLink, action, zaction==-1);
	}
	public JFormImageCardResponsive AddImageCard(String zLabel, JWebIcon image,String labelLink,String zaction) throws Exception {
		BizAction action = getBaseWin().findActionByUniqueId(zaction);
		return AddImageCard(zLabel, image,labelLink, action);
	}
	public JFormImageCardResponsive AddImageCard(String zLabel, JWebIcon image,String labelLink,BizAction action) throws Exception {
		return AddImageCard(zLabel, image,labelLink,action,false);
	}
	public JFormImageCardResponsive AddImageCard(String zLabel, JWebIcon image,String labelLink,BizAction action, boolean refreshForm) throws Exception {
		if (action==null && !refreshForm) 
			return null;
//		if (!getBaseWin().verifyAction(action,true,true)) return null;
		if (!refreshForm && !action.isOkAll()) return null;
		JFormImageCardResponsive oEdit= new JFormImageCardResponsive();
		oEdit.initialize();

		oEdit.setContent(false);
		oEdit.setLabel(zLabel);

		if (!refreshForm)
			oEdit.setActionLink(action);
		else
			oEdit.setRefreshForm(true);
		oEdit.setIcon(image);
		oEdit.setLabelLink(labelLink);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oEdit);
		getControles().AddControl(oEdit);
		return oEdit;
	}
	public JFormImageCardResponsive AddImageCard(String zLabel, String labelLink,BizAction action, int image) throws Exception {
		if (!action.isOkAll()) return null;
		JFormImageCardResponsive oEdit= new JFormImageCardResponsive();
		oEdit.setContent(false);

		oEdit.setLabel(zLabel);

		oEdit.setActionSource(action);
		oEdit.setActionLink(action);
		oEdit.setIdGraph(image);
		oEdit.setLabelLink(labelLink);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oEdit);
		getControles().AddControl(oEdit);
		return oEdit;
	}

	
	public JFormImageCardResponsive AddImageCard(String zLabel, int action, Graph graph) throws Exception {
		return AddImageCard(zLabel,action,-1, graph,"",false,JImageIcon.PAINT_SCALED,null);
	}
	public JFormImageCardResponsive AddImageCard(String zLabel, int action, int graph) throws Exception {
		return AddImageCard(zLabel,action,-1, graph,"",false,JImageIcon.PAINT_SCALED,null);
	}
	public JFormImageCardResponsive AddImageCard(String zLabel, int actionSource, String lableLink,  int actionLink,int graph) throws Exception {
		return AddImageCard(zLabel,actionSource,actionLink, graph,"",false,JImageIcon.PAINT_SCALED, lableLink);
	}
	public JFormImageCardResponsive AddImageCard(String zLabel, String sCampo) throws Exception {
			return AddImageCard(zLabel,-1,-1,0,sCampo,false,JImageIcon.PAINT_SCALED,null);
	}
	public JFormImageCardResponsive AddImageCard(String zLabel, String sCampo, String lableLink, int zActionLink) throws Exception {
		return AddImageCard(zLabel,-1,zActionLink,0,sCampo,false,JImageIcon.PAINT_SCALED, lableLink);
}
	public JFormImageCardResponsive AddImageCard(String zLabel, String sCampo,boolean expand,int type) throws Exception {
		return AddImageCard(zLabel,-1,-1,0,sCampo,false,JImageIcon.PAINT_SCALED,null);
	}

	public JFormImageCardResponsive AddImageCard(String zLabel, String sCampo, String lableLink, String zActionLink) throws Exception {
		BizAction action = this.getBaseWin().findActionByUniqueId(zActionLink);
		return AddImageCard(zLabel,-1,action,null,sCampo,false,JImageIcon.PAINT_SCALED, lableLink);
	}
	public JFormImageCardResponsive AddImageCard(String zLabel, int zActionSource, int zActionLink, Graph graph, String sCampo,boolean expand,int type,String zLabelLink) throws Exception {
		BizAction action = this.getBaseWin().findAction(zActionLink);
		return this.AddImageCard(zLabel, zActionSource, action, graph, sCampo, expand, type, zLabelLink);
	}
	public JFormImageCardResponsive AddImageCard(String zLabel, int zActionSource, BizAction zActionLink, Graph graph, String sCampo,boolean expand,int type,String zLabelLink) throws Exception {
		JFormImageCardResponsive oImage;
		oImage = new JFormImageCardResponsive();
		oImage.initialize();
		oImage.setContent(true);
		oImage.setLabel(zLabel);
		oImage.setLabelLink(zLabelLink);

		if (zActionSource!=-1) {
			BizAction oAction = getBaseWin().findAction(zActionSource);
			oImage.setGraph(graph);
			oImage.setActionSource(oAction);
		} else {
			if (!sCampo.equals("")) {
				oImage.setProp(getBaseWin().getRecord().getPropDeep(sCampo));
				oImage.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));
			}
		}
		if (zActionLink!=null)  {
			oImage.setActionLink(zActionLink);
			if (zLabelLink==null) oImage.setLabelLink(zActionLink.GetDescr());		
		}
		getControles().AddControl(oImage,findByField(sCampo));
		oImage.setExpand(expand);
		oImage.setType(type);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oImage);
  	return oImage;
	}
	public JFormImageCardResponsive AddImageCard(String zLabel, int zActionSource, int zActionLink, int graph, String sCampo,boolean expand,int type,String zLabelLink) throws Exception {
		JFormImageCardResponsive oImage;

		oImage = new JFormImageCardResponsive();
		oImage.initialize();
		oImage.setContent(true);
		oImage.setLabel(zLabel);
		oImage.setLabelLink(zLabelLink);
		if (zActionSource!=-1) {
			BizAction oAction = getBaseWin().findAction(zActionSource);
			oImage.setIdGraph(graph);
			oImage.setActionSource(oAction);
		} else {
			if (!sCampo.equals("")) {
				oImage.setProp(getBaseWin().getRecord().getPropDeep(sCampo));
				oImage.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));
			}
		}
		if (zActionLink!=-1)  {
			BizAction act = getBaseWin().findAction(zActionLink);
			oImage.setActionLink(act);
			if (zLabelLink==null) oImage.setLabelLink(act.GetDescr());
		}
		getControles().AddControl(oImage,findByField(sCampo));
		oImage.setExpand(expand);
		oImage.setType(type);
		BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oImage);
		return oImage;
  }
	
	public JFormLabelResponsive AddItemLabel(String zLabel, int size) throws Exception {
		JFormLabelResponsive l = this.AddItemLabel(zLabel);
		l.setSizeColumns(size);
		return l;
	}

	public JFormLabelResponsive AddItemLabel(String zLabel) throws Exception {
		 JFormLabelResponsive oControl;
	
	 oControl = new JFormLabelResponsive();
	 oControl.setValue(zLabel);
	 getControles().AddControl(oControl);
	 return oControl;
	}
	
	public JFormMultipleCheckResponsive AddItemMultipleCheck(String zLabel, String zTipoDato, String zRequerido, String zCampo, JControlCombo zWins) throws Exception {
		 JFormMultipleCheckResponsive oControl;
		
		 oControl = new JFormMultipleCheckResponsive();
		 oControl.initialize();
		 oControl.setLabel(zLabel);
		 oControl.setProp(getBaseWin().getRecord().getProp(zCampo));
		 oControl.setFixedProp(getBaseWin().getRecord().getFixedProp(zCampo));
		 oControl.SetControlWin(zWins);
		 oControl.SetTipoDato(zTipoDato);

		 oControl.SetRequerido(zRequerido);
		 BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oControl);
 	 getControles().AddControl(oControl,findByField(zCampo));
		 return oControl;
	 }
	 public JFormMultipleCheckResponsive AddItemMultipleCheck(String zLabel, String zRequerido, String zCampo, JWins zWins) throws Exception {
		 JFormMultipleCheckResponsive oControl;
	
		 oControl = new JFormMultipleCheckResponsive();
		 oControl.initialize();
		 oControl.setLabel(zLabel);
		 oControl.setProp(getBaseWin().getRecord().getPropDeep(zCampo));
		 oControl.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(zCampo));
		 oControl.SetWinsAsociado(zWins);
	
		 oControl.SetRequerido(zRequerido);
		 BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oControl);
  	 getControles().AddControl(oControl,findByField(zCampo));
		 return oControl;
	 }

	 public JFormMultipleResponsive AddItemMultiple(String zLabel, String zTipoDato, String zRequerido, String zCampo, JControlCombo zWins) throws Exception {
		 JFormMultipleResponsive oControl;
		
		 oControl = new JFormMultipleResponsive();
		 oControl.initialize();
		 oControl.setLabel(zLabel);
		 oControl.setProp(getBaseWin().getRecord().getPropDeep(zCampo));
		 oControl.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(zCampo));
		 oControl.SetControlWin(zWins);
		 oControl.SetTipoDato(zTipoDato);

		 oControl.SetRequerido(zRequerido);
		 BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oControl);
		 getControles().AddControl(oControl);
		 return oControl;
	 }
	 public JFormListBoxResponsive AddItemListBox(String zLabel, String zTipoDato, String zRequerido, String zCampo, JControlCombo zWins) throws Exception {
		 JFormListBoxResponsive oControl;
		
		 oControl = new JFormListBoxResponsive();
		 oControl.initialize();
		 oControl.setLabel(zLabel);
		 oControl.setProp(getBaseWin().getRecord().getPropDeep(zCampo));
		 oControl.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(zCampo));
		 oControl.SetControlWin(zWins);
		 oControl.SetTipoDato(zTipoDato);

		 oControl.SetRequerido(zRequerido);
		 BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oControl);
		 getControles().AddControl(oControl,findByField(zCampo));
		 return oControl;
	 }

	 public JFormMultipleResponsive AddItemMultiple(String zLabel, String zRequerido, String zCampo, JWins zWins) throws Exception {
		 JFormMultipleResponsive oControl;
	
		 oControl = new JFormMultipleResponsive();
		 oControl.initialize();
		 oControl.setLabel(zLabel);
		 oControl.setProp(getBaseWin().getRecord().getPropDeep(zCampo));
		 oControl.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(zCampo));
		 oControl.SetWinsAsociado(zWins);
	
		 oControl.SetRequerido(zRequerido);
		 BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oControl);
  	 getControles().AddControl(oControl,findByField(zCampo));
		 return oControl;
	 }
		public JFormMapResponsive AddItemMap(String zMap, String zTipoDato, String zRequer, String sCampo) throws Exception {
			JFormMapResponsive oEdit;

			oEdit = new JFormMapResponsive();
			oEdit.initialize();
			oEdit.setLabel(zMap);
			if (sCampo != null) {
				oEdit.setProp(getBaseWin().getRecord().getPropDeep(sCampo));
				oEdit.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));
			}
			oEdit.SetRequerido(zRequer);
			oEdit.SetTipoDato(zTipoDato);

			 BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oEdit);
	  	if (BizPssConfig.getPssConfig().hasGoogleMaps())
				getControles().AddControl(oEdit,findByField(sCampo));
			return oEdit;
		}
		public JFormMapResponsive AddItemMap(String zMap,  String field, int action, String modo) throws Exception {
			BizAction oAction = getBaseWin().findAction(action);
			return AddItemMap(zMap,JBaseForm.CHAR,JBaseForm.OPT,field,(JWins)oAction.getResult(),modo);
		}
		public JFormMapResponsive AddItemMap(String zMap, int action,String modo) throws Exception {
			BizAction oAction = getBaseWin().findAction(action);
			return AddItemMap(zMap,JBaseForm.CHAR,JBaseForm.OPT,null,(JWins)oAction.getResult(),modo);
		}
		public JFormMapResponsive AddItemMap(String zMap,String zTipoDato, String zRequer, String sField, JWins sCampo,String modo) throws Exception {
			JFormMapResponsive oEdit;

			oEdit = new JFormMapResponsive();
			oEdit.initialize();
			oEdit.setLabel(zMap);
			oEdit.setWins(sCampo);
			oEdit.setProp(getBaseWin().getRecord().getPropDeep(sField));
			oEdit.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sField));
			oEdit.SetRequerido(zRequer);
			oEdit.setMapModo(modo);
			oEdit.SetTipoDato(zTipoDato);
			 BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oEdit);

	  	if (BizPssConfig.getPssConfig().hasGoogleMaps())
				getControles().AddControl(oEdit,findByField(sField));
			return oEdit;
		}


		public JFormTextAreaResponsive AddItem(String zArea, String zTipoDato, String zRequer, String sCampo) throws Exception {
			JFormTextAreaResponsive oArea;

			oArea = new JFormTextAreaResponsive();
			oArea.initialize();
			oArea.setLabel(zArea);
			oArea.setProp(getBaseWin().getRecord().getPropDeep(sCampo));
			oArea.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));
			oArea.SetRequerido(zRequer);
			oArea.SetTipoDato(zTipoDato);
			 BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oArea);

			getControles().AddControl(oArea,findByField(sCampo));
			return oArea;
		}

		public JFormTextAreaResponsive AddItemArea(String zArea, String zTipoDato, String zRequer, String sCampo) throws Exception {
			return AddItemArea(zArea, zTipoDato, zRequer, sCampo, false);
		}

		public JFormTextAreaResponsive AddItemArea(String zArea, String zTipoDato, String zRequer, String sCampo, boolean isWeb) throws Exception {
			return AddItemArea(zArea,zTipoDato,zRequer,sCampo,isWeb,null,null,650,40,0,0,0,null,true);
		}
		public JFormTextAreaResponsive AddItemArea(String zArea, String zTipoDato, String zRequer, String sCampo, boolean isWeb,long anchoPagina, long margenIzq) throws Exception {
			return AddItemArea(zArea,zTipoDato,zRequer,sCampo,isWeb,null,null,anchoPagina,margenIzq,0,0,0,null,true);
		}
		public JFormTextAreaResponsive AddItemArea(String zArea, String zTipoDato, String zRequer, String sCampo, boolean isWeb,long anchoPagina, long margenIzq, long margenImgSup, long margenImgLeft, long margenImgSize, String fondo) throws Exception {
			return AddItemArea(zArea,zTipoDato,zRequer,sCampo,isWeb,null,null,anchoPagina,margenIzq,margenImgSup,margenImgLeft,margenImgSize,fondo,true);
		}
		public JFormTextAreaResponsive AddItemArea(String zArea, String zTipoDato, String zRequer, String sCampo, boolean isWeb, JPlantilla p, String mapaSource,long anchoPagina, long margenIzq, long margenImgSup, long margenImgLeft, long margenImgSize, String fondo,boolean formulario) throws Exception {
			JFormTextAreaResponsive oArea;

			oArea = new JFormTextAreaResponsive();
			oArea.initialize();
			oArea.setLabel(zArea);
			oArea.setProp(getBaseWin().getRecord().getPropDeep(sCampo));
			oArea.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));
			oArea.SetRequerido(zRequer);
			oArea.SetTipoDato(zTipoDato);
			oArea.setWeb(isWeb);
			oArea.setPlantilla(p);
			oArea.setMapaSource(mapaSource);
			oArea.setAnchoPagina(anchoPagina);
			oArea.setMargenIzq(margenIzq);
			oArea.setMargenImgSup(margenImgSup);
			oArea.setMargenImgLeft(margenImgLeft);
			oArea.setMargenImgSize(margenImgSize);
			oArea.setFondo(fondo);
			oArea.setFormulario(formulario);
			 BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oArea);
			getControles().AddControl(oArea,findByField(sCampo));
			return oArea;
		}
		
		public JFormHtmlTextAreaResponsive AddItemHtml(String zLabel, String zTipoDato, String zRequer, String sCampo) throws Exception {
			return AddItemHtml(zLabel, zTipoDato, zRequer, sCampo, false);
		}

		public JFormHtmlTextAreaResponsive AddItemHtml(String zLabel, String zTipoDato, String zRequer, String sCampo, boolean isWeb) throws Exception {
			return AddItemHtml(zLabel,zTipoDato,zRequer,sCampo,isWeb,null,null,650,40,0,0,0,null,true);
		}
		public JFormHtmlTextAreaResponsive AddItemHtml(String zLabel, String zTipoDato, String zRequer, String sCampo, boolean isWeb,long anchoPagina, long margenIzq) throws Exception {
			return AddItemHtml(zLabel,zTipoDato,zRequer,sCampo,isWeb,null,null,anchoPagina,margenIzq,0,0,0,null,true);
		}
		public JFormHtmlTextAreaResponsive AddItemHtml(String zLabel, String zTipoDato, String zRequer, String sCampo, boolean isWeb,long anchoPagina, long margenIzq, long margenImgSup, long margenImgLeft, long margenImgSize, String fondo) throws Exception {
			return AddItemHtml(zLabel,zTipoDato,zRequer,sCampo,isWeb,null,null,anchoPagina,margenIzq,margenImgSup,margenImgLeft,margenImgSize,fondo,true);
		}

		public JFormHtmlTextAreaResponsive AddItemHtml(String zLabel, String zTipoDato, String zRequer, String sCampo, boolean isWeb, JPlantilla p, String mapaSource,long anchoPagina, long margenIzq, long margenImgSup, long margenImgLeft, long margenImgSize, String fondo,boolean formulario) throws Exception {
 

			JFormHtmlTextAreaResponsive oArea;
			oArea = new JFormHtmlTextAreaResponsive();
			oArea.initialize();
			oArea.setLabel(zLabel);
			if (sCampo != null) {
				JObject<?> prop = getBaseWin().getRecord().getPropDeep(sCampo);
				oArea.setProp(prop);
				oArea.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));
				getForm().getInternalPanel().getControles().removeById(sCampo);
			}
			oArea.SetRequerido(zRequer);
			oArea.SetTipoDato(zTipoDato);
			oArea.setWeb(isWeb);
			oArea.setPlantilla(p);
			oArea.setMapaSource(mapaSource);
			oArea.setAnchoPagina(anchoPagina);
			oArea.setMargenIzq(margenIzq);
			oArea.setMargenImgSup(margenImgSup);
			oArea.setMargenImgLeft(margenImgLeft);
			oArea.setMargenImgSize(margenImgSize);
			oArea.setFondo(fondo);
			oArea.setHeight(500);
			oArea.setFormulario(formulario);
			 BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oArea);
			getControles().AddControl(oArea,findByField(sCampo));
			return oArea;
		}
		// -------------------------------------------------------------------------//
		// Agrego un Item RadioGroup
		// -------------------------------------------------------------------------//
		public JFormRadioResponsive AddItemRadio(String zGroup, String zTipoDato, String zRequer, String sCampo,JMap<String,String> options) throws Exception {
			JFormRadioResponsive oRadio;

			oRadio = new JFormRadioResponsive();
			oRadio.initialize();
			oRadio.setLabel(zGroup);
			oRadio.setProp(getBaseWin().getRecord().getPropDeep(sCampo));
			oRadio.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));
			oRadio.SetRequerido(zRequer);
			oRadio.SetTipoDato(zTipoDato);

			JIterator<String> buttonIt = options.getKeyIterator();
			while (buttonIt.hasMoreElements()) {
				String element = buttonIt.nextElement();
				oRadio.AddValor(options.getElement(element),element);
			}
			 BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oRadio);

				getControles().AddControl(oRadio,findByField(sCampo));

			return oRadio;
		}
		public JFormWinLOVResponsive AddItemWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo) throws Exception {
			return this.AddItemWinLov(zLabel, zTipoDato, zRequer, sCampo, null, false, false, -1);
		}

		public JFormWinLOVResponsive AddItemWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, JWins zWins, int action) throws Exception {
			return this.AddItemWinLov(zLabel, zTipoDato, zRequer, sCampo, zWins.createControlWin(), false, false, action);
		}
		public JFormWinLOVResponsive AddItemWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, IControl zControlWins, int action) throws Exception {
			return this.AddItemWinLov(zLabel, zTipoDato, zRequer, sCampo, zControlWins, false, false, action);
		}

		public JFormWinLOVResponsive AddItemWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, JWins zWins) throws Exception {
			return AddItemWinLov(zLabel, zTipoDato, zRequer, sCampo, zWins.createControlWin());
		}

		public JFormWinLOVResponsive AddItemWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, IControl zControlWins) throws Exception {
			return this.AddItemWinLov(zLabel, zTipoDato, zRequer, sCampo, zControlWins, false, false, -1);
		}

		public JFormWinLOVResponsive AddItemWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, int action) throws Exception {
			return AddItemWinLov(zLabel, zTipoDato, zRequer, sCampo, null, false, false, action);
		}

		public JFormWinLOVResponsive AddItemWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, boolean zIsMultiple, boolean zShowsInMultipleLines, int action) throws Exception {
			return AddItemWinLov(zLabel, zTipoDato, zRequer, sCampo, null, zIsMultiple, zShowsInMultipleLines, action);
		}

		public JFormWinLOVResponsive AddItemWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, IControl zControlWins, boolean zIsMultiple, boolean zShowsInMultipleLines, int action) throws Exception {
			IControl controlWins = zControlWins;
			JFormWinLOVResponsive oLOV = new JFormWinLOVResponsive(zIsMultiple, zShowsInMultipleLines);
			if (action != -1)
				oLOV.setAction(getBaseWin(), getBaseWin().findAction(action));
			else if (controlWins==null){
				controlWins = getBaseWin().getRecord().getFixedPropDeep(sCampo).getControl(getBaseWin().getRecord());
				if (!controlWins.buildWin().getRecord().getFixedProp(controlWins.buildWin().getKeyField()).isAutonumerico())
					oLOV.setShowKey(true);
			}
			oLOV.initialize();
			oLOV.setLabel(zLabel);
			oLOV.setControlWins(controlWins);
			oLOV.setProp(getBaseWin().getRecord().getPropDeep(sCampo));
			oLOV.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));
			oLOV.SetRequerido(zRequer);
			oLOV.SetTipoDato(zTipoDato);
			BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oLOV);

	//RJL		getBaseWin().setCanConvertToURL(false); //OJO/
			getControles().AddControl(oLOV,findByField(sCampo));

			return oLOV;
		}
		public JFormDropDownWinLovResponsive addItemDropDownWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo) throws Exception {
			return this.addItemDropDownWinLov(zLabel, zTipoDato, zRequer, sCampo, null, false, false, -1);
		}

		public JFormDropDownWinLovResponsive addItemDropDownWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, JWins zWins, int action) throws Exception {
			return this.addItemDropDownWinLov(zLabel, zTipoDato, zRequer, sCampo, zWins.createControlWin(), false, false, action);
		}
		public JFormDropDownWinLovResponsive addItemDropDownWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, IControl zControlWins, int action) throws Exception {
			return this.addItemDropDownWinLov(zLabel, zTipoDato, zRequer, sCampo, zControlWins, false, false, action);
		}

		public JFormDropDownWinLovResponsive addItemDropDownWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, JWins zWins) throws Exception {
			return addItemDropDownWinLov(zLabel, zTipoDato, zRequer, sCampo, zWins.createControlWin());
		}

		public JFormDropDownWinLovResponsive addItemDropDownWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, IControl zControlWins) throws Exception {
			return this.addItemDropDownWinLov(zLabel, zTipoDato, zRequer, sCampo, zControlWins, false, false, -1);
		}

		public JFormDropDownWinLovResponsive addItemDropDownWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, int action) throws Exception {
			return addItemDropDownWinLov(zLabel, zTipoDato, zRequer, sCampo, null, false, false, action);
		}

		public JFormDropDownWinLovResponsive addItemDropDownWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, boolean zIsMultiple, boolean zShowsInMultipleLines, int action) throws Exception {
			return addItemDropDownWinLov(zLabel, zTipoDato, zRequer, sCampo, null, zIsMultiple, zShowsInMultipleLines, action);
		}

		public JFormDropDownWinLovResponsive addItemDropDownWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, IControl zControlWins, boolean zIsMultiple, boolean zShowsInMultipleLines, int action) throws Exception {
			IControl controlWins = zControlWins;
			JFormDropDownWinLovResponsive oLOV = new JFormDropDownWinLovResponsive(zIsMultiple, zShowsInMultipleLines);
			if (action != -1)
				oLOV.setAction(getBaseWin(), getBaseWin().findAction(action));
			else if (controlWins==null){
				controlWins = getBaseWin().getRecord().getFixedPropDeep(sCampo).getControl(getBaseWin().getRecord());
				if (!controlWins.buildWin().getRecord().getFixedProp(controlWins.buildWin().getKeyField()).isAutonumerico())
					oLOV.setShowKey(true);
			}
			oLOV.initialize();
			oLOV.setLabel(zLabel);
			oLOV.setWin(getBaseWin());
			oLOV.setControlWins(controlWins);
			oLOV.setProp(getBaseWin().getRecord().getPropDeep(sCampo));
			oLOV.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sCampo));
			oLOV.SetRequerido(zRequer);
			oLOV.SetTipoDato(zTipoDato);
			oLOV.setIdActionNew("1");
			oLOV.setIdActionUpdate("2");
			BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oLOV);

		//RJL	getBaseWin().setCanConvertToURL(false); //OJO/
			getControles().AddControl(oLOV,findByField(sCampo));

			return oLOV;
		}

		public JFormTableResponsive AddItemTable(String zTable, String zCampo, Class jwins) throws Exception {
			return AddItemTable(zTable, zCampo, jwins, 2, -1,null,true);
		}
		public JFormTableResponsive AddItemTable(String zTable, String zCampo, Class jwins, String vision) throws Exception {
			return AddItemTable(zTable, zCampo, jwins, 2, -1,null,true,vision);
		}
		public JFormTableResponsive AddItemTable(String zTable, String zCampo, Class jwins, boolean editable) throws Exception {
			return AddItemTable(zTable, zCampo, jwins, 2, -1,null,editable);
		}
		public JFormTableResponsive AddItemTable(String zTable, String zCampo, Class jwins,JRecords valorDefault) throws Exception {
			return AddItemTable(zTable, zCampo, jwins, 2, -1,valorDefault,true);
		}
		public JFormTableResponsive AddItemTable(String zTable, String zCampo, Class jwins,JRecords valorDefault, boolean editable) throws Exception {
			return AddItemTable(zTable, zCampo, jwins, 2, -1,valorDefault,editable);
		}
		public JFormTableResponsive AddItemTable(String zTable, String zCampo, Class jwins, int updateaction) throws Exception {
			return AddItemTable(zTable, zCampo, jwins, updateaction, -1);
		}
		public JFormTableResponsive AddItemTable(String zTable, String zCampo, Class jwins, int updateaction,JRecords valorDefault) throws Exception {
			return AddItemTable(zTable, zCampo, jwins, updateaction, -1);
		}
		public JFormTableResponsive AddItemTable(String zTable, String zCampo, Class jwins, int updateaction, int newaction) throws Exception {
			return AddItemTable(zTable, zCampo, jwins, updateaction, newaction,null,true);
		}
		public JFormTableResponsive AddItemTable(String zTable, String zCampo, Class jwins, int updateaction, int newaction,JRecords valorDefault) throws Exception {
			return AddItemTable(zTable, zCampo, jwins, updateaction, newaction, valorDefault,true);
		}
		public JFormTableResponsive AddItemTable(String zTable, String zCampo, Class jwins, int updateaction, int newaction,JRecords valorDefault, boolean editable) throws Exception {
			return AddItemTable(zTable, zCampo, jwins, updateaction, newaction, valorDefault ,editable, null);
		}
		public JFormTableResponsive AddItemTable(String zTable, String zCampo, Class jwins, int updateaction, int newaction,JRecords valorDefault, boolean editable,String vision) throws Exception {
			return AddItemTable(zTable, zCampo, jwins, updateaction, newaction, valorDefault ,editable, vision, false);
		}
		public JFormTableResponsive AddItemTable(String zTable, String zCampo, Class jwins, int updateaction, int newaction,JRecords valorDefault, boolean editable,String vision,boolean multiSelect) throws Exception {
			if (zCampo == null)
				return null;
		  JFormTableResponsive oLista = new JFormTableResponsive();
		  oLista.initialize();
			oLista.setVision(vision);
			// generate form control
			oLista.setProp(getBaseWin().getRecord().getProp(zCampo));
			oLista.setFixedProp(getBaseWin().getRecord().getFixedProp(zCampo));
			oLista.setWin(this.getBaseWin());
			oLista.setLabel(zTable);
			oLista.setBaseForm(getForm());
			oLista.setMultipleSelect(multiSelect);
			//oLista.setForm(getForm());
			oLista.setNewAction(newaction);
			oLista.setUpdateAction(updateaction);
			oLista.setjWinClass(jwins);
			oLista.setCampo(zCampo);
			oLista.setIdControl(zCampo);
			oLista.SetReadOnly(!editable);
			oLista.SetDisplayName(zTable==null?getBaseWin().getRecord().getFixedProp(zCampo).GetDescripcion():zTable);
			oLista.setKeepHeight(getForm().isFullSize());
			oLista.setKeepWidth(getForm().isFullSize());
			 BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oLista);
			getControles().AddControl(oLista,findByField(zCampo));
			if (valorDefault!=null) oLista.SetValorDefault(valorDefault);
			oLista.setResponsive(true);
	//		oLista.GetLista();
			return oLista;
		}
///
	

		public JFormTableExpandResponsive AddItemTableExpand(String zTable, String zCampo, Class jwins) throws Exception {
			return AddItemTableExpand(zTable, zCampo, jwins, 2, -1,-1,null,true,null,false,JWinGridExpand.GRID1ROW);
		}
		public JFormTableExpandResponsive AddItemTableExpand(String zTable, String zCampo, Class jwins, boolean editable) throws Exception {
			return AddItemTableExpand(zTable, zCampo, jwins, 2, -1,-1,null,editable,null,false,JWinGridExpand.GRID1ROW);
		}
		public JFormTableExpandResponsive AddItemTableExpand(String zTable, String zCampo, Class jwins, boolean editable, String vision) throws Exception {
			return AddItemTableExpand(zTable, zCampo, jwins, 2, -1,-1,null,editable,vision,false,JWinGridExpand.GRID1ROW);
		}
		public JFormTableExpandResponsive AddItemTableExpand(String zTable, String zCampo, Class jwins,JRecords valorDefault) throws Exception {
			return AddItemTableExpand(zTable, zCampo, jwins, 2, -1,-1,valorDefault,true,null,false,JWinGridExpand.GRID1ROW);
		}
		public JFormTableExpandResponsive AddItemTableExpand(String zTable, String zCampo, Class jwins,JRecords valorDefault, boolean editable) throws Exception {
			return AddItemTableExpand(zTable, zCampo, jwins, 2, -1,-1,valorDefault,editable,null,false,JWinGridExpand.GRID1ROW);
		}
		public JFormTableExpandResponsive AddItemTableExpand(String zTable, String zCampo, Class jwins, int updateaction) throws Exception {
			return AddItemTableExpand(zTable, zCampo, jwins, updateaction, -1);
		}
		public JFormTableExpandResponsive AddItemTableExpand(String zTable, String zCampo, Class jwins, int updateaction,boolean resizable) throws Exception {
			return AddItemTableExpand(zTable, zCampo, jwins, updateaction, -1,-1,null,true,null, resizable,JWinGridExpand.GRID1ROW);
		}
		public JFormTableExpandResponsive AddItemTableExpand(String zTable, String zCampo, Class jwins, int updateaction,JRecords valorDefault) throws Exception {
			return AddItemTableExpand(zTable, zCampo, jwins, updateaction, -1,-1,null,true,null,false,JWinGridExpand.GRID1ROW);
		}
		public JFormTableExpandResponsive AddItemTableExpand(String zTable, String zCampo, Class jwins, int updateaction, int newaction) throws Exception {
			return AddItemTableExpand(zTable, zCampo, jwins, updateaction, newaction,null,true,null,false,JWinGridExpand.GRID1ROW);
		}
		public JFormTableExpandResponsive AddItemTableExpand(String zTable, String zCampo, Class jwins, int updateaction, int newaction, boolean editable) throws Exception {
			return AddItemTableExpand(zTable, zCampo, jwins, updateaction, newaction,null,editable,null,true,JWinGridExpand.GRID1ROW);
		}
		public JFormTableExpandResponsive AddItemTableExpand(String zTable, String zCampo, Class jwins, int updateaction, int newaction, boolean editable, String vision) throws Exception {
			return AddItemTableExpand(zTable, zCampo, jwins, updateaction, newaction,null,editable,vision,true,JWinGridExpand.GRID1ROW);
		}		
		
		public JFormTableExpandResponsive AddItemTableExpand(String zTable, String zCampo, Class jwins, int updateaction, int newaction,JRecords valorDefault, boolean editable,String vision,boolean expanded,String model) throws Exception {
			return AddItemTableExpand(zTable, zCampo, jwins, updateaction, newaction,-1,null,editable,vision,true,JWinGridExpand.GRID1ROW);
		}
		public JFormTableExpandResponsive AddItemTableExpand(String zTable, String zCampo, Class jwins, int updateaction, int newaction, int otherNewAction,JRecords valorDefault, boolean editable,String vision,boolean expanded,String model) throws Exception {
			if (zCampo == null)
				return null;
		  JFormTableExpandResponsive oLista = new JFormTableExpandResponsive();
		  oLista.initialize();
		  oLista.setVision(vision);
			
			// generate form control
			oLista.setProp(getBaseWin().getRecord().getProp(zCampo));
			oLista.setFixedProp(getBaseWin().getRecord().getFixedProp(zCampo));
			oLista.setWin(this.getBaseWin());
			oLista.setLabel(zTable);
			oLista.setExpanded(expanded);
			oLista.setNewAction(newaction);
			oLista.setOtherNewAction(otherNewAction);
			oLista.setUpdateAction(updateaction);
			oLista.setjWinClass(jwins);
			oLista.setCampo(zCampo);
			oLista.setModel(model);
			oLista.setIdControl(zCampo);
			oLista.SetReadOnly(!editable);
			oLista.SetDisplayName(zTable==null?getBaseWin().getRecord().getFixedProp(zCampo).GetDescripcion():zTable);
			oLista.setKeepHeight(getForm().isFullSize());
			oLista.setKeepWidth(getForm().isFullSize());
			oLista.setBaseForm(getForm());
			 BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oLista);
			getControles().AddControl(oLista,findByField(zCampo));
			if (valorDefault!=null) oLista.SetValorDefault(valorDefault);
			oLista.setResponsive(true);
			oLista.GetLista();
//			getControles().AddControl(oLista);
			// oLista.setAction(zAction);
			
			return oLista;
		}

		public JFormLista AddItemTab(String zId) throws Exception {
			return AddItemTab(zId,null);
		}
		public JFormLista AddItemTab(String zId,String title) throws Exception {
			BizAction oAction = getBaseWin().findActionByUniqueId(zId);
			if (title==null && oAction!=null) title=this.getBaseWin().getDescrAction(oAction);
			return AddItemList(oAction,title);
		}

		public JFormLista AddItemTab(int zId) throws Exception {
			return AddItemTab(zId,null);
		}
		
		// Additem de un JWins a un JTabbedPane a través de una acción
		public JFormLista AddItemTab(int zId,String title) throws Exception {
			BizAction oAction = getBaseWin().findAction(zId);
			if (title==null && oAction!=null) title=this.getBaseWin().getDescrAction(oAction);
			return AddItemList(oAction,title);
		}
		public JFormMatrixResponsive AddItemMatrix(int zId) throws Exception {
			return AddItemMatrix(this.getBaseWin().findAction(zId),null);
		}
		public JFormMatrixResponsive AddItemMatrix(int zId, String titulo) throws Exception {
			return AddItemMatrix(this.getBaseWin().findAction(zId),titulo);
		}
		public JFormMatrixResponsive AddItemMatrix(String zId) throws Exception {
			return AddItemMatrix(this.getBaseWin().findActionByUniqueId(zId));
		}
		
		public JFormMatrixResponsive AddItemMatrix(BizAction action) throws Exception {
			String title=this.getBaseWin().getDescrAction(action);
			return AddItemMatrix( action, title);
		}

		public JFormLista AddItemListJSon(int zId) throws Exception {
			BizAction oAction = getBaseWin().findAction(zId);
			String tit = oAction!=null? this.getBaseWin().getDescrAction(oAction): null;
			return AddItemListJSon(oAction, tit);
		}
		public JFormLista AddItemListJSon(String zId) throws Exception {
			BizAction oAction = getBaseWin().findActionByUniqueId(zId);
			String tit = oAction!=null? this.getBaseWin().getDescrAction(oAction): null;
			return AddItemListJSon(oAction, tit);
		}
		
		
		public JFormLista AddItemListJSon(BizAction zAction,String title) throws Exception {
			if (zAction == null)
				return null;
			// JBaseWin oBWin=zAction.getResult();

			zAction.setModal(getForm().isModal());

			JWinList oWinLista = new JWinList(null, getForm().createControlWin(zAction));
			oWinLista.setEmbedded(true);
			oWinLista.setToolbar(JWins.TOOLBAR_TOP);
			oWinLista.generate(getForm());

			if (getForm().getOwnerAction()!=null)
				zAction.setRow(getForm().getOwnerAction().getActionSource().getRow());

			// generate form control
			JFormLista oLista;
			
			
		  oLista = new JFormListJSonResponsive();
		  oLista.initialize();
 		//  oLista = new JFormLista();
			oLista.setWin(this.getBaseWin());
			oLista.setId(getForm().buildTabName(zAction));
			oLista.setTitle(title);
			oLista.SetWinLista(oWinLista);
			oLista.setAction(zAction);
			oLista.setIdControl("tabjson_"+getForm().buildTabName(zAction));
			oLista.SetDisplayName(title);
			oLista.setKeepHeight(getForm().isFullSize());
			oLista.setKeepWidth(getForm().isFullSize());
			// oLista.setAction(zAction);
			getControles().AddControl(oLista);
			
			if (this instanceof JFormTabPanelResponsive) {
				oLista.setTabPane((JFormTabPanelResponsive)this);
				oLista.setIdControl(this.getForm().buildTabName(zAction));
				((JFormTabPanelResponsive)this).addTab(oLista.getIdControl(),oLista);
			} 
			return oLista;
		}
		
		
		public JFormListExpandResponsive AddItemListExpand(int zId) throws Exception {
			BizAction oAction = getBaseWin().findAction(zId);
			String tit = oAction != null ? this.getBaseWin().getDescrAction(oAction) : null;
			return AddItemListExpand(oAction, tit);
		}
		
		public JFormForm AddItemForm(String zId) throws Exception {
			BizAction oAction = getBaseWin().findActionByUniqueId(zId);
			String tit = oAction!=null? this.getBaseWin().getDescrAction(oAction): null;
			return AddItemForm(oAction, tit);
		}

		public JFormForm AddItemForm(int zId) throws Exception {
			BizAction oAction = getBaseWin().findAction(zId);
			String tit = oAction!=null? this.getBaseWin().getDescrAction(oAction): null;
			return AddItemForm(oAction, tit);
		}

		public JFormForm AddItemForm( BizAction zAction, String title) throws Exception {
			if (zAction==null) return null;
			if (zAction.getSubmit()==null) return null;
			zAction.setModal(getForm().isModal());

			JBaseForm form = zAction.getSubmit().getFormEmbedded();
			if (form == null)
				return null;
			form.setEmbedded(true);
			form.build();
			if (getForm().getOwnerAction()!=null)
				zAction.setRow(getForm().getOwnerAction().getActionSource().getRow());

			JFormForm oForm = new JFormForm();
			oForm.initialize();
			oForm.setForm(form);
			oForm.setAction(zAction);
			oForm.setTitle(title);
			oForm.setWin(getBaseWin());
			oForm.setIdControl("tf_"+getForm().buildTabName(zAction));
			oForm.SetDisplayName(zAction.GetDescr());
			 BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oForm);
		getControles().AddControl(oForm);
					
			if (this instanceof JFormTabPanelResponsive) {
				oForm.setTabPane((JFormTabPanelResponsive)this);
				oForm.setIdControl(this.getForm().buildTabName(zAction));
				((JFormTabPanelResponsive)this).addTab(oForm.getIdControl(),oForm);
			} 
			return oForm;
		}		
		
//		public JFormLocalForm AddItemLocalForm(String title) throws Exception {
//			JFormLocalForm oForm = new JFormLocalForm();
//			oForm.initialize();
//			oForm.setForm(getForm());
//			oForm.setTitle(title);
//			oForm.setIdControl("tf_"+getForm().buildTabName(title));
//			oForm.SetDisplayName(title);
//			 BizUsuario.getUsr().getSkin().createGenerator().configureJFormItem(oForm);
//			getControles().AddControl(oForm);
//					
//			if (this instanceof JFormTabPanelResponsive) {
//				oForm.setTabPane((JFormTabPanelResponsive)this);
//				oForm.setIdControl(this.getForm().buildTabName(title));
//				((JFormTabPanelResponsive)this).addTab(oForm.getIdControl(),oForm);
//			} 
//			return oForm;
//		}

		public JFormTableBigDataResponsive AddItemTableBigData(int zId) throws Exception {
			BizAction oAction = getBaseWin().findAction(zId);
			String tit = oAction!=null? this.getBaseWin().getDescrAction(oAction): null;
			return AddItemTableBigData(oAction, tit);
		}
		public JFormTableBigDataResponsive AddItemTableBigData(String zId) throws Exception {
			BizAction oAction = getBaseWin().findActionByUniqueId(zId);
			String tit = oAction!=null? this.getBaseWin().getDescrAction(oAction): null;
			return AddItemTableBigData(oAction, tit);
		}
	
		public JFormTableBigDataResponsive AddItemTableBigData(BizAction zAction,String title) throws Exception {
			if (zAction == null)
				return null;
			// JBaseWin oBWin=zAction.getResult();

			zAction.setModal(getForm().isModal());

			JWinBigData oWinLista = new JWinBigData(null, getForm().createControlWin(zAction));
			oWinLista.setEmbedded(true);
			oWinLista.setToolbar(JWins.TOOLBAR_TOP);
			oWinLista.generate(getForm());

			if (getForm().getOwnerAction()!=null)
				zAction.setRow(getForm().getOwnerAction().getActionSource().getRow());

			// generate form control
			JFormTableBigDataResponsive oLista;
			
			
		  oLista = new JFormTableBigDataResponsive();
		  oLista.initialize();
 		//  oLista = new JFormLista();
			oLista.setWin(this.getBaseWin());
			oLista.setId(getForm().buildTabName(zAction));
			oLista.setTitle(title);
			oLista.SetWinLista(oWinLista);
			oLista.setAction(zAction);
			oLista.setIdControl("tabjson_"+getForm().buildTabName(zAction));
			oLista.SetDisplayName(title);
			oLista.setKeepHeight(getForm().isFullSize());
			oLista.setKeepWidth(getForm().isFullSize());
			// oLista.setAction(zAction);
			getControles().AddControl(oLista);
			
			if (this instanceof JFormTabPanelResponsive) {
				oLista.setTabPane((JFormTabPanelResponsive)this);
				oLista.setIdControl(this.getForm().buildTabName(zAction));
				((JFormTabPanelResponsive)this).addTab(oLista.getIdControl(),oLista);
			} 
			return oLista;
		}

		public JFormTabPanelResponsive AddItemTabPanel( ) throws Exception {
			JFormTabPanelResponsive oPanel = new JFormTabPanelResponsive();
			oPanel.setForm(getForm());
			oPanel.setWin(getBaseWin());
			oPanel.setFormatFields(getForm().getFormatFields());
			getControles().AddControl(oPanel);
			return oPanel;
		}		
		public JFormLista AddItemList(int zId) throws Exception {
			BizAction oAction = getBaseWin().findAction(zId);
			String tit = oAction!=null? this.getBaseWin().getDescrAction(oAction): null;
			return AddItemList(oAction, tit);
		}
		public JFormLista AddItemList(String zId) throws Exception {
			BizAction oAction = getBaseWin().findActionByUniqueId(zId);
			String tit = oAction!=null? this.getBaseWin().getDescrAction(oAction): null;
			return AddItemList(oAction, tit);
		}
		public JFormLista AddItemList(int zId,Serializable Data) throws Exception {
			BizAction oAction = getBaseWin().findAction(zId);
			String tit = oAction!=null? this.getBaseWin().getDescrAction(oAction): null;
			return AddItemList(oAction, tit);
		}
	
		public JFormLista AddItemList(String zTitle, String zId) throws Exception {
			BizAction oAction = getBaseWin().findActionByUniqueId(zId);
			String tit = zTitle!=null? zTitle : oAction!=null? this.getBaseWin().getDescrAction(oAction): null;
			return AddItemList(oAction, tit);
		}
		public JFormLista AddItemList(String title, String zCampo, Class jwins) throws Exception {
			return AddItemList(title, zCampo, jwins, -1, -1, null);
		}		
		public JFormLista AddItemList(String title, String zCampo, Class jwins, int updateaction, int newaction) throws Exception {
			return AddItemList(title, zCampo, jwins, updateaction, newaction, null);
		}		
		public JFormLista AddItemList(String title, String zCampo, Class jwins, int updateaction, int newaction,JRecords valorDefault) throws Exception {
			if (zCampo == null)
				return null;
			BizAction zAction = getForm().getOwnerAction().getActionSource();
	//		zAction.setObjOwner(getForm().getBaseWin());
			String tit = title==null && zAction != null ? this.getBaseWin().getDescrAction(zAction) : title;
			zAction.setModal(getForm().isModal());

			
			JWinList oWinLista = new JWinList(null, getForm().createControlWin(zCampo,jwins));
			oWinLista.setEmbedded(true);
			oWinLista.setToolbar(JWins.TOOLBAR_TOP);
			oWinLista.generate(getForm());
		
			// generate form control
			JFormLista oLista = new JFormLista();
		  oLista.initialize();
			oLista.setWin(this.getBaseWin());
			oLista.setId(getForm().buildTabName(zAction));
			oLista.setTitle(tit);
			oLista.SetWinLista(oWinLista);
			oLista.setAction(zAction);
			oLista.setCampo(zCampo);
			oLista.setWinCampo(getForm().getBaseWin());
			oLista.setWinClassCampo(jwins);
			oLista.setIdControl("tab_"+getForm().buildTabName(zAction));
			oLista.SetDisplayName(tit);
			oLista.setKeepHeight(getForm().isFullSize());
			oLista.setKeepWidth(getForm().isFullSize());
			// oLista.setAction(zAction);
				getControles().AddControl(oLista,findByField(zCampo));
			if (this instanceof JFormTabPanelResponsive) {
				oLista.setTabPane((JFormTabPanelResponsive)this);
				oLista.setIdControl(this.getForm().buildTabName(zAction));
				((JFormTabPanelResponsive)this).addTab(oLista.getIdControl(),oLista);
			} 
			return oLista;

		}
		public JFormSwapListResponsive AddItemSwap(String title, String zCampo,String zCampoSource,Class classWins,Class classWinsSource, String fieldKeyOptions, String fieldKeySource ) throws Exception {
			if (zCampo == null)
				return null;
			BizAction zAction = getForm().getOwnerAction().getActionSource();
	//		zAction.setObjOwner(getForm().getBaseWin());
			String tit = title==null && zAction != null ? this.getBaseWin().getDescrAction(zAction) : title;
			zAction.setModal(getForm().isModal());

				// generate form control
			JFormSwapListResponsive oSwap = new JFormSwapListResponsive();
			
			oSwap.initialize();
			oSwap.setWinCampo(getBaseWin());
			oSwap.setCampo(zCampo);
			
//			JWins wins = (JWins)classWins.newInstance();
//			wins.setRecords((JRecords)getBaseWin().getRecord().getPropDeep(zCampo).getObjectValue());
			
			JWins winsSource = (JWins)classWinsSource.newInstance();
			winsSource.setRecords((JRecords)getBaseWin().getRecord().getPropDeep(zCampoSource).getObjectValue());
			oSwap.setWinsSource(winsSource);
			oSwap.setWinClassCampo(classWins);
			oSwap.setFieldKeyOptions(fieldKeyOptions);
			oSwap.setFieldKeySource(fieldKeySource);
//			oSwap.setAction(getBaseWin(), this.getb);
			getControles().AddControl(oSwap,findByField(zCampo));
	
			return oSwap;

		}
		public JFormTabResponsive getItemList(String id) throws Exception {
			if (this instanceof JFormTabPanelResponsive) {
				
				String newid = "p_"+JWebActionFactory.getCurrentRequest().getNameDictionary(id);
				if (JWebActionFactory.getCurrentRequest().getLevel()>1) newid+="__l"+JWebActionFactory.getCurrentRequest().getLevel();
				return ((JFormTabPanelResponsive)this).getTabs().getElement(newid);
			} 
			return null;

		
		}
		public JFormLista AddItemList(BizAction zAction,String title) throws Exception {
			if (zAction == null)
				return null;
			// JBaseWin oBWin=zAction.getResult();
			
			zAction.setModal(getForm().isModal());
					
			JWinList oWinLista = new JWinList(null, getForm().createControlWin(zAction));
			oWinLista.setEmbedded(true);
			oWinLista.setToolbar(JWins.TOOLBAR_IN);
			oWinLista.generate(getForm());
			if (getForm().getOwnerAction()!=null)
				zAction.setRow(getForm().getOwnerAction().getActionSource().getRow());

			// generate form control
			JFormLista oLista = new JFormLista();
		  oLista.initialize();
			oLista.setWin(this.getBaseWin());
			oLista.setId(getForm().buildTabName(zAction));
			oLista.setTitle(title);
			oLista.SetWinLista(oWinLista);
			oLista.setAction(zAction);
			if (title!=null) 
				oLista.setLabel(title);
			oLista.setIdControl("tab_"+getForm().buildTabName(zAction));
			oLista.SetDisplayName(title);
			oLista.setKeepHeight(getForm().isFullSize());
			oLista.setKeepWidth(getForm().isFullSize());
			// oLista.setAction(zAction);
			getControles().AddControl(oLista);
			if (this instanceof JFormTabPanelResponsive) {
				oLista.setTabPane((JFormTabPanelResponsive)this);
				oLista.setIdControl(this.getForm().buildTabName(zAction));
				((JFormTabPanelResponsive)this).addTab(oLista.getIdControl(),oLista);
			} 
			return oLista;
		}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
		public JFormLista AddItemListOnDemand(int zId) throws Exception {
			BizAction oAction = getBaseWin().findAction(zId);
			String tit = oAction!=null? this.getBaseWin().getDescrAction(oAction): null;
			return AddItemListOnDemand(oAction, tit);
		}
		public JFormLista AddItemListOnDemand(String zId) throws Exception {
			BizAction oAction = getBaseWin().findActionByUniqueId(zId);
			String tit = oAction!=null? this.getBaseWin().getDescrAction(oAction): null;
			return AddItemListOnDemand(oAction, tit);
		}
		public JFormLista AddItemListOnDemand(String zTitle, int zId) throws Exception {
			BizAction oAction = getBaseWin().findAction(zId);
			String tit = zTitle!=null? zTitle : oAction!=null? this.getBaseWin().getDescrAction(oAction): null;
			return AddItemListOnDemand(oAction, tit);
		}
		public JFormLista AddItemListOnDemand(String zTitle, String zId) throws Exception {
			BizAction oAction = getBaseWin().findActionByUniqueId(zId);
			String tit = zTitle!=null? zTitle : oAction!=null? this.getBaseWin().getDescrAction(oAction): null;
			return AddItemListOnDemand(oAction, tit);
		}
		public JFormLista AddItemListOnDemand(String title, String zCampo, Class jwins) throws Exception {
			return AddItemListOnDemand(title, zCampo, jwins, -1, -1, null);
		}		
		public JFormLista AddItemListOnDemand(String title, String zCampo, Class jwins, int updateaction, int newaction) throws Exception {
			return AddItemListOnDemand(title, zCampo, jwins, updateaction, newaction, null);
		}		
		public JFormLista AddItemListOnDemand(String title, String zCampo, Class jwins, int updateaction, int newaction,JRecords valorDefault) throws Exception {
			if (zCampo == null)
				return null;
			BizAction zAction = getForm().getOwnerAction().getActionSource();
			String tit = title==null && zAction != null ? this.getBaseWin().getDescrAction(zAction) : title;
			zAction.setModal(getForm().isModal());

			
			JWinList oWinLista = new JWinList(null, getForm().createControlWin(zCampo,jwins));
			oWinLista.setEmbedded(true);
			oWinLista.setToolbar(JWins.TOOLBAR_TOP);
			oWinLista.generate(getForm());
		
			// generate form control
			JFormLista oLista = new JFormLista();
		  oLista.initialize();
			oLista.setOnDemand(true);
			oLista.setWin(this.getBaseWin());
			oLista.setId(getForm().buildTabName(zAction));
			oLista.setTitle(tit);
			oLista.SetWinLista(oWinLista);
			oLista.setAction(zAction);
			oLista.setCampo(zCampo);
			oLista.setWinCampo(getForm().getBaseWin());
			oLista.setWinClassCampo(jwins);
			oLista.setIdControl("tab_"+getForm().buildTabName(zAction));
			oLista.SetDisplayName(tit);
			oLista.setKeepHeight(getForm().isFullSize());
			oLista.setKeepWidth(getForm().isFullSize());
			// oLista.setAction(zAction);
			getControles().AddControl(oLista,findByField(zCampo));
			if (this instanceof JFormTabPanelResponsive) {
				oLista.setTabPane((JFormTabPanelResponsive)this);
				oLista.setIdControl(this.getForm().buildTabName(zAction));
				((JFormTabPanelResponsive)this).addTab(oLista.getIdControl(),oLista);
			} 
			return oLista;

		}
	
		public JFormLista AddItemListOnDemand(BizAction zAction,String title) throws Exception {
			if (zAction == null)
				return null;
			// JBaseWin oBWin=zAction.getResult();
			
			
			JWinList oWinLista = new JWinList(null, getForm().createControlWin(zAction));
			oWinLista.setEmbedded(true);
			oWinLista.setToolbar(JWins.TOOLBAR_IN);
			oWinLista.generate(getForm());
			
			if (getForm().getOwnerAction()!=null)
				zAction.setRow(getForm().getOwnerAction().getActionSource().getRow());

			// generate form control
			JFormLista oLista = new JFormLista();
		  oLista.initialize();
			oLista.setOnDemand(true);
			oLista.setWin(this.getBaseWin());
			oLista.setId(getForm().buildTabName(zAction));
			oLista.setTitle(title);
			oLista.SetWinLista(oWinLista);
			oLista.setAction(zAction);
			if (title!=null) 
				oLista.setLabel(title);
			oLista.setIdControl("tab_"+getForm().buildTabName(zAction));
			oLista.SetDisplayName(title);
			oLista.setKeepHeight(getForm().isFullSize());
			oLista.setKeepWidth(getForm().isFullSize());
			// oLista.setAction(zAction);
			getControles().AddControl(oLista);
			if (this instanceof JFormTabPanelResponsive) {
				oLista.setTabPane((JFormTabPanelResponsive)this);
				oLista.setIdControl(this.getForm().buildTabName(zAction));
				((JFormTabPanelResponsive)this).addTab(oLista.getIdControl(),oLista);
			} 
			return oLista;
		}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
		public JFormTreeResponsive AddItemTree(String label,int zId) throws Exception {
			BizAction oAction = getBaseWin().findAction(zId);
			String tit = oAction!=null? this.getBaseWin().getDescrAction(oAction): null;
			return AddItemTree(label,oAction, tit, 0);
		}
		public JFormTreeResponsive AddItemTree(String label,int zId, int treeColumn) throws Exception {
			BizAction oAction = getBaseWin().findAction(zId);
			String tit = oAction!=null? this.getBaseWin().getDescrAction(oAction): null;
			return AddItemTree(label,oAction, tit, treeColumn);
		}
		public JFormTreeResponsive AddItemTree(String label,String zId) throws Exception {
			BizAction oAction = getBaseWin().findActionByUniqueId(zId);
			String tit = oAction!=null? this.getBaseWin().getDescrAction(oAction): null;
			return AddItemTree(label,oAction, tit, 0);
		}

		public JFormTreeResponsive AddItemTree(String label,BizAction zAction, String title,int treeColumn) throws Exception {
			if (zAction == null)
				return null;
			// JBaseWin oBWin=zAction.getResult();
			zAction.setModal(getForm().isModal());

			JWinList oWinLista = new JWinList(null, getForm().createControlTree(zAction));
			oWinLista.setEmbedded(true);
			oWinLista.setToolbar(JWins.TOOLBAR_TOP);
			oWinLista.generate(getForm());
			
			if (getForm().getOwnerAction()!=null)
				zAction.setRow(getForm().getOwnerAction().getActionSource().getRow());
	
			// generate form control
			JFormTreeResponsive oLista = new JFormTreeResponsive();
		  oLista.initialize();
			oLista.setWin(this.getBaseWin());
			oLista.setId(getForm().buildTabName(zAction));
			oLista.setTitle(title);
			oLista.SetWinLista(oWinLista);
			oLista.setAction(zAction);
			oLista.setIdControl("tab_"+getForm().buildTabName(zAction));
			oLista.SetDisplayName(title);
			oLista.setLabel(label);
			oLista.setTreeColumn(treeColumn);
			oLista.setKeepHeight(getForm().isFullSize());
			oLista.setKeepWidth(getForm().isFullSize());
			// oLista.setAction(zAction);
			getControles().AddControl(oLista);
			if (this instanceof JFormTabPanelResponsive) {
				oLista.setTabPane((JFormTabPanelResponsive)this);
				oLista.setIdControl(this.getForm().buildTabName(zAction));
				((JFormTabPanelResponsive)this).addTab(oLista.getIdControl(),oLista);
			} 
			return oLista;
		}
		public JFormTreeComponentResponsive AddItemTree(String label, String zTipoDato, String zRequer, String sField) throws Exception {
		   return AddItemTree(label, zTipoDato, zRequer, sField, getBaseWin().GetNroIcono(), getBaseWin().getRecord().getFixedProp(sField).getControl(getBaseWin().getRecord()));
		}
		public JFormTreeComponentResponsive AddItemTree(String label, String zTipoDato, String zRequer, String sField,int icono) throws Exception {
		   return AddItemTree(label, zTipoDato, zRequer, sField, icono, getBaseWin().getRecord().getFixedProp(sField).getControl(getBaseWin().getRecord()));
		}
		public JFormTreeComponentResponsive AddItemTree(String label, String zTipoDato, String zRequer, String sField,int icono,IControl sCampo) throws Exception {
			JFormTreeComponentResponsive oEdit;

			oEdit = new JFormTreeComponentResponsive();
			oEdit.initialize();
			oEdit.setLabel(label);
			oEdit.SetControlWin(sCampo);
			oEdit.setProp(getBaseWin().getRecord().getPropDeep(sField));
			oEdit.setFixedProp(getBaseWin().getRecord().getFixedPropDeep(sField));
			oEdit.SetRequerido(zRequer);
			oEdit.SetTipoDato(zTipoDato);

			GuiIcon oIcon=GuiIconos.GetGlobal().buscarIcono(GuiIconos.SIZE_DEFA,icono);
			if (oIcon!=null) oEdit.setIcon(oIcon.GetFile());

			getControles().AddControl(oEdit);
			return oEdit;
		}

		
		public JFormListExpandResponsive AddItemListExpand(BizAction zAction,String title) throws Exception {
			if (zAction == null)
				return null;
			// JBaseWin oBWin=zAction.getResult();
			zAction.setModal(getForm().isModal());

			JWinList oWinLista = new JWinList(null, getForm().createControlWin(zAction));
			oWinLista.setEmbedded(true);
			oWinLista.setToolbar(JWins.TOOLBAR_TOP);
			oWinLista.generate(getForm());
			if (getForm().getOwnerAction()!=null)
				zAction.setRow(getForm().getOwnerAction().getActionSource().getRow());

			// generate form control
			JFormListExpandResponsive oLista = new JFormListExpandResponsive();
			oLista.initialize();
			oLista.setWin(this.getBaseWin());
			oLista.setId(getForm().buildTabName(zAction));
			oLista.setTitle(title);
			oLista.SetWinLista(oWinLista);
			oLista.setAction(zAction);
			oLista.setIdControl("tabexp_"+getForm().buildTabName(zAction));
			oLista.SetDisplayName(title);
			oLista.setKeepHeight(getForm().isFullSize());
			oLista.setKeepWidth(getForm().isFullSize());
			getControles().AddControl(oLista);
			
			return oLista;
		}	
		public JFormMatrixResponsive AddItemMatrix(BizAction zAction,String title) throws Exception {
			if (zAction == null)
				return null;
			// JBaseWin oBWin=zAction.getResult();
			zAction.setModal(getForm().isModal());

			JWinMatrix oWinLista = new JWinMatrix(null, getForm().createControlWin(zAction));
			oWinLista.setEmbedded(true);
			oWinLista.generate(getForm());

			if (getForm().getOwnerAction()!=null)
				zAction.setRow(getForm().getOwnerAction().getActionSource().getRow());

			// generate form control
			JFormMatrixResponsive oLista = new JFormMatrixResponsive();
			oLista.initialize();
			oLista.setWin(this.getBaseWin());
			oLista.setId(getForm().buildTabName(zAction));
			oLista.setTitle(title);
			oLista.SetWinLista(oWinLista);
			oLista.setAction(zAction);
			oLista.setIdControl("tabmtx_"+getForm().buildTabName(zAction));
			oLista.SetDisplayName(title);
			oLista.setKeepHeight(getForm().isFullSize());
			oLista.setKeepWidth(getForm().isFullSize());
			getControles().AddControl(oLista);
			if (this instanceof JFormTabPanelResponsive) {
				oLista.setTabPane((JFormTabPanelResponsive)this);
				oLista.setIdControl(this.getForm().buildTabName(zAction));
				((JFormTabPanelResponsive)this).addTab(oLista.getIdControl(),oLista);
			} 
			return oLista;
		}

		public JFormPanelResponsive setLabelLeft() throws Exception {
			this.setFormatFields(JFormPanelResponsive.FIELD_LABEL_HORIZONTAL_LEFT);
			return this;
		}
		public JFormPanelResponsive setLabelRight() throws Exception {
			this.setFormatFields(JFormPanelResponsive.FIELD_LABEL_HORIZONTAL_RIGHT);
			return this;
		}
		public JFormPanelResponsive setLabelLeft(int size) throws Exception {
			this.setLabelLeft();
			this.setSizeLabels(size);
			return this;
		}
		
	public void AddSubForm(JWin win, JBaseForm subform) throws Exception {
		subform.build();
		this.setForm(subform);
		this.setWin(win);
		JFormPanelResponsive intPanel = subform.getInternalPanel();
		intPanel.setId(intPanel.getId()+"-"+subform.getBaseWin().getUniqueId());
		this.getControles().AddControl(intPanel);
	}
	
	public JFormDivResponsive AddTextPanel( ) throws Exception {
		JFormDivResponsive panel = this.AddDivPanel();
		panel.setResponsiveClass("text-group");
		return panel;
	}		
}
