package pss.common.terminals.messages.requires.display;

/**
 * Description:
 *
 * @author Ricardo Lamberti, Iván Rubín
 *
 */

public class JText {
  private int oModo;
  private String oText;
  private int oPosX;
  private int oPosY;
  private int oPosition;
  private boolean oCutWords;

  public int getModo()       { return this.oModo; }
  public int getPosX()       { return this.oPosX; }
  public int getPosY()       { return this.oPosY; }
  public int getPosition()       { return this.oPosition; }
  public boolean getCutWords()       { return this.oCutWords; }
  public String getText()       { return this.oText; }

  public JText(int modo, String text, int posx, int posy , int position, boolean cutWords) {
    oModo = modo;
    oText = text;
    oPosX = posx;
    oPosY = posy;
    oPosition = position;
    oCutWords = cutWords;
  }

}
