/*
 * Created on 12-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import pss.common.regions.multilanguage.JLanguage;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JScript;
import pss.core.services.records.JProperty;
import pss.core.tools.JTools;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.responsiveControls.JFormEditResponsive;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;
import pss.www.ui.input.JWebInputFormat;

/**
 * 
 * 
 * Created on 12-jun-2003
 * 
 * @author PSS
 */

public abstract class JWebViewEditComponent extends JWebViewComponent implements JWebControlInterface {

	private boolean bEditable=true;
	private boolean bNoForm=false;

	private int iTabIndex;
	private JWebFormControl oController;
	private long bBlockOverSize=-1;
	private boolean outstanding = false;
	boolean autocomplete=true;
	
	public boolean isAutocomplete() {
		return autocomplete;
	}
	public void setAutocomplete(boolean autocomplete) {
		this.autocomplete = autocomplete;
		
	}
	public boolean isOutstanding() {
		return outstanding;
	}

	public void setOutstanding(boolean outstanding) {
		this.outstanding = outstanding;
	}		

	public long getBlockOverSize() {
		return bBlockOverSize;
	}

	public void setBlockOverSize(long bBlockOverSize) {
		this.bBlockOverSize = bBlockOverSize;
	}
  
	
	public boolean isNoForm() {
		return bNoForm;
	}

	public void setNoForm(boolean bNoForm) {
		this.bNoForm = bNoForm;
	}
	
	protected void tableinfoToXML(JXMLContent zContent) throws Exception {
		if (!isInTable()) return;
		zContent.setAttribute("intable", isInTable());
		zContent.setAttribute("table_name", getTableName());
		zContent.setAttribute("table_row", getTableRow());
		zContent.setAttribute("table_column", getTableCol());


	}
	protected boolean checkConstraints() {
		return true;
	}

	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		zContent.setAttribute("editable", this.isEditable());
		zContent.setAttribute("needenter", this.isNeedEnter());
		zContent.setAttribute("noform", this.isNoForm());
		if (isOutstanding())
			zContent.setAttribute("outstanding", this.isOutstanding());
		if (!isAutocomplete())
			zContent.setAttribute("autocomplete", "off");
		if (this.getForm()!=null&&!this.isNoForm())
			zContent.setAttribute("form_name", this.getForm().getFormName());
		if (this.iTabIndex>0) {
			zContent.setAttribute("tab_index", this.iTabIndex);
		}
		if (getBlockOverSize()!=-1)
			zContent.setAttribute("max_length", getBlockOverSize());


