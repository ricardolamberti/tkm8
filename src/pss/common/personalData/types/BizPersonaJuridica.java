package  pss.common.personalData.types;

import pss.common.personalData.BizPersona;

public class BizPersonaJuridica extends BizPersona {

	public BizPersonaJuridica() throws Exception {
		super();
	}

  @Override
	protected String getFieldApellidoDescription() {return "Razón Social";}
  @Override
	protected String getFieldFechaNacimiento() {return "Fecha de Inicio de Actividades";}
	
	public String getNombreCompleto() throws Exception {
		return this.GetApellido();
	}

	public String getTipoPersona() throws Exception {
		return BizPersona.TIPO_JURIDICA;
	}

}
