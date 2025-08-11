package pss.bsp.contrato.detalle.nivel;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;

public class JTipoPremioPorcentajePorNivel extends JTipoPremio  implements ITipoPremio {
	public double calcule(double total, double level, BizNivel nivel, BizDetalle detalle) throws Exception {
		BizNivel n=null;
		double premio =0;
		double porcPremio=0;
		double nivelAnterior=0;
		int cantNiveles=0;
		JRecords<BizNivel> niveles= nivel.getObjDetalle().getObjNiveles();
  	niveles.addOrderBy("valor","ASC");
		JIterator<BizNivel> it =niveles.getStaticIterator();
		while (it.hasMoreElements()) {
			n = it.nextElement();
			double valor = n.getValor();
			if (valor>=total) break;
			premio+= (porcPremio/100)*(valor-nivelAnterior);
			nivelAnterior=valor;
			porcPremio=n.getPremio();
			cantNiveles++;
			n=null;
		}
		if (n!=null && cantNiveles>1) //primer nivel no premia
			premio+= (porcPremio/100)*(total-nivelAnterior);
		return JTools.rd(premio,2);
	}
	@Override
	public double estimar(double total,  BizNivel nivel, BizDetalle detalle) throws Exception {
		return calcule(total,nivel.getValor(),nivel, detalle);
	}
	public double calculePerc(double total, double level, BizNivel nivel) throws Exception {
		return nivel.getPremio();
	}
	public String display(BizNivel nivel) throws Exception {
		return nivel.getPremio()+" %";
	}
	public String getParametro(long param,BizNivel nivel) throws Exception {
		if (param==0) return 	"DATA";
		return null;
	}
}