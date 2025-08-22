package pss.bsp.contrato.rutas;

import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JOrderedMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiObjetivosRutas extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiObjetivosRutas() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15011; } 
  public String  GetTitle()    throws Exception  { return "Objetivos Rutas"; }
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
//  	JGrupoColumnaLista info = zLista.AddGrupoColumnaLista("Info");
//  	JGrupoColumnaLista esti = zLista.AddGrupoColumnaLista("Estimaci�n");
//  	JGrupoColumnaLista market = zLista.AddGrupoColumnaLista("Market share");
//    zLista.AddColumnaLista("grafico").setGrupo(info);
//    zLista.AddColumnaLista("objetivo").setGrupo(esti);
//    zLista.AddColumnaLista("ingreso").setGrupo(esti);
//    zLista.AddColumnaLista("premioporc").setGrupo(esti);
//    zLista.AddColumnaLista("premio_estimado").setGrupo(esti);
//    zLista.AddColumnaLista("cantidad").setGrupo(market);
//    zLista.AddColumnaLista("total").setGrupo(market);
//    zLista.AddColumnaLista("fms").setGrupo(market);
//    zLista.AddColumnaLista("ms").setGrupo(market);
	  zLista.AddColumnaLista("ruta");
	  zLista.AddColumnaLista("cantidad");
	  zLista.AddColumnaLista("total");
//	  zLista.AddColumnaLista("fms");
	  zLista.AddColumnaLista("ms");
    
  }
  
	public void createTotalizer(JWinList oLista) throws Exception {
//		oLista.addTotalizerText("ruta", "Totales"); // un texto
//		oLista.addTotalizer("cantidad", 0, JTotalizer.OPER_SUM); // la suma del
//		oLista.addTotalizer("total", 0, JTotalizer.OPER_SUM); // la suma del
//		oLista.addTotalizer("ingreso", 2, JTotalizer.OPER_SUM); // la suma del
//		oLista.addTotalizer("premio", 2, JTotalizer.OPER_SUM); // la suma del
//		oLista.addTotalizer("premio_estimado", 2, JTotalizer.OPER_SUM); // la suma del
	}
	
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addEditResponsive("Ruta","CHAR", "ruta","ilike");
//		zFiltros.NuevoCombo("Ver solo","mensaje",comboOpciones()).SetValorDefault(BizObjetivosRuta.MAS_10);
	}
	
	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}

	static JWins objComboOpciones;
	public JWins comboOpciones() throws Exception {
		if (objComboOpciones!=null) return objComboOpciones;
		JOrderedMap<String,String> map = JCollectionFactory.createOrderedMap();
		map.addElement(BizObjetivosRuta.PLANO,"Estado actual");
		map.addElement(BizObjetivosRuta.MAS_10,"Vender 10 mas en esta ruta");
		map.addElement(BizObjetivosRuta.MAS_20,"Vender 20 mas en esta ruta");
		map.addElement(BizObjetivosRuta.MAS_50,"Vender 50 mas en esta ruta");
		map.addElement(BizObjetivosRuta.MAS_100,"Vender 100 mas en esta ruta");
		return objComboOpciones=JWins.createVirtualWinsFromMap(map);
	}


}
