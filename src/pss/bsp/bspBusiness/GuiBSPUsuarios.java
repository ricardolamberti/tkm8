package pss.bsp.bspBusiness;

import pss.common.security.GuiUsuarios;
import pss.core.win.JWin;
import pss.core.winUI.lists.JWinList;

public class GuiBSPUsuarios extends GuiUsuarios {

	public GuiBSPUsuarios() throws Exception {
		super();
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiBSPUsuario.class;
	}

	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("usuario").setActionOnClick(this.hasDropListener() ? 4 : 1);
		zLista.AddColumnaLista("descripcion");
		zLista.AddColumnaLista("fecha_ultimo_ingreso");
		zLista.AddColumnaLista("origen_login");
		getRecords().clearOrderBy();
		getRecords().addOrderBy("usuario");
	}

}
