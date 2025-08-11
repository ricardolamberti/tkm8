package pss.core.winUI.responsiveControls;

import java.awt.Component;
import java.awt.Insets;

import javax.swing.border.Border;

import pss.common.security.BizUsuario;
import pss.core.services.fields.JScript;
import pss.core.tools.collections.JList;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.www.ui.JWebIcon;

public class JFormControlResponsive extends JFormControl {

	public final static String AUTO="";
	public final static String SMALL="sm-";
	public final static String MEDIUM="md-";
	public final static String LARGE="lg-";
	public final static String EXTRALARGE="xl-";

	public final static String ALIGNLEFT="align-self-start";
	public final static String ALIGNCENTER="align-self-center";
	public final static String ALIGNRIGHT="align-self-end";
	
	public final static String CONTROL="form-control";

	
	String sizeDevice = AUTO;
	String columns = "12";
	String complexColumnClass;
	String offsetClass;
	long offsetPos;	
//	String align;	
	String label;	
	JWebIcon labelIcon;
	String responsiveClass;
	long maxWidth;
	long maxHeight;
	long minWidth;
	long minHeight;
	int width;
	int height;
	boolean enteristab;
	boolean onlyExpanded;
	boolean onlyCollapsed;


	JScript script;
	private Insets oPadding;
	private long bBlockOverSize=-1;
  private int presicion =-1; // -1: auto



  public int getPrecision() {
		return presicion;
	}

	public JFormControlResponsive setPrecision(int presicion) {
		this.presicion = presicion;
		return this;
	}
  public boolean isOnlyExpanded() {
		return onlyExpanded;
	}

	public JFormControlResponsive setOnlyExpanded(boolean onlyExpanded) {
		this.onlyExpanded = onlyExpanded;
		return this;
	}
  public boolean isOnlyCollapsed() {
		return onlyCollapsed;
	}

	public JFormControlResponsive setOnlyCollapsed(boolean onlyCollapsed) {
		this.onlyCollapsed = onlyCollapsed;
		return this;
	}
	public long getBlockOverSize() {
		return bBlockOverSize;
	}

	public JFormControlResponsive setBlockOverSize(long bBlockOverSize) {
		this.bBlockOverSize = bBlockOverSize;
		return this;
	}
  
	public Insets getPadding() {
		return oPadding;
	}

	public JFormControlResponsive setPadding(Insets oPadding) {
		this.oPadding = oPadding;
		return this;
	}

	private Insets borderRadius;
	public JFormControlResponsive setBorderRadius(Insets v) {
		this.borderRadius = v;
		return this;
	}
	public Insets getBorderRadius() {
		return borderRadius;
	}

	public JFormControlResponsive setBorderRadius(int tl, int tr, int bl, int br) {
		return setBorderRadius(new Insets(tl,tr,bl,br)); // sacar el objecto Inserts que es de awt
	}

	public JFormControlResponsive setPadding(int t, int l, int b, int r) {
		return setPadding(new Insets(t,l,b,r)); // sacar el objecto Inserts que es de awt
	}

	public JFormControlResponsive setMargin(int t, int l, int b, int r) {
		return setMargin(new Insets(t,l,b,r)); // sacar el objecto Inserts que es de awt
	}

	public Insets getMargin() {
		return oMargin;
	}
	
	public boolean showFormatted() throws Exception {
		if (getModo().equals(JBaseForm.MODO_CONSULTA)) return true;
		if (getModo().equals(JBaseForm.MODO_CONSULTA_ACTIVA) && !isEditable()) return true;
		
		return false;
	}

	public JFormControlResponsive setMargin(Insets oMargin) {
		this.oMargin = oMargin;
		return this;
	}

	public Insets getBorderInsets() {
		return oBorderInsets;
	}

	public JFormControlResponsive setBorderInsets(Insets oBorderInsets) {
		this.oBorderInsets = oBorderInsets;
		return this;
	}

