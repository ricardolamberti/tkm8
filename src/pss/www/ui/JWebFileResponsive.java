package pss.www.ui;

import java.awt.Dimension;

import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormFileResponsive;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;

public class JWebFileResponsive extends JWebViewComponent implements JWebControlInterface {

	boolean editable = true;

	@Override
	public String getComponentType() {
		return "file_responsive";
	}
 
	public static JWebFileResponsive create(JWebViewComposite parent, JFormFileResponsive fileControl) throws Exception {
		JWebFileResponsive webFile=new JWebFileResponsive();
		webFile.takeAttributesFormControlResponsive(fileControl);
		webFile.setResponsive(fileControl.isResponsive());
		if (parent!=null) parent.addChild(fileControl.getName(), webFile);
		return webFile;
	}
	
	public void setEditable(boolean editable) throws Exception {
		this.editable = editable;
	}

	public void clear() throws Exception {
	}
	
  public String getSpecificValue() throws Exception {
  	return null;
  }
  public String getDisplayValue() throws Exception {
  	return null;
  }
  public void setValue(String zVal) throws Exception {
  }

  public void setValueFromUIString(String zVal) throws Exception {
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
		zContent.setAttribute("editable", this.editable);

  }
	
	public void onShow(String mode) throws Exception {
		if (mode.equals(JBaseForm.MODO_CONSULTA))
			this.setVisible(false);
		else
			this.setVisible(true);
	}


}
