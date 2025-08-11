package pss.bsp.contrato.detalle.prorrateo.cliente;

import java.util.Calendar;
import java.util.Map;
import java.util.StringTokenizer;

import edu.emory.mathcs.backport.java.util.TreeMap;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.prorrateo.BizProrrateo;
import pss.bsp.contrato.detalle.prorrateo.tickets.BizTicketProrrateo;
import pss.bsp.contrato.detalle.prorrateo.tickets.BizTicketProrrateos;
import pss.bsp.dk.BizClienteDK;
import pss.common.customList.config.relation.JRelations;
import pss.common.event.mailing.BizMailingPersona;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JIntervalDate;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JOrderedMap;
import pss.tourism.pnr.BizBooking;
import pss.tourism.pnr.BizPNRTicket;
import pss.tourism.pnr.GuiBookings;
import pss.tourism.pnr.GuiPNRTickets;

public class BizClienteProrrateos  extends JRecords<BizClienteProrrateo> {
;
	public Class<BizClienteProrrateo> getBasedClass() {
		return BizClienteProrrateo.class;
	}
	long oldIdDetalle;
	String oldClientes;
	JIntervalDate oldIntervalo;
	
	JMap<String,BizClienteProrrateo> lastClientes;
	@Override
	public boolean readAll() throws Exception {
	  String idcompany = getFilterValue("company");
    lastClientes=JCollectionFactory.createMap();
    long iddetalle = getFilterValue("detalle")==null?0:Long.parseLong(getFilterValue("detalle"));
    String clientes = getFilterValue("cliente")==null?null:getFilterValue("cliente");
    JIntervalDate intervalo = getFilterIntervalValue("periodo");
    JList<String> listaClientes = null;
    
		BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Consultando la base", 0, 100, false, null);

    if (oldIntervalo!=null&&iddetalle==oldIdDetalle && (( oldClientes==null && clientes==null)||(oldClientes!=null&& oldClientes.equals(clientes))) && oldIntervalo.equals(intervalo)) {
    	this.firstRecord();
    	return true;
    }
  	this.endStatic();
    this.setStatic(true);
    if (intervalo!=null)
     oldIntervalo=new JIntervalDate(intervalo.getStartDateValue(),intervalo.getEndDateValue());
    oldClientes=clientes;
    oldIdDetalle=iddetalle;
    if (clientes!=null) {
    	listaClientes = JCollectionFactory.createList();
      StringTokenizer toks = new StringTokenizer(clientes,",;|.");
	    while (toks.hasMoreElements()) {
	    	listaClientes.addElement(toks.nextToken().toLowerCase());
	    }
    }
    if (iddetalle!=0) {
      BizDetalle detalle = new BizDetalle();
      detalle.dontThrowException(true);
      if (!detalle.read(iddetalle)) return false;
      
      fillDetalle(detalle,null,listaClientes,1,2);
    } else {
    	JRecords<BizDetalle> recs = new JRecords<BizDetalle>(BizDetalle.class);
    	recs.addJoin(JRelations.JOIN,"bsp_contrato","bsp_contrato_detalle.company = bsp_contrato.company and bsp_contrato_detalle.id=bsp_contrato.id");
    	recs.addFilter("company", idcompany);
    	if (intervalo!=null) recs.addFilter("bsp_contrato","fecha_hasta", JDateTools.getDateStartDay(intervalo.getStartDateValue()), "JDATE", ">=", "AND", "(");
    	if (intervalo!=null) recs.addFilter("bsp_contrato","fecha_desde", JDateTools.getDateEndDay(intervalo.getEndDateValue()), "JDATE", "<=", "OR", ")");
  		recs.addOrderBy("bsp_contrato","id","asc");
  		recs.addOrderBy("prioridad","asc");
    	int row=0;
    	JIterator<BizDetalle> it = recs.getStaticIterator();
    	while (it.hasMoreElements()) {
    		BizDetalle det = it.nextElement();
    		if (!det.isUpfront()) continue;
    	//	if (!det.getDetalleGanancia().getWinRef().getClass().equals(GuiPNRTicket.class)) continue;
    		fillDetalle(det,intervalo,listaClientes,++row,recs.getStaticItems().size()+1);
    	}
    }
    
		BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Preparando grilla", 99, 100, false, null);
    
		this.Ordenar("cliente");

		return true;

	}
	
