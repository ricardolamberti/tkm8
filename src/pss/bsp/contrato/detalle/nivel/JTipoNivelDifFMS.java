package pss.bsp.contrato.detalle.nivel;

import pss.core.tools.JTools;

public class JTipoNivelDifFMS extends JTipoNivel implements ITipoNivel {

	public double calcule( BizNivel nivel) throws Exception {
			if ((nivel.getParam1()+nivel.getParam2())==0) return 0;
			return JTools.rd(((nivel.getObjDetalle().getFMSGlobal()+nivel.getObjDetalle().getShareGapGlobal())*(nivel.getParam1()/100.0)),2) ;
	}
	public String getParametro(long param,BizNivel nivel) throws Exception {
		if (param==0) return 	null; 
		if (param==1) return 	"% Cumpl."; 
		return null;
	}
	

  public boolean needFMSGLobal()  throws Exception {
  	return true;
  }
  public boolean needShareGapGLobal()  throws Exception {
  	return true;
  }
  
	@Override
	public String getError(BizNivel nivel) throws Exception {
		if (nivel.getObjDetalle().getFMSGlobal()==0) return "Debe proveerse FMS global";
		if (nivel.getObjDetalle().getShareGapGlobal()==0) return "Debe proveerse target share gap";
		return super.getError(nivel);
	}
}
