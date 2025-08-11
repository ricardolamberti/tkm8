package pss.core.services.fields;

/**
 * An object which holds a <code>JBDs</code> object.<br>
 * It uses objects of class <code>JBDs</code> to store the value in its
 * <code>pVal</code> variable.<br>
 *
 * @author Leonardo Pronzolino-Yannino
 * @version 1.0.0
 */

import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class JObjBDs<TRecord extends JRecord> extends JObject<JRecords<TRecord>> {

  @SuppressWarnings("unchecked")
	public JRecords<TRecord> getValue() throws Exception  { preset(); return (JRecords<TRecord>)getObjectValue(); }
  @SuppressWarnings("unchecked")
	public JRecords<TRecord> getRawValue() throws Exception  { return (JRecords<TRecord>)getObjectValue(); }

  public JObjBDs() {
  }

  String filter;

  public boolean useRawValue() {
  	return true;
  }


  public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public JObjBDs( JRecords<TRecord> zVal ) {
    super.setValue( zVal );
  	zVal.setPropAsoc(this);
  }

  public void setValue( JRecords<TRecord> zVal ) {
    super.setValue( zVal );
    if (zVal!=null)
    	zVal.setPropAsoc(this);
  }

  
  @SuppressWarnings("unchecked")
	@Override
	public void setValue( String zVal ) throws Exception {
  	if (zVal.equals("") ) return;
    super.setValue( getObjectClass().newInstance() );
    ((JRecords<TRecord>)getObjectValue()).unSerialize(zVal);
  }

  @Override
	public String getObjectType() { return JObject.JBDS; }
  @Override
	public Class getObjectClass() { return JRecords.class; }

	public boolean isNull() throws Exception {
		preset();
		return getObjectValue() == null || (((JRecords)getObjectValue()).isStatic() && ((JRecords)getObjectValue()).getStaticItems().size()==0);
	}
}

