package pss.bsp.contrato.detalleAvianca.grilla;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.nivel.BizNivel;
import pss.bsp.contrato.detalleAvianca.objetivos.BizObjetivosAvianca;
import pss.bsp.contrato.detalleAvianca.objetivos.BizObjetivosAviancas;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JOrderedMap;

public class BizObjetivosGrillas extends JRecords<BizObjetivosGrilla> {
;
	public Class<BizObjetivosGrilla> getBasedClass() {
		return BizObjetivosGrilla.class;
	}
	
	
	String conclusion;

	Double voladoAHoy;


	Double totalTicket;
	Double totalPuntos;
	Double porcAlcanzado;


	public Double getPorcAlcanzado() {
		return porcAlcanzado;
	}

	public void setPorcAlcanzado(Double porcAlcanzado) {
		this.porcAlcanzado = porcAlcanzado;
	}


	Double volado;
	Double totalPremiosAHoy;
	Double totalPremios;
	Double totalPremiosExtra;
	public Double getTotalPremiosAHoy() {
		return totalPremiosAHoy;
	}

	public void setTotalPremiosAHoy(Double totalPremiosAHoy) {
		this.totalPremiosAHoy = totalPremiosAHoy;
	}
	
	public Double getTotalPremiosExtra() {
		return totalPremiosExtra;
	}

	public void setTotalPremiosExtra(Double totalPremiosExtra) {
		this.totalPremiosExtra = totalPremiosExtra;
	}

	public Double getTotalPremios() {
		return totalPremios;
	}

	public void setTotalPremios(Double totalPremios) {
		this.totalPremios = totalPremios;
	}
	public String getConclusion() {
		return conclusion;
	}

	public Double getTotalTicket() {
		return totalTicket;
	}

	public void setTotalTicket(Double totalTicket) {
		this.totalTicket = totalTicket;
	}

	public Double getTotalPuntos() {
		return totalPuntos;
	}

	public void setTotalPuntos(Double totalTicktesEsperados) {
		this.totalPuntos = totalTicktesEsperados;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}
	public Double getVoladoAHoy() {
		return voladoAHoy;
	}

	public void setVoladoAHoy(Double voladoAHoy) {
		this.voladoAHoy = voladoAHoy;
	}

	public Double getVolado() {
		return volado;
	}

	public void setVolado(Double volado) {
		this.volado = volado;
	}

  public String getHtmlData(BizNivel nivel,boolean title) throws Exception {

  	String s="";
  	s+="<table width=\"100%\">";
  	s+="<tr>";
  	if (title) s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: left; color: rgb(0, 0, 0); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">"+(title?"<B>MS:</B>":"")+"</font></td>";
  	s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: right; color: rgb(0, 0, 0); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">"+JTools.toPrinteableNumber(nivel.getValor(),2)+"</font></td>";
  	s+="</tr>";
  	s+="<tr>";
  	if (title) s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: left; color: rgb(0, 0, 0); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">"+(title?"<B>Puntos:</B>":"")+"</font></td>";
  	s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: right; color: rgb(0, 0, 0); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">"+JTools.toPrinteableNumber(nivel.getParam4(),0)+"</font></td>";
  	s+="</tr>";
  	s+="</table>";
  	return s;
  }
  public String getStringData(BizNivel nivel) throws Exception {
  	return "MS: "+JTools.toPrinteableNumber(nivel.getValor(),2)+"% Puntos: "+JTools.toPrinteableNumber(nivel.getParam4(),0);
  }

