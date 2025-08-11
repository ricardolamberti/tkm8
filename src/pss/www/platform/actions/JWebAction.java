/*
 * Created on 13-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions;

import java.io.Serializable;

import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.actions.requestBundle.JWebActionDataBundle;
import pss.www.ui.processing.JXMLRepresentable;



public abstract class JWebAction implements JXMLRepresentable {

  // the data to be negotiated between the server and the client for this action
  JWebActionDataBundle oDataBundle;
  private String sDescription;
  private JWebAction actionParent;
  private long  sKey;
  private boolean forceSubmit=false;
  private boolean bSubmitAfterBack=true;
  private boolean isCancel=false;
  private boolean isWin=false; 
  private boolean isWinList=false; 
  private boolean isForm=false; 
  private boolean isFormLov=false; 
  private boolean isWinListRefresh=false; 
  private boolean bNuevaVentana=false; 
  private boolean bSwitcheable=false; 
  private boolean bOpenInModal = false;
  private Serializable data=null;
	public Serializable getData() {
		return data;
	}
	public void setData(Serializable data) {
		this.data = data;
	}
	public boolean isOpenInModal() {
		return bOpenInModal;
	}
	public void setOpenInModal(boolean openInNewWindow) {
		bOpenInModal = openInNewWindow;
	}
	public boolean isSwitcheable() {
		return bSwitcheable;
	}
	public void setSwitcheable(boolean bSwitcheable) {
		this.bSwitcheable = bSwitcheable;
	}
	public void setForceSubmit(boolean v) {
		this.forceSubmit = v;
	}
	public void setSubmitAfterBack(boolean v) {
		this.bSubmitAfterBack = v;
	}
	public void setIsCancel(boolean v) {
		this.isCancel = v;
	}
	public void setIsWin(boolean v) {
		this.isWin = v;
	}
	public void setIsWinList(boolean v) {
		this.isWinList = v;
	}
	public void setIsForm(boolean v) {
		this.isForm = v;
	}
	public void setIsFormLov(boolean v) {
		this.isFormLov = v;
	}
	public void setNuevaVentana(boolean v) {
		this.bNuevaVentana = v;
	}
	public void setIsWinListRefresh(boolean v) {
		this.isWinListRefresh = v;
	}
	public boolean isForceSubmit() {
		return this.forceSubmit;
	}
	public boolean isSubmitAfterBack() {
		return this.bSubmitAfterBack;
	}
	
	public boolean isCancel() {
		return this.isCancel;
	}
	public boolean isWin() {
		return this.isWin;
	}
	public boolean isWinList() {
		return this.isWinList;
	}
	public boolean isForm() {
		return this.isForm;
	}
	public boolean isFormLov() {
		return this.isFormLov;
	}
	public boolean isNuevaVentana() {
		return this.bNuevaVentana;
	}
	public boolean isWinListRefresh() {
		return this.isWinListRefresh;
	}
	public long getKey() {
		return sKey;
	}
	public String getUniqueKey() {
		return null;
	}
	public void setKey(long sKey) {
		this.sKey = sKey;
	}
	public boolean hasActionParent() { return actionParent!=null;}
	public JWebAction getActionParent() throws Exception { return actionParent;}

  
  public void destroy() {
    if (this.oDataBundle != null) {
      this.oDataBundle.destroy();
      this.oDataBundle = null;
    }
    this.sDescription = null;
  }
  public void clear() {
    if (this.oDataBundle != null) {
    	JWebActionData rescue = this.oDataBundle.getData("subsession");
    	JWebActionData rescueMobile = this.oDataBundle.getData("is_mobile");
      this.oDataBundle.clear();
      this.oDataBundle.addData(rescue);
      if (rescueMobile!=null)
      	this.oDataBundle.addData(rescueMobile);
    }
  }


  public JWebActionData addData(String zArgGroupId) {
    if (this.oDataBundle==null) {
      this.oDataBundle = new JWebActionDataBundle();
    }
    JWebActionData oData = this.oDataBundle.getData(zArgGroupId);
    if (oData==null) {
      oData = this.oDataBundle.addData(zArgGroupId);
    }
    return oData;
  }

  public JWebActionData addReplaceData(String zArgGroupId) {
    if (this.oDataBundle==null) {
      this.oDataBundle = new JWebActionDataBundle();
    }
    return this.oDataBundle.addData(zArgGroupId);
  }

  public JWebActionData getData(String zArgGroupId) {
    if (this.oDataBundle==null) {
      return null;
    } else {
      return this.oDataBundle.getData(zArgGroupId);
    }
  }
  
  public boolean isEnabled() {
    return true;
  }


  public String getDescription() {
    return this.sDescription;
  }

  public void setDescription(String string) {
    this.sDescription = string;
  }
  
  public String asURL() {return "";}
  
  public void setActionParent(JWebAction v) {
  	this.actionParent=v;
  }


}
