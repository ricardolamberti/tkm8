package  pss.bsp.parseo.bspParseo;

import pss.bsp.parseo.IParseo;
import pss.core.win.JWin;
import pss.core.win.JWins;

public interface IInterfazParseo  extends IParseo {
	public JWins getGuisDetail(String zCompany,String zOwner, String zIdPdf) throws Exception;
	public JWin getGuiHeader(String zCompany,String zOwner, String zIdPdf) throws Exception;
	public void eliminar(String zCompany, String zOwner, String zIdPdf) throws Exception;

}
