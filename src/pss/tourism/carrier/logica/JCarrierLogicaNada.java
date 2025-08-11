package  pss.tourism.carrier.logica;

import pss.tourism.pnr.BizPNRSegmentoAereo;

public class JCarrierLogicaNada extends JCarrierLogicaBase  implements ICarrierLogica {
  public void doCarrierLogica(String familia, BizPNRSegmentoAereo segmento) throws Exception {

		if (familia!=null) {
			segmento.setFamiliaTarifaria(familia == null ||familia.length() < 6 ? "" : familia.substring(3, 5));
		} else {
			segmento.setFamiliaTarifaria("");
			
		}
  }

 }
