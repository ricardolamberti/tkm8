package  pss.common.regions.nodes;

import pss.common.components.JSetupParameters;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizNodoPC extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	JString pCompany=new JString();
	JString pNodo=new JString();
	JString pPssPath=new JString();
	JString pHost=new JString();
	JBoolean pIsMaster=new JBoolean();

	public String GetPssPath() throws Exception {
		return pPssPath.getValue();
	}

	@Override
	public String GetTable() {
		return "nod_nodo_pc";
	}

	public void setNodo(String zValue) {
		pNodo.setValue(zValue);
	}

	public void setHost(String zValue) {
		pHost.setValue(zValue);
	}

	public void setPath(String zValue) {
		pPssPath.setValue(zValue);
	}

	public boolean getIsMaster() throws Exception {
		return pIsMaster.getValue();
	}

	public String getHost() throws Exception {
		return pHost.getValue();
	}

	@Override
	public void setupConfig(JSetupParameters zParams) throws Exception {
		zParams.setExportData(zParams.isLevelNode());
	}

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizNodoPC() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("company", pCompany);
		addItem("nodo", pNodo);
		addItem("pss_path", pPssPath);
		addItem("host", pHost);
		addItem("is_master", pIsMaster);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "company", "Empresa", true, true, 15);
		addFixedItem(KEY, "nodo", "Sucursal", true, true, 15);
		addFixedItem(KEY, "host", "Host", true, true, 20);
		addFixedItem(FIELD, "pss_path", "Pss Path", true, true, 50);
		addFixedItem(FIELD, "is_master", "Es Nodo Maestro", true, true, 1);
	}

	public void execProcessNodoMaster() throws Exception {
		JExec oExec=new JExec(this, "processNodoMaster") {

			@Override
			public void Do() throws Exception {
				processNodoMaster();
			}
		};
		oExec.execute();

	}

	public void processNodoMaster() throws Exception {
		JRecords<BizNodoPC> oNodosPCs=new JRecords<BizNodoPC>(BizNodoPC.class);
		oNodosPCs.addFilter("nodo", this.pNodo);

		oNodosPCs.readAll();
		oNodosPCs.firstRecord();
		while (oNodosPCs.nextRecord()) {
			BizNodoPC oNodo=oNodosPCs.getRecord();
			if (oNodo.pIsMaster.getValue()) {
				oNodo.pIsMaster.setValue(false);
				oNodo.update();
			}
		}

		pIsMaster.setValue(true);
		this.update();
	}
}
