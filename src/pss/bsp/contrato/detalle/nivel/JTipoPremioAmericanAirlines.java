package pss.bsp.contrato.detalle.nivel;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.core.tools.JTools;

public class JTipoPremioAmericanAirlines extends JTipoPremio implements ITipoPremio {

	public double calculePerc(double total, double level, BizNivel nivel) throws Exception {
		double valorExpected = nivel.getParam1();
		double valorLLegado = level;
		if (valorExpected==0) return 0;
		double nn= (valorLLegado/valorExpected)*100;
		
		if (nn<nivel.getParam2()) return 0;
		if (nn>nivel.getParam3()) return nivel.getParam5();
		double x1 = nivel.getParam2();
		double x2 = nivel.getParam3();
		double y1 = nivel.getParam4();
		double y2 = nivel.getParam5();
		if ((x2-x1)==0) return 0;
			
		double factor = (y2-y1)/(x2-x1);
		double asc = (-(factor)*x1)+y1;
		return (factor*nn)+asc;
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
		return null;
	}
	
	

}
