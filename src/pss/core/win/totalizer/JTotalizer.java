package pss.core.win.totalizer;

import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.winUI.lists.JWinList;

public class JTotalizer {

  public static final String OPER_SUM	   = "SUM";
  public static final String OPER_STATICSUM     = "SSUM";
  public static final String OPER_CURRSTATICSUM = "CURR_SSUM";
  public static final String OPER_CURRSUM       = "CURR_SUM";
  public static final String OPER_COUNT	 = "COUNT";
  public static final String OPER_MAX	   = "MAX";
  public static final String OPER_AVG	   = "AVG";
  public static final String OPER_VALUE	 = "VALUE";
  public static final String OPER_PERCENTAGE    = "PERCENTAGE";

  private JWinList	   oWinList;

  public JTotalizer(JWinList zWinList) {
    oWinList = zWinList;
  }

  public class Properties {
    String     column;
    String     value1    = null;
    String     value2    = null;
    String     operation = JTotalizer.OPER_SUM;
    JObject<?> value     = null;
    int	align     = -1;
    int	decimals  = 2;
    String	moneda  = null;

    public Properties(String column, String operation, int align) {
      super();
      this.column = column;
      this.operation = operation;
      this.align = align;
    }

    public Properties(String column, String operation, String texto, int align) {
      super();
      this.column = column;
      this.value = new JString(texto);
      this.operation = operation;
      this.align = align;
    }
    
    public Properties(String column, String operation, JObject v, int align) {
      super();
      this.column = column;
      this.value = v;
      this.operation = operation;
      this.align = align;
    }

    public Properties(String column, String operation, String texto, int align, int decimals) {
      super();
      this.column = column;
      this.value = new JString(texto);
      this.operation = operation;
      this.align = align;
      this.decimals = decimals;
    }

    public Properties(String column, String value1, String value2, String operation, String texto, int align, int decimals) {
      super();
      this.column = column;
      this.value = new JString(texto);
      this.operation = operation;
      this.align = align;
      this.decimals = decimals;
      this.value1 = value1;
      this.value2 = value2;
    }

    public JObject getValue() throws Exception {
      if (this.operation.equals(JTotalizer.OPER_SUM))
      	value = new JFloat(JTools.rd(selectSum(), this.decimals));
      else if (this.operation.equals(JTotalizer.OPER_COUNT))
      	value = new JLong(selectCount());
      else if (this.operation.equals(JTotalizer.OPER_MAX))
      	value = new JString(selectMax());
      else if (this.operation.equals(JTotalizer.OPER_AVG))
      	value = new JFloat(JTools.rd(selectAvg(), this.decimals));
      else if (this.operation.equals(JTotalizer.OPER_CURRSUM)) {
      	JCurrency curr = new JCurrency(JTools.rd(selectSum(), this.decimals));
      	curr.setSimbolo(true);
      	curr.setMoneda(this.moneda);
      	value = curr;
      } else if (this.operation.equals(JTotalizer.OPER_STATICSUM)) {
      	value = new JFloat(this.selectStaticSum(column));
    	} else if (this.operation.equals(JTotalizer.OPER_CURRSTATICSUM)) {
				JCurrency curr = new JCurrency(JTools.rd( selectStaticSum(column), this.decimals) ); 
				curr.setSimbolo(true);
				curr.setJuntarSimbolo(true);
				curr.setSimboloNuevo(getMonedaIfAreEquals(column));
				value = curr;
    	}

      
      return value;
      	
    }

    private double selectAvg() throws Exception {
      return oWinList.getWins().getRecords().selectAvg(column);
    }

    private double selectSum() throws Exception {
      return oWinList.getWins().getRecords().selectSum(column);
    }

  public double selectStaticSum(String campo) throws Exception {
    double suma = 0d;
    JIterator<JRecord> iter = oWinList.getWins().getRecords().getStaticIterator();
    while (iter.hasMoreElements()) {
    	JRecord r = iter.nextElement();
    	suma += ((JFloat) r.getProp(campo)).getValue();
     }
     return JTools.rd(suma);
   }

   private long selectCount() throws Exception {
  	 return oWinList.getWins().getRecords().selectCount();
   }

    private String selectMax() throws Exception {
      return oWinList.getWins().getRecords().selectMax(column);
    }

    public void setMoneda(String v) {
      this.moneda=v;
    }
    public String getColumn() {
      return column;
    }

    public String getValue1() {
      return value1;
    }

    public String getValue2() {
      return value2;
    }

    public String getOperation() {
      return operation;
    }

    public int getDecimals() {
      return decimals;
    }

    public boolean hasAlignment() {
      return align != -1;
    }

    public int getAlignment() {
      return align;
    }
  }

  private JMap<String, Properties> columns;

  private JMap<String, Properties> getLinks() {
    if (columns == null)
      columns = JCollectionFactory.createMap();
    return columns;
  }
  
  public boolean hasAny() throws Exception {
  	return !this.getLinks().isEmpty();
  }

  public Properties addTotalizer(String fieldInList, String oper, int align) {
  	return this.addTotalizer(fieldInList, oper, align, 2);
  }

  public Properties addTotalizer(String fieldInList, String oper, int align, int decimals) {
  	Properties p = new Properties(fieldInList, oper, "", align, decimals);
    this.getLinks().addElement(fieldInList, p);
    return p;
  }


  public Properties addTotalizer(String fieldInList, String val1, String val2, String oper, int align, int decimals) {
  	Properties p = new Properties(fieldInList, val1, val2, oper, "", align, decimals);
  	this.getLinks().addElement(fieldInList, p);
  	return p;
  }

  public Properties addTotalizerText(String fieldInList, String Text, int align) {
  	Properties p = new Properties(fieldInList, JTotalizer.OPER_VALUE, Text, align);
    this.getLinks().addElement(fieldInList, p);
    return p;
  }
  
  public Properties addTotalizer(String fieldInList, JObject v, int align) {
  	Properties p = new Properties(fieldInList, JTotalizer.OPER_VALUE, v, align);
    this.getLinks().addElement(fieldInList, p);
    return p;
  }

  public Properties getProp(String fieldInList) {
    return getLinks().getElement(fieldInList);
  }

  public String getOperation(String fieldInList) {
    return getLinks().getElement(fieldInList).operation;
  }

  public JObject<?> getValue(String fieldInList) throws Exception {
    Properties oProp = getLinks().getElement(fieldInList);
    if (oProp == null)
      return null;
    return oProp.getValue();
  }

  public int getAlignment(String fieldInList) {
    return getLinks().getElement(fieldInList).align;
  }
  
  
  public String getMonedaIfAreEquals(String campo) throws Exception {
	    String mon = "";
	    String oldmon = "";
	    JIterator<JRecord> iter = oWinList.getWins().getRecords().getStaticIterator();
	    while (iter.hasMoreElements()) {
	    	JRecord r = iter.nextElement();
	    	if (r.getProp(campo) instanceof JCurrency) {
	    		mon = ((JCurrency) r.getProp(campo)).getCurrencySymbol();
	    		if (oldmon.equals("")) oldmon=mon; 
	    		if (!oldmon.equals(mon)) return "";
	    	}
	     }
	    return mon;
	   }
  

}
