package pss.common.tableGenerator;

import pss.common.dbManagement.synchro.base.JBaseSystemDBFields;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.records.JRecords;
import pss.core.win.JWins;
import pss.core.winUI.icons.GuiIcon;

public class GuiTableColumns extends JWins {

	public GuiTableColumns() throws Exception {
		SetNroIcono(GuiIcon.ADD_COLUMN_ICON);
		SetClassWin(GuiTableColumn.class);
		SetTitle("Columnas de la tabla");
		SetItemDescrip("column_name");
		String sName = 		JBDatos.getBaseMaster().getSystemDBFieldImpl();

//		String sName = JBDatos.getBaseMaster().getMetadataColumnImpl();
		JRecords oBDatos = JBaseSystemDBFields.VirtualCreate();
		setRecords(oBDatos);
		//    SetDatos(new JBDs( Class.forName("pss.core.BDatos.Metadata.JColumna" + JBDatos.GetBases().GetBaseByName( JBDatos.GetBases().GetDatabaseDefault() ).GetDatabaseType()) ));
	}
 

	
}
