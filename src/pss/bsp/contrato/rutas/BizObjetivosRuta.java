package pss.bsp.contrato.rutas;

import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizObjetivosRuta extends JRecord {
	public static final String PLANO = "0";
	public static final String MAS_10 = "1";
	public static final String MAS_20 = "2";
	public static final String MAS_50 = "3";
	public static final String MAS_100 = "4";
	
  public JString pCompany = new JString();
  public JLong pLinea = new JLong();
  public JLong pId = new JLong();
  public JString pRuta = new JString();
  public JString pIdAerolinea = new JString();
  public JLong pCantidad = new JLong();
  public JLong pTotal = new JLong();
  public JLong pFactor = new JLong();
  public JFloat pMS = new JFloat(){
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JFloat pFMS = new JFloat(){
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JString pLabelZonas = new JString();
  public JString pZonas = new JString();
  public JString pGrafico = new JString();
  public JFloat pIngreso = new JFloat(){
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JFloat pObjetivo = new JFloat(){
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JLong pCantTickets = new JLong();
  public JFloat pPorcentaje = new JFloat(){
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JString pPremioPorc = new JString(){
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JFloat pPremio = new JFloat(){
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  public JFloat pPremioEst = new JFloat(){
  	public int getCustomPrecision() throws Exception {
  		return 2;
  	};
  };
  protected JString pImagen1 = new JString() {
  	public void preset() throws Exception {pImagen1.setValue(getImagen1(false));}
  };

  public JBoolean pCumple = new JBoolean();
  public JString pMensaje = new JString();
  

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public String getCompany() throws Exception {
  	return pCompany.getValue();
  }
  public long getIdDetalle() throws Exception {
  	return pId.getValue();
  }
  public String getRuta() throws Exception {
  	return pRuta.getValue();
  }
  public String getIdAerolinea() throws Exception {
  	return pIdAerolinea.getValue();
  }
  public long getFactor() throws Exception {
  	return pFactor.getValue();
  }
  /**
   * Constructor de la Clase
   */
  public BizObjetivosRuta() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "id", pId );
    this.addItem( "linea", pLinea );
    this.addItem( "id_aerolinea", pIdAerolinea);
    this.addItem( "ruta", pRuta);
    this.addItem( "cantidad", pCantidad );
    this.addItem( "total", pTotal );
    this.addItem( "ms", pMS );
    this.addItem( "fms", pFMS );
    this.addItem( "zonas", pZonas );
    this.addItem( "labelzonas", pLabelZonas );
    this.addItem( "grafico", pGrafico );
    this.addItem( "ingreso", pIngreso );
    this.addItem( "objetivo", pObjetivo );
    this.addItem( "premioporc", pPremioPorc );
    this.addItem( "premio", pPremio );
    this.addItem( "premio_estimado", pPremioEst );
    this.addItem( "cumple", pCumple );
    this.addItem( "mensaje", pMensaje );
    this.addItem( "imagen1", pImagen1 );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "id", "Id", true, false, 32 );
    this.addFixedItem( KEY, "linea", "Linea", false, false, 32 );
    this.addFixedItem( FIELD, "id_aerolinea", "Aerolinea", true, true, 250 );
    this.addFixedItem( FIELD, "ruta", "Ruta", true, true, 250 );
    this.addFixedItem( FIELD, "cantidad", "Cantidad tkt", true, true, 18 );
    this.addFixedItem( FIELD, "total", "Total tkt", true, true, 18 );
    this.addFixedItem( FIELD, "ms", "MS", true, true, 18,2 );
    this.addFixedItem( FIELD, "fms", "FMS", true, true, 18,2 );
    this.addFixedItem( FIELD, "zonas", "Zonas", true, true, 18,2 );
    this.addFixedItem( FIELD, "labelzonas", "Label Zonas", true, true, 100 );
    this.addFixedItem( FIELD, "grafico", "Objetivo", true, true, 18,2 );
    this.addFixedItem( FIELD, "ingreso", "Indic. ganancia", true, true, 18,2 );
    this.addFixedItem( FIELD, "objetivo", "Indic. objetivo", true, true, 18,2 );
    this.addFixedItem( FIELD, "premioporc", "%", true, true, 18,2 );
    this.addFixedItem( FIELD, "premio", "Premio", true, true, 18,2 );
    this.addFixedItem( FIELD, "premio_estimado", "Premio", true, true, 18,2 );
    this.addFixedItem( FIELD, "cumple", "Cumple", true, true, 1 );
    this.addFixedItem( FIELD, "mensaje", "Mensaje", true, true, 250 );
    this.addFixedItem( VIRTUAL, "imagen1", "Imagen", true, true, 250 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return null; }

	public String getImagen1(boolean printerVersion) throws Exception {
//		GuiSerieCalculadas w = new GuiSerieCalculadas();
//	  Date fdesde = getFDesde();
//	  Date fhasta = getFHasta();
//	  if (fdesde==null) fdesde=getObjContrato().getFechaDesde();
//	  if (fhasta==null) fhasta=getObjContrato().getFechaHasta();
//  	if (getObjEvent()==null) return null;
//
//		String sql="";
//		
//		sql+=" select * from bsp_serie_calculada ";
//		sql+=" where modelo = "+getModelo()+" and variable="+getVariable()+" and company='"+getCompany()+"' ";
//		sql+=" and fecha between '"+fdesde+"' and '"+fhasta+"' ";
////		sql+=" select company,max(fecha) as fecha,0 as id,modelo, 0 as mes , dia as dia, 0 as anio, max(valor_ant) as valor_ant, max(valor) as valor, max(valor_estimado) as valor_estimado,max(acumulado) as acumulado, variable from bsp_serie_calculada ";
////		sql+=" where modelo = "+getModelo()+" and variable="+getVariable()+" and company='"+getCompany()+"' ";
////		sql+=" and fecha between '"+fdesde+"' and '"+fhasta+"' ";
//// 		sql+=" group by company,modelo,variable, dia ";
////		sql+=" order by company,modelo,variable, dia ";
//		w.getRecords().SetSQL(sql);
//		JWinList wl = new JWinList(w);
//		w.AddColumnasDefault(wl);
//		//Graph gr = new GraphXYLine();
//		GraphScriptSerieTemporal gr = new GraphScriptSerieTemporal();
//		
//		gr.addAtributtes(Graph.GRAPH_ATTR_ROTATENAMES, "1");
//		gr.addAtributtes(Graph.GRAPH_ATTR_DECIMALPRECISON, "2");
//		gr.addAtributtes(Graph.GRAPH_ATTR_NUMBERPREFIX, "");
//		gr.addAtributtes(Graph.GRAPH_ATTR_EXPORTENABLED, "1");
//		gr.addAtributtes(Graph.GRAPH_ATTR_XAXISNAME, "Tiempo");
//		gr.setWithFocus(!printerVersion);
//		gr.addAtributtes(Graph.GRAPH_ATTR_YAXISNAME, getObjEvent().getDescripcion());
//		gr.setTitle("Analisis "+getObjEvent().getDescripcion());
////		gr.setImage("html/images/fondochart.png");
//		ModelGrid mg=new ModelGrid();
//		
//		mg.addColumn("dia", ModelGrid.GRAPH_FUNCTION_CATEGORIE);
//		mg.addColumn(new Dataset("Datos actuales"),"valor", ModelGrid.GRAPH_FUNCTION_VALUE);
//		mg.addColumn(new Dataset("Estimacion"),"valor_estimado", ModelGrid.GRAPH_FUNCTION_VALUE);
//		mg.addColumn(new Dataset("año anterior"),"valor_ant", ModelGrid.GRAPH_FUNCTION_VALUE);
//		if (getAcumulativo())
//			mg.addColumn(new Dataset("Acumulado"),"acumulado", ModelGrid.GRAPH_FUNCTION_VALUE);
////		else
////			mg.addColumn(new Dataset("Acumulado"),"acumulado", ModelGrid.GRAPH_FUNCTION_VALUE);
//		gr.setModel(mg);
//		gr.clearRefs();
//		JRecords<BizNivel> niveles = getObjNiveles();
//		JIterator<BizNivel> it = niveles.getStaticIterator();
//		while (it.hasMoreElements()) {
//			BizNivel nivel = it.nextElement();
//			gr.addRef(nivel.getDescripcion(), nivel.getValor(), nivel.getColor());
//		}
////		gr.setMax(getValorObjetivo());
////		gr.setLeyendaMax("Valor Objetivo");
////		gr.setColorMax("#00ff00");
//  	wl.addGrafico(gr);
//    w.ConfigurarFiltrosLista(wl);
//    
//		Graph g=wl.getGrafico(1);
//		if (g!=null) {
//			g.localFill(wl,null,null);
//			g.setRefresh(1);
//			return g.getImage(615, 552).replace("script:", "");
//		}
		return null;
	}
	

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}
