package  pss.common.regions.divitions;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiLocalidades extends JWins {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiLocalidades() throws Exception {
    SetNroIcono       ( 1 );
  }


  @Override
	public int     GetNroIcono() throws Exception  { return 1; }
  @Override
	public String  GetTitle()    throws Exception  { return "Sub-Sub Divisiones"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiLocalidad.class; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nuevo Registro" );
  }

//  public boolean OkAction(BizAction zAct) throws Exception {
//    if (zAct.GetId() == 1 ) if (this.getParentJBD()==null || !(this.getParentJBD() instanceof BizCiudad)) return false;
//    return true;
//  }

  //-------------------------------------------------------------------------//
  // Configuro las columnas que quiero mostrar en la grilla
  //-------------------------------------------------------------------------//
  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("localidad");
//    zLista.SetColumnaBusqueda("localidad");
  }

}
