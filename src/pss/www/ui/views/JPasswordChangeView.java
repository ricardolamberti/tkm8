/*
 * Created on 12-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.views;

import pss.common.regions.company.JCompanyBusiness;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizLogTrace;
import pss.common.security.BizLoginTrace;
import pss.common.security.BizSegConfiguracion;
import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.data.BizPssConfig;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JPassword;
import pss.core.services.fields.JString;
import pss.core.tools.JExcepcion;
import pss.core.ui.components.JPssImage;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.applications.JSessionedApplication;
import pss.www.ui.JWebDivResponsive;
import pss.www.ui.JWebEditResponsive;
import pss.www.ui.JWebFieldsetResponsive;
import pss.www.ui.JWebForm;
import pss.www.ui.JWebHResponsive;
import pss.www.ui.JWebImageResponsive;
import pss.www.ui.JWebLabelDataResponsive;
import pss.www.ui.JWebLabelResponsive;
import pss.www.ui.JWebLink;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewPasswordResponsive;
import pss.www.ui.JWebViewZoneRow;

/**
 * 
 * 
 * Created on 12-jun-2003
 * @author PSS
 */


public class JPasswordChangeView extends JWebView {

	JWebHResponsive title;
  private JWebEditResponsive oUser;
  private JWebEditResponsive oUsuario;
  private JWebViewPasswordResponsive oPassword;
  private JWebViewPasswordResponsive oPasswordNew;
  private JWebViewPasswordResponsive oPasswordNewConf;
  private JWebLabelResponsive oMsgLabel;

  @Override
	protected void build() throws Exception {
  //  this.setTitle("Cambio de contraseña");
  //  this.setIcon(JWebIcon.getPssIcon(GuiIcon.LOGIN_ICON));

    // create the form
		JWebViewZoneRow row = new JWebViewZoneRow();
		JWebDivResponsive marco = new JWebDivResponsive();
		JWebDivResponsive panel = new JWebDivResponsive();
		JWebDivResponsive panelHeading = new JWebDivResponsive();
		JWebDivResponsive panelBody = new JWebDivResponsive();
		JWebImageResponsive imagen = new JWebImageResponsive(JPssImage.SOURCE_PSS,"logos/"+BizPssConfig.getPssConfig().getLogo(),null);
		title = new JWebHResponsive(3,JLanguage.translate("Cambio contraseña."));
		JWebFieldsetResponsive fieldset = new JWebFieldsetResponsive();
		JWebForm oForm = new JWebForm();
		
		panel.setClassResponsive("login-panel panel panel-default");
		panelHeading.setClassResponsive("panel-heading");
		panelBody.setClassResponsive("panel-body");
		marco.setClassResponsive("col-xs-offset-0 col-xs-12 col-sm-offset-1 col-sm-10 col-md-offset-2 col-md-8 col-lg-4 col-lg-offset-4");
		fieldset.setClassResponsive("fieldset-soft");
		imagen.setClassResponsive("img-responsive center-block");
		
		this.add("row",row);
		row.add("marco", marco);
		marco.add("panel",panel);
		panel.add("heading", panelHeading);
		panel.add("body", panelBody);
		panelHeading.add("body", title);
		if (!BizPssConfig.getPssConfig().getLogo().equalsIgnoreCase("nada.gif"))
			panelBody.add("image", imagen);
		panelBody.add("password_change_form", oForm);
		oForm.add("fieldset", fieldset);
		 
		oMsgLabel = new JWebLabelResponsive();
		fieldset.add("msg_label",oMsgLabel);

	  oUser = new JWebEditResponsive();
		oUser.setVisible(false);
		fieldset.add("username",oUser);

	  oUsuario = new JWebEditResponsive();
	  oUsuario.setLabelLateral(JLanguage.translate("Usuario"));
	  oUsuario.setEditable(false);
		fieldset.add("real_username",oUsuario);

		oPassword = new JWebViewPasswordResponsive();
		oPassword.setLabelLateral(JLanguage.translate("Contraseña actual"));
		fieldset.add("password",oPassword);

		oPasswordNew = new JWebViewPasswordResponsive();
		oPasswordNew.setLabelLateral(JLanguage.translate("Contraseña nueva"));
		fieldset.add("new_password",oPasswordNew);

		oPasswordNewConf = new JWebViewPasswordResponsive();
		oPasswordNewConf.setLabelLateral(JLanguage.translate("Contraseña nueva"));
		fieldset.add("new_password_confirmation",oPasswordNewConf);

		oUser.setController(new JString(), "Usuario", true, BizSegConfiguracion.C_MAX_USERNAME_LENGTH, JObject.JUPPERCASE);
//		oPassword.setController(new JPassword(), "Contraseña actual",  false, BizSegConfiguracion.C_MAX_PASSWORD_LEN, null);
		oPasswordNew.setController(new JPassword(), "Contraseña nueva",  false, BizSegConfiguracion.C_MAX_PASSWORD_LEN, null);
		oPasswordNewConf.setController(new JPassword(), "Contraseña nueva confimación",  false, BizSegConfiguracion.C_MAX_PASSWORD_LEN, null);

		JWebLink olink = new JWebLink();
		olink = new JWebLink(JLanguage.translate("Confirmar"));
		olink.setWebAction(JWebActionFactory.createDoPasswordChange());
		olink.setClassResponsive("btn btn-lg btn-primary btn-block");
		olink.setSubmit(true);
		olink.setMode(JWebLink.MODE_BUTTON);
		
		fieldset.add(JLanguage.translate("confirm"), olink );


  }


