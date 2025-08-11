package pss.bsp.contrato.detalle.nivel;

public class JTipoNivelUpfront  implements ITipoNivel {

	@Override
	public Double getValorDefault(long param, BizNivel nivel) throws Exception {
		return null;
	}
	@Override
	public boolean getEditable(long param, BizNivel nivel) throws Exception {
		return getParametro(param, nivel)!=null;
	}
	
  public boolean needFMSGLobal()  throws Exception {
  	return false;
  }
  public boolean needShareGapGLobal()  throws Exception {
  	return false;
  }
  public boolean needValorRefGLobal()  throws Exception {
  	return false;
  }

	public String getError(BizNivel nivel) throws Exception {
		return null;
	}
	@Override
	public double calcule(BizNivel nivel) throws Exception {
		return 0;
	}
	@Override
	public String getParametro(long param, BizNivel nivel) throws Exception {
		return null;
	}
	@Override
	public double calculeForPremio(BizNivel nivel, double valor) throws Exception {
		return valor;
	}
}