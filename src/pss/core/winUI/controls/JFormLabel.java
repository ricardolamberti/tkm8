package pss.core.winUI.controls;

/**
 * Description:
 * @author Iván Rubín
 */

import java.awt.Component;

import javax.swing.JLabel;

import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormLabelResponsive;

public class JFormLabel extends JFormControl  {
  
	private JLabel  oLabel;

  public JFormLabel( JLabel zLabel) {
  	this.oLabel = zLabel;
  }
  
  @Override
	public Component getComponent() {
  	return this.oLabel;
  }
  
  @Override
	public void setValue(String value) throws Exception {
  	oLabel.setText(value);
  	this.setForcedValue(value);
  }
  
  public void hide() throws Exception {
  	oLabel.setVisible(false);
  }
 
	public JFormControlResponsive getResponsiveVersion() throws Exception {
		JFormLabelResponsive newCtrl = new JFormLabelResponsive();
		newCtrl.getDataComponente(this);
		return newCtrl;
	}
}
