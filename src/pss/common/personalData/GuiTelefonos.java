package  pss.common.personalData;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiTelefonos extends JWins {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiTelefonos() throws Exception {
  }

  @Override
	public int     GetNroIcono() throws Exception  { return 762; }
  @Override
	public String  GetTitle()    throws Exception  { return "Teléfonos"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiTelefono.class; }

  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nuevo Registro" );
  }

  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
     zLista.AddIcono("");
     zLista.AddColumnaLista("descr_tipo_tel");
     zLista.AddColumnaLista("numero");
     zLista.AddColumnaLista("observacion");
  }
}
