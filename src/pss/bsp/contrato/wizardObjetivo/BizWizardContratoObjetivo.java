package pss.bsp.contrato.wizardObjetivo;

import java.util.Date;

import com.ibm.icu.util.Calendar;

import pss.bsp.contrato.BizContrato;
import pss.bsp.contrato.conocidos.BizContratoConocido;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalleObjetivo.BizDetalleObjetivo;
import pss.bsp.contrato.detalleUpfrontRutas.BizDetalleUpfrontRuta;
import pss.core.services.JExec;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JMultiple;
import pss.core.services.fields.JObjBD;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.tourism.carrier.BizCarrier;

public class BizWizardContratoObjetivo  extends JRecord {

  private JString pAerolineas = new JString();
  private JDate pFechaDesde = new JDate();
  private JDate pFechaHasta = new JDate();
  private JString pCompany = new JString();
  private JString pDescripcion = new JString();
  private JString pPeriodo = new JString();
  private JObjBD pDetalle = new JObjBD() {
  	public void preset() throws Exception {
  		if (pDetalle.isRawNotNull()) return;
  		pDetalle.setValue(getDetalle());
  	};
  };
  private JLong pPaso = new JLong();

  private JMultiple pIdContrato = new JMultiple();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setAerolineas(String zValue) throws Exception {    pAerolineas.setValue(zValue);  }
  public String getAerolineas() throws Exception {     return pAerolineas.getValue();  }
  public boolean isNullAerolineas() throws Exception { return  pAerolineas.isNull(); } 
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public boolean isNullDescripcion() throws Exception { return  pDescripcion.isNull(); } 
  public void setNullToDescripcion() throws Exception {  pDescripcion.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public String getPeriodo() throws Exception {     return pPeriodo.getValue();  }
  public void setPaso(long zValue) throws Exception {    pPaso.setValue(zValue);  }
  public long getPaso() throws Exception {     return pPaso.getValue();  }
  public void setFechaDesde(Date zValue) throws Exception {    pFechaDesde.setValue(zValue);  }
  public Date getFechaDesde() throws Exception {     return pFechaDesde.getValue();  }

  public boolean isNullFechaDesde() throws Exception { return  pFechaDesde.isNull(); } 
  public void setNullToFechaDesde() throws Exception {  pFechaDesde.setNull(); } 
  public void setFechaHasta(Date zValue) throws Exception {    pFechaHasta.setValue(zValue);  }
  public Date getFechaHasta() throws Exception {     return pFechaHasta.getValue();  }
  public boolean isNullFechaHasta() throws Exception { return  pFechaHasta.isNull(); } 
  public void setNullToFechaHasta() throws Exception {  pFechaHasta.setNull(); } 
  public void setIdContrato(JList<String> zValue) throws Exception {    pIdContrato.setValue(zValue);  }
  public JList<String> getIdContrato() throws Exception {     return pIdContrato.getValue();  }
 
  BizDetalle objDetalle;
  public BizDetalle getDetalle() throws Exception {     
  	if (objDetalle!=null) return (BizDetalle)objDetalle;
  	BizDetalleObjetivo det = new BizDetalleObjetivo();
  	det.setCompany(getCompany());
  	return objDetalle =det; 
  }

  /**
   * Constructor de la Clase
   */
  public BizWizardContratoObjetivo() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "paso", pPaso );
    this.addItem( "id_aerolineas", pAerolineas );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "periodo", pPeriodo );
    this.addItem( "fecha_desde", pFechaDesde );
    this.addItem( "fecha_hasta", pFechaHasta );
    this.addItem( "paso", pPaso );
    this.addItem( "detalle", pDetalle );
    this.addItem( "id_contrato", pIdContrato);
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, false, 15 );
    this.addFixedItem( FIELD, "paso", "paso", true, false, 16 );
    this.addFixedItem( FIELD, "id_aerolineas", "Aerolineas", true, false, 15 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, false, 500 );
    this.addFixedItem( FIELD, "periodo", "periodo", true, false, 18 );
    this.addFixedItem( FIELD, "fecha_desde", "Fecha desde", true, false, 18 );
    this.addFixedItem( FIELD, "fecha_hasta", "Fecha hasta", true, false, 18 );
    this.addFixedItem( FIELD, "paso", "paso", true, false, 18 );
    this.addFixedItem( FIELD, "id_contrato", "contratos", true, false, 18 );
    this.addFixedItem( RECORD, "detalle", "detalle", true, false, 18 ).setClase(BizDetalle.class);
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return ""; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public BizCarrier getObjCarrier() throws Exception {
  	if (pAerolineas.isNull()) return null;
  	BizCarrier event = new BizCarrier();
  	event.dontThrowException(true);
  	if (!event.Read(getAerolineas())) return null;
  	return event;
  	
  }
  
  public BizContrato getObjContrato() throws Exception {
  	BizContrato c = new BizContrato();
  	c.setFechaDesde(getFechaDesde());
  	c.setFechaHasta(getFechaHasta());
  	return c;
  	
  }
 
 
  
 	public BizContrato execGenerarContrato(final BizDetalle rec) throws Exception {
 		JExec oExec = new JExec(null, "processGenerarContrato") {

 			@Override
 			public void Do() throws Exception {
 				setOutput(processGenerarContrato(rec));
 			}
 		};
 		oExec.execute();
 		return (BizContrato)oExec.getOutput();
 	}

	public JRecords<BizContrato> getContratosCandidatos() throws Exception {
		JRecords<BizContrato> eventsSearch = new  JRecords<BizContrato>(BizContrato.class);
		JRecords<BizContrato> events = new  JRecords<BizContrato>(BizContrato.class);
		events.setStatic(true);
		events.addFilter("tipo_contrato", "O");
  	boolean onlyOne=false;
  	boolean find=false;
  	eventsSearch.addFilter("company", getCompany());
  	eventsSearch.addFilter("tipo_contrato", "O");
  	eventsSearch.addFilter("descripcion", "Objetivo ", "like");
  	if (getFechaDesde()==null) {
  		eventsSearch.addOrderBy("fecha_desde", "DESC");
  		onlyOne=true;
  	} else {
  		eventsSearch.addFilter("fecha_hasta", getFechaDesde(), ">=");
  		eventsSearch.addOrderBy("fecha_desde", "ASC");
  	}
  	Date lastFechaHasta;
  	JIterator<BizContrato> it=eventsSearch.getStaticIterator();
  	while(it.hasMoreElements()) {
  		BizContrato c = it.nextElement();
  		lastFechaHasta = c.getFechaHasta();
  		if (!c.getPeriodo().equals(getPeriodo())) continue;
  		find=true;
  		events.addItem(c);
  		if (onlyOne) break;
  	}
  	
  	
  	if (!find) {
  		BizContrato c = new BizContrato();
  		c.setDescripcion("Nuevo Contrato");
  		if (getFechaDesde()==null) 
  			fillFechas(c,new Date());
  		else
  			fillFechas(c,getFechaDesde());
  		events.addItem(c);
  		
  	}
  		
  	return events;
  }
	private void fillFechas(BizContrato c,Date fdesde) throws Exception {
		Calendar fci = Calendar.getInstance();
		Calendar fcf = Calendar.getInstance();
		fci.setTime(JDateTools.getFirstDayOfMonth(fdesde));
		fcf.setTime(JDateTools.getFirstDayOfMonth(fdesde));
		if (getPeriodo().equals(BizContratoConocido.MENSUAL)) {
			c.setFechaDesde(JDateTools.getFirstDayOfMonth(fdesde));
			c.setFechaHasta(JDateTools.getLastDayOfMonth(fdesde));
			return;
		}
		if (getPeriodo().equals(BizContratoConocido.BIMESTRAL)) {
			int mes = fci.get(Calendar.MONTH);
			int mesDesde = (mes/2)*2;
			int mesFin = ((mes/2)*2)+1;
			fci.set(Calendar.MONTH,mesDesde);
			fcf.set(Calendar.MONTH,mesFin);
			c.setFechaDesde(JDateTools.getFirstDayOfMonth(fci.getTime()));
			c.setFechaHasta(JDateTools.getLastDayOfMonth(fcf.getTime()));
			return;
		}
		if (getPeriodo().equals(BizContratoConocido.TRIMESTRAL)) {
			int mes = fci.get(Calendar.MONTH);
			int mesDesde = (mes/3)*3;
			int mesFin = ((mes/3)*3)+2;
			fci.set(Calendar.MONTH,mesDesde);
			fcf.set(Calendar.MONTH,mesFin);
			c.setFechaDesde(JDateTools.getFirstDayOfMonth(fci.getTime()));
			c.setFechaHasta(JDateTools.getLastDayOfMonth(fcf.getTime()));
			return;
		}
		if (getPeriodo().equals(BizContratoConocido.CUATRIMESTRAL)) {
			int mes = fci.get(Calendar.MONTH);
			int mesDesde = (mes/4)*4;
			int mesFin = ((mes/4)*4)+3;
			fci.set(Calendar.MONTH,mesDesde);
			fcf.set(Calendar.MONTH,mesFin);
			c.setFechaDesde(JDateTools.getFirstDayOfMonth(fci.getTime()));
			c.setFechaHasta(JDateTools.getLastDayOfMonth(fcf.getTime()));
			return;
		}
		if (getPeriodo().equals(BizContratoConocido.SEMESTRAL)) {
			int mes = fci.get(Calendar.MONTH);
			int mesDesde = (mes/6)*6;
			int mesFin = ((mes/6)*6)+5;
			fci.set(Calendar.MONTH,mesDesde);
			fcf.set(Calendar.MONTH,mesFin);
			c.setFechaDesde(JDateTools.getFirstDayOfMonth(fci.getTime()));
			c.setFechaHasta(JDateTools.getLastDayOfMonth(fcf.getTime()));
			return;
		}
		if (getPeriodo().equals(BizContratoConocido.ANUAL)) {
			c.setFechaDesde(JDateTools.getFirstDayOfYear(fdesde));
			c.setFechaHasta(JDateTools.getLastDayOfYear(fdesde));
			return;
		}
		if (getPeriodo().equals(BizContratoConocido.MARZOAMARZO)) {
			int mes = fci.get(Calendar.MONTH);
			Calendar pivot = Calendar.getInstance();
			pivot.clear();
			pivot.set(fci.get(Calendar.YEAR), 2, 1,0,0,0);
			//pivot.getTime() fci.getTime()
			if (pivot.after(fci)&&!pivot.equals(fci)) {
				fci.set(Calendar.MONTH,2);
				fcf.set(Calendar.MONTH,2);
				fci.add(Calendar.MONTH,-12);
			} else {
				fci.set(Calendar.MONTH,2);
				fcf.set(Calendar.MONTH,2);
				fcf.add(Calendar.MONTH,12);
				
			}
			c.setFechaDesde(JDateTools.getFirstDayOfMonth(fci.getTime()));
			c.setFechaHasta(JDateTools.getLastDayOfMonth(fcf.getTime()));
			return;
		}
	}
	
	public BizContrato processGenerarContrato(BizDetalle rec) throws Exception {
		BizContrato last=null;
		JIterator<BizContrato> it = getContratosCandidatos().getStaticIterator();
		while (it.hasMoreElements()) {
			BizContrato contrato = it.nextElement();
			if (contrato.getDescripcion().equals("Nuevo Contrato")) {
					contrato.setCompany(getCompany());
					contrato.setDescripcion("Objetivo "+getPeriodo());
					contrato.setFechaDesde(contrato.getFechaDesde());
					contrato.setFechaHasta(contrato.getFechaHasta());
					contrato.setTipoContrato("O");
					contrato.processInsert();
					contrato.setId(contrato.getIdentity("id"));
				}
			if (rec==null) {
				last=contrato;
					continue;
			}
	
			BizDetalle detalle = getDetalle();
				
			BizDetalle det = rec;
			det.copyProperties(detalle);
			det.setCompany(contrato.getCompany());
			det.setId(contrato.getId());
			det.setObjContrato(contrato);
			det.processInsert();
			det.setLinea(det.getIdentity("linea"));
			
			last=contrato;
		}
		
		return last;
	}


	public long getPrevPaso() throws Exception {
		long paso = getPaso();
		paso = paso - 1;
		return paso;
	}
	public boolean hayMasPasos() throws Exception {
		long paso = getPaso();
		long ultimoPaso=3;

		return paso<(ultimoPaso);
		
	}	
	public long getNextPaso() throws Exception {
		long paso = getPaso();
		paso = paso + 1;
     
		return paso;
	}
	
	
	

	
	

}
