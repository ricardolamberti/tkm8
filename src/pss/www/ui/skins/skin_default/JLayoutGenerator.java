/*
 * Created on 09-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.skins.skin_default;

import pss.JPath;
import pss.common.security.BizUsuario;
import pss.www.ui.JWebIcon;
import pss.www.ui.JWebNavigationBarGroup;
import pss.www.ui.JWebView;
import pss.www.ui.skins.ILayoutGenerator;
import pss.www.ui.skins.JBasicLayoutGenerator;

/**
 * 
 * 
 * Created on 09-jun-2003
 * 
 * @author PSS
 */

public class JLayoutGenerator extends JBasicLayoutGenerator implements ILayoutGenerator {


	private static final String SKIN_PATH="skins/sbadmin";

	public String getSkinPath() {
		return SKIN_PATH;
	}
	public String sourceColors() throws Exception {
		return "skins/sbadmin/css/dark.css";
	}
	public String source() throws Exception {
		return "skins/sbadmin/css/sb-admin-2.css";
	}
	public void buildHeaderMenu(JWebView parent) throws Exception {
	}

	public void fillNavBar(JWebView parent) throws Exception {
		String logo = BizUsuario.getUsr().getObjCompany().getLogo();
		String logoPath=logo==null?null:JPath.PssPathLogos()+BizUsuario.getUsr().getObjCompany().getLogo();
 
		parent.addNavigationTitle(BizUsuario.getUsr().getObjBusiness().getTitle(),null,null);//,JWebIcon.getPssIcon(logoPath),"height:100%;");
  	parent.addGoBack(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-arrow-left"), null);
		parent.addGoHelp(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-question-circle"), null);
		parent.addSecurity(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-key"), null);
		parent.addGoHome(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-home"), null);
		parent.addNewSession(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-copy"), null);
		parent.addMailFolder(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-envelope"),"Mensajeria",1);
		parent.addAlertFolder(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-bell"),"Alertas",1);
		JWebNavigationBarGroup g3 = parent.addUserFolder(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-user"),"Config",1);
		if (g3!=null) {
			parent.addSeparator(g3);
			parent.addUserPreferences(g3, JWebIcon.getResponsiveIcon("fa fa-fw fa-address-card"),"Preferencias");
//			parent.addUserListExcludeCols(g3, JWebIcon.getResponsiveIcon("fa fa-fw fa-columns"),"Preferencias Vista");
			parent.addChangePassword(g3, JWebIcon.getResponsiveIcon("fa fa-fw fa-lock"),"Cambiar clave");
			parent.addSeparator(g3);
			parent.addCloseSession(g3,JWebIcon.getResponsiveIcon("fa fa-fw fa-sign-out"),"Cerrar sesión" );
		}
		parent.addRefresh(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-sync-alt"),null);
		parent.addActiveHelp(null,null,"Ayuda");
		
		parent.addLogo(JWebIcon.getPssIcon(logoPath),"height:25px;");

	}
}