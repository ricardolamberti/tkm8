package pss.common.terminals.messages.requires.display;

/**
 * Description:
 *
 * @author Ricardo Lamberti, Iván Rubín
 *
 */

import pss.common.terminals.core.JTerminal;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrTimeout;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JList;

public class JForm extends JPage {

  public static final int DEFAULT_INPUTS_COLLECTION_SIZE = 1;

  private JList<JInput>   oInputs = JCollectionFactory.createList( JForm.DEFAULT_INPUTS_COLLECTION_SIZE );

//  private int iFirstQuestion = 0;
//  private boolean bWithScroll = false;
  private JInput oActiveInput;

  public void addInput( JInput zInput )    { this.oInputs.addElement( zInput ); oActiveInput = zInput;}
  public void addInput( JInput zInput, boolean zActive )    { this.oInputs.addElement( zInput );  if (zActive) oActiveInput = zInput; }

  public JForm() {
  }

  /**
   * JPage abstract methods
   *
   * execute
   *
   */
  @Override
	public Answer execute(JTerminal terminal) throws Exception {
    long     lTimeout = 0;
    AwrTimeout oTimeout;

    oTimeout = (AwrTimeout)this.getEvents().has( AwrTimeout.class );
    if( oTimeout != null ) {
      lTimeout = oTimeout.getTimeout();
    }

//    if((this.sClientMessage!=null) && (this.sClientMessage.length() > 0))
//     pool.clientDisplay(this.sClientMessage, true);

    return question( oActiveInput, lTimeout );
  }

  private Answer question( JInput zInput, long zTimeout ) throws Exception {
//    JEvent event;
//    String message;
//    message = zInput.question(oHeader, oFooter, pool, zTimeout/1000);
//    if ( message.equals("TIMEOUT" ) ) return new JTimeout( getTime() );
//    if ( oTCom.isCanceledByUser(message) ) return new JCancel();
//    if ( oTCom.manageReply(message) ) return new JError( new JPOSException() );
//    if ( !zInput.validate(zAutomat, message) ) return new JExternal();
//    return new JStringInput(message);
  	return null;
  }
/*
  private String getPresentation( BizTerminalCom oTCom , int lines, int width) throws Exception {
    String message = "";

    bWithScroll = false;
    if (lines/2 <= oInputs.size())
      linesForQuestion = 2;
    else if (lines <= oInputs.size())
      linesForQuestion = 1;
    else {
      linesForQuestion = 1;
      bWithScroll = true;
    }


    JIterator iterator = oInputs.getIterator();
    for(int i=0; iterator.hasMoreElements(); i++) {
      JInput oInput = (JInput)oInputs.getElementAt(i);
      message += getPresentationQuestion(oTCom, oInput, linesForQuestion, width-1);
      iterator.nextElement();
    }

    return message;
  }

  private String getPresentationQuestion( BizTerminalCom oTCom , JInput input,int lines, int width) throws Exception {
    String message = "";

    return message;
  }

*/
}
