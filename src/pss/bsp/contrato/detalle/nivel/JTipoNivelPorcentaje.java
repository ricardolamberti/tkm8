package pss.bsp.contrato.detalle.nivel;

import pss.core.tools.JTools;

public class JTipoNivelPorcentaje extends JTipoNivel implements ITipoNivel  {

	public double calcule(BizNivel nivel) throws Exception {
		return JTools.rd((nivel.getObjDetalle().getValorGlobal() * (nivel.getParam1()/100)),2);
	}
	public String getParametro(long param,BizNivel nivel) throws Exception {
		if (param==0) return 	null;
		if (param==1) return 	"% cumplimiento";
  	return null;
	}
	
  @Override
  public boolean needValorRefGLobal() throws Exception {
  	return true;
  }
  
	@Override
	public String getError(BizNivel nivel) throws Exception {
		if (nivel.getObjDetalle().getFMSGlobal()==0) return "Debe proveerse un valor global de referencia";
		return super.getError(nivel);
	}

}
