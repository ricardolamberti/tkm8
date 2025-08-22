package pss.tourism.interfaceGDS.galileo;

import java.io.BufferedReader;
import java.io.File;

import pss.tourism.interfaceGDS.BaseTransaction;
import pss.tourism.interfaceGDS.FileProcessor;
import pss.tourism.interfaceGDS.galileo.record.GalileoRecord;
import pss.tourism.interfaceGDS.galileo.record.RecordA00;
import pss.tourism.interfaceGDS.galileo.record.RecordA01;
import pss.tourism.interfaceGDS.galileo.record.RecordA02;
import pss.tourism.interfaceGDS.galileo.record.RecordA03;
import pss.tourism.interfaceGDS.galileo.record.RecordA04;
import pss.tourism.interfaceGDS.galileo.record.RecordA05;
import pss.tourism.interfaceGDS.galileo.record.RecordA06;
import pss.tourism.interfaceGDS.galileo.record.RecordA07;
import pss.tourism.interfaceGDS.galileo.record.RecordA08;
import pss.tourism.interfaceGDS.galileo.record.RecordA09;
import pss.tourism.interfaceGDS.galileo.record.RecordA10;
import pss.tourism.interfaceGDS.galileo.record.RecordA11;
import pss.tourism.interfaceGDS.galileo.record.RecordA12;
import pss.tourism.interfaceGDS.galileo.record.RecordA13;
import pss.tourism.interfaceGDS.galileo.record.RecordA14;
import pss.tourism.interfaceGDS.galileo.record.RecordA15;
import pss.tourism.interfaceGDS.galileo.record.RecordA16;
import pss.tourism.interfaceGDS.galileo.record.RecordA17;
import pss.tourism.interfaceGDS.galileo.record.RecordA18;
import pss.tourism.interfaceGDS.galileo.record.RecordA19;
import pss.tourism.interfaceGDS.galileo.record.RecordA20;
import pss.tourism.interfaceGDS.galileo.record.RecordA21;
import pss.tourism.interfaceGDS.galileo.record.RecordA22;
import pss.tourism.interfaceGDS.galileo.record.RecordA23;
import pss.tourism.interfaceGDS.galileo.record.RecordA24;
import pss.tourism.interfaceGDS.galileo.record.RecordA25;
import pss.tourism.interfaceGDS.galileo.record.RecordA26;
import pss.tourism.interfaceGDS.galileo.record.RecordA27;
import pss.tourism.interfaceGDS.galileo.record.RecordA28;
import pss.tourism.interfaceGDS.galileo.record.RecordA29;
import pss.tourism.interfaceGDS.galileo.record.RecordA30;
import pss.tourism.interfaceGDS.galileo.record.RecordHeader;
import pss.tourism.interfaceGDS.galileo.transaction.TicketTransaction;
import pss.tourism.interfaceGDS.galileo.transaction.VoidTicketTransaction;

public class GalileoFileProcessor extends FileProcessor {

	protected String getGds() {
		return "GALILEO";
	}

	int hcount = 0;

	protected void internalProcessFile(File file, BufferedReader input, boolean reprocess) throws Exception {
		String line = "";

		// skip header
		line = processBaseRecord(input, line, RecordHeader.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA00.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA01.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA02.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA03.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA04.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA05.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA06.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA07.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA08.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA09.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA10.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA11.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA12.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA13.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA14.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA15.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA16.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA17.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA18.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA19.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA20.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA21.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA22.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA23.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA24.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA25.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA26.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA27.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA28.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA29.class.getName());
		line = processMultiBaseRecordEmptyLine(input, line, RecordA30.class.getName());


		processFileInformation(reprocess);
		
		RecordA29.resetEMDMap();

	}

	protected boolean processVoidTicketTransaction() throws Exception {
		RecordHeader r1 = ((RecordHeader) mRecords.getElement(GalileoRecord.HEADER));
		pnrLocator = r1.getFieldValue(RecordHeader.T50RCL);
		String tipo = r1.getFieldValue(RecordHeader.T50IN12);
		if (!tipo.equals("V"))
			return false;
		VoidTicketTransaction v = new VoidTicketTransaction();
		v.setCompany(company);
		v.setGds(this.getGds());
		v.setPnrFile(currfile);
		v.setPnrLocator(pnrLocator);
		v.save(null,pnrLocator, mRecords);//falta implmentar,de donde sale la fecha de anulacion
		v.postProcessTransaction(r1.getFieldValueAsDate(RecordHeader.T50DFT));
		if (company == null)
			company = v.getCompany();

		return true;
	}

	protected BaseTransaction createTransaction() throws Exception {
		return new TicketTransaction();
	}

}
