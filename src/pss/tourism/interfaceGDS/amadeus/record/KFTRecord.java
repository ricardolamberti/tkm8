package pss.tourism.interfaceGDS.amadeus.record;

import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;

public class KFTRecord extends AmadeusRecord {

	private String TAX_COUNT="TXCOUNT";
	private String CURRENCY="CURRENCY";
	private String TAX_AMOUNT="TXAMOUNT";
	private String TAX_CODE="TXCODE";
	private String TAX_SUM="TXSUM";

	private String OTAX_COUNT="OTXCOUNT";
	private String OCURRENCY="OCURRENCY";
	private String OTAX_AMOUNT="OTXAMOUNT";
	private String OTAX_CODE="OTXCODE";
	private String OTAX_SUM="OTXSUM";


	
	
	public KFTRecord() {
		ID = "KFT";
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {
		int i = 0;
		if (line.substring(i += 5).length() < 6)
			return line;

		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(line.substring(i), AmadeusRecord.FIELD_SEPARATOR);
		tok.skipEmptyTokens(false);

		int idx=1;
		double totalTax = 0.0f;
		double ototaltax = 0.0f;
		int oidx=1;
		String aux = tok.nextToken();
		while (tok.hasMoreTokens()) {
			i = 0;
			if ( aux.length() > 0 ) {
				String oldtax = aux.substring(i,++i);
				if (oldtax.equals("O")) {
					try {
					String currency = aux.substring(i, i += 3);
					String amount   = aux.substring(i, i += 9).trim();
					if (amount.equals("EXEMPT")) amount = "0";
					String taxcode  = aux.substring(i, i += 2);
					int curridx = checkOTaxCode( taxcode, idx );
					if ( curridx > 0 ) {
						double taxamount1 = Double.parseDouble(getFieldValue(OTAX_AMOUNT + curridx)==null?"0":getFieldValue(OTAX_AMOUNT + curridx));
						double taxamount2 = Double.parseDouble(amount);
					  setFieldValue(OTAX_AMOUNT + curridx, JTools.rd(taxamount1+taxamount2,2)+"");
					} else {
					  addFieldValue(OCURRENCY + idx, currency);
					  addFieldValue(OTAX_CODE + idx, taxcode );
					  addFieldValue(OTAX_AMOUNT + idx, amount);
					  oidx++;
					}
					char arr[] = amount.toCharArray();
			
					ototaltax += Double.parseDouble(amount.trim());
					} catch (Exception ignored) {}
					aux = tok.nextToken();
					continue;
				}
				String currency = aux.substring(i, i += 3);
				String amount   = aux.substring(i, i += 9).trim();
				if (amount.equals("EXEMPT")) amount = "0";
				String taxcode  = aux.substring(i, i += 2);
				int curridx = checkTaxCode( taxcode, idx );
				if ( curridx > 0 ) {
					double taxamount1 = Double.parseDouble(getFieldValue(TAX_AMOUNT + curridx));
					double taxamount2 = Double.parseDouble(amount);
				  setFieldValue(TAX_AMOUNT + curridx, JTools.rd(taxamount1+taxamount2,2)+"");
				} else {
				  addFieldValue(CURRENCY + idx, currency);
				  addFieldValue(TAX_CODE + idx, taxcode );
				  addFieldValue(TAX_AMOUNT + idx, amount);
				  idx++;
				}
				char arr[] = amount.toCharArray();
		
				totalTax += Double.parseDouble(amount.trim());
			}
			aux = tok.nextToken();
		}
		addFieldValue(TAX_COUNT, idx-1);
		addFieldValue(TAX_SUM, totalTax+"");
		addFieldValue(OTAX_COUNT, oidx-1);
		addFieldValue(OTAX_SUM, ototaltax+"");
		

		return line;
	}
	
	private int checkOTaxCode(String taxcode, int idx) {
		for ( int i = 1 ; i < idx ; i++ ) {
			if ( getFieldValue(TAX_CODE+i).equals(taxcode) )
				return i;
		}
		return 0;
	}
	
	private int checkTaxCode(String taxcode, int idx) {
		for ( int i = 1 ; i < idx ; i++ ) {
			if ( getFieldValue(TAX_CODE+i).equals(taxcode) )
				return i;
		}
		return 0;
	}
	public String getOTaxAmount(int i) {
		return getFieldValue(OTAX_AMOUNT+i);
	}
	public String getOTaxCurrency(int i) {
		return getFieldValue(OCURRENCY+i);
	}
	public String getOTaxCode(int i) {
		return getFieldValue(OTAX_CODE+i);
	}

	public int getOTaxCount() throws Exception {
		return Integer.parseInt(getFieldValue(OTAX_COUNT));
	}
	
	public double getOTotalTax() throws Exception {
		return Double.parseDouble(getFieldValue(OTAX_SUM));
	}

	public String getTaxAmount(int i) {
		return getFieldValue(TAX_AMOUNT+i);
	}
	public String getTaxCurrency(int i) {
		return getFieldValue(CURRENCY+i);
	}
	public String getTaxCode(int i) {
		return getFieldValue(TAX_CODE+i);
	}

	public int getTaxCount() throws Exception {
		return Integer.parseInt(getFieldValue(TAX_COUNT));
	}
	
	public double getTotalTax() throws Exception {
		return Double.parseDouble(getFieldValue(TAX_SUM));
	}


}
