package  pss.bsp.consolid.model.liquidacion.terrestres;

import pss.bsp.consolid.model.liquidacion.detail.BizLiqDetail;
import pss.bsp.consolid.model.liquidacion.header.BizLiqHeader;
import pss.bsp.parseo.IFinder;


public interface IFinderTerrestre extends IFinder {
		public void detectHeader(BizLiqHeader header)throws Exception;
		public void detectDetail(BizLiqDetail detail) throws Exception;
}
