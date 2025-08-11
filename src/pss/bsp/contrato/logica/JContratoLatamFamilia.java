package pss.bsp.contrato.logica;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.bsp.contrato.detalleLatamFamilia.BizDetalleDataminingTriFamilia;
import pss.bsp.contrato.detalleLatamFamilia.GuiDetalleDataminingTriFamilia;

public class JContratoLatamFamilia implements ILogicaContrato {
	//
//@Override
//public void addObjetivos(BizObjetivo contrato) throws Exception {
//	
//}

@Override
public GuiDetalle getWin() throws Exception {
	return new GuiDetalleDataminingTriFamilia();
}

@Override
public BizDetalle getBiz() throws Exception {
	return new BizDetalleDataminingTriFamilia();
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