package pss.core.data.interfaces.sentences;

import java.io.Serializable;
import java.util.Date;

import pss.core.data.interfaces.connections.JBDato;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.records.JBaseRecord;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;

//========================================================================== //
// Clase para Registro Base
//========================================================================== //
public class JBaseRegistro  implements Serializable {

	// -------------------------------------------------------------------------- //
	// Propiedades
	// -------------------------------------------------------------------------- //
	protected boolean isQuoted=false;
	protected transient JBDato oDatabase;
	protected JBaseRecord oDato;

  public void setQuotedOn() {
  	isQuoted=true;
  }

	
	public JBDato getDatabase() {
		return oDatabase;
	}

	public void setDatabase(JBDato zBase) {
		oDatabase=zBase;
	}

	public void setDato(JBaseRecord zDato) {
		oDato=zDato;
	}
	
  public void setTop(long zRows ) {
  }

  public void setDistinct(boolean zDist ) {
  }
  
  public void setPageSize(long zRows ) {
  }

	public void setWithUse(boolean useUse) {
	}
  public void setOffset(long zRows ) {
  }

	// -------------------------------------------------------------------------- //
	// Constructores
	// -------------------------------------------------------------------------- //
	
	@Deprecated
	public static JBaseRegistro VirtualCreate(JBDato zBase) throws Exception {
		return recordsetFactory(zBase);
	}
	
	public static JBaseRegistro recordsetFactory(JBDato zBase) throws Exception {
		JBaseRegistro oRegBase=(JBaseRegistro) zBase.GetClassInterface().newInstance();
		oRegBase.setDatabase(zBase);
		return oRegBase;
	}

	@Deprecated
	public static JBaseRegistro VirtualCreate(JBaseRecord record) throws Exception {
		return JBaseRegistro.recordsetFactory(record);
	}
	
	public static JBaseRegistro recordsetFactory(JBaseRecord record) throws Exception {
		if (record.GetTable()==null) JExcepcion.SendError("Debe especificar un tabla");
	 	JBDato oBDato=JBDatos.GetBases().GetBaseByTable(record.GetTable());
		JBaseRegistro oRegBase=oBDato.createRowInterface();
		oRegBase.setDato(record);
		return oRegBase;
	}

	public static JBaseRegistro recordsetFactory() throws Exception {
		JRecord oBD=new JRecord();
		oBD.getStructure().setTable("");
		return VirtualCreate(oBD);
	}

	@Deprecated 
	//@Link recorsetFactory()
	public static JBaseRegistro VirtualCreate() throws Exception {
		return recordsetFactory();
	}

	// -------------------------------------------------------------------------- //
	// Clases Base Genericas a sobreescribir
	// -------------------------------------------------------------------------- //
	public void openCursor() throws Exception {
	}

	public void first() throws Exception {
	}

	public boolean next() throws Exception {
		return true;
	}

	public void insert() throws Exception {
	}

	public String ArmarInsert() throws Exception {
		return "";
	}
	public String ArmarSelect() throws Exception {
		return "";
	}
	
	public void update() throws Exception {
	}

	public void delete() throws Exception {
	}

	public void close() throws Exception {
	}

	public void updateCatalog() throws Exception {
	}

	public boolean EOF() throws Exception {
		return true;
	}

	// public Object selectObjectMax(String zCampo) throws Exception {
	// return null;
	// }
	public Object selectObjectAvg(String zCampo, boolean isNumber) throws Exception {
		return null;
	}

	public Object selectObjectMax(String zCampo, boolean isNumber) throws Exception {
		return null;
	}

	public Object selectObjectMin(String zCampo) throws Exception {
		return null;
	}

	public String SelectMax(String zCampo) throws Exception {
		return "";
	}

	public String selectMin(String zCampo) throws Exception {
		return "";
	}

	public long selectSupraCount(String sql) throws Exception {
		return 0;
	}
	
	public long selectCount() throws Exception {
		return 0;
	}

	public String selectSum(String zCampo) throws Exception {
		return "0";
	}

	public long NextSecuencia(String zCampo) throws Exception {
		return 0;
	}

	public long GetIdentity(String zCampo) throws Exception {
		return 0;
	}

	public void executeTransaction(String zSql) throws Exception {
	}

	public void executeWithoutTransaction(String zSql) throws Exception {
	}

	public void executeQueryWithoutTransaction(String sql) throws Exception {

	}

	public void Execute(String zSQL) throws Exception {
	}

	public void ExecuteQuery(String zSQL) throws Exception {
	}
	
	public int getResult() {
		return 0;
	}

	public JMap<String, String> ExecuteQueryOneRow(JList<String> zReturnFieldList, String zSQL) throws Exception {
		return null;
	}

	public String CampoAsStr(String zC) throws Exception {
		return null;
	}

