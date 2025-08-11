package pss.www.ui;

import java.awt.Rectangle;

import pss.common.security.BizUsuario;
import pss.core.tools.PssLogger;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.winUI.controls.IFormTable;
import pss.core.winUI.controls.JFormControles;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormRowGridExpandResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;

public class JWebRowGridExpandResponsive  extends JWebPanelResponsive implements JWebControlInterface, JWebEditComponentContainer, JWebActionOwnerProvider {

	// private JFormControles oControls;
	private JWebRootPane oRootPanel;
	protected JBaseForm oBaseForm;
	private String campo;
	private String provider;
	private IFormTable table;
	private String nameParent;
	private String classHeader;
	private String toolbarPos = JBaseWin.TOOLBAR_TOP;

	private boolean resizable;
	private boolean expanded;

	private Rectangle rectangle;
	private BizAction sourceAction;

	public boolean isToolbarNone() throws Exception {
		return this.getToolbarPos().equals(JBaseWin.TOOLBAR_NONE);
	}

	public boolean isToolbarTop() throws Exception {
		return this.getToolbarPos().equals(JBaseWin.TOOLBAR_TOP);
	}
	public boolean isToolbarBottom() throws Exception {
		return this.getToolbarPos().equals(JBaseWin.TOOLBAR_BOTTOM);
	}

	public boolean isToolbarLeft() throws Exception {
		return this.getToolbarPos().equals(JBaseWin.TOOLBAR_LEFT);
	}
	public boolean isToolbarRight() throws Exception {
		return this.getToolbarPos().equals(JBaseWin.TOOLBAR_RIGHT);
	}
	public boolean isToolbarIn() throws Exception {
		return this.getToolbarPos().equals(JBaseWin.TOOLBAR_IN);
	}
	public String getToolbarPos() {
		return toolbarPos;
	}
	public void setToolbarPos(String toolbarPos) {
		this.toolbarPos = toolbarPos;
	}

