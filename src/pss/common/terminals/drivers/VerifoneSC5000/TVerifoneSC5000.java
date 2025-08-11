package pss.common.terminals.drivers.VerifoneSC5000;

import pss.common.terminals.core.JTerminal;
import pss.common.terminals.interfaces.JCardReadInterface;
import pss.common.terminals.interfaces.JClientDisplayInterface;
import pss.common.terminals.interfaces.JClientKeyboarInterface;
import pss.common.terminals.interfaces.JDataEncriptInterface;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrClientAccept;
import pss.common.terminals.messages.answer.AwrClientCancel;
import pss.common.terminals.messages.answer.AwrClientKey;
import pss.common.terminals.messages.answer.AwrData;
import pss.common.terminals.messages.answer.AwrError;
import pss.common.terminals.messages.answer.AwrMagneticCard;
import pss.common.terminals.messages.answer.AwrOk;
import pss.common.terminals.messages.answer.AwrPinBlock;
import pss.common.terminals.messages.answer.AwrStringInput;
import pss.common.terminals.messages.answer.AwrTimeout;
import pss.common.terminals.messages.requires.cripto.JDes;
import pss.common.terminals.messages.requires.cripto.JPin;
import pss.common.terminals.messages.requires.display.JClientDisplay;
import pss.core.connectivity.client.serial.JSerialClient;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;

