package pss.bsp.contrato.logica;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.bsp.contrato.detalleUpfrontRutas.BizDetalleUpfrontRuta;
import pss.bsp.contrato.detalleUpfrontRutas.GuiDetalleUpfrontRuta;

public class JContratoUpfrontRuta  implements ILogicaContrato {
	//
//@Override
//public void addObjetivos(BizObjetivo contrato) throws Exception {
//	
//}

@Override
public GuiDetalle getWin() throws Exception {
	return new GuiDetalleUpfrontRuta();
}

@Override
public BizDetalle getBiz() throws Exception {
	return new BizDetalleUpfrontRuta();
}
public boolean needIndicadorObjetivo() throws Exception {
	return false;
}
public boolean needIndicadorObjetivoGanancia() throws Exception {
	return true;
}
@Override
public boolean needIndicadorObjetivoAuxiliar() throws Exception {
	return false;
}


}
