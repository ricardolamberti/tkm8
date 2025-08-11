package pss.common.documentos.hitos.tipoHitos.crear;

import java.util.Date;

import pss.common.documentos.hitos.BizHito;
import pss.common.security.BizUsuario;


public class BizHitoCrear extends BizHito {

	public BizHitoCrear() throws Exception {
		super();
	}

	@Override
	public void processInsert() throws Exception {
		this.setTipoHito(BizHito.CREAR);
		this.setFecha(new Date());
		this.setIdoperador(BizUsuario.getUsr().GetUsuario());
		super.processInsert();
	}
}
