package pss.common.version;

import java.util.List;
import java.util.Map;

import pss.common.version.pack.detail.BizPackageDetail;

/**
 * 
 * Debe ser implementado por los objetos que puedan ser  serializados y deserializados
 * 
 */
public interface IEmpaquetable  {
	public void pack(IVersionGenerator idVersion, List<BizPackageDetail> list, Map<String, IVersionGenerator> versionGenerators, boolean full, boolean deleted) throws Exception;
	public void unpack(String xmlContent, boolean isDelete) throws Exception;
	public void unpackTruncate() throws Exception;
	public String uniqueId() throws Exception ;
} 
