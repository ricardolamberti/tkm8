package pss.www.ui;

import java.awt.Component;
import java.awt.Dimension;

import pss.core.maps.GeoPositionable;
import pss.core.tools.GeoPosition;
import pss.core.tools.collections.JIterator;
import pss.core.ui.components.JPssGoogleMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.responsiveControls.JFormMapResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.content.generators.JXMLContent;

public class JWebMapResponsive extends JWebViewEditComponent implements JWebControlInterface {

	private GeoPosition geoPosition;
	private int zoom;
	private int icono;
	private String mode;
	JWins waypoints;

	public JWins getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(JWins waypoints) {
		this.waypoints = waypoints;
	}
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	public int getIcono() {
		return icono;
	}

	public void setIcono(int icono) {
		this.icono = icono;
	}

	@Override
	protected Dimension getDefaultSize() {
		return new Dimension(150, 150);
	}

	@Override
	public String getSkinStereotype() {
		return "gmap";
	}

	@Override
	public void setValueFromDBString(String string) throws Exception {
		int comma=string.indexOf(",");
		if (comma==-1) {
			geoPosition=new GeoPosition(0.0, 0.0);
			return;
		}
		geoPosition=new GeoPosition(Double.parseDouble(string.substring(0, comma)), Double.parseDouble(string.substring(comma+1)));
	}

	protected JWebAction getActionDefault(JWin zWin) throws Exception {
		BizAction action = null;
		String uniqueId = zWin.getDobleClickByUniqueId();
		if (uniqueId!=null) {
			action = zWin.findActionByUniqueId(uniqueId);
		} 
		if (action==null)	action = zWin.findAction(zWin.GetDobleClick());
		if (action==null) action=zWin.findAction(JWin.ACTION_DROP);
		if (action==null)	action=zWin.findAction(JWin.ACTION_QUERY);
		if (action==null) return null;
		return JWebActionFactory.createViewAreaAndTitleAction(action, null, false, null);
  }
	
	@Override
	protected void widgetToXML(JXMLContent zContent) throws Exception {
		zContent.startNode("gmap");
		boolean posicionado=false;
		if (!(geoPosition.getLatitude()==0 && geoPosition.getLongitude()==0)) {
			zContent.setAttribute("lat", geoPosition.getLatitude());
			zContent.setAttribute("lng", geoPosition.getLongitude());
			zContent.setAttribute("zoom", getZoom());
			posicionado=true;
		}
		else if (waypoints==null) {
			zContent.setAttribute("lat", 0);
			zContent.setAttribute("lng", 0);
			zContent.setAttribute("zoom", 1);
		}
		zContent.setAttribute("modo", getMode());

		if (waypoints!=null) {
				JIterator<JWin> it = waypoints.getStaticIterator();
				boolean first=true;
				while (it.hasMoreElements()) {
					JWin oWin=it.nextElement();
					if (oWin.isGeoPositionable()) {
						GeoPosition gp=((GeoPositionable) oWin.getRecord()).getGeoPosition(null);
						if (gp==null&&getMode().equals(JPssGoogleMap.POLYGON)) continue;
//							if (!posicionado) {
//								zContent.setAttribute("mapdir", oWin.getDireccionMapa());
//								zContent.setAttribute("mapname", oWin.getNombreMapa());
//								posicionado=true;
//							}
							zContent.startNode("waypoint");
								if (gp!=null) {
									zContent.setAttribute("lat", gp.getLatitude());
									zContent.setAttribute("lng", gp.getLongitude());
								}
								zContent.setAttribute("mapdir", oWin.getDireccionMapa());
								zContent.setAttribute("mapname", oWin.getNombreMapa());
								
								JWebAction actLink = getActionDefault(oWin);
									zContent.startNode("link");
								//zContent.setAttribute("link",actLink.asURL());
									zContent.startNode("image");
										zContent.setAttribute("source", "pss_icon");
										zContent.setAttribute("file",oWin.getIconoMapa());
									zContent.endNode("image");
								zContent.endNode("link");
							zContent.endNode("waypoint");
					
						

					}
				}
			
		}
		zContent.endNode("gmap");

	}

	public void onShow(String mode) throws Exception {
	}

	@Override
	public String getValueAsUIString() throws Exception {
		return this.getValue().toString();
	}

	@Override
	public void setValueFromUIString(String zValue) {
		try {
			setValueFromDBString(zValue);
		} catch (Exception e) {
		}
	}

	@Override
	public String getValueAsDBString() throws Exception {
		return geoPosition.getLatitude()+","+geoPosition.getLongitude();
	}

	@Override
	public Object getValue() {
		return geoPosition;
	}

	@Override
	public void setValue(Object zValue) throws Exception {
		geoPosition=(GeoPosition) zValue;
	}

	@Override
	public void takeAttributesForm(JWebViewComposite webparent,Component comp) throws Exception {
		JPssGoogleMap map=(JPssGoogleMap) comp;
		geoPosition=GeoPosition.setSwingGeoPosition(map.getAddressLocation());
		zoom=map.getZoom(); 
		mode=map.getMode();
		waypoints=map.getPoints();
		super.takeAttributesForm(webparent,comp);
	}

	public static JWebMapResponsive create(JWebViewComposite parent, JFormMapResponsive oWinControl) throws Exception {
		JWebMapResponsive webMap=oWinControl.getAddressLocation()==null?new JWebMapResponsive(0,0):new JWebMapResponsive(oWinControl.getAddressLocation().getLatitude(), oWinControl.getAddressLocation().getLongitude());
		webMap.takeAttributesFormControlResponsive(oWinControl);
		webMap.setMinHeightResponsive(oWinControl.getHeight());
		webMap.setMinWidthResponsive(oWinControl.getWidth());
		webMap.setWaypoints(oWinControl.getWins());
		webMap.setMode(oWinControl.getMapModo());

		if (parent!=null) parent.addChild(oWinControl.getName(), webMap);
		return webMap;
	}

	JWebMapResponsive(double latX, double latY) {
		geoPosition=new GeoPosition(latX, latY);
	}

	@Override
	public void destroy() {
	}

	@Override
	public String getComponentType() {
		return "gmap_responsive";
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom=zoom;
	}

	public GeoPosition getGeoPosition() {
		return geoPosition;
	}

	public void setGeoPosition(GeoPosition geoPosition) {
		this.geoPosition=geoPosition;
	}


	@Override
	protected String getState() throws Exception {
		return (this.getForm().isModoConsulta()) ? null : "edit";
	}

	
}
