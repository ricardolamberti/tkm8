package  pss.bsp.consola.dashBoard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import pss.common.customList.config.customlist.BizCustomList;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.ui.components.JPssImage;
import pss.core.win.JWin;
import pss.core.winUI.controls.JFormImage;
import pss.core.winUI.forms.JBaseForm;

public class FormDashBoard extends JBaseForm {


	private static final long serialVersionUID = 1245260233503L;

	  /**
	   * Propiedades de la Clase
	   */
	//JPssLabel lusuario = new JPssLabel();
	//JPssEdit usuario = new JPssEdit  ();
	//JPssLabel lusos = new JPssLabel();

	private JPssImage jTextField = null;

	private JPssImage jTextField1 = null;
	private JPssImage jTextField2 = null;
	private JPssImage jTextField3 = null;
	private JPssImage jTextField4 = null;
	private JPssImage jTextField5 = null;



	

	  /**
	   * Constructor de la Clase
	   */
	  public FormDashBoard() throws Exception {
	    try { jbInit(); }
	    catch (Exception e) { e.printStackTrace(); } 
	  }

	  public GuiDashBoard getWin() { return (GuiDashBoard) getBaseWin(); }

	  /**
	   * Inicializacion Grafica
	   */
	  protected void jbInit() throws Exception {
	    setSize(new Dimension(1070, 665));


	   
//	    lusuario.setText("Bienvenido/a");
//	    lusuario.setBounds(new Rectangle(16, 16, 84, 22)); 
//	    usuario.setBounds(new Rectangle(105, 16, 143, 22)); 
//	    add(lusuario, null);
//	    add(usuario , null);
	//
	//
//	    lusos.setText( "Licencia" );
//	    lusos.setBounds(new Rectangle(479, 16, 63, 22)); 
//	    licencia.setBounds(new Rectangle(547, 16, 143, 22)); 
//	    add(lusos, null);
//	    add(licencia , null);
	  }
	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {
//	    AddItem( usuario, CHAR, REQ, "usuario" );
//	    AddItem( licencia, CHAR, REQ, "licencia" );
	  		setAutoRefresh(true, 10000, "DASHBOARD");
	  		
	  		

		  	JRecords<BizCustomList> grps= getWin().GetcDato().getObjFavoritos();
		  	JIterator<BizCustomList> it = grps.getStaticIterator();
		  	int size=grps.getStaticItems().size();
		  	int tama[]= new int[100];
		  	if (size==1) tama=(new int[]{12});
		  	if (size==2) tama=(new int[]{6,6});
		  	if (size==3) tama=(new int[]{12,6,6});
		  	if (size==4) tama=(new int[]{12,4,4,4});
		  	if (size==5) tama=(new int[]{12,6,6,6,6});
		  	if (size==6) tama=(new int[]{12,6,6,4,4,4});
		  	if (size==7) tama=(new int[]{12,6,6,6,6,6,6});
		  	if (size>7) for(int j=0;j<size;j++) tama[j]=4;
		  	int tamaMD[]= new int[100];
		  	if (size==1) tamaMD=(new int[]{12});
		  	if (size==2) tamaMD=(new int[]{6,6});
		  	if (size==3) tamaMD=(new int[]{12,6,6});
		  	if (size==4) tamaMD=(new int[]{12,6,6,12});
		  	if (size==5) tamaMD=(new int[]{12,6,6,6,6});
		  	if (size==6) tamaMD=(new int[]{12,6,6,6,6,12});
		  	if (size==7) tamaMD=(new int[]{12,6,6,6,6,6,6});
		  	if (size>7) for(int j=0;j<size;j++) tamaMD[j]=6;
		  	int i=0;
		  	while (it.hasMoreElements()) {
		  		BizCustomList list = it.nextElement();
//			  	AddImageCard(list.getDescripcion(),"Ver mas",100+i,1).setSizeColumns(tama[i]).setHeight(300);
		  		AddCardPanel(200+i).setDiferido(true).setComplexColumnClass("clearfix col-"+(tama[i])+" col-xl-"+(tama[i])+" col-lg-"+(tamaMD[i])+" col-md-"+(tamaMD[i])+" col-sm-12) col-xs-12");;
			  	i++;
		  	}


		  } 


	@Override
	public boolean isFullSize() throws Exception {
		return true;
	}
	public boolean isFixHight() throws Exception {
		return false;
	}
	public boolean isFixWidth() throws Exception {
		return false;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JPssImage getJTextField() {
		if (jTextField == null) {
			jTextField = new JPssImage();
		}
		return jTextField;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JPssImage getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JPssImage();
		}
		return jTextField1;
	} 
	private JPssImage getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JPssImage();
			jTextField2.setName("jTextField2");
		}
		return jTextField2;
	}
	private JPssImage getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JPssImage();
			jTextField3.setName("jTextField3");
		}
		return jTextField3;
	}
	private JPssImage getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JPssImage();
			jTextField4.setName("jTextField4");
		}
		return jTextField4;
	}
	private JPssImage getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JPssImage();
			jTextField5.setName("jTextField5");
		}
		return jTextField5;
	}




	 
}