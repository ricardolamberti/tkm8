package pss.core.connectivity.messageManager.server.confMngr.configTransaction;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiConfigTransaction extends JWin {

	/**
	 * Constructor de la Clase
	 */
	public GuiConfigTransaction() throws Exception {
	}

	public JRecord ObtenerDato() throws Exception {
		return new BizConfigTransaction();
	}

	public int GetNroIcono() throws Exception {
		return 909;
	}

	public String GetTitle() throws Exception {
		return "Transaccion de Configuracion";
	}

	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormConfigTransaction.class;
	}

	public String getKeyField() throws Exception {
		return "description";
	}

	public String getDescripField() {
		return "description";
	}

	public BizConfigTransaction GetcDato() throws Exception {
		return (BizConfigTransaction) this.getRecord();
	}

}
