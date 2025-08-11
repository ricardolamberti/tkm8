/*
 * Created on 12-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.views;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletResponse;

import org.apache.cocoon.environment.http.HttpEnvironment;
import org.json.JSONObject;

import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizSegConfiguracion;
import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.data.BizPssConfig;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.tools.JTools;
import pss.core.ui.components.JPssImage;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.actions.resolvers.JAbstractLoginResolver;
import pss.www.platform.actions.results.JWebActionResult;
import pss.www.platform.applications.JwtTokenUtil;
import pss.www.ui.JWebCaptcha;
import pss.www.ui.JWebCheckResponsive;
import pss.www.ui.JWebDivResponsive;
import pss.www.ui.JWebEditResponsive;
import pss.www.ui.JWebFieldsetResponsive;
import pss.www.ui.JWebForm;
import pss.www.ui.JWebHResponsive;
import pss.www.ui.JWebImageResponsive;
import pss.www.ui.JWebLabelResponsive;
import pss.www.ui.JWebLink;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewPasswordResponsive;
import pss.www.ui.JWebViewZoneRow;

public class JLoginView extends JWebView implements IActionPerform, ILoginPage {

	private JWebEditResponsive oUser;
	private String language;
	private String loginTitle;
	private String loginRegister;
	private String loginRecovery;
	private String loginTitleLogo;
	private String loginText;
	private String loginFooter;
	private String loginTextLink;
	private String loginLink;
	static long cont = 0;

	@Override
	protected void build() throws Exception {

		// this.setTitle("Inicio de sesión");
		// this.setIcon(JWebIcon.getPssIcon(GuiIcon.LOGIN_ICON));
		analizeIniConfiguration();

		JLanguage l = JLanguage.getInstance();
		l.setLanguageFromBrowser(language);

		JWebViewZoneRow row = new JWebViewZoneRow();
		JWebDivResponsive marco = new JWebDivResponsive();
		JWebDivResponsive panel = new JWebDivResponsive();
		JWebDivResponsive panelHeading = new JWebDivResponsive();
		JWebDivResponsive panelBody = new JWebDivResponsive();
		JWebDivResponsive imagenDiv = new JWebDivResponsive();
		JWebDivResponsive formDiv = new JWebDivResponsive();
		JWebDivResponsive formDiv2 = new JWebDivResponsive();
		JWebImageResponsive imagen = new JWebImageResponsive(JPssImage.SOURCE_PSS, "logos/" + BizPssConfig.getPssConfig().getLogo(), null);
		JWebHResponsive title = new JWebHResponsive(3, getMessage("Inicio de sesión"));
		JWebFieldsetResponsive fieldset = new JWebFieldsetResponsive();
		JWebForm oForm = new JWebForm();
		panel.setClassResponsive("login-panel panel panel-default");
		panelHeading.setClassResponsive("panel-heading");
		panelBody.setClassResponsive("panel-body");
		marco.setClassResponsive("col-xs-offset-0 col-xs-12 col-sm-offset-1 col-sm-10 col-md-offset-2 col-md-8 col-lg-4 col-lg-offset-4");
		fieldset.setClassResponsive("fieldset-soft");
		fieldset.setFormatFields(JFormPanelResponsive.FIELD_LABEL_VERTICAL);
		imagenDiv.setClassResponsive("col-md-offset-2 col-md-8");
		formDiv.setClassResponsive("col-sm-12");
		formDiv2.setClassResponsive("fieldset-soft");
		this.add("row", row);
		row.add("marco", marco);
		marco.add("panel", panel);
		panel.add("heading", panelHeading);
		panel.add("body", panelBody);
		panelHeading.add("body", title);
		panelBody.add("imagediv", imagenDiv);
		if (!BizPssConfig.getPssConfig().getLogo().equalsIgnoreCase("nada.gif"))
			imagenDiv.add("image", imagen);
		panelBody.add("login_formdiv", formDiv);
		formDiv.add("login_form", oForm);
		oForm.add("fieldset", fieldset);

		this.oUser = new JWebEditResponsive();
		this.oUser.setLabelLateral(getMessage("Usuario"));
//		this.oUser.setFieldClass("col-sm-12");
		fieldset.add("username", this.oUser);

		JWebViewPasswordResponsive oPassword = new JWebViewPasswordResponsive();
		oPassword.setLabelLateral(getMessage("Password"));
//		oPassword.setFieldClass("col-sm-12");
		fieldset.add("password", oPassword);

		oUser.setController(new JString(), "Usuario", true, BizSegConfiguracion.C_MAX_USERNAME_LENGTH, JObject.JUPPERCASE);
		oPassword.setController(new JString(), "Password", false, BizSegConfiguracion.C_MAX_PASSWORD_LEN, null);


		addCaptcha(fieldset);
		

		fieldset.add("separador", formDiv2);


		JWebLink olink = new JWebLink();
		olink = new JWebLink(getMessage("Ingresar"));
		olink.setWebAction(JWebActionFactory.createDoLogin());
		olink.setClassResponsive("btn btn-lg btn-primary btn-block");
		olink.setSubmit(true);
		olink.setMode(JWebLink.MODE_BUTTON);

		fieldset.add(getMessage("confirm"), olink);

		if (BizPssConfig.getPssConfig().askPersistentLogin()) {
			JWebCheckResponsive oPersistent = new JWebCheckResponsive();
//			oPersistent.setMode(JFormCheckResponsive.MODE_SINGLE);
			oPersistent.setLabelLateral("Recordar?");
			oPersistent.setClassResponsive("col-sm-12");

			fieldset.add("persistent", oPersistent);
		}

		if (BizPssConfig.getPssConfig().getAdmitirRegistracion()) {
			JWebLink olinkRegistration = new JWebLink(getMessage(loginRegister));
			olinkRegistration.setWebAction(JWebActionFactory.createRegister());
			olinkRegistration.setSubmit(true);
			olinkRegistration.setClassResponsive("col-sm-6");

			fieldset.add(getMessage("register"), olinkRegistration);
		}
		if (BizPssConfig.getPssConfig().getAdmitirRecuperacionClave()) {
			JWebLink oLinkRecovery = new JWebLink(getMessage(loginRecovery));
			oLinkRecovery.setWebAction(JWebActionFactory.createRecovery());
			oLinkRecovery.setSubmit(true);
			oLinkRecovery.setClassResponsive("col-sm-6 text-right");

			fieldset.add(getMessage("recovery"), oLinkRecovery);
		}

		// JWebLabelResponsive label = new
		// JWebLabelResponsive(loginText,loginTitleLogo.equals("")?null:JWebIcon.getPssIcon(Integer.parseInt(loginTitleLogo)));
		// label.setClassResponsive("col-sm-12");
		// fieldset.add("leyenda", label);
		if (!loginFooter.equals("")) {
			JWebLabelResponsive label2 = new JWebLabelResponsive(loginFooter);
			label2.setClassResponsive("col-sm-12   alert alert-success");
			fieldset.add("leyenda2", label2);
			l.removeLanguageFromBrowser();
		}
	}

	protected synchronized boolean isCaptchaValid(String response) {
		try {
			String secretKey = BizPssConfig.getPssConfig().getGoogleCaptchaServer();
			String url = "https://www.google.com/recaptcha/api/siteverify", params = "secret=" + secretKey + "&response=" + response;

			HttpURLConnection http = (HttpURLConnection) new URL(url).openConnection();
			http.setDoOutput(true);
			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			OutputStream out = http.getOutputStream();
			out.write(params.getBytes("UTF-8"));
			out.flush();
			out.close();

			InputStream res = http.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(res, "UTF-8"));

			StringBuilder sb = new StringBuilder();
			int cp;
			while ((cp = rd.read()) != -1) {
				sb.append((char) cp);
			}
			JSONObject json = new JSONObject(sb.toString());
			res.close();

			return json.getBoolean("success");
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return false;
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
		boolean persistent = loginResolver.getRequest().getFormData("login_form").get("persistent") == null ? false : loginResolver.getRequest().getFormData("login_form").get("persistent").contentEquals("on") || loginResolver.getRequest().getFormData("login_form").get("persistent").contentEquals("true");

		checkCaptcha(loginResolver);

		if (BizPssConfig.getPssConfig().acceptLoginWithMail()) {
			// Pasar de mail a usuario para logearse con mail
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

  private boolean isValidUser(String username, String password) {
       return true;
  }
  
	protected void addCaptcha(JWebFieldsetResponsive fieldset) throws Exception {
		String ip = (String) getRequest().getModelObject("ip");
		if (BizPssConfig.getPssConfig().getUseCaptcha().equalsIgnoreCase("S") || (BizPssConfig.getPssConfig().getUseCaptcha().equalsIgnoreCase("A") && JTools.isOutAccess(ip))) {
			JWebCaptcha oCpt = new JWebCaptcha();
			fieldset.add("captcha", oCpt);
		}
	}

	protected void checkCaptcha(JAbstractLoginResolver loginResolver) throws Exception {
		String sCaptcha = loginResolver.getRequest().getArgument("g-recaptcha-response");
		if (sCaptcha != null) {
			if (!isCaptchaValid(sCaptcha))
				throw new Exception("CODIGO VERIFICACION INVALIDO");
		}
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

	private void analizeIniConfiguration() throws Exception {
		BizPssConfig cfg = BizPssConfig.getPssConfig();
		String secc = cfg.getCachedStrictValue("GENERAL", "APPLICATION");
		loginTitle = cfg.getCachedValue(secc, "LOGIN_WELCOME_TITLE", "Bienvenido");
		loginRegister = cfg.getCachedValue(secc, "LOGIN_REGISTRESE", "Nuevo Usuario? Registrese");
		loginRecovery = cfg.getCachedValue(secc, "LOGIN_RECUPERAR", "Olvidó su clave?");
		loginTitleLogo = cfg.getCachedValue(secc, "LOGIN_WELCOME_TITLE_LOGO", "15000");
		loginText = cfg.getCachedValue(secc, "LOGIN_WELCOME_TEXT", "Ud ha ingresado al área privada del SITI. Ingrese los datos de su usuario y presione 'confirmar' para comenzar a administrar su Empresa");
		loginFooter = cfg.getCachedValue(secc, "LOGIN_WELCOME_FOOTER", "* Sitio optimizado para : Internet Explorer 7 o superior, Mozilla Firefox 3 o superior");
		loginTextLink = cfg.getCachedValue(secc, "LOGIN_WELCOME_TEXT_LINK", "Pentaware S.A.");
		loginLink = cfg.getCachedValue(secc, "LOGIN_WELCOME_LINK", "http://www.pentaware.com.ar");
	}

	public String getLoginPage() {
		if (JWebActionFactory.isMobile())
			return "mobile-do";
		return "login";
	}
	public String getMessage(String zMsg) throws Exception {
		String msg = BizUsuario.retrieveSkinGenerator().getMessage(zMsg, null);
		return msg;
	}
}
