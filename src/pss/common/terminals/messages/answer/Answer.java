package pss.common.terminals.messages.answer;

import pss.core.services.fields.JObjRecord;


public abstract class Answer extends JObjRecord {
	
  /**
   * answers whether this event matches zEvent or not
   */
  public abstract boolean matches( Answer zEvent ) throws Exception;

  /**
   * answers whether this event's class is assignable from zEvent's class
   */
  public boolean is( Class zEventClass ) {
    return this.getClass().isAssignableFrom( zEventClass );
  }

  /**
   * we're a family!
   */
  public boolean isChipCard()          { return false; }
  public boolean isError()             { return false; }
  public boolean isExternal()          { return false; }
  public boolean isKey()               { return false; }
  public boolean isMagneticCard()      { return false; }
  public boolean isMenuItemSelection() { return false; }
  public boolean isStringInput()       { return false; }
  public boolean isTag()               { return false; }
  public boolean isTimeout()           { return false; }
  public boolean isIdle()              { return false; }
  public boolean isAccept()            { return false; }
  public boolean isCancel()            { return false; }
  public boolean isEndOfSequence()     { return false; }
  public boolean isEndAnimation() 	   { return false; }
  public boolean isData()       		   { return false; }
  public boolean isManualCancel() 		 { return false; }
  public boolean isBoolean()  				 { return false; }
    
}
