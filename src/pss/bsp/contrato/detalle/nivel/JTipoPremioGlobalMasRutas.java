package pss.bsp.contrato.detalle.nivel;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;

public class JTipoPremioGlobalMasRutas extends JTipoPremio   implements ITipoPremio {
	public double calcule(double total, double level, BizNivel nivel, BizDetalle detalle) throws Exception {
		JRecords<BizDetalle> recs = new JRecords<BizDetalle>(BizDetalle.class);
		recs = nivel.getObjDetalle().getObjContrato().getDetalles();
		recs.addFilter("kicker", true);
		double gananciaRutas = 0;
		double gananciaGlobal = total;
		double porcBono = calculePerc(total, level, nivel);
		JIterator<BizDetalle> it = recs.getStaticIterator();
		while (it.hasMoreElements()) {
			BizDetalle det =it.nextElement();
			if (det.getLinea()==detalle.getLinea()) continue;//soy yo mismo
			gananciaRutas+=det.getCalculeGanancia();
		}
		if (gananciaGlobal==0) return 0;
		return  JTools.rd((gananciaGlobal + gananciaRutas) * (porcBono /100));
	}
	@Override
	public double estimar(double total,  BizNivel nivel, BizDetalle detalle) throws Exception {
		return calcule(total,nivel.getValor(),nivel, detalle);
	}
	public double calculePerc(double total, double level, BizNivel nivel) throws Exception {
		return nivel.getPremio();
	}
	public String display(BizNivel nivel) throws Exception {
		return "( incenTotal + incentRutas ) X "+nivel.getPremio()+" %";
	}
	public String getParametro(long param,BizNivel nivel) throws Exception {
		if (param==0) return 	"DATA";
		if (param==1) return 	"% cumplimiento";
		return null;
	}
}