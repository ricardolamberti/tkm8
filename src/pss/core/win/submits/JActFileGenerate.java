package pss.core.win.submits;

import pss.JPath;
import pss.common.security.BizUsuario;
import pss.core.win.JBaseWin;

public class JActFileGenerate extends JActSubmit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8671110613347096283L;
	private String fileName;
//	private String pathProvider;
	
//	public void setFileName(String value) {
//		this.fileName=value;
//	}
	public String getFileName() {
		return fileName!=null?fileName:"document";
	}
	
	public String getProvider() {
		try {
			if (BizUsuario.getUsr()!=null) 
				return BizUsuario.getUsr().getObjBusiness().getSitemapFolder();
		} catch (Exception e) {
		}
		return "business_resource";
	}

	public String getExtension() throws Exception {
		return ".pdf";
	}
	public String getPath() throws Exception {
		return JPath.PssPathTempFiles();
		//return fileName;
	}
//	public String getFileName() {
//		return fileName!=null?fileName:"document";
//	}
	
//	public String getExtension() throws Exception {
//		return ".html";
//	}
//	public String getPath() throws Exception {
//		return JPath.PssPathTempFiles();
//	}

	public JActFileGenerate(JBaseWin zResult, int zActionId) {
		super(zResult, zActionId);
	}
	public JActFileGenerate(JBaseWin zResult) {
		super(zResult);
	}
	
//	public String getFullFileName() throws Exception {
//		return this.getFileName()+this.getExtension();
//	}
//	public String getAbsoluteFileName() throws Exception {
//		return this.getPath()+"/"+getFullFileName();
//	}
//	
//	public void checkFile() throws Exception {
//		JTools.MakeDirectory(JPath.PssPathTempFiles());
//	  File file = new File(getAbsoluteFileName());
//    file.delete();
//	}
	
	@Override
	public void submit() throws Exception {
//		this.checkFile();
//		this.fileName = this.generate(this.getAbsoluteFileName());
		this.fileName = this.generate();
		if (fileName==null) throw new Exception("Reporte vacio");
	}
	
//	public void generate(String fileName) throws Exception {}
	public String generate() throws Exception { return null;}

  @Override
	public boolean isOnlySubmit() { return true;}

 // public boolean isHistoryAction() throws Exception {return true;};
}
