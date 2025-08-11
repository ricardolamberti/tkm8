package pss.www.ui;

import pss.core.winUI.responsiveControls.JFormButtonPayResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.content.generators.JXMLContent;

public class JWebButtonPayResponsive extends JWebEditResponsive   {


	private String country;
	private String currency;
	private String status;
	private double price;
	private String mode;
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String getComponentType() {
		return "buttonpay_responsive";
	}
 
	public static JWebButtonPayResponsive create(JWebViewComposite parent, JFormButtonPayResponsive fileControl) throws Exception {
		JWebButtonPayResponsive webFile=new JWebButtonPayResponsive();
		webFile.takeAttributesFormControlResponsive(fileControl);
		webFile.setResponsive(fileControl.isResponsive());
		webFile.setCountry(fileControl.getCountry());
		webFile.setCurrency(fileControl.getCurrency());
		webFile.setStatus(fileControl.getStatus());
		webFile.setPrice(fileControl.getPrice());
		webFile.setMode(fileControl.getMode());
		if (parent!=null) parent.addChild(fileControl.getName(), webFile);
		return webFile;
	}
	


	

	
	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		zContent.setAttribute("form_name", this.getForm().getFormName());
		zContent.setAttribute("editable", true);
		zContent.setAttribute("country", getCountry());
		zContent.setAttribute("currency", getCurrency());
		zContent.setAttribute("status", getStatus());
		zContent.setAttribute("price", getPrice());
		zContent.setAttribute("mode", getMode());
	
  }
	
	

  public JWebAction getWebAction() throws Exception {
  	return this.getObjectProvider().getWebSourceAction();
  }

	@Override
	public void onShow(String modo) throws Exception {
		
	}
}
