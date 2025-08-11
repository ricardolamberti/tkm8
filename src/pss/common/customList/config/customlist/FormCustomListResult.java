package pss.common.customList.config.customlist;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormCustomListResult  extends JBaseForm {


	private static final long serialVersionUID = 1226426905817L;


	  /**
	   * Constructor de la Clase
	   */
	  public FormCustomListResult() throws Exception {
	    try { jbInit(); }
	    catch (Exception e) { e.printStackTrace(); } 
	  }

	  public  GuiCustomList getWin() { return (GuiCustomList) getBaseWin(); }

	  /**
	   * Inicializacion Grafica
	   */
	  protected void jbInit() throws Exception {
//	    setLayout(new BorderLayout());
//	    setSize(new Dimension(1070, 665));
//
//			jLabel = new JLabel();
//			jLabel.setText("  Vista previa (solo 10 registros). Utilize Ejecutar para ver vista completa");
//			this.add(jLabel, BorderLayout.SOUTH);
//
//	    this.add(getJSplitPane(), BorderLayout.CENTER);
	  }
	  
	  @Override
	  public void OnShow() throws Exception {
//	  	jLabel.setVisible(getWin().GetVision().equals("PREVIEW"));
	  	super.OnShow();
	  }
	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {
//
//	  	JFormLista l;
//  	
//	  	if (!getWin().GetcDato().isInforme() ) {
//		  	if (getWin().GetcDato().isMatriz())
//			  	l=AddItemMatrix( 100); 
//		  	else
//		  		l=AddItemTab( 100); 
//
//		  	AddImageCard(null,"Ver mas",100,1).setSizeColumns(4).setHeight(300);
//		  	AddImageCard(null,"Ver mas",100,2).setSizeColumns(4).setHeight(300);
//		  	AddImageCard(null,"Ver mas",100,3).setSizeColumns(4).setHeight(300);
//		  	l.setNeedRefreshAllScreen(true);
//		  } else {;
//  	    JFormTabPanelResponsive panel = AddItemTabPanel();
//	  		int i =101;
//	  		JIterator<BizInforme> st = getWin().GetcDato().getObjInformes().getStaticIterator();
//	  		while (st.hasMoreElements()) {
//	  			BizInforme inf = st.nextElement();
//			  	l=panel.AddItemMatrix( i); 
//			  	i++;
//
// 		  	}
	  	AddItemEdit( null, CHAR,OPT, "company").setVisible(false);
	  	AddItemEdit( null, UINT,OPT, "list_id").setVisible(false);
//	    AddItemEdit( null, CHAR,OPT, "description" ).setVisible(false);
//	    AddItemEdit( null, CHAR,OPT, "record_owner" ).setVisible(false);
//	    AddItemEdit( null, CHAR,OPT, "rel_id" ).setVisible(false);
//	    AddItemEdit( null, CHAR,OPT, "agrupado" ).setVisible(false);
//	    AddItemEdit( null, CHAR,OPT, "modelo" ).setVisible(false);
//	    AddItemEdit( null, CHAR, OPT, "invisible" ).setVisible(false);
	//    setBackground("fondotkm.png");

	  	}

/*	  	
//  		JFormSplitPane c = this.AddItem(getJSplitPane()); 
	  	JFormLista l;
	  	
	  	if (!getWin().GetcDato().isInforme() ) {
		  	if (getWin().GetcDato().isMatriz())
			  	l=this.AddItemMatrix(getJTabbedPane(), 100); 
		  	else
		  		l=this.AddItem(getJTabbedPane(), 100); 

		  	JRecords<BizGraph> grps= getWin().getGraficos().getRecords();
		  	grps.toStatic();
		  	if (getWin().getMode()==GuiCustomList.ONLY_GRAPH && grps.sizeStaticElements()!=0){
		  		jSplitPane.setEstado(JPssSplitPane.MINIMIZE_A);
		  		jSplitPane.setEnabled(false);
		  	}
		  		
		  	if (grps.sizeStaticElements()==0 || getWin().getMode()==GuiCustomList.ONLY_TABLE) {
		  		jSplitPane.setEstado(JPssSplitPane.MINIMIZE_B);
		  		jSplitPane.setEnabled(false);
//					jTabbedPane.setBounds(new Rectangle(12, 17, 1020, 633));
		  	}else if (grps.sizeStaticElements()==1) {
			    JFormImage im=AddItem( getJTextField(), 100, 1 );
					jPanel.add(getJTextField(),BorderLayout.CENTER);
//			    im.setKeepWidth(true);
//			    im.setKeepHeight(true);
		  	}else if (grps.sizeStaticElements()==2) {
			    JFormImage im=AddItem( getJTextField(), 100, 1 );
					getJTextField().setBounds(new Rectangle(0, 0, 640, 320));
					jPanel.add(getJTextField(),BorderLayout.NORTH);
//			    im.setKeepWidth(true);
			    im=AddItem( getJTextField1(), 100, 2 );
	  			jPanel.add(getJTextField1(),BorderLayout.CENTER);
//			    im.setKeepWidth(true);
//			    im.setKeepHeight(true);
				} else if (grps.sizeStaticElements() > 2) {
					jPanel.setLayout(new GridLayout());
					JFormImage im = AddItem(getJTextField(), 100, 1);
					jPanel.add(getJTextField());
					im = AddItem(getJTextField1(), 100, 2);
					jPanel.add(getJTextField1());
					im = AddItem(getJTextField2(), 100, 3);
					jPanel.add(getJTextField2());
				}
		  	
//		  	if (getWin().GetVision().equals("RESULT")) {
//		  	l.setReadOnly(true);
//		  	if (this.isEmbedded()) l.setToolBar(false);
			  	
//		  	}		  		
		  	l.setKeepHeight(false);
		  	l.setKeepWidth(false);
		  	l.setNeedRefreshAllScreen(true);
		  } else {;
	  		int i =101;
	  		JIterator<BizInforme> st = getWin().GetcDato().getObjInformes().getStaticIterator();
	  		while (st.hasMoreElements()) {
	  			BizInforme inf = st.nextElement();
			  	if (inf.getObjCustomList().isMatriz())
				  	l=this.AddItemMatrix(getJTabbedPane(), i, inf.getDescripcion()); 
			  	else
			  		l=this.AddItem(getJTabbedPane(), i, inf.getDescripcion()); 

			  	l.setNeedRefreshAllScreen(true);
			  	i++;

 		  	}
	  		jSplitPane.setEstado(JPssSplitPane.MINIMIZE_B);
	  		jSplitPane.setEnabled(false);
	  	}

			//	  	if (l!=null) l.setKeepHeight(true);
//	  	if (l!=null) l.setKeepWidth(grps.sizeStaticElements()==0?true:false);
//	  	
*/
	  
	  
	  
	  
		
		public boolean hideTabArea() throws Exception {
			return !getWin().GetcDato().isInforme();
		}
		
//		public boolean hideToolbar() throws Exception {
//			return true;
//		}



	}  //  @jve:decl-index=0:visual-constraint="36,5"
