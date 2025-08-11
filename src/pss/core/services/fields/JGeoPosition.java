package pss.core.services.fields;

import pss.core.tools.GeoPosition;

public class JGeoPosition extends JObject<GeoPosition> {

	public GeoPosition getValue() throws Exception {
		preset();
		return getGeoPositionValue();
	};

	public GeoPosition getRawValue() {
		return getGeoPositionValue();
	};

	private GeoPosition getGeoPositionValue() {
		if (this.getObjectValue()==null) return null;
		return ((GeoPosition) this.getObjectValue());
	}

	public JGeoPosition() {
	}

	public JGeoPosition(GeoPosition latitudeLongitude) {
		setValue(latitudeLongitude);
	}

	public JGeoPosition(String latitudeLongitude) {
		setValue(latitudeLongitude);
	}

	public JGeoPosition(double latitude, double longitude) {
		super.setValue(new GeoPosition(latitude, longitude));
	}

	@Override
	public void setValueFormUI(String zVal) {
		if (zVal==null) {
			super.setValue(new GeoPosition(0.0, 0.0));
			return;
		}
		int comma=zVal.indexOf(",");
		if (comma==-1) {
			super.setValue(new GeoPosition(0.0, 0.0));
			return;
		}
		super.setValue(new GeoPosition(Double.parseDouble(zVal.substring(0, comma).equals("")?"0":zVal.substring(0, comma)), Double.parseDouble(zVal.substring(comma+1).trim().equals("")?"0":zVal.substring(comma+1))));
	}

	@Override
	public void setValue(String zVal) {
		if (zVal==null) {
			super.setValue(new GeoPosition(0.0, 0.0));
			return;
		}
		int comma=zVal.indexOf(",");
		if (comma==-1) {
			super.setValue(new GeoPosition(0.0, 0.0));
			return;
		}
		super.setValue(new GeoPosition(Double.parseDouble(zVal.substring(0, comma)), Double.parseDouble(zVal.substring(comma+1))));
	}

	@Override
	public String toRawString() throws Exception {
		if (this.isNull()) return "";
		return getGeoPositionValue().getLatitude()+","+getGeoPositionValue().getLongitude();
	}

	@Override
	public String getObjectType() {
		return JObject.JGEOPOSITION;
	}

	@Override
	public Class getObjectClass() {
		return GeoPosition.class;
	}

	// ////////////////////////////////////////////////////////////////////////////
	//
	// FORMATTING METHODS
	//
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * Answers a <code>String</code> representation of the value this <code>JObject</code> holds, formatted to the user which is going to see it. It does the same as the <code>#toFormattedString()</code> method, except in that it does not invoke the <code>#Pre()</code> method first.
	 * <p>
	 * This method may be overridden by subclasses to provide appropriate representations, depending on the data type they represent.
	 * 
	 * @return a formatted <code>String</code> representation of the value this <code>JObject</code> holds
	 */
	@Override
	public String toRawFormattedString() throws Exception {
		return toRawString();
	}

}
