package  pss.common.event.mailing;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiMailingPersonas extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiMailingPersonas() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10032; } 
  public String  GetTitle()    throws Exception  { return "Mailing destinatarios"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiMailingPersona.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
//    addActionNew( 1, "Nuevo Registro" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddColumnaLista("codigo");
    zLista.AddColumnaLista("descripcion");
    zLista.AddColumnaLista("mail");
  }
  
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
  	zFiltros.addEditResponsive("Codigo", "codigo");
  	zFiltros.addEditResponsive("Descripcion", "descripcion").setOperator("ilike");
  	zFiltros.addEditResponsive("Mail", "mail").setOperator("ilike");
  	super.ConfigurarFiltros(zFiltros);
  }
	

}
