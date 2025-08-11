package  pss.common.components;

public interface JSourceInterface {

	public void loadSource(long asiento) throws Exception;
	public String getSourceDescription() throws Exception;
	public String getSourceMoneda() throws Exception;

}
