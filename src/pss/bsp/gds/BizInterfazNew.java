package  pss.bsp.gds;

import java.util.Date;
import java.util.Vector;

import com.ibm.icu.util.Calendar;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.monitor.log.BizBspLog;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.JExec;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.collections.JIterator;
import pss.tourism.interfaceGDS.Manager;
import pss.tourism.pnr.BizPNRTicket;

public class BizInterfazNew extends JRecord {

  private JDateTime pLastPnr = new JDateTime();
  private JDateTime pLastSqlEvent = new JDateTime();
  private JDateTime pLastupdate = new JDateTime();
  private JFloat pBookingsTotal = new JFloat();
  private JFloat pBookingsMensual = new JFloat();
  private JFloat pTicketTotal = new JFloat();
  private JFloat pTicketMensual = new JFloat();
  private JString pCompany = new JString();
  private JString pMes12 = new JString();
  private JString pMes11 = new JString();
  private JString pMes10 = new JString();
  private JString pMes9 = new JString();
  private JString pMes8 = new JString();
  private JString pMes7 = new JString();
  private JString pMes6 = new JString();
  private JString pMes5 = new JString();
  private JString pMes4 = new JString();
  private JString pMes3 = new JString();
  private JString pMes2 = new JString();
  private JString pMes1 = new JString();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setBookingTotal(double zValue) throws Exception {    pBookingsTotal.setValue(zValue);  }
  public double getBookingTotal() throws Exception {     return pBookingsTotal.getValue();  }
  public void setBookingMensual(double zValue) throws Exception {    pBookingsMensual.setValue(zValue);  }
  public double getBookingMensual() throws Exception {     return pBookingsMensual.getValue();  }

  public void setTicketTotal(double zValue) throws Exception {    pTicketTotal.setValue(zValue);  }
  public double getTicketTotal() throws Exception {     return pTicketTotal.getValue();  }
  public void setTicketMensual(double zValue) throws Exception {    pTicketMensual.setValue(zValue);  }
  public double getTicketMensual() throws Exception {     return pTicketMensual.getValue();  }

