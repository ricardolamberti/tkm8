package pss.bsp.bo.formato;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pss.bsp.bo.BizInterfazBO;
import pss.bsp.consolidador.IConciliable;
import pss.bsp.parseo.IFormato;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

import com.ibm.icu.util.StringTokenizer;

public class BizFormato extends JRecord implements IFormato {

	private JString pCompany = new JString();

	private JString pOwner = new JString();

	private JString pIdformato = new JString();

	private JString pD1 = new JString();

	private JString pD2 = new JString();

	private JString pD3 = new JString();

	private JString pD4 = new JString();

	private JString pD5 = new JString();

	private JString pD6 = new JString();

	private JString pD7 = new JString();

	private JString pD8 = new JString();

	private JString pD9 = new JString();

	private JString pD10 = new JString();

	private JString pD11 = new JString();

	private JString pD12 = new JString();

	private JString pD13 = new JString();

	private JString pD14 = new JString();

	private JString pD15 = new JString();

	private JString pD16 = new JString();

	private JString pD17 = new JString();

	private JString pD18 = new JString();

	private JString pD19 = new JString();

	private JString pD20 = new JString();
  private JString pD21 = new JString();
  private JString pD22 = new JString();
  private JString pD23 = new JString();
  private JString pD24 = new JString();
  private JString pD25 = new JString();
  private JString pD26 = new JString();
  private JString pD27 = new JString();
  private JString pD28 = new JString();
  private JString pD29 = new JString();
  private JString pD30 = new JString();
  private JString pD31 = new JString();
  private JString pD32 = new JString();
  private JString pD33 = new JString();
  private JString pD34 = new JString();
  private JString pD35 = new JString();
  private JString pD36 = new JString();
  private JString pD37 = new JString();
  private JString pD38 = new JString();
  private JString pD39 = new JString();
  private JString pD40 = new JString();

	private JString pDescripcion = new JString();

	private JString pExampleParseo = new JString();

	private JString pExampleIdInterfaz = new JString();
	
	private JString pConciliableKey = new JString();
	
	static JMap<String, String> camposPosibles;

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setConciliableKey(String zValue) throws Exception {
		pConciliableKey.setValue(zValue);
	}

	public String getConciliableKey() throws Exception {
		return pConciliableKey.getValue();
	}

	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public boolean isNullCompany() throws Exception {
		return pCompany.isNull();
	}

	public void setNullToCompany() throws Exception {
		pCompany.setNull();
	}

	public void setOwner(String zValue) throws Exception {
		pOwner.setValue(zValue);
	}

	public String getOwner() throws Exception {
		return pOwner.getValue();
	}

	public boolean isNullOwner() throws Exception {
		return pOwner.isNull();
	}

	public void setNullToOwner() throws Exception {
		pOwner.setNull();
	}

	public void setIdformato(String zValue) throws Exception {
		pIdformato.setValue(zValue);
	}

	public String getIdformato() throws Exception {
		return pIdformato.getValue();
	}

	public boolean isNullIdformato() throws Exception {
		return pIdformato.isNull();
	}

	public void setNullToIdformato() throws Exception {
		pIdformato.setNull();
	}

	public void setD1(String zValue) throws Exception {
		pD1.setValue(zValue);
	}

	public String getD1() throws Exception {
		return pD1.getValue();
	}

	public boolean isNullD1() throws Exception {
		return pD1.isNull();
	}

	public void setNullToD1() throws Exception {
		pD1.setNull();
	}

	public void setD2(String zValue) throws Exception {
		pD2.setValue(zValue);
	}

	public String getD2() throws Exception {
		return pD2.getValue();
	}

	public boolean isNullD2() throws Exception {
		return pD2.isNull();
	}

	public void setNullToD2() throws Exception {
		pD2.setNull();
	}

	public void setD3(String zValue) throws Exception {
		pD3.setValue(zValue);
	}

	public String getD3() throws Exception {
		return pD3.getValue();
	}

	public boolean isNullD3() throws Exception {
		return pD3.isNull();
	}

	public void setNullToD3() throws Exception {
		pD3.setNull();
	}

	public void setD4(String zValue) throws Exception {
		pD4.setValue(zValue);
	}

	public String getD4() throws Exception {
		return pD4.getValue();
	}

	public boolean isNullD4() throws Exception {
		return pD4.isNull();
	}

	public void setNullToD4() throws Exception {
		pD4.setNull();
	}

	public void setD5(String zValue) throws Exception {
		pD5.setValue(zValue);
	}

	public String getD5() throws Exception {
		return pD5.getValue();
	}

	public boolean isNullD5() throws Exception {
		return pD5.isNull();
	}

	public void setNullToD5() throws Exception {
		pD5.setNull();
	}

	public void setD6(String zValue) throws Exception {
		pD6.setValue(zValue);
	}

	public String getD6() throws Exception {
		return pD6.getValue();
	}

	public boolean isNullD6() throws Exception {
		return pD6.isNull();
	}

	public void setNullToD6() throws Exception {
		pD6.setNull();
	}

	public void setD7(String zValue) throws Exception {
		pD7.setValue(zValue);
	}

