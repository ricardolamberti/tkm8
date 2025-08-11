package pss.bsp.contrato.rutas.ms;

import pss.core.graph.Graph;
import pss.core.graph.implementations.GraphBar2D;
import pss.core.graph.implementations.GraphScriptPie;
import pss.core.graph.model.ModelMatrix;
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

public class GuiMSs extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiMSs() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15011; } 
  public String  GetTitle()    throws Exception  { return "Objetivos MS"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiMS.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {

  }

	@Override
	public JRecords<BizMS> ObtenerDatos() {
		return new BizMSs();
	}


  
	public BizMSs getcRecords() throws Exception {
		return (BizMSs) this.getRecords();
	}
	
  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddColumnaLista("ruta");
    zLista.AddColumnaLista("aerolinea");
    zLista.AddColumnaLista("cantidad");
    zLista.AddColumnaLista("porcentaje");
    
  }
  
  @Override
  public long selectSupraCount() throws Exception {
  	return -1;
  }
		

	@Override
	public void ConfigurarGraficos(JWinList oWinlist) throws Exception {
  	Graph gr = new GraphScriptPie();
  	gr.addAtributtes(Graph.GRAPH_ATTR_ROTATENAMES, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_DECIMALPRECISON, "2");
		gr.addAtributtes(Graph.GRAPH_ATTR_NUMBERPREFIX, "");
		gr.addAtributtes(Graph.GRAPH_ATTR_EXPORTENABLED, "1");
	
		gr.setTitle("Market Share por ruta");
		gr.getModel().addColumn("ruta", ModelMatrix.GRAPH_FUNCTION_CATEGORIE);
		gr.getModel().addColumn("aerolinea", ModelMatrix.GRAPH_FUNCTION_DATASET);
		gr.getModel().addColumn("cantidad", ModelMatrix.GRAPH_FUNCTION_VALUE);

  	oWinlist.addGrafico(gr);
  	super.ConfigurarGraficos(oWinlist);
	}

}
