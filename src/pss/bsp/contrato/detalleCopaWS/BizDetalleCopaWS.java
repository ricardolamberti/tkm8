package pss.bsp.contrato.detalleCopaWS;

import java.util.Calendar;
import java.util.Date;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.nivel.BizNivel;
import pss.bsp.contrato.detalleCopaWS.objetivos.BizObjetivosCopaWS;
import pss.bsp.contrato.detalleCopaWS.objetivos.BizObjetivosCopasWS;
import pss.bsp.contrato.detalleCopaWS.objetivos.GuiObjetivosCopasWS;
import pss.bsp.contrato.logica.JContratoCopaWS;
import pss.common.event.sql.datos.BizSqlEventDato;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.regions.multilanguage.JLanguage;
import pss.core.graph.Graph;
import pss.core.graph.implementations.GraphScriptMedioReloj;
import pss.core.graph.implementations.GraphScriptPie;
import pss.core.graph.model.ModelVector;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;
import pss.core.winUI.lists.JWinList;

public class BizDetalleCopaWS extends BizDetalle {

  
  public BizDetalleCopaWS() throws Exception {
		super();
	}


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void autogenerar() throws Exception {
  	//ver borrado
  }
  
  
  @Override
  public void processInsert() throws Exception {
  	if (pDescripcion.isNull()) pDescripcion.setValue("WS "+getIdAerolinea());
  	setLogicaContrato(JContratoCopaWS.class.getName());

  	super.processInsert();
  	autogenerar();
  }
  
  @Override
  public void processUpdate() throws Exception {
  	if (pDescripcion.isNull()) pDescripcion.setValue("WS "+getIdAerolinea());
  	setLogicaContrato(JContratoCopaWS.class.getName());
  	super.processUpdate();
  	autogenerar();
  }
  
	public String getImagen1(boolean printerVersion) throws Exception {
		GuiObjetivosCopasWS w = new GuiObjetivosCopasWS();
		BizObjetivosCopasWS r = getObjetivos();
		r.addFilter("mensaje",  BizObjetivosCopaWS.SOLO_CUMPLE);
		w.setRecords(r);

		JWinList wl = new JWinList(w);
		w.AddColumnasDefault(wl);
		GraphScriptPie gr = new GraphScriptPie();
		gr.addAtributtes(Graph.GRAPH_ATTR_ROTATENAMES, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_DECIMALPRECISON, "2");
		gr.addAtributtes(Graph.GRAPH_ATTR_NUMBERPREFIX, "");
		gr.addAtributtes(Graph.GRAPH_ATTR_EXPORTENABLED, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_XAXISNAME, "Cumplimiento");
		gr.addAtributtes(Graph.GRAPH_ATTR_YAXISNAME, "Objetivos");
		gr.setTitle("Rutas por rendimiento");
		ModelVector mg=new ModelVector();
		
		mg.addColumn("ruta", ModelVector.GRAPH_FUNCTION_DATASET);
		mg.addColumn("porcentaje", ModelVector.GRAPH_FUNCTION_VALUE);
		gr.setModel(mg);
  	wl.addGrafico(gr);
    w.ConfigurarFiltrosLista(wl);
    
		Graph g=wl.getGrafico(1);
		if (g!=null) {
			g.localFill(wl,null,null);
			g.setRefresh(1);
			return g.getImage(300, 300).replace("script:", "");
		}
		return null;
	}
	
	public String getImagen2() throws Exception {     
		GraphScriptMedioReloj gr = new GraphScriptMedioReloj();
		gr.setMax((long)getValorObjetivo());
		if (getValorObjetivo()<1) return "";
		gr.setSize(260);
		
		long dato = ((long)getValorObjetivo()<=(long)getValorActual())?(long)getValorObjetivo():(long)getValorActual();
		gr.setValor(dato);
//		gr.setTitle("OBJETIVO A HOY");
		gr.setLeyenda(""+dato);
		return gr.getImage(300, 300).replace("script:", "");
	}


	public BizObjetivosCopasWS getObjetivos()  throws Exception {
		BizObjetivosCopasWS w = new BizObjetivosCopasWS();
		w.addFilter("company", getCompany());
		w.addFilter("contrato", getId());
		w.addFilter("detalle", getLinea());
		return w;
	}
	
