package pss.core.maps;

import pss.core.tools.GeoPosition;

public interface GeoPositionable {

	public GeoPosition getGeoPosition(String field) throws Exception;
}
