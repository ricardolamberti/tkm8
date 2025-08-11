package pss.core.services.fields;

import javax.servlet.ServletContext;

public interface IContext {
	public void setContext( ServletContext obj);
	public void setUser( String user);

}
