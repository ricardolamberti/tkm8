/*
 * Created on 11-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.util.LinkedHashMap;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JScript;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.www.platform.JWebIconsConstants;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.applications.JWebHistoryManager;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;
import pss.www.ui.processing.JXMLRepresentable;
import pss.www.ui.skins.ILayoutGenerator;

public abstract class JWebViewComponent implements JXMLRepresentable, JWebViewsConstants, JWebIconsConstants, JWebContentHolder {

	// ////////////////////////////////////////////////////////////////////////////
	//
	// INSTANCE VARIABLES
	//
	// ////////////////////////////////////////////////////////////////////////////

	private String sName;
	private String sClassResponsive;
	private String sRole;
	private String sRoleMobile;
	private String sSizeResponsive;
	private String sLabelLateral;
	private JWebIcon iLabelIcon;
	private String sLabelLink;
	private String sLabelClass;
	private String sFieldClass;
	private String sLabelLinkClass;
	private Point oLocation; // posicion desde la esquina superior derecha
	private Insets oInsetsLocation; // posicion desde la esquina inferior
									// derecha
	private String position = "static";
	private String oRelativePosition;
	private Dimension oSize;
	private Dimension oRealSize;
	private Dimension oResponsiveSize;
	private Dimension oResponsiveMinSize;
	protected String sSkinStereotype;
	private boolean bVisible = true;
	private Insets oPadding;
	private Insets oMargin;
	private Insets oBorderInsets;
	private Insets oParentBorderInsets;
	private String backgroundImage;
	private String backgroundRepeat;
	private String backgroundPosition;
	private String backgroundSize;
	private String oForeground;
	private String oBackground;
	private String overflowX;
	private String overflowY;
  private int hAlign = JFormControl.ALIGN_DEFAULT;
  private int vAlign = JFormControl.ALIGN_DEFAULT;

	private String alert;
	private String connectControl;
	private String connectControlField;
	private String connectControlOperator;
	private String connectControlDatatype;
	private boolean onlyExpanded;
	private boolean onlyCollapsed;


	private boolean refreshForm = false;
	private boolean detectChanges = false;

	private int zIndex = -1;
	private String table = null;;
	private long inTableRow = -1;
	private long inTableCol = -1;
	private JWebAction onBlur; // se dispara con la perdida de foco
	private boolean forceFocus; // se dispara con la perdida de foco
	private String placeHolder;
	private boolean responsive;
	private boolean affix;
  private long maxWidth;
  private long maxHeight;
	private String formatField;
	private boolean enterIsTab;
	
	private JFormControl oControl;
	private JScript script;
	protected boolean bGenerateInternalEvents=true;
	boolean filterNeverHide;

	public void setFilterNeverHide(boolean filterNeverHide) {
		this.filterNeverHide = filterNeverHide;
	}
 public JScript getScript() {
		return script;
	}

	public void setScript(JScript script) {
		this.script = script;
	}
  
	public JFormControl getControl() {
		return oControl;
	}

	public void setControl(JFormControl oControl) {
		this.oControl = oControl;
	}
	public boolean isDetectChanges() {
		return detectChanges;
	}

	public void setDetectChanges(boolean detectChanges) {
		this.detectChanges = detectChanges;
	}
	public String getConnectControl() {
		return connectControl;
	}
	public boolean hasConnectControl() {
		return connectControl!=null;
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
	public String getConnectControlField() {
		return connectControlField;
	}

	public void setConnectControlField(String connectControlField) {
		this.connectControlField = connectControlField;
	}


	public void setConnectControlDatatype(String connectControlDatatype) {
		this.connectControlDatatype = connectControlDatatype;
	}

	public boolean isEnterIsTab() {
		return enterIsTab;
	}

	public void setEnterIsTab(boolean enterIsTab) {
		this.enterIsTab = enterIsTab;
	}

	private int sizeLabels;
	
	public String getFormatFields() {
		return formatField;
	}

	public void setFormatFields(String foramtFields) {
		this.formatField = foramtFields;
	}
	
	public boolean isLabelHorizontal() {
		if (this.getFormatFields()==null) return false;
		if (isLabelRight())
				return true;
		if (isLabelLeft())
				return true;
		if (isLabelCenter())
			return true;
		return false;
	}
	public boolean isLabelCenter() {
		return this.getFormatFields().equals(JFormPanelResponsive.FIELD_LABEL_HORIZONTAL_CENTER);
	}
	public boolean isLabelRight() {
		return this.getFormatFields().equals(JFormPanelResponsive.FIELD_LABEL_HORIZONTAL_RIGHT);
	}
	public boolean isLabelLeft() {
		return this.getFormatFields().equals(JFormPanelResponsive.FIELD_LABEL_HORIZONTAL_LEFT);
	}
	
	public int getSizeLabels() {
		return this.sizeLabels;
	}

	public void setSizeLabels(int size) {
		this.sizeLabels = size;
	}

	public int offsetBottom;
	public int offsetRight;

	public boolean fullWidth;
	public boolean fullHeight;

	private JWebViewComposite oParent;

	private String sToolTip;
	private String sHelp;
	String dropZone;
	JBaseWin dropManager;
	protected String tableName=null;

	// REMEMBER TO CLEAN UP ALL THE VARIABLES DEFINED HERE, IN THE cleanUp()
	// METHOD

	// ////////////////////////////////////////////////////////////////////////////
	//
	// PUBLIC API
	//
	// ////////////////////////////////////////////////////////////////////////////
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String table) {
		this.tableName = table;
	}
	public boolean isInTable() {
		return inTableRow>=0;
	}
	public long getTableRow() {
		return inTableRow;
	}
	public long getTableCol() {
		return inTableCol;
	}
	public void setInTable(String name,long row,long column) {
		this.tableName=name;
		this.inTableRow = row;
		this.inTableCol = column;
		this.oRelativePosition = inTableRow == -1 ? null : "relative";
	}

	public boolean isResponsive() {
		return responsive;
	}

	public void setResponsive(boolean responsive) {
		this.responsive = responsive;
	}

	public void setX(int zValue) {
		this.getLocationLazyly().x = zValue;
		// this.markParentToLayout();
	}

	public void setY(int zValue) {
		this.getLocationLazyly().y = zValue;
		// this.markParentToLayout();
	}

	public void setZ(int zValue) {
		this.zIndex = zValue;
		// this.markParentToLayout();
	}
	public boolean isAffix() {
		return affix;
	}

	public void setAffix(boolean affix) {
		this.affix = affix;
	}
	public void clearLocation() {
		this.oLocation = null;
		this.oInsetsLocation = null;
	}

	public void setLocation(int zXValue, int zYValue) {
		this.setX(zXValue);
		this.setY(zYValue);
	}

	public void setLocation(Point dim) {
		this.setX(dim.x);
		this.setY(dim.y);
	}

	public Point getLocation() {
		return this.oLocation;
	}

	public void setWidth(int zValue) {
		this.getSizeLazyly().width = zValue;
		// this.markParentToLayout();
	}
	public JWebIcon getLabelIcon() {
		return iLabelIcon;
	}

	public void setLabelIcon(JWebIcon iLabelIcon) {
		this.iLabelIcon = iLabelIcon;
	}
	public void setHeight(int zValue) {
		if (zValue == -1)
			zValue = -1;
		else if (zValue < 0)
			zValue = 10;
		this.getSizeLazyly().height = zValue;
		// this.markParentToLayout();
	}

	public void setHeightResponsive(int zValue) {
		this.getResponsiveSize().height = zValue;
	}

	public void setWidthResponsive(int zValue) {
		this.getResponsiveSize().width = zValue;
	}
	
	public void setMinHeightResponsive(int zValue) {
		this.getResponsiveMinSize().height = zValue;
	}

	public void setMinWidthResponsive(int zValue) {
		this.getResponsiveMinSize().width = zValue;
	}


	public void clearSize() {
		this.oSize = null;
	}
 
	public void tryChangeSize(int w, int h) {
		Dimension d = this.getSizeLazyly();
		if (this.isFixWidth() && d.width > w) {
			w = d.width;
		}

		if (this.isFixHight() && d.height > h) {
			h = d.height;
		}
		this.setSize(w, h);
	}

	public void setSize(int zWidth, int zHeight) {
		this.setWidth(zWidth);
		this.setHeight(zHeight);
	}
	public void setMinSize(int zWidth, int zHeight) {
		this.setMinWidthResponsive(zWidth);
		this.setMinHeightResponsive(zHeight);
	}

	public int getAjusteScrollInverso() {
		return 17;
	}

	public long getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(long maxWidth) {
		this.maxWidth = maxWidth;
	}
	public long getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(long maxHeight) {
		this.maxHeight = maxHeight;
	}
	public boolean hasPlaceHolder() throws Exception {
		return placeHolder != null && !placeHolder.equals("");
	}

	public String getPlaceHolder() throws Exception {
		return placeHolder;
	}

	public void setPlaceHolder(String title) throws Exception {
		this.placeHolder = title;
	}
	
	private int fontStyle;
	public void setFontStyle(int v) {
		this.fontStyle=v;
	}
	public int getFontStyle() {
		return this.fontStyle;
	}

	private int fontWeight;
	public void setFontWeight(int v) {
		this.fontWeight=v;
	}
	public int getFontWeight() {
		return this.fontWeight;
	}

	private int fontSize;
	public void setFontSize(int v) {
		this.fontSize=v;
	}
	public int getFontSize() {
		return this.fontSize;
	}

	private boolean noWrap;
	public void setNoWrap(boolean v) {
		this.noWrap=v;
	}
	public boolean isNoWrap() {
		return this.noWrap;
	}

	public Dimension getSize() {
		return this.oSize;
	}

	public Dimension getRealSize() {
		if (oRealSize == null)
			return oSize;
		return this.oRealSize;
	}

	public Dimension getResponsiveSize() {
		if (oResponsiveSize == null)
			return this.oResponsiveSize = new Dimension(-1, -1);
		return this.oResponsiveSize;
	}
	
	public Dimension getResponsiveMinSize() {
		if (oResponsiveMinSize == null)
			return this.oResponsiveMinSize = new Dimension(-1, -1);
		return this.oResponsiveMinSize;
	}


	public void setLeftMargin(int zMargin) {
		this.getPaddingLazyly().left = zMargin;
	}

	public void setRightMargin(int zMargin) {
		this.getPaddingLazyly().right = zMargin;
	}

	public void setTopMargin(int zMargin) {
		this.getPaddingLazyly().top = zMargin;
	}

	public void setBottomMargin(int zMargin) {
		this.getPaddingLazyly().bottom = zMargin;
	}

	public void setMargins(int zLeft, int zTop, int zRight, int zBottom) {
		Insets mrgns = this.getPaddingLazyly();
		mrgns.left = zLeft;
		mrgns.top = zTop;
		mrgns.right = zRight;
		mrgns.bottom = zBottom;
	}

	public boolean isForceFocus() {
		return forceFocus;
	}

	public void setForceFocus(boolean forceFocus) {
		this.forceFocus = forceFocus;
	}

	public void clearPadding() {
		this.oPadding = null;
	}

	public Insets getPadding() {
		return this.oPadding;
	}
	public void setBorderInsets(Insets oBorderInsets) {
		this.oBorderInsets = oBorderInsets;
	}
  public boolean isOnlyExpanded() {
		return onlyExpanded;
	}

	public void setOnlyExpanded(boolean onlyExpanded) {
		this.onlyExpanded = onlyExpanded;
	}
  public boolean isOnlyCollapsed() {
		return onlyCollapsed;
	}

	public void setOnlyCollapsed(boolean onlyCollapsed) {
		this.onlyCollapsed = onlyCollapsed;
	}
	public void setPadding(Insets oPadding) {
		this.oPadding = oPadding;
	}
	public void setMargin(Insets oMargin) {
		this.oMargin = oMargin;
	}
	public Insets getMargin() {
		return this.oMargin;
	}

	public Insets getBorder() {
		return this.oBorderInsets;
	}

	public JWebAction getOnBlur() {
		return onBlur;
	}

	public void setOnBlur(JWebAction onBlur) {
		this.onBlur = onBlur;
	}

	public String getDropZone() {
		return dropZone;
	}

	public JBaseWin getDropManager() {
		return dropManager;
	}

	public void addDropManager(String zone, JBaseWin win) throws Exception {
		dropZone = zone;
		dropManager = win;
	}

	public void resolveMargins() throws Exception {
		Element margin = this.getSkinChildNode("margin");
		if (margin == null)
			return;
		Insets marg = this.getMarginLazyly();
		if (marg.left < 1 && margin.hasAttribute("left")) {
			marg.left = Integer.parseInt(margin.getAttribute("left"));
		}
		if (marg.right < 1 && margin.hasAttribute("right")) {
			marg.right = Integer.parseInt(margin.getAttribute("right"));
		}
		if (marg.top < 1 && margin.hasAttribute("top")) {
			marg.top = Integer.parseInt(margin.getAttribute("top"));
		}
		if (marg.bottom < 1 && margin.hasAttribute("bottom")) {
			marg.bottom = Integer.parseInt(margin.getAttribute("bottom"));
		}
	}

	public void resolvePadding() throws Exception {
		Element padding = this.getSkinChildNode("padding-fixed");
		if (padding == null)
			return;
		Insets padd = this.getPaddingLazyly();
		if (padd.left < 1 && padding.hasAttribute("left")) {
			padd.left = Integer.parseInt(padding.getAttribute("left"));
		}
		if (padd.right < 1 && padding.hasAttribute("right")) {
			padd.right = Integer.parseInt(padding.getAttribute("right"));
		}
		if (padd.top < 1 && padding.hasAttribute("top")) {
			padd.top = Integer.parseInt(padding.getAttribute("top"));
		}
		if (padd.bottom < 1 && padding.hasAttribute("bottom")) {
			padd.bottom = Integer.parseInt(padding.getAttribute("bottom"));
		}
	}

	public Insets getParentBorderInsets() throws Exception {
		if (this.oParentBorderInsets == null) {
			if (this.oParent != null) {
				this.oParentBorderInsets = this.oParent.getBorderLazyly();
			} else if (this.isView()) {
				this.oParentBorderInsets = new Insets(0, 0, 0, 0);
			}
		}
		return this.oParentBorderInsets;
	}

	public void resolveBorders() throws Exception {
		// if (this.oBorderInsets!=null) {
		// return this.oBorderInsets;
		// }
		Element nodo = this.getSkinChildNode("border");
		if (nodo == null)
			return;
		int iGeneralThickness = 0;
		if (nodo.hasAttribute("thickness")) {
			iGeneralThickness = Integer.parseInt(nodo.getAttribute("thickness"));
		}
		int iLeft = iGeneralThickness, iTop = iGeneralThickness, iRight = iGeneralThickness, iBottom = iGeneralThickness;
		nodo = this.getSkinChildNode("border/border[@id='left']");
		if (nodo != null && nodo.hasAttribute("thickness")) {
			iLeft = Integer.parseInt(nodo.getAttribute("thickness"));
		}
		nodo = this.getSkinChildNode("border/border[@id='top']");
		if (nodo != null && nodo.hasAttribute("thickness")) {
			iTop = Integer.parseInt(nodo.getAttribute("thickness"));
		}
		nodo = this.getSkinChildNode("border/border[@id='right']");
		if (nodo != null && nodo.hasAttribute("thickness")) {
			iRight = Integer.parseInt(nodo.getAttribute("thickness"));
		}
		nodo = this.getSkinChildNode("border/border[@id='bottom']");
		if (nodo != null && nodo.hasAttribute("thickness")) {
			iBottom = Integer.parseInt(nodo.getAttribute("thickness"));
		}
		Insets border = this.getBorderLazyly();
		border.top = iTop;
		border.left = iLeft;
		border.bottom = iBottom;
		border.right = iRight;
	}

	public boolean hasToolTip() {
		if (this.getToolTip() == null)
			return false;
		if (this.getToolTip().trim().length() <= 0)
			return false;
		return true;
	}
	
	private String findHelp() throws Exception {
		try {
			if (this.hasHelp()) return this.getHelp();
			if (this.isEditComponent())
				return this.getForm().getWin().getHelpFor(this.getName().toLowerCase());
			return "";
		} catch (Exception e) {
			return "---";
		}
	}

	public void toHelpXML(JXMLContent zContent) throws Exception {

		zContent.startNode("help_"+this.getComponentType());
		zContent.setAttribute("name", this.getName());
		zContent.setAttribute("label", JLanguage.translate(this.getLabelLateral()));
		zContent.setAttribute("help", " "+JLanguage.translate(this.findHelp()));

		this.componentToHelpXML(zContent);
		
		zContent.endNode("help_"+this.getComponentType());
		

	}
	public void toXML(JXMLContent zContent) throws Exception {
		// long start = System.currentTimeMillis();
		this.validate();

		zContent.startNode(this.getComponentType());
		zContent.setAttribute("name", this.getName());
		if (this.getForm()!=null)
			zContent.setAttribute("form_name", this.getForm().getFormName());
		
		if (hasClassResponsive()) {
			String classResp = JTools.replace( this.getClassResponsive(), JFormPanelResponsive.CLASSDEFAULT, BizUsuario.retrieveSkinGenerator().getPanelDefault(), 0);
			zContent.setAttribute("class_responsive", classResp);
		}
		
		if (hasSizeResponsive())
			zContent.setAttribute("size_responsive", this.getSizeResponsive());
		if (hasRole())
			zContent.setAttribute("role", this.getRole());
		if (hasRoleMobile())
			zContent.setAttribute("role_mobile", this.getRoleMobile());
		if (isEnterIsTab())
			zContent.setAttribute("enteristab", this.isEnterIsTab());
		if (this.isAffix())
			zContent.setAttribute("affix", this.isAffix());
		zContent.setAttribute("skin_stereotype", this.getSkinStereotype());
		zContent.setAttribute("visible", this.isVisible());
		if (hasPlaceHolder())
			zContent.setAttributeEscape("placeholder", JLanguage.translate(this.getPlaceHolder()));
		if (isForceFocus())
			zContent.setAttribute("force_focus", this.isForceFocus());
		zContent.setAttribute("refreshForm", refreshForm);
		zContent.setAttribute("detect_changes", this.isDetectChanges());
		if (hasConnectControl()) {
			zContent.setAttribute("connect_control", this.getConnectControl());
			zContent.setAttribute("connect_control_operator", this.getConnectControlOperator());
			zContent.setAttribute("connect_control_datatype", this.getConnectControlDatatype());
			zContent.setAttribute("connect_control_field", this.getConnectControlField());
		}
		if (backgroundImage != null && !backgroundImage.equals("")) 
			zContent.setAttribute("backgroundimage", "/"+JWebActionFactory.getCurrentRequest().getURIok() + "/" +backgroundImage );
		if (backgroundRepeat != null && !backgroundRepeat.equals("")) 
			zContent.setAttribute("backgroundrepeat", backgroundRepeat);
		if (backgroundPosition != null && !backgroundPosition.equals("")) 
			zContent.setAttribute("backgroundposition", backgroundPosition);
		if (backgroundSize != null && !backgroundSize.equals("")) 
			zContent.setAttribute("backgroundsize", backgroundSize);

		if (border != null) 
			zContent.setAttribute("border", border);
		if (borderTop != null) 
			zContent.setAttribute("bordertop", borderTop);
		if (borderBottom != null) 
			zContent.setAttribute("borderbottom", borderBottom);

		if (displayType != null) 
			zContent.setAttribute("display", this.displayType);

		if (boxShadow != null) 
			zContent.setAttribute("boxshadow", this.boxShadow);

		if (borderRadius != null) {
			zContent.setAttribute("border-radius-tl", borderRadius.top);
			zContent.setAttribute("border-radius-tr", borderRadius.left);
			zContent.setAttribute("border-radius-bl", borderRadius.bottom);
			zContent.setAttribute("border-radius-br", borderRadius.right);
		}

		zContent.setAttribute("style_attr", "'null'");

		if (this.isTitled()) {
			String sTitle = ((JWebTitledComponent) this).getTitle();
			if (sTitle != null && sTitle.trim().length() > 0) {
				zContent.setAttributeNLS("title", sTitle);

			}
		}
		String sState = this.getState();
		if (sState != null && sState.trim().length() > 0) {
			zContent.setAttribute("state", sState);
		}

		// label
		if (this.isLabelHolder()) {
			String sLabel = ((JWebLabelHolder) this).getLabel();
			if (sLabel != null && sLabel.length() > 0) {
				zContent.setAttributeNLS("label", sLabel);
			}
		}
		if (this.isLabelRightHolder()) {
			String sLabelRight = ((JWebLabelRightHolder) this).getLabelRight();
			if (sLabelRight != null && sLabelRight.length() > 0) {
				zContent.setAttributeNLS("label_right", sLabelRight);
			}
		}
		if (this.isOptions()) {
			String sLabel = ((JWebOptions) this).getOptions();
			if (sLabel != null && sLabel.length() > 0) {
				zContent.setAttribute("options", sLabel);
			}
		}
		if (getMaxWidth()>0) {
			zContent.setAttribute("maxwidth", getMaxWidth());
		}
		if (getMaxHeight()>0) {
			zContent.setAttribute("maxheight", getMaxHeight());
		}
		String labelClass = this.skin().getClassLabel(this,this.getForm(),sLabelClass);
		String fieldClass = this.skin().getClassField(this,this.getForm(),sFieldClass);
		if (labelClass!=null)	zContent.setAttribute("label_class", labelClass);
		if (fieldClass!=null)	zContent.setAttribute("field_class", fieldClass);

		if (sLabelLateral != null) {
			zContent.setAttributeNLS("label_lateral", sLabelLateral);
		}

		if (sLabelLink != null) {
			String labelLinkClass = this.skin().getClassLinkLabel(this,this.getForm(),sLabelLinkClass);
			if (labelLinkClass!=null)	zContent.setAttribute("label_linkclass", labelLinkClass);
			zContent.setAttributeNLS("label_link", sLabelLink);
		}

		if (getMargin()!=null) {
			zContent.setAttribute("margin-top", getMargin().top);
			zContent.setAttribute("margin-bottom", getMargin().bottom);
			zContent.setAttribute("margin-left", getMargin().left);
			zContent.setAttribute("margin-right", getMargin().right);
			
		}
		if (getPadding()!=null) {
			zContent.setAttribute("padding-top", getPadding().top);
			zContent.setAttribute("padding-bottom", getPadding().bottom);
			zContent.setAttribute("padding-left", getPadding().left);
			zContent.setAttribute("padding-right", getPadding().right);
			
		}
		if (isOnlyExpanded()) {
			zContent.setAttribute("only-expanded", true);
		}
		if (isOnlyCollapsed()) {
			zContent.setAttribute("only-collapsed", true);
		}
		// tooltip
		if (this.hasToolTip()) {
			zContent.setAttributeNLS("tool_tip", JLanguage.translate(this.getToolTip()));
		}

		// overflow
		String sOverflowX = this.getOverflowX();
		if (sOverflowX != null) {
			zContent.setAttribute("overflow-x", sOverflowX);
		}

		String sOverflowY = this.getOverflowY();
		if (sOverflowY != null) {
			zContent.setAttribute("overflow-y", sOverflowY);
		}
		// color
		String cForeground = this.getForeground();
		if (cForeground != null) {
			zContent.setAttribute("foreground",  JTools.toHtmlColor(cForeground));
		}
		String cBackground = this.getBackground();
		if (cBackground != null) {
			zContent.setAttribute("background", JTools.toHtmlColor(cBackground) );
		}

		if (this.getFontStyle()!=0) {
			zContent.setAttribute("font_style", this.getFontStyle(this.getFontStyle()));
		}
		if (this.getFontWeight()!=0) {
			zContent.setAttribute("font_weight", this.getFontWeight(this.getFontWeight()));
		}
		if (this.getFontSize()!=0) {
			zContent.setAttribute("font_size", this.getFontSize());
		}

		if (this.isNoWrap()) {
			zContent.setAttribute("nowrap", true);
		}

		if (oRelativePosition != null) {
			zContent.setAttribute("relative", this.oRelativePosition);

		}

		if (this.isDataToggle()) {
			if (((JWebDataToggle) this).getDataToggle() != null && !((JWebDataToggle) this).getDataToggle().equals(""))
				zContent.setAttribute("data_toggle", ((JWebDataToggle) this).getDataToggle());
		}
		

		// location & size
		if (this.oLocation != null || this.oInsetsLocation != null) {
			this.locationToXML(zContent);
		}
		if (this.oResponsiveMinSize != null) {
			if (getResponsiveMinSize().width > 0)
				zContent.setAttribute("rwidth", getResponsiveMinSize().width);
			if (getResponsiveMinSize().height > 0)
				zContent.setAttribute("rheight", getResponsiveMinSize().height);
		}

		if (this.oResponsiveSize != null) {
			if (getResponsiveSize().width > 0)
				zContent.setAttribute("width", getResponsiveSize().width);
			if (getResponsiveSize().height > 0)
				zContent.setAttribute("height", getResponsiveSize().height);
		} else {
			if (this.oSize != null) {
				this.sizeToXML(zContent);
			}
		}
		this.scriptToXML(zContent);

		//
		// subclass responsibility generation
		//
		this.componentToXML(zContent);
		if (iLabelIcon != null) {
			zContent.startNode("labelicon");
			iLabelIcon.toXML(zContent);
			zContent.endNode("labelicon");
		}

		if (this.getDropManager() != null) {
			zContent.startNode("dragdropinfo");
			this.addDrop(zContent, this instanceof JWebActionOwnerProvider ? (JWebActionOwnerProvider) this : getObjectProvider(), this.getDropZone(), this.getDropManager());
			zContent.endNode("dragdropinfo");
		}

		// alert
		String sAlert = this.getAlert();
		if (sAlert != null) {
			zContent.startNode("alert");
			zContent.setAttributeNLS("msg", JTools.replaceForeignCharsForWeb(sAlert));
			zContent.endNode("alert");
		}

		// margins
		if (this.oPadding != null && !this.isComposite()) { // composites apply
			// margins with
			// layouts
			zContent.startNode("padding");
			if (this.oPadding.left >= 0) {
				zContent.setAttribute("left", this.oPadding.left);
			}
			if (this.oPadding.right >= 0) {
				zContent.setAttribute("right", this.oPadding.right);
			}
			if (this.oPadding.top >= 0) {
				zContent.setAttribute("top", this.oPadding.top);
			}
			if (this.oPadding.bottom >= 0) {
				zContent.setAttribute("bottom", this.oPadding.bottom);
			}
			zContent.endNode("padding");
		}

		// action
		if (this.isActionable()) {
			JWebAction oAction = ((JWebActionable) this).getWebAction();
			if (oAction != null) {
				oAction.toXML(zContent);
			}
		}

		// icon
		if (this.isIconHolder()) {
			JWebIcon oIcon = ((JWebIconHolder) this).getIcon();
			if (oIcon != null) {
				oIcon.toXML(zContent);
				JWebIcon oMaxIcon = ((JWebIconHolder) this).getMaximizeIcon();
				if (oMaxIcon != null) {
					zContent.startNode("maximized");
					oMaxIcon.toXML(zContent);
					zContent.endNode("maximized");
				}
			
			} else {
//				Node oIconNode = this.getSkinChildNode("foreground/icon");
//				if (oIconNode != null) {
//					zContent.embed(oIconNode);
//				}
			}
		}

		if (this.isArrow()) {
			JWebIcon oArrow = ((JWebArrow) this).getArrow();
			zContent.startNode("arrow");
			if (oArrow != null) {
				oArrow.toXML(zContent);
			} else {
				Node oIconNode = this.getSkinChildNode("foreground/icon");
				if (oIconNode != null) {
					zContent.embed(oIconNode);
				}
			}
			zContent.endNode("arrow");
		}

		if (onBlur != null) {
			zContent.startNode("onblur");
			onBlur.toXML(zContent);
			zContent.endNode("onblur");

		}
		// content layout
		if (this.isContentHolder()) {
			JWebContentHolder oContentHolder = (JWebContentHolder) this;
			Element oContentLayoutNode = this.getSkinChildNode("foreground/content_layout");
			int hAl = oContentHolder.getHAlignment();
			int vAl = oContentHolder.getVAlignment();
			zContent.startNode("content_layout");
			if (hAl != JFormControl.ALIGN_DEFAULT) {
				zContent.setAttribute("halignment", this.getHAlignmentString(hAl));
			} else {
				this.passAttribute("halignment", oContentLayoutNode, zContent);
			}
			if (vAl != JFormControl.ALIGN_DEFAULT) {
				zContent.setAttribute("valignment", this.getVAlignmentString(vAl));
			} else {
				this.passAttribute("valignment", oContentLayoutNode, zContent);
			}
			if (this.isCompoundLabelHolder()) {
				JWebCompoundLabelHolder oCompoundLabelHolder = (JWebCompoundLabelHolder) this;
				int iTextPos = oCompoundLabelHolder.getTextPosition();
				int iTextIconGap = oCompoundLabelHolder.getTextIconGap();
				String style = oCompoundLabelHolder.getStyleImage();
				if (style!=null) {
					zContent.setAttribute("image_style", style);
				}
				if (iTextPos != TEXT_POSITION_DEFAULT) {
					zContent.setAttribute("text_position", this.getTextPositionString(vAl));
				} else {
					this.passAttribute("text_position", oContentLayoutNode, zContent);
				}
				if (iTextIconGap != TEXT_ICON_GAP_DEFAULT) {
					zContent.setAttribute("text_icon_gap", iTextIconGap);
				} else {
					this.passAttribute("text_icon_gap", oContentLayoutNode, zContent);
				}
			}
			zContent.endNode("content_layout");
		}

		zContent.endNode(this.getComponentType());

		// JDebugPrint.logDebug("toXML: " + this.getName() + "-- " +
		// this.getSkinStereotype()+" -- "+ (System.currentTimeMillis() -
		// start));

	}

	public void destroy() {
		this.sName = null;
		this.sClassResponsive = null;
		this.sSizeResponsive = null;
		this.sRole = null;
		this.oLocation = null;
		this.oSize = null;
		this.sSkinStereotype = null;
		this.oPadding = null;
		this.sToolTip = null;
		if (this.oParent != null) {
			this.oParent.removeChild(this.getName());
			this.oParent = null;
		}
	}

	protected void locationToXML(JXMLContent zContent) throws Exception {
		if (isInTable())
			return;
		if (this.position != null)
			zContent.setAttribute("position", position);

		if (this.oInsetsLocation != null) {
			if (this.oInsetsLocation.left >= 0) {
				zContent.setAttribute("l", this.oInsetsLocation.left);
			}
			if (this.oInsetsLocation.top >= 0) {
				zContent.setAttribute("t", this.oInsetsLocation.top);
			}
			if (this.oInsetsLocation.right >= 0) {
				zContent.setAttribute("r", this.oInsetsLocation.right);
			}
			if (this.oInsetsLocation.bottom >= 0) {
				zContent.setAttribute("b", this.oInsetsLocation.bottom);
			}
			return;
		}

		if (this.oLocation.x >= 0) {
			zContent.setAttribute("x", this.oLocation.x);
		}
		if (this.oLocation.y >= 0) {
			zContent.setAttribute("y", this.oLocation.y);
		}
		if (this.zIndex >= 0) {
			zContent.setAttribute("z", this.zIndex);
		}

	}

	protected void generateOnCalculate(JXMLContent zContent) throws Exception {
		JScript script=getScript();
		if (script!=null) 
			this.generateScript(zContent, script);
	}
//	private boolean generatedScript=false;
	protected void generateScript(JXMLContent zContent, JScript script) throws Exception {
		if (script.isPure()) {
			zContent.setAttribute("onCalculate", script.getScript());
			zContent.setAttribute("isCalculeOthersFields", true);
			return;
		}
//		if (generatedScript) return;
//		generatedScript=true;
		String onCalculate="";
		String formatLinea = "";
		JWebEditComponentContainer form = this.getForm();
		LinkedHashMap<String, String> map= new LinkedHashMap<String, String>();
		if (script.getBind()==null) {
			zContent.setAttribute("onCalculate", script.getScript());
			zContent.setAttribute("isCalculeOthersFields", true);
			if (script.isCalculeOnStart() || script.isCalculeOnAnyChange()) {
				zContent.setAttribute("onAnyChange", script.getScript());
			}
			return;
		}
		JIterator<String> i = script.getBind().getKeyIterator();
		while (i.hasMoreElements()) {
			String key = i.nextElement();
			String value = (String)script.getBind().getElement(key);
			map.put("$"+key, "document.getElementById('dgf_"+form.getFormName()+"_fd-"+value+"')" );
			map.put("$('#"+key+"')", "$('#dgf_"+form.getFormName()+"_fd-"+value+"')" );
			if (form.getFormName().indexOf("filter_pane")!=-1) {
				map.put(key, "document.getElementById('dgf_"+form.getFormName()+"_fd-"+value+"').value" );
				
			} else {
				map.put(key+"=", "document.getElementById('dgf_"+form.getFormName()+"_fd-"+value+"').value=" );
				map.put(key, "resolve('dgf_"+form.getFormName()+"_fd-"+value+"')");
			}

			formatLinea+="formatear('dgf_"+form.getFormName()+"_fd-"+value+"');";
		}
		if (!script.isCalculeOthersFields()) {
			map.put("$this", "document.getElementById('dgf_"+form.getFormName()+"_fd-"+this.getName()+"')" );
			map.put("this", "document.getElementById('dgf_"+form.getFormName()+"_fd-"+this.getName()+"').value" );
		}
		if (script.isCalculeOthersFields()) {
			onCalculate=script.getFormulaInContext(map)+";"+formatLinea;
			zContent.setAttribute("onCalculate", onCalculate);
			zContent.setAttribute("isCalculeOthersFields", script.isCalculeOthersFields());
		} else {
			onCalculate="document.getElementById('dgf_"+form.getFormName()+"_fd-"+this.getName()+"').value="+script.getFormulaInContext(map)+";";
		}
		if (script.isCalculeOnStart() || script.isCalculeOnAnyChange()) {
			zContent.setAttribute("onAnyChange", onCalculate);
			if (script.isCalculeOnStart()) zContent.setAttribute("isCalculeOnStart", script.isCalculeOnStart());
			if (script.isCalculeOnAnyChange()) zContent.setAttribute("isCalculeOnAnyChange", script.isCalculeOnAnyChange());
			zContent.setAttribute("orden", script.getOrden());
		}	

	}
	
	protected void scriptToXML(JXMLContent zContent) throws Exception {
		this.generateOnCalculate(zContent);
	}	
	protected void sizeToXML(JXMLContent zContent) throws Exception {
		oRealSize = new Dimension(-1, -1);
		if (this.getSize().width >= 0)
			oRealSize.width = this.getSize().width;

		if (this.getSize().height > 0)
			oRealSize.height = this.getSize().height;

		if (oRealSize.width != -1)
			zContent.setAttribute("width", oRealSize.width);
		if (oRealSize.height != -1)
			zContent.setAttribute("height", oRealSize.height);
	}

	protected void passAttribute(String zAttrName, Element zFrom, JXMLContent zTo) throws Exception {
		if (zFrom != null && zFrom.hasAttribute(zAttrName)) {
			zTo.setAttribute(zAttrName, zFrom.getAttribute(zAttrName));
		}
	}


	protected Element getSkinNode() throws Exception {
		return (Element) BizUsuario.retrieveSkin().getStyle(this.getRealSkinStereotype());
	}

	protected Element getSkinChildNode(String zThisRelativeXPath) throws Exception {
		return (Element) BizUsuario.retrieveSkin().getStyleChild(this.getRealSkinStereotype(), zThisRelativeXPath);
	}

	public JWebView getView() {
		if (this.isView()) {
			return (JWebView) this;
		}
		JWebViewComposite oParent = this.getParent();
		while (oParent != null) {
			if (oParent.isView()) {
				return (JWebView) oParent;
			} else {
				oParent = oParent.getParent();
			}
		}
		throw new RuntimeException("Component has no parent page: " + this.getDescription());
	}

	public JWebViewComposite getParent() {
		return this.oParent;
	}

	public String getName() {
		return this.sName;
	}

	public String getClassResponsive() {
		return this.sClassResponsive;
	}

	public String getSizeResponsive() throws Exception {
		if (getForm()!=null && getForm().isInLine()) 
			return "inline_component";
		return this.sSizeResponsive;
	}

	public String getRole() {
		return this.sRole;
	}

	public String getRoleMobile() {
		return this.sRoleMobile;
	}
	public boolean hasRawClassResponsive() {
		String name = sClassResponsive;
		if (name == null)
			return false;
		if (name.equals(""))
			return false;
		return true;
	}

	public boolean hasClassResponsive() {
		String name = this.getClassResponsive();
		if (name == null)
			return false;
		if (name.equals(""))
			return false;
		return true;
	}

	public boolean hasSizeResponsive()  throws Exception {
		String name = this.getSizeResponsive();
		if (name == null)
			return false;
		if (name.equals(""))
			return false;
		return true;
	}

	public boolean hasRole() {
		String name = this.getRole();
		if (name == null)
			return false;
		if (name.equals(""))
			return false;
		return true;
	}
	public boolean hasRoleMobile() {
		String name = this.getRoleMobile();
		if (name == null)
			return false;
		if (name.equals(""))
			return false;
		return true;
	}
	public boolean hasName() {
		String name = this.getName();
		if (name == null)
			return false;
		if (name.equals(""))
			return false;
		return true;
	}

	public String getObjectName() {
		if (hasName()) return getName();
		return "comp-" + this.hashCode();
	}

	public String getDescription() {
		return this.getComponentType() + "[" + this.getName() + "]";
	}

	@Override
	public String toString() {
		return this.getDescription();
	}

	public String getSkinStereotype() {
		if (this.sSkinStereotype == null) {
			return getComponentType();
		}
		return this.sSkinStereotype;
	}

	public String getRealSkinStereotype() {
		return getSkinStereotype();
	}

	public void setSkinStereotype(String string) {
		this.sSkinStereotype = string;
		// this.markParentToLayout();
	}

	public JWebView getRoot() {
		if (this.oParent != null) {
			return this.oParent.getRoot();
		} else if (this.isView()) {
			return (JWebView) this;
		} else {
			return null;
		}
	}

	public boolean isVisible() {
		if (!this.bVisible)
			return false;
		if (oParent == null)
			return true;
		return oParent.isVisible();
	}

	public void setVisible(boolean b) {
		this.bVisible = b;
		// this.markParentToLayout();
	}
	public void setFieldClass(String b) {
		this.sFieldClass = b;
		// this.markParentToLayout();
	}
	public void setLabelClass(String b) {
		this.sLabelClass = b;
		// this.markParentToLayout();
	}
	public void setLabelLateral(String b) {
		this.sLabelLateral = b;
		// this.markParentToLayout();
	}
	public void setLabelLink(String b) {
		this.sLabelLink = b;
		// this.markParentToLayout();
	}
	public String getLabelLateral() {
		return this.sLabelLateral;
	}

	public boolean hasLabel() {
		if (this.sLabelLateral!=null && this.sLabelLateral.length()>0) return true;
			if (this.iLabelIcon!=null) return true;
		return false;
	}
	public String getLabelLink() {
		return this.sLabelLink;
	}
// public void markParentToLayout() {
	// if (this.oParent!=null) {
	// this.oParent.markToLayout();
	// }
	//// if (this.isComposite()) {
	//// ((JWebViewComposite) this).markToLayout();
	//// }
	// }

	public String getToolTip() {
		return this.sToolTip;
	}

	public void setToolTip(String string) {
		this.sToolTip = string;
	}

	
	public String getHelp() {
		return this.sHelp;
	}
	
	public boolean hasHelp() {
		return this.sHelp!=null && !this.sHelp.equals("");
	}


	public void setHelp(String string) {
		this.sHelp = string;
	}

	public boolean isView() {
		return false;
	}

	// public boolean isForm() {
	// return false;
	// }

	public boolean isAbsolutelyNamed() {
		return this instanceof JAbsolutelyNamedWebViewComponent;
	}

	public boolean isComposite() {
		return false;
	}

	public boolean isEditComponent() {
		return false;
	}

	public boolean isBreak() {
		return false;
	}

	public boolean isActionHolder() {
		return this instanceof JWebActionHolder;
	}

	public boolean isActionable() {
		return this instanceof JWebActionable;
	}

	public boolean isContentHolder() {
		return this instanceof JWebContentHolder;
	}

	public boolean isLabelHolder() {
		return this instanceof JWebLabelHolder;
	}
	public boolean isLabelRightHolder() {
		return this instanceof JWebLabelRightHolder;
	}

	public boolean isOptions() {
		return this instanceof JWebOptions;
	}

	public boolean isIconHolder() {
		return this instanceof JWebIconHolder;
	}

	public boolean isArrow() {
		return this instanceof JWebArrow;
	}

	public boolean isDataToggle() {
		return this instanceof JWebDataToggle;
	}

	public boolean isCompoundLabelHolder() {
		return this instanceof JWebCompoundLabelHolder;
	}

	public boolean isNotebook() {
		return false;
	}

	public boolean isNotebookTab() {
		return false;
	}

	public boolean isTitled() {
		return this instanceof JWebTitledComponent;
	}

	public Dimension calculateSize() throws Exception {
		Element oSkinNode = this.getSkinNode();
		boolean wOK = false, hOK = false;
		Dimension oDim = new Dimension(-1, -1);
		if (oSkinNode != null) {
			if (oSkinNode.hasAttribute("width")) {
				oDim.width = Integer.parseInt(oSkinNode.getAttribute("width"));
				wOK = true;
			}
			if (oSkinNode.hasAttribute("height")) {
				oDim.height = Integer.parseInt(oSkinNode.getAttribute("height"));
				hOK = true;
			}
		}
		if (wOK && hOK) {
			return oDim;
		} else {
			Dimension oDefault = this.getDefaultSize();
			if (oDefault != null) {
				if (!wOK) {
					oDim.width = oDefault.width;
				}
				if (!hOK) {
					oDim.height = oDefault.height;
				}
			}
		}

		return oDim;
	}

	// ////////////////////////////////////////////////////////////////////////////
	//
	// package INTERNAL API
	//
	// ////////////////////////////////////////////////////////////////////////////

	Point getLocationLazyly() {
		if (this.oLocation == null) {
			this.oLocation = new Point(-1, -1);
		}
		return this.oLocation;
	}

	Dimension getSizeLazyly() {
		if (this.oSize == null) {
			this.oSize = new Dimension(-1, -1);
		}
		return this.oSize;
	}

	public void setSize(Dimension d) {
		this.oSize = d;
	}

	Insets getPaddingLazyly() {
		if (this.oPadding == null) {
			this.oPadding = new Insets(0, 0, 0, 0);
		}
		return this.oPadding;
	}

	Insets getMarginLazyly() {
		if (this.oMargin == null) {
			this.oMargin = new Insets(0, 0, 0, 0);
		}
		return this.oMargin;
	}

	Insets getBorderLazyly() {
		if (this.oBorderInsets == null) {
			this.oBorderInsets = new Insets(0, 0, 0, 0);
		}
		return this.oBorderInsets;
	}

	public void setParent(JWebViewComposite zParent) {
		this.oParent = zParent;
	}

	public void setName(String zName) {
		if (this.isReservedName(zName)) {
			throw new RuntimeException("Component name cannot be used because it is a reserved name: '" + zName + "'");
		}
		this.sName = zName;
	}

	public JWebViewComponent setClassResponsive(String zClass) {
		this.sClassResponsive = zClass;
		return this;
	}

	public JWebViewComponent setSizeResponsive(String zSize) {
		this.sSizeResponsive = zSize;
		return this;
	}

	public JWebViewComponent setRole(String zRole) {
		this.sRole = zRole;
		return this;
	}

	public JWebViewComponent setRoleMobile(String zRole) {
		this.sRoleMobile = zRole;
		return this;
	}

	boolean isReservedName(String zName) {
		if (zName == null)
			return false;
		return zName.equalsIgnoreCase("navform") || zName.equalsIgnoreCase("mainform");
	}

	String getHAlignmentString(int hAl) {
		switch (hAl) {
		case JFormControl.HALIGN_LEFT:
			return "left";
		case JFormControl.HALIGN_CENTER:
			return "center";
		case JFormControl.HALIGN_RIGHT:
			return "right";
		default:
			return "left";
		}
	}

	String getVAlignmentString(int vAl) {
		switch (vAl) {
		case JFormControl.VALIGN_TOP:
			return "top";
		case JFormControl.VALIGN_MIDDLE:
			return "middle";
		case JFormControl.VALIGN_BOTTOM:
			return "bottom";
		default:
			return "top";
		}
	}

	String getTextPositionString(int zTextPos) {
		switch (zTextPos) {
		case TEXT_POSITION_LEFT:
			return "left";
		case TEXT_POSITION_RIGHT:
			return "right";
		case TEXT_POSITION_TOP:
			return "top";
		case TEXT_POSITION_BOTTOM:
			return "bottom";
		default:
			return "left";
		}
	}

	String getFontStyle(int s) {
		switch (s) {
		case JFormControl.FONT_STYLE_NORMAL:
			return "normal";
		case JFormControl.FONT_STYLE_ITALIC:
			return "italic";
		default:
			return "normal";
		}
	}
	
	String getFontWeight(int s) {
		switch (s) {
		case JFormControl.FONT_WEIGHT_NORMAL:
			return "normal";
		case JFormControl.FONT_WEIGHT_BOLD:
			return "bold";
		default:
			return "normal";
		}
	}

	// ////////////////////////////////////////////////////////////////////////////
	//
	// METHODS TO OVERRIDE IN SUBCLASSES
	//
	// ////////////////////////////////////////////////////////////////////////////
	/**
	 * A component's state can be used as a selector for the skin.
	 */
	protected String getState() throws Exception {
		return null;
	}

	public abstract String getComponentType();

	protected void componentPreLayout(JXMLContent zContent) throws Exception {
	};

	protected void componentToXMLposLayout(JXMLContent zContent) throws Exception {
	}

	protected abstract void componentToXML(JXMLContent zContent) throws Exception;
	protected void componentToHelpXML(JXMLContent zContent) throws Exception {
		
	}
	protected void actionsToHelpXML(String title,JBaseWin win,JXMLContent zContent) throws Exception {
		boolean firstAction=true;

		JIterator<BizAction> iter = win.getActionMap().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizAction action = iter.nextElement();
//			if (!action.isAccessToDetail() && !action.ifToolBar()) continue;
//			if ((win instanceof JWins) && action.isOnlyInForm()) continue;
//			if (!win.verifyAction(action, true, false)) continue;
			boolean active = action.isOkAll();
			String reason =  win.whyNotOkAction(action);
			if (!active && reason==null) continue;
			if (firstAction) {
				zContent.startNode("subtitle");
				zContent.setAttribute("title", title);
				zContent.endNode("subtitle");
				firstAction=false;
			}
			String help = win.getHelpFor(action);
			if (help==null) help=action.getHelp();
			zContent.startNode("action");
			zContent.setAttribute("name", JLanguage.translate(action.GetDescr()));
			zContent.setAttribute("help",JLanguage.translate(help));
			zContent.setAttribute("active", active );
			if (reason!=null)
			 zContent.setAttribute("reason", JLanguage.translate(win.whyNotOkAction(action)));
			zContent.endNode("action");
		
		
		}
	}
	protected abstract Dimension getDefaultSize() throws Exception;

	public void validate() throws Exception {
		// do nothing by default
	}

	public void takeAttributesFormControlResponsive(JFormControlResponsive comp) throws Exception {
		this.setVisible(comp.isVisible()&&!comp.isHide());
		this.setLabelLateral(comp.getLabel());
		this.setLabelIcon(comp.getLabelIcon());
		this.setResponsive(comp.isResponsive());
		this.setClassResponsive(comp.getResponsiveClass());
		this.setSizeResponsive(comp.getColumnClass());
		this.setEnterIsTab(comp.isEnteristab());
		this.setName(comp.getName());
		this.getResponsiveSize().width=(comp.getWidth());
		this.getResponsiveSize().height=(comp.getHeight());
		this.getResponsiveMinSize().width=(int)(comp.getMinWidth());
		this.getResponsiveMinSize().height=(int)(comp.getMinHeight());
		this.setMaxWidth(comp.getMaxWidth());
		this.setMaxHeight(comp.getMaxHeight());
		this.setFontStyle(comp.getFontStyle());
		this.setFontWeight(comp.getFontWeigth());
		this.setFontSize(comp.getFontSize());
		this.setNoWrap(comp.isNoWrap());
		this.setOverflowX(comp.getOverflowX());
		this.setOverflowY(comp.getOverflowY());
		this.setHAlignment(comp.getHAlignment());
		this.setVAlignment(comp.getVAlignment());
		this.setBackgroundImage(comp.getBackgroundImage());
		this.setBackgroundRepeat(comp.getBackgroundRepeat());
		this.setBackgroundSize(comp.getBackgroundSize());
		this.setBorder(comp.getBorder());
		this.setBorderRadius(comp.getBorderRadius());
		this.setBorderTop(comp.getBorderTop());
		this.setBorderBottom(comp.getBorderBottom());
		this.setDisplayType(comp.getDisplayType());
		this.setBoxShadow(comp.getBoxShadow());

		this.setFormatFields(comp.getFormatFields());
		this.setSizeLabels(comp.getSizeLabels());
		this.setScript(comp.getScript());
		this.setGenerateInternalEvents(comp.isGenerateInternalEvents());
		this.setMargin(comp.getMargin());
		this.setPadding(comp.getPadding());
		this.setOnlyExpanded(comp.isOnlyExpanded());
		this.setOnlyCollapsed(comp.isOnlyCollapsed());
		
		takeAttributesFormControl(comp);
	}

	public void takeAttributesFormControl(JFormControl comp) throws Exception {
//		if (comp.getToolTip() != null) {
//			this.setToolTip(comp.getToolTip());
//		}
		this.fullWidth = comp.isKeepWidth();
		this.fullHeight = comp.isKeepHeight();
		this.setPlaceHolder(/*comp.getPlaceHolder()==null?comp.GetDisplayName():*/comp.getPlaceHolder());
		this.setLabelClass(comp.getLabelClass());
		this.setFieldClass(comp.getFieldClass());
		this.setRefreshForm(comp.hasToRefreshForm());
		this.setDetectChanges(comp.isDetectChanges());
		this.setSkinStereotype(comp.getSkinStereotype());
		this.setConnectControl(comp.getConnectControl());
		this.setConnectControlOperator(comp.getConnectControlOperator());
		this.setConnectControlDatatype(comp.getConnectControlDatatype());
		this.setConnectControlField(comp.getConnectControlField());
		this.setToolTip(comp.findToolTip());
		this.setHelp(comp.getHelp());
		this.setFilterNeverHide(comp.isFilterNeverHide());

		if (comp.isInTable()) {
			this.setInTable(comp.getTableName(),comp.getInTableRow(),comp.getInTableCol());
		}
		if (comp.getForeground() != null) {
			this.setForeground(comp.getForeground());
		}
		if (comp.getBackground() != null) {
			this.setBackground(comp.getBackground());
		}

		if (comp.getDropManager() != null) {
			this.addDropManager(comp.getDropZone(), comp.getDropManager());
		}
		this.setControl(comp);
		// this.setVisible(comp.isVisible());
	}

