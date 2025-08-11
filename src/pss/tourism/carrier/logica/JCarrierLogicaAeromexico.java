package pss.tourism.carrier.logica;

import pss.bsp.familia.BizFamilia;
import pss.tourism.pnr.BizBooking;
import pss.tourism.pnr.BizPNRSegmentoAereo;

public class JCarrierLogicaAeromexico extends JCarrierLogicaBase  implements ICarrierLogica {
 
	public void doCarrierLogica(String familia, BizPNRSegmentoAereo segmento) throws Exception {
		
		if (familia!=null) {
				if ((segmento.getObjAeropuertoDespegue().getCountry().equalsIgnoreCase("US") && segmento.getObjAeropuertoArrivo().getCountry().equalsIgnoreCase("MX")) ||  
						(segmento.getObjAeropuertoDespegue().getCountry().equalsIgnoreCase("MX") && segmento.getObjAeropuertoArrivo().getCountry().equalsIgnoreCase("US"))
						) {
					segmento.setFamiliaTarifaria(familia == null ||familia.length() < 8 ? "" : "US"+familia.substring(5, 7));
					
				} else {
					segmento.setFamiliaTarifaria(familia == null ||familia.length() < 8 ? "" : "MX"+ familia.substring(6, 7));
					
				}
		} else {
			segmento.setFamiliaTarifaria("");
			
		}
  }
	
	 public boolean doCarrierLogica(String familia, BizBooking bok, BizPNRSegmentoAereo segmento) throws Exception {
		 String sOldFam= segmento.getFamiliaTarifaria();
		 if (familia!=null) {
			 if (bok!=null ) {
					if ((bok.getObjAeropuertoDespegue().getCountry().equalsIgnoreCase("US") && segmento.getObjAeropuertoDestino().getCountry().equalsIgnoreCase("MX")) ||  
							(bok.getObjAeropuertoDespegue().getCountry().equalsIgnoreCase("MX") && segmento.getObjAeropuertoDestino().getCountry().equalsIgnoreCase("US"))
							) {
						segmento.setFamiliaTarifaria(familia == null ||familia.length() < 8 ? "" : "US"+familia.substring(5, 7));
						
					} else {
						segmento.setFamiliaTarifaria(familia == null ||familia.length() < 8 ? "" : "MX"+ familia.substring(6, 7));
				 
			 }
				} else {
					
					if ((segmento.getObjAeropuertoDespegue().getCountry().equalsIgnoreCase("US") && segmento.getObjAeropuertoDestino().getCountry().equalsIgnoreCase("MX")) ||  
							(segmento.getObjAeropuertoDespegue().getCountry().equalsIgnoreCase("MX") && segmento.getObjAeropuertoDestino().getCountry().equalsIgnoreCase("US"))
							) {
						segmento.setFamiliaTarifaria(familia == null ||familia.length() < 8 ? "" : "US"+familia.substring(5, 7));
						
					} else {
						segmento.setFamiliaTarifaria(familia == null ||familia.length() < 8 ? "" : "MX"+ familia.substring(6, 7));
					}
				}
			} else {
				segmento.setFamiliaTarifaria("");
				
			}
			return !sOldFam.equals(segmento.getFamiliaTarifaria());
	 }

 }
