package  pss.bsp.interfaces.copa;

import pss.bsp.interfaces.copa.cabecera.BizCopaCabecera;
import pss.bsp.interfaces.copa.detalle.BizCopaDetalle;
import pss.bsp.parseo.IFinder;


public interface IFinderCopa extends IFinder {
		public void detectHeader(BizCopaCabecera header)throws Exception;
		public void detectDetail(BizCopaDetalle detail) throws Exception;
}
