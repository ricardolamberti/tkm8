package pss.tourism.interfaceGDS.sabre.record;

import java.util.Date;

import com.ibm.icu.util.StringTokenizer;

import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;
import pss.tourism.interfaceGDS.Tools;

public class M8Record extends SabreRecord {

	public static final String MESSAGE_ID = "IU8MID";
	public static final String REMARK_NUMBER = "IU8RKN";
	public static final String REMARK_TEXT = "IU8RMK";
	public static final String IU8RCC = "IU8RCC";
	public static final String IU8RCV = "IU8RCV";
	public static final String IU8ROG = "IU8ROG";
	public static final String IU8ROC = "IU8ROC";
	public static final String IU8RTK = "IU8RTK";
	public static final String IU8RST = "IU8RST";
	public static final String IU8ROB = "IU8ROB";

	private static final String MONEDA = "MONEDA";
	private static final String TAX = "TAX";
	private static final String TAX2 = "TAX-";
	private static final String PAX = "PAX";
	private static final String FEE1 = "FEE ";
	private static final String FEE2 = "FEE-";
	private static final String TARIFA1= "TR ";
	private static final String FEE3 = "FP ";
	private static final String EXP1 = "EXP ";
	private static final String EXP2 = "EXP-";
	private static final String GASTOS1 = "GASTOS ";
	private static final String GASTOS2 = "GASTOS-";
	private static final String IMPVTA1 = "IMP VTA";
	private static final String TAXXT1 = "TAX XT";
	private static final String TAXQN1 = "TAX QN";
	private static final String TAXDL1 = "TAX DL";
	private static final String DIFXT = "DIF XT";
	private static final String PEN = "PEN";
	private static final String COMMISSION1 = "CO ";
	private static final String COMMISSION3 = "CG ";
	private static final String CUSTOMERID1 = "CC ";
	private static final String COSTCENTER1 = "ST ";
	private static final String CREDITCARD1 = "TC ";
	private static final String CREDITCARD2 = "TC-";
	private static final String CREDITCARD_SEG1 = "TC SEG";
	private static final String CREDITCARD_SEG2 = "TC-SEG";
	private static final String CREDITCARD_AUTH1 = "TC AUTH";
	private static final String CREDITCARD_AUTH2 = "TC-AUTH";
	private static final String CONSUMO = "CO2*";

	private static final String OBSERVATION1 = "OB ";
	private static final String OVERGAIN1 = "OG ";
	private static final String OVERCEDIDO1 = "OC ";
	private static final String TICKETNUMBER1 = "TK ";
	private static final String VENDOR1 = "CV ";
	private static final String IMPVTA2 = "IMP-VTA";
	private static final String TAXXT2 = "TAX-XT";
	private static final String TAXQN2 = "TAX-QN";
	private static final String TAXDL2 = "TAX-DL";
	private static final String TARIFA2 = "TR-";
	private static final String COMMISSION2 = "CG-";
	private static final String CUSTOMERID2 = "CC-";
	private static final String COSTCENTER2 = "ST-";
	private static final String OBSERVATION2 = "OB-";
	private static final String OVERGAIN2 = "OG-";
	private static final String OVERCEDIDO2 = "OC-";
	private static final String TICKETNUMBER2 = "TK-";
	private static final String VENDOR2 = "CV-";
	private static final String VENDOR3 = "TKT*";
	private static final String CABOTAJE = "TIPO TKT CABOTAJE";