	public String getD7() throws Exception {
		return pD7.getValue();
	}

	public boolean isNullD7() throws Exception {
		return pD7.isNull();
	}

	public void setNullToD7() throws Exception {
		pD7.setNull();
	}

	public void setD8(String zValue) throws Exception {
		pD8.setValue(zValue);
	}

	public String getD8() throws Exception {
		return pD8.getValue();
	}

	public boolean isNullD8() throws Exception {
		return pD8.isNull();
	}

	public void setNullToD8() throws Exception {
		pD8.setNull();
	}

	public void setD9(String zValue) throws Exception {
		pD9.setValue(zValue);
	}

	public String getD9() throws Exception {
		return pD9.getValue();
	}

	public boolean isNullD9() throws Exception {
		return pD9.isNull();
	}

	public void setNullToD9() throws Exception {
		pD9.setNull();
	}

	public void setD10(String zValue) throws Exception {
		pD10.setValue(zValue);
	}

	public String getD10() throws Exception {
		return pD10.getValue();
	}

	public boolean isNullD10() throws Exception {
		return pD10.isNull();
	}

	public void setNullToD10() throws Exception {
		pD10.setNull();
	}

	public void setD11(String zValue) throws Exception {
		pD11.setValue(zValue);
	}

	public String getD11() throws Exception {
		return pD11.getValue();
	}

	public boolean isNullD11() throws Exception {
		return pD11.isNull();
	}

	public void setNullToD11() throws Exception {
		pD11.setNull();
	}

	public void setD12(String zValue) throws Exception {
		pD12.setValue(zValue);
	}

	public String getD12() throws Exception {
		return pD12.getValue();
	}

	public boolean isNullD12() throws Exception {
		return pD12.isNull();
	}

	public void setNullToD12() throws Exception {
		pD12.setNull();
	}

	public void setD13(String zValue) throws Exception {
		pD13.setValue(zValue);
	}

	public String getD13() throws Exception {
		return pD13.getValue();
	}

	public boolean isNullD13() throws Exception {
		return pD13.isNull();
	}

	public void setNullToD13() throws Exception {
		pD13.setNull();
	}

	public void setD14(String zValue) throws Exception {
		pD14.setValue(zValue);
	}

	public String getD14() throws Exception {
		return pD14.getValue();
	}

	public boolean isNullD14() throws Exception {
		return pD14.isNull();
	}

	public void setNullToD14() throws Exception {
		pD14.setNull();
	}

	public void setD15(String zValue) throws Exception {
		pD15.setValue(zValue);
	}

	public String getD15() throws Exception {
		return pD15.getValue();
	}

	public boolean isNullD15() throws Exception {
		return pD15.isNull();
	}

	public void setNullToD15() throws Exception {
		pD15.setNull();
	}

	public void setD16(String zValue) throws Exception {
		pD16.setValue(zValue);
	}

	public String getD16() throws Exception {
		return pD16.getValue();
	}

	public boolean isNullD16() throws Exception {
		return pD16.isNull();
	}

	public void setNullToD16() throws Exception {
		pD16.setNull();
	}

	public void setD17(String zValue) throws Exception {
		pD17.setValue(zValue);
	}

	public String getD17() throws Exception {
		return pD17.getValue();
	}

	public boolean isNullD17() throws Exception {
		return pD17.isNull();
	}

	public void setNullToD17() throws Exception {
		pD17.setNull();
	}

	public void setD18(String zValue) throws Exception {
		pD18.setValue(zValue);
	}

	public String getD18() throws Exception {
		return pD18.getValue();
	}

	public boolean isNullD18() throws Exception {
		return pD18.isNull();
	}

	public void setNullToD18() throws Exception {
		pD18.setNull();
	}

	public void setD19(String zValue) throws Exception {
		pD19.setValue(zValue);
	}

	public String getD19() throws Exception {
		return pD19.getValue();
	}

	public boolean isNullD19() throws Exception {
		return pD19.isNull();
	}

	public void setNullToD19() throws Exception {
		pD19.setNull();
	}

	public void setD20(String zValue) throws Exception {
		pD20.setValue(zValue);
	}

	public String getD20() throws Exception {
		return pD20.getValue();
	}

	public boolean isNullD20() throws Exception {
		return pD20.isNull();
	}

	public void setNullToD20() throws Exception {
		pD20.setNull();
	}
	public void setD21(String zValue) throws Exception {
		pD21.setValue(zValue);
	}

	public String getD21() throws Exception {
		return pD21.getValue();
	}
	public void setD22(String zValue) throws Exception {
		pD22.setValue(zValue);
	}

	public String getD23() throws Exception {
		return pD23.getValue();
	}
	public void setD23(String zValue) throws Exception {
		pD23.setValue(zValue);
	}

	public String getD22() throws Exception {
		return pD22.getValue();
	}
	public void setD24(String zValue) throws Exception {
		pD24.setValue(zValue);
	}

	public String getD24() throws Exception {
		return pD24.getValue();
	}
	public void setD25(String zValue) throws Exception {
		pD25.setValue(zValue);
	}

