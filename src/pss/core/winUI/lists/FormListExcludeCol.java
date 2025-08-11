package pss.core.winUI.lists;

import pss.common.security.BizUsuario;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;

public class FormListExcludeCol extends JBaseForm {

	/**
	 * Constructor de la Clase
	 */
	public FormListExcludeCol() throws Exception {
	}

	public GuiListExcludeCol GetWin() {
		return (GuiListExcludeCol) getBaseWin();
	}

	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemEdit("company", "CHAR", "REQ", "company");
		AddItemEdit("className", "CHAR", "REQ", "class_name");
		AddItemCombo("colName", "CHAR", "REQ", "col_name", getColumns()).setRefreshForm(true);;
		AddItemEdit("colDesc", "CHAR", "REQ", "col_desc");
	}
	
	JMap<String,String> map = JCollectionFactory.createMap();

	private JWins getColumns() throws Exception {

		String clase = this.GetWin().GetcDato().getClassName();

		JWins wins = (JWins) Class.forName(clase).newInstance();

		wins.setSelectedCell("1");
		wins.getSelectedCell();
		JList<JColumnaLista> list = wins.getColWinList().GetColumnasLista();
		
		JIterator<JColumnaLista> it = list.getIterator();
	
		while (it.hasMoreElements()) {
			JColumnaLista col = it.nextElement();
			if ( BizListExcludeCol.findCol(BizUsuario.getUsr().getCompany(), clase, wins.GetVision(), col.GetCampo())==false)
			  map.addElement(col.GetCampo(), col.GetTitulo());
			
		}

		return JWins.createVirtualWinsFromMap(map);

	}

}