//	public JWebCompositeLayout createWebLayout(LayoutManager layout) throws Exception {
//		if (layout == null)
//			return null;
//		if (layout instanceof FlowLayout)
//			return new JWebFlowLayout();
//		if (layout instanceof VerticalFlowLayout)
//			return new JWebFlowLayout();
//		if (layout instanceof GridLayout)
//			return new JWebGridLayout();
//		if (layout instanceof BorderLayout)
//			return new JWebBorderLayout();
//		// agregar stretchlayout
//		return null;
//	}


	public boolean hasParent() throws Exception {
		return this.getParent() != null;
	}

//	public boolean isParentBaseForm(Component comp) throws Exception {
//		if (comp == null)
//			return false;
//		if (comp.getParent() == null)
//			return false;
//		return (comp.getParent() instanceof JBaseForm);
//	}


	public void takeAttributesForm(JWebViewComposite webparent, Component comp) throws Exception {
		int offsetX = 0, offsetY = 0;

//		this.assignMyLayout(comp);
//
//		if (webparent != null)
//			webparent.pushIntoMyLayout(this, comp);

		this.setMinSize(comp.getSize().width, comp.getSize().height);
		this.setLocation(comp.getX() + offsetX, comp.getY() + offsetY);
		this.setVisible(this.isVisible(comp));

//		if (this.isParentBaseForm(comp)) { // este ajuste, es para que siga
//											// respetando la distancia contra
//											// los margenes
//			this.offsetRight = comp.getParent().getWidth() - (comp.getLocation().x + comp.getSize().width);
//			this.offsetBottom = comp.getParent().getHeight() - (comp.getLocation().y + comp.getSize().height);
//		}

//		if (comp instanceof JPssPanel) {
//			String image = ((JPssPanel) comp).getBackgroundImageFile();
//			backgroundImage = image;
//			backgroundRepeat = "no-repeat;";
//		}

	}

	protected boolean isVisible(Component comp) {
		if (comp == null)
			return true;
		if (!comp.isVisible())
			return false;
		return this.isVisible(comp.getParent());
	}

	public String getRelativePosition() {
		return oRelativePosition;
	}

	public void setRelativePosition(String relativePosition) {
		oRelativePosition = relativePosition;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getForeground() {
		return oForeground;
	}
	public void setForeground(Color foreground) {
		oForeground = JTools.ColorToString(foreground);
	}

	public void setForeground(String foreground) {
		oForeground = foreground;
	}

	public String getBackground() {
		return oBackground;
	}

	public void setBackground(String background) {
		oBackground = background;
	}
	public void setBackground(Color background) {
		oBackground = JTools.ColorToString( background);
	}
	public void setBackgroundImage(String background) {
		backgroundImage = background;
	}
	public void setBackgroundRepeat(String v) {
		backgroundRepeat = v;
	}
	
	public void setBackgroundPosition(String background) {
		backgroundPosition = background;
	}

	public void setBackgroundSize(String background) {
		backgroundSize = background;
	}

	private String border;
	public void setBorder(String v) {
		this.border = v;
	}

	private Insets borderRadius;
	public void setBorderRadius(Insets v) {
		this.borderRadius = v;
	}


	private String borderTop;
	public void setBorderTop(String v) {
		this.borderTop = v;
	}

	private String borderBottom;
	public void setBorderBottom(String v) {
		this.borderBottom = v;
	}

	private String displayType;
	public void setDisplayType(String v) {
		this.displayType = v;
	}

	private String boxShadow;
	public void setBoxShadow(String v) {
		this.boxShadow = v;
	}

	public Insets getInsetsLocation() {
		return oInsetsLocation;
	}

	public void setInsetsLocation(Insets insetsLocation) {
		oInsetsLocation = insetsLocation;
	}

	public String getOverflowX() {
		return overflowX;
	}

	public void setOverflowX(String overflow) {
		this.overflowX = overflow;
	}

	public String getOverflowY() {
		return overflowY;
	}

	public void setOverflowY(String overflow) {
		this.overflowY = overflow;
	}
	
  public int getHAlignment() {
    return this.hAlign;
  }

  public int getVAlignment() {
    return this.vAlign;
  }

  public void setHAlignment(int i) {
    this.hAlign = i;
  }

  public void setVAlignment(int i) {
    this.vAlign = i;
  }


	public boolean hasToRefreshForm() {
		return refreshForm;
	}

	public void setRefreshForm(boolean refreshForm) {
		this.refreshForm = refreshForm;
	}

	public JWebEditComponentContainer getForm()  {
		JWebViewComposite oParent = this.getParent();
		while (oParent != null) {
			if (oParent instanceof JWebEditComponentContainer) {
				return (JWebEditComponentContainer) oParent;
			} else {
				oParent = oParent.getParent();
			}
		}
		return null;
		// throw new Exception("Edit component has no parent form:
		// "+this.getDescription());
	}
	public JWebActionOwnerProvider getObjectProviderNoException() {
		JWebViewComposite oParent = this.getParent();
		while (oParent != null) {
			if (oParent instanceof JWebActionOwnerProvider && !(oParent instanceof JWebRowGridExpandResponsive)) {
				return (JWebActionOwnerProvider) oParent;
			} else {
				oParent = oParent.getParent();
			}
		}
		return null;
	}
	
	
	public JWebActionOwnerProvider getObjectProvider() {
		JWebViewComposite oParent = this.getParent();
		while (oParent != null) {
			if (!(oParent instanceof JWebLocalFormResponsive) && (oParent instanceof JWebActionOwnerProvider) && !(oParent instanceof JWebRowGridExpandResponsive) && !(oParent instanceof JWebDropDownWinLovResponsive) && !(oParent instanceof JWebCardResponsive)) {
				return (JWebActionOwnerProvider) oParent;
			} else {
				oParent = oParent.getParent();
			}
		}
		throw new RuntimeException("Edit component has no ojbect provider: " + this.getDescription());
	}
	
	public JWebActionOwnerProvider getObjectProviderWithDropDown() {
		JWebViewComposite oParent = this.getParent();
		while (oParent != null) {
			if (!(oParent instanceof JWebLocalFormResponsive) && (oParent instanceof JWebActionOwnerProvider) && !(oParent instanceof JWebRowGridExpandResponsive) && !(oParent instanceof JWebCardResponsive)) {
				return (JWebActionOwnerProvider) oParent;
			} else {
				oParent = oParent.getParent();
			}
		}
		throw new RuntimeException("Edit component has no ojbect provider: " + this.getDescription());
	}
	
	
	public JWebActionOwnerProvider getObjectProviderWithCard() {
		JWebViewComposite oParent = this.getParent();
		while (oParent != null) {
			if (!(oParent instanceof JWebLocalFormResponsive) && oParent instanceof JWebActionOwnerProvider && !(oParent instanceof JWebRowGridExpandResponsive) ) {
				return (JWebActionOwnerProvider) oParent;
			} else {
				oParent = oParent.getParent();
			}
		}
		throw new RuntimeException("Edit component has no ojbect provider: " + this.getDescription());
	}

	public JWebActionOwnerProvider findActionOwnerProvider(String find) throws Exception {
		IWebWinForm form=this.getParent().findJWebWinForm();
		if (form==null) return null;
		JWebViewComponent comp = form.findComponentByControl(find);
		if (comp==null) return null;
		if (comp instanceof JWebActionOwnerProvider) return (JWebActionOwnerProvider) comp;
		if (comp instanceof JWebTableResponsive) {
			return ((JWebTableResponsive)comp).getLista();
		}
		return null;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	private boolean fixHight = false;

	public void setFixHight(int value) {
		this.setHeight(value);
		this.fixHight = true;
	}

	private boolean fixWidth = false;

	public void setFixWidth(int value) {
		this.setWidth(value);
		this.fixWidth = true;
	}

	public boolean isFixWidth() {
		return this.fixWidth;
	}

	public boolean isFixHight() {
		return this.fixHight;
	}

	public JWebHistoryManager getHistoryManager() throws Exception {
		return JWebActionFactory.getCurrentRequest().getSession().getHistoryManager();
	}

	public void addDrop(JXMLContent zContent, JWebActionOwnerProvider zObjectProvider, String zone, JBaseWin win) throws Exception {
		String idListenerDrop = null;
		JBaseWin winDrag = win.getObjectDrageable(zone);
		if (winDrag != null) {
			zContent.setAttribute("drag", zContent.addObject(winDrag));
			zContent.setAttribute("dragclass", winDrag.getClass().getSimpleName());
		}

		String value = win.getDragForeground(zone) != null ? win.getDragForeground(zone) : null;
		if (value != null)
			zContent.setAttribute("foreground", JTools.toHtmlColor(value) );
		value = win.getDragBackground(zone) != null ? win.getDragBackground(zone) : null;
		if (value != null)
			zContent.setAttribute("background", JTools.toHtmlColor(value) );

		if (win.acceptDrop(zone)) {
			String zoneDrop = win.getZoneDrop(zone);
			JBaseWin listener = win.getListenerForDragAndDropWeb(zone, win.getParent());

			idListenerDrop = zContent.addObject(listener);

			JWebAction actDrag = JWebActionFactory.createDropAction(zObjectProvider, zoneDrop, idListenerDrop);
			if (actDrag != null) {
				zContent.startNode("drop");
				String accepts = win.getDropClassAccepted();
				if (accepts != null)
					zContent.setAttribute("dropclassaccept", accepts);
				actDrag.toXML(zContent);
				zContent.endNode("drop");
			}
		}
		JBaseWin listener = win.getDblClickObjectDrag(zone, win.getParent());
		if (listener == null)
			return;
		int action = win.getDblClickDragAction(zone, win.getParent());
		if (action == -1)
			return;
		BizAction dblClick = listener.getActionMap().findAction(action);
		if (dblClick == null)
			return;
		String idOwn = zContent.addObject(listener);
		JWebAction actDblClick = JWebActionFactory.createViewAreaAndTitleAction(dblClick, zObjectProvider, false, idOwn);
		if (actDblClick != null) {
			zContent.startNode("dblclickDrag");
			actDblClick.toXML(zContent);
			zContent.endNode("dblclickDrag");
		}

	}

	public void verifySize() throws Exception {
		if (!this.hasParent())
			return;
		if (!(this.getParent() instanceof JWebWinForm))
			return;
		if (this.fullWidth && this.getParent().getSize().width != -1) {
			this.setWidth(this.getParent().getSize().width - (this.getLocation().x + this.offsetRight));
			if (this.getSize().width < 0)
				this.setWidth(0);
		}
		if (this.fullHeight && this.getParent().getSize().height != -1) {
			this.setHeight(this.getParent().getSize().height - (this.getLocation().y + this.offsetBottom));
			if (this.getSize().height < 0)
				this.setHeight(0);
		}

	}

//	public void ajustarSize() throws Exception {
//		if (JWebActionFactory.getCurrentRequest().getUICoordinator().getClientConfiguration().bordersAreInternal())
//			return;
//
//		// margenes y bordes que lo adiciona el browser asi que el panel interno
//		// hay que restarles los bordes
//		Dimension oSize = JWebLayouts.getLayoutSizeOf(this);
//		Insets borders = this.getBorderLazyly();
//		Insets margins = this.getMarginLazyly();
//
//		this.tryChangeSize(oSize.width - (borders.right + borders.left + margins.right + margins.left), oSize.height - (borders.top + borders.bottom + margins.top + margins.bottom));
//	}
	
	public String getSpecificValue() throws Exception {
		return null;
	}
  public JWin getWinElegido(String zClave) throws Exception {
  	return null;
  }
  public JWin[] getWinElegidos(String zClave) throws Exception {
  	return null;
  }
	public void setEditable(boolean editable) throws Exception {
	}

	public void clear() throws Exception {
	}
	
  public String getDisplayValue() throws Exception {
  	return null;
  }
  public void setValue(String zVal) throws Exception {
  }
  public void setValue(JWin zVal) throws Exception {
  }
  public void setValue(JWins zVal) throws Exception {
  }
  public void setValue(JObject zVal) throws Exception {
  }


  public void setValueFromUIString(String zVal) throws Exception {
  }
  
  public void setController(JWebFormControl control) {
  }
  
  public ILayoutGenerator skin() throws Exception {
  	return BizUsuario.retrieveSkinGenerator();
  }

   public boolean isGenerateInternalEvents() {
		return bGenerateInternalEvents;
	}

	public void setGenerateInternalEvents(boolean bGenerateInternalEvents) {
		this.bGenerateInternalEvents = bGenerateInternalEvents;
	}
	
  public boolean hasLabelInfo() {
  	return true;
  }
}
