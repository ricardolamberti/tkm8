package pss.core.data.implementation.xml;

import java.sql.SQLException;
import java.sql.Statement;

import com.ibm.icu.util.StringTokenizer;

import pss.core.data.interfaces.connections.JBaseJDBC;
import pss.core.data.interfaces.sentences.JRegJDBC;
import pss.core.data.interfaces.structure.RField;
import pss.core.data.interfaces.structure.RFixedOrderBy;
import pss.core.data.interfaces.structure.RGroupBy;
import pss.core.data.interfaces.structure.RJoins;
import pss.core.data.interfaces.structure.ROrderBy;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;

public class JRegJDBCImpl extends JRegJDBC {

	private int wherelen = 0;
	
	@Override
	public String getVirtualBDsField(String zFieldToEvaluate, JRecords<BizVirtual> vBDs, String zElse) throws Exception {
		String sConditionalString = "";
		String sConditionalStringEnd = "";
		vBDs.firstRecord();
		while (vBDs.nextRecord()) {
			BizVirtual oVirtual = vBDs.getRecord();
			sConditionalString = " decode(" + zFieldToEvaluate + "," + oVirtual.getValor() + "," + oVirtual.getDescrip() + ",";
			sConditionalStringEnd += ")";
		}
		if (!sConditionalString.equals("")) {
			sConditionalString += zElse + sConditionalStringEnd;
		}
		return sConditionalString;
	}

	@Override
	protected String getConditionalFieldAux(String zFieldToEvaluate, JList<String> vCondition, String zTrue, String zFalse) throws Exception {
		String sConditionalString = "";
		String sConditionalStringEnd = "";
		JIterator<String> oIter = vCondition.getIterator();
		while (oIter.hasMoreElements()) {
			String zCondition = oIter.nextElement();
			sConditionalString += " decode(" + zFieldToEvaluate + "," + zCondition + "," + zTrue + ",";
			sConditionalStringEnd += ")";
		}
		if (!sConditionalString.equals(""))
			sConditionalString += zFalse + sConditionalStringEnd;
		return sConditionalString;
	}

	@Override
	public String getConcatenateOperator() throws Exception {
		return " || ";
	}

	public JRegJDBCImpl() {
		super();
	}
	
	public String Where() throws Exception {
		String where = super.Where();
    this.wherelen = where.trim().length(); 		
    return where;
	}
	
  public String getTop() {
  	String ret = "";
  	if ( lTopRows < 0 ) return ret;
  	if ( this.wherelen > 0 ) 
  		ret += " AND ";
  	else 
  		ret += " WHERE ";
    return ret+" rownum <= " + lTopRows + " ";
  }

