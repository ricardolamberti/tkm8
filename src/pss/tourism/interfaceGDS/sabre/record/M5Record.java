package pss.tourism.interfaceGDS.sabre.record;

import java.util.StringTokenizer;

import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.tourism.interfaceGDS.Tools;

public class M5Record extends SabreRecord {

	public static final String MESSAGE_ID = "IU5MID";
	public static final String M5_ACCOUNTING_ITEM_NUMBER = "IU5PTY";
	public static final String INTERFACE_NAME_ITEM_NUMBER = "IU5MIN";
	public static final String ACCOUNTING_DATA_LINE = "IU5VR1";
	public static final String MFLAG = "IU5VR1_0";
	public static final String CARRIER_CODE = "IU5VR1_1";
	public static final String TICKET_NUMBER = "IU5VR1_2";
	public static final String IU5VR1_3 = "IU5VR1_3";
	public static final String COMISION_AMOUNT = "IU5VR1_4";
	public static final String IU5VR1_5 = "IU5VR1_5";
	public static final String MON_AMOUNT = "IU5VR1_6b";
	public static final String NET_AMOUNT = "IU5VR1_6";
	public static final String IU5VR1_7 = "IU5VR1_7";
	public static final String TAX_AMOUNT = "IU5VR1_8";
	public static final String TAX_LOCAL = "IU5IVA";
	public static final String IU5VR1_9 = "IU5VR1_9";
	public static final String OTHERTAX_AMOUNT = "IU5VR1_X_";
	public static final String IS_ONE = "IU5VR1_10";
	public static final String IU5VR1_11 = "IU5VR1_11";
	public static final String FORM_OF_PAYMENT = "IU5VR1_12";
	public static final String CARD_CODE = "IU5VR1_13";
	public static final String CREDIT_CARD_NUMBER = "IU5VR1_14";
	public static final String PAX_DATA = "IU5VR1_15";
	public static final String E_DATA = "IU5VR1_16";
	public static final String E_MERCADO = "IU5VR1_17";
	public static final String PAXID = "IU5VR1_PAXID";

	public static int idx = 1;
	public static String last = null;
	public boolean voucher =false;
	public static JMap<String, Integer> aPaxs = null;

	public static void initM5() throws Exception {
		idx = 1;
		aPaxs = JCollectionFactory.createMap();
	}
	
	public boolean hasVoucher() {
		return voucher;
	}

	public M5Record() {
		ID = SabreRecord.M5;
	}