	public String getD25() throws Exception {
		return pD25.getValue();
	}
	public void setD26(String zValue) throws Exception {
		pD26.setValue(zValue);
	}

	public String getD26() throws Exception {
		return pD26.getValue();
	}
	public void setD27(String zValue) throws Exception {
		pD27.setValue(zValue);
	}

	public String getD27() throws Exception {
		return pD27.getValue();
	}
	public void   setD28(String zValue) throws Exception {	pD28.setValue(zValue);}
	public String getD28() throws Exception{	       return pD28.getValue();}

	public void   setD29(String zValue) throws Exception {	pD29.setValue(zValue);}
	public String getD29() throws Exception{	       return pD29.getValue();}

	public void   setD30(String zValue) throws Exception {	pD30.setValue(zValue);}
	public String getD30() throws Exception{	       return pD30.getValue();}

	public void   setD31(String zValue) throws Exception {	pD31.setValue(zValue);}
	public String getD31() throws Exception{	       return pD31.getValue();}

	public void   setD32(String zValue) throws Exception {	pD32.setValue(zValue);}
	public String getD32() throws Exception{	       return pD32.getValue();}

	public void   setD33(String zValue) throws Exception {	pD33.setValue(zValue);}
	public String getD33() throws Exception{	       return pD33.getValue();}
	
	public void   setD34(String zValue) throws Exception {	pD34.setValue(zValue);}
	public String getD34() throws Exception{	       return pD34.getValue();}
	
	public void   setD35(String zValue) throws Exception {	pD35.setValue(zValue);}
	public String getD35() throws Exception{	       return pD35.getValue();}

	public void   setD36(String zValue) throws Exception {	pD36.setValue(zValue);}
	public String getD36() throws Exception{	       return pD36.getValue();}

	public void   setD37(String zValue) throws Exception {	pD37.setValue(zValue);}
	public String getD37() throws Exception{	       return pD37.getValue();}

	public void   setD38(String zValue) throws Exception {	pD38.setValue(zValue);}
	public String getD38() throws Exception{	       return pD38.getValue();}

	public void   setD39(String zValue) throws Exception {	pD39.setValue(zValue);}
	public String getD39() throws Exception{	       return pD39.getValue();}

	public void   setD40(String zValue) throws Exception {	pD40.setValue(zValue);}
	public String getD40() throws Exception{	       return pD40.getValue();}


	public void setDescripcion(String zValue) throws Exception {
		pDescripcion.setValue(zValue);
	}

	public String getDescripcion() throws Exception {
		return pDescripcion.getValue();
	}

	public boolean isNullDescripcion() throws Exception {
		return pDescripcion.isNull();
	}

	public void setNullToDescripcion() throws Exception {
		pDescripcion.setNull();
	}

	public void setExampleParseo(String zValue) throws Exception {
		pExampleParseo.setValue(zValue);
	}

	public String getExampleParseo() throws Exception {
		return pExampleParseo.getValue();
	}

	public void setExampleIdInterfaz(String zValue) throws Exception {
		pExampleIdInterfaz.setValue(zValue);
	}

	public String getExampleIdInterfaz() throws Exception {
		return pExampleIdInterfaz.getValue();
	}

