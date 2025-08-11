package pss.tourism.interfaceGDS.amadeus.record;

import java.util.StringTokenizer;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;
import pss.tourism.interfaceGDS.amadeus.record.IRecord.EMD;

public class EMDRecord extends AmadeusRecord {

	public static String FARE = "FARE";
	public static String NET = "NET";
	public static String NET_CURRENCY = "NET_CURR";
	public static String TOTAL = "TOTAL";
	public static String TOTAL_CURRENCY = "TOT_CURR";
	public static String TAX = "TAX";
	public static String TAX_CURRENCY = "TAX_CURR";
	public static String TAX_CODE = "TAX_CODE";
	public static String AIRLINE_CODE = "AIRLINE_CODE";

	public static String ID_EMD = "ID_EMD";

	public double getFare() {
		try {
			return Double.parseDouble(getFieldValue(FARE));
		} catch (Exception eee) {
			return 0.0f;
		}
	}

	public String getEMDID() {
		return getFieldValue(ID_EMD);
	}
	public String getAirlane() {
		return getFieldValue(AIRLINE_CODE);
	}
	public String getNetCurrency() {
		return getFieldValue(NET_CURRENCY);
	}

	public String getTotalCurrency() {
		return getFieldValue(TOTAL_CURRENCY);
	}

	public String getTaxCurrency() {
		return getFieldValue(TAX_CURRENCY);
	}

	public String getTaxCode() {
		return getFieldValue(TAX_CODE);
	}

	public double getNetFare() {
		try {
			return Double.parseDouble(getFieldValue(NET));
		} catch (Exception eee) {
			return 0.0f;
		}
	}

	public double getTotalFare() {
		try {
			return Double.parseDouble(getFieldValue(TOTAL));
		} catch (Exception eee) {
			return 0.0f;
		}
	}

	public double getTaxAmount() {
		try {
			return Double.parseDouble(getFieldValue(TAX)==null?"0":getFieldValue(TAX));
		} catch (Exception eee) {
			return 0.0f;
		}
	}

	public EMDRecord() {
		ID = "EMD";
	}

	// ==========================================================================
	// A Record
	// ==========================================================================
	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {
		int i = 0;
		if (line.substring(i += 2).length() < 6)
			return line;

		try {
			int i2 = line.substring(i).indexOf(";F;");
			if (i2 < 0)
				i2 = line.substring(i).indexOf(";R;");

			if (i2 < 0)
				return line;

			String emd = line.substring(i2 + 3 + 2);
			JStringTokenizer tok3 = JCollectionFactory.createStringTokenizer(line, ';');
			tok3.skipEmptyTokens(false);
			String m1 = tok3.nextToken();
			String airlineCode = tok3.nextToken();
			setFieldValue("AIRLINE_CODE", airlineCode);
			String vuelo = tok3.nextToken();
			String descraerolinea = tok3.nextToken();
			String fecha = tok3.nextToken();
			String emdId = tok3.nextToken();
			
			JStringTokenizer tok2 = JCollectionFactory.createStringTokenizer(emd, ';');
			tok2.skipEmptyTokens(false);
			// String fare = tok2.nextToken().trim();

			// CV-75.00;F;USD 75.00;N;;USD 84.00;;T- USD 9.00EC

			String net = tok2.nextToken();
			int ii = net.indexOf(' ');
			String currency = net.substring(0, ii);
			String netvalue = net.substring(ii).trim();
			setFieldValue(NET_CURRENCY, currency);
			setFieldValue(NET, netvalue);
			setFieldValue(FARE, netvalue);
			setFieldValue(ID_EMD, emdId);

			String a=tok2.nextToken(); // N value
			String b=tok2.nextToken(); // N value

			String total = tok2.nextToken();
			ii = total.indexOf(' ');
			String tcurr = total.substring(0, ii).trim();
			String totalvalue = total.substring(ii).trim();
			setFieldValue(TOTAL_CURRENCY, tcurr);
			setFieldValue(TOTAL, totalvalue);

			String c=tok2.nextToken(); // empty

			String token = tok2.nextToken();
			String istax = "";

			
			if (token.length() > 2)
				istax = token.substring(0, 2).trim();

			if (istax.equals("T-")) {

				String tax = token.substring(2).trim();
				ii = tax.indexOf(' ');
				String taxcurr = tax.substring(0, ii).trim();
				String taxvalue = tax.substring(ii, tax.length() - 2).trim();
				String taxcode = tax.substring(tax.length() - 2);
				setFieldValue(TAX_CURRENCY, taxcurr);
				setFieldValue(TAX, taxvalue);
				setFieldValue(TAX_CODE, taxcode);

			}

			String lineold = line;
			while (true) {
				getInput().mark(14);
				char cbuf[] = new char[14];
				getInput().read(cbuf, 0, 14);

				line = new String(cbuf);
				if (line.startsWith("I-")) {
					getInput().reset();

					break;
				}
				if (line.startsWith("EMD"))
					break;
				if (line.indexOf("EMD") > 0) {
					getInput().reset();
					getInput().read(cbuf, 0, 10);
				} else
					getInput().reset();
				
				if (line.startsWith("MC"))
					break;
				if (line.startsWith("EMQ")) {
					getInput().reset();
				} else if (line.indexOf("MC") > 0) {
					getInput().reset();
					getInput().read(cbuf, 0, 10);
				} else
					getInput().reset();
			

				getInput().mark(1000);
				line = getInput().readLine();
				if (line == null)
					break;
				if (line.startsWith("ICW") == false && line.startsWith("EMQ") == false)
					break;
				if (line.startsWith("ICW"))
					processICW(line);
				if (line.startsWith("EMQ"))
					processEMQ(line);

			}

			line = lineold;
			getInput().reset();

		} catch (Exception eee) {

		}

		return line;
	}

