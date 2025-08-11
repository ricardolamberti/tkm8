package  pss.bsp.consolidador;

import pss.core.win.JWin;


public interface IConsolidador {
	public static final String ONLY_BSP = "Solo en BSP";
	public static final String ONLY_BO = "Solo en BO";
	public static final String ONLY_LIQ = "Solo en LIQ";
	public static final String OK = "Iguales";
	public static final String DISTINCT = "Distintos";

	public static final String BO = "BO";
	public static final String PNR = "PNR";

		public JWin getJWin();
}
