package pss.common.layout;


public class JFieldReq {

  JFieldInterface oInterface;
  private Object source1;
  private Object source2;
  public String sector;
  public String id;
//  JArray params = null;
  private String param1;
  private String param2;
//  BizLayout oLayout = null;

  public JFieldReq() {}

  public JFieldReq(JFieldInterface zInterfase, String zSector, Object zSource1) {
  	this(zInterfase, zSector, zSource1, null);
  }

  public JFieldReq(JFieldInterface zInterfase, String zSector, Object zSource1, Object zSource2) {
    source1 = zSource1;
    source2 = zSource2;
    sector = zSector;
    oInterface = zInterfase;
  }


//  public BizLayout getLayout() { return oLayout; }
  public String getId() { return id; }

  public void setId(String value) {id = value;}
  public void setParam1(String value) {param1 = value;}
  public void setParam2(String value) {param2 = value;}
  public void setSource1(Object value) {source1 = value;}
  public void setSource2(Object value) {source2 = value;}
  public JFieldInterface getInterface() { return oInterface; }
  public JFieldReq with(String zId ) throws Exception { id = zId; param1=null; param2=null; return this; }
  public JFieldReq with(String zId, String p1 ) throws Exception { id = zId; param1=p1; param2=null; return this; }

  public int getInt(String zId ) throws Exception { return this.getInt(zId, null, null);}
  public int getInt(String zId, String p1) throws Exception { return this.getInt(zId, p1, null);}
  public int getInt(String zId, String p1, String p2) throws Exception {
  	Object obj = this.get(zId, p1, p2);
  	if (obj instanceof String)
  		return Integer.parseInt((String)obj);
  	else
  		return ((Integer)obj).intValue();
  }

  public long getLong(String zId ) throws Exception { return this.getLong(zId, null, null);}
  public long getLong(String zId, String p1) throws Exception { return this.getLong(zId, p1, null);}
  public long getLong(String zId, String p1, String p2) throws Exception {
  	Object obj = this.get(zId, p1, p2);
  	if (obj instanceof String)
  		return Long.parseLong((String)obj);
  	else
  		return ((Long)obj).longValue();
  }

  public boolean getBool(String zId ) throws Exception { return this.getBool(zId, null, null);}
  public boolean getBool(String zId, String p1) throws Exception { return this.getBool(zId, p1, null);}
  public boolean getBool(String zId, String p1, String p2) throws Exception {
  	Object obj = this.get(zId, p1, p2);
  	if (obj instanceof String)
  		return Boolean.parseBoolean((String)obj);
  	else
  		return ((Boolean)obj).booleanValue();
  }

  public String getString(String zId ) throws Exception { return this.getString(zId, null, null);}
  public String getString(String zId, String p ) throws Exception { return this.getString(zId, p, null);}
  public String getString(String zId, String p1, String p2 ) throws Exception { 
  	Object obj = this.get(zId, p1, p2);
  	if (obj instanceof String)
  		return (String)obj;
  	else
  		return String.valueOf(obj);
  }

  public double getDouble(String zId ) throws Exception { return this.getDouble(zId, null, null);}
  public double getDouble(String zId, String p ) throws Exception { return this.getDouble(zId, p, null);}
  public double getDouble(String zId, String p1, String p2 ) throws Exception {
  	Object obj = this.get(zId, p1, p2);
  	if (obj instanceof String)
  		return Double.parseDouble((String)obj);
  	else
  		return ((Double)obj).doubleValue();
  }
  
  public Object get() throws Exception { return get(id, param1, null);}
  public Object get(String zId ) throws Exception { return get(zId, null, null);}
  public Object get(String zId, String p ) throws Exception { return get(zId, p, null);}
  public Object get(String zId, String p1, String p2 ) throws Exception { id = zId; param1=p1; param2=p2;return oInterface.getField(this);}


  public Object getSource1() {
  	return source1;
  }
  public Object getSource2() {
  	return source2;
  }
  public String getParam1() {
  	return param1;
  }

  public String getParam2() {
  	return param2;
  }
  
  public String findParam2(String def) {
  	if (!this.hasParam2()) return def;
  	return param2;
  }


  public boolean hasParam2() {
  	return param2!=null && !param2.isEmpty();
  }
  
/*    if (params==null) return null;
    return i >= params.size() ? null : (String) params.getElementAt(i);
  }

  public void addParam(String param) {
    if( param == null )  return;
    if( params == null ) params = JCollectionFactory.createList();
    params.addElement(param);
  }
  public void clearParams() {  if ( params != null ) params.removeAllElements(); }
*/

}