	private Insets oMargin;
	private Insets oBorderInsets;


	public void setOffsetClass(String offsetClass) {
		this.offsetClass = offsetClass;
	}

	public JScript getScript() {
		return script;
	}

	public JFormControlResponsive setScript(JScript script) {
		this.script = script;
		return this;
	}


//	@Override
//	public void setPreferredWidth(int zWidth) {
//		this.setMaxWidth(zWidth);
//	}

	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public void disable() throws Exception {
		SetReadOnly(true);
	}
	public void enable() throws Exception {
		SetReadOnly(false);
	}

	public long getMaxWidth() {
		return maxWidth;
	}
	public JFormControlResponsive setMaxWidth(long maxWidth) {
		this.maxWidth = maxWidth;
		return this;
	}
	public long getMaxHeight() {
		return maxHeight;
	}
	public JFormControlResponsive setMaxHeight(long maxHeight) {
		this.maxHeight = maxHeight;
		return this;
	}

	public long getMinWidth() {
		return minWidth;
	}
	public JFormControlResponsive setMinWidth(long v) {
		this.minWidth = v;
		return this;
	}
	public long getMinHeight() {
		return minHeight;
	}
	public JFormControlResponsive setMinHeight(long v) {
		this.minHeight = v;
		return this;
	}

	public void setLayout() throws Exception {
		applyOrganizeColumns();
	}
	
	public void applyOrganizeColumns() throws Exception {
		if (getControls().getParent()==null) {
			if (getControls().getBaseForm()==null) return;
			if (getControls().getBaseForm().getInternalPanel()==null) return;
			if (getControls().getBaseForm().getInternalPanel().getOrganizeColumns()==0) return;
			this.setSizeColumns((long)(12/getControls().getBaseForm().getInternalPanel().getOrganizeColumns()));
			return;
		}
		if (getControls().getParent()==null) return;
		if (getControls().getParent().getOrganizeColumns()==0) return;
		this.setSizeColumns((long)(12/getControls().getParent().getOrganizeColumns()));
	}

	protected int sizeLabels=-1;
	public JFormControlResponsive setSizeLabels(int size) {
		this.sizeLabels = size;
		return this;
	}

	public int getSizeLabels() {
		if (sizeLabels!=-1)
			return this.sizeLabels;
		if (getControls()==null) return 5;
		if (getControls().getParent()==null) return 5;
		return this.getControls().getParent().getSizeLabels();
	}

	
	private String formatFields=null;

	public JFormControlResponsive setFormatFields(String foramtFields) {
		this.formatFields = foramtFields;
		return this;
	}

	public String getFormatFields() throws Exception {
		if (formatFields!=null) return formatFields;
		if (getControls()==null) return formatFields;
		if (getControls().getParent()==null) {
			if (getControls().getBaseForm()==null) return null;
			if (getControls().getBaseForm().getInternalPanel()==null) return null;
			return getControls().getBaseForm().getInternalPanel().getFormatFields();
		}
		if (getControls().getParent()==null) return null;
		return getControls().getParent().getFormatFields();
	}
	
	
	public void hide() throws Exception {
		setVisible(false);
	}

	public void show() throws Exception {
		setVisible(true);
	}

	public String getResponsiveClass() {
  	//if (responsiveClass==null) return CONTROL;
		return responsiveClass;
	}

	public JFormControlResponsive setReadOnly(boolean zValor) {
		bReadOnly=zValor;
		return this;
	}


	public JFormControlResponsive setResponsiveClass(String responsiveClass) {
		this.responsiveClass = responsiveClass;
		return this;
	}

	public String getLabel() {
		return label;
	}

	public JFormControlResponsive setLabel(String text) {
		this.label = text;
		return this;
	}

//	public String getAlign() throws Exception {
//	  return align;
//	}
	
	public JWebIcon getLabelIcon() {
		return labelIcon;
	}

