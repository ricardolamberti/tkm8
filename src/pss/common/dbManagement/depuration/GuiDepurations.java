package  pss.common.dbManagement.depuration;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiDepurations extends JWins {

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiDepurations() throws Exception {
  }

  @Override
	public int     GetNroIcono() throws Exception  { return 913; }
  @Override
	public String  GetTitle()    throws Exception  { return "Depuraciones"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiDepuration.class; }

  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nuevo Registro" );
  }

  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("descr_modulo");
    zLista.AddColumnaLista("descr_entidad");
    zLista.AddColumnaLista("dias_sin_depurar");
  }

}
