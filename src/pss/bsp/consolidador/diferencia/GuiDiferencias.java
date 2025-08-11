package pss.bsp.consolidador.diferencia;

import pss.bsp.consola.referencia.GuiReferencias;
import pss.bsp.consolidador.consolidacion.detalle.BizConsolidacion;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.win.totalizer.JTotalizer;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormComboResponsive;

public class GuiDiferencias extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiDiferencias() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10046; } 
  public String  GetTitle()    throws Exception  { return "Diferencias"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiDiferencia.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
  	 //   addActionNew( 1, "Nuevo Registro" );
  	  	this.addAction(10, "Ver referencias", null, 10055, true, true, true, "Group");
  	   	
  	  }
  		public JAct getSubmitFor(BizAction a) throws Exception {
  			if (a.getId() == 10)	return new JActWins(GuiReferencias.getReferenciasDifer());
  			return null;
  		}
  		@Override
  		public String getPreviewFlag() throws Exception {
  			return JWins.PREVIEW_NO;
  		}
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {

		JFormComboResponsive c = zFiltros.addComboResponsive(
				"Resultados conciliacion",
				"status", this.getTiposStatus(), true);
		c.setRefreshForm(true);
		//c.setPreferredWidth(400);
		
	}
	
	private JWins getTiposStatus() throws Exception {
		return JWins.CreateVirtualWins(BizConsolidacion.ObtenerResultadosAnalisis());
	}

	public void createTotalizer(JWinList oLista) throws Exception {
		oLista.addTotalizerText("boleto", "Totales"); // un texto
		oLista.addTotalizer("tarifa_bsp", 2, JTotalizer.OPER_SUM); // la suma del
		oLista.addTotalizer("contado_bsp", 2, JTotalizer.OPER_SUM); // la suma del campo
		oLista.addTotalizer("credito_bsp", 2, JTotalizer.OPER_SUM); // la suma del campo
		oLista.addTotalizer("impuesto_bsp", 2, JTotalizer.OPER_SUM); // la suma del campo
		oLista.addTotalizer("tarifa_bo", 2, JTotalizer.OPER_SUM); // la suma del campo
		oLista.addTotalizer("contado_bo", 2, JTotalizer.OPER_SUM); // la suma del campo
		oLista.addTotalizer("credito_bo", 2, JTotalizer.OPER_SUM); // la suma del campo
		oLista.addTotalizer("impuesto_bo", 2, JTotalizer.OPER_SUM); // la suma del campo
		oLista.addTotalizer("tarifa_dif", 2, JTotalizer.OPER_SUM); // la suma del campo
		oLista.addTotalizer("contado_dif", 2, JTotalizer.OPER_SUM); // la suma del campo
		oLista.addTotalizer("credito_dif", 2, JTotalizer.OPER_SUM); // la suma del campo
		oLista.addTotalizer("comision_dif", 2, JTotalizer.OPER_SUM); // la suma del campo
		oLista.addTotalizer("impuesto_dif", 2, JTotalizer.OPER_SUM); // la suma del campo
	}

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
  	zLista.AddColumnaLista("Boleto","boleto");
  	zLista.AddColumnaLista("operacion");
  	zLista.AddColumnaLista("tipo_ruta");
  	zLista.AddColumnaLista("id_aerolinea");
  	zLista.AddColumnaLista("tarifa_bsp");
  	zLista.AddColumnaLista("tarifa_bo");
  	zLista.AddColumnaLista("contado_bsp");
  	zLista.AddColumnaLista("contado_bo");
  	zLista.AddColumnaLista("credito_bsp");
  	zLista.AddColumnaLista("credito_bo");
  	zLista.AddColumnaLista("comision_bsp");
  	zLista.AddColumnaLista("comision_bo");
  	zLista.AddColumnaLista("impuesto_bsp");
  	zLista.AddColumnaLista("impuesto_bo");
  	zLista.AddColumnaLista("tarifa_dif");
  	zLista.AddColumnaLista("contado_dif");
  	zLista.AddColumnaLista("credito_dif");
  	zLista.AddColumnaLista("comision_dif");
  	zLista.AddColumnaLista("impuesto_dif");
  	zLista.AddColumnaLista("observacion");
   }
}