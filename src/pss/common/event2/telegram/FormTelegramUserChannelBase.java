package pss.common.event2.telegram;

import pss.core.winUI.forms.JBaseForm;
import pss.core.win.JWin;
import pss.core.winUI.responsiveControls.JFormEditResponsive;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.core.winUI.responsiveControls.JFormCDatetimeResponsive;
import pss.core.win.JWins;
import pss.core.winUI.responsiveControls.JFormComboResponsive;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.common.security.BizUsuario;
import pss.common.regions.company.GuiCompanies;
public class FormTelegramUserChannelBase extends JBaseForm {


	private static final long serialVersionUID = 1686759227952L;



	/*
	* Constructor de la Clase
	*/
	public FormTelegramUserChannelBase() throws Exception {
	}

	public GuiTelegramUserChannel getWin() { return (GuiTelegramUserChannel) getBaseWin(); }

	/*
	* Linkeo los campos con la variables del form
	*/
	@Override
	public void InicializarPanel( JWin zWin ) throws Exception {
		JFormEditResponsive edit;
		JFormCheckResponsive chk;
		JFormCDatetimeResponsive date;
		JFormComboResponsive cmb;
		edit = AddItemEdit( BizTelegramUserChannelBase.D_ID,  UINT, OPT, BizTelegramUserChannelBase.ID );
		edit.hide();
		cmb = AddItemCombo( BizTelegramUserChannelBase.D_COMPANY, CHAR, OPT, BizTelegramUserChannelBase.COMPANY , new JControlCombo() { 
			@Override
			public JWins getRecords(boolean zOneRow) throws Exception {  return getCompanies(this.getFormControl()); } } );
		cmb.setSizeColumns(3);
		edit = AddItemEdit( BizTelegramUserChannelBase.D_USERID,  CHAR, OPT, BizTelegramUserChannelBase.USERID );
		edit.setSizeColumns(3);
		edit = AddItemEdit( BizTelegramUserChannelBase.D_CHANNEL_ID,  CHAR, OPT, BizTelegramUserChannelBase.CHANNEL_ID );
		edit.setSizeColumns(3);
		chk = AddItemCheck( BizTelegramUserChannelBase.D_REGISTERED, CHAR, OPT, BizTelegramUserChannelBase.REGISTERED , "S", "N" );
		chk.setSizeColumns(3);
		date = AddItemDateTime( BizTelegramUserChannelBase.D_DATE_REGISTERED,  DATETIME, OPT, BizTelegramUserChannelBase.DATE_REGISTERED );
		date.setSizeColumns(3);
		date = AddItemDateTime( BizTelegramUserChannelBase.D_DATE_CONFIRMED,  DATETIME, OPT, BizTelegramUserChannelBase.DATE_CONFIRMED );
		date.setSizeColumns(3);
		chk = AddItemCheck( BizTelegramUserChannelBase.D_CONFIRMED, CHAR, OPT, BizTelegramUserChannelBase.CONFIRMED , "S", "N" );
		chk.setSizeColumns(3);
		edit = AddItemEdit( BizTelegramUserChannelBase.D_CHANNEL_DESCRIPTION,  CHAR, OPT, BizTelegramUserChannelBase.CHANNEL_DESCRIPTION );
		edit.setSizeColumns(3);
		edit = AddItemEdit( BizTelegramUserChannelBase.D_PIN,  UINT, OPT, BizTelegramUserChannelBase.PIN );
		edit.setSizeColumns(3);
		chk = AddItemCheck( BizTelegramUserChannelBase.D_ADMIN_USER, CHAR, OPT, BizTelegramUserChannelBase.ADMIN_USER , "S", "N" );
		chk.setSizeColumns(3);
		chk = AddItemCheck( BizTelegramUserChannelBase.D_RESET_USER, CHAR, OPT, BizTelegramUserChannelBase.RESET_USER , "S", "N" );
		chk.setSizeColumns(3);
	}

   public JWins getCompanies(JFormControl control) throws Exception {
      GuiCompanies w = new GuiCompanies(); 
      w.getRecords().addOrderBy("company");
      return w; 

   }




}
