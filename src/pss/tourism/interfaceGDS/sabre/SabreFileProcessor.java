package pss.tourism.interfaceGDS.sabre;

import java.io.BufferedReader;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pss.core.tools.JDateTools;
import pss.core.tools.PssLogger;
import pss.tourism.interfaceGDS.BaseTransaction;
import pss.tourism.interfaceGDS.FileProcessor;
import pss.tourism.interfaceGDS.amadeus.record.MFRecord;
import pss.tourism.interfaceGDS.sabre.record.AARecord;
import pss.tourism.interfaceGDS.sabre.record.M0Record;
import pss.tourism.interfaceGDS.sabre.record.M0VoidEMDRecord;
import pss.tourism.interfaceGDS.sabre.record.M0VoidRecord;
import pss.tourism.interfaceGDS.sabre.record.M1Record;
import pss.tourism.interfaceGDS.sabre.record.M2Record;
import pss.tourism.interfaceGDS.sabre.record.M3Record;
import pss.tourism.interfaceGDS.sabre.record.M4Record;
import pss.tourism.interfaceGDS.sabre.record.M5Record;
import pss.tourism.interfaceGDS.sabre.record.M6Record;
import pss.tourism.interfaceGDS.sabre.record.M7Record;
import pss.tourism.interfaceGDS.sabre.record.M8Record;
import pss.tourism.interfaceGDS.sabre.record.M9Record;
import pss.tourism.interfaceGDS.sabre.record.MARecord;
import pss.tourism.interfaceGDS.sabre.record.MBRecord;
import pss.tourism.interfaceGDS.sabre.record.MERecord;
import pss.tourism.interfaceGDS.sabre.record.MGRecord;
import pss.tourism.interfaceGDS.sabre.record.MXRecord;
import pss.tourism.interfaceGDS.sabre.record.SabreRecord;
import pss.tourism.interfaceGDS.sabre.transaction.TicketTransaction;
import pss.tourism.interfaceGDS.sabre.transaction.VoidTicketTransaction;

public class SabreFileProcessor extends FileProcessor {

	protected String getGds() {
		return "SABRE";
	}

	protected void internalProcessFile(File file, BufferedReader input, boolean reprocess)
			throws Exception {

		String line = input.readLine();

		// AA Record header parsing
		line = processAARecord(line);

		// parse header of void record
		line = processM0VoidRecord(line);

		if (line != null) {
			// parse header of record
			processM0Record(input, line);

			// processing M1 records
			line = processMultiBaseRecord(input, line, M1Record.class.getName(), getNumberOfM1());

			// processing M2 records
			line = processMultiBaseRecord(input, line, M2Record.class.getName(), getNumberOfM2());

			// processing M3 records
			line = processMultiBaseRecord(input, line, M3Record.class.getName(), getNumberOfM3());

			if (line!=null && line.startsWith("M3"))input.readLine();//a veces hay una linea extra de m3
			// processing M4 records
			line = processMultiBaseRecord(input, line, M4Record.class.getName(), getNumberOfM4());

			M5Record.initM5();
			// processing M5 records
			line = processMultiBaseRecord(input, line, M5Record.class.getName(), getNumberOfM5());

			// processing M6 records
			line = processMultiBaseRecord(input, line, M6Record.class.getName(), getNumberOfM6());

			// processing M7 records
			line = processBaseRecord(input, line, M7Record.class.getName());

			// processing M8 records
			M8Record.initM8();
			line = processBaseRecord(input, line, M8Record.class.getName());

			// processing M9 records
			line = processBaseRecord(input, line, M9Record.class.getName());

			// processing MA records
			line = processBaseRecord(input, line, MARecord.class.getName());
			
			// processing MB records
			line = processBaseRecord(input, line, MBRecord.class.getName());

			// processing ME records
			line = processMultiBaseRecord(input, line, MERecord.class.getName(), getNumberOfM3() + 1);

			// processing MF records
			line = processBaseRecord(input, line, MFRecord.class.getName());
			// processing MG records
			line = processMultiBaseRecord(input, line, MGRecord.class.getName(), getNumberOfM5() + 1);

			if (line!=null && line.startsWith("MXP"))input.readLine();//a veces hay una linea extra de mpx

			// processing MX records
			line = processBaseRecord(input, line, MXRecord.class.getName());

		}

		while ((line = input.readLine()) != null) {
			PssLogger.logDebug(line);
		}

		processFileInformation(reprocess);

	}

