package pss.bsp.contrato.detalle.mercado;

import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizDetalleMercado extends JRecord  {

	private JString pCompany=new JString();
	private JLong pIdContrato=new JLong();
	private JLong pIdDetalle=new JLong();
	private JLong pLinea=new JLong();
	private JString pIdAerolinea=new JString();
	private JString pOrigen=new JString();
	private JString pDestino=new JString();
	private JString pMarketingId=new JString();
	private JFloat pFMS=new JFloat();
	private JFloat pFMSEdited=new JFloat();

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
	public void setIdDetalle(long zValue) throws Exception {
		pIdDetalle.setValue(zValue);
	}

	public long getIdDetalle() throws Exception {
		return pIdDetalle.getValue();
	}



	public void setIdContrato(long zValue) throws Exception {
		pIdContrato.setValue(zValue);
	}

	public long getIdContrato() throws Exception {
		return pIdContrato.getValue();
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

	public void setDestino(String zValue) throws Exception {
		pDestino.setValue(zValue);
	}

	public String getDestino() throws Exception {
		return pDestino.getValue();
	}

	public boolean isNullDestino() throws Exception {
		return pDestino.isNull();
	}

	public void setNullToDestino() throws Exception {
		pDestino.setNull();
	}

	public void setOrigen(String zValue) throws Exception {
		pOrigen.setValue(zValue);
	}

	public String getOrigen() throws Exception {
		return pOrigen.getValue();
	}

	public boolean isNullOrigen() throws Exception {
		return pOrigen.isNull();
	}

	public void setNullToOrigen() throws Exception {
		pOrigen.setNull();
	}

	public void setIdAerolinea(String zValue) throws Exception {
		pIdAerolinea.setValue(zValue);
	}

	public long getIdAerolinea() throws Exception {
		return Long.parseLong(pIdAerolinea.getValue());
	}

	public boolean isNullIdAerolinea() throws Exception {
		return pIdAerolinea.isNull();
	}

	public void setNullToIdAerolinea() throws Exception {
		pIdAerolinea.setNull();
	}

	public void setMarketingID(String zValue) throws Exception {
		pMarketingId.setValue(zValue);
	}

	public String getMarketingID() throws Exception {
		return pMarketingId.getValue();
	}

	public boolean isNullMarketingID() throws Exception {
		return pMarketingId.isNull();
	}

	public void setNullToMarketingID() throws Exception {
		pMarketingId.setNull();
	}
	
	public void setFMS(double zValue) throws Exception {
		pFMS.setValue(zValue);
	}

	public double getFMS() throws Exception {
		return pFMS.getValue();
	}

	public void setFMSEdited(double zValue) throws Exception {
		pFMSEdited.setValue(zValue);
	}

	public double getFMSEdited() throws Exception {
		return pFMSEdited.getValue();
	}
	public boolean isNullFMS() throws Exception {
		return pFMS.isNull();
	}

	public void setNullToFMS() throws Exception {
		pFMS.setNull();
	}



	/**
	 * Constructor de la Clase
	 */
	public BizDetalleMercado() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("id_contrato", pIdContrato);
		this.addItem("id_detalle", pIdDetalle);
		this.addItem("linea", pLinea);
		this.addItem("origen", pOrigen);
		this.addItem("destino", pDestino);
		this.addItem("marketing_id", pMarketingId);
		this.addItem("id_aerolinea", pIdAerolinea);
		this.addItem("fms", pFMS);
		this.addItem("fms_edited", pFMSEdited);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Company", true, true, 50);
		this.addFixedItem(KEY, "id_contrato", "Id contrato", true, true, 18);
		this.addFixedItem(KEY, "id_detalle", "Id detalle", true, true, 18);
		this.addFixedItem(KEY, "linea", "Linea", false, false, 18);
		this.addFixedItem(FIELD, "origen", "Origen", true, false, 50);
		this.addFixedItem(FIELD, "destino", "destino", true, false, 50);
		this.addFixedItem(FIELD, "marketing_id", "Marketing ID", true, false, 50);
		this.addFixedItem(FIELD, "id_aerolinea", "ID Aerolinea", true, false, 50);
		this.addFixedItem(FIELD, "fms", "FMS", true, false, 18, 2);
		this.addFixedItem(FIELD, "fms_edited", "FMS editado", true, false, 18, 2);
	}

	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.bsp.contrato.detalle.mercado.GuiDetalleMercados");
  	JRelation rel; 
  	
  	rel = rels.addRelationForce(10, "restriccion usuario");
   	rel.addFilter(" (BSP_DETALLE_MERCADO.company in (COMPANY_CUSTOMLIST)) ");
  
  }


	
	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "BSP_DETALLE_MERCADO";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Default read() method
	 */
	public boolean read(String zCompany,  long zIdcontrato, long zIdDetalle, long zLinea) throws Exception {
		addFilter("company", zCompany);
		addFilter("id_contrato", zIdcontrato);
		addFilter("id_detalle", zIdDetalle);
		addFilter("linea", zLinea);
		return read();
	}

	public boolean read(String zCompany,  String carrier, String mercado) throws Exception {
		addFilter("company", zCompany);
		addFilter("id_aerolinea", carrier);
		addFilter("marketing_id", mercado);
		return read();
	}

	
	@Override
	public void processInsert() throws Exception {
		super.processInsert();
	}

	@Override
	public void processUpdate() throws Exception {
		super.processUpdate();
	}

	

}