	@Override
	public boolean readAll() throws Exception {
		String conclusion="";
		this.endStatic();
    this.setStatic(true);
    String idcompany = getFilterValue("company");
    long idcontrato = Long.parseLong(getFilterValue("contrato"));
    long iddetalle = Long.parseLong(getFilterValue("detalle"));
    JOrderedMap<String, Double> acumPremios = JCollectionFactory.createOrderedMap();  
 
    BizDetalle detalle = new BizDetalle();
    detalle.dontThrowException(true);
    if (!detalle.read(iddetalle)) return false;
    
    BizObjetivosAviancas objetivos = new BizObjetivosAviancas();
    
    objetivos.addFilter("company", idcompany);
    objetivos.addFilter("contrato", idcontrato);
    objetivos.addFilter("detalle", iddetalle);
		objetivos.readAll();
		JIterator<BizObjetivosAvianca> it = objetivos.getStaticIterator();
    
    while (it.hasMoreElements()) {
    	BizObjetivosAvianca det = it.nextElement();
    	
    	BizObjetivosGrilla grilla = new BizObjetivosGrilla();
    	
    	grilla.pCompany.setValue(idcompany);
    	grilla.pIdContrato.setValue(idcontrato);
    	grilla.pRuta.setValue(det.getRuta());
    	grilla.pCantidad.setValue(det.getCantidad());
    	grilla.pFaltante.setValue(det.getFaltante());
    	grilla.pFMS.setValue(det.getFMS());
    	grilla.pMS.setValue(det.getMS());
    	grilla.pIngreso.setValue((det.getObjDetalleRuta()==null)?0:det.getObjDetalleRuta().getGanancia());
    	grilla.pTotal.setValue(det.getTotal());
    	grilla.pPuntos.setValue(det.getPuntos());
    	grilla.pCumplible.setValue(det.getTotal()>=detalle.getLimiteMinTkt());
    	int i=1;
    	JRecords<BizNivel> niveles = det.getObjDetalleRuta().getObjNiveles();
    	JIterator<BizNivel> itn = niveles.getStaticIterator();
    	while (itn.hasMoreElements()) {
    		BizNivel niv = itn.nextElement();
    		switch (i) {
	    		case 1:
	    			grilla.pLabelZonas1.setValue(getHtmlData(niv,true));
	    			grilla.pMS1.setValue(niv.getValor());
	    			grilla.pPuntos1.setValue(niv.getParam4());
	    			if (niv.getValor()<=grilla.pMS.getValue()) grilla.setCumple(i);
	    			break;
	    		case 2:
	    			grilla.pLabelZonas2.setValue(getHtmlData(niv,false));
	    			grilla.pMS2.setValue(niv.getValor());
	    			grilla.pPuntos2.setValue(niv.getParam4());
	    			if (niv.getValor()<=grilla.pMS.getValue()) grilla.setCumple(i);
	    			break;
	    		case 3:
	    			grilla.pLabelZonas3.setValue(getHtmlData(niv,false));
	    			grilla.pMS3.setValue(niv.getValor());
	    			grilla.pPuntos3.setValue(niv.getParam4());
	    			if (niv.getValor()<=grilla.pMS.getValue()) grilla.setCumple(i);
	    			break;
	    		case 4:
	    			grilla.pLabelZonas4.setValue(getHtmlData(niv,false));
	    			grilla.pMS4.setValue(niv.getValor());
	    			grilla.pPuntos4.setValue(niv.getParam4());
	    			if (niv.getValor()<=grilla.pMS.getValue()) grilla.setCumple(i);
	    			break;
	    		case 5:
	    			grilla.pLabelZonas5.setValue(getHtmlData(niv,false));
	    			grilla.pMS5.setValue(niv.getValor());
	    			grilla.pPuntos5.setValue(niv.getParam4());
	    			if (niv.getValor()<=grilla.pMS.getValue()) grilla.setCumple(i);
	    			break;
	    		case 6:
	    			grilla.pLabelZonas6.setValue(getHtmlData(niv,false));
	    			grilla.pMS6.setValue(niv.getValor());
	    			grilla.pPuntos6.setValue(niv.getParam4());
	    			if (niv.getValor()<=grilla.pMS.getValue()) grilla.setCumple(i);
	    			break;
	    		case 7:
	    			grilla.pLabelZonas7.setValue(getHtmlData(niv,false));
	    			grilla.pMS7.setValue(niv.getValor());
	    			grilla.pPuntos7.setValue(niv.getParam4());
	    			if (niv.getValor()<=grilla.pMS.getValue()) grilla.setCumple(i);
	    			break;
	    		case 8:
	    			grilla.pLabelZonas8.setValue(getHtmlData(niv,false));
	    			grilla.pMS8.setValue(niv.getValor());
	    			grilla.pPuntos8.setValue(niv.getParam4());
	    			if (niv.getValor()>=grilla.pMS.getValue()) grilla.setCumple(i);
	    			break;
	    		case 9:
	    			grilla.pLabelZonas9.setValue(getHtmlData(niv,false));
	    			grilla.pMS9.setValue(niv.getValor());
	    			grilla.pPuntos9.setValue(niv.getParam4());
	    			if (niv.getValor()>=grilla.pMS.getValue()) grilla.setCumple(i);
	    			break;
    		}
	    		
    		i++;
    	}
    	addItem(grilla);
    }
    	
    return true;
    
	}

  
}