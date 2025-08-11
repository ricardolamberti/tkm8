package pss.common.documentos;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiDocums extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiDocums() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10020; } 
  public String  GetTitle()    throws Exception  { return "Documentos"; }
  public Class<? extends JWin>  GetClassWin()    { return GuiDocum.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
//     addActionNew( 1, "Nuevo Registro" );
  }

  @Override
  public boolean OkAction(BizAction act) throws Exception {
  	return super.OkAction(act);
  }

//  public JWin createNewDoc() throws Exception {
//		return new GuiDocLocal(); 
//	}
  
	@Override
	public JAct getSubmit(BizAction a) throws Exception {
//		if (a.getId()==1) return new JActNew(this.createNewDoc(), 0);
		return this.getSubmitFor(a);
	}

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("titulo");
    zLista.AddColumnaLista("usuario");
  }
  
	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
    
	}
	
  

}
