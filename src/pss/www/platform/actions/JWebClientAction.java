/*
 * Created on 17-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions;


import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.JWebActionOwnerProvider;


public abstract class JWebClientAction extends JWebAction {


  //////////////////////////////////////////////////////////////////////////////
  //
  //  INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////
  private JWebActionOwnerProvider oOwnerProvider;
  private Integer objectOwnerID;

  
  @Override
	public void destroy() {
    this.oOwnerProvider = null;
    super.destroy();
  }

//  protected String getProviderName() throws Exception {
//  	return this.oOwnerProvider.getProviderName();
//  }

  public final void toXML(JXMLContent zContent) throws Exception {
    zContent.startNode("action");
    zContent.setAttribute("function", getFunction());
    zContent.endNode("action");
  }

  public JWebActionOwnerProvider getOwnerProvider() {
    return this.oOwnerProvider;
  }

  public void setOwnerProvider(JWebActionOwnerProvider provider) {
    this.oOwnerProvider = provider;
  }

  public boolean hasObjectOwnerID() {
		return objectOwnerID!=null;
	}
  public int getObjectOwnerID() {
		return objectOwnerID.intValue();
	}
	public void setObjectOwnerID(int zObjectOwnerID) {
		objectOwnerID = new Integer(zObjectOwnerID);
	}
	public abstract String getFunction() throws Exception;
  
}
