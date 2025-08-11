package  pss.bsp.ticketsOracle;

import java.util.Date;

import pss.bsp.consolid.model.trxOra.GuiTrxOras;
import pss.bsp.consolidador.IConciliable;
import pss.common.security.BizUsuario;
import pss.core.services.JExec;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;
import pss.core.tools.collections.JIterator;
import pss.tourism.pnr.BizPNRTicket;

public class BizTicketsOracle extends JRecord implements IHeaderOracle {

	private JString pCompany=new JString();
	private JString pOwner=new JString();
	private JLong pId=new JLong();
	private JString pDescripcion=new JString();
	private JString pEstado=new JString();

	private JDate pFechaDesde=new JDate();
	private JDate pFechaHasta=new JDate();


	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}


	public boolean isNullCompany() throws Exception {
		return pCompany.isNull();
	}

	public void setNullToCompany() throws Exception {
		pCompany.setNull();
	}

	public void setOwner(String zValue) throws Exception {
		pOwner.setValue(zValue);
	}

	public String getOwner() throws Exception {
		return pOwner.getValue();
	}

	public boolean isNullOwner() throws Exception {
		return pOwner.isNull();
	}

	public void setNullToOwner() throws Exception {
		pOwner.setNull();
	}

	public void setId(long zValue) throws Exception {
		pId.setValue(zValue);
	}

	public long getId() throws Exception {
		return pId.getValue();
	}


	public void setDescripcion(String zValue) throws Exception {
		pDescripcion.setValue(zValue);
	}

	public String getDescripcion() throws Exception {
		return pDescripcion.getValue();
	}

	public boolean isNullDescripcion() throws Exception {
		return pDescripcion.isNull();
	}

	public void setNullToDescripcion() throws Exception {
		pDescripcion.setNull();
	}

	public void setEstado(String zValue) throws Exception {
		pEstado.setValue(zValue);
	}

	public String getEstado() throws Exception {
		return pEstado.getValue();
	}

	public boolean isNullEstado() throws Exception {
		return pEstado.isNull();
	}

	public void setNullToEstado() throws Exception {
		pEstado.setNull();
	}

	public void setFechaDesde(Date zValue) throws Exception {
		pFechaDesde.setValue(zValue);
	}

	public Date getPeriodoDesde() throws Exception {
		return pFechaDesde.getValue();
	}

	public boolean isNullFechaDesde() throws Exception {
		return pFechaDesde.isNull();
	}

	public void setNullToFechaDesde() throws Exception {
		pFechaDesde.setNull();
	}

	public void setFechaHasta(Date zValue) throws Exception {
		pFechaHasta.setValue(zValue);
	}

	public Date getPeriodoHasta() throws Exception {
		return pFechaHasta.getValue();
	}

	public boolean isNullFechaHasta() throws Exception {
		return pFechaHasta.isNull();
	}

	public void setNullToFechaHasta() throws Exception {
		pFechaHasta.setNull();
	}


	/**
	 * Constructor de la Clase
	 */
	public BizTicketsOracle() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("owner", pOwner);
		this.addItem("id", pId);
		this.addItem("descripcion", pDescripcion);
		this.addItem("estado", pEstado);
		this.addItem("fecha_desde", pFechaDesde);
		this.addItem("fecha_hasta", pFechaHasta);

	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Company", true, true, 50);
		this.addFixedItem(KEY, "id", "Id", false, false, 64);
		this.addFixedItem(FIELD, "owner", "Owner", true, true, 50);
		this.addFixedItem(FIELD, "descripcion", "Descripcion", true, false, 300);
		this.addFixedItem(FIELD, "estado", "Estado", true, false, 50);
		this.addFixedItem(FIELD, "fecha_desde", "Fecha desde", true, false, 20);
		this.addFixedItem(FIELD, "fecha_hasta", "Fecha hasta", true, false, 20);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "BSP_ORACLE";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(String zCompany,  String zIdpdf) throws Exception {
		addFilter("company", zCompany);
//		addFilter("owner", zOwner);
		addFilter("idPDF", zIdpdf);
		return read();
	}

	@Override
	public void processDelete() throws Exception {
	
		super.processDelete();
	}
	static long id=0;
	@Override
	public void processInsert() throws Exception {
		if (pOwner.isNull()) pOwner.setValue(BizUsuario.getUsr().GetUsuario());
		if (pEstado.isNull()) pEstado.setValue("PENDIENTE");
		pDescripcion.setValue("período "+JDateTools.DateToString(getPeriodoDesde())+" hasta "+JDateTools.DateToString(getPeriodoHasta()));
		super.processInsert();
	}

	
	public void execProcessToPNRs() throws Exception {
		JExec oExec = new JExec(this, "processToPNRs") {

			@Override
			public void Do() throws Exception {
				processToPNRs();
			}
		};
		oExec.execute();
	}

	public void processToPNRs() throws Exception {
		GuiTrxOras wins = new GuiTrxOras();
		wins.getRecords().addFixedFilter(
				"	WHERE apps.CNS_ARTRX_DK_V.fecha_emision \n"
				+ "      BETWEEN TO_DATE('"+JDateTools.DateToString( getPeriodoDesde(),"dd/MM/YYYY")+"', 'DD/MM/YYYY') \n"
				+ "          AND TO_DATE('"+JDateTools.DateToString( getPeriodoHasta(),"dd/MM/YYYY")+"', 'DD/MM/YYYY')\n"
			);
		//wins.getRecords().addFilter("fecha_cupon", getPeriodoDesde(),">=");
	//	wins.getRecords().addFilter("fecha_cupon", getPeriodoHasta(),"<=");
		JIterator<JRecord> it = wins.getRecords().getStaticIterator();
		while (it.hasMoreElements()) {
			JRecord rec= it.nextElement();
			if (!(rec instanceof IConciliable)) continue;
			IConciliable bsp = (IConciliable) rec;
			String boleto = bsp.getStringValue(IConciliable.BOLETOS, true);
			if (boleto==null) continue;
			BizPNRTicket ticket = new BizPNRTicket();
			ticket.dontThrowException(true);
			if (!ticket.ReadByBoleto(getCompany(), boleto)) {
				//RJL! OJO!
				continue;// deberia crearlo y luego ver los conflicts de entrada?
			} else {
/*				ticket.setCreationDate(bsp.getDateValue(IConciliable.FECHA));
 				ticket.setTarifaFactura(bsp.getDoubleValue(IConciliable.TARIFA));
 				ticket.setImpuestosTotalFactura(bsp.getDoubleValue(IConciliable.IMPUESTO_ACUM));
 				ticket.setImpuestoFactura(bsp.getDoubleValue(IConciliable.IMPUESTO_ACUM)-ticket.getIvaFactura());
 				
 				// tema con las comisiones a veces vienen negativas y a veces positivas... no se si esta bien esta solucion
 				if ((bsp.getDoubleValue(IConciliable.COMISION)>0 && bsp.getDoubleValue(IConciliable.TARIFA)>0) ||
 						(bsp.getDoubleValue(IConciliable.COMISION)<0 && bsp.getDoubleValue(IConciliable.TARIFA)<0)) {
	 				ticket.setComisionFactura(bsp.getDoubleValue(IConciliable.COMISION));
					ticket.setNetoFactura(bsp.getDoubleValue(IConciliable.TARIFA)-(bsp.getDoubleValue(IConciliable.COMISION)+bsp.getDoubleValue(IConciliable.COMISION_OVER)));
 				} else {
	 				ticket.setComisionFactura(-bsp.getDoubleValue(IConciliable.COMISION));
					ticket.setNetoFactura(bsp.getDoubleValue(IConciliable.TARIFA)+bsp.getDoubleValue(IConciliable.COMISION)-bsp.getDoubleValue(IConciliable.COMISION_OVER));
 				}
 				ticket.setComisionPerc(bsp.getDoubleValue(IConciliable.COMISION_PORC));
				ticket.setImporteover(""+bsp.getDoubleValue(IConciliable.COMISION_OVER));
*/				ticket.setVoid(bsp.getStringValue(IConciliable.OPERACION, true).indexOf("REFUND")!=-1);
			  if (ticket.isVoided()) {
			  	ticket.processAnular(null);
			  } else
			  	ticket.update();
			}
		}

		
		
		
		
	}
	
	
	@Override
	public void processUpdate() throws Exception {
		if (pOwner.isNull()) pOwner.setValue(BizUsuario.getUsr().GetUsuario());
		pDescripcion.setValue("período "+JDateTools.DateToString(getPeriodoDesde())+" hasta "+JDateTools.DateToString(getPeriodoHasta()));
		super.processUpdate();
	}

}
