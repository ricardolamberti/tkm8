package pss.common.version.generator;

import java.util.HashMap;
import java.util.Map;

import pss.common.version.IVersionGenerator;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

/**
 * Supongamos que existe un generado de version eess y uno compania
 * 
 *  si uno se pone a generar version desde una eess deben ir todos los cambio que se hicieron en la eess, pero tambien los de la compania
 *  pero los datos de esta, tambien deben ir a otras estaciones.
 *  
 *  Esta clase en combinacion con JGenerateVersion y la implementacion de pack en JMMWin resuelven esto. 
 *  Usan una tabla vrs_version_generator, que para cada configuracion de eess llevan cual fue la ultima configuracion de compania.
 *  de tal modo que al generar un paquete de la eess agregan los cambios de compania desde la ultima vez qeu se sincronizo hasta ahora
 *  y luego actualizan este campo.
 */

public class BizVersionGenerators extends JRecord {

  private JString pParentVrs = new JString();
  private JString pChildVrs = new JString();
  private JLong pVersionId = new JLong();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

 
  public void setParentVrs(String zValue) throws Exception {    pParentVrs.setValue(zValue);  }
  public String getParentVrs() throws Exception {     return pParentVrs.getValue();  }
  public boolean isNullParentVrs() throws Exception { return  pParentVrs.isNull(); } 
  public void setNullToParentVrs() throws Exception {  pParentVrs.setNull(); } 
  public void setChildVrs(String zValue) throws Exception {    pChildVrs.setValue(zValue);  }
  public String getChildVrs() throws Exception {     return pChildVrs.getValue();  }
  public boolean isNullChildVrs() throws Exception { return  pChildVrs.isNull(); } 
  public void setNullToChildVrs() throws Exception {  pChildVrs.setNull(); } 
  public void setVersionId(long zValue) throws Exception {    pVersionId.setValue(zValue);  }
  public long getVersionId() throws Exception {     return pVersionId.getValue();  }
  public boolean isNullVersionId() throws Exception { return  pVersionId.isNull(); } 
  public void setNullToVersionId() throws Exception {  pVersionId.setNull(); } 

  /**
   * Constructor de la Clase
   */
  public BizVersionGenerators() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "parent_vrs", pParentVrs );
    this.addItem( "child_vrs", pChildVrs );
    this.addItem( "version_id", pVersionId );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "parent_vrs", "Vrs Padre", true, true, 50 );
    this.addFixedItem( KEY, "child_vrs", "Vrs hijo", true, true, 50 );
    this.addFixedItem( FIELD, "version_id", "version", true, true, 15 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "vrs_version_generators"; }

  static Map <String,Long> cache;
  
  static long getVersionCache(String p,String c) throws Exception {
  	if (cache==null) 	cache = new HashMap<String,Long>();
  	if (cache.get(p+"_"+c)!=null) return cache.get(p+"_"+c).longValue();
		BizVersionGenerators gen = new BizVersionGenerators();
		gen.read(p, c);
		cache.put(p+"_"+c, gen.getVersionId());
		return gen.getVersionId();
  	
  }
  public static void clearCache() {
  	cache=null;
  }
  
  public static long getIDVersion(IVersionGenerator versionParent, IVersionGenerator child) throws Exception {
		if (versionParent.getKeyVersion().equals(child.getKeyVersion())) return versionParent.getIdVersionGenerator();
		return getVersionCache(versionParent.getKeyVersion(), child.getKeyVersion());
  }
  
  public static void setIDVersion(IVersionGenerator versionParent, IVersionGenerator child, long idVersion) throws Exception {
		if (versionParent.getKeyVersion().equals(child.getKeyVersion())) return;
		BizVersionGenerators gen = new BizVersionGenerators();
		gen.read(versionParent.getKeyVersion(), child.getKeyVersion());
		if (gen.getVersionId()>=idVersion) return;
		gen.setVersionId(idVersion+1);
		gen.processUpdate();
  }

  /**
   * Default read() method
   */
  public boolean read( String  parentVrs,String  childVrs ) throws Exception { 
    addFilter( "parent_vrs",  parentVrs ); 
    addFilter( "child_vrs",  childVrs ); 
    dontThrowException(true);
    if (read()) return true;
    setParentVrs(parentVrs);
    setChildVrs(childVrs);
    setVersionId(0);
		processInsert();
    return true;
  } 
}

