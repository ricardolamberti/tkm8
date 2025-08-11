package  pss.common.customList.config.owner;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComboBox;

import pss.common.security.GuiUsuarios;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;

public class FormCustomListOwner extends JBaseForm {


private static final long serialVersionUID = 1226426905817L;

JPssEdit company = new JPssEdit  ();
JPssEdit listId = new JPssEdit  ();
JPssEdit secuencia = new JPssEdit  ();
  private JPssEdit recordOwner = new JPssEdit();
//	private JPssEdit recordSet = new JPssEdit();

	private JPssLabel lRecordSet = null;

	private JComboBox jRelId = null;

	private JComboBox jClaveC = null;

	/**
   * Constructor de la Clase
   */
  public FormCustomListOwner() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiCustomListOwner getWin() { return (GuiCustomListOwner) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    lRecordSet = new JPssLabel();
    lRecordSet.setBounds(new Rectangle(17, 23, 71, 22));
    lRecordSet.setText("Usuario");
    setLayout(null);
    this.setSize(new Dimension(417, 394));


    this.add(lRecordSet, null);
    this.add(getJRecordSet(), null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, REQ, "company" );
    AddItem( listId, UINT, REQ, "list_id" );
    AddItem( secuencia, UINT, OPT, "secuencia" );
    AddItem( jRelId, CHAR, REQ, "usuario", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return getUsuario(one);
    	}
    });

  }
  @Override
  	public void OnPostShow() throws Exception {
  		super.OnPostShow();
  	}
  
  private JWins getUsuario(boolean one) throws Exception {
	  return new GuiUsuarios().addFilterAdHoc("company", getWin().GetcDato().getCompany());
  }

  /**
	 * This method initializes jRecordSet	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJRecordSet() {
		if (jRelId == null) {
			jRelId = new JComboBox();
			jRelId.setBounds(new Rectangle(100, 23, 298, 22));
		}
		return jRelId;
	}


}  //  @jve:decl-index=0:visual-constraint="-1,16"
