package pss.tourism.interfaceGDS.travelport.record;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;

public class RecordA extends TravelPortRecord {
	
	private String PASSENGERID="PASSENGERID";
	private String PASSENGERS="PASSENGERS";
	private String PAXNAME="PAXNAME";
	private String PAXTYPE="PAXTYPE";
	private String PAXTICKET="PAXTICKET";
	private String PAXRONDA="PAXRONDA";
	
	
	public RecordA() {
		ID = ARECORD;
	}
	
	private boolean isVoid=false;
	
	public boolean isVoid() { return isVoid;}

	public String doProcess(JMap<String, Object> mRecords, String line)
			throws Exception {

		int i = 0;

		if (line.substring(i += 2).length() < 2)
			return line;
		
		int passengers = Integer.parseInt(getFieldValueAsNumber(PASSENGERS))+1;
		setFieldValue(PASSENGERS, passengers+"");

		// A\1.1\GUERRA/LEONARDO\ADT\*\*\9010738598599\E-
		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(line
				.substring(i), TravelPortRecord.FIELD_SEPARATOR);
		tok.skipEmptyTokens(false);
		
		if ( tok.hasMoreTokens() == false ) return "";
		String stok = tok.nextToken();
		
		String sid = stok.substring(0,1);
		
		addFieldValue(PASSENGERID+passengers, sid );
		addFieldValue(PAXRONDA+passengers, getRonda()+"" );
		
		if ( tok.hasMoreTokens() == false ) return "";
		addFieldValue(PAXNAME+passengers, tok.nextToken() );
		if ( tok.hasMoreTokens() == false ) return "";
		addFieldValue(PAXTYPE+passengers, tok.nextToken() );
		if ( tok.hasMoreTokens() == false ) return "";
		tok.nextToken();
		if ( tok.hasMoreTokens() == false ) return "";
		tok.nextToken();
		if ( tok.hasMoreTokens() == false ) return "";
		String ticket = tok.nextToken();
		if (ticket.startsWith("V")) {
			isVoid = true;
			ticket = ticket.substring(1);
		}
		if (ticket.length()>10) {
			ticket = ticket.substring(0,10);
		}
			
		addFieldValue(PAXTICKET+passengers, ticket );

		
		return line;
		
	}
	
	public int getNumberOfTickets() {
		return Integer.parseInt(getFieldValue(PASSENGERS));
	}


	public String getTicketNumber(int i) {
		return getFieldValue(PAXTICKET+i);
	}
	
	public String getPaxType(int i) {
		return getFieldValue(PAXTYPE+i);
	}
	public int getPaxRonda(int i) {
		return Integer.parseInt(getFieldValue(PAXRONDA+i));
	}
	public String getPaxId(int i) {
		return getFieldValue(PASSENGERID+i);
	}

	public String getPaxName(int i) {
		return getFieldValue(PAXNAME+i);
	}

	public String getPassengerTypeIn(int i) {
		return getPaxType(i);
	}






}
