package  pss.common.regions.company;

import java.util.Iterator;

import pss.common.regions.multilanguage.JLanguage;
import pss.core.data.BizPssConfig;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;

public class JCompanyBusinessModules {

	public static final String DEFAULT="DEFAULT";
	public static final String TOURISM="TURISM";
	public static final String RETAIL="RETAIL";
	public static final String PYME="PYME";
	public static final String JUDICIAL="JUDICIAL";
	public static final String FLEET="FLEET";
	public static final String TRANSIT="TRANSIT";
	public static final String LEX="LEX";
	public static final String BSP="BSP";
	public static final String LEX_PASE="LEX_PASE";
	public static final String MDS="MDS";
	public static final String FCRT="FCRT";
	public static final String SYJ="SYJ";
	public static final String ROT="ROT";
	public static final String TURNOS="TURNOS";
	public static final String GAMESERVER="GAMESERVER";

	private static final String[][] aModulos= { 
		{ DEFAULT, "Default", "pss.common.regions.company.JCompanyBusinessDefault" },
		{ RETAIL, "Retail", "pss.erp.shop.company.JCompanyBusinessRetail" },
		{ PYME, "Pyme", "pss.pyme.company.JCompanyBusinessPyme" },
		{ TOURISM, "Turismo", "pss.tourism.company.JCompanyBusinessTourism" },
		{ JUDICIAL, "Judicial", "pss.judicial.JCompanyBusinessJudicial" },
		{ FLEET, "Flotas/HeadOffice", "pss.ho.company.JCompanyBusinessFleet" },
		{ TRANSIT, "Transit", "pss.ho.transit.company.JCompanyBusinessTransit" },
		{ LEX, "Lex", "pss.lex.lexBusiness.JCompanyBusinessLex" },
		{ BSP, "BSP", "pss.bsp.bspBusiness.JCompanyBusinessBSP" },
		{ TURNOS, "TURNOS", "pss.turnos.turnosBusiness.JCompanyBusinessTurnos" },
		{ LEX_PASE, "Lex Pase", "pss.lex.lexBusiness.pase.JCompanyBusinessLexPase" },
		{ MDS, "Desarrollo Social", "pss.mds.JCompanyBusinessMds" },
		{ FCRT, "Estacion de Servicio", "pss.forecourt.company.JCompanyBusinessFCRT"},
		{ ROT, "Rot", "pss.rot.JCompanyBusinessRot" },
		{ SYJ, "Judicial", "pss.sj.JCompanyBusinessSyJ" },
		{ GAMESERVER, "GAMESERVER", "pss.game.gameBusiness.JCompanyBusinessGame" }
		
		
	};
		
		
	private static JMap<String, JCompanyBusinessModule> mModulos=null;
	
	private static JMap<String, JCompanyBusinessModule> getModulosMap() throws Exception {
		if (mModulos!=null) return mModulos;
		cargarModulosMap();
		return mModulos;
	}

	private static synchronized void cargarModulosMap() throws Exception {
		if (mModulos!=null) return;
		JMap<String, JCompanyBusinessModule> map=JCollectionFactory.createMap();
		for(int i=0; i<aModulos.length; i++)
			try {
				Class.forName(aModulos[i][2]).newInstance();
				map.addElement(aModulos[i][0], new JCompanyBusinessModule(aModulos[i]));
			} catch (Throwable e) {
			}
		mModulos=map;
	}

	public static String getDescripcion(String zModulo) throws Exception {
		return JLanguage.translate(getModulosMap().getElement(zModulo).sDescripcion);
	}

	public static JRecords<BizVirtual> getJBDsForWins() throws Exception {
		JRecords<BizVirtual> oBDs=JRecords.createVirtualBDs();
		Iterator<JCompanyBusinessModule> oIter=getModulosMap().valueIterator();
		while (oIter.hasNext()) {
			JCompanyBusinessModule oMod=oIter.next();
			oBDs.addItem(JRecord.virtualBD(oMod.sModulo, oMod.sDescripcion, 1));
		}
		return oBDs;
	}

	public static JWins getWins() throws Exception {
		return JWins.CreateVirtualWins(getJBDsForWins());
	}
	
	

	static public class JCompanyBusinessModule {

		String sModulo="";
		String sDescripcion="";
		String sClase="";
		JCompanyBusiness oCompanyBusiness=null;

		public JCompanyBusinessModule(String[] vModulo) {
			sModulo=vModulo[0];
			sDescripcion=vModulo[1];
			sClase=vModulo[2];
		}

		public JCompanyBusiness getInstance() throws Exception {
			if (oCompanyBusiness==null) {
				oCompanyBusiness=(JCompanyBusiness) Class.forName(sClase).newInstance();
				oCompanyBusiness.setTipoBusiness(sModulo);
			}
			return oCompanyBusiness;
		}
	}

	public static JCompanyBusiness getInstanceFor(String zModulo) throws Exception {
		Iterator<JCompanyBusinessModule> oIter=getModulosMap().valueIterator();
		while (oIter.hasNext()) {
			JCompanyBusinessModules.JCompanyBusinessModule oModulo=oIter.next();
			if (oModulo.sModulo.toUpperCase().equals(zModulo.toUpperCase())) return oModulo.getInstance();
		}
		JExcepcion.SendError("Negocio no instalado");
		return null;
	}
	
	public static JCompanyBusiness createBusinessDefault() throws Exception {
		String businessDefault = BizPssConfig.getPssConfig().getBusinessDefault();
		return JCompanyBusinessModules.getInstanceFor(businessDefault);
	}


}