  public void setLastPnr(Date zValue) throws Exception {    pLastPnr.setValue(zValue);  }
  public Date getLastPnr() throws Exception {     return pLastPnr.getValue();  }
  public void setLastSqlEvent(Date zValue) throws Exception {    pLastSqlEvent.setValue(zValue);  }
  public Date getLastSqlEvent() throws Exception {     return pLastSqlEvent.getValue();  }
  public void setLastupdate(Date zValue) throws Exception {    pLastupdate.setValue(zValue);  }
  public Date getLastupdate() throws Exception {     return pLastupdate.getValue();  }
  public boolean isNullLastupdate() throws Exception { return  pLastupdate.isNull(); } 
  public void setNullToLastupdate() throws Exception {  pLastupdate.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 

  public String getMes(int mes) throws Exception {     
  	if (mes==1) return pMes1.getValue();  
  	if (mes==2) return pMes2.getValue();  
  	if (mes==3) return pMes3.getValue();  
  	if (mes==4) return pMes4.getValue();  
  	if (mes==5) return pMes5.getValue();  
  	if (mes==6) return pMes6.getValue();  
  	if (mes==7) return pMes7.getValue();  
  	if (mes==8) return pMes8.getValue();  
  	if (mes==9) return pMes9.getValue();  
  	if (mes==10) return pMes10.getValue();  
  	if (mes==11) return pMes11.getValue();  
  	if (mes==12) return pMes12.getValue();
  	return "";
  }

  /**
   * Constructor de la Clase
   */
  public BizInterfazNew() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "book_tot", pBookingsTotal );
    this.addItem( "book_men", pBookingsMensual );
    this.addItem( "tick_tot", pTicketTotal );
    this.addItem( "tick_men", pTicketMensual );
    this.addItem( "lastpnr", pLastPnr );
    this.addItem( "lastsql", pLastSqlEvent );
    this.addItem( "lastupdate", pLastupdate );
    this.addItem( "company", pCompany );
    this.addItem( "mes12", pMes12 );
    this.addItem( "mes11", pMes11 );
    this.addItem( "mes10", pMes10 );
    this.addItem( "mes9", pMes9 );
    this.addItem( "mes8", pMes8 );
    this.addItem( "mes7", pMes7 );
    this.addItem( "mes6", pMes6 );
    this.addItem( "mes5", pMes5 );
    this.addItem( "mes4", pMes4 );
    this.addItem( "mes3", pMes3 );
    this.addItem( "mes2", pMes2 );
    this.addItem( "mes1", pMes1 );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( FIELD, "book_tot", "books total", true, false, 18 );
    this.addFixedItem( FIELD, "book_men", "books mensual", true, false, 18 );
    this.addFixedItem( FIELD, "tick_tot", "books total", true, false, 18 );
    this.addFixedItem( FIELD, "tick_men", "books mensual", true, false, 18 );
    this.addFixedItem( FIELD, "lastpnr", "last pnr", true, false, 18 );
    this.addFixedItem( FIELD, "lastsql", "Last sql", true, false, 18 );
    this.addFixedItem( FIELD, "lastupdate", "Lastupdate", true, false, 18 );
    this.addFixedItem( KEY, "company", "Company", true, true, 50 );
    this.addFixedItem( FIELD, "mes12", "mes 12", true, false, 100 );
    this.addFixedItem( FIELD, "mes11", "mes 11", true, false, 100 );
    this.addFixedItem( FIELD, "mes10", "mes 10", true, false, 100 );
    this.addFixedItem( FIELD, "mes9", "mes 9", true, false, 100 );
    this.addFixedItem( FIELD, "mes8", "mes 8", true, false, 100 );
    this.addFixedItem( FIELD, "mes7", "mes 7", true, false, 100 );
    this.addFixedItem( FIELD, "mes6", "mes 6", true, false, 100 );
    this.addFixedItem( FIELD, "mes5", "mes 5", true, false, 100 );
    this.addFixedItem( FIELD, "mes4", "mes 4", true, false, 100 );
    this.addFixedItem( FIELD, "mes3", "mes 3", true, false, 100 );
    this.addFixedItem( FIELD, "mes2", "mes 2", true, false, 100 );
    this.addFixedItem( FIELD, "mes1", "mes 1", true, false, 100 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "bsp_interface_news"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( String zCompany ) throws Exception { 
    addFilter( "company",  zCompany ); 
    return read(); 
  } 
  
  public Vector<String> execProcessFiles() throws Exception {
		JExec oExec = new JExec(null, "processFiles") {

			@Override
			public void Do() throws Exception {
				setOutput(processFiles());
			}
		};
		oExec.execute();
		return (Vector<String>)oExec.getOutput();
	}
  
  public void execActualizarAll() throws Exception {
		JExec oExec = new JExec(null, "actualizar") {

			@Override
			public void Do() throws Exception {
				actualizarAll();
			}
		};
		oExec.execute();
	}
  public void execActualizar(final Date lastSQL, final Date lastPNR, final Date lastUpdate) throws Exception {
		JExec oExec = new JExec(null, "actualizar") {

			@Override
			public void Do() throws Exception {
				actualizar(lastSQL,lastPNR,lastUpdate);
			}
		};
		oExec.execute();
	}
  public void actualizarAll( ) throws Exception {
  	JRecords<BizInterfazNew> regs= new JRecords<BizInterfazNew>(BizInterfazNew.class);
  	JIterator<BizInterfazNew> it = regs.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizInterfazNew comp = it.nextElement();
  		if (!comp.isEnable()) continue;
  		comp.actualizar(null, null,null);
  	}
  
  }  
  public void actualizar( Date lastSQL,  Date lastPNR,  Date lastUpdate) throws Exception {
			if (lastSQL!=null) setLastSqlEvent(lastSQL);
			if (lastPNR!=null) {
				if (JDateTools.dateEqualOrAfter(new Date(), lastPNR))
					setLastPnr(lastPNR);
				else
					setLastPnr(new Date());
			}
			setBookingTotal(calculeCantidadBookings());
			setTicketTotal(calculeCantidadTickets());
			setBookingMensual(calculeTicketsPromedioBooks());
			setTicketMensual(calculeTicketsPromedio());
			pMes1.setValue(calculeMes(1));
			pMes2.setValue(calculeMes(2));
			pMes3.setValue(calculeMes(3));
			pMes4.setValue(calculeMes(4));
			pMes5.setValue(calculeMes(5));
			pMes6.setValue(calculeMes(6));
			pMes7.setValue(calculeMes(7));
			pMes8.setValue(calculeMes(8));
			pMes9.setValue(calculeMes(9));
			pMes10.setValue(calculeMes(10));
			pMes11.setValue(calculeMes(11));
			pMes12.setValue(calculeMes(12));
			
			
			if (lastUpdate!=null) setLastupdate(lastUpdate);
			processUpdate();
  }
  
