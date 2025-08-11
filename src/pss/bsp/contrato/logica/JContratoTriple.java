package pss.bsp.contrato.logica;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.bsp.contrato.detalleDataminingTri.BizDetalleDataminingTri;
import pss.bsp.contrato.detalleDataminingTri.GuiDetalleDataminingTri;

public class JContratoTriple  implements ILogicaContrato {
	//
//@Override
//public void addObjetivos(BizObjetivo contrato) throws Exception {
//	
//}

@Override
public GuiDetalle getWin() throws Exception {
	return new GuiDetalleDataminingTri();
}

@Override
public BizDetalle getBiz() throws Exception {
	return new BizDetalleDataminingTri();
}
public boolean needIndicadorObjetivo() throws Exception {
	return true;
}
public boolean needIndicadorObjetivoGanancia() throws Exception {
	return true;
}
@Override
public boolean needIndicadorObjetivoAuxiliar() throws Exception {
	return true;
}


}