	@Override
	public String ArmarSelect() throws Exception {
		String sAux;
		boolean bFirst = true;
		boolean bFirstOrderBy = true;

		sAux = "select ";

		if (!oDato.getStructure().hasFields()) {
			sAux = sAux +  "*";
		} else {
			JList<RField> aCampos = oDato.getFields();
			JIterator<RField> oIt = aCampos.getIterator();
			while (oIt.hasMoreElements()) {
				RField oCampo = oIt.nextElement();
				if (bFirst) {
					bFirst = false;
				} else {
					sAux = sAux + " , ";
				}
				if (oCampo.GetTabla() != "") {
					sAux = sAux + oCampo.GetTabla() + ".";
				}
				sAux = sAux + oCampo.GetCampo();
			}
		}
		sAux = sAux + this.buildFrom();
		sAux = sAux + this.Where() + this.getTop();
		
		// Armo el Group by del Select
		if (oDato.getStructure().hasGroupBy()) {
			JList<RGroupBy> oList = oDato.getGroupBy();
			bFirst = true;
			JIterator<RGroupBy> oIterator = oList.getIterator();
			while (oIterator.hasMoreElements()) {
				RGroupBy oGroup = oIterator.nextElement();
				if (bFirst) {
					bFirst = false;
					sAux = sAux + " group by ";
				} else {
					sAux = sAux + " , ";
				}
				sAux = sAux + oGroup.GetTabla() + "." + oGroup.GetCampo();
			}
		}

		// armo el Order by del Select
		if (oDato.getStructure().hasCorteControl()) {
			JList<ROrderBy> oEnum = oDato.getCorteControl();
			bFirst = true;
			JIterator<ROrderBy> oIt = oEnum.getIterator();
			while (oIt.hasMoreElements()) {
				ROrderBy oOrder = oIt.nextElement();
				if (bFirst && bFirstOrderBy) {
					bFirst = false;
					bFirstOrderBy = false;
					sAux = sAux + " order by ";
				} else
					sAux = sAux + " , ";
				sAux = sAux + oOrder.GetTabla() + "." + oOrder.GetCampo() + " " + oOrder.GetOrden();
			}
		}
		if (oDato.getStructure().hasOrderBy()) {
			JList<ROrderBy> oEnum = oDato.getOrderBy();
			bFirst = true;
			JIterator<ROrderBy> oIt = oEnum.getIterator();
			while (oIt.hasMoreElements()) {
				ROrderBy oOrder = oIt.nextElement();
				if (bFirst && bFirstOrderBy) {
					bFirst = false;
					bFirstOrderBy = false;
					sAux = sAux + " order by ";
				} else
					sAux = sAux + " , ";
				sAux = sAux + oOrder.GetTabla() + "." + oOrder.GetCampo() + " " + oOrder.GetOrden();
			}
		}
		// armo el Order by Fijo del Select
		if (oDato.getStructure().hasFixedOrderBy()) {
			JList<RFixedOrderBy> oEnum = oDato.getFixedOrderBy();
			bFirst = true;
			JIterator<RFixedOrderBy> oIt = oEnum.getIterator();
			while (oIt.hasMoreElements()) {
				RFixedOrderBy oOrder = oIt.nextElement();
				if (bFirst && bFirstOrderBy) {
					bFirst = false;
					bFirstOrderBy = false;
					sAux = sAux + " order by ";
				} else
					sAux = sAux + " , ";
				sAux = sAux + oOrder.GetOrder();
			}
		}

		if (oDato.ifLock()) {
	/*		sAux = sAux + " for Update ";*/
		}
		sAux+=";";
		
		if (sAux.equalsIgnoreCase("SELECT * FROM TABLES ;"))
			sAux = "SELECT TABLES;";
		if (sAux.startsWith("select * from COLUMNS"))
			sAux = "SELECT COLUMNS "+sAux.substring(sAux.indexOf('\'')+1,sAux.lastIndexOf('\''))+";";
		return sAux;
	}

	@Override
	public String toNumber(String zFieldname) throws Exception {
		return "to_number(" + zFieldname + ")";
	}

	@Override
	public String ArmarInsert() throws Exception {
		String sAux;
		String sValor;
		String sCampos;
		String sValores;
		boolean bFirst = true;

		sAux = "insert into " + oDato.getStructure().getTable() + " ";
		sCampos = "";
		sValores = "";

		if (!oDato.getStructure().hasFields())
			JExcepcion.SendError("Debe especificar algun campo");
		JList<RField> oEnum = oDato.getFields();
		JIterator<RField> oIt = oEnum.getIterator();
		while (oIt.hasMoreElements()) {
			RField oCampo = oIt.nextElement();
			if (oCampo.ifOnlySelects())
				continue;
			if (bFirst) {
				bFirst = false;
			} else {
				sCampos = sCampos + " , ";
				sValores = sValores + " , ";
			}
			sCampos = sCampos + oCampo.GetCampo();
			if (oCampo.ifAutonumerico()) {
				sValor = oCampo.GetTabla() + "_" + oCampo.GetCampo() + ".nextval";
			} else {
				sValor = oCampo.GetValor();
			}
			sValores = sValores + ArmarDato(oCampo.GetTabla(), oCampo.GetTipo(), sValor);
		}
		sAux = sAux + "(" + sCampos + ") values (" + sValores + ")";
		sAux+=";";
		return sAux;
	}

