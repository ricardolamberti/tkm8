package pss.common.terminals.core;

import pss.common.terminals.interfaces.JCardReadInterface;
import pss.common.terminals.interfaces.JClientKeyboarInterface;
import pss.common.terminals.interfaces.JOperatorKeyboarInterface;
import pss.common.terminals.interfaces.JTagReadInterface;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrError;
import pss.common.terminals.messages.requires.JRequire;
import pss.common.terminals.messages.requires.common.JCaptureEvent;
import pss.core.connectivity.client.parallel.JParallelClient;
import pss.core.connectivity.client.serial.JSerialClient;
import pss.core.services.fields.JObjRecord;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;

public abstract class JTerminal extends JObjRecord {

	public static final int D_ALL = 0;
  public static final int D_CRYPTO = 1;
  public static final int D_MAGNETIC_READ = 2;
  public static final int D_MAGNETIC_WRITE = 3;
  public static final int D_CHIP_READ = 4;
  public static final int D_TAG = 5;
  public static final int D_PRINTER = 6;
  public static final int D_CASH_DRAWER = 7;
//  public static final int D_CASH_DRAWER_SENSOR = 8;
  public static final int D_OPERATOR_KEYB = 9;
  public static final int D_CLIENT_KEYB = 10;
  public static final int D_OPERATOR_DISPLAY = 11;
  public static final int D_CLIENT_DISPLAY = 12;
  public static final int D_CUSTOMER_DISPLAY = 13;
  public static final int D_BARCODE_READER = 14;
  
  public static final int POS_TOP    = 0x02;
  public static final int POS_DOWN   = 0x04;
  public static final int POS_RIGHT  = 0x08;
  public static final int POS_LEFT   = 0x10;
  public static final int POS_VERTICAL_CENTER   = 0x20;
  public static final int POS_HORIZONTAL_CENTER   = 0x40;
  public static final int POS_TOPLEFT   = POS_TOP | POS_LEFT;
  public static final int POS_CENTER = POS_VERTICAL_CENTER | POS_HORIZONTAL_CENTER;

  
  private static JMap<String, String> driverTypes=null; 
  private JMap<String, String> drivers;
  private JMap<String, String> availablesDrivers;
  private String connectionString;
  private String sType=null;
  private long lTerminalPool=0L;
  private long lTerminalId=0L;
  private String sNroSerie=null;
  private String sMacAddress=null;
  private String sCompany=null;
  private JPrinterAdapter printAdapter = null;
	
  public abstract void loadDrivers() throws Exception;
  public abstract Answer open() throws Exception;
  public abstract Answer close() throws Exception;


  public JMap<String, String> getAllDrivers() throws Exception {
  	if (this.drivers!=null) return this.drivers;
  	this.loadDrivers();
  	return this.drivers;
  }
  protected void addDriver(int driverType) throws Exception {
  	if (drivers==null) this.drivers = JCollectionFactory.createMap();
  	String sDriverType = String.valueOf(driverType);
  	drivers.addElement(sDriverType, JTerminal.getDriversTypes().getElement(sDriverType));
  }
  public void enableDriver(int driverType) throws Exception {
  	if (!this.hasDriverFor(driverType)) JExcepcion.SendError("No existe Driver en la terminal");
  	if (this.availablesDrivers==null) this.availablesDrivers=JCollectionFactory.createMap();
  	this.availablesDrivers.addElement(String.valueOf(driverType), String.valueOf(driverType));
  }
  public boolean hasDriverFor(int value) throws Exception {
  	return this.getAllDrivers().containsKey(String.valueOf(value));
  }
  public boolean hasAvailableDriverFor(int value) throws Exception {
  	return this.availablesDrivers.containsKey(String.valueOf(value));
  }
  public JMap<String, String> getAvailableDrivers() throws Exception {
  	return this.availablesDrivers;
  }
  
  public String getConnectinString() {
  	return this.connectionString;
  }
  
  public void setConnectionString(String value) {
  	this.connectionString=value;
  }
  public void setCompany(String value) {
  	this.sCompany=value;
  }
  public String getCompany() {
  	return this.sCompany;
  }
  
  public void setTerminalId(long value) {
  	this.lTerminalId=value;
  }
  public void setTerminalPool(long value) {
  	this.lTerminalPool=value;
  }
  public void setNroSerie(String value) {
  	this.sNroSerie=value;
  }
  public void setMacAddress(String value) {
  	this.sMacAddress=value;
  }
  public void setType(String value) {
  	this.sType=value;
  }
  public long getTerminalPool() {
  	return this.lTerminalPool;
  }
  public long getTerminalId() {
  	return this.lTerminalId;
  }
  public String getNroSerie() {
  	return this.sNroSerie;
  }
  public String getMacAddress() {
  	return this.sMacAddress;
  }
  public String getType() {
  	return this.sType;
  }
  
  public final JPrinterAdapter getPrintAdapter() throws Exception {
  	if (this.printAdapter!=null) return this.printAdapter;
  	return (this.printAdapter=this.createPrintAdapter());
  }
  
  public JPrinterAdapter createPrintAdapter() throws Exception {
  	return new JPrinterAdapter(this);
  }

