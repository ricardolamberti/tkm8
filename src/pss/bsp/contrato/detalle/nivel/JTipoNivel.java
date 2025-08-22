package pss.bsp.contrato.detalle.nivel;

public abstract class JTipoNivel implements ITipoNivel {

	@Override
	public Double getValorDefault(long param, BizNivel nivel) throws Exception {
		return null;
	}
	
	public double calculeForPremio(BizNivel nivel,double value) throws Exception {
		return value;
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
}
