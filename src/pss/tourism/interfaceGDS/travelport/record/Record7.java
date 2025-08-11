package pss.tourism.interfaceGDS.travelport.record;

import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;

public class Record7 extends TravelPortRecord {

	public static final String FOP = "FOP";
	public static final String CC = "CC";

	public Record7() {
		ID = SEVEN;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;
		if (line.trim().equals(""))
			return line;
		try {
			if (line.substring(i += 2).length() < 2)
				return line;
		} catch (Exception ee) {
			return line;
		}

		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(line.substring(i), TravelPortRecord.FIELD_SEPARATOR);
		tok.skipEmptyTokens(false);

		String linefop = tok.nextToken();
		String fop = "";
		fop = linefop.substring(0, 2);
		if (fop.equals(CC)) {
			fop += linefop.substring(2, 2 + 2);
			addFieldValue(CC, getCreditCard(linefop.substring(4)));
		} else if (fop.startsWith("CA"))
			fop = "CASH";
		else if (fop.equals("CC"))
			fop = line.substring(2, 4);

		addFieldValue(FOP, fop);

		return line;
	}

	public String getCreditCardNumber() {
		return getFieldValue(CC);
	}

	private String getCreditCard(String fop2) {
		String card = "";

		int i = 0;
		while (JTools.isNumber(fop2.charAt(i)) || fop2.charAt(i) == ' ') {
			if (fop2.charAt(i) != ' ')
				card += fop2.charAt(i);
			i++;
		}

		return card;
	}

	public String getFormOfPaymentDesc() {
		return getFieldValue(FOP);
	}

}
