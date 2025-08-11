package pss.core.services.fields;

import java.io.Serializable;

import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.w3c.dom.Element;

import pss.core.tools.PssLogger;

public class JObject<T extends Serializable> extends JBaseObject {

	public final static String JAUTONUMERICO = "JAUTONUMERICO";
	public final static String JBDS = "JBDS";
	public final static String JBD = "JBD";
	public final static String JBOOLEAN = "JBOOLEAN";
	public final static String JCONTAINER = "JCONTAINER";
	public final static String JCURRENCY = "JCURRENCY";
	public final static String JDATE = "JDATE";
	public final static String JDATETIME = "JDATETIME";
	public final static String JINTERVALDATETIME = "JINTERVALDATETIME";
	public final static String JINTERVALDATE = "JINTERVALDATE";
	public final static String JFLOAT = "JFLOAT";
	public final static String JHOUR = "JHOUR";
	public final static String JINTEGER = "JINT";
	public final static String JLONG = "JLONG";
	public final static String JLOWERCASE = "JLOWERCASE";
	public final static String JMULTIPLE = "JMULTIPLE";
	public final static String JNOTTRIM = "NOT_TRIM";
	public final static String JOBJECT = "JOBJECT";
	public final static String JPASSWORD = "JPASSWORD";
	public final static String JSTRING = "JCHAR";
	public final static String JUNSIGNED = "JUNSIGNED";
	public final static String JUPPER_NOSPACE = "JUPPER_NOSPACE";
	public final static String JUPPER_ONLYLETTERORDIGIT = "JUPPER_ONLYLETTERORDIGIT";
	public final static String JUPPERCASE = "JUPPERCASE";
	public final static String JXMLSTRING = "JXMLSTRING";
	public final static String JGEOPOSITION = "JGEOPOSITION";
	public final static String JBLOB = "JBLOB";
	public final static String JCOLOUR = "JCOLOUR";
	public final static String JIMAGE = "JIMAGE";
	public final static String JHTML = "JHTML";
	public final static String JWINACTION = "JWINACTION";

	private byte m_bEstablecida = 0;
	private boolean modifiedOnServer=false;


	protected IRecordBean hyb;
	Class<IRecordBean> clase;
	Class<IRecordBean> claseRecs;
  public Class<IRecordBean> getClase() {
		return clase;
	}
  public Class<IRecordBean> getClaseRecs() {
		return claseRecs;
	}
  public void setHibernate(IRecordBean zVO,String zName,Class<IRecordBean> zclase,Class<IRecordBean> zclaseRecs) {
  	hyb = zVO;
  	name = zName;
  	clase = zclase;
		claseRecs = zclaseRecs;
	}
  public void setHibernate(IRecordBean zVO,String zName) {
  	hyb = zVO;
  	name = zName;
  }
  public boolean isHibernate() {
  	return hyb!=null;
  }
  

	private String name;
	private String uniqueId;
	


	public Object pVal;
	private JScript script = null;
	private JScript scriptOnChangeScript = null;

	public Object getInternalVal() {
		return pVal;
	}

	public void unsetEstablish() {
		m_bEstablecida = 0;
	}

	public Object asObject() throws Exception {
		preset();
		return getObjectValue();
	}

	public String asPrintealbleObject() throws Exception {
		return String.valueOf(asRawObject());
	}

	// --------------------------------------------------------------------------
	// Manejo de OBJECTs
	// --------------------------------------------------------------------------
	public Object asRawObject() throws Exception {
		return getObjectValue();
	}

	public int compareTo(JObject zObject) {
		int ret = -999;

		try {
			if (isInteger())
				ret = ((Integer) getObjectValue()).compareTo(new Integer(zObject.toString()));
			else if (isFloat())
				ret = ((Float) getObjectValue()).compareTo(new Float(zObject.toString()));
			else if (isLong())
				ret = ((Long) getObjectValue()).compareTo(new Long(zObject.toString()));
			else if (isDate())
				ret = ((java.util.Date) getObjectValue()).compareTo(new java.util.Date(zObject.toString()));
			else if (isString())
				ret = ((String) getObjectValue()).compareTo(zObject.toString());
			else if (isBoolean()) ret = getObjectValue().toString().compareTo(zObject.toString());
		}
		catch (Exception ex) {}

		return ret;
	}

	// --------------------------------------------------------------------------
	// Metodos para comparar objetos
	// --------------------------------------------------------------------------
	@Override
	public boolean equals(Object zObject) {
		try {
			// return zObject!=null && zObject.getClass()==this.getClass() &&
			// ((JObject)zObject).AsString().equals(this.AsString());
			return zObject != null && zObject.getClass() == this.getClass() && ((JObject) zObject).toString().equals(this.toString());
		}
		catch (Exception ex) {
			return false;
		}
	}

	public int getCustomPrecision() throws Exception {
		return 0;
	}