	public static JMap<String, String> getCamposPosibles() {
		if (camposPosibles != null) return camposPosibles;
		camposPosibles = JCollectionFactory.createOrderedMap();
		camposPosibles.addElement(IConciliable.NO_CHEQUEAR, IConciliable.NO_CHEQUEAR);
		camposPosibles.addElement(IConciliable.AEROLINEA_BOLETOS, IConciliable.AEROLINEA_BOLETOS);
		camposPosibles.addElement(IConciliable.AEROLINEA, IConciliable.AEROLINEA);
		camposPosibles.addElement(IConciliable.BASE_IMPONIBLE, IConciliable.BASE_IMPONIBLE);
		camposPosibles.addElement(IConciliable.BOLETOS, IConciliable.BOLETOS);
		camposPosibles.addElement(IConciliable.BOLETOS_LARGO, IConciliable.BOLETOS_LARGO);
		camposPosibles.addElement(IConciliable.CLIENTE, IConciliable.CLIENTE);
		camposPosibles.addElement(IConciliable.COMISION, IConciliable.COMISION);
		camposPosibles.addElement(IConciliable.COMISION_ACUM, IConciliable.COMISION_ACUM);
		camposPosibles.addElement(IConciliable.COMISION_ACUM_INV, IConciliable.COMISION_ACUM_INV);
		camposPosibles.addElement(IConciliable.COMISION_OVER, IConciliable.COMISION_OVER);
		camposPosibles.addElement(IConciliable.COMISION_PORC, IConciliable.COMISION_PORC);
		camposPosibles.addElement(IConciliable.COMISION_INV, IConciliable.COMISION_INV);
		camposPosibles.addElement(IConciliable.COMISION_OVER_INV, IConciliable.COMISION_OVER_INV);
		camposPosibles.addElement(IConciliable.CONTADO, IConciliable.CONTADO);
		camposPosibles.addElement(IConciliable.CONTADO_BRUTO, IConciliable.CONTADO_BRUTO);
		camposPosibles.addElement(IConciliable.FECHA, IConciliable.FECHA);
		camposPosibles.addElement(IConciliable.ID_AEROLINEA, IConciliable.ID_AEROLINEA);
		camposPosibles.addElement(IConciliable.IDCLIENTE, IConciliable.IDCLIENTE);
		camposPosibles.addElement(IConciliable.IMPUESTO_1, IConciliable.IMPUESTO_1);
		camposPosibles.addElement(IConciliable.IMPUESTO_2, IConciliable.IMPUESTO_2);
		camposPosibles.addElement(IConciliable.IMPUESTO_ACUM, IConciliable.IMPUESTO_ACUM);
		camposPosibles.addElement(IConciliable.IMP_SOBRE_COMISION, IConciliable.IMP_SOBRE_COMISION);
		camposPosibles.addElement(IConciliable.IMP_SOBRE_COMISION_INV, IConciliable.IMP_SOBRE_COMISION_INV);
		camposPosibles.addElement(IConciliable.NETO, IConciliable.NETO);
		camposPosibles.addElement(IConciliable.NUMERO_TARJETA, IConciliable.NUMERO_TARJETA);
		camposPosibles.addElement(IConciliable.OPERACION, IConciliable.OPERACION);
		camposPosibles.addElement(IConciliable.TARIFA, IConciliable.TARIFA);
		camposPosibles.addElement(IConciliable.TARIFA_SIN_COMISION, IConciliable.TARIFA_SIN_COMISION);
		camposPosibles.addElement(IConciliable.TARJETA, IConciliable.TARJETA);
		camposPosibles.addElement(IConciliable.TARJETA_BRUTO, IConciliable.TARJETA_BRUTO);
		camposPosibles.addElement(IConciliable.TIPO_RUTA, IConciliable.TIPO_RUTA);
		camposPosibles.addElement(IConciliable.TIPO_TARJETA, IConciliable.TIPO_TARJETA);
		camposPosibles.addElement(IConciliable.TOTALAPAGAR, IConciliable.TOTALAPAGAR);
		camposPosibles.addElement(IConciliable.TOTAL, IConciliable.TOTAL);
		camposPosibles.addElement(IConciliable.OBSERVACION, IConciliable.OBSERVACION);
		return camposPosibles;
	}

