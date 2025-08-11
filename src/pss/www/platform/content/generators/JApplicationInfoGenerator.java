/*
 * Created on 09-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.generators;

import pss.JPssVersion;
import pss.common.regions.multilanguage.JLanguage;
import pss.core.data.BizPssConfig;

/**
 * 
 * 
 * Created on 09-jun-2003
 * 
 * @author PSS
 */

public class JApplicationInfoGenerator extends JXMLComponentGenerator {

	@Override
	protected void doGenerate() throws Exception {
		this.startNode("application");
		this.setAttribute("name", JPssVersion.getPssTitle());
		this.setAttribute("version_info", JLanguage.translate("Versión")+": "+JPssVersion.getPssVersion());
		this.setAttribute("release_info", JLanguage.translate("Fecha release")+": "+JPssVersion.getPssDate());
		if (BizPssConfig.getPssConfig().hasGoogleMaps()) {
			String sGoogleKey=BizPssConfig.getPssConfig().getCachedValue("GOOGLE_MAPS", "KEY", "ABQIAAAAXW9Ge-JkQvsWrHsdEM5ughT42aBd2TDQoA4gzG0PdqlIyh6G7hTJV7B6weD_DWCl_ncFmCKF2GuSCg");
			this.setAttribute("google_map_key", sGoogleKey);
		}
		if (BizPssConfig.getPssConfig().hasGooglePay()) {
			this.setAttribute("google_pay", "S");
			this.setAttribute("google_pay_gateway", BizPssConfig.getPssConfig().getGooglePayGateway());
			this.setAttribute("google_pay_gatewayMerchantId", BizPssConfig.getPssConfig().getGooglePayGatewayMerchantId());
			this.setAttribute("google_pay_environment", BizPssConfig.getPssConfig().getGooglePayEnvironment());
		}	

		if (BizPssConfig.getPssConfig().hasMercadoPagoPay()) {
			this.setAttribute("mercadopago_pay", "S");
			this.setAttribute("mercadopago_pay_publickey", BizPssConfig.getPssConfig().getMercadoPagoPayPublicKey());
		}	
		
		this.endNode("application");
	}

	@Override
	protected String getBaseContentName() {
		return "application.info";
	}

}