  @Override
	protected void setUp(JWebRequest zRequest) throws Exception {
    String sUsername = zRequest.getFormData("login_form").get("username").toUpperCase();
		
		String realUsername=sUsername;
		String sPassword="";
		BizUsuario user = new BizUsuario();
		BizLoginTrace login=new BizLoginTrace();
		String	loginClass=BizPssConfig.getPssConfig().getLoginTraceClass();
		if (loginClass!=null && !loginClass.equals("")) {
	    JAplicacion.GetApp().openSession();
			JAplicacion.GetApp().openApp("change.password", "Web", true);
			login = (BizLoginTrace)Class.forName(loginClass).newInstance();
			login.SetLogin(sUsername.toLowerCase());
			user=login.getObjUsuarioByMail();
	    sPassword = user.getPasswordDecrypt();
			if (user!=null)
				realUsername = user.GetUsuario();
			JAplicacion.GetApp().closeApp();
			JAplicacion.GetApp().closeSession();
		} else {
	 		if (BizPssConfig.getPssConfig().acceptLoginWithMail() && sUsername.indexOf("@")!=-1) {
  			//Pasar de mail a usuario para logearse con mail
		    JAplicacion.GetApp().openSession();
				JAplicacion.GetApp().openApp("change.password", "Web", true);
  			BizUsuario userMail = new BizUsuario();
  			userMail.dontThrowException(true);
  			userMail.addFilter("user_mail", sUsername.toLowerCase());
  			if (userMail.read())
  				realUsername = userMail.GetUsuario();
  			JAplicacion.GetApp().closeApp();
  			JAplicacion.GetApp().closeSession();
  			
  		}      
	    sPassword = this.getUserPassword(zRequest.getProcessor().getApplication(), realUsername);
		}
		
    if (sPassword==null || sPassword.trim().length() < 1) {
      this.oPassword.setEditable(false);
      this.oPassword.setText("");
      this.title.setLabel("Ingreso inicial de contraseña");
      this.oMsgLabel.setLabel("Su contraseña está en blanco. Por favor, ingrese una nueva para poder iniciar sesión.");
      oPassword.setController(new JPassword(), JLanguage.translate("Contraseña actual"), false, BizSegConfiguracion.C_MAX_PASSWORD_LEN, null);
    } else {
    	this.title.setLabel("Cambio obligatorio de contraseña");
      this.oMsgLabel.setLabel("Su contraseña ha caducado. Por favor, ingrese una nueva para poder iniciar sesión.");
      oPassword.setController(new JPassword(), JLanguage.translate("Contraseña actual"), true, BizSegConfiguracion.C_MAX_PASSWORD_LEN, null);
    }
    this.oUser.setText(realUsername);
    this.oUsuario.setText(sUsername);
  }

  private String getUserPassword(JSessionedApplication zApplication, String zUsername) throws Exception {
    JAplicacion oLOCALAplicacion = new JAplicacion();
    BizUsuario   oLOCALUsuario = new BizUsuario();
    JBDatos     oLOCALBDatos = new JBDatos();

    try {
      // set the global objects for the running Thread
    	JAplicacion.openSession();

     // initializes application
      oLOCALAplicacion.openApp(zApplication.getName(), zApplication.getType(), true); 
       oLOCALUsuario.dontThrowException(true);
      if( !oLOCALUsuario.Read(zUsername) ) {
     			JExcepcion.SendError(getMessage("Usuario inválido"));
      }
      String sPassword = oLOCALUsuario.getPasswordDecrypt();
      return sPassword;
    } catch( Exception e ) {
      try {oLOCALBDatos.rollback(); } catch( Exception ignored ) {}
      throw e;
    } finally {
      JBDatos.closeAllDatabases();
      // clean up global data
      JAplicacion.closeSession();
    }
  }
	public String getMessage(String zMsg) throws Exception {
		String msg = 	BizUsuario.retrieveSkinGenerator().getMessage(zMsg, null);
		return msg;
	}
}
