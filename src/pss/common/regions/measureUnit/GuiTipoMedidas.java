package  pss.common.regions.measureUnit;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiTipoMedidas extends JWins {

  public GuiTipoMedidas() throws Exception {
  }

  @Override
	public Class<? extends JWin> GetClassWin() { return GuiTipoMedida.class; }
  @Override
	public int GetNroIcono() throws Exception { return 899; }
  @Override
	public String GetTitle() throws Exception { return "Tipos de Unidades"; }

  @Override
	public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Tipo de Unidad de Medida" );
  }

  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
     zLista.AddIcono("");
     zLista.AddColumnaLista("descripcion");
  }

}

