package pss.common.terminals.messages.requires.display;

/**
 * Description:
 *
 * @author Ricardo Lamberti, Iván Rubín
 *
 */

import pss.common.terminals.messages.answer.Answer;


public class JButton {
	
  private String sDescription;
  private Answer oEvent;

  public String getDescription() { return this.sDescription; }
  public Answer getEvent()       { return this.oEvent; }

  public JButton( String zDescription, Answer zEvent ) {
    this.sDescription = zDescription;
    this.oEvent = zEvent;
  }
}
