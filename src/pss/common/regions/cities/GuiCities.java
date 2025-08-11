package  pss.common.regions.cities;

import pss.common.regions.divitions.GuiPaisesLista;
import pss.common.security.BizUsuario;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiCities extends JWins {

	/**
	 * Constructor de la Clase
	 */
	public GuiCities() throws Exception {
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 5107;
	}

	@Override
	public String GetTitle() throws Exception {
		return BizUsuario.getUsr().getObjBusiness().getLabelRegionOrigenPlural();
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiCity.class;
	}

	/**
	 * Mapeo las acciones con las operaciones
	 */
	@Override
	public void createActionMap() throws Exception {
		addActionNew(1, "Nueva Cuidad");
	}

	/**
	 * Configuro las columnas que quiero mostrar en la grilla
	 */
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		if (!this.getRecords().filterHasValue("country"))
			zLista.AddColumnaLista("descr_country");
		zLista.AddColumnaLista("descripcion");
		zLista.AddColumnaLista("codigo");
	}

	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addWinLovResponsive("País", "country", new JControlWin() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return new GuiPaisesLista();
			}
		});
		zFiltros.addEditResponsive("Cuidad", "CHAR", "descripcion");
	}

//	@Override
//	public void readAll() throws Exception {
//		if (this.getRecords().getOrderBy()==null) this.getRecords().addOrderBy("descripcion");
//		super.readAll();
//	}

}
