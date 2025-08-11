package  pss.tourism.carrier.logica;

import pss.tourism.pnr.BizBooking;
import pss.tourism.pnr.BizPNRSegmentoAereo;

public interface ICarrierLogica {
  public void doCarrierLogica(String familia, BizPNRSegmentoAereo segmento) throws Exception;
  public boolean doCarrierLogica(String familia, BizBooking bok, BizPNRSegmentoAereo segmento) throws Exception;
 }