	protected boolean processVoidTicketTransaction() throws Exception {
		M0VoidRecord vr = ((M0VoidRecord) mRecords.getElement(SabreRecord.M0VOID));
		if (vr == null)  {
			vr = ((M0VoidEMDRecord) mRecords.getElement(SabreRecord.M0VOIDEMD));
		}
		if (vr == null)
			return false;
		AARecord aa = ((AARecord) mRecords.getElement(SabreRecord.AA));
		this.pnrLocator = vr.getPNRLocator();
		VoidTicketTransaction vt = new VoidTicketTransaction();
		vt.setCompany(company);
		vt.setGds(currfile.toLowerCase().indexOf("copa.")!=-1?"NDC":getGds());
		vt.setPnrFile(currfile);
		vt.setPnrLocator(this.pnrLocator);
		vt.save(aa,vr);
		vt.postProcessTransaction(aa.getCreationDate(extractYear(currfile)));
		if (company == null)
			company = vt.getCompany();
//		setIATA(vt.getIATA());
		return true;
	}
  public static long extractYear(String input) throws Exception {
    // Patrón para capturar un año de 4 dígitos
    Pattern pattern = Pattern.compile("\\b(\\d{4})\\b");
    Matcher matcher = pattern.matcher(input);
    
    // Buscar el primer año válido
    if (matcher.find()) {
        return Long.parseLong( matcher.group(1));
    }
    
    // Devolver null si no se encuentra un año
    return JDateTools.getAnioActual();
}
	/**
	 * @param input
	 * @param line
	 * @throws Exception
	 */
	private void processM0Record(BufferedReader input, String line)
			throws Exception {
		M0Record m0 = new M0Record();
		m0.setInput(input);
		m0.process(mRecords, line);
		this.branch = m0.getBranch();
		this.pnrLocator = m0.getPNRLocator();
	}

	/**
	 * @return
	 */
	private int getNumberOfM1() {
		return ((M0Record) mRecords.getElement(SabreRecord.M0)).getNumberOfM1();
	}

	private int getNumberOfM3() {
		return ((M0Record) mRecords.getElement(SabreRecord.M0)).getNumberOfM3();
	}

	private int getNumberOfM2() {
		return ((M0Record) mRecords.getElement(SabreRecord.M0)).getNumberOfM2();
	}

	private int getNumberOfM4() {
		return ((M0Record) mRecords.getElement(SabreRecord.M0)).getNumberOfM4();
	}

	private int getNumberOfM5() {
		return ((M0Record) mRecords.getElement(SabreRecord.M0)).getNumberOfM5();
	}

	private int getNumberOfM6() {
		return ((M0Record) mRecords.getElement(SabreRecord.M0)).getNumberOfM6();
	}

	/**
	 * @param line
	 * @return
	 * @throws Exception
	 */
	private String processM0VoidRecord(String line) throws Exception {
		M0VoidRecord m0v = new M0VoidRecord();
		String oldLine = line;
		line = m0v.process(mRecords, line);
		if (line!=null) {
			M0VoidEMDRecord m0emd= new M0VoidEMDRecord();
			line = m0emd.process(mRecords, oldLine);
		}
		return line;
	}

	protected BaseTransaction createTransaction() throws Exception {
		return new TicketTransaction();
	}

	/**
	 * @param line
	 * @return
	 * @throws Exception
	 */
	private String processAARecord(String line) throws Exception {
		AARecord aa = new AARecord();
		line = aa.process(mRecords, line);
		return line;
	}

}
