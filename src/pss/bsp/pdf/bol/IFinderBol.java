package  pss.bsp.pdf.bol;

import pss.bsp.parseo.IFinder;
import pss.bsp.pdf.bol.cabecera.BizBolCabecera;
import pss.bsp.pdf.bol.detalle.BizBolDetalle;
import pss.bsp.pdf.bol.impuesto.BizBolImpuesto;


public interface IFinderBol extends IFinder {
		public void detectHeader(BizBolCabecera header)throws Exception;
		public void detectDetail(BizBolDetalle detail) throws Exception;
		public void detectTax(BizBolImpuesto tax) throws Exception;
}
