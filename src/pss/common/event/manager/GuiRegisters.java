package pss.common.event.manager;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiRegisters extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiRegisters() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 948; } 
  public String  GetTitle()    throws Exception  { return "Eventos Registrados"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiRegister.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Lsitener" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
//	  zLista.AddColumnaLista("descr_biz");
	  zLista.AddColumnaLista("Listen Entidad", "descr_listenentidad");
	  zLista.AddColumnaLista("Listen Usuario", "listen_usuario");
	  zLista.AddColumnaLista("descr_evento");
	  zLista.AddColumnaLista("descr_action");
	  zLista.AddColumnaLista("Sender Sucursal ", "descr_sendernodo");
	  zLista.AddColumnaLista("Sender Usuario ", "sender_user");
//	  zLista.AddColumnaLista("Filtros", "descr_filtros");
	  zLista.AddColumnaLista("Transformer", "descr_transformer");
	  
//	  zLista.AddColumnaLista("descr_action_id");
  }

}