	private static final String FEE5 = "RM*FFA";
	private static final String FEE4 = "RII*FFA";

//Centro de costo TKMST
//Cliente	        TKMCC
//vendedor	TKMCV
//precio ref      TKMPR
//Sucursal        TKMSC
//Over ganado     TKMOG
//Over cedido     TKMOC
//Observacion     TKMOB
//
//ejemplo de m8 (_____ representa la info)
//M801TKMCC-________/TKMCV-________
//M802TKMSC-_____/TKMPR-________/TKMST-__________

	

	
	private static final String CUENTATKM= "TKM*CT-";
	private static final String HFARETKM= "TKM*HF-";
	private static final String LFARETKM= "TKM*LF-";
	private static final String CORPTKM= "TKM*CR-";
	private static final String SOLICTKM= "TKM*AU-";
	private static final String FSTKM= "TKM*FS-";
	private static final String CLASETKM= "TKM*CL-";
	private static final String PROPOSITOTKM= "TKM*PP-";
	private static final String FECHARESERVATKM= "TKM*FR-";
	private static final String COSTCENTERTKM= "TKM*ST-";
	private static final String CUSTOMERIDTKM = "TKM*CC-";
	private static final String VENDORTKM = "TKM*CV-";
	private static final String PRECIOREFTKM = "TKM*PR-";
	private static final String SUCURSALTKM = "TKM*SC-";
	private static final String OVERGAINTKM = "TKM*OG-";
	private static final String OVERCEDIDOTKM = "TKM*OC-";
	private static final String OBSERVACIONTKM = "TKM*OB-";
	private static final String TOURCODETKM = "TKM*TC-";
	private static final String CODIGONEGOCIOTKM = "TKM*CN-";
	private static final String REGIONTKM = "TKM*RG-";
	private static final String DEPARTAMENTOTKM = "TKM*DP-";
	private static final String AUTORIZTKM = "TKM*AUCC-";
	private static final String AUTORIZSEGTKM = "TKM*SGCC-";
	private static final String FEETKM = "TKM*FEE-";
	private static final String CONSUMOTKM = "TKM*CO2-";
	private static final String CUSTOMER = "R/RUC";
	private static final String EMPLOYEE = "U1-";
	private static final String REASONCODE = "U2-";
	private static final String REASONCODEHOTEL = "U3-";
	private static final String COSTCENTER = "U6-";
	private static final String EMAIL = "U7-";
	private static final String FECHACREATE = "U8-";
	private static final String AV = "U9-";
	private static final String FECHAAUTH = "U10-";
	private static final String BUSINESSGROUP = "U11-";

	private static final String CUENTATKM2= "TKMCT-";
	private static final String HFARETKM2= "TKMHF-";
	private static final String LFARETKM2= "TKMLF-";
	private static final String CORPTKM2= "TKMCR-";
	private static final String SOLICTKM2= "TKMAU-";
	private static final String FSTKM2= "TKMFS-";
	private static final String CLASETKM2= "TKMCL-";
	private static final String PROPOSITOTKM2= "TKMPP-";
	private static final String FECHARESERVATKM2= "TKMFR-";
	private static final String COSTCENTERTKM2= "TKMST-";
	private static final String CUSTOMERIDTKM2 = "TKMCC-";
	private static final String VENDORTKM2 = "TKMCV-";
	private static final String PRECIOREFTKM2 = "TKMPR-";
	private static final String SUCURSALTKM2 = "TKMSC-";
	private static final String OVERGAINTKM2 = "TKMOG-";
	private static final String OVERCEDIDOTKM2 = "TKMOC-";
	private static final String OBSERVACIONTKM2 = "TKMOB-";
	private static final String TOURCODETKM2 = "TKMTC-";
	private static final String CODIGONEGOCIOTKM2 = "TKMCN-";
	private static final String REGIONTKM2 = "TKMRG-";
	private static final String DEPARTAMENTOTKM2 = "TKMDP-";
	private static final String AUTORIZTKM2 = "TKMAUCC-";
	private static final String AUTORIZSEGTKM2 = "TKMSGCC-";
	private static final String FEETKM2 = "TKMFEE-";

	// compatibilidad
	//5.Z*HF-high fare                                                       
	//5.Z*LF-low fare                                                       
	//5.Z*COR- y o n     (uso convenio corporativo)  yes or no                                               
	//5.Z*CLAS- e o b   (clase de tarifa)                                               
	//5.Z*AUT- quien solicita                                                    
	//5.Z*U1-centro costo                                                       
	//5.Z*U2-Número de cuenta                                                       
	//5.Z*U3-proposito del viaje                                                       
	//5.Z*U4-fecha en que se hizo reserva  (para determinar  días de precompra) 
	//5.Z*U5- FS   (fare savings)
	private static final String COSTCENTERCT= "Z*U1-";
	private static final String CUENTACT= "Z*U2-";
	private static final String PROPOSITOCT= "Z*U3-";
	private static final String FECHARESERVACT= "Z*U4-";
	private static final String FSCT= "Z*U5-";
	private static final String CORPORATIVOCT= "Z*COR-";
	private static final String CLASCT= "Z*CLAS-";
	private static final String HFARECT= "Z*HF-";
	private static final String LFARECT= "Z*LF-";
	private static final String SOLICCT= "Z*AUT-";
	private static final String HFARECT2= "HF-";
	private static final String LFARECT2= "LF-";
	
