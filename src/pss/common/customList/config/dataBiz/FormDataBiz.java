package  pss.common.customList.config.dataBiz;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComboBox;

import pss.common.security.GuiUsuarios;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.ui.components.JScrollableTextArea;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormSwingTextArea;
import pss.core.winUI.forms.JBaseForm;
import javax.swing.JLabel;

public class FormDataBiz extends JBaseForm {


private static final long serialVersionUID = 1226426905817L;

JPssEdit campo = new JPssEdit  ();
JScrollableTextArea valor = new JScrollableTextArea  ();
JPssEdit descripcion = new JPssEdit  ();
private JLabel lblCampo;
private JLabel lblDescripcion;
private JLabel lblValor;

	/**
   * Constructor de la Clase
   */
  public FormDataBiz() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiDataBiz getWin() { return (GuiDataBiz) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    this.setSize(new Dimension(391, 615));

    campo.setBounds(91, 36, 100, 23);
    descripcion.setBounds(91, 70, 100, 23);
    valor.setBounds(91, 104, 285, 500);
    this.add(campo);
    this.add(valor);
    this.add(descripcion);
    
    lblCampo = new JLabel("Campo");
    lblCampo.setBounds(10, 40, 46, 14);
    add(lblCampo);
    
    lblDescripcion = new JLabel("Descripcion");
    lblDescripcion.setBounds(10, 74, 71, 14);
    add(lblDescripcion);
    
    lblValor = new JLabel("Valor");
    lblValor.setBounds(10, 108, 71, 14);
    add(lblValor);

  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( campo, CHAR, OPT, "campo" ).SetReadOnly(true);
    AddItem( descripcion, CHAR, OPT, "descripcion" ).SetReadOnly(true);
    JFormControl c = AddItem( valor.getTextArea(), CHAR, OPT, "valor" );
    c.SetReadOnly(true);
    c.setKeepHeight(true);
    c.setKeepWidth(true);
  }
}  //  @jve:decl-index=0:visual-constraint="-1,16"
