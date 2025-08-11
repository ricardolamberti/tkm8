package pss.common.terminals.messages.requires.display;

/**
 * Description:
 *
 * @author Ricardo Lamberti, Iván Rubín
 *
 */
import pss.common.terminals.core.JTerminalGroup;

public class JInput {
  public static final int NO_SYMBOL = 0;
  public static final int NO_DELIMITER = 0;

  public static final int FIELD_TYPE_NUMERIC = 0x01;
  public static final int FIELD_TYPE_ALPHANUM = 0x02;
  public static final int FIELD_TYPE_PASSWORD = 0x04;
  public static final int FIELD_TYPE_AMOUNT = 0x80;

  @SuppressWarnings("unused")
  private String sLabel;
  @SuppressWarnings("unused")
  private String sExtraInfo = "";
  @SuppressWarnings("unused")
  private char   cSymbol = JInput.NO_SYMBOL;
  @SuppressWarnings("unused")
  private char   cCharDelimiterOpen = '[';
  @SuppressWarnings("unused")
  private char   cCharDelimiterClose = ']';

  @SuppressWarnings("unused")
  private int    iType = JInput.FIELD_TYPE_NUMERIC;
  @SuppressWarnings("unused")
  private int    iMaxPlace = 0;
  @SuppressWarnings("unused")
  private int    iMinPlace = 0;
  @SuppressWarnings("unused")
  private int    iDecPlace = 0;
  @SuppressWarnings("unused")
  private int    iTimeoutKey = 100000;
  @SuppressWarnings("unused")
  private String sDefault = "";
  @SuppressWarnings("unused")
  private boolean bPinPad = false;
  @SuppressWarnings("unused")
  private String sWKey = "";
  @SuppressWarnings("unused")
  private long iKeyIndex = 0;
  @SuppressWarnings("unused")
  private int iCriptMethod = 0;
  @SuppressWarnings("unused")
  private String sPan = "";

  public JInput( String zLabel ) {
    this.sLabel = zLabel;
  }

  public void setExtraInfo( String extra )    { if (extra!=null) this.sExtraInfo = extra; else this.sExtraInfo="";}
  public void setSymbol( char c )    { this.cSymbol = c; }
  public void setDelimiter( char open, char close ) {
    this.cCharDelimiterOpen = open;
    this.cCharDelimiterClose = close;
  }

  public void setType( int zType ) { this.iType = zType; }
  public void setMaxPlace( int zMax ) { this.iMaxPlace = zMax; }
  public void setMinPlace( int zMin ) { this.iMinPlace = zMin; }
  public void setDecPlace( int zDec ) { this.iDecPlace = zDec; }
  public void setTimeoutKey( int zTimeout ) { this.iTimeoutKey = zTimeout; }
  public void setDefault( String zDefault ) { this.sDefault = zDefault; }
  public void setPinPad( String wKey, long mKeyIndex, int zCriptMethod, String pan ) {
    this.sWKey = wKey;
    this.iKeyIndex = mKeyIndex;
    this.iCriptMethod = zCriptMethod;
    this.sPan = pan;
    this.bPinPad = true;
  }

  public String question( JHeader zHeader,JFooter zFooter,JTerminalGroup pool, long zTimeout) throws Exception {
/*    double decPlace[] = {1.0, 10.0, 100.0, 1000.0, 10000.0, 100000.0, 1000000.0, 10000000.0, 100000000.0};
    if (bPinPad)
      return oTCom.readPin(sLabel,sWKey,(int)iKeyIndex,iCriptMethod,sPan,iMaxPlace,(int)zTimeout);

     if (zFooter==null) {
       zFooter = new JFooter("");
       zFooter.addFooter("<CANCEL>");
       zFooter.addFooter("<ENTER>");
     }

     String oAnswer = oTCom.getInputTraslated(zHeader==null?"":zHeader.getHeaderFormatted(pool),
                          JLanguage.translateExtendedText(sLabel),
                          JLanguage.translateExtendedText(sExtraInfo),
                          zFooter.getFooterFormatted(oTCom),
                          cCharDelimiterOpen,cCharDelimiterClose,
                          iType, iMaxPlace, iMinPlace, iDecPlace,
                          sDefault, (int) zTimeout, iTimeoutKey);

    if (iDecPlace>0 && !(oAnswer.equals("ERROR") || oAnswer.equals("TIMEOUT") || oAnswer.equals("CANCELAR"))) {
      oAnswer = String.valueOf(Long.parseLong(oAnswer)/decPlace[iDecPlace]);
  
  	}
*/
    return null;
  }

}