	private static final String COSTCENTERCT_M2= "Z*U6-";
	private static final String HFARECT_M2= "Z*U1-";
	private static final String LFARECT_M2= "Z*U2-";
	private static final String SOLICCT_M2= "Z*U3-";
	private static final String FECHARESERVACT_M2= "Z*U5-";
	private static final String CUENTACT_M2= "Z*U4-";

	
	private static final String CUSTOMERRM = "RM*ACECLN-";
	private static final String COSTCENTERTRM1 = "RM*ACECRM-CC:";
	private static final String COSTCENTERTRM2 = "RM* ZOOM/CC-";
	private static final String COSTCENTERTRM3 = "RM* ZOOM/CC1+";
	private static final String COSTCENTERTRM4 = "RM*CO-";
	private static final String COSTCENTERTRM5 = "RM*CC";
	private static final String COSTCENTERTRM6 = "RM*CC-";
	private static final String COSTCENTERTRM7 = "RM*CO";
	private static final String COSTCENTERTRM8 = "RM*ZOOM/CC2+";
	private static final String COSTCENTERTRM9 = "RII*CC2-";
	private static final String COSTCENTERTRM10 = "RM*CC2-";

	private static final String VENDORRM1 = "RM*/SAP/PID-";
	private static final String VENDORRM2 = "RM*EN-";
	private static final String VENDORRM3 = "RII*EN-";
	private static final String VENDORRM4 = "RM*NE-";

	private static final String SOLICRM1= "RII*RQ-";
	private static final String SOLICRM2= "Rm*RQ-";
	private static final String SOLICRM3= "RM*RQ-";

	private static final String TOURCODERM1= "RII*TC-";
	private static final String TOURCODERM2= "RM*TC-";

	private static final String LFARERM1= "RM*ACESV1-";
	private static final String LFARERM2= "RM*LF-";
	private static final String LFARERM3= "RII*LF-";
	private static final String LFARERM4= "RM*LF";
	private static final String LFARERM5= "RMLF";
	private static final String LFARERM6	= "RIILF";

	private static final String HFARERM1= "RM*ACESV2-";
	private static final String HFARERM2= "RM*FF-";
	private static final String HFARERM3= "RII*FF-";
	private static final String HFARERM4= "RM*FF";
	private static final String HFARERM5= "RMFF";
	private static final String HFARERM6	= "RIIFF";

	
	private static final String CLASEM1= "RM*ACESV2-";
	private static final String CLASEM2= "RM*TT";

	private static final String AHORRORM1= "RM*SVID1-1";
	private static final String AHORRORM2= "RII*SVID1-";
	private static final String AHORRORM3= "RM*SVID-";
	private static final String AHORRORM4= "RII*SVID-";

	private static final String AHORRORM5= "RM*SVID2-";
	private static final String AHORRORM6= "RII*SVID2-";

	private static final String OBSERVACIONRM1 = "RM*FFA-";
	private static final String OBSERVACIONRM2 = "RII*FFA-";
	private static final String OBSERVACIONRM3 = "RM*FFB-";
	private static final String OBSERVACIONRM4 = "RII*FFB-";

	private static final String OBSERVACIONRM5 = "RM*FFC-";
	private static final String OBSERVACIONRM6 = "RII*FFC-";
	private static final String CODIGONEGOCIORM1 = "RII*BG-";
	private static final String CODIGONEGOCIORM2 = "RM*BG-";

	private static final String REGIONRM1 = "RII*RG-";
	private static final String REGIONRM2 = "RM*RG-";

	private static final String DEPARTAMENTORM1 = "RII*DP-";
	private static final String DEPARTAMENTORM2 = "RM*DP-";

	public M8Record() {
		ID = SabreRecord.M8;
	}

	public static JMap<String, Integer> aPaxs = null;

	public static void initM8() throws Exception {
		aPaxs = JCollectionFactory.createMap();
	}

	// ==========================================================================
	// M8 Record - Passenger Invoice Data Record
	// ==========================================================================
	// The information contained in the M8 record is only created if the TJR
	// option is
	// set to the ON position AND if the remarks section of the PNR contains
	// remarks
	// items that are entered with a "5."
	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		int i = 2;
		addFieldValue(MESSAGE_ID, ID); // MESSAGE ID
		addFieldValue(REMARK_NUMBER, line.substring(i, i += 2));

