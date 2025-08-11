package  pss.bsp.pdf.arg2;

import pss.bsp.parseo.IFinder;
import pss.bsp.pdf.arg.cabecera.BizArgCabecera;
import pss.bsp.pdf.arg.detalle.BizArgDetalle;
import pss.bsp.pdf.arg.impuesto.BizArgImpuesto;


public interface IFinderArg2 extends IFinder {
		public void detectHeader(BizArgCabecera header)throws Exception;
		public void detectDetail(BizArgDetalle detail) throws Exception;
		public void detectTax(BizArgImpuesto tax) throws Exception;
}
