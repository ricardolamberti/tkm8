package pss.bsp.contrato.detalle.nivel;

import pss.core.tools.JTools;

public class JTipoNivelIntervalo extends JTipoNivel  implements ITipoNivel {
	//((act - va)/va)x100)+100=indice 
	//params1 es fa la media de la aerolinea
	//params2 es indice mayor el valorl limite menor
	//params3 es va valor anterior
	public double calcule( BizNivel nivel) throws Exception {
		double va =  nivel.getRealValor();
//		if (va>nivel.getParam2()) return va;
		return va;
	}
	public String getParametro(long param,BizNivel nivel) throws Exception {
		if (param==0) return 	null;
		if (param==1) return 	"Valor mínimo";
		if (param==2) return 	"Valor máximo";
		if (param==3) return 	"período anterior(0 si autocalcula)";
		return null;
	}
	
	@Override
	public String getError(BizNivel nivel) throws Exception {
		return super.getError(nivel);
	}
}