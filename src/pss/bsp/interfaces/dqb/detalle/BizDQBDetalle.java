package pss.bsp.interfaces.dqb.detalle;

import java.util.Date;

import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.security.BizUsuario;
import pss.core.services.JExec;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.tourism.pnr.BizPNRTicket;

public class BizDQBDetalle extends JRecord {

	
	
	public static final String OK ="OK";
	public static final String ERROR ="ERROR";

	private JString pCompany = new JString();
	private JString pOwner = new JString();
	private JString pIdpdf = new JString();
	private JLong pLinea = new JLong();
	private JString pIdAerolinea = new JString();
	private JDate pFecha = new JDate();
	private JString pGds = new JString();
	private JString pTipoDoc = new JString();
	private JString pBoleto = new JString();
	private JString pPnr = new JString();
	private JString pStatus = new JString();
	private JLong pInterfaceId = new JLong();
	private JString pCodeCons = new JString();
	private JString pConsolicion = new JString();
	
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setIdAerolinea(String zValue) throws Exception {
		pIdAerolinea.setValue(zValue);
	}

	public String getIdAerolinea() throws Exception {
		return pIdAerolinea.getValue();
	}

	public void setFecha(Date zValue) throws Exception {
		pFecha.setValue(zValue);
	}

	public Date getFecha() throws Exception {
		return pFecha.getValue();
	}

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

	public void setIdpdf(String zValue) throws Exception {
		pIdpdf.setValue(zValue);
	}

	public String getIdpdf() throws Exception {
		return pIdpdf.getValue();
	}

	public boolean isNullIdpdf() throws Exception {
		return pIdpdf.isNull();
	}

	public void setNullToIdpdf() throws Exception {
		pIdpdf.setNull();
	}

	public void setLinea(long zValue) throws Exception {
		pLinea.setValue(zValue);
	}

	public long getLinea() throws Exception {
		return pLinea.getValue();
	}

	public boolean isNullLinea() throws Exception {
		return pLinea.isNull();
	}

	public void setNullToLinea() throws Exception {
		pLinea.setNull();
	}

	public boolean isNullIdAerolinea() throws Exception {
		return pIdAerolinea.isNull();
	}

