package pss.bsp.consolidador.consolidacionOracle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.consolidador.IConciliable;
import pss.bsp.consolidador.IConsolidador;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.tourism.pnr.BizPNRTicket;

public class BizConsolidacionOracle extends JRecord {

	public final static String VISION_LIBRE = "1";
  private JString pCompany = new JString();
  private JString pOwner = new JString();
  private JLong pIdpdf = new JLong();
  private JString pTipo = new JString();
  private JLong pLinea = new JLong();
  private JString pStatus = new JString();
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
  private JString pObservaciones = new JString();
  private JString pNroBoleto = new JString();
  private JDate pFecha = new JDate();
  private JBoolean pOnlyTickets = new JBoolean();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setOwner(String zValue) throws Exception {    pOwner.setValue(zValue);  }
  public String getOwner() throws Exception {     return pOwner.getValue();  }
  public boolean isNullOwner() throws Exception { return  pOwner.isNull(); } 
  public void setNullToOwner() throws Exception {  pOwner.setNull(); } 
  public void setIdpdf(long zValue) throws Exception {    pIdpdf.setValue(zValue);  }
  public long getIdpdf() throws Exception {     return pIdpdf.getValue();  }
  public boolean isNullIdpdf() throws Exception { return  pIdpdf.isNull(); } 
  public void setNullToIdpdf() throws Exception {  pIdpdf.setNull(); } 
  public void setTipo(String zValue) throws Exception {    pTipo.setValue(zValue);  }
  public String getTipo() throws Exception {     return pTipo.getValue();  }
  public boolean isNullTipo() throws Exception { return  pTipo.isNull(); } 
  public void setNullToTipo() throws Exception {  pTipo.setNull(); } 
  public void setLinea(long zValue) throws Exception {    pLinea.setValue(zValue);  }
  public long getLinea() throws Exception {     return pLinea.getValue();  }
  public boolean isNullLinea() throws Exception { return  pLinea.isNull(); } 
  public void setNullToLinea() throws Exception {  pLinea.setNull(); } 
  public void setStatus(String zValue) throws Exception {    pStatus.setValue(zValue);  }
  public String getStatus() throws Exception {     return pStatus.getValue();  }
  public boolean isNullStatus() throws Exception { return  pStatus.isNull(); } 
  public void setNullToStatus() throws Exception {  pStatus.setNull(); } 
  public void setD1(String zValue) throws Exception {    pD1.setValue(zValue);  }
  public String getD1() throws Exception {     return pD1.getValue();  }
  public boolean isNullD1() throws Exception { return  pD1.isNull(); } 
  public void setNullToD1() throws Exception {  pD1.setNull(); } 
  public void setD2(String zValue) throws Exception {    pD2.setValue(zValue);  }
  public String getD2() throws Exception {     return pD2.getValue();  }
  public boolean isNullD2() throws Exception { return  pD2.isNull(); } 
  public void setNullToD2() throws Exception {  pD2.setNull(); } 
  public void setD3(String zValue) throws Exception {    pD3.setValue(zValue);  }
  public String getD3() throws Exception {     return pD3.getValue();  }
  public boolean isNullD3() throws Exception { return  pD3.isNull(); } 
  public void setNullToD3() throws Exception {  pD3.setNull(); } 
  public void setD4(String zValue) throws Exception {    pD4.setValue(zValue);  }
  public String getD4() throws Exception {     return pD4.getValue();  }
  public boolean isNullD4() throws Exception { return  pD4.isNull(); } 
  public void setNullToD4() throws Exception {  pD4.setNull(); } 
  public void setD5(String zValue) throws Exception {    pD5.setValue(zValue);  }
  public String getD5() throws Exception {     return pD5.getValue();  }
  public boolean isNullD5() throws Exception { return  pD5.isNull(); } 
  public void setNullToD5() throws Exception {  pD5.setNull(); } 
  public void setD6(String zValue) throws Exception {    pD6.setValue(zValue);  }
  public String getD6() throws Exception {     return pD6.getValue();  }
  public boolean isNullD6() throws Exception { return  pD6.isNull(); } 
  public void setNullToD6() throws Exception {  pD6.setNull(); } 
  public void setD7(String zValue) throws Exception {    pD7.setValue(zValue);  }
  public String getD7() throws Exception {     return pD7.getValue();  }
  public boolean isNullD7() throws Exception { return  pD7.isNull(); } 
  public void setNullToD7() throws Exception {  pD7.setNull(); } 
  public void setD8(String zValue) throws Exception {    pD8.setValue(zValue);  }
  public String getD8() throws Exception {     return pD8.getValue();  }
  public boolean isNullD8() throws Exception { return  pD8.isNull(); } 
  public void setNullToD8() throws Exception {  pD8.setNull(); } 
  public void setD9(String zValue) throws Exception {    pD9.setValue(zValue);  }
  public String getD9() throws Exception {     return pD9.getValue();  }
  public boolean isNullD9() throws Exception { return  pD9.isNull(); } 
  public void setNullToD9() throws Exception {  pD9.setNull(); } 
  public void setD10(String zValue) throws Exception {    pD10.setValue(zValue);  }
  public String getD10() throws Exception {     return pD10.getValue();  }
  public boolean isNullD10() throws Exception { return  pD10.isNull(); } 
  public void setNullToD10() throws Exception {  pD10.setNull(); } 
  public void setD11(String zValue) throws Exception {    pD11.setValue(zValue);  }
  public String getD11() throws Exception {     return pD11.getValue();  }
  public boolean isNullD11() throws Exception { return  pD11.isNull(); } 
  public void setNullToD11() throws Exception {  pD11.setNull(); } 
  public void setD12(String zValue) throws Exception {    pD12.setValue(zValue);  }
  public String getD12() throws Exception {     return pD12.getValue();  }
  public boolean isNullD12() throws Exception { return  pD12.isNull(); } 
  public void setNullToD12() throws Exception {  pD12.setNull(); } 
  public void setD13(String zValue) throws Exception {    pD13.setValue(zValue);  }
  public String getD13() throws Exception {     return pD13.getValue();  }
  public boolean isNullD13() throws Exception { return  pD13.isNull(); } 
  public void setNullToD13() throws Exception {  pD13.setNull(); } 
  public void setD14(String zValue) throws Exception {    pD14.setValue(zValue);  }
  public String getD14() throws Exception {     return pD14.getValue();  }
  public boolean isNullD14() throws Exception { return  pD14.isNull(); } 
  public void setNullToD14() throws Exception {  pD14.setNull(); } 
  public void setD15(String zValue) throws Exception {    pD15.setValue(zValue);  }
  public String getD15() throws Exception {     return pD15.getValue();  }
  public boolean isNullD15() throws Exception { return  pD15.isNull(); } 
  public void setNullToD15() throws Exception {  pD15.setNull(); } 
  public void setD16(String zValue) throws Exception {    pD16.setValue(zValue);  }
  public String getD16() throws Exception {     return pD16.getValue();  }
  public boolean isNullD16() throws Exception { return  pD16.isNull(); } 
  public void setNullToD16() throws Exception {  pD16.setNull(); } 
  public void setD17(String zValue) throws Exception {    pD17.setValue(zValue);  }
  public String getD17() throws Exception {     return pD17.getValue();  }
  public boolean isNullD17() throws Exception { return  pD17.isNull(); } 
  public void setNullToD17() throws Exception {  pD17.setNull(); } 
  public void setD18(String zValue) throws Exception {    pD18.setValue(zValue);  }
  public String getD18() throws Exception {     return pD18.getValue();  }
  public boolean isNullD18() throws Exception { return  pD18.isNull(); } 
  public void setNullToD18() throws Exception {  pD18.setNull(); } 
  public void setD19(String zValue) throws Exception {    pD19.setValue(zValue);  }
  public String getD19() throws Exception {     return pD19.getValue();  }
  public boolean isNullD19() throws Exception { return  pD19.isNull(); } 
  public void setNullToD19() throws Exception {  pD19.setNull(); } 
  public void setD20(String zValue) throws Exception {    pD20.setValue(zValue);  }
  public String getD20() throws Exception {     return pD20.getValue();  }
  public boolean isNullD20() throws Exception { return  pD20.isNull(); } 
  public void setNullToD20() throws Exception {  pD20.setNull(); } 
  public void setObservaciones(String zValue) throws Exception {    pObservaciones.setValue(zValue);  }
  public String getObservaciones() throws Exception {     return pObservaciones.getValue();  }
  public boolean isNullObservaciones() throws Exception { return  pObservaciones.isNull(); } 
  public void setNullToObservaciones() throws Exception {  pObservaciones.setNull(); } 
  public void setNroBoleto(String zValue) throws Exception {    pNroBoleto.setValue(zValue);  }
  public String getNroBoleto() throws Exception {     return pNroBoleto.getValue();  }
  public boolean isNullNroBoleto() throws Exception { return  pNroBoleto.isNull(); } 
  public void setNullToNroBoleto() throws Exception {  pNroBoleto.setNull(); } 
  public void setFecha(Date zValue) throws Exception {    pFecha.setValue(zValue);  }
  public Date getFecha() throws Exception {     return pFecha.getValue();  }
  

