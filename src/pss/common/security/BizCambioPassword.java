package  pss.common.security;

import pss.core.services.fields.JObject;
import pss.core.services.fields.JPassword;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;

public class BizCambioPassword extends JRecord {

	JString pLogin = new JString();
	JString pDescrip = new JString();
	JPassword pPassActual = new JPassword();
	JPassword pPassNueva = new JPassword();
	JPassword pPassConfir = new JPassword();

	private static int iLoginFails = 0;

	public static int GetLoginFails() {
		return iLoginFails;
	}

	public BizCambioPassword() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		this.addItem("login", pLogin);
		this.addItem("descrip", pDescrip);
		this.addItem("passactual", pPassActual);
		this.addItem("passnueva", pPassNueva);
		this.addItem("passconfir", pPassConfir);
	}

	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "login", "Usuario", true, true, 40, 0,	JObject.JUPPERCASE);
		this.addFixedItem(FIELD, "descrip", "Descripción", true, false, 40, 0,JObject.JLOWERCASE);
		this.addFixedItem(FIELD, "passactual", "Clave actual", true, false,20);
    this.addFixedItem(FIELD, "passnueva", "Clave nueva", true, true,20);
    this.addFixedItem(FIELD, "passconfir", "Confirmar", true, true,20);
/*		this.addFixedItem(FIELD, "passactual", "Clave actual", true, false,
				BizSegConfiguracion.C_MAX_PASSWORD_LEN);
		this.addFixedItem(FIELD, "passnueva", "Clave nueva", true, true,
				BizSegConfiguracion.C_MAX_PASSWORD_LEN);
		this.addFixedItem(FIELD, "passconfir", "Confirmar", true, true,
				BizSegConfiguracion.C_MAX_PASSWORD_LEN);*/
	}

	public void setNewPassword(String zVal) throws Exception {
		this.pPassNueva.setValue(zVal);
	}

	public void setCurrentPassword(String zVal) throws Exception {
		this.pPassActual.setValue(zVal);
	}

	public void setConfirmPassword(String zVal) throws Exception {
		this.pPassConfir.setValue(zVal);
	}

	public void setUser(String zVal) throws Exception {
		this.pLogin.setValue(zVal);
	}

	public void setDescripcion(String zVal) throws Exception {
		this.pDescrip.setValue(zVal);
	}

	@Override
	public void processInsert() throws Exception {

		BizUsuario user = new BizUsuario();
		user.lock(true);
		user.dontThrowException(true);
		if (!user.Read(pLogin.toString())) {
			JExcepcion.SendError("Usuario inválido");
		}

		if (pPassNueva.getValue().equals("")) {
			JExcepcion.SendError("La clave ingresada no puede estar vacía o ser espacios en blanco");
		}

		if (!user.isBlanked()
				&& !pPassActual.getValue().equals(user.getPasswordDecrypt())
				&& !BizUsuario.IsAdminUser()) {
			JExcepcion.SendError("La clave actual ingresada es incorrecta");
		}

		if (pPassActual.getValue().equals(pPassNueva.getValue())) {
			JExcepcion.SendError("La nueva clave es idéntica a la clave actual");
		}

		if (!pPassNueva.getValue().equals(pPassConfir.toString())) {
			JExcepcion.SendError("La confirmación de la clave no es igual a la nueva clave ingresada");
		}

		long iMinLen = user.findLongitudMinClave();
		if (pPassNueva.toString().length() < iMinLen) {
			JExcepcion.SendError("La clave debe tener un mínimo de " + String.valueOf(iMinLen) + " caracteres");
		}

		if (user.findHasStrongPas()) { 
			if (!JTools.checkStrongPass(pPassNueva.getValue())) 
				JExcepcion.SendError("La clave debe ser mas compleja (8 caracteres o más, números, letras mayusculas y minusculas)");
		}

		user.pClave.setValue(JTools.StringToPassword(user.GetUsuario(),	pPassNueva.getValue()));
		user.pFechaCambioClave.setValue(BizUsuario.getUsr().todayGMT());
		user.pIntentosClave.setValue(0);
		user.update();
	}

	public String getMessage(String zMsg) throws Exception {
		String msg = 	BizUsuario.retrieveSkinGenerator().getMessage(zMsg, null);
		return msg;
	}
}
