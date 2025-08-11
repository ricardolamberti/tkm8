package pss.core.services.fields;

import pss.common.regions.divitions.BizPais;
import pss.common.security.BizUsuario;
import pss.core.tools.JExcepcion;
import pss.core.tools.formatters.JBusinessDataFormatter;
import pss.core.tools.formatters.JRegionalFormatterFactory;

public class JCurrency extends JFloat {

private static final int CANTIDAD_ENTEROS_MAXIMO = 12;

public int iEnteros;
public int iDecimales;
//static private boolean first = true;
//static private String sCurrencyFormat = new String();
//static private String sCurrencyDBFormat = new String();
//static private BizPais oCountry = null;
//static private DecimalFormat CurrencyFormatter =null;
//static private DecimalFormat CurrencyDBFormatter =null;
private boolean simbolo=false;
private boolean simboloAtras=false;
private boolean juntarSimbolo=false;
private String moneda;
private String simboloNuevo="";

public int GetEnteros()  { return iEnteros;}
public int GetDecimales() { return iDecimales;}
public void setSimbolo(boolean value) {
	simbolo=value;
}
public void setSimboloAtras(boolean value) {
	simboloAtras=value;
}
public void setJuntarSimbolo(boolean value) {
	juntarSimbolo=value;
}
public void setSimboloNuevo(String value) {
	simboloNuevo=value;
}
public String getSimboloNuevo() throws Exception {
  	return this.simboloNuevo;
  }
public boolean getSimboloAtras() throws Exception {
  	return this.simboloAtras;
}


//private String currencyId =null;

  public JCurrency(){
//    if (first){
//      init();
//      first=false;
//    }
  }
  public JCurrency( boolean s ) {
  	this.simbolo=s;
  }
  public JCurrency( double zVal ) {
		this();
	  super.setValue( new Double( zVal ) );
  }
  public JCurrency( boolean s, String mon, double val ) {
  	this.simbolo=s;
  	this.moneda=mon;
  	this.setValue(new Double( val ));
  }
  

//  public JCurrency( String moneda, double zVal ) {
//  	this();
//    super.setValue( new Double( zVal ) );
//    this.currencyId=moneda;
//  }

  public void setMoneda(String mon) {
  	this.moneda=mon;
  }

  @Override
	public String getObjectType() { return JObject.JCURRENCY; }

//  private void init(){
//    try{
//      setCountry();
//      setCurrencyFormat();
//      setFormatter();
//
//      iEnteros = CANTIDAD_ENTEROS_MAXIMO;
//      iDecimales = CurrencyFormatter.getMaximumFractionDigits();
//
//      setCurrencyFormatter();
//
//      setDBFormatter();
//    } catch ( Exception e ) { return;  }
//  }


  public int GetSize() {
   return this.GetDecimales() + this.GetEnteros();
  }

//  private void setCurrencyFormat() throws Exception{
//    sCurrencyFormat= oCountry.getCurrencyFormat().toString();
//  }

//  private void setCountry() throws Exception{
////    oCountry = new BizPais();
////    oCountry.Read(BizUsuario.GetGlobal().getPais());
//    oCountry = BizUsuario.getUsr().getObjCountry();
//  }

//  private void setFormatter() throws Exception{
//    DecimalFormatSymbols simbolos =  new DecimalFormatSymbols();
//    CurrencyFormatter = new DecimalFormat(sCurrencyFormat);
//    CurrencyFormatter.setDecimalFormatSymbols(simbolos);
//  }

//  private void setCurrencyFormatter() throws Exception{
//    DecimalFormatSymbols simbolos =  new DecimalFormatSymbols();
//    simbolos.setDecimalSeparator('.');
//    sCurrencyFormat = "###0";
//    if(iDecimales > 0) sCurrencyFormat += ".";
//    for(int i=0;i<iDecimales;i++){
//      sCurrencyFormat = sCurrencyFormat + "0";
//    }
//    CurrencyFormatter = new DecimalFormat(sCurrencyFormat);
//    CurrencyFormatter.setDecimalFormatSymbols(simbolos);
//  }

//  private void setDBFormatter() throws Exception{
//    DecimalFormatSymbols simbolos =  new DecimalFormatSymbols();
//    simbolos.setDecimalSeparator('.');
//    sCurrencyDBFormat = "####";
//    if(iDecimales > 0) sCurrencyDBFormat += ".";
//    for(int i=0;i<iDecimales;i++){
//      sCurrencyDBFormat = sCurrencyDBFormat + "#";
//    }
//    CurrencyDBFormatter = new DecimalFormat(sCurrencyDBFormat);
//    CurrencyDBFormatter.setDecimalFormatSymbols(simbolos);
//
//  }
/*
  este metodo debera habilitarse cuando dispongamos de un editor mas adecuado que el PssEdit
  public String toString() {
    try {
      return this.CurrencyFormatter.format(pVal);
    } catch ( Exception e ) { return "Error"; }
  }
*/

  public void validateSize(JObject zObjeto) throws Exception{
    JCurrency dMaxMonto=new JCurrency();
    dMaxMonto.setValue(Math.pow(10d,GetEnteros())-1);
    if(((JCurrency)zObjeto).getValue()> dMaxMonto.getValue()){
      JExcepcion.SendError("El importe a procesar o un monto total en que participa dicho importe excede limites monetarios del sistema. Monto máximo^= " + dMaxMonto.toFormattedString() );
   }
  }


