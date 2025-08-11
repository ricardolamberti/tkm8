package pss.bsp.persona;

import pss.common.personalData.types.BizPersonaJuridica;
import pss.core.services.JExec;

public class BizPersonaJuridicaBSP extends BizPersonaJuridica {

	public BizPersonaJuridicaBSP() throws Exception {
		super();
		// TODO Auto-generated constructor stub
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
}
