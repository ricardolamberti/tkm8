package pss.bsp.persona;

import pss.common.personalData.types.GuiPersonaJuridica;
import pss.core.services.records.JRecord;
import pss.core.winUI.forms.JBaseForm;

public class GuiPersonaJuridicaBSP  extends GuiPersonaJuridica {

	public GuiPersonaJuridicaBSP() throws Exception {
		super();
	}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizPersonaFisicaBSP();
	}
	
	@Override
	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormPersonaJuridicaBSP.class;
	}
	

	public BizPersonaJuridicaBSP GetccDato() throws Exception {
		return (BizPersonaJuridicaBSP) this.getRecord();
	}
}
