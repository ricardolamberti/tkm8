package pss.bsp.contrato.logica;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.bsp.contrato.detalleUpfront.BizDetalleUpfront;
import pss.bsp.contrato.detalleUpfront.GuiDetalleUpfront;

public class JContratoUpfront implements ILogicaContrato {
	//
//@Override
//public void addObjetivos(BizObjetivo contrato) throws Exception {
//	
//}

@Override
public GuiDetalle getWin() throws Exception {
	return new GuiDetalleUpfront();
}

@Override
public BizDetalle getBiz() throws Exception {
	return new BizDetalleUpfront();
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
