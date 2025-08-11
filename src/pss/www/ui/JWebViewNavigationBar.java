/*
 * Created on 25-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import pss.www.platform.actions.JWebAction;
import pss.www.platform.content.generators.JXMLContent;

public class JWebViewNavigationBar extends JWebViewInternalComposite {

	boolean navBarRight = true;
	boolean navBarInverse = false;
	boolean navBarBlack = false;

	String containerClass ="page-wrapper";
	
	public String getContainerClass() {
		return containerClass;
	}

	public void setContainerClass(String containerClass) {
		this.containerClass = containerClass;
	}

	JWebUnsortedList oNavBarPrincipal;
	long idNavBar = 0;

	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
		zContent.setAttribute("container_class", getContainerClass());
	}

	@Override
	public String getComponentType() {
		return "navigation_bar_complex";
	}

	public boolean isNavBarRight() {
		return navBarRight;
	}

	public void setNavBarRight(boolean navBarRight) {
		this.navBarRight = navBarRight;
	}

	public boolean isNavBarInverse() {
		return navBarInverse;
	}

	public void setNavBarInverse(boolean navBarInverse) {
		this.navBarInverse = navBarInverse;
	}

	public JWebViewInternalComposite getNavBarLista() throws Exception {
		if (oNavBarPrincipal != null)
			return oNavBarPrincipal;
		return this;
	}

	public boolean isNavBarBlack() {
		return navBarBlack;
	}

	public void setNavBarBlack(boolean navBarBlack) {
		this.navBarBlack = navBarBlack;
	}

	public JWebViewNavigationBar buildNav(String role) throws Exception {
		return this;
	}

	public void addUserMessage(String message) throws Exception {
	}

	public void addNavigationTitle(String title,JWebIcon icon,String styleImage) throws Exception {
	}
	public void addNavigationTitle(JWebViewComposite title,JWebIcon icon,String styleImage) throws Exception {
	}

	public void addNavigationMenu(JWebViewNavigationBar sidebar) throws Exception {
	}

	public JWebNavigationBarGroup addNavigationGroup(JWebNavigationBarGroup group, JWebIcon zIcon,String name, long level) throws Exception {
		return null;
	}
	public void addNavigationAction(JWebNavigationBarGroup group, String zAction, JWebIcon zIcon, String label, boolean newwindow)	throws Exception {
		return;
	}
	public void addNavigationAction(JWebNavigationBarGroup group, String zAction, JWebIcon zIcon, String label, boolean newwindow, String confirmation)	throws Exception {
			return;
	}
	public void addNavigationAction(JWebNavigationBarGroup group, JWebAction zAction, JWebIcon zIcon, String label, boolean newwindow) throws Exception {
			return;
	}
	public void addNavigationAction(JWebNavigationBarGroup group, JWebAction zAction, JWebIcon zIcon, String label, boolean newwindow,String confirmation) throws Exception {
		return;
}
	public void addLogo(JWebIcon logo,String style) throws Exception {
		return;
	}
	public void addNavigationToggle(JWebNavigationBarGroup group, JWebAction zAction, String name,boolean state, String active, String desactive, String label) throws Exception {
		return;
}

	public void addNavigationSeparator(JWebNavigationBarGroup group) throws Exception {
	}

	public void addNavigationActionMensaje(JWebNavigationBarGroup group, String title, String fecha, String contents, JWebAction zAction, JWebIcon zIcon, long destacar) throws Exception {
	}

}
