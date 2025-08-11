package pss.www.platform.tasks;

import pss.core.data.BizPssConfig;
import pss.core.tools.PssLogger;
import pss.core.tools.depureFiles.JDepureFiles;

public class JDepurateFilesTask extends JWebCleanUpTask {

	public JDepurateFilesTask(long zDelay) {
		super(zDelay);
	}

	@Override
	public String getName() {
			return "depurate";
	}

	@Override
	protected void doCleanUp() {
		depurate();

	}
	
	static boolean bDepurate = false;
	public void depurate() {
		try {
		
			if (!BizPssConfig.getPssConfig().activeDepurateFiles()) {
				PssLogger.logInfo("NO Depurate, inactive");
				return;
			}
			if (bDepurate) return;
			bDepurate=true;
			JDepureFiles depurate = new JDepureFiles();
			depurate.setPath(BizPssConfig.getPssConfig().getDepurateFiles());
			depurate.setIQtyDepureDays((int)BizPssConfig.getPssConfig().getDepurateDays());
			depurate.depure();
			bDepurate=false;
		} catch (Exception e) {
			bDepurate=false;
			PssLogger.logError(e);
		}
	}


}
