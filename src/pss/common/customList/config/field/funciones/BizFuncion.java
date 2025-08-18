package pss.common.customList.config.field.funciones;


import pss.common.customList.config.field.BizField;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizFuncion extends JRecord   {


	public static final String FUNTION_ANIOACTUAL = "ANIOACTUAL";
	public static final String FUNTION_MESACTUAL = "MESACTUAL";
	public static final String FUNTION_BIMESTREACTUAL = "BIMESTREACTUAL";
	public static final String FUNTION_TRIMESTREACTUAL = "TRIMESTREACTUAL";
	public static final String FUNTION_CUATRIMESTREACTUAL = "CUATRIMESTREACTUAL";
	public static final String FUNTION_SEMESTREACTUAL = "SEMESTREACTUAL";
	public static final String FUNTION_ANIOANTERIOR = "ANIOANTERIOR";
	public static final String FUNTION_MESANTERIOR = "MESANTERIOR";
	public static final String FUNTION_BIMESTREANTERIOR = "BIMESTREANTERIOR";
	public static final String FUNTION_TRIMESTREANTERIOR = "TRIMESTREANTERIOR";
	public static final String FUNTION_CUATRIMESTREANTERIOR = "CUATRIMESTREANTERIOR";
	public static final String FUNTION_SEMESTREANTERIOR = "SEMESTREANTERIOR";
	public static final String FUNTION_HOY = "HOY";
	public static final String FUNTION_AYER = "AYER";
	public static final String FUNTION_MANIANA = "MANIANA";
	public static final String FUNTION_ULTIMOS = "ULTIMOS";
	public static final String FUNTION_PROXIMOS = "PROXIMOS";
	public static final String FUNTION_INTERVALO = "INTERVAL";

	public static final String FUNTION_ANIO = "ANIO";
	public static final String FUNTION_MES = "MES";
	public static final String FUNTION_BIMESTRE = "BIMESTRE";
	public static final String FUNTION_TRIMESTRE = "TRIMESTRE";
	public static final String FUNTION_CUATRIMESTRE = "CUATRIMESTRE";
	public static final String FUNTION_SEMESTRE = "SEMESTRE";
	public static final String FUNTION_PASADO = "PASADO";
	public static final String FUNTION_FUTURO= "FUTURO";

	
	private JString pFuncion = new JString();
	private JString pDescripcion = new JString();
	private JBoolean pWithOper = new JBoolean();
	private JBoolean pIsFunction = new JBoolean();

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setFuncion(String zValue) throws Exception {
		pFuncion.setValue(zValue);
	}

	public String getFuncion() throws Exception {
		return pFuncion.getValue();
	}

	public void setWithOper(boolean value) throws Exception {
		pWithOper.setValue(value);
	}

	public boolean hasOnlyFunction() throws Exception {
		return pIsFunction.getValue();
	}
	
	public void setOnlyFunction(boolean value) throws Exception {
		pIsFunction.setValue(value);
	}

	public boolean hasOperador() throws Exception {
		return pWithOper.getValue();
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
	public BizFuncion() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("funcion", pFuncion);
		this.addItem("descripcion", pDescripcion);
		this.addItem("with_oper", pWithOper);
		this.addItem("funtion", pFuncion);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(FIELD, "funcion", "Función", true, true, 15);
		this.addFixedItem(FIELD, "descripcion", "Descripción", true, true, 100);
		this.addFixedItem(FIELD, "with_oper", "Tiene Operador", true, true, 1);
		this.addFixedItem(FIELD, "funtion", "Es funcion", true, true, 1);
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

	public static BizFuncion createFuncion(String funcion, String descripcion, boolean withOper, boolean function) throws Exception {
		BizFuncion f = new BizFuncion();
		f.setFuncion(funcion);
		f.setDescripcion(descripcion);
		f.setWithOper(withOper);
		f.setOnlyFunction(function);
		return f;
	}

	public static BizFuncion findFuncion(String f) throws Exception {
		if (f==null) return null;
		BizFuncion func = (BizFuncion)BizFuncion.getFuncionesFecha().findInHash(f);
		if (func==null) func = (BizFuncion)BizFuncion.getFuncionesNumericas().findInHash(f);
		return func;
	}

	public static boolean activeFunctionInLista(String funcion) throws Exception {
		if (funcion==null) return false;
		if (funcion.equals(FUNTION_ANIO)) return true;
		if (funcion.equals(FUNTION_SEMESTRE)) return true;
		if (funcion.equals(FUNTION_CUATRIMESTRE)) return true;
		if (funcion.equals(FUNTION_TRIMESTRE)) return true;
		if (funcion.equals(FUNTION_BIMESTRE)) return true;
		if (funcion.equals(FUNTION_MES)) return true;
		if (funcion.equals(BizFuncion.FUNTION_ULTIMOS)) return true;
		if (funcion.equals(BizFuncion.FUNTION_PROXIMOS)) return true;
		return false;
	}
	public static boolean isFuncionYOperador(String f) throws Exception {
		if (f==null) return false;
		if (f.equals(FUNTION_ANIOACTUAL)) return true;
		if (f.equals(FUNTION_MESACTUAL)) return true;
		if (f.equals(FUNTION_BIMESTREACTUAL)) return true;
		if (f.equals(FUNTION_TRIMESTREACTUAL)) return true;
		if (f.equals(FUNTION_CUATRIMESTREACTUAL)) return true;
		if (f.equals(FUNTION_SEMESTREACTUAL)) return true;
		if (f.equals(FUNTION_ANIOANTERIOR)) return true;
		if (f.equals(FUNTION_MESANTERIOR)) return true;
		if (f.equals(FUNTION_BIMESTREANTERIOR)) return true;
		if (f.equals(FUNTION_TRIMESTREANTERIOR)) return true;
		if (f.equals(FUNTION_CUATRIMESTREANTERIOR)) return true;
		if (f.equals(FUNTION_SEMESTREANTERIOR)) return true;
		if (f.equals(FUNTION_HOY)) return true;
		if (f.equals(FUNTION_AYER)) return true;
		if (f.equals(FUNTION_MANIANA)) return true;
		if (f.equals(FUNTION_FUTURO)) return true;
		if (f.equals(FUNTION_PASADO)) return true;
		return false;
	}
	public static JRecords<BizFuncion> getFuncionesFecha() throws Exception {
		JRecords<BizFuncion> recs=new JRecords<BizFuncion>(BizFuncion.class);
		recs.setStatic(true);
		recs.addItem(BizFuncion.createFuncion(FUNTION_ANIO, "año", true, false));
		recs.addItem(BizFuncion.createFuncion(FUNTION_MES, "Mes", true, false));
		recs.addItem(BizFuncion.createFuncion(FUNTION_BIMESTRE, "Bimestre", true, false));
		recs.addItem(BizFuncion.createFuncion(FUNTION_TRIMESTRE, "Trimestre", true, false));
		recs.addItem(BizFuncion.createFuncion(FUNTION_CUATRIMESTRE, "Cuatrimestre", true, false));
		recs.addItem(BizFuncion.createFuncion(FUNTION_SEMESTRE, "Semestre", true, false));
		recs.addItem(BizFuncion.createFuncion(FUNTION_ANIOACTUAL, "año actual", false, true));
		recs.addItem(BizFuncion.createFuncion(FUNTION_MESACTUAL, "Mes actual", false, true));
		recs.addItem(BizFuncion.createFuncion(FUNTION_BIMESTREACTUAL, "Bimestre actual", false, true));
		recs.addItem(BizFuncion.createFuncion(FUNTION_TRIMESTREACTUAL, "Trimestre actual", false, true));
		recs.addItem(BizFuncion.createFuncion(FUNTION_CUATRIMESTREACTUAL, "Cuatrimestre actual", false, true));
		recs.addItem(BizFuncion.createFuncion(FUNTION_SEMESTREACTUAL, "Semestre actual", false, true));
		recs.addItem(BizFuncion.createFuncion(FUNTION_ANIOANTERIOR, "año anterior", false, true));
		recs.addItem(BizFuncion.createFuncion(FUNTION_MESANTERIOR, "Mes anterior", false, true));
		recs.addItem(BizFuncion.createFuncion(FUNTION_BIMESTREANTERIOR, "Bimestre anterior", false, true));
		recs.addItem(BizFuncion.createFuncion(FUNTION_TRIMESTREANTERIOR, "Trimestre anterior", false, true));
		recs.addItem(BizFuncion.createFuncion(FUNTION_CUATRIMESTREANTERIOR, "Cuatrimestre anterior", false, true));
		recs.addItem(BizFuncion.createFuncion(FUNTION_SEMESTREANTERIOR, "Semestre anterior", false, true));
		recs.addItem(BizFuncion.createFuncion(FUNTION_HOY, "Hoy", false, true));
		recs.addItem(BizFuncion.createFuncion(FUNTION_AYER, "Ayer", false, true));
		recs.addItem(BizFuncion.createFuncion(FUNTION_MANIANA, "Mañana", false, true));
		recs.addItem(BizFuncion.createFuncion(FUNTION_FUTURO, "En el futuro", false, true));
		recs.addItem(BizFuncion.createFuncion(FUNTION_PASADO, "En el pasado", false, true));
		recs.addItem(BizFuncion.createFuncion(FUNTION_ULTIMOS, "Ultimos días", true, true));
		recs.addItem(BizFuncion.createFuncion(FUNTION_PROXIMOS, "Proximos días", true, true));
		recs.convertToHash("funcion");
		return recs;
	}

	public static JRecords<BizFuncion> getFuncionesNumericas() throws Exception {
		JRecords<BizFuncion> recs=new JRecords<BizFuncion>(BizFuncion.class);
		recs.setStatic(true);
		recs.addItem(BizFuncion.createFuncion(BizField.FUNTION_MAX, "Máximo", true, false));
		recs.addItem(BizFuncion.createFuncion(BizField.FUNTION_MIN, "Mínimo", true, false));
		recs.addItem(BizFuncion.createFuncion(BizField.FUNTION_COUNT, "Cantidad", true, false));
		recs.addItem(BizFuncion.createFuncion(BizField.FUNTION_SUM, "Suma", true, false));
		recs.addItem(BizFuncion.createFuncion(BizField.FUNTION_AVR, "Promedio", true, false));
		recs.addItem(BizFuncion.createFuncion(BizField.FUNTION_NULO, "Sin valor", false, false));
		recs.addItem(BizFuncion.createFuncion(BizField.FUNTION_NONULO, "Con valor", false, false));
		recs.convertToHash("funcion");
		return recs;
	}

	public static JRecords<BizFuncion> getFunctionMapByType(String tipo) throws Exception {
		if (tipo==null) return BizFuncion.getFunctionEmptyMap();
		if (tipo.equals(JObject.JDATE))
			return BizFuncion.getFuncionesFecha();
		if (tipo.equals(JObject.JDATETIME))
			return BizFuncion.getFuncionesFecha();
		if (tipo.equals(JObject.JLONG))
			return BizFuncion.getFuncionesNumericas();
		return BizFuncion.getFunctionEmptyMap();
	}
//	public static JRecords<BizFuncion> getFunctionStringMap() throws Exception {
//		JRecords<BizFuncion> recs=new JRecords<BizFuncion>(BizFuncion.class);
//		recs.setStatic(true);
//		return recs;
//	}	
	
	public static JRecords<BizFuncion> getFunctionEmptyMap() throws Exception {
		JRecords<BizFuncion> recs=new JRecords<BizFuncion>(BizFuncion.class);
		recs.setStatic(true);
		return recs;
	}	

}
