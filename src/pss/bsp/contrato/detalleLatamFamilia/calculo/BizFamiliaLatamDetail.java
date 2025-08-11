package pss.bsp.contrato.detalleLatamFamilia.calculo;

import pss.bsp.contrato.BizContrato;
import pss.bsp.contrato.detalleLatamFamilia.BizDetalleDataminingTriFamilia;
import pss.common.customList.config.relation.JRelations;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizFamiliaLatamDetail extends JRecord {

	private JLong pId = new JLong();
	protected JString pCompany = new JString();
	protected JLong pIdDetalle = new JLong();
	protected JLong pIdContrato = new JLong();
	protected JString pFamilia = new JString();
	protected JFloat pPeso = new JFloat();
	protected JFloat pProporcion = new JFloat();
	protected JFloat pPonderado = new JFloat() {
		public void preset() throws Exception {
			pPonderado.setPrecision(2);
			pPonderado.setValue((pPeso.getValue()*pProporcion.getValue()/100));
		};
	};

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setId(long zValue) throws Exception {
		pId.setValue(zValue);
	}

	public long getId() throws Exception {
		return pId.getValue();
	}

	public boolean isNullId() throws Exception {
		return pId.isNull();
	}

	public void setNullToId() throws Exception {
		pId.setNull();
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
	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public void setFamilia(String zValue) throws Exception {
		pFamilia.setValue(zValue);
	}

	public String getFamilia() throws Exception {
		return pFamilia.getValue();
	}

	public void setPeso(double zValue) throws Exception {
		pPeso.setValue(zValue);
	}

	public double getPeso() throws Exception {
		return pPeso.getValue();
	}
	public void setProporcion(double zValue) throws Exception {
		pProporcion.setValue(zValue);
	}

	public double getProporcion() throws Exception {
		return pProporcion.getValue();
	}	
	public void setPonderado(double zValue) throws Exception {
		pPonderado.setValue(zValue);
	}

	public double getPonderado() throws Exception {
		return pPonderado.getValue();
	}
/**
	 * Constructor de la Clase
	 */
	public BizFamiliaLatamDetail() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("id_contrato", pIdContrato);
		this.addItem("id_detalle", pIdDetalle);
		this.addItem("id", pId);
		this.addItem("familia", pFamilia);
		this.addItem("peso", pPeso);
		this.addItem("proporcion", pProporcion);
		this.addItem("ponderado", pPonderado);

	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "id", "id", false, false, 64);
		this.addFixedItem(KEY, "company", "Company", true, true, 15);
		this.addFixedItem(KEY, "id_contrato", "Id Contrato", true, true, 64);
		this.addFixedItem(KEY, "id_detalle", "Id Detalle", true, false, 64);
		this.addFixedItem(FIELD, "familia", "Familia Tarifaria", true, false, 100);
		this.addFixedItem(FIELD, "peso", "Peso Familia", true, false, 18,2);
		this.addFixedItem(FIELD, "proporcion", "Proporcion Familia", true, false, 18,2);
		this.addFixedItem(FIELD, "ponderado", "Ponderado Familia", true, false, 18,2);

	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "BSP_LATAM_DETAIL";
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void attachRelationMap(JRelations rels) throws Exception {
		rels.setSourceWinsClass("pss.bsp.contrato.detalleLatamFamilia.calculo.GuiFamiliaLatamDetails");
		super.attachRelationMap(rels);
	}

	public boolean read(long id) throws Exception {
		addFilter("id", id);
		return read();
	}

	public boolean read(String company, long idContrato, long idDetalle, long id) throws Exception {
		addFilter("company", company);
		addFilter("id_detalle", idDetalle);
		addFilter("id_contrato", idContrato);
		addFilter("id", id);
		return read();
	}

	BizContrato objContrato;

	public BizContrato getObjContrato() throws Exception {
		BizContrato p = new BizContrato();
		p.read(pIdContrato.getValue());
		return objContrato = p;
	}

	BizDetalleDataminingTriFamilia objDetalle;

	public BizDetalleDataminingTriFamilia getObjDetalle() throws Exception {
		BizDetalleDataminingTriFamilia p = new BizDetalleDataminingTriFamilia();
		p.read(pIdDetalle.getValue());
		return objDetalle = p;
	}
}
