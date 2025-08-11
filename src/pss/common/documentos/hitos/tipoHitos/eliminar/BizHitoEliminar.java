package pss.common.documentos.hitos.tipoHitos.eliminar;

import java.util.Date;

import pss.common.documentos.hitos.BizHito;
import pss.common.security.BizUsuario;


public class BizHitoEliminar extends BizHito {

	public BizHitoEliminar() throws Exception {
		super();
	}

	@Override
	public void processInsert() throws Exception {
		this.setTipoHito(BizHito.ELIMINAR);
		this.setFecha(new Date());
		this.setIdoperador(BizUsuario.getUsr().GetUsuario());
		super.processInsert();
	}
}