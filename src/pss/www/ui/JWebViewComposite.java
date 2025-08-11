/*
 * Created on 11-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import java.awt.Dimension;

import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JOrderedMap;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormForm;
import pss.core.winUI.controls.JFormLista;
import pss.core.winUI.controls.JFormLocalForm;
import pss.core.winUI.responsiveControls.JFormButtonPayResponsive;
import pss.core.winUI.responsiveControls.JFormButtonResponsive;
import pss.core.winUI.responsiveControls.JFormCDatetimeResponsive;
import pss.core.winUI.responsiveControls.JFormCardResponsive;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.core.winUI.responsiveControls.JFormColorPickerResponsive;
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormComboResponsive;
import pss.core.winUI.responsiveControls.JFormDivResponsive;
import pss.core.winUI.responsiveControls.JFormDropDownButtonResponsive;
import pss.core.winUI.responsiveControls.JFormDropDownComboResponsive;
import pss.core.winUI.responsiveControls.JFormDropDownWinLovResponsive;
import pss.core.winUI.responsiveControls.JFormEditFromToResponsive;
import pss.core.winUI.responsiveControls.JFormEditResponsive;
import pss.core.winUI.responsiveControls.JFormFieldsetExpandedResponsive;
import pss.core.winUI.responsiveControls.JFormFieldsetResponsive;
import pss.core.winUI.responsiveControls.JFormFileResponsive;
import pss.core.winUI.responsiveControls.JFormHtmlTextAreaResponsive;
import pss.core.winUI.responsiveControls.JFormImageCardResponsive;
import pss.core.winUI.responsiveControls.JFormImageResponsive;
import pss.core.winUI.responsiveControls.JFormInfoCardResponsive;
import pss.core.winUI.responsiveControls.JFormIntervalCDatetimeResponsive;
import pss.core.winUI.responsiveControls.JFormLabelDataResponsive;
import pss.core.winUI.responsiveControls.JFormLabelResponsive;
import pss.core.winUI.responsiveControls.JFormLineResponsive;
import pss.core.winUI.responsiveControls.JFormListExpandResponsive;
import pss.core.winUI.responsiveControls.JFormListJSonResponsive;
import pss.core.winUI.responsiveControls.JFormMapResponsive;
import pss.core.winUI.responsiveControls.JFormMatrixResponsive;
import pss.core.winUI.responsiveControls.JFormMultipleCheckResponsive;
import pss.core.winUI.responsiveControls.JFormMultipleResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormPasswordResponsive;
import pss.core.winUI.responsiveControls.JFormRadioResponsive;
import pss.core.winUI.responsiveControls.JFormRowGridExpandResponsive;
import pss.core.winUI.responsiveControls.JFormRowGridResponsive;
import pss.core.winUI.responsiveControls.JFormSwapListResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTableBigDataResponsive;
import pss.core.winUI.responsiveControls.JFormTableExpandResponsive;
import pss.core.winUI.responsiveControls.JFormTableResponsive;
import pss.core.winUI.responsiveControls.JFormTextAreaResponsive;
import pss.core.winUI.responsiveControls.JFormTreeComponentResponsive;
import pss.core.winUI.responsiveControls.JFormTreeResponsive;
import pss.core.winUI.responsiveControls.JFormWinLOVResponsive;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;
import pss.www.ui.processing.JWebCanvas;

public abstract class JWebViewComposite extends JWebViewComponent implements JWebCanvas {

	// ////////////////////////////////////////////////////////////////////////////
	//
	// STATIC VARIABLES
	//  
	// ////////////////////////////////////////////////////////////////////////////
	private static final int STATE_BUILD_PENDING=0;
	private static final int STATE_BUILDING=1;
	private static final int STATE_BUILT=2;

	// ////////////////////////////////////////////////////////////////////////////
	//
	// INSTANCE VARIABLES
	//  
	// ////////////////////////////////////////////////////////////////////////////
	JOrderedMap<String, JWebViewComponent> oChildren;
//	private JWebCompositeLayout oLayout;
	private int iBuiltState=STATE_BUILD_PENDING;
	protected boolean bNeedsLayout;
 

	// ////////////////////////////////////////////////////////////////////////////
	//
	// CONSTRUCTORS
	//  
	// ////////////////////////////////////////////////////////////////////////////
	public JWebViewComposite() {
		this.oChildren=JCollectionFactory.createOrderedMap();
	}

	@Override
	public void destroy() {
		if (this.oChildren!=null) {
			Object[] oChildren=this.oChildren.getElements();
			JWebViewComponent oComp;
			for(int i=0; i<oChildren.length; i++) {
				oComp=(JWebViewComponent) oChildren[i];
				oComp.destroy();
			}
			this.oChildren.removeAllElements();
			this.oChildren=null;
		}
//		this.oLayout=null;
		super.destroy();
	}

	// ////////////////////////////////////////////////////////////////////////////
	//
	// PUBLIC API
	//  
	// ////////////////////////////////////////////////////////////////////////////

//	public void markToLayout() {
//		this.bNeedsLayout=true;
////		this.sizeAjusted=false;
//	}

	public JWebForm getForm(String zChildName) throws Exception {
		return (JWebForm) this.getChild(zChildName);
	}

	public JWebPanel getPanel(String zChildName) throws Exception {
		return (JWebPanel) this.getChild(zChildName);
	}

	public void add(String zChildName, JWebViewInternalComposite zComponent) throws Exception {
		zComponent.ensureIsBuilt();
		this.addChild(zChildName, zComponent);
	}

	public void addBreak() throws Exception {
		this.addChild("br_at_"+String.valueOf(this.getChildrenCount()), new JWebFlowBreak());
	}
	
	public void addLine() throws Exception {
		this.addBreak();
		JWebTitledBorder t =new JWebTitledBorder();
		t.setSize((int)this.getSize().getWidth(), 2);
		this.addChild(t);
		this.addBreak();
	}

	public JIterator<JWebViewComponent> getChildren() throws Exception {
		this.ensureIsBuilt();
		return this.oChildren.getValueIterator();
	}

	public int getChildrenCount() {
		return this.oChildren.size();
	}

	@Override
	public boolean isComposite() {
		return true;
	}

//	public boolean hasLayout() {
//		return this.oLayout!=null;
//	}
//
//	public JWebCompositeLayout getLayout() {
//		return this.oLayout;
//	}
//
//	public JWebBorderLayout getBorderLayout() {
//		return (JWebBorderLayout )this.oLayout;
//	}
//
//	public void setLayout(JWebCompositeLayout layout) throws Exception {
//		this.oLayout=layout;
//	}
	
//	private boolean parentNeedLayout() throws Exception {
//		if (this.getParent()==null) return false;
//		return (this.getParent().bNeedsLayout);
//	}
	
//	public void doLayout() throws Exception {
////		if (BizPssConfig.getIsResponsive()) return;
//		if (!this.bNeedsLayout) return;
//		if (this.parentNeedLayout()) 
//			return; // hace fallar el history
//		boolean localNeededLayout=this.bNeedsLayout;
//			// resolve margins from skin if it has no margins set explicitly
//		this.resolveMargins();
//		this.resolveBorders();
//		this.resolvePadding();
//		// do layout !!
//
////		PssLogger.logDebug(this.getName());
//		if (this.getLayout()!=null) {
//			this.getLayout().layout(this);
//		}
//
//		this.bNeedsLayout=false;	
//	
//		JIterator<JWebViewComponent> iter=this.getChildren();
//		JWebViewComponent oComp;
//		while (iter.hasMoreElements()) {
//			oComp=iter.nextElement();
//			oComp.verifySize();
//			if (!oComp.isComposite()) continue;
//
//			JWebViewComposite oComposite=(JWebViewComposite) oComp;
//			if (localNeededLayout) 
//				oComposite.markToLayout();
//
////			if (oComposite.fullHeight && oComposite.fullWidth) 
////				continue;
//
//			oComposite.doLayout();
//		}
//		if (this.bNeedsLayout) {// si algun hijo forzo el relayout 
//			this.doLayout();
//			return;
//		}
//		this.ajustarSize();
//	}
  
	public JWebViewComponent getChild(int zIndex) throws Exception {
		this.ensureIsBuilt();
		return this.oChildren.getElementAt(zIndex);
	}

	
	@Override
	public Dimension getDefaultSize() throws Exception {
		return new Dimension(10, 10);
	}

	@Override
	public void validate() throws Exception {
		this.ensureIsBuilt();
	}

	// ////////////////////////////////////////////////////////////////////////////
	//
	// package INTERNAL UTILITIES
	//  
	// ////////////////////////////////////////////////////////////////////////////

	public JWebViewComponent getChild(String zChildName) throws Exception {
		// this.ensureIsBuilt();
		return this.oChildren.getElement(zChildName);
	}

	public JWebViewComponent getChildDeep(String zChildName) throws Exception {
		JWebViewComponent obj = this.oChildren.getElement(zChildName);
		if (obj!=null) return obj;
		JIterator<String> iter = this.oChildren.getKeyIterator();
		while (iter.hasMoreElements()) {
			String key = iter.nextElement();
			JWebViewComponent of = this.oChildren.getElement(key);
			if (key.equals(zChildName)) return of;
			if (of instanceof JWebViewComposite) {
				JWebViewComponent o = ((JWebViewComposite) of).getChildDeep(zChildName);
				if (o!=null) return o;
			}
		}
		return null;
	}
	
	public void addChild(JWebViewComponent zComponent) throws Exception {
		if (zComponent.hasName())
			this.addChild(zComponent.getName(), zComponent);
		else
			this.addChild("control-"+zComponent.hashCode(), zComponent);
	}
	public void addChild(String zChildName, JWebViewComponent zComponent) throws Exception {
		this.ensureIsBuilt();
		if (zChildName==null) {
			throw new RuntimeException("Cannot add a component with null name");
		} else if (this.oChildren.containsKey(zChildName)) {
			throw new RuntimeException("Container already contains a component named: '"+zChildName+"'");
//		} else if (zChildName.indexOf('.')!=-1) {
//			throw new RuntimeException("Component name must not contain: '.'");
		} else if (zComponent.getParent()!=null&&zComponent.getName()!=null) { // navigation
			// bar
			// actions
			// trick
			throw new RuntimeException("Component has already a parent: "+zComponent.getName());
		}
		this.oChildren.addElement(zChildName, zComponent);
		zComponent.setParent(this);
		zComponent.setName(zChildName);
//		this.markToLayout();
	}

	public void removeChild(String zChildName) {
		JWebViewComponent oComp=this.oChildren.getElement(zChildName);
		if (oComp!=null) {
			oComp.setName(null);
			oComp.setParent(null);
			this.oChildren.removeElement(zChildName);
//			this.markToLayout();
		}
	}

	public synchronized void ensureIsBuilt() throws Exception {
		if (this.iBuiltState!=STATE_BUILD_PENDING) {
			return;
		}
		try {
			this.iBuiltState=STATE_BUILDING;
			this.build();
//			this.markToLayout();
			this.iBuiltState=STATE_BUILT;
		} catch (Exception e) {
			this.iBuiltState=STATE_BUILD_PENDING;
			throw e;
		}
	}

	// ////////////////////////////////////////////////////////////////////////////
	//
	// XML REPRESENTABLE IMPLEMENTATION
	//  
	// ////////////////////////////////////////////////////////////////////////////

	protected void componentPreLayout(JXMLContent zContent) throws Exception {
		JIterator<JWebViewComponent> oChildrenIt=this.getChildren();
		JWebViewComponent oComp;
		while (oChildrenIt.hasMoreElements()) {
			oComp=oChildrenIt.nextElement();
			oComp.componentPreLayout(zContent);
		}
	}

	@Override
	protected void componentToXMLposLayout(JXMLContent zContent) throws Exception {
		// generate children XML
		JIterator<JWebViewComponent> oChildrenIt=this.getChildren();
		JWebViewComponent oComp;
		while (oChildrenIt.hasMoreElements()) {
			oComp=oChildrenIt.nextElement();
			if (oComp.isInTable()) continue;
			oComp.toXML(zContent);
		}
	}

		@Override
		protected void componentToXML(JXMLContent zContent) throws Exception {
			// generate this component XML
		zContent.setAttribute("composite", true);

		// layout this container
//		this.doLayout();

	  // generate this component XML
 		this.containerToXML(zContent);
		
		this.componentToXMLposLayout(zContent);
	}
		@Override
	protected void componentToHelpXML(JXMLContent zContent) throws Exception {
			// generate this component XML
		zContent.setAttribute("composite", true);


 		this.containerToHelpXML(zContent);
		JIterator<JWebViewComponent> oChildrenIt=this.getChildren();
		JWebViewComponent comp;
		while (oChildrenIt.hasMoreElements()) {
			comp=oChildrenIt.nextElement();
			if (comp.isInTable()) continue;
//			if (!oComp.hasHelp()) continue;
			if (!comp.isVisible()) continue;
			comp.toHelpXML(zContent);
		}
	}

	//
	// METHODS TO OVERRIDE IN SUBCLASSES
	//
	protected abstract void containerToXML(JXMLContent zContent) throws Exception;
	protected void containerToHelpXML(JXMLContent zContent) throws Exception {

	}

	protected void build() throws Exception {
		// a method to initialize a composite
	}

	protected JWebViewComposite getWebComponentParentForCreate(JFormControl webControl) {
		return this;
	}

	protected JWebFormControl createWebControl(JFormControl formControl,String onlyThisControl) throws Exception {
		JWebFormControl webControl=new JWebFormControl();
		webControl.setFormComponent(formControl);
		webControl.setProp(formControl.getProp());
		webControl.setFixedProp(formControl.getFixedProp());
		webControl.setIdControl(formControl.getIdControl());
		webControl.SetRequerido(formControl.GetRequerido());
		webControl.setSkinStereotype(formControl.getSkinStereotype());
		webControl.SetPermitirTodo(formControl.isPermitirTodo());
		webControl.setPermiteVacio(formControl.isPermiteVacio());
		webControl.SetTipoDato(formControl.GetTipoDato());
		webControl.setUnsigned(formControl.isUnsigned());
//		webControl.setAlignContent(formControl.getAlignContent());
		webControl.setHide(formControl.isHide());
		webControl.setOperator(formControl.getOperator());
		webControl.SetDisplayName(formControl.GetDisplayName());
		webControl.setPropDefault(formControl.getValorDefault());
		webControl.SetValorDefault(formControl.getValorDefault());
//		webControl.setDependencies(formControl.getDependencies());
		webControl.SetReadOnly(formControl.ifReadOnly());
		webControl.setDisplayOnly(formControl.isDisplayOnly());
		webControl.setPersistente(formControl.isPersistente());
		webControl.setLinkedLabel(formControl.getLinkedLabel());
		webControl.setInTableRow(formControl.getInTableRow());
		webControl.setInTableCol(formControl.getInTableCol());
		webControl.setTableName(formControl.getTableName());
		webControl.setForcedValue(formControl.getForcedValue());
		webControl.setMultiField(formControl.getMultiField());
		webControl.setFilterNeverHide(formControl.isFilterNeverHide());
		
		JWebControlInterface webComponent=this.createWebComponent(webControl, formControl,onlyThisControl);
		if (webComponent==null) return null;

		if (formControl.getProp()!=null && formControl.getProp().isRecord() && formControl.findObjectValue()!=null)
			webComponent.setValue(formControl.findObjectValue());
		else
   	 webComponent.setValue(formControl.getValue());
		webComponent.setEditable(formControl.isEditable());
//		webComponent.setVisible(formControl.isVisible());

		webComponent.setController(webControl);
		webControl.setComponent(webComponent);
		webControl.setVisible(webComponent.isVisible());

		return webControl;
	}
	
	
	
//	protected void addWebComponentFormControl(JWebFormControl webControl, JFormControl formControl) throws Exception {
//		JWebViewComponent webComponent=this.createWebComponent(webControl, formControl);
//		if (webComponent==null) return;
//		if (webComponent instanceof JWebControlInterface) {
//			JWebControlInterface c = (JWebControlInterface) webComponent;
//			c.setController(webControl);
//			webControl.setComponent(c);
//		}
//		return;
//	}
	 

	public IWebWinForm findJWebWinForm() throws Exception {
		if (this instanceof JWebWinForm) return (JWebWinForm)this;
		if (getParent()==null) return null;
		return getParent().findJWebWinForm();
	}
	
	public JWebViewComponent findComponentByControl( String zIdControl) throws Exception {
		JWebViewComponent comp = null;

		JIterator<JWebViewComponent> iter = this.getChildren();
		while (iter.hasMoreElements()) {
			comp = iter.nextElement();
			if (comp.getControl()!=null && comp.getControl().getIdControl()!=null && comp.getControl().getIdControl().equals(zIdControl))
				return comp;
			if (comp.isComposite()) {
				JWebViewComponent find = ((JWebViewComposite) comp).findComponentByControl( zIdControl );
				if (find!=null) return find;
			}
		}
	
		return null;
	}

	
	public boolean isMainPanel() {
		return true;
	}
	
	public JWebControlInterface createWebComponent(JWebFormControl webControl, JFormControl winControl,String onlyThisControl) throws Exception {
		return createWebComponentResponsive(winControl,onlyThisControl);
		
//		Component component=winControl.getComponent();
//		
//		if (component==null) return createWebComponentResponsive(winControl,onlyThisControl);
//		JWebViewComposite parent=this.getWebComponentParentForCreate(winControl);
//		if (winControl instanceof JFormRow) 
//			return JWebRow.create( parent, (JFormRow) winControl,onlyThisControl);
//			
//		if (component==null) return null;
//		parent = this.drawPanel(parent, component.getParent(),component,winControl.isResponsive());
//
//		if (component instanceof JPssLabelSign) {
//			return JWebSign.create(parent, (JPssLabelSign) component, (JFormSign) winControl);
//		}else	if (component instanceof JPssLabelScanner) {
//				return JWebScanner.create(parent, (JPssLabelScanner) component, (JFormScanner) winControl);
//		}else	if (component instanceof JTable) {
//			return JWebTable.create( parent, (JTable) component, (JFormTable) winControl);
//		}else	if (component instanceof JPssButton) {
//			return JWebButton.create(parent, (JPssButton) component, (JFormButton) winControl);
//		}else	if (component instanceof JPssButton) {
//			return JWebButton.create(parent, (JPssButton) component, (JFormButton) winControl);
//		}else	if (component instanceof JPssButton) {
//			return JWebButton.create(parent, (JPssButton) component, (JFormButton) winControl);
//		}	else if (component instanceof JPssLabelFile) {
//			return JWebFile.create(parent, (JPssLabelFile) component, (JFormFile) winControl);
//		} if (component instanceof JPssLabelFormLov) {
//			return JWebFormLov.create(parent, (JPssLabelFormLov) component, (JFormLOV) winControl, webControl);
//		} if (component instanceof JPssLabelWinLov) {
//			return JWebWinLOV.create(parent, (JPssLabelWinLov) component, (JWinLOV) winControl, webControl);
//		} else if (winControl instanceof JFormSwingSearch) {
//			return JWebSearch.create(parent, (JTextField) component, (JFormSwingSearch) winControl);
//		} else if (component instanceof JLabel) {
//			return JWebLabel.create(parent, (JLabel) component, winControl);
//		} else if (component instanceof JPssGoogleMap && BizPssConfig.getPssConfig().hasGoogleMaps() ) {
//			return JWebGoogleMap.create(parent, (JPssGoogleMap) component, (JFormSwingMap) winControl);
//		} else if (component instanceof JPasswordField) {
//			return JWebPasswordField.create(parent, (JPasswordField) component, (JFormSwingEdit) winControl);
//		} else if (component instanceof JPssColorPicker) {
//			return JWebColorPicker.create(parent, (JPssColorPicker) component, (JFormColorPicker) winControl);
//		} else if (component instanceof JPssLabelData) {
//			return JWebLabelData.create(parent, (JPssLabelData) component, (JFormSwingEdit) winControl);
//		} else if (component instanceof JTextField) {
//			return JWebTextField.create(parent, (JTextField) component, (JFormSwingEdit) winControl);
//		} else if (component instanceof JPssHtmlTextArea) {
//			return JWebHtmlTextArea.create(parent, (JPssHtmlTextArea) component, (JFormSwingTextArea) winControl);
//		} else if (component instanceof JPssEditTextArea) {
//			return JWebTextArea.create(parent, (JPssEditTextArea) component, (JFormSwingTextArea) winControl);
//		} else if (component instanceof JPssDependantCombo) {
//			return JWebDependantComboBox.create(parent, (JPssDependantCombo) component, (JFormSwingCombo) winControl, webControl);
//		} else if (component instanceof JPssList) {
//			return JWebMultiple.create(parent, (JPssList) component, (JFormMultiple) winControl, webControl);
//		} else if (component instanceof JList) {
//			return JWebComboBox.create(parent, (JList) component, (JFormListBox) winControl, webControl);
//		} else if (component instanceof JComboBox) {
//			return JWebComboBox.create(parent, (JComboBox) component, (JFormSwingCombo) winControl, webControl);
//		} else if (component instanceof JTree) {
//			return JWebTree.create(parent, (JTree) component, (JFormTree) winControl, webControl);
//		} else if (component instanceof JCheckBox) {
//			return JWebCheckbox.create(parent, (JCheckBox) component, (JFormCheck) winControl);
//		} else if (component instanceof JRadioButton) {
//			return JWebRadioButtonSet.create(parent, (JRadioButton) component, (JFormSwingRadio) winControl);
//		} else if (component instanceof JPssCalendarEdit) {
//			return JWebDateChooser.create(parent, (JPssCalendarEdit) component, (JFormCDate) winControl);
//		} else if (component instanceof JPssDateTimeEdit) {
//			return JWebDatetimeChooser.create(parent, (JPssDateTimeEdit) component, (JFormCDatetime) winControl);
//		} else if (component instanceof JPssImage) {
//			return JWebImage.create(parent, (JPssImage) component, (JFormImage) winControl);
//		} else { PssLogger.logDebug("-----------------------------------------------------------------");
//			PssLogger.logDebug(" No web component for Control class: "+winControl.getClass().getName());
//			PssLogger.logDebug("-----------------------------------------------------------------");
//			return new JWebTextField();
//		}
	}
	
public JWebControlInterface createWebComponentResponsive(JFormControl winControl,String onlyThisControl) throws Exception {
		//if (!winControl.isResponsive()) return null;
	
		JWebViewComposite parent=this.getWebComponentParentForCreate(winControl);
		if (winControl instanceof JFormRowGridResponsive) {
			return JWebRowResponsive.create( parent, (JFormRowGridResponsive) winControl,onlyThisControl);
		} else if (winControl instanceof JFormRowGridExpandResponsive) {
			return JWebRowGridExpandResponsive.create( parent, (JFormRowGridExpandResponsive) winControl,onlyThisControl);
		} else if (winControl instanceof JFormPasswordResponsive) {
			return JWebViewPasswordResponsive.create(parent, (JFormPasswordResponsive) winControl);
		} else if (winControl instanceof JFormIntervalCDatetimeResponsive) {
			return JWebIntervalDateTimeChooserResponsive.create(parent, (JFormIntervalCDatetimeResponsive) winControl);
		} else if (winControl instanceof JFormCDatetimeResponsive) {
			return JWebDateTimeChooserResponsive.create(parent, (JFormCDatetimeResponsive) winControl);
		} else	if (winControl instanceof JFormTableExpandResponsive) {
			return JWebTableExpandResponsive.create( parent, (JFormTableExpandResponsive) winControl);
		} else	if (winControl instanceof JFormTableResponsive) {
			return JWebTableResponsive.create( parent, (JFormTableResponsive) winControl,onlyThisControl);
		} else if (winControl instanceof JFormButtonResponsive) {
			return JWebButtonResponsive.create(parent, (JFormButtonResponsive) winControl);
		} else if (winControl instanceof JFormDropDownButtonResponsive) {
			return JWebViewDropDownButton.create(parent, (JFormDropDownButtonResponsive) winControl);
		} else if (winControl instanceof JFormFileResponsive) {
			return JWebFileResponsive.create(parent, (JFormFileResponsive) winControl);
		} else if (winControl instanceof JFormButtonPayResponsive) {
			return JWebButtonPayResponsive.create(parent, (JFormButtonPayResponsive) winControl);
		} else if (winControl instanceof JFormHtmlTextAreaResponsive) {
			return JWebHtmlTextAreaResponsive.create(parent, (JFormHtmlTextAreaResponsive) winControl);
		} else if (winControl instanceof JFormTextAreaResponsive) {
			return JWebViewTextAreaResponsive.create(parent, (JFormTextAreaResponsive) winControl);
		} else if (winControl instanceof JFormLabelDataResponsive) {
			return JWebLabelDataResponsive.create(parent, (JFormLabelDataResponsive) winControl);
		} else if (winControl instanceof JFormEditResponsive) {
			return JWebEditResponsive.create(parent, (JFormEditResponsive) winControl);
		} else if (winControl instanceof JFormEditFromToResponsive) {
			return JWebEditFromToResponsive.create(parent, (JFormEditFromToResponsive) winControl);
		} else if (winControl instanceof JFormCheckResponsive) {
			return JWebCheckResponsive.create(parent, (JFormCheckResponsive) winControl);
		} else if (winControl instanceof JFormDropDownComboResponsive) {
			return JWebDropDownComboResponsive.create(parent, (JFormDropDownComboResponsive) winControl);
		} else if (winControl instanceof JFormComboResponsive) {
			return JWebComboResponsive.create(parent, (JFormComboResponsive) winControl);
		} else if (winControl instanceof JFormMultipleCheckResponsive) {
			return JWebMultipleCheckResponsive.create(parent, (JFormMultipleCheckResponsive) winControl);
		} else if (winControl instanceof JFormMultipleResponsive) {
			return JWebMultipleResponsive.create(parent, (JFormMultipleResponsive) winControl);
		} else if (winControl instanceof JFormColorPickerResponsive) {
			return JWebColorPickerResponsive.create(parent, (JFormColorPickerResponsive) winControl);
		} else if (winControl instanceof JFormRadioResponsive) {
			return JWebRadioResponsive.create(parent, (JFormRadioResponsive) winControl);
		} else if (winControl instanceof JFormTreeComponentResponsive) {
			return JWebTreeResponsive.create(parent, (JFormTreeComponentResponsive) winControl);
		} else if (winControl instanceof JFormMapResponsive) {
			return JWebMapResponsive.create(parent, (JFormMapResponsive) winControl);
		} else if (winControl instanceof JFormSwapListResponsive) {
			return JWebWinSwapListResponsive.create(parent, (JFormSwapListResponsive) winControl);
		} else if (winControl instanceof JFormDropDownWinLovResponsive) {
			return JWebDropDownWinLovResponsive.create(parent, (JFormDropDownWinLovResponsive) winControl,onlyThisControl);
		} else if (winControl instanceof JFormWinLOVResponsive) {
			return JWebWinLOVResponsive.create(parent, (JFormWinLOVResponsive) winControl,onlyThisControl);
		} else if (winControl instanceof JFormLabelResponsive) {
			return JWebLabelResponsive.create(parent, (JFormLabelResponsive) winControl);
		} else if (winControl instanceof JFormLineResponsive) {
			return JWebFlowBreak.create(parent, (JFormLineResponsive) winControl);
		} else if (winControl instanceof JFormMatrixResponsive) {
			return JWebWinMatrixResponsive.create(parent, (JFormMatrixResponsive) winControl);
		} else if (winControl instanceof JFormListJSonResponsive) {
			return JWebWinListJSonResponsive.create(parent, (JFormListJSonResponsive) winControl);
		} else if (winControl instanceof JFormListExpandResponsive) {
			return JWebWinListExpandResponsive.create(parent, (JFormListExpandResponsive) winControl);
		} else if (winControl instanceof JFormTreeResponsive) {
			return JWebWinTreeResponsive.create(parent, (JFormTreeResponsive) winControl);
		} else if (winControl instanceof JFormTableBigDataResponsive) {
			return JWebWinGridBigDataResponsive.create(parent, (JFormTableBigDataResponsive) winControl);
		} else if (winControl instanceof JFormLista) {
			return JWebWinListResponsive.create(parent, (JFormLista) winControl);
		} else if (winControl instanceof JFormForm) {
			return JWebFormFormResponsive.create(parent, (JFormForm) winControl,onlyThisControl);
		} else if (winControl instanceof JFormLocalForm) {
			return JWebLocalFormResponsive.create(parent, (JFormLocalForm) winControl,onlyThisControl);
		} else if (winControl instanceof JFormInfoCardResponsive) {
			return JWebInfoCardResponsive.create(parent, (JFormInfoCardResponsive) winControl);
		} else if (winControl instanceof JFormImageCardResponsive) {
			return JWebImageCardResponsive.create(parent, (JFormImageCardResponsive) winControl);
		} else if (winControl instanceof JFormImageResponsive) {
			return JWebImageResponsive.create(parent, (JFormImageResponsive) winControl);
		} else if (winControl instanceof JFormColumnResponsive) {
			return JWebColumnResponsive.create(parent, (JFormColumnResponsive) winControl,onlyThisControl);
		} else if (winControl instanceof JFormFieldsetExpandedResponsive) {
			return JWebFieldsetExpandedResponsive.create(parent, (JFormFieldsetExpandedResponsive) winControl,onlyThisControl);
		} else if (winControl instanceof JFormFieldsetResponsive) {
			return JWebFieldsetResponsive.create(parent, (JFormFieldsetResponsive) winControl,onlyThisControl);
		} else if (winControl instanceof JFormCardResponsive) {
			return JWebCardResponsive.create(parent, (JFormCardResponsive) winControl,onlyThisControl);
		} else if (winControl instanceof JFormTabPanelResponsive) {
			return JWebTabPanelResponsive.create(parent, (JFormTabPanelResponsive) winControl,onlyThisControl);
		} else if (winControl instanceof JFormDivResponsive) {
			return JWebDivResponsive.create(parent, (JFormDivResponsive) winControl,onlyThisControl);
		} else if (winControl instanceof JFormPanelResponsive) {
			return JWebPanelResponsive.create(parent, (JFormPanelResponsive) winControl,onlyThisControl);
		} else { PssLogger.logDebug("-----------------------------------------------------------------");
			PssLogger.logDebug(" No web component for Control class: "+winControl.getClass().getName());
			PssLogger.logDebug("-----------------------------------------------------------------");
			return new JWebTextField();
		}
	}

//	public void pack() throws Exception {
//		int totalWidth=0;
//		int maxHeight=0;
//		int hgap=0;
//		if (this.getLayout()!=null) hgap=this.getLayout().getHGap();
//		JIterator<JWebViewComponent> iter=this.getChildren();
//		while (iter.hasMoreElements()) {
//			JWebViewComponent comp=iter.nextElement();
//			totalWidth+=comp.getSize().width;
//			totalWidth+=hgap;
//			if (comp.getSize().height>maxHeight) maxHeight=comp.getSize().height;
//		}
//		Dimension d = JWebLayouts.getLayoutSizeOf(this);
//		this.setWidth(totalWidth);
//		this.setHeight(d.height);
//	}

	public boolean isBuildPending() {
		return this.iBuiltState==STATE_BUILD_PENDING;
	}

	public void dontBuild() {
		this.iBuiltState=STATE_BUILT;
	}

	


	

	
//	public void ajustarSize() throws Exception {
//		if (JWebActionFactory.getCurrentRequest().getUICoordinator().getClientConfiguration().bordersAreInternal())
//			return;
//
//		//margenes y bordes que lo adiciona el browser asi que el panel interno hay que restarles los bordes
//		Dimension oSize=JWebLayouts.getLayoutSizeOf(this);
//		Insets borders=this.getBorderLazyly();
//		Insets margins=this.getMarginLazyly();
//
//		this.tryChangeSize(oSize.width-(borders.right+borders.left+margins.right+margins.left),oSize.height-(borders.top+borders.bottom+margins.top+margins.bottom));
//	}

	
//	public Rectangle createLayoutArea() throws Exception {
//	//	 long start = System.currentTimeMillis();
//	//	JDebugPrint.logDebug("Layout ["+zComposite.getName()+"]");
////		if (this.getName().equals("win_list_navigation_bar"))
////			PssLogger.logDebug("Layout ["+this.getName()+"]");
//		Dimension oSize=JWebLayouts.getLayoutSizeOf(this);
//		if (oSize==null||oSize.width<=0||oSize.height<=0) {
//			//throw new RuntimeException("A composite must have a not-null and non-zero size to be able to be layed out");
//		}
//		// determine the suitable margins (now according to IE CSS border endering)
//		Insets margin=this.getMarginLazyly();
//		Insets borders=this.getBorderLazyly();
//		Insets padding=this.getPaddingLazyly();
//
//	
//		this.setSize(oSize.width, oSize.height);
//			
//		Insets area=new Insets(
//				margin.top+padding.top+borders.top, // top
//				margin.left+padding.left+borders.left, // left
//				margin.bottom+padding.bottom+borders.bottom, // bottom
//				margin.right+padding.right+borders.right// right
//		);
//
//		Rectangle layoutArea=new Rectangle();
//		layoutArea.x=padding.left;
//		layoutArea.y=padding.top;
//		layoutArea.width=oSize.width-area.left-area.right;
//		layoutArea.height=oSize.height-area.top-area.bottom;
//
//		return layoutArea;
//		
//	}

//	protected void assignMyLayout(Component comp) throws Exception {
//		if (!this.isCompPanel(comp)) 
//			return;
//
//		JPanel panel = (JPanel)comp;
//		JWebCompositeLayout webLayout = this.createWebLayout(panel.getLayout());
//		if (webLayout==null) return;
//		webLayout.takeLayoutAttributesFrom(panel.getLayout());
//		this.setLayout(webLayout);
//	}

//	private boolean isCompPanel(Component comp) throws Exception {
//		if (comp==null) return false;
//		if (comp instanceof JPanel) return true;
//		return false;
//	}


	public void pushWidth(int w) throws Exception {
		JIterator<JWebViewComponent> iter= this.oChildren.getValueIterator();
		while (iter.hasMoreElements()) {
			JWebViewComponent c = iter.nextElement();
			c.setWidth(w);
		}
	}
 
	public JWebTabResponsive findTab(String provider) throws Exception {
  	for(Object c : this.oChildren.getElements()) {
  		if (c instanceof JWebTabResponsive) {
  			if (((JWebTabResponsive)c).getId().equals(provider))
  					return (JWebTabResponsive)c;
  		}
  		else if (c instanceof JWebViewComposite) {
  			JWebTabResponsive tab = ((JWebViewComposite) c).findTab(provider);
  			if (tab!=null) return tab;
  		}
  	}
  	return null;
  }
	
	public boolean isModoConsulta() throws Exception {
		JWebEditComponentContainer form = this.getForm();
		if (form==null) return false;
		return form.isModoConsulta();
	}
 
  public boolean hasLabelInfo() {
  	return false;
  }

	
}
