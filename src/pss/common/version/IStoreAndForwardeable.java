package pss.common.version;


public interface IStoreAndForwardeable {

	public void beginStoreAndForward()  throws Exception;
	public void endStoreAndForward()  throws Exception;
	public void storeAndForward()  throws Exception;
	public boolean hasStoreAndForward()  throws Exception;
	
	public String getKeyMessage()  throws Exception;
	public String serialize() throws Exception;
	public void unSerialize(String zData) throws Exception;
} 
