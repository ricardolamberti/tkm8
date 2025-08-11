/*
 * Created on 05-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.generators;

import java.io.Serializable;

import org.apache.cocoon.environment.Response;

import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.win.JBaseWin;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.users.history.BizUserHistory;
import pss.www.ui.JWebHelp;
import pss.www.ui.JWebHelpBox;
import pss.www.ui.JWebPage;
import pss.www.ui.JWebPublicity;
import pss.www.ui.JWebView;
import pss.www.ui.controller.JWebClientConfiguration;
import pss.www.ui.skins.JLayoutGenerator;

public abstract class JXMLPageGenerator extends JBasicXMLContentGenerator {

	private JWebPage oPage;
	String ipaddress;
	
	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	
	public void setSaveHelp(String oHelpWin,String oHelpAction,String status) {
		if (oPage==null) return;
		oPage.setSaveHelp(oHelpWin,oHelpAction, status);
	}



	@Override
	public boolean isPageGenerator() {
		return true;
	}

	synchronized String registerObject(JBaseWin zObject) throws Exception {
		return oRequest.registerObject(zObject);
	}
	synchronized String registerObject(String pos,JBaseWin zObject) throws Exception {
		return oRequest.registerObject(pos,zObject);
	}
	synchronized String registerObjectObjTemp(Serializable zObject) throws Exception {
		return oRequest.registerObjectTemp(zObject);
	}
	synchronized String registerObjectObj(Serializable zObject) throws Exception {
		return oRequest.registerObjectObj(zObject);
	}
	synchronized Object getRegisterObjectObj(String pos) throws Exception {
		return oRequest.getRegisterObject(pos);
	}

	@Override
	protected void doGenerate() throws Exception {
		this.oPage=this.createPage();	
		if (getSession()==null ||  getSession().getHistoryManager().getLastSubmit()==null)
			oPage.setSourceAction(JWebActionFactory.getSourceActionFor(this.getRequest()));
		else
			oPage.setSourceAction(JWebActionFactory.getSourceActionForHistory(this.getRequest()));
				// build view
		BizUsuario.saveSkin(this.getSkinProvider().getSkinFor(this.getSession()));
		JWebView oView=this.createView();
		if (oView==null) oView=this.createNewView();
		if (oView!=null) {
			getRequest().addModelObject("ip", getIpaddress());
			oView.setRequest(getRequest());
			oPage.setView(oView);
			oView.setUICoordinator(this.getRequest().getUICoordinator());
			renderHelp(  );
			// auto-refresh
			if (oView.isAutoRefresh()) {
				int iMinutes;
				if (this.getSession()==null) {
					iMinutes=10;
				} else {
					iMinutes=this.getSession().getTimeout()*2/3;
				}
				this.oResponse.setHeader("Refresh", String.valueOf(60*iMinutes));
			}
		}
		// set up the page from the request
		oPage.setUp(this.getRequest());
		// serialize the page !!
		oPage.toXML(this.asXMLContent());
		
		
		this.endGenerator();
	}

	private void endGenerator() throws Exception {
		if (this.getSession()==null) return;
		BizUserHistory last = this.getSession().getStadistic(getRequest().getRequestid());
		if (last==null) return;
		last.setTimeGenerator(last.getDelta());
		last.setFinalize(true);
	}
	private void renderHelp() throws Exception {
		JWebHelp oHelp=this.createHelp();
		if (oHelp!=null) {
			oPage.setHelp(oHelp);
			oHelp.setUICoordinator(this.getRequest().getUICoordinator());
		}
		JList<JWebHelpBox> helpboxs=this.createArrayHelpBox();
		if (helpboxs!=null) {
			oPage.setHelpBox(helpboxs);
			if (oPage.getHelpBox()!=null) {
				JIterator<JWebHelpBox> oCompsIt=oPage.getHelpBox().getIterator();
				JWebHelpBox oComp;
				while (oCompsIt.hasMoreElements()) {
					oComp=oCompsIt.nextElement();
					oComp.setUICoordinator(this.getRequest().getUICoordinator());
				}
			}
		}

//		JList<JWebPublicity> publicities=this.createArrayPublicity();
//		if (publicities!=null) {
//			oPage.setPublicity(publicities);
//			if (oPage.getPublicity()!=null) {
//				JIterator<JWebPublicity> oCompsIt=oPage.getPublicity().getIterator();
//				JWebPublicity oComp;
//				while (oCompsIt.hasMoreElements()) {
//					oComp=oCompsIt.nextElement();
//					oComp.setUICoordinator(this.getRequest().getUICoordinator());
//				}
//			}
//		}
	
	}

	@Override
	protected void cleanUp() {
		if (this.oPage!=null) {
			if (!this.isServerSideCacheable()) {
				this.oPage.destroy();
			}
			this.oPage=null;
		}
		super.cleanUp();
	}

	protected JWebPage createPage() throws Exception {
		JWebPage oPage=new JWebPage();
		oPage.setLayoutStereotype(this.getPageLayoutStereotype());
		// build header
		oPage.addHeaderComponent(new JApplicationInfoGenerator());
		oPage.addHeaderComponent(new JSessionInfoGenerator());
		oPage.addHeaderComponent(new JLayoutGenerator());
		// oPage.addHeaderComponent(new
		// JWaitingPane(this.getSkinProvider().getSkinFor(this.getSession())));
		// oPage.addHeaderComponent(new JWaitingPane());

		// oPage.addHeaderComponent(this.getSkinProvider().getSkinFor(this.getSession()).toXML());
		//
		return oPage;
	}

	@Override
	protected void setResponseHeaders(Response zResponse) throws Exception {
		if (JWebActionFactory.getSerializerFor(this.oRequest).equalsIgnoreCase("mobile") ||
				JWebActionFactory.getSerializerFor(this.oRequest).equalsIgnoreCase("html") ||
				JWebActionFactory.getSerializerFor(this.oRequest).equalsIgnoreCase("graph") ||
				JWebActionFactory.getSerializerFor(this.oRequest).equalsIgnoreCase("map")) {
			if (JWebClientConfiguration.getCurrent().acceptsGZip()) {
				zResponse.addHeader("Content-Encoding", "gzip");
			}
		}
  	if (getRequest().isMobile() && getRequest().getURI().indexOf("jsessionid")!=-1) {
			zResponse.addHeader("set-mobile-cookie", "JSESSIONID="+ getRequest().getURI().substring(getRequest().getURI().indexOf("jsessionid")+11)+"; Path=/"+BizPssConfig.getPssConfig().getAppURLPrefix()+"; Secure; HttpOnly");
	  	if (getSession()!=null&&getSession().getPersistentKey()!=null) {
	  		zResponse.addHeader("set-mobile-persistent-cookie", "JPERSISTENTSESSIONID="+ getSession().getPersistentKey()+"; Max-Age="+(60*60*24*14)+" ;Path=/"+BizPssConfig.getPssConfig().getAppURLPrefix()+"; Secure; HttpOnly");
	  	}
  	}
  	if (getSession()!=null&&getSession().getPersistentKey()!=null) {
			zResponse.addHeader("set-cookie", "JPERSISTENTSESSIONID="+ getSession().getPersistentKey()+"; Max-Age="+(60*60*24*14)+" ;Path=/"+BizPssConfig.getPssConfig().getAppURLPrefix()+"; Secure; HttpOnly");
  	}
		super.setResponseHeaders(zResponse);
	}

	private JWebView createNewView() throws Exception {
		Class<JWebView> viewClass=this.getViewClass();
		if (viewClass==null) return null;
		return viewClass.newInstance();
	}

	//
	// METHODS TO OVERRIDE IN SUBCLASSES
	//
	protected Class<JWebView> getViewClass() throws Exception {
		return null;
	}

	protected JWebView createView() throws Exception {
		return null;
	}

	protected JWebHelp createHelp() throws Exception {
		return null;
	}

	protected JList<JWebHelpBox> createArrayHelpBox() throws Exception {
		return null;
	}

	protected JList<JWebPublicity> createArrayPublicity() throws Exception {
		return null;
	}

	protected abstract String getPageLayoutStereotype() throws Exception;

}
