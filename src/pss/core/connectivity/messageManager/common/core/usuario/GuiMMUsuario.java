package pss.core.connectivity.messageManager.common.core.usuario;

import pss.common.security.GuiUsuario;
import pss.core.services.records.JRecord;

public class GuiMMUsuario extends GuiUsuario {

	public GuiMMUsuario() throws Exception {
		super();
	}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizMMUsuario();
	}
	
}
