package pss.bsp.contrato.detalle.nivel;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;

public class JTipoPremioPorcentajePorNivelDePorcentajes  extends JTipoPremio  implements ITipoPremio {
	public double calcule(double total, double level, BizNivel nivel, BizDetalle detalle) throws Exception {
		BizNivel n=null;
		double premio =0;
		double premioAcum =0;
		double cienporciento = (total/level)*100;
//		double nivelAnterior=0;
		double premioAnterior =0;
		double nroNiveles=0;
		double baseAnterior=0;
		double porcAnterior=0;
		JRecords<BizNivel> niveles= nivel.getObjDetalle().getObjNiveles();
  	niveles.addOrderBy("valor","ASC");
		JIterator<BizNivel> it =niveles.getStaticIterator();
		while (it.hasMoreElements()) {
			n = it.nextElement();
			double valor = n.getValor();
			if (valor>=level) break;
			
			double base = cienporciento * (valor/100);
			premio = (porcAnterior/100)*(base-baseAnterior);
			nroNiveles++;
			premioAcum+=premio;
//			nivelAnterior=valor;
			premioAnterior=premio;
			baseAnterior=base;
			porcAnterior=n.getPremio();
			n=null;
		}
		if (nroNiveles>1) {
			premio= (porcAnterior/100)*(total-baseAnterior);//-nivelAnterior);
			premioAcum+=premio;
		}
		return JTools.rd(premioAcum,2);
	}
	@Override
	public double estimar(double total,  BizNivel nivel, BizDetalle detalle) throws Exception {
		return calcule(total,nivel.getValor()+0.01,nivel, detalle);
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