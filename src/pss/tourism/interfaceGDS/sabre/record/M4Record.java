package pss.tourism.interfaceGDS.sabre.record;

import com.ibm.icu.util.StringTokenizer;

import pss.core.tools.collections.JMap;

public class M4Record extends SabreRecord {

  public static final String  MESSAGE_ID="IU4MID";
  public static final String  M3_RECORD_ITEM_NUMBER="IU4SEG"; //
  public static final String  PASSENGER_TYPE_CODE="IU4TYP"; //
  public static final String  CONNECTION_INDICATOR="IU4CNI"; //
  public static final String  ENTITLEMENT_TYPE="IU4ETP";
  public static final String  FARE_NOT_VALID_BEFORE_DATE="IU4NVB"; //
  public static final String  FARE_NOT_VALID_AFTER_DATE="IU4NVA"; //
  public static final String  STATUS="IU4AAC";
  public static final String  BAGGAGE_ALLOWANCE_WEIGHT_LIMIT="IU4AWL"; //
  public static final String  FARE_BASIS_CODE="IU4FBS";
  public static final String  AMTRAK_CLASS_OF_SERVICE="IU4ACL"; //
  public static final String  FARE_BY_LEG_DOLLAR_AMOUNT="IU4AMT"; //
  public static final String  ELECTRONIC_TICKET_INDICATOR="IU4ETK"; //
  public static final String  FARE_BASIS_CODE_EXPANDED="IU4FB2"; //
  public static final String  TICKET_DESIGNATOR_EXPANDED="IU4TD2"; //
  public static final String  CURRENCY_CODE="IU4CUR"; //
  public static final String  SPARE="IU4SP2";
  public static final String  CARRIAGE_RETURN="IU4CR1";

  public M4Record() {
		ID=SabreRecord.M4;
	}
	
  //  ==========================================================================
  //  M4 Record - Passenger Entitlement Data Record
  //  ==========================================================================
  //  Represents the M4 Record which is only applicable to M3 "AIR" format itinerary types
  //  This record is based
	public String doProcess(JMap<String,Object>mRecords, String line) throws Exception {

		int i=2;
		addFieldValue(MESSAGE_ID,ID);  //MESSAGE ID
    addFieldValue(M3_RECORD_ITEM_NUMBER,line.substring(i,i+=2)); 
    addFieldValue(PASSENGER_TYPE_CODE,line.substring(i,i+=3)); 
    addFieldValue(CONNECTION_INDICATOR,line.substring(i,i+=1)); 
    addFieldValue(ENTITLEMENT_TYPE,line.substring(i,i+=1)); 
    addFieldValue(FARE_NOT_VALID_BEFORE_DATE,line.substring(i,i+=5)); 
    addFieldValue(FARE_NOT_VALID_AFTER_DATE,line.substring(i,i+=5)); 
    addFieldValue(STATUS,line.substring(i,i+=2)); 
    addFieldValue(BAGGAGE_ALLOWANCE_WEIGHT_LIMIT,line.substring(i,i+=3)); 
    addFieldValue(FARE_BASIS_CODE,line.substring(i,i+=13)); 
    addFieldValue(AMTRAK_CLASS_OF_SERVICE,line.substring(i,i+=2)); 
    addFieldValue(FARE_BY_LEG_DOLLAR_AMOUNT,line.substring(i,i+=8)); 
    addFieldValue(ELECTRONIC_TICKET_INDICATOR,line.substring(i,i+=1)); 
    addFieldValue(FARE_BASIS_CODE_EXPANDED,line.substring(i,i+=10)); 
    addFieldValue(TICKET_DESIGNATOR_EXPANDED,line.substring(i,i+=12)); 
    if (line.length()>=i+16)  addFieldValue(CURRENCY_CODE,line.substring(i,i+=3)); 
    addFieldValue(SPARE,line.substring(i,i+=13)); 
		return line;

	}
	


	
}
