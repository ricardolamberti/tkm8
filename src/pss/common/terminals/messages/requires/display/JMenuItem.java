package pss.common.terminals.messages.requires.display;

/**
 * Description:
 *
 * @author Ricardo Lamberti, Iván Rubín
 *
 */

public class JMenuItem {
  private String sDescription;
  private Object oValue;
  private int iShortCut;

  public String getDescription() { return this.sDescription; }
  public Object getValue()       { return this.oValue; }
  public int getShortCut()       { return this.iShortCut; }

  public JMenuItem( String zDescription, Object zValue ) {
    this.sDescription = zDescription;
    this.oValue = zValue;
    this.iShortCut = -1;
  }

  public JMenuItem( String zDescription, Object zValue , int zShortCut) {
    this.sDescription = zDescription;
    this.oValue = zValue;
    this.iShortCut = zShortCut;
  }

}
