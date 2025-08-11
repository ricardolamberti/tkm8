package  pss.bsp.bo.gen;

import pss.bsp.bo.formato.BizFormato;
import pss.bsp.bo.gen.cabecera.BizGenCabecera;
import pss.bsp.bo.gen.detalle.BizGenDetalle;
import pss.bsp.parseo.IFinder;


public interface IFinderGenBO extends IFinder {
	public void detectHeader(BizGenCabecera header)throws Exception;
	public void detectDetail(BizGenDetalle detail) throws Exception;
	public void detectFormat(BizFormato format) throws Exception;
}