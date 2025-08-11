package pss.common.personalData.aditionals;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiPersonaAdicionales extends JWins {



  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiPersonaAdicionales() throws Exception {
  }

  @Override
	public Class<? extends JWin> GetClassWin() { return GuiPersonaAdicional.class; }
  @Override
	public String GetTitle() { return "Tarjetas"; }
  @Override
	public int GetNroIcono() { return 22; }


  @Override
	public void createActionMap() throws Exception {
  	addActionNew     ( 1, "Nuevo Campo" );
  }
  
  
  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("descr_tipo");
    zLista.AddColumnaLista("observacion");
  }
  
  
}
