package pss.www.ui.views;

import java.awt.Color;

import pss.common.customLogin.BizCustomLogin;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizSegConfiguracion;
import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.tools.JTools;
import pss.core.tools.collections.JMap;
import pss.core.ui.components.JPssImage;
import pss.core.win.JWins;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.actions.resolvers.JAbstractLoginResolver;
import pss.www.platform.actions.results.JWebActionResult;
import pss.www.ui.JWebCheckResponsive;
import pss.www.ui.JWebDivResponsive;
import pss.www.ui.JWebEditResponsive;
import pss.www.ui.JWebElementList;
import pss.www.ui.JWebFieldsetResponsive;
import pss.www.ui.JWebForm;
import pss.www.ui.JWebHResponsive;
import pss.www.ui.JWebIcon;
import pss.www.ui.JWebImageResponsive;
import pss.www.ui.JWebLink;
import pss.www.ui.JWebTextField;
import pss.www.ui.JWebUnsortedList;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewPasswordResponsive;
import pss.www.ui.JWebViewZoneRow;


public class JExtendedLoginView extends JWebView  implements IActionPerform,ILoginPage {

  private JWebEditResponsive oUser;
	private String language;
	private String logo;
	private String loginTitle;
	private String loginTitleLogo;
	private String loginText;
	private String loginFooter;
	private String loginTextLink;
	private String loginLink;
	private String leyendaUsuario;
	private String leyendaClave;
	private String leyendaBoton;
	private String secondaryTitle;
	
	private Color zoneABackColor;
	private String zoneAImage;
	private Color zoneBBackColor;
	private String zoneBImage;
	
	private Color secondaryTitleColor;
	private Color itemsColor;
	private String[] items;
	private boolean hasCaptcha;
	private BizCustomLogin customLogin;
	private String style;
	
	public BizCustomLogin getCustomLogin() throws Exception {
		if (customLogin!=null) return customLogin;
		customLogin = BizCustomLogin.getDefaultCustomLogin();
		return customLogin;
	}
	
	private void analizeIniConfiguration() throws Exception {
		BizPssConfig cfg = BizPssConfig.getPssConfig();
		String secc = cfg.getCachedStrictValue("GENERAL", "APPLICATION");
		loginTitle = cfg.getCachedValue(secc, "LOGIN_WELCOME_TITLE" , "Bienvenido");
		loginTitleLogo = cfg.getCachedValue(secc, "LOGIN_WELCOME_TITLE_LOGO", "15000" );
		loginText = cfg.getCachedValue(secc, "LOGIN_WELCOME_TEXT" ,"Ud ha ingresado al área privada del SITI. Ingrese los datos de su usuario y presione 'confirmar' para comenzar a administrar su Empresa");
		loginFooter = cfg.getCachedValue(secc, "LOGIN_WELCOME_FOOTER" , "* Sitio optimizado para : Internet Explorer 7 o superior, Mozilla Firefox 3 o superior");
		loginTextLink = cfg.getCachedValue(secc, "LOGIN_WELCOME_TEXT_LINK" , "Pentaware S.A.");
		loginLink = cfg.getCachedValue(secc, "LOGIN_WELCOME_LINK", "http://www.pentaware.com.ar" );
		leyendaClave = "Password";
		leyendaUsuario = "Usuario";
		leyendaBoton = "Ingresar";
		zoneABackColor = null;
		zoneBBackColor = new Color(255,255,255);
		secondaryTitleColor = new Color(255,255,255);
		itemsColor = new Color(255,255,255);

		zoneAImage = "pss_icon/backgrounds/fondotkm.3.png";
		zoneBImage = "";
		secondaryTitle= cfg.getCachedValue(secc, "LOGIN_SECONDARY_TITLE", "Ticketmining 7" );
		items = new String[3];
		items[0]= cfg.getCachedValue(secc, "LOGIN_ITEM_1", "Nuevo editor DM" );
		items[1]= cfg.getCachedValue(secc, "LOGIN_7ITEM_2", "Más control" );
		items[2]= cfg.getCachedValue(secc, "LOGIN_ITEM_3", "Interfaz más sencilla" );
		logo=BizPssConfig.getPssConfig().getLogo();
		String ip = (String)getRequest().getModelObject("ip");
		style = "";
		hasCaptcha =  BizPssConfig.getPssConfig().getUseCaptcha().equalsIgnoreCase("S") ||	(BizPssConfig.getPssConfig().getUseCaptcha().equalsIgnoreCase("A") && JTools.isOutAccess(ip));
	}
	private boolean analizeBaseConfiguration() throws Exception {
//		if ( !BizPssConfig.getPssConfig().getUseDatabase()) return false;
//		BizCustomLogin cfg = getCustomLogin();
//		if (cfg==null) return false;
//		loginTitle = cfg.getWelcome();
//		loginTitleLogo = ""+cfg.getTitleLogo();
//		loginText = cfg.getWelcomeText();
//		loginFooter = cfg.getFooterText();
//		loginTextLink = cfg.getTextLink();
//		loginLink = cfg.getLink();
//		leyendaClave = cfg.getLeyendaClave();
//		leyendaUsuario = cfg.getLeyendaUsuario();
//		leyendaBoton = cfg.getLeyendaBoton();
//		hasCaptcha = cfg.hasCaptcha();
//		secondaryTitle = cfg.getSecondaryTitle();
//		logo = cfg.getLogo();
//		zoneABackColor = cfg.hasBackgroundColorA()?cfg.getBackgroundColorA():null;
//		zoneBBackColor = cfg.hasBackgroundColorA()?cfg.getBackgroundColorB():null;;
//		zoneAImage = cfg.hasBackgroundImageA()?cfg.getBackgroundImageA():null;//;
//		zoneBImage = cfg.hasBackgroundImageB()?cfg.getBackgroundImageB():null;;
//		secondaryTitleColor = new Color(255,255,255);
//		itemsColor = new Color(255,255,255);
//		items = cfg.getListItems();
//		style =cfg.getStyle();
//		return true;
		return false;
	}

