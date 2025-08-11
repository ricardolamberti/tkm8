package  pss.common.customList.config.customlist;

import java.awt.Dimension;
import java.awt.Rectangle;

import pss.common.customList.config.field.campo.GuiCampo;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.ui.components.JScrollableTextArea;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormSQL extends JBaseForm {


private static final long serialVersionUID = 1226426905817L;

JPssEdit company = new JPssEdit  ();
JPssEdit listId = new JPssEdit  ();
JPssEdit secuencia = new JPssEdit  ();
  private JPssEdit recordOwner = new JPssEdit();
//	private JPssEdit recordSet = new JPssEdit();

	private JPssLabel lfunc = null;

	private JScrollableTextArea jSQL = null;

	/**
   * Constructor de la Clase
   */
  public FormSQL() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiCampo getWin() { return (GuiCampo) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    lfunc = new JPssLabel();
    lfunc.setBounds(new Rectangle(17, 17, 39, 22));
    lfunc.setText("SQL");
    setLayout(null);
    setSize(new Dimension(630, 438));


    this.add(lfunc, null);
    this.add(getJSQL(), null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, REQ, "company" );
    AddItem( listId, UINT, REQ, "list_id" );
    AddItem( jSQL.getTextArea(), CHAR, OPT, "sql");
  }
  

	/**
	 * This method initializes jSQL	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JScrollableTextArea getJSQL() {
		if (jSQL == null) {
			jSQL = new JScrollableTextArea();
			jSQL.setBounds(new Rectangle(18, 44, 596, 375));
		}
		return jSQL;
	} 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