	@Override
	public JRecords<BizClienteProrrateo> toStatic() throws Exception {
		if (this.isStatic())
			return this;
		if (!this.isResultSetOpened())
			this.readAll();
		//this.filledItems();
		this.setStatic(true);
		this.firstRecord();
		return this;
	}
	public void fillDetalle(BizDetalle detalle,JIntervalDate intervalo,JList<String> listaClientes, int row, int total ) throws Exception {
		JOrderedMap<String, Double> acumPremios = JCollectionFactory.createOrderedMap();
		BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Analizando "+detalle.getDescripcion(), row, total, false, null);
    
    double comdefa = 0;
    JRecords<BizProrrateo> prs = new  JRecords<BizProrrateo>(BizProrrateo.class);
    prs.addFilter("company", detalle.getCompany());
    prs.addFilter("contrato", detalle.getId());
    prs.addFilter("detalle", detalle.getLinea());
    JIterator<BizProrrateo> itp = prs.getStaticIterator();
    boolean find=false;
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
    	find = true;
    }
    if (!find) return;
    Calendar periodo = Calendar.getInstance();
    periodo.setTime(detalle.getFDesde());

    if (detalle.getDetalleGanancia() instanceof GuiPNRTickets) {
  		GuiPNRTickets tkts = (GuiPNRTickets)detalle.getDetalleGanancia();
  		int id=0;
  		JIterator<BizPNRTicket> it = tkts.getRecords().getStaticIterator();
  		while (it.hasMoreElements()) {
  			BizPNRTicket tkt = it.nextElement();
  			if (tkt.getNumeroboleto().equals("3326768732"))
  				PssLogger.logDebug("info");
  			if (!tkt.hasCustomerIdReducido()) continue;
  			if (listaClientes !=null && !(listaClientes.containsElement(tkt.getCustomerIdReducido().toLowerCase()))) continue;
  			if (intervalo!=null && !intervalo.inInterval(tkt.getCreationDate())) continue;
  			
  			String cl = tkt.getCustomerIdReducido();
  			BizClienteProrrateo lastCliente = lastClientes.getElement(cl);
  			if (lastCliente==null) {
  	  		BizClienteDK mail = new BizClienteDK();
  	  		mail.dontThrowException(true);
  	  		mail.ReadByDK( tkt.getCompany(),cl);
  	  		lastCliente = new BizClienteProrrateo();
  	  		lastCliente.setCliente(cl);
  	  		lastCliente.setCompany(tkt.getCompany());
  	  		lastCliente.setClienteDescripcion(mail.getDescripcion());
  	  		lastCliente.setClienteNumero(mail.getDK());
  	  		lastCliente.setDetalle(new BizTicketProrrateos());
  	  		lastCliente.setCodigoMoneda(tkt.getCodigoMoneda());
  	  		if (intervalo!=null)
  	  			lastCliente.setPeriodo(intervalo.getValue());
  	  		lastCliente.setMonto(0);
  	  		lastCliente.setComision(0);
  	  		addItem(lastCliente);
  	  		lastCliente.getDetalle().addFilter("fecha", intervalo);
  	  		lastClientes.addElement(cl, lastCliente);
  			}
    		

  			BizTicketProrrateo obj = new BizTicketProrrateo();
  			obj.pCompany.setValue(detalle.getCompany());
  			obj.pTicket.setValue(tkt);
  			obj.pCustomer.setValue(tkt.getCustomerIdReducido());
  			obj.pId.setValue(id++);
  			obj.pIdContrato.setValue(detalle.getId());
  			obj.pCodigoMoneda.setValue(tkt.getCodigoMoneda());
  			obj.pLinea.setValue(detalle.getLinea());
  			Double proc = acumPremios.getElement(tkt.getCustomerIdReducido());
  			obj.pPorcentaje.setValue(proc==null?comdefa:proc.doubleValue());
  			obj.pComision.setValue(JTools.rd(tkt.getTarifaFactura()*obj.pPorcentaje.getValue()/100,2));
  			obj.pFecha.setValue(tkt.getCreationDate());
  			lastCliente.setMonto(lastCliente.getMonto()+tkt.getTarifaFactura());
  			lastCliente.setComision(lastCliente.getComision()+obj.pComision.getValue());
  			lastCliente.getDetalle().addItem(obj);
  			
  		}
  		
  		

    	
    } else {
    	Map<String,String> map = new TreeMap();
    	GuiBookings tkts = (GuiBookings)detalle.getDetalleGanancia();
  		int id=0;
  		JIterator<BizBooking> it = tkts.getRecords().getStaticIterator();
  		while (it.hasMoreElements()) {
  			BizBooking bok = it.nextElement();
   			if (bok.getNumeroBoleto().equals("3326768732"))
  				PssLogger.logDebug("info");
   			if (map.get(bok.getNumeroBoleto())!=null) continue;
  			map.put(bok.getNumeroBoleto(),bok.getNumeroBoleto());
  			BizPNRTicket tkt = bok.getObjTicket();
  			if (!tkt.hasCustomerIdReducido()) continue;
   			if (listaClientes !=null && !(listaClientes.containsElement(tkt.getCustomerIdReducido().toLowerCase()))) continue;
   		  if (intervalo!=null && !intervalo.inInterval(tkt.getCreationDate())) continue;
  			
  			String cl = tkt.getCustomerIdReducido();
  			BizClienteProrrateo lastCliente = lastClientes.getElement(cl);
  			if (lastCliente==null) {
  	  		BizMailingPersona mail = new BizMailingPersona();
  	  		mail.dontThrowException(true);
  	  		mail.read( BizMailingPersona.CLIENTE_REDUCIDO,tkt.getCompany(),cl);
  	  		lastCliente = new BizClienteProrrateo();
  	  		lastCliente.setCliente(cl);
  	  		lastCliente.setCompany(tkt.getCompany());
  	  		lastCliente.setClienteDescripcion(mail.getDescripcion());
  	  		lastCliente.setClienteNumero(mail.getCodigo());
  	  		lastCliente.setDetalle(new BizTicketProrrateos());
  	  		lastCliente.setCodigoMoneda(tkt.getCodigoMoneda());
  	  		lastCliente.setPeriodo(intervalo.getValue());
  	  		lastCliente.setMonto(0);
  	  		lastCliente.setComision(0);
  	  		lastCliente.getDetalle().addFilter("fecha", intervalo);
  	  		addItem(lastCliente);
  	  		lastClientes.addElement(cl, lastCliente);
  			}
    		

  			BizTicketProrrateo obj = new BizTicketProrrateo();
  			obj.pCompany.setValue(detalle.getCompany());
  			obj.pTicket.setValue(tkt);
  			obj.pCustomer.setValue(tkt.getCustomerIdReducido());
  			obj.pId.setValue(id++);
  			obj.pIdContrato.setValue(detalle.getId());
  			obj.pCodigoMoneda.setValue(tkt.getCodigoMoneda());
  			obj.pLinea.setValue(detalle.getLinea());
  			Double proc = acumPremios.getElement(tkt.getCustomerIdReducido());
  			obj.pPorcentaje.setValue(proc==null?comdefa:proc.doubleValue());
  			obj.pComision.setValue(JTools.rd(tkt.getTarifaFactura()*obj.pPorcentaje.getValue()/100,2));
  			obj.pFecha.setValue(tkt.getCreationDate());
  			lastCliente.setMonto(lastCliente.getMonto()+tkt.getTarifaFactura());
  			lastCliente.setComision(lastCliente.getComision()+obj.pComision.getValue());
  			lastCliente.getDetalle().addItem(obj);
  			
  		}
  		
  		

    }
    
		
	}

  
  
}