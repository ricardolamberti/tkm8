package  pss.common.regions.measureUnit;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiUnidadMedidas extends JWins {

  public GuiUnidadMedidas() throws Exception {
  }

  @Override
	public Class<? extends JWin> GetClassWin() { return GuiUnidadMedida.class; }
  @Override
	public int GetNroIcono() throws Exception { return 100; }
  @Override
	public String GetTitle() throws Exception { return "Unidades de Medida"; }

  @Override
	public void createActionMap() throws Exception {
    addActionNew( 1, "Nueva Unidad de Medida" );
  }

  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
     zLista.AddIcono("");
     zLista.AddColumnaLista("descripcion");
     zLista.AddColumnaLista("abreviatura");
     zLista.AddColumnaLista("factor");
  }

}

