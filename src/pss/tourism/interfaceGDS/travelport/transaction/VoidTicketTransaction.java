package pss.tourism.interfaceGDS.travelport.transaction;

import java.util.Date;

import pss.tourism.interfaceGDS.BaseTransaction;
import pss.tourism.interfaceGDS.travelport.record.RecordA;
import pss.tourism.pnr.BizPNRTicket;
import pss.tourism.voidManual.BizVoidManual;

public class VoidTicketTransaction extends BaseTransaction {

	public void save(Date anul,String pnrid,  RecordA a ) throws Exception {


		int it = a.getNumberOfTickets();

		for (int t = 0; t < it; t++) {

			String ticket = a.getTicketNumber(t + 1);

			BizPNRTicket pt = new BizPNRTicket();
			pt.dontThrowException(true);
			if (pt.Read(company, pnrid, ticket)) {
//				pt.SetAnulado(true);
				pt.processAnular(anul);
			} else {
				new BizVoidManual().procAnularForce(getCompany(), ticket);
				
			}
		}
		
//		BizPNR pnr = new BizPNR();
//		pnr.dontThrowException(true);
//		if (pnr.Read(company, pnrid)) {
//			IATA = pnr.getIATA();
//		}

	}

//	public String getIATA() throws Exception {
//		return IATA;
//	}

}