	public JFormControlResponsive setLabelIcon(JWebIcon text) {
		this.labelIcon = text;
		return this;
	}


//	public JFormControlResponsive setAlign(String align) {
//		this.align = align;
//		return this;
//	}

	public long getOffsetPos() {
		return offsetPos;
	}

	public JFormControlResponsive setOffsetPos(long offsetPos) {
		this.offsetPos = offsetPos;
		return this;
	}

	public String getOffsetClass() {
		return offsetClass;
	}

	public JFormControlResponsive setOffsetColums(String offsetClass) {
		this.offsetClass = offsetClass;
		return this;
	}

	public boolean isEnteristab() {
		return enteristab && isEditable();
	}

	public JFormControlResponsive setEnteristab(boolean enteristab) {
		this.enteristab = enteristab;
		return this;
	}

	public String getSizeDevice() {
		return sizeDevice;
	}

	public JFormControlResponsive setSizeDevice(String size) {
		this.sizeDevice = size;
		return this;
	}
	public JFormControlResponsive setComplexColumnClass(String size) {
		this.complexColumnClass = size;
		return this;
	}

	public String getColumns() {
		return columns;
	}
	public JFormControlResponsive setSizeColumns(long columns) {
		this.columns = ""+columns;
		return this;
	}
	public JFormControlResponsive setColumnsAuto() {
		this.columns = "auto";
		return this;
	}
	public JFormControlResponsive setColumnsEquals() {
		this.columns = "";
		return this;
	}
	public JFormControlResponsive setHeight(int h) {
		this.height = h;
		return this;
	}
	public JFormControlResponsive setWidth(int w) {
		this.width = w;

		return this;
	} 
	
	public String getSpecificValue() throws Exception {
		if (getProp()==null) return "";
		if (getProp().isNull()) 
			return "";
		return getProp().toString();
	}
	
	public JFormControlResponsive setSizeColumns(String columns) {
		this.columns = columns;
		return this;
	}


	public String getColumnClass() throws Exception {
		String cls = "";
		if (complexColumnClass!=null) 
			return complexColumnClass;
		if (getSizeDevice().equals("")) {
			cls += BizUsuario.retrieveSkinGenerator().getColumnClass(this,getColumns());
			
		} else {
			cls+="col-"+getSizeDevice()+getColumns();
				
		}
 		if (offsetClass!=null) 
 			cls += " "+offsetClass+" ";
 		else if (offsetPos!=0) 
			cls+=" col-sm-offset-"+offsetPos;
// 		if (align!=null) 
//			cls+=" "+align;
 		return cls;
	}

	protected void updateComponentSize() {
	}

	protected void updateComponentWidth() {
	}

	public void setBorder(Border b) {
	}

	public JList<Component> getComponents() throws Exception {
		return null;
	}

	public void generateComponentList(JList<Component> comps) throws Exception {
	}

	public Component getComponent() {
		return null;
	}

	public Component getComponentForList() {
		return null;
	}
	// ----------------------------------------c---------------------------------//
	// Constructor
	// -------------------------------------------------------------------------//
	public JFormControlResponsive() {
		super();
		setResponsive(true);
	}


