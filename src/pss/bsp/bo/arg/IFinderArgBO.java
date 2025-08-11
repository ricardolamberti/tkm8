package  pss.bsp.bo.arg;

import pss.bsp.bo.arg.cabecera.BizArgCabecera;
import pss.bsp.bo.arg.detalle.BizArgDetalle;
import pss.bsp.parseo.IFinder;


public interface IFinderArgBO extends IFinder {
	public void detectHeader(BizArgCabecera header)throws Exception;
	public void detectDetail(BizArgDetalle detail) throws Exception;
}

