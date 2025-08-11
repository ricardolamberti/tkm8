/*
 * Created on 06-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.resolvers;

import pss.common.security.BizLoginTrace;
import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.data.BizPssConfig;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.records.JRecord;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.actions.results.JWebActionResult;
import pss.www.platform.applications.JSessionedApplication;

/**
 * 
 * 
 * Created on 06-jun-2003
 * @author PSS
 */

public class JDoPasswordChangeResolver extends JAbstractLoginResolver {

  @Override
	protected String getBaseActionName() {
    return "do.password.change";
  }


  @Override
	protected JWebActionResult perform() throws Throwable {
    // retrieve the fields from the request
    JWebActionData oFormData = this.getRequest().getFormData("password_change_form");
    String sUsername = oFormData.get("username").toUpperCase();
    String sPassword = oFormData.get("password");
    String sNewPassword = oFormData.get("new_password");
    String sNewPasswordConfirmation = oFormData.get("new_password_confirmation");

    // try to change the password
    this.doPasswordChange(this.getApplication(), sUsername, sPassword, sNewPassword, sNewPasswordConfirmation);

    // if succeeded, perfom login now !!!
    return null;// this.performLogin(sUsername, sNewPassword, null, null, false);
  }



  private void doPasswordChange(JSessionedApplication zApplication, String sUsername, String sPassword, String sNewPassword, String sNewPasswordConfirmation) throws Exception {

    JAplicacion oLOCALAplicacion = new JAplicacion();
    BizUsuario   oLOCALUsuario = new BizUsuario();
    JBDatos     oLOCALBDatos = new JBDatos();

    try {
      // set the global objects for the running Thread
      JAplicacion.SetApp( oLOCALAplicacion );
      BizUsuario.SetGlobal( oLOCALUsuario );
      JBDatos.SetGlobal( oLOCALBDatos );

      // initializes application
      // open databases on the previously set global JBDatos
      oLOCALAplicacion.openApp(zApplication.getName(), zApplication.getType(), true);

      BizUsuario user = new BizUsuario();;
  		BizLoginTrace login=new BizLoginTrace();
  		String	loginClass=BizPssConfig.getPssConfig().getLoginTraceClass();
  		if (loginClass!=null && !loginClass.equals("")) {
  			login = (BizLoginTrace)Class.forName(loginClass).newInstance();
  			login.SetLogin(sUsername.toLowerCase());
  			user=login.getObjUsuarioByMail();
  		} else {
  			user = BizUsuario.getUsr();
  		}

      JRecord cambioPass = user.getObjBusiness().buildCambioClave(sUsername,null,sPassword,sNewPassword,sNewPasswordConfirmation).getRecord();
      cambioPass.execProcessInsert();
//      cambioPass.getProp( "login" ).setValue( sUsername );
//	    cambioPass.getProp( "passactual" ).setValue( sPassword );
//	    cambioPass.getProp( "passnueva" ).setValue( sNewPassword );
//	    cambioPass.getProp( "passconfir" ).setValue( sNewPasswordConfirmation );

    } catch( Exception e ) {
      try {oLOCALBDatos.rollback(); } catch( Exception ignored ) {}
      throw e;
    } finally {
      JBDatos.closeAllDatabases();

      // clean up global data
      JAplicacion.closeSession();
    }
  }


}
