/*
 * Created on 05-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.generators;

import pss.common.security.BizUsuario;

/**
 * 
 * 
 * Created on 05-jun-2003
 * 
 * @author PSS
 */

public class JSessionInfoGenerator extends JXMLComponentGenerator {
	

	@Override
	protected void doGenerate() throws Exception {
		if (this.getSession()!=null) {
			this.startNode("session");
	
//			PssLogger.logDebug("Thread ------------------------------> GENERADA : "+this.getSession().getIdDictionary());
			this.setAttribute("subsession", this.getSession().getIdSubsession());
			this.setAttribute("id", this.getSession().getId());
			this.setAttribute("user", BizUsuario.getUsr().GetUsuario());
			this.setAttribute("user_name", BizUsuario.getUsr().GetDescrip());
			if (!BizUsuario.getUsr().isAdminUser()) {
				if (BizUsuario.getUsr().hasNodo()) {
					this.setAttribute("node", BizUsuario.getUsr().getObjNodo().GetDescrip());
					this.setAttribute("country", BizUsuario.getUsr().getObjCountry().GetDescrip());
				}
				if ( BizUsuario.getUsr().getObjCompany() != null ) {
				  this.setAttribute("company", BizUsuario.getUsr().getObjCompany().getDescription());
				  this.setAttribute("company_id", BizUsuario.getUsr().getObjCompany().getCompany());
				}
			}
			this.endNode("session");
		}
	}

	@Override
	protected String getBaseContentName() {
		return "session.info";
	}

}
