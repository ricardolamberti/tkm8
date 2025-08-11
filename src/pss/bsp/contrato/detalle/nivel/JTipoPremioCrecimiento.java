package pss.bsp.contrato.detalle.nivel;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.core.tools.JTools;

public class JTipoPremioCrecimiento  extends JTipoPremio  implements ITipoPremio {

	public double calculePerc(double total, double level, BizNivel nivel) throws Exception {
		double min = nivel.getParam1(); 
		double max = nivel.getParam2();
		double ant = nivel.getParam3();
		if (ant==0) {
			ant = nivel.getObjDetalle().getCalculeValorAnoAnterior();
		}
		if (ant==0) return 0;
		double pmin = nivel.getParam4();
		double pmax = nivel.getParam5();
		double crec = ((total-ant)/ant)*100+100;
		double CF= nivel.getParam6();
		double a = Math.pow((crec-min)/(max-min),CF);
		double b = (pmax-pmin);
		double porc= (a * b) + pmin;
		if (porc>pmax) return pmax;
		if (porc<pmin) return 0;
		return porc;
	}
	
	@Override
	public double calcule(double total, double level, BizNivel nivel, BizDetalle detalle) throws Exception {
		double factor = calculePerc(total,level,nivel);
		return JTools.rd(total * (factor/100),2);
	}

	@Override
	public double estimar(double total,  BizNivel nivel, BizDetalle detalle) throws Exception {
		return calcule(total,nivel.getValor(),nivel, detalle);
	}
	@Override
	public String display(BizNivel nivel) throws Exception {
		return nivel.getParam4()+"% - "+nivel.getParam5()+"%";
	}

	@Override
	public String getParametro(long param, BizNivel nivel) throws Exception {
		if (param==0) return 	null;
		if (param==4) return 	"% Min";
		if (param==5) return 	"% Max";
		if (param==6) return 	"Factor Curva";
		return null;
	}

}