  public JSerialClient createSerialConnection() throws Exception {
  	if (this.connectionString==null) JExcepcion.SendError("Debe configurar el string de conexión");
  	JStringTokenizer tokens = JCollectionFactory.createStringTokenizer(this.connectionString, ';');
  	JSerialClient serial = new JSerialClient();
    serial.setPort(tokens.nextToken());
    if (!tokens.hasMoreTokens()) return serial; 
    serial.setBauds(Integer.parseInt(tokens.nextToken()));
    if (!tokens.hasMoreTokens()) return serial;
    serial.setDataBits(Integer.parseInt(tokens.nextToken()));
    if (!tokens.hasMoreTokens()) return serial;
    serial.setParity(tokens.nextToken());
    if (!tokens.hasMoreTokens()) return serial;
    serial.setStopBit(Integer.parseInt(tokens.nextToken()));
    return serial;
  }
  
  public JParallelClient createParallelConnection() throws Exception {
  	if (this.connectionString==null) JExcepcion.SendError("Debe configurar el string de conexión");
  	JStringTokenizer tokens = JCollectionFactory.createStringTokenizer(this.connectionString, ';');
  	JParallelClient parallel = new JParallelClient();
  	parallel.setPort(tokens.nextToken());
    return parallel;
  }
  
  
  public void startPolling() throws Exception {
  }
  public void stopPolling() throws Exception {
  }


  public void waitTerminalEvent(JCaptureEvent captureEvent) {
    try {
      while(true) {
        if (this.verifyClientKeyboard(captureEvent)) return;
        if (this.verifyOperatorKeyboard(captureEvent)) return;
        if (this.verifyMCR(captureEvent)) return;
        if (this.verifyTAG(captureEvent)) return;
      }
    } catch( InterruptedException e ) {
    	return;
    } catch( Exception e ) {
    	captureEvent.setLastEvent(new AwrError(e));
    } 
  }


  private boolean verifyClientKeyboard(JCaptureEvent captureEvent) throws Exception {
  	if( Thread.currentThread().isInterrupted() ) return true;
  	if( !captureEvent.hasToCheckClientKey()) return false;
  	if (!this.hasAvailableDriverFor(D_CLIENT_KEYB)) return false;
  	
    Answer event = ((JClientKeyboarInterface)this).readInputByKey(0);
    if (!event.isKey()) return false;
    captureEvent.setLastEvent(event);
    return true;
  }
  
  private boolean verifyOperatorKeyboard(JCaptureEvent captureEvent) throws Exception {
  	if( Thread.currentThread().isInterrupted() ) return true;
  	if( !captureEvent.hasToCheckOperatorKey()) return false;
  	if (!this.hasAvailableDriverFor(D_OPERATOR_KEYB)) return false;
  	
    Answer event = ((JOperatorKeyboarInterface)this).readInputByKey(0);
    if (!event.isKey()) return false;
    captureEvent.setLastEvent(event);
    return true;
  }
  
  private boolean verifyMCR(JCaptureEvent captureEvent) throws Exception {
  	if( Thread.currentThread().isInterrupted() ) return true;
  	if( !captureEvent.hasToCheckMCR()) return false;
  	if (!this.hasAvailableDriverFor(D_MAGNETIC_READ)) return false;

  	Answer event = ((JCardReadInterface)this).readCard();
  	if (!event.isMagneticCard()) return false;
  	captureEvent.setLastEvent(event);
  	return true;
  }

  private boolean verifyTAG(JCaptureEvent captureEvent) throws Exception {
  	if( Thread.currentThread().isInterrupted() ) return true;
  	if( !captureEvent.hasToCheckTAG()) return false;
  	if (!this.hasAvailableDriverFor(D_TAG)) return false;
  	Answer event = ((JTagReadInterface)this).readTag();
  	if (!event.isTag()) return false;
  	captureEvent.setLastEvent(event);
  	return true;
  }

  public static synchronized JMap<String, String> getDriversTypes() throws Exception {
  	if (driverTypes!=null) return driverTypes; 
  	JMap<String, String> map = JCollectionFactory.createMap();
  	map.addElement(String.valueOf(D_CRYPTO), "Procesador Criptográfico");
  	map.addElement(String.valueOf(D_MAGNETIC_READ), "Lector Tarjeta Magnética");
  	map.addElement(String.valueOf(D_MAGNETIC_WRITE), "Grabador Tarjeta Magnética");
  	map.addElement(String.valueOf(D_CHIP_READ), "Lectora Tarjeta CHIP");
  	map.addElement(String.valueOf(D_TAG), "Lectora TAG");
  	map.addElement(String.valueOf(D_PRINTER), "Impresora");
  	map.addElement(String.valueOf(D_CASH_DRAWER), "Cajón de Dinero");
//  	map.addElement(String.valueOf(D_CASH_DRAWER_SENSOR), "Sensor Cajón Dinero Abierto");
  	map.addElement(String.valueOf(D_OPERATOR_KEYB), "Teclado Operador");
  	map.addElement(String.valueOf(D_CLIENT_KEYB), "Teclado Cliente");
  	map.addElement(String.valueOf(D_OPERATOR_DISPLAY), "Display Operador");
  	map.addElement(String.valueOf(D_CLIENT_DISPLAY), "Display Cliente");
  	map.addElement(String.valueOf(D_CUSTOMER_DISPLAY), "Customer Display");
  	map.addElement(String.valueOf(D_BARCODE_READER), "Lector Código de Barras");
  	return (driverTypes=map);
  }
  
	public Answer process(JRequire require) throws Exception {
		require.addTerminal(this);
		require.executeRequire();
		return require.getAnswer();
	}
	public boolean processAsBoolean(JRequire require) throws Exception {
		this.process(require);
		return require.answerAsBoolean();
	}
	public String processAsString(JRequire require) throws Exception {
		this.process(require);
		return require.answerAsString();
	}
	
  
}
