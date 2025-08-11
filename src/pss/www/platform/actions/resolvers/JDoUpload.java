package pss.www.platform.actions.resolvers;

import pss.www.platform.actions.results.JWebActionResult;

public class JDoUpload extends JBasicWebActionResolver {
	@Override
	protected boolean isAjax() {
		return true;
	} /*
		 * public Map act(Redirector redirector, SourceResolver resolver, Map
		 * objectModel, String source, Parameters par) throws Exception { Request
		 * request = ObjectModelHelper.getRequest(objectModel); Part filePart =
		 * (Part) request.get("uploaded_file"); if (filePart==null) return
		 * Collections.EMPTY_MAP; File file = ((PartOnDisk)filePart).getFile();
		 * getLogger().debug("Uploaded file = " + file.getCanonicalPath()); // here
		 * you can open an InputStream on the file or whatever // you may also want
		 * to delete the file after using it return Collections.EMPTY_MAP; }
		 */

	@Override
	protected JWebActionResult perform() throws Throwable {
		this.addObjectToResult("upload", "upload");
		return super.perform();
	}

	@Override
	protected String getBaseActionName() {
		return "upload";
	}
}