	public void getDataComponente(JFormControl oldCtrl) throws Exception {
		this.setProp(oldCtrl.getProp());
		this.SetRequerido(oldCtrl.GetRequerido());
		this.setVisible(oldCtrl.isVisible()&&oldCtrl.getComponent().isVisible());
		this.setHide(oldCtrl.getComponent().getParent()==null);
		this.SetTipoDato(oldCtrl.GetTipoDato());
		this.SetDisplayName(oldCtrl.GetDisplayName());
		this.setFixedProp(oldCtrl.getFixedProp());
		this.setLabel(oldCtrl.findLabel());
		this.setOperator(oldCtrl.getOperator());
		this.setIdControl(oldCtrl.getIdControl());
		this.setSkinStereotype(oldCtrl.getSkinStereotype());
		this.SetPermitirTodo(oldCtrl.ifPermitirTodo());
		this.SetValorDefault(oldCtrl.getValorDefault());
//		this.setDependencies(oldCtrl.getDependencies());
		this.SetReadOnly(oldCtrl.ifReadOnly());
		this.setPersistente(oldCtrl.isPersistente());
		this.setHide(oldCtrl.getComponent().getParent()==null);
		this.setUnsigned(oldCtrl.isUnsigned());
//		this.setAlignContent(oldCtrl.getAlignContent());
		this.setLinkedLabel(oldCtrl.getLinkedLabel());
		this.setInTableRow(oldCtrl.getInTableRow());
		this.setInTableCol(oldCtrl.getInTableCol());
		this.setTableName(oldCtrl.getTableName());
		this.setRefreshForm(oldCtrl.hasToRefreshForm());
		this.setForcedValue(oldCtrl.getForcedValue());
//		this.setMaxWidth(oldCtrl.getPreferredWidth());
//		this.setMaxHeight(oldCtrl.getPreferredHeight());
		this.setToolTip(oldCtrl.getToolTip());
		this.setPlaceHolder((oldCtrl.getPlaceHolder()==null||oldCtrl.getPlaceHolder().equals(""))?oldCtrl.GetDisplayName():oldCtrl.getPlaceHolder());
		if (oldCtrl.getComponent()==null) {
			this.setComplexColumnClass("col-sm-12");
		} else {
			if (oldCtrl.getComponent().getParent()==null) {
				this.setVisible(false);
			} else {
				int ancho = oldCtrl.getComponent().getWidth();
				int newAncho=((ancho/100)+1);
				if (oldCtrl.getComponent()!=null && oldCtrl.getComponent().getParent()!=null){
					newAncho=(oldCtrl.getComponent().getParent().getSize().getWidth()/12)==0?0:(int)(ancho/(oldCtrl.getComponent().getParent().getSize().getWidth()/12));
				}
				this.setComplexColumnClass("col-xs-"+newAncho);
				this.setMinWidth(ancho);
				this.setMinHeight(oldCtrl.getComponent().getHeight());
			}
		}

	}

	public String getResponsiveHalf(boolean preview) throws Exception {
		if (preview)
			return "col-xl-6 col-lg-12 col-md-12 colS-sm-12";
		else
			return "col-xl-6 col-lg-6 col-md-12 col-sm-12";
	}

	private int hAlign;
	public int getHAlignment() throws Exception {
		if (hAlign==JFormControl.ALIGN_DEFAULT) 
			return this.findHAlignDefault();
		return hAlign;
	}
	public JFormControlResponsive setHAlign(int v) {
		this.hAlign = v;
		return this;
	}
	
	public int findHAlignDefault() throws Exception {
		return JFormControl.ALIGN_DEFAULT;
	}

	private int vAlign;
	public int getVAlignment() throws Exception {
		if (vAlign==JFormControl.ALIGN_DEFAULT) 
			return this.findVAlignDefault();
		return vAlign;
	}
	public JFormControlResponsive setVAlign(int v) {
		this.vAlign = v;
		return this;
	}
	public int findVAlignDefault() throws Exception {
		return JFormControl.ALIGN_DEFAULT;
	}

	private int fontSize;
	public JFormControlResponsive setFontSize(int v) {
		this.fontSize=v;
		return this;
	}

	public int getFontSize() {
		return this.fontSize;
	}

	private int fontStyle;
	public JFormControlResponsive setFontStyle(int v) {
		this.fontStyle=v;
		return this;
	}
	
	public int getFontStyle() {
		return this.fontStyle;
	}

	private int fontWeigth;
	public JFormControlResponsive setFontWeigth(int v) {
		this.fontWeigth=v;
		return this;
	}

	public int getFontWeigth() {
		return this.fontWeigth;
	}

	private String overflowX;
	public JFormControlResponsive setOverflowX(String v) {
		this.overflowX=v;
		return this;
	}

