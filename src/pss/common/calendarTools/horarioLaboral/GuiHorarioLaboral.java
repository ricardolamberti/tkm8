package  pss.common.calendarTools.horarioLaboral;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiHorarioLaboral extends JWin {

	/**
	 * Constructor de la Clase
	 */
	public GuiHorarioLaboral() throws Exception {
	}

	public JRecord ObtenerDato() throws Exception {
		return new BizHorarioLaboral();
	}

	public int GetNroIcono() throws Exception {
		return 10001;
	}

	public String GetTitle() throws Exception {
		return "Horario Laboral";
	}

	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormHorarioLaboral.class;
	}

	public String getKeyField() throws Exception {
		return "descripcion";
	}

	public String getDescripField() {
		return "descripcion";
	}

	public BizHorarioLaboral GetcDato() throws Exception {
		return (BizHorarioLaboral) this.getRecord();
	}

}
