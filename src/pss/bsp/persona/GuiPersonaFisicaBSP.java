package pss.bsp.persona;

import pss.common.personalData.types.GuiPersonaFisica;
import pss.core.services.records.JRecord;
import pss.core.winUI.forms.JBaseForm;

public class GuiPersonaFisicaBSP extends GuiPersonaFisica {

	public GuiPersonaFisicaBSP() throws Exception {
		super();
	}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizPersonaFisicaBSP();
	}
	
	@Override
	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormPersonaFisicaBSP.class;
	}
	

	public BizPersonaFisicaBSP GetccDato() throws Exception {
		return (BizPersonaFisicaBSP) this.getRecord();
	}
}
