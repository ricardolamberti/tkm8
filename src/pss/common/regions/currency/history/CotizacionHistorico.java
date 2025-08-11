package pss.common.regions.currency.history;

import java.util.Date;

import pss.core.tools.collections.JMap;

public class CotizacionHistorico {
	public CotizacionHistorico() {
		// TODO Auto-generated constructor stub
	}
	JMap<Date,Cotizacion> history;
	Date fechaIni;
	Cotizacion cotizIni;
	Date fechaFin;
	Cotizacion cotizFin;
}