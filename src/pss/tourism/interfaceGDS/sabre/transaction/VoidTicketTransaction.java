package pss.tourism.interfaceGDS.sabre.transaction;

import pss.tourism.interfaceGDS.BaseTransaction;
import pss.tourism.interfaceGDS.sabre.record.AARecord;
import pss.tourism.interfaceGDS.sabre.record.M0VoidRecord;
import pss.tourism.pnr.BizPNRTicket;
import pss.tourism.voidManual.BizVoidManual;

public class VoidTicketTransaction extends BaseTransaction {

	public void save(AARecord aa,M0VoidRecord vr) throws Exception {
		BizPNRTicket pt = new BizPNRTicket();
		pt.dontThrowException(true);
		if (pt.Read(company, vr.getPNRLocator(), vr.getTicketNumber())) {
//			pt.SetAnulado(true);
			
			pt.processAnular(aa.getCreationDate(pt.getCreationDate().getYear()+1900));
		} else {
			new BizVoidManual().procAnularForce(getCompany(), vr.getTicketNumber());
			
		}
		IATA = vr.getIATA();
//		BizPNR pnr = new BizPNR();
//		pnr.dontThrowException(true);
//		if (pnr.Read(company, vr.getPNRLocator())) {
//			IATA = pnr.getIATA();
//		}

	}

	public String getIATA() throws Exception {
		return IATA;
	}

}
