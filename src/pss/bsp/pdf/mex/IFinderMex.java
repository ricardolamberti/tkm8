package  pss.bsp.pdf.mex;

import pss.bsp.parseo.IFinder;
import pss.bsp.pdf.mex.cabecera.BizMexCabecera;
import pss.bsp.pdf.mex.detalle.BizMexDetalle;
import pss.bsp.pdf.mex.impuesto.BizMexImpuesto;


public interface IFinderMex extends IFinder {
		public void detectHeader(BizMexCabecera header)throws Exception;
		public void detectDetail(BizMexDetalle detail) throws Exception;
		public void detectTax(BizMexImpuesto tax) throws Exception;
}
