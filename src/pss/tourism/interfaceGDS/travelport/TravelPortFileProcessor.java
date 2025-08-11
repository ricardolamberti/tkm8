package pss.tourism.interfaceGDS.travelport;

import java.io.BufferedReader;
import java.io.File;
import java.util.Date;
import java.util.StringTokenizer;

import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.tourism.interfaceGDS.BaseTransaction;
import pss.tourism.interfaceGDS.FileProcessor;
import pss.tourism.interfaceGDS.travelport.record.Record01;
import pss.tourism.interfaceGDS.travelport.record.Record02;
import pss.tourism.interfaceGDS.travelport.record.Record03;
import pss.tourism.interfaceGDS.travelport.record.Record04;
import pss.tourism.interfaceGDS.travelport.record.Record06;
import pss.tourism.interfaceGDS.travelport.record.Record1;
import pss.tourism.interfaceGDS.travelport.record.Record2;
import pss.tourism.interfaceGDS.travelport.record.Record3;
import pss.tourism.interfaceGDS.travelport.record.Record4;
import pss.tourism.interfaceGDS.travelport.record.Record5;
import pss.tourism.interfaceGDS.travelport.record.Record6;
import pss.tourism.interfaceGDS.travelport.record.Record7;
import pss.tourism.interfaceGDS.travelport.record.Record8;
import pss.tourism.interfaceGDS.travelport.record.Record9;
import pss.tourism.interfaceGDS.travelport.record.RecordA;
import pss.tourism.interfaceGDS.travelport.record.RecordB;
import pss.tourism.interfaceGDS.travelport.record.RecordC;
import pss.tourism.interfaceGDS.travelport.record.RecordD;
import pss.tourism.interfaceGDS.travelport.record.RecordE;
import pss.tourism.interfaceGDS.travelport.record.RecordF;
import pss.tourism.interfaceGDS.travelport.record.RecordG;
import pss.tourism.interfaceGDS.travelport.record.RecordH;
import pss.tourism.interfaceGDS.travelport.record.RecordM;
import pss.tourism.interfaceGDS.travelport.record.RecordMINUSE;
import pss.tourism.interfaceGDS.travelport.record.RecordN;
import pss.tourism.interfaceGDS.travelport.record.RecordO;
import pss.tourism.interfaceGDS.travelport.record.RecordQ;
import pss.tourism.interfaceGDS.travelport.record.RecordR;
import pss.tourism.interfaceGDS.travelport.record.RecordS;
import pss.tourism.interfaceGDS.travelport.record.RecordT;
import pss.tourism.interfaceGDS.travelport.record.RecordU;
import pss.tourism.interfaceGDS.travelport.record.RecordV;
import pss.tourism.interfaceGDS.travelport.record.RecordW;
import pss.tourism.interfaceGDS.travelport.record.RecordX;
import pss.tourism.interfaceGDS.travelport.record.RecordZ;
import pss.tourism.interfaceGDS.travelport.record.TravelPortRecord;
import pss.tourism.interfaceGDS.travelport.transaction.TicketTransaction;
import pss.tourism.interfaceGDS.travelport.transaction.VoidTicketTransaction;
import pss.tourism.pnr.BizPNRTicket;

public class TravelPortFileProcessor extends FileProcessor {

	protected String getGds() { return "TRAVELPORT"; }

	int hcount = 0;
	
	static int year = -1;
	
