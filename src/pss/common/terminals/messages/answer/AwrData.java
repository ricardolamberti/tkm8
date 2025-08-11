package pss.common.terminals.messages.answer;

/**
 * Description:
 * @author Iván Rubín
 */

public class AwrData extends Answer {

	private String data=null; 
	
  public AwrData(String data) {
  	this.data = data;
  }
  
  @Override
	public boolean isData()       { return true; }
  
  
  public String getData() { return this.data;}

  @Override
	public boolean matches( Answer zEvent ) {
    return false;
  }

}
