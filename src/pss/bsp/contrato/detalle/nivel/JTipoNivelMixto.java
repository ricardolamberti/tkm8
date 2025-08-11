package pss.bsp.contrato.detalle.nivel;

public class JTipoNivelMixto extends JTipoNivel implements ITipoNivel  {

	public double calcule(BizNivel nivel) throws Exception {
		if (nivel.getObjDetalle().getValorTotal()<nivel.getParam2()) return 0;
		return nivel.getRealValor();
	}
	public String getParametro(long param,BizNivel nivel) throws Exception {
		if (param==0) return 	"DATA";
		if (param==1) return 	"Ganancia superior a";
	return null;
	}
	
  
	@Override
	public String getError(BizNivel nivel) throws Exception {
		if (nivel.getObjDetalle().getValorTotal()<nivel.getParam2()) return "No se alcanzo el minimo de "+nivel.getParam2();
		return super.getError(nivel);
	}

}
