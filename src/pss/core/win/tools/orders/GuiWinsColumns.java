package pss.core.win.tools.orders;

import pss.core.services.records.JRecords;
import pss.core.tools.JPair;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.lists.JColumnaLista;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.applications.JHistoryProvider;

public class GuiWinsColumns extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiWinsColumns() throws Exception {
  }
  
	public int     GetNroIcono() throws Exception  { return 10032; } 
  public String  GetTitle()    throws Exception  { return "Ordenar"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiWinsColumn.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
  }

  transient JWins winsToOrder;
  transient String actionProvider;
	transient GuiWinsColumns objColumnas;
	
	public static GuiWinsColumns getOrders(JWins wins) throws Exception {
		String tab = JWebActionFactory.getCurrentRequest().getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "table_provider");
		if (tab!=null) {
			JHistoryProvider prov = JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().findHistoryProvider(tab);
			if (prov!=null && prov.getColumnsOrder()!=null ) {
				GuiWinsColumns cols = new GuiWinsColumns();
				cols.getRecords().setStatic(true);
				cols.setWinsToOrder(wins);
				long order=0;
				for (JPair<String,String> pair:prov.getColumnsOrder()) {
					GuiWinsColumn col = new GuiWinsColumn();
					col.GetcDato().setField(pair.fisrtObject);
					col.GetcDato().setOrdenAsc(pair.secondObject.equalsIgnoreCase("asc"));
					col.GetcDato().setOrden(order++);
					cols.addRecord(col);
				}
				cols.setActionProvider(tab);
							
				return cols;
			}
		}
		
		GuiWinsColumns orders = new GuiWinsColumns();
		orders.setWinsToOrder(wins);
		orders.setActionProvider(tab);
		orders.getRecords().setStatic(true);
		return orders;
	}
	public void copyInfo(GuiWinsColumns cols) throws Exception {
		setWinsToOrder(cols.getWinsToOrder());
		setActionProvider(cols.getActionProvider());		
	}
	public GuiWinsColumns getColumnsSelect() throws Exception {
		return this;
	}
  public GuiWinsColumns fillColumnsSource(BizAction a) throws Exception {
		if (objColumnas!=null) return objColumnas;
		JFormFiltro filters = new JFormFiltro(winsToOrder);
		filters.initialize();
		filters.applyFilterMap(a, false);
		winsToOrder.asignFiltersFromFilterBar(filters);

		JWinList listCols = new JWinList(winsToOrder);
  	listCols.setForExport(true);
  	winsToOrder.ConfigurarColumnasLista(listCols);
  	int orden=1;
  	GuiWinsColumns winCols = new GuiWinsColumns();
  	JRecords<BizWinsColumn> columnas = new JRecords<BizWinsColumn>(BizWinsColumn.class);
  	getRecords().convertToHash("field");
  	columnas.setStatic(true);
  	
  	JList<JColumnaLista> cols =listCols.GetColumnasLista();
  	JIterator<JColumnaLista> it = cols.getIterator();
  	while(it.hasMoreElements()) {
  		JColumnaLista col = it.nextElement();
  		if (col.GetCampo().trim().equals("")) continue;
  		if (!col.isSortable()) continue;
  		if (getRecords().findInHash(col.GetCampo())!=null) {
  			columnas.addItem((BizWinsColumn)getRecords().findInHash(col.GetCampo()));
  		} else {
    		BizWinsColumn wCol = new BizWinsColumn();
    		wCol.setOrden(orden++);
    		wCol.setField(col.GetCampo());
    		wCol.setDescripcion(col.GetColumnaTitulo());
    		wCol.setOrdenAsc(true);
    		columnas.addItem(wCol);
  			
  		}
  	}
  	columnas.convertToHash("field");
  	winCols.setRecords(columnas);
  	return objColumnas=winCols;
  }

	public String getActionProvider() {
		return actionProvider;
	}

	public void setActionProvider(String actionProvider) {
		this.actionProvider = actionProvider;
	}

	@Override
	public JAct Drop(JBaseWin zBaseWin) throws Exception {
		if (zBaseWin instanceof GuiWinsColumns) {
			GuiWinsColumns wins = (GuiWinsColumns) zBaseWin;
			this.setRecords(wins.getRecords());
		}
		return super.Drop(zBaseWin);
	}
	public JWins getWinsToOrder() {
		return winsToOrder;
	}

	public void setWinsToOrder(JWins winsToOrder) {
		this.winsToOrder = winsToOrder;
	}

	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}
}
