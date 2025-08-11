package  pss.common.personalData.types;

import pss.common.personalData.BizPersona;

public class BizPersonaFisica extends BizPersona {

	public BizPersonaFisica() throws Exception {
		super();
	}

	public String getTipoPersona() throws Exception {
		return BizPersona.TIPO_FISICA;
	}

	

}
