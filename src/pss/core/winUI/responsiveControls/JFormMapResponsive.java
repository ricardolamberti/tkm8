package pss.core.winUI.responsiveControls;

import pss.core.tools.GeoPosition;
import pss.core.win.JWins;

public class JFormMapResponsive extends JFormControlResponsive {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JWins oWins;
	String mapmodo;
	public String getMapModo() {
		return mapmodo;
	}

	public void setMapModo(String modo) {
		this.mapmodo = modo;
	}

	GeoPosition oDato;

	public GeoPosition getAddressLocation() throws Exception {
		updateFromProp();
		return oDato;
	}

	public void setAddressLocation(GeoPosition oDato) throws Exception {
		this.getProp().setValue(oDato);
		updateFromProp();
	}

	// -------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------//
	public JFormMapResponsive() {
	}

	// -------------------------------------------------------------------------//
	// Blanqueo el campo
	// -------------------------------------------------------------------------//
	@Override
	public void clear() throws Exception {
		oProp.setNull();
		updateFromProp();
//		oDato=(new GeoPosition(0.0, 0.0));
	}

	

//	@Override
//	public void BaseToControl(String zModo, boolean userRequest) throws Exception {
//		if (this.getProp()==null) return;
//		if (zModo.equals("A")) {
//			setValue(this.getProp().toRawString());
//		} else setValue(this.getProp().toFormattedString());
//	}

	// -------------------------------------------------------------------------//
	// Obtengo el valor del campo
	// -------------------------------------------------------------------------//
	@Override
	public String getSpecificValue() throws Exception {
		updateFromProp();
		if (oDato==null) return "";
		return oDato.getLatitude()+","+oDato.getLongitude();
	}

	// -------------------------------------------------------------------------//
	// Obtengo el valor del campo
	// -------------------------------------------------------------------------//
	@Override
	public boolean hasValue() {
		return true;
	}

	@Override
	public void setValue(String zVal) throws Exception {
		setValue(zVal);
    updateFromProp();
	}

//	@Override
//	public boolean isVisible() {
//		return this.oMap.isVisible();
//	}

	

	@SuppressWarnings("unchecked")
	public void setWins(JWins wins) throws Exception {
		if (!wins.isGeoPositionable()) {
			throw new Exception("No contiene campos que especifiquen su posicion global");
		}
		oWins=wins;
	}

	public JWins getWins() {
		return oWins;
	}
	

	private void updateFromProp() throws Exception {
		if (getProp()==null) return;
		if (getProp().isNull()) {
			oDato=(new GeoPosition(0.0, 0.0));
			return;
		}
		String out = "";
		if (getModo().equals("A")) {
			out=(this.getProp().toRawString());
		} else 
			out=(this.getProp().toFormattedString());

		int comma=out.indexOf(",");
		if (comma==-1) {
			oDato=(new GeoPosition(0.0, 0.0));
			return;
		}
		oDato=(new GeoPosition(Double.parseDouble(out.substring(0, comma)), Double.parseDouble(out.substring(comma+1))));

	}

}