  public void calcule(boolean update)  throws Exception {
  	BizObjetivosCopasWS objs=getObjetivos();
		objs.readAll();

		pValorObjetivo.setValue(getCalculeValorObjetivo());

	  pValorFinContrato.setValue(objs.getPorcAlcanzado());
	  pValorTotalFinContrato.setValue(objs.getVolado());
	  pNivelAlcanzadoEstimada.setValue(getCalculeNivelAlcanzado());
	  pGananciaEstimada.setValue(getCalculeGanancia());

	  pSigValorObjetivo.setValue(getCalculeSigValorObjetivo());
	  pConclusion.setValue(getConclusion(objs.getConclusion()));
  }

  
  public String getConclusion(String solucion) throws Exception {
   	Calendar hoy = Calendar.getInstance();
  	hoy.setTime(new Date());
  	Double data=getValorActual();
  	String error=getErrorNiveles();
  	String conclusion="";
  	if (error !=null) 
  		conclusion=error;
  	else{
    	String nivel=getCalculeNivelAlcanzado();
    	if (!nivel.equals("Ninguno")) {
  	  	conclusion+=JTools.getDoubleNumberEmbedded(nivel)==0?"Se llego al objetivo":JLanguage.translate("Se llego al "+nivel+" de premio");
    	} else {
    		conclusion = solucion;
    	}
  	}
  	
  	return conclusion;
  }

  public double getCalculeValorObjetivo() throws Exception { 
  	BizNivel nivel = getObjNivelFavorito();
  	if (nivel==null) {
  		nivel =  getObjNivelMayor();
  		if (nivel==null) return 0;
  		
  	}
  	return nivel.getValor();
  };   
  
  public double getCalculeValorActual() throws Exception { 
  	if (getObjEvent()==null) return 0;
  	Calendar hoy = Calendar.getInstance();
		hoy.setTime(getFechaActual());
		
	 	Calendar desde = Calendar.getInstance();
			desde.setTime(hasFechaDesdeCalculo()?getFechaDesdeCalculo():getObjContrato().getFechaDesde());
	
		Calendar hasta = Calendar.getInstance();
		hasta.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
		
		if (hoy.before(desde)){
	  	if (getAcumulativo()) return calculeAcumulado(null,desde);
	  	return getObjEvent().getValor(null,desde); 
		}
		if (hoy.after(hasta)) {
	  	if (getAcumulativo()) return calculeAcumulado(null,hasta);
	  	return getObjEvent().getValor(null,hasta); 
		}
  	if (getAcumulativo()) return calculeAcumulado(desde,hoy);
  	return getObjEvent().getValor(desde,hoy); 
  }
  public double getCalculeValorAnoAnterior() throws Exception { 
  	if (getObjEvent()==null) return 0;

		Calendar desde = Calendar.getInstance();
		desde.setTime(getObjContrato().getFechaDesde());
		desde.add(Calendar.YEAR, -1);
		
		Calendar hasta = Calendar.getInstance();
		hasta.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
		hasta.add(Calendar.YEAR, -1);

  	if (getAcumulativo()) return calculeAcumulado(desde,hasta);
		BizSqlEventDato d = getObjEvent().getValorAnterior(desde, hasta);
		if (d!=null) return d.getValue();
		
  	return getObjEvent().getValor(desde,hasta); 
  }  
  public double getCalculeValorFinContrato() throws Exception { 
  	if (getObjEvent()==null) return 0;
   	Calendar desde = Calendar.getInstance();
  		desde.setTime(hasFechaDesdeCalculo()?getFechaDesdeCalculo():getObjContrato().getFechaDesde());
  	Calendar hoy = Calendar.getInstance();
		hoy.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
  	if (getAcumulativo()) return calculeAcumulado(desde,hoy);
  	return getObjEvent().getValor(desde,hoy); 
  }
  public String imprimirResumen() throws Exception {
		JRecord[]recs = new JRecord[4];
		recs[0]=BizBSPCompany.getObjBSPCompany(this.getCompany());
		recs[1]=getObjContrato();
		recs[2]=this;
		recs[3]=null;
		
		String tempfile =BizPlantilla.generateListadoTemporario(this.getCompany(),true,recs,"sys_contrato_detalle_copa_ws");
		return tempfile;
	}

 

}
