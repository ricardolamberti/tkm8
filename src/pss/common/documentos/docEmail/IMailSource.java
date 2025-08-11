package pss.common.documentos.docEmail;


public interface IMailSource {

	public void onNotifySource(BizDocEmail mail) throws Exception;
	
}
