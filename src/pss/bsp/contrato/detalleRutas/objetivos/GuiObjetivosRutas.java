package pss.bsp.contrato.detalleRutas.objetivos;

import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JOrderedMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.totalizer.JTotalizer;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiObjetivosRutas extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiObjetivosRutas() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15011; } 
  public String  GetTitle()    throws Exception  { return "Objetivos Ruta"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiObjetivosRuta.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {

  }

	@Override
	public JRecords<BizObjetivosRuta> ObtenerDatos() {
		return new BizObjetivosRutas();
	}


  
	public BizObjetivosRutas getcRecords() throws Exception {
		return (BizObjetivosRutas) this.getRecords();
	}
	
  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
//    zLista.AddColumnaLista("ruta");
    zLista.AddColumnaLista("grafico");
    zLista.AddColumnaLista("cantidad");
    if (!GetVision().equals("PAX")) {
    	zLista.AddColumnaLista("total");
      zLista.AddColumnaLista("ms");
    }
    zLista.AddColumnaLista("faltante");
    zLista.AddColumnaLista("ingreso");
    zLista.AddColumnaLista("mensaje");
    
  }
  
	public void createTotalizer(JWinList oLista) throws Exception {
		oLista.addTotalizerText("ruta", "Totales"); // un texto
		oLista.addTotalizer("cantidad", 0, JTotalizer.OPER_SUM); // la suma del
//		oLista.addTotalizer("cantidad_esp", 0, JTotalizer.OPER_SUM); // la suma del
		oLista.addTotalizer("total", 0, JTotalizer.OPER_SUM); // la suma del
//		oLista.addTotalizer("faltante", 0, JTotalizer.OPER_SUM); // la suma del
		oLista.addTotalizer("ingreso", 2, JTotalizer.OPER_SUM); // la suma del
	}
	
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addEditResponsive("Ruta","CHAR", "ruta","ilike");
		zFiltros.addComboResponsive("Ver solo","mensaje",comboOpciones()).SetValorDefault(BizObjetivosRuta.SOLO_CUMPLE);
	}
	

	static JWins objComboOpciones;
	public JWins comboOpciones() throws Exception {
		if (objComboOpciones!=null) return objComboOpciones;
		JOrderedMap<String,String> map = JCollectionFactory.createOrderedMap();
		map.addElement(BizObjetivosRuta.SOLO_CUMPLE,"los que cumplen");
		map.addElement(BizObjetivosRuta.FALTA_CANTIDAD,"los cumplibles");
		map.addElement(BizObjetivosRuta.SOLO_NO_CUMPLE,"los que NO cumplen");
		return objComboOpciones=JWins.createVirtualWinsFromMap(map);
	}


}
