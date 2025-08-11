package pss.bsp.contrato.detalle.nivel;

import pss.core.tools.JTools;

public class JTipoNivelPorcAnterior extends JTipoNivel  implements ITipoNivel {
	//va / vt=r/100/fa
	//params1 es fa la media de la aerolinea
	//params2 es r el valorl limite menor
	public double calcule( BizNivel nivel) throws Exception {
		double vr=nivel.getObjDetalle().getValorGlobal();
		double va = vr!=0?vr:nivel.getObjDetalle().getCalculeValorAnoAnterior();
		return JTools.rd((va*((nivel.getParam1())/100)),2);
	}
	public String getParametro(long param,BizNivel nivel) throws Exception {
		if (param==0) return 	null;
		if (param==1) return 	"Indice (200=100%)";
		return null;
	}
	
  @Override
  public boolean needValorRefGLobal() throws Exception {
  	return true;
  }
	@Override
	public String getError(BizNivel nivel) throws Exception {
		double vr=nivel.getObjDetalle().getValorGlobal();
		double va = vr!=0?vr:nivel.getObjDetalle().getCalculeValorAnoAnterior();
		if (va==0) return "No existe info del período anterior";
		return super.getError(nivel);
	}
}