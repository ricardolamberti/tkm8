package  pss.bsp.interfaces.dqb;

import pss.bsp.interfaces.dqb.cabecera.BizDQBCabecera;
import pss.bsp.interfaces.dqb.detalle.BizDQBDetalle;
import pss.bsp.parseo.IFinder;


public interface IFinderDQB extends IFinder {
		public void detectHeader(BizDQBCabecera header)throws Exception;
		public void detectDetail(BizDQBDetalle detail) throws Exception;
}
