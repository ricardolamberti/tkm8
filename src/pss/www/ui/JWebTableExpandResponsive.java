package pss.www.ui;

import pss.common.security.BizUsuario;
import pss.core.winUI.responsiveControls.JFormTableExpandResponsive;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;

public class JWebTableExpandResponsive   extends JWebPanelResponsive implements JWebControlInterface  {

//	private JWebPanel oPageArea;
  private String campo;
  private String sizeRow;
  private String model;
	private long zoomtofit;
	String activo;

	public String getActivo() {
		return activo;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}
		
	public boolean isZoomtofit() {
		return zoomtofit>0;
	}

	public void setZoomtofit(long zoomtofit) {
		this.zoomtofit = zoomtofit;
	}
	public long getZoomtofit() {
		return this.zoomtofit;
	}

  public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSizeRow() {
		return sizeRow;
	}
	public void setSizeRow(String sizeRow) {
		this.sizeRow = sizeRow;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public JWebTableExpandResponsive() {
		super();
	}
	@Override
	protected void build() throws Exception {
//		JWebBorderLayout oLayout=new JWebBorderLayout(2, 2);
//		this.setLayout(oLayout);
	}
	@Override
	public void destroy() {

		super.destroy();
	}
	
	public static JWebTableExpandResponsive create(JWebViewComposite parent, JFormTableExpandResponsive control) throws Exception {
		if (parent==null) return null;

		IWebWinForm form= parent.findJWebWinForm();
		JWebTableExpandResponsive table=new JWebTableExpandResponsive();
		table.setCampo(control.getFixedProp().GetCampo());
		table.setResponsive(control.isResponsive());
		table.setSizeRow(control.getSizeRow());
		table.setModel(control.getModel());
		table.setZoomtofit(control.getZoomtofit());
		table.ensureIsBuilt();
		parent.addChild(table.getObjectName(), table); 
	
		table.takeAttributesFormControlResponsive(control);
		table.setActivo(control.getActivo());	

	  JWebPanelResponsive panel=BizUsuario.retrieveSkinGenerator().buildGridtable(table);
		panel.addAllComponentes(control,null);

		return table;
	}
	
	
	@Override
	public String getComponentType() {
		return "table_responsive";
	}


	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
		if (this.getForm() != null)
			zContent.setAttribute("form_name", this.getForm().getFormName());
		zContent.setAttribute("active", getActivo());
		zContent.setAttribute("id", getName());

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

}
