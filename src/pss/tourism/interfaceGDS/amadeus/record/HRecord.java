package pss.tourism.interfaceGDS.amadeus.record;

import java.util.Date;

import pss.core.tools.JDateTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;
import pss.tourism.interfaceGDS.Tools;
import pss.tourism.interfaceGDS.sabre.record.M3Record;

public class HRecord extends AmadeusRecord {

	public static final String SEGMENT = "SEGMENT";
	public static final String DEPARTURE_AIRPORT = "DEPARTURE_AIRPORT";
	public static final String ARRIVAL_AIRPORT = "ARRIVAL_AIRPORT";
	public static final String FLIGHT_INFO = "FLIGHT_INFO";
	public static final String CARRIER = "CARRIER";
	public static final String CLASS = "CLASS";
	public static final String FLIGHT_NUMBER = "FLIGHT_NUMBER";
	public static final String DEPARTURE_DATE = "DEPARTURE_DATE";
	public static final String DEPARTURE_TIME = "DEPARTURE_TIME";
	public static final String ARRIVAL_TIME = "ARRIVAL_TIME";
	public static final String ARRIVAL_DATE = "ARRIVAL_DATE";
	public static final String STATUS = "STATUS";
	public static final String PLANE_TYPE = "PLANE_TYPE";
	private static final String FLIGHT_LAPSE = "FLIGHT_LAPSE";
	public static String ID_EMD = "ID_EMD";

	public HRecord() {
		ID = H;
	}

	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {
		int i = 0;
		if (line.substring(i += 2).length() < 6)
			return line;
		
		line = line.substring(2);

		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(line.substring(i), AmadeusRecord.FIELD_SEPARATOR);
		tok.skipEmptyTokens(false);

		String ignore = tok.nextToken(); // spare1
		if (ignore.endsWith("X")) {
			
			String lineold = line;
			while (true) {
				getInput().mark(10000);
				line = getInput().readLine();
				if (line == null)
					break;
				if (line.startsWith("X-")==false)
					break;
				if (line.startsWith("X-")) 
				   continue;
			}

			line=lineold;
			getInput().reset();
			return line;
		}
		
		String aux = tok.nextToken();
		
		i = 0;
		String codseg = aux.substring(i, i += 3);
		addFieldValue(SEGMENT, codseg);
		addFieldValue("STOP", aux.substring(i,i+1));

		String despegue = aux.substring(i += 1);
		
		
		addFieldValue(DEPARTURE_AIRPORT, despegue);
		tok.nextToken();
		String arrivo = tok.nextToken().trim();
		addFieldValue(ARRIVAL_AIRPORT, arrivo);
		tok.nextToken();
		String flight = tok.nextToken().trim();
		addFieldValue(FLIGHT_INFO, flight);
		
		DRecord d = ((DRecord)mRecords.getElement(AmadeusRecord.D));
	
		if (flight.length() >= 13) {

			int j = 0;
			addFieldValue(CARRIER, flight.substring(0, 2));
			addFieldValue(CLASS, flight.substring(11, 13));
			if (flight.substring(7).startsWith("OPEN") == true) {
				addFieldValue(DEPARTURE_DATE, Tools.convertAmadeusStringToStringYYYYMMDD(tok.nextToken().substring(0, 5),true,d.getYear(),d.getMonth()));
			} else {
				addFieldValue(FLIGHT_NUMBER, flight.substring(6, 10));
				j = 15;
				addFieldValue(DEPARTURE_DATE, Tools.convertAmadeusStringToStringYYYYMMDD(flight.substring(j, j += 5),true,d.getYear(),d.getMonth()));
				addFieldValue(DEPARTURE_TIME, Tools.getOnlyNumbers(flight.substring(j, j += 5)));
				addFieldValue(ARRIVAL_TIME, Tools.getOnlyNumbers(flight.substring(j, j += 5)));
				addFieldValue(ARRIVAL_DATE, Tools.convertAmadeusStringToStringYYYYMMDD(flight.substring(j, j += 5),true,d.getYear(),d.getMonth()));
				addFieldValue(STATUS, tok.nextToken().substring(0, 2));
				tok.nextToken();
				tok.nextToken();
				tok.nextToken();
				addFieldValue(PLANE_TYPE, tok.nextToken());
				tok.nextToken();
				tok.nextToken();
				tok.nextToken();
				tok.nextToken();
				tok.nextToken();
				tok.nextToken();
				addFieldValue(FLIGHT_LAPSE, tok.nextToken());
			}

			j++;

		} 
			
		String lineold = line;
		while (true) {
			getInput().mark(1000);
			line = getInput().readLine();
			if (line == null)
				break;
			if (line.startsWith("ICW")) {
				processICW(line);
				continue;
			}
			if (line.startsWith("X-")==false)
				break;
			if (line.startsWith("X-")) 
			   continue;

		}

		line=lineold;
		getInput().reset();

		
		// 0123456789012345678901234567890
		// H-001;003OEZE;BUENOS AIRES EZE ;DFW;DALLAS FORT WORTH;AA 0996 N N
		// 14OCT2135 0615 15OCT;OK01;HK01;DB;0;777;M;;2PC;;;ET;1040 ;N;5273;D

		// H-001;005OAEP;BUENOS AIRES AEP ;BRC;SAN CARLOS DE B ;LA 4346 V V
		// 11OCT1235 1500 11OCT;OK04;HK04;S ;0;320;M;LANARGENTINA;23K;;;ET;0225
		// ;N;832;
		// H-002;006OBRC;SAN CARLOS DE B ;AEP;BUENOS AIRES AEP ;LA 4345 K K
		// 13OCT1220 1425 13OCT;OK04;HK04;S ;0;320;M;LANARGENTINA;23K;;;ET;0205
		// ;N;832;

		return line;
	}
	
