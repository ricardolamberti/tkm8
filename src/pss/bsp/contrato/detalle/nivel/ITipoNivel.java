package pss.bsp.contrato.detalle.nivel;

import pss.core.tools.collections.JIterator;

public interface ITipoNivel {
	public double calcule(BizNivel nivel) throws Exception; 
	public double calculeForPremio(BizNivel nivel,double value) throws Exception;
	public String getParametro(long param,BizNivel nivel) throws Exception ; 
	public Double getValorDefault(long param,BizNivel nivel) throws Exception ; 
	public boolean getEditable(long param,BizNivel nivel) throws Exception ; 
  public boolean needFMSGLobal()  throws Exception;
  public boolean needShareGapGLobal()  throws Exception;
  public boolean needValorRefGLobal()  throws Exception;
	public String getError(BizNivel nivel) throws Exception ; 

}
