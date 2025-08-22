package pss.bsp.monitor.consola;

import pss.core.ui.components.JPssImage;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormImageResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormMonitorService extends JBaseForm {


private static final long serialVersionUID = 1245253307166L;


  public FormMonitorService() throws Exception {
  }

  public GuiBspMonitorService getWin() { return (GuiBspMonitorService) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
		setAutoRefresh(true, 10000, "BSPSERVICE");
		
		JFormPanelResponsive row = AddItemRow();
		JFormColumnResponsive colLeft = row.AddItemColumn(6);
		JFormColumnResponsive colRight = row.AddItemColumn(6);

	 	JFormImageResponsive i= colLeft.AddItemImage("Tiempos", "image" );
    i.setSource(JPssImage.SOURCE_SCRIPT);
    	i= colRight.AddItemImage("Company", "image_comp" );
    i.setSource(JPssImage.SOURCE_SCRIPT);
    i= AddItemRow().AddItemImage("Full", "image_full" );
    i.setSource(JPssImage.SOURCE_SCRIPT);
   
     AddItemRow().AddItemArea("Estado", CHAR, OPT,  "status").setHeight(500);
		autoBuildTabs(getInternalPanel(), zWin);

  }
} 