	/**
	 * Constructor de la Clase
	 */
	public BizFormato() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("owner", pOwner);
		this.addItem("id_formato", pIdformato);
		this.addItem("d1", pD1);
		this.addItem("d2", pD2);
		this.addItem("d3", pD3);
		this.addItem("d4", pD4);
		this.addItem("d5", pD5);
		this.addItem("d6", pD6);
		this.addItem("d7", pD7);
		this.addItem("d8", pD8);
		this.addItem("d9", pD9);
		this.addItem("d10", pD10);
		this.addItem("d11", pD11);
		this.addItem("d12", pD12);
		this.addItem("d13", pD13);
		this.addItem("d14", pD14);
		this.addItem("d15", pD15);
		this.addItem("d16", pD16);
		this.addItem("d17", pD17);
		this.addItem("d18", pD18);
		this.addItem("d19", pD19);
    this.addItem( "d20", pD20 );
    this.addItem( "d21", pD21 );
    this.addItem( "d22", pD22 );
    this.addItem( "d23", pD23 );
    this.addItem( "d24", pD24 );
    this.addItem( "d25", pD25 );
    this.addItem( "d26", pD26 );
    this.addItem( "d27", pD27 );
    this.addItem( "d28", pD28 );
    this.addItem( "d29", pD29 );
    this.addItem( "d30", pD30 );
    this.addItem( "d31", pD31 );
    this.addItem( "d32", pD32 );
    this.addItem( "d33", pD33 );
    this.addItem( "d34", pD34 );
    this.addItem( "d35", pD35 );
    this.addItem( "d36", pD36 );
    this.addItem( "d37", pD37 );
    this.addItem( "d38", pD38 );
    this.addItem( "d39", pD39 );
    this.addItem( "d40", pD40 );
		this.addItem("descripcion", pDescripcion);
		this.addItem("conciliable_key", pConciliableKey);
		this.addItem("example_idinterfaz", pExampleIdInterfaz);
		this.addItem("example_parseo", pExampleParseo);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Company", true, true, 50);
		this.addFixedItem(KEY, "id_formato", "Id formato", true, true, 50);
		this.addFixedItem(FIELD, "owner", "Owner", true, true, 50);
		this.addFixedItem(FIELD, "d1", "D1", true, true, 150);
		this.addFixedItem(FIELD, "d2", "D2", true, true, 150);
		this.addFixedItem(FIELD, "d3", "D3", true, true, 150);
		this.addFixedItem(FIELD, "d4", "D4", true, true, 150);
		this.addFixedItem(FIELD, "d5", "D5", true, true, 150);
		this.addFixedItem(FIELD, "d6", "D6", true, true, 150);
		this.addFixedItem(FIELD, "d7", "D7", true, true, 150);
		this.addFixedItem(FIELD, "d8", "D8", true, true, 150);
		this.addFixedItem(FIELD, "d9", "D9", true, true, 150);
		this.addFixedItem(FIELD, "d10", "D10", true, true, 150);
		this.addFixedItem(FIELD, "d11", "D11", true, true, 150);
		this.addFixedItem(FIELD, "d12", "D12", true, true, 150);
		this.addFixedItem(FIELD, "d13", "D13", true, true, 150);
		this.addFixedItem(FIELD, "d14", "D14", true, true, 150);
		this.addFixedItem(FIELD, "d15", "D15", true, true, 150);
		this.addFixedItem(FIELD, "d16", "D16", true, true, 150);
		this.addFixedItem(FIELD, "d17", "D17", true, true, 150);
		this.addFixedItem(FIELD, "d18", "D18", true, true, 150);
		this.addFixedItem(FIELD, "d19", "D19", true, true, 150);
		this.addFixedItem(FIELD, "d20", "D20", true, true, 150);
    this.addFixedItem( FIELD, "d21", "D21", true, false, 150 );
    this.addFixedItem( FIELD, "d22", "D22", true, false, 150 );
    this.addFixedItem( FIELD, "d23", "D23", true, false, 150 );
    this.addFixedItem( FIELD, "d24", "D24", true, false, 150 );
    this.addFixedItem( FIELD, "d25", "D25", true, false, 150 );
    this.addFixedItem( FIELD, "d26", "D26", true, false, 150 );
    this.addFixedItem( FIELD, "d27", "D27", true, false, 150 );
    this.addFixedItem( FIELD, "d28", "D28", true, false, 150 );
    this.addFixedItem( FIELD, "d29", "D29", true, false, 150 );
    this.addFixedItem( FIELD, "d30", "D30", true, false, 150 );
    this.addFixedItem( FIELD, "d31", "D31", true, false, 150 );
    this.addFixedItem( FIELD, "d32", "D32", true, false, 150 );
    this.addFixedItem( FIELD, "d33", "D33", true, false, 150 );
    this.addFixedItem( FIELD, "d34", "D34", true, false, 150 );
    this.addFixedItem( FIELD, "d35", "D35", true, false, 150 );
    this.addFixedItem( FIELD, "d36", "D36", true, false, 150 );
    this.addFixedItem( FIELD, "d37", "D37", true, false, 150 );
    this.addFixedItem( FIELD, "d38", "D38", true, false, 150 );
    this.addFixedItem( FIELD, "d39", "D39", true, false, 150 );
    this.addFixedItem( FIELD, "d40", "D40", true, false, 150 );
    this.addFixedItem(FIELD, "descripcion", "Descripcion", true, true, 150);
		this.addFixedItem(FIELD, "conciliable_key", "Clave conciliable", true, true, 150);
		this.addFixedItem(VIRTUAL, "example_idinterfaz", "Ejemplo parseo", true, false, 150);
		this.addFixedItem(VIRTUAL, "example_parseo", "Ejemplo Interfaz", true, false, 150);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "BSP_INTERFAZ_BO_FORMATO";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(String zCompany, String zIdformato) throws Exception {
		addFilter("company", zCompany);
//		addFilter("owner", zOwner);
		addFilter("id_formato", zIdformato);
		return read();
	}

	public String getD(int id) throws Exception {
		if (id == 1) return getD1();
		if (id == 2) return getD2();
		if (id == 3) return getD3();
		if (id == 4) return getD4();
		if (id == 5) return getD5();
		if (id == 6) return getD6();
		if (id == 7) return getD7();
		if (id == 8) return getD8();
		if (id == 9) return getD9();
		if (id == 10) return getD10();
		if (id == 11) return getD11();
		if (id == 12) return getD12();
		if (id == 13) return getD13();
		if (id == 14) return getD14();
		if (id == 15) return getD15();
		if (id == 16) return getD16();
		if (id == 17) return getD17();
		if (id == 18) return getD18();
		if (id == 19) return getD19();
		if (id == 20) return getD20();
		if (id == 21) return getD21();
		if (id == 22) return getD22();
		if (id == 23) return getD23();
		if (id == 24) return getD24();
		if (id == 25) return getD25();
		if (id == 26) return getD26();
		if (id == 27) return getD27();
		if (id == 28) return getD28();
		if (id == 29) return getD29();
		if (id == 30) return getD30();
		if (id == 31) return getD31();
		if (id == 32) return getD32();
		if (id == 33) return getD33();
		if (id == 34) return getD34();
		if (id == 35) return getD35();
		if (id == 36) return getD36();
		if (id == 37) return getD37();
		if (id == 38) return getD38();
		if (id == 39) return getD39();
		if (id == 40) return getD40();
		return null;
	}

