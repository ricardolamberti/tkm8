package pss.core.services.fields;

import java.io.Serializable;


public interface IRecordBean extends Serializable {
	public Serializable getObjInstance() throws Exception ;
	public void setObjInstance(Serializable obj) throws Exception ;
	public void setRecordRef(Class record) throws Exception;
	public void setProperty(String name,Object value) throws Exception;
	public Object getProperty(JObject value,String name) throws Exception;
}
