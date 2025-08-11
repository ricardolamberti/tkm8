package pss.bsp.contrato.detalle.nivel;

import pss.core.tools.JTools;

public class JTipoNivelCrecimiento extends JTipoNivel  implements ITipoNivel {
	//((act - va)/va)x100)+100=indice 
	//params1 es fa la media de la aerolinea
	//params2 es indice mayor el valorl limite menor
	//params3 es va valor anterior
	public double calcule( BizNivel nivel) throws Exception {
		double va = nivel.getParam3()!=0?nivel.getParam3():nivel.getObjDetalle().getCalculeValorAnoAnterior();
		double valorEnIndice = JTools.rd((((nivel.getParam1()-100)/100)*va)+va,2);
		return valorEnIndice;
	}
	public String getParametro(long param,BizNivel nivel) throws Exception {
		if (param==0) return 	null;
		if (param==1) return 	"Indice mínimo (100=100%)";
		if (param==2) return 	"Indice máximo (100=100%)";
		if (param==3) return 	"período anterior(0 si autocalcula)";
		return null;
	}
	
	@Override
	public String getError(BizNivel nivel) throws Exception {
		if ( nivel.getParam3()!=0) {
			double va = nivel.getObjDetalle().getCalculeValorAnoAnterior();
			if (va==0) return "No existe info del período anterior";
		}
		return super.getError(nivel);
	}
}