	public String getOverflowX() {
		return this.overflowX;
	}

	private String overflowY;
	public JFormControlResponsive setOverflowY(String v) {
		this.overflowY=v;
		return this;
	}

	public String getOverflowY() {
		return this.overflowY;
	}

	private String backgroundImage;
	public JFormControlResponsive setBackgroundImage(String v) {
		this.backgroundImage=v;
		return this;
	}

	public String getBackgroundImage() {
		return this.backgroundImage;
	}

	private String backgroundRepeat;
	public JFormControlResponsive setBackgroundRepeat(String v) {
		this.backgroundRepeat=v;
		return this;
	}

	public String getBackgroundRepeat() {
		return this.backgroundRepeat;
	}

	private String backgroundSize;
	public JFormControlResponsive setBackgroundSize(String v) {
		this.backgroundSize=v;
		return this;
	}

	public String getBackgroundSize() {
		return this.backgroundSize;
	}

	private String border;
	public JFormControlResponsive setBorder(String v) {
		this.border=v;
		return this;
	}
	public String getBorder() {
		return this.border;
	}

	private String borderTop;
	public JFormControlResponsive setBorderTop(String v) {
		this.borderTop=v;
		return this;
	}
	public String getBorderTop() {
		return this.borderTop;
	}
	
	private String borderBottom;
	public JFormControlResponsive setBorderBottom(String v) {
		this.borderBottom=v;
		return this;
	}
	public String getBorderBottom() {
		return this.borderBottom;
	}



	private boolean noWrap=false;
	public JFormControlResponsive setNoWrap(boolean v) {
		this.noWrap=v;
		return this;
	}

	public boolean isNoWrap() {
		return this.noWrap;
	}
	
	private String displayType;
	public JFormControlResponsive setDisplayType(String v) {
		this.displayType=v;
		return this;
	}
	public JFormControlResponsive displayNone() {
		return this.setDisplayType(DISPLAY_NONE);
	}
	public JFormControlResponsive displayBlock() {
		return this.setDisplayType(DISPLAY_BLOCK);
	}
	public JFormControlResponsive displayFlex() {
		return this.setDisplayType(DISPLAY_FLEX);
	}
	public JFormControlResponsive displayFlowRoot() {
		return this.setDisplayType(DISPLAY_FLOW_ROOT);
	}
	public JFormControlResponsive displayInlineBlock() {
		return this.setDisplayType(DISPLAY_INLINE_BLOCK);
	}

	public String getDisplayType() {
		return this.displayType;
	}

	private String boxShadow;
	public JFormControlResponsive setBoxShadow(String v) {
		this.boxShadow=v;
		return this;
	}

	public String getBoxShadow() {
		return this.boxShadow;
	}

	// shortcuts
	public JFormControlResponsive italic() {
		return this.setFontStyle(JFormControl.FONT_STYLE_ITALIC);
	}

	public JFormControlResponsive bold() {
		return this.setFontWeigth(JFormControl.FONT_WEIGHT_BOLD);
	}

	public JFormControlResponsive size(int v) {
		return this.setFontSize(v);
	}

	public JFormControlResponsive color(String v) throws Exception {
		return (JFormControlResponsive)this.setForeground(v);
	}

	public JFormControlResponsive noWrap() throws Exception {
		return this.setNoWrap(true);
	}

	public JFormControlResponsive right() throws Exception {
		return this.setHAlign(JFormControl.HALIGN_RIGHT);
	}

	public JFormControlResponsive left() throws Exception {
		return this.setHAlign(JFormControl.HALIGN_LEFT);
	}

	public JFormControlResponsive center() throws Exception {
		return this.setHAlign(JFormControl.HALIGN_CENTER);
	}
	
	public JFormControlResponsive overHidden() throws Exception {
		return this.setOverflowX(JFormControl.OVER_HIDDEN);
	}
	
}
