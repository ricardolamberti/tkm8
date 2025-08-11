package  pss.common.security;

import java.util.Calendar;
import java.util.Date;

import pss.core.services.fields.JDate;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;

public class BizCompanyTrace extends JRecord {

	private JString pCompanyKey=new JString();
	private JString pPassword=new JString();

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Constructor de la Clase
	 */
	public BizCompanyTrace() throws Exception {
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Properties methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Adds the object properties
	 */
	@Override
	public void createProperties() throws Exception {
		this.addItem("company_key", this.pCompanyKey);
		this.addItem("password", this.pPassword);
	}

	/**
	 * Adds the fixed object properties
	 */
	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(FIELD, "company_key", "Clave Compañia", true, false, 20);
		this.addFixedItem(VIRTUAL, "password", "Clave", true, false, 20);
	}

	/**
	 * Returns the table name
	 */
	@Override
	public String GetTable() {
		return "SEG_COMPANY_TRACE";
	}

	public String getCompanyKey() throws Exception {
		return this.pCompanyKey.getValue();
	}

	public void setCompanyKey(String zValue) throws Exception {
		this.pCompanyKey.setValue(zValue);
	}

	public String getPassword() throws Exception {
		return this.pPassword.getValue();
	}

	public void setPassword(String zValue) throws Exception {
		this.pPassword.setValue(zValue);
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean checkCompanyPassword() throws Exception {
		String sKey=null;
		String sRightKey=null;
		String sAux=null;

		this.pCompanyKey.setNull();
		this.dontThrowException(true);
		this.Read();

		if (pPassword.getValue().length()<20) {
			return false;
		}
		sAux="090"+pPassword.getValue();
		sKey=getKey(sAux.substring(3, sAux.length()));
		sRightKey=getRightKey(pCompanyKey.getValue());
		if (sKey.substring(0, 10).compareTo(sRightKey.substring(0, 10))==0&&sKey.substring(10, 13).compareTo(sAux.substring(0, 3))==0) {
			updateKey(sRightKey);
		} else {
			return false;
		}
		return true;
	}

	private String getKey(String zCompany) throws Exception {
		String sKey=null;
		String sAux=null;
		long lTime=0;
		Date oFecha=null;
		Calendar oCalendar=null;

		sAux=zCompany.substring(13, 14)+zCompany.substring(1, 2)+zCompany.substring(3, 4)+zCompany.substring(12, 13)+zCompany.substring(6, 7)+zCompany.substring(5, 6)+zCompany.substring(10, 11)+zCompany.substring(8, 9);
		lTime=JTools.hexaToDecimal(sAux);
		if (lTime<0) {
			return "";
		}
		oCalendar=Calendar.getInstance();
		oCalendar.setTimeInMillis(lTime*1000l);
		oFecha=oCalendar.getTime();
		sKey=JDateTools.DateToString(oFecha, "yyyy")+JDateTools.DateToString(oFecha, "MM")+JDateTools.DateToString(oFecha, "dd")+zCompany.substring(15, 16)+zCompany.substring(17, 18)+zCompany.substring(0, 1)+zCompany.substring(16, 17)+zCompany.substring(14, 15);

		return sKey;
	}

	private String getRightKey(String zCompany) throws Exception {
		String sRightKey=null;

		sRightKey=JDateTools.DateToString(new Date(), "yyyyMMdd")+"00";
		sRightKey=sameOrNextDay(sRightKey, zCompany);
		return sRightKey;
	}

	private String sameOrNextDay(String zRightKey, String zBDKey) throws Exception {
		JDateTools oFecha1=null;
		JDateTools oFecha2=null;
		JDateTools oFechaAux=null;

		oFecha1=new JDateTools();
		oFecha2=new JDateTools();
		try {
			oFecha1.set(Calendar.YEAR, Integer.parseInt(zRightKey.substring(0, 4)));
			oFecha1.set(Calendar.MONTH, Integer.parseInt(zRightKey.substring(4, 6)));
			oFecha1.set(Calendar.DAY_OF_MONTH, Integer.parseInt(zRightKey.substring(6, 8)));
			oFecha2.set(Calendar.YEAR, Integer.parseInt(zBDKey.substring(0, 4)));
			oFecha2.set(Calendar.MONTH, Integer.parseInt(zBDKey.substring(4, 6)));
			oFecha2.set(Calendar.DAY_OF_MONTH, Integer.parseInt(zBDKey.substring(6, 8)));
			oFecha1.addDays(1);
			if (oFecha1.compareTo(oFecha2)==0||oFechaAux.compareTo(oFecha2)==0) {
				return zBDKey;
			}
		} catch (Exception ex) {
			return zRightKey;
		}

		return zRightKey;
	}

	private void updateKey(String zKey) throws Exception {
		int iTimes=0;
		JDate oActual=null;
		Date dActual=null;
		String sAuxKey=null;
		Calendar oCalendar=null;
		BizCompanyTrace oCompanyPassword=null;

		dActual=new Date();
		oActual=new JDate(new Date());
		iTimes=Integer.parseInt(zKey.substring(8, 10));
		if (iTimes>=19) {
			oCalendar=Calendar.getInstance();
			oCalendar.setTime(oActual.getValue());
			oCalendar.add(Calendar.DAY_OF_WEEK, 1);
			dActual=oCalendar.getTime();
			iTimes=0;
		}
		sAuxKey=JDateTools.DateToString(dActual, "yyyyMMdd")+JTools.LPad(String.valueOf(iTimes), 2, "0");
		if (sAuxKey.substring(0, 8).compareTo(zKey.substring(0, 8))==0) {
			iTimes++;
		} else {
			iTimes=0;
		}
		sAuxKey=JDateTools.DateToString(dActual, "yyyyMMdd")+JTools.LPad(String.valueOf(iTimes), 2, "0");
		// this.pCompanyKey.SetValor(sAuxKey);
		oCompanyPassword=new BizCompanyTrace();
		// oCompanyPassword.pCompanyKey.SetValor(sAuxKey);
		oCompanyPassword.dontThrowException(true);
		if (!oCompanyPassword.Read()) {
			oCompanyPassword.pCompanyKey.setValue(sAuxKey);
			oCompanyPassword.processInsert();
		} else {
			oCompanyPassword.pCompanyKey.setValue(sAuxKey);
			oCompanyPassword.processUpdate();
		}
	}

}
