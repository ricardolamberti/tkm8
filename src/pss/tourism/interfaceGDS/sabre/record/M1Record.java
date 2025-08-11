package pss.tourism.interfaceGDS.sabre.record;

import java.util.StringTokenizer;

import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class M1Record extends SabreRecord {

  public static final String MESSAGE_ID= "IU1MID";
  public static final String INTERFACE_NAME_ITEM_NUMBER= "IU1PNO";
  public static final String PASSENGER_NAME= "IU1PNM";
  public static final String PASSENGER_MAN_NUMBER= "IU1PRK";
  public static final String ADVANTAGE_AAIRPASS_IND= "IU1IND";
  public static final String FREQUENT_TRAVELER_NUMBER= "IU1AVN";
  public static final String FREQUENT_TRAVELER_MEMBERSHIP_LEVEL= "IU1MLV";
  public static final String NUMBER_OF_ITINERARIES_M3= "IU1NM3";
  public static final String NAME_SELECTED_FOR_TICKETING= "IU1TKT";
  public static final String SPARE= "IU1SSM";
  public static final String NUMBER_OF_ACCTG_LINES_M5= "IU1NM5";
  public static final String NUMBER_OF_ITINERARY_REMARKS_M7= "IU1NM7";
  public static final String NUMBER_OF_INVOICE_REMARKS_M8= "IU1NM8";
  public static final String NUMBER_OF_INTERFACE= "IU1NM9";
  public static final String RESERVED_FOR_FUTURE_USE= "IU1NMA";
  public static final String CARRIAGE_RETURN= "IU1CR1";
  public static final String START_OF_VARIABLE_DATA= "IU1VAR";
  public static final String M3_ITEM_NUMBERS_FOR_THIS= "IU1M3C";
  public static final String M5_ITEM_NUMBERS_FOR_THIS= "IU1M5C";
  public static final String M7_ITEM_NUMBERS_FOR_THIS= "IU1M7C";
  public static final String M8_ITEM_NUMBERS_FOR_THIS= "IU1M8C";
  public static final String M9_ITEM_NUMBERS_FOR_THIS= "IU1M9C";

	public M1Record() {
		ID=SabreRecord.M1;
	}
	
  //   ==========================================================================
	//   M1 Record - Passenger Invoice Data Record
	//   ==========================================================================
	//   This record contains passenger names and passenger names remarks from the PNR
	//  There is one M1 record per passenger present in the PNR.
	public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

		int i=2;
		addFieldValue(MESSAGE_ID,ID);  //MESSAGE ID
		addFieldValue(INTERFACE_NAME_ITEM_NUMBER, line.substring(i,i+=2)); // INTERFACE NAME ITEM NUMBER 2
		addFieldValue(PASSENGER_NAME, line.substring(i,i+=64)); // PASSENGER NAME 64
		addFieldValue(PASSENGER_MAN_NUMBER, line.substring(i,i+=30)); //PASSENGER MAN NUMBER 30
		addFieldValue(ADVANTAGE_AAIRPASS_IND, line.substring(i,i+=1)); // ADVANTAGE/AAIRPASS IND 1
		addFieldValue(FREQUENT_TRAVELER_NUMBER, line.substring(i,i+=20)); // FREQUENT TRAVELER NUMBER 20
    addFieldValue(FREQUENT_TRAVELER_MEMBERSHIP_LEVEL, line.substring(i,i+=5)); // FREQUENT TRAVELER MEMBERSHIP LEVEL 5
    addFieldValue(NUMBER_OF_ITINERARIES_M3, line.substring(i,i+=2)); // NUMBER OF ITINERARIES M3 2
    addFieldValue(NAME_SELECTED_FOR_TICKETING, line.substring(i,i+=1)); // NAME SELECTED FOR TICKETING 1
    addFieldValue(SPARE, line.substring(i,i+=1)); // SPARE 1
    addFieldValue(NUMBER_OF_ACCTG_LINES_M5, line.substring(i,i+=2)); // NUMBER OF ACCTG LINES M5 2
    addFieldValue(NUMBER_OF_ITINERARY_REMARKS_M7, line.substring(i,i+=2)); // NUMBER OF ITINERARY REMARKS M7 2
    addFieldValue(NUMBER_OF_INVOICE_REMARKS_M8, line.substring(i,i+=2)); // NUMBER OF INVOICE REMARKS M8 2
    addFieldValue(NUMBER_OF_INTERFACE, line.substring(i,i+=2)); // NUMBER OF INTERFACE 2
    addFieldValue(RESERVED_FOR_FUTURE_USE, line.substring(i,i+=2)); // RESERVED FOR FUTURE USE 2

    line = this.getInput().readLine();
    i=0;
  //  addFieldValue(START_OF_VARIABLE_DATA, line.substring(i,i+=2)); // 2 en la practica no lo encuentro que venga
    
    addFieldValue(M3_ITEM_NUMBERS_FOR_THIS, line.substring(i) ); // 
    addFieldValue(M5_ITEM_NUMBERS_FOR_THIS, line=this.getInput().readLine() ); // 
    addFieldValue(M7_ITEM_NUMBERS_FOR_THIS, line=this.getInput().readLine() ); // 
    addFieldValue(M8_ITEM_NUMBERS_FOR_THIS, line=this.getInput().readLine() ); // 
    addFieldValue(M9_ITEM_NUMBERS_FOR_THIS, line=this.getInput().readLine() ); // 
    
		return line;

	}
	
	public String getPaxId() { 
	  return Integer.parseInt( this.getFieldValue(INTERFACE_NAME_ITEM_NUMBER) ) + "";
	}
		
	public String getPaxName() { 
	  return this.getFieldValue(M1Record.PASSENGER_NAME);
	}
	
	public String getPaxFiscalId() { 
		String out = M1Record.PASSENGER_MAN_NUMBER;
		String cuit="";
		StringTokenizer toks = new StringTokenizer(out,"/,");
		while (toks.hasMoreTokens()) {
			String tok = toks.nextToken();
			if (tok.indexOf("CUI")==-1) continue;
			cuit=tok;
			cuit = cuit.substring(cuit.indexOf("CUI"));
			cuit = JTools.replace(cuit, " ", "");
			cuit = JTools.replace(cuit, "-", "");
			cuit = JTools.replace(cuit, ".", "");
		}
		
	  return cuit;
	}
	
	
	
}
