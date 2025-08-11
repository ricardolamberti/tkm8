package pss.core.winUI.lists;

import java.awt.Rectangle;

import pss.core.tools.collections.JIterator;
import pss.core.win.IControl;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.controls.IFormTable;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormRow;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIconos;
import pss.core.winUI.responsiveControls.IResizableView;
import pss.core.winUI.responsiveControls.JFormButtonResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormRowGridExpandResponsive;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.ui.JWebIcon;

public class JWinGridExpand extends JWinList {
	public static final String GRID1ROW    = "GR";
	public static final String GRIDROW    = "GM";
	public static final String GRIDCOLUMN = "GC";
	public static final String GRID2PAR1IMPAR = "GA";
	public static final String GRIDFREE = "FR";
	
	protected String serializedWins = null;
	protected IFormTable formTable;
	boolean responsive;
	boolean expanded;
	String model;

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean resizable) {
		this.expanded = resizable;
	}

	public boolean isResponsive() {
		return responsive;
	}

	public void setResponsive(boolean responsive) {
		this.responsive = responsive;
	}

	public IFormTable getFormTable() {
		return formTable;
	}

	public void setFormTable(IFormTable formTable) {
		this.formTable = formTable;
	}

	public JWinGridExpand(JWins zWins) throws Exception {
		super(zWins);
	}

	public JWinGridExpand(JWins wins, IControl zControlWins) throws Exception {
		super(wins, zControlWins);
	}

	public JFormControl buildNew( BizAction oAction) throws Exception {
		JFormButtonResponsive mas = new JFormButtonResponsive();
		mas.setAction(oAction);
		mas.setImagen(JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,oAction.GetNroIcono()));//JWebIcon.getResponsiveIcon("fa fa-plus-circle fa-5x")
		// mas.setLabel("MAS");
		return mas;
	}

	public JFormControl buildRow(int rowpos, JWin win, long estado, BizAction oAction, String modo, boolean resizable) throws Exception {
		JBaseForm form;
		if (oAction == null)
			form = win.buildLocalForm(modo);
		else {
			oAction.setRowId(""+rowpos);
			JAct act = win.getSubmit(oAction);
			JAct owner = getBaseForm().getOwnerAction();
			if (owner.getWinLovRowId()!=null && owner.getWinLovRowId().indexOf("_row_"+rowpos+"_")!=-1)
				act.markFieldChanged(owner.getFieldChangeProvider(), owner.getWinLovRowId(), owner.getFieldChanged());
			form = act.getFormEmbedded();
			form.build();

		}
		form.setIdRow(""+rowpos);
		form.Do(baseForm.GetModo());
		JFormRowGridExpandResponsive formRow = new JFormRowGridExpandResponsive();
		formRow.setIdRow(rowpos);
		formRow.setVisible(!win.isHide());
		formRow.setExpanded(isExpanded());
		win.getRecord().setRowId(""+rowpos);
		formRow.setEstado(estado);
		formRow.setForm(form);
		String s = getFormTable().getCampo();
		if (JWebActionFactory.getCurrentRequest().getLevel() > 1)
			s+="__l"+JWebActionFactory.getCurrentRequest().getLevel();
		s+="_row_" + rowpos;

		formRow.setProvider(s);
		formRow.setTable(getFormTable());
		formRow.setWin(win);
		//formRow.setMaxHeight(500);
		formRow.setAction(oAction);
		formRow.setTitle(form.getTitle());
		formRow.setTitleRight(form.getTitleRight());
		formRow.setMode(win.getRowGridMode());
		formRow.setResizable(resizable);
		if (resizable && win.getRecord() instanceof IResizableView) {
			IResizableView obj = (IResizableView) win.getRecord();
			formRow.setRectangle(new Rectangle((int) obj.getPosX(), (int) obj.getPosY(), (int) obj.getWidth(), (int) obj.getHeight()));
		}

		formRow.getControles().AddControles(form);
		if (win.acceptDrop("row"))
			formRow.addDropManager("row", win);

		return formRow;

	}

	public void generate(JBaseForm mainform) throws Exception {
		super.generate(mainform);
		this.getWins().setParent(mainform.getBaseWin());

		if (getModel().equals(GRIDCOLUMN))
			new render3Column().render(mainform);
		else if (getModel().equals(GRID2PAR1IMPAR))
			new render2Cols1Row().render(mainform);
		else if (getModel().equals(GRID1ROW))
			new renderRows().render(mainform);
		else if (getModel().equals(GRIDROW))
			new renderMultiRows().render(mainform);
		else // GRIDFREE
			new renderGrid().render(mainform);

		if (mainform.GetModo().equals(JBaseForm.MODO_CONSULTA)) {
			getFormTable().disable();
		} else
			getFormTable().edit(JBaseForm.MODO_ALTA);
	}
	
	class render3Column extends renderGrid {
		public void render(JBaseForm mainform) throws Exception {
			JIterator<JWin> itw = getWins().getStaticIterator();
			int rowpos = 0;
			int count =getWins().getRecords().getStaticItems().size();
			if (count!=0) {
				int cols=1;
				if (count==2) cols=2;
				if (count==3) cols=3;
				if (count==4) cols=2;
				if (count>4) cols=3;
				long rows=count/cols;
				if (rows==0 ) rows=1;
				JFormPanelResponsive a = (JFormPanelResponsive) getFormTable();
				JFormPanelResponsive[] columnas = new JFormPanelResponsive[cols];
				for(int i=0;i<cols;i++) {
					columnas[i] = addSimpleColumn(a,(int)(12/cols));
				}
				int c = 0;
				while (itw.hasMoreElements()) {
					JWin win = itw.nextElement();
					JFormPanelResponsive r = addSimpleRow(columnas[c]);
					if (!addRow(r,rowpos,win,mainform,false))
						continue;
					rowpos++;
					c++;
					if (c>=cols) c=0;
						
					
				}
			}
			
			if (mustAddNew(mainform)) {
				addNewRow(rowpos++, mainform);
				addOtherNewRow(rowpos++, mainform);
			}
		}
	
	}
	class render2Cols1Row extends renderGrid {
		public void render(JBaseForm mainform) throws Exception {
			JIterator<JWin> itw = getWins().getStaticIterator();
			int rowpos = 0;
			int count =getWins().getRecords().getStaticItems().size();
			if (count!=0) {
				long cols=count==1?1:2;
				long rows=(count/cols)+(count/2);
				if (rows==0 ) rows=1;
				boolean exit=false;
				JFormPanelResponsive a = (JFormPanelResponsive) getFormTable();
				for(int j=0;j<rows&&!exit;j++) {
					JFormPanelResponsive r = addSimpleRow(a);
					for(int i=0;i<cols&&!exit;i++) {
						boolean impar = j%2!=0;
						if (!itw.hasMoreElements()) {
							exit=true;
							break;
						}
						JFormPanelResponsive c = addSimpleColumn(r,impar?12:(int)(12/cols));
						JWin win = itw.nextElement();
						if (!addRow(c,rowpos,win,mainform,false)) {
							continue;
						}
						rowpos++;
					  if (impar) break;
						
					}
				}
			}
			
			if (mustAddNew(mainform)) {
				addNewRow(rowpos, mainform);
				addOtherNewRow(rowpos++, mainform);
			}

		}
	
	}
	class renderRows extends renderGrid {
		public void render(JBaseForm mainform) throws Exception {
			JIterator<JWin> itw = getWins().getStaticIterator();
			int rowpos = 0;
			
			while (itw.hasMoreElements()) {
				JWin win = itw.nextElement();
				if (!addRow(null,rowpos,win,mainform, false))
					continue;
				rowpos++;
			}
			
			if (mustAddNew(mainform)) {
				addNewRow(rowpos, mainform);
				addOtherNewRow(rowpos++, mainform);
			}


		}
	
	}
	class renderMultiRows extends renderGrid {
		public void render(JBaseForm mainform) throws Exception {
			JIterator<JWin> itw = getWins().getStaticIterator();
			int rowpos = 0;
			int count =getWins().getRecords().getStaticItems().size();
			if (count!=0) {
				int rows=1;
				if (count==2) rows=2;		
				if (count==3) rows=2;		
				if (count==4) rows=2;		
				if (count>4) rows=(int)Math.ceil(count/3);
				int cols=(int)Math.ceil(count/rows);
				Integer colSize[]=new Integer[rows];
				JFormPanelResponsive a = (JFormPanelResponsive) getFormTable();
				JFormPanelResponsive[] filas = new JFormPanelResponsive[rows];
				int restantes = count;
				for(int i=0;i<rows;i++) {
					filas[i] = addSimpleRow(a);
					colSize[i]=(restantes>cols)?cols:restantes;
					restantes-=colSize[i];
				}
				int f = 0;
				while (itw.hasMoreElements()) {
					JWin win = itw.nextElement();
					JFormPanelResponsive r = addSimpleColumn(filas[f],(int)(12/colSize[f]));
					if (!addRow(r,rowpos,win,mainform,false))
						continue;
					rowpos++;
					f++;
					if (f>=rows) f=0;
						
					
				}
			}
			
			if (mustAddNew(mainform)) {
				addNewRow(rowpos, mainform);
				addOtherNewRow(rowpos++, mainform);
			}

		}
	
	}
	class renderGrid {
		long lStatus;
		String modo;
		boolean ok;
		boolean hayNew = false;

		public void render(JBaseForm mainform) throws Exception {
			JIterator<JWin> itw = getWins().getStaticIterator();
			int rowpos = 0;
			
			while (itw.hasMoreElements()) {
				JWin win = itw.nextElement();
				if (!addRow(null,rowpos,win,mainform, true))
					continue;
				rowpos++;
			}
			
			if (mustAddNew(mainform)) {
				addNewRow(rowpos, mainform);
				addOtherNewRow(rowpos++, mainform);
			}

		}
		JFormPanelResponsive addSimpleRow(JFormPanelResponsive parent) throws Exception {
			return parent.AddItemRow();
		}
		JFormPanelResponsive addSimpleColumn(JFormPanelResponsive parent,int size) throws Exception {
			return (JFormPanelResponsive)parent.AddItemColumn().setComplexColumnClass("col-xs-"+size);
		}
		boolean addRow(JFormPanelResponsive parent,int rowpos,JWin win,JBaseForm mainform, boolean free) throws Exception {
			fillStatus(win);
			if (ignore())	return false;
			fillModo(win,mainform);
			if (parent==null)
				getFormTable().AddItemRow(buildRow(rowpos, win, lStatus, getActionUpdate(win), modo, free));
			else
				parent.AddItemRow(buildRow(rowpos, win, lStatus, getActionUpdate(win), modo, free));
			return true;
		}
		void addNewRow(int rowpos,JBaseForm mainform) throws Exception {
			BizAction oAction = getActionNew();
			if (oAction!=null && getWins().OkAction(oAction)) {
				getFormTable().AddItemRow(buildNew(oAction));
			}
		}
		void addOtherNewRow(int rowpos,JBaseForm mainform) throws Exception {
			BizAction oAction = getOtherActionNew();
			if (oAction!=null && getWins().OkAction(oAction)) {
				getFormTable().AddItemRow(buildNew(oAction));
			}
		}
		
		void fillStatus(JWin win) throws Exception {
			win.getRecord().setStatic(true);
			String status = win.getRecord().getExtraData("internal_estado_fila");
			if (JFormRow.isEmpty(status))
				lStatus = JFormRow.UPDATE;
			else
				lStatus = JFormRow.getSimpleStatus(status);
			if (lStatus == JFormRow.NEW)
				hayNew = true;

		}
		
		void fillModo(JWin win,JBaseForm mainform) throws Exception {
		  modo = !mainform.GetModo().equals(JBaseForm.MODO_CONSULTA) && isEnabled() ? JBaseForm.MODO_MODIFICACION : JBaseForm.MODO_CONSULTA;
			if (lStatus == JFormRow.DELETE) {
				modo = JBaseForm.MODO_CONSULTA;
				ok = false;
			} else if (mainform.GetModo().equals(JBaseForm.MODO_CONSULTA) || !ok) {
				lStatus = JFormRow.NOEDIT;
				modo = JBaseForm.MODO_CONSULTA;
				ok = false;
			}
		}		
		BizAction getActionUpdate(JWin win) throws Exception {
			BizAction oAction = win.findAction(getFormTable().getUpdateAction());
			ok = (oAction != null && win.OkAction(oAction));
			return oAction;
		}		
		BizAction getActionNew() throws Exception {
			BizAction oAction = null;
			if (getFormTable().getNewAction() != -1) {
				oAction = getBaseForm().getBaseWin().findAction(getFormTable().getNewAction());
				if (oAction != null && !getBaseForm().getBaseWin().OkAction(oAction))
					oAction = null;
			} else {
				oAction = getWins().findAction(1);
				if (oAction != null && !getWins().OkAction(oAction))
					oAction = null;
			}	
			return oAction;

		}		
		BizAction getOtherActionNew() throws Exception {
			BizAction oAction = null;
			if (getFormTable().getOtherNewAction() != -1) {
				oAction = getBaseForm().getBaseWin().findAction(getFormTable().getOtherNewAction());
				if (oAction != null && !getBaseForm().getBaseWin().OkAction(oAction))
					oAction = null;
			}
			return oAction;

		}		
		boolean mustAddNew(JBaseForm mainform) throws Exception {
			return isEnabled() && !hayNew && !mainform.GetModo().equals(JBaseForm.MODO_CONSULTA);
		}		
		boolean ignore() throws Exception {
			return lStatus == JFormRow.DELETE;
		}		

		JWin getNewWin(BizAction oAction) throws Exception {
			if (oAction==null) return null;
			JWin win = null;
			win = oAction.getSubmit().getWinResult();
			win.SetVision(getWins().GetVision());
			win.getRecord().setStatic(true);
			return win;
		}		


	}

	public void ConfigurarFiltrosLista() throws Exception {
		this.getWins().ConfigurarFiltrosLista(this);
	}

}
