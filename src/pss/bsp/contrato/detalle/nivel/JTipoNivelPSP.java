package pss.bsp.contrato.detalle.nivel;

import pss.core.tools.JTools;

public class JTipoNivelPSP   extends JTipoNivel implements ITipoNivel {
	//va / vt=r/100/fa
	//params1 es fa la media de la aerolinea
	//params2 es r el valorl limite menor
	public double calcule( BizNivel nivel) throws Exception {
			return  JTools.rd((nivel.getObjDetalle().getFMSGlobal()/100)*nivel.getParam1(),2);
	}
	public String getParametro(long param,BizNivel nivel) throws Exception {
		if (param==0) return 	null;
		if (param==1) return 	"Objetivo";
		return null;
	}
	


  public boolean needFMSGLobal()  throws Exception {
  	return true;
  }

	@Override
	public String getError(BizNivel nivel) throws Exception {
		if (nivel.getObjDetalle().getFMSGlobal()==0) return "Debe proveerse Pax expected en el campo FMS Global";
		return super.getError(nivel);
	}
}

