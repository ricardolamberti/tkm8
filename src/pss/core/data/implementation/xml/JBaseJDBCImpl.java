package pss.core.data.implementation.xml;

import pss.core.data.connectionPool.JDBConnectionPools;
import pss.core.data.interfaces.connections.JBDato;
import pss.core.data.interfaces.connections.JBaseJDBC;

/**
 * @author PSS
 */
public class JBaseJDBCImpl extends JBaseJDBC {

	private static final String DESCRIPTION = "XML";
	private static final String METADATA_COLUMN_IMPL = "pss.common.dbManagement.synchro.JDBColumnaXml";
	private static final String METADATA_COLUMNS_IMPL = "pss.common.dbManagement.synchro.JDBColumnasXml";
	private static final String METADATA_INDEX_IMPL = "pss.common.dbManagement.synchro.JDBIndiceXml";
	private static final String METADATA_INDEXES_IMPL = "pss.common.dbManagement.synchro.JDBIndicesXml";
	private static final String METADATA_TABLE_IMPL = "pss.common.dbManagement.synchro.JDBTablaXml";
	private static final String METADATA_TABLES_IMPL = "pss.common.dbManagement.synchro.JDBTablasXml";
	private static final String SETUP_COMPARISON_IMPL = "pss.common.dbManagement.synchro.JDBTableComparisonXml";
	private static final String CONNECTION_IMPL = "pss.core.data.implementation.xml.JPssConnectionImpl";
	private static final String ROW_IMPL = "pss.core.data.implementation.xml.JRegJDBCImpl";

	public JBaseJDBCImpl() throws Exception {
		super();
	}

	@Override
	public boolean isSQLServer() {
		return false;
	}
	
	@Override
	public boolean isSQL92() {
		return true;
	}
	@Override
	public String getDatabaseDescription() {
		return DESCRIPTION;
	}

	@Override
	public String getMetadataColumnImpl() {
		return METADATA_COLUMN_IMPL;
	}

	@Override
	public String getMetadataColumnsImpl() {
		return METADATA_COLUMNS_IMPL;
	}

	@Override
	public String getMetadataIndexImpl() {
		return METADATA_INDEX_IMPL;
	}

	@Override
	public String getMetadataIndexesImpl() {
		return METADATA_INDEXES_IMPL;
	}

	@Override
	public String getMetadataTableImpl() {
		return METADATA_TABLE_IMPL;
	}

	@Override
	public String getMetadataTablesImpl() {
		return METADATA_TABLES_IMPL;
	}

	@Override
	public String getDatabaseName() {
		int iIndex = this.sConnectionUrl.lastIndexOf(":");
		return this.sConnectionUrl.substring(iIndex + 1);
	}

	@Override
	public String getSetupTableComparisonImpl() {
		return SETUP_COMPARISON_IMPL;
	}

	@Override
	public String getConnectionInterface() {
		return CONNECTION_IMPL;
	}

	@Override
	public String getRowInterface() {
		return ROW_IMPL;
	}

	@Override
	public String getAdminUserFileName() {
		return "auth.dat";
	}

	@Override
	public String getCommonUserFileName() {
		return "auth.dat";
	}

	@Override
	public String getLowerCaseInstruction() {
		return "lower";
	}

	/**
	 * Creates the master database
	 */
	public void setParamsMaster(JBDato zBaseSource) throws Exception {
		this.SetName(zBaseSource.GetName() + "_CONFIG");
		this.SetInterface(zBaseSource.GetInterface());
		this.SetClassInterface(zBaseSource.GetClassInterface());
		
		this.sUsername = null;
		this.sPassword = null;
		this.sUserService = zBaseSource.GetUserService();
		this.sAuth = zBaseSource.getAuthenticationMode();
		this.sDriverName = zBaseSource.GetDriverName();
		this.sDriverInterface = zBaseSource.getDriverInterface();
		this.sConnectionInterface = zBaseSource.getConnectionInterface();
		this.sConnectionUrl = zBaseSource.getConnectionURL();

		oConnectionPools = JDBConnectionPools.getInstance();
		this.oDriverImpl = this.createDriverImpl(this.sConnectionUrl, this.sUsername, this.sPassword, this.sDriverName, this.sConnectionInterface, this.sDriverInterface, this.lLockTimeoutSeconds);
		// this.oConnectionPools.addPool(this.sBaseDatos, iMaxConnections, this.oDriverImpl);
	}

	/**
	 * <p>
	 * Setear en cada variable que resuelve un tipo de objeto de la base de datos la clase que
	 * implementa en forma fisica o virtual el objeto de la base de datos correspondiente
	 * </p>
	 */
	protected void initializeVirtualClassesNames() {
		systemDBFieldImpl = new String("pss.common.dbManagement.synchro.xml.JXmlSystemDBField");
		systemDBFieldsImpl = new String("pss.common.dbManagement.synchro.xml.JXmlSystemDBFields");
		systemDBIndexImpl = new String("pss.common.dbManagement.synchro.xml.JXmlSystemDBIndex");
		systemDBIndexesImpl = new String("pss.common.dbManagement.synchro.xml.JXmlSystemDBIndexes");
		systemDBTableImpl = new String("pss.common.dbManagement.synchro.xml.JXmlSystemDBTable");
		systemDBTablesImpl = new String("pss.common.dbManagement.synchro.xml.JXmlSystemDBTables");
		virtualDBFieldImpl = new String("pss.common.dbManagement.synchro.xml.JXmlVirtualDBField");
		virtualDBFieldsImpl = new String("pss.common.dbManagement.synchro.xml.JXmlVirtualDBFields");
		virtualDBIndexImpl = new String("pss.common.dbManagement.synchro.xml.JXmlVirtualDBIndex");
		virtualDBIndexesImpl = new String("pss.common.dbManagement.synchro.xml.JXmlVirtualDBIndexes");
		virtualDBTableImpl = new String("pss.common.dbManagement.synchro.xml.JXmlVirtualDBTable");
	}

}
