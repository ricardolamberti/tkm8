package  pss.common.regions.divitions;

import pss.common.components.JSetupParameters;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizCiudadCP extends JRecord {

	public final static String ABM_CIUDAD ="ABM_CIUDAD";
	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JString pPais=new JString();
	private JString pProvincia=new JString();
	private JString pCiudad=new JString();
	private JString pCodPostal = new JString();
	private JString pCiudadDescr=new JString();

	public void setPais(String zVal) throws Exception {
		pPais.setValue(zVal);
	}

	public String getPais() throws Exception {
		return pPais.getValue();
	}

	public void setProvincia(String zVal) throws Exception {
		pProvincia.setValue(zVal);
	}

	public String getProvincia() throws Exception {
		return pProvincia.getValue();
	}

	public void setCiudad(String zVal) throws Exception {
		pCiudad.setValue(zVal);
	}

	public String getCiudad() throws Exception {
		return pCiudad.getValue();
	}

	public void setCodPostal(String zVal) throws Exception {
		pCodPostal.setValue(zVal);
	}

	public String getCodPostal() throws Exception {
		return pCodPostal.getValue();
	}

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//

	public BizCiudadCP() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("pais", pPais);
		addItem("provincia", pProvincia);
		addItem("ciudad_id", pCiudad);
		addItem("ciudad", pCiudadDescr);
		addItem("cod_postal", pCodPostal);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "pais", "Pais", true, true, 15);
		addFixedItem(KEY, "provincia", "División", true, true, 15);
		addFixedItem(KEY, "ciudad_id", "Id Sub División", true, true, 15);
		addFixedItem(KEY, "cod_postal", "Cod. Postal", true, false, 20);
		addFixedItem(FOREIGN, "ciudad", "Ciudad", true, false, 250,0,null,null,"REG_CIUDAD");
	}

	@Override
	public String GetTable() {
		return "REG_CIUDAD_CP";
	}

	

	public boolean readCP(String zCP) throws Exception {
		addFilter("cod_postal", zCP);
		return this.read();
	}

	public boolean readCP(String zCiudadId,String zCP) throws Exception {
		addFilter("ciudad_id", zCiudadId);
		addFilter("cod_postal", zCP);
		return this.read();
	}
	public boolean readCP(int zCiudadId,String zCP) throws Exception {
		addFilter("ciudad_id", zCiudadId);
		addFilter("cod_postal", zCP);
		return this.read();
	}
	public long countCP(int zCiudadId,String zCP) throws Exception {
		addFilter("ciudad_id", zCiudadId);
		addFilter("cod_postal", zCP);
		return this.selectCount();
	}	
	public long countCP(String zPais, String zProvincia, String zCiudadId) throws Exception {
		addFilter("pais", zPais);
		addFilter("provincia", zProvincia);
		addFilter("ciudad_id", zCiudadId);
		return this.selectCount();
	}
	public boolean Read(String zPais, String zProvincia, String zCiudadId) throws Exception {
		addFilter("pais", zPais);
		addFilter("provincia", zProvincia);
		addFilter("ciudad_id", zCiudadId);
		return this.read();
	}
	public boolean read() throws Exception {
		this.addJoin("REG_CIUDAD");
		this.addFixedFilter("where REG_CIUDAD_CP.pais=REG_CIUDAD.pais and REG_CIUDAD_CP.provincia=REG_CIUDAD.provincia and REG_CIUDAD_CP.ciudad_id=REG_CIUDAD.ciudad_id ");
		return super.read();
	}

	public boolean Read(String zPais, String zProvincia, int zCiudadId) throws Exception {
		addFilter("pais", zPais);
		addFilter("provincia", zProvincia);
		addFilter("ciudad_id", zCiudadId);
		return this.read();
	}
	BizCiudad objCiudad;
	public BizCiudad getObjCiudad() throws Exception {
		if (pCiudad.isNull()) return null;
		if (objCiudad!=null) return objCiudad;
		BizCiudad c = new BizCiudad();
		c.dontThrowException(true);
		if (!c.Read(getPais(), getProvincia(), getCiudad())) return null;
		return objCiudad=c;
		
	}
	public String getDescripcionCiudad() throws Exception {
		if (getObjCiudad()==null) return "";
		return getObjCiudad().getDescripcion();
	}

	@Override
	public void setupConfig(JSetupParameters zParams) throws Exception {
		zParams.setTruncateData(zParams.isLevelCountry());
		zParams.setExportData(zParams.isLevelCountry());
	}

}
