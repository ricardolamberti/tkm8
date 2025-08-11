package  pss.bsp.pdf.ecu;

import pss.bsp.parseo.IFinder;
import pss.bsp.pdf.ecu.cabecera.BizEcuCabecera;
import pss.bsp.pdf.ecu.detalle.BizEcuDetalle;
import pss.bsp.pdf.ecu.impuesto.BizEcuImpuesto;


public interface IFinderEcu extends IFinder {
		public void detectHeader(BizEcuCabecera header)throws Exception;
		public void detectDetail(BizEcuDetalle detail) throws Exception;
		public void detectTax(BizEcuImpuesto tax) throws Exception;
}
