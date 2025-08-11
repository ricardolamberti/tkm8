package pss.www.ui.skins;

import pss.core.data.BizPssConfig;
import pss.www.platform.content.generators.JXMLComponentGenerator;

public class JLayoutGenerator  extends JXMLComponentGenerator {

	
	protected JWebSkin getSkin() throws Exception {
	 return this.getSkinProvider().getSkinFor(this.getSession()); 
	}
	@Override
	protected void doGenerate() throws Exception {
		this.startNode("layouts");
		this.setAttribute("base_path",  getSkin().createGenerator().getSkinPath());
		this.setAttribute("url_prefix", BizPssConfig.getPssConfig().getAppURLPrefix());
		this.setAttribute("url_total", BizPssConfig.getPssConfig().getUrlTotal());
		
		this.addCss();
		this.addJs();

		addVersionInfo();
		addNodeInfo();
		addUserInfo();
		addTextboxHeight();
		addLabelDataHeight();
		this.endNode("layouts");
	}
	
	protected void addLabelDataHeight() throws Exception {
		this.startNode("layout");
		this.setAttribute("id", "label_data");
		this.setAttribute("height", "21");
		this.endNode("layout");
	}

	protected void addVersionInfo() throws Exception {
		this.startNode("layout");
		this.setAttribute("id", "version_info");
		this.setAttribute("width", "200");
		this.endNode("layout");
	}

	protected void addNodeInfo() throws Exception {
		this.startNode("layout");
		this.setAttribute("id", "node_info");
		this.setAttribute("width", "150");
		this.endNode("layout");
	}

	protected void addUserInfo() throws Exception {
		this.startNode("layout");
		this.setAttribute("id", "user_info");
		this.setAttribute("width", "150");
		this.endNode("layout");
	}

	protected void addCompanyInfo() throws Exception {
		this.startNode("layout");
		this.setAttribute("id", "company_info");
		this.setAttribute("width", "150");
		this.endNode("layout");
	}
	protected void addTextboxHeight() throws Exception {
		this.startNode("layout");
		this.setAttribute("id", "text_field");
		this.setAttribute("height", "21");
		this.endNode("layout");
	}

	protected void addLogoFrontDoor() throws Exception {
		SkinTools tool = new SkinTools();
		tool.generateFrontDoorLogos();
		this.startNode("icon");
		this.setAttribute("source", tool.getResource());
		this.setAttribute("file", tool.getLogo());
		this.setAttribute("link", tool.getLink());
		this.endNode("icon");
	}

	protected void addLogoInDoor() throws Exception {
		SkinTools tool = new SkinTools();
		tool.generateIndoorLogos();
		this.startNode("icon");
		this.setAttribute("source", tool.getResource());
		this.setAttribute("file", tool.getLogo());
		this.setAttribute("link", tool.getLink());
		this.endNode("icon");
	}

	protected void addCss() throws Exception {
		this.startNode("css");
		this.setAttribute("source_colors", getSkin().createGenerator().sourceColors());
		this.setAttribute("source", getSkin().createGenerator().source());
		if (!needOldCss()) this.setAttribute("ignore_oldcss", "yes");
		this.endNode("css");
	}
	protected void addJs() throws Exception {
		this.startNode("js");
		this.setAttribute("source", "skins/sbadmin/js/sb-admin-2.js");
		this.endNode("js");
	}
	
	public boolean needOldCss() throws Exception {
		return getSkin().createGenerator().needOldCss();
	}
	


	
	protected void addManufacturerLogo(String logo, String link) throws Exception {
		this.startNode("layout");
		this.setAttribute("id", "manufacturer_info");
		this.startNode("icon");
		this.setAttribute("source", "pss_icon");
		this.setAttribute("file", logo);
		if (link != null)
			this.setAttribute("link", link);
		this.endNode("icon");
		this.endNode("layout");
	}
	
	protected void addManufacturerLogo(String logo) throws Exception {
		this.addManufacturerLogo(logo, null);
	}


	@Override
	protected String getBaseContentName() {
		return "application.info";
	}

}
