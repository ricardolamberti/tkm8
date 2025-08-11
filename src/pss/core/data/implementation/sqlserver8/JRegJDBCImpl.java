package pss.core.data.implementation.sqlserver8;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;

import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.connections.JBaseJDBC;
import pss.core.data.interfaces.sentences.JRegJDBC;
import pss.core.data.interfaces.structure.RField;
import pss.core.data.interfaces.structure.RJoins;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JList;

public class JRegJDBCImpl extends JRegJDBC {

	@Override
	public void first() throws Exception {
	}

	@Override
	public String getVirtualBDsField(String zFieldToEvaluate, JRecords<BizVirtual> vBDs, String zElse) throws Exception {
		StringBuffer sConditionalString=new StringBuffer(128);
		sConditionalString.append(" case ");
		sConditionalString.append(zFieldToEvaluate);
		vBDs.firstRecord();
		while (vBDs.nextRecord()) {
			BizVirtual oVirtual=vBDs.getRecord();
			sConditionalString.append(" when '");
			sConditionalString.append(oVirtual.getValor());
			sConditionalString.append("' then '");
			sConditionalString.append(oVirtual.getDescrip());
			sConditionalString.append("' ");
		}
		sConditionalString.append(" else ");
		sConditionalString.append(zElse);
		sConditionalString.append(" end ");
		return sConditionalString.toString();
	}

	@Override
	protected String getIgnoreLocksHintAux() throws Exception {
		return " with (READPAST) ";
	}

	@Override
	protected String getDirtyReadHintAux() throws Exception {
		return " with (READUNCOMMITTED) ";
	}

  public String getTop() {
    if ( lTopRows < 0 ) return "";
    return " top " + lTopRows + " ";
  }

	
	@Override
	protected String getConditionalFieldAux(String zFieldToEvaluate, JList<String> vCondition, String zTrue, String zFalse) throws Exception {
		StringBuffer sConditionalString=new StringBuffer(128);
		String sFirstCondition=vCondition.getIterator().nextElement();

		if (sFirstCondition.equalsIgnoreCase("null")) {
			sConditionalString.append(" case when ");
			sConditionalString.append(zFieldToEvaluate);
			sConditionalString.append(" is null ");
			sConditionalString.append(" then ");
			sConditionalString.append(zTrue);
			sConditionalString.append(" else ");
			sConditionalString.append(zFalse);
			sConditionalString.append(" end ");
		} else {
			Iterator<String> oIter=vCondition.iterator();
			sConditionalString.append(" case ");
			sConditionalString.append(zFieldToEvaluate);
			while (oIter.hasNext()) {
				String zCondition=oIter.next();
				sConditionalString.append(" when ");
				sConditionalString.append(zCondition);
				sConditionalString.append(" then ");
				sConditionalString.append(zTrue);
			}
			sConditionalString.append(" else ");
			sConditionalString.append(zFalse);
			sConditionalString.append(" end ");
		}
		return sConditionalString.toString();
	}

	public String getLeft(String zFieldToCut, String zLength) throws Exception {
		return " left( "+zFieldToCut+","+zLength+") ";
	}

	@Override
	public String getConcatenateOperator() throws Exception {
		return " + ";
	}

	@Override
	public String toNumber(String zFieldname) {
		return "cast("+zFieldname+" as numeric)";
	}
	
	public String dateDiff(String field, String fecha) throws Exception {
		return "datediff(dd, "+field+", '"+fecha+"')";
	}

	/**
	 * Constructor
	 */
	public JRegJDBCImpl() {
		super();
	}

	@Override
	protected Statement getQueryOpenStatement(JBaseJDBC oDatabaseImpl) throws Exception {
		return oDatabaseImpl.GetConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	}

	@Override
	public String ArmarUpdateCatalog() throws Exception {
		String out="";
		if (this.oDato.hasCatalog()) return null;
		out="sp_fulltext_catalog '"+this.oDato.getCatalog().getName()+"', 'start_incremental'";
		return out;
	}

