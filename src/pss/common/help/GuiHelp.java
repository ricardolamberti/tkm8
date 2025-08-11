package  pss.common.help;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.winUI.forms.JBaseForm;

public class GuiHelp extends JWin {

	/**
	 * Constructor de la Clase
	 */
	public GuiHelp() throws Exception {
	}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizHelp();
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 953;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Ayuda";
	}

	@Override
	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormHelp.class;
	}

	@Override
	public String getKeyField() throws Exception {
		return "secuencia";
	}

	@Override
	public String getDescripField() {
		return "help";
	}

	public BizQuestion GetcDato() throws Exception {
		return (BizQuestion) this.getRecord();
	}

	@Override
	public void createActionMap() throws Exception {
		this.addActionQuery(1, "Consultar");
		this.addActionUpdate(2, "Modificar");
		this.addActionDelete(3, "Eliminar");

	}
	
	@Override
	public boolean OkAction(BizAction zAct) throws Exception {
		return super.OkAction(zAct);
	}


}
