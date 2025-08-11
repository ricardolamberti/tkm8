/*
 * Created on 12-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.skins.skin_siti;

import pss.common.security.BizSegConfiguracion;
import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.data.BizPssConfig;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.tools.JTools;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.actions.resolvers.JAbstractLoginResolver;
import pss.www.platform.actions.results.JWebActionResult;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.JWebDivResponsive;
import pss.www.ui.JWebEditResponsive;
import pss.www.ui.JWebFieldsetResponsive;
import pss.www.ui.JWebForm;
import pss.www.ui.JWebHResponsive;
import pss.www.ui.JWebLink;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewPasswordResponsive;
import pss.www.ui.JWebViewZoneRow;
import pss.www.ui.views.IActionPerform;
import pss.www.ui.views.ILoginPage;

public class JLoginSiti extends JWebView implements IActionPerform, ILoginPage {

	private JWebEditResponsive oUser;
	private String language;
//	private String loginTitle;
//	private String loginTitleLogo;
//	private String loginText;
//	private String loginFooter;
//	private String loginTextLink;
//	private String loginLink;
//	 static long cont=0;

	@Override
	protected void build() throws Exception {


		String url=this.getRequest().getURI();
		url=url.substring(1,url.indexOf("/",1));
//		analizeIniConfiguration();
//		BizPssConfig.getPssConfig().getLogo();
		
//		JLanguage l = JLanguage.getInstance();
//		l.setLanguageFromBrowser(language);
		
		JWebViewZoneRow row = new JWebViewZoneRow();
		this.add("row",row);
		row.setBackgroundImage("skin/skin_siti/images/foto0.jpg");
		row.setBackgroundSize(JFormControl.BACK_SIZE_COVER);
//		row.setHeight(625);
		row.setClassResponsive("login-canvas");
		JWebDivResponsive marco = new JWebDivResponsive();
		marco.setClassResponsive("col-md-4 col-md-offset-4");
		row.add("marco", marco);

		JWebDivResponsive panel = new JWebDivResponsive();
		panel.setClassResponsive("login-panel panel panel-default");
		panel.setHeight(280);
		marco.add("panel",panel);

		JWebDivResponsive panelHeading = new JWebDivResponsive();
		panelHeading.setClassResponsive("panel-heading");
		panel.add("heading", panelHeading);

		JWebHResponsive title = new JWebHResponsive(3,getMessage("Inicio de sesión"));
		panelHeading.add("body", title);

		JWebDivResponsive panelBody = new JWebDivResponsive();
		panelBody.setClassResponsive("panel-body");
		panel.add("body", panelBody);

		JWebDivResponsive imagenDiv = new JWebDivResponsive();
		imagenDiv.setClassResponsive("col-md-5 login-panel-image");
		panelBody.add("imagediv", imagenDiv);
		JWebDivResponsive imagen = new JWebDivResponsive();
		imagen.setClassResponsive("login-image");
		imagen.setBackgroundImage("skin/skin_siti/images/logo-siti2.png");
		imagen.setBackgroundSize("contain");
		imagenDiv.add("image", imagen);

		JWebDivResponsive formDiv = new JWebDivResponsive();
		formDiv.setClassResponsive("col-md-7");
		panelBody.add("login_formdiv", formDiv);

		JWebDivResponsive formDiv2 = new JWebDivResponsive();
		formDiv2.setClassResponsive("fieldset-soft");

//		JWebImageResponsive imagen = new JWebImageResponsive(JPssImage.SOURCE_PSS,"logos/"+BizPssConfig.getPssConfig().getLogo(),null);
//		imagenDiv.add("image", imagen);
		
		JWebFieldsetResponsive fieldset = new JWebFieldsetResponsive();
		fieldset.setClassResponsive("fieldset-login");
		fieldset.setFormatFields(JFormPanelResponsive.FIELD_LABEL_VERTICAL);

		JWebForm oForm = new JWebForm();
		formDiv.add("login_form", oForm);
		oForm.add("fieldset", fieldset);

		this.oUser = new JWebEditResponsive();
		this.oUser.setLabelLateral("Usuario");
		fieldset.add("username",this.oUser);
		

		JWebViewPasswordResponsive oPassword = new JWebViewPasswordResponsive();
		oPassword.setLabelLateral("Clave");
		fieldset.add("password",oPassword);

		oUser.setController(new JString(), "Usuario", true, BizSegConfiguracion.C_MAX_USERNAME_LENGTH, JObject.JUPPERCASE);
		oPassword.setController(new JString(), "Password",  false, BizSegConfiguracion.C_MAX_PASSWORD_LEN, null);

//		String ip = (String) getRequest().getModelObject("ip");
		fieldset.add("separador", formDiv2);


		JWebLink olink = new JWebLink();
		olink = new JWebLink(getMessage("Ingresar"));
		olink.setWebAction(JWebActionFactory.createDoLogin());
		olink.setClassResponsive("btn btn-lg btn-primary btn-block");
		olink.setSubmit(true);
		olink.setMode(JWebLink.MODE_BUTTON);

		fieldset.add(getMessage("confirm"), olink);
		
	}

	@Override
	public void toXML(JXMLContent zContent) throws Exception {
		// TODO Auto-generated method stub
		super.toXML(zContent);
	}
	@Override
	protected void init(JWebRequest zRequest) throws Exception {
		language = zRequest.getBrowserLanguage();
	}

	@Override
	protected void setUp(JWebRequest zRequest) throws Exception {
		String sUsername = zRequest.getFormData("login_form").get("username");
		if (sUsername != null) {
			this.oUser.setText(sUsername.trim().toUpperCase());
		}
	}

	@Override
	protected void generateNavigationBarActions() throws Exception {
		// no navigation actions for the login view, which
		// is the first one !!!!
	}

	public JWebActionResult perform(JAbstractLoginResolver loginResolver) throws Throwable {
		String sUsername = JTools.replace(loginResolver.getRequest().getFormData("login_form").get("username").toUpperCase(), "-", "");
		String sPassword = loginResolver.getRequest().getFormData("login_form").get("password");
		String sBase = loginResolver.getRequest().getFormData("login_form").get("base");
		String sCaptcha = loginResolver.getRequest().getFormData("login_form").get("captcha");
		String captcha = loginResolver.getRequest().getFormData("login_form").get("cpt");
		if (captcha != null) {
			if (!sCaptcha.equals(JTools.PasswordToString(captcha)))
				throw new Exception(getMessage("Fallo verificación contra bots"));
		}
		if (BizPssConfig.getPssConfig().acceptLoginWithMail()) {
			//Pasar de mail a usuario para logearse con mail
			JAplicacion.GetApp().openSession();
			JAplicacion.GetApp().openApp("login", "Web", true);
			BizUsuario user = new BizUsuario();
			user.dontThrowException(true);
			user.addFilter("user_mail", sUsername.toLowerCase());
			if (user.read())
				sUsername = user.GetUsuario();
			JAplicacion.GetApp().closeApp();
			JAplicacion.GetApp().closeSession();
			
		}
		return loginResolver.performLogin(sUsername, sPassword, sBase, null, false);
	}

	// LOGIN_WELCOME_TITLE=Bienvenido
	// LOGIN_WELCOME_TITLE_LOGO=15000
	// LOGIN_WELCOME_TEXT=Ud ha ingresado al área privada del SITI. Ingrese los
	// datos de su usuario y presione 'confirmar' para comenzar a administrar su
	// Empresa
	// LOGIN_WELCOME_FOOTER=* Sitio optimizado para : Internet Explorer 7 o
	// superior, Mozilla Firefox 3 o superior
	// LOGIN_WELCOME_TEXT_LINK=Pentaware S.A.
	// LOGIN_WELCOME_LINK=http://www.pentaware.com.ar

//	private void analizeIniConfiguration() throws Exception {
//		BizPssConfig cfg = BizPssConfig.getPssConfig();
//		String secc = cfg.getCachedStrictValue("GENERAL", "APPLICATION");
//		loginTitle = cfg.getCachedValue(secc, "LOGIN_WELCOME_TITLE", "Bienvenido");
//		loginTitleLogo = cfg.getCachedValue(secc, "LOGIN_WELCOME_TITLE_LOGO", "15000");
//		loginText = cfg.getCachedValue(secc, "LOGIN_WELCOME_TEXT",
//				"Ud ha ingresado al área privada del SITI. Ingrese los datos de su usuario y presione 'confirmar' para comenzar a administrar su Empresa");
//		loginFooter = cfg.getCachedValue(secc, "LOGIN_WELCOME_FOOTER", "* Sitio optimizado para : Internet Explorer 7 o superior, Mozilla Firefox 3 o superior");
//		loginTextLink = cfg.getCachedValue(secc, "LOGIN_WELCOME_TEXT_LINK", "Pentaware S.A.");
//		loginLink = cfg.getCachedValue(secc, "LOGIN_WELCOME_LINK", "http://www.pentaware.com.ar");
//	}

	public String getLoginPage() {
		return "login";
	}
	public String getMessage(String zMsg) throws Exception {
		String msg = 	BizUsuario.retrieveSkinGenerator().getMessage(zMsg, null);
		return msg;
	}
}