	public static int getYear() {
		return year;
	}

	
	protected void internalProcessFile(File file, BufferedReader input, boolean reprocess)
			throws Exception {
		String line = "";

		year = -1;
		
		String name = file.getName();
		
		StringTokenizer tok = new StringTokenizer(name, ".");
		tok.nextToken();
		tok.nextToken();
		String sy = tok.nextToken();
		if (JTools.isNumber(sy)) {
			try {
				year = Integer.parseInt(sy);
			} catch (Exception ee) {
			}
		}
		
		
		// skip header
		input.mark(2000);
		while ((line = input.readLine()) != null) {
			PssLogger.logDebug(line);
			if (line.startsWith("A\\")) {
				input.reset();
				break;
			}
				
			if (line.equals("**"))
				break;
		}
		ronda=0;
		String lineold = "";
		input.mark(2000);
		while ( (line = input.readLine()) != null )  {
			if (line.trim().equals(""))
				continue;
			if (lineold.equals(line))  {
//				line = input.readLine();
//				if ( line == null)
				  break;
			}
			lineold = line;
			input.reset();
			line = processBaseRecord(input, line, Record1.class.getName());
			line = processBaseRecord(input, line, Record2.class.getName());
			line = processBaseRecord(input, line, Record3.class.getName());
			line = processBaseRecord(input, line, Record4.class.getName());
			line = processBaseRecord(input, line, Record5.class.getName());
			line = processBaseRecord(input, line, Record6.class.getName());
			line = processBaseRecord(input, line, Record7.class.getName());
			line = processBaseRecord(input, line, Record8.class.getName());
			line = processBaseRecord(input, line, Record9.class.getName());
			line = processBaseRecord(input, line, Record9.class.getName());
			//line = processBaseRecord(input, line, Record01.class.getName());
			line = processBaseRecord(input, line, Record01.class.getName());
//			if (segs>0)
//				segments=segs;
			line = processBaseRecord(input, line, Record02.class.getName());
			line = processBaseRecord(input, line, Record03.class.getName());
			line = processBaseRecord(input, line, Record04.class.getName());
			line = processBaseRecord(input, line, Record06.class.getName());
			line = processBaseRecord(input, line, RecordA.class.getName());
			line = processBaseRecord(input, line, RecordB.class.getName());
			line = processBaseRecord(input, line, RecordC.class.getName());
			line = processBaseRecord(input, line, RecordD.class.getName());
			line = processBaseRecord(input, line, RecordE.class.getName());
			line = processBaseRecord(input, line, RecordF.class.getName());

			line = processBaseRecord(input, line, RecordG.class.getName());
			line = processBaseRecord(input, line, RecordH.class.getName());
			line = processBaseRecord(input, line, RecordMINUSE.class.getName());
			line = processBaseRecord(input, line, RecordM.class.getName());
			line = processBaseRecord(input, line, RecordN.class.getName());
			line = processBaseRecord(input, line, RecordO.class.getName());
			line = processBaseRecord(input, line, RecordQ.class.getName());
			line = processBaseRecord(input, line, RecordR.class.getName());
			line = processBaseRecord(input, line, RecordS.class.getName());
			line = processBaseRecord(input, line, RecordT.class.getName());
			line = processBaseRecord(input, line, RecordU.class.getName());
			line = processBaseRecord(input, line, RecordV.class.getName());
			line = processBaseRecord(input, line, RecordX.class.getName());
			line = processBaseRecord(input, line, RecordW.class.getName());
			line = processBaseRecord(input, line, RecordZ.class.getName());
			ronda++;
		} 
		processFileInformation(reprocess);

	}

	protected boolean processVoidTicketTransaction() throws Exception {
		RecordA vr = ((RecordA) mRecords.getElement(TravelPortRecord.ARECORD));
		if (vr == null)  
			return false;
		if(vr.isVoid()==false) return false;
		Record1 r1 = ((Record1) mRecords.getElement(TravelPortRecord.ONE));
        if (r1 == null) {
        	BizPNRTicket pt = new BizPNRTicket();
    		pt.dontThrowException(true);
    		if (!pt.ReadByBoleto(company, vr.getTicketNumber(1)) )
    			return true;
    		this.pnrLocator = pt.getCodigopnr();
    	} else {
		this.pnrLocator = r1.getPNRLocator();
    	}
		VoidTicketTransaction vt = new VoidTicketTransaction();
		vt.setCompany(company);
		vt.setGds(getGds());
		vt.setPnrFile(currfile);
		vt.setPnrLocator(this.pnrLocator);
		vt.save(null,pnrLocator, vr );//RJL, falta implemntar de donde sacar la fecha de la anulacion
		vt.postProcessTransaction(new Date());
		if (company == null)
			company = vt.getCompany();
//		setIATA(vt.getIATA());
		return true;
	}
	
	protected BaseTransaction createTransaction() throws Exception {
		return new TicketTransaction();
	}


}
