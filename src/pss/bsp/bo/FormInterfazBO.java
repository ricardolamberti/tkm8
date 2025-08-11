package  pss.bsp.bo;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComboBox;
import javax.swing.JTabbedPane;

import pss.bsp.consola.typesLicense.ITKM;
import pss.common.security.BizUsuario;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormInterfazBO extends JBaseForm {


private static final long serialVersionUID = 1245254423061L;

  JPssEdit company = new JPssEdit  ();
JPssEdit owner = new JPssEdit  ();
JPssLabel lidInterfaz = new JPssLabel();
JPssEdit idInterfaz = new JPssEdit  ();
JPssLabel ldescripcion = new JPssLabel();
JPssEdit descripcion = new JPssEdit  ();
JPssLabel lestado = new JPssLabel();
JPssEdit estado = new JPssEdit  ();
JPssLabel lfecha_desde = new JPssLabel();
JPssEdit fecha_desde = new JPssEdit  ();
JPssLabel lfecha_hasta = new JPssLabel();
JPssEdit fecha_hasta = new JPssEdit  ();
JPssLabel ltipo_interfaz = new JPssLabel();
JPssEdit tipo_interfaz = new JPssEdit  ();
JPssLabel liata = new JPssLabel();
//JComboBox iata = new JComboBox();
JPssEdit iata = new JPssEdit();

private JTabbedPane jTabbedPane = null;


  /**
   * Constructor de la Clase
   */
  public FormInterfazBO() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiInterfazBO getWin() { return (GuiInterfazBO) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(589, 547));


    company.setBounds(new Rectangle(1, 13, 13, 22)); 
    add(company , null);


    owner.setBounds(new Rectangle(1, 40, 13, 22)); 
    add(owner , null);


    lidInterfaz.setText( "Idinterfaz" );
    lidInterfaz.setBounds(new Rectangle(30, 14, 123, 22)); 
    idInterfaz.setBounds(new Rectangle(158, 14, 143, 22)); 
    add(lidInterfaz, null);
    add(idInterfaz , null);


    ldescripcion.setText( "Descripcion" );
    ldescripcion.setBounds(new Rectangle(30, 41, 123, 22)); 
    descripcion.setBounds(new Rectangle(158, 41, 419, 22)); 
    add(ldescripcion, null);
    add(descripcion , null);


    lestado.setText( "Estado" );
    lestado.setBounds(new Rectangle(305, 13, 123, 22)); 
    estado.setBounds(new Rectangle(433, 13, 143, 22)); 
    add(lestado, null);
    add(estado , null);


    lfecha_desde.setText( "Fecha desde" );
    lfecha_desde.setBounds(new Rectangle(30, 67, 123, 22)); 
    fecha_desde.setBounds(new Rectangle(158, 67, 143, 22)); 
    add(lfecha_desde, null);
    add(fecha_desde , null);


    lfecha_hasta.setText( "Fecha hasta" );
    lfecha_hasta.setBounds(new Rectangle(307, 67, 123, 22)); 
    fecha_hasta.setBounds(new Rectangle(435, 67, 143, 22)); 
    add(lfecha_hasta, null);
    add(fecha_hasta , null);

    liata.setText( "IATA" );
    liata.setBounds(new Rectangle(306, 93, 123, 22)); 
    iata.setBounds(new Rectangle(434, 93, 143, 22)); 
    add(liata, null);
    add(iata , null);

    ltipo_interfaz.setText( "Tipo interfaz" );
    ltipo_interfaz.setBounds(new Rectangle(30, 94, 123, 22)); 
    tipo_interfaz.setBounds(new Rectangle(158, 94, 143, 22)); 
    add(ltipo_interfaz, null);
    add(tipo_interfaz , null);
    this.add(getJTabbedPane(), null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, REQ, "company" ).setVisible(false);
    AddItem( owner, CHAR, REQ, "owner" ).setVisible(false);
    AddItem( idInterfaz, UINT, REQ, "idInterfaz" );
    AddItem( descripcion, CHAR, REQ, "descripcion" );
    AddItem( estado, CHAR, REQ, "estado" );
    AddItem( fecha_desde, DATE, REQ, "fecha_desde" );
    AddItem( fecha_hasta, DATE, REQ, "fecha_hasta" );
    AddItem( tipo_interfaz, CHAR, REQ, "tipo_interfaz" );
//    AddItem( iata, CHAR, REQ, "iata" , ((ISitita)BizUsuario.getUsr().getObjLicense().getLicense()).getIATAs());
    AddItem( iata, CHAR, REQ, "iata" );
    AddItem( getJTabbedPane(),20);
    AddItemForm( getJTabbedPane(),10);
    AddItem( getJTabbedPane(),25);
  }

  /**
   * This method initializes jTabbedPane	
   * 	
   * @return javax.swing.JTabbedPane	
   */
  private JTabbedPane getJTabbedPane() {
  	if (jTabbedPane==null) {
  		jTabbedPane=new JTabbedPane();
  		jTabbedPane.setBounds(new Rectangle(31, 126, 543, 409));
  	}
  	return jTabbedPane;
  } 
}  //  @jve:decl-index=0:visual-constraint="8,18"
