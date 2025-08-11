package pss.bsp.contrato.detalle.nivel;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.core.tools.JTools;

public class JTipoPremioObjetivo extends JTipoPremio  implements ITipoPremio {
	public double calcule(double total, double level, BizNivel nivel, BizDetalle detalle) throws Exception {
		return JTools.rd((total*100)/nivel.getValor(),2);
	}
	@Override
	public double estimar(double total,  BizNivel nivel, BizDetalle detalle) throws Exception {
		return calcule(total,nivel.getValor(),nivel, detalle);
	}
	public double calculePerc(double total, double level, BizNivel nivel) throws Exception {
		return JTools.rd((total*100)/nivel.getValor(),2);
	}
	public String display(BizNivel nivel) throws Exception {
		return nivel.getPremio()+" %";
	}
	public String getParametro(long param,BizNivel nivel) throws Exception {
		return null;
	}
}