  /**
   * Constructor de la Clase
   */
  public BizConsolidacionOracle() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "owner", pOwner );
    this.addItem( "idpdf", pIdpdf );
    this.addItem( "tipo", pTipo );
    this.addItem( "linea", pLinea );
    this.addItem( "status", pStatus );
    this.addItem( "d1", pD1 );
    this.addItem( "d2", pD2 );
    this.addItem( "d3", pD3 );
    this.addItem( "d4", pD4 );
    this.addItem( "d5", pD5 );
    this.addItem( "d6", pD6 );
    this.addItem( "d7", pD7 );
    this.addItem( "d8", pD8 );
    this.addItem( "d9", pD9 );
    this.addItem( "d10", pD10 );
    this.addItem( "d11", pD11 );
    this.addItem( "d12", pD12 );
    this.addItem( "d13", pD13 );
    this.addItem( "d14", pD14 );
    this.addItem( "d15", pD15 );
    this.addItem( "d16", pD16 );
    this.addItem( "d17", pD17 );
    this.addItem( "d18", pD18 );
    this.addItem( "d19", pD19 );
    this.addItem( "d20", pD20 );
    this.addItem( "observaciones", pObservaciones );
    this.addItem( "nro_boleto", pNroBoleto );
    this.addItem( "fecha", pFecha );
    this.addItem( "only_tickets", pOnlyTickets );
      }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 50 );
    this.addFixedItem( KEY, "idpdf", "Idpdf", true, true, 300 );
    this.addFixedItem( KEY, "linea", "Linea", true, true, 18 );
    this.addFixedItem( KEY, "tipo", "Tipo", true, true, 50 );
    this.addFixedItem( FIELD, "owner", "Owner", true, true, 50 );
    this.addFixedItem( FIELD, "status", "Status", true, true, 50 );
    this.addFixedItem( FIELD, "d1", "D1", true, false, 150 );
    this.addFixedItem( FIELD, "d2", "D2", true, false, 150 );
    this.addFixedItem( FIELD, "d3", "D3", true, false, 150 );
    this.addFixedItem( FIELD, "d4", "D4", true, false, 150 );
    this.addFixedItem( FIELD, "d5", "D5", true, false, 150 );
    this.addFixedItem( FIELD, "d6", "D6", true, false, 150 );
    this.addFixedItem( FIELD, "d7", "D7", true, false, 150 );
    this.addFixedItem( FIELD, "d8", "D8", true, false, 150 );
    this.addFixedItem( FIELD, "d9", "D9", true, false, 150 );
    this.addFixedItem( FIELD, "d10", "D10", true, false, 150 );
    this.addFixedItem( FIELD, "d11", "D11", true, false, 150 );
    this.addFixedItem( FIELD, "d12", "D12", true, false, 150 );
    this.addFixedItem( FIELD, "d13", "D13", true, false, 150 );
    this.addFixedItem( FIELD, "d14", "D14", true, false, 150 );
    this.addFixedItem( FIELD, "d15", "D15", true, false, 150 );
    this.addFixedItem( FIELD, "d16", "D16", true, false, 150 );
    this.addFixedItem( FIELD, "d17", "D17", true, false, 150 );
    this.addFixedItem( FIELD, "d18", "D18", true, false, 150 );
    this.addFixedItem( FIELD, "d19", "D19", true, false, 150 );
    this.addFixedItem( FIELD, "d20", "D20", true, false, 150 );
    this.addFixedItem( FIELD, "observaciones", "Observaciones", true, false, 250 );
    this.addFixedItem( FIELD, "nro_boleto", "nro_boleto", true, false, 50);
    this.addFixedItem( FIELD, "fecha", "fecha", true, false, 18);
    this.addFixedItem( VIRTUAL, "only_tickets", "only tickets", true, false, 1);
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_CONCILIACION_ORACLE"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( String zCompany, String zIdpdf, long zLinea ) throws Exception { 
    addFilter( "company",  zCompany ); 
