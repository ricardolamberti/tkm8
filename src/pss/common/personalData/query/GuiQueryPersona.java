package pss.common.personalData.query;

import pss.common.personalData.newPerson.GuiNewPerson;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;

public class GuiQueryPersona extends JWin {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiQueryPersona() throws Exception {
	}

	public JRecord ObtenerDato() throws Exception {
		return new BizQueryPersona();
	}
	
	@Override
	public String getSearchFields() throws Exception {
		return "per_description";
	}
	
	@Override
	public String getDescripField() {
		return "per_description";
	}

	public BizQueryPersona GetcDato() throws Exception {
		return (BizQueryPersona) this.getRecord();
	}
	
	@Override
	public void createActionMap() throws Exception {
		this.addAction(24, "Consulta", null, 117, false, false);
	}
	
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==24) return new JActQuery(this.getNewObjQueryPerson());
		return null;
	}
	
	public GuiNewPerson getNewObjQueryPerson() throws Exception {
		if (!this.GetcDato().hasData()) return this.createNewPerson();
		GuiNewPerson nc= this.createNewPerson();
		nc.setRecord(this.GetcDato().getNewQueryPerson());
		nc.setDropListener(this.getDropListener());
		return nc;
	}
	
	
	public GuiNewPerson createNewPerson() throws Exception {
		return new GuiNewPerson();
	}

}
