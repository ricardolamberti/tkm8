package pss.bsp.contrato.detalleCopa.objetivos;

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

public class GuiObjetivosCopas extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiObjetivosCopas() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15011; } 
  public String  GetTitle()    throws Exception  { return "Objetivos COPA"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiObjetivosCopa.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {

  }

	@Override
	public JRecords<BizObjetivosCopa> ObtenerDatos() {
		return new BizObjetivosCopas();
	}


  
	public BizObjetivosCopas getcRecords() throws Exception {
		return (BizObjetivosCopas) this.getRecords();
	}
	
  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
//    zLista.AddColumnaLista("ruta");
    zLista.AddColumnaLista("grafico");
    zLista.AddColumnaLista("cantidad");
    zLista.AddColumnaLista("total");
    zLista.AddColumnaLista("minimo");
    zLista.AddColumnaLista("cantidad_esp");
    zLista.AddColumnaLista("faltante");
    zLista.AddColumnaLista("fms");
    zLista.AddColumnaLista("ms");
    zLista.AddColumnaLista("porcentaje");
//    zLista.AddColumnaLista("ingreso");
//    zLista.AddColumnaLista("ticket_prom");
    zLista.AddColumnaLista("cumple");
    zLista.AddColumnaLista("mensaje");
    
  }
  
	public void createTotalizer(JWinList oLista) throws Exception {
		oLista.addTotalizerText("ruta", "Totales"); // un texto
		oLista.addTotalizer("cantidad", 0, JTotalizer.OPER_SUM); // la suma del
		oLista.addTotalizer("cantidad_esp", 0, JTotalizer.OPER_SUM); // la suma del
		oLista.addTotalizer("total", 0, JTotalizer.OPER_SUM); // la suma del
		oLista.addTotalizer("faltante", 0, JTotalizer.OPER_SUM); // la suma del
//		oLista.addTotalizer("ingreso", 2, JTotalizer.OPER_SUM); // la suma del
	}
	
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addEditResponsive("Ruta","CHAR", "ruta","ilike");
		zFiltros.addComboResponsive("Ver solo","mensaje",comboOpciones()).SetValorDefault(BizObjetivosCopa.DESTACADOS);
	}
	

	static JWins objComboOpciones;
	public JWins comboOpciones() throws Exception {
		if (objComboOpciones!=null) return objComboOpciones;
		JOrderedMap<String,String> map = JCollectionFactory.createOrderedMap();
		map.addElement(BizObjetivosCopa.SOLO_CUMPLE,"solo los que cumplen");
		map.addElement(BizObjetivosCopa.FALTA_CANTIDAD,"solo los cumplibles");
		map.addElement(BizObjetivosCopa.SOLO_NO_CUMPLE,"los que NO cumplen");
		map.addElement(BizObjetivosCopa.DESTACADOS,"Destacados");
		return objComboOpciones=JWins.createVirtualWinsFromMap(map);
	}


}
