package pss.core.winUI.controls;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.io.Serializable;
import java.util.Date;

import javax.swing.JLabel;

//import javax.swing.JComponent;
//import javax.swing.JLabel;
//import javax.swing.border.Border;

import pss.common.regions.multilanguage.JLanguage;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JIntervalDate;
import pss.core.services.fields.JIntervalDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JMultiple;
import pss.core.services.fields.JObjBD;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JObjectFactory;
import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormCardResponsive;
import pss.core.winUI.responsiveControls.JFormControlResponsive;

public class JFormControl {

	public final static String MODO_CONSULTA="C";
	public final static String MODO_ALTA="A";
	public final static String MODO_MODIFICACION="M";
	public final static String MODO_ELIMINAR="E";
	// deprecado, usar HALIGN u VALIGN
//	public final static String ALIGN_CONTENT_LEFT="left";
//	public final static String ALIGN_CONTENT_RIGHT="right";
	public final static int NEXT_BREAK=1;
	public final static int NEXT_LINE=2;

  public static final int ALIGN_DEFAULT = 0;
  
  public static final int HALIGN_LEFT = 1;
  public static final int HALIGN_CENTER = 2;
  public static final int HALIGN_RIGHT = 3;

  public static final int VALIGN_TOP = 4;
  public static final int VALIGN_MIDDLE = 5;
  public static final int VALIGN_BOTTOM = 6;

  public static final int FONT_WEIGHT_NORMAL = 1;
  public static final int FONT_WEIGHT_BOLD = 2;

  public static final int FONT_STYLE_NORMAL = 1;
  public static final int FONT_STYLE_ITALIC = 2;
  
  public static final String OVER_HIDDEN = "hidden";
  public static final String OVER_SCROLL = "scroll";
  public static final String OVER_AUTO = "auto";
  
  public static final String BACK_REPEAT_ROUND = "round";
  public static final String BACK_REPEAT_REPEAT = "repeat";
  public static final String BACK_REPEAT_NOREPEAT = "no-repeat";
  
  public static final String BACK_SIZE_CONTAIN = "contain";
  public static final String BACK_SIZE_COVER= "cover";

  public static final String DISPLAY_BLOCK = "block";
  public static final String DISPLAY_INLINE_BLOCK = "inline-block";
  public static final String DISPLAY_FLEX = "flex";
  public static final String DISPLAY_NONE = "none";
  public static final String DISPLAY_FLOW_ROOT= "flow-root";
  
  protected JObject oProp=null;
	protected String oFieldProp=null;


	protected JProperty oFixedProp=null;
	protected String sDisplayName;
	protected boolean bPreAsignado;
	protected boolean bPermitirTodo=false;
	protected boolean bPermiteVacio=true;

	protected JObject propDefault=null;
	protected String sRequerido;
//	protected String sAlignContent=null;

	protected boolean bReadOnly=false;
	protected boolean bVisible=true;
	protected boolean bHide=false;
	protected String labelClass;

	protected String fieldClass;
	protected String connectControl;
	protected String connectControlField;
	protected String connectControlOperator;
	protected String connectControlDatatype;


	protected String sSkinStereotype=null;
	protected String tableName=null;
	protected long inTableRow=-1;
	protected long inTableCol=-1;
	protected boolean responsive=false;
	protected boolean bUnsigned=false;
	
	protected boolean bGenerateInternalEvents=true;
	

	protected boolean bRemoveIsDuplicate=false;
	
	protected Serializable extraFilter = null;
	


	//	private boolean bEditable=false;
	boolean keepWidth;
	boolean keepHeight;
	
	protected boolean bDisplayOnly=false;
	protected boolean bPersistente=false;
	protected String sTipoDato;
	protected String sDependVirtual=null;
	protected boolean bCampoQuery=false;
	protected String sIdControl=null;
	protected String id=null;
	protected int nextControl=0;
	protected String sHelp;
	boolean filterNeverHide;
	String multiField;
	 


	public Serializable getExtraFilter() {
		return extraFilter;
	}

	public void setExtraFilter(Serializable extraFilter) {
		this.extraFilter = extraFilter;
	}

  public String getMultiField() throws Exception {
		return multiField;
	}

	public boolean hasMultiField()  throws Exception  {
		return getMultiField()!=null;
	}

	public boolean isAcceptURL() {
		return false;
	}
	public JFormControl setMultiField(String mf) {
		this.multiField = mf;
		return this;
	} 

  public boolean isFilterNeverHide() {
		return filterNeverHide;
	}

	public JFormControl setFilterNeverHide(boolean filterNeverHide) {
		this.filterNeverHide = filterNeverHide;
		return this;
	}
//	public String getAlignContent() {
//		return sAlignContent;
//	}

	public boolean isRemoveIsDuplicate() {
		return bRemoveIsDuplicate;
	}

	public void setRemoveIsDuplicate(boolean bRemoveIsDuplicate) {
		this.bRemoveIsDuplicate = bRemoveIsDuplicate;
	}
	
//	public String resolveAlignContent() {
//		if (sAlignContent!=null)
//			return sAlignContent;
//		if (this.isFloat()) return ALIGN_CONTENT_RIGHT;
//		return ALIGN_CONTENT_LEFT;
//	}
//	public JFormControl setAlignContent(String sAlignContent) {
//		this.sAlignContent = sAlignContent;
//		return this;
//	}
	public String getDescriptionValue(JWin win) throws Exception {
		return win.getDescripFieldValue();
	}
	public boolean isHtmlDescriptionValue(JWin win) throws Exception {
		return win.isHtmlDescriptionValue();
	}
	
