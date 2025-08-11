package pss.common.version;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pss.common.version.generator.BizVersionGenerators;
import pss.common.version.pack.BizPackage;
import pss.common.version.pack.detail.BizPackageDetail;

/**
 * 
 * manejador del proceso de empaquetado de version, dado un JVersion
 * 
 */
public class JGenerateVersion {
	public enum VersionStatus {
		CREATE, READY_TO_SEND, PENDING, READY_TO_APPLY , OK, ERROR, OBSOLETE;
		
		public static VersionStatus get(String s) {
			if (s.equals(CREATE.toString())) return CREATE;
			if (s.equals(READY_TO_SEND.toString())) return READY_TO_SEND;
			if (s.equals(PENDING.toString())) return PENDING;
			if (s.equals(READY_TO_APPLY.toString())) return READY_TO_APPLY;
			if (s.equals(OK.toString())) return OK;
			if (s.equals(ERROR.toString())) return ERROR;
			if (s.equals(OBSOLETE.toString())) return OBSOLETE;
			return null;
		}
	}

	IVersionGenerator version;
	boolean full;
 
	public JGenerateVersion(IVersionGenerator v, boolean zfull) {
		version = v;
		full=zfull;
	}

	public long pack() throws Exception {
		long packID = -1;
		List<BizPackageDetail> list = new ArrayList<BizPackageDetail>();
    Map<String,IVersionGenerator> versionGenerators = new HashMap<String,IVersionGenerator>();
		BizVersionGenerators.clearCache();
		version.getStartedVersion().pack(version, list, versionGenerators, full, false);
		// incremento el sig id de version
		if (list.isEmpty()) throw new Exception("No hay cambio para enviar");
    versionGenerators.put(version.getKeyVersion(),version);

		// los guardo
		BizPackage  pack = new BizPackage();
		packID = pack.SelectMaxLong("id_package") + 1;
		pack.setIdpackage(packID);
		pack.setFullVersion(full);
		pack.setDateCreation(new Date());
		pack.setStatus(VersionStatus.CREATE);
		pack.processInsert();

		int line = 1;
		for (BizPackageDetail p : list) {
			p.setIdpackage(pack.getIdpackage());
			p.setIdsub_package(line++);
			p.processInsert();
		}
		
		// incremento version en los generadores de version afectados por el envio
		for (IVersionGenerator v: versionGenerators.values()) {
			v.incrementVersion();
		}
		
		
		return packID;
	}

}
