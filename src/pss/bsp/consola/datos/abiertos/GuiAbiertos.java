package pss.bsp.consola.datos.abiertos;

import java.util.Date;

import pss.common.regions.company.GuiCompanies;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JObject;
import pss.core.tools.JDateTools;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.responsiveControls.JFormCDatetimeResponsive;
import pss.core.winUI.responsiveControls.JFormIntervalCDatetimeResponsive;
import pss.tourism.carrier.GuiCarriers;
import pss.tourism.pnr.GuiPNRTickets;

public class GuiAbiertos extends GuiPNRTickets {


	public GuiAbiertos() throws Exception {
		super();
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiAbierto.class;
	}


	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		if (BizUsuario.getUsr().isAdminUser())
			zFiltros.addComboResponsive("Agencia", "company", new GuiCompanies()).SetValorDefault(BizUsuario.getUsr().getCompany());


		zFiltros.addComboResponsive("Tipo documento", "tipo_operacion", new JControlCombo() {
			@Override
			public JWins getRecords(Object zSource, boolean zOneRow) throws Exception {
				return getTiposOperacion();
			}
		}, false).SetValorDefault("ETR");

		JFormIntervalCDatetimeResponsive d= zFiltros.addIntervalCDateResponsive("F.emisión", "creation_date");
		d.SetValorDefault(JDateTools.getFirstDayOfMonth(new Date()),new Date());
		
		JFormCDatetimeResponsive dv= zFiltros.addCDateResponsive("F.despegue posterior a", "departure_date");
		dv.setOperator(">=");
		dv.SetValorDefault(new Date());
		zFiltros.addWinLovResponsive("LA",  "CodigoAerolinea",new GuiCarriers().addOrderAdHoc("description", "asc")).setShowKey(true);
		zFiltros.addEditResponsive("Boleto", JObject.JSTRING, "NumeroBoleto");
		
	}
	public long selectSupraCount() throws Exception {
		return 0;
	}
}
