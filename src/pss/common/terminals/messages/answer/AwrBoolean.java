package pss.common.terminals.messages.answer;

import pss.core.services.fields.JBoolean;

/**
 * Description:
 * @author Iván Rubín
 */

public class AwrBoolean extends Answer {

	private JBoolean pBool = new JBoolean();
	
  public AwrBoolean(boolean value) {
  	this.pBool.setValue(value);
  }

  @Override
	public boolean isBoolean() { return true; }
  
  public boolean getValue() throws Exception { 
  	return pBool.getValue(); 
  }
  
  @Override
	public boolean matches( Answer zEvent ) {
  	return false;
  }

}
