package  pss.common.backup.settings;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormControlResponsive;

public class FormBackUpGroupDetail extends JBaseForm {

  public FormBackUpGroupDetail() throws Exception {
  }

  public GuiBackUpGroupDetail getWin() { return (GuiBackUpGroupDetail) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormControlResponsive oCtrl = AddItemEdit( "company", CHAR, REQ, "company" );
  	oCtrl.setVisible(false);
  	oCtrl = AddItemEdit( "grupo", CHAR, REQ, "grupo" );
    oCtrl.setVisible(false);
    AddItemEdit( "tabla", CHAR, REQ, "tabla" );
    oCtrl = AddItemCombo("type", "CHAR", REQ, "type", new JControlCombo() {
    	@Override
  		public JWins getRecords(boolean zOneRow) throws Exception {
    		return getTypes();
    	};
    });
    oCtrl.SetValorDefault("ALL");
    AddItemCheck( "isDetail", REQ, "is_detail").SetValorDefault(false);
    AddItemEdit( "header", CHAR, OPT, "header_table" );
    this.AddItemTabPanel().AddItemTab(10);
  }
  
	@Override
	public void checkControls(String controlId) throws Exception {
		if (this.valueControl("is_detail") == "N") {
			this.findControl("header_table").setValue("");
		}
		super.checkControls(controlId);
	}
  
  private JWins getTypes() throws Exception {
  	JMap<String, String> map=JCollectionFactory.createOrderedMap();
  	map.addElement("ALL", "Toda la Tabla");
  	map.addElement("INC", "Incremental");
  	return JWins.createVirtualWinsFromMap(map,10031);
  }  
  
}
