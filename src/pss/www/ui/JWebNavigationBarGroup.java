package pss.www.ui;

import pss.www.platform.content.generators.JXMLContent;

public class JWebNavigationBarGroup extends JWebViewInternalComposite implements JWebArrow,JWebIconHolder,JWebTitledComponent,JWebDataToggle {

	private JWebIcon oIcon;
	private JWebIcon oArrow;
	String label;
	String dataTogle;
	String navGroupClass;
	
	public String getNavGroupClass() {
		return navGroupClass;
	}
	public void setNavGroupClass(String navGroupClass) {
		this.navGroupClass = navGroupClass;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public void setDataToggle(String zdatatoggle) {
		this.dataTogle = zdatatoggle;
	}


	public JWebIcon getIcon() {
    return this.oIcon;
  }
  public JWebIcon getMaximizeIcon() {
		return null;
	}
	
	public JWebIcon getArrow() {
    return this.oArrow;
  }
	@Override
	public String getDataToggle() throws Exception {
		return dataTogle;
	};

  public void setIcon(JWebIcon icon) {
    this.oIcon = icon;
  }
  public void setArrow(JWebIcon arrow) {
    this.oArrow = arrow;
  }
  	
  @Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
  	zContent.setAttribute("nav_group_class", getNavGroupClass());
  }

  @Override
	public String getComponentType() {
    return "navigation_group";
  }
  
	@Override
	public String getTitle() throws Exception {
		return label;
	
	}

}
