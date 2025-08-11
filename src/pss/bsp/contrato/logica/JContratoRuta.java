package pss.bsp.contrato.logica;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.bsp.contrato.detalleRutas.BizDetalleRuta;
import pss.bsp.contrato.detalleRutas.GuiDetalleRuta;

public class JContratoRuta implements ILogicaContrato {



@Override
public GuiDetalle getWin() throws Exception {
	return new GuiDetalleRuta();
}

@Override
public BizDetalle getBiz() throws Exception {
	return new BizDetalleRuta();
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
