package pss.common.terminals.messages.requires.display;

/**
 * Description:
 *
 * @author Ricardo Lamberti, Iván Rubín
 *
 */

import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.requires.JEvents;
import pss.common.terminals.messages.requires.JRequire;
import pss.common.terminals.messages.requires.common.JCaptureEvent;

public abstract class JPage extends JRequire {
	
  public static long DEFAULT_TIMEOUT = 30000;

  private JEvents   eventFilters = new JEvents();
  private long      lTimeOfCreation;

  protected JHeader oHeader;
  protected JFooter oFooter;
  protected String  sClientMessage;
  protected boolean bClear = true;

  public JPage() {
    lTimeOfCreation = System.currentTimeMillis();
  }
  
  public void setClientMessage( String zMessage ) {
    this.sClientMessage = zMessage;
  }

  public void setHeader( JHeader zHeader ) {
    this.oHeader = zHeader;
  }
  public void setFooter( JFooter zFooter ) {
    this.oFooter = zFooter;
  }

  public void addButton( JButton oButton ) throws Exception {
    this.getEvents().addValue( oButton.getEvent() );
  }

  /**
   *  Answers the time elapsed since instantiation
   */
  public long getTime() {
    return System.currentTimeMillis() - lTimeOfCreation;
  }

  public JEvents getEvents() { return this.eventFilters; }

  /**
   *  This will do the page on TCom, including minor interactions, and will
   * answer the exiting event.
   */


//  public JEvent intializeExecute( JTerminalPool pool ) {
//    return this.getEvents().matches( new JTimeout(0) );
//  }
  
  public void setClear( boolean zClear ) { this.bClear = zClear; }

  protected Answer captureEvent() throws Exception {
  	if (this.eventFilters==null) return null;
  	if (!this.eventFilters.hasValues()) return null;
  	Answer event=null;
  	while(this.getEvents().matches((event=this.detectEvent()))==null);
  	return event;
  }

  
  public Answer detectEvent() throws Exception {
    JCaptureEvent capture = new JCaptureEvent(null);
    capture.setEventFilters(this.getEvents().getMap());
    return capture.capture();
  }
}
