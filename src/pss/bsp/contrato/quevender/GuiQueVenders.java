package pss.bsp.contrato.quevender;

import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JOrderedMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.totalizer.JTotalizer;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormSwingEdit;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JGrupoColumnaLista;
import pss.core.winUI.lists.JWinList;

public class GuiQueVenders extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiQueVenders() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15011; } 
  public String  GetTitle()    throws Exception  { return "Extra Info"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiQueVender.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {

  }

	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}

@Override
public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
	zLista.AddColumnaLista("descripcion");
}

}