	public void setNullToIdAerolinea() throws Exception {
		pIdAerolinea.setNull();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizDQBDetalle() throws Exception {
	}

	public boolean isNullFecha() throws Exception {
		return pFecha.isNull();
	}

	public void setNullToFecha() throws Exception {
		pFecha.setNull();
	}

//Métodos para pGds
	public void setGds(String zValue) throws Exception {
		pGds.setValue(zValue);
	}

	public String getGds() throws Exception {
		return pGds.getValue();
	}

	public boolean isNullGds() throws Exception {
		return pGds.isNull();
	}

	public void setNullToGds() throws Exception {
		pGds.setNull();
	}

//Métodos para pTipoDoc
	public void setTipoDoc(String zValue) throws Exception {
		pTipoDoc.setValue(zValue);
	}

	public String getTipoDoc() throws Exception {
		return pTipoDoc.getValue();
	}

	public boolean isNullTipoDoc() throws Exception {
		return pTipoDoc.isNull();
	}

	public void setNullToTipoDoc() throws Exception {
		pTipoDoc.setNull();
	}

//Métodos para pBoleto
	public void setBoleto(String zValue) throws Exception {
		pBoleto.setValue(zValue);
	}

	public String getBoleto() throws Exception {
		return pBoleto.getValue();
	}

	public boolean isNullBoleto() throws Exception {
		return pBoleto.isNull();
	}

	public void setNullToBoleto() throws Exception {
		pBoleto.setNull();
	}

//Métodos para pPnr
	public void setPnr(String zValue) throws Exception {
		pPnr.setValue(zValue);
	}

	public String getPnr() throws Exception {
		return pPnr.getValue();
	}

	public boolean isNullPnr() throws Exception {
		return pPnr.isNull();
	}

	public void setNullToPnr() throws Exception {
		pPnr.setNull();
	}

//Métodos para pStatus
	public void setStatus(String zValue) throws Exception {
		pStatus.setValue(zValue);
	}

	public String getStatus() throws Exception {
		return pStatus.getValue();
	}

	public boolean isNullStatus() throws Exception {
		return pStatus.isNull();
	}

	public void setNullToStatus() throws Exception {
		pStatus.setNull();
	}
	
	public void setConsolicion(String zValue) throws Exception {
		pConsolicion.setValue(zValue);
	}

	public String getConsolicion() throws Exception {
		return pConsolicion.getValue();
	}
	public void setCodeCons(String zValue) throws Exception {
		pCodeCons.setValue(zValue);
	}

	public String getCodeCons() throws Exception {
		return pCodeCons.getValue();
	}
	public void setInterfaceid(Long zValue) throws Exception {
		pInterfaceId.setValue(zValue);
	}

	public Long getInterfaceId() throws Exception {
		return pInterfaceId.getValue();
	}
	public boolean isNullInterfaceId() throws Exception {
		return pInterfaceId.isNull();
	}

//Añadiendo las propiedades en createProperties
	@Override
	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("owner", pOwner);
		this.addItem("idPDF", pIdpdf);
		this.addItem("linea", pLinea);
		this.addItem("id_aerolinea", pIdAerolinea);
		this.addItem("fecha", pFecha);
		this.addItem("gds", pGds);
		this.addItem("tipo_doc", pTipoDoc);
		this.addItem("boleto", pBoleto);
		this.addItem("pnr", pPnr);
		this.addItem("status", pStatus);
		this.addItem("interface_id", pInterfaceId);
		this.addItem("code_cons", pCodeCons);
		this.addItem("consolidado", pConsolicion);
	}

//Añadiendo las propiedades en createFixedProperties
	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Company", true, true, 50);
		this.addFixedItem(KEY, "idPDF", "Idpdf", true, true, 300);
		this.addFixedItem(KEY, "linea", "Linea", true, true, 18);
		this.addFixedItem(FIELD, "owner", "Owner", true, true, 50);
		this.addFixedItem(FIELD, "id_aerolinea", "ID Aerolinea", true, false, 50);
		this.addFixedItem(FIELD, "fecha", "Fecha", true, false, 20);
		this.addFixedItem(FIELD, "gds", "GDS", true, false, 50);
		this.addFixedItem(FIELD, "tipo_doc", "Tipo Documento", true, false, 50);
		this.addFixedItem(FIELD, "boleto", "Boleto", true, false, 50);
		this.addFixedItem(FIELD, "pnr", "PNR", true, false, 15);
		this.addFixedItem(FIELD, "status", "Status", true, false, 50);
		this.addFixedItem(FIELD, "interface_id", "Boleto id", true, false, 18);
		this.addFixedItem(FIELD, "code_cons", "Resultado", true, false, 100);
		this.addFixedItem(FIELD, "consolidado", "Consolidado", true, false, 100);
	}

	public void attachRelationMap(JRelations rels) throws Exception {
		rels.setSourceWinsClass("pss.bsp.interfaces.bqd.detalle.GuiBQDDetalles");
		JRelation rel;

		rel = rels.addRelationForce(10, "restriccion usuario");
		rel.addFilter(" (BSP_DQB_DETALLE.company is null or BSP_DQB_DETALLE.company in (COMPANY_CUSTOMLIST)) ");

		// rels.hideField("company");
		rels.hideField("owner");
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "BSP_DQB_DETALLE";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Default read() method
	 */
	public boolean read(String zCompany, String zIdpdf, long zLinea) throws Exception {
		addFilter("company", zCompany);
		addFilter("idPDF", zIdpdf);
		addFilter("linea", zLinea);
		return read();
	}

	public boolean read(String zCompany, Date zDate) throws Exception {
		addFilter("company", zCompany);
		addFilter("fecha", zDate);

		return read();
	}

	@Override
	public void processInsert() throws Exception {
		if (pOwner.isNull())
			pOwner.setValue(BizUsuario.getUsr().GetUsuario());
		super.processInsert();
	}

	@Override
	public void processUpdate() throws Exception {
		if (pOwner.isNull())
			pOwner.setValue(BizUsuario.getUsr().GetUsuario());
		super.processUpdate();
	}
	transient BizPNRTicket  objBoleto;
	
	public BizPNRTicket getObjBoleto() throws Exception {
		if (this.objBoleto != null)
			return this.objBoleto;
		BizPNRTicket a = new BizPNRTicket();
		a.dontThrowException(true);
		long ref = this.pInterfaceId.getValue();
		
		if (!a.read(ref))
			return null;
		return (this.objBoleto = a);
	}
}
