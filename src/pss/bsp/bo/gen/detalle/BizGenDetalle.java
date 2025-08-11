package  pss.bsp.bo.gen.detalle;

import java.util.Date;

import pss.bsp.bo.data.BizBODetalle;
import pss.bsp.bo.gen.cabecera.BizGenCabecera;
import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.consolidador.IConciliable;
import pss.bsp.parseo.IFormato;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;

public class BizGenDetalle extends JRecord implements IConciliable {

  private JString pCompany = new JString();
  private JString pOwner = new JString();
  private JLong pIdinterfaz = new JLong();
  private JLong pLinea = new JLong();
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


 BizGenCabecera objHeader;
  
	public BizGenCabecera getHeader() throws Exception {
		if (objHeader!=null) return objHeader;
		BizGenCabecera c = new BizGenCabecera();
		c.read(this.getCompany(), this.getIdinterfaz());
		return (objHeader=c);
	}
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
  public void setIdinterfaz(long zValue) throws Exception {    pIdinterfaz.setValue(zValue);  }
  public long getIdinterfaz() throws Exception {     return pIdinterfaz.getValue();  }
  public boolean isNullIdinterfaz() throws Exception { return  pIdinterfaz.isNull(); } 
  public void setNullToIdinterfaz() throws Exception {  pIdinterfaz.setNull(); } 
  public void setLinea(long zValue) throws Exception {    pLinea.setValue(zValue);  }
  public long getLinea() throws Exception {     return pLinea.getValue();  }
  public boolean isNullLinea() throws Exception { return  pLinea.isNull(); } 
  public void setNullToLinea() throws Exception {  pLinea.setNull(); } 
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

  /**
   * Constructor de la Clase
   */
  public BizGenDetalle() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "owner", pOwner );
    this.addItem( "idInterfaz", pIdinterfaz );
    this.addItem( "linea", pLinea );
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
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 50 );
    this.addFixedItem( KEY, "idInterfaz", "Idinterfaz", true, true, 18 );
    this.addFixedItem( KEY, "linea", "Linea", true, true, 18 );
    this.addFixedItem( FIELD, "owner", "Owner", true, true, 50 );
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
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_INTERFAZ_BO_GEN_DETALLE"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( String zCompany, long zIdinterfaz, long zLinea ) throws Exception { 
    addFilter( "company",  zCompany ); 
//    addFilter( "owner",  zOwner ); 
    addFilter( "idInterfaz",  zIdinterfaz ); 
    addFilter( "linea",  zLinea ); 
    return read(); 
  }
  
  IFormato formato;
  

	public IFormato getFormato() throws Exception {
		if (formato!=null) return formato;
		return formato = getHeader().getObjFormato();
	}


	public void setFormato(IFormato formato) {
		this.formato = formato;
	}


	public Date getDateValue(String field, boolean check) throws Exception {
		try {
			IFormato f =  getFormato();
			String prop = f.getColumn(field);
			return JDateTools.StringToDate(this.getProp(prop).toString());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void processInsert() throws Exception {
		if (pOwner.isNull()) pOwner.setValue(BizUsuario.getUsr().GetUsuario());
		super.processInsert();
		
		processBODetalle();
	}

	@Override
	public void processUpdate() throws Exception {
		if (pOwner.isNull()) pOwner.setValue(BizUsuario.getUsr().GetUsuario());
		super.processUpdate();
	}

	public void processBODetalle() throws Exception {
		BizBODetalle detalle = new BizBODetalle();
		detalle.setCompany(this.getCompany());
		detalle.setLinea(this.getLinea());
		detalle.setIdinterfaz(this.getIdinterfaz());
		detalle.setOwner(this.getOwner());
		detalle.setAerolinea(this.getStringValue(IConciliable.AEROLINEA, true));
		detalle.setBaseImponible(this.getDoubleValue(IConciliable.BASE_IMPONIBLE, null, true));
		detalle.setBoleto(this.getStringValue(IConciliable.BOLETOS, true));
		detalle.setComision(this.getDoubleValue(IConciliable.COMISION, null, true));
		detalle.setComisionOver(this.getDoubleValue(IConciliable.COMISION_OVER, null, true));
		detalle.setContado(this.getDoubleValue(IConciliable.CONTADO, null, true));
		detalle.setImpSobrecom(this.getDoubleValue(IConciliable.IMP_SOBRE_COMISION, null, true));
		detalle.setImpuesto1(this.getDoubleValue(IConciliable.IMPUESTO_1, null, true));
		detalle.setImpuesto2(this.getDoubleValue(IConciliable.IMPUESTO_2, null, true));
		detalle.setBaseImponible(this.getDoubleValue(IConciliable.BASE_IMPONIBLE, null, true));
		detalle.setTarifa(this.getDoubleValue(IConciliable.TARIFA, null, true));
		detalle.setTarjeta(this.getDoubleValue(IConciliable.TARJETA, null, true));
		detalle.setObservaciones(this.getStringValue(IConciliable.OBSERVACION, true));
		detalle.setOperacion(this.getStringValue(IConciliable.OPERACION, true));
		detalle.setTipoRuta(this.getStringValue(IConciliable.TIPO_RUTA, true));
		detalle.setTipoTarjeta(this.getStringValue(IConciliable.TIPO_TARJETA, true));
		detalle.setCliente(this.getStringValue(IConciliable.CLIENTE, true));
		detalle.setIdCliente(this.getStringValue(IConciliable.IDCLIENTE, true));
		detalle.setNumeroTarjeta(this.getStringValue(IConciliable.NUMERO_TARJETA, true));
		detalle.setFecha(this.getDateValue(IConciliable.FECHA, true));
		detalle.processUpdateOrInsertWithCheck();
	
	}
	private double getFactorBO() throws Exception {
		double fx =BizBSPUser.getUsrBSP().getBspConsola().getConfigView().getFactorBo();
		if (fx==0) return 1;
		return fx;
	}

	public Double getDoubleValue(String field, String moneda, boolean check) throws Exception {
		IFormato f =  getFormato();
		String prop = f.getColumn(field);
		if (prop==null) return 0.0;
		if (this.getProp(prop).toString().equals("")) 
			return 0.0;
		JObject value = this.getProp(prop);
		String svalue =JTools.getNumberEmbeddedWithDecSigned(value.toString());
		if (svalue.equals("")) return 0.0;
		return JTools.getDoubleNumberEmbedded(svalue)*getFactorBO();
	}

	public Long getLongValue(String field, boolean check) throws Exception {
		IFormato f =  getFormato();
		String prop = f.getColumn(field);
		if (prop==null) return new Long(0);
		if (this.getProp(prop).toString().equals("")) 
			return new Long(0);
		JObject value = this.getProp(prop);
		String svalue =JTools.getNumberEmbeddedWithDecSigned(value.toString());
		int pos=svalue.indexOf(".");
		if (pos!=-1)
			svalue=svalue.substring(0,pos);
		if (svalue.equals("")) svalue = "0";

		if (field.equals(IConciliable.AEROLINEA)) return Long.parseLong(svalue);
		if (field.equals(IConciliable.ID_AEROLINEA)) return Long.parseLong(svalue);
		
		return Long.parseLong(svalue)*(long)getFactorBO();
	}
	
	public String getStringValue(String field, boolean check) throws Exception {
		IFormato f =  getFormato();
		String prop = f.getColumn(field);
		if (prop==null) return "";
		return this.getProp(prop).toString();
	}
	@Override
	public String getSituation(IConciliable other, double precision) throws Exception {
			return "";
	}

	@Override
	public String getContrato() throws Exception {
		return "";
	}
}
