package pss.www.ui.views;

import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizSegConfiguracion;
import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.tools.JTools;
import pss.core.ui.components.JPssImage;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.actions.resolvers.JAbstractLoginResolver;
import pss.www.platform.actions.results.JWebActionResult;
import pss.www.ui.JWebDivResponsive;
import pss.www.ui.JWebEditResponsive;
import pss.www.ui.JWebFieldsetResponsive;
import pss.www.ui.JWebForm;
import pss.www.ui.JWebImageResponsive;
import pss.www.ui.JWebLink;
import pss.www.ui.JWebTextField;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewPasswordResponsive;
import pss.www.ui.JWebViewZoneRow;

public class JLoginEmbbededView extends JWebView implements IActionPerform, ILoginPage {

	private JWebEditResponsive oUser;
	private String language;
	private String loginTitle;
	private String loginTitleLogo;
	private String loginText;
	private String loginFooter;
	private String loginTextLink;
	private String loginLink;
	 static long cont=0;
	 

	@Override
	protected void build() throws Exception {

		analizeIniConfiguration();
		
		JLanguage l = JLanguage.getInstance();
		l.setLanguageFromBrowser(language);
		
		JWebViewZoneRow row = new JWebViewZoneRow();
		JWebDivResponsive marco = new JWebDivResponsive();
		JWebDivResponsive panel = new JWebDivResponsive();
		JWebDivResponsive panelBody = new JWebDivResponsive();
		JWebFieldsetResponsive fieldset = new JWebFieldsetResponsive();
		JWebForm oForm = new JWebForm();

		panel.setClassResponsive("panel panel-default");
		panelBody.setClassResponsive("panel-body");
		marco.setClassResponsive("col-xs-offset-0 col-xs-12 col-sm-offset-1 col-sm-10 col-md-offset-2 col-md-8 col-lg-4 col-lg-offset-4");
		fieldset.setClassResponsive("fieldset-soft");
		
		this.add("row",row);
		row.add("marco", marco);
		marco.add("panel",panel);
		panel.add("body", panelBody);
		panelBody.add("login_form", oForm);
		oForm.add("fieldset", fieldset);

		this.oUser = new JWebEditResponsive();
		this.oUser.setLabelLateral(getMessage("Usuario"));
		fieldset.add("username",this.oUser);

		JWebViewPasswordResponsive oPassword = new JWebViewPasswordResponsive();
		oPassword.setLabelLateral(getMessage("Password"));
		fieldset.add("password",oPassword);

		oUser.setController(new JString(), "Usuario", true, BizSegConfiguracion.C_MAX_USERNAME_LENGTH, JObject.JUPPERCASE);
		oPassword.setController(new JString(), "Password",  false, BizSegConfiguracion.C_MAX_PASSWORD_LEN, null);

		String ip = (String)getRequest().getModelObject("ip");

		if ( BizPssConfig.getPssConfig().getUseCaptcha().equalsIgnoreCase("S") ||
				(BizPssConfig.getPssConfig().getUseCaptcha().equalsIgnoreCase("A") && JTools.isOutAccess(ip))) {
			String captcha = JTools.generateCaptcha();
			String fcaptcha = JTools.buildCaptcha(captcha,75,40);
			JWebImageResponsive image = new JWebImageResponsive(JPssImage.SOURCE_URL,fcaptcha,"#");
			image.setSize(75,40);
			
			JWebTextField oCpt = new JWebTextField();
			JWebTextField oCpt2 = new JWebTextField();
			fieldset.add("cap_img", image);
			fieldset.add("captcha", oCpt);
			fieldset.add("cpt", oCpt2);
			oCpt2.setVisible(false);
			oCpt2.setText(JTools.StringToPassword(captcha));
		}
		
		JWebLink olink = new JWebLink();
		olink = new JWebLink(getMessage("Ingresar"));
		olink.setWebAction(JWebActionFactory.createDoLogin());
		olink.setClassResponsive("btn btn-lg btn-primary btn-block");
		olink.setSubmit(true);
		olink.setMode(JWebLink.MODE_BUTTON);
		
		fieldset.add(getMessage("confirm"), olink );

		l.removeLanguageFromBrowser();

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
		String sUsername = JTools.replace(loginResolver.getRequest().getFormData("login_form").get("username").toUpperCase(),"-","");
		String sPassword = loginResolver.getRequest().getFormData("login_form").get("password");
		String sBase = loginResolver.getRequest().getFormData("login_form").get("base");
	  String sCaptcha = loginResolver.getRequest().getFormData("login_form").get("captcha");
	  String captcha = loginResolver.getRequest().getFormData("login_form").get("cpt");

	  if (captcha!=null) {
	  	if (!sCaptcha.equals(JTools.PasswordToString(captcha))) throw new Exception(getMessage("Fallo verificación contra bots"));
	  }
		return loginResolver.performLogin(sUsername, sPassword, sBase, null, false);
	}
	
	
//	LOGIN_WELCOME_TITLE=Bienvenido
//	LOGIN_WELCOME_TITLE_LOGO=15000
//	LOGIN_WELCOME_TEXT=Ud ha ingresado al área privada del SITI. Ingrese los datos de su usuario y presione 'confirmar' para comenzar a administrar su Empresa
//	LOGIN_WELCOME_FOOTER=* Sitio optimizado para : Internet Explorer 7 o superior, Mozilla Firefox 3 o superior
//	LOGIN_WELCOME_TEXT_LINK=Pentaware S.A.
//	LOGIN_WELCOME_LINK=http://www.pentaware.com.ar

	private void analizeIniConfiguration() throws Exception {
		BizPssConfig cfg = BizPssConfig.getPssConfig();
		String secc = cfg.getCachedStrictValue("GENERAL", "APPLICATION");
		loginTitle = cfg.getCachedValue(secc, "LOGIN_WELCOME_TITLE" , "Bienvenido");
		loginTitleLogo = cfg.getCachedValue(secc, "LOGIN_WELCOME_TITLE_LOGO", "15000" );
		loginText = cfg.getCachedValue(secc, "LOGIN_WELCOME_TEXT" ,"Ud ha ingresado al área privada del SITI. Ingrese los datos de su usuario y presione 'confirmar' para comenzar a administrar su Empresa");
		loginFooter = cfg.getCachedValue(secc, "LOGIN_WELCOME_FOOTER" , "* Sitio optimizado para : Internet Explorer 7 o superior, Mozilla Firefox 3 o superior");
		loginTextLink = cfg.getCachedValue(secc, "LOGIN_WELCOME_TEXT_LINK" , "Pentaware S.A.");
		loginLink = cfg.getCachedValue(secc, "LOGIN_WELCOME_LINK", "http://www.pentaware.com.ar" );
	}


	public String getLoginPage() {
		return "login";
	}
	public String getMessage(String zMsg) throws Exception {
		String msg = 	BizUsuario.retrieveSkinGenerator().getMessage(zMsg, null);
		return msg;
	}


}
