package  pss.common.calendarTools.diasNoLaborales;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiDiaNoLaborable extends JWin {

	/**
	 * Constructor de la Clase
	 */
	public GuiDiaNoLaborable() throws Exception {
	}

	public JRecord ObtenerDato() throws Exception {
		return new BizDiaNoLaborable();
	}

	public int GetNroIcono() throws Exception {
		return 10001;
	}

	public String GetTitle() throws Exception {
		return "Dia No Laborable";
	}

	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormDiaNoLaborable.class;
	}

	public String getKeyField() throws Exception {
		return "descripcion";
	}

	public String getDescripField() {
		return "descripcion";
	}

	public BizDiaNoLaborable GetcDato() throws Exception {
		return (BizDiaNoLaborable) this.getRecord();
	}

}
