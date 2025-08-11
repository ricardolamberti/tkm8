package pss.core.win.tools.orders;

import pss.common.regions.multilanguage.JLanguage;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizWinsColumn extends JRecord {
	
	private JString pField = new JString();
	private JString pDescripcion = new JString();
	private JString pDescripcionToShow = new JString() {
		public void preset() throws Exception {
			pDescripcionToShow.setValue(pDescripcion.getValue()+" - "+JLanguage.translate(pOrderAsc.getValue()?"ASC":"DESC"));;
		};
	};
	private JBoolean pOrderAsc = new JBoolean();
	private JLong pOrden = new JLong();


	public void setField(String zVal) {	pField.setValue(zVal);	}
	public void setDescripcion(String zVal) {	pDescripcion.setValue(zVal);	}
	public void setOrdenAsc(boolean zVal) {	pOrderAsc.setValue(zVal);	}
	public void setOrden(long zVal) {	pOrden.setValue(zVal);	}
	
	public String getField() throws Exception { return pField.getValue();}
	public String getDescripcion() throws Exception { return pDescripcion.getValue();}
	public boolean getOrdenAsc() throws Exception { return pOrderAsc.getValue();}
	public long getOrden() throws Exception { return pOrden.getValue();}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Constructor de la Clase
   */
  public BizWinsColumn() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "field", pField );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "descripcion_show", pDescripcionToShow );
    this.addItem( "asc", pOrderAsc );
    this.addItem( "orden", pOrden );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "field", "Campo", true, true, 200 );
    this.addFixedItem( FIELD, "descripcion", "Campo", true, false, 200 );
    this.addFixedItem( FIELD, "descripcion_show", "Campo", true, false, 300 );
    this.addFixedItem( FIELD, "asc", "Ascendente", true, false, 1 );
    this.addFixedItem( FIELD, "orden", "Orden", true, false, 18 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return ""; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////



}
