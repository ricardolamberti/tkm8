package  pss.bsp.pdf.pan;

import pss.bsp.parseo.IFinder;
import pss.bsp.pdf.pan.cabecera.BizPanCabecera;
import pss.bsp.pdf.pan.detalle.BizPanDetalle;
import pss.bsp.pdf.pan.impuesto.BizPanImpuesto;


public interface IFinderPan extends IFinder {
		public void detectHeader(BizPanCabecera header)throws Exception;
		public void detectDetail(BizPanDetalle detail) throws Exception;
		public void detectTax(BizPanImpuesto tax) throws Exception;
}
