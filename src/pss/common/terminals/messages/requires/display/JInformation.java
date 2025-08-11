package pss.common.terminals.messages.requires.display;

/**
 * Description:
 *
 * @author Ricardo Lamberti, Iván Rubín
 *
 */

import pss.common.terminals.core.JTerminal;
import pss.common.terminals.interfaces.JOperatorDisplayInterface;
import pss.common.terminals.messages.answer.Answer;

public class JInformation extends JPage {
	
  protected String  sInformation;
  protected int iPosition = JTerminal.POS_TOPLEFT;
  protected boolean bCutWords = false;

  protected static final int MAX_LINES = 20;

  public void setClearScreen( boolean zClear ) { this.bClear = zClear; }
  public void setCenter( boolean zCenter ) { if (zCenter) this.iPosition |= JTerminal.POS_HORIZONTAL_CENTER; else  this.iPosition &= ~JTerminal.POS_HORIZONTAL_CENTER;}
  public void setPosition( int pos ) { this.iPosition = pos;}
  public void setVCenter( boolean zVCenter ) { if (zVCenter) this.iPosition |= JTerminal.POS_VERTICAL_CENTER;  else  this.iPosition &= ~JTerminal.POS_VERTICAL_CENTER;}
  public void setCutWords( boolean zCutWords ) { this.bCutWords = zCutWords; }

  protected JInformation() {
  }

  public void setInformation(String value) {
  	this.sInformation = value;
  }
  public String getInformation() {
  	return this.sInformation;
  }

  @Override
	public void addButton(JButton oButton) throws Exception  {
    if (!oButton.getDescription().equals("")) {
      if (oFooter==null)
        oFooter = new JFooter();
      oFooter.addFooter(oButton.getDescription());
    }
    super.addButton(oButton);
  }

  /**
   * JPage abstract methods
   *
   * execute
   *
   */
/*  public JEvent execute( JTerminalPool pool ) {
    JEvent oAnswer = null;

    try {
       if we pass the intialize step without an answer 
      if( (oAnswer=intializeExecute( oTCom ) ) == null ) {
         page's execution will give us the answer 
        oAnswer = display( zAutomat, oTCom );
      }
    } catch( Exception e ) {
      oAnswer = new JError( e );
    }

    return oAnswer;
  }
*/
  
  @Override
	public Answer execute(JTerminal terminal) throws Exception {
  	JOperatorDisplayInterface operDisplay = (JOperatorDisplayInterface) terminal;
    operDisplay.operatorDisplay(this);
    return this.captureEvent();
  }
  

//--

}
