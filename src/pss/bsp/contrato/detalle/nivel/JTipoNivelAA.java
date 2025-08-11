package pss.bsp.contrato.detalle.nivel;

import pss.core.tools.JTools;

public class JTipoNivelAA  extends JTipoNivel implements ITipoNivel {
	//va / vt=r/100/fa
	//params1 es fa la media de la aerolinea
	//params2 es r el valorl limite menor
	public double calcule( BizNivel nivel) throws Exception {
			return  JTools.rd((nivel.getParam2()/100)*nivel.getParam1(),2);
	}
	public String getParametro(long param,BizNivel nivel) throws Exception {
		if (param==0) return 	null;
		if (param==1) return 	"Pax Expected %";
		if (param==2) return 	"FMS Min";
		if (param==3) return 	"FMS Max";
		return null;
	}
	
	@Override
	public String getError(BizNivel nivel) throws Exception {
		if (nivel.getParam1()==0) return "Debe cargarse el FMS esperado en el nivel";
		return super.getError(nivel);
	}

}
