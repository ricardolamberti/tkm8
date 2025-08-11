package pss.common.terminals.messages.answer;

/**
 * Description:
 * @author Iv�n Rub�n
 */

public class AwrPinBlock extends Answer {

	private String pinBlock=null; 
	
  public AwrPinBlock(String pinBlock) {
  	this.pinBlock = pinBlock;
  }
  
  public String getPinBlock() { return this.pinBlock;}

  @Override
	public boolean matches( Answer zEvent ) {
    return false;
  }
  
}
