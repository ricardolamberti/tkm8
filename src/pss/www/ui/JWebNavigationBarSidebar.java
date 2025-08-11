package pss.www.ui;

import pss.core.win.actions.BizAction;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.content.generators.JXMLContent;

public class JWebNavigationBarSidebar extends JWebViewNavigationBar  {


	String classNavigation="navbar-default sidebar";
	String classNavigationInternal="sidebar-nav navbar-collapse";
	

  public String getClassNavigation() {
		return classNavigation;
	}

	public void setClassNavigation(String classNavigation) {
		this.classNavigation = classNavigation;
	}

	public String getClassNavigationInternal() {
		return classNavigationInternal;
	}
	
	@Override
	public JWebViewNavigationBar buildNav(String role) throws Exception {
		setClassResponsive("nav");
		return super.buildNav(role);
	}

	public void setClassNavigationInternal(String classNavigationInternal) {
		this.classNavigationInternal = classNavigationInternal;
	}

	@Override
	public String getComponentType() {
    return "navigation_group_sidebar";
  }

	long idNavBar=0;
	
	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
		zContent.setAttribute("class_navigation", classNavigation);
		zContent.setAttribute("class_navigation_internal", classNavigationInternal);
  }

	public JWebNavigationBarGroup addNavigationGroup(JWebNavigationBarGroup group, JWebIcon zIcon,String name, long level) throws Exception {
		JWebNavigationBarGroup oNBG=new JWebNavigationBarGroup();
		oNBG.setIcon(zIcon);
		oNBG.setLabel(name);
		oNBG.setNavGroupClass("dropdown");
		oNBG.setArrow(JWebIcon.getResponsiveIcon("fas fa-angle-down"));//, JWebIcon.getResponsiveIcon( "caret")
		
		String clase ="";
		clase = "nav ";
		if (level==1)
			clase += "nav-second-level ";
		else if (level==2)
			clase += "nav-third-level ";
		else if (level==3)
			clase += "nav-fourth-level ";
		else if (level==4)
			clase += "nav-fitfh-level ";
		oNBG.setClassResponsive(clase);
		
		if (group==null)
			this.addChild(name+String.valueOf(idNavBar++), oNBG);
		else
			group.addChild(name+String.valueOf(idNavBar++), oNBG);
		return oNBG;
	}
	public void addLogo(JWebIcon logo,String styleImage) throws Exception {
		JWebNavigationItem oLink=new JWebNavigationItem();
		oLink.setSkinStereotype("navigation_bar_link");
		oLink.setIcon(logo);
		if (styleImage!=null) oLink.setStyleImage(styleImage);
		
		JWebElementList li = new JWebElementList();
		li.addChild("navigation_bar_link"+String.valueOf(idNavBar++), oLink);
		this.addChild("navigation_bar_li"+String.valueOf(idNavBar++), li);
	}
	public void addNavigationAction(JWebNavigationBarGroup group, JWebAction zAction, JWebIcon zIcon, String label, boolean newwindow)	throws Exception {
		addNavigationAction(group,zAction,zIcon,label,newwindow,null);
	}
	public void addNavigationAction(JWebNavigationBarGroup group, JWebAction zAction, JWebIcon zIcon, String label, boolean newwindow, String confirmation)	throws Exception {
		if (zAction==null) return;
		JWebNavigationItem oLink=new JWebNavigationItem();
		oLink.setSkinStereotype("navigation_bar_link");
		oLink.setWebAction(zAction);
		oLink.setLabel(label);
		oLink.setOpensNewWindow(newwindow);
		oLink.setIcon(zIcon);
		oLink.setConfirmation(confirmation);
		oLink.setSubmit(zAction.isForceSubmit());
		oLink.setCancel(zAction.isCancel());
	
		JWebElementList li = new JWebElementList();
		li.addChild("navigation_bar_link"+String.valueOf(idNavBar++), oLink);
		if (group==null)
			this.addChild("navigation_bar_li"+String.valueOf(idNavBar++), li);
		else
			group.addChild("navigation_bar_li"+String.valueOf(idNavBar++), li);
	}
	public void addNavigationAction(JWebNavigationBarGroup group,String zAction, JWebIcon zIcon, String label, boolean newwindow) throws Exception {
		addNavigationAction(group, zAction, zIcon, label, newwindow, null);
	}
	public void addNavigationAction(JWebNavigationBarGroup group,String zAction, JWebIcon zIcon, String label, boolean newwindow, String confirmation) throws Exception {
		if (zAction==null) return;
		JWebNavigationItem oLink=new JWebNavigationItem();
		oLink.setSkinStereotype("navigation_bar_link");
		oLink.setIdAction(zAction);
		oLink.setLabel(label);
		oLink.setIcon(zIcon);
		oLink.setOpensNewWindow(newwindow);
		oLink.setConfirmation(confirmation);


		JWebElementList li = new JWebElementList();
		li.addChild("navigation_bar_link"+String.valueOf(idNavBar++), oLink);
		if (group==null)
			this.addChild("navigation_bar_li"+String.valueOf(idNavBar++), li);
		else
			group.addChild("navigation_bar_li"+String.valueOf(idNavBar++), li);
	}

	public JWebViewComponent addNavigationAction(JWebNavigationBarGroup group,String zActionId,JWebAction webAction, JWebIcon zIcon, String label, BizAction action) throws Exception {
		if (webAction==null) return null;
		JWebNavigationItem oLink=new JWebNavigationItem();
//		oLink.setClassResponsive("pss-action");
		oLink.setSkinStereotype("navigation_bar_link");
		oLink.setWebAction(webAction);
		oLink.setLabel(label);
		oLink.setIcon(zIcon);
		oLink.setOpensNewWindow(webAction.isNuevaVentana());
		if (action.getForeground()!=null)
			oLink.setForeground(action.getForeground());
		if (action.getBackground()!=null)
			oLink.setBackground(action.getBackground());
		oLink.setSubmit(webAction.isForceSubmit());
		oLink.setCancel(webAction.isCancel());
	
	
		JWebElementList li = new JWebElementList();
		li.addChild(zActionId, oLink);
		if (group==null)
			this.addChild("navigation_bar_li"+String.valueOf(idNavBar++), li);
		else
			group.addChild("navigation_bar_li"+String.valueOf(idNavBar++), li);
		return oLink;
	}

	
	public void addNavigationSeparator(JWebNavigationBarGroup group) throws Exception {
		JWebElementList li = new JWebElementList();
		li.setClassResponsive("divider");
		group.addChild("navigation_bar_sep"+String.valueOf(idNavBar++), li);
	}
	


}
