package pss.common.training.level3;

import pss.common.training.level3.sub.GuiSubLevels3;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormLevel3 extends JBaseForm {


private static final long serialVersionUID = 1218486819468L;

  /**
   * Propiedades de la Clase
   */
/**
   * Constructor de la Clase
   */
  public FormLevel3() throws Exception {
  }

  public GuiLevel3 GetWin() { return (GuiLevel3) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "id", LONG, OPT, "id" ).setVisible(false);
    AddItemEdit( "descripcion con refresh form", CHAR, REQ, "descripcion" ).setRefreshForm(true);
    AddItemDropDownCombo("Dropdown combo", CHAR, OPT, "ddcombo",new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		GuiSubLevels3 levels = new GuiSubLevels3();
    		levels.getRecords().addFilter("id", GetWin().GetcDato().getId());
    		return levels;
    	}
    });
    AddItemDropDownWinLov("Dropdown winlov", CHAR, OPT, "ddwinlov", new JControlWin() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		GuiSubLevels3 levels = new GuiSubLevels3();
    		levels.getRecords().addFilter("id", GetWin().GetcDato().getId());
    		return levels;
    	}
    });
    getInternalPanel().AddItemIntervalDateTime("interval", DATE, OPT, "desde","hasta");
    getInternalPanel().addButton("recargar",-1);
    getInternalPanel().AddItemListBox("listbox", CHAR, OPT, "listbox", new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		GuiSubLevels3 levels = new GuiSubLevels3();
    		levels.getRecords().addFilter("id", GetWin().GetcDato().getId());
    		return levels;
    	}
    }).setOnlyShowSelected(false);
    getInternalPanel().AddItemMultipleCheck("multi check", CHAR,OPT, "multicheck",  new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		GuiSubLevels3 levels = new GuiSubLevels3();
    		levels.getRecords().addFilter("id", GetWin().GetcDato().getId());
    		return levels;
    	}
    });;
    getInternalPanel().AddItemCheck("checker", OPT, "checker");
    getInternalPanel().AddItemImage("imagen","imagen");
    getInternalPanel().AddItemLine();
   
    AddItemLabel("Esto es un label");
    getInternalPanel().AddItemLabelData("un data label", CHAR, "labeldata");
    

    JFormTabPanelResponsive panels = AddItemTabPanel();
    panels.AddItemList(10);

  }

  @Override
  	public void checkControls(String sControlId) throws Exception {
  		// TODO Auto-generated method stub
  		super.checkControls(sControlId);
  	}
}  //  @jve:decl-index=0:visual-constraint="-391,-28"
