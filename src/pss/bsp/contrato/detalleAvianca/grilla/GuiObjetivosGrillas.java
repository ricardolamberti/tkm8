package pss.bsp.contrato.detalleAvianca.grilla;

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

public class GuiObjetivosGrillas extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiObjetivosGrillas() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15011; } 
  public String  GetTitle()    throws Exception  { return "Objetivos AVIANCA"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiObjetivosGrilla.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {

  }

	@Override
	public JRecords<BizObjetivosGrilla> ObtenerDatos() {
		return new BizObjetivosGrillas();
	}


  
	public BizObjetivosGrillas getcRecords() throws Exception {
		return (BizObjetivosGrillas) this.getRecords();
	}
	
  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddColumnaLista("ruta");
    zLista.AddColumnaLista("cantidad");
    zLista.AddColumnaLista("total");
    zLista.AddColumnaLista("puntos");
    zLista.AddColumnaLista("ms");
    zLista.AddColumnaLista("labelzonas1");
    zLista.AddColumnaLista("labelzonas2");
    zLista.AddColumnaLista("labelzonas3");
    zLista.AddColumnaLista("labelzonas4");
    zLista.AddColumnaLista("labelzonas5");
    zLista.AddColumnaLista("labelzonas6");
    zLista.AddColumnaLista("labelzonas7");
    zLista.AddColumnaLista("labelzonas8");
    zLista.AddColumnaLista("labelzonas9");
    zLista.AddColumnaLista("ingreso");
    
  }
  
	public void createTotalizer(JWinList oLista) throws Exception {
		oLista.addTotalizerText("ruta", "Totales"); // un texto
		oLista.addTotalizer("cantidad", 0, JTotalizer.OPER_SUM); // la suma del
		oLista.addTotalizer("total", 0, JTotalizer.OPER_SUM); // la suma del
		oLista.addTotalizer("faltante", 0, JTotalizer.OPER_SUM); // la suma del
		oLista.addTotalizer("puntos", 0, JTotalizer.OPER_SUM); // la suma del
		oLista.addTotalizer("ingreso", 0, JTotalizer.OPER_SUM); // la suma del
		}
	
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addEditResponsive("Ruta","CHAR", "ruta","ilike");
	}
	

	


}