  public long calculeCantidadTickets() throws Exception {
  	JRecords<BizPNRTicket> tickets = new JRecords<BizPNRTicket>(BizPNRTicket.class);
  	
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
			tickets.addFixedFilter(" ( company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and customer_id_reducido = '"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
		} else
			tickets.addFilter("company", getCompany());
  	return tickets.selectCount();
  }
  public long calculeCantidadBookings() throws Exception {
  	JRecords<BizPNRTicket> tickets = new JRecords<BizPNRTicket>(BizPNRTicket.class);
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
			tickets.addFixedFilter(" ( company  in ("+ BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany()+") and customer_id_reducido = '"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
		} else
			tickets.addFilter("company", getCompany());
  	return (long)tickets.selectSum("cant_segmentos");
  }
  
  public long calculeTicketsPromedio() throws Exception {
  	JBaseRegistro regs=JBaseRegistro.recordsetFactory();
		double books=0;
		long cant = 0;
		String SQL ="select date_part('year', creation_date) , date_part('month', creation_date),coalesce(count(*),0) as books  from TUR_PNR_BOLETO";   
		SQL +=" where TUR_PNR_BOLETO.company = '"+getCompany()+"' ";
		SQL +=" group by date_part('year', creation_date) , date_part('month', creation_date)";
		regs.ExecuteQuery(SQL);
		while (regs.next()) {
			double book=regs.CampoAsFloat("books");
			if (book<50) continue;
  		books+=book;
  		cant++;
  		
  	}
  	return (long)((cant==0)?0:books/cant);
  }

  public String calculeMes(int mes) throws Exception {
  	JBaseRegistro regs=JBaseRegistro.recordsetFactory();
  	Calendar cal = Calendar.getInstance();
  	cal.setTime(new Date());
  	cal.add(Calendar.MONTH,-mes);
  	
  	int mesActual = cal.get(Calendar.MONTH)+1;
  	int anoActual = cal.get(Calendar.YEAR);
		double books=0;
		long cant = 0;
		String SQL ="select date_part('year', creation_date) , date_part('month', creation_date),coalesce(count(*),0) as books  from TUR_PNR_BOLETO";   
		SQL +=" where TUR_PNR_BOLETO.company = '"+getCompany()+"' and  date_part('year', creation_date)="+anoActual+" and date_part('month', creation_date)="+mesActual;
		SQL +=" group by date_part('year', creation_date) , date_part('month', creation_date)";
		regs.ExecuteQuery(SQL);
		while (regs.next()) {
			double book=regs.CampoAsFloat("books");
  		books+=book;
  		cant++;
  		
  	}
		long cantidad =(long)((cant==0)?0:books/cant);
  	return cantidad+"/"+Math.ceil((float)cantidad/500f);
  }

  public long calculeTicketsPromedioBooks() throws Exception {
		JBaseRegistro regs=JBaseRegistro.recordsetFactory();
		double books=0;
		long cant = 0;
		String SQL ="select date_part('year', creation_date) , date_part('month', creation_date),coalesce(sum(cant_segmentos),0) as books  from TUR_PNR_BOLETO";   
		SQL +=" where TUR_PNR_BOLETO.company = '"+getCompany()+"' ";
		SQL +=" group by date_part('year', creation_date) , date_part('month', creation_date)";
		regs.ExecuteQuery(SQL);
		while (regs.next()) {
			double book=regs.CampoAsFloat("books");
			if (book<50) continue;
  		books+=book;
  		cant++;
  		
  	}
  	return (long)((cant==0)?0:books/cant);
  }
  public boolean isEnable() throws Exception {
  	BizBSPCompany c= getObjCompany();
  	if (c==null) return false;
  	if (c.getObjExtraData()==null) return false;
  	return !c.getObjExtraData().getInactiva();
  }
  public  BizBSPCompany getObjCompany() throws Exception {
  	if (pCompany.isNull()) return null;
  	return BizBSPCompany.getObjBSPCompany(pCompany.getValue());
  }
  
  public Vector<String> processFiles() throws Exception {
		BizBspLog.log("DEFAULT", "PRINCIPAL", BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG , "Inicio procesamiento PNRs", null, null, null, 0, 0, 0);

  	Manager mgr = new Manager();
		Vector<String> companies = new Vector<String>();
		int fileProc=mgr.processFiles(companies);
		BizBspLog.log("DEFAULT", "PRINCIPAL", BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_LOG , "Fin procesamiento PNRs [%4]", null, null, null, fileProc, 0, 0);

		return companies;
  }
}
