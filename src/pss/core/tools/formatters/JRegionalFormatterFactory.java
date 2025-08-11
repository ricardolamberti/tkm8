package pss.core.tools.formatters;

/**
 * <p>
 * A factory of regional formatters.<br>
 * It knows how to instantiate either user-related data regional formatters or
 * currency-related data regional formatters.<br>
 * It also maintains a cache of formatters to create everyone only once.
 * </p>
 * <p>
 * This class provides static methods to create objects.
 * </p>
 *
 * @author Leonardo Pronzolino
 * @version 1.0.0
 */

import pss.common.regions.divitions.BizPais;
import pss.common.security.BizUsuario;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public abstract class JRegionalFormatterFactory {

	// ////////////////////////////////////////////////////////////////////////////
	//
	// STATIC VARIABLES
	//
	// ////////////////////////////////////////////////////////////////////////////
	// the map of regional data formatters by country
	private static JMap<String, JRegionalDataFormatter> oRegionalFormattersByCountry;
	// the map of business data formatters by country
	private static JMap<String, JMap<String, JBusinessDataFormatter>> oBusinessFormattersByCountry;

	// ////////////////////////////////////////////////////////////////////////////
	//
	// METHODS
	//
	// ////////////////////////////////////////////////////////////////////////////

	//
	// refresh methods
	//
	public static void refreshForCountry(String zCountryId) {
		// refresh regional data formatters
		getRegionalFormattersByCountry().removeElement(zCountryId);
		// refresh business data formatters
		JMap<String, JBusinessDataFormatter> formattersByBusinessCountry=getBusinessFormattersByCountry().getElement(zCountryId);
		if (formattersByBusinessCountry!=null) {
			formattersByBusinessCountry.removeElement(zCountryId);
		}
		getBusinessFormattersByCountry().removeElement(zCountryId);
	}

	public static void refreshForAllCountries() {
		// refresh regional data formatters
		getRegionalFormattersByCountry().removeAllElements();
		// refresh business data formatters
		JIterator<JMap<String, JBusinessDataFormatter>> oKeysIt=getBusinessFormattersByCountry().getValueIterator();
		while (oKeysIt.hasMoreElements()) {
			JMap formattersByBusinessCountry=oKeysIt.nextElement();
			if (formattersByBusinessCountry!=null) {
				formattersByBusinessCountry.removeAllElements();
			}
		}
		getBusinessFormattersByCountry().removeAllElements();
	}

	//
	// regional data formatters for the users
	//
	public static JRegionalDataFormatter getRegionalFormatter() throws Exception {
		return getRegionalFormatter(BizUsuario.getUsr().getObjBirthCountry());
	}

	//
	// absolute regional data formatters
	//
	public static JRegionalDataFormatter getAbsoluteRegionalFormatter() throws Exception {
		return getAbsoluteRegionalFormatter(BizUsuario.getUsr().getObjCountry());
	}

	private static JRegionalDataFormatter getAbsoluteRegionalFormatter(BizPais country) throws Exception {
		return getRegionalFormatter(country);
	}

	// public static JRegionalDataFormatter getAbsoluteRegionalFormatter(String zFormattingCountryId) throws Exception {
	// return getRegionalFormatter(resolveCountry(zFormattingCountryId));
	// }

	//
	// business formatters for the users
	//
	public static JBusinessDataFormatter getBusinessFormatter() throws Exception {
		return getBusinessFormatter(BizUsuario.getUsr().getObjBirthCountry(), BizUsuario.getUsr().getObjCountry());
	}

	public static JBusinessDataFormatter getBusinessFormatter(BizPais country) throws Exception {
		return getBusinessFormatter(BizUsuario.getUsr().getObjBirthCountry(), country);
	}

	public static JBusinessDataFormatter getBusinessFormatter(String countryId) throws Exception {
		return getBusinessFormatter(BizUsuario.getUsr().getObjBirthCountry(), resolveCountry(countryId));
	}

	//
	// absolute business formatters
	//
	public static JBusinessDataFormatter getAbsoluteBusinessFormatter() throws Exception {
		return getBusinessFormatter(BizUsuario.getUsr().getObjCountry(), BizUsuario.getUsr().getObjCountry());
	}

	//
	// generic regional data formatter provider, by formatting country
	//
	public static JRegionalDataFormatter getRegionalFormatter(BizPais zFormattingCountry) throws Exception {
		String sCountryId=zFormattingCountry.GetPais();
		JRegionalDataFormatter oFormatter=getRegionalFormattersByCountry().getElement(sCountryId);
		if (oFormatter==null) {
			oFormatter=new JRegionalDataFormatter(zFormattingCountry);
			getRegionalFormattersByCountry().addElement(sCountryId, oFormatter);
		}
		return oFormatter;
	}

	//
	// generic business formatter provider, by formatting country & business country
	//
	private static JBusinessDataFormatter getBusinessFormatter(BizPais userCountry, BizPais zBusinessCountry) throws Exception {
		// First of all, get the map of business country TO business formatter. There is one
		// of this maps for each formatting country.
		String userCountryId=userCountry.GetPais();
		JMap<String, JBusinessDataFormatter> formattersByBusinessCountry=getBusinessFormattersByCountry().getElement(userCountryId);
		if (formattersByBusinessCountry==null) {
			formattersByBusinessCountry=JCollectionFactory.createMap();
			getBusinessFormattersByCountry().addElement(userCountryId, formattersByBusinessCountry);
		}
		// Then, get the business formatter for the passed business country
		String sBusinessCountryId=zBusinessCountry.GetPais();
		JBusinessDataFormatter oFormatter=formattersByBusinessCountry.getElement(sBusinessCountryId);
		if (oFormatter==null) {
			oFormatter=new JBusinessDataFormatter(userCountry, zBusinessCountry);
			formattersByBusinessCountry.addElement(sBusinessCountryId, oFormatter);
		}
		return oFormatter;

	}

	//
	// formatters caches
	//
	private static JMap<String, JRegionalDataFormatter> getRegionalFormattersByCountry() {
		if (oRegionalFormattersByCountry==null) {
			oRegionalFormattersByCountry=JCollectionFactory.createMap();
		}
		return oRegionalFormattersByCountry;
	}

	private static JMap<String, JMap<String, JBusinessDataFormatter>> getBusinessFormattersByCountry() {
		if (oBusinessFormattersByCountry==null) {
			oBusinessFormattersByCountry=JCollectionFactory.<String, JMap<String, JBusinessDataFormatter>> createMap();
		}
		return oBusinessFormattersByCountry;
	}

	private static BizPais resolveCountry(String zCountryId) throws Exception {
		BizPais oPais=new BizPais();
		if (!oPais.Read(zCountryId)) JExcepcion.SendError("Pais inexistente: "+zCountryId);
		return oPais;
	}

}
