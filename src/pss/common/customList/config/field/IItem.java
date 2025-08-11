package  pss.common.customList.config.field;

public interface IItem {
  public void execSubir(final long pos) throws Exception;
  public void execBajar(final long pos) throws Exception;
	public String getDescripcion() throws Exception;
	public String getCompany() throws Exception;
	public long getListId() throws Exception;
	public long getSecuencia() throws Exception;
	public IItem getClon(String company,long listId) throws Exception;
}
