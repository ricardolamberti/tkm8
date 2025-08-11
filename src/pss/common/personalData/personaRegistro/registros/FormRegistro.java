package  pss.common.personalData.personaRegistro.registros;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComboBox;

import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormRegistro extends JBaseForm {


private static final long serialVersionUID = 1218223408500L;

  JPssLabel ldescripcion = new JPssLabel();
  JPssEdit jId = new JPssEdit  ();
JPssEdit descripcion = new JPssEdit  ();
JPssLabel ltipo = new JPssLabel();
JComboBox tipo = new JComboBox  ();
/**
   * Constructor de la Clase
   */
  public FormRegistro() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiRegistro GetWin() { return (GuiRegistro) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(471, 101));


    ldescripcion.setText("Descripción");
    ldescripcion.setBounds(new Rectangle(16, 22, 87, 22)); 
    descripcion.setBounds(new Rectangle(112, 22, 323, 22)); 
    add(ldescripcion, null);
    add(descripcion , null);

    ltipo.setText("Tipo sociedad");
    ltipo.setBounds(new Rectangle(16, 22+27, 87, 22)); 
    tipo.setBounds(new Rectangle(112, 22+27, 323, 22)); 
    add(ltipo, null);
    add(tipo , null);

  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( jId, CHAR, OPT, "id" );
    AddItem( descripcion, CHAR, REQ, "descripcion" );
  //  AddItem( tipo, CHAR, OPT, "id_tipo_sociedad", new GuiTiposSociedad() );
   } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
