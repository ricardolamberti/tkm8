package pss.core.ui.components;

import org.jdesktop.swingx.JXMapKit;

import pss.core.win.JWins;

public class JPssGoogleMap extends JXMapKit {

	public final static String  POINTS = "points";
	public final static String  POLYGON = "polygon";
	int myZoom;
	String mode=POINTS;
	JWins points;
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 879954336564744042L;

	/***************************************************************************
	 * CONSTRUCTOR
	 **************************************************************************/
	public JPssGoogleMap() {
		super();
	}

	public void setEditable(boolean b) {
		this.setEnabled(b);
	}

	public boolean isEditable() {
		return this.isEnabled();
	}
	
	public int getZoom() {
		return myZoom;
	}

	@Override
	public void setZoom(int zoom) {
		this.myZoom = zoom;
		super.setZoom(zoom);
	}

	@SuppressWarnings("unchecked")
	public void setPoints(JWins waypoints) {
		points = waypoints;
    //create a WaypointPainter to draw the points
		
//	    WaypointPainter painter = new WaypointPainter();
//	    painter.setWaypoints(points);
//	    getMainMap().setOverlayPainter(painter);
	}


	public JWins getPoints() {
		return points;
	}



}
