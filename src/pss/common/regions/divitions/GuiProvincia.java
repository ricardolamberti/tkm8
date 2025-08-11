package  pss.common.regions.divitions;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiProvincia extends JWin {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiProvincia() throws Exception {
	}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizProvincia();
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 73;
	}

	@Override
	public String GetTitle() throws Exception {
		return "División";
	}

	@Override
	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormProvincia.class;
	}

	@Override
	public String getKeyField() throws Exception {
		return "provincia";
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
		addAction(10, "Departamento/partido", null, 10017, false, false);
		addAction(30, "Ciudades", null, 10017, false, false);
	}

	// public JWins ObtenerCiudades() throws Exception {
	// JEnlace oEnlace = new JEnlace();
	// oEnlace.AddValor("pais", "pais", GetcDato().pPais.getValue());
	// oEnlace.AddValor("provincia", "provincia",
	// GetcDato().pProvincia.getValue());
	// GuiCiudades oCiudades = new GuiCiudades();
	// oCiudades.SetEnlazado(oEnlace);
	// oCiudades.getRecords().setParent(GetcDato());
	// return oCiudades;
	// }

	// private String getTipoCiudad() throws Exception{
	// String sPais = GetcDato().pPais.getValue();
	// BizPais oPais = new BizPais();
	// oPais.Read(sPais);
	// return oPais.GetCiudad();
	// }

	public BizProvincia GetcDato() throws Exception {
		return (BizProvincia) this.getRecord();
	}

	@Override
	public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId()==30)	return new JActWins(this.getCiudades());
		if (a.getId()==10)	return new JActWins(this.getDepartamentos());
		return super.getSubmit(a);
	}

	public JWins getCiudades() throws Exception {
		JWins records=new GuiCiudades();
		records.getRecords().addFilter("pais", this.GetcDato().GetPais());
		records.getRecords().addFilter("provincia", this.GetcDato().GetProvincia());
		// records.setParent(this);
		records.addOrderAdHoc("ciudad", "ASC");

		return records;
	}
	public JWins getDepartamentos() throws Exception {
		JWins records=new GuiDepartamentos();
		records.getRecords().addFilter("pais", this.GetcDato().GetPais());
		records.getRecords().addFilter("provincia", this.GetcDato().GetProvincia());
		// records.setParent(this);
		records.addOrderAdHoc("nombre", "ASC");

		return records;
	}
	
}
