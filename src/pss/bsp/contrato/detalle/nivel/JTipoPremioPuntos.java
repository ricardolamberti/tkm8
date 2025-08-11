package pss.bsp.contrato.detalle.nivel;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.core.tools.JTools;

public class JTipoPremioPuntos  extends JTipoPremio implements ITipoPremio  {

	public double calcule(double total, double level, BizNivel nivel, BizDetalle detalle) throws Exception {
		return JTools.rd((nivel.getPremio()/100)*total,2);
	}
	@Override
	public double estimar(double total,  BizNivel nivel, BizDetalle detalle) throws Exception {
		return calcule(total,nivel.getValor(),nivel, detalle);
	}
	public double calculePerc(double total, double level, BizNivel nivel) throws Exception {
		return nivel.getPremio();
	}
	public String display(BizNivel nivel) throws Exception {
		return nivel.getPremio()+" %"+(nivel.getParam4()==0?"":"("+ nivel.getParam4()+" puntos)");
	}
	public String getParametro(long param,BizNivel nivel) throws Exception {
		if (param==0) return 	"DATA";
		if (param==4) return 	"Puntos";
	  return null;
	}
}
