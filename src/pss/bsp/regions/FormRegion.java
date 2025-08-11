package  pss.bsp.regions;

import java.awt.Dimension;
import java.awt.Rectangle;

import pss.common.security.BizUsuario;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;
import javax.swing.JTabbedPane;

public class FormRegion extends JBaseForm {


private static final long serialVersionUID = 1245253307166L;

  /**
   * Propiedades de la Clase
   */
JPssLabel lpais = new JPssLabel();
JPssEdit pais = new JPssEdit  ();
JPssEdit company = new JPssEdit  ();
JPssEdit id = new JPssEdit  ();

private JTabbedPane jTabbedPane = null;
/**
   * Constructor de la Clase
   */
  public FormRegion() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiRegion getWin() { return (GuiRegion) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(595, 312));


    lpais.setText("Región");
    lpais.setBounds(new Rectangle(15, 22, 123, 22)); 
    pais.setBounds(new Rectangle(143, 22, 201, 22)); 
    add(lpais, null);
    add(pais , null);


    this.add(getJTabbedPane(), null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( pais, CHAR, REQ, "descripcion" );
    AddItem( company, CHAR, OPT, "company" ).SetValorDefault(BizUsuario.getUsr().getCompany());
    AddItem( id, CHAR, OPT, "id" );
    AddItem(getJTabbedPane(),10);
  }

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBounds(new Rectangle(14, 54, 574, 246));
		}
		return jTabbedPane;
	} 
}  //  @jve:decl-index=0:visual-constraint="24,19"
