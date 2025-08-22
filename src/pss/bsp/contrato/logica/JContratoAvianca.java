package pss.bsp.contrato.logica;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.bsp.contrato.detalleAvianca.BizDetalleAvianca;
import pss.bsp.contrato.detalleAvianca.GuiDetalleAvianca;

public class JContratoAvianca implements ILogicaContrato {

//	@Override
//	public void addObjetivos(BizObjetivo contrato) throws Exception {
//
//	}


	@Override
	public GuiDetalle getWin() throws Exception {
		return new GuiDetalleAvianca();
	}

	@Override
	public BizDetalle getBiz() throws Exception {
		return new BizDetalleAvianca();
	}
	
	public boolean needIndicadorObjetivo() throws Exception {
		return false;
	}
	public boolean needIndicadorObjetivoGanancia() throws Exception {
		return false;
	}
	@Override
		public boolean needIndicadorObjetivoAuxiliar() throws Exception {
			return false;
		}
}
