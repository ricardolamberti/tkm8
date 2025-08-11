package pss.tourism.interfaceGDS;

import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class FareParser extends BaseRecord {
	// M6ADT1 BUE AR SLA AR BUE M/BT END
	// M6ADT1 25MAY MEC AV UIO Q6.00 47.70 USD 53.70END ROE1.00
	// M6ADT1 GYE XL X/UIO LA X/MAD IB BCN Q GYEBCN200.00M895.00//MAD LA GYE Q
	// BCNGYE200.00M895.00NUC2190.00END ROE1.00
	// XT20.00ED5.30OR53.21QB5.00QI37.60JD7.00QV1.30OG
	// M6ADT1 BUE LA SCL Q27.00 92.50LA BUE Q27.00 157.50NUC304.00END ROE1.00
	// M6ADT1 BUE LA X/SCL LA GYE//UIO Q BUEUIO47.00M153.00LA X/LIM LA BUE Q
	// UIOBUE47.00M476.50NUC723.50END ROE1.00
	// XT5.00QI10.00E243.93XR10.00TQ10.00QO36.20AR
	// M6ADT1 UIO DL ATL DL ORL UIOATL928.00DL MEX1225.00 1S50.00Q
	// UIOMEX85.00NUC2288.00END ROE1.00 PD
	// 147.36EC3.00WT5.00ED52.37QB5.00QI35.00US5.50YC7.00XY5.00XA5.60AY4.50XF
	// XT15.00ED0.40US5.60AY22.63UKXFMCO4.5
	// M6ADT1 BSB DL X/ATL DL CVG1469.00DL X/ATL DL SAO3656.50
	// 1S50.00NUC5.175.50END ROE1.00
	// XT35.40US11.20AY5.50YC7.00XY5.00XA4.50XFATL4.5
	// M6ADT1 GYE EQ UIO Q11.00 103.00HLEFXAEE EQ GYE Q11.00 128.00 YLEFPAEE
	// USD253.00END
	// M6ADT1 GYE LA LIM LA TRU Q GYETRU54.00M416.50//X/LIM LA UIO Q
	// TRUUIO54.00M787.50NUC1312.00END ROE1.00
	// XT20.00ED26.32QB5.00QI15.00DY41.38HW
	// M6ADT1 SLA LA X/E/BUE LA LIM LA UIO Q SLAUIO47.00M1391.00LA X/LIM
	// LAX/E/BUE LA SLA Q UIOSLA47.00M1099.00 D BUELIM253.00NUC2837.00END
	// ROE1.00 XT53.21QB5.00QI10.00E257.00XR10.00TQ10.00QO141.90AR
	// M6ADT1 UIO LA LIMA Q54.00 504.00LA BUE Q94.00 858.50LA LIM Q94.00
	// 858.50LA UIO Q54.00 504.00NUC3021.00
	// M6SRC1 UIO UA X/HOU Q85.00UA X/CHI UA LAN122.50TNN7E9LS/CD50UA X/CHI UA
	// X/SAO Q65.00O6RIO200.00KLN7E9LS CM X/PTY CM UIO Q RIOUIO47.00
	// 380.00M2/CD50 NUC899.50END ROE1.00
	// XT50.00ED53.82QB5.00QI11.20AY46.39BR1.25AH5.50YC7.00XY3.96XA4.50XFORD4.5
	// GYE 9V BLA Q40.00 104.50 9V GYE Q40.00 104.50Q GYEGYE10.00NUC299.00
	public static final String RECORD_PASSENGER_TYPE = "IU6RTY";
	public static final String FARE_CALCULATION_TYPE = "IU6FCT";
	public static final String COMPRESS_PRINT_INDICATOR = "IU6CPR";
	public static final String VARIABLE_FARE_CALCULATION_DATA = "END";
	public static final String FARE_CALCULATION_DATA = "IU6FC12";

	protected String data;
	protected String sobrante = data;
	int i = 0;
	protected String destino = null;
	protected String origen;
	protected String carrier = "";
	int ln = 1;
	protected JMap<String, Object> mRecords;
	double impuesto = 0;
	boolean findM = false;
	boolean find = true;
	FareRecord f = new FareRecord();
	int entro;
	protected String moneda;
	protected String roe;
	protected double xtamount;
	protected String total;

	protected double q;
	protected String monedaQ;

	protected JMap<String, String> mTaxFields = JCollectionFactory.createMap();
	protected JMap<String, FareRecord> mFareFields = JCollectionFactory.createMap();

	public JMap<String, FareRecord> getFareFields() {
		return mFareFields;
	}

	public void setFareFields(JMap<String, FareRecord> mFareFields) {
		this.mFareFields = mFareFields;
	}

	public double getXtamount() {
		return xtamount;
	}

	public void setXtamount(double xtamount) {
		this.xtamount = xtamount;
	}


	public double getQ() {
		return q;
	}
	

	public String getMonedaQ() {
		return monedaQ;
	}
	
	

	public void setQ(double q) {
		this.q = q;
	}

	public String getRoe() {
		return roe;
	}

	public Double getDoubleRoe() {
		int pos = roe.indexOf(".");
		if (pos!=-1) {
			int pos2 = roe.indexOf(".",pos+1);
			if (pos2!=-1) {
				return Double.parseDouble(roe.substring(0,pos+3));
			}
		}
		return roe == null || roe.trim().equals("") || !JTools.isNumber(roe) ? 0 : Double.parseDouble(roe.trim());
	}

	public double getXTAmount() throws Exception {
		return JTools.rd(xtamount, 2);
	}

	public void setRoe(String roe) {
		this.roe = roe;
	}

	public String getSobrante() {
		return sobrante;
	}

	public void setSobrante(String sobrante) {
		this.sobrante = sobrante;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public int getTaxCount() {
		return mTaxFields.size();
	}

	public JMap<String, String> getTaxCollection() {
		return mTaxFields;
	}

	public FareParser() {
	};

	protected String getFareData() {
		return this.getFieldValue(FARE_CALCULATION_DATA);
	}

	protected String getFareDataCalc() {
		return this.getFieldValue(VARIABLE_FARE_CALCULATION_DATA);
	}

	public String getPassengerType() {
		return this.getFieldValue(RECORD_PASSENGER_TYPE);
	}

	protected void processFareLine(String line) throws Exception {

		if (line.length() == 0)
			return;
		analizeFareDataQ(line);

		int mode = 1;
		int i = 0;
		int j = line.indexOf("END");
		if (j == -1) { // el modo 2, viene sin end... pero tambien viene sin end
			// cuando no incluye VARIABLE_FARE_CALCULATION_DATA
			try {
				if (line.substring(9).indexOf(" ") > 10)
					mode = 2; // intento diferenciar si es un modo 2 o es un modo 1
				// sin zona variable. se deduce que el modo 1
				// siempre tiene un espacio de separador
			} catch (Exception eee) {
			}
		}

		if (mode == 1) {
			if (j >= i && j >= 0) {
				addFieldValue(VARIABLE_FARE_CALCULATION_DATA, line.substring(i, j));
				i = j + 3; // skip END
			}
			String fareData = line.substring(i);
			if (fareData.equals(""))
				return;
			addFieldValue(FARE_CALCULATION_DATA, fareData);
			analizeFareData(getFareData());
			try {
				//analizeExtraFareDataMode1(getFareDataCalc(), mRecords);
			} catch (Exception e) {
				PssLogger.logError(e);
			}
		} else {
			try {
				String fareData = line.substring(i);
				String sobrante = analizeExtraFareDataMode2(fareData);
				addFieldValue(VARIABLE_FARE_CALCULATION_DATA, fareData);
				addFieldValue(FARE_CALCULATION_DATA, sobrante);
				analizeFareData(sobrante);
			} catch (Exception e) {
				PssLogger.logError(e);
			}

		}

	}

	public void analizeFareDataQ(String fareData) {
		 int pos=0;
		 int idx=0;
		 monedaQ=null;
		 
			String sroe = "";
			if (fareData.endsWith("ROE")) return;
		  pos = fareData.indexOf("ROE");
			if (pos!=-1) {
				String fareRoe = fareData.substring(pos + 3);
				int ii = 0;
				while (JTools.isNumber(fareRoe.charAt(ii)) || fareRoe.charAt(ii) == '.') {
					sroe += fareRoe.charAt(ii++);
					if (ii >= fareRoe.length())
						break;
				}
				
			}

			if (sroe.equals(""))
				sroe = "1.00";
			roe = ""+JTools.getDoubleNumberEmbedded(sroe);
		 pos=0;
		 q=0;
		 while ((idx = fareData.indexOf(TAX_Q,pos)) >= 0) {
				String data = fareData.substring(idx + 2);
				pos=idx+1;
				String smount="";
				int ii = 0;
				while (JTools.isNumber(data.charAt(ii)) || data.charAt(ii) == '.') {
					smount += data.charAt(ii++);
					if (ii >= data.length())
						break;
				}		
				q += JTools.getDoubleNumberEmbedded(smount);
				if (ii < data.length() && data.charAt(ii)=='Q') {// a veces vienen 2 q seguidas
					ii++;
					smount="";
					while (JTools.isNumber(data.charAt(ii)) || data.charAt(ii) == '.') {
						smount += data.charAt(ii++);
						if (ii >= data.length())
							break;
					}		
					q += JTools.getDoubleNumberEmbedded(smount);
				}
				if (monedaQ==null) { //simplificacion para encontrar la moneda si es dolares o nuc es dolaes, todo lo otro es la moneda local.
					if (data.indexOf("USD",ii)!=-1)
						monedaQ="USD";
					else if (getDoubleRoe()==1 && data.indexOf("NUC")!=-1)
						monedaQ="USD";
					else {
						monedaQ="EUR";
					}
					
				}
				
			}
//		 if (monedaQ !=null &&monedaQ.equals("NUC")) {
//				monedaQ="EUR";
//			 q*=1f/droe;
//		 }
	}
	
	public void analizeFareData(String fareData) {

		if (fareData.length() <= 3)
			return;
		int idx = 0;
		// boolean isdomestic=false;

		boolean hasXT = false;
		roe = "";
		String type = fareData.substring(idx, 3);
		if (type.equals("ROE")) {
			fareData = fareData.substring(idx + 3);
			int ii = 0;
			while (JTools.isNumber(fareData.charAt(ii)) || fareData.charAt(ii) == '.') {
				roe += fareData.charAt(ii++);
				if (ii >= fareData.length())
					break;
			}
			fareData = fareData.substring(ii);
			if (fareData.length() < 3)
				return;
			// isdomestic=false;
			type = fareData.substring(idx, 3);
		}

		if (roe.equals(""))
			roe = "1.00";
		

		 
		if ((idx = fareData.indexOf(TAX_XT)) >= 0) {
			hasXT = true;
			fareData = fareData.substring(idx + 2);
			// isdomestic=false;
		} else if ((idx = fareData.indexOf(TAX_PD)) >= 0) {
			fareData = fareData.substring(idx + 2);
			// isdomestic=false;
		}
		
		
	
		
		if (fareData.indexOf(TAX_XT) >= 0)
			hasXT = true;

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
			if (ii+2 >fareData.length())
				break;
			taxamount = taxamount.replace(" ", "");
			taxcode = fareData.substring(ii, ii + 2);
			// if (taxcode.equals(TAX_DL) )
			// isdomestic=true;
			if (taxcode.equals(TAX_XT) == false && taxcode.equals(TAX_PD) == false) {
				if (hasXT && taxamount.length() > 0) {
					xtamount += Double.parseDouble(taxamount);
				}
				if (!taxamount.equals("")) {
					PssLogger.logDebug("TaxCode " + taxcode + " = " + taxamount);
					mTaxFields.addElement(taxcode, taxamount);
				}
			}

			fareData = fareData.substring(ii + 2);

		}


		// bDomestic = isdomestic;

	}

	public FareParser analizeExtraFareDataMode1(String zdata, JMap<String, Object> zRecords) throws Exception {
		data = zdata;
		mRecords = zRecords;
		if (data == null)
			return null;
		if (data.endsWith("M/BT"))
			return null; // sin info
		if (data.endsWith("M/IT"))
			return null; // sin info
		if (data.endsWith("BT"))
			return null; // sin info

		removeMarcasDesconocidas();
		removeM4Marks();
		origen = f.airport_from = data.substring(i, i + 3);
		i += 3;

		while (quedeQueParsear()) {
			impuesto = 0;
			consumeEspacios();
			entro = i;

			while (hayRutas()) {
				consumeAerolinea();
				consumeDestino();
				f.addEscala(origen, carrier, destino);
				consumeRelocalizacionOrigen();
				consumeEspacios();
				find = true;
				while (find) {
					find = false;
					try {
					consumeQ();
					consumeM();
					consumeS();
					consumeImporte();
					consumeD();
					} catch (Exception eee) {}
				}
				if (f.importe != null)
					break;
			}
			consumeEspacios();

			consumeMarcasRaras();

			f.id = ln;
			f.impuesto = impuesto;
			mFareFields.addElement("" + (ln++), f);

			if (detectFinish())
				break;

			f = new FareRecord();
			readNextOrigen();

			if (entro == i)
				throw new Exception("Parser error: " + data);
		}

		return this;

	}

	private boolean quedeQueParsear() throws Exception {
		return i < data.length();
	}

	protected boolean hayRutas() throws Exception {
		return data.charAt(i + 2) == ' ' || (data.substring(i + 3).startsWith("/") && JTools.isAlpha(data.substring(i, i + 2))) || (getNumber(data.substring(i + 5)) != null && JTools.isAlphaNumeric(data.substring(i, i + 5)));
	}

	private boolean detectFinish() throws Exception {
		if (JTools.isAlpha(data.substring(i, i + 3)) && getNumber(data.substring(i + 3).trim()) != null && (data.length() - (getNumber(data.substring(i + 3).trim()).length() + 3 + i)) < 9) {// no
																																																																																													// encuentro
																																																																																													// otra
																																																																																													// manera
																																																																																													// de
			// salir
			moneda = data.substring(i, i + 3);
			total = getNumber(data.substring(i + 3).trim());
			i += total.length() + 3;
			sobrante = data.substring(i);
			return true;

		}
		return false;
	}

	private void readNextOrigen() throws Exception {
		if (data.charAt(i) == '/' && (data.charAt(i + 1) == '-' || data.charAt(i + 1) == '/')) {
			i += 2;
			while (data.charAt(i) == ' ')
				i++;
			if (data.charAt(i + 1) == '/') {
				i += 2;
			}
			origen = f.airport_from = data.substring(i, i + 3);
			i += 3;
		} else {
			origen = f.airport_from = destino;
		}
	}

	private void consumeMarcasRaras() throws Exception {
		if (data.length() > i + 8 && getNumber(data.substring(i + 8)) == null && data.substring(i, i + 8).indexOf(" ") == -1 && JTools.isOnlyLettersWithinSpaces(data.substring(i, i + 8), false))
			i += 8;
		if (data.length() > i + 8 && getNumber(data.substring(i + 7)) == null && data.substring(i, i + 7).indexOf(" ") == -1 && JTools.isOnlyLettersWithinSpaces(data.substring(i, i + 7), false))
			i += 7;

	}

	private void consumeRelocalizacionOrigen() throws Exception {
		if (data.substring(i, i + 2).equals("//")) {
			i += 2;
			destino = data.substring(i, i + 3);
			i += 3;
		}
		origen = destino;

	}

	private void consumeEspacios() throws Exception {
		while (data.charAt(i) == ' ')
			i++;

	}

	private void consumeAerolinea() throws Exception {
		if (!(data.charAt(i) != ' ' && data.charAt(i + 1) != ' ' && data.charAt(i + 2) == ' '))
			return;
		carrier = data.substring(i, i + 2);
		i += 2;
		if (data.charAt(i) == ' ')
			i++;

	}

	private void consumeDestino() throws Exception {
		if (data.charAt(i + 1) == '/') {
			boolean find = true;
			while (find) {
				find = true;
				if (data.substring(i).startsWith("E/"))
					i += 2;
				else if (data.substring(i).startsWith("X/"))
					i += 2;
				else if (data.substring(i).startsWith("T/"))
					i += 2;
				else
					find = false;

			}
			consumeQ();
			destino = data.substring(i, i + 3);
			i += 3;
			f.airport_to = destino;

		} else {
			consumeQ();
			destino = data.substring(i, i + 3);
			i += 3;
			f.airport_to = destino;
		}

	}

	private void removeMarcasDesconocidas() throws Exception {
		if (data.substring(i).startsWith("I-"))
			i += 2;
		data = JTools.replace(data, "*", " ");
		if (!data.substring(i + 3).startsWith(" "))
			if (data.substring(i + 5).startsWith(" "))
				i += 5; // salteo una fecha que aparece a veces
		while (data.charAt(i) == ' ')
			i++;
	}

	protected void removeM4Marks() throws Exception {
	}

	private void consumeQ() throws Exception {
		if (data.charAt(i) == 'Q' && (data.charAt(i + 1) == ' ' || JTools.isNumber(data.charAt(i + 1)) || JTools.isOnlyLettersWithinSpaces(data.substring(i + 1, i + 7), false))) {
			String tipo = "";
			String imp = "";
			i++;
			while (data.charAt(i) == ' ')
				i++;

			if (JTools.isOnlyLettersWithinSpaces(data.substring(i, i + 6), false)) {
				tipo = data.substring(i, i + 6);
				i += 6;
			}
			if (getNumber(data.substring(i)) != null) {
				imp = getNumber(data.substring(i));
				i += imp.length();
				impuesto += Double.parseDouble(imp);
			}
			if (data.substring(i, i + 2).equals("B/")) {
				i += 5;
			}
			f.addTax(tipo, imp);
			while (data.charAt(i) == ' ')
				i++;

			find = true;

		}

	}

	private void consumeM() throws Exception {
		findM = false;
		if ((data.substring(i, i + 3).equals("25M") || data.substring(i, i + 3).equals("20M") || data.substring(i, i + 3).equals("15M") || data.substring(i, i + 3).equals("10M")) && getNumber(data.substring(i + 3)) != null) {
			i += 3;
			findM = true;
		} else if (data.substring(i, i + 4).equals("20M ")) {
			i += 4;
			findM = true;
		} else if (data.substring(i, i + 4).equals("15M ")) {
			i += 4;
			findM = true;
		} else if (data.substring(i, i + 4).equals("10M ")) {
			i += 4;
			findM = true;
		} else if (data.substring(i, i + 3).equals("5M ")) {
			i += 3;
			findM = true;
		} else if (data.substring(i, i + 2).equals("M ")) {
			i += 2;
			findM = true;
		} else if (data.substring(i, i + 2).equals("5M") && getNumber(data.substring(i + 2)) != null) {
			i += 2;
			findM = true;
		} else if (data.substring(i, i + 1).equals("M") && getNumber(data.substring(i + 1)) != null) {
			i += 1;
			findM = true;
		} else if (data.substring(i, i + 2).equals("M ") && JTools.isOnlyLettersWithinSpaces(data.substring(i + 2, i + 8), false) && getNumber(data.substring(i + 8)) != null) {
			i += 9;
			findM = true;
		}
		if (findM) {
			while (data.charAt(i) == ' ')
				i++;
			find = true;

			if (JTools.isOnlyLettersWithinSpaces(data.substring(i, i + 6), false)) {
				data.substring(i, i + 6);
				i += 6;
			}
			if (getNumber(data.substring(i)) != null) {
				while (data.charAt(i) == ' ')
					i++;
				f.importe = getNumber(data.substring(i));
				if (f.importe != null) {
					i += f.importe.length();
				}
			}
			// f.addTax(tipo,imp);
			while (data.charAt(i) == ' ')
				i++;

		}

	}

	private void consumeS() throws Exception {
		// tomo S como un impuesto
		if (((data.substring(i, i + 1).equals("S")) && getNumber(data.substring(i + 1)) != null) || (((data.substring(i, i + 2).equals("1S")) || (data.substring(i, i + 2).equals("2S")) && getNumber(data.substring(i + 2)) != null))) {
			if (data.substring(i, i + 2).equals("2S"))
				i += 2;
			if (data.substring(i, i + 2).equals("1S"))
				i += 2;
			if (data.substring(i, i + 1).equals("S"))
				i += 1;
			find = true;
			String imp = getNumber(data.substring(i));
			i += imp.length();
			impuesto += Double.parseDouble(imp);
		}

	}

	private void consumeImporte() throws Exception {
		// leo importe de la operacion
		// if (data.substring(i,i+1).equals(" "))i+=1;
		while (data.charAt(i) == ' ')
			i++;
		// if (getNumber(data.substring(i+6))!=null &&
		// data.substring(i,i+6).indexOf(" ")==-1 &&
		// data.substring(i,i+6).indexOf(".")==-1)i+=6;
		// while (data.charAt(i)==' ') i++;
		if (data.length() > i + 6 && JTools.isOnlyLettersWithinSpaces(data.substring(i, i + 6), false))
			i += 6;
		if (data.substring(i).startsWith("USD")) i+=3;
		String importe = getNumber(data.substring(i));
		if (importe != null) {
			find = true;
			f.importe = importe;
			i += f.importe.length();
			while (data.length()>=i && data.charAt(i) == ' ')
				i++;
			if (data.length() > i + 8 && JTools.isOnlyLettersWithinSpaces(data.substring(i, i + 8), false)) {
				i += 8;
				if (JTools.isOnlyLettersWithinSpaces(data.substring(i + 1, i + 4), false) && data.substring(i, i + 5).startsWith("/"))
					i += 5;
			}
			if (data.length() > i + 7 && JTools.isOnlyLettersWithinSpaces(data.substring(i, i + 7), false))
				i += 7;
			if (data.length() > i + 6 && JTools.isOnlyLettersWithinSpaces(data.substring(i, i + 6), false))
				i += 6;
		}
		while (data.charAt(i) == ' ')
			i++;

	}

	private void consumeD() throws Exception {
		while (data.charAt(i) == 'D' && data.charAt(i + 1) == ' ') {
			find = true;
			String tipo = "";
			String imp = "";
			i++;
			while (data.charAt(i) == ' ')
				i++;

			if (JTools.isOnlyLettersWithinSpaces(data.substring(i, i + 6), false)) {
				tipo = data.substring(i, i + 6);
				i += 6;
			} else if (JTools.isOnlyLetters(data.substring(i, i + 7), false)) {
				tipo = data.substring(i, i + 7);
				i += 7;
			}
			if (getNumber(data.substring(i)) != null) {
				imp = getNumber(data.substring(i));
				i += imp.length();
				f.importe = "" + (Double.parseDouble(imp) + Double.parseDouble(f.importe));
			}
			while (data.charAt(i) == ' ')
				i++;

		}

	}

	public String getNumber(String data) {
		int pos = data.indexOf(".");
		if (pos == -1) {
			String n = "";
			int j = 0;
			while (data.length() > j && JTools.isNumberPure("" + data.charAt(j)))
				n += data.charAt(j++);
			return j == 0 || j == 1 ? null : n;
		}
		if (data.length() < pos + 2)
			return null;
		if (!JTools.isNumber(data.substring(0, pos + 3)))
			return null;
		return data.substring(0, pos + 3);
	}

	public String analizeExtraFareDataMode2(String data) {
		String sobrante = data;
		int i = 0;
		String destino;
		String origen;
		if (data.substring(i).charAt(i) == ' ')
			i++;
		if (data.substring(i).charAt(i) == ' ')
			i++;
		int ln = 1;
		FareRecord f = new FareRecord();
		origen = f.airport_from = data.substring(i + 1, i + 4);
		i += 4;

		while (i < data.length()) {
			while (data.charAt(i) == 'X' && data.charAt(i + 1) == '/') {
				destino = data.substring(i + 2, i + 5);
				i += 5;
				if (destino.startsWith("E/"))
					destino = destino.substring(2);

				f.addEscala(origen, "", destino);
				origen = destino;
			}
			destino = f.airport_to = data.substring(i, i + 3);
			i += 3;
			if (destino.equals("NUC") || destino.equals("USD") || destino.equals("EUR")) {// no
																																										// encuentro
																																										// otra
																																										// manera
																																										// de
				// salir
				if (getNumber(data.substring(i)) != null) {
					moneda = destino;
					total = getNumber(data.substring(i));
					i += total.length();
					sobrante = data.substring(i);
					break;

				}
			}

			f.addEscala(origen, "", destino);

			double impuesto = 0;
			if (i>=data.length()) 
				break;
			while ( data.charAt(i) == 'Q') {
				String tipo = "";
				String imp = "";
				i++;
				if (getNumber(data.substring(i)) == null) {
					tipo = data.substring(i, i + 6);
					i += 6;
				}
				if (getNumber(data.substring(i)) != null) {
					imp = getNumber(data.substring(i));
					i += imp.length();
					impuesto += Double.parseDouble(imp);
				}
				f.addTax(tipo, imp);
			}

			if (data.substring(i, i + 3).equals("25M") || data.substring(i, i + 3).equals("20M") || data.substring(i, i + 3).equals("15M") || data.substring(i, i + 3).equals("10M"))
				i += 3;
			if (data.substring(i, i + 2).equals("5M"))
				i += 2;
			if (data.substring(i, i + 1).equals("M"))
				i += 1;

			f.impuesto = impuesto;
			f.importe = getNumber(data.substring(i));
			i += f.importe==null?0:f.importe.length();
			f.id = ln;
			mFareFields.addElement("" + (ln++), f);
			f = new FareRecord();
			if (i>=data.length()) 
				break;
			if (data.charAt(i) == '/' && data.charAt(i + 1) == '-') {
				origen = f.airport_from = data.substring(i + 2, i + 5);
				i += 5;
			} else {
				origen = f.airport_from = destino;
			}

		}

		return sobrante;
	}

}
