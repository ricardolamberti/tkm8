/*
 * Created on 25-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.controller;

import java.awt.Dimension;

import pss.www.platform.actions.JWebRequest;
import pss.www.ui.processing.JWebTreeCoordinator;
import pss.www.ui.processing.JWebUICoordinator;


public abstract class JBasicWebUICoordinator implements JWebUICoordinator {

  private JBusinessNodesWebTreeCoordinator oTreeCoordinator;
  private JWebClientConfiguration oClientConf;

  public JBasicWebUICoordinator() {
    this.oClientConf = new JWebClientConfiguration(this);
  }


  public JWebTreeCoordinator getTreeCoordinator() {
    if (this.oTreeCoordinator==null) {
      this.oTreeCoordinator = new JBusinessNodesWebTreeCoordinator(this);
    }
    return this.oTreeCoordinator;
  }
  

  public void initialize(JWebRequest zRequest) throws Exception {
    String sHRes = zRequest.getData("client_conf").get("sw");
    String sVRes = zRequest.getData("client_conf").get("sh");
    if (sHRes != null && sVRes != null) {
      this.oClientConf.oScreenSize = new Dimension(Integer.parseInt(sHRes), Integer.parseInt(sVRes));
    } else {
      this.oClientConf.oScreenSize = new Dimension(800,600); // the default
    }
 //   String sAccepted = zRequest.getHeader("accept-encoding");
    String sUserAgent = zRequest.getHeader("user-agent");
//    this.oClientConf.bAcceptsGZip = sAccepted != null && sAccepted.indexOf("gzip") != -1;
    this.oClientConf.setUserAgent(sUserAgent);
  }
  
  public void refresh(JWebRequest zRequest) throws Exception {
    String sHRes = zRequest.getData("client_conf").get("pw");
    String sVRes = zRequest.getData("client_conf").get("ph");
    if (this.oClientConf.oCurrentPageSize==null) {
      this.oClientConf.oCurrentPageSize = new Dimension();
    }
    try {
			if (sHRes != null && sVRes != null) {
			  this.oClientConf.oCurrentPageSize.width = Integer.parseInt(sHRes);
			  this.oClientConf.oCurrentPageSize.height = Integer.parseInt(sVRes);
			} else {
			  this.oClientConf.oCurrentPageSize.width = this.oClientConf.oScreenSize.width;
			  this.oClientConf.oCurrentPageSize.height = this.oClientConf.oScreenSize.height;
			}
		} catch (Exception e) {
		  this.oClientConf.oCurrentPageSize.width = 800;
		  this.oClientConf.oCurrentPageSize.height =  600;
		}
  }

  public JWebClientConfiguration getClientConfiguration() {
    return this.oClientConf;
  }


  public void cleanUp() {
    if (this.oTreeCoordinator != null) {
      this.oTreeCoordinator.cleanUp();
      this.oTreeCoordinator = null;
    }
    this.oClientConf = null;
  }
}
