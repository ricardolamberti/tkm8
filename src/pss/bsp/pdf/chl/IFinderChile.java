package  pss.bsp.pdf.chl;

import pss.bsp.parseo.IFinder;
import pss.bsp.pdf.chl.cabecera.BizChileCabecera;
import pss.bsp.pdf.chl.detalle.BizChileDetalle;
import pss.bsp.pdf.chl.impuesto.BizChileImpuesto;


public interface IFinderChile extends IFinder {
		public void detectHeader(BizChileCabecera header)throws Exception;
		public void detectDetail(BizChileDetalle detail) throws Exception;
		public void detectTax(BizChileImpuesto tax) throws Exception;
}