	// --------------------------------------------------------------------------
	// Tipos de Clases
	// --------------------------------------------------------------------------
	public Class getObjectClass() {
		return Object.class;
	}

	public String getObjectType() {
		return JOBJECT;
	}

	public Object getObjectValue() {
		try {
			if (hyb!=null) 
				return hyb.getProperty(this,name);
	   	if (isRecord()) 
				if (pVal instanceof String) return null;
			} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return pVal;
	}

	public String getRel() throws Exception {
		return null;
	}

	public boolean hasCustomPrecision() throws Exception {
		return false;
	} 

	public boolean hasValue() throws Exception {
		return isRawNotNull();
	}

	public boolean isBoolean() throws Exception {
		return getObjectType().equals(JBOOLEAN);
	}

	public boolean isRecord() throws Exception {
		return getObjectType().equals(JBD);
	}

	public boolean isRecords() throws Exception {
		return getObjectType().equals(JBDS);
	}

	public boolean isBaseRecord() throws Exception {
		return this.isRecord() || this.isRecords();
	}

	public boolean isGeoPosition() throws Exception {
		return getObjectType().equals(JGEOPOSITION);
	}

	// --------------------------------------------------------------------------
	// Metodos para chequear los tipos de datos
	// --------------------------------------------------------------------------
	public boolean isCurrency() throws Exception {
		return getObjectType().equals(JCURRENCY);
	}

	public boolean isDate() throws Exception {
		return getObjectType().equals(JDATE) || getObjectType().equals(JDATETIME);
	}
	public boolean isDateTime() throws Exception {
		return getObjectType().equals(JDATETIME);
	}
	public boolean isIntervalDateTime() throws Exception {
		return getObjectType().equals(JINTERVALDATETIME);
	}
	public boolean isIntervalDate() throws Exception {
		return getObjectType().equals(JINTERVALDATE);
	}
	public boolean isEstablecida() {
		return m_bEstablecida == 1;
	}

	public boolean isFloat() throws Exception {
		return getObjectType().equals(JFLOAT);
	}

	public boolean isHour() throws Exception {
		return getObjectType().equals(JHOUR);
	}

	public boolean isInteger() throws Exception {
		return getObjectType().equals(JINTEGER);
	}

	public boolean isLong() throws Exception {
		return getObjectType().equals(JLONG);
	}

	public boolean isPassword() throws Exception {
		return getObjectType().equals(JPASSWORD);
	}

	public boolean isNotNull() throws Exception {
		return !isNull();
	}

	public boolean isNull() throws Exception {
		preset();
		return getObjectValue() == null;
	}

	// --------------------------------------------------------------------------
	// Manejo de STRINGs
	// --------------------------------------------------------------------------
	/*
	 * Sobre los AsString y los AsDBString, etc.
	 * ----------------------------------------- #toString() --> para obtener la
	 * representación interna del valor como String; esta es la representación
	 * usada por la base de datos, y nunca debe llegar al usuario;
	 * 
	 * #toStringOrig() --> igual que #toString(), pero sin aplicar el método
	 * #Pre();
	 * 
	 * #toFormattedString() --> para obtener la representación del valor que debe
	 * ver el usuario; ésta sale de aplicar regional formatters en los casos que
	 * corresponda
	 * 
	 * #toFormattedStringOrig() --> igual que #toFormattedString(), pero sin
	 * aplicar el método #Pre();
	 * 
	 */

	public boolean isRawNotNull() throws Exception {
		return !isRawNull();
	}

	public boolean isRawNull() throws Exception {
		return getObjectValue() == null;
	}

	public boolean isString() throws Exception {
		return getObjectType().equals(JSTRING);
	}
	public boolean isHtml() throws Exception {
		return getObjectType().equals(JHTML);
	}
	public boolean isMultiple() throws Exception {
		return getObjectType().equals(JMULTIPLE);
	}

	//	public void setFilter(String f) throws Exception {
//	}
	public void preset() throws Exception {
	}
	public void setFilter(String f) throws Exception {
	}
	public boolean forcePresetForDefault() throws Exception {
		return false;
	}
	public void forceDefault() throws Exception {
	}

	@Override
	public String serialize() throws Exception {
		return this.toString();
	}

	@Override
	public boolean serializeAsXML() {
		return false;
	}

	// --------------------------------------------------------------------------
	// Manejo de NULLs
	// --------------------------------------------------------------------------
	public void setNull() {
		this.setValue((T) null);
	}

	public void setEstablecida(boolean value) {
		if (value)
			m_bEstablecida = 1;
		else m_bEstablecida = 0;
	}

