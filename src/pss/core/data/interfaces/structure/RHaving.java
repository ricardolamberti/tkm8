package pss.core.data.interfaces.structure;

import java.io.Serializable;

public class RHaving  implements Serializable {

	public static final int OPERADOR_IGUAL=1;
	public static final int OPERADOR_MENOR=2;
	public static final int OPERADOR_MAYOR=3;
	public static final int OPERADOR_MENOR_O_IGUAL=4;
	public static final int OPERADOR_MAYOR_O_IGUAL=5;
	public static final int OPERADOR_DISTINTO=6;
	public static final String OPERATOR_BEGIN_IN="B_IN";
	public static final String OPERATOR_END_IN="E_IN";

	/**
	 * para usar estos operadores se deben crear los correspondientes catalogos en el motor de la base.
	 */
	public static final String OPERATOR_CONTAINS="CONTAINS";
	public static final String OPERATOR_FREETEXT="FREETEXT";

	String sTabla;
	String sCampo;
	String sOperador;
	String sValor;
	String sTipo;
	String sRelacion;
	String sAgrup;
	boolean dynamic=false;
	boolean virtual=false;

	public RHaving() {
	}

//	public RFilter(String zCampo, String zValor, String zOperador) {
//		setField(zCampo);
//		setValue(zValor);
//		setOperator(zOperador);
//	}

	public void setTable(String value) {
		sTabla=value;
	}

	public String getTable() {
		return sTabla;
	}

	public String getField() {
		return sCampo;
	}

	public void setField(String s) {
		sCampo=s;
	}

	public String getOperator() {
		return sOperador;
	}

	public void setOperator(String z) {
		sOperador=z;
	}

	public String getValue() {
		return sValor;
	}
	
	public boolean hasValue() {
		return sValor!=null && !sValor.equals("");
	}

	public void setValue(String s) {
		sValor=s;
	}

	public String getType() {
		return sTipo;
	}

	public String getRelation() {
		return sRelacion;
	}

	public boolean hasRelation() {
		return this.sRelacion!=null;
	}

	public String getAgrup() {
		return sAgrup;
	}

	public boolean hasAgrup() {
		return sAgrup!=null;
	}

	public boolean hasTable() {
		return sTabla!=null&&sTabla.length()!=0;
	}

	public void setDynamic(boolean b) {
		dynamic=b;
	}

	public void setType(String t) {
		this.sTipo=t;
	}

	public boolean isDynamic() {
		return dynamic;
	}

	public void setVirtual(boolean b) {
		virtual=b;
	}

	public boolean isVirtual() {
		return virtual;
	}

	public int GetOperadorCode() {
		if (getOperator()!=null) {
			if (getOperator().equals("=")) {
				return OPERADOR_IGUAL;
			} else if (getOperator().equals("<")) {
				return OPERADOR_MENOR;
			} else if (getOperator().equals(">")) {
				return OPERADOR_MAYOR;
			} else if (getOperator().equals("<=")) {
				return OPERADOR_MENOR_O_IGUAL;
			} else if (getOperator().equals(">=")) {
				return OPERADOR_MAYOR_O_IGUAL;
			} else if (getOperator().equals("<>")) {
				return OPERADOR_DISTINTO;
			}
		}

		return OPERADOR_IGUAL;
	}

	// public void toSAX( JSAXWrapper wrapper ) throws Exception {
	// AttributesImpl attr = new AttributesImpl();
	// attr.addAttribute( "", "name", "name", "CDATA", sCampo );
	//
	// if( getOperator() != null ) {
	// try {
	// if( getOperator().equals( "=" ) ) {
	// attr.addAttribute( "", "operatorcode", "operatorcode", "CDATA", new Integer( OPERADOR_IGUAL ).toString() );
	// } else if( getOperator().equals( "<" ) ) {
	// attr.addAttribute( "", "operatorcode", "operatorcode", "CDATA", new Integer( OPERADOR_MENOR ).toString() );
	// } else if( getOperator().equals( ">" ) ) {
	// attr.addAttribute( "", "operatorcode", "operatorcode", "CDATA", new Integer( OPERADOR_MAYOR ).toString() );
	// } else if( getOperator().equals( "<=" ) ) {
	// attr.addAttribute( "", "operatorcode", "operatorcode", "CDATA", new Integer( OPERADOR_MENOR_O_IGUAL ).toString() );
	// } else if( getOperator().equals( ">=" ) ) {
	// attr.addAttribute( "", "operatorcode", "operatorcode", "CDATA", new Integer( OPERADOR_MAYOR_O_IGUAL ).toString() );
	// } else if( getOperator().equals( "<>" ) ) {
	// attr.addAttribute( "", "operatorcode", "operatorcode", "CDATA", new Integer( OPERADOR_DISTINTO ).toString() );
	// } else {
	// attr.addAttribute( "", "operatorcode", "operatorcode", "CDATA", new Integer( OPERADOR_IGUAL ).toString() );
	// }
	// } catch( NumberFormatException e ) {}
	// }
	//
	// wrapper.startElement( "filtro", attr );
	// wrapper.characters( sValor );
	// wrapper.endElement( "filtro" );
	// }

	// Crea un filtro sobre la base de un string
	// nombre_del_filtro + "_" + código_operador + "=" + valor
	public static RFilter filtroFromString(String zUnparsedData) {
		int index;
		RFilter oFiltro;
		String nombre, valor;
		int codigoOperador=OPERADOR_IGUAL;

		index=zUnparsedData.indexOf("=");
		if (index==-1) return null;
		if (zUnparsedData.length()<=index) return null;

		nombre=zUnparsedData.substring(0, index);
		valor=zUnparsedData.substring(index+1);

		index=nombre.lastIndexOf("_");
		// Si el nombre está dividido en nombre + "_" + codigo_operador
		if (index!=-1) {
			if (nombre.length()>index) {
				// Operador
				String aux=nombre.substring(index+1);
				// Verdadero nombre
				nombre=nombre.substring(0, index);
				try {
					codigoOperador=new Integer(aux).intValue();
				} catch (NumberFormatException e) {
				}
			} else {
				// Verdadero nombre
				nombre=nombre.substring(0, index);
			}
		}

		oFiltro=new RFilter();
		oFiltro.setField(nombre);
		oFiltro.setValue(valor);

		switch (codigoOperador) {
		case OPERADOR_IGUAL:
			oFiltro.setOperator("=");
			break;
		case OPERADOR_MENOR:
			oFiltro.setOperator("<");
			break;
		case OPERADOR_MAYOR:
			oFiltro.setOperator(">");
			break;
		case OPERADOR_MENOR_O_IGUAL:
			oFiltro.setOperator("<=");
			break;
		case OPERADOR_MAYOR_O_IGUAL:
			oFiltro.setOperator(">=");
			break;
		case OPERADOR_DISTINTO:
			oFiltro.setOperator("<>");
			break;
		}

		return oFiltro;
	}
}
