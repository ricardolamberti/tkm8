/*
 * Created on 14-may-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package pss.www.ui.skins;

import pss.JPath;
import pss.core.data.BizPssConfig;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.applications.JWebApplicationContext;
import pss.www.platform.applications.JWebApplicationSession;

/**
 * @author PSS
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and Comments
 */
public class JPssWebSkinProvider {


	public JPssWebSkinProvider(JWebApplicationContext zContext) {
	}


	public JWebSkin getSkinFor(JWebApplicationSession zSession) throws Exception {
		if (zSession==null) return getSkin();
		return zSession.getSkin();
	}
	JWebSkin skin;
	public JWebSkin getSkin() throws Exception {
		if (skin!=null) return skin;
	
		String sSkin=null;
		
		if (JWebActionFactory.isMobile())
			sSkin=BizPssConfig.getPssConfig().getSkinMobileDefault();
		else
			sSkin=BizPssConfig.getPssConfig().getSkinDefault();

		String sSkinDirAbsoluteURL=JPath.normalizePath(JPath.PssMainPath())+"/www/ui/skins/"+sSkin;
		skin=new JWebSkin(sSkinDirAbsoluteURL,"pss.www.ui.skins."+sSkin+".JLayoutGenerator");
		return skin;
	}

}
