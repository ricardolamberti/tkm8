package pss.common.security;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;


public class GuiTemporalPasswordBase extends JWin {



	/*
	* Constructor de la Clase
	*/
	public GuiTemporalPasswordBase() throws Exception {
	}


	public JRecord ObtenerDato()   throws Exception { return new BizTemporalPassword(); }
	public int GetNroIcono()   throws Exception { return 10074; }
	public String GetTitle()   throws Exception { return "Clave Temporal"; }
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormTemporalPassword.class; }
	public String  getKeyField() throws Exception { return "ID"; }
	public String  getDescripField() { return "USERID"; }
	public void createActionMap() throws Exception {
		createActionQuery();
		createActionUpdate();
		createActionDelete();
	}


	public BizTemporalPassword GetcDato() throws Exception { return (BizTemporalPassword) this.getRecord(); }

 
}
