package pss.bsp.persona;

import pss.common.personalData.types.BizPersonaFisica;
import pss.core.services.JExec;

public class BizPersonaFisicaBSP extends BizPersonaFisica {

	public BizPersonaFisicaBSP() throws Exception {
		super();
	}

	public void execProcessInsert() throws Exception {
		JExec oExec = new JExec(this, "processInsert") {

			@Override
			public void Do() throws Exception {
				processInsert();
			}
		};
		oExec.execute();
	}
	
	@Override
	public void processInsert() throws Exception {
		// TODO Auto-generated method stub
		super.processInsert();
	}
}
