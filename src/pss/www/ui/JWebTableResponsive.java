package pss.www.ui;

import pss.core.win.JBaseWin;
import pss.core.winUI.lists.JWinGrid;
import pss.core.winUI.responsiveControls.JFormTableResponsive;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;

public class JWebTableResponsive  extends JWebPanelResponsive implements JWebControlInterface  {

	JWebWinGridResponsive lista;


	//	private JWebPanel oPageArea;
  private String campo;
  public JFormTableResponsive getTableControl() {
		return (JFormTableResponsive)this.getControl();
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public JWebTableResponsive() {
		super();
	}
	public JWebWinGridResponsive getLista() {
		return lista;
	}
	public void setLista(JWebWinGridResponsive lista) {
		this.lista = lista;
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
	
	public static JWebTableResponsive create(JWebViewComposite parent, JFormTableResponsive control,String onlyThisControl) throws Exception {
		if (parent==null) return null;

		IWebWinForm form= parent.findJWebWinForm();
		JWebTableResponsive table=new JWebTableResponsive();
		table.setCampo(control.getFixedProp().GetCampo());
		table.setResponsive(control.isResponsive());
		table.ensureIsBuilt();
		parent.addChild("table", table); 
		
//		if (parent.getLayout()!=null && parent.getLayout() instanceof JWebBorderLayout) 
//			((JWebBorderLayout)parent.getLayout()).setCenterComponent(table);
		
		table.takeAttributesFormControlResponsive(control);
  

		JWebWinGridResponsive webList = new JWebWinGridResponsive(form.getSourceAction(), (JWinGrid) control.GetLista(),table);
		webList.setEmbedded(true);
		webList.setModifiedOnServer(control.isModifiedOnServer());
		// webList.setLayout(table.getLayout());
		webList.setToolbar(JBaseWin.TOOLBAR_NONE);
		webList.setMode(control.getMode());
		webList.setControl(control);
		webList.ensureIsBuilt();
		// webList.setShowActionBar(true);
		table.setLista(webList);
		table.add("table_in", webList.getRootPanel());
		table.addAllComponentes(control,onlyThisControl);

		return table;
	}
	
	
	@Override
	public String getComponentType() {
		return "div_responsive";
	}


	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
		// TODO Auto-generated method stub
		
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
