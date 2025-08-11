package pss.tourism.carrier.logica;

import pss.tourism.pnr.BizBooking;
import pss.tourism.pnr.BizPNRSegmentoAereo;

public class JCarrierLogicaBase {
	  public void doCarrierLogica(String familia, BizPNRSegmentoAereo segmento) throws Exception {
			if (familia!=null) {
				segmento.setFamiliaTarifaria(familia == null ||familia.length() < 6 ? "" : familia.substring(3, 5));
			} else {
				segmento.setFamiliaTarifaria("");
				
			}
	  }
	  
		 public boolean doCarrierLogica(String familia, BizBooking bok, BizPNRSegmentoAereo segmento) throws Exception {
			 return false;
		 }
}