		this.tableinfoToXML(zContent);
		this.widgetToXML(zContent);
		if (this.getController()==null) return;

		
		JFormControl oControl=this.getController();
		if (checkConstraints()) {
			zContent.startNode("constraints");
			zContent.setAttribute("inputmode", this.isInputMode(oControl));
			zContent.setAttribute("required", oControl.ifRequerido());
			zContent.setAttribute("datatype", oControl.GetObjectType());
			zContent.setAttribute("blockoversite", getBlockOverSize());
			zContent.setAttribute("unsigned", oControl.isUnsigned());
//			zContent.setAttribute("align", oControl.resolveAlignContent());
			zContent.setAttribute("description", JTools.replaceForeignCharsForWeb(JLanguage.translate(oControl.GetDisplayName())));
			zContent.startNode("format");
			this.inputFormatToXML(zContent);
			zContent.endNode("format");
			zContent.endNode("constraints");
		}
//		if (oControl.hasDependencies()) {
//			JIterator<String> oChildren=oControl.getDependencies().getIterator();
//			while (oChildren.hasMoreElements()) {
//				zContent.startNode("dependencies");
//				zContent.setAttribute("child", oChildren.nextElement());
//				zContent.endNode("dependencies");
//			}
//		}
	}
	
	public void takeAttributesFormControl(JFormControl comp) throws Exception {
			if (comp instanceof JFormEditResponsive) {
				JFormEditResponsive compE = (JFormEditResponsive) comp;
			}
			if (comp.ifReadOnly()) 
				this.setEditable(false);

		super.takeAttributesFormControl(comp);
	}
	
	private boolean isInputMode(JFormControl control) throws Exception {
		if (this.isFormConsulta(control)) return false;
//		if (control.getProp().hasScript()) return true;
//		if (control.ifReadOnly()) return false;
		return true;
	}
	
	private boolean isFormConsulta(JFormControl control) throws Exception {
		if (control.getControls()==null) return false; 
		if (control.getControls().getBaseForm()==null ) return false; 
		return control.getControls().getBaseForm().isConsulta();
	}

	@Override
	public void destroy() {
		if (this.oController!=null) {
			this.oController.Remove();
			this.oController=null;
		}
		super.destroy();
	}

	public boolean isEditable() {
		return this.bEditable;
	}

	public boolean isNeedEnter() {
		return false;
	}
	
	public void setEditable(boolean b) {
		this.bEditable=b;
	}

	
	public int getTabIndex() {
		return this.iTabIndex;
	}

	public void setTabIndex(int i) {
		this.iTabIndex=i;
	}

	@Override
	public boolean isEditComponent() {
		return true;
	}

	public JWebFormControl getController() {
		return this.oController;
	}

	public void setController(JWebFormControl control) {
		this.oController=control;
	}

	public void setController(JObject<?> zDataType, String zLabel, boolean zRequired, int zMaxLength, String zInputAttrs) throws Exception {
		if (this.oController!=null) {
			throw new RuntimeException("Component has already a controller");
		}
		if (this.getName()==null) {
			throw new RuntimeException("Unnamed components cannot have a controller");
		}
		JWebFormControl oControl=new JWebFormControl();
		oControl.setComponent(this);
		String sDesc=zLabel!=null ? zLabel : this.getName();
		JProperty oPropDesc=new JProperty("", this.getName(), sDesc, zDataType, null, false, zRequired, zMaxLength, 0, zInputAttrs, "");
		oControl.setFixedProp(oPropDesc);
		oControl.SetRequerido(zRequired ? "REQ" : "OPT");
		oControl.SetDisplayName(sDesc);
		this.setController(oControl);
	}


	public void setController(JObject<?> zDataType, JWebLabel zLabel, boolean zRequired, int zMaxLength, String zInputAttrs) throws Exception {
		if (this.oController!=null) {
			throw new RuntimeException("Component has already a controller");
		}
		if (this.getName()==null) {
			throw new RuntimeException("Unnamed components cannot have a controller");
		}
		JWebFormControl oControl=new JWebFormControl();
		oControl.setComponent(this);
		String sDesc=zLabel!=null ? zLabel.getLabel() : this.getName();
		JProperty oPropDesc=new JProperty("", this.getName(), sDesc, zDataType, null, false, zRequired, zMaxLength, 0, zInputAttrs, "");
		oControl.setFixedProp(oPropDesc);
		oControl.SetRequerido(zRequired ? "REQ" : "OPT");
		oControl.SetDisplayName(sDesc);
		this.setController(oControl);
	}



	/**
	 * Generation method to serialize max_length, pattern and format chars.<br>
	 * The default is no format constraints.
	 */
	protected void inputFormatToXML(JXMLContent zContent) throws Exception {
		JWebInputFormat oFormat=this.getInputFormat();
		if (oFormat!=null) {
			zContent.setAttribute("max_length", oFormat.getMaxLength());
			zContent.setAttribute("pattern", oFormat.getValidationPattern());
			zContent.setAttribute("chars", oFormat.getValidationFormatChars());
		} else {
			zContent.setAttribute("needs_input_check", false);
			zContent.setAttribute("max_length", -1);
			zContent.setAttribute("pattern", "");
			zContent.setAttribute("chars", "");
		}
	}

	//
	// METHODS TO OVERRIDE IN SUBLCASSES
	//

	public JWebInputFormat getInputFormat() throws Exception {
		return null;
	}

	public void clear() throws Exception {
		setValueFromUIString(null);
	}

	public String getSpecificValue() throws Exception {
		return this.getValueAsDBString();
	}

	public String getDisplayValue() throws Exception {
		return this.getSpecificValue();
	}
	
	public void setValue(String value) throws Exception {
		this.setValueFromDBString(value);
	}

	// public void blanquear() throws Exception {setValueFromUIString(null);}

	public abstract String getValueAsUIString() throws Exception;

	public abstract void setValueFromUIString(String zValue) throws Exception;

	public abstract String getValueAsDBString() throws Exception;

	public abstract void setValueFromDBString(String zDBString) throws Exception;

	public abstract Object getValue() throws Exception;

	public abstract void setValue(Object zValue) throws Exception;

	protected abstract void widgetToXML(JXMLContent zContent) throws Exception;

	public void onShow(String mode) throws Exception {
	}

	protected void generateOnCalculate(JXMLContent zContent) throws Exception {
		super.generateOnCalculate(zContent);
		
		if (this.getForm()==null) return ;
		if ( this.getForm().isModoConsulta()) return ;
		if (this.getController()==null) return ;
		if (this.getController().getProp()==null) return ;
		if (!getController().getProp().hasScript()) return ;

		JScript script=getController().getProp().getOnChangeScript(); //getObjOnChangeScript()
		if (script!=null) this.generateScript(zContent, script);

		script=getController().getProp().getScript(); // getObjScript los cripts pueden cambiar segun el contexto HGK
		if (script!=null) this.generateScript(zContent, script);

	}


	
	

}