//    addFilter( "owner",  zOwner ); 
    addFilter( "idpdf",  zIdpdf ); 
    addFilter( "linea",  zLinea ); 
    return read(); 
  } 

  BizPNRTicket objBoleto;
  public BizPNRTicket getObjBoleto() throws Exception {
  	if (pNroBoleto.isNull()) return null;
  	if (objBoleto!=null) return objBoleto;
  	BizPNRTicket pnr = new BizPNRTicket();
  	pnr.dontThrowException(true);
  	if (!pnr.ReadByBoleto(getCompany(), getNroBoleto())) return null;
  	return objBoleto=pnr;
  }
  public boolean hasBoleto() throws Exception {
  	return (getObjBoleto()!=null);
  }
  
	public List<String> getFieldName(int id) throws Exception {
		List<String> lista = new ArrayList<String>();
		if (id==1) lista.add(BizBSPUser.getUsrBSP().getBspConsola().getConfigView().getConciliableKey());
		if (id==2) lista.add( IConciliable.FECHA ) ;
		if (id==3) lista.add( IConciliable.OPERACION);
		if (id==4) lista.add( IConciliable.ID_AEROLINEA);
		if (id==5) lista.add( IConciliable.AEROLINEA);
		if (id==6) lista.add( IConciliable.TARIFA);
		if (id==7) lista.add( IConciliable.CONTADO);
		if (id==8) lista.add( IConciliable.TARJETA);
		if (id==7) lista.add( IConciliable.CONTADO_BRUTO);
		if (id==8) lista.add( IConciliable.TARJETA_BRUTO);
		if (id==9) lista.add( IConciliable.TIPO_RUTA);
		if (id==10) lista.add( IConciliable.BASE_IMPONIBLE);
		if (id==11) lista.add( IConciliable.COMISION);
		if (id==11) lista.add( IConciliable.COMISION_INV);
		if (id==12) lista.add( IConciliable.COMISION_OVER);
		if (id==12) lista.add( IConciliable.COMISION_OVER_INV);
		if (id==12) lista.add( IConciliable.COMISION_ACUM);
		if (id==12) lista.add( IConciliable.COMISION_ACUM_INV);
		if (id==13) lista.add( IConciliable.COMISION_PORC);
		if (id==14) lista.add( IConciliable.IMP_SOBRE_COMISION);
		if (id==14) lista.add( IConciliable.IMP_SOBRE_COMISION_INV);
		if (id==15) lista.add( IConciliable.IMPUESTO_1);
		if (id==15) lista.add( IConciliable.IMPUESTO_ACUM);
		if (id==16) lista.add( IConciliable.IMPUESTO_2);
		if (id==17) lista.add( IConciliable.NETO);
		if (id==17) lista.add( IConciliable.TARIFA_SIN_COMISION);
		if (id==18) lista.add( IConciliable.TOTALAPAGAR);
		if (id==18) lista.add( IConciliable.TOTAL);
		if (id==19) lista.add( IConciliable.TIPO_TARJETA);
		if (id==20) lista.add( IConciliable.NUMERO_TARJETA);
//	  if (id==21) lista.add( IConciliable.IDCLIENTE);
//	  if (id==22) lista.add( IConciliable.CLIENTE);
		return lista;
	}
	public void setD(String campo,String data) throws Exception {
		if (campo.equals(IConciliable.BOLETOS)) setD1(data);
		if (campo.equals(IConciliable.BOLETOS_LARGO)) setD1(data);
		if (campo.equals(IConciliable.AEROLINEA_BOLETOS)) setD1(data);
		if (campo.equals(IConciliable.FECHA)) setD2(data);
		if (campo.equals(IConciliable.OPERACION)) setD3(data);
		if (campo.equals(IConciliable.ID_AEROLINEA)) setD4(data);
		if (campo.equals(IConciliable.AEROLINEA)) setD5(data);
		if (campo.equals(IConciliable.TARIFA)) setD6(data);
		if (campo.equals(IConciliable.CONTADO)) setD7(data);
		if (campo.equals(IConciliable.TARJETA)) setD8(data);
		if (campo.equals(IConciliable.CONTADO_BRUTO)) setD7(data);
		if (campo.equals(IConciliable.TARJETA_BRUTO)) setD8(data);
		if (campo.equals(IConciliable.TIPO_RUTA)) setD9(data);
		if (campo.equals(IConciliable.BASE_IMPONIBLE)) setD10(data);
		if (campo.equals(IConciliable.COMISION)) setD11(data);
		if (campo.equals(IConciliable.COMISION_OVER)) setD12(data);
		if (campo.equals(IConciliable.COMISION_ACUM)) setD12(data);
		if (campo.equals(IConciliable.COMISION_ACUM_INV)) setD12(data);
		if (campo.equals(IConciliable.COMISION_PORC)) setD13(data);
		if (campo.equals(IConciliable.IMP_SOBRE_COMISION)) setD14(data);
		if (campo.equals(IConciliable.COMISION_INV)) setD11(data);
		if (campo.equals(IConciliable.COMISION_OVER_INV)) setD12(data);
		if (campo.equals(IConciliable.IMP_SOBRE_COMISION_INV)) setD14(data);
		if (campo.equals(IConciliable.IMPUESTO_1)) setD15(data);
		if (campo.equals(IConciliable.IMPUESTO_ACUM)) setD15(data);
		if (campo.equals(IConciliable.IMPUESTO_2)) setD16(data);
		if (campo.equals(IConciliable.NETO)) setD17(data);
		if (campo.equals(IConciliable.TARIFA_SIN_COMISION)) setD17(data);
		if (campo.equals(IConciliable.TOTALAPAGAR)) setD18(data);
		if (campo.equals(IConciliable.TOTAL)) setD18(data);
		if (campo.equals(IConciliable.TIPO_TARJETA)) setD19(data);
		if (campo.equals(IConciliable.NUMERO_TARJETA)) setD20(data);
//		if (campo.equals(IConciliable.IDCLIENTE)) setD21(data);
//		if (campo.equals(IConciliable.CLIENTE)) setD22(data);
  }
	
	public static JRecords<BizVirtual> ObtenerResultadosAnalisis() throws Exception {
		JRecords<BizVirtual> oBDs = JRecords.createVirtualBDs();
		oBDs.addItem(JRecord.virtualBD(IConsolidador.OK, "Correctos", 943));
		oBDs.addItem(JRecord.virtualBD(IConsolidador.DISTINCT, "Con diferencias", 939));
		return oBDs;
	}	
	public static JRecords<BizVirtual> ObtenerResultadosConsolidacion() throws Exception {
		JRecords<BizVirtual> oBDs = JRecords.createVirtualBDs();
		oBDs.addItem(JRecord.virtualBD(IConsolidador.OK, "Correctos", 943));
		oBDs.addItem(JRecord.virtualBD(IConsolidador.DISTINCT, "Con diferencias", 940));
		oBDs.addItem(JRecord.virtualBD(IConsolidador.ONLY_BSP, "Solo en BSP", 939));
		oBDs.addItem(JRecord.virtualBD(IConsolidador.ONLY_BO, "Solo en BO/PNR", 941));
		return oBDs;
	}
	
  public String getFieldForeground(String zColName) throws Exception {
  	String valor = getPropAsStringNoExec(zColName);
  	if (getStatus().equals(IConsolidador.ONLY_BSP))	return "FF0000"; 
  	if (getStatus().equals(IConsolidador.ONLY_BO))	return "FF0000"; 
   	if (valor.startsWith("[")) return "0000FF";
  	if (valor.indexOf(" <> ")!=-1) return "FF0000";
  	return "000000";
  }
	@Override
	public void processInsert() throws Exception {
		super.insert();
	}

}