	// public String CampoAsBlob(String zC) throws Exception {
	public String CampoAsBlob(String zC) throws Exception {
		return null;
	}
	
	public Long CampoAsLong(String zC) throws Exception {
		return null;
	}

	public Integer CampoAsInt(String zC) throws Exception {
		return null;
	}

	public Double CampoAsFloat(String zC) throws Exception {
		return null;
	}

	public String CampoAsBinary(String zC) throws Exception {
		return null;
	}

	public Object CampoAsObject(String zC) throws Exception {
		return null;
	}

	public java.util.Date CampoAsDate(String zC) throws Exception {
		return null;
	}

	public java.sql.Time CampoAsTime(String zC) throws Exception {
		return null;
	}
	
	

	// -------------------------------------------------------------------------- //
	// Select
	// -------------------------------------------------------------------------- //
	public boolean Select() throws Exception {
		this.openCursor();
		this.next();
		if (EOF()&&!oDato.withoutException())
		// JExcepcion.SendError( "Ninguna Fila Encontrada: " + oDato.GetTable() );
		throw new JNoRowFoundException("Ninguna Fila Encontrada:^ "+oDato.GetTable(), oDato.GetTable());

		return !EOF();
	}

	public long longValue(String zCampo) throws Exception {
		Long campo=CampoAsLong(zCampo);
		return (campo==null) ? 0L : campo.longValue();
	}

	public int intValue(String zCampo) throws Exception {
		Integer campo=CampoAsInt(zCampo);
		return (campo==null) ? 0 : campo.intValue();
	}

	public double doubleValue(String zCampo) throws Exception {
		Double campo=CampoAsFloat(zCampo);
		return (campo==null) ? 0d : campo.doubleValue();
	}

	public boolean ExecuteQueryLookForExistence(String zSQL) throws Exception {
		return false;
	}

	public String toNumber(String zFieldname) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String toNumber(String zFieldname, String zFormatMask) throws Exception {
		throw new UnsupportedOperationException();
	}


	public String fmin(String zFieldname) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fmax(String zFieldname) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fbooleanValue(String zFieldname,String strue,String sfalse) throws Exception {
		throw new UnsupportedOperationException();
		}	
	public String fnulo(String zFieldname) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fnonulo(String zFieldname) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fsum(String zFieldname) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fsumover(String zFieldname) throws Exception {
		throw new UnsupportedOperationException();
	}

	public String favg(String zFieldname) throws Exception {
		throw new UnsupportedOperationException();
	}

	public String fcount(String zFieldname) throws Exception {
		throw new UnsupportedOperationException();
	}

	public String fmes(String zFieldname) throws Exception {
		throw new UnsupportedOperationException();
	}
	
	public String fporcWithOver(String zFieldname,JList<String> partition) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fporc(String zFieldname,String zTotalize) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fanio(String zFieldname) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fanioactual(String zFieldname,Date hoy) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String ffuturo(String zFieldname,Date hoy) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fpasado(String zFieldname,Date hoy) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fbimestreactual(String zFieldname,Date hoy) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String ftrimestreactual(String zFieldname,Date hoy) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fcuatrimestreactual(String zFieldname,Date hoy) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fsemestreactual(String zFieldname,Date hoy) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fmesactual(String zFieldname,Date hoy) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fhoy(String zFieldname,Date hoy) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fayer(String zFieldname,Date hoy) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fmaniana(String zFieldname,Date hoy) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fultimos(String zFieldname,Date hoy,long valor) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fproximos(String zFieldname,Date hoy,long valor) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fintervalo(String zFieldname,String valor1,String valor2) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String faniomes(String zFieldname) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String faniosem(String zFieldname) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fbimestre(String zFieldname) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String ftrimestre(String zFieldname) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fcuatrimestre(String zFieldname) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fsemestre(String zFieldname) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fdiasemana(String zFieldname) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String fdiames(String zFieldname) throws Exception {
		throw new UnsupportedOperationException();
	}


	public String fsubstring(String zFieldname, int desde, int hasta) throws Exception {
		throw new UnsupportedOperationException();
	}

	public String ftoDate(String zFieldname, String format) throws Exception {
		throw new UnsupportedOperationException();
	}

	public String ftoChar(String zFieldname, String format) throws Exception {
		throw new UnsupportedOperationException();
	}
	public String dateDiff(String field, String d) throws Exception {
		throw new UnsupportedOperationException();
	}
	
	public boolean readExist() throws Exception {
		openCursor();
		boolean bRet=next();
		close();
		return bRet;
	}


	public boolean isDatabaseSupportsOffset() {
		return false;
	}
	
	public JIterator<String> getFieldNameIterator() throws Exception {
		return null;
	}
	
	
	public String selectAvg(String zCampo) throws Exception {
		return "0";
	}

}
