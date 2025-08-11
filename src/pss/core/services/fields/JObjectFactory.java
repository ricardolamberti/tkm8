package pss.core.services.fields;

import pss.core.tools.JExcepcion;

/*
 * User: rasensio
 * Project: MAIN
 * Created on Apr 26, 2004
 */

public class JObjectFactory {

  /**
   * JObjectFactory's constructor
   */
  private JObjectFactory() {
    super();
  }

  public static JObject<?> createObject(String type, String value) throws Exception {
    try {
    	JObject<?> obj = virtualCreate(type);
    	obj.setValue(value);
    	return obj;
//      if (type.equals(JObject.JSTRING)) {
//        object = new JString(value);
//      } else if (type.equals(JObject.JINTEGER)) {
//        object = new JInteger(Integer.parseInt(value));
//      } else if (type.equals(JObject.JLONG)) {
//        object = new JLong(Long.parseLong(value));
//      } else if (type.equals(JObject.JBOOLEAN)) {
//        object = new JBoolean(Boolean.valueOf(value).booleanValue());
//      } else if (type.equals(JObject.JGEOPOSITION)) {
//          object = new JGeoPosition(value);
//        }
    } catch (Exception e) {
      JExcepcion.SendError("Error convirtiendo a String");
    }
  	return null;
  }
  
	public static JObject virtualCreate(String type) throws Exception {
		if (type.equals(JObject.JBOOLEAN)) return new JBoolean();
		if (type.equals(JObject.JCURRENCY)) return new JCurrency();
		if (type.equals(JObject.JHTML)) return new JHtml();
		if (type.equals(JObject.JDATE)) return new JDate();
		if (type.equals(JObject.JDATETIME)) return new JDateTime();
		if (type.equals(JObject.JINTERVALDATETIME)) return new JIntervalDateTime();
		if (type.equals(JObject.JINTERVALDATE)) return new JIntervalDate();
		if (type.equals(JObject.JFLOAT)) return new JFloat();
		if (type.equals(JObject.JHOUR)) return new JHour();
		if (type.equals(JObject.JINTEGER)) return new JInteger();
		if (type.equals(JObject.JLONG)) return new JLong();
		if (type.equals(JObject.JSTRING)) return new JString();
		if (type.equals(JObject.JPASSWORD)) return new JPassword();
		if (type.equals(JObject.JSTRING)) return new JString();
		if (type.equals(JObject.JMULTIPLE)) return new JMultiple();
		if (type.equals(JObject.JCOLOUR)) return new JString();
		if (type.equals(JObject.JIMAGE)) return new JImage();
		if (type.equals(JObject.JMULTIPLE)) return new JMultiple();
		if (type.equals(JObject.JBDS)) return new JObjBDs();
		if (type.equals(JObject.JBD)) return new JObjBD();
		return null;
	}


}
