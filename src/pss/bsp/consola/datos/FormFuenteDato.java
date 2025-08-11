package  pss.bsp.consola.datos;

import java.awt.Dimension;

import javax.swing.JTabbedPane;

import pss.core.win.JWin;
import pss.core.winUI.controls.JFormLista;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormFuenteDato extends JBaseForm {


private static final long serialVersionUID = 1245260233503L;

  /**
   * Propiedades de la Clase
   */
//JPssLabel lusuario = new JPssLabel();
//JPssEdit usuario = new JPssEdit  ();
//JPssLabel lusos = new JPssLabel();
//JPssEdit licencia = new JPssEdit  ();

private JTabbedPane jTabbedPane = null;


  /**
   * Constructor de la Clase
   */
  public FormFuenteDato() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiFuenteDato getWin() { return (GuiFuenteDato) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setSize(new Dimension(679, 361));
    setLayout(null);



//    lusuario.setText("Bienvenido/a");
//    lusuario.setBounds(new Rectangle(16, 16, 84, 22)); 
//    usuario.setBounds(new Rectangle(105, 16, 143, 22)); 
//    add(lusuario, null);
//    add(usuario , null);
//
//
//    lusos.setText( "Licencia" );
//    lusos.setBounds(new Rectangle(479, 16, 63, 22)); 
//    licencia.setBounds(new Rectangle(547, 16, 143, 22)); 
//    add(lusos, null);
//    add(licencia , null);
    this.add(getJTabbedPane());
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
//    AddItem( usuario, CHAR, REQ, "usuario" );
//    AddItem( licencia, CHAR, REQ, "licencia" );
  	JFormLista l;
  	
  	JFormTabPanelResponsive tabs=AddItemTabPanel();
  	tabs.AddItemList(10);
  		tabs.AddItemListOnDemand(15);
  	tabs.AddItemListOnDemand(20);
   	tabs.AddItemListOnDemand(500);
   	tabs.AddItemListOnDemand(170);
   	tabs.AddItemListOnDemand(175);
     	
  	tabs.AddItemListOnDemand(30);
  	tabs.AddItemListOnDemand(35);
  	tabs.AddItemListOnDemand(45);
   	tabs.AddItemListOnDemand(36);
   	tabs.AddItemListOnDemand(37);
    tabs.AddItemListOnDemand(38);
  	tabs.AddItemListOnDemand(120);
  	tabs.AddItemListOnDemand(39);
  	tabs.AddItemListOnDemand(130);
  	
     	
//    l=AddItem(getJTabbedPane(),10);
//    l=AddItem(getJTabbedPane(),15);
//    l=AddItem(getJTabbedPane(),20);
//    l=AddItem(getJTabbedPane(),30);
//    l=AddItem(getJTabbedPane(),35);
//    l=AddItem(getJTabbedPane(),36);
//    l=AddItem(getJTabbedPane(),38);
//    l=AddItem(getJTabbedPane(),120);
//    l=AddItemlist(getJTabbedPane(),39);

	  } 

  

/**
 * This method initializes jTabbedPane	
 * 	
 * @return javax.swing.JTabbedPane	
 */
private JTabbedPane getJTabbedPane() {
	if (jTabbedPane == null) {
		jTabbedPane = new JTabbedPane();
		jTabbedPane.setBounds(3,3,666,347);
		
	}
	return jTabbedPane;
}
@Override
public boolean isFullSize() throws Exception {
	return true;
}



}  //  @jve:decl-index=0:visual-constraint="10,10" 
