package pss.core.reports;

import java.util.HashMap;
import java.util.Map;

import com.actuate.ereport.output.JPreviewToolBarConfiguration;


public class JPssPreviewToolBar extends JPreviewToolBarConfiguration {

  public JPssPreviewToolBar() {
    super();
  }

  @SuppressWarnings("unchecked")
	public void addControl(String zControlId) {
    this.getControlIds().add(zControlId);
  }
  public void removeControl(String zControlId) {
    this.getControlIds().remove(zControlId);
  }
  public void removeAllControls() {
    this.getControlIds().clear();
  }

  @Override
	protected Map<String, String> getDefaultControlToolTips() {
    Map<String, String> iconMap = new HashMap<String, String>();
    iconMap.put(CONTROL_EXPORT, "Exportar reporte...");
    iconMap.put(CONTROL_PRINT, "Imprimir");
    iconMap.put(CONTROL_SEPARATOR, "");
    iconMap.put(CONTROL_ZOOM, "Zoom");
    iconMap.put(CONTROL_NAVIGATE_FIRST, "Primera p�gina");
    iconMap.put(CONTROL_NAVIGATE_PREVIOUS, "P�gina anterior");
    iconMap.put(CONTROL_NAVIGATE_GOTO_PAGE, "Ir a p�gina...");
    iconMap.put(CONTROL_NAVIGATE_NEXT, "P�gina siguiente");
    iconMap.put(CONTROL_NAVIGATE_LAST, "�ltima p�gina");
    iconMap.put(CONTROL_REFRESH, "Actualizar");
    iconMap.put(CONTROL_TOC, "Mostrar/Ocultar Tabla de Contenidos");
    iconMap.put(CONTROL_SHOW_MARGINS, "Mostrar/Ocultar M�rgenes");
    iconMap.put(CONTROL_ABOUT, "Acerca de...");
    return iconMap;
  }

}
