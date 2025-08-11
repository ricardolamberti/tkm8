package pss.common.regions.divitions;

import pss.core.services.records.JRecords;

public class BizPaises extends JRecords<BizPais> {

	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizPaises() throws Exception {
	}

	@Override
	public Class<BizPais> getBasedClass() {
		return BizPais.class;
	}

	@Override
	public String GetTable() {
		return "REG_PAIS_CONFIG";
	}

	protected boolean loadForeignFields() {
		return true;
	}

	@Override
	public boolean readAll() throws Exception {
//		this.addFuntion("*");
		this.clearFields();
		this.addFuntion("*");
		this.addJoin("reg_pais");
		this.addFixedFilter("reg_pais.pais=reg_pais_config.pais");
		this.addOrderBy("reg_pais.descripcion");
		return super.readAll();
	}


}
