package pss.tourism.interfaceGDS.travelport.record;

import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;
import pss.tourism.interfaceGDS.FareParser;
import pss.tourism.interfaceGDS.Tools;

public class RecordB extends FareParser {

	public static final String MESSAGE_ID = "MESSAGE_ID";
	public static final String PASSFAREID = "PASSFAREID";
	public static final String RECORD_PASSENGER_TYPE = "RECORD_PASSENGER_TYPE";
	public static final String FARE_CALCULATION_TYPE = "FARE_CALCULATION_TYPE";
	public static final String COMPRESS_PRINT_INDICATOR = "COMPRESS_PRINT_INDICATOR";
	public static final String VARIABLE_FARE_CALCULATION_DATA = "VARIABLE_FARE_CALCULATION_DATA";
	public static final String FARE_CALCULATION_DATA = "FARE_CALCULATION_DATA";
	public static final String BASE_CURRENCY_CODE = "BASE_CURRENCY_CODE";

	JMap<String, String> mPassIds = JCollectionFactory.createMap();

	String passtype = "";
	private boolean bDomestic = false;

	private JMap<String, JMap<String, String>> mTaxType = JCollectionFactory.createMap();

	public int getTaxCount(String passtype) {
		JMap<String, String> mTaxFields = mTaxType.getElement(passtype);
		return mTaxFields.size();
	}

	public JMap<String, String> getTaxCollection(String passtype) {
		JMap<String, String> mTaxFields = mTaxType.getElement(passtype);
		return mTaxFields;
	}

	public RecordB() {
		ID = "B";
	}

	protected void removeM4Marks() throws Exception {
		int idx = data.indexOf('\\');
		if (idx < 0)
			this.data = this.data.substring(3).trim();
		else
			this.data = this.data.substring(3, idx).trim();

	}

	public String getFareIdFromPaxType(String paxtype) {
		return mPassIds.getElement(paxtype);
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {
		int i = 0;
		if (line.substring(i += 1).length() < 2)
			return line;

		processFareLine(line.substring(i));

		passtype = line.substring(i, i += 3);

		addFieldValue(RECORD_PASSENGER_TYPE + passtype, passtype);
		int j = line.indexOf("END");
		int k = line.indexOf("NUC");

		if (k < 0)
			k = line.indexOf("ARS");
		if (k < 0)
			k = line.indexOf("USD");
		if (k < 0)
			k = line.indexOf("MXN");

		if (k + 3 > j)
			return line;
		if (JTools.isNumber( line.substring(k + 3, j)) == false ) return line;
		
		long passfareid = (long) JTools.rd(Double.parseDouble(Tools.getOnlyNumbers(line.substring(k + 3, j))), 0);
		mPassIds.addElement(passtype, passfareid + "");
		PssLogger.logDebug("PassFareID " + passtype + ": " + passfareid);

		if (j >= i && j >= 0) {
			addFieldValue(VARIABLE_FARE_CALCULATION_DATA + passtype, line.substring(i, j));
			i = j + 3; // skip END
		}
		String fareData = line.substring(i);
		addFieldValue(FARE_CALCULATION_DATA + passtype, fareData);
		analizeFareData(fareData, passtype);

		return line;

	}

	private void analizeFareData(String fareData, String passtype) throws Exception {

		if (fareData.length() == 0)
			return;
		boolean isdomestic = false;

		// END ROE1.00AF
		// XT\38.20QO9.60TQ262.90FR5.70IZ\84.80QX37.40DE110.20RA11.70EX\25.70HB29.10IT6.00MJ10.30VT\1USD/3.820000ARS

		JMap<String, String> mTaxFields = JCollectionFactory.createMap();

		int idx = fareData.indexOf("XT");
		if (idx >= 0)
			fareData = fareData.substring(idx + 2);
		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(fareData, '\\');
		tok.skipEmptyTokens(false);

		int count = tok.countTokens();

		isdomestic = false;

		for (int i = 0; i < count && tok.hasMoreTokens(); i++) {

			fareData = tok.nextToken().trim();

			if (i == count - 1) {
				if (fareData.equals("") == false)
					if (fareData.substring(0, 1).equals("1") && Tools.isOnlyLetters(fareData.substring(1, 4), false) && Tools.isOnlyLetters(fareData.substring(fareData.length() - 3), false)) {
						addFieldValue(BASE_CURRENCY_CODE + passtype, fareData.substring(1, 4));
						break;
					}
				if (fareData.startsWith("XT"))
					fareData = fareData.substring(2);
			}

			boolean flag2 = true;
			while (flag2 && fareData.length() > 0) {
				String taxcode = "", taxamount = "";

				int ii = 0;
				while (JTools.isNumber(fareData.charAt(ii)) || fareData.charAt(ii) == '.' || fareData.charAt(ii) == ' ') {
					taxamount += fareData.charAt(ii++);
					if (ii >= fareData.length())
						return;
				}
				if (ii == 0)
					break;
				taxamount = taxamount.replace(" ", "");
				taxcode = fareData.substring(ii, ii + 2);
				if (taxcode.equals(TAX_DL))
					isdomestic = true;
				if (taxcode.equals(TAX_XT) == false && taxcode.equals(TAX_PD) == false) {
					PssLogger.logDebug("TaxCode " + taxcode + " = " + taxamount);
					String taxamount1 = mTaxFields.getElement(taxcode);
					String ta1 = taxamount1 == null ? "" : taxamount1;
					if (ta1.trim().equals("") == false) {
						double am1 = Double.parseDouble(taxamount1);
						String ta = taxamount == null ? "0" : taxamount;
						if (ta.trim().equals(""))
							ta = "0";
						double am2 = Double.parseDouble(ta);
						mTaxFields.removeElement(taxcode);
						mTaxFields.addElement(taxcode, JTools.rd(am1 + am2, 2) + "");
					} else
						mTaxFields.addElement(taxcode, taxamount);
				}

				fareData = fareData.substring(ii + 2);

			}
		}
		mTaxType.addElement(passtype, mTaxFields);

		bDomestic = isdomestic;

	}

	public boolean isDomestic() {
		return bDomestic;
	}

	public String getPassengerType() {
		return this.getFieldValue(RECORD_PASSENGER_TYPE);
	}

	public String getCurrencyBaseFare(String passtype) {
		String curr = getFieldValue(BASE_CURRENCY_CODE + passtype);
		if (curr == null)
			curr = "ARS";
		return curr;
	}

}
