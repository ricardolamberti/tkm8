/*
 * Created on 23-ago-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.controller;

import pss.core.services.fields.JIntervalDate;
import pss.core.services.fields.JIntervalDateTime;
import pss.core.services.fields.JObject;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.www.ui.JWebControlInterface;

public class JWebFormControl extends JFormControl {

  private JFormControl formControl;
  private JWebControlInterface webControl;
  
  //
  //  CONSTRUCTOR
  //
  public JWebFormControl() {
  }
  
  
  //
  //  INITIALIZATION METHODS
  //
  public void setComponent(JWebControlInterface webCtrl) {
    this.webControl=webCtrl;
  }
  public JWebControlInterface getWebComponent() {
    return this.webControl;
  }
  public void setFormComponent(JFormControl webCtrl) {
    this.formControl=webCtrl;
  }
  public JFormControl getFormComponent() {
    return this.formControl;
  }

  //
  //  ACCESS API
  //

  @Override
	public void edit(String zModo) throws Exception {
  		this.webControl.setEditable(true);
  }
  @Override
	public void clear() throws Exception {
  		this.webControl.clear();
  }
  @Override
	public void disable() throws Exception {
  		this.webControl.setEditable(false);
  }

  @Override
	public void enable() throws Exception {
  		this.webControl.setEditable(true);
  }

  @Override
	public void hide() throws Exception {
  		this.webControl.setVisible(false);
  }
  @Override
	public void show() throws Exception {
  		this.webControl.setVisible(true);
  }
  @Override
	public JFormControl setVisible(boolean zValue) throws Exception {
  	this.bVisible=zValue;//(zValue);
  	return this;
	}
  
//	public boolean isVisible() {
//  	return this.webControl.isVisible();
//	}

  
  @Override
	public boolean hasValue() throws Exception {
  	return (this.getValue() != null && !this.getValue().equals("")) || (findObjectValue()!=null);
  }
  @Override
	public String getSpecificValue() throws Exception {
  	return this.webControl.getSpecificValue();
  }
  @Override
	public void setValue(String zVal) throws Exception {
  	this.formControl.setValue(zVal);
  	this.webControl.setValue(zVal);
  }
	public void setValue(JWin zVal) throws Exception {
  	this.formControl.setValue(zVal);
  	this.webControl.setValue(zVal);
  }
	public JWin GetWinSelect() throws Exception {
		return this.formControl.GetWinSelect();
	}
	public void setValue(JWins zVal) throws Exception {
		this.formControl.setValue(zVal);
  	this.webControl.setValue(zVal);
  }
	public void setValueFormUI(String zVal) throws Exception {
		this.webControl.setValueFromUIString(zVal);
	}
	public void setValue(JIntervalDateTime zVal) throws Exception {
		this.webControl.setValue(zVal);
	}
	public void setValue(JIntervalDate zVal) throws Exception {
		this.webControl.setValue(zVal);
	}
	@Override
	public String getDescriptionValue(JWin win) throws Exception {
		if (formControl!=null)
			return formControl.getDescriptionValue(win);
		return super.getDescriptionValue(win);
	}
	@Override
	public String getKeyValue(JWin win) throws Exception {
		if (formControl!=null)
			return formControl.getKeyValue(win);
		return super.getKeyValue(win);
	}
  @Override
	public void Remove() {
    this.webControl = null;
  }

//  public void DefaultToControl() throws Exception {
////    setValue(this.getDefaultAsInputString());
//  }

	public void BaseToControl(String zModo, boolean userRequest) throws Exception {
		super.BaseToControl(zModo, userRequest);
		this.webControl.onShow(zModo);
	}

  /**
   * Overriden because constraints are performed on the client.
   */
  @Override
	public void ControlToBase() throws Exception {
     if (this.getProp()== null) return;

    String sVal = this.getValue();

    if (sVal==null) {
      this.getProp().setValue("");
      return;
    }

    this.getProp().setValue(sVal);
  }

  @Override
	public boolean ifRequerido() throws Exception {
    if ( this.GetRequerido() == null ) return false;
    return this.GetRequerido().equals("REQ");
  }

  @Override
	public boolean isDate() throws Exception {
    return this.GetObjectType().equals(JObject.JDATE);
  }

  @Override
	public boolean isHour() throws Exception {
    return this.GetObjectType().equals(JObject.JHOUR);
  }

  @Override
	public boolean isImage() throws Exception {
    return this.GetObjectType().equals(JObject.JIMAGE);
  }
  @Override
	public boolean isColour() throws Exception {
    return this.GetObjectType().equals(JObject.JCOLOUR);
  }
  public boolean isDateTime() throws Exception {
    return this.GetObjectType().equals(JObject.JDATETIME);
  }

  @Override
	public boolean isNumerico() {
    return  GetObjectType().equals(JObject.JFLOAT) ||
            GetObjectType().equals(JObject.JCURRENCY) ||
            GetObjectType().equals(JObject.JINTEGER) ||
            GetObjectType().equals(JObject.JLONG) ||
            GetObjectType().equals(JObject.JAUTONUMERICO);
  }
  
  public JWin getWinElegido(String zClave) throws Exception {
  	return webControl.getWinElegido(zClave);
  }
  public JWin[] getWinElegidos(String zClave) throws Exception {
  	return webControl.getWinElegidos(zClave);
  }


	
	
	@Override
	public String toString() {
		try {
			String op = getOperator()==null?"=":getOperator();
			op =  op.equalsIgnoreCase("like")?" contiene":":";
			String val = webControl.getDisplayValue();
			if (val==null || val.equals("")) return "";
			return GetDisplayName()+op+" "+val;
		} catch (Exception e) {
			return "";
		}
	}
}