	@Override
	public String ArmarUpdate() throws Exception {
		String sAux;
		String sTipo;
		boolean bFirst = true;
		sAux = "update " + oDato.getStructure().getTable() + " set ";
		if (!oDato.getStructure().hasFields()) {
			return null;
			// throw new JNoFieldException();
		}
		JList<RField> aCampos = oDato.getFields();
		JIterator<RField> oIt = aCampos.getIterator();
		while (oIt.hasMoreElements()) {
			RField oCampo = oIt.nextElement();
			if (oCampo.ifAutonumerico())
				continue;
			if (oCampo.ifOnlySelects())
				continue;

			if (bFirst) {
				bFirst = false;
			} else {
				sAux = sAux + " , ";
			}
			sAux = sAux + oCampo.GetCampo() + " = ";
			sTipo = oCampo.GetTipo();
			sAux = sAux + ArmarDato(oDato.getStructure().getTable(), sTipo, oCampo.GetValor());
		}
		sAux = sAux + Where();
		sAux+=";";
		return sAux;
	}

	@Override
	public String buildFrom() throws Exception {
		String sAux = " from " + oDato.getStructure().getTableForFrom();
		// Tablas vinculadas con joins
		if (oDato.getStructure().hasJoins()) {
			JList<RJoins> oEnum = oDato.getJoins();
			JIterator<RJoins> oIt = oEnum.getIterator();
			while (oIt.hasMoreElements()) {
				RJoins oJoin = oIt.nextElement();
				if (!oJoin.isInnerJoin())
					sAux = sAux + " , ";
				sAux = sAux + " " + oJoin.GetTablaJoin() + " ";
			}
		}
		return sAux;
	}

	@Override
	public long GetIdentity(String zCampo) throws Exception {
		QueryInit();
		String sCampo = this.oDato.getStructure().getTable() + "_" + zCampo + ".currval";
		sSQL = "Select " + sCampo + " sec from dual";
		QueryOpen();
		this.next();
		long lRet = this.CampoAsLong("sec").longValue();
		return lRet;
	}

	@Override
	protected Statement getQueryOpenStatement(JBaseJDBC oDatabaseImpl) throws Exception {
		return oDatabaseImpl.GetConnection().createStatement();
	}

	
	@Override
	protected String getStringValueAux(String zField, int zSize) throws Exception {
		return " to_char(" + zField + ") ";
	}

	@Override
	protected String getLeftStringAux(String zField, int zSize) throws Exception {
		return " substr( " + zField + ",1," + zSize + ") ";
	}

	@Override
	protected void checkSpecialErrors(SQLException zSQLExe) throws Exception {
		if (zSQLExe.getMessage().indexOf("La conversión del tipo de datos char a datetime " + "produjo un valor datetime fuera de intervalo") != -1)
			JExcepcion.SendError("Fecha fuera de rango");
		if ((zSQLExe.getMessage().indexOf("value larger than specified precision allows for this column") != -1))
			JExcepcion.SendError("Importe fuera de rango");
		if (zSQLExe.getErrorCode() == 54)
			JExcepcion.SendError("Error de Datos Bloqueados, reintente mas tarde.");

	}

	
	/** 
	 * convierte de dd-mm-yyyy a yyyy-mm-dd
	 * @param in
	 * @return
	 */
	private String convertDate(String in) {
		StringTokenizer s = new StringTokenizer(in,"-");
		String d = s.nextToken();
		String m = s.nextToken();
		String y = s.nextToken();
		return y+"-"+m+"-"+d;
	}
	private String convertDateTime(String in) {
		StringTokenizer s = new StringTokenizer(in,"-: ");
		String d = s.nextToken();
		String m = s.nextToken();
		String y = s.nextToken();
		String h = s.nextToken();
		String min = s.nextToken();
		String sec = s.nextToken();
		return y+"-"+m+"-"+d+"T"+h+":"+min+":"+sec;
	}
	
}
