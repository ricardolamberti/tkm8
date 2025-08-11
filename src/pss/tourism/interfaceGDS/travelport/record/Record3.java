package pss.tourism.interfaceGDS.travelport.record;

import java.util.Date;

import pss.core.tools.JDateTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;
import pss.tourism.interfaceGDS.Tools;

public class Record3 extends TravelPortRecord {

	public static final String IATA = "IATA";

	public static final String BOOKING_SID = "BOOKING_SID";
	public static final String BOOKING_SID2 = "BOOKING_SID2";
	
	private String BOOK_DATE = "BOOK_DATE";

	private String BOOK_TIME = "BOOK_TIME";

	
	private String BOOK_AGENT_SINE="BOOK_AGENT_SINE";

	public Record3() {
		ID = THREE;
	}

	// ==========================================================================
	// AMD Record - Retrans && PNRLocator
	// ==========================================================================
	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		int i = 0;

		if (line.substring(i += 2).length() < 2)
			return line;

		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(line
				.substring(i), TravelPortRecord.FIELD_SEPARATOR);
		tok.skipEmptyTokens(false);

		int count = tok.countTokens();

		String book = tok.nextToken().substring(3);
		
		addFieldValue(BOOKING_SID, book.substring(0, 3));

		book = tok.nextToken().substring(3);
		i=0;
		count--;
		addFieldValue(BOOKING_SID2, book.substring(i, i += 3));
		count--;
		addFieldValue(IATA, book.substring(i, i += 8));
		count--;

		String datetime = book.substring(i+=1);
		count--;
		i=0;
		addFieldValue(BOOK_DATE, datetime.substring(i, i += 7));
		addFieldValue(BOOK_TIME, datetime.substring(i,i+=4));
		addFieldValue(BOOK_AGENT_SINE, datetime.substring(i));
		
		return line;
	}
	public String getBook1() {
		return getFieldValue(BOOKING_SID);
	}
	public String getBook2() {
		return getFieldValue(BOOKING_SID2);
	}
	
	public String getIATA() {
		return getFieldValue(IATA);
	}
	
	public Date getCreationDate() throws Exception {
		return JDateTools.StringToDate(Tools.convertDateMonthStringToNumber(getFieldValue(BOOK_DATE)),"ddMMyy");
	}
	

	public Date getCreationDateAIR() throws Exception {
		return getCreationDate();
	}


}