	private void addNews(JWebDivResponsive zoneA)  throws Exception  {
		JWebUnsortedList ul = new JWebUnsortedList();
		
		
		JWebHResponsive infozone = new JWebHResponsive(1,secondaryTitle);
		infozone.setForeground(secondaryTitleColor);

		infozone.setClassResponsive("text-center");
		ul.setClassResponsive("fa-ul");

		zoneA.add("infozone", infozone);
		zoneA.add("info",ul);

		int i=1;
		for(String item:items) {
			if (item==null||item.equals("")) continue;
			JWebElementList li = new JWebElementList();
			li.setClassResponsive("col-md-6 col-md-offset-3 ");
			ul.add("infoli"+i,li);
			JWebHResponsive litext = new JWebHResponsive(2,item);
			litext.setForeground(itemsColor);
			litext.setIcon(JWebIcon.getResponsiveIcon("fa-li fa fa-check"));
			li.add("tkm"+i, litext);
			i++;
			
		}
	}
  
  @Override
	protected void build() throws Exception {
  	if (!analizeBaseConfiguration())
  		analizeIniConfiguration();
		
		JLanguage l = JLanguage.getInstance();
		l.setLanguageFromBrowser(language);
		
		JWebViewZoneRow row = new JWebViewZoneRow();
		JWebDivResponsive zoneA = new JWebDivResponsive();
		JWebDivResponsive zoneB = new JWebDivResponsive();
		JWebDivResponsive marco = new JWebDivResponsive();
		JWebDivResponsive panel = new JWebDivResponsive();
		JWebDivResponsive panelHeading = new JWebDivResponsive();
		JWebDivResponsive panelBody = new JWebDivResponsive();
		JWebImageResponsive imagen = new JWebImageResponsive(JPssImage.SOURCE_PSS,"logos/"+logo,null);
		JWebHResponsive title = new JWebHResponsive(3,getMessage(loginTitle));
		JWebFieldsetResponsive fieldset = new JWebFieldsetResponsive();
		JWebForm oForm = new JWebForm();

		zoneB.setClassResponsive("col-sm-6 col-xs-12 panel-back-right");
		if (zoneBBackColor!=null) zoneA.setBackground(zoneBBackColor);
		if (zoneBImage!=null) {
			zoneB.setBackgroundImage(zoneBImage);
			if (style.indexOf("contain")!=-1) {
				zoneB.setBackgroundSize("contain");
			}
			zoneB.setBackgroundPosition("center");
		}
		zoneA.setClassResponsive("col-sm-6 hidden-xs panel-back-left");
		if (zoneABackColor!=null) zoneA.setBackground(zoneABackColor);
		if (zoneAImage!=null){
			zoneA.setBackgroundImage(zoneAImage);
			if (style.indexOf("contain")!=-1) {
				zoneA.setBackgroundSize("contain");
			}
			zoneA.setBackgroundPosition("center");
		}
		panel.setClassResponsive("panel");
		panelHeading.setClassResponsive("panel-heading");
		panelBody.setClassResponsive("panel-body");
		marco.setClassResponsive("col-xs-offset-0 col-xs-12 col-sm-offset-1 col-sm-10 col-md-offset-2 col-md-8 ");
		if (zoneBBackColor!=null) marco.setBackground(zoneBBackColor);

		fieldset.setClassResponsive("fieldset-soft");
		if (!BizPssConfig.getPssConfig().getLogo().equalsIgnoreCase("nada.gif"))
			imagen.setClassResponsive("img-responsive center-block");


		this.add("row",row);
		row.add("zone_a", zoneA);
		row.add("zone_b", zoneB);
		addNews(zoneA);
		zoneB.add("marco", marco);
		marco.add("panel",panel);
		panel.add("heading", panelHeading);
		if (zoneBBackColor!=null) panel.setBackground(zoneBBackColor);
		panel.add("body", panelBody);
//		panelHeading.add("body", title);
		if (!BizPssConfig.getPssConfig().getLogo().equalsIgnoreCase("nada.gif"))
			panelBody.add("image", imagen);
		panelBody.add("login_form", oForm);
		if (zoneBBackColor!=null) panelBody.setBackground(zoneBBackColor);
		oForm.add("fieldset", fieldset);

		fieldset.add("title", title);
		if (zoneBBackColor!=null) fieldset.setBackground(zoneBBackColor);
		


		this.oUser = new JWebEditResponsive();
		this.oUser.setLabelLateral(getMessage(leyendaUsuario));
		fieldset.add("username",this.oUser);

		JWebViewPasswordResponsive oPassword = new JWebViewPasswordResponsive();
		oPassword.setLabelLateral(getMessage(leyendaClave));
		fieldset.add("password",oPassword);

		oUser.setController(new JString(), "Usuario", true, BizSegConfiguracion.C_MAX_USERNAME_LENGTH, JObject.JUPPERCASE);
		oPassword.setController(new JString(), "Password",  false, BizSegConfiguracion.C_MAX_PASSWORD_LEN, null);


		if ( hasCaptcha) {
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
		olink = new JWebLink(getMessage(leyendaBoton));
		olink.setWebAction(JWebActionFactory.createDoLogin());
		olink.setClassResponsive("btn btn-lg btn-primary btn-block");
		olink.setSubmit(true);
		olink.setMode(JWebLink.MODE_BUTTON);
		
		fieldset.add(getMessage("confirm"), olink );
		if (BizPssConfig.getPssConfig().askPersistentLogin()) {
			JWebCheckResponsive oPersistent = new JWebCheckResponsive();
//			oPersistent.setMode(JFormCheckResponsive.MODE_SINGLE);
			oPersistent.setLabelLateral("Recordar?");
			oPersistent.setClassResponsive("col-sm-12");
		
			fieldset.add("persistent", oPersistent);
		}
		
		l.removeLanguageFromBrowser();

    
    
    
  }
  
	@Override
	protected void init(JWebRequest zRequest) throws Exception {
		language = zRequest.getBrowserLanguage();
	}
	private JMap<String,String> fillGlobalDatabaseMap() throws Exception {
		return JBDatos.getDatabaseList();
	}
	
  private JWins getBases(boolean oneRow) throws Exception {
  	return JWins.createVirtualWinsFromMap(fillGlobalDatabaseMap());
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
	  String sUsername = loginResolver.getRequest().getFormData("login_form").get("username").toUpperCase();
	  String sPassword = loginResolver.getRequest().getFormData("login_form").get("password");
	  String sBase = loginResolver.getRequest().getFormData("login_form").get("base");
	  boolean persistent = loginResolver.getRequest().getFormData("login_form").get("persistent")==null?false:
			loginResolver.getRequest().getFormData("login_form").get("persistent").contentEquals("on")||
			loginResolver.getRequest().getFormData("login_form").get("persistent").contentEquals("true")
			;
	  return loginResolver.performLogin(sUsername, sPassword, sBase, null, persistent);
	}

	@Override
	public String getLoginPage() {
		return "do";
	}
	public String getMessage(String zMsg) throws Exception {
		String msg = 	BizUsuario.retrieveSkinGenerator().getMessage(zMsg, null);
		return msg;
	}
}
