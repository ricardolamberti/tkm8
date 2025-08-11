package pss.www.ui;

import pss.www.platform.actions.JWebAction;

public class JWebNavigationBarTop extends JWebViewNavigationBar {

	public JWebViewNavigationBar buildNav(String role) throws Exception {
		String clase = "nav navbar-no-bottom "+(isNavBarBlack()? "navbar-black":"navbar") ; //nav-bar
		if (navBarInverse)
			clase += " navbar-inverse";
		else
			clase += " navbar-default";

		clase += " navbar-static-top";
//		clase += " pss-navbar";
		this.setName("navigation_bar");

		this.setClassResponsive(clase);
		this.setRole("navigation");

		JWebUnsortedList oUl = new JWebUnsortedList();
		clase = "nav";
		clase += " navbar-top-links";
		if (navBarRight)
			clase += " navbar-right";
		else
			clase += " navbar-left";
		oUl.setClassResponsive(clase);
		oNavBarPrincipal = oUl;
		oUl.setRoleMobile(role);
		this.addChild("navigation_bar_principal" + String.valueOf(idNavBar++), oUl);

		return this;
	}
	
	public void addUserMessage(String message) throws Exception {
		JWebLabel oLabel = new JWebLabel();
		oLabel.setSkinStereotype("navigation_bar_label");
		oLabel.setLabel(message);
		getNavBarLista().addChild("navigation_bar_link" + String.valueOf(idNavBar++), oLabel);
	}

	public void addNavigationTitle(String title,JWebIcon icon,String styleImage) throws Exception {
		JWebContainer header = new JWebContainer();
		header.setName("navheader");
		header.setClassResponsive("navbar-header");
		this.addChild(header);

		JWebLabelResponsive ltitle = new JWebLabelResponsive();
		ltitle.setName("title");
		ltitle.setClassResponsive("navbar-brand");
		ltitle.setStyleImage(styleImage);
		if (title!=null) ltitle.setLabel(title);
		if (icon!=null) ltitle.setIcon(icon);
		header.addChild(ltitle);
	}

	public void addNavigationTitle(JWebViewComposite title,JWebIcon icon,String styleImage) throws Exception {
		JWebContainer header = new JWebContainer();
		header.setName("navheader");
		header.setClassResponsive("navbar-header");
		this.addChild(header);


		if (title!=null) header.addChild(title);
	}

	public JWebNavigationBarGroup addNavigationGroup(JWebNavigationBarGroup group, JWebIcon zIcon,	String name, long level) throws Exception {
		JWebNavigationBarGroup oNBG = new JWebNavigationBarGroup();
		if (zIcon!=null) zIcon.setStyleImage("float: left;");
		oNBG.setIcon(zIcon);
		if (level==1) {
			oNBG.setArrow(JWebIcon.getResponsiveIcon("caret"));
			oNBG.setNavGroupClass("dropdown");
		} else {
			oNBG.setNavGroupClass("dropdown-submenu");//dropdown-menu
		}
		oNBG.setLabel(name);
		oNBG.setDataToggle("dropdown");

		oNBG.setClassResponsive("dropdown-menu");

		if (group == null)
			getNavBarLista().addChild(name + String.valueOf(idNavBar++), oNBG);
		else
			group.addChild(name + String.valueOf(idNavBar++), oNBG);
		return oNBG;
	}
	public void addNavigationAction(JWebNavigationBarGroup group, String zAction, JWebIcon zIcon, String label, boolean newwindow) throws Exception {
		addNavigationAction(group, zAction, zIcon, label, newwindow, null);
	}
	public void addNavigationAction(JWebNavigationBarGroup group, String zAction, JWebIcon zIcon, String label, boolean newwindow,String sConfirmation) throws Exception {
		if (zAction==null) return;
		JWebNavigationItem oLink=new JWebNavigationItem();
		oLink.setSkinStereotype("navigation_bar_link");
		oLink.setIdAction(zAction);
		oLink.setLabel(label);
		oLink.setIcon(zIcon);
		oLink.setOpensNewWindow(newwindow);
		oLink.setConfirmation(sConfirmation);
		
		JWebElementList li = new JWebElementList();
		li.addChild(zAction, oLink);
		if (group==null)
			getNavBarLista().addChild("navigation_bar_li"+String.valueOf(idNavBar++), li);//this.addChild("navigation_bar_li"+String.valueOf(idNavBar++), li);
		else
			group.addChild("navigation_bar_li"+String.valueOf(idNavBar++), li);
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
	public void addNavigationAction(JWebNavigationBarGroup group, JWebAction zAction, JWebIcon zIcon, String label, boolean newwindow) throws Exception {
		addNavigationAction(group,zAction,zIcon,label,newwindow,null);
	}
	public void addNavigationAction(JWebNavigationBarGroup group, JWebAction zAction, JWebIcon zIcon, String label, boolean newwindow,String sConfirmation) throws Exception {
		if (zAction==null) return;
		JWebNavigationItem oLink=new JWebNavigationItem();
		oLink.setSkinStereotype("navigation_bar_link");
		oLink.setWebAction(zAction);
		oLink.setLabel(label);
		oLink.setIcon(zIcon);
		oLink.setOpensNewWindow(newwindow);
		oLink.setConfirmation(sConfirmation);
		JWebElementList li = new JWebElementList();
		li.addChild(zAction.toString(), oLink);
		if (group==null)
			this.addChild("navigation_bar_li"+String.valueOf(idNavBar++), li);
		else
			group.addChild("navigation_bar_li"+String.valueOf(idNavBar++), li);
	}

	public void addNavigationSeparator(JWebNavigationBarGroup group) throws Exception {
		JWebElementList li = new JWebElementList();
		li.setClassResponsive("divider");
		if (group == null)
			getNavBarLista().addChild("navigation_bar_sep" + String.valueOf(idNavBar++), li);
		else
			group.addChild("navigation_bar_sep" + String.valueOf(idNavBar++), li);
	}

	public void addNavigationActionMensaje(JWebNavigationBarGroup group, String title, String fecha, String contents, JWebAction zAction, JWebIcon zIcon, long destacar) throws Exception {
		JWebLink oLink = new JWebLink();
		oLink.setWebAction(zAction);
		oLink.setTitle(title);
		oLink.setSubTitle(fecha);
		oLink.setLabel(contents);
		oLink.setIcon(zIcon);
		oLink.setImportant(destacar);

		JWebElementList li = new JWebElementList();
		li.setClassResponsive("dropdown");
		li.addChild("navigation_bar_link" + String.valueOf(idNavBar++), oLink);

		if (group == null)
			getNavBarLista().addChild("navigation_bar_li" + String.valueOf(idNavBar++), li);
		else
			group.addChild("navigation_bar_li" + String.valueOf(idNavBar++), li);
	}

}
