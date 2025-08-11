package pss.www.ui;

import java.awt.Dimension;

import pss.core.winUI.forms.JBaseForm;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;

/**
 * 
 * 
 * Created on 12-jun-2003
 * 
 * @author PSS
 */

public class JWebCaptcha extends JWebViewEditComponent implements JWebControlInterface  {


	@Override
	public String getComponentType() {
		return  "captcha";
	}


	String value;
	public void clear() throws Exception {
	}
	
  public String getSpecificValue() throws Exception {
  	return value;
  }
  public String getDisplayValue() throws Exception {
  	return value;
  }
  public void setValue(String zVal) throws Exception {
  	value=zVal;
  }

  public void setValueFromUIString(String zVal) throws Exception {
  	value=zVal;
  }
  
  public void setController(JWebFormControl control) {
  }
  
	

	@Override
	protected Dimension getDefaultSize() throws Exception {
		return null;
	}

	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		zContent.setAttribute("form_name", this.getForm().getFormName());
  }
	
	public void onShow(String mode) throws Exception {
		if (mode.equals(JBaseForm.MODO_CONSULTA))
			this.setVisible(false);
		else
			this.setVisible(true);
	}



	@Override
	public String getValueAsUIString() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getValueAsDBString() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void setValueFromDBString(String zDBString) throws Exception {
		// TODO Auto-generated method stub
		
	}



	@Override
	public Object getValue() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void setValue(Object zValue) throws Exception {
		// TODO Auto-generated method stub
		
	}



	@Override
	protected void widgetToXML(JXMLContent zContent) throws Exception {
		// TODO Auto-generated method stub
		
	}


}
