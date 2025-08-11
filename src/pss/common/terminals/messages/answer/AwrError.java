package pss.common.terminals.messages.answer;

import pss.core.services.fields.JString;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;

/**
 * Description:
 *
 * @author Iván Rubín, Ricardo Lamberti
 *
 */

public class AwrError extends Answer {
	
  private Exception oException=null;
  private JString pClassException = new JString();
  private JString pErrorMsg = new JString();

  @Override
	public boolean isError() { return true; }

  public boolean hasException() { return this.oException!=null; }
  public Exception getError() { return this.oException; }
  public String getErrorMessage() throws Exception { return this.pErrorMsg.getValue(); }
  public boolean hasClassException() throws Exception { return this.pClassException.isNotNull(); }
  public void throwError() throws Exception {
		JExcepcion.SendError(this.pClassException.getValue() + "-" + this.getErrorMessage());
//  	Exception ex = (Exception)Class.forName(pClassException.getValue()).newInstance();
//  	if (ex instanceof JExcepcion) {
//  		JExcepcion.SendError(this.getErrorMessage());
//  	}
//  	throw ex;
  }

  /**
   * Constructs an error event expressing any error
   */
  public AwrError() {
  }
  public AwrError(String error) {
    this.pErrorMsg.setValue(error);
  }


  /**
   * Constructs an error event expressing a particular error
   */
  public AwrError( Exception zException ) {
		PssLogger.logError(zException);
    this.oException = zException;
    this.pClassException.setValue(zException.getClass().getName());
    this.pErrorMsg.setValue(zException.getMessage());
  }
  
  @Override
	public void loadFieldMap() throws Exception {
  	this.addValue("class_exc", pClassException);
  	this.addValue("error_msg", pErrorMsg);
  }

  @Override
	public boolean matches( Answer zEvent ) {
    /* We're talking about errors */
    if( zEvent.isError() ) {

      /* Does this match any error? */
      if( this.oException == null ) {
        return true;
      }

      /* Meaning if this.oException's class is zEvent's exception's class or
         one of it's superclasess. isAssignableFrom (c) Leo */
      if( ((AwrError)zEvent).getError().getClass().isAssignableFrom( oException.getClass() ) ) {
        return true;
      }
    }

    return false;
  }
}

