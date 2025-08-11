package pss.tourism.interfaceGDS.sabre.record;

import java.io.Serializable;

import pss.core.tools.JTools;
import pss.core.tools.collections.JMap;
import pss.tourism.interfaceGDS.FareParser;

public class M6Record extends FareParser implements Serializable {

	public static final String MESSAGE_ID = "IU6MID";

	public M6Record() {
		ID = SabreRecord.M6;
	}

	// ==========================================================================
	// M6 Record - Passenger Fare Calculation Data Record
	// ==========================================================================
	// This record is based off fare calculation data from the PNR. The M6
	// contains
	// fare calculation information needed to print a ticket. There is one M6
	// created
	// for each passenger type ticketed. If more than one passenger has the same
	// fare
	// calculation type, the passenger type in the fare calculation record
	// matches
	// the
	// passenger type in the M2 record.
	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {
		int mode = 1;
		int i = 2;
		addFieldValue(MESSAGE_ID, ID); // MESSAGE ID
		addFieldValue(RECORD_PASSENGER_TYPE, line.substring(i, i += 3));
		if (line.length()<i+1) return line;
		addFieldValue(FARE_CALCULATION_TYPE, line.substring(i, i += 1));
		addFieldValue(COMPRESS_PRINT_INDICATOR, line.substring(i, i += 1));
		this.mRecords = mRecords;
		processFareLine(line.substring(i));

		return line;

	}

	protected void removeM4Marks() throws Exception {
		M0Record m0 = (M0Record) (mRecords.getElement(SabreRecord.M0));
		int im4 = m0.getNumberOfM4();
		for (int j = 0; j < im4; j++) {
			M4Record m4 = (M4Record) (mRecords.getElement(SabreRecord.M4
					+ (j + 1)));
			String expanded = m4
					.getFieldValue(M4Record.FARE_BASIS_CODE_EXPANDED);
			String ticket = m4
					.getFieldValue(M4Record.TICKET_DESIGNATOR_EXPANDED);
			if (!ticket.isEmpty() && !expanded.isEmpty()
					&& (expanded + "/" + ticket).length() > 4)
				data = JTools.replace(data, expanded + "/" + ticket, " ");
			if (!ticket.isEmpty() && !expanded.isEmpty()
					&& ("M/" + ticket).length() > 4)
				data = JTools.replace(data, "M/" + ticket, " ");// a veces viene
																// una
																// inexplicable
																// M
		}
		im4 = m0.getNumberOfM4();
		for (int j = 0; j < im4; j++) {
			M4Record m4 = (M4Record) (mRecords.getElement(SabreRecord.M4
					+ (j + 1)));
			String expanded = m4
					.getFieldValue(M4Record.FARE_BASIS_CODE_EXPANDED);
			String ticket = m4
					.getFieldValue(M4Record.TICKET_DESIGNATOR_EXPANDED);
			if (!expanded.isEmpty() && expanded.length() >= 5)
				data = JTools.replace(data, expanded, " ");
			if (!ticket.isEmpty() && ticket.length() > 6)
				data = JTools.replace(data, ticket, " ");
		}
	}

	// UIOX/PTYLAXQUIOLAX85.00QUIOLAX20.00273.50BJSQ174.28800.00/-SZXX/BJSLAXQ174.282213.17X/PTYUIOQLAXUIO85.00251.00NUC4076.23XFLAX4.5
	// PD

