package pss.common.customDashboard2;

import pss.common.security.BizUsuario;
import pss.core.services.records.JRecords;
import pss.core.ui.components.JPssImage;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormImageCardResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.www.ui.JWebIcon;

public class FormDashBoardConfig extends JBaseForm {

	private static final long serialVersionUID = 1563965359048L;

	/**
	 * Constructor de la Clase
	 */
	public FormDashBoardConfig() throws Exception {
	}

	public GuiDashBoardConfig getWin() {
		return (GuiDashBoardConfig) getBaseWin();
	}

	/**
	 * Inicializacion Grafica
	 */
	/**
	 * Linkeo los campos con la variables del form
	 */
	public void InicializarPanel(JWin zWin) throws Exception {

		this.AddItemEdit("ID",CHAR,OPT,"ID").setHide(true);
		this.AddItemEdit("COMPANY",CHAR,OPT,"COMPANY").setHide(true);
		
		JFormPanelResponsive rowA = (JFormPanelResponsive) AddItemRow().setGutter(true);
		JFormColumnResponsive col0 = (JFormColumnResponsive) rowA.AddItemColumn().setSizeColumns(12);
		JFormPanelResponsive rowB = (JFormPanelResponsive) AddItemRow().setGutter(true);
		JFormColumnResponsive col1 = (JFormColumnResponsive) rowB.AddItemColumn().setSizeColumns(12);

		BizDashBoardConfig.clearDashBoard();

		addFilters(col0);

		this.getWin().GetcDato().initConfig();

		int isize = 2;

		JRecords<BizDashBoardConfig> cfgs = new JRecords<BizDashBoardConfig>(BizDashBoardConfig.class);
		cfgs.addFilter("company", BizUsuario.getUsr().getCompany());
		cfgs.addFilter("userid", BizUsuario.getUsr().GetUsuario());
		cfgs.addFilter("dash_name", this.getWin().GetcDato().getDashBoardName());
		cfgs.addOrderBy("DASH_ORDER");
		cfgs.toStatic();

		while (cfgs.nextRecord()) {
			BizDashBoardConfig cfg = cfgs.getRecord();
			if (cfg == null)
				continue;
			int idx = (int) cfg.getDashDescription();
			DashBoardData dbd = this.getWin().GetcDato().getData(idx);

			if (cfg.getExcluded())
				continue;
			if (dbd == null)
				continue;
			if (dbd.isImage()) {
				JFormImageCardResponsive img = col1.AddImageCard(dbd.getDescrip(), dbd.getField(), dbd.getActionDescrip(), dbd.getAction());
				img.setSource(JPssImage.SOURCE_PSSDATA);
				img.SetReadOnly(true);
			} else {
				addCard(col1, isize, dbd);
			}

		}

	}

	protected void addCard(JFormColumnResponsive col1, int isize, DashBoardData dbd) throws Exception {
		col1.AddInfoCard(dbd.getDescrip(), JBaseForm.CHAR, dbd.getField(), JWebIcon.getResponsiveIcon(dbd.getIcon()), dbd.getActionDescrip(), dbd.getAction())
				.setResponsiveClass(dbd.getLevel()).setComplexColumnClass("clearfix col-3 col-xl-3 col-lg-6 col-md-6 col-sm-12 col-xs-12").setSizeColumns(isize);
	}

	
	protected void addFilters(JFormColumnResponsive col0) throws Exception {
		
	}
	
	

	public boolean acceptDrop(String zone) throws Exception {
		return true;
	}

}
