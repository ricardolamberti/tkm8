package pss.bsp.contrato.logica;

import pss.bsp.contrato.BizContrato;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.bsp.contrato.detalleDatamining.BizDetalleDatamining;
import pss.bsp.contrato.detalleDatamining.GuiDetalleDatamining;

public class JContratoNormal implements ILogicaContrato {
//
//	@Override
//	public void addObjetivos(BizObjetivo contrato) throws Exception {
//		
//	}

	@Override
	public GuiDetalle getWin() throws Exception {
		return new GuiDetalleDatamining();
	}

	@Override
	public BizDetalle getBiz() throws Exception {
		return new BizDetalleDatamining();
	}
	public boolean needIndicadorObjetivo() throws Exception {
		return true;
	}
	public boolean needIndicadorObjetivoGanancia() throws Exception {
		return true;
	}
	@Override
	public boolean needIndicadorObjetivoAuxiliar() throws Exception {
		return false;
	}


}
