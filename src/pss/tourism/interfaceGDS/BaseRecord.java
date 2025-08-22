package pss.tourism.interfaceGDS;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class BaseRecord {

	public String ID = "";
  public final static String TAX_ZK="ZK";
  public final static String TAX_XT="XT";
  public final static String TAX_DL="DL";
  public final static String TAX_PD="PD";
  public final static String TAX_Q=" Q";
  
  public final static String CURR_ARS="ARS";

	private JMap<String, String> mFields = JCollectionFactory.createMap();
	private BufferedReader input=null;
	public int ronda;
	
	public int getRonda() {
		return ronda;
	}
	public String consumeEmptyLines(BufferedReader input) throws IOException {
		String line;
		input.mark(2000);
		while ((line = input.readLine()) != null) {
			if (!line.trim().isEmpty()) {
				input.reset();
				return line;
			}
			input.mark(2000);
		}
		return null; // Devuelve null si llega al final del stream
	}
	public void setRonda(int ronda) {
		this.ronda = ronda;
	}

	public boolean hasSpecialSubRecord() {
		return false;
	}
	
	public boolean isEmpty() {
		return mFields.isEmpty();
	}
	
	/**
	 * @return the input
	 */
	public BufferedReader getInput() {
		return input;
	}

	/**
	 * @param input the input to set
	 */
	public void setInput(BufferedReader input) {
		this.input = input;
	}

	public String getID() { return ID; }
	
	public boolean hasRecordType(String line) throws Exception {
		if ( line == null ) return false;
		if (line.toUpperCase().startsWith(ID) == false) {
			//PssLogger.logDebug("Record "+ID+" not found");
			if ( input != null )
  			this.input.reset();
			return false;
		}
		return true;
	}
	
	public String process(JMap<String,Object>mRecords, String line) throws Exception {
		return process(mRecords, line, -1);
	}
	
	private boolean ignoreRecord=false;
	
	public void setIgnoreRecord(boolean value) {
		ignoreRecord = value;
	}

	public String process(JMap<String,Object>mRecords, String line, int idx) throws Exception {
		int i=0;
		if (hasRecordType(line)==false) {
			return line;
		}
		PssLogger.logDebug("------------------------------------------------------------------------------------");
		PssLogger.logDebug("-  Parsing " + ID + " begin");
		PssLogger.logDebug("------------------------------------------------------------------------------------");
		String oldline = line;
 		String rline = this.doProcess(mRecords,line);
		PssLogger.logDebug("------------------------------------------------------------------------------------");
		PssLogger.logDebug("-  Parsing "+ ID +  " end");
		PssLogger.logDebug("------------------------------------------------------------------------------------");
		String id = getID();
		if ( idx > 0 )
			id=id+idx;
		if (id.equals(oldline)==false)
	      mRecords.addElement(id, this);
		return rline;
	}
	
	public String doProcess(JMap<String, Object> mRecords, String line) throws Exception {
		return line;
	}
	
	public void addFieldValue(String key, long value) {
		addFieldValue(key,value+"");
	}
	
	public void addFieldValue(String key, int value) {
		addFieldValue(key,value+"");
	}

	public void addFieldValue(String key, String value) {
		if (value==null) return;
//		if (key.equals("CURRENTYEAR")==false)
//		  PssLogger.logDebug("("+key+")=("+value.trim()+")");
		mFields.addElement(key, value.trim());
	}
	
	public void setFieldValue(String key, String value) {
		mFields.removeElement(key);
		this.addFieldValue(key,value);
	}

	public String getFieldValue(String key) {
		return mFields.getElement(key);
	}

	public Date getFieldValueAsDate(String key,long year) throws Exception {
		String f = mFields.getElement(key);
		if ( f == null ) return null;
		if ( f.equals("") ) return null;
		String day = this.getFieldValue(key).substring(0, 2);
		String month = Tools.convertMonthStringToNumber(this.getFieldValue(key).substring(2));
		if (year%4!=0 && JTools.getLongNumberEmbedded(month)==2 && JTools.getLongNumberEmbedded(day)==29)
			day="28";
		Date date = JDateTools.StringToDate(""+year + month + day, "yyyyMMdd");
		return date;
	}
	public Date getFieldValueAsDateShort(String key,long anio) throws Exception {
		String f = mFields.getElement(key);
		if ( f == null ) return null;
		if ( f.equals("") ) return null;
		String day = this.getFieldValue(key).substring(0, 2);
		String month = Tools.convertMonthStringToNumber(this.getFieldValue(key).substring(2,5));
		String year = ""+anio;

		Date date = JDateTools.StringToDate(""+year+month+day, "yyyyMMdd");
		return date;
	}
	public Date getFieldValueAsDate(String key) throws Exception {
		String f = mFields.getElement(key);
		if ( f == null ) return null;
		if ( f.equals("") ) return null;
		String day = this.getFieldValue(key).substring(0, 2);
		String month = Tools.convertMonthStringToNumber(this.getFieldValue(key).substring(2,5));
		String year = "20"+this.getFieldValue(key).substring(5);

		Date date = JDateTools.StringToDate(""+year+month+day, "yyyyMMdd");
		return date;
	}
	public Date getFieldValueAsDateLong(String key) throws Exception {
		String f = mFields.getElement(key);
		if ( f == null ) return null;
		if ( f.equals("") ) return null;
		String day = this.getFieldValue(key).substring(0, 2);
		String month = Tools.convertMonthStringToNumber(this.getFieldValue(key).substring(2,5));
		String year = this.getFieldValue(key).substring(5);

		Date date = JDateTools.StringToDate(""+year+month+day, "yyyyMMdd");
		return date;
	}
	public String getFieldValueAsNumber(String key) {
		String f = mFields.getElement(key);
		if ( f == null ) return "0";
		if ( f.equals("") ) return "0";
		return f;
	}
	public long getFieldValueNumber(String key) {
		String f = mFields.getElement(key);
		if ( f == null ) return 0;
		if ( f.equals("") ) return 0;
		return Long.parseLong(f);
	}
	public double getFieldValueDouble(String key,int decimales) throws Exception {
		String f = mFields.getElement(key);
		if ( f == null ) return 0;
		if ( f.equals("") ) return 0;
		int s = f.length();
		return Double.parseDouble(f.substring(0, s-decimales)+"."+f.substring(s-decimales));
	}
	public double getFieldValueDouble(String key) throws Exception {
		String f = mFields.getElement(key);
		if ( f == null ) return 0;
		if ( f.equals("") ) return 0;
		if ( f.equals("EXEMPT")) return 0;
		return Double.parseDouble(JTools.getNumberEmbeddedWithDecSigned(f));
	}
	public String getFieldValueMoneda(String key) throws Exception {
		String f = mFields.getElement(key);
		if ( f == null ) return "";
		if ( f.equals("") ) return "";
		if ( f.length()-3<=0) return "";
		return f.substring(f.length()-3,f.length());
	}
	public double getFieldValueFirstDouble(String key) throws Exception {
		String f = mFields.getElement(key);
		if ( f == null ) return 0;
		if ( f.equals("") ) return 0;
		int pos = f.indexOf(" ");
		if (pos!=-1) f=f.substring(0, pos);
		return Double.parseDouble(JTools.getNumberEmbeddedWithDecSigned(f));
	}
	public String getFieldValueFirstMoneda(String key) throws Exception {
		String f = mFields.getElement(key);
		if ( f == null ) return "";
		if ( f.equals("") ) return "";
		if ( f.length()<3) return "";
		if (f.startsWith("@"))
			return f.substring(1,4);
		return f.substring(0,3);
	}
}
