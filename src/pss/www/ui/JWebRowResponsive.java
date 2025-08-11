package pss.www.ui;

import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.winUI.controls.IFormTable;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormControles;
import pss.core.winUI.controls.JFormLabel;
import pss.core.winUI.controls.JFormRow;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormRowGridResponsive;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;

public class JWebRowResponsive extends JWebViewComposite implements JWebControlInterface ,JWebEditComponentContainer{

	// private JFormControles oControls;
	private JWebRootPane oRootPanel;
	protected JBaseForm oBaseForm;
	private String campo;
	private String provider;
	private IFormTable table;
	
	public IFormTable getTable() {
		return table;
	}

	public void setTable(IFormTable table) {
		this.table = table;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
  public boolean isUploadData() throws Exception {
  	return false;
  }
  public boolean isCard() throws Exception {
  	return false;
  }

	private long rowid;
	private long estado;
	private boolean selected;
		
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public long getEstado() {
		return estado;
	}

	public void setEstado(long estado) {
		this.estado = estado;
	}

	public long getRowid() {
		return rowid;
	}

	public void setRowid(long rowid) {
		this.rowid = rowid;
	}
	public static JWebRowResponsive create(JWebViewComposite parent, JFormRow control,String onlyThisControl) throws Exception {
		if (parent==null) return null;
		JWebRowResponsive row = new JWebRowResponsive(control.GetForm());
		row.setRowid(control.getIdRow());
		row.setProvider(/*parent.getObjectProviderWithCard().getProviderName()+"_"+*/control.getProvider());
		row.setEstado(control.getEstado());
		row.setCampo(control.getTable().getFixedProp().GetCampo());
		row.createControls(control.GetForm(),control.getIdRow(),onlyThisControl);
		row.setTable(control.getTable());
		row.setResponsive(control.isResponsive());
		parent.addChild(row);
	
		return row;
	}
	public static JWebRowResponsive create(JWebViewComposite parent, JFormRowGridResponsive control,String onlyThisControl) throws Exception {
		if (parent==null) return null;
		JWebRowResponsive row = new JWebRowResponsive(control.GetForm());
		row.setRowid(control.getIdRow());
		row.setProvider( /*parent.getObjectProviderWithCard().getProviderName()+"_"+*/control.getProvider());
		row.setEstado(control.getEstado());
		row.setCampo(control.getTable().getFixedProp().GetCampo());
		row.createControls(control.GetForm(),control.getIdRow(),onlyThisControl);
		row.setResponsive(control.isResponsive());
		row.setTable(control.getTable());
		parent.addChild(row);
	 
		return row;
	}
	
	public void createControls(JBaseForm f,long idRow,String onlyThisControl) throws Exception {
		JFormControles controls=new JFormControles(this.oBaseForm,null);
		JIterator<JFormControl> iter=f.getControles().getRecursiveItems();
		while (iter.hasMoreElements()) {
			JFormControl formControl=iter.nextElement();
			if (formControl instanceof JFormLabel) continue;
			controls.AddControl(this.createWebControl(formControl,onlyThisControl));
		}
		f.setControles(controls);
	}
	
	public String getCampo() {
		return this.campo;

	}
	
	public void setCampo(String zValue) {
		this.campo=zValue;
	}

	public JWebRootPane getRootPanel() {
		return this.oRootPanel;
	}

	public void setRootPanel(JWebRootPane panel) {
		this.oRootPanel=panel;
	}

	
	public JWebRowResponsive(JBaseForm zBaseForm) {
		this.oBaseForm=zBaseForm;
	}


	public String getAbsoluteName() {
		return this.getName();
	}

	@SuppressWarnings("unchecked")
	public Class getAbsoluteClass() {
		return JWebWinForm.class;
	}



	
	@Override
	public void destroy() {
		if (this.oBaseForm!=null) {
			try {
				this.oBaseForm.cleanUp();
			} catch (Exception e) {
				PssLogger.logDebug(e);
			}
			this.oBaseForm=null;
		}
		super.destroy();
	}

	//
	// ACCESS API
	// 
	public JWin getWin() {
		return this.oBaseForm.GetBaseWin();
	}

	public JFormControles getControls() throws Exception {
		return this.oBaseForm.getControles();
	}

	//
	// INTERNAL IMPLEMENTATION
	//

	//
	// METHODS TO OVERRIDE IN SUBCLASSES
	//

	public boolean isEditForm() {
		return true;
	}

	//
	// supertypes implementation
	//


//	@Override
//	protected void containerToXML(JXMLContent zContent) throws Exception {
//	}


	public String getTitle() {
		return this.oBaseForm.getTitle();
	}

	//
	// internal implementation
	//

	


	@Override
	public String getComponentType() {
		return "win_row_responsive";
	}
	

	public JBaseForm getBaseForm() throws Exception {
		return this.oBaseForm;
	}


	@Override
	public void clear() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDisplayValue() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSpecificValue() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onShow(String modo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setController(JWebFormControl control) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEditable(boolean editable) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValue(String zVal) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValueFromUIString(String zVal) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFormName() throws Exception {
		return getProvider();
	}

	@Override
	public boolean isModoConsulta() throws Exception {
		if (getForm()==null) return false;
		return getForm().isModoConsulta();
	}
	@Override
	public boolean isAlta() throws Exception {
		return getForm().isAlta();
	}

	@Override
	public boolean isInLine() {
		return true;
	}

  

}