	@Override
	public String ArmarInsert() throws Exception {
		StringBuffer sBuffer=new StringBuffer(512);
		String sValor;
		StringBuffer sCampos=new StringBuffer(256);
		StringBuffer sValores=new StringBuffer(256);
		boolean bFirst=true;

		sBuffer.append(INSERT_INTO);
		sBuffer.append(oDato.getStructure().getTable());
		sBuffer.append(SPACE);

		if (!oDato.getStructure().hasFields()) {
			JExcepcion.SendError("Debe especificar algun campo");
		}
		JList<RField> oEnum=oDato.getFields();
		Iterator<RField> oIt=oEnum.iterator();
		while (oIt.hasNext()) {
			RField oCampo=oIt.next();
			if (oCampo.ifAutonumerico()) continue;
			if (oCampo.ifOnlySelects()) continue;
			if (bFirst) {
				bFirst=false;
			} else {
				sCampos.append(COMMA);
				sValores.append(COMMA);
			}
			sCampos.append(oCampo.GetCampo());
			sValor=oCampo.GetValor();
			sValores.append(ArmarDato(oCampo.GetTabla(), oCampo.GetTipo(), sValor));
		}
		sBuffer.append("(");
		sBuffer.append(sCampos);
		sBuffer.append(") values (");
		sBuffer.append(sValores);
		sBuffer.append(")");
		return sBuffer.toString();
	}


	@Override
	public String ArmarUpdate() throws Exception {
		StringBuffer sBuffer=new StringBuffer(512);
		String sTipo;
		boolean bFirst=true;

		sBuffer.append(UPDATE);
		sBuffer.append(oDato.getStructure().getTable());
		sBuffer.append(SET);
		if (!oDato.getStructure().hasFields()) {
			return null;
		}
		JList<RField> aCampos=oDato.getFields();
		Iterator<RField> oIt=aCampos.iterator();
		while (oIt.hasNext()) {
			RField oCampo=oIt.next();
			if (oCampo.ifAutonumerico()) continue;
			if (oCampo.ifOnlySelects()) continue;
			if (bFirst) {
				bFirst=false;
			} else {
				sBuffer.append(COMMA);
			}
			sBuffer.append(oCampo.GetCampo());
			sBuffer.append(EQUAL);
			sTipo=oCampo.GetTipo();
			sBuffer.append(ArmarDato(oDato.getStructure().getTable(), sTipo, oCampo.GetValor()));
		}
		sBuffer.append(Where());
		return sBuffer.toString();
	}

	@Override
	public String buildFrom() throws Exception {
		StringBuffer sBuffer=new StringBuffer(128);

		sBuffer.append(" from ");
		sBuffer.append(oDato.getStructure().getTableForFrom() + this.getHint());

		// Tablas vinculadas con inner joins
		if (oDato.getStructure().hasJoins()) {
			Iterator<RJoins> oIt=oDato.getJoins().iterator();
			while (oIt.hasNext()) { // primero los inner joins
				RJoins oJoin=oIt.next();
				if (!oJoin.isInnerJoin()) continue;
				sBuffer.append(" "+oJoin.GetTablaJoin()+ " " +(oJoin.GetAliasJoin()!=null?oJoin.GetAliasJoin()+" ":"" ));
			}
		}

		// Tablas vinculadas con joins
		if (oDato.getStructure().hasJoins()) {
			Iterator<RJoins> oIt=oDato.getJoins().iterator();
			oIt=oDato.getJoins().iterator();
			while (oIt.hasNext()) {
				RJoins oJoin=oIt.next();
				if (oJoin.isInnerJoin()) continue;
				sBuffer.append("," + oJoin.GetTablaJoin()+this.getHint());
			}
		}
		return sBuffer.toString();
	}
	
	
	private String getHint() throws Exception {
		String hint = "";
		if (oDato.ifLock()) {
			hint += " with (ROWLOCK, UPDLOCK) ";
		} else if (oDato.ifIgnoreLocks()) {
			hint += " with (READPAST)";
		} else if (oDato.ifNoLock()) {
			hint += " with (NOLOCK) ";
		} else if (oDato.ifDirtyRead() || !this.getDatabase().isTransactionInProgress()) {
			hint += " with (READUNCOMMITTED) ";
		}
		
		return hint;

//		if (oDato.hasIndexHint()) {
//			if (!bWith) {
//				sBuffer.append(" with (");
//				bWith=true;
//			} else sBuffer.append(" ,");
//			sBuffer.append(" index("+oDato.getIndexHint()+") ");
//		}
//		if (bWith) {
//			sBuffer.append(") ");
//		}
		
	}

	@Override
	public long GetIdentity(String zCampo) throws Exception {
		QueryInit();
		sSQL="Select @@identity ";
		QueryOpen();
		this.next();
		long lRet=this.CampoAsLong("").longValue();
		this.close();
		return lRet;
	}

	@Override
	protected String getStringValueAux(String zField, int zSize) throws Exception {
		return " convert(varchar("+zSize+"),"+zField+") ";
	}

	@Override
	protected String getLeftStringAux(String zField, int zSize) throws Exception {
		return " left( "+zField+","+zSize+") ";
	}

