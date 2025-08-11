package pss.common.customList.config.informe;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComboBox;

import pss.common.customList.config.customlist.GuiCustomLists;
import pss.common.customList.config.field.campo.GuiCampo;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;

public class FormInforme extends JBaseForm {


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
  public FormInforme() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiInforme getWin() { return (GuiInforme) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    lRecordSet = new JPssLabel();
    lRecordSet.setBounds(new Rectangle(17, 23, 71, 22));
    lRecordSet.setText("Listado");
    setLayout(null);
    this.setSize(new Dimension(417, 394));


    this.add(lRecordSet, null);
    this.add(getJRecordSet(), null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null, UINT, REQ, "list_id" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "secuencia" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "informe" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "descripcion" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "orden" ).setHide(true);
    if (getWin().GetVision().equals("EMBEDDED"))
    	AddCardPanel(40).setWithActions(false);
    else
    	AddCardPanel(30).setDiferido(true).setWithActions(false);

  }
  @Override
  	public void OnPostShow() throws Exception {
  		super.OnPostShow();
  	}
  
  private JWins getInformes(boolean one) throws Exception {
	  return new GuiCustomLists().addFilterAdHoc("company", getWin().GetcDato().getCompany());
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
