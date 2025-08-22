package pss.bsp.contrato.detalleCopaPorRutas.objetivos;

import pss.bsp.contrato.detalleRutas.objetivos.BizObjetivosRuta;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JOrderedMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.totalizer.JTotalizer;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiObjetivosCopasPorRutas extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiObjetivosCopasPorRutas() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15011; } 
  public String  GetTitle()    throws Exception  { return "Objetivos COPA por rutas"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiObjetivosCopaPorRutas.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {

  }

	@Override
	public JRecords<BizObjetivosCopaPorRutas> ObtenerDatos() {
		return new BizObjetivosCopasPorRutas();
	}


  
	public BizObjetivosCopasPorRutas getcRecords() throws Exception {
		return (BizObjetivosCopasPorRutas) this.getRecords();
	}
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
//  zLista.AddColumnaLista("ruta");
  zLista.AddColumnaLista("ruta");
  if (!GetVision().equals("PAX")) {
   	zLista.AddColumnaLista("Aerolinea $","cantidad");
  	zLista.AddColumnaLista("Industria $","total");
    zLista.AddColumnaLista("ms");
  } else {
   	zLista.AddColumnaLista("Cantidad tkt","cantidad");
     	
  }
  zLista.AddColumnaLista("faltante");
  zLista.AddColumnaLista("ingreso");
 // zLista.AddColumnaLista("mensaje");
  
}

public void createTotalizer(JWinList oLista) throws Exception {
	oLista.addTotalizerText("ruta", "Totales"); // un texto
	oLista.addTotalizer("cantidad", 0, JTotalizer.OPER_SUM); // la suma del
//	oLista.addTotalizer("cantidad_esp", 0, JTotalizer.OPER_SUM); // la suma del
	oLista.addTotalizer("total", 0, JTotalizer.OPER_SUM); // la suma del
//	oLista.addTotalizer("faltante", 0, JTotalizer.OPER_SUM); // la suma del
	oLista.addTotalizer("ingreso", 2, JTotalizer.OPER_SUM); // la suma del
}

public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
	zFiltros.addEditResponsive("Ruta","CHAR", "ruta","ilike");
//.addComboResponsive("Ver solo","mensaje",comboOpciones()).SetValorDefault(BizObjetivosRuta.SOLO_CUMPLE);
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
