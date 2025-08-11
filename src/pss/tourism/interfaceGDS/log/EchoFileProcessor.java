package pss.tourism.interfaceGDS.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Date;

import pss.JPath;
import pss.bsp.bspBusiness.BizCompanyExtraData;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JStringTokenizer;
import pss.tourism.interfaceGDS.FileProcessor;

public class EchoFileProcessor extends FileProcessor {

	protected void internalProcessFile(File file, BufferedReader input, boolean reprocess) throws Exception {
		String line = input.readLine();

		PssLogger.logDebug("Echo Received: " + line);

		JStringTokenizer tok = JCollectionFactory.createStringTokenizer(line, '|');
		tok.skipEmptyTokens(false);

		String company = tok.nextToken().toUpperCase();
		String store = tok.nextToken();
		String id = tok.nextToken();
		String lastfile = tok.nextToken();
		lastfile = lastfile.substring(lastfile.indexOf(".") + 1);
		Date lastecho = new Date(file.lastModified());
		String version = "";
		String lastTransfer = null;
		String host2 = null;
		String lastDir = null;
		String lastServer = null;
		String lastIp = null;

		if (tok.countTokens() > 4)
			version = tok.nextToken();
		if (tok.countTokens() > 5)
			lastTransfer = tok.nextToken();
		if (tok.countTokens() > 6)
			host2 = tok.nextToken();
		if (tok.countTokens() > 7)
			lastDir = tok.nextToken();
		if (tok.countTokens() > 8)
			lastServer = tok.nextToken();
		if (tok.countTokens() > 9)
			lastIp = tok.nextToken();
		boolean update = false;

		BizInterfaceLog log = new BizInterfaceLog();
		log.dontThrowException(true);
		log.addFilter("company", company);
		log.addFilter("store", store);
		log.addFilter("id", id);
//		if (lastDir != null)
//			log.addFilter("last_directory", lastDir);
		if (log.read())
			update = true;

		log.setCompany(company);
		log.setStore(store);
		log.setId(id);
		log.setLastEcho(lastecho);
		if (hasValue(lastfile))
			log.setLastFile(lastfile);
		if (hasValue(lastTransfer))
			log.setLastTransfer(JDateTools.StringToDateTime(lastTransfer));
		if (hasValue(version)) {
			if (JTools.isNumber(version))
				log.setVersion(Long.parseLong(version));
		}

		if (hasValue(host2))
			log.setHost2(host2);

		if (hasValue(lastDir))
			log.setLastDirectory(lastDir);

		if (hasValue(lastIp))
			log.setLastIP(lastIp);

		if (hasValue(lastServer))
			log.setLastServer(lastServer);

		if (update)
			log.update();
		else
			log.insert();

		setSubDirectory("");

		noSubDirectory = true;

		checkVersion();

	}

	private boolean hasValue(String val) {
		if (val == null)
			return false;
		if (val.trim().equals(""))
			return false;
		return true;
	}

	private void checkVersion() throws Exception {
		File files[] = getVerFiles(JPath.PssPathInputOriginal());
		for (int i = 0; i < files.length; i++) {
			String fname = files[i].getName();
			fname = fname.substring(fname.indexOf(".") + 1, fname.indexOf(".") + 1 + 3);
			int ver = Integer.parseInt(fname);
			if (versionInstalledInAllClients(ver)) {
				PssLogger.logDebug("Deleting Version: " + fname);
				files[i].delete();
			}
		}
	}

	private boolean versionInstalledInAllClients(int ver) throws Exception {
		BizInterfaceLog il = new BizInterfaceLog();
		long ctall = il.selectCount();
		il = new BizInterfaceLog();
		il.addFilter("version", ver, ">=");
		long ctver = il.selectCount();
		if (ctall == ctver)
			return true;
		return false;
	}

	private FilenameFilter verfilter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			String fname = name.toLowerCase();
			return fname.startsWith("client") && fname.endsWith(".zip");
		}
	};

	private File[] getVerFiles(String path) throws Exception {
		File dir = new File(path);
		File files[] = dir.listFiles(verfilter);
		return files;
	}

}
