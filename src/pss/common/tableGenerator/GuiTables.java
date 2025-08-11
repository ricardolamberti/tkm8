package pss.common.tableGenerator;

import pss.common.dbManagement.synchro.base.JBaseSystemDBTables;
import pss.core.services.fields.JObject;
import pss.core.services.records.JRecords;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.icons.GuiIcon;
import pss.core.winUI.lists.JFormFiltro;

public class GuiTables extends JWins {

  /**
   * Constructor de la clase
   */
	public GuiTables() throws Exception {
		SetNroIcono(GuiIcon.ALTER_TABLE_ICON);
		SetClassWin(GuiTable.class);
		SetTitle("Tablas del Sistema");
		SetItemDescrip(GetTitle());
	}
	
	
	@Override
	public JRecords ObtenerDatos() throws Exception { 
		return JBaseSystemDBTables.VirtualCreate(); 
	}
	
	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		JFormControl oControl;

    oControl = zFiltros.addEditResponsive("Tabla" , JObject.JSTRING, "table_name" , "LIKE" );
}   
	


}
