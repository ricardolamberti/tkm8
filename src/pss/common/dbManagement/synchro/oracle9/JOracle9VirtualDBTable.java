/**
 * 
 */
package  pss.common.dbManagement.synchro.oracle9;

import pss.common.dbManagement.synchro.JDBSqlAction;
import pss.common.dbManagement.synchro.JDBSqlActionList;
import pss.common.dbManagement.synchro.base.JBaseVirtualDBTable;

/**
 * 
 *
 */
public class JOracle9VirtualDBTable extends JBaseVirtualDBTable {

	/**
	 * 
	 */
	public JOracle9VirtualDBTable() {
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseVirtualDBTable#generateCreateTableSQL()
	 */
	@Override
	protected String generateCreateTableSQL() throws Exception {
		String sql;
		
		sql = "CREATE TABLE " + this.tableName + " (";
		sql += this.virtualDBFields.generateFieldsDeclarationSQL();
		sql += ")";
		
		return sql;
	}

	/**
	 * @see pss.common.dbManagement.synchro.base.JBaseVirtualDBTable#generateDropTableSQL()
	 */
	@Override
	public JDBSqlActionList generateDropTableSQL() throws Exception {
		JDBSqlActionList sqlActionList = new JDBSqlActionList();
		JDBSqlAction sqlAction = new JDBSqlAction();
		String sql;
				
		sql = "DROP TABLE " + this.tableName;
		
		sqlAction.setAction(JDBSqlAction.DROP_TABLE);
		sqlAction.setSql(sql);

		sqlActionList.addAction(sqlAction);
		
		return sqlActionList;
	}

	
//	/**
//	 * Genera el SQL que describe el tipo y la longitud de un campo de una tabla, segun el formato especifico
//	 * del motor de la base de datos donde se implementa. 
//	 * 
//	 * @param tableField - Representacion logica de un campo de la base de datos.
//	 * @return retorna un string con el SQL que describe el campo dependiendo del motor de base de datos
//	 * @throws Exception
//	 */
//	@Override
//	protected String makeSqlDataTypes(JDBClassTableField jdbTableField)  throws Exception {
//		
//		int iLength=jdbTableField.getFieldLength();
//		if (jdbTableField.getFieldSQLDataType().charAt(0)==JBaseSystemDBField.C_TIPO_NUMBER) {
//			iLength+=jdbTableField.getFieldPrecition();
//		}
//		
//		String sql="";
//
//		String sLength=String.valueOf(iLength);
//		String sType=this.getDataTypeDescSQL(jdbTableField.getFieldSQLDataType());
//		sql+=sType;
//
//		switch (jdbTableField.getFieldSQLDataType().charAt(0)) {
//		case JBaseSystemDBField.C_TIPO_CHAR:
//		case JBaseSystemDBField.C_TIPO_VARCHAR: {
//			if (sLength.length()!=0) 
//				sql+="("+sLength+")";
//			break;
//		}
//		case JBaseSystemDBField.C_TIPO_AUTONUMERICO:
//		case JBaseSystemDBField.C_TIPO_NUMBER: {
//			sql+="("+sLength;
//			if (jdbTableField.getFieldPrecition()!=0) 
//				sql+=","+jdbTableField.getFieldPrecition();
//			sql+=")";
//			if (jdbTableField.getFieldSQLDataType().charAt(0)==JBaseSystemDBField.C_TIPO_AUTONUMERICO) {
//				sql+=" IDENTITY";
//			}
//			break;
//		}
//		default:
//			break;
//		} // end switch
//
//		// Por ahora todos los campos son NULL salvo que sean campos clave.
//		// Definido por HGK el 08.09.2008 
//		// if (jdbTableField.isNullableField()) {
//		if (jdbTableField.getFieldType() !=  JProperty.TIPO_CLAVE) {
//			sql += " NULL ";
//		} else {
//			sql += " NOT NULL ";
//		}
//	
//		return sql;
//	}
//
//	/**
//	 * Realiza la conversion del tipo de datos generico al tipo de datos SQL especifico de cada motor 
//	 * de base de datos.
//	 * 
//	 * @param DataType - tipo de datos generico tipificado en JDBBaseColumna
//	 * @return tipo de dato SQL 
//	 * @throws Exception
//	 */
//	@Override
//	protected String getDataTypeDescSQL(String DataType) throws Exception {
//		if (DataType.charAt(0)==JBaseSystemDBField.C_TIPO_TEXT) {
//			return "VARCHAR2(4000)";
//		}
//		if (DataType.charAt(0)==JBaseSystemDBField.C_TIPO_CHAR) {
//			return "CHAR";
//		}
//		if (DataType.charAt(0)==JBaseSystemDBField.C_TIPO_VARCHAR) {
//			return "VARCHAR2";
//		}
//		if (DataType.charAt(0)==JBaseSystemDBField.C_TIPO_DATE) {
//			return "DATE";
//		}
//		/*
//		 * if ( DataType.charAt(0) == JBaseColumna.C_TIPO_AUTONUMERICO ) { return "AUTONUMERIC"; }
//		 */
//		return "NUMBER";
//	}
//	
//	/**
//	 * Genera el SQL que agrega un campo a la tabla
//	 * 
//	 * @param tableField - Representacion logica de un campo de la base de datos.
//	 * @return retorna un string con el SQL que crea un nuevo campo
//	 * @throws Exception
//	 */
//	@Override
//	public String addColumn(JDBClassTableField jdbTableField) throws Exception {
//		String sql;
//		
//		sql = "ALTER TABLE " + this.tableName; // oDbTableClass.getTableName();
//		sql += " ADD (" + jdbTableField.getFieldName() + " ";
//		sql += this.makeSqlDataTypes(jdbTableField) + ") ";
//		
//		return sql;
//	}	
//	
//	/**
//	 * Genera el SQL que modifica los atributos de un campo a la tabla
//	 * 
//	 * @param tableField - Representacion logica de un campo de la base de datos.
//	 * @return retorna un string con el SQL que crea un nuevo campo
//	 * @throws Exception
//	 */
//	@Override
//	protected String modifyColumn(JDBClassTableField jdbTableField) throws Exception{
//		String sql;
//		
//		sql = "ALTER TABLE " + this.tableName; // oDbTableClass.getTableName();
//		sql += " MODIFY (" + jdbTableField.getFieldName() + " ";
//		sql += this.makeSqlDataTypes(jdbTableField) + ") ";
//		
//		return sql;
//	}	
//	
//	/**
//	 * Genera el SQL que describe un campo de una tabla, segun el formato especifico
//	 * del motor de la base de datos donde se implementa. 
//	 * 
//	 * @param tableField - Representacion logica de un campo de la base de datos.
//	 * @return retorna un string con el SQL que describe el campo dependiendo del motor de base de datos
//	 * @throws Exception
//	 */
//	@Override
//	protected String makeColumnDescSQL(JDBClassTableField jdbTableField) throws Exception {
//		String sql=jdbTableField.getFieldName()+" "+this.makeSqlDataTypes(jdbTableField);
//		return sql;		
//	}	
	
	
}
