package pss.common.terminals.drivers.EpsonSerial;

import pss.common.terminals.core.JTerminal;
import pss.common.terminals.interfaces.JCashDrawerInterface;
import pss.common.terminals.interfaces.JPrinterInterface;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrOk;
import pss.core.connectivity.client.serial.JSerialClient;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;

public class TEpsonSerial extends JTerminal implements JPrinterInterface,
																											 JCashDrawerInterface {

	private static final String START       = String.valueOf((char)27) + String.valueOf((char)99)  + String.valueOf((char)48);
	private static final String CUT_TICKET  = START + String.valueOf((char)3) + String.valueOf((char)27) + String.valueOf((char)100) + String.valueOf((char)12) + String.valueOf((char)27) + String.valueOf((char)99) + String.valueOf((char)3) + String.valueOf((char)27) + String.valueOf((char)105);
	private static final String RETURN_CAR  = "\n";
	private static final String OPEN_CASH   = String.valueOf((char)27) + String.valueOf((char)112) + String.valueOf((char)0)  + String.valueOf((char)1) + String.valueOf((char)2);

	
	
	private JSerialClient serial=null;
//	private int linesToSkip;
	
	public TEpsonSerial() {
	}
	
	@Override
	public void loadDrivers() throws Exception {
		this.addDriver(JTerminal.D_PRINTER);
		this.addDriver(JTerminal.D_CASH_DRAWER);
	}

	
  @Override
	public Answer open() throws Exception {
  	this.close();
    JSerialClient serialConn = this.createSerialConnection();
    if (!serialConn.connect())
   	 JExcepcion.SendError("No se pudo abrir el Puerto: " + serial.getPort());
    this.serial = serialConn;
    return new AwrOk();
  }
  
  @Override
	public Answer close() throws Exception {
    if( serial != null ) {
    	this.serial.disconnect();
    	this.serial=null;
    }
    return new AwrOk();
  }

  

	public Answer closeShift() throws Exception {
		return null;
	}
	public Answer closeDay() throws Exception {
		return null;
	}


  public Answer printLine(String line) throws Exception {
    Thread.sleep( 200 );
    serial.write( JTools.getBytesAscii(line) );
    serial.write( RETURN_CAR );
    Thread.sleep( 200 );
    return new AwrOk();
  }

  public Answer openDoc() throws Exception {
  	return new AwrOk();
  }
  
  public Answer cancelDoc() throws Exception {
  	this.newLine(3);
    Thread.sleep( 300 );
    this.printLine("** COMPROBANTE ANULADO POR EL USUARIO **");
    this.newLine(5);
    serial.write(CUT_TICKET);
    return new AwrOk();
  }

  public Answer closeDoc() throws Exception {
  	serial.write(CUT_TICKET);
 		return new AwrOk();
  }

  public Answer flush() throws Exception {
 		return new AwrOk();
  }
  
  public Answer skeepLines(int lines) throws Exception {
  	for (int i=0; i<lines; i++) {
  		this.newLine();
  	}
  	return new AwrOk();
  }

  public Answer openCashDrawer() throws Exception {
	  serial.write(OPEN_CASH);
	  return new AwrOk();
  }

  public Answer isCashDrawerOpen() throws Exception {
    JExcepcion.SendError("Impresora sin senson de cajon de dinero");
    return null;
  }
  

  public void newLine() throws Exception {
    this.printLine("");
  }

  private void newLine(int howManyLines) throws Exception {
    for(int i=0; i<howManyLines; i++) { this.printLine(""); }
  }
	
}
