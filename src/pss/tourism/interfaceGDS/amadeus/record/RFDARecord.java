package pss.tourism.interfaceGDS.amadeus.record;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import pss.core.tools.JDateTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;

public class RFDARecord extends AmadeusRecord {

	protected String ORIGINAL_DATE = "RF_DATE";

	public RFDARecord() {
		ID = "RFDA";
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {
		int i = 0;
		if (line.substring(i += 5).length() < 6)
			return line;

		String source = line.substring(i).trim();
		
		String result[] = source.split(";");

		String dat = result[0];
//		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(source, AmadeusRecord.FIELD_SEPARATOR);
//		tok.skipEmptyTokens(false);
//
//		String dat = tok.nextToken();
//		String b = tok.nextToken();
//		String b1 = tok.nextToken();
//		String b2 = tok.nextToken();
//		String b3 = tok.nextToken();
//		String b4 = tok.nextToken();

		try {
			SimpleDateFormat oSimple = new SimpleDateFormat("ddMMMyy", Locale.ENGLISH);
			oSimple.setLenient(false);
			Date d = oSimple.parse(dat);
			addFieldValue(ORIGINAL_DATE, JDateTools.DateToString(d, "dd/MM/yyyy"));
		} catch (Exception ee) {

		}

		return line;

	}

	public Date getOriginalDate() throws Exception {
		return JDateTools.StringToDate(getFieldValue(ORIGINAL_DATE));
	}

}
