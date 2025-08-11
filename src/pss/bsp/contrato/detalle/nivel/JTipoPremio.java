package pss.bsp.contrato.detalle.nivel;

public abstract class JTipoPremio  implements ITipoPremio {
	@Override
	public Double getValorDefault(long param, BizNivel nivel) throws Exception {
		return null;
	}
	@Override
	public boolean getEditable(long param, BizNivel nivel) throws Exception {
		return getParametro(param, nivel)!=null;
	}
	public String getError(BizNivel nivel) throws Exception {
		return null;
	}
	public String display(String valor) throws Exception {
		return valor+" %";
	}


}
