package pss.common.documentos;

import pss.core.services.records.JRecord;

public interface IDocumentSource {
	
	public JRecord createSource(BizDocum doc) throws Exception;
	
}
