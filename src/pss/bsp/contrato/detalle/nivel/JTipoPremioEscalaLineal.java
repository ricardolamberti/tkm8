package pss.bsp.contrato.detalle.nivel;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;

public class JTipoPremioEscalaLineal  extends JTipoPremio  implements ITipoPremio {
	public double calcule(double total, double level, BizNivel nivel, BizDetalle detalle) throws Exception {
		BizNivel n=null;
		BizNivel nivelPremio=null;
		JRecords<BizNivel> niveles= nivel.getObjDetalle().getObjNiveles();
  	niveles.addOrderBy("valor","ASC");
		JIterator<BizNivel> it =niveles.getStaticIterator();
		while (it.hasMoreElements()) {
			n = it.nextElement();
			double valor = n.getValor();
			if (valor>level) {
				break;
			}
			nivelPremio=n;
		}
		if (nivelPremio==null) return 0;
		double porcentaje=0;
		if (n.equals(nivelPremio)) porcentaje= n.getPremio();
		else if ((n.getValor()-nivelPremio.getValor())==0) porcentaje = n.getPremio();
		else {
			// si esta en medio, calcular escala lineal
			double angulo=(n.getPremio()-nivelPremio.getPremio())/(n.getValor()-nivelPremio.getValor());
			porcentaje = nivelPremio.getPremio()+(angulo*(level-nivelPremio.getValor()));
		}
		return JTools.rd(total * (porcentaje/100.0),2);
	}
	@Override
	public double estimar(double total,  BizNivel nivel, BizDetalle detalle) throws Exception {
		return calcule(total,nivel.getValor(),nivel, detalle);
	}
	public double calculePerc(double total, double level, BizNivel nivel) throws Exception {
		BizNivel n=null;
		BizNivel nivelPremio=null;
		JRecords<BizNivel> niveles= nivel.getObjDetalle().getObjNiveles();
  	niveles.addOrderBy("valor","ASC");
		JIterator<BizNivel> it =niveles.getStaticIterator();
		while (it.hasMoreElements()) {
			n = it.nextElement();
			double valor = n.getValor();
			if (valor>level) {
				break;
			}
			nivelPremio=n;
		}
		if (nivelPremio==null) return 0;
		double porcentaje=0;
		if (n.equals(nivelPremio)) porcentaje= n.getPremio();
		else if ((n.getValor()-nivelPremio.getValor())==0) porcentaje = n.getPremio();
		else {
			// si esta en medio, calcular escala lineal
			double angulo=(n.getPremio()-nivelPremio.getPremio())/(n.getValor()-nivelPremio.getValor());
			porcentaje = nivelPremio.getPremio()+(angulo*(level-nivelPremio.getValor()));
		}
		return porcentaje;
	}
	public String display(BizNivel nivel) throws Exception {
		return nivel.getPremio()+" %";
	}
	public String getParametro(long param,BizNivel nivel) throws Exception {
		if (param==0) return 	"DATA";
		return null;
	}
}