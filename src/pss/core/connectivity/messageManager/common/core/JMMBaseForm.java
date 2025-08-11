package pss.core.connectivity.messageManager.common.core;

import pss.core.winUI.forms.JBaseForm;


/**
 * Implementación base de formularios para el Message Manager. Extiende
 * {@link JBaseForm} agregando utilidades específicas para la configuración y
 * manejo de ventanas del subsistema de mensajería.
 */
public class JMMBaseForm extends JBaseForm {
//	private JTabbedPane jChildPanel = null;
//	private static int BORDER = 10;
//
//	/**
//   * 
//   */
//  private static final long serialVersionUID = 5551099018204986322L;
//  JMMWin mmwin;
//  
//	public void InicializarFixedPanel(JWin zWin) throws Exception {
//		if (!(zWin instanceof JMMWin)) return;
//		mmwin = (JMMWin) zWin;
//
//		for(IMMConfigurationSubscriptor u : mmwin.getMMBaseDato().getChilds().values()) {
//			JMMWin winchild = mmwin.getChildWin(u);
//			if (!winchild.isFullIntegred(mmwin)) continue;
//
//			JMMBaseForm form=	(JMMBaseForm)winchild.getFormPanel().newInstance();
//			form.SetWin(winchild);
//			form.SetModo(GetModo());
//			form.build();
//			JPssPanel panel = new JPssPanel();
//			Component[] comp = form.getComponents();
//			panel.setSize(form.getSize());
//			for (int c=0;c<comp.length;c++ ) {
//				panel.add(comp[c],null);
//			}
//			JIterator<JFormControl> controles = form.getControles().getAllItems();
//			while(controles.hasMoreElements()) {
//					JFormControl c = controles.nextElement();
//					getControles().AddControl(c);
//			}
//			form.setControles(getControles());
//			Rectangle rect = new Rectangle(BORDER,getMaxY(),Math.max(panel.getSize().width , getSize().width-(BORDER*2)),panel.getBounds().height+BORDER);
//			TitledBorder border=new TitledBorder(BorderFactory.createEtchedBorder(), winchild.GetTitle());
//			panel.setBorder(border);
//			panel.setBounds(rect);
//			
//			adjustSize(rect);
//			this.add(panel);
//		}
//
//		if (mmwin.fillExtendedPanel( this ))
//			if (!hasExtendedPanel()) {
//				add(getExtraExtendedPane(),null);
//			}
//	}
//	
//	private boolean hasExtendedPanel() throws Exception {
//		Component[] comp = this.getComponents();
//		for (int c=0;c<comp.length;c++ ) {
//			if (comp[c] instanceof JTabbedPane) return true;
//		}
//		return false;
//	}
//	private JTabbedPane getExtendedPanel() throws Exception {
//		Component[] comp = this.getComponents();
//		for (int c=0;c<comp.length;c++ ) {
//			if (comp[c] instanceof JTabbedPane) return (JTabbedPane)comp[c];
//		}
//		return getExtraExtendedPane();
//	}
//	
//	public void ReRead() throws Exception {
//		if ((oWin instanceof JMMWin)) {;
//			JMMWin mmwin = (JMMWin) oWin;
//			for(IMMConfigurationSubscriptor u : mmwin.getMMBaseDato().getChilds().values()) {
//				JMMWin winchild = mmwin.getChildWin(u);
//				ReRead(winchild);
//			}		
//		}
//		super.ReRead();
//	}
//
//	
//  protected void jbInit() throws Exception {
//  }
//  
//  public JFormLista addTabItem( BizAction act) throws Exception {
//  	return AddItem(getExtendedPanel(), act,null);
//  }
//  public JFormLista addTabItem( int act) throws Exception {
//  	return AddItem(getExtendedPanel(), act);
//  }
//  public JFormLista addTabItem( String act) throws Exception {
//  	return AddItem(getExtendedPanel(), act);
//  }
//
//  public JFormForm addTabFormItem( String act) throws Exception {
//  	return AddItemForm(getExtendedPanel(), act);
//  }
//
//  public JFormForm addTabFormItem( int act) throws Exception {
//  	return AddItemForm(getExtendedPanel(), act);
//  }
//
//  public JFormForm addTabFormItem( BizAction act) throws Exception {
//  	return AddItemForm(getExtendedPanel(), act);
//  }
//
//  private JTabbedPane getExtraExtendedPane() {
//		if (jChildPanel == null) {
//			jChildPanel = new JTabbedPane();
//			Rectangle rect = getPosExtendedPane(300);
//			jChildPanel.setBounds(rect);
//			adjustSize(rect);
//		}
//		return jChildPanel;
//	} 
//
//	private void adjustSize(Rectangle rect) {
//		if (this.getBounds().height<rect.y+rect.height) 
//		   setSize(new Dimension(this.getBounds().width,rect.y+rect.height+BORDER));
//		if (this.getBounds().width<rect.x+rect.width) 
//		   setSize(new Dimension(rect.x+rect.width+BORDER,this.getBounds().height));
//	}
//	
//	private Rectangle getPosExtendedPane(int zheight) {
//		Rectangle rect = new Rectangle();
//		int border = BORDER;
//		int height = zheight;
//		rect.x = border;
//		rect.y = getMaxY()+border;
//		rect.width = this.getBounds().width-(border*2);
//		rect.height = height;
//		return rect;
//	}
//	
//	private int getMaxY() {
//		Component[] comp = getComponents();
//		int maxY = 0;
//		for (int c=0;c<comp.length;c++ ) {
//			int y = comp[c].getBounds().y+comp[c].getBounds().height;
//			if (maxY<y) maxY = y;
//		}
//		return maxY;
//	}
	

}