public class TVerifoneSC5000 extends JTerminal implements JClientDisplayInterface,
																													JCardReadInterface,
																													JClientKeyboarInterface,
																													JDataEncriptInterface {

  public final static int GOOD_READ = 0;
  public final static int TIMEOUT  = -3;
  
  private final static char STX = 0x02;
  private final static char ETX = 0x03;
  private final static char ABORT = 0x04;
  private final static char ACK = 0x06;
  private final static char EOT = 0x0F;
  private final static char NAK = 0x0F;
  private final static char SI  = 0x0F;
  private final static char SO  = 0x0E;
  private final static long MESSAGE_TIMEOUT = 3000;
  private final static long MESSAGE_LONG_TIMEOUT = 6000;
  
  
  private boolean noDisplay = false;
  private JSerialClient serialClient=null;

	public TVerifoneSC5000() {
	}
	
  @Override
	public void loadDrivers() throws Exception {
		this.addDriver(JTerminal.D_CRYPTO);
		this.addDriver(JTerminal.D_CLIENT_KEYB);
		this.addDriver(JTerminal.D_CLIENT_DISPLAY);
		this.addDriver(JTerminal.D_MAGNETIC_READ);
  }
  
  
  private String getBlankLine() {
  	return "                ";
  }
  
  public int getLinesClientDisplay() { 
  	return 2;
  }

  public int getLineLenClientDisplay() { 
  	return 16;
  }

/*  public void limpia() throws Exception {
    for (int i=0;i<getLines();i++)
    	sLine[i] = this.getBlankLine();
  }
*/

//----------------------------------------------------------------------------------------------------------------------
  private boolean acceptCancel(String msg) throws Exception {
  	this.displayData(msg, 1, 1, true);
  	Answer result = readKey(20000);
  	return result.isAccept();
  }

  public Answer clientDisplay(JClientDisplay display) throws Exception {
  	this.displayData(display.getInformation(), 1, 1, true);
	  return new AwrOk();
  }

  public void loadMasterKey(int iIndex, String mk) throws Exception {
	  byte[] mess = new byte[22];
	  int i=0,j=0;
	  mk = JTools.LPad(mk,16,"0");
	  try {
		  mess[i++]= SI;
		  mess[i++]= '0';
		  mess[i++]= '2';
		  mess[i++]= (byte)(iIndex+'0');
   	      for(j=0;j<16 && j<mk.length();j++)
		   	mess[i++] = mk.getBytes()[j];
		  mess[i++]= SO;
		  mess[i++]= LRC(mess,i);
		  this.sendReceive(mess,MESSAGE_LONG_TIMEOUT,SO);
	  }
	  catch(Exception e) {
		  PssLogger.logError(e);
	  }
  }

  @Override
	public  Answer open() throws Exception {
  	this.close();
	  JSerialClient serial = this.createSerialConnection();
	  serial.setDataBits(7);
	  serial.setParity(JSerialClient.PAR_EVEN);
	  serial.setStopBit(1);
//		if( !serial.connect() )
//			JExcepcion.SendError("No se pudo abrir el Puerto: ^" + serial.getPort());
		this.serialClient = serial;
  	return new AwrOk();
  }
  
  @Override
	public Answer close() throws Exception {
  	if (this.serialClient!=null) {
    	this.serialClient.disconnect();
    	this.serialClient=null;
    }
  	return new AwrOk();
  }



//----------------------------------------------------------------------------------------------------------------------
  private byte LRC (byte[] zMess,int length) throws Exception {
	  byte value = 0;
	  for (int i=1;i<length;i++) {
		  value ^= zMess[i];
	  }
	  return value;
  }
    
  public void displayData(String msg, int xPos, int yPos, boolean center) throws Exception {
	  if (msg==null)
		  return;
	  PssLogger.logDebug("Mensaje client: -"+msg+"- x:"+xPos+" y:"+yPos);
	  if (yPos>this.getLinesClientDisplay()) return; 

		String[] sLines = JTools.stringToLines(msg, this.getLineLenClientDisplay(), this.getLinesClientDisplay(), center, false);
		this.display(sLines[0], sLines[1]);
  }
  
  private void display(String line1, String line2) throws Exception {
	  if (noDisplay) return;
	  byte[] mess = new byte[38];
	  int i=0,j=0;
	  i=0;
	  mess[i++]= STX;
	  mess[i++]= 'Z';
	  mess[i++]= '2';
	  mess[i++]= 0x1A; // clear screen
	  for(j=0;j<line1.length();j++)
		  mess[i++] = line1.getBytes()[j];
	  for(;j<16;j++)
		  mess[i++] = ' ';
	  for(j=0;j<line2.length();j++)
		  mess[i++] = line2.getBytes()[j];
	  for(;j<16;j++)
		  mess[i++] = ' ';
	  mess[i++]= ETX;
	  mess[i++]= LRC(mess,i);
	  send(mess);
  }

/*  protected void userPressedEnter() throws Exception {
	String result;
    while (true) {
      result = readKey(10000);
      if (result.equals("TIMEOUT")) {
    	return;
      }
      if (result.equals("ERROR")) {
      	return;
      }
      if (result.equals("\r")) {
        	return;
        }
      Thread.sleep(100);
    }
  }
  
*///----------------------------------------------------------------------------------------------------------------------
  private boolean selectMasterKey(int idMK) throws Exception {
	  byte[] mess = new byte[6];
	  int i=0;
	  mess[i++]= SI;
	  mess[i++]= '0';
	  mess[i++]= '8';
	  mess[i++]= (byte)(idMK+'0');
	  mess[i++]= SO;
	  mess[i++]= LRC(mess,i);
	  return send(mess)==ACK;
  }
  
  public boolean cancelSesion() throws Exception {
	  byte[] mess = new byte[5];
	  int i=0;
	  mess[i++]= STX;
	  mess[i++]= '7';
	  mess[i++]= '2';
	  mess[i++]= ETX;
	  mess[i++]= LRC(mess,i);
	  return send(mess)==ACK;
  }


  public Answer msgPin(String msg1,String msg2,String msg3, int pinLen,String wk,String panBuf,int timeout) throws Exception {
	  byte[] mess = new byte[16+16+panBuf.length()+msg1.length()+msg2.length()+msg3.length()];
	  if (pinLen>16) pinLen = 16;
	  String sPinLen = String.valueOf(pinLen);
	  wk = JTools.LPad(wk,16,"0");
	  sPinLen = JTools.LPad(sPinLen,2,"0");
	  int i=0,j=0;
	  mess[i++]= STX;
	  mess[i++]= 'Z';
	  mess[i++]= '6';
	  mess[i++]= '2';
	  mess[i++]= '.';
      for(j=0;j<19 && j<panBuf.length();j++)
	   	mess[i++] = panBuf.getBytes()[j];
	  mess[i++]= 0x1C;
      for(j=0;j<16 && j<wk.length();j++)
  	   	mess[i++] = wk.getBytes()[j];
      for(j=0;j<sPinLen.length();j++)
    	   	mess[i++] = sPinLen.getBytes()[j];
      for(j=0;j<sPinLen.length();j++)
  	   	mess[i++] = sPinLen.getBytes()[j];
	  mess[i++]= 'N';
      for(j=0;j<16 && j<msg1.length();j++)
    	   	mess[i++] = msg1.getBytes()[j];
	  mess[i++]= 0x1C;
      for(j=0;j<16 && j<msg2.length();j++)
  	   	mess[i++] = msg2.getBytes()[j];
	  mess[i++]= 0x1C;
      for(j=0;j<16 && j<msg3.length();j++)
    	   	mess[i++] = msg3.getBytes()[j];
	  mess[i++]= 0x1C;
	  mess[i++]= ETX;
	  mess[i++]= LRC(mess,i);
	  Answer event = this.sendReceive(mess,MESSAGE_TIMEOUT+timeout,ETX);
	  if (!event.isData()) return event;
	  String resp = event.toString();
	  if (resp.length()>9+16) {
		  return new AwrPinBlock(resp.substring(9,16+9));
	  }
	  return new AwrError();
  }

  public Answer readPin(JPin pinModel) throws Exception {
    if (pinModel.hasMsgConfirm()) {
    	if( !this.acceptCancel(pinModel.getMsgConfirm()+"\n"+ pinModel.getAmount()) )  {
    		this.clearClientDisplay();
    		return new AwrClientCancel();
    	}
    }
    Answer result = new AwrError();

    if (pinModel.getMasterKeyIndex() > 9)
    	JExcepcion.SendError("Indice de Master Key invalido");

    if (this.selectMasterKey(pinModel.getMasterKeyIndex())) {
  		result = this.msgPin(pinModel.getMsgClave(),"",pinModel.getMsgProcess(), pinModel.getPinLen(), pinModel.getWorkingKey(), pinModel.getPan(), pinModel.getTimeout());
  		this.cancelSesion();
  	}
  	PssLogger.logDebug("PINBLOCK: "+result);
//    this.clearDisplay();
    return result;
  }

//----------------------------------------------------------------------------------------------------------------------
  public Answer getInput(int fieldType, int max, int min, int dec, int xPos, int yPos, String inicial, int timeoutTotal, int timeoutTecla) throws Exception  {
	  byte[] mess = new byte[12];
	  int i=0;
	  if (timeoutTotal>255) timeoutTotal = 255;
	  String sTimeout = String.valueOf(timeoutTotal);
	  sTimeout = JTools.LPad(sTimeout,3,"0");
	  if (max>49) throw new Exception("Max no debe superar 49");
	  String sMax = String.valueOf(max);
	  sMax = JTools.LPad(sMax,2,"0");
	  i=0;
	  mess[i++]= STX;
	  mess[i++]= 'Z';
	  mess[i++]= '5';
	  mess[i++]= '0';
	  mess[i++]= ((fieldType&0x04)!=0)?(byte)48:(byte)49; // get buffer
	  mess[i++]= (byte)sTimeout.charAt(0);
	  mess[i++]= (byte)sTimeout.charAt(1);
	  mess[i++]= (byte)sTimeout.charAt(2);
	  mess[i++]= (byte)sMax.charAt(0);
	  mess[i++]= (byte)sMax.charAt(1);
	  mess[i++]= ETX;
	  mess[i++]= this.LRC(mess,i);
	  Answer event = this.sendReceive(mess,MESSAGE_TIMEOUT+(timeoutTotal*1000),ETX);
	  if (!event.isData()) return event;
	  String resp = event.toString();
	  if (resp.length()<=0) return new AwrError();
	  if (resp.charAt(4)=='?') return new AwrTimeout(0);
	  return new AwrStringInput(resp);
  }
//----------------------------------------------------------------------------------------------------------------------
  public Answer readKey(int timeout) throws Exception {
		  byte[] mess = new byte[9];
		  timeout/=1000;
		  if (timeout>255) timeout = 255;
		  String sTimeout = String.valueOf(timeout);
		  sTimeout = JTools.LPad(sTimeout,3,"0");
		  int i=0;

		  mess[i++]= STX;
		  mess[i++]= 'Z';
		  mess[i++]= '4';
		  mess[i++]= '2';
		  mess[i++]= (byte)sTimeout.charAt(0);
		  mess[i++]= (byte)sTimeout.charAt(1);
		  mess[i++]= (byte)sTimeout.charAt(2);
		  mess[i++]= ETX;
		  mess[i++]= LRC(mess,i);
		  Answer event = this.sendReceive(mess,MESSAGE_TIMEOUT+(timeout*1000),ETX);
		  if (!event.isData()) return event;
		  String resp = event.toString();
		  if (resp.length()<=0) return new AwrError();
			if (resp.charAt(4)=='?') return new AwrTimeout(0);
		  char tecla = resp.charAt(4);
		  if (tecla=='A') tecla = 'a';
		  if (tecla=='B') tecla = 'b';
		  if (tecla=='C') tecla = 'c';
		  if (tecla=='*') return new AwrClientCancel();
		  if (tecla=='#') return new AwrClientAccept();
		  return new AwrClientKey(tecla);
  }

//  public JEvent readKeys() throws Exception {
//		 byte[] mess = new byte[7];
//		 int i=0,sizeResp;
//		
//		 i=0;
//		 mess[i++]= STX;
//		 mess[i++]= 'Z';
//		 mess[i++]= '4';
//		 mess[i++]= '4';
//		 mess[i++]= '1'; // get buffer
//		 mess[i++]= ETX;
//		 mess[i++]= LRC(mess,i);
//		 JEvent event = this.sendReceive(mess,MESSAGE_TIMEOUT,ETX);
//		 if (!event.isRawResponse()) return event;
//		 String resp = event.toString();
//		 if (resp.length()<=6) return new JError(); 
//		 sizeResp=(resp.charAt(4)-'0')*10+(resp.charAt(5)-'0');
//		 if (sizeResp==99 || sizeResp==0) return new JError();
//		 resp =  resp.substring(6,sizeResp);
//		 return result;
//   }
   //----------------------------------------------------------------------------------------------------------------------
/*   public int readInputByKey(int timeOut) {
	   int tecla = 0;
	   try {
		   tecla = readInputByKey();
		   while (tecla==0 && timeOut-->0) {
			   Thread.sleep(1);
			   tecla = readInputByKey();
		   }
	   }
	   catch (Exception e) {
		   JDebugPrint.logError(e);
	   }
	   return tecla;
   }
*/// ----------------------------------------------------------------------------------------------------------------------
//   private String buffer = "";

//   public JKey readInputByKey() {
//	   int tecla;
//	   buffer += readKey(1000);
//	   if (buffer.length()==0) return 0;
//	   tecla = buffer.charAt(0);
//	   buffer = buffer.substring(1);
//
//	   return tecla;
//   }
//   public void clearBuffer() {
//	   buffer = "";
//   }
   
   public Answer sendReceive(byte mess[],long timeout,char end) throws Exception {
	   byte[] ack = new byte[1];
	   String ask = "";
	   String resp = "";
	   boolean respOK = false;
	   JSerialClient serial = this.serialClient;
	   synchronized (serial) {
		   Thread.sleep(50);
		   for(int retry =0;retry<3;retry++) {
			   if (!serial.write(mess)) {
				   PssLogger.logError("No se pudo escribir en el puerto");
				   serial.disconnect();
				   serial.connect();
				   continue;
			   }
			   while (ask.length()==0) {
				   ask = serial.recv();
				   if (ask.length()>0) break;
				   Thread.sleep(10);
			   }
			   if (ask.charAt(0) == EOT) return new AwrData("");
			   if (ask.charAt(0) == ACK) break;
			   if (ask.charAt(0) == ABORT) 
			  	 return new AwrClientCancel();			   
		   }
		   if (ask.length()>1) resp = ask.substring(1);
		   for(int retry =0;!respOK && retry<3;retry++) {
			   if (retry!=0) {
				   resp = "";
				   ack[0] = NAK;
				   serial.write(ack);
				   resp = "";
			   }
			   long inicio = System.currentTimeMillis();
			   while (System.currentTimeMillis()-inicio<timeout) {
				   if (resp.length()>0)  {
					   if (resp.charAt(0)==ABORT) 
					  	 return new AwrClientCancel();
					   if (end!=SO && resp.charAt(0)==EOT) break;
					   if (resp.length()>1)  {
						   if (resp.charAt(resp.length()-2)==end) {
							   if (resp.charAt(resp.length()-1)==LRC(resp.getBytes(),resp.length()-1)) {
								   ack[0] = ACK;
								   serial.write(ack);
								   respOK = true;
								   break;
							   }
							   else {
								   break;
							   }
						   }
					   }
				   }
				   resp += serial.recv();
				   Thread.sleep(10);
			   }
		   }
	   }
	   return new AwrData(resp);
  }

  public char send(byte mess[]) throws Exception {
	  JSerialClient serial = this.serialClient;
	  synchronized (serial) {

		  String ask = "";
		  for(int retry =0;retry<3;retry++) {
			  if (!serial.write(mess)) {
				  PssLogger.logError("No se pudo escribir en el puerto");
				  serial.disconnect();
				  serial.connect();
				  continue;
			  }
			  long inicio = System.currentTimeMillis();
			  while (System.currentTimeMillis()-inicio<MESSAGE_TIMEOUT) {
				  ask = serial.recv();
				  if (ask.length()>0) break;
				  Thread.sleep(10);
			  }
			  if (ask.length()==0) break;
			  if (ask.charAt(0) == EOT) break;
			  if (ask.charAt(0) == ACK) return ACK;
		  }
	  }
	  return NAK;
  }
  // cuando comienza a escuchar tarjetas no puede hacer otra cos,
  // por eso solo se permite la captura con capture
  // y no se puede disable y enable buffer, no hacen nada, pues no estoy seguro que siempre se apague
  public void disableMcrBuffer() throws Exception {
  }

  public void doDisableMcrBuffer() throws Exception {
	  byte[] mess = new byte[6];
	  int i=0;
	  noDisplay=false;
	  mess[i++]= STX;
	  mess[i++]= 'Q';
	  mess[i++]= '4';
	  mess[i++]= '1';
	  mess[i++]= ETX;
	  mess[i++]= LRC(mess,i);
	  send(mess);
  }

  public void enableMcrBuffer() throws Exception {
	  byte[] mess = new byte[6];
	  int i=0;
	  mess[i++]= STX;
	  mess[i++]= 'Q';
	  mess[i++]= '4';
	  mess[i++]= '0';
	  mess[i++]= ETX;
	  mess[i++]= LRC(mess,i);
	  send(mess);
	  noDisplay=true;
  }
//----------------------------------------------------------------------------------------------------------------------

  public Answer readCard() throws Exception {

		  byte[] ack = new byte[1];
		  String resp;
		  JSerialClient serial = this.serialClient;
		  synchronized (serial) {
			  resp = serial.recv();
			  long inicio = System.currentTimeMillis();
			  while (true) {
				  if (System.currentTimeMillis()-inicio>5000)
					  return new AwrTimeout(0);
				  Thread.sleep(50);
				  if (resp.length()<=0) {
					  resp += serial.recv();
					  continue;
				  }
				  if (resp.charAt(0)==EOT)
						break;
					if (resp.charAt(0)==ABORT)
					  return new AwrClientCancel();
					if (resp.charAt(0)!=STX)
					  break;
				  if (resp.length()>1)  {
						if (resp.charAt(resp.length()-2)==ETX) {
							break;
					  } else {
						  ack[0] = NAK;
						  serial.write(ack);
						  continue;
					  } 	
				  }
			  }
			  
			  if (resp.length()<=1) return new AwrError("Error leyendo tranks");
			  ack[0] = ACK;
			  serial.write(ack);
		  }
					  // leyo la tarjeta
		  int j;
		  String track1="";
		  String track2="";
		  String track3="";
		  for (j=4;j<resp.length();j++) {
			  if (resp.charAt(j)==0x1C) break; // fin track 2
			  track2+=resp.charAt(j);
		  }
		  j++;
		  for (;j<resp.length();j++) {
			  if (resp.charAt(j)==0x1C) break; // fin track 1
			  track1+=resp.charAt(j);
		  }
		  j++;
		  for (;j<resp.length();j++) {
			  if (resp.charAt(j)==0x1C) break; // fin track 3
			  track3+=resp.charAt(j);
		  }

		  if (track1.equals("*")) 
		  	return new AwrError("Es tarjeta chip");
				  						 
		  PssLogger.logDebug("MAG: "+track1);
		  PssLogger.logDebug("MAG: "+track2);
			this.enableMcrBuffer();
		  return new AwrMagneticCard(track1, track2, track3);
  }
//----------------------------------------------------------------------------------------------------------------------
  public Answer dataEncrypt(JDes des) throws Exception {
	  PssLogger.logDebug("SC5000 - dataEncrypt - dataBlock      : " + des.getDataBlock());
	  PssLogger.logDebug("SC5000 - dataEncrypt - workingkey     : " + des.getWorkingKey());
	  PssLogger.logDebug("SC5000 - dataEncrypt - masterkeyindex : " + des.getMasterKeyIndex());
	  if (des.getMasterKeyIndex() > 9)
		  return new AwrError("Master Key Invalida");
		
	  return this.calcDES(des);
  }

//----------------------------------------------------------------------------------------------------------------------
  private Answer calcDES(JDes des)  throws Exception {
	  byte[] mess = new byte[13+16+des.getDataBlock().length()];
	  int j;
	  int i=0;
	  String wkey = JTools.LPad(des.getWorkingKey(),16,"0");

	  mess[i++]= STX;
	  mess[i++]= 'Z';
	  mess[i++]= '6';
	  mess[i++]= '6';
	  mess[i++]= '4';
	  mess[i++]= '0';
	  mess[i++]= '0';
	  mess[i++]= (byte)(des.getMasterKeyIndex()+'0');
	  mess[i++]= 0x1C;
 	      for(j=0;j<16 && j<wkey.length();j++)
	   	mess[i++] = wkey.getBytes()[j];
	  mess[i++]= 0x1C;
	  mess[i++]= 0x1C;
 	      for(j=0;j<28 && j<des.getDataBlock().length();j++)
		   	mess[i++] = des.getDataBlock().getBytes()[j];
	  mess[i++]= ETX;
	  mess[i++]= LRC(mess,i);
	  Answer event = sendReceive(mess,MESSAGE_TIMEOUT,ETX);
	  if (!event.isData()) return event;
	  String resp = event.toString();
	  if (resp.length()<=0) return new AwrError("Error calculando DES");
		if (resp.charAt(4)=='?') return new AwrTimeout(0);
		if (resp.charAt(4)!='0') {
		  switch (resp.charAt(4)) {
		  case '1':  return new AwrError("ready for next z66 packet and no MAC follow"); 
		  case '2':  return new AwrError("out-of-order error and no MAC follow"); 
		  case '3':  return new AwrError("pointer] error and no MAC follow");
		  case '4':  return new AwrError("[secondary key] error and no MAC follow");
		  case '5':  return new AwrError("package frame error and no MAC follow");
		  case '6':  return new AwrError("[flag]error");
		  case '7':  return new AwrError("[message]error");
		  case '8':  return new AwrError("[working key]error");
		  }
		}
		return new AwrData(resp.substring(5,16+5));
  }

/*//----------------------------------------------------------------------------------------------------------------------
  public String getKeysPressed(int minLen, int maxLen, int inputType, String upperLine) throws Exception {
	  doSecondaryDisplay(upperLine);
	  return getInput(inputType,maxLen, minLen, 0, 0, 0, "",250, 250);
  }
*/
  public Answer readInputByKey(int timeOut) throws Exception {
  	return this.readKey(timeOut);
  }
  
  public Answer clearClientDisplay() throws Exception {
	  this.display(this.getBlankLine(), this.getBlankLine());
	  return new AwrOk();
  }
      
  @Override
	public void startPolling() throws Exception {
  	this.enableMcrBuffer();
  }
  @Override
	public void stopPolling() throws Exception {
    this.disableMcrBuffer();
  }
  
  
  
}
