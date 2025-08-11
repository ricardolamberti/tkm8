package  pss.common.regions.nodes;

import pss.common.components.JSetupParameters;
import pss.common.dbManagement.JDBReplicator;
import pss.core.data.interfaces.connections.JBDato;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;

public class BizNodoDatabase extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JString pCompany=new JString();
	private JString pNodo=new JString();
	private JString pHost=new JString();
	private JString pSeccionIni=new JString();
	private JInteger pPort=new JInteger();
	private JString pHostDb=new JString();
	private JBoolean pIsMaster=new JBoolean();
	private JBoolean pIsConnectedToThisDatabase=new JBoolean() {

		@Override
		public void preset() throws Exception {
			ObtenerConnectedTo();
		}
	};
	private JBoolean pIsBaseLocal=new JBoolean() {

		@Override
		public void preset() throws Exception {
			ObtenerIsBaseLocal();
		}
	};

	// GETTERs
	public String GetNodo() throws Exception {
		return pNodo.getValue();
	}

	public String GetHost() throws Exception {
		return pHost.getValue();
	}

	public String GetSeccionIni() throws Exception {
		return pSeccionIni.getValue();
	}

	public int GetPort() throws Exception {
		return pPort.getValue();
	}

	public String GetHostDb() throws Exception {
		return pHostDb.getValue();
	}

	public boolean GetIsMaster() throws Exception {
		return pIsMaster.getValue();
	}

	public boolean GetIsConnectedToThisDatabase() throws Exception {
		return pIsConnectedToThisDatabase.getValue();
	}

	public boolean GetIsBaseLocal() throws Exception {
		return pIsBaseLocal.getValue();
	}

	// SETTERs
	public void SetNodo(String zVal) throws Exception {
		pNodo.setValue(zVal);
	}

	public void SetHost(String zVal) throws Exception {
		pHost.setValue(zVal);
	}

	public void SetSeccionIni(String zVal) throws Exception {
		pSeccionIni.setValue(zVal);
	}

	public void SetPort(int zVal) throws Exception {
		pPort.setValue(zVal);
	}

	public void SetHostDb(String zVal) throws Exception {
		pHostDb.setValue(zVal);
	}

	public void SetIsMaster(boolean zVal) throws Exception {
		pIsMaster.setValue(zVal);
	}

	public void SetIsConnectedToThisDatabase(boolean zVal) throws Exception {
		pIsConnectedToThisDatabase.setValue(zVal);
	}

	public void SetIsBaseLocal(boolean zVal) throws Exception {
		pIsBaseLocal.setValue(zVal);
	}

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizNodoDatabase() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("company", pCompany);
		addItem("nodo", pNodo);
		addItem("host", pHost);
		addItem("seccion_ini", pSeccionIni);
		addItem("port", pPort);
		addItem("host_db", pHostDb);
		addItem("is_master", pIsMaster);
		addItem("conectado", pIsConnectedToThisDatabase);
		addItem("is_base_local", pIsBaseLocal);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "company", "Empresa", true, true, 50);
		addFixedItem(KEY, "nodo", "Sucursal", true, true, 50);
		addFixedItem(KEY, "host", "Host Msg", true, true, 50);
		addFixedItem(FIELD, "seccion_ini", "Seccion Ini", true, true, 30);
		addFixedItem(FIELD, "port", "Port Msg", true, true, 18);
		addFixedItem(FIELD, "host_db", "Host DB", true, true, 50);
		addFixedItem(FIELD, "is_master", "Es Master", true, false, 1);
		addFixedItem(VIRTUAL, "conectado", "Conectado", true, false, 1);
		addFixedItem(VIRTUAL, "is_base_local", "Es base local", true, false, 1);
	}

	@Override
	public String GetTable() {
		return "NOD_DATABASE";
	}

	public boolean Read(String zCompany, String zNodo, String zHost) throws Exception {
		this.addFilter("company", zCompany);
		this.addFilter("nodo", zNodo);
		this.addFilter("host", zHost);
		return this.Read();
	}

	@Override
	public void validateConstraints() throws Exception {
		this.validateOneMaster();
	}

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * Valida que se pueda configurar un máximo de 1(un) nodo master
	 * 
	 * @author CJG
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	public void validateOneMaster() throws Exception {
		JRecords<BizNodoDatabase> oNodosDB=null;
		try {
			oNodosDB=new JRecords<BizNodoDatabase>(BizNodoDatabase.class);
			oNodosDB.addFilter("nodo", this.GetNodo());
			oNodosDB.addFilter("is_master", "S");
			oNodosDB.readAll();
			oNodosDB.toStatic();
			long cant=oNodosDB.selectCount();
			if (cant==0) return; // ok, no hay master elegido
			oNodosDB.firstRecord();
			oNodosDB.nextRecord();
			BizNodoDatabase oNodoDB=oNodosDB.getRecord();
			if (oNodoDB.GetHost().equalsIgnoreCase(this.GetHost())&&oNodoDB.GetNodo().equalsIgnoreCase(this.GetNodo())) return; // él mismo
			if (!this.pIsMaster.getValue()) return;
			JExcepcion.SendError("Ya está indicado quien es master para el nodo^ "+this.GetNodo());
		} finally {
			if (oNodosDB==null) return;
			oNodosDB.closeRecord();
		}
	}

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * Setea la variable pIsLocal con S si el host = hostdb, o N caso contrario
	 * 
	 * @author CJG
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	private void ObtenerConnectedTo() throws Exception {
		JBDato oDatoDef=JBDatos.GetBases().getPrivateCurrentDatabase();
		SetIsConnectedToThisDatabase(pHostDb.getValue().equalsIgnoreCase(oDatoDef.getHostName()));
	}

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * Setea la variable pIsLocal con S si el host = hostdb, o N caso contrario
	 * 
	 * @author CJG
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	private void ObtenerIsBaseLocal() throws Exception {
		this.SetIsBaseLocal(pHostDb.getValue().equalsIgnoreCase(JTools.GetHostName()));
	}

	public boolean isConnectedToThisDatabase() throws Exception {
		return pIsConnectedToThisDatabase.getValue();
	}

	public boolean isMaster() throws Exception {
		return pIsMaster.getValue();
	}

	public boolean isReplicationInstalled() throws Exception {
		return JDBReplicator.getInstance().isReplicationInstalled();
	}

	public void installReplication() throws Exception {
		JDBReplicator.getInstance().installReplication();
	}

	public void suscribeToReplicatiion() throws Exception {
		JDBReplicator.getInstance().addSubscriber(pHostDb.getValue());
		JDBReplicator.getInstance().addSubscription(pHostDb.getValue());
	}

	@Override
	public void setupConfig(JSetupParameters zParams) throws Exception {
		zParams.setExportData(zParams.isLevelNode());
	}

}