	public String doProcess2(JMap<String, Object> mRecords, String line) throws Exception {
    int i = 0;
    if (line.substring(i += 2).length() < 6)
        return line;

    try {
        int i2 = line.substring(i).indexOf(";F;");
        if (i2 < 0)
            i2 = line.substring(i).indexOf(";R;");

        if (i2 < 0)
            return line;

        String emd = line.substring(i2 + 3 + 2);

        JStringTokenizer tok2 = JCollectionFactory.createStringTokenizer(emd, ';');
        tok2.skipEmptyTokens(false);

        String net = tok2.nextToken();
        int ii = net.indexOf(' ');
        String currency = net.substring(0, ii);
        String netvalue = net.substring(ii).trim();
        setFieldValue(NET_CURRENCY, currency);
        setFieldValue(NET, netvalue);
        setFieldValue(FARE, netvalue);

        String a=tok2.nextToken(); // N value
        String b=tok2.nextToken(); // N value

        String total = tok2.nextToken();
        ii = total.indexOf(' ');
        String tcurr = total.substring(0, ii).trim();
        String totalvalue = total.substring(ii).trim();
        setFieldValue(TOTAL_CURRENCY, tcurr);
        setFieldValue(TOTAL, totalvalue);

        String c=tok2.nextToken(); // empty

        String token = tok2.nextToken();
        String istax = "";

        if (token.length() > 2)
            istax = token.substring(0, 2).trim();

        if (istax.equals("T-")) {
            String tax = token.substring(2).trim();
            ii = tax.indexOf(' ');
            String taxcurr = tax.substring(0, ii).trim();
            String taxvalue = tax.substring(ii, tax.length() - 2).trim();
            String taxcode = tax.substring(tax.length() - 2);
            setFieldValue(TAX_CURRENCY, taxcurr);
            setFieldValue(TAX, taxvalue);
            setFieldValue(TAX_CODE, taxcode);
        }

        // Ajuste: Buscar la línea "EMD" y extraer AIRLINE_CODE
        while (true) {
            getInput().mark(14);
            char cbuf[] = new char[14];
            getInput().read(cbuf, 0, 14);

            line = new String(cbuf);

            if (line.startsWith("EMD")) {
                // Capturar el código de aerolínea (segundo token después de "EMD")
                JStringTokenizer emdTok = JCollectionFactory.createStringTokenizer(line, ';');
                emdTok.skipEmptyTokens(false);

                emdTok.nextToken(); // Salta "EMDXXX"
                String airlineCode = emdTok.nextToken().trim(); // Captura el código de aerolínea
                setFieldValue("AIRLINE_CODE", airlineCode);

              
            }

            if (line.startsWith("I-") || line.startsWith("MC")) {
                getInput().reset();
                break;
            }

            getInput().reset();
            getInput().mark(1000);
            line = getInput().readLine();

            if (line == null || (!line.startsWith("ICW") && !line.startsWith("EMQ")))
                break;

            if (line.startsWith("ICW"))
                processICW(line);
            if (line.startsWith("EMQ"))
                processEMQ(line);
        }

    } catch (Exception eee) {
        eee.printStackTrace();
    }

    return line;
}




	private void processEMQ(String line) {

	}

	private void processICW(String line) {
		JStringTokenizer tok2 = JCollectionFactory.createStringTokenizer(line, ';');
		tok2.skipEmptyTokens(false);

		tok2.nextToken();

		setFieldValue(ID_EMD, tok2.nextToken());

	}

}
