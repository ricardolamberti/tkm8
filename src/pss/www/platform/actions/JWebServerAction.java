/*
 * Created on 17-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions;


import java.io.Serializable;

import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;
import pss.core.win.actions.BizAction;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.actions.requestBundle.JWebActionDataBundle;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.JWebActionOwnerProvider;
import pss.www.ui.JWebFilterPaneResponsive;
import pss.www.ui.JWebWinGenericResponsive;


public class JWebServerAction extends JWebAction {


  //////////////////////////////////////////////////////////////////////////////
  //
  //  INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////
  private String sURI;
  // this attribute tells whether to invoke the action with the mainform, which
  // sends the input fields as request parameters, or with the navform, which
  // only sends the request ignoring the input fields
  private boolean bSubmit;
  private JWebActionOwnerProvider oOwnerProvider;
  private String oOwnerProviderName="";
	private String objectOwnerID;
	private String objectContextID;
	private String confirmMessageDescription=null;
  private boolean bOpenInNewWindow = false;
  private boolean bPrintNewWindow = false;
  private boolean bBackonPrint = false;
  private boolean bRefreshOnPrint = false;
	private boolean bOpenInNewSession = false;
	private boolean bRefreshFatherWindow = false;
	private boolean bMaximize = false;
  private boolean bCloseFatherWindow = false;
  private String idAction=null;
  private String resolveString=null;
  private String ajaxContainer="";
  private String redirectorParam="";
  private boolean cancelable = true;
  private boolean uploadata = false;
  private String specialSelector=null;

	public String getUniqueKey() {
		return 
				sURI+"_"+
				oOwnerProvider+"_"+
				bSubmit+"_"+
				confirmMessageDescription+"_"+
				bOpenInNewWindow+"_"+
				bPrintNewWindow+"_"+
				bBackonPrint+"_"+
				bRefreshOnPrint+"_"+
				bOpenInNewSession+"_"+
				bRefreshFatherWindow+"_"+
				bMaximize+"_"+
				bCloseFatherWindow+"_"+
				idAction+"_"+
				resolveString+"_"+
				ajaxContainer+"_"+
				redirectorParam+"_"+
				cancelable+"_"+
				uploadata+"_"+
				specialSelector;
	}

	private boolean asinc = true;
   public String getRedirectorParam() {
		return redirectorParam;
	}

	public void setRedirectorParam(String redirectorParam) {
		this.redirectorParam = redirectorParam;
	}

	public void addRedirectorParam(String redirectorParam) {
		this.redirectorParam += "&"+redirectorParam;
	}
	public String getSpecialSelector() {
		return specialSelector;
	}

	public void setSpecialSelector(String specialSelector) {
		this.specialSelector = specialSelector;
	}
  public boolean isBackOnPrint() {
		return bBackonPrint;
	}

	public void setBackOnPrint(boolean bBackInPrint) {
		this.bBackonPrint = bBackInPrint;
	}
	 public boolean isRefreshOnPrint() {
			return bRefreshOnPrint;
		}

		public void setRefreshOnPrint(boolean bRefreshInPrint) {
			this.bRefreshOnPrint = bRefreshInPrint;
		}
	public String getIdAction() {
  	return this.idAction;
  }
  
  public boolean isCancelable() {
		return cancelable;
	}
	public void setCancelable(boolean cancelable) {
		this.cancelable = cancelable;
	}
  public boolean isUploadata() {
		return uploadata;
	}

	public void setUploadata(boolean uploadata) {
		this.uploadata = uploadata;
	}

	public void setAsinc(boolean v) {
		this.asinc = v;
	}
	
	private String postFunction=null; 
	public void setPostFunction(String f) {
		this.postFunction = f;
	}

	public boolean hasPostFunction() {
		return this.postFunction!=null;
	}

	public String getPostFunction() {
		return this.postFunction;
	}
	
  public String getObjectContextID() {
		return objectContextID;
	}

	public void setObjectContextID(String objectContextID) {
		this.objectContextID = objectContextID;
	}


	//////////////////////////////////////////////////////////////////////////////
  //
  //  CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////
  JWebServerAction() {
  }
  JWebServerAction(String zURI, boolean zIsSubmit) {
    this.setURI(zURI);
    this.bSubmit = zIsSubmit;
  }


  //////////////////////////////////////////////////////////////////////////////
  //
  //  PUBLIC API
  //
  //////////////////////////////////////////////////////////////////////////////

  @Override
	public void destroy() {
    this.sURI = null;
    this.oOwnerProvider = null;
    super.destroy();
  }


  public boolean isSubmit() {
    return this.bSubmit;
  }

  public void setSubmit(boolean b) {
    this.bSubmit = b;
  }
  public void setAjaxContainer(String value) {
    this.ajaxContainer = value;
  }

  public String getoOwnerProviderName() {
		return oOwnerProviderName;
	}

	public void setoOwnerProviderName(String oOwnerProviderName) {
		this.oOwnerProviderName = oOwnerProviderName;
	}
  protected String getProviderName() throws Exception {
  	if (this.oOwnerProvider==null) return getoOwnerProviderName();
  	return this.oOwnerProvider.getProviderName();
  }

  public void toXML(JXMLContent zContent) throws Exception {
  	if (this.hasFunction()) {
  		zContent.startNode("action");
  		zContent.setAttribute("function", getFunction());
  		zContent.endNode("action");
  		return;
  	}
  	
    zContent.startNode("action");
    zContent.setAttribute("type", "server_request");
    zContent.setAttribute("is_submit", this.bSubmit);
    String uri = this.getURI();
    if (uri==null || uri.trim().length() < 1 || !isValidURI(uri)) {
      throw new RuntimeException("Invalid action URI: '" + uri + "'");
    }
    zContent.setAttribute("target", uri);
    
    String ajaxContainer = "";
     if (this.isOpenInNewSession()) {
    	 ajaxContainer+= "NEW_SESSION" ;
    } else if (this.isOpenInNewWindow()) {
    	ajaxContainer+= this.isRefreshFatherWindow()?"NEW_WINDOW_REFRESH":(this.isCloseFatherWindow()?"NEW_WINDOW_CLOSE":"NEW_WINDOW");
    } else if (this.isOpenInModal()) {
    	ajaxContainer+= "modal_"+this.getAjaxContainer()+"_"+getProviderName()+(JWebActionFactory.getCurrentRequest().getLevel()>1?"":"__l"+JWebActionFactory.getCurrentRequest().getLevel());
    } else if (this.isPrintInNewWindow()) {
    	ajaxContainer+= this.isRefreshFatherWindow()?"PRINT_WINDOW_REFRESH":this.isCloseFatherWindow()?"PRINT_WINDOW_CLOSE":"PRINT_WINDOW";
    } else {
    	ajaxContainer+= this.getAjaxContainer();
    }
     
     if ((this.isOpenInNewSession()||this.isOpenInNewWindow() || this.isPrintInNewWindow()) && this.isMaximize()) {
    	 ajaxContainer+= "_MAX";
  	 
     }
     zContent.setAttribute("ajax_container",ajaxContainer);
     
     

  	if (this.oOwnerProvider != null) {
      zContent.setAttribute("obj_provider", this.getProviderName());
    }
  	
   	if (this.getObjectOwnerID()!=null) {
  		zContent.setAttribute("object_owner_id", this.getObjectOwnerID());
  	}
   	if (this.getObjectContextID()!=null) {
  		zContent.setAttribute("object_context_id", this.getObjectContextID());
  	}
  	if (this.getData() != null) {
      zContent.setAttribute("data_asoc", zContent.addObjectObjTemp(getData()));
    }
  	
  	
    zContent.setAttribute("data_string", this.dataAsURLString());
    zContent.setAttribute("id_action", this.idAction);
    zContent.setAttribute("resolve_string", this.resolveString);
    zContent.setAttribute("cancelable", this.cancelable);
    zContent.setAttribute("special_selector", this.specialSelector);
    zContent.setAttribute("back_on_printer", (this.isBackOnPrint()?"true":"false") );
    zContent.setAttribute("refresh_on_printer", (this.isRefreshOnPrint()?"true":"false") );
    zContent.setAttribute("upload_data", this.uploadata);
    zContent.setAttribute("is_submitafterback", this.isSubmitAfterBack());
    zContent.setAttribute("asinc", this.asinc);
    if (isConfirmMessage()) {
    	zContent.setAttribute("confirmation",  BizUsuario.retrieveSkinGenerator().getMessageConfirm(getConfirmMessageDescription(),getDescription()));
    }
  	zContent.setAttribute("perderdatos", BizUsuario.retrieveSkinGenerator().getMessageConfirmLostData());

    if (this.hasActionParent()) {
			zContent.setAttribute("owner_object_owner_id", this.getServerActionParent().getObjectOwnerID());
	    zContent.setAttribute("owner_obj_provider", this.getServerActionParent().getProviderName());
	    zContent.setAttribute("owner_id_action", this.getServerActionParent().getIdAction());
    }
  	if (this.hasPostFunction()) {
  		zContent.setAttribute("post_function", this.getPostFunction());
  	}

    
    zContent.endNode("action");

//    if (this.hasActionOwner()) {
//  		zContent.startNode("owner");
////      zContent.setAttribute("obj_provider", this.getServerActionOwner().getProviderName());
//  		zContent.endNode("owner");
//  	}

  }
  public String dataAsURLString() {
  	return dataAsURLString(0);
  }  
  public String dataAsURLString(long maxsize) {
    if (this.isScript()) {
      return "";
    }
    JWebActionDataBundle data = this.oDataBundle;
    String sURL;
    if (data != null) {
      StringBuffer sActionData = new StringBuffer(500);
      JIterator<JWebActionData> oGroupIt = data.getDataIterator();
      while (oGroupIt.hasMoreElements()) {
      	JWebActionData group = oGroupIt.nextElement();
        sActionData.append(JWebActionFactory.asURLString(group));
        sActionData.append('&');
        if (maxsize!=0&&sActionData.length()>maxsize)
        	break;
      }
      sURL = sActionData.toString();
    } else {
      sURL = "";
    }
    return sURL;
  }
  
  
  private boolean isScript() {
    return this.getURI()!=null && this.getURI().toLowerCase().startsWith("javascript");
  }
  
  @Override
	public String asURL() {
    if (this.isScript()) {
      return this.getURI();
    } 
    String subsession=JWebActionFactory.getCurrentRequest().getSession()==null?"":JWebActionFactory.getCurrentRequest().getSession().getIdSubsession();
    try {
			if (this.getURI().equals("do-respaction")) {
			  return "/"+BizPssConfig.getPssConfig().getAppURLPrefix()+"/html/redirect.html?subsession="+ subsession +"&sbmt=" + String.valueOf(this.bSubmit) +getRedirectorParam()+ "&" + this.dataAsURLString(1000);
			}
		} catch (Exception e) {
		 PssLogger.logError(e);
		}
    
    return this.getURI()+ "?subsession="+ subsession +"&sbmt=" + String.valueOf(this.bSubmit) +getRedirectorParam()+ "&" + this.dataAsURLString(1000);
  }
  
 
  public String getURI() {
    return this.sURI;
  }

  public void setURI(String string) {
    this.sURI = string;
  }

  @Override
	public String toString() {
    StringBuffer oBuff = new StringBuffer();
    oBuff.append(this.getURI()).append("[submit=").append(this.bSubmit).append("] {\n");
    if (this.oDataBundle!=null) {
      JIterator<JWebActionData> oIt = this.oDataBundle.getDataIterator();
      while (oIt.hasMoreElements()) {
        oBuff.append(oIt.nextElement().toString()).append('\n');
      }
    } else {
      oBuff.append("NO DATA\n");
    }
    oBuff.append("}");
    return oBuff.toString();
  }



  //////////////////////////////////////////////////////////////////////////////
  //
  //  INTERNAL UTILITIES
  //
  //////////////////////////////////////////////////////////////////////////////

  private boolean isValidURI(String zURI) {
    char[] aChars = new char[zURI.length()];
    zURI.getChars(0, aChars.length-1, aChars, 0);
    char c;
    for (int i = 0; i < aChars.length; i++) {
      c = aChars[i];
//      if (c=='?' || c=='&' || c=='=') {
//        return false;
//      }
    }
    return true;
  }
  

  public JWebActionOwnerProvider getOwnerProvider() {
    return this.oOwnerProvider;
  }

  public void setOwnerProvider(JWebActionOwnerProvider provider) {
    this.oOwnerProvider = provider;
  }

  public String getAjaxContainer() throws Exception {return this.ajaxContainer;}
  public boolean hasAjaxContainer() { return !this.ajaxContainer.equals("");}

  public String getObjectOwnerID() {
		return objectOwnerID;
	}
	public void setObjectOwnerID(String zObjectOwnerID) {
		objectOwnerID = zObjectOwnerID;
	}
	public boolean isConfirmMessage() {
		return confirmMessageDescription!=null;
	}
	public String getConfirmMessageDescription() {
		return confirmMessageDescription;
	}
	public void setConfirmMessageDescription(String confirmMessageDescription) {
		this.confirmMessageDescription = confirmMessageDescription;
	}
	public boolean isOpenInNewWindow() {
		return bOpenInNewWindow;
	}
	public void setOpenInNewWindow(boolean openInNewWindow) {
		bOpenInNewWindow = openInNewWindow;
	}

	public boolean isPrintInNewWindow() {
		return bPrintNewWindow;
	}
	public void setPrintInNewWindow(boolean printInNewWindow) {
		bPrintNewWindow = printInNewWindow;
	}
  public boolean isRefreshFatherWindow() {
		return bRefreshFatherWindow;
	}
	public void setRefreshFatherWindow(boolean bRefreshFatherWindow) {
		this.bRefreshFatherWindow = bRefreshFatherWindow;
	}
  public boolean isMaximize() {
		return bMaximize;
	}
	public void setMaximize(boolean bMax) {
		this.bMaximize = bMax;
	}
  public boolean isCloseFatherWindow() {
		return bCloseFatherWindow;
	}
	public void setCloseFatherWindow(boolean bCloseFatherWindow) {
		this.bCloseFatherWindow = bCloseFatherWindow;
	}
	public boolean isOpenInNewSession() {
		return bOpenInNewSession;
	}
	public void setOpenInNewSession(boolean value) {
		bOpenInNewSession = value;
	}
	public void setIdAction(String value) {
		this.idAction = value;
	}
	public void setResolveString(String value) {
		this.resolveString = value;
	}
	
	public boolean hasFunction() { return false;}
	public String getFunction() throws Exception { return null;}
	
	public JWebServerAction prepareWebAction(BizAction zAction, JWebActionOwnerProvider provider, String zObjectOwnerId) throws Exception {
		return prepareWebAction(zAction, provider, zObjectOwnerId,zAction.getRow());
	}

	public JWebServerAction prepareWebAction(BizAction zAction, JWebActionOwnerProvider provider, String zObjectOwnerId, String row) throws Exception {
		return prepareWebAction(zAction, provider, zObjectOwnerId,row, null);
	}
	public JWebServerAction prepareWebAction(BizAction zAction, JWebActionOwnerProvider provider, String zObjectOwnerId, String row, String objContext) throws Exception {
		String sActionId=zAction.getIdAction();
		if (!JWebActionFactory.canPerform(sActionId)) {
			return new JWebDisabledAction();
		} else {
			this.setIdAction(JWebActionFactory.idActionToURL(zAction.getIdAction(),row));
			this.setOwnerProvider(provider);
			
			this.setDescription(zAction.GetDescr());
			this.setKey(zAction.GetKeyAction());
			if (zAction.hasConfirmMessage()) {
				this.setConfirmMessageDescription(zAction.getConfirmMessageDescription());
			}
			this.setBackOnPrint(zAction.isBackOnPrint());
			this.setRefreshOnPrint(zAction.isRefreshOnPrint());
			this.setOpenInNewWindow(zAction.isNuevaVentana());
			this.setOpenInNewSession(zAction.isNuevaSession());
			this.setOpenInModal(!zAction.isBackNoModal() && zAction.isModal());
			this.setPrintInNewWindow(zAction.isImprimir());
			this.setRefreshFatherWindow(zAction.isRefreshFatherWindow());
			this.setCloseFatherWindow(zAction.isCloseFatherWindow());
			if (zObjectOwnerId!=null)  this.setObjectOwnerID(zObjectOwnerId);
			if (objContext!=null)  this.setObjectContextID(objContext);
			this.setData(extractData(provider,zAction));
			this.setCancelable(zAction.isCancelable());
			this.setSpecialSelector(zAction.getSpecialSelector());
			this.setUploadata(zAction.isUploadData());
			this.setAsinc(!zAction.hasPostFunction());
			this.setPostFunction(zAction.getPostFunction());
			return this;
		}
	}
	private Serializable extractData(JWebActionOwnerProvider provider,BizAction zAction) throws Exception {
		if (provider instanceof JWebWinGenericResponsive)
				return ((JWebWinGenericResponsive)provider).getData();
		if (provider instanceof JWebFilterPaneResponsive && ((JWebFilterPaneResponsive) provider).getWebWinList() instanceof JWebWinGenericResponsive)
			return ((JWebWinGenericResponsive)((JWebFilterPaneResponsive) provider).getWebWinList()).getData();
		return zAction.getData();
	}
	public JWebServerAction prepareWebAction(String zone,JWebActionOwnerProvider provider, String zObjectOwnerId) throws Exception {
			this.setIdAction(zone);
			this.setOwnerProvider(provider);
			this.setDescription("");
			this.setOpenInNewWindow(false);
			this.setOpenInModal(false);
			this.setPrintInNewWindow(false);
			this.setRefreshFatherWindow(false);
			if (zObjectOwnerId!=null) 
				this.setObjectOwnerID(zObjectOwnerId);
			this.setCancelable(false);
			this.setUploadata(false);
			return this;
		
	}
	public JWebServerAction getServerActionParent() throws Exception {
		return (JWebServerAction) this.getActionParent();
	}

	public JWebActionData getNavigationData() throws Exception {
		return this.addData("win_list_nav");
	}

}
