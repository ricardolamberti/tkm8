package pss.tourism.interfaceGDS.galileo.transaction;

import java.util.Date;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.tourism.interfaceGDS.BaseTransaction;
import pss.tourism.interfaceGDS.galileo.record.GalileoRecord;
import pss.tourism.interfaceGDS.galileo.record.RecordA02;
import pss.tourism.pnr.BizPNRTicket;

public class VoidTicketTransaction extends BaseTransaction {

	
	private JList<RecordA02> getRecordsA02() throws Exception {
		JList<RecordA02> list = JCollectionFactory.createList();
		int i=0;
		while (true){
			i++;
			RecordA02 a02 = (RecordA02) (mRecords.getElement(GalileoRecord.A02RECORD + i));
			if (a02 == null) break;
			list.addElement(a02);
		}
		
		return list;
	}
	
	public void save(Date anul,String pnrid,JMap<String, Object> mRecords ) throws Exception {

		this.mRecords = mRecords;

		JIterator<RecordA02> iter = this.getRecordsA02().getIterator();
		while (iter.hasMoreElements()) {
			RecordA02 a02 = iter.nextElement();
			
			BizPNRTicket pt = new BizPNRTicket();
			pt.dontThrowException(true);
			if (pt.Read(company, pnrid, a02.getFieldValue(RecordA02.A02TKT))) {
				pt.setVoid(true);
				pt.setVoidDate(anul);
				pt.update();
			}
		}

//		int it = ir.getNumberOfTickets();

//		for (int t = 0; t < it; t++) {
//
//			String ticket = ir.getTicketNumber(t + 1);
//
//			BizPNRTicket pt = new BizPNRTicket();
//			pt.dontThrowException(true);
//			if (pt.Read(company, pnrid, ticket)) {
//				pt.setVoid(true);
//				pt.update();
//			}
//		}

	
	}

	public String getIATA() throws Exception {
		return IATA;
	}

}
