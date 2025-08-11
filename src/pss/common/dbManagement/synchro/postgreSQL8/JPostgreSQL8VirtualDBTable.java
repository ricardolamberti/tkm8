/**
 * 
 */
package  pss.common.dbManagement.synchro.postgreSQL8;

import pss.common.dbManagement.synchro.JDBSqlAction;
import pss.common.dbManagement.synchro.JDBSqlActionList;
import pss.common.dbManagement.synchro.base.JBaseVirtualDBTable;

/**
 * 
 *
 */
public class JPostgreSQL8VirtualDBTable extends JBaseVirtualDBTable {

	/**
	 * 
	 */
	public JPostgreSQL8VirtualDBTable() {
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
//		sql = "ALTER TABLE " + this.tableName; //  oDbTableClass.getTableName();
//		sql += " ADD COLUMN " + jdbTableField.getFieldName() + " ";
//		sql += this.makeSqlDataTypes(jdbTableField);
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
//		sql = "ALTER TABLE " + this.tableName; //  oDbTableClass.getTableName();
//		sql += " MODIFY COLUMN" + jdbTableField.getFieldName() + " ";
//		sql += this.makeSqlDataTypes(jdbTableField);
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
//		String sql;
//		
//		// Chequea que el nombre del campo sea reservado o no para ponerle comillas asi lo salvo.
//		if (JDBColumnaPostgresql.isFieldNameIsReserved(jdbTableField.getFieldName())) {
//			sql = "\"" + jdbTableField.getFieldName() + "\"";
//		} else {
//			sql = jdbTableField.getFieldName();
//		}
//		
//		sql+= " "+this.makeSqlDataTypes(jdbTableField);
//		return sql;		
//	}	
	
	
}
