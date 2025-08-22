package pss.bsp.contrato.detalleAvianca.objetivos;

import pss.bsp.contrato.detalleAvianca.GuiDetalleAvianca;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JOrderedMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.totalizer.JTotalizer;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiObjetivosAviancas extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiObjetivosAviancas() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15011; } 
  public String  GetTitle()    throws Exception  { return "Objetivos AVIANCA"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiObjetivosAvianca.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {

  }

	@Override
	public JRecords<BizObjetivosAvianca> ObtenerDatos() {
		return new BizObjetivosAviancas();
	}


  
	public BizObjetivosAviancas getcRecords() throws Exception {
		return (BizObjetivosAviancas) this.getRecords();
	}
	
  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
//    zLista.AddColumnaLista("ruta");
    zLista.AddColumnaLista("grafico");
    zLista.AddColumnaLista("cantidad");
    zLista.AddColumnaLista("total");
    if (isPuntos()) {
      zLista.AddColumnaLista("faltante");
    	zLista.AddColumnaLista("puntos");
    }
    zLista.AddColumnaLista("ms");
    zLista.AddColumnaLista("next_obj");
    zLista.AddColumnaLista("ingreso");
    if (isPuntos()) {
    	zLista.AddColumnaLista("ticket_prom");
    } else {
      zLista.AddColumnaLista("premio");
          	
    }
    zLista.AddColumnaLista("cumple");
    zLista.AddColumnaLista("mensaje");
    
  }
  
  boolean isPuntos() throws Exception {
  	return ((GuiDetalleAvianca)this.getParent()).GetcDato().isPuntos();
  }
  
	public void createTotalizer(JWinList oLista) throws Exception {
		oLista.addTotalizerText("ruta", "Totales"); // un texto
		oLista.addTotalizer("cantidad", 0, JTotalizer.OPER_SUM); // la suma del
		oLista.addTotalizer("total", 0, JTotalizer.OPER_SUM); // la suma del
    if (isPuntos()) {
  		oLista.addTotalizer("faltante", 0, JTotalizer.OPER_SUM); // la suma del
    	oLista.addTotalizer("puntos", 0, JTotalizer.OPER_SUM); // la suma del
    } else {
     	oLista.addTotalizer("premio", 0, JTotalizer.OPER_SUM); // la suma del
         	
    }
		oLista.addTotalizer("ingreso", 2, JTotalizer.OPER_SUM); // la suma del
	}
	
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addEditResponsive("Ruta","CHAR", "ruta","ilike");
		zFiltros.addComboResponsive("Ver solo","mensaje",comboOpciones());
	}
	

	static JWins objComboOpciones;
	public JWins comboOpciones() throws Exception {
		if (objComboOpciones!=null) return objComboOpciones;
		JOrderedMap<String,String> map = JCollectionFactory.createOrderedMap();
		map.addElement(BizObjetivosAvianca.FULL_PUNTOS,"Puntos completos");
		map.addElement(BizObjetivosAvianca.CON_ALGUNOS_PUNTOS,"Con puntos");
		map.addElement(BizObjetivosAvianca.SIN_PUNTOS,"Sin puntos");
		return objComboOpciones=JWins.createVirtualWinsFromMap(map);
	}


}