	@Override
	protected void checkSpecialErrors(SQLException zSQLExe) throws Exception {
		if (zSQLExe.getErrorCode()==JBaseJDBCImpl.ERROR_LOCK_TIMEOUT) {
			try {
				verifyLockStatus();
			} catch (Exception ex) {
				if (ex!=null) PssLogger.logInfo("LOCK STATUS: ERROR detectado-"+ex.getMessage());
				else PssLogger.logInfo("LOCK STATUS: ERROR desconocido detectado");
			}
			JExcepcion.SendError("Error de Datos Bloqueados, reintente.");
		}
		if (zSQLExe.getErrorCode()==JBaseJDBCImpl.ERROR_ARITHMETIC_OVERFLOW) JExcepcion.SendError("valor fuera de rango");
		if (zSQLExe.getErrorCode()==JBaseJDBCImpl.ERROR_ILEGAL_DATETIME) JExcepcion.SendError("Fecha fuera de rango");
		if (zSQLExe.getErrorCode()==JBaseJDBCImpl.ERROR_DUPLICATE_KEY) {
			PssLogger.logError(zSQLExe);
			JExcepcion.SendError("ALTA duplicada. Revise la identificación del ítem que intenta ingresar.");
		}

	}

	private void verifyLockStatus() throws Exception {

		JList<Long> vBlockingProcess=JCollectionFactory.createList();

		ExecuteQuery("select @@spid spid");
		this.next();
		long lSPIDlocal=CampoAsLong("spid").longValue();
		PssLogger.logInfo("LOCK STATUS: SPID="+String.valueOf(lSPIDlocal));

		String sDo=SELECT+" sli.req_spid,sli.rsc_dbid,sli.rsc_objid,sli.rsc_indid,substring (v.name, 1, 4) As Type,"+" substring (sli.rsc_text, 1, 16) as Resource,substring (u.name, 1, 8) As Mode,"+" substring (x.name, 1, 5) As Status,"+" o.name,req_ecid,req_ownertype,p.kpid   ,p.blocked ,p.waittype ,p.waittime  ,  p.lastwaittype ,"+" p.waitresource ,p.dbid,p.uid,p.cpu, p.physical_io,p.memusage,p.login_time, p.last_batch,"+" p.ecid,p.open_tran,p.status, p.hostname,p.program_name,p.hostprocess,p.cmd,p.nt_domain,"+" p.nt_username,p.net_address,p.net_library,p.loginame "+" from "+" master.dbo.syslockinfo sli,master.dbo.spt_values v,master.dbo.spt_values x,"+" master.dbo.spt_values u,sysobjects o,master..sysprocesses p "+" where "+" sli.rsc_type = v.number and v.type = 'LR' and sli.req_status = x.number"+" and x.type = 'LS' and sli.req_mode + 1 = u.number and u.type = 'L'"+" and o.id =* rsc_objid and p.spid = sli.req_spid"+" order by sli.req_spid, o.name ";
		ExecuteQuery(sDo);

		while (this.next()) {
			if (isBlockingProcess(vBlockingProcess, lSPIDlocal)) vBlockingProcess.addElement(CampoAsLong("req_spid"));

			logProcess();
		}
		killBlockingProcesses(vBlockingProcess);
	}

	private void killBlockingProcesses(JList<Long> vBlockingProcess) throws Exception {
		if (vBlockingProcess.size()>0) {
			// KILL no permite estar dentro de una Transacción
			boolean bTxInProgress=JBDatos.GetBases().getPrivateCurrentDatabase().isTransactionInProgress();
			if (bTxInProgress) JBDatos.GetBases().rollback();
			Iterator<Long> oIter=vBlockingProcess.iterator();
			while (oIter.hasNext()) {
				long lBlockingSPID=oIter.next().longValue();
				PssLogger.logInfo("KILLING SPID "+String.valueOf(lBlockingSPID));
				String sDo="kill "+String.valueOf(lBlockingSPID);
				executeWithoutTransaction(sDo);
				this.close();
			}
			if (bTxInProgress) JBDatos.GetBases().beginTransaction();
		}
	}