	public void setValue(Object zVal) {
		m_bEstablecida = 1;
		if (isHibernate()) {
			try {
				if (hyb!=null) 
					hyb.setProperty(name, zVal);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		} else
			pVal = zVal;
	}
	
	public Object getHybValue() {
			if (isHibernate()) {
				try {
					if (hyb!=null) {
						Object obj = hyb.getObjInstance(); 
						return obj;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}			
			}
			return pVal;
		}

	public void setValue(String zVal) throws Exception {
		m_bEstablecida = 1;
		if (isHibernate()) {
			try {
				if (hyb!=null) {
					Object obj = hyb.getObjInstance(); 
					PropertyAccessor myAccessor = PropertyAccessorFactory.forBeanPropertyAccess(obj);
					myAccessor.setPropertyValue(name,zVal);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}			
		} else
			pVal = zVal;
	}
	
	public void copy(JObject val) throws Exception {
		if (val.isNull()) 
			this.setNull();
		else
			this.setValue(val.asRawObject());
	}

	public void setValueFormUI(String zVal) throws Exception {
		if ((zVal.equals(",")&&isFloat())||zVal.equals("null")) this.setValue(null);
		else this.setValue(zVal);
	}

	/**
	 * Answers a <code>String</code> representation of the value this
	 * <code>JObject</code> holds, formatted to the user which is going to see
	 * it.
	 * 
	 * @return a formatted <code>String</code> representation of the value this
	 *         <code>JObject</code> holds
	 */
	public final String toFormattedString() throws Exception {
		preset();
		return toRawFormattedString();
	}

	/**
	 * Answers a <code>String</code> representation of the value this
	 * <code>JObject</code> holds, formatted to the user which is going to see
	 * it. It does the same as the <code>#toFormattedString()</code> method,
	 * except in that it does not invoke the <code>#Pre()</code> method first.
	 * <p>
	 * This method may be overridden by subclasses to provide appropriate
	 * representations, depending on the data type they represent.
	 * 
	 * @return a formatted <code>String</code> representation of the value this
	 *         <code>JObject</code> holds
	 */
	public String toRawFormattedString() throws Exception {
		return toRawString();
	}

	/**
	 * Answers the value this <code>JObject</code> holds as a
	 * <code>String</code>. It does the same as the <code>#toString()</code>
	 * method, except in that it does not invoke the <code>#Pre()</code> method
	 * first.
	 * <p>
	 * This method may be overridden by subclasses to provide appropriate
	 * representations, depending on the data type they represent.
	 * 
	 * @return the value this <code>JObject</code> holds as a
	 *         <code>String</code>
	 */
	public String toRawString() throws Exception {
		if (!this.hasValue()) return "";
		return this.getObjectValue().toString();
	}

	/**
	 * Answers the value this <code>JObject</code> holds as a
	 * <code>String</code>. The String this method answers is the
	 * representation which will be used as the database value. It should never be
	 * visible to the user.<br>
	 * To get the String representation visible to the user call the method
	 * #toFormattedString().
	 * 
	 * @return the value this <code>JObject</code> holds as a
	 *         <code>String</code>
	 * @throws RuntimeException
	 *           in case any axception occurrs when invoking either the
	 *           <code>#Pre()</code> method or the <code>#toStringOrig()</code>
	 *           method; a <code>RuntimeException</code> is thrown instead of
	 *           the occurred exception because of the method contract of
	 *           <code>Object#toString()</code>, which does not thorws any
	 *           checked exception
	 */
	@Override
	public final String toString() {
		try {
			preset();
			return toRawString();
		}
		catch (Exception ex) {
			PssLogger.logError(ex);
			throw new RuntimeException(ex.getMessage());
		}
	}

	@Override
	public void unserialize(Element e) throws Exception {
		String value = e.getAttribute("value");
		this.setValue(value);
	}

	public String toInputString() throws Exception {
		return this.toFormattedString();
	}

	public final JScript getObjScript() throws Exception {
		if (script == null) script = this.getScript();
		return script;
	}
	
	public final JScript getObjOnChangeScript() throws Exception {
		if (scriptOnChangeScript == null) scriptOnChangeScript = this.getOnChangeScript();
		return scriptOnChangeScript;
	}
	
	public JScript getOnChangeScript() throws Exception {
		return scriptOnChangeScript;
	}
	public JScript getScript() throws Exception {
		return script;
	}
	
	public void setScript(JScript zScript) throws Exception {
		 script=zScript;
	}
	public void setScriptOnChangeScript(JScript zScript) throws Exception {
		 scriptOnChangeScript=zScript;
	}

	public boolean hasScript() throws Exception {
		return script!=null || scriptOnChangeScript!=null;
	}
	public boolean isModifiedOnServer() {
		return modifiedOnServer;
	}
	public void setModifiedOnServer(boolean modifiedOnServer) {
		this.modifiedOnServer = modifiedOnServer;
	}
  public boolean hasHtml() {
    return false;
  }

  public String toHtml() throws Exception {
    return this.toRawFormattedString();
  }
  
  public void copyForm(JObject val) throws Exception {
  	if (val==null) return;
  	this.setValue(val.asRawObject());
  }

  public int getPrecision() throws Exception {
  	return -1;
  }
  
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

}