	// ==========================================================================
	// M5 Record - Passenger Accounting Data Record
	// ==========================================================================
	// This record is the accounting record. There is one M5 per accounting data
	// line in the
	// PNR. If specific accounting lines are not selected, the record will
	// contain
	// all accounting
	// lines present in the PNR. The record will contain accounting lines on a 1
	// to 1 match with
	// name(s).
	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {
		boolean bus=false;
		voucher=false;

		int i = 2;
		addFieldValue(MESSAGE_ID, ID); // MESSAGE ID
		addFieldValue(M5_ACCOUNTING_ITEM_NUMBER, line.substring(i, i += 2));
		addFieldValue(INTERFACE_NAME_ITEM_NUMBER, line.substring(i, i += 2));
		addFieldValue(ACCOUNTING_DATA_LINE, line.substring(i));
		i++; // skip 1
		addFieldValue(MFLAG, line.substring(i, i += 1));
		if ( line.substring(i+7, i+10).equals("VCH") ) { //Voucher
			i=line.indexOf("/",i);
			i=line.indexOf("/",i+1);
			voucher=true;
		
		} else if ( line.substring(i, i+6).equals("VOYBUS") ) {
			bus=true;
			addFieldValue(CARRIER_CODE, line.substring(i, i += 6));
			addFieldValue(TICKET_NUMBER, line.substring(i+4, i += 10));
		} else if ( line.substring(i, i+6).equals("CAMION") ) {
			addFieldValue(CARRIER_CODE, "CAMION");
		} else if ( line.substring(i, i+6).equals("HOTELS") ) {
			addFieldValue(CARRIER_CODE, "CAMION");
		} else {	
		  addFieldValue(CARRIER_CODE, line.substring(i, i += 3));
			addFieldValue(TICKET_NUMBER, line.substring(i, i += 10));
		}

		
		String addinfo = line.substring(i + 1);

		StringTokenizer st = new StringTokenizer(addinfo, "/");

		String comision = st.nextToken();
		// cada tanto vienen numeros gigantes sin punto, que no se que son, pero no es el precio
		addFieldValue(COMISION_AMOUNT, comision.indexOf(".")==-1?(comision.trim().length()>4?"0":Tools.getOnlyNumbers(comision)):comision);
		// addFieldValue(IU5VR1_5,line.substring(i,i+=1));
		String importe = st.nextToken();
		if (importe.length()>3 && !importe.substring(0,3).trim().equals("")) {
			addFieldValue(MON_AMOUNT, JTools.isOnlyLettersWithinSpaces(importe.substring(0,3), false)?importe.substring(0,3):"");
		}
		addFieldValue(NET_AMOUNT,  importe.indexOf(".")==-1?Tools.getOnlyNumbers(importe):Tools.getOnlyNumbers(importe));
		// addFieldValue(IU5VR1_7,line.substring(i,i+=1));
		String tax =st.nextToken();
		addFieldValue(TAX_AMOUNT,  tax.indexOf(".")==-1?(tax.trim().length()>7?"0":Tools.getOnlyNumbers(tax.trim())):Tools.getOnlyNumbers(tax.trim()));
		// addFieldValue(IU5VR1_9,line.substring(i,i+=1));
		if (!st.hasMoreTokens())
			return line;
		String isone = st.nextToken();
		int t=1;
		String iva = null; // el primero con D es el iva
		while (isone!=null && !isone.equals("ONE")&& !isone.equals("PER")&& !isone.equals("ALL")) {
			if (isone.startsWith("D")) {
				if (iva==null) {
					iva = Tools.getOnlyNumbers(isone);
					addFieldValue(TAX_LOCAL,iva );
				}
				else
					addFieldValue(OTHERTAX_AMOUNT+(t++), Tools.getOnlyNumbers(isone));
			}
			if (!st.hasMoreTokens()) break;
			isone = st.nextToken();
		}
		addFieldValue(IS_ONE, isone);
		// addFieldValue(IU5VR1_11,line.substring(i,i+=1));
		if (!st.hasMoreTokens()) return line;
		String paxdata = st.nextToken();
		int j = 0;
		addFieldValue(FORM_OF_PAYMENT, paxdata.substring(j, j = +2));

		if ((getFormOfPayment().equals("CC")||getFormOfPayment().equals("CX"))&&paxdata.length()>2) {
			addFieldValue(CARD_CODE, paxdata.substring(j, j += 2));
			int posFin=paxdata.indexOf(" ",j);
			String creditcard = "";
			if (posFin>=j) {
				creditcard = paxdata.substring(j, posFin);
				j=posFin;
				addFieldValue(CREDIT_CARD_NUMBER, creditcard);
				addFieldValue(PAX_DATA, getPaxData(paxdata.substring(j)));
			} else {
				creditcard = paxdata.substring(j);
				addFieldValue(CREDIT_CARD_NUMBER, creditcard);
			}
		} else {
			addFieldValue(CARD_CODE, "");
			addFieldValue(CREDIT_CARD_NUMBER, "");
			
			addFieldValue(PAX_DATA, getPaxData(paxdata.substring(j)));
		}

		if (!st.hasMoreTokens())
			return line;
		String tok =st.nextToken(); // 1
		if (!st.hasMoreTokens())
			return line;
		String mercado = st.nextToken();
		if (mercado.indexOf("EMD")!=-1)
			this.addFieldValue(E_DATA, "EMD");
			
		this.addFieldValue(E_MERCADO, mercado);
		if (!st.hasMoreTokens())
			return line;
		this.addFieldValue(E_DATA, st.nextToken());
		
		if (!st.hasMoreTokens())
			return line;

		return line;

	}

	private String getPaxData(String data) {
		try {
			data = data.trim();
			String paxid = data.substring(0, data.indexOf('.'));
			
			int id = Integer.parseInt(paxid);
			Integer ii = aPaxs.getElement(paxid);
			if (ii != null) {
//				ii++;
//				paxid = paxid+ii;
//				data = paxid+data.substring(3);
//				aPaxs.addElement(paxid, ii);
			} else {
				aPaxs.addElement(paxid, id);
			}
			this.addFieldValue(PAXID, paxid);
		} catch (Exception ignored) {
			this.addFieldValue(PAXID, "99");
		}
		return data;
	}

	public String getCarrierCode() throws Exception {
		return this.getFieldValue(CARRIER_CODE).replace('#', ' ').replace('/', ' ').trim();
	}
	
