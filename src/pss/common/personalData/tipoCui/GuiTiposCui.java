package  pss.common.personalData.tipoCui;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiTiposCui extends JWins {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiTiposCui() throws Exception {
  }

  @Override
	public int     GetNroIcono() throws Exception  { return 10036; }
  @Override
	public String  GetTitle()    throws Exception  { return "Tipos Fiscales"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiTipoCui.class; }

  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nuevo Registro" );
  }

  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("tipo_cui");
    zLista.AddColumnaLista("descripcion");
  }


}
