package pss.tourism.interfaceGDS.amadeus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import pss.JPath;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.tourism.interfaceGDS.BaseTransaction;
import pss.tourism.interfaceGDS.FileProcessor;
import pss.tourism.interfaceGDS.amadeus.record.AIRRecord;
import pss.tourism.interfaceGDS.amadeus.record.AMDRecord;
import pss.tourism.interfaceGDS.amadeus.record.ARecord;
import pss.tourism.interfaceGDS.amadeus.record.ATCRecord;
import pss.tourism.interfaceGDS.amadeus.record.ATFRecord;
import pss.tourism.interfaceGDS.amadeus.record.AmadeusRecord;
import pss.tourism.interfaceGDS.amadeus.record.BRecord;
import pss.tourism.interfaceGDS.amadeus.record.CRecord;
import pss.tourism.interfaceGDS.amadeus.record.DRecord;
import pss.tourism.interfaceGDS.amadeus.record.EMDRecord;
import pss.tourism.interfaceGDS.amadeus.record.EMQRecord;
import pss.tourism.interfaceGDS.amadeus.record.GRecord;
import pss.tourism.interfaceGDS.amadeus.record.HRecord;
import pss.tourism.interfaceGDS.amadeus.record.IRecord;
import pss.tourism.interfaceGDS.amadeus.record.KFTFRecord;
import pss.tourism.interfaceGDS.amadeus.record.KFTRecord;
import pss.tourism.interfaceGDS.amadeus.record.KNRecord;
import pss.tourism.interfaceGDS.amadeus.record.KNTBRecord;
import pss.tourism.interfaceGDS.amadeus.record.KNTFRecord;
import pss.tourism.interfaceGDS.amadeus.record.KNTIRecord;
import pss.tourism.interfaceGDS.amadeus.record.KNTRRecord;
import pss.tourism.interfaceGDS.amadeus.record.KNTWRecord;
import pss.tourism.interfaceGDS.amadeus.record.KNTYRecord;
import pss.tourism.interfaceGDS.amadeus.record.KRFRRecord;
import pss.tourism.interfaceGDS.amadeus.record.KRFRecord;
import pss.tourism.interfaceGDS.amadeus.record.KRFWRecord;
import pss.tourism.interfaceGDS.amadeus.record.KRFYRecord;
import pss.tourism.interfaceGDS.amadeus.record.KRecord;
import pss.tourism.interfaceGDS.amadeus.record.KSRecord;
import pss.tourism.interfaceGDS.amadeus.record.KSTBRecord;
import pss.tourism.interfaceGDS.amadeus.record.KSTFRecord;
import pss.tourism.interfaceGDS.amadeus.record.KSTIRecord;
import pss.tourism.interfaceGDS.amadeus.record.KSTRRecord;
import pss.tourism.interfaceGDS.amadeus.record.KSTWRecord;
import pss.tourism.interfaceGDS.amadeus.record.KSTYRecord;
import pss.tourism.interfaceGDS.amadeus.record.LRecord;
import pss.tourism.interfaceGDS.amadeus.record.MCRecord;
import pss.tourism.interfaceGDS.amadeus.record.MRecord;
import pss.tourism.interfaceGDS.amadeus.record.MUCRecord;
import pss.tourism.interfaceGDS.amadeus.record.NRecord;
import pss.tourism.interfaceGDS.amadeus.record.ORecord;
import pss.tourism.interfaceGDS.amadeus.record.QRecord;
import pss.tourism.interfaceGDS.amadeus.record.RFDARecord;
import pss.tourism.interfaceGDS.amadeus.record.RFDFRecord;
import pss.tourism.interfaceGDS.amadeus.record.RFDLRecord;
import pss.tourism.interfaceGDS.amadeus.record.RFDMRecord;
import pss.tourism.interfaceGDS.amadeus.record.RFDRecord;
import pss.tourism.interfaceGDS.amadeus.record.SIAARecord;
import pss.tourism.interfaceGDS.amadeus.record.SIABRecord;
import pss.tourism.interfaceGDS.amadeus.record.SIAIRecord;
import pss.tourism.interfaceGDS.amadeus.record.SIANRecord;
import pss.tourism.interfaceGDS.amadeus.record.SPTRecord;
import pss.tourism.interfaceGDS.amadeus.record.TAXRecord;
import pss.tourism.interfaceGDS.amadeus.record.TRecord;
import pss.tourism.interfaceGDS.amadeus.record.URecord;
import pss.tourism.interfaceGDS.amadeus.transaction.TicketTransaction;
import pss.tourism.interfaceGDS.amadeus.transaction.VoidTicketTransaction;

