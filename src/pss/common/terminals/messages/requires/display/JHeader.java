package pss.common.terminals.messages.requires.display;

/**
 * Description:
 *
 * @author Ricardo Lamberti, Iván Rubín
 */

import pss.common.terminals.interfaces.JOperatorDisplayInterface;
import pss.core.tools.JTools;


public class JHeader {
  public static final int LEFT = 0x02;
  public static final int RIGHT = 0x04;
  public static final int CENTER = 0x06;

  private String sHeader = "";
  private String sButtons = "";
  private int    iPosition = JHeader.LEFT;

  public JHeader( String zHeader ) {
    this.sHeader = zHeader;
    this.iPosition = LEFT;
  }

  public JHeader( String zHeader, int zPosition ) {
    this.sHeader = zHeader;
    this.iPosition = zPosition;
  }

  public String getHeader()   { return this.sHeader; }
  public int    getPosition() { return this.iPosition; }

  public void addHeader(String zHead)   {
      this.sButtons += zHead;
  }

  public String getHeaderFormatted(JOperatorDisplayInterface operDisplay) throws Exception {
    String header = "";
    int len = 0;
    int lenHeader = 0;

    lenHeader = getHeader().length();
    len = sButtons.equals("")?0:sButtons.length()+3;
    if (len>operDisplay.getlineLenOperatorDisplay()-lenHeader)
      len = operDisplay.getlineLenOperatorDisplay()-lenHeader;
    if (len<0) len =0;
    if ((iPosition&JHeader.CENTER)==JHeader.CENTER)
      header = JTools.centerString(getHeader(),operDisplay.getlineLenOperatorDisplay()-len,' ');
    else if ((iPosition&JHeader.LEFT)!=JHeader.RIGHT)
      header = JTools.RPad(getHeader(),operDisplay.getlineLenOperatorDisplay()-len," ");
    else if ((iPosition&JHeader.RIGHT)!=JHeader.LEFT)
      header = JTools.LPad(getHeader(),operDisplay.getlineLenOperatorDisplay()-len," ");

    if (len!=0)
      header+="<"+sButtons.substring(0,len-3)+"> ";

    return header;
  }
}
