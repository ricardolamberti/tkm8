package  pss.common.customList.config.customlist;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComboBox;

import pss.common.security.GuiUsuarios;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormCustomListFav extends JBaseForm {


private static final long serialVersionUID = 1226426806993L;

  JPssEdit winOwner = new JPssEdit  ();
//JPssEdit recordOwner = new JPssEdit  ();
JPssEdit company = new JPssEdit  ();
JPssEdit listId = new JPssEdit  ();  //  @jve:decl-index=0:visual-constraint="530,11"
private JPssLabel lrecord_set1 = null;

private JComboBox jModelo = null;

/**
   * Constructor de la Clase
   */
  public FormCustomListFav() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiCustomListFav getWin() { return (GuiCustomListFav) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    lrecord_set1 = new JPssLabel();
    lrecord_set1.setBounds(new Rectangle(15, 17, 57, 22));
    lrecord_set1.setText("Usuario");
    setLayout(null);
    setSize(new Dimension(496, 115));


    this.add(lrecord_set1, null);
    this.add(getJModelo(), null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, REQ, "company" );
    AddItem( listId, UINT, OPT, "list_id" );
    AddItem( getJModelo(), CHAR, REQ, "usuario" , new GuiUsuarios());
 
  }
  


	/**
	 * This method initializes jModelo	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJModelo() {
		if (jModelo == null) {
			jModelo = new JComboBox();
			jModelo.setBounds(new Rectangle(79, 17, 143, 22));
		}
		return jModelo;
	} 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
