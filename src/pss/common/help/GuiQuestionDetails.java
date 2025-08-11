package  pss.common.help;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiQuestionDetails extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiQuestionDetails() throws Exception {
  }


  @Override
	public int     GetNroIcono() throws Exception  { return 724; } 
  @Override
	public String  GetTitle()    throws Exception  { return "Detalles Pregunta"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiQuestionDetail.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  @Override
	public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Registro" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("step");
    zLista.AddColumnaLista("page");
    zLista.AddColumnaLista("action");
    zLista.AddColumnaLista("x");
    zLista.AddColumnaLista("y");
    zLista.AddColumnaLista("width");
    zLista.AddColumnaLista("height");
    zLista.AddColumnaLista("id");
    zLista.AddColumnaLista("typePos");
    zLista.AddColumnaLista("type");
    zLista.AddColumnaLista("help");
  }

}
