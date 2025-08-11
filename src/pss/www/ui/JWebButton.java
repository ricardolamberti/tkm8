/*
 * Created on 12-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import pss.core.ui.components.JPssButton;
import pss.core.winUI.controls.JFormButton;
import pss.core.winUI.controls.JFormControl;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.ui.controller.JWebFormControl;

public class JWebButton  extends JWebAbstractActionView  implements JWebControlInterface {
  
  public JWebButton() {
  }

  public JWebButton(String zLabel) {    
    this.setLabel(zLabel);
  }

  @Override
	public String getComponentType() {
    return isResponsive()?"web_button_responsive": "web_button";
  }


	public static JWebButton create(JWebViewComposite parent, JPssButton zComp, JFormButton zControl) throws Exception {
		JWebButton webButton=new JWebButton();
		webButton.takeAttributesForm(parent,zComp);
		webButton.takeAttributesFormControl(zControl);
		webButton.setResponsive(zControl.isResponsive());
		webButton.setSize(zComp.getSize().width, zComp.getSize().height);
		webButton.setLabel(zControl.getButton().getLabel());
		if (zComp.getParent()==null) webButton.setVisible(false);
		if (parent!=null) parent.addChild(zControl.getName(), webButton);
		if (zControl.getAction()!=null && parent!=null) {
			webButton.setWebAction(JWebActionFactory.createViewAreaAndTitleAction(zControl.getAction(),webButton.getObjectProvider(),zControl.isSubmit(),null));
		} 
		if (parent instanceof JWebFilterPanel) ((JWebFilterPanel)parent).addButtons(webButton);
		return webButton;
	}

	public void takeAttributesFormControl(JFormControl comp) throws Exception {
		if (comp.getToolTip()!=null) {
			this.setToolTip(comp.getToolTip());
	  }
  	this.setRefreshForm(comp.hasToRefreshForm());
  	this.setSkinStereotype(comp.getSkinStereotype());
		
	  if (comp.getForeground()!=null) {
	  	this.setForeground(comp.getForeground());
	  }
	  if (comp.getBackground()!=null) {
	  	this.setBackground(comp.getBackground());
	  }
	  this.setVisible(comp.isVisible()&&!comp.isHide());
	  super.takeAttributesFormControl(comp);
	}  

  public JWebAction getWebAction()  throws Exception{
  	if (super.getWebAction()!=null) return super.getWebAction();
  	if (!this.hasToRefreshForm()) return null;
  	JWebAction a= this.getObjectProvider().getWebSourceAction();
  	a.setActionParent(a);
  	return a;
  }
	
	@Override
	public void clear() throws Exception {
	
	}

	@Override
	public String getDisplayValue() throws Exception {
		return null;
	}



	@Override
	public void onShow(String modo) throws Exception {
		
	}

	@Override
	public void setController(JWebFormControl control) {
		
	}

	@Override
	public void setEditable(boolean editable) throws Exception {
		
	}

	@Override
	public void setValue(String zVal) throws Exception {
		
	}

	@Override
	public void setValueFromUIString(String zVal) throws Exception {
	
	}
}
