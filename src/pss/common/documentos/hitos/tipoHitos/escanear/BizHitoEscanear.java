package pss.common.documentos.hitos.tipoHitos.escanear;

import java.util.Date;

import pss.common.documentos.hitos.BizHito;
import pss.common.security.BizUsuario;


public class BizHitoEscanear extends BizHito {
	public BizHitoEscanear() throws Exception {
		super();
	}

	@Override
	public void processInsert() throws Exception {
		this.setTipoHito(BizHito.ESCANEAR);
		this.setFecha(new Date());
		this.setIdoperador(BizUsuario.getUsr().GetUsuario());
		super.processInsert();
	}
}
