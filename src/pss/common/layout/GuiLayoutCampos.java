package pss.common.layout;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiLayoutCampos extends JWins {



  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiLayoutCampos() throws Exception {
  }

  @Override
	public int     GetNroIcono() throws Exception  { return 201; }
  @Override
	public String  GetTitle()    throws Exception  { return "Campos"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiLayoutCampo.class; }

  @Override
	public void createActionMap() throws Exception {
    this.addActionNew ( 1, "Nuevo Registro" );
  }

  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("descr_sector");
    zLista.AddColumnaLista("tipo_campo");
    zLista.AddColumnaLista("descr_campo");
    zLista.AddColumnaLista("y");
    zLista.AddColumnaLista("x");
    zLista.AddColumnaLista("pos", "position");
    zLista.AddColumnaLista("long_max");
    zLista.AddColumnaLista("alineacion");
  }
  
  @Override
  public String getPreviewSplitPos() throws Exception {
  	return "70%";
  }
  
}
