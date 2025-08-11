package  pss.common.components;

import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiDirectorios extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiDirectorios() throws Exception {
	}

	@Override
	public JRecords<? extends JRecord> ObtenerDatos() throws Exception {
		return new BizDirectorios();
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiDirectorio.class;
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 402;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Directorios";
	}

	@Override
	public void OnReadAllDatos() throws Exception {
		String sDirectorio=this.getRecords().getFilterValue("directorio");
		GuiClases oClases=new GuiClases();
		oClases.getRecords().addFilter("directorio", sDirectorio);
		oClases.readAll();
		this.AppendDatos(oClases);
	}

	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("descripcion");
	}

}
