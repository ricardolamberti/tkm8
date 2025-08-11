package  pss.common.security;

import pss.core.services.JExec;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JPassword;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizAutorizar extends JRecord {

	public JString		pLogin		= new JString();
	public JPassword	pPassword	= new JPassword();
	public JString		pAccion		= new JString();

	/*****************************************************************************
	 * Constructor. Adds the properties
	 ****************************************************************************/
	public BizAutorizar() throws Exception {}

	@Override
	public void createProperties() throws Exception {
		this.addItem("login", pLogin);
		this.addItem("password", pPassword);
		this.addItem("accion", pAccion);
	}

	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(FIELD, "login", "Login", true, true, 10, 0, JObject.JUPPERCASE);
		this.addFixedItem(FIELD, "password", "Password", true, true, 20, 0, JObject.JLOWERCASE);
		this.addFixedItem(FIELD, "accion", "Accion", true, true, 100);
	}

	@Override
	public String GetTable() {
		return "";
	}

	/*****************************************************************************
	 * Action method
	 ****************************************************************************/
	@Override
	public void processInsert() throws Exception {
		BizUsuario oUsuario = BizUsuario.getUsr().getLinkedUser(pLogin.getValue());
		if (oUsuario == null) {
			oUsuario = new BizUsuario();
			oUsuario.dontThrowException(true);
			if (!oUsuario.Read(pLogin.getValue()))
				throw new JInvalidUserException();
			// se cachea el usuario supervisor por temas de performance
			BizUsuario.getUsr().addLinkedUser(oUsuario);
		} else {
			// se releen los atributos del usuario pero se mantiene el cache de
			// operaciones habilitadas
			oUsuario.Read(pLogin.getValue());
		}
 
		oUsuario.validatePassword(pPassword.getValue());

		BizOperacionRol oOpeRol = oUsuario.OperacionHabil(pAccion.getValue());
		if (oOpeRol == null || oOpeRol.ifClaveSupervisor())
			throw new UserNotAuthorizedException("99", "El Usuario no se encuentra Autorizado");

		BizUsuario.getUsr().setAuthorization(pAccion.getValue(), oUsuario.GetUsuario());
	}

	/*****************************************************************************
	 * Setter and action perform
	 ****************************************************************************/
	public void validateSupervisor(String zLogin, String zPassword, String zAction) throws Exception {
		this.setUsername(zLogin);
		this.setPassword(zPassword);
		this.setAction(zAction);
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				processInsert();
			}
		};
		oExec.SetThreadName("Pss-validateSupervisor");
		oExec.executeWithNewConnection();
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// SETTER METHODS
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setUsername(String zValue) {
		this.pLogin.setValue(zValue);
	}

	public void setPassword(String zValue) {
		this.pPassword.setValue(zValue);
	}

	public void setAction(String zValue) {
		this.pAccion.setValue(zValue);
	}
	/*
	 * public void checkCompanyPassword() throws Exception { String sKey = null;
	 * String sRightKey = null; String sAux = null;
	 * 
	 * if (this.pPassword.GetValor().length() < 20) { JExcepcion.SendError("Clave
	 * invalida"); } this.pCompanyKey.SetNull(); this.SetNoExcSelect(true);
	 * this.Read(); sAux = "077"+this.pPassword.GetValor(); sKey =
	 * this.getKey(sAux.substring(3, sAux.length())); sRightKey =
	 * this.getRightKey(); if
	 * (sKey.substring(0,10).compareTo(sRightKey.substring(0,10)) == 0 &&
	 * sKey.substring(10, 13).compareTo(sAux.substring(0, 3)) == 0) {
	 * this.updateKey(sRightKey); } else { JExcepcion.SendError("Clave invalida"); } }
	 * 
	 * private String getKey(String zCompany) throws Exception { String sKey =
	 * null; String sAux = null; long lTime = 0; Date oFecha = null; Calendar
	 * oCalendar = null;
	 * 
	 * sAux = zCompany.substring(13, 14) + zCompany.substring(1, 2) +
	 * zCompany.substring(3, 4) + zCompany.substring(12, 13) +
	 * zCompany.substring(6, 7) + zCompany.substring(5, 6) +
	 * zCompany.substring(10, 11) + zCompany.substring(8, 9); lTime =
	 * JTools.hexaToDecimal(sAux); if ( lTime<0 ) { return ""; } oCalendar =
	 * Calendar.getInstance(); oCalendar.setTimeInMillis((long)lTime*1000l);
	 * oFecha = oCalendar.getTime(); sKey = JDateTools.DateToString(oFecha, "yyyy") +
	 * JDateTools.DateToString(oFecha, "MM") + JDateTools.DateToString(oFecha, "dd") +
	 * zCompany.substring(15,16) + zCompany.substring(17, 18) +
	 * zCompany.substring(0, 1) + zCompany.substring(16 ,17) +
	 * zCompany.substring(14, 15);
	 * 
	 * return sKey; }
	 * 
	 * private String getRightKey() throws Exception { String sRightKey = null;
	 * 
	 * sRightKey = JDateTools.DateToString(new Date(), "yyyyMMdd") + "00"; sRightKey =
	 * this.sameOrNextDay(sRightKey, this.pCompanyKey.GetValor().equals(null) ?
	 * null : this.pCompanyKey.GetValor());
	 * 
	 * return sRightKey; }
	 * 
	 * private String sameOrNextDay(String zRightKey, String zBDKey) throws
	 * Exception { JDate oFecha1 = null; JDate oFecha2 = null; JDate oFechaAux =
	 * null;
	 * 
	 * oFecha1 = new JDate(); oFecha2 = new JDate(); try {
	 * oFecha1.set(Calendar.YEAR, Integer.parseInt(zRightKey.substring(0,4)));
	 * oFecha1.set(Calendar.MONTH, Integer.parseInt(zRightKey.substring(4,6)));
	 * oFecha1.set(Calendar.DAY_OF_MONTH,
	 * Integer.parseInt(zRightKey.substring(6,8))); oFecha2.set(Calendar.YEAR,
	 * Integer.parseInt(zBDKey.substring(0,4))); oFecha2.set(Calendar.MONTH,
	 * Integer.parseInt(zBDKey.substring(4,6)));
	 * oFecha2.set(Calendar.DAY_OF_MONTH,
	 * Integer.parseInt(zBDKey.substring(6,8))); oFechaAux = new
	 * JDate(JDate.AddDays(oFecha1.GetValor(), 1)); if (oFecha1.compareTo(oFecha2) ==
	 * 0 || oFechaAux.compareTo(oFecha2) == 0) { return zBDKey; } } catch
	 * (Exception ex) { return zRightKey; }
	 * 
	 * return zRightKey; }
	 * 
	 * private void updateKey(String zKey) throws Exception { int iTimes = 0;
	 * JDate oActual = null; Date dActual = null; String sAuxKey = null; Calendar
	 * oCalendar = null; BizSiteMasterCompanyPassword oCompanyPassword = null;
	 * 
	 * dActual = new Date(); oActual = new JDate(new Date()); iTimes =
	 * Integer.parseInt(zKey.substring(8, 10)); if (iTimes >= 19) { oCalendar =
	 * Calendar.getInstance(); oCalendar.setTime(oActual.GetValor());
	 * oCalendar.add(Calendar.DAY_OF_WEEK, 1); dActual = oCalendar.getTime();
	 * iTimes = 0; } sAuxKey = JDateTools.DateToString(dActual, "yyyyMMdd") +
	 * JTools.LPad(String.valueOf(iTimes), 2, "0"); if
	 * (sAuxKey.substring(0,8).compareTo(zKey.substring(0,8)) == 0) { iTimes++; }
	 * else { iTimes = 0; } sAuxKey = JDateTools.DateToString(dActual, "yyyyMMdd") +
	 * JTools.LPad(String.valueOf(iTimes), 2, "0"); //
	 * this.pCompanyKey.SetValor(sAuxKey); oCompanyPassword = new
	 * BizSiteMasterCompanyPassword(); //
	 * oCompanyPassword.pCompanyKey.SetValor(sAuxKey);
	 * oCompanyPassword.SetNoExcSelect(true); if (!oCompanyPassword.Read()) {
	 * oCompanyPassword.pCompanyKey.SetValor(sAuxKey);
	 * oCompanyPassword.processInsert(); } else {
	 * oCompanyPassword.pCompanyKey.SetValor(sAuxKey);
	 * oCompanyPassword.processUpdate(); } }
	 * 
	 */

}
