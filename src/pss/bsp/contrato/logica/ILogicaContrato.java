package pss.bsp.contrato.logica;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;

public interface ILogicaContrato  {
	public GuiDetalle getWin() throws Exception;
	public BizDetalle getBiz() throws Exception;
//	public void addObjetivos(BizObjetivo contrato) throws Exception; 

	public boolean needIndicadorObjetivo() throws Exception;
	public boolean needIndicadorObjetivoGanancia() throws Exception;
	public boolean needIndicadorObjetivoAuxiliar() throws Exception;

}
