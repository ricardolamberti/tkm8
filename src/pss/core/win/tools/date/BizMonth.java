package pss.core.win.tools.date;

/**
 * Description: at GuiMonths.java
 * @author Iván Rubín
 */

import pss.core.services.fields.JInteger;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizMonth extends JRecord {

  private JInteger   pClave          = new JInteger();
  private JString    pDescripcion    = new JString();

  public JInteger getClave()         { return pClave; }
  public JString  getDescripcion()   { return pDescripcion; }

  public BizMonth() throws Exception {}
  public BizMonth( int zClave, String zDescripcion ) throws Exception {
    pClave.setValue( zClave );
    pDescripcion.setValue( zDescripcion );
  }

  @Override
	public void createProperties() throws Exception {
    addItem( "clave", pClave );
    addItem( "descripcion", pDescripcion );
  }

  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem( KEY, "clave", "Número", true, true, 6 );
    addFixedItem( FIELD, "descripcion", "Descripción", true, true, 6 );
  }

  @Override
	public String GetTable() { return ""; }

}
