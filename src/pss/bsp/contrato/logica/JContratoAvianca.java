package pss.bsp.contrato.logica;

import pss.bsp.contrato.BizContrato;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.bsp.contrato.detalle.nivel.BizNivel;
import pss.bsp.contrato.detalle.nivel.JTipoNivelNormal;
import pss.bsp.contrato.detalle.nivel.JTipoNivelPuntos;
import pss.bsp.contrato.detalle.nivel.JTipoPremioPorcentajeDelTotal;
import pss.bsp.contrato.detalle.nivel.JTipoPremioPuntos;
import pss.bsp.contrato.detalleAvianca.BizDetalleAvianca;
import pss.bsp.contrato.detalleAvianca.GuiDetalleAvianca;
import pss.bsp.interfaces.copa.cabecera.BizCopaCabecera;
import pss.bsp.interfaces.copa.detalle.BizCopaDetalle;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;

public class JContratoAvianca implements ILogicaContrato {

//	@Override
//	public void addObjetivos(BizObjetivo contrato) throws Exception {
//
//	}


	@Override
	public GuiDetalle getWin() throws Exception {
		return new GuiDetalleAvianca();
	}

	@Override
	public BizDetalle getBiz() throws Exception {
		return new BizDetalleAvianca();
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
