package pss.common.terminals.drivers.WindowPrinter;

import pss.common.terminals.core.JPrinterAdapter;
import pss.common.terminals.core.JTerminal;
import pss.common.terminals.interfaces.JPrinterInterface;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrOk;
import pss.core.tools.JObjectArrayPool;
import pss.core.tools.jnidisp.CFunc;

public class TWindowPrinter extends JTerminal implements JPrinterInterface {

  public static final String PORTRAIT  = "PORTRAIT";
  public static final String LANDSCAPE = "LANDSCAPE";

  public static final String COURIER_6 = "COURIER_6";
  public static final String COURIER_8 = "COURIER_8";
  public static final String COURIER_10 = "COURIER_10";
  public static final String COURIER_12 = "COURIER_12";

  public static final String LUCIDA_6 = "LUCIDA_6";
  public static final String LUCIDA_8 = "LUCIDA_8";
  public static final String LUCIDA_10 = "LUCIDA_10";
  public static final String LUCIDA_12 = "LUCIDA_12";

  private Object[] obj;
  private final JObjectArrayPool oArray = new JObjectArrayPool(7);

  StringBuffer sDocum = new StringBuffer();

  private CFunc cPrintDoc;

  private static final String LINE_FEED = "\n";

  public TWindowPrinter() throws Exception {
  }
  @Override
	public JPrinterAdapter createPrintAdapter() throws Exception {
  	return new TWindowPrinterAdapter(this);
  }

  
	@Override
	public void loadDrivers() throws Exception {
		this.addDriver(JTerminal.D_PRINTER);
	}

  @Override
	public Answer close() throws Exception {
  	return new AwrOk();
  }

  @Override
	public Answer open() throws Exception {
    if( cPrintDoc == null )
    	cPrintDoc = new CFunc( "WindowsPrinter.dll", "PrintDocument" );
    return new AwrOk();
  }
  
//----------------------------------------------------------------------------------------------------------------------
  public Answer printLine(String line) throws Exception {
    sDocum.append(line);
    sDocum.append(LINE_FEED);
    return new AwrOk();
  }
  
  public boolean sendDoc(String font, int sizeFont, String mode, int lineSpacing) throws Exception {
    obj = oArray.getObjectArray(6);
    obj[0] = this.getConnectinString();
    obj[1] = sDocum.toString();
    if( COURIER_12.equals(font) ) {
      obj[2] = "Courier";
      obj[3] = new Integer(12);
    } else if( COURIER_10.equals(font) ) {
      obj[2] = "Courier";
      obj[3] = new Integer(10);
    } else if( COURIER_8.equals(font) ) {
      obj[2] = "Courier";
      obj[3] = new Integer(8);
    } else if( COURIER_6.equals(font) ) {
      obj[2] = "Courier";
      obj[3] = new Integer( 6 );
    } else if( LUCIDA_12.equals(font) ) {
      obj[2] = "Lucida Console";
      obj[3] = new Integer( 12 );
    } else if( LUCIDA_10.equals(font) ) {
      obj[2] = "Lucida Console";
      obj[3] = new Integer( 10 );
    } else if( LUCIDA_8.equals(font) ) {
      obj[2] = "Lucida Console";
      obj[3] = new Integer( 8 );
    } else if( LUCIDA_6.equals(font) ) {
      obj[2] = "Lucida Console";
      obj[3] = new Integer( 6 );
    } else {
      obj[2] = font;
      obj[3] = new Integer( sizeFont==0?10:sizeFont );
    }
    obj[4] = new Integer(lineSpacing);
    obj[5] = new Integer( LANDSCAPE.equals( mode ) ? 1 : 0 );

    return cPrintDoc.callInt(obj) == 0;
  }

//----------------------------------------------------------------------------------------------------------------------
  public Answer openDoc() throws Exception {
  	this.open();
    sDocum.delete(0, sDocum.length());
    return new AwrOk();
  }
//----------------------------------------------------------------------------------------------------------------------
  public Answer cancelDoc() throws Exception {
    sDocum.delete(0, sDocum.length());
    return new AwrOk();
  }
//----------------------------------------------------------------------------------------------------------------------
  public Answer flush() throws Exception {
    return new AwrOk();
  }
  
  public Answer closeDoc() throws Exception {
    sDocum.delete(0, sDocum.length());
    return new AwrOk();
  }

//----------------------------------------------------------------------------------------------------------------------
  public Answer closeDay() throws Exception {
  	return new AwrOk();
  }
//----------------------------------------------------------------------------------------------------------------------
  public Answer closeShift() throws Exception {
  	return new AwrOk();
  }
//----------------------------------------------------------------------------------------------------------------------
  private void newLine() throws Exception {
    sDocum.append(LINE_FEED);
  }
//----------------------------------------------------------------------------------------------------------------------
  private void newLine( int howManyLines ) throws Exception {
    for( int i = 0; i < howManyLines; i++ ) {
    	this.newLine();
    }
  }
//----------------------------------------------------------------------------------------------------------------------
  public Answer skeepLines(int lines) throws Exception {
    this.newLine(lines);
    return new AwrOk();
  }
}
