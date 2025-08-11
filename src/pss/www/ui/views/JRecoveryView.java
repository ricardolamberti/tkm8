package pss.www.ui.views;

import java.util.Date;

import pss.JPath;
import pss.common.regions.company.JCompanyBusiness;
import pss.common.regions.company.JCompanyBusinessModules;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizSegConfiguracion;
import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.data.BizPssConfig;
import pss.core.services.fields.JPassword;
import pss.core.services.fields.JString;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.ui.components.JPssImage;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.actions.resolvers.JAbstractLoginResolver;
import pss.www.platform.actions.results.JWebActionResult;
import pss.www.ui.JWebComboResponsive;
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
import pss.www.ui.JWebViewsConstants;

public class JRecoveryView  extends JWebView implements IActionPerform, ILoginPage {

	private JWebEditResponsive oCompany;
	private JWebEditResponsive oEmail;
	private JWebComboResponsive oPais;
  private JWebEditResponsive oUser;
  private JWebViewPasswordResponsive oPasswordNew;
  private JWebViewPasswordResponsive oPasswordNewConf;

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
		JWebImageResponsive imagen = new JWebImageResponsive(JPssImage.SOURCE_PSS,"logos/"+BizPssConfig.getPssConfig().getLogo(),null);
		JWebHResponsive title = new JWebHResponsive(3,getMessage("Inicio de sesión"));
		JWebFieldsetResponsive fieldset = new JWebFieldsetResponsive();
		JWebForm oForm = new JWebForm();
		panel.setClassResponsive("login-panel panel panel-default");
		panelHeading.setClassResponsive("panel-heading");
		panelBody.setClassResponsive("panel-body");
		marco.setClassResponsive("col-xs-offset-0 col-xs-12 col-sm-offset-1 col-sm-10 col-md-offset-2 col-md-8 col-lg-4 col-lg-offset-4");
		fieldset.setClassResponsive("fieldset-soft");
		fieldset.setFormatFields(JFormPanelResponsive.FIELD_LABEL_VERTICAL);
		imagenDiv.setClassResponsive("col-md-offset-2 col-md-8");
		formDiv.setClassResponsive("col-md-12");
		formDiv2.setClassResponsive("fieldset-soft");
		this.add("row",row);
		row.add("marco", marco);
		marco.add("panel",panel);
		panel.add("heading", panelHeading);
		panel.add("body", panelBody);
		panelHeading.add("body", title);
		panelBody.add("imagediv", imagenDiv);
		if (!BizPssConfig.getPssConfig().getLogo().equalsIgnoreCase("nada.gif"))
			imagenDiv.add("image", imagen);
		panelBody.add("login_formdiv", formDiv);
		formDiv.add("login_form", oForm);
		oForm.add("fieldset", fieldset);

		this.oEmail = new JWebEditResponsive();
		this.oEmail.setLabelLateral(getMessage("eMail"));
		fieldset.add("email",this.oEmail);
		oEmail.setController(new JString(), "Email", true, 250, null);

		String captcha = JTools.generateCaptcha();
		String fcaptcha = JTools.buildCaptcha(captcha, 100, 60);

		JWebDivResponsive panelImage = new JWebDivResponsive();
		JWebImageResponsive image = new JWebImageResponsive(JPssImage.SOURCE_URL, fcaptcha, "#");
		image.setHeight(80);
		panelImage.setClassResponsive("col-sm-3 ");
		JWebEditResponsive oCpt = new JWebEditResponsive();
		JWebEditResponsive oCpt2 = new JWebEditResponsive();
		panelImage.add("cap_img", image);
		fieldset.add("pan_img", panelImage);
		fieldset.add("captcha", oCpt);
		fieldset.add("cpt", oCpt2);
		oCpt2.setVisible(false);
		oCpt2.setText(JTools.StringToPassword(captcha));
		oCpt.setSizeResponsive("col-sm-3");
		oCpt.setAutocomplete(false);
		fieldset.add("separador", formDiv2);

