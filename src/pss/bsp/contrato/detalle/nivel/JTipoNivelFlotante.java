package pss.bsp.contrato.detalle.nivel;

import pss.core.tools.JTools;

public class JTipoNivelFlotante extends JTipoNivel implements ITipoNivel  {

	public double calcule(BizNivel nivel) throws Exception {
		double vt = nivel.getObjDetalle().getValorGlobal();
		double alcanzado = nivel.getObjDetalle().getCalculeAuxiliar();
		double porc = nivel.getObjDetalle().getShareGapGlobal();
		if (Math.abs(100-((alcanzado*100)/vt))<=porc) return nivel.getParam1();
		if (vt==0) return 0;
		return JTools.rd(nivel.getParam1() * (alcanzado/vt),2);
	}

	public String getParametro(long param, BizNivel nivel) throws Exception {

		if (param == 0)	return null;
		if (param == 1)	return "Valor ref.a alcanzar";
		return null;
	}

	@Override
	public boolean needValorRefGLobal() throws Exception {
		return true;
	}
	
	@Override
	public boolean needShareGapGLobal() throws Exception {
		return true;
	}

	@Override
	public String getError(BizNivel nivel) throws Exception {
		double vr = nivel.getObjDetalle().getValorGlobal();
		if (vr == 0) return "Debe proporcionar nivel industria referencia";
		vr = nivel.getObjDetalle().getShareGapGlobal();
		if (vr == 0) return "Debe proporcionar porcentaje de intervalo";
		return super.getError(nivel);
	}
}
