package pss.common.documentos.docElectronico;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiDocElectronicos extends JWins {



	  /**
	   * Constructor de la Clase
	   */
	  public GuiDocElectronicos() throws Exception {
	  }


	  public int     GetNroIcono() throws Exception  { return 10020; } 
	  public String  GetTitle()    throws Exception  { return "Documentos"; }
	  public Class<? extends JWin>  GetClassWin()                   { return GuiDocElectronico.class; }
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
	    zLista.AddColumnaLista("File", "titulo");
	  }

		public boolean hasNavigationBar() throws Exception {
			return false;
		}

	}
