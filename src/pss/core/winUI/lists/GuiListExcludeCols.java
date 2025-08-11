package pss.core.winUI.lists;

import pss.common.security.BizUsuario;
import pss.core.tools.collections.JList;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;

public class GuiListExcludeCols extends JWins {

	/**
	 * Constructor de la Clase
	 */
	public GuiListExcludeCols() throws Exception {
		this.getRecords().addOrderBy("col_order");
	}

	public int GetNroIcono() throws Exception {
		return 5012;
	}

	public String GetTitle() throws Exception {
		return "Configurar Columnas";
	}

	public Class<? extends JWin> GetClassWin() {
		return GuiListExcludeCol.class;
	}

	/**
	 * Mapeo las acciones con las operaciones
	 */
	public void createActionMap() throws Exception {
		// addActionNew( 1, "Nuevo Registro" );
		addAction(10, "Inicializar", null, 5012, true, true);
		addAction(15, "Remover Columnas", null, 5012, true, true);
		addAction(20, "Borrar Cache", null, 5012, true, true);
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)
			return new JActSubmit(this, 10) {
				public void execSubmit() throws Exception {
					initialize();
				}
			};
		if (a.getId() == 15)
			return new JActSubmit(this, 15) {
				public void execSubmit() throws Exception {
					deInitialize();
				}
			};

		if (a.getId() == 20)
			return new JActSubmit(this, 20) {
				public void submit() throws Exception {
					cleanCache();
				}
			};
		return super.getSubmitFor(a);
	}

	protected void cleanCache() {
		BizListExcludeCol.cleanCache();
	}

	public void initialize() throws Exception {

		String clase = this.getRecords().getFilterValue("class_name");
		String vision = this.getRecords().getFilterValue("vision_name");

		JList<JColumnaLista> list = buildList(clase, vision);
		
		BizListExcludeCol bcol = new BizListExcludeCol();
		bcol.addColumns(clase, vision, list);

	}

	private JList<JColumnaLista> buildList(String clase, String vision) throws InstantiationException, IllegalAccessException, ClassNotFoundException, Exception {
		JWins wins = (JWins) Class.forName(clase).newInstance();
		wins.SetVision(vision);

		wins.setSelectedCell("1");
		wins.getSelectedCell();
		JList<JColumnaLista> list = wins.getColWinList().GetColumnasLista();
		return list;
	}

	public void deInitialize() throws Exception {

		String clase = this.getRecords().getFilterValue("class_name");
		String vision = this.getRecords().getFilterValue("vision_name");

		JList<JColumnaLista> list = buildList(clase, vision);

		BizListExcludeCol bcol = new BizListExcludeCol();
		bcol.removeColumns(clase, vision, list);

	}

	/**
	 * Configuro las columnas que quiero mostrar en la grilla
	 */
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("col_desc");
		if (BizUsuario.IsAdminCompanyUser())
			zLista.AddColumnaLista("is_admin");
		zLista.AddColumnaLista("excluded");
	}

}
