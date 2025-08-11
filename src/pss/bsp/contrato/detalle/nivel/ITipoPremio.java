package pss.bsp.contrato.detalle.nivel;

import pss.bsp.contrato.detalle.BizDetalle;

public interface ITipoPremio {
	public double estimar(double total, BizNivel nivel, BizDetalle detalle) throws Exception; 
	public double calcule(double total, double level, BizNivel nivel, BizDetalle detalle) throws Exception; 
	public double calculePerc(double total, double level,BizNivel nivel) throws Exception; 
	public String display(BizNivel nivel) throws Exception; 
	public String display(String nivel) throws Exception; 
	public String getParametro(long param,BizNivel nivel) throws Exception ; 
	public Double getValorDefault(long param,BizNivel nivel) throws Exception ; 
	public boolean getEditable(long param,BizNivel nivel) throws Exception ; 
	public String getError(BizNivel nivel) throws Exception ; 


}
