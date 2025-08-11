package  pss.common.regions.divitions;

import pss.core.services.records.JRecords;

public class BizCiudadCPes  extends JRecords<BizCiudadCP>  {


	public BizCiudadCPes() {
	}

	@Override
	public Class<BizCiudadCP> getBasedClass() { return BizCiudadCP.class;}
	
	public boolean readAll() throws Exception {
		this.setLoadForeignFields(true);
		this.clearFields();
		this.addField("pais");
		this.addField("provincia");
		this.addField("ciudad_id");
		this.addField("cod_postal");
		this.addField("REG_CIUDAD","ciudad","");
		this.addJoin("REG_CIUDAD");
		this.addFixedFilter("where REG_CIUDAD_CP.pais=REG_CIUDAD.pais and REG_CIUDAD_CP.provincia=REG_CIUDAD.provincia and REG_CIUDAD_CP.ciudad_id=REG_CIUDAD.ciudad_id ");
		return super.readAll();
	}

	


}