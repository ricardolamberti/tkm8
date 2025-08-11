package pss.bsp.contrato.detalle.prorrateo.tickets;

import java.util.Calendar;
import java.util.Map;

import edu.emory.mathcs.backport.java.util.TreeMap;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.prorrateo.BizProrrateo;
import pss.core.services.fields.JIntervalDate;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JOrderedMap;
import pss.tourism.pnr.BizBooking;
import pss.tourism.pnr.BizPNRTicket;
import pss.tourism.pnr.GuiBookings;
import pss.tourism.pnr.GuiPNRTickets;

public class BizTicketProrrateos extends JRecords<BizTicketProrrateo> {
;
	public Class<BizTicketProrrateo> getBasedClass() {
		return BizTicketProrrateo.class;
	}
	
	

	@Override
	public boolean readAll() throws Exception {
    String idcompany = getFilterValue("company");

    long iddetalle = getFilterValue("detalle")==null?0:Long.parseLong(getFilterValue("detalle"));
    JIntervalDate intervalo = getFilterIntervalValue("fecha");
   
    if (iddetalle!=0) {
  		this.endStatic();
      this.setStatic(true);
      BizDetalle detalle = new BizDetalle();
      detalle.dontThrowException(true);
      if (!detalle.read(iddetalle)) return false;
      
      fillDetalle(detalle,null);
  		this.Ordenar("customer");
    } else {
      this.setStatic(true);
    	this.firstRecord();
//    	JRecords<BizDetalle> recs = new JRecords<BizDetalle>(BizDetalle.class);
//    	recs.addJoin(JRelations.JOIN,"bsp_contrato","bsp_contrato_detalle.company = bsp_contrato.company and bsp_contrato_detalle.id=bsp_contrato.id");
//    	recs.addFilter("company", idcompany);
//    	if (intervalo!=null) recs.addFilter("bsp_contrato","fecha_desde", intervalo.getStartDateValue(), "JDATE", ">=", "AND", "(");
//    	if (intervalo!=null) recs.addFilter("bsp_contrato","fecha_hasta", intervalo.getEndDateValue(), "JDATE", "<=", "OR", ")");
//    	JIterator<BizDetalle> it = recs.getStaticIterator();
//    	while (it.hasMoreElements()) {
//    		BizDetalle det = it.nextElement();
//    		if (!det.isUpfront()) continue;
//    		if (!det.getDetalleGanancia().getWinRef().getClass().equals(GuiPNRTicket.class)) continue;
//    		fillDetalle(det,intervalo);
//    	}
//  		this.Ordenar("customer");
    }
    
    

		return true;

	}
	
	@Override
	public JRecords<BizTicketProrrateo> toStatic() throws Exception {
		if (this.isStatic())
			return this;
		if (!this.isResultSetOpened())
			this.readAll();
		//this.filledItems();
		this.setStatic(true);
		this.firstRecord();
		return this;
	}
	public void fillDetalle(BizDetalle detalle,JIntervalDate intervalo) throws Exception {
		JOrderedMap<String, Double> acumPremios = JCollectionFactory.createOrderedMap();
    
    double comdefa = 0;
    JRecords<BizProrrateo> prs = new  JRecords<BizProrrateo>(BizProrrateo.class);
    prs.addFilter("company", detalle.getCompany());
    prs.addFilter("contrato", detalle.getId());
    prs.addFilter("detalle", detalle.getLinea());
  	JIterator<BizProrrateo> itp = prs.getStaticIterator();
		while (itp.hasMoreElements()) {
			BizProrrateo pr = itp.nextElement();
			JList<String> cls = pr.getCliente();
			JIterator<String> clsit = cls.getIterator();
			while (clsit.hasMoreElements()) {
				String cl = clsit.nextElement();
				acumPremios.addElement(cl, pr.getComision());
			}
		
			if (cls.isEmpty())
				comdefa = pr.getComision();
		}

		intervalo = getFilterIntervalValue("fecha");
    Calendar periodo = Calendar.getInstance();
    periodo.setTime(detalle.getFDesde());
    
    if (detalle.getDetalleGanancia() instanceof GuiPNRTickets) {
    	if (intervalo!=null) {
    		detalle.setFechaDesdeCalculo(intervalo.getStartDateValue());
    		detalle.setFechaCalculo(intervalo.getEndDateValue());
    	}
  		GuiPNRTickets tkts = (GuiPNRTickets)detalle.getDetalleGanancia();
  		int id=0;
  		JIterator<BizPNRTicket> it = tkts.getRecords().getStaticIterator();
  		while (it.hasMoreElements()) {
  			BizPNRTicket tkt = it.nextElement();
  			if (!tkt.hasCustomerIdReducido()) continue;
  			if (intervalo!=null && !intervalo.inInterval(tkt.getCreationDate())) continue;
  			BizTicketProrrateo obj = new BizTicketProrrateo();
  			obj.pCompany.setValue(detalle.getCompany());
  			obj.pTicket.setValue(tkt);
  			obj.pCustomer.setValue(tkt.getCustomerIdReducido());
  			obj.pId.setValue(id++);
  			obj.pCodigoMoneda.setValue(tkt.getCodigoMoneda());
  			obj.pIdContrato.setValue(detalle.getId());
  			obj.pLinea.setValue(detalle.getLinea());
  			String cl = tkt.getCustomerIdReducido();
  			Double porc = acumPremios.getElement(cl);
				if (porc == null) {
					porc = acumPremios.getElement("ALL");
				}
				if (porc == null)
					porc = 0.0;
  			obj.pPorcentaje.setValue(porc);
  			obj.pComision.setValue(JTools.rd(tkt.getTarifaFactura()*obj.pPorcentaje.getValue()/100,2));
  			obj.pFecha.setValue(tkt.getCreationDate());
  			addItem(obj);
  			
  		} 
  		   	
    }else {
    	Map<String,String> map = new TreeMap();
    	GuiBookings tkts = (GuiBookings)detalle.getDetalleGanancia();
  		int id=0;
  		JIterator<BizBooking> it = tkts.getRecords().getStaticIterator();
  		while (it.hasMoreElements()) {
  			BizBooking bok = it.nextElement();
  			if (map.get(bok.getNumeroBoleto())!=null) continue;
  			map.put(bok.getNumeroBoleto(),bok.getNumeroBoleto());
  			BizPNRTicket tkt = bok.getObjTicket();
  			if (!tkt.hasCustomerIdReducido()) continue;
  			if (intervalo!=null && !intervalo.inInterval(tkt.getCreationDate())) continue;
  			BizTicketProrrateo obj = new BizTicketProrrateo();
  			obj.pCompany.setValue(detalle.getCompany());
  			obj.pTicket.setValue(tkt);
  			obj.pCustomer.setValue(tkt.getCustomerIdReducido());
  			obj.pId.setValue(id++);
  			obj.pCodigoMoneda.setValue(tkt.getCodigoMoneda());
  			obj.pIdContrato.setValue(detalle.getId());
  			obj.pLinea.setValue(detalle.getLinea());
  			String cl = tkt.getCustomerIdReducido();
  			Double porc = acumPremios.getElement(cl);
  			if (porc == null) {
					porc = acumPremios.getElement("ALL");
				}
				if (porc == null)
					porc = 0.0;
				obj.pPorcentaje.setValue(porc);
  			obj.pComision.setValue(JTools.rd(tkt.getTarifaFactura()*obj.pPorcentaje.getValue()/100,2));
  			obj.pFecha.setValue(tkt.getCreationDate());
  			addItem(obj);
  			
  		} 	
		}


		

		
	}

  
  
}