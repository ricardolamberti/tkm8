package pss.www.ui.views;

import java.util.Date;

import pss.common.regions.company.JCompanyBusiness;
import pss.common.regions.company.JCompanyBusinessModules;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizSegConfiguracion;
import pss.common.security.BizUsuario;
import pss.common.security.tracingUser.BizTracingUser;
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
import pss.www.ui.JWebViewPasswordResponsive;
import pss.www.ui.JWebViewZoneRow;
import pss.www.ui.JWebViewsConstants;

public class JRegistrationView  extends JLoginView implements IActionPerform, ILoginPage {

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

		this.oCompany = new JWebEditResponsive();
		this.oCompany.setLabelLateral(getMessage("Empresa"));
		fieldset.add("empresa",this.oCompany);

		this.oUser = new JWebEditResponsive();
		this.oUser.setLabelLateral(getMessage("Apellido y nombre"));
		fieldset.add("apellido",this.oUser);
		
		this.oEmail = new JWebEditResponsive();
		this.oEmail.setLabelLateral(getMessage("eMail"));
		fieldset.add("email",this.oEmail);
		
//		this.oPais = new JWebComboResponsive();
//		this.oPais.setLabelLateral(getMessage("Pais"));
//		fieldset.add("País",this.oPais);

		oPasswordNew = new JWebViewPasswordResponsive();
		oPasswordNew.setLabelLateral(JLanguage.translate("Contraseña"));
		fieldset.add("new_password",oPasswordNew);
		oPasswordNewConf = new JWebViewPasswordResponsive();
		oPasswordNewConf.setLabelLateral(JLanguage.translate("Re-ingrese Contraseña"));
		fieldset.add("new_password_confirmation",oPasswordNewConf);

		oCompany.setController(new JString(), "Empresa", true, 250, null);
		oUser.setController(new JString(), "Usuario", true, 250, null);
		oEmail.setController(new JString(), "Email", true, 250, null);
//		oPais.setController(new JString(), "Pais", true,15, null, new JControlCombo() {
//			@Override
//			public JWins getRecords() throws Exception {
//				return new GuiPaisesLista();
//			}
//		});
		oPasswordNew.setController(new JPassword(), "Contraseña nueva",  false, BizSegConfiguracion.C_MAX_PASSWORD_LEN, null);
		oPasswordNewConf.setController(new JPassword(), "Contraseña nueva confimación",  false, BizSegConfiguracion.C_MAX_PASSWORD_LEN, null);

		addCaptcha(fieldset);
	
		fieldset.add("separador", formDiv2);


		JWebLink olink = new JWebLink();
		olink = new JWebLink(getMessage("Registrar"));
		olink.setWebAction(JWebActionFactory.createDoRegister());
		olink.setClassResponsive("btn btn-lg btn-primary btn-block");
		olink.setSubmit(true);
		olink.setMode(JWebLink.MODE_BUTTON);

		fieldset.add(getMessage("confirm"), olink);

		
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
	public JWebActionResult registration(JAbstractLoginResolver loginResolver) throws Throwable {
		String sUsername = JTools.HexToAscii(loginResolver.getRequest().getArgument("user"));
		String sCompany = JTools.HexToAscii(loginResolver.getRequest().getArgument("company"));
		String sEmail = JTools.HexToAscii(loginResolver.getRequest().getArgument("email"));
		String sPais = JTools.HexToAscii(loginResolver.getRequest().getArgument("pais"));
		String sKeyEnc = loginResolver.getRequest().getArgument("key");
		BizUsuario user=null;
		String sPassword=null;

		try {
			JAplicacion.GetApp().openSession();
			JAplicacion.GetApp().openApp("register", "Web", true);
			String sKey = JTools.PasswordToString(sKeyEnc);
			BizTracingUser track=BizTracingUser.registerSecond(sEmail,sKey);
			if (track==null) {
				throw new Exception("Acceso inválido");
			}
			
			if (sKey.indexOf(sUsername+"_|_")==-1) {
				track.registerProblem("Clave corrupta");
				throw new Exception("Acceso inválido");
			}
			if (sKey.indexOf(sCompany+"_#_")==-1) {
				track.registerProblem("Clave corrupta");
				throw new Exception("Acceso inválido");
			}

			sUsername = sKey.substring( 0, sKey.indexOf("_|_"));
			sPassword = sKey.substring( sKey.indexOf("_#_") + 3, sKey.indexOf("_*_"));
			String sFecha = sKey.substring(sKey.indexOf("_*_") + 3);
			Date d = JDateTools.StringToDate(sFecha);
	
			int days = JDateTools.getDaysBetween(d, new Date());
			if (days > 1) {
				track.registerProblem("Solicitud vencida ("+JDateTools.DateToString(d)+")");
				throw new Exception("Solicitud vencida");
			}

			BizUsuario admin = new BizUsuario();
			admin.Read(BizUsuario.C_ADMIN_USER);
			BizUsuario.SetGlobal(admin);
			JCompanyBusiness business = JCompanyBusinessModules.createBusinessDefault();
			user = business.execRegisterNewCompany(sUsername, sCompany, sPassword, sEmail, sPais);

			track.registerThird(sEmail,user.GetUsuario(),user.getCompany());

			BizUsuario.SetGlobal(null);
			JAplicacion.GetApp().closeApp();
			JAplicacion.GetApp().closeSession();
		} catch (Exception e) {
			JAplicacion.GetApp().closeApp();
			JAplicacion.GetApp().closeSession();
			throw e;
		}
	
		//return loginResolver.getRedirector().goLoginPage();
		return null;//loginResolver.performLogin(user.GetUsuario(), sPassword, null, null, false);
	}
	public JWebActionResult perform(JAbstractLoginResolver loginResolver) throws Throwable {
		if (loginResolver.getRequest().getArgument("key")!=null) {
			return registration(loginResolver);
		}
		
		String sUsername = loginResolver.getRequest().getFormData("login_form").get("apellido");
		String sCompany = loginResolver.getRequest().getFormData("login_form").get("empresa");
		String sPassword = loginResolver.getRequest().getFormData("login_form").get("new_password");
		String sPasswordRepeat = loginResolver.getRequest().getFormData("login_form").get("new_password_confirmation");
		String sEmail = loginResolver.getRequest().getFormData("login_form").get("email");
		String sPais = "AR";// loginResolver.getRequest().getFormData("login_form").get("pais");
	
		checkCaptcha(loginResolver);
		
		if (!sPassword.equals(sPasswordRepeat)) {
			throw new Exception(getMessage("Contraseñas no coinciden"));

		}
		try {
			JAplicacion.GetApp().openSession();
			JAplicacion.GetApp().openApp("register", "Web", true);
			BizTracingUser track=BizTracingUser.registerIncial(sUsername,sCompany,sEmail,sPais);
			JCompanyBusiness business = JCompanyBusinessModules.createBusinessDefault();
			business.startRegisterNewCompany(sUsername, sCompany, sPassword, sEmail, sPais);
			track.registerSend();
			JAplicacion.GetApp().closeApp();
			JAplicacion.GetApp().closeSession();
		} catch (Exception e) {
			JAplicacion.GetApp().closeApp();
			JAplicacion.GetApp().closeSession();
			throw e;
		}
			
		return loginResolver.getRedirector().goShowMessage(JWebViewsConstants.MESSAGE_TYPE_INFO, "Registración", "Se envió un mail con el link de acceso");
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
