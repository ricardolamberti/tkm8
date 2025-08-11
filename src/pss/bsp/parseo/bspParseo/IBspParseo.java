package  pss.bsp.parseo.bspParseo;

import pss.bsp.parseo.IParseo;
import pss.core.win.JWin;
import pss.core.win.JWins;


public interface IBspParseo extends IParseo {
		public JWins getGuisDetail(String zCompany,String zOwner, String zIdPdf) throws Exception;
		public JWin getGuiHeader(String zCompany,String zOwner, String zIdPdf) throws Exception;
		public void eliminar(String zCompany, String zOwner, String zIdPdf) throws Exception;
		public void refill(String zCompany, String zOwner, String zIdPdf) throws Exception;
		public String getConciliableKey() throws Exception;
		public boolean supportRefunds();
		public void processDks(String zCompany) throws Exception;
			
		
}
