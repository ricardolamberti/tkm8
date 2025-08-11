package pss.common.terminals.messages.requires.display;

/**
 * Description:
 *
 * @author Ricardo Lamberti, Iván Rubín
 */

import pss.common.terminals.interfaces.JOperatorDisplayInterface;
import pss.core.tools.JTools;


public class JFooter {
  public static final int LEFT = 0x02;
  public static final int RIGHT = 0x04;
  public static final int CENTER = 0x06;

  private String sFooter;
  private int    iPosition = JHeader.CENTER;
  private int    iButtons = 0;

  public JFooter() {
    this.sFooter = "";
    this.iPosition = CENTER;
  }

  public JFooter( String zFooter ) {
    this.sFooter = zFooter;
    this.iPosition = CENTER;
  }

  public JFooter( String zFooter, int zPosition ) {
    this.sFooter = zFooter;
    this.iPosition = zPosition;
  }

  public void addFooter(String zFoot)   {
      this.sFooter += ((iButtons==0)?"":" / ")+ zFoot;
      this.iButtons++;
  }

  public String getFooter()   { return this.sFooter; }
  public int    getPosition() { return this.iPosition; }

  public String getFooterFormatted(JOperatorDisplayInterface operDisplay) throws Exception {
    String footer = "";

    if ((iPosition&JHeader.CENTER)==JFooter.CENTER)
      footer = JTools.centerString(getFooter(), operDisplay.getlineLenOperatorDisplay(),' ');
    else if ((iPosition&JHeader.LEFT)!=JFooter.RIGHT)
      footer = JTools.RPad(getFooter(),operDisplay.getlineLenOperatorDisplay()," ");
    else if ((iPosition&JHeader.RIGHT)!=JFooter.LEFT)
      footer = JTools.LPad(getFooter(),operDisplay.getlineLenOperatorDisplay()," ");

    return footer;
  }
}
