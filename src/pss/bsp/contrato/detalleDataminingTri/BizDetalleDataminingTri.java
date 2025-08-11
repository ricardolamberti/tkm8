package pss.bsp.contrato.detalleDataminingTri;

import java.util.Calendar;
import java.util.Date;

import com.ibm.icu.util.StringTokenizer;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.mercado.BizDetalleMercado;
import pss.bsp.contrato.detalle.nivel.BizNivel;
import pss.bsp.contrato.detalleUpfront.BizDetalleUpfront;
import pss.bsp.contrato.logica.JContratoNormal;
import pss.bsp.gds.BizInterfazNew;
import pss.common.event.sql.BizSqlEvent;
import pss.common.event.sql.datos.BizSqlEventDato;
import pss.common.regions.currency.BizMoneda;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;
import pss.tourism.pnr.BizPNRTicket;
import pss.tourism.pnr.GuiPNRTickets;

public class BizDetalleDataminingTri extends BizDetalle {

	public BizDetalleDataminingTri() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void processInsert() throws Exception {
			if (pLogicaContrato.isNull())
				setLogicaContrato(JContratoNormal.class.getName());

			if (getObjLogicaInstance().needIndicadorObjetivo()) {
				if (pVariable.isNull())
					throw new Exception("Debe especificar una variable de medidción");
				if (pValor.isNull())
					pValor.setValue(getValorObjetivoEvento());
			}
			if (pDescripcion.isNull())
				pDescripcion.setValue(getObjEvent().getDescripcion());
			
		super.processInsert();
	}

