package  pss.common.regions.divitions;

import pss.common.components.BizCompInstalados;
import pss.common.personalData.GuiTiposDoc;
import pss.common.personalData.tipoCui.GuiTiposCui;
import pss.common.regions.cities.GuiCities;
import pss.common.regions.measureUnit.GuiTipoMedidas;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiPais extends JWin {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiPais() throws Exception {
	}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizPais();
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 1;
	}

	@Override
	public String GetTitle() throws Exception {
		return "País Operativo";
	}

	@Override
	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormPais.class;
	}

	@Override
	public String getKeyField() throws Exception {
		return "pais";
	}

	@Override
	public String getDescripField() {
		return "descripcion";
	}

	// -------------------------------------------------------------------------//
	// Mapeo las acciones con las operaciones
	// -------------------------------------------------------------------------//
	@Override
	public void createActionMap() throws Exception {
		addActionQuery(1, "Consultar");
		addActionUpdate(2, "Modificar");
		addActionDelete(3, "Eliminar");

		// addAction( 15, "Monedas" , null, 41, false, false, true, "Group");
		addAction(16, "Tipos Documentos", null, 765, false, false, true, "Group");
		addAction(17, "Divisiones", null, 73, false, false, true, "Group");
		addAction(18, "Estado Civil", null, 987, false, false, true, "Group");
		addAction(19, "Unidades de Medida", null, 899, false, false, true, "Group");
		addAction(20, "Ciudades", null, 1, false, false, true);
		addAction(22, "Tipos Fiscales", null, 765, false, false);
	}
	@Override
	public boolean OkAction(BizAction a) throws Exception {
//		if (a.getId()==17) {
//			if (!GetcDato().GetDivision().equals(""))
//				a.SetTitle(GetcDato().GetDivision());
//		}
		return super.OkAction(a);
	}
	
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		// if (a.getId()==15) return new JActWins(ObtenerMonedas());
		if (a.getId()==16) return new JActWins(ObtenerTiposDocs());
		if (a.getId()==17) return new JActWins(ObtenerProvincias());
		if (a.getId()==18) return new JActWins(ObtenerTiposEstadoCivil());
		if (a.getId()==19) return new JActWins(ObtenerTipoMedidas());
		if (a.getId()==20) return new JActWins(this.getCiudades());
		if (a.getId()==22) return new JActWins(getTiposCui());
		return null;
	}

	public JWins ObtenerTiposEstadoCivil() throws Exception {
		JWins wins=BizCompInstalados.CorePersonasGuiEstadosCiviles();
		wins.getRecords().addFilter("pais", GetcDato().pPais.getValue());
		return wins;
	}
	
	public JWins getCiudades() throws Exception {
		GuiCities g=new GuiCities();
		g.getRecords().addFilter("country", this.GetcDato().GetPais());
		return g;
	}

	public JWins ObtenerTipoMedidas() throws Exception {
		return new GuiTipoMedidas();
	}

	// -------------------------------------------------------------------------//
	// Devuelvo el dato ya casteado
	// -------------------------------------------------------------------------//
	public BizPais GetcDato() throws Exception {
		return (BizPais) this.getRecord();
	}

	@Override
	public String GetIconFile() throws Exception {
		return GetcDato().getIconFile();
	}

	public JWins ObtenerTiposDocs() throws Exception {
		GuiTiposDoc wins=new GuiTiposDoc();
		wins.getRecords().addFilter("pais", GetcDato().pPais.getValue());
		return wins;
	}

	public JWins getTiposCui() throws Exception {
		GuiTiposCui wins=new GuiTiposCui();
		wins.getRecords().addFilter("pais", GetcDato().pPais.getValue());
		return wins;
	}

	// public JWins ObtenerMonedas() throws Exception {
	// GuiMonedaPaises wins = new GuiMonedaPaises();
	// wins.getRecords().addFilter("pais", GetcDato().pPais.getValue());
	// wins.SetVision(GuiMonedaPais.CURRENCY);
	// return wins;
	// }

	public JWins ObtenerProvincias() throws Exception {
		GuiProvincias wins=new GuiProvincias();
		wins.getRecords().addFilter("pais", GetcDato().pPais.getValue());
		return wins;
	}

//	@Override
//	public boolean ifAcceptRefresh(JWinEvent e, boolean zGlobal) throws Exception {
//		if (super.ifAcceptRefresh(e, zGlobal)) return true;
//		JRecord origen=(JRecord) e.GetOrigenBaseWin().GetBaseDato();
//		if (!e.ifDeleteOk()&&!e.ifRefreshDeleteOk()) return ((origen instanceof BizMonedaCotizacion)&&origen.hasReferenceTo(GetcDato()));
//		return false;
//	}

//	@Override
//	public boolean ifAcceptEmbeddedListsRefresh(JWinEvent e) throws Exception {
//		return true;
//	}

}
