package  pss.common.security;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiUserElectronicId extends JWin {

	/**
	 * Constructor de la Clase
	 */
	public GuiUserElectronicId() throws Exception {
	}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizUserElectronicId();
	}
	@Override
	public int GetNroIcono() throws Exception {
		return 351;
	}
	@Override
	public String GetTitle() throws Exception {
		return "Identificación electrónica";
	}
	@Override
	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormUserElectronicId.class;
	}
	@Override
	public String getKeyField() throws Exception {
		return "electronic_id";
	}
	@Override
	public String getDescripField() {
		return "usuario";
	}
	/**
	 * Mapeo las acciones con las operaciones
	 */
	@Override
	public void createActionMap() throws Exception {
		addActionQuery(1, "Consultar");
//		SetAccionModificar(2, "Modificar");
		addActionDelete(3, "Eliminar");
	}

	/**
	 * Devuelvo el dato ya casteado
	 */
	public BizUserElectronicId GetcDato() throws Exception {
		return (BizUserElectronicId)this.getRecord();
	}

}
