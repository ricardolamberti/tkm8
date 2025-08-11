package pss.bsp.contrato.detalle.nivel;

public class JTipoNivelNormalConLimiteExterno extends JTipoNivel implements ITipoNivel {
	public double calcule(BizNivel nivel) throws Exception {
		return nivel.getRealValor();
	}
	public double calculeForPremio(BizNivel nivel,double value) throws Exception {
		if (nivel.getObjDetalle().getCalculeAuxiliar()<nivel.getParam1()) return 0;
		return value;
	}
	public String getParametro(long param,BizNivel nivel) throws Exception {
		if (param==0) return 	"DATA";
		if (param==1) return 	"Limite superior a";
	return null;
	}
	
  
	@Override
	public String getError(BizNivel nivel) throws Exception {
		if (nivel.getObjDetalle().getCalculeAuxiliar()<nivel.getParam1()) return "No se alcanzo el minimo de "+nivel.getParam1();
		return super.getError(nivel);
	}

}
