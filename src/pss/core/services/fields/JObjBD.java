package pss.core.services.fields;

/**
 * An object which holds a <code>JBDs</code> object.<br>
 * It uses objects of class <code>JBDs</code> to store the value in its
 * <code>pVal</code> variable.<br>
 *
 * @author Leonardo Pronzolino-Yannino
 * @version 1.0.0
 */

import pss.core.services.records.JBaseRecord;
import pss.core.services.records.JRecord;

public class JObjBD extends JObject {

	public JRecord getValue() throws Exception  { preset(); return (JRecord)getObjectValue(); }
	public JRecord getRawValue() throws Exception  { return (JRecord)getObjectValue(); }

  public JObjBD() {
  }
  
  public JObjBD( JRecord zVal ) {
    super.setValue( zVal );
  }

  public void setValue( JRecord zVal ) {
    super.setValue( zVal );
  }

  @Override
	public String getObjectType() { return JObject.JBD; }
  
  @Override
	public Class getObjectClass() { return JBaseRecord.class; }

  
}

