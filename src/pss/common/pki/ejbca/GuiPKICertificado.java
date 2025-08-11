package  pss.common.pki.ejbca;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;

public class GuiPKICertificado extends JWin {

	/**
	 * Constructor de la Clase
	 */
	public GuiPKICertificado() throws Exception {
	}

	public JRecord ObtenerDato() throws Exception {
		return new BizPKICertificado();
	}

	public int GetNroIcono() throws Exception {
		return 10003;
	}

	public String GetTitle() throws Exception {
		return "Certificado";
	}

	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormPKICertificado.class;
	}


	public String getKeyField() throws Exception {
		return "firma";
	}

	public String getDescripField() {
		return "estado";
	}

	public BizPKICertificado GetcDato() throws Exception {
		return (BizPKICertificado) this.getRecord();
	}

	public void createActionMap() throws Exception {
		
	}

	
	@Override
	public boolean OkAction(BizAction zAct) throws Exception {
		return super.OkAction(zAct);
	}

	public JAct getSubmitFor(BizAction a) throws Exception {
			return null;
	}




}
