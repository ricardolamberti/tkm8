package pss.bsp.contrato.detalle.nivel;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiNiveles extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiNiveles() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15010; } 
  public String  GetTitle()    throws Exception  { return "Niveles"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiNivel.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Nivel" );
  }

  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	if (GetVision().equals(BizNivel.NIVEL_ONLYMETA)) {
   	 zLista.AddColumnaLista("valor");
     zLista.AddColumnaLista("color");
     zLista.AddColumnaLista("elegido");
     return;
  	}
  	zLista.AddColumnaLista("valor");
    zLista.AddColumnaLista("color");
    zLista.AddColumnaLista("tipo_premio_descr");
    zLista.AddColumnaLista("premio");
    zLista.AddColumnaLista("estimado");
    zLista.AddColumnaLista("elegido");
  }




}