package pss.www.ui;

public class JWebPublicity extends JWebView {
	// tipo de recurso imagen o flash
	String type;	
	
	// posicion del recurso, pss, web, direccion
	long localization; 
	
	// link
	String link;
	
	// si es relative o inner con que id de html
	String resource;

	

	@Override
	public String getComponentType() {
		return "publicity";
	}
	static int uniqueId = 0;
	@Override
	public String getName() {
	    return "publicity_"+(uniqueId++); 
	}
	
	@Override
	protected void build() throws Exception {
//		JWebBorderLayout oLayoutPubl = new JWebBorderLayout();
//		this.setLayout(oLayoutPubl);

		JWebPanel oPanel = new JWebPanel();
//		JWebBorderLayout oLayout = new JWebBorderLayout();
//		oPanel.setLayout(oLayout);
		oPanel.setSkinStereotype(this.getSkinStereotypeView());
//		oLayoutPubl.setCenterComponent(oPanel);
		this.add("publicity_panel", oPanel);
		
		if (type.equalsIgnoreCase("image")) {
			JWebImageResponsive oImage = new JWebImageResponsive(getLocalization(),getResource(),getLink());
//			oLayout.setCenterComponent(oImage);
			oPanel.add("publicity_image", oImage);
			
		}
		else if (type.equalsIgnoreCase("flash")) {
			JWebFlash oFlash = new JWebFlash(getLocalization(),getResource(),getLink());
//			oLayout.setCenterComponent(oFlash);
			oPanel.add("publicity_image", oFlash);
			
		}

	}
	protected String getSkinStereotypeView() throws Exception {
		return "publicity";
	}
	@Override
	protected void generateNavigationBarActions() throws Exception {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public long getLocalization() {
		return localization;
	}

	public void setLocalization(long localization) {
		this.localization = localization;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}




}
