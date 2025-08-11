package pss.tourism.interfaceGDS.amadeus.transaction;

import java.util.Date;

import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.tourism.interfaceGDS.BaseTransaction;
import pss.tourism.interfaceGDS.Tools;
import pss.tourism.interfaceGDS.amadeus.record.IRecord;
import pss.tourism.interfaceGDS.amadeus.record.TRecord;
import pss.tourism.pnr.BizPNRTicket;
import pss.tourism.voidManual.BizVoidManual;

public class VoidTicketTransaction extends BaseTransaction {

	public void save(String voidDate,IRecord ir) throws Exception {
		int i = 1;

		if (IRecord.isEMD()) {

			Object[] ids = IRecord.getIds();
			for (int idx = 0; idx < IRecord.countEMDs(); idx++) {
				String emdid = ids[idx].toString();
				String ticket = IRecord.getEMDTicket(emdid);
				saveVoidTicket(voidDate,ticket);
			}
			return;

		}

		int it = ir.getNumberOfTickets();

		for (int t = 0; t < it; t++) {

			String ticket = ir.getTicketNumber(t + 1);

			saveVoidTicket(voidDate,ticket);

		}


	}
	public void saveWithT(String voidDate,TRecord ir) throws Exception {
	
	
			String ticket = ir.getTicketNumber();

			saveVoidTicket(voidDate,ticket);

		


	}
	private void saveVoidTicket(String voidDate,String ticket) throws Exception {
		BizPNRTicket pt = new BizPNRTicket();
		pt.dontThrowException(true);
		if (this.getPnrLocator() != null) {
			if (pt.Read(company, this.getPnrLocator(), ticket) == false) {
					new BizVoidManual().procAnularForce(getCompany(), ticket);
					return;
				}
		} else {
			if (this.getCreationDate() != null) {
				if (pt.Read(company, this.getCreationDate(), ticket) == false){
					new BizVoidManual().procAnularForce(getCompany(), ticket);
					return;
				}
			}

		}
		this.setPnrLocator(pt.getCodigopnr());
		long year = pt.getCreationDate().getYear()+1900;
		String day = voidDate.substring(0, 2);
		String month = Tools.convertMonthStringToNumber(voidDate.substring(2));
		if (year%4!=0 && JTools.getLongNumberEmbedded(month)==2 && JTools.getLongNumberEmbedded(day)==29)
			day="28";
		Date date = JDateTools.StringToDate(""+year + month + day, "yyyyMMdd");
		pt.processAnular(date);
	}

	public String getIATA() throws Exception {
		return IATA;
	}

	// public String getIATA() throws Exception {
	// return IATA;
	// }

}