		int rmi = getNumberOfRemarkItems(mRecords);

		String remarkData = "";
		for (int index = 1; index <= rmi; index++) {
			if (index > 1) {
				line="";
				while(line!=null&&line.equals("")) line = getInput().readLine();
				i = 4;
			}
			if (line==null||line.startsWith("M9")||line.startsWith("ME")||line.startsWith("MX")) // muchas veces viene mal la cantidad de m8s
				break;
			if (line.indexOf("PAX") > 0) {
				remarkData += getPaxData(line.substring(i)) + "|";
			} else {
				if (line.length()>i)
					remarkData += line.substring(i) + "|";
			}
		}
		addFieldValue(REMARK_TEXT, remarkData);
		analizeRemarkItems(remarkData);
		return line;

	}

	private String getPaxData(String data) {
		try {
			String paxid = data.substring(4, 7);
			int id = Integer.parseInt(data.substring(6, 7));
			Integer ii = aPaxs.getElement(paxid);
			if (ii != null) {
				ii++;
				data = "PAX" + paxid.substring(0,2) + ii ;

				aPaxs.addElement(paxid, ii);
			} else {
				aPaxs.addElement(paxid, id);
			}
		} catch (Exception ignored) {
		}
		return data;
	}

	private int getNumberOfRemarkItems(JMap<String, Object> mRecords) {
		return ((M0Record) mRecords.getElement(SabreRecord.M0)).getNumberOfM8();
	}

	private void analizeRemarkItems(String remarkData) {
	}

	public String getRemarkItems() {
		return this.getFieldValue(REMARK_TEXT);
	}

	public String getServiceFee() throws Exception {
		

		String s;
		s = searchCode(getRemarkItems(), FEETKM, "");
		if (!s.equals(""))
			s = searchCode(getRemarkItems(), FEETKM2, "");
		if (!s.equals(""))
			s = searchCode(getRemarkItems(), "RM*FFA-", "");
		if (!s.equals(""))
			s = searchCode(getRemarkItems(), FEE5, "");
		if (!s.equals(""))
			s = searchCode(getRemarkItems(), FEE4, "");
		if (!s.equals(""))
			s = searchCode(getRemarkItems(), FEE1, "");
		if (!s.equals(""))
			s = searchCode(getRemarkItems(), FEE2, "");
		if (!s.equals(""))
			s = searchCode(getRemarkItems(), FEE3, "");
		
		return s;
	}
	


	private String searchCode(String search, String code, String codedefault) {
		if (search.equals(""))
			return codedefault;
		StringTokenizer toks = new StringTokenizer(search, "|/");
		while (toks.hasMoreTokens()) {
			String tok = toks.nextToken();
			if (tok.startsWith(code)) return tok.substring(code.length()).trim();
		}
		return codedefault;
	}
	private String searchCodeLine(String search, String code, String codedefault) {
		if (search.equals(""))
			return codedefault;
		StringTokenizer toks = new StringTokenizer(search, "|");
		while (toks.hasMoreTokens()) {
			String tok = toks.nextToken();
			if (tok.startsWith(code)) return tok.substring(code.length()).trim();
		}
		return codedefault;
	}
	

	public boolean isRemision(String paxinfo) {
		if (paxinfo == null)
			return false;
		return paxinfo.indexOf(DIFXT) != -1;
	}

	public String getCustomerId() {
		String s = searchCode(getRemarkItems(), CUSTOMERIDTKM, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), CUSTOMERIDTKM2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), CUSTOMERRM, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), CUSTOMERID2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), CUSTOMERID1, "");
		return s;
	}
	public String getCustomer() {
		String s = searchCodeLine(getRemarkItems(), CUSTOMER, "");
		StringTokenizer ss = new StringTokenizer(s,"-");
		String s1 = ss.hasMoreTokens()?ss.nextToken():"";
		String s2 = ss.hasMoreTokens()?ss.nextToken():"";
		if (s2.equals("")) s = s1.length()<13?s1:s1.substring(13);
		else s=s2;
		return s;
	}
	public String getEmployee() {
		String s = searchCode(getRemarkItems(), EMPLOYEE, "");
		return s;
	}
	public String getEmailID() {
		String s = searchCode(getRemarkItems(), EMAIL, "");
		return s;
	}
	public String getReasonCode() {
		String s = searchCode(getRemarkItems(), REASONCODE, "");
		return s;
	}
	public String getResonCodeHotel() {
		String s = searchCode(getRemarkItems(), REASONCODEHOTEL, "");
		return s;
	}
	public String getAV() {
		String s = searchCode(getRemarkItems(), AV, "");
		return s;
	}
	public String getFechaCreate() {
		String s = searchCodeLine(getRemarkItems(), FECHACREATE, "");
		return s;
	}
	public String getFechaAuth() {
		String s = searchCodeLine(getRemarkItems(), FECHAAUTH, "");
		return s;
	}
	public String getBusinessGroup() {
		String s = searchCode(getRemarkItems(), BUSINESSGROUP, "");
		return s;
	}

	public String getCuenta() {
		String s = searchCode(getRemarkItems(), CUENTATKM, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), CUENTATKM2, "");
		if (s.equals("")) {
			if (clarkTourModo1())
				s = searchCode(getRemarkItems(), CUENTACT, "");
			else
				s = searchCode(getRemarkItems(), CUENTACT_M2, "");

		}
		return s;
	}
	public String getProposito() {
		String s = searchCode(getRemarkItems(), PROPOSITOTKM, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), PROPOSITOTKM2, "");
		if (s.equals(""))
			s = searchCodeLine(getRemarkItems(), PROPOSITOCT, "");
		return s;
	}
	
	public Date getFechaReserva(long anio) throws Exception {
		String s = searchCode(getRemarkItems(), FECHARESERVATKM, "");
		if (s.equals("")) 
			s = searchCode(getRemarkItems(), FECHARESERVATKM2, "");
		if (s.equals("")) {
			if (clarkTourModo1())
				s = searchCode(getRemarkItems(), FECHARESERVACT, "");
			else
				s = searchCode(getRemarkItems(), FECHARESERVACT_M2, "");

		}
		if (s.equals("")) return null;
		if (s.length()==5) { //20MAR
			try {
				s= Tools.convertDateMonthStringToNumber(s,anio);
			} catch (Exception e) {
				return null;
			}
		}
		if (s.length()==7) { //20MAR07
			try {
				s= Tools.convertDateMonthStringToNumberShort(s);
			} catch (Exception e) {
				return null;
			}
		}
		if (s.length()==9) { //20MAR2007
			try {
				s= Tools.convertDateMonthStringToNumber(s);
			} catch (Exception e) {
				return null;
			}
		}
		try {
			return JDateTools.StringToDate(s, "ddMMyyyy");
		} catch (Exception e) {
			return null;
		}
	}

	public boolean clarkTourModo1() {
		String s = searchCode(getRemarkItems(), FSCT, "");
		if (s.length()!=7) return true;
		if (!JTools.isNumber(s.substring(0, 2))) return true;
		if (!JTools.isAlpha(s.substring(3, 5))) return true;
		return false;
	}
	
	public String getFareSavings() {
		String s = searchCode(getRemarkItems(), FSTKM, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), FSTKM2, "");
		if (s.equals("")) {
			if (clarkTourModo1())
				s = searchCode(getRemarkItems(), FSCT, "");
		}
		if (s.equals(""))
			s = searchCode(getRemarkItems(), AHORRORM1, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), AHORRORM2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), AHORRORM3, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), AHORRORM4, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), AHORRORM5, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), AHORRORM6, "");
		return s;
	}
	public Boolean getCorporativo() {
		String s = searchCode(getRemarkItems(), CORPTKM, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), CORPTKM2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), CORPORATIVOCT, "");
		return s.equals("")?null:s.equalsIgnoreCase("s")||s.equalsIgnoreCase("y")?true:false;
	}
	public String getClase() {
		String s = searchCode(getRemarkItems(), CLASETKM, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), CLASETKM2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), CLASCT, "");
		if (s.equals("")) {
			s = searchCode(getRemarkItems(), CLASEM1, "");//RM*ACESV2-850.60-RT
			int pos = s.indexOf("-");
			if (pos!=-1) {
				s = s.substring(pos+1);
			}
		}
		if (s.equals(""))
			s = searchCode(getRemarkItems(), CLASEM2, "");
		return s;
	}
	public String getSolicitante() {
		String s = searchCode(getRemarkItems(), SOLICTKM, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), SOLICTKM2, "");
		if (s.equals("")) {
			if (clarkTourModo1())
				s = searchCode(getRemarkItems(), SOLICCT, "");
			else
				s = searchCode(getRemarkItems(), SOLICCT_M2, "");

		}
		if (s.equals(""))
			s = searchCode(getRemarkItems(), SOLICRM1, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), SOLICRM2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), SOLICRM3, "");
		return s;
	}
	public double getHighFare() throws Exception {
		String s = searchCode(getRemarkItems(), HFARETKM, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), HFARETKM2, "");
		if (s.equals("")) {
			if (clarkTourModo1())
				s = searchCode(getRemarkItems(), HFARECT, "");
			else
				s = searchCode(getRemarkItems(), HFARECT_M2, "");
		}
		if (s.equals(""))
			s = searchCode(getRemarkItems(), HFARECT2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), HFARERM1, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), HFARERM2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), HFARERM3, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), HFARERM4, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), HFARERM5, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), HFARERM6, "");
		return s.equals("")?0:Double.parseDouble(Tools.getOnlyNumbers(s).equals("")?"0":JTools.replace(Tools.getOnlyNumbers(s),"..","."));
	}
	public double getLowFare() throws Exception {
		String s = searchCode(getRemarkItems(), LFARETKM, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), LFARETKM2, "");
		if (s.equals("")) {
			if (clarkTourModo1())
				s = searchCode(getRemarkItems(), LFARECT, "");
			else
				s = searchCode(getRemarkItems(), LFARECT_M2, "");
		}
		if (s.equals(""))
			s = searchCode(getRemarkItems(), LFARECT2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), LFARERM1, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), LFARERM2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), LFARERM3, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), LFARERM4, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), LFARERM5, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), LFARERM6, "");
		return s.equals("")?0:Double.parseDouble(Tools.getOnlyNumbers(s).equals("")?"0":JTools.replace(Tools.getOnlyNumbers(s),"..","."));
	}
	public String getTourCode() throws Exception {
		String s = searchCode(getRemarkItems(), TOURCODETKM, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), TOURCODETKM2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), TOURCODERM1, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), TOURCODERM2, "");
		return s;
	}
	public String getCostCener() {
		String s = searchCode(getRemarkItems(), COSTCENTERTKM, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), COSTCENTERTKM2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), COSTCENTER, "");
		if (s.equals("")) {
			if (clarkTourModo1())
				s = searchCode(getRemarkItems(), COSTCENTERCT, "");
			else
				s = searchCode(getRemarkItems(), COSTCENTERCT_M2, "");
		}

		if (s.equals(""))
			s = searchCode(getRemarkItems(), COSTCENTERTRM1, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), COSTCENTERTRM2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), COSTCENTERTRM3, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), COSTCENTERTRM4, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), COSTCENTERTRM5, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), COSTCENTERTRM6, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), COSTCENTERTRM7, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), COSTCENTERTRM8, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), COSTCENTERTRM9, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), COSTCENTERTRM10, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), COSTCENTER2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), COSTCENTER1, "");
		return s.replace("-", "");
	}
	public String getCodigoNegocio() {
		String s = searchCode(getRemarkItems(), CODIGONEGOCIOTKM, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), CODIGONEGOCIOTKM2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), CODIGONEGOCIORM1, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), CODIGONEGOCIORM2, "");
		return s;
	}
	public String getRegion() {
		String s = searchCode(getRemarkItems(), REGIONTKM, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), REGIONTKM2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), REGIONRM1, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), REGIONRM2, "");
		return s;
	}
	public String getDepartamento() {
		String s = searchCode(getRemarkItems(), DEPARTAMENTOTKM, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), DEPARTAMENTOTKM2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), DEPARTAMENTORM1, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), DEPARTAMENTORM2, "");
		return s;
	}
	public String getAutorizationCreditCard() {
		String s = searchCode(getRemarkItems(), AUTORIZTKM, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), AUTORIZTKM2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), CREDITCARD_AUTH1, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), CREDITCARD_AUTH2, "");
		return s;
	}
	public String getConsumo() {
		String s = searchCode(getRemarkItems(), CONSUMOTKM, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), CONSUMO, "");
		
		return s;
	}
	public String getCodSegCreditCard() {
		String s = searchCode(getRemarkItems(), AUTORIZSEGTKM, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), AUTORIZSEGTKM2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), CREDITCARD_SEG1, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), CREDITCARD_SEG2, "");
		return s;
	}
	public String getObservation() {
		String s = searchCode(getRemarkItems(), OBSERVACIONTKM, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), OBSERVACIONTKM2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), OBSERVATION2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), OBSERVATION1, "");
		return s;
	}
	public String getCostoFee() {
		String s = searchCode(getRemarkItems(), OBSERVACIONRM1, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), OBSERVACIONRM2, "");
		return s;
	}
	public String getOrdenFee() {
		String s = searchCode(getRemarkItems(), OBSERVACIONRM3, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), OBSERVACIONRM4, "");
		return s;
	}
	public String getPagoFee() {
		String s = searchCode(getRemarkItems(), OBSERVACIONRM5, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), OBSERVACIONRM6, "");
				return s;
	}

	public String getSucursal() {
		String s = searchCode(getRemarkItems(), SUCURSALTKM, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), SUCURSALTKM2, "");
		return s;
	}

	public String getVendor() {
		String s = searchCode(getRemarkItems(), VENDORTKM, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), VENDORTKM2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), VENDORRM1, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), VENDORRM2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), VENDORRM3, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), VENDORRM4, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), VENDOR2, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), VENDOR1, "");
		if (s.equals(""))
			s = searchCode(getRemarkItems(), VENDOR3, "");
		return s;
	}


	public String getOverGain1(String tkt) throws Exception {
		String code2 = "TK-" + tkt.substring(tkt.length() - 3);
		String res = getTicketInfoMethod1(code2, OVERCEDIDOTKM, OVERGAIN1, OVERGAIN2);
		if (res.equals("0") ) {
			code2 = tkt+"";
			res = getTicketInfoMethod1(code2, "OVER-", null, null);
		}
		return res;
	}
	public double getTarifaReal1(String tkt) throws Exception {
		if (tkt.length() - 3<0) return 0;
		String code2 =  "TR-" + tkt.substring(tkt.length() - 3);
		String res = getTicketInfoMethod1(code2, PRECIOREFTKM, TARIFA1, TARIFA2);
		if (res.equals("0") ) {
			code2 = tkt+"";
			res = getTicketInfoMethod1(code2, "ORIG-", null,null);
		}
		String num=JTools.getNumberEmbeddedWithDecSigned(res);
		return num.equals("")?0:Double.parseDouble(num);	}

	public String getServiceFee1(String tkt) throws Exception {
		String	code2 = tkt+"";
		String	res = getTicketInfoMethod1(code2, "FEE-", null, null);
		return res;
	}
	
	/**
	 * @param ticket
	 * @return
	 * @throws Exception
	 */
	private String getTicketInfoMethod1(String ticket, String code1,
			String code2,String code3) throws Exception {
		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(
				this.getRemarkItems(), '|');
		String line = "";
		while (tok.hasMoreTokens()) {
			line = tok.nextToken();
			boolean ifline=false;
			if (code2!=null)
			  ifline = line.startsWith(ticket);
			else
			  ifline = line.indexOf(ticket)>=0;
			if (ifline) {
				if (code2==null) code2 = "XXXX";
				if (code3==null) code3 = "XXXX";
				JStringTokenizer tok2 = JCollectionFactory.createStringTokenizer(line, '/');
				while (tok2.hasMoreTokens()) {
					String s = tok2.nextToken();
					if (s.startsWith(code1))
						return Tools.getOnlyNumbers(s.substring(code1.length()));
					if (s.startsWith(code2))
						return Tools.getOnlyNumbers(s.substring(code2.length()));
					if (s.startsWith(code3))
						return Tools.getOnlyNumbers(s.substring(code3.length()));
				}
			}
		}
		return "0";
	}

	public String getOverCedido1(String tkt) throws Exception {
		String code2 = "TK-" + tkt.substring(tkt.length() - 3);
		return getTicketInfoMethod1(code2, OVERCEDIDOTKM,OVERCEDIDO1, OVERCEDIDO2);
	}

	public boolean isMaxtrip() throws Exception {
		return this.getRemarkItems().contains(" MAXTRIP ");
	}
}
