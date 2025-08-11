package pss.common.agenda.turno;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiTurnos extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiTurnos() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 2003; } 
  public String  GetTitle()    throws Exception  { return "Manejador turnos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiTurno.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Manejador" );
  }


 
  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("descripcion");
    //  super.ConfigurarColumnasLista(zLista);
  }
  
  @Override
  protected void asignFilterByControl(JFormControl control) throws Exception {
  	if (control.getName().equals("tipo")) {
  		SetVision(control.getValue());
  		return;
  	}
  	super.asignFilterByControl(control);
  }
  
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
  	zFiltros.addCheckResponsive(null, "tipo", "MENSUAL", "DIARIO").setRefreshForm(true);
  	super.ConfigurarFiltros(zFiltros);
  }

}
