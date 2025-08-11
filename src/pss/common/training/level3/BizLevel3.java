package pss.common.training.level3;

import pss.core.graph.Graph;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JImage;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JMultiple;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;
import pss.www.platform.users.online.GuiOnlineUsers;

public class BizLevel3 extends JRecord {

  private JLong pId = new JLong();
  private JString pDescripcion = new JString();
  private JString pCombo = new JString();
  private JString pWinLov = new JString();
  private JDate pDesde = new JDate();
  private JDate pHasta = new JDate();
  private JString pListBox = new JString();
  private JMultiple pMulticheck = new JMultiple();
  private JBoolean pCheck = new JBoolean();
  private JImage pImagen = new JImage() {
 		public void preset() throws Exception {
 			pImagen.setValue(generateGraph1());;
 		};
 	};

  private JString pLabelData = new JString() {
 		public void preset() throws Exception {
 			pLabelData.setValue(pDescripcion.getValue());
 		};
 	};
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizLevel3() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "ddcombo", pCombo );
    this.addItem( "ddwinlov", pWinLov );
    this.addItem( "desde", pDesde );
    this.addItem( "hasta", pHasta );
    this.addItem( "listbox", pListBox );
    this.addItem( "multicheck", pMulticheck );
    this.addItem( "imagen", pImagen );
    this.addItem( "checker", pCheck );
    this.addItem( "labeldata", pLabelData );
    

  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "Id", false, false, 16 );
    this.addFixedItem( FIELD, "descripcion", "Usuario", true, true, 250 );
    this.addFixedItem( FIELD, "ddcombo", "dropdown combo", true, false, 250 );
    this.addFixedItem( FIELD, "ddwinlov", "dropdown Winlov", true, false, 250 );
    this.addFixedItem( FIELD, "desde", "fecha desde", true, false, 18 );
    this.addFixedItem( FIELD, "hasta", "fecha hasta", true, false, 18 );
    this.addFixedItem( FIELD, "listbox", "listbox", true, false, 500 );
    this.addFixedItem( FIELD, "multicheck", "multicheck", true, false, 500 );
    this.addFixedItem( VIRTUAL, "Imagen", "imagen", true, false, 500 );
    this.addFixedItem( VIRTUAL, "labeldata", "labeldata", true, false, 500 );
    this.addFixedItem( FIELD, "checker", "checker", true, false, 1 );
   }
  /**
   * Returns the table name
   */
  public String GetTable() { return "TRA_LEVEL3"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default Read() method
   */
  public boolean Read( String zIdgrupo ) throws Exception { 
    addFilter( "id",  zIdgrupo ); 
    return read(); 
  } 

	public String generateGraph1() throws Exception {
		
		JWins w = new GuiOnlineUsers();
		JWinList wl = new JWinList(w);
    w.ConfigurarColumnasLista(wl);
    w.ConfigurarGraficos(wl);
    w.ConfigurarFiltrosLista(wl);
    
		Graph g=wl.getGrafico(1);
		if (g!=null) {
			
			g.localFill(wl,null,null);
			g.setRefresh(1);
			return g.getImage(711, 342);
		}
		return null;
	}
}
