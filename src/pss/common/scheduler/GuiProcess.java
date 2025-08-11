package  pss.common.scheduler;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiProcess extends JWin {
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiProcess() throws Exception {
	}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizProcess();
	}

	@Override
	public String GetTitle() throws Exception {
		return "Datos del Proceso";
	}
	
  @Override
	public int GetNroIcono() throws Exception { return 5007; }

	@Override
	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormProcess.class;
	}

	@Override
	public String getKeyField() throws Exception {
		return "pid";
	}

	@Override
	public String getDescripField() {
		return "descripcion";
	}

	// -------------------------------------------------------------------------//
	// Mapeo las acciones con las operaciones
	// -------------------------------------------------------------------------//
	@Override
	public void createActionMap() throws Exception {
		this.createActionQuery();
		this.createActionUpdate();
		this.createActionDelete();
  	this.addAction(10, "Servidores", null, 5005, true, false, true, "Group");
	}

	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)
			return new JActWins(this.getHostsPerProcess());
		return super.getSubmit(a);
	}

	public BizProcess GetcDato() throws Exception {
		return (BizProcess) this.getRecord();
	}

	public JWins getHostsPerProcess() throws Exception {
		GuiProcessHosts oHosts = new GuiProcessHosts();
		oHosts.getRecords().addFilter("pid", GetcDato().pid.getValue());
		return oHosts;
	}

}
