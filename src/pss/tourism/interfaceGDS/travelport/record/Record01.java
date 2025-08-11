package pss.tourism.interfaceGDS.travelport.record;

import java.util.Date;

import pss.core.tools.JDateTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;
import pss.tourism.interfaceGDS.Tools;
import pss.tourism.interfaceGDS.travelport.TravelPortFileProcessor;

public class Record01 extends TravelPortRecord {

	private String CARRIERCODE = "CARRIERCODE";
	private String FLIGHT_NUMBER = "FLIGHT_NUMBER";
	private String SEGMENTS = "SEGMENTS";
	private String SEGMENTCODE = "SEGMENTCODE";
	private String STOPS = "STOPS";
	private String FLIGHT_TIME="FLIGHT_TIME";
	public static final String DEPARTURE_AIRPORT = "DEPARTURE_AIRPORT";
	public static final String ARRIVAL_AIRPORT = "ARRIVAL_AIRPORT";
	public static final String FLIGHT_INFO = "FLIGHT_INFO";
	public static final String CLASS = "CLASS";
	public static final String DEPARTURE_DATE = "DEPARTURE_DATE";
	public static final String DEPARTURE_TIME = "DEPARTURE_TIME";
	public static final String ARRIVAL_TIME = "ARRIVAL_TIME";
	public static final String ARRIVAL_DATE = "ARRIVAL_DATE";
	public static final String STATUS = "STATUS";
	public static final String PLANE_TYPE = "PLANE_TYPE";
	public static final String FARE_FAMILY = "FAREFAMILY";
	public static final String CONNECTED_CODE = "CONN_CODE";
	
	

	public static int segments = 0;

	public Record01() {
		ID = ZEROONE;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {

		int i = 0;

		if (line.substring(i += 2).length() < 2)
			return line;

		int segments = Integer.parseInt(getFieldValueAsNumber(SEGMENTS)) + 1;
		setFieldValue(SEGMENTS, segments + "");

		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(line.substring(i), TravelPortRecord.FIELD_SEPARATOR);
		tok.skipEmptyTokens(false);

		addFieldValue(SEGMENTCODE + segments, tok.nextToken());
		String flinfo = tok.nextToken();
		i = 0;
		addFieldValue(STOPS + segments, flinfo.substring(i, i += 1));
		i++;
		addFieldValue(CARRIERCODE + segments, flinfo.substring(i, i += 2));
		addFieldValue(FLIGHT_NUMBER + segments, flinfo.substring(i));

		String fclass = tok.nextToken();
		addFieldValue(CLASS + segments, fclass);

		i = 0;
		String sdep = tok.nextToken();
		addFieldValue(DEPARTURE_AIRPORT + segments, sdep.substring(i, i += 3));

		Record3 m = (Record3) mRecords.getElement(TravelPortRecord.THREE);

		Date creationDate;
		if (m != null) 
			creationDate = m.getCreationDate();
		else {
			creationDate = JDateTools.StringToDate("1-1-"+TravelPortFileProcessor.getYear());
		}


		String date = Tools.convertWithOriginalDateToStringYYYYMMDD(sdep.substring(i, i += 5), creationDate);

		addFieldValue(DEPARTURE_DATE + segments, date);
		addFieldValue(DEPARTURE_TIME + segments, Tools.formatTime(sdep.substring(i)));

		i = 0;
		String sarr = tok.nextToken();

		addFieldValue(ARRIVAL_AIRPORT + segments, sarr.substring(i, i += 3));
		date = Tools.convertWithOriginalDateToStringYYYYMMDD(sarr.substring(i, i += 5), creationDate);
		addFieldValue(ARRIVAL_DATE + segments, date);
		addFieldValue(ARRIVAL_TIME + segments, Tools.formatTime(sarr.substring(i)));

		tok.nextToken();
		tok.nextToken();
		tok.nextToken();
		String status =tok.nextToken();
		addFieldValue(STATUS + segments, status.substring(0, 2));
		addFieldValue(CONNECTED_CODE + segments, status.length()<4?status.substring(2, 3):status.substring(3, 4));
		tok.nextToken();
		addFieldValue(FARE_FAMILY + segments, 	tok.nextToken());
		tok.nextToken();
		tok.nextToken();
		addFieldValue(FLIGHT_TIME + segments, 	tok.nextToken());
		
		

		return line;

	}

	public int getNumberOfSegments() {
		int segments = Integer.parseInt(getFieldValueAsNumber(SEGMENTS));
		return segments;
	}
	public String getFlightTime(int segment) {
		return getFieldValue(FLIGHT_TIME + segment);
	}
	public String getFareFamily(int segment) {
		return getFieldValue(FARE_FAMILY + segment);
	}
	public String getConnectedCode(int segment) {
		return getFieldValue(CONNECTED_CODE + segment);
	}

	public String getStatus(int segment) {
		return getFieldValue(STATUS + segment);
	}

	public String getFlightNumber(int segment) {
		return getFieldValue(FLIGHT_NUMBER + segment);
	}

	public String getClassOfService(int segment) {
		return getFieldValue(CLASS + segment);
	}

	public String getCarrierCode(int segment) {
		return getFieldValue(CARRIERCODE + segment);
	}

	public String getDepartureCityCode(int segment) {
		return getFieldValue(DEPARTURE_AIRPORT + segment);
	}

	public String getArrivalCityCode(int segment) {
		return getFieldValue(ARRIVAL_AIRPORT + segment);
	}

	public String getDepartureTime(int segment) {
		String dep = (this.getFieldValue(DEPARTURE_TIME + segment));
		if (dep.startsWith("24"))
			dep = "00" + dep.substring(2);
		return dep;
	}

	public String getArrivalTime(int segment) {
		String arr = (this.getFieldValue(ARRIVAL_TIME + segment));
		if (arr.startsWith("24"))
			arr = "00" + arr.substring(2);
		return arr;
	}

	public Date getArrivalDate(int segment) throws Exception {
		return JDateTools.StringToDate(getFieldValue(ARRIVAL_DATE + segment), "yyyyMMdd");
	}

	public Date getDepartureDate(int segment) throws Exception {
		return JDateTools.StringToDate(getFieldValue(DEPARTURE_DATE + segment), "yyyyMMdd");
	}

	public String getSegment(int i) {
		return getFieldValue(SEGMENTCODE + i);
	}

}
