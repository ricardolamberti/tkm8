package  pss.common.dbManagement.synchro;

import pss.core.tools.JExcepcion;

/**
 * Clase que almacena informacion sobre los campos de un indice.
 * 
 */
public class JDBClassIndexField {

	/**
	 * Tipo de orden del campo del indice en forma Ascendente
	 */
	public static final String TIPO_ORDEN_ASC = "A";
	/**
	 * Tipo de orden del campo del indice en forma Descendente
	 */
	public static final String TIPO_ORDEN_DESC = "D";

	/**
	 * Nombre del campo
	 */
	private String fieldName = null;

	/**
	 * Orden de los campos
	 */
	private String sortFieldOrder = null;

	/**
	 * Retorna el nombre del campo del indice o
	 * 
	 * @return Nombre del campo o un campo vacio si no esta configurado
	 */
	public String getFieldName() {
		if (fieldName == null) {
			return "";
		}
		return fieldName;
	}

	/**
	 * Setea el nombre del campo
	 * 
	 * @param pFieldName
	 *          Nombre del campo
	 * @throws Exception
	 *           Si el nombre del campo es nulo o vacio
	 */
	public void setFieldName(String pFieldName) throws Exception {
		if (pFieldName == null || pFieldName.isEmpty()) {
			JExcepcion.SendError("Nombre del campo del indice es nulo o vacio");
		}

		this.fieldName = pFieldName;
	}

	/**
	 * Retorna el tipo de ordenamiento que tiene el campo. Por default retorna
	 * {@link #TIPO_ORDEN_ASC TIPO_ORDEN_ASC}
	 * 
	 * @return Tipo de ordenamiento. Retorna uno de los siguientes valores:
	 *         <li>{@link #TIPO_ORDEN_ASC TIPO_ORDEN_ASC}</li>
	 *         <li>{@link #TIPO_ORDEN_DESC TIPO_ORDEN_DESC}</li>
	 */
	public String getSortFieldOrder() {
		if (this.sortFieldOrder == null) {
			return TIPO_ORDEN_ASC;
		}
		return sortFieldOrder;
	}

	/**
	 * Setea el tipo de ordenamiento del campo
	 * 
	 * @param pSortFieldOrder
	 *          Tipo de Ordenamiento puede ser uno de los siguientes valores:
	 *         <li>{@link #TIPO_ORDEN_ASC TIPO_ORDEN_ASC}</li>
	 *         <li>{@link #TIPO_ORDEN_DESC TIPO_ORDEN_DESC}</li>          
	 * @throws Exception Si el campo es nulo, vacio o no es ninguno de los valores validos
	 */
	public void setSortFieldOrder(String pSortFieldOrder) throws Exception {
		if (pSortFieldOrder == null || pSortFieldOrder.isEmpty()) {
			JExcepcion.SendError("Tipo de ordenamiento es vacio o nulo");
		}
		
		if (pSortFieldOrder.compareTo(TIPO_ORDEN_ASC) != 0 && 
				pSortFieldOrder.compareTo(TIPO_ORDEN_DESC) != 0) {
			JExcepcion.SendError("Tipo de ordenamiento invalido: " + pSortFieldOrder );
		}
		
		this.sortFieldOrder = pSortFieldOrder;
	}

}
