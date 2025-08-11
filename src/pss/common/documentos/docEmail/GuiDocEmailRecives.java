package pss.common.documentos.docEmail;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiDocEmailRecives extends JWins {



	  /**
	   * Constructor de la Clase
	   */
	  public GuiDocEmailRecives() throws Exception {
	  }


//	  public int     GetNroIcono() throws Exception  { return 10020; } 
//	  public String  GetTitle()    throws Exception  { return "Correo externo"; }
//	  public Class<? extends JWin>  GetClassWin() { return GuiDocEmailRecive.class; }
	  /**
	   * Mapeo las acciones con las operaciones
	   */
	  public void createActionMap() throws Exception {
//	    addActionNew( 1, "Nuevo Registro" );
	  }



	  /**
	   * Configuro las columnas que quiero mostrar en la grilla
	   */
	  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
	    zLista.AddIcono("");
	    zLista.AddColumnaLista("titulo");
	    zLista.AddColumnaLista("autor");
	    zLista.AddColumnaLista("Fecha recepción","fecha");
	    zLista.AddColumnaLista("descr_tramite");
	    zLista.AddColumnaLista("leido");
	  }

	}
