package pss.bsp.contrato.logica;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.bsp.contrato.detalleBackendRutas.BizDetalleBackendRuta;
import pss.bsp.contrato.detalleBackendRutas.GuiDetalleBackendRuta;

public class JContratoBackendRuta  implements ILogicaContrato {
	//
//@Override
//public void addObjetivos(BizObjetivo contrato) throws Exception {
//	
//}

@Override
public GuiDetalle getWin() throws Exception {
	return new GuiDetalleBackendRuta();
}

@Override
public BizDetalle getBiz() throws Exception {
	return new BizDetalleBackendRuta();
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