	public String getColumn(String type) throws Exception {
		if (getD1().equals(type)) return "d1";
		if (getD2().equals(type)) return "d2";
		if (getD3().equals(type)) return "d3";
		if (getD4().equals(type)) return "d4";
		if (getD5().equals(type)) return "d5";
		if (getD6().equals(type)) return "d6";
		if (getD7().equals(type)) return "d7";
		if (getD8().equals(type)) return "d8";
		if (getD9().equals(type)) return "d9";
		if (getD10().equals(type)) return "d10";
		if (getD11().equals(type)) return "d11";
		if (getD12().equals(type)) return "d12";
		if (getD13().equals(type)) return "d13";
		if (getD14().equals(type)) return "d14";
		if (getD15().equals(type)) return "d15";
		if (getD16().equals(type)) return "d16";
		if (getD17().equals(type)) return "d17";
		if (getD18().equals(type)) return "d18";
		if (getD19().equals(type)) return "d19";
		if (getD20().equals(type)) return "d20";
		if (getD21().equals(type)) return "d21";
		if (getD22().equals(type)) return "d22";
		if (getD23().equals(type)) return "d23";
		if (getD24().equals(type)) return "d24";
		if (getD25().equals(type)) return "d25";
		if (getD26().equals(type)) return "d26";
		if (getD27().equals(type)) return "d27";
		if (getD28().equals(type)) return "d28";
		if (getD29().equals(type)) return "d29";
		if (getD30().equals(type)) return "d30";
		if (getD31().equals(type)) return "d31";
		if (getD32().equals(type)) return "d32";
		if (getD33().equals(type)) return "d33";
		if (getD34().equals(type)) return "d34";
		if (getD35().equals(type)) return "d35";
		if (getD36().equals(type)) return "d36";
		if (getD37().equals(type)) return "d37";
		if (getD38().equals(type)) return "d38";
		if (getD39().equals(type)) return "d39";
		if (getD40().equals(type)) return "d40";
		return null;
	}

	HashMap<String, long[]> campos = new HashMap<String, long[]>();

	private long[] getCampo(String name) {
		long[] pos = campos.get(name);
		if (pos == null) {
			pos = new long[100];
			campos.put(name, pos);
		}
		return pos;
	}

	@Override
	public void processUpdate() throws Exception {
		if (pOwner.isNull()) pOwner.setValue(BizUsuario.getUsr().GetUsuario());
		if (pConciliableKey.isNull()||pConciliableKey.equals(IConciliable.BOLETOS)||pConciliableKey.equals(IConciliable.BOLETOS_LARGO)||pConciliableKey.equals(IConciliable.AEROLINEA_BOLETOS)) {
			fillConciliable();
		}
		if (pConciliableKey.isNull()) {
			pConciliableKey.setValue(getD1());
		}
		super.processUpdate();
		JRecords<BizInterfazBO> allInt = new JRecords<BizInterfazBO>(BizInterfazBO.class);
		allInt.addFilter("company", getCompany());
		allInt.addFilter("id_formato", getIdformato());
		JIterator<BizInterfazBO> it = allInt.getStaticIterator();
		while (it.hasMoreElements()) {
			BizInterfazBO interfaz = it.nextElement();
			interfaz.refillConsolidado();
		}
	}
	
	@Override
	public void processInsert() throws Exception {
		if (pOwner.isNull()) pOwner.setValue(BizUsuario.getUsr().GetUsuario());
//		if (pConciliableKey.isNull()) {
//			fillConciliable();
//		}
		if (pConciliableKey.isNull()) {
			pConciliableKey.setValue(getD1());
		}
		super.processInsert();
	}

