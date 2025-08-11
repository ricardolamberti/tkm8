package pss.www.platform.users.stadistics;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormFieldsetResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.www.platform.users.threads.GuiThreads;


public class FormStadistics extends JBaseForm {


private static final long serialVersionUID = 1218223408500L;


/**
   * Constructor de la Clase
   */
  public FormStadistics() throws Exception {
  }

  public GuiStadistics GetWin() { return (GuiStadistics) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
   	JFormPanelResponsive row1 = AddItemRow();
   	JFormPanelResponsive row2 = AddItemRow();
   	JFormPanelResponsive columna1 = row1.AddItemColumn(6);
   	JFormPanelResponsive columna2 = row1.AddItemColumn(6);
   	JFormFieldsetResponsive panelOnlines = columna1.AddItemFieldset("Usuarios activos");
    JFormFieldsetResponsive panelTrafico = columna1.AddItemFieldset("Tráfico");
  	JFormFieldsetResponsive panelUso= columna2.AddItemFieldset("Usuarios");
  	JFormFieldsetResponsive panelResources= columna2.AddItemFieldset("Recursos");
  	JFormFieldsetResponsive panelInfo= columna1.AddItemFieldset("Info");
  	JFormFieldsetResponsive panelMem= columna2.AddItemFieldset("Memoria");
  	panelTrafico.AddItemEdit( "Solicitudes: ", LONG, OPT, "total_solic" ).setSizeColumns(3);
  	panelTrafico.AddItemEdit( "Promedio", LONG, OPT, "prom_solic" ).setSizeColumns(3);
  	panelTrafico.AddItemEdit( "Maximo", LONG, OPT, "max_solic" ).setSizeColumns(3);
  	panelTrafico.AddItemEdit( "Fecha Max.", DATETIME, OPT, "time_solic" ).setSizeColumns(3);
  	panelTrafico.AddItemEdit( "Transf.bytes", LONG, OPT, "total_bytes" ).setSizeColumns(3);
  	panelTrafico.AddItemEdit( "Promedio Bytes", LONG, OPT, "prom_bytes" ).setSizeColumns(3);
  	panelTrafico.AddItemEdit( "Max Bytes en 1 seg", LONG, OPT, "max_bytes" ).setSizeColumns(3);
  	panelTrafico.AddItemEdit( "Momento Max Bytes en 1 seg", DATETIME, OPT, "time_bytes" ).setSizeColumns(3);
  	panelTrafico.AddItemEdit( "Usuarios", LONG, OPT, "total_users" ).setSizeColumns(3);
  	panelTrafico.AddItemEdit( "Promedio Users", LONG, OPT, "prom_users" ).setSizeColumns(3);
  	panelTrafico.AddItemEdit( "Max Users en 1 seg", LONG, OPT, "max_users" ).setSizeColumns(3);
  	panelTrafico.AddItemEdit( "Momento Max Users en 1 seg", DATETIME, OPT, "time_users" ).setSizeColumns(3);
   	panelTrafico.AddItemEdit( "Memoria", FLOAT, OPT, "mem" ).setSizeColumns(3);
    panelTrafico.AddItemEdit( "Promedio Mem", FLOAT, OPT, "prom_mem" ).setSizeColumns(3);
  	panelTrafico.AddItemEdit( "Max Mem", FLOAT, OPT, "max_mem" ).setSizeColumns(3);
  	panelTrafico.AddItemEdit( "Momento Max Mem", DATETIME, OPT, "time_mem" ).setSizeColumns(3);
  	panelUso.AddItemImage(null, "graph1" );
  	panelOnlines.AddItemList(20);
  	panelInfo.AddItemHtml(null, CHAR, OPT, "info").setHeight(600);
   	JFormPanelResponsive p1 = panelResources.AddItemColumn(4);
  	JFormPanelResponsive p2 = panelResources.AddItemColumn(4);
  	JFormPanelResponsive p3 = panelResources.AddItemColumn(4);
  	p1.AddItemImage(null, "graphMemory" );
  	p2.AddItemImage(null, "graphCpu" );
  	p3.AddItemImage(null, "graphHard" );
  	panelMem.AddItemImage(null, "graphMemoryHist" ).setHeight(400);
    JFormFieldsetResponsive panelThread = row2.AddItemFieldset("Threads");
    panelThread.AddItemList(null, "threads" ,GuiThreads.class);
    setAutoRefresh(true, 10000, "SYSTEMINFO");
   }

}  //  @jve:decl-index=0:visual-constraint="10,10" 
