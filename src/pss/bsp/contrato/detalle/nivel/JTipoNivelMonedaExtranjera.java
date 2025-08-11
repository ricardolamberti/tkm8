package pss.bsp.contrato.detalle.nivel;

import pss.core.tools.JTools;

public class JTipoNivelMonedaExtranjera extends JTipoNivel  implements ITipoNivel {
	//va / vt=r/100/fa
	//params1 es fa la media de la aerolinea
	//params2 es r el valorl limite menor
	public double calcule( BizNivel nivel) throws Exception {
		double vMon=nivel.getObjDetalle().getValorGlobal();
		return JTools.rd(vMon*nivel.getParam1(),2);
	}
	public String getParametro(long param,BizNivel nivel) throws Exception {
		if (param==0) return 	null;
		if (param==1) return 	"Limite en Moneda extranjera";
		return null;
	}
	
  @Override
  public boolean needValorRefGLobal() throws Exception {
  	return true;
  }
	@Override
	public String getError(BizNivel nivel) throws Exception {
		double vr=nivel.getObjDetalle().getValorGlobal();
		if (vr==0) return "DEBE CARGAR COTIZACION, EN VALOR GLOBAL";
		return super.getError(nivel);
	}
}