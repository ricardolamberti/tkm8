package  pss.common.customList.config.field.operadores;


import pss.common.customList.config.field.funciones.BizFuncion;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizOperador extends JRecord   {


	
	private JString pOperador = new JString();
	private JString pDescripcion = new JString();
	private JInteger pCantValores = new JInteger();

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setOperador(String zValue) throws Exception {
		pOperador.setValue(zValue);
	}

	public String getOperador() throws Exception {
		return pOperador.getValue();
	}

	public void setCantValores(int value) throws Exception {
		pCantValores.setValue(value);
	}

	public int getCantValores() throws Exception {
		return pCantValores.getValue();
	}

	public boolean hasValores() throws Exception {
		return pCantValores.getValue()!=0;
	}

	public String getDescripcion() throws Exception {
		return this.pDescripcion.getValue();
	}
	public void setDescripcion(String zValue) throws Exception {
		pDescripcion.setValue(zValue);
	}

	/**
	 * Constructor de la Clase
	 */
	public BizOperador() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("operador", pOperador);
		this.addItem("descripcion", pDescripcion);
		this.addItem("cant_valores", pCantValores);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(FIELD, "operador", "Operador", true, true, 15);
		this.addFixedItem(FIELD, "descripcion", "Descripción", true, true, 100);
		this.addFixedItem(FIELD, "cant_valores", "Cant Valores", true, true, 5);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static BizOperador createOperador(String operador, String descripcion, int cantValores) throws Exception {
		BizOperador f = new BizOperador();
		f.setOperador(operador);
		f.setDescripcion(descripcion);
		f.setCantValores(cantValores);
		return f;
	}

	public static BizOperador findOperador(String o) throws Exception {
		if (o==null) return null;
		BizOperador oper = (BizOperador)BizOperador.getOperadores().findInHash(o);
		return oper;
	}
	
	public static JRecords<BizOperador> getOperadoresLOV() throws Exception {
		JRecords<BizOperador> recs=new JRecords<BizOperador>(BizOperador.class);
		recs.setStatic(true);
		recs.addItem(BizOperador.createOperador("=", "igual", 1));
		recs.addItem(BizOperador.createOperador("<>", "distinto", 1));
		recs.addItem(BizOperador.createOperador("NOT_NULL", "No Nulo", 0));
		recs.addItem(BizOperador.createOperador("IS_NULL", "Nulo", 0));
		recs.convertToHash("operador");
		return recs;
	}
	public static JRecords<BizOperador> getOperadores() throws Exception {
		JRecords<BizOperador> recs=new JRecords<BizOperador>(BizOperador.class);
		recs.setStatic(true);
		recs.addItem(BizOperador.createOperador("=", "igual", 1));
		recs.addItem(BizOperador.createOperador("<>", "distinto", 1));
		recs.addItem(BizOperador.createOperador(">", "mayor", 1));
		recs.addItem(BizOperador.createOperador("<", "menor", 1));
		recs.addItem(BizOperador.createOperador(">=", "mayor o igual", 1));
		recs.addItem(BizOperador.createOperador("<=", "menor o igual", 1));
		recs.addItem(BizOperador.createOperador("like", "Contenga", 1));
		recs.addItem(BizOperador.createOperador("not like", "No Contenga", 1));
		recs.addItem(BizOperador.createOperador("NOT_NULL", "No Nulo", 0));
		recs.addItem(BizOperador.createOperador("IS_NULL", "Nulo", 0));
		recs.addItem(BizOperador.createOperador(BizFuncion.FUNTION_INTERVALO, "Intervalo", 2));
		recs.addItem(BizOperador.createOperador("in", "Incluya", 1));
		recs.addItem(BizOperador.createOperador("not in", "No Incluya", 1));
		recs.convertToHash("operador");
		return recs;
	}
	
}