	public void fillConciliable() throws Exception {
			if (getD1().equals(IConciliable.BOLETOS)||getD1().equals(IConciliable.BOLETOS_LARGO)||getD1().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD1.getValue());
			if (getD2().equals(IConciliable.BOLETOS)||getD2().equals(IConciliable.BOLETOS_LARGO)||getD2().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD2.getValue());
			if (getD3().equals(IConciliable.BOLETOS)||getD3().equals(IConciliable.BOLETOS_LARGO)||getD3().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD3.getValue());
			if (getD4().equals(IConciliable.BOLETOS)||getD4().equals(IConciliable.BOLETOS_LARGO)||getD4().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD4.getValue());
			if (getD5().equals(IConciliable.BOLETOS)||getD5().equals(IConciliable.BOLETOS_LARGO)||getD5().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD5.getValue());
			if (getD6().equals(IConciliable.BOLETOS)||getD6().equals(IConciliable.BOLETOS_LARGO)||getD6().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD6.getValue());
			if (getD7().equals(IConciliable.BOLETOS)||getD7().equals(IConciliable.BOLETOS_LARGO)||getD7().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD7.getValue());
			if (getD8().equals(IConciliable.BOLETOS)||getD8().equals(IConciliable.BOLETOS_LARGO)||getD8().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD8.getValue());
			if (getD9().equals(IConciliable.BOLETOS)||getD9().equals(IConciliable.BOLETOS_LARGO)||getD9().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD9.getValue());
			if (getD10().equals(IConciliable.BOLETOS)||getD10().equals(IConciliable.BOLETOS_LARGO)||getD10().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD10.getValue());
			if (getD11().equals(IConciliable.BOLETOS)||getD11().equals(IConciliable.BOLETOS_LARGO)||getD11().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD11.getValue());
			if (getD12().equals(IConciliable.BOLETOS)||getD12().equals(IConciliable.BOLETOS_LARGO)||getD12().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD12.getValue());
			if (getD13().equals(IConciliable.BOLETOS)||getD13().equals(IConciliable.BOLETOS_LARGO)||getD13().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD13.getValue());
			if (getD14().equals(IConciliable.BOLETOS)||getD14().equals(IConciliable.BOLETOS_LARGO)||getD14().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD14.getValue());
			if (getD15().equals(IConciliable.BOLETOS)||getD15().equals(IConciliable.BOLETOS_LARGO)||getD15().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD15.getValue());
			if (getD16().equals(IConciliable.BOLETOS)||getD16().equals(IConciliable.BOLETOS_LARGO)||getD16().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD16.getValue());
			if (getD17().equals(IConciliable.BOLETOS)||getD17().equals(IConciliable.BOLETOS_LARGO)||getD17().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD17.getValue());
			if (getD18().equals(IConciliable.BOLETOS)||getD18().equals(IConciliable.BOLETOS_LARGO)||getD18().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD18.getValue());
			if (getD19().equals(IConciliable.BOLETOS)||getD19().equals(IConciliable.BOLETOS_LARGO)||getD19().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD19.getValue());
			if (getD20().equals(IConciliable.BOLETOS)||getD20().equals(IConciliable.BOLETOS_LARGO)||getD20().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD20.getValue());
			if (getD21().equals(IConciliable.BOLETOS)||getD21().equals(IConciliable.BOLETOS_LARGO)||getD21().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD21.getValue());
			if (getD22().equals(IConciliable.BOLETOS)||getD22().equals(IConciliable.BOLETOS_LARGO)||getD22().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD22.getValue());
			if (getD23().equals(IConciliable.BOLETOS)||getD23().equals(IConciliable.BOLETOS_LARGO)||getD23().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD23.getValue());
			if (getD24().equals(IConciliable.BOLETOS)||getD24().equals(IConciliable.BOLETOS_LARGO)||getD24().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD24.getValue());
			if (getD25().equals(IConciliable.BOLETOS)||getD25().equals(IConciliable.BOLETOS_LARGO)||getD25().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD25.getValue());
			if (getD26().equals(IConciliable.BOLETOS)||getD26().equals(IConciliable.BOLETOS_LARGO)||getD26().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD26.getValue());
			if (getD27().equals(IConciliable.BOLETOS)||getD27().equals(IConciliable.BOLETOS_LARGO)||getD27().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD27.getValue());
			if (getD28().equals(IConciliable.BOLETOS)||getD28().equals(IConciliable.BOLETOS_LARGO)||getD28().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD28.getValue());
			if (getD29().equals(IConciliable.BOLETOS)||getD29().equals(IConciliable.BOLETOS_LARGO)||getD29().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD29.getValue());
			if (getD30().equals(IConciliable.BOLETOS)||getD30().equals(IConciliable.BOLETOS_LARGO)||getD30().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD30.getValue());
			if (getD31().equals(IConciliable.BOLETOS)||getD31().equals(IConciliable.BOLETOS_LARGO)||getD31().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD31.getValue());
			if (getD32().equals(IConciliable.BOLETOS)||getD32().equals(IConciliable.BOLETOS_LARGO)||getD32().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD32.getValue());
			if (getD33().equals(IConciliable.BOLETOS)||getD33().equals(IConciliable.BOLETOS_LARGO)||getD33().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD33.getValue());
			if (getD34().equals(IConciliable.BOLETOS)||getD34().equals(IConciliable.BOLETOS_LARGO)||getD34().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD34.getValue());
			if (getD35().equals(IConciliable.BOLETOS)||getD35().equals(IConciliable.BOLETOS_LARGO)||getD35().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD35.getValue());
			if (getD36().equals(IConciliable.BOLETOS)||getD36().equals(IConciliable.BOLETOS_LARGO)||getD36().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD36.getValue());
			if (getD37().equals(IConciliable.BOLETOS)||getD37().equals(IConciliable.BOLETOS_LARGO)||getD37().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD37.getValue());
			if (getD38().equals(IConciliable.BOLETOS)||getD38().equals(IConciliable.BOLETOS_LARGO)||getD38().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD38.getValue());
			if (getD39().equals(IConciliable.BOLETOS)||getD39().equals(IConciliable.BOLETOS_LARGO)||getD39().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD39.getValue());
			if (getD40().equals(IConciliable.BOLETOS)||getD40().equals(IConciliable.BOLETOS_LARGO)||getD40().equals(IConciliable.AEROLINEA_BOLETOS))
				pConciliableKey.setValue(pD40.getValue());
		}
	public GregorianCalendar getDate(String dato, GregorianCalendar suggest) {
		Pattern p = Pattern.compile("(\\d){1,2}(-|\\/)(\\d){2}(-|\\/)(\\d){2,4}");
		Matcher m = p.matcher(dato);
		if (!m.matches()) return null;
		GregorianCalendar d = new GregorianCalendar();
		d = (GregorianCalendar) suggest.clone();
		StringTokenizer eval = new StringTokenizer(dato, "-/");
		String d1 = (String) eval.nextElement();
		String d2 = (String) eval.nextElement();
		String d3 = (String) eval.nextElement();
		if (d1 == null || d2 == null) return null;
		if (d3 != null) {
			d.set(Calendar.YEAR, Integer.parseInt(d3));
		}
		d.set(Calendar.MONTH, Integer.parseInt(d2) - 1);
		d.set(Calendar.DAY_OF_MONTH, Integer.parseInt(d1));
		return d;

	}