	public boolean isCash() throws Exception {
		return this.getFieldValue(FORM_OF_PAYMENT).equals("CA");
	}
	public boolean isTarjeta() throws Exception {
		return !this.isCash();
	}


	private String getFormOfPayment() throws Exception {
		return this.getFieldValue(FORM_OF_PAYMENT);
	}

	public String getFormOfPaymentDesc() throws Exception {
		if (this.getFieldValue(FORM_OF_PAYMENT).equals("CA"))
			return "CASH";
		return  this.getFieldValue(CARD_CODE);
	}

	public boolean hasTicketNumber() throws Exception {
		return this.getTicketNumber()!=null && !this.getTicketNumber().equals("");
	}

	public String getTicketNumber() throws Exception {
		return this.getFieldValue(TICKET_NUMBER);
	}
	public String getMercado() throws Exception {
		if (this.getFieldValue(E_MERCADO)==null)
			return "D";
		return this.getFieldValue(E_MERCADO).equals("D")?"D":"I";
	}

	public double getAmount() throws Exception {
		return JTools.rd(Double.parseDouble(this.getFieldValue(NET_AMOUNT))
				+ getTaxLocal() 
				+ Double.parseDouble(this.getFieldValue(TAX_AMOUNT)) 
				+ getOtherTaxAmount());
	}

	public double getSaleAmount() throws Exception {
		return JTools.rd(this.getNetAmount() + this.getTaxLocal()+ this.getTaxAmount()+this.getOtherTaxAmount());
	}

	public double getNetAmount() throws Exception {
		return Double.parseDouble(this.getFieldValue(NET_AMOUNT));
	}
	public String getCurrency() throws Exception {
		String  value=this.getFieldValue(MON_AMOUNT);
		if (value==null || value.trim().equals("")) return null;
		if (!JTools.isOnlyLetters(value, false)) return null;
		return value;
	}

	public double getComisionPerc() throws Exception {
		String  value=this.getFieldValue(COMISION_AMOUNT);
		if (!value.trim().startsWith("P")) return 0;
		return Double.parseDouble(Tools.getOnlyNumbers(value));
	}
	public double getComisionAmount() throws Exception {
		String  value=this.getFieldValue(COMISION_AMOUNT);
		if (value.trim().startsWith("P")) return 0;
		return Double.parseDouble(Tools.getOnlyNumbers(value));
	}

	public double getTaxAmount() throws Exception {
		return Double.parseDouble(this.getFieldValue(TAX_AMOUNT));
	}
	public double getTaxLocal() throws Exception {
		if (this.getFieldValue(TAX_LOCAL)==null) return 0;
		return Double.parseDouble(this.getFieldValue(TAX_LOCAL));
	}
	public Double getOtherTax(int tax) throws Exception {
		String val = this.getFieldValue(OTHERTAX_AMOUNT+(tax++));
		if (val==null) return null;
		return Double.parseDouble(val);
		
	}
	public double getOtherTaxAmount() throws Exception {
		int t=1;
		double acum=0;
		while (true) {
			String val = this.getFieldValue(OTHERTAX_AMOUNT+(t++));
			if (val==null) break;
			acum+=Double.parseDouble(val);
		}
		return acum;
	}
	public String getCreditCardNumber() throws Exception {
		return this.getFieldValue(CREDIT_CARD_NUMBER);
	}
	
	public String getPaxIdInt() { 
		String id = this.getFieldValue(INTERFACE_NAME_ITEM_NUMBER) ;
		if (id.equals("@@")) return "@@";
		if (id.equals("@")) return "@@";
		if (id.equals("")) return "01";
		if (id.equals("AA")) return "AA";
	  return Integer.parseInt( this.getFieldValue(INTERFACE_NAME_ITEM_NUMBER) ) + "";
	}


	public String getPaxId() {
		String id = this.getFieldValue(PAXID);
		return id;
	}

	public boolean hasMFlag() {
		String id = this.getFieldValue(MFLAG);
		return "M".equals(id);
	}

	public boolean isRemision() {
		String data = this.getFieldValue(E_DATA);
		if (data == null)
			return false;
		return data.indexOf("-@") != -1;
	}

	public boolean isOne() {
		return this.getFieldValue(M5Record.IS_ONE).equals("ONE");
	}
	public boolean isEMD() {
		if (this.getFieldValue(M5Record.E_DATA)==null) return false;
		return this.getFieldValue(M5Record.E_DATA).startsWith("EMD");
	}
	public String getTipoOperacion() {
		if (isEMD()) 
			return "EMD";
		return "ETR";
	}

}