		JWebLink olink = new JWebLink();
		olink = new JWebLink(getMessage("Recuperar"));
		olink.setWebAction(JWebActionFactory.createDoRecovery());
		olink.setClassResponsive("btn btn-lg btn-primary btn-block");
		olink.setSubmit(true);
		olink.setMode(JWebLink.MODE_BUTTON);


		fieldset.add(getMessage("confirm"), olink);

		
	}

	@Override
	protected void init(JWebRequest zRequest) throws Exception {
		language = zRequest.getBrowserLanguage();
	}

	@Override
	protected void setUp(JWebRequest zRequest) throws Exception {

	}

	@Override
	protected void generateNavigationBarActions() throws Exception {
		// no navigation actions for the login view, which
		// is the first one !!!!
	}
	public JWebActionResult blanqueo(JAbstractLoginResolver loginResolver) throws Throwable {
			String clave = loginResolver.getRequest().getArgument("key");
			clave = JTools.PasswordToString(clave);
			String usuario = clave.substring(0, clave.indexOf("_|_"));
			String company = clave.substring(clave.indexOf("_|_") + 3, clave.indexOf("_*_"));
			String sFecha = clave.substring(clave.indexOf("_*_") + 3);
			Date d = JDateTools.StringToDate(sFecha);

			int days = JDateTools.getDaysBetween(d, new Date());
			if (days > 1)
				throw new Exception("Solicitud vencida");
			JAplicacion.GetApp().openSession();
			JAplicacion.GetApp().openApp("register", "Web", true);

			BizUsuario user = new BizUsuario();
			user.Read(usuario);
			if (!user.getCompany().equals(company))
				throw new Exception("Solicitud inválida");
			user.execBlanquearClave();
			JAplicacion.GetApp().closeApp();
			JAplicacion.GetApp().closeSession();
		
			return loginResolver.getRedirector().goPasswordChangePageByMail(user.getMailUser());

	}
	public JWebActionResult perform(JAbstractLoginResolver loginResolver) throws Throwable {
		if (loginResolver.getRequest().getArgument("key")!=null) {
			return blanqueo(loginResolver);
		}
		
		String sEmail = loginResolver.getRequest().getFormData("login_form").get("email");
		String sCaptcha = loginResolver.getRequest().getFormData("login_form").get("captcha");
		String captcha = loginResolver.getRequest().getFormData("login_form").get("cpt");
		if (captcha != null) {
			if (!sCaptcha.equals(JTools.PasswordToString(captcha)))
				throw new Exception(getMessage("Fallo verificación contra bots"));
		}
		
		JAplicacion.GetApp().openSession();
		JAplicacion.GetApp().openApp("register", "Web", true);

		JCompanyBusiness business = JCompanyBusinessModules.createBusinessDefault();
		business.recoveryUser(sEmail);

		JAplicacion.GetApp().closeApp();
		JAplicacion.GetApp().closeSession();
			
		return loginResolver.getRedirector().goShowMessage(JWebViewsConstants.MESSAGE_TYPE_INFO, "Recuperación", "Se envió un mail con el link de acceso");
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
		loginTitleLogo = cfg.getCachedValue(secc, "LOGIN_WELCOME_TITLE_LOGO", "15000");
		loginText = cfg.getCachedValue(secc, "LOGIN_WELCOME_TEXT",
				"Ud ha ingresado al área privada del SITI. Ingrese los datos de su usuario y presione 'confirmar' para comenzar a administrar su Empresa");
		loginFooter = cfg.getCachedValue(secc, "LOGIN_WELCOME_FOOTER", "* Sitio optimizado para : Internet Explorer 7 o superior, Mozilla Firefox 3 o superior");
		loginTextLink = cfg.getCachedValue(secc, "LOGIN_WELCOME_TEXT_LINK", "Pentaware S.A.");
		loginLink = cfg.getCachedValue(secc, "LOGIN_WELCOME_LINK", "http://www.pentaware.com.ar");
	}

	public String getLoginPage() {
		return "login";
	}
	public String getMessage(String zMsg) throws Exception {
		String msg = 	BizUsuario.retrieveSkinGenerator().getMessage(zMsg, null);
		return msg;
	}
}