  /**
   * Answers a <code>String</code> representation of the value this
   * <code>JObject</code> holds, formatted to the user which is going to see it.
   * It does the same as the <code>#toFormattedString()</code> method, except in
   * that it does not invoke the <code>#Pre()</code> method first.
   * <p>
   * This method may be overridden by subclasses to provide appropriate
   * representations, depending on the data type they represent.
   *
   * @return a formatted <code>String</code> representation of the value this
   *         <code>JObject</code> holds
   */
  @Override
	public String toRawFormattedString() throws Exception {
    if ( this.isRawNull() && !this.formatsNullToZero() ) {
      return "";
    }
    JBusinessDataFormatter f = this.getFormatter();
    double d = this.getRawValue();
    if (d==0d) d=0d; // para sacar el -0.00;
    String s =f.formatCurrencyRedondeoSimetrico(d, getPrecision());
    if (simbolo&&!getSimboloAtras()&&this.getRawValue()!=0d) s=getCurrencySymbol(f)+(juntarSimbolo?"":" ")+s;
    if (simbolo&&getSimboloAtras()&&this.getRawValue()!=0d) s=s+getCurrencySymbol(f);
    

    return s;
  }
  
  public String getCurrencySymbol() throws Exception {
	  JBusinessDataFormatter f = this.getFormatter();
	  return this.getCurrencySymbol(f);
  }
  
  private String getCurrencySymbol(JBusinessDataFormatter f) throws Exception {
	  if (!this.getSimboloNuevo().equals(""))
		  return this.getSimboloNuevo();
	  return f.getCurrencySymbol(getCurrencyCode());
  }
  
  private JBusinessDataFormatter getFormatter() throws Exception {
  	return JRegionalFormatterFactory.getBusinessFormatter(this.getBusinessCountry());
  }
  
  private int getDecimales() throws Exception {
  	if (this.hasCustomPrecision()) 
  		return this.getCustomPrecision();
  	else
  		return getFormatter().getCurrencyFractionDigits(getCurrencyCode());

  }
  
  
//  protected boolean isForceRd() throws Exception {
//  	return false;
//  }
//  
  protected boolean formatsNullToZero() {
    return false;
  }
  protected String getLocalCurrency() throws Exception {
    return BizUsuario.getUsr().getLocalCurrency();
  }
	protected BizPais getBusinessCountry() throws Exception {
    return BizUsuario.getUsr().getObjCountry();
	}
  
  public final String getCurrencyCode() throws Exception {
  	String id = this.getCurrencyId();
  	if (id!=null && !id.equals("")) return id;
  	return this.getLocalCurrency();
  }
  
  public String getCurrencyId() throws Exception {
  	return this.moneda;
  }
//  public String toRawString() throws Exception {
//    if ( this.isRawNull() ) return "";
//    JBusinessDataFormatter oFormater = JRegionalFormatterFactory.getAbsoluteBusinessFormatter(this.getBusinessCountry());
//
//    int iPrecision = 0;
//    if (this.hasCustomPrecisionOnlyForTable()) iPrecision = this.getCustomPrecisionOnlyForTable();
//    else if (this.hasCustomPrecision()) iPrecision = this.getCustomPrecision();
//    else iPrecision = oFormater.getCurrencyFractionDigits(getCurrencyId());
//
//    return oFormater.formatNumberToString(this.getRawValue(), iPrecision );
//  }

  public String toInputString() throws Exception {
  	if (this.isNull()) return "";
  	JBusinessDataFormatter f = this.getFormatter();
//  	String s = this.toFormattedString();
  	String s = f.formatCurrencyRedondeoSimetrico(this.getRawValue(), getPrecision());
  	return s.replace(""+f.getGroupingSeparator(), "").replace(""+f.getDecimalSeparator(), ".");
  }

	public static JCurrency getCurrencyField(double value, final String moneda) throws Exception {
		JCurrency c = new JCurrency(value) {
			public String getCurrencyId() {
				return moneda;
			}
		};
		return c;
	}

	public static String format(String moneda, double value) throws Exception {
		return new JCurrency(true, moneda, value).toFormattedString();
	}

	public static String formatRaw(String moneda, double value) throws Exception {
		return new JCurrency(false, moneda, value).toFormattedString();
	}

//	@Override
//	public Object asDataXls() throws Exception {
//    JBusinessDataFormatter f = this.getFormatter();
//    return JTools.rd(this.getRawValue(), getDecimales());
//	}

  public void copyForm(JObject val) throws Exception {
  	if (val==null) return;
  	super.copyForm(val);
  	JCurrency c = (JCurrency) val;
  	this.moneda=c.getCurrencyCode();
  	this.juntarSimbolo=c.juntarSimbolo;
  	this.simbolo=c.simbolo;
  	this.simboloAtras=c.simboloAtras;
  	this.simboloNuevo=c.simboloNuevo;
  }

  public int getPrecision() throws Exception {
  	return this.getDecimales();
  }


}
