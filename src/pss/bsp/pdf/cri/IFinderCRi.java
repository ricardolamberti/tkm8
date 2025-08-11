package  pss.bsp.pdf.cri;

import pss.bsp.parseo.IFinder;
import pss.bsp.pdf.cri.cabecera.BizCRiCabecera;
import pss.bsp.pdf.cri.detalle.BizCRiDetalle;
import pss.bsp.pdf.cri.impuesto.BizCRiImpuesto;


public interface IFinderCRi extends IFinder {
		public void detectHeader(BizCRiCabecera header)throws Exception;
		public void detectDetail(BizCRiDetalle detail) throws Exception;
		public void detectTax(BizCRiImpuesto tax) throws Exception;
}
