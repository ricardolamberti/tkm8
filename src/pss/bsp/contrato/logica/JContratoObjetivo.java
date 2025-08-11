package pss.bsp.contrato.logica;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.bsp.contrato.detalleObjetivo.BizDetalleObjetivo;
import pss.bsp.contrato.detalleObjetivo.GuiDetalleObjetivo;

public class JContratoObjetivo  implements ILogicaContrato {
	//
//@Override
//public void addObjetivos(BizObjetivo contrato) throws Exception {
//	
//}

@Override
public GuiDetalle getWin() throws Exception {
	return new GuiDetalleObjetivo();
}

@Override
public BizDetalle getBiz() throws Exception {
	return new BizDetalleObjetivo();
}
public boolean needIndicadorObjetivo() throws Exception {
	return true;
}
public boolean needIndicadorObjetivoGanancia() throws Exception {
	return false;
}
@Override
public boolean needIndicadorObjetivoAuxiliar() throws Exception {
	return false;
}


}