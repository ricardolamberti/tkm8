package  pss.common.regions.nodes;

import pss.common.components.JSetupParameters;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizRegion extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	JString pCompany=new JString();
	JLong pRegion=new JLong();
	JString pDescripcion=new JString();

	public JLong getRegion() {
		return pRegion;
	}

	public JString getDescription() {
		return pDescripcion;
	}

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizRegion() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("company", pCompany);
		addItem("region", pRegion);
		addItem("descripcion", pDescripcion);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "company", "Empresa", true, true, 15);
		addFixedItem(KEY, "region", "Región", true, true, 18);
		addFixedItem(FIELD, "descripcion", "Descripción", true, true, 50);
	}

	@Override
	public String GetTable() {
		return "nod_region";
	}

	@Override
	public void setupConfig(JSetupParameters zParams) throws Exception {
		zParams.setExportData(zParams.isLevelCountry());
	}

	public boolean Read(long zRegion) throws Exception {
		addFilter("region", zRegion);
		return this.Read();
	}

	@Override
	public void processInsert() throws Exception {
		if (pRegion.isNull()) {
			BizRegion max=new BizRegion();
			max.addFilter("company", pCompany.getValue());
			long region=this.SelectMaxLong("region");
			pRegion.setValue(region+1);
		}
		this.insertRecord();
	}

	@Override
	public void processDelete() throws Exception {
		/*
		 * JBDs oRegionRegiones = new JBDs(BizRegionRegion.class); oRegionRegiones.SetFiltros("region1", this.pRegion.GetValor()); oRegionRegiones.ReadAll(); oRegionRegiones.DeleteAll();
		 */

		JRecords<BizRegionNodo> oRegionNodos=new JRecords<BizRegionNodo>(BizRegionNodo.class);
		oRegionNodos.addFilter("region", this.pRegion.getValue());
		oRegionNodos.readAll();
		oRegionNodos.processDeleteAll();

		this.deleteRecord();
	}

	/*
	 * 
	 * This is not being used by the moment...
	 * 
	 * public void EnviarObjeto(JBD zBD, String zMetodo, String zSAF) throws Exception {
	 *  // Regiones JBDs oRegiones = new JBDs(BizRegionRegion.class); oRegiones.SetFiltros("region1", this.pRegion.GetValor()); oRegiones.ReadAll();
	 * 
	 * while ( oRegiones.NextRecord() ){ BizRegionRegion oReg = (BizRegionRegion) oRegiones.GetRecord(); BizRegion oRegion = new BizRegion(); oRegion.Read(oReg.pRegion2.GetValor()); oRegion.EnviarObjeto(zBD, zMetodo, "N"); }
	 *  // Nodos JBDs oRegionNodos = new JBDs(BizRegionNodo.class); oRegionNodos.SetFiltros("region", this.pRegion.GetValor()); oRegionNodos.ReadAll(); while ( oRegionNodos.NextRecord() ) { BizRegionNodo oReg = (BizRegionNodo) oRegionNodos.GetRecord(); BizNodo oNodo = new BizNodo(); oNodo.Read(oReg.pNodo.GetValor()); BizNodoDatabase oNodoDBMaster = oNodo.getNodoDatabaseMaster(); zBD.EnviarObjeto(oNodoDBMaster.GetHost(), oNodoDBMaster.GetPort(), zMetodo, "N"); }
	 *  }
	 */

}