	public boolean isVoid() {
		return getFieldValue(FLIGHT_INFO)!=null&&getFieldValue(FLIGHT_INFO).equals("VOID");
	}
	
	public String getConnectionIndicator()  {
		return getFieldValue("STOP");
	}

	public String getFlightLapse() {
		return getFieldValue(FLIGHT_LAPSE);
	}

	public String getPlaneType() {
		return getFieldValue(PLANE_TYPE);
	}

	public String getStatus() {
		return getFieldValue(STATUS);
	}

	public String getFlightNumber() {
		return getFieldValue(FLIGHT_NUMBER);
	}

	public String getClassOfService() {
		return getFieldValue(CLASS);
	}

	public String getCarrierCode() {
		return getFieldValue(CARRIER);
	}

	public String getDepartureCityCode() {
		return getFieldValue(DEPARTURE_AIRPORT);
	}

	public boolean hasSegment() {
		return getFieldValue(SEGMENT)!=null;
	}

	public String getSegment() {
		return Integer.parseInt(getFieldValue(SEGMENT)) + "";
	}

	public String getArrivalCityCode() {
		return getFieldValue(ARRIVAL_AIRPORT);
	}

	public String getDepartureTime() {
		return Tools.formatTime(this.getFieldValue(DEPARTURE_TIME));
	}

	public String getArrivalTime() {
		return Tools.formatTime(this.getFieldValue(ARRIVAL_TIME));
	}

	public Date getArrivalDate() throws Exception {
		return JDateTools.StringToDate(getFieldValue(ARRIVAL_DATE), "yyyyMMdd");
	}

	public Date getDepartureDate() throws Exception {
		return JDateTools.StringToDate(getFieldValue(DEPARTURE_DATE), "yyyyMMdd");
	}
	private void processICW(String line) {
		JStringTokenizer tok2 = JCollectionFactory.createStringTokenizer(line, ';');
		tok2.skipEmptyTokens(false);

		tok2.nextToken();

		setFieldValue(ID_EMD, tok2.nextToken());

	}
}
