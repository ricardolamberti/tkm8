package  pss.bsp.pdf.gua;

import pss.bsp.parseo.IFinder;
import pss.bsp.pdf.gua.cabecera.BizGuaCabecera;
import pss.bsp.pdf.gua.detalle.BizGuaDetalle;
import pss.bsp.pdf.gua.impuesto.BizGuaImpuesto;


public interface IFinderGua extends IFinder {
		public void detectHeader(BizGuaCabecera header)throws Exception;
		public void detectDetail(BizGuaDetalle detail) throws Exception;
		public void detectTax(BizGuaImpuesto tax) throws Exception;
}
