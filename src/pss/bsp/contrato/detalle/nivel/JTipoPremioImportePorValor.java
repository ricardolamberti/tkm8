package pss.bsp.contrato.detalle.nivel;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.core.tools.JTools;

public class JTipoPremioImportePorValor extends JTipoPremio  implements ITipoPremio {
	public double calcule(double total, double level, BizNivel nivel, BizDetalle detalle) throws Exception {
		return JTools.rd((nivel.getPremio()*total),2);
	}
	@Override
	public double estimar(double total,  BizNivel nivel, BizDetalle detalle) throws Exception {
		return calcule(total,nivel.getValor(),nivel, detalle);
	}
	public double calculePerc(double total, double level, BizNivel nivel) throws Exception {
		return nivel.getPremio();
	}
	public String display(BizNivel nivel) throws Exception {
		return "$ "+nivel.getPremio();
	}
	public String display(String valor) throws Exception {
		return "$ "+valor;
	}
	public String getParametro(long param,BizNivel nivel) throws Exception {
		if (param==0) return 	"DATA";
		return null;
	}
}