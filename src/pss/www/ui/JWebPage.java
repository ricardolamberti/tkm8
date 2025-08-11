/*
 * Created on 11-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import java.net.URLDecoder;

import org.apache.commons.lang.CharEncoding;

import pss.common.event.device.BizQueueMessage;
import pss.common.mail.JMail;
import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.processing.JXMLRepresentable;

public class JWebPage implements JXMLRepresentable, JWebViewsConstants {

	// ////////////////////////////////////////////////////////////////////////////
	//
	// INSTANCE VARIABLES
	//  
	// ////////////////////////////////////////////////////////////////////////////
	private JList<JXMLRepresentable> oHeaderComponents;
	private JWebView oView;
	private JWebHelp oHelp;
	private JList<JWebHelpBox> oHelpBox;
//	private JList<JWebPublicity> oPublicity;

	private JWebAction oSourceAction;
	private String sLayoutStereotype;
	
	// para uso en el editor de help
	private String oHelpWin;
	private String oHelpAction;
	private String oHelpStatus;

	public String getHelpWin() {
		return oHelpWin;
	}
	public String getHelpAction() {
		return oHelpAction;
	}
	public String getHelpStatus() {
		return oHelpStatus;
	}

	public void setSaveHelp(String oHelpWin,String oHelpAction,String status) {
		this.oHelpWin=oHelpWin;
		this.oHelpAction=oHelpAction;
		this.oHelpStatus=status;
	}

	// ////////////////////////////////////////////////////////////////////////////
	//
	// CONSTRUCTORS
	//  
	// ////////////////////////////////////////////////////////////////////////////
	public JWebPage() {
		this.oHeaderComponents = JCollectionFactory.createList();
		this.oHelpBox = JCollectionFactory.createList();
//		this.oPublicity = JCollectionFactory.createList();
	}

	// ////////////////////////////////////////////////////////////////////////////
	//
	// PUBLIC API
	//  
	// ////////////////////////////////////////////////////////////////////////////

	public void addHeaderComponent(JXMLRepresentable zComponent) {
		this.oHeaderComponents.addElement(zComponent);
	}

	public void setUp(JWebRequest zRequest) throws Exception {
		if (this.oView != null) {
			// setup the view
			this.oView.setUpFrom(zRequest);
		}
	}

	public void toXML(JXMLContent zContent) throws Exception {
		zContent.startNode("page");

		//
		// SERIALIZE THE HEADER
		//
		zContent.startNode("header");
		// set the page stereotype
		if (this.sLayoutStereotype == null) {
			throw new RuntimeException("Page layout stereotype not set");
			// } else if
			// (!this.sLayoutStereotype.equals(PAGE_LAYOUT_APP_FRONT_DOOR)
			// && !this.sLayoutStereotype.equals(PAGE_LAYOUT_APP_INDOORS)) {
			// throw new RuntimeException("Unknown page layout stereotype: " +
			// this.sLayoutStereotype);
		} else {
			zContent.setAttribute("stereotype", this.sLayoutStereotype);
			zContent.setAttribute("version", BizPssConfig.getVersionJS());
		}
		if (BizUsuario.getUsr()!=null)
			zContent.setAttribute("accept_comma",BizUsuario.getUsr().getObjBusiness().isAcceptComma());
		else
			zContent.setAttribute("accept_comma",false);
		zContent.setAttribute("ajax_container",JWebActionFactory.getCurrentRequest().getAjaxContainer());
		zContent.setAttribute("google_captcha_key",BizPssConfig.getPssConfig().getGoogleCaptcha());
		
		if (/*JAplicacion.GetApp()!=null && JAplicacion.GetApp().getQuestionHelp()!=null &&*/ BizPssConfig.getPssConfig().hasHelpEditor()) {
			zContent.startNode("savehelp");
			zContent.setAttribute("win", getHelpWin());
			zContent.setAttribute("action", getHelpAction());
			zContent.setAttribute("status", getHelpStatus());
			zContent.endNode("savehelp");
		}
		
		// serialize header components
		JIterator<JXMLRepresentable> oHeaderComponentsIt = this.oHeaderComponents.getIterator();
		while (oHeaderComponentsIt.hasMoreElements()) {
			oHeaderComponentsIt.nextElement().toXML(zContent);
		}
		
		JWebAction oSrcAction = this.getSourceAction();
		if (oSrcAction != null) {
			oSrcAction.toXML(zContent);
		}
		zContent.endNode("header");
		
		boolean msgEmerg= false;
		if (BizUsuario.getUsr()!=null) {
			BizQueueMessage msg = BizUsuario.getUsr().getMessageForWeb();
			if (msg!=null) {
				zContent.startNode("message");
				zContent.setAttribute("urgent_type",msg.getType());
			  zContent.setAttribute("urgent_permanent",msg.isPermanent());
				zContent.addTextNode("urgent_title",JTools.replaceForeignCharsForWeb(msg.getTitle()));
				zContent.addTextNode("urgent_message",JTools.replaceForeignCharsForWeb(URLDecoder.decode(msg.getInfo(), CharEncoding.ISO_8859_1)));
				if (msg.hasLink()) {
					JWebAction act = JWebActionFactory.createViewAreaAntTitleAction(msg.getLink());
					act.toXML(zContent);
				} 
				zContent.endNode("message");
				msgEmerg= true;
			}
			
				
		}

		if (!msgEmerg&&BizUsuario.getUsr()!=null&&BizUsuario.getUsr().getObjBusiness().hasMailIcon() && JMail.getMyMail().hasUrgentMail() ) {
			zContent.startNode("message");
			zContent.addTextNode("urgent_title",JMail.getMyMail().getUrgentMail().getSender()+": "+JMail.getMyMail().getUrgentMail().getTitle());
			zContent.addTextNode("urgent_type","success");
			try {
				zContent.addTextNode("urgent_message",JTools.decodeIso(JMail.getMyMail().getUrgentMail().getMessage()));// el decode es explosivo y temperamental
			} catch (Exception e) {
				zContent.addTextNode("urgent_message",JMail.getMyMail().getUrgentMail().getMessage());
			}
			JWebAction act = JWebActionFactory.createHideMail();
			act.toXML(zContent);
			zContent.endNode("message");
		}

		//
		// SERIALIZE THE VIEW
		//
		if (this.oView != null) {
			long start = System.currentTimeMillis();
			this.oView.componentPreLayout(zContent);
			this.oView.toXML(zContent);
			PssLogger.logWait("toXML took:"+ (System.currentTimeMillis() - start));
		}

		if (this.oHelp!= null ) {
			zContent.startNode("help_form");
			this.oHelp.toXML(zContent);
			zContent.endNode("help_form");
		}
		if (this.oHelpBox!=null) {
			
			zContent.startNode("help");
			// serialize help box components
			JIterator<JWebHelpBox> oHelpBoxIt = this.oHelpBox.getIterator();
			while (oHelpBoxIt.hasMoreElements()) {
				oHelpBoxIt.nextElement().toXML(zContent);
			}

			zContent.endNode("help");
		}