	public boolean isModal() throws Exception {
		return false;
	}
	public void setSourceAction(BizAction value) {
		this.sourceAction = value;
	}
  public boolean isUploadData() throws Exception {
  	return false;
  }
  public boolean isCard() throws Exception {
  	return false;
  }
	public BizAction getSourceAction() {
		return this.sourceAction;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public boolean isResizable() {
		return resizable;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	public String getClassHeader() {
		return classHeader;
	}
	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public void setClassHeader(String classHeader) {
		this.classHeader = classHeader;
	}

	public String getNameParent() {
		return nameParent;
	}

	public void setNameParent(String nameParent) {
		this.nameParent = nameParent;
	}

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

	private long rowid;
	private long estado;
	
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

	
	public static JWebRowGridExpandResponsive create(JWebViewComposite parent, JFormRowGridExpandResponsive control,String onlyThisControl) throws Exception {
		if (parent==null) return null;
		JWebRowGridExpandResponsive row = new JWebRowGridExpandResponsive(control.GetForm());
		row.setRowid(control.getIdRow());
		row.setProvider(control.getProvider());
	  row.setResizable(control.isResizable());
	  row.setExpanded(control.isExpanded());
		row.setEstado(control.getEstado());
		row.setClassResponsive(control.getResponsiveClass());
		row.setCampo(control.getTable().getFixedProp().GetCampo());
		row.setResponsive(control.isResponsive());
		row.setTable(control.getTable());
		row.setLabel(control.getTitle());
		row.setLabelRight(control.getTitleRight());
		row.setEditable(control.getTable().isEditable());
		row.setRectangle(control.getRectangle());
		row.setNameParent(control.getTable().getName());
		row.setToolbarPos(control.getTable().getRowToolbarPos());
		row.setSourceAction(control.getAction());
		row.setVisible(control.isVisible());
		row.takeAttributesFormControlResponsive(control);
		//row.createControls(control.GetForm(),control.getIdRow(),onlyThisControl);
		parent.addChild(row);
		row.buildForm(control,onlyThisControl);
		return row;
	}
	JWebPanelResponsive rootPane;
	JWebPanelResponsive toolbarRootPane;
	
	public JWebPanelResponsive getRootPane() {
		return rootPane;
	}

	public void setRootPane(JWebPanelResponsive rootPane) {
		this.rootPane = rootPane;
	}

	public JWebPanelResponsive getToolbarRootPane() {
		return toolbarRootPane;
	}

	public void setToolbarRootPane(JWebPanelResponsive toolbarRootPane) {
		this.toolbarRootPane = toolbarRootPane;
	}

	public void buildForm(JFormRowGridExpandResponsive control,String onlyThisControl) throws Exception {
		setRootPane(this);
		setToolbarRootPane(this);
		BizUsuario.retrieveSkinGenerator().buildGridRow(this,control.getMode());
		if (this.isEditable())
			getToolbarRootPane().addChild(this.buildActionBar());
		getRootPane().addAllComponentes(control,onlyThisControl);
	}
	
	public void setActionBar(JWebWinActionBarForm zActionBar) {
		this.oActionBar = zActionBar;
	}
	
	public JWebWinActionBar getActionBar() {
		return this.oActionBar;
	}
	
	public boolean hasActionBar() {
		return this.oActionBar != null;
	}

	JWebWinActionBar oActionBar;
	public JWebViewInternalComposite buildActionBar() throws Exception {
		return this.generatedActionBar().getRootPanel();
	}
	public JWebWinActionBar generatedActionBar() throws Exception {
		JWebWinActionBar actionBar = new JWebWinActionBarForm(this, true, getToolbarPos(), true);
		actionBar.setPreview(false);
		this.oActionBar = actionBar;
		this.oActionBar.setForceWithLabels(false);
		this.oActionBar.setRootPanel(BizUsuario.retrieveSkinGenerator().createActionBarForm(this));
		return this.oActionBar;
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

	
	public JWebRowGridExpandResponsive(JBaseForm zBaseForm) {
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



	public String getTitle() {
		return this.oBaseForm.getTitle();
	}

	//
	// internal implementation
	//

	


	@Override
	public String getComponentType() {
		return "win_row_expand_responsive";
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

	boolean editable;
	@Override
	public void setEditable(boolean zeditable) throws Exception {
		editable = zeditable;
	}

	public boolean isEditable() {
		return editable;
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
		this.iRegisteredObjectId = zContent.addObject(this.getWinName(), this.getWin());
		String objectContext = zContent.addObject(""+getTable().getWin().hashCode(), getTable().getWin());
		
		if (this.oActionBar!=null) 
			this.oActionBar.addActionsFor(this.getWin(), this.iRegisteredObjectId, null, true, true, false, isModal(),objectContext);
		
		zContent.setAttribute("rowid", getRowid());
		zContent.setAttribute("label", getLabel());
		if (getLabelRight()!=null && getLabelRight().equals(""))
			zContent.setAttribute("label_right", getLabelRight());
		zContent.setAttribute("name_parent", getNameParent());
		zContent.setAttribute("form_name", getProvider());
		zContent.setAttribute("provider_name", getProviderName());
		zContent.setAttribute("toolbarpos", getToolbarPos());
		zContent.setAttribute("class_header_responsive", getClassHeader());
		zContent.setAttribute("visible", isVisible());
		zContent.setAttribute("expanded", isExpanded());
		zContent.setAttribute("resizable", isResizable());
		zContent.setAttribute("editable", isEditable());
		if (getRectangle()!=null) {
			zContent.setAttribute("cv_posx", getRectangle().getX());
			zContent.setAttribute("cv_posy", getRectangle().getY());
			zContent.setAttribute("cv_width", getRectangle().getWidth());
			zContent.setAttribute("cv_height", getRectangle().getHeight());
			
			zContent.startNode("control");
			zContent.startNode("text_field_responsive");
			zContent.setAttribute("name", "internal_estado_fila");
			zContent.setAttribute("form_name", this.getProvider());
			zContent.setAttribute("skin_stereotype", "text_field");
			zContent.setAttribute("visible", false);
			zContent.addTextNode("text", ""+ this.getEstado());
			zContent.endNode("text_field_responsive");
			zContent.endNode("control");

			zContent.startNode("control");
			zContent.startNode("text_field_responsive");
			zContent.setAttribute("name", "posy");
			zContent.setAttribute("form_name", this.getProvider());
			zContent.setAttribute("skin_stereotype", "text_field");
			zContent.setAttribute("visible", false);
			zContent.addTextNode("text", ""+getRectangle().getY());
			zContent.endNode("text_field_responsive");
			zContent.endNode("control");

			zContent.startNode("control");
			zContent.startNode("text_field_responsive");
			zContent.setAttribute("name", "posx");
			zContent.setAttribute("form_name", this.getProvider());
			zContent.setAttribute("skin_stereotype", "text_field");
			zContent.setAttribute("visible", false);
			zContent.addTextNode("text", ""+getRectangle().getX());
			zContent.endNode("text_field_responsive");
			zContent.endNode("control");

			zContent.startNode("control");
			zContent.startNode("text_field_responsive");
			zContent.setAttribute("name", "height");
			zContent.setAttribute("form_name", this.getProvider());
			zContent.setAttribute("skin_stereotype", "text_field");
			zContent.setAttribute("visible", false);
			zContent.addTextNode("text", ""+getRectangle().getHeight());
			zContent.endNode("text_field_responsive");
			zContent.endNode("control");

			zContent.startNode("control");
			zContent.startNode("text_field_responsive");
			zContent.setAttribute("name", "width");
			zContent.setAttribute("form_name", this.getProvider());
			zContent.setAttribute("skin_stereotype", "text_field");
			zContent.setAttribute("visible", false);
			zContent.addTextNode("text", ""+getRectangle().getWidth());
			zContent.endNode("text_field_responsive");
			zContent.endNode("control");


		}
		if (this.getDropManager() != null) {
			zContent.startNode("dragdropinfo");
			this.addDrop(zContent, this instanceof JWebActionOwnerProvider ? (JWebActionOwnerProvider) this : getObjectProvider(), this.getDropZone(), this.getDropManager());
			zContent.endNode("dragdropinfo");
		}

	

	}

//		for (int i = 0; i < oColumns.length; i++) {
//			oCol = oColumns[i];
//			if (oCol.isVisible())continue;
//			JWebViewComponent comp=null;
//			
//			JWebFormControl wcomp = (JWebFormControl)row.getBaseForm().GetControles().findControl(oCol.GetCampo());
//			if (wcomp==null) continue;
//			comp=(JWebViewComponent)wcomp.getWebComponent();
//			if (comp==null) continue;
//			if (comp!=null) {
//				zContent.startNode("control");
//				comp.setInTable(row.getRowid());
//				comp.toXML(zContent);
//				zContent.endNode("control");
//			}
//		}
//	}

	@Override
	public String getFormName() throws Exception {
		return getProvider();
	}

	@Override
	public boolean isModoConsulta() throws Exception {
		return getForm().isModoConsulta();
	}
	@Override
	public boolean isAlta() throws Exception {
		return getForm().isAlta();
	}

	@Override
	public boolean isInLine() {
		return false;
	}

	@Override
	public String getProviderName() throws Exception {
		return getProvider();

	}

	private String iRegisteredObjectId = null;

	@Override
	public String getRegisteredObjectId() {
		return iRegisteredObjectId;
	}

	@Override
	public JWebAction getWebSourceAction() throws Exception {
		return JWebActionFactory.createViewAreaAndTitleAction(this.sourceAction, this, true, this.getWinName());

	}
	public String getWinName() throws Exception {
//	return this.getWin()==null?"temporal":this.getWin().getClass().getSimpleName();
	return getProviderName()!=null?getProviderName():(this.getWin()==null?"temporal":this.getWin().getClass().getSimpleName());
}
  

}
