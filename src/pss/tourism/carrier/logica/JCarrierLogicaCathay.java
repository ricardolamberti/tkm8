package pss.tourism.carrier.logica;

import pss.tourism.pnr.BizPNRSegmentoAereo;

public class JCarrierLogicaCathay  extends JCarrierLogicaBase  implements ICarrierLogica {
	 
		public void doCarrierLogica(String familia, BizPNRSegmentoAereo segmento) throws Exception {
			
			if (familia!=null) {

						segmento.setFamiliaTarifaria(familia == null ||familia.length() < 4 ? "" : familia.substring(2, 4));
			} else {
				segmento.setFamiliaTarifaria("");
				
			}
	  }

	 }