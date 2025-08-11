package pss.common.event2.telegram;

import pss.core.win.JWin;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.lists.JColumnaLista;
import pss.core.winUI.lists.JFormFiltro;
import java.util.Date;
import pss.core.tools.JDateTools;
import pss.core.winUI.responsiveControls.JFormIntervalCDatetimeResponsive;
import pss.core.win.JWins;
import pss.core.winUI.responsiveControls.JFormComboResponsive;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.common.security.BizUsuario;
import pss.common.regions.company.GuiCompanies;
import pss.core.win.JWins;

public class GuiTelegramUserChannelsBase extends JWins {



	/*
	* Constructor de la Clase
	*/
	public GuiTelegramUserChannelsBase() throws Exception {
	}


	public static int ICON = 1116;
	public int     GetNroIcono() throws Exception  { return ICON; } 
	public String  GetTitle()    throws Exception  { return "Canales de telegram"; }
	public Class<? extends JWin> GetClassWin() { return GuiTelegramUserChannel.class; }
	/*
	* Mapeo las acciones con las operaciones
	*/
	public void createActionMap() throws Exception {
		addActionNew( 1, "Nuevo Registro" );
	}



	/*
	* Configuro las columnas que quiero mostrar en la grilla
	*/
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		JColumnaLista list=null;
		zLista.AddIcono("");
		zLista.AddColumnaLista(BizTelegramUserChannel.ID).setVisible(false);
		list = zLista.AddColumnaLista(BizTelegramUserChannel.COMPANY);
		list = zLista.AddColumnaLista(BizTelegramUserChannel.USERID);
		list = zLista.AddColumnaLista(BizTelegramUserChannel.CHANNEL_ID);
		list = zLista.AddColumnaLista(BizTelegramUserChannel.REGISTERED);
		list = zLista.AddColumnaLista(BizTelegramUserChannel.DATE_REGISTERED);
		list = zLista.AddColumnaLista(BizTelegramUserChannel.DATE_CONFIRMED);
		list = zLista.AddColumnaLista(BizTelegramUserChannel.CONFIRMED);
		list = zLista.AddColumnaLista(BizTelegramUserChannel.CHANNEL_DESCRIPTION);
		list = zLista.AddColumnaLista(BizTelegramUserChannel.PIN);
		list = zLista.AddColumnaLista(BizTelegramUserChannel.ADMIN_USER);
		list = zLista.AddColumnaLista(BizTelegramUserChannel.RESET_USER);
	}



	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception { 
		JFormControl cmb;
		cmb = zFiltros.addComboResponsive("Empresa", BizTelegramUserChannel.COMPANY, new JControlCombo() { 
			@Override
			public JWins getRecords(boolean zOneRow) throws Exception {  return getCompanies(this.getFormControl()); }; }, true  ); 
	}


   public JWins getCompanies(JFormControl control) throws Exception {
      GuiCompanies w = new GuiCompanies(); 
      w.getRecords().addOrderBy("company");
      return w; 

   }


}