public class AmadeusFileProcessor extends FileProcessor {

	protected String getGds() {
		return "AMADEUS";
	}

	int hcount = 0;

	protected void preProcessFile(File file) throws Exception {
    StringBuffer fileData = new StringBuffer();
    BufferedReader reader = new BufferedReader(new FileReader(file));
    char[] buf = new char[1024];
    int numRead=0;
    while((numRead=reader.read(buf)) != -1){
        fileData.append(buf, 0, numRead);
    }
    reader.close();
    if (fileData.toString().startsWith("AIR")&&(fileData.toString().indexOf("#")>50 || fileData.toString().indexOf("#")==-1 )) return;
    String out;
    
    if (fileData.toString().startsWith("AIR") )
      out = fileData.toString().replaceAll("#", ";");
    else
     out = fileData.toString().substring(3).replaceAll("#", ";");
    
		moveFile(file, JPath.PssPathInputProcessed(), subdir, getGds(),JDateTools.CurrentDate("yyyyMM"));
    JTools.writeStringToFile(out, file.getAbsolutePath());
	}
	

	protected void internalProcessFile(File file, BufferedReader input, boolean reprocess)
			throws Exception {
		String line = "";
		boolean specialVoid=false;

		while (true) {
			mRecords = JCollectionFactory.createMap(10);
			line = processBaseRecord(input, line, AIRRecord.class.getName());
			line = processBaseRecord(input, line, AMDRecord.class.getName());
			if (line.startsWith("RM*")) {
				specialVoid=true;
				line = input.readLine();
					line = processBaseRecord(input, line, AMDRecord.class.getName());
			} else
				line = input.readLine();
			line = processBaseRecord(input, line, MUCRecord.class.getName());
			if (specialVoid)
				line = processBaseRecord(input, line, TRecord.class.getName());
			line = processBaseRecord(input, line, ARecord.class.getName());
			String classname = BRecord.class.getName();
			line = processBaseRecord(input, line, classname);
			line = processBaseRecord(input, line, CRecord.class.getName());
			line = processBaseRecord(input, line, DRecord.class.getName());

			line = processBaseRecord(input, line, GRecord.class.getName());
			segments = processMultiBaseRecord(input, HRecord.class.getName(), AmadeusRecord.H);
			int seg2 = processMultiBaseRecord(input, URecord.class.getName(), "U-");
			if (seg2 > segments)
				segments = seg2;
			processMultiBaseRecord(input, MCRecord.class.getName(), "MC");

			int emds = processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, URecord.class.getName(), "U-");
			emds += processMultiBaseRecord(input, EMDRecord.class.getName(), "EMD");
			processMultiBaseRecord(input, MCRecord.class.getName(), "MC");

			// line = processBaseRecord(input, line, ICWRecord.class.getName());
			// line = processBaseRecord(input, line, EMDRecord.class.getName());
			// line = processBaseRecord(input, line, ICWRecord.class.getName());
			line = processBaseRecord(input, line, EMQRecord.class.getName());
			line = processBaseRecord(input, line, RFDARecord.class.getName());
			line = processBaseRecord(input, line, RFDFRecord.class.getName());
			line = processBaseRecord(input, line, RFDLRecord.class.getName());
			line = processBaseRecord(input, line, RFDMRecord.class.getName());
			line = processBaseRecord(input, line, RFDRecord.class.getName());
			processMultiBaseRecord(input, MCRecord.class.getName(), "MC");

			while (true) {
				line = processBaseRecord(input, line, KRecord.class.getName());
				line = processBaseRecord(input, line, KFTRecord.class.getName());
				line = processBaseRecord(input, line, KFTFRecord.class.getName());
	
				line = processBaseRecord(input, line, KNRecord.class.getName());
	
				line = processBaseRecord(input, line, KNTBRecord.class.getName());
				line = processBaseRecord(input, line, KNTFRecord.class.getName());
				line = processBaseRecord(input, line, KNTIRecord.class.getName());
				line = processBaseRecord(input, line, KNTRRecord.class.getName());
				line = processBaseRecord(input, line, KNTWRecord.class.getName());
				line = processBaseRecord(input, line, KNTYRecord.class.getName());
				
				line = processBaseRecord(input, line, KSRecord.class.getName());
				line = processBaseRecord(input, line, SPTRecord.class.getName());
				line = processBaseRecord(input, line, KSTFRecord.class.getName());
				line = processBaseRecord(input, line, KSTBRecord.class.getName());
				line = processBaseRecord(input, line, KSTIRecord.class.getName());
				line = processBaseRecord(input, line, KSTRRecord.class.getName());
				line = processBaseRecord(input, line, KSTWRecord.class.getName());
				line = processBaseRecord(input, line, KSTYRecord.class.getName());
				line = processBaseRecord(input, line, KRFRecord.class.getName());
				line = processBaseRecord(input, line, KRFRRecord.class.getName());
				line = processBaseRecord(input, line, KRFYRecord.class.getName());
				line = processBaseRecord(input, line, KRFWRecord.class.getName());
				if (line==null || !line.startsWith("K"))
					break;
			}
			line = processBaseRecord(input, line, TAXRecord.class.getName());
			line = processBaseRecord(input, line, ATCRecord.class.getName());
			line = processBaseRecord(input, line, SIAARecord.class.getName());
			line = processBaseRecord(input, line, SIABRecord.class.getName());
			line = processBaseRecord(input, line, SIAIRecord.class.getName());
			line = processBaseRecord(input, line, SIANRecord.class.getName());
			line = processBaseRecord(input, line, ATCRecord.class.getName());
			line = processBaseRecord(input, line, LRecord.class.getName());
			line = processBaseRecord(input, line, MRecord.class.getName());
			line = processBaseRecord(input, line, NRecord.class.getName());
			line = processBaseRecord(input, line, ORecord.class.getName());
			line = processBaseRecord(input, line, QRecord.class.getName());
			line = processBaseRecord(input, line, ATFRecord.class.getName());
			IRecord.resetEMDMap();
			line = processBaseRecord(input, line, IRecord.class.getName());
			boolean needContinue=false;
			if (line==null) {
				needContinue=false;
			}
			if (line!=null && line.equals("END")) {
				needContinue=true;
			}
			if (line!=null && line.equals("ENDX")) {
				needContinue=false;
			}
			while (!needContinue && (line = input.readLine()) != null) {
				if (line.equals("END")) {
					needContinue=true;
					break;
				}
				PssLogger.logDebug(line);
			}

			processFileInformation(reprocess);
			if (!needContinue ) break;
			input.mark(10000);
				line = input.readLine();
				
				if (line==null)
					needContinue=false;
			
			if (!needContinue ) break;
			input.reset();

		}
	}

	protected boolean processVoidTicketTransaction() throws Exception {

		boolean isRefund = false;

		AMDRecord vr = ((AMDRecord) mRecords.getElement(AmadeusRecord.AMD));
		BRecord b = ((BRecord) mRecords.getElement(AmadeusRecord.B));
		if (vr == null)
			return false;
		else {
			
			if (b != null) {
				if (b.isRefund() == false) {
					if (vr.isVoid() == false)
						return false;
				} else
					isRefund = true;
			}
		}

		MUCRecord muc = ((MUCRecord) mRecords.getElement(AmadeusRecord.MUC));
		
		VoidTicketTransaction vt = new VoidTicketTransaction();

		vt.setPnrFile(currfile);
		vt.setCompany(company);
		vt.setGds(getGds());
		if (isRefund) {
			RFDARecord r = (RFDARecord) mRecords.getElement("RFDA");
			if (r == null)
				r = (RFDARecord) mRecords.getElement("RFDF");
			if (r == null)
				r = (RFDARecord) mRecords.getElement("RFDL");
			if (r == null)
				r = (RFDARecord) mRecords.getElement("RFDM");
			if (r == null)
				r = (RFDRecord) mRecords.getElement("RFD");

			vt.setCreationDate(r.getOriginalDate());
		} else {
			vt.setPnrLocator(muc.getPNRLocator());
		}
		if (isRefund==false) {
			IRecord i = (IRecord) mRecords.getElement(AmadeusRecord.I);
			if (i!=null) vt.save(vr.getVoidDate(),i);
			else {
				TRecord t = (TRecord) mRecords.getElement(AmadeusRecord.T);
				vt.saveWithT(vr.getVoidDate(),t);
			}
		}
		vt.postProcessTransaction(null);
		vt.setIATA(muc.getIATA());
		if (company == null)
			company = vt.getCompany();

		this.pnrLocator = vt.getPnrLocator();

		// setIATA(vt.getIATA());
		return true;

	}

	protected BaseTransaction createTransaction() throws Exception {
		MUCRecord muc = ((MUCRecord) mRecords.getElement(AmadeusRecord.MUC));
		this.pnrLocator = muc.getPNRLocator();

		return new TicketTransaction();
	}

}
