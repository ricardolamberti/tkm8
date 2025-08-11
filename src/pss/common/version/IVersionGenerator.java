package pss.common.version;
/**
 * Debe ser implementado por todas las clases que lleven el ultimo numero de version empaquetado.
 *  En forecourt fcrtStore y fcrtCompanyVersion
 *
 */
public interface IVersionGenerator {
	public long getIdVersionGenerator() throws Exception ;
	public long getIdVersionGenerator(IVersionGenerator versionParent) throws Exception ;
	public void incrementVersion() throws Exception;
	public String getKeyVersion() throws Exception;
	public IEmpaquetable getStartedVersion()  throws Exception;
} 