	@Override
	public void processUpdate() throws Exception {
		if (pLogicaContrato.isNull())
			setLogicaContrato(JContratoNormal.class.getName());

		if (getObjLogicaInstance().needIndicadorObjetivo()) {
			if (pValor.isNull())
				pValor.setValue(getValorObjetivoEvento());
		}
		super.processUpdate();
	}
  public boolean needFMSGLobal()  throws Exception {
  	JRecords<BizNivel> niveles = pNiveles.getRawValue();
  	if (niveles==null)
  		niveles=getObjNiveles();
  	JIterator<BizNivel> it = niveles.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizNivel nivel = it.nextElement();
  		if (nivel.getTipoObjetivoInstance().needFMSGLobal()) 
  			return true;
  	}
  	return false;
  }	
  public boolean needShareGapGLobal()  throws Exception {
  	JRecords<BizNivel> niveles = pNiveles.getRawValue();
  	if (niveles==null)
  		niveles=getObjNiveles();
  	JIterator<BizNivel> it = niveles.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizNivel nivel = it.nextElement();
  		if (nivel.getTipoObjetivoInstance().needShareGapGLobal()) 
  			return true;
  	}
  	return false;
  }	
  public boolean needValorRefGLobal()  throws Exception {
  	JRecords<BizNivel> niveles = pNiveles.getRawValue();
  	if (niveles==null)
  		niveles=getObjNiveles();
  	JIterator<BizNivel> it = niveles.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizNivel nivel = it.nextElement();
  		if (nivel.getTipoObjetivoInstance().needValorRefGLobal()) 
  			return true;
  	}
  	return false;
  }	
  public void calcule(boolean update)  throws Exception {
  	
	  pGananciaAuxiliar.setValue(getCalculeAuxiliar());

	  JRecords<BizNivel> niveles = new JRecords<BizNivel>(BizNivel.class);
		niveles.addFilter("company", getCompany());
		niveles.addFilter("id", getId());
		niveles.addFilter("linea", getLinea());
		niveles.addOrderBy("valor","ASC");
		JIterator<BizNivel> it = niveles.getStaticIterator();
		while (it.hasMoreElements()) {
			BizNivel nivel = it.nextElement();
			nivel.setObjDetalle(this);
			nivel.setValor(nivel.getValor());
			if (update) nivel.update();
		}

	  pValorObjetivo.setValue(getCalculeValorObjetivo());
	  pValorFinContrato.setValue(getCalculeValorFinContrato());
	  pValorTotalFinContrato.setValue(getCalculeValorTotalFinContrato());
	  pNivelAlcanzadoEstimada.setValue(getCalculeNivelAlcanzado());
	  pGananciaEstimada.setValue(getCalculeGanancia());

	  pSigValorObjetivo.setValue(getCalculeSigValorObjetivo());
	  pConclusion.setValue(getConclusion());

	  pAnalisis.setValue(getAnalisis());
	  
  }
  
  @Override
  public String getConclusion() throws Exception {
  	String error="";
  	if (needFMSGLobal() && pFMSGlobal.isNull()) 
  		error += (error.equals("")?"":", ")+"DEBE DEFINIR FMS";
  	if (needShareGapGLobal() && pShareGapGLobal.isNull()) 
  		error += (error.equals("")?"":", ")+"DEBE DEFINIR TARGET SHARE GAP";
//  	if (needValorRefGLobal() && pValorGlobal.isNull()) 
//  		error += (error.equals("")?"":", ")+"DEBE DEFINIR VALOR REFERENCIA";
  	if (!error.equals("")) return error;
  	
  	String errores=getErrorNiveles();
  	if (errores !=null) 
  		return errores;
  	
  	return super.getConclusion();
  }

  public String getAnalisis() throws Exception {
  	String analisis="";
  	// cuantos tickets faltan?
  	String unidad= "";
  	if (!getObjEvent().isOK()) return "Reprocesando indicadores";

  	String campo=getObjEvent().getCampoDescr().toLowerCase();
  	String error=getConclusion();
  	if (campo.indexOf("porcentaje")!=-1) unidad="%";
  	else if (campo.indexOf("boletos")!=-1) unidad="tickets";
  	else if (campo.indexOf("dolares")!=-1) unidad="dolares";
  	else if (campo.indexOf("neto")!=-1||campo.indexOf("tarifa")!=-1||campo.indexOf("total")!=-1||campo.indexOf("iva")!=-1
  			||campo.indexOf("impuesto")!=-1||campo.indexOf("comision")!=-1) 
  		unidad= "pesos";
  	//BizMoneda.getMonedaLocal(BizBSPCompany.getObjBSPCompany(getCompany()).getPais());
  	
  	String analisisHtml="";
  	Calendar fh = Calendar.getInstance();
  	fh.setTime(getFHasta());
  	double valorObjetivo = pSigValorObjetivo.getValue();
  	double valorActual= pValorFinContrato.getValue();
  	if (!error.equals("")) {
  		analisis=error;
  	}
  	else if (valorObjetivo==0) {
  		analisis="Se cumplimentaron todos los objetivos";
  	} else {
  		if (!unidad.equals("%")) {
  			double diferencia = valorObjetivo - valorActual;
  			analisis = "Debe vender <b>"+JTools.rd(diferencia,2)+"</b> "+unidad+" para alcanzar el siguiente objetivo.";
  		}
  		else {
  			double total = this.getObjEvent().getDetallesSinFuncion(null, fh, null);
  			
  			double diferencia = (total*(100.0/valorActual))*((valorObjetivo - valorActual)/100.0);
  			analisis = "Debe vender <b>"+JTools.rd(diferencia,2)+"</b> tickets para alcanzar el siguiente objetivo.";
  		}
  	}
	
    analisisHtml="<html><body> ";
    analisisHtml+="<p><font size=\"4\" color=\"#000000\" face=\"arial, verdana, helvetica\">Que vender? </font></p><br><p><font size=\"3\" color=\"#000000\" face=\"arial, verdana, helvetica\">";
    analisisHtml+=analisis;
    analisisHtml+="</font></p><br><br><br><br><br></body></html>";
  	
  	return analisisHtml;
  }

  public double getCalculeValorObjetivo() throws Exception { 
  	BizNivel nivel = getObjNivelFavorito();
  	if (nivel==null) {
  		nivel =  getObjNivelMayor();
  		if (nivel==null) return 0;
  		
  	}
  	return nivel.getValor();//-getReembolso(); 
  };   
  // la fecha actual es la fecha de ultimo pnr ingreado.

  public double getCalculeValorActual() throws Exception { 
   	if (getObjEvent()==null) return 0;
  	
  	
   		Calendar desde = Calendar.getInstance();
  		desde.setTime(hasFechaDesdeCalculo()?getFechaDesdeCalculo():getObjContrato().getFechaDesde());
  		
  		Calendar hasta = Calendar.getInstance();
  		hasta.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
  		
  		Calendar hoy = Calendar.getInstance();
  		hoy.setTime(getFechaActual());
    	if (!getObjEvent().isOK())
    		return getObjEvent().getValor(desde,hoy);
  	 	BizSqlEventDato dato=  getObjEvent().getValorAnterior(desde,hoy);
  	 	return (dato==null?0:dato.getValue());

//		if (hoy.before(desde)){
//	  	if (getAcumulativo()) return calculeAcumulado(desde);
//	  	BizSqlEventDato dato = getObjEvent().getValorAnterior(desde, desde);
//	  	if (dato!=null) return dato.getValue();T
////	  	return getObjEvent().getValor(desde);
//	  	return 0;
//		}
//		
//		if (hoy.after(hasta)) {
//	  	if (getAcumulativo()) return calculeAcumulado(hasta);
//	  	BizSqlEventDato dato = getObjEvent().getValorAnterior(desde, hasta);
//	  	if (dato!=null) return dato.getValue();
////	  	return getObjEvent().getValor(hasta); 
//	  	return 0;
//		}
//  	if (getAcumulativo()) return calculeAcumulado(hoy);
//  	BizSqlEventDato dato = getObjEvent().getValorAnterior(desde, hoy);
//  	if (dato!=null) return dato.getValue();
////  	return getObjEvent().getValor(hoy); 
//  	return 0;
  }
  public double getCalculeValorAnoAnterior() throws Exception { 
  	if (getObjEvent()==null) return 0;

		Calendar desde = Calendar.getInstance();
		desde.setTime(getObjContrato().getFechaDesde());
		desde.add(Calendar.YEAR, -1);
		
		Calendar hasta = Calendar.getInstance();
		hasta.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
		hasta.add(Calendar.YEAR, -1);

  	if (getAcumulativo()) return calculeAcumulado(null,hasta);
  	if (!getObjEvent().isOK())
  		return getObjEvent().getValor(null,hasta);
		BizSqlEventDato d = getObjEvent().getValorAnterior(null, hasta);
		if (d!=null) return d.getValue();
		
  	return getObjEvent().getValor(null,hasta);
  }  
  public double getCalculeValorFinContrato() throws Exception { 
  	if (getObjEvent()==null) return 0;
   	Calendar desde = Calendar.getInstance();
  		desde.setTime(hasFechaDesdeCalculo()?getFechaDesdeCalculo():getObjContrato().getFechaDesde());
  	Calendar hoy = Calendar.getInstance();
		hoy.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
  	if (getAcumulativo()) return calculeAcumulado(desde,hoy);
  	if (!getObjEvent().isOK()) {
  		return getObjEvent().getValor(desde,hoy);
  	}
  	BizSqlEventDato dato= getObjEvent().getValorAnterior(desde,hoy);
  	return (dato==null?0:dato.getValue());//-getReembolso()
  }
  

  public  void processMercado() throws Exception {
  	// se puede buscar en el detalle todos los mercados
  	
  	if (!getObjEvent().getClassDetalle().equals(GuiPNRTickets.class.getName())) return;
  	
  	Calendar hoy = Calendar.getInstance();
		hoy.setTime(new Date());
		Calendar desde = Calendar.getInstance();
		desde.setTime(hasFechaDesdeCalculo()?getFechaDesdeCalculo():getObjContrato().getFechaDesde());
		Calendar hasta = Calendar.getInstance();
		hasta.setTime(getObjContrato().getFechaHasta());
		
		JMap<String,String> aerolineas = JCollectionFactory.createMap();
		if (hoy.after(hasta))
			hoy = hasta;
		
		JWins wins= getObjEvent().getDetalles(desde,hoy,getObjEvent()!=null?getObjEvent().getCampo():null);
		JRecords<BizPNRTicket> tickets = (JRecords<BizPNRTicket>)wins.getRecords();
		JIterator<BizPNRTicket> it = tickets.getStaticIterator();
		while (it.hasMoreElements()) {
			BizPNRTicket t = it.nextElement();
			if (aerolineas.getElement(t.getCarrier())==null) {
				aerolineas.addElement(t.getCarrier(), t.getCarrier());
			}
			StringTokenizer toks = new StringTokenizer(t.getMarket(),",");
			while (toks.hasMoreTokens()) {
				boolean insert=false;
				String mrk = toks.nextToken();
				if (!mrk.startsWith("BOK-")) continue;
				String mercado = mrk.substring(4);
				BizDetalleMercado detMerc = new BizDetalleMercado();
				detMerc.dontThrowException(true);
				if (!detMerc.read(getCompany(), getIdAerolinea(),mercado)) {
					detMerc.setCompany(getCompany());
					detMerc.setIdContrato(getId());
					detMerc.setIdDetalle(getLinea());
					detMerc.setIdAerolinea(t.getCarrier());
					detMerc.setMarketingID(mercado);
					detMerc.setOrigen(t.getAeropuertoOrigen());
					detMerc.setDestino(t.getAeropuertoDestino());
					insert=true;
				}
				
				// buscar FMS
				if (detMerc.getFMSEdited()!=0) {
					detMerc.setFMS(detMerc.getFMSEdited());
				} else {
					detMerc.setFMS(getFMS(getCompany(), getIdAerolinea(),mercado));
				}
				
				if (insert)
					detMerc.processInsert();
				else
					detMerc.processUpdate();
				
			}
			
			String straer="";
			JIterator<String>ita=aerolineas.getValueIterator();
			while(ita.hasMoreElements()) {
				straer+= (straer.equals("")?"":", ")+"'"+ita.nextElement()+"'";
			}
			
			setIdAerolinea(straer);
		}

  }
 
}
