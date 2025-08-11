package pss.www.ui;

import pss.common.security.BizUsuario;
import pss.www.platform.actions.JWebAction;

public class JWebViewNavigationResponsive extends JWebViewNavigationBar {

	public JWebViewNavigationBar buildNav(String role) throws Exception {
		String clase = (isNavBarBlack()? "navbar-black":"navbar")+" navbar-no-bottom";
		if (navBarInverse)
			clase += " navbar-inverse";
		else
			clase += " navbar-default";

		clase += " navbar-static-top";
//		clase += " pss-navbar";
		this.setName("navigation_bar");

		this.setClassResponsive(clase+ " col-lg-12");
		this.setRole("navigation");

		JWebUnsortedList oUl = new JWebUnsortedList();
		clase = "nav";
		clase += " navbar-top-links ";
		if (navBarRight)
			clase += " navbar-right";
		else
			clase += " navbar-left";
		oUl.setClassResponsive(clase);
		oUl.setRoleMobile(role);
		oNavBarPrincipal = oUl;
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

		if (icon!=null) {
			JWebImageResponsive limage = new JWebImageResponsive();
			limage.setName("title_icon");
			limage.setIcon(icon);
			limage.setStyleIcon("float:left;"+styleImage);
			limage.setSinDIV(true);
			header.addChild(limage);
		}
		
		if (title!=null && !title.isEmpty()) {
			JWebLabelResponsive ltitle = new JWebLabelResponsive();
			ltitle.setName("title");
			ltitle.setClassResponsive("navbar-brand");
			if (title!=null) ltitle.setLabel(title);
			ltitle.setTextPosition(JWebViewsConstants.TEXT_POSITION_LEFT);
			header.addChild(ltitle);
		}
	}
	public void addNavigationTitle(JWebViewComposite title,JWebIcon icon,String styleImage) throws Exception {
		JWebContainer header = new JWebContainer();
		header.setName("navheader");
		header.setClassResponsive("navbar-header");
		this.addChild(header);

		if (icon!=null) {
			JWebImageResponsive limage = new JWebImageResponsive();
			limage.setName("title_icon");
			limage.setIcon(icon);
			limage.setStyleIcon("float:left;"+styleImage);
			limage.setSinDIV(true);
			header.addChild(limage);
		}
		
		if (title!=null)
			header.addChild(title);
	}
	public void addNavigationMenu(JWebViewNavigationBar menu) throws Exception {
		
		if (BizUsuario.retrieveSkinGenerator().menuPosPrincipal().equals("TOP")) {
			if (menu!=null) {
				if (menu.isNavBarInverse())
					this.getParent().addChild("top-menu", menu);
				else
					this.addChild("top-menu", menu);
			}
			this.setContainerClass("container");
			
		} else if (BizUsuario.retrieveSkinGenerator().menuPosPrincipal().equals("SIDEBAR")) {
			JWebLink link = new JWebLink();
			link.setClassResponsive("btn-expand-collapse");
			link.setIcon(JWebIcon.getResponsiveIcon("glyphicon glyphicon-menu-left"));

			this.setContainerClass("page-wrapper");
			if (menu!=null) {
				menu.addChild("toggle_menu", link);
				this.addChild("side-menu", menu);
			}
			
		} else {
			this.setContainerClass("container");
			
		}
	}

	public JWebNavigationBarGroup addNavigationGroup(JWebNavigationBarGroup group, JWebIcon zIcon, String name, long level) throws Exception {
		JWebNavigationBarGroup oNBG = new JWebNavigationBarGroup();
		oNBG.setIcon(zIcon);
		oNBG.setArrow(JWebIcon.getResponsiveIcon("fa fa-fw fa-caret-down"));
		oNBG.setDataToggle("dropdown");
		oNBG.setNavGroupClass("dropdown");
		oNBG.setClassResponsive("dropdown-menu dropdown-user");

		if (group == null)
			getNavBarLista().addChild(name + String.valueOf(idNavBar++), oNBG);
		else
			group.addChild(name + String.valueOf(idNavBar++), oNBG);
		return oNBG;
	}
	
	public void addNavigationToggle(JWebNavigationBarGroup group, JWebAction zAction, String name,boolean state, String active, String desactive, String label) throws Exception {
		if (group == null)
			BizUsuario.retrieveSkinGenerator().createToggleInMenu(getNavBarLista(), name, state, zAction, label, active, desactive);
		else
			BizUsuario.retrieveSkinGenerator().createToggleInMenu(group, name, state, zAction, label, active, desactive);
		return;
  }
	public void addLogo(JWebIcon logo,String styleImage) throws Exception {
		JWebLink oLink = new JWebLink();
		oLink.setSkinStereotype("navigation_bar_link");
		oLink.setClassResponsive("dropdown-toggle");
		oLink.setIcon(logo);
		if (styleImage!=null) oLink.setStyleImage(styleImage);
		
		JWebElementList li = new JWebElementList();
		li.setClassResponsive("dropdown");
		li.addChild("navigation_bar_link" + String.valueOf(idNavBar++), oLink);

		getNavBarLista().addChild("navigation_bar_li" + String.valueOf(idNavBar++), li);
	}
	public void addNavigationAction(JWebNavigationBarGroup group, JWebAction zAction, JWebIcon zIcon, String label, boolean newwindow) throws Exception {
		addNavigationAction(group,zAction,zIcon,label,newwindow,null);
	}
	public void addNavigationAction(JWebNavigationBarGroup group, JWebAction zAction, JWebIcon zIcon, String label, boolean newwindow,String sConfirmation) throws Exception {
		if (zAction == null)
			return;
		JWebLink oLink = new JWebLink();
		oLink.setSkinStereotype("navigation_bar_link");
		oLink.setClassResponsive("dropdown-toggle");
		oLink.setWebAction(zAction);
		oLink.setLabel(label);
		oLink.setIcon(zIcon);
		oLink.setOpensNewWindow(newwindow);
		
		JWebElementList li = new JWebElementList();
		li.setClassResponsive("dropdown");
		li.addChild("navigation_bar_link" + String.valueOf(idNavBar++), oLink);

		if (group == null)
			getNavBarLista().addChild("navigation_bar_li" + String.valueOf(idNavBar++), li);
		else
			group.addChild("navigation_bar_li" + String.valueOf(idNavBar++), li);
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
