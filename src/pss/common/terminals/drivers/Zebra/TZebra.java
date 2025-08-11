package pss.common.terminals.drivers.Zebra;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.PrintServiceAttribute;
import javax.print.attribute.standard.PrinterName;

import pss.common.terminals.core.JTerminal;
import pss.common.terminals.interfaces.JPrinterInterface;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrOk;
import pss.core.tools.PssLogger;

public class TZebra extends JTerminal implements JPrinterInterface {
	 PrintService psZebra = null;
//   String sPrinterName = null;
   String lines;
   private static final String LINE_FEED = "\n";
		
	@Override
	public void loadDrivers() throws Exception {
		this.addDriver(JTerminal.D_PRINTER);
	}
	
	@Override
	public Answer open() throws Exception {
			String connection = this.getConnectinString().toLowerCase();
			if (connection.startsWith("name:")) connection=connection.substring(5);
			
	    PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
      for (int i = 0; i < services.length; i++) {
         PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);
         String sPrinterName = ((PrinterName) attr).getValue();
         PssLogger.logDebug("printername: "+sPrinterName);
         if (sPrinterName.toLowerCase().equals(connection)) {
             psZebra = services[i];
             break;
         }
     }
			if (psZebra == null) {
		    throw new Exception ("Zebra printer is not found.["+connection+"]");
		}
		return new AwrOk();
	}
	
	@Override
	public Answer close() throws Exception {
		return new AwrOk();
	}
	
	public Answer printLine(String line) throws Exception {
//		String s =  "R0,0\n" +   // Set Reference Point                                                             
//    "N\n" +         // Clear Image Buffer                                                             
//    "ZB\n" + // Print direction (from Bottom of buffer)
//    "Q202,16\n" +  // Set label Length and gap
//    "A80,0,0,2,2,3,N,\"   Poder Judicial\"\n" +
//    "A80,50,0,3,1,3,N,\"  Registro General Inmobiliario\"\n" +
//    "A80,110,0,3,2,2,N,\"     SAN JUAN\"\n" +
//    "A80,150,0,4,1,1,N,\"  Mesa de Entradas y Salidas\"\n" +
//    "A80,180,0,3,1,1,N,\"  "+ dateString+" "+JDateTools.CurrentTime(JDateTools.DEFAULT_HOUR_FORMAT)+" 2011-030063\"\n" +
//    "B100,220,0,1A,2,7,50,N,\"03006320/09/201100\"\n" +                            
//    "A80,280,0,2,2,1,N,\"*030063 20/09/2009 00*\"\n" +
//    "P1\n";   // Print content of buffer, 1 label

		if (lines==null) lines="";
		lines+=line+(line.endsWith(LINE_FEED)?"":LINE_FEED);
  	return new AwrOk();
	}
	public Answer closeShift() throws Exception {
		throw new Exception("Funcion no soportada");
	}
	public Answer closeDay() throws Exception {
		throw new Exception("Funcion no soportada");
	}
	
	public Answer cancelDoc() throws Exception {
		lines = null;
		return new AwrOk();
	}
	public Answer openDoc() throws Exception {
		return new AwrOk();
  }
	public Answer closeDoc() throws Exception {
		lines=null;
  	return new AwrOk();
	}
	public Answer flush() throws Exception {
    if (lines==null) throw new Exception("Documento Cancelado o nunca iniciado");
		DocPrintJob job;
		job = psZebra.createPrintJob();
		PssLogger.logDebug("TICKET: ["+lines+"]");
// lines =  "R0,0\n" +   // Set Reference Point                                                             
//  "N\n" +         // Clear Image Buffer                                                             
//  "ZB\n" + // Print direction (from Bottom of buffer)
//  "Q190,16\n" +  // Set label Length and gap
//  "A80,0,0,2,2,3,N,\"   Poder Judicial\"\n" +
//  "A80,50,0,3,1,3,N,\"  Registro General Inmobiliario\"\n" +
//  "A80,110,0,3,2,2,N,\"     SAN JUAN\"\n" +
//  "A80,150,0,4,1,1,N,\"  Mesa de Entradas y Salidas\"\n" +
//  "A80,180,0,3,1,1,N,\"  20/09/2011 00:00:00 2011-030063\"\n" +
//  "B100,220,0,1A,2,7,50,N,\"03006320/09/201100\"\n" +                            
//  "A80,280,0,2,2,1,N,\"*030063 20/09/2009 00*\"\n" +
//  "P2\n";   // Print content of buffer, 1 label
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		Doc doc = new SimpleDoc(lines.getBytes(), flavor, null);
		job.print(doc, null);
		lines=null;
		return new AwrOk();
	}
	public Answer skeepLines(int lines) throws Exception {
  	return new AwrOk();
	}

	
}
