package pss.bsp.contrato.detalle.nivel;


public class JTipoNivelNormal extends JTipoNivel implements ITipoNivel  {

	public double calcule(BizNivel nivel) throws Exception {
		return nivel.getRealValor();
	}
	public String getParametro(long param,BizNivel nivel) throws Exception {
		if (param==0) return 	"DATA";
	return null;
	}
}
