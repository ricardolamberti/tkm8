package pss.bsp.contrato.detalle.nivel;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;

public class JTipoPremioNivelSobreTotal  extends JTipoPremio  implements ITipoPremio {
	public double calcule(double total, double level, BizNivel nivel, BizDetalle detalle) throws Exception {
		BizNivel n=null;
		double premio =0;
		double premioanterior=0;
		double nivelAnterior=0;
		JRecords<BizNivel> niveles= nivel.getObjDetalle().getObjNiveles();
  	niveles.addOrderBy("valor","ASC");
		JIterator<BizNivel> it =niveles.getStaticIterator();
		int niv=0;
		while (it.hasMoreElements()) {
			n = it.nextElement();
			double valor = n.getValor();
			if (valor>level) break;
			if (premioanterior!=0) {
				premio+= (premioanterior*(valor-nivelAnterior));
			}
			premioanterior = n.getPremio();
			nivelAnterior=valor;
			n=null;
			niv++;
		}
		if (premioanterior!=0) 
			premio+= premioanterior*(level-nivelAnterior);
		if (level==0) return 0;
		if (niv<=1) return 0;
		return JTools.rd(((premio/level))*total,2);
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