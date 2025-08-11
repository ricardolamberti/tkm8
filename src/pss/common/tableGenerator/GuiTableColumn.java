package pss.common.tableGenerator;

import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.icons.GuiIcon;



public class GuiTableColumn extends JWin {

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiTableColumn() throws Exception {
		SetItemDescrip("column_name");
		SetItemClave("column_name");
		SetNroIcono(GuiIcon.ADD_COLUMN_ICON);
    //SetDato((JBD)  Class.forName("pss.core.BDatos.Metadata.JColumna" + JBDatos.GetBases().GetBaseByName( JBDatos.GetBases().GetDatabaseDefault() ).GetDatabaseType()).newInstance() );
    //setRecord((JRecord)  Class.forName(JBDatos.getBaseMaster().getMetadataColumnImpl()).newInstance() );
    setRecord((JRecord)  Class.forName(JBDatos.getBaseMaster().getSystemDBFieldImpl()).newInstance() );
		setReadeable(false);
  }
 
	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}
}
