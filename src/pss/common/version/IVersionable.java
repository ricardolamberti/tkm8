package pss.common.version;

/**
 * Debe ser implementado por los objetos que sean pasibles calcular el delta con el anterior paquete
 * 
 * Por lo general biz
 */
public interface IVersionable  {
  
	public long getIdVersion() throws Exception ;
	public void setIdVersion(long idVersion) throws Exception ;
	public void setDeleted(boolean deleted) throws Exception ;
	public boolean getDeleted() throws Exception ;
	public IVersionGenerator getVersionGenerator() throws Exception ;
	public IEmpaquetable getEmpaquetable()  throws Exception;
	public void deleteAfterPack() throws Exception ;
} 
