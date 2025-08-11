package pss.common.terminals.drivers.PDFPrinter;


import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.util.Date;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttribute;
import javax.print.attribute.standard.PrinterName;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import pss.common.terminals.drivers.PDFFile.TPDFPrinter;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrOk;
import pss.core.tools.PssLogger;

public class TPDFRealPrinter extends TPDFPrinter {

	boolean supportPDF = false;
 PrintService psPrinter = null;

	public TPDFRealPrinter() throws Exception {
	}
  public String getFilename() throws Exception {
  	return (new Date().getTime())+".pdf";
  }


	@Override
	public Answer open() throws Exception {
		String connection = this.getConnectinString().toLowerCase();
		if (connection.startsWith("name:"))	connection = connection.substring(5);
		// busco por si es una impresora que soporta imprimir pdf directamente
		PrintService[] services = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.PDF, null);
		for (int i = 0; i < services.length; i++) {
			PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);
			String sPrinterName = ((PrinterName) attr).getValue();
			PssLogger.logDebug("printername: " + sPrinterName);
			if (sPrinterName.toLowerCase().equals(connection)) {
				psPrinter = services[i];
				supportPDF=true;
				break;
			}
		}

		if (psPrinter == null) {
			// no lo soporta, busco una cualquiera y uso el convertidor de pdfbox a postscript
			services = PrintServiceLookup.lookupPrintServices(null, null);
			for (int i = 0; i < services.length; i++) {
				PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);
				String sPrinterName = ((PrinterName) attr).getValue();
				PssLogger.logDebug("printername: " + sPrinterName);
				if (sPrinterName.toLowerCase().equals(connection)) {
					psPrinter = services[i];
					supportPDF=false;
					break;
				}
			}
		}
		if (psPrinter == null) 
	    throw new Exception ("Printer is not found.["+connection+"]");

		return new AwrOk();
	}
	public Answer closeDoc() throws Exception {
		Answer ans = super.closeDoc();
	
		FileInputStream fis = new FileInputStream(sFileName);
		if (ans.is(AwrOk.class)) {
			if (supportPDF) {
				Doc pdfDoc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.PDF, null);
				DocPrintJob printJob = psPrinter.createPrintJob();
				printJob.print(pdfDoc, new HashPrintRequestAttributeSet());
				fis.close();
			} else {
				PrinterJob job = PrinterJob.getPrinterJob();
				job.setPrintService(psPrinter);
				PDDocument doc = PDDocument.load(fis);
				job.setPageable(new PDFPageable(doc));
				job.print();
			}
		}
		return ans;
	}
}
