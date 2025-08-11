/*
 * Created on 15-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.controller;

import java.awt.Dimension;

import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.applications.JWebApplicationSession;
import pss.www.ui.processing.JWebUICoordinator;

public class JWebClientConfiguration implements JWebSessionOwnedObject {

  private JWebUICoordinator oCoordinator;

  Dimension oScreenSize;
  Dimension oCurrentPageSize;
  boolean bAcceptsGZip;
  boolean bBordersAreInternal;

/*  static JBasicWebClientConfiguration DEFAULT = new JBasicWebClientConfiguration();


  // NULL instance constructor
  private JBasicWebClientConfiguration() {
    this(JFrontDoorUICoordinator.getInstance());
    this.oScreenSize = new Dimension(1024,768);
    this.oCurrentPageSize = new Dimension(600,500);
    this.bAcceptsGZip = false;
  }*/
  JWebClientConfiguration(JWebUICoordinator zCoordinator) {
    this.oCoordinator = zCoordinator;
    this.bAcceptsGZip = true; //RJL temorariamente desactivado
  }


  public static JWebClientConfiguration getCurrent() throws Exception {
    /*JBasicWebApplicationSession oSession = JBasicWebApplicationSession.getCurrent();
    if (oSession==null) {
      return DEFAULT;
    }
    return oSession.getUICoordinator().getClientConfiguration();
    */
    return JWebActionFactory.getCurrentRequest().getUICoordinator().getClientConfiguration();
  }


  public void setUserAgent(String zUserAgent) {
  	if (zUserAgent==null) {
      this.bBordersAreInternal = false;
  		return;
  	}
  	if (zUserAgent.indexOf("Chrome") != -1) { // google
      this.bBordersAreInternal = false;
    }
    else if (zUserAgent.indexOf("Gecko") != -1) { // Mozilla
      this.bBordersAreInternal = false;
    } else { // explorer
      this.bBordersAreInternal = true;
    }
  }



  public boolean bordersAreInternal() {
    return this.bBordersAreInternal;
  }


  public JWebApplicationSession getSession() {
    return this.oCoordinator.getSession();
  }

  public int getScreenWidth() {
    return this.oScreenSize.width;
  }
  public int getScreenHeight() {
    return this.oScreenSize.height;
  }

  public int getCurrentPageWidth() {
    return this.oCurrentPageSize.width;
  }
  public int getCurrentPageHeight() {
    return this.oCurrentPageSize.height;
  }

  public boolean acceptsGZip() {
    return this.bAcceptsGZip;
  }

}