	private boolean isBlockingProcess(JList<Long> vBlockingProcess, long lSPIDlocal) throws Exception {
		if (!getBaseJDBC().getDriver().isAntiBlockingSystemEnabled()) return false;

		String sMode=CampoAsStr("Mode");
		Long lSPID=CampoAsLong("req_spid");
		// No debe ser el mismo proceso que está bloqueado
		if (lSPID.longValue()!=lSPIDlocal) {
			// Bloqueo tipo Exclusive o Update o Shared

			if (sMode.equals("X")||sMode.equals("U")||sMode.equals("S")) {
				// Calculo la antiguedad del proceso candidato
				Date dUltimaTxFecha=CampoAsDate("last_batch");
				String sUltimaTxHora=CampoAsTime("last_batch").toString();
				Date dUltimaTx=JDateTools.StringToDate(dUltimaTxFecha+SPACE+sUltimaTxHora, "yyyy-MM-dd HH:mm:ss");

				long lTimeoutSeconds=0;
				if (sMode.equals("X")||sMode.equals("U")) {
					lTimeoutSeconds=getBaseJDBC().getAntiBlockingTimeoutSeconds();
				}
				if (sMode.equals("S")&&CampoAsStr("Type").equals("DB")) {
					return false;
				}
				if (sMode.equals("S")) {
					lTimeoutSeconds=60*60; // Un select puede hasta 60 minutos
				}

				if (dUltimaTx.getTime()<(System.currentTimeMillis()-lTimeoutSeconds*1000)) {
					if (!vBlockingProcess.containsElement(lSPID)) return true;
				}
			}
		}
		return false;
	}

	private void logProcess() throws Exception {
		String sDo="  "+CampoAsStr("name")+"-"+CampoAsStr("req_spid")+"-"+CampoAsStr("Type")+"-"+CampoAsStr("Mode")+"-"+CampoAsStr("hostname").trim()+"-"+CampoAsStr("program_name").trim()+"-"+CampoAsStr("rsc_dbid")+"-"+CampoAsStr("rsc_objid")+"-"+CampoAsStr("rsc_indid")+"-"+CampoAsStr("Resource").trim()+"-"+CampoAsStr("Status")+"-"+CampoAsStr("req_ecid")+"-"+CampoAsStr("req_ownertype")+"-"+CampoAsStr("kpid")+"-"+CampoAsStr("blocked")+"-"+CampoAsStr("waittype")+"-"+CampoAsStr("waittime")+"-"+CampoAsStr("lastwaittype").trim()+"-"+CampoAsStr("waitresource").trim()+"-"+CampoAsStr("dbid")+"-"+CampoAsStr("uid")+"-"+CampoAsStr("cpu")+"-"+CampoAsStr("physical_io")+"-"+CampoAsStr("memusage")+"-"+CampoAsStr("login_time")+"-"+CampoAsStr("last_batch")+"-"+CampoAsStr("ecid")+"-"+CampoAsStr("open_tran")+"-"+CampoAsStr("status").trim()+"-"+CampoAsStr("hostprocess").trim()+"-"+CampoAsStr("cmd").trim()+"-"+CampoAsStr("nt_domain").trim()+"-"+CampoAsStr("nt_username").trim()+"-"
				+CampoAsStr("net_address").trim()+"-"+CampoAsStr("net_library").trim()+"-"+CampoAsStr("loginame").trim();
		PssLogger.logInfo(sDo);
	}

	@Override
	protected String getConditionalFieldIsNull(String zFieldToEvaluate, String zIsNull) throws Exception {
		// IF el campo NOT NULL: devuelve el campo
		// ELSE: devuelve zTrue
		return " isnull("+zFieldToEvaluate+",'"+zIsNull+"') ";
	}

	@Override
	public boolean readExist() throws Exception {
		QueryInit();
		sSQL=ArmarSelect().replaceFirst(SELECT, "select top 1 ");
		QueryOpen();
		boolean bRet=next();
		close();
		return bRet;
	}

	/*
	 * Obtiene el comando para realizar el backup de la instancia de la base de datos seleccionada
	 */

	@Override
	public String getBackupCommand() throws Exception {
		return "pss.core.DBManagement.JDBManagementSQLServer8|backupDefault|";
	}
	
	@Override
	public String fsum(String zFieldname) throws Exception {
		return "sum(" + (zFieldname) + ")";
	}
	@Override
	public String fmax(String zFieldname) throws Exception {
		return "max(" + (zFieldname) + ")";
	}
	@Override
	public String fmin(String zFieldname) throws Exception {
		return "min(" + (zFieldname) + ")";
	}

	@Override
	public String favg(String zFieldname) throws Exception {
		return "avg(" + (zFieldname) + ")";
	}

	@Override
	public String fcount(String zFieldname) throws Exception {
		return "count(" + (zFieldname) + ")";
	}
	

	
	public String fsubstring(String zFieldname, int desde, int hasta) throws Exception {
		return "substr(" + (zFieldname) +","+desde+","+hasta+ ")";
	}

	public String ftoDate(String zFieldname, String format) throws Exception {
		return "to_date(" + (zFieldname) +", '"+format+"')";
	}

	public String ftoChar(String zFieldname, String format) throws Exception {
		return "convert(varchar, " + (zFieldname) +", "+format+")";
	}

}
