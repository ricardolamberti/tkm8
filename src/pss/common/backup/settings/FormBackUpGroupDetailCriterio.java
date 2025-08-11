package  pss.common.backup.settings;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;

public class FormBackUpGroupDetailCriterio extends JBaseForm {

  public FormBackUpGroupDetailCriterio() throws Exception {
  }

  public GuiBackUpGroupDetailCriterio getWin() { return (GuiBackUpGroupDetailCriterio) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "company", CHAR, REQ, "company" );
    AddItemEdit( "grupo", CHAR, REQ, "grupo" );
    AddItemEdit( "tabla", CHAR, REQ, "tabla" );
    AddItemEdit( "field_name1", CHAR, REQ, "field_name" );
    AddItemEdit( "operator1", CHAR, REQ, "operator" );
    AddItemEdit( "value1", CHAR, OPT, "value" );
    JFormControl oCtrl = AddItemCombo("type_of_field", "CHAR", REQ, "type_of_field", new JControlCombo() { @Override
  		public JWins getRecords(boolean zOneRow) throws Exception {return getTypes();};});
    oCtrl.SetValorDefault("COMP");    
  }
  
  private JWins getTypes() throws Exception {
  	JMap<String, String> map=JCollectionFactory.createOrderedMap();
  	map.addElement(BizBackUpGroupDetailCriterio.TYPE_COMPANY, "Empresa");
  	map.addElement(BizBackUpGroupDetailCriterio.TYPE_FDESDE, "Fecha Desde");
  	map.addElement(BizBackUpGroupDetailCriterio.TYPE_FHASTA, "Fecha Hasta");
  	map.addElement(BizBackUpGroupDetailCriterio.TYPE_RELATION, "Relación");
  	map.addElement(BizBackUpGroupDetailCriterio.TYPE_MANUAL, "Manual");
  	return JWins.createVirtualWinsFromMap(map,10031);
  }  
  
  
} 