	public String getKeyValue(JWin win) throws Exception {
		return win.GetValorItemClave();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public JFormControl setId(String id) {
		this.sIdControl = id;
		return this;
	}

	protected JFormControles oControles;
//	private int preferredWidth;
//	private int preferredHeight;
//	private JList<Component> componentList;
	private String sForcedValue=null;
//	private JList<String> oDependencies=null;
	private JPssLabel linkedLabel=null;
	private boolean bForcedDefault=false;
	private boolean bPopupIcon=false;
	private String oBackground;
	private String oForeground;
	private String sToolTip;
	private String placeHolder;
	private String title;
	private String titleRight;


	public void initialize() throws Exception {
		
	}
	public void onAddToForm() throws Exception {
		
	}

	public String getConnectControl() {
		return connectControl;
	}

	public void setConnectControl(String connectControl) {
		this.connectControl = connectControl;
	}
	public String getConnectControlOperator() {
		return connectControlOperator;
	}

	public void setConnectControlOperator(String connectControlOperator) {
		this.connectControlOperator = connectControlOperator;
	}

	public String getConnectControlDatatype() {
		return connectControlDatatype;
	}

	public void setConnectControlDatatype(String connectControlDatatype) {
		this.connectControlDatatype = connectControlDatatype;
	}
	public String getConnectControlField() {
		return connectControlField;
	}

	public void setConnectControlField(String connectControlField) {
		this.connectControlField = connectControlField;
	}

	public String getTitle() throws Exception {
		return title;
	}

	public void setTitle(String title) throws Exception {
		this.title = title;
	}
	public String getTitleRight() throws Exception {
		return titleRight;
	}

	public void setTitleRight(String title) throws Exception {
		this.titleRight = title;
	}
	public boolean isHide() {
		return bHide;
	}

	/**
	 * Para siempre oculto
	 */
	public JFormControl setHide(boolean bHide) {
		this.bHide = bHide;
		return this;
	}

	public String getControlValue(String id, String def) throws Exception {
		JFormControl c = this.getControls().findControl(id);
		if (c==null) return def;
		return c.getValue();
	}

	public void setUnsigned(boolean bUnsigned) {
		this.bUnsigned = bUnsigned;
	}
	public void setLayout() throws Exception {
		
	}
	private String operator=null;

	public String getModo() {
		if (getControls().getBaseForm()==null) return JBaseForm.MODO_ALTA;
		return getControls().getBaseForm().GetModo();
	}


	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String table) {
		this.tableName = table;
	}
	public long getInTableCol() {
		return inTableCol;
	}

	public void setInTableCol(long col) {
		this.inTableCol = col;
	}
	public boolean isInTable() {
		return inTableRow>=0;
	}
	public long getInTableRow() {
		return inTableRow;
	}

	public void setInTableRow(long row) {
		this.inTableRow = row;
	}
	public String getLabelClass() {
		return labelClass;
	}

	public JFormControl setLabelClass(String labelClass) {
		this.labelClass = labelClass;
		return this;
	}

	public String getFieldClass() {
		return fieldClass;
	}