	public void determinarColumna(int[] candidatos, String[][] learningTable, Date value, String name) {
		int x, y, j;
		for (j = 0; candidatos[j] != -1; j++) {
			y = candidatos[j];
			for (x = 0; x < learningTable[y].length; x++) {
				if (learningTable[y][x] == null) continue;
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(value);
				GregorianCalendar d = getDate(learningTable[y][x], cal);
				if (d == null) continue;
				if (cal.compareTo(d) == 0) {
					long[] pos = getCampo(name);
					pos[x]++;
				}
			}
		}
	}

	public void determinarColumna(int[] candidatos, String[][] learningTable, double value, String name) {
		int x, y, j;
		for (j = 0; candidatos[j] != -1; j++) {
			y = candidatos[j];
			for (x = 0; x < learningTable[y].length; x++) {
				if (learningTable[y][x] == null) continue;
				try {
					long[] pos = getCampo(name);
					if (Math.abs(Double.parseDouble(learningTable[y][x]) - value) <= 0.015) {
						pos[x] += (value == 0 ? 0 : 5);
					} else {
						pos[x] -= 3;
					}
				} catch (Exception e) {
				}
			}
		}
	}

	public void determinarColumna(int[] candidatos, String[][] learningTable, String value, String name) {
		int x, y, j;
		for (j = 0; candidatos[j] != -1; j++) {
			y = candidatos[j];
			if (learningTable[y] == null) continue;
			for (x = 0; x < learningTable[y].length; x++) {
				if (learningTable[y][x] == null) continue;
				long[] pos = getCampo(name);
				if (learningTable[y][x].trim().equalsIgnoreCase(value.trim())) {
					pos[x] += (value.equals("") ? 0 : 5);
				} else {
					pos[x] -= 3;
				}
			}
		}
	}

	public int[] determinarColumna(String[][] learningTable, String value, String name) {
		int x, y, z = 0;
		int[] candidatos = new int[100];
		candidatos[z] = -1;
		for (y = 0; y < learningTable.length; y++) {
			if (learningTable[y] == null) continue;
			for (x = 0; x < learningTable[y].length; x++) {
				if (learningTable[y][x] == null) continue;
				if (learningTable[y][x].trim().equalsIgnoreCase(value.trim())) {
					long[] pos = getCampo(name);
					candidatos[z++] = y;
					candidatos[z] = -1;
					pos[x]++;
				}
			}
		}
		return candidatos;
	}

	private void assignValue(long p, String title) throws Exception {
		if (p == 0) setD1(title);
		if (p == 1) setD2(title);
		if (p == 2) setD3(title);
		if (p == 3) setD4(title);
		if (p == 4) setD5(title);
		if (p == 5) setD6(title);
		if (p == 6) setD7(title);
		if (p == 7) setD8(title);
		if (p == 8) setD9(title);
		if (p == 9) setD10(title);
		if (p == 10) setD11(title);
		if (p == 11) setD12(title);
		if (p == 12) setD13(title);
		if (p == 13) setD14(title);
		if (p == 14) setD15(title);
		if (p == 15) setD16(title);
		if (p == 16) setD17(title);
		if (p == 17) setD18(title);
		if (p == 18) setD19(title);
		if (p == 19) setD20(title);
		if (p == 20) setD21(title);
		if (p == 21) setD22(title);
		if (p == 22) setD23(title);
		if (p == 23) setD24(title);
		if (p == 24) setD25(title);
		if (p == 25) setD26(title);
		if (p == 26) setD27(title);
		if (p == 27) setD28(title);
		if (p == 28) setD29(title);
		if (p == 29) setD30(title);
		if (p == 30) setD31(title);
		if (p == 31) setD32(title);
		if (p == 32) setD33(title);
		if (p == 33) setD34(title);
		if (p == 34) setD35(title);
		if (p == 35) setD36(title);
		if (p == 36) setD37(title);
		if (p == 37) setD38(title);
		if (p == 38) setD39(title);
		if (p == 39) setD40(title);
	}

	public boolean determineColumns() throws Exception {
		int exitos = 0;
		int fracasos = 0;
		Iterator<String> i = campos.keySet().iterator();
		while (i.hasNext()) {
			String key = i.next();
			long[] pos = campos.get(key);
			long max = 0;
			long p = 0;
			for (int v = 0; v < pos.length; v++)
				if (pos[v] > max) {
					max = pos[v];
					p = v;
				}
			if (max > 0) {
				exitos++;
				assignValue(p, key);
			} else {
				fracasos++;
			}
		}
		return true;// exitos > fracasos;
	}
}
