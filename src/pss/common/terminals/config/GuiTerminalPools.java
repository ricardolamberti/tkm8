package pss.common.terminals.config;

import pss.common.regions.nodes.GuiNodos;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiTerminalPools extends JWins {

	/**
	 * Constructor de la Clase
	 */
	public GuiTerminalPools() throws Exception {
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 890;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Pooles de Terminales";
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiTerminalPool.class;
	}

	/**
	 * Mapeo las acciones con las operaciones
	 */
	@Override
	public void createActionMap() throws Exception {
		addActionNew(1, "Nuevo Registro");
	}

	/**
	 * Configuro las columnas que quiero mostrar en la grilla
	 */
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("descr_nodo");
		zLista.AddColumnaLista("description");
	}

  public void ConfigurarFiltros(JFormFiltro filter) throws Exception {
  	filter.addEditResponsive("company", "company");
  	filter.addComboResponsive("Sucursal", "nodo", new JControlCombo() {
  		public JWins getRecords(Object source, boolean one) throws Exception {
  			return getNodos(source, one);
  		}
  	}, true);
  }
  
  private JWins getNodos(Object source, boolean one) throws Exception {
  	JFormControl c = (JFormControl) source;
  	GuiNodos g = new GuiNodos();
  	g.getRecords().addFilter("company", c.findControl("company").getValue());
  	return g;
  }

  
}
