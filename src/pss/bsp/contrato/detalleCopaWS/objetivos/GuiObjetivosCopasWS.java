package pss.bsp.contrato.detalleCopaWS.objetivos;

import pss.bsp.contrato.detalle.GuiDetalle;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JOrderedMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.totalizer.JTotalizer;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormSwingEdit;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormIntervalCDatetimeResponsive;

public class GuiObjetivosCopasWS extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiObjetivosCopasWS() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15011; } 
  public String  GetTitle()    throws Exception  { return "Objetivos COPA"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiObjetivosCopaWS.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {

  }

	@Override
	public JRecords<BizObjetivosCopaWS> ObtenerDatos() {
		return new BizObjetivosCopasWS();
	}


  
	public BizObjetivosCopasWS getcRecords() throws Exception {
		return (BizObjetivosCopasWS) this.getRecords();
	}
	
  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
//    zLista.AddColumnaLista("ruta");
    zLista.AddColumnaLista("ruta");
    zLista.AddColumnaLista("CM Sales","cantidad");
    zLista.AddColumnaLista("Industry sales","total");
    zLista.AddColumnaLista("ms");
    zLista.AddColumnaLista("CM Sales tkts","cantidad_tkt");
    zLista.AddColumnaLista("Industry sales tkts","total_tkt");
    
  }
  
	public void createTotalizer(JWinList oLista) throws Exception {
		oLista.addTotalizerText("ruta", "Totales"); // un texto
		oLista.addTotalizer("cantidad", 0, JTotalizer.OPER_SUM); // la suma del
		oLista.addTotalizer("total", 0, JTotalizer.OPER_SUM); // la suma del
		oLista.addTotalizer("cantidad_tkt", 0, JTotalizer.OPER_SUM); // la suma del
		oLista.addTotalizer("total_tkt", 0, JTotalizer.OPER_SUM); // la suma del
	}
	
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.NuevoEdit("Ruta","CHAR", "ruta","ilike");
		zFiltros.addIntervalCDateResponsive("Intervalo", "fecha");
	}
	



}
