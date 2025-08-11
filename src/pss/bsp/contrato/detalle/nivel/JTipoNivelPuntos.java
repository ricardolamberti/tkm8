package pss.bsp.contrato.detalle.nivel;

import pss.bsp.contrato.BizContrato;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;

public class JTipoNivelPuntos extends JTipoNivel implements ITipoNivel  {

	public double calcule(BizNivel nivel) throws Exception {
	
		return nivel.getRealValor();
	}
	public String getParametro(long param,BizNivel nivel) throws Exception {
		if (param==0) return 	"DATA";
	return null;
	}
}