	// private void analizeExtraFareData() {
	// String fareData = getFareDataCalc();
	// FareRecord f = null;
	// JList<String> lista = getLista(fareData);
	//
	// int i = 1;
	// String forg = null;
	// String org = null;
	// String air = null;
	// String imp = null;
	// String typ = null;
	// String dst = null;
	// double ito = 0;
	// boolean impuesto = false;
	// f = new FareRecord();
	// JIterator<String> it = lista.getIterator();
	// while (it.hasMoreElements()) {
	// String pal = it.nextElement();
	// if (moneda != null) {
	// total = pal;
	// break;
	// }
	// if (impuesto == true && !JTools.isNumberPure(pal)
	// && pal.length() != 6) {
	// impuesto = false;
	//
	// }
	// if (org == null && pal.length() == 3) {
	// forg = org = pal;
	// continue;
	// }
	// if (air == null && pal.length() == 2) {
	// air = pal;
	// dst = null;
	// impuesto = false;
	// typ = null;
	// imp = null;
	// continue;
	// }
	// if (dst == null && pal.startsWith("X/")) {
	// String d = pal.substring(2);
	// if (d.startsWith("E/"))
	// d = d.substring(2);
	// if (d.startsWith("T/"))
	// d = d.substring(2);
	//
	// f.addEscala(org, air, d);
	// org = d;
	// air = null;
	// continue;
	// }
	// if (dst == null && pal.length() == 3) {
	// if (air == null) {
	// moneda = pal;
	// continue;
	// }
	// dst = pal;
	// f.addEscala(org, air, dst);
	// f.airport_from = forg;
	// f.airport_to = dst;
	// f.id = i;
	// mFareFields.addElement("" + (i++), f);
	// org = dst;
	// forg = dst;
	// continue;
	// }
	// if (dst == null && pal.length() == 5
	// && (pal.startsWith("/-") || (pal.startsWith("//")))) {
	// forg = org = pal.substring(2);
	// continue;
	// }
	// if (f != null && pal.equals("M")) {// facturado a la milla.. por
	// // ahora lo ignoro
	// continue;
	// }
	// if (f != null && pal.length() == 1) {// impuestos Q, D
	// impuesto = true;
	// continue;
	// }
	// if (impuesto && JTools.isNumberPure(pal)) {
	// imp = pal;
	// impuesto = false;
	// ito += Double.parseDouble(pal);
	// f.addTax(typ, imp);
	// continue;
	// }
	// if (impuesto && pal.length() == 6 && !JTools.isNumberPure(pal)) {
	// typ = pal;
	// continue;
	// }
	// if (!impuesto && JTools.isNumberPure(pal)) {
	// f.importe = pal;
	// f.impuesto = ito;
	// ito = 0;
	// if (dst != null)
	// org = dst;
	// air = null;
	// dst = null;
	// impuesto = false;
	// typ = null;
	// imp = null;
	// f = new FareRecord();
	// continue;
	// }
	// }
	//
	// }

	// JList<String> getLista(String texto) {
	// JList<String> lista = JCollectionFactory.createList();
	//
	// JStringTokenizer tok = JCollectionFactory.createStringTokenizer(texto,
	// ' ');
	// tok.skipEmptyTokens(false);
	// int posM;
	// while (tok.hasMoreTokens()) {
	// String val = tok.nextToken();
	// if ((posM = val.indexOf("M")) != -1) {
	// if (posM >= 1 && val.length() > posM + 1
	// && JTools.isNumberPure("" + val.charAt(posM + 1))
	// && JTools.isNumberPure("" + val.charAt(posM - 1))) {
	// if (posM >= 3
	// && !JTools.isNumberPure("" + val.charAt(posM - 3))
	// && val.charAt(posM - 3) != '.'
	// && (val.charAt(posM - 1) == '0' || val
	// .charAt(posM - 1) == '5')
	// && (val.charAt(posM - 2) == '1' || val
	// .charAt(posM - 2) == '2')) {
	// val = val.substring(0, posM - 2) + "M"
	// + val.substring(posM + 1);
	// } else if (posM >= 2
	// && !JTools.isNumberPure("" + val.charAt(posM - 2))
	// && val.charAt(posM - 2) != '.'
	// && val.charAt(posM - 1) == '5') {
	// val = val.substring(0, posM - 1) + "M"
	// + val.substring(posM + 1);
	// } else if (posM == 2
	// && (val.charAt(posM - 1) == '0' || val
	// .charAt(posM - 1) == '5')
	// && (val.charAt(posM - 2) == '1' || val
	// .charAt(posM - 2) == '2')) {
	// val = val.substring(0, posM - 2) + "M"
	// + val.substring(posM + 1);
	// } else if (posM >= 1 && val.charAt(posM - 1) == '5') {
	// val = val.substring(0, posM - 1) + "M"
	// + val.substring(posM + 1);
	// }
	// }
	// }
	// String pal = null;
	// char type = ' ';
	// for (int i = 0; i < val.length(); i++) {
	// boolean isNumber = JTools.isNumber(val.charAt(i))
	// || val.charAt(i) == '.';
	// if (type == ' ') {
	// type = isNumber ? 'N' : 'C';
	// pal = "" + val.charAt(i);
	// } else if (type == 'N') {
	// if (!isNumber) {
	// if (pal != null)
	// lista.addElement(pal);
	// pal = "" + val.charAt(i);
	// type = isNumber ? 'N' : 'C';
	// } else {
	// pal += "" + val.charAt(i);
	// }
	// } else if (type == 'C') {
	// if (isNumber) {
	// if (pal != null)
	// lista.addElement(pal);
	// pal = "" + val.charAt(i);
	// type = isNumber ? 'N' : 'C';
	// } else {
	// pal += "" + val.charAt(i);
	// }
	// }
	// }
	// if (pal != null)
	// lista.addElement(pal);
	// }
	// return lista;
	// }

	// private int getNumberOfFareCalculationData(JMap<String, Object> mRecords)
	// {
	// return ((M0Record) mRecords.getElement(SabreRecord.M0)).getNumberOfM8();
	// }
	//

}
