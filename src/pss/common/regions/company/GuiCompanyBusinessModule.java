package pss.common.regions.company;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiCompanyBusinessModule extends JWin {

		public GuiCompanyBusinessModule() throws Exception {}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizCompanyBusinessModule();
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 5000;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Módulo de Negocio";
	}

	@Override
	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormCompanyBusinessModule.class;
	}

  @Override
	public String getKeyField() { return "company"; }
  @Override
	public String getDescripField() { return "module"; }

	// -------------------------------------------------------------------------//
	// Devuelvo el dato ya casteado
	// -------------------------------------------------------------------------//
	public BizCompanyBusinessModule GetcDato() throws Exception {
		return (BizCompanyBusinessModule) this.getRecord();
	}
	
	@Override
	public void createActionMap() throws Exception {
		super.createActionMap();
	}
	
}