//		if (this.oPublicity!=null && this.oPublicity.size() != 0) {
//			zContent.startNode("publicity_campaign");
//
//			// serialize publicity components
//			JIterator<JWebPublicity> oPublicityIt = this.oPublicity.getIterator();
//			while (oPublicityIt.hasMoreElements()) {
//				oPublicityIt.nextElement().toXML(zContent);
//			}
//
//			zContent.endNode("publicity_campaign");
//		}
		zContent.startNode("url");
		zContent.setAttribute("location", this.getSourceAction().asURL());
		zContent.setAttribute("modal", this.getSourceAction().isOpenInModal());
		zContent.setAttribute("payload", JWebActionFactory.getCurrentRequest().getPack().getRegisterObjectsSerialized());
		zContent.endNode("url");	
		//
		// SERIALIZE REGISTERED OBJECTS
		//
		zContent.endNode("page");

	}

	public void destroy() {
		if (this.oHeaderComponents != null) {
			JIterator<JXMLRepresentable> oCompsIt = this.oHeaderComponents.getIterator();
			JXMLRepresentable oComp;
			while (oCompsIt.hasMoreElements()) {
				oComp = oCompsIt.nextElement();
				oComp.destroy();
				oCompsIt.remove();
			}
			this.oHeaderComponents = null;
		}
		if (this.oHelpBox != null) {
			JIterator<JWebHelpBox> oCompsIt = this.oHelpBox.getIterator();
			JXMLRepresentable oComp;
			while (oCompsIt.hasMoreElements()) {
				oComp = oCompsIt.nextElement();
				oComp.destroy();
				oCompsIt.remove();
			}
			this.oHelpBox = null;
		}
//		if (this.oPublicity != null) {
//			JIterator<JWebPublicity> oCompsIt = this.oPublicity.getIterator();
//			JXMLRepresentable oComp;
//			while (oCompsIt.hasMoreElements()) {
//				oComp = oCompsIt.nextElement();
//				oComp.destroy();
//				oCompsIt.remove();
//			}
//			this.oPublicity = null;
//		}
		if (this.oView != null) {
			this.oView.destroy();
			this.oView = null;
		}
		if (this.oHelp != null) {
			this.oHelp.destroy();
			this.oHelp = null;
		}
		if (this.oSourceAction != null) {
			this.oSourceAction.destroy();
			this.oSourceAction = null;
		}
		this.sLayoutStereotype = null;
	}

	public JWebView getView() {
		return this.oView;
	}

	public void setView(JWebView view) {
		this.oView = view;
	}

	public JWebAction getSourceAction() {
		return this.oSourceAction;
	}

	public void setSourceAction(JWebAction action) {
		this.oSourceAction = action;
	}

	public String getLayoutStereotype() {
		return this.sLayoutStereotype;
	}

	public void setLayoutStereotype(String string) {
		this.sLayoutStereotype = string;
	}

	public JWebHelp getHelp() {
		return oHelp;
	}

	public void setHelp(JWebHelp help) {
		oHelp = help;
	}

	public JList<JWebHelpBox> getHelpBox() {
		return oHelpBox;
	}

	public void setHelpBox(JList<JWebHelpBox> helpBox) {
		oHelpBox = helpBox;
	}
//
//	public JList<JWebPublicity> getPublicity() {
//		return oPublicity;
//	}
//
//	public void setPublicity(JList<JWebPublicity> publicity) {
//		oPublicity = publicity;
//	}

	


	
	
}
