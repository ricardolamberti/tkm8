package pss.bsp.pdf.col;

import pss.bsp.parseo.IFinder;
import pss.bsp.pdf.col.cabecera.BizColCabecera;
import pss.bsp.pdf.col.detalle.BizColDetalle;
import pss.bsp.pdf.col.impuesto.BizColImpuesto;


public interface IFinderCol extends IFinder {
		public void detectHeader(BizColCabecera header)throws Exception;
		public void detectDetail(BizColDetalle detail) throws Exception;
		public void detectTax(BizColImpuesto tax) throws Exception;
}