	public JFormControl setFieldClass(String formClass) {
		this.fieldClass = formClass;
		return this;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public boolean isKeepWidth() {
		return keepWidth;
	}

	public void setKeepWidth(boolean keepWidth) {
		this.keepWidth = keepWidth;
	}

	public boolean isKeepHeight() {
		return keepHeight;
	}

	public void setKeepHeight(boolean keepHeight) {
		this.keepHeight = keepHeight;
	}
	
	public String getPlaceHolder() {
		return placeHolder;
	}

	public JFormControl setPlaceHolder(String title) {
		this.placeHolder = title;
		return this;
	}
	// -------------------------------------------------------------------------//
	// Metodos de las Propiedades de la Clase
	// -------------------------------------------------------------------------//
	public JObject getProp() {
		return oProp;
	}

	public JProperty getFixedProp() {
		return oFixedProp;
	}

	public boolean hasFixedProp() {
		return oFixedProp!=null;
	}

	public String getFieldProp() {
		return oFieldProp;
	}

	public void setFieldProp(String oFieldProp) {
		this.oFieldProp = oFieldProp;
	}
	public boolean isResponsive() {
		return responsive;
	}

	public void setResponsive(boolean zresponsive) {
		this.responsive = zresponsive;
	}

	public String GetDisplayName() {
		if (sDisplayName!=null&&!sDisplayName.equals("")) return sDisplayName;
		if (getFixedProp()!=null) return getFixedProp().GetDescripcion();
		return "";
	}
	public String getSkinStereotype() {
		return sSkinStereotype;
	}

	public void setSkinStereotype(String sSkinStereotype) {
		this.sSkinStereotype = sSkinStereotype;
	}
	public String GetRequerido() {
		return sRequerido;
	}

	public String GetTipoDato() {
		return sTipoDato;
	}

	public boolean ifPermitirTodo() {
		return bPermitirTodo;
	}
	public boolean isPermiteVacio() {
		return bPermiteVacio;
	}

	public JFormControl setPermiteVacio(boolean bPermiteVacio) {
		this.bPermiteVacio = bPermiteVacio;
		return this;
	}

	public boolean ifPreasignado() {
		return bPreAsignado;
	}

	public JObject getValorDefault() {
		return propDefault;
	}

	public void setPropDefault(JObject propDefault) {
		this.propDefault = propDefault;
	}

	public String GetDependVirtual() {
		return sDependVirtual;
	}

	public boolean ifReadOnly() {
		return bReadOnly;
	}

	public boolean isDisplayOnly() {
		return bDisplayOnly;
	}

	public boolean isPersistente() {
		return bPersistente;
	}
	public boolean isForcedDefault() {
		return bForcedDefault;
	}
	public boolean isPermitirTodo() {
		return bPermitirTodo;
	}

	public JFormControles getControls() {
		return oControles;
	}

	public void setLinkedLabel(JPssLabel value) {
		this.linkedLabel=value;
	}

	public void setForcedDefault(boolean value) {
		this.bForcedDefault=value;
	}

	public JPssLabel getLinkedLabel() {
		return this.linkedLabel;
	}

//	public int getPreferredWidth() {
//		return this.preferredWidth;
//	}
//
//	public void setPreferredWidth(int zWidth) {
//		this.preferredWidth=zWidth;
//		this.updateComponentWidth();
//	}
//	public int getPreferredHeight() {
//		return this.preferredHeight;
//	}
//
//	public void setPreferredHeight(int zWidth) {
//		this.preferredHeight=zWidth;
//		
//	}

	public boolean isGenerateInternalEvents() {
		return bGenerateInternalEvents;
	}

	public void setGenerateInternalEvents(boolean bGenerateInternalEvents) {
		this.bGenerateInternalEvents = bGenerateInternalEvents;
	}

//	protected void updateComponentSize() {
//		Object object=this.getComponent();
//		if (!(object instanceof Component)) return;
//		int height=0, width=0;
//		Component comp=(Component) object;
//		if (comp.getSize().height<=0) height=this.getDefaultHeight();
//		if (comp.getSize().width<=0) width=this.getDefaultWidth();
//		comp.setSize(width, height);
//	}

//	protected void updateComponentWidth() 
//{
//		Object object=this.getComponent();
//		if (!(object instanceof Component)) return;
//		Component comp=(Component) object;
//		if (comp!=null) {
//			int iWidth=this.getPreferredWidth();
//			if (iWidth<1) {
//				iWidth=this.getDefaultWidth();
//			}
//			if (iWidth<1) return;
//			comp.setSize(iWidth, comp.getHeight());
//			if (comp instanceof JComponent) {
//				JComponent oComp=(JComponent) comp;
//				if (oComp.isPreferredSizeSet()) {
//					oComp.setPreferredSize(new Dimension(iWidth, comp.getPreferredSize().height));
//				} else {
//					oComp.setPreferredSize(new Dimension(iWidth, JFormControl.getDefaultControlHeight()));
//				}
//			}
//		}
//	}

//	public void setBorder(Border b) {
//		Component c=this.getComponent();
//		if (c instanceof Component) ((JComponent) c).setBorder(b);
//	}

//	public JList<Component> getComponents() throws Exception {
//		if (componentList!=null) return componentList;
//		componentList=JCollectionFactory.createList();
//		this.generateComponentList(componentList);
//		return componentList;
//	}

	public void generateComponentList(JList<Component> comps) throws Exception {
		comps.addElement(this.getComponent());
	}

	public Component getComponent() {
		return null;
	}

	public Component getComponentForList() {
		return this.getComponent();
	}

	protected int getDefaultWidth() {
		return 0;
	}

	protected String getResponsiveWidth() {
		return "";
	}
	private Boolean detectChanges=null;

	public boolean isDetectChanges() {
		if (detectChanges==null && getControls()!=null && getControls().getBaseForm()!=null)
			return getControls().getBaseForm().isDetectChanges();
		if(detectChanges==null) return true;
		return detectChanges;
	}

	public void setDetectChanges(boolean detectChanges) {
		this.detectChanges = detectChanges;
	}

	// Indica cuando un cambio en este control altera la estructura del formulario
	private boolean bRefreshForm=false;

	public JFormControl setRefreshForm(boolean b) {
		bRefreshForm=b;
		return this;
	}

	public boolean hasToRefreshForm() {
		return bRefreshForm;
	}

	// public void setAccionRefresh( JSaxable action ) { m_accionRefresh = action; }
	// public JSaxable getAccionRefresh() { return m_accionRefresh; }

	public boolean ifTieneDefault() {
		return propDefault!=null;
	}

	public String getName() {
		if (sIdControl==null) return "component-"+this.hashCode();
		return sIdControl;
	}

	public void setProp(JObject zProp) {
		oProp=zProp;
	}
	public void SetDisplayName(String zDisp) {
		sDisplayName=zDisp;
	}

	// public void SetValorDefault(JObject zVal) throws Exception {
	// if (this.bReadOnly)
	// valorDefault=zVal.toFormattedString();
	// else
	// valorDefault=zVal.toInputString();
	// }
	public JFormControl SetValorDefault(JObject zVal) throws Exception {
		if (zVal==null) return this;
		this.SetValorDefault((Serializable)zVal.asObject());
		return this;
	}
	
	protected JObject getPropDefault() throws Exception {
		if (propDefault==null) 
			propDefault=JObjectFactory.virtualCreate(oProp.getObjectType());
		return this.propDefault;
	}
	public boolean isValueDefault() throws Exception {
		if (!hasDefaultValue()) return false;
		if (!hasValue()) return false;
		return getPropDefault().equals(getProp());
	}
	public void clearDefault() throws Exception {
		propDefault=null;
	}
	public JFormControl SetValorDefault(Serializable zVal) throws Exception {
		if (zVal instanceof String) 
			return this.SetValorDefault((String)zVal);
		if (zVal==null) return this;
		if (oProp==null) return this;
		getPropDefault().setValue(zVal);
		return this;
	}
	public JFormControl SetValorDefault(Object zVal) throws Exception {
		if (zVal==null) return this;
		if (oProp==null) return this;
		getPropDefault().setValue(zVal);
		return this;
	}

	public JFormControl SetValorDefault(double zVal) throws Exception {
		this.SetValorDefault(new Double(zVal));
		return this;
	}

	public JFormControl SetValorDefault(long zVal) throws Exception {
		this.SetValorDefault(new Long(zVal));
		return this;
	}

	public JFormControl SetValorDefault(int zVal) throws Exception {
		this.SetValorDefault(new Integer(zVal));
		return this;
	}

	public JFormControl SetValorDefault(String zVal) throws Exception {
		if (zVal==null || zVal.equals("")) {
			this.clearDefault();
			return this;
	}
		if (oProp==null) return this;
		this.getPropDefault().setValue(zVal);
		return this;
	}

	public JFormControl SetValorDefault(boolean zVal) throws Exception {
		return this.SetValorDefault(new Boolean(zVal));
	}

	public JFormControl SetValorDefault(Date zVal) throws Exception {
		return this.SetValorDefault((Serializable) zVal);
		// sValorDefault=JDate.DateToString(zVal);
	}

	public void setearFiltros(JRecord zBD) throws Exception {
			if (getProp() == null)
				return;
			if (!getFixedProp().isKey())
				return;
			if (!propHasRawValue())
				return;
			zBD.addFilter(getFixedProp().GetCampo(), getProp().toString());
	}

	public JFormCardResponsive findCard(String row, String zName) throws Exception {
		if (this instanceof JFormCardResponsive)
			return (JFormCardResponsive)this;
		return null;
	}

	public JFormControl findControl(String row, String zName) throws Exception {
		if (getIdControl() == null)
			return null;
		if (row != null && row.indexOf("_card_")==-1)
			return null;
		if (getIdControl().equalsIgnoreCase(zName.trim())) {
			return this;
		}
		return null;
	}


	public JFormControl findControlByField(String zName, String operador) throws Exception {
		if (getFixedProp() == null) return null;
		if (!getFixedProp().GetCampo().trim().equalsIgnoreCase(zName.trim())) return null;
		if (operador==null) return this;
		String oper = getOperator()!=null?getOperator():"=";
		if (oper.equals("IN")) oper="="; // en el filtro winlov el operador es IN
		if (!operador.equalsIgnoreCase(oper)) return null;
		return this;
	}
	public void refreshScripts() throws Exception {
			if (getProp() != null && getProp().hasScript())
				BaseToControl("A", true);
	}


	public void recordDataToDefault() throws Exception {
			if (ifPreasignado())
				return;
			this.forcePresetForDefault();
			if (this.propHasRawValue()) {
//				  esto es porque no existe un modo refresh para cuando se hasce refreshForm en un alta, entonces simulamos lo que viene de la web como defaults
//				this.SetValorDefault(getProp().asRawObject());
// HGK hay que copiar todas las propiedades por ejemplo el JCurrency que tiene los decimales				
				this.getPropDefault().copyForm(this.getProp());
				//					PssLogger.logInfo("Dato: "+getProp().asRawObject());
//				if (getProp().isRecord()) {
//					setValue((JRecord)getProp().asRawObject());
//				} else {
//					setValueFormUI(getProp().toRawString());
//				}
			}
	}

	public JFormControl SetTipoDato(String zVal) {
		sTipoDato=zVal;
		return this;
	}

	public void SetRequerido(String zVal) {
		sRequerido=zVal;
	}

	public void SetPermitirTodo(boolean zOk) {
		bPermitirTodo=zOk;
	}

	public void SetPreasignado(boolean zOk) {
		bPreAsignado=zOk;
	}

	public void SetControles(JFormControles zC) {
		oControles=zC;
	}

	public void SetDependVirtual(String zValor) {
		sDependVirtual=zValor;
	}

	public JFormControl SetReadOnly(boolean zValor) {
		bReadOnly=zValor;
		return this;
	}

	public void setDisplayOnly(boolean zValor) {
		bDisplayOnly=zValor;
	}

	public void setPersistente(boolean zValor) {
		bPersistente=zValor;
	}

	public void SetCampoQuery(boolean zValor) {
		bCampoQuery=zValor;
	}

	public void setFixedProp(JProperty zProp) {
		oFixedProp=zProp;
		if (zProp==null) return;
		if (zProp.GetCampo()==null) return;
	//	this.sIdControl =  zProp.GetCampo().trim(); //rjl 
		if (zProp.isTableBased()) bCampoQuery=true;
	}

	public void markMainICon() {
		this.setIdControl("main_icon");
	}
	public void setIdControl(String zId) {
		this.sIdControl=zId;
	}
	public void setIdControlIfNeed() {
		if (this.sIdControl!=null) return; 
		if (oFixedProp==null) return;
		if (oFixedProp.GetCampo()==null) return;
		if (this.sIdControl!=null) return;
		this.sIdControl =  oFixedProp.GetCampo().trim();  
	}

	public String getIdControl() {
		return this.sIdControl;
	}

	// ----------------------------------------c---------------------------------//
	// Constructor
	// -------------------------------------------------------------------------//
	public JFormControl() {
		propDefault=null;
		bPermitirTodo=true;
		bPreAsignado=false;
	}

	//
	// static methods for constants
	//
	public static int getDefaultControlHeight() {
		return 21;
	}

	public int getDefaultHeight() {
		return 21;
	}

	// -------------------------------------------------------------------------//
	// Metodos Virtuales a sobreescribir
	// -------------------------------------------------------------------------//
	public void edit() throws Exception {
		this.edit(JBaseForm.MODO_ALTA);
	}
	public void edit(String zModo) throws Exception {
	}

	public void clear() throws Exception {
	}

	public void disable() throws Exception {
	}
	public void enable() throws Exception {
	}


	public void PutTop(int zTop) throws Exception {
	}

	public void PutLeft(int zLeft) throws Exception {
	}

	public void hide() throws Exception {
	}

	public void show() throws Exception {
	}

//	public Container CrearControl() throws Exception {
//		return null;
//	}

	public int GetTop() {
		return 0;
	}

	public int GetLeft() {
		return 0;
	}

	public int GetWidth() {
		return 0;
	}

	public boolean hasValue() throws Exception {
		return false;
	}
	
	public void setValue(String zVal) throws Exception {
		if(zVal.equals(""))
			clear();
		else if (getProp().isRecord()) {
			JWin win = getWinElegido(zVal);
			if (win!=null)
				getProp().setValue(win.getRecord());
		} else if (getProp().isIntervalDate()) {
			
		} else if (getProp().isIntervalDateTime()) {
			
		} else
			getProp().setValue(zVal);
	}
	public void setValue(JRecords rec) throws Exception {
		if (getProp().isRecords())
			getProp().setValue(rec);
	}
	public void setValue(JRecord rec) throws Exception {
		if (getProp().isRecord())
			getProp().setValue(rec);
		else 
			getProp().setValueFormUI(rec.getPropAsString(rec.getKeyField()));
	}
	public void setValue(JWin win) throws Exception {
		if (getProp().isRecord())
			getProp().setValue(win.getRecord());
		else
			getProp().setValue(win.getRecord().getPropAsString(win.getKeyField()));
	}
	public void setValue(JWins wins) throws Exception {
		if (getProp().isRecords())
			getProp().setValue(wins.getRecords());
		else {
			String keys="";
			JIterator<JWin> it = ((JWins)wins).getStaticIterator();
			while (it.hasMoreElements()) {
				JWin win = it.nextElement();
				keys+=(keys.equals("")?"":",")+win.getRecord().getPropAsString(win.getKeyField());;
			}
			getProp().setValue(keys);
		}

	}
	
	public void setValue(double value) throws Exception {
		this.setValue(new JFloat(value).toInputString());
	}

	public void setValue(long value) throws Exception {
		this.setValue(new JLong(value).toInputString());
	}
	
	public void setValue(Date value) throws Exception {
		this.setValue(new JDate(value).toInputString());
	}
	
	public void setValueFormUI(String zVal) throws Exception {
		this.setValue(zVal);
	}

	public void setValue(JMultiple zVal) throws Exception {
		this.setValue(zVal.asPrintealbleObject());
	}
	public void setValue(JIntervalDateTime zVal) throws Exception {
		this.setValue(zVal.asPrintealbleObject());
	}
	public void setValue(JIntervalDate zVal) throws Exception {
		this.setValue(zVal.asPrintealbleObject());
	}

	public void Remove() throws Exception {
	}
	public JFormControl removeField( JFormControl sCampo) throws Exception {
		return this.equals(sCampo)?this:null;
	}

	public int getAditionalComponentWidth() throws Exception {
		return 0;
	}

	public String getSpecificValue() throws Exception {
		return "";
	}
	
	public final String findValue() throws Exception {
		String v = this.getValue();
		if (v!=null && !v.equals("")) return v;
		v =this.getProp().toRawString();
		if (v!=null && !v.equals("")) return v;
		if (this.hasDefaultValue()) // esto no deberia ir...
			return this.getValorDefault().toRawString();
		return null;
	}

	public final JObject findObjectValue() throws Exception {
		JObject v =this.getProp();
		if (v!=null && v.hasValue()) return v;
//		if (this.hasDefaultValue())
//			return this.getValorDefault();
		return null;
	}

	public final String getValue() throws Exception {
		if (hasForcedValue()) return getForcedValue();
		else return getSpecificValue();
	}
	public String getKeyFieldName(String defa) throws Exception {
		return null;
	}
//	public void SetFocus() {
//		Object object=this.getComponentForList();
//		if (object instanceof Component) ((Component) object).requestFocus();
//	}
//	
//	public void BaseToControlAndEdit(String zModo) throws Exception {
//		this.BaseToControl(zModo, true);
//		this.editIfPosible(zModo, partialRefresh);
//	}

	// -------------------------------------------------------------------------//
	// De la base al control
	// -------------------------------------------------------------------------//

	
	public void BaseToControl(String zModo, boolean userRequest) throws Exception {
//		if (this.getProp()==null) return;
//		if (getProp() instanceof JObjBD) {
//			this.setValue((JRecord)this.getProp().getObjectValue());
//			return;
//		}	else if (getProp() instanceof JObjBDs) {
//			this.setValue((JRecords)this.getProp().getObjectValue());
//			return;
//		} 
//		if (zModo.equals("A")) 
//			this.setValue(this.getProp().toRawString());
//		else {
//			this.setValue(this.getProp().toString());
//		}
	}

	// public void DefaultToControl() throws Exception {
	// setValue( this.GetValorDefault() );
	// }

	public String getValueDescription() throws Exception {
		return this.getValue();
	}

	// -------------------------------------------------------------------------//
	// Mando los datos del control a la base de datos
	// -------------------------------------------------------------------------//
	public void ControlToBase() throws Exception {
		if (oProp==null) return;

		boolean bTieneValor=this.hasValue();

		if (!bTieneValor) {
			if (ifRequerido()) JExcepcion.SendError("Debe ingresar un valor al campo: {"+JLanguage.translate(getFixedProp().GetDescripcion())+"}");
			oProp.setValue("");
			return;
		}
		
		if ( oProp.isNotNull() && oProp.toString().equals(this.getValue()) )
			return;

		if (this.isDatetime()&&!JDateTools.CheckDatetime(this.getValue())) // oProp.AsString() ) )
			JExcepcion.SendError("Debe ingresar una fecha valida para el campo"+" "+getFixedProp().GetDescripcion());

		if (this.isDate()&&!JDateTools.CheckDate(this.getValue())) // oProp.AsString() ) )
			JExcepcion.SendError("Debe ingresar una fecha valida para el campo"+" "+getFixedProp().GetDescripcion());

		if (this.isHour()&&!JDateTools.CheckHour(this.getValue())) // oProp.AsString() ) )
			JExcepcion.SendError("Debe ingresar una hora valida para el campo "+getFixedProp().GetDescripcion());

		if (this.isColour()) ChequearDatoNumerico(this.getValue());
		if (this.isNumerico()) ChequearDatoNumerico(this.getValue());

		// Al ser Read Only se supone que el dato no cambió y por lo tanto no hace falta re-leerlo
		// del control, el inconveniente además es que en el caso de un numérico ReadOnly el control
		// visualiza el .toStringFormatted y no se puede revertir a un formato de base de datos
		// a partir del String del Control y entonces para evitar el error no se re-lee. Emilio 19/11/02
		// if (!(this.isNumerico()&&ifReadOnly()))
		this.controlToProp();
	}

	public void ControlToBaseWithinCheck() throws Exception {
//		if (oProp==null) return;
//		if (!this.hasValue()) return;
//		if ( oProp.isNotNull() && oProp.toString().equals(this.getValue()) ) return;
//		if ( oProp.isRecord()) return;
//		if ( oProp.isRecords()) return;
//		oProp.setValue(this.getValue());
	}
	public void controlToProp() throws Exception {
//		if (oProp==null) return;
//		oProp.setValue(this.getValue());
	}

	public boolean ifRequerido() throws Exception {
		if (this.GetRequerido()==null) return false;
		return this.GetRequerido().equals(JBaseForm.REQ);
	}

	public boolean isDatetime() throws Exception {
		return this.GetTipoDato().equals(JBaseForm.DATETIME);
	}

	public boolean isDate() throws Exception {
		return this.GetTipoDato().equals(JBaseForm.DATE);
	}

	public boolean isHour() throws Exception {
		return this.GetTipoDato().equals(JBaseForm.HOUR);
	}
	public boolean isColour() throws Exception {
		return this.GetTipoDato().equals(JBaseForm.COLOUR);
	}
	public boolean isImage() throws Exception {
		return this.GetTipoDato().equals(JBaseForm.IMAGE);
	}
	
	/*
	 * public boolean ifCampoIngresable() throws Exception { if ( oProp == null ) return true; if ( oProp.ifVirtual() ) return false; return true; }
	 */
	// -------------------------------------------------------------------------//
	// Verifico si el dato tiene valor
	// -------------------------------------------------------------------------//
	public void forcePresetForDefault() throws Exception {
		if (oProp==null) return;
		if (oProp.forcePresetForDefault())
				oProp.preset();
	}
	public boolean propHasRawValue() throws Exception {
		if (oProp==null) return false;
		if (oProp.isRawNull()) return false;
		return true;
	}

	public boolean changeValue() throws Exception {
		return !getValue().equals(oProp.toString());
	}

	// -------------------------------------------------------------------------//
	// Verifico si campo es clave
	// -------------------------------------------------------------------------//
	public boolean ifClave() {
		if (getFixedProp()==null) return false;
		return getFixedProp().isKey();
	}

	public boolean ChequearDatoNumerico(String zValor) throws Exception {
		if (zValor.equals("")) JExcepcion.SendError("Debe ingresar un valor al campo: "+getFixedProp().GetDescripcion());

		// Solo chequeo el formato si hubo posibilidades de modificar el dato.
		// Esto es porque si el dato estaba protegido entonces se visualizó con el formato regional
		// por lo tanto posee separadores de miles, etc. el las funciones que evalúan el Número
		// solo funcionan si el formato es el de Edición (osea el .toString del objeto no el .toStringFormatted).
		if (!ifReadOnly()) {
			boolean signed=!(GetTipoDato().equals(JBaseForm.UINT)||GetTipoDato().equals(JBaseForm.UFLOAT)||GetTipoDato().equals(JBaseForm.ULONG));

			// Chequeo si es numerico
			if (!JTools.isNumber(zValor, signed)) {
				if (signed) {
					JExcepcion.SendError("Debe ingresar un valor numérico para el campo: "+getFixedProp().GetDescripcion());
				} else {
					JExcepcion.SendError("Debe ingresar un valor numérico y mayor o igual que cero para el campo: "+getFixedProp().GetDescripcion());
				}
			}

			// Chequeo si es entero unsigned
			if (GetTipoDato().equals(JBaseForm.UINT)&&!JTools.isIntegerNumber(zValor)) JExcepcion.SendError("Debe ingresar un valor numérico y mayor o igual que cero al campo: "+getFixedProp().GetDescripcion());

			// Chequeo si es float unsigned
			if (this.GetTipoDato().equals(JBaseForm.UFLOAT)&&Double.parseDouble(zValor)<0) JExcepcion.SendError("Debe ingresar un valor numérico y mayor o igual que cero al campo: "+getFixedProp().GetDescripcion());
		}
		return true;
	}

	public boolean isNumerico() {
		if (GetTipoDato()==null) return false;
		return GetTipoDato().equals(JBaseForm.INT)||GetTipoDato().equals(JBaseForm.FLOAT)||GetTipoDato().equals(JBaseForm.LONG)||GetTipoDato().equals(JBaseForm.UFLOAT)||GetTipoDato().equals(JBaseForm.UINT)||GetTipoDato().equals(JBaseForm.ULONG);
	}

	public boolean isFloat() {
		if (GetTipoDato()==null) return false;
		return GetTipoDato().equals(JBaseForm.FLOAT)||GetTipoDato().equals(JBaseForm.UFLOAT);
	}

	// public void SetearValorDefault() throws Exception {
	// if (this.valorDefault==null) return;
	// if (this.getProp()==null) return;
	// this.getProp().setValue(valorDefault);
	// }

	public void asignDefaultData() throws Exception {
		if (this.ifPreasignado()) return;
//		if (!this.ifTieneDefault()) return;
		this.asignDefaultValue(this.getValorDefault());
		// if (this.ifReadOnly()) this.getProp().setValue(this.GetValorDefault());
		// this.SetearValorDefault();
		// this.BaseToControl(JBaseForm.MODO_ALTA, true);
	}
	
	public void asignDefaultDataActive() throws Exception {
		if (this.ifPreasignado()) return;
		if (hasValue()) return;
		if (!this.ifTieneDefault()) return;
		PssLogger.logInfo("SET DEFAULT: "+this.getValorDefault()+" en "+this.getId());
		this.asignDefaultValue(this.getValorDefault());
	}
	
	public void assignDefault(double zVal) throws Exception {
		this.SetValorDefault(zVal);
		this.asignDefaultData();
	}
	
	public void assignDefault(String zVal) throws Exception {
		this.SetValorDefault(zVal);
		this.asignDefaultData();
	}

	public void assignDefault(int zVal) throws Exception {
		this.SetValorDefault(zVal);
		this.asignDefaultData();
	}

	// para sobrescribir
	protected void asignDefaultValue(JObject value) throws Exception {
//		if (this.bReadOnly)
//			this.setValue(this.getDefaultAsFormattedString());
//		else
		if (value==null)
			this.clear();
		else
			//this.setValue(this.getDefaultAsInputString());
			if (value.isMultiple()) {
				this.setValue((JMultiple)value);
			} else if (value.isIntervalDateTime()) {
				this.setValue((JIntervalDateTime)value);
			} else if (value.isIntervalDate()) {
				this.setValue((JIntervalDate)value);
			} else if (value.isBaseRecord()) {
				this.setValue(((JObjBD)value).getValue());
			} else
				this.setValueFormUI(value.toInputString());
	}

//	public String getDefaultAsInputString() throws Exception {
//		this.getProp().setValue(this.getValorDefault()); // esto puede traer problemas
//		return this.getProp().toInputString();
//	}

	public String getDefaultAsFormattedString() throws Exception {
		this.getProp().setValue(this.getValorDefault()); // esto puede traer problemas
		return this.getProp().toFormattedString();
	}

	public boolean ifCampoQuery() {
		return bCampoQuery;
	}

	public JWin GetWinSelect() throws Exception {
		return null;
	}
	
  public JWin getWinElegido(String zClave) throws Exception {
  	return null;
  }
  public JWin[] getWinElegidos(String zClave) throws Exception {
  	return null;
  }
	public void setWinSelect(JWin win) throws Exception {
	}

	// public void toSAX( JSAXWrapper wrapper ) throws Exception {
	// AttributesImpl attr = new AttributesImpl();
	//
	// attr.addAttribute( "", "type", "type", "CDATA", getClass().getName() );
	// wrapper.startElement( "field", attr );
	// wrapper.endElement( "field" );
	// }
	//

	//
	// web methods
	//
	public void mapSwingComponents(JMap zMapToPutIn) {

	}

	public boolean isVisible() {
		return this.bVisible;
	}
	public boolean isTableEditable() {
		return this.isComponentEditable();
	}

	public boolean isEditable() {
		return !this.ifReadOnly()&&this.isComponentEditable();
	}

	protected boolean isComponentEditable() {
		return true;
	}

	public void internalBuild() throws Exception {
	}
	
	public void editIfPosible(String zModo, boolean partialRefresh) throws Exception {
		if (this.ifPreasignado()) {
			this.disable();
			return;
		}
		if (this.ifReadOnly()) {
			this.disable();
			return;
		}
		if (zModo.equals(JBaseForm.MODO_CONSULTA)) {
			this.disable();
			return;
		}
		if ((zModo.equals(JBaseForm.MODO_MODIFICACION)||zModo.equals(JBaseForm.MODO_CONSULTA_ACTIVA))&&this.ifClave()) {
			this.disable();
			return;
		}
		if (zModo.equals(JBaseForm.MODO_ALTA) && !partialRefresh) {
			if (this.ifTieneDefault()) this.asignDefaultData();
			else this.clear();
		}
		this.edit(zModo);
	}

	public String GetObjectType() {
		String tipoBD="";
		String tipoForm=GetTipoDato();
		if (getProp()!=null) tipoBD= getProp().getObjectType();

		if (tipoForm!=null) {
			if (tipoBD.equals("")) {
				if (tipoForm.equals(JBaseForm.INT)) 			return JObject.JINTEGER;
				if (tipoForm.equals(JBaseForm.UINT))			return JObject.JINTEGER;
				if (tipoForm.equals(JBaseForm.LONG)) 		return JObject.JLONG;
				if (tipoForm.equals(JBaseForm.ULONG)) 		return JObject.JLONG;
				if (tipoForm.equals(JBaseForm.FLOAT))		return JObject.JFLOAT;
			} else if (tipoBD.equals(JObject.JLONG)) {
				if (tipoForm.equals(JBaseForm.INT)) 			return JObject.JINTEGER;
				if (tipoForm.equals(JBaseForm.UINT))			return JObject.JINTEGER;
				if (tipoForm.equals(JBaseForm.LONG)) 		return JObject.JLONG;
				if (tipoForm.equals(JBaseForm.ULONG)) 		return JObject.JLONG;
					if (tipoForm.equals(JBaseForm.FLOAT))		return JObject.JFLOAT;
			} else if (tipoBD.equals(JObject.JDATE)||tipoBD.equals(JObject.JDATETIME)) {
				if (tipoForm.equals(JBaseForm.DATE)) 		return JObject.JDATE;
				if (tipoForm.equals(JBaseForm.DATETIME)) return JObject.JDATETIME;
			} else if  (tipoBD.equals(JObject.JSTRING)) {
				if (tipoForm.equals(JBaseForm.CHAR)) 		return JObject.JSTRING;
				if (tipoForm.equals(JBaseForm.INT)) 			return JObject.JINTEGER;
				if (tipoForm.equals(JBaseForm.UINT))			return JObject.JINTEGER;
				if (tipoForm.equals(JBaseForm.LONG)) 		return JObject.JLONG;
				if (tipoForm.equals(JBaseForm.ULONG)) 		return JObject.JLONG;
				if (tipoForm.equals(JBaseForm.FLOAT))		return JObject.JFLOAT;
				if (tipoForm.equals(JBaseForm.HOUR))		
					return JObject.JHOUR;
				
			}  else if  (tipoBD.equals(JObject.JFLOAT)) {
				if (tipoForm.equals(JBaseForm.INT)) 			return JObject.JINTEGER;
				if (tipoForm.equals(JBaseForm.UINT))			return JObject.JINTEGER;
				if (tipoForm.equals(JBaseForm.LONG)) 		return JObject.JLONG;
				if (tipoForm.equals(JBaseForm.ULONG)) 		return JObject.JLONG;
				if (tipoForm.equals(JBaseForm.FLOAT))		return JObject.JFLOAT;
				if (tipoForm.equals(JBaseForm.CHAR))		return JObject.JSTRING;
				
			} else if  (tipoBD.equals(JObject.JCOLOUR)) {
				if (tipoForm.equals(JBaseForm.COLOUR))		return JObject.JCOLOUR;
				
			}
			
		}

		return tipoBD;
	}

	public String getForcedValue() {
		return sForcedValue;
	}

	public void setForcedValue(String forcedValue) {
		sForcedValue=forcedValue;
	}

	public boolean hasForcedValue() {
		return sForcedValue!=null;
	}

//	public void addDependencies(String child) throws Exception {
//		if (oDependencies==null) oDependencies=JCollectionFactory.createList();
//		oDependencies.addElement(child);
//	}

//	public JList<String> getDependencies() throws Exception {
//		return oDependencies;
//	}
//
//	public boolean hasDependencies() throws Exception {
//		return oDependencies!=null;
//	}

//	public void setDependencies(JList<String> dependencies) throws Exception {
//		this.oDependencies=dependencies;
//	}

	/**
	 * permite ocultar dinamicamente (temporalmente)
	 */
	public JFormControl setVisible(boolean value) throws Exception {

		this.bVisible=value;
		if (this.getComponent()!=null) 
			this.getComponent().setVisible(value);
		if (this.linkedLabel!=null) this.linkedLabel.setVisible(value);
		return this;
	}
	
	public JFormControl setOnlyDisableUpdate(JBaseForm form) throws Exception {
		if (form.isAlta())
				this.setVisible(false);
		this.SetReadOnly(true);
		return this;
	}
	public boolean hasDefaultValue() throws Exception {
		return propDefault!=null;
	}
	public void setDefaultValueProp(JObject prop) throws Exception {
		 propDefault=prop;
	}

	public JFormControl findControl(String id) throws Exception {
		return this.getControls().findControl(id);
	}
	
	public JFormControl setPopupIcon(boolean value) throws Exception {
		this.bPopupIcon=value;
		return this;
	}
	
	public boolean hasPopupIcon() {
		return this.bPopupIcon;
	}
	

	public String getForeground() {
		return oForeground;
	}

	public JFormControl setForeground(Color foreground) {
		setForeground(JTools.ColorToString(foreground));
		if (getComponent()!=null)
			getComponent().setForeground(foreground);
		return this;
	}
	public JFormControl setForeground(String foreground) {
		oForeground=foreground;
		return this;
	}
	
	
	public String getBackground() {
		return oBackground;
	}

	
	public JFormControl setBackground(Color background) {
		setBackground(JTools.ColorToString(background));
		if (getComponent()!=null)
			getComponent().setBackground(background);
		return this;
	}
	public JFormControl setBackground(String background) {
		oBackground=background;
		return this;
	}
	
	public String getToolTip() {
		return sToolTip;
	}

	public String findToolTip() throws Exception {
		if (this.sToolTip!=null) return this.sToolTip;
		if (!this.hasFixedProp()) return null;
		return this.getFixedProp().GetDescripcion();
	}
	
	public JFormControl setToolTip(String toolTip) {
		this.sToolTip=toolTip;
		return this;
	}
	
	public String getHelp() {
		return this.sHelp;
	}
	
	public boolean hasHelp() {
		return this.sHelp!=null;
	}


	public void setHelp(String string) {
		this.sHelp = string;
	}


	@Override
	public String toString() {
		try {
			String op = getOperator()==null?"=":getOperator();
			op =  op.equalsIgnoreCase("like")?"contiene":op;
			String val = getValueDescription()==null?(getValue()==null?null:getValue()):getValueDescription();
			if (val==null || val.equals("")) return "";
			return GetDisplayName()+" "+op+" "+val;
		} catch (Exception e) {
			return "";
		}
	}
	
	public int getNextControl() throws Exception {
		return this.nextControl;
	}

	public void addBreak() throws Exception {
		this.nextControl=NEXT_BREAK;
	}

	public void addLine() throws Exception {
		this.nextControl=NEXT_LINE;
	}

	String dropZone;
	JBaseWin dropManager;
	public String getDropZone() {
		return dropZone;
	}


	public JBaseWin getDropManager() {
		return dropManager;
	}


	public void addDropManager(String zone,JBaseWin win) throws Exception {
		dropZone=zone;
		dropManager=win;
	}
//	public void setEditable(boolean value) throws Exception {
//		this.bEditable=value;
//	}

	public boolean hasValueControl(String id) throws Exception {
		JFormControl c = this.getControls().findControl(id);
		if (c==null) return false;
		return c.hasValue();
	}

	public boolean isUnsigned() throws Exception {
		if (this.sTipoDato==null) return bUnsigned;
		if (this.sTipoDato==JBaseForm.UFLOAT) return true;
		if (this.sTipoDato==JBaseForm.ULONG) return true;
		if (this.sTipoDato==JBaseForm.UINT) return true;
		return false;
	}


	public JFormControlResponsive getResponsiveVersion() throws Exception {
		return null;
	}

	public String  findLabel() throws Exception {
		
		int x=getComponent().getX();
		int y=getComponent().getY();
		
		Container panel =  getComponent().getParent();
		if (panel==null) return GetDisplayName();
			int size = panel.getComponentCount();
			for (int i = 0; i < size; i++) {
				Component comp = panel.getComponent(i);
				if (comp instanceof JLabel ) {
					int lx=comp.getX()+comp.getWidth();
					int ly=comp.getY();
					int lx2=comp.getX();
					int ly2=comp.getY()+comp.getHeight();
					
					double distance1 = Math.sqrt(((x-lx)*(x-lx))+(((y-ly)*(y-ly))));
					double distance2 = Math.sqrt(((x-lx2)*(x-lx2))+(((y-ly2)*(y-ly2))));
					
					if (distance1<15 || distance2<15) {
						return ((JLabel)comp).getText();
				 }
			}
		}

		
		return  GetDisplayName();
	}
	public boolean useTwoFields() throws Exception {
		return false;
	}
}
