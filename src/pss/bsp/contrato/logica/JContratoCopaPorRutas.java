package pss.bsp.contrato.logica;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.bsp.contrato.detalleCopaPorRutas.BizDetalleCopaPorRutas;
import pss.bsp.contrato.detalleCopaPorRutas.GuiDetalleCopaPorRutas;

public class JContratoCopaPorRutas implements ILogicaContrato {


@Override
public GuiDetalle getWin() throws Exception {
	return new GuiDetalleCopaPorRutas();
}

@Override
public BizDetalle getBiz() throws Exception {
	return new BizDetalleCopaPorRutas();
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
