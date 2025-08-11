package pss.common.training.level1;

import java.util.Date;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;

public class FormLevel1 extends JBaseForm {


private static final long serialVersionUID = 1218486819468L;


  /**
   * Constructor de la Clase
   */
  public FormLevel1() throws Exception {
  }

  public GuiLevel1 GetWin() { return (GuiLevel1) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "Id", LONG, OPT, "id" ).setVisible(false);
    AddItemEdit( "Descripción", CHAR, REQ, "descripcion" );
    AddItemCombo( "Combo", CHAR, OPT, "combo" , new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		JMap<String,String> map = JCollectionFactory.createOrderedMap();
    		map.addElement("1", "Opcion 1");
    		map.addElement("2", "Opcion 2");
    		map.addElement("3", "Opcion 3");
    		map.addElement("4", "Opcion 4");
    		map.addElement("5", "Opcion 5");
    		return JWins.createVirtualWinsFromMap(map);
    	}
    });
    AddItemDateTime( "fecha", DATE, OPT, "fecha" ).SetValorDefault(new Date());
    AddItemDateTime( "fecha hora", DATETIME, OPT, "fechahora" ).SetValorDefault(new Date());
    AddItemDateTime( "hora", HOUR, OPT, "hora" );
    AddItemEdit( "numero", LONG, OPT, "numero" ).setPopupIcon(true);
    AddItemEdit( "flotante", FLOAT, OPT, "decimal" );

		AddItemRadio("Multi", CHAR, OPT, "multi", getOpciones()).SetValorDefault("A");
		
    AddItemArea("Texto", CHAR, OPT, "texto" );

  } 
  
  private JMap getOpciones() {
  	JMap map = JCollectionFactory.createMap();
  	map.addElement("A", "A");
  	map.addElement("B", "B");
  	map.addElement("C", "C");
  	return map;
  }

}  //  @jve:decl-index=0:visual-constraint="-382,-30"
