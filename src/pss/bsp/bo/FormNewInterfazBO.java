package  pss.bsp.bo;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import pss.bsp.bo.formato.GuiFormatos;
import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.consola.typesLicense.ITKM;
import pss.common.security.BizUsuario;
import pss.core.ui.components.JPssCalendarEdit;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.ui.components.JPssLabelFile;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormSwingCombo;
import pss.core.winUI.forms.JBaseForm;


public class FormNewInterfazBO extends JBaseForm {


	private static final long serialVersionUID = 1245253394589L;

	  JPssEdit company = new JPssEdit  ();
	JPssEdit owner = new JPssEdit  ();
	JPssEdit idPDF = new JPssEdit  ();
	JPssLabel ldescripcion = new JPssLabel();
	JPssEdit descripcion = new JPssEdit  ();
	JPssLabel lpdfFilename = new JPssLabel();
	JPssLabelFile pdfFilename = new JPssLabelFile  ();
	JPssLabel ltipo_interfaz = new JPssLabel();
	JComboBox tipo_interfaz = new JComboBox  ();
	JPssLabel lformato = new JPssLabel();
	JComboBox formato = new JComboBox  ();
	JPssLabel lfechaDesde = new JPssLabel();
	JPssCalendarEdit fechaDesde = new JPssCalendarEdit  ();
	JPssLabel lfechaHasta = new JPssLabel();
	JPssCalendarEdit fechaHasta = new JPssCalendarEdit  ();

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel11 = null;

	private JLabel jLabel12 = null;

	private JLabel jLabel121 = null;
	JPssLabel liata = new JPssLabel();
	JPssEdit iata = new JPssEdit();
//	JComboBox iata = new JComboBox();
	  /**
	   * Constructor de la Clase
	   */
	  public FormNewInterfazBO() throws Exception {
	    try { jbInit(); }
	    catch (Exception e) { e.printStackTrace(); } 
	  }

	  public GuiInterfazBO getWin() { return (GuiInterfazBO) getBaseWin(); }

	  /**
	   * Inicializacion Grafica
	   */
	  protected void jbInit() throws Exception {
	    jLabel121 = new JLabel();
	    jLabel121.setBounds(new Rectangle(155, 304, 325, 34));
	    jLabel121.setText("Escoja el periodo referenciado por la interfaz. ");
	    jLabel12 = new JLabel();
	    jLabel12.setBounds(new Rectangle(155, 236, 325, 30));
	    jLabel12.setText("Escoja el archivo con la interfaz");
	    jLabel11 = new JLabel();
	    jLabel11.setBounds(new Rectangle(155, 169, 325, 35));
	    jLabel11.setText("Si guardo una asignacion de columnas, aqui puede elegirla.\n Si tiene dudas mantega el  valor por defecto");
	    jLabel1 = new JLabel();
	    jLabel1.setBounds(new Rectangle(155, 104, 325, 34));
	    jLabel1.setText("Escoja el tipo de interfaz de su archivo de BackOffice.\n Si tiene duda mantenga el valor por defecto");
	    jLabel = new JLabel();
	    jLabel.setBounds(new Rectangle(155, 54, 325, 17));
	    jLabel.setText("Una Descripción de referencia");
	    setLayout(null);
	    setSize(new Dimension(504, 375));


	    company.setBounds(new Rectangle(3, 8, 8, 22)); 
	    add(company , null);


	    owner.setBounds(new Rectangle(3, 35, 8, 22)); 
	    add(owner , null);


	    idPDF.setBounds(new Rectangle(3, 62, 8, 22)); 
	    add(idPDF , null);


	    ldescripcion.setText( "Descripcion" );
	    ldescripcion.setBounds(new Rectangle(29, 28, 123, 22)); 
	    descripcion.setBounds(new Rectangle(155, 28, 325, 22)); 
	    add(ldescripcion, null);
	    add(descripcion , null);

	    
	    ltipo_interfaz.setText( "Tipo interfaz" );
	    ltipo_interfaz.setBounds(new Rectangle(29, 76, 123, 22)); 
	    tipo_interfaz.setBounds(new Rectangle(155, 76, 325, 22)); 
	    add(ltipo_interfaz, null);
	    add(tipo_interfaz , null);

	    lformato.setText( "Formato" );
	    lformato.setBounds(new Rectangle(29, 142, 123, 22)); 
	    formato.setBounds(new Rectangle(155, 142, 325, 22)); 
	    add(lformato, null);
	    add(formato , null);

	    lpdfFilename.setText( "Archivo Interfaz:" );
	    lpdfFilename.setBounds(new Rectangle(29, 211, 123, 22)); 
	    pdfFilename.setBounds(new Rectangle(155, 211, 325, 22)); 
	    add(lpdfFilename, null);
	    add(pdfFilename , null);
	    
	    lfechaDesde.setText("Periodo desde:");
	    lfechaDesde.setBounds(new Rectangle(29, 276, 123, 22)); 
	    fechaDesde.setBounds(new Rectangle(155, 276, 132, 22)); 
	    add(lfechaDesde, null);
	    add(fechaDesde , null);

	    lfechaHasta.setText( "hasta:" );
	    lfechaHasta.setBounds(new Rectangle(296, 276, 61, 22)); 
	    fechaHasta.setBounds(new Rectangle(359, 276, 120, 22)); 
	    
//	    liata.setText( "IATA" );
//	    liata.setBounds(new Rectangle(26, 342, 123, 22)); 
//	    iata.setBounds(new Rectangle(154, 342, 143, 22)); 
//	    add(liata, null);
//	    add(iata , null);

	    add(lfechaHasta, null);
	    add(fechaHasta , null);
	    this.add(jLabel, null);
	    this.add(jLabel1, null);
	    this.add(jLabel11, null);
	    this.add(jLabel12, null);
	    this.add(jLabel121, null);
	    
	  }

		private String getDefaultFormato() throws Exception {
			return BizBSPUser.getUsrBSP().getBspConsola().getConfigView().getDefaultFormato();
		}
		private String getDefaultInterfaz() throws Exception {
			String val= BizBSPUser.getUsrBSP().getBspConsola().getConfigView().getDefaultInterfaz();
			if (val.equals("")) return "NEW";
			return val;
		}
	  
	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {
	    AddItem( company, CHAR, OPT, "company" ).setVisible(false);
	    AddItem( owner, CHAR, OPT, "owner" ).setVisible(false);
	    AddItem( idPDF, CHAR, OPT, "idInterfaz" ).setVisible(false);
	    AddItem( descripcion, CHAR, OPT, "descripcion" );
	    JFormSwingCombo combo = AddItem( tipo_interfaz, CHAR, OPT, "tipo_interfaz" , getTipoInterfaz());
	    combo.SetValorDefault(getDefaultInterfaz());
	    combo = AddItem( formato, CHAR, OPT, "id_formato",  new JControlCombo() {@Override
	  		public JWins getRecords(boolean one) throws Exception {return getFormatos(one);}} );
	    combo.SetValorDefault(getDefaultFormato());
      AddItem( pdfFilename, CHAR, OPT, "filename" );
      AddItem( fechaDesde, DATE, REQ, "fecha_desde" );
      AddItem( fechaHasta, DATE, REQ, "fecha_hasta" );
  //    AddItem( iata, CHAR, REQ, "iata" , ((ISitita)BizUsuario.getUsr().getObjLicense().getLicense()).getIATAs());
      AddItem( iata, CHAR, REQ, "iata" ).SetValorDefault("-");;

	  } 
	  
	  private JWins getFormatos(boolean one) throws Exception {
	  	GuiFormatos guis = new GuiFormatos();
	  	if (one) {
		  	guis.getRecords().addFilter("company", this.getWin().GetcDato().getCompany());
//		  	guis.getRecords().addFilter("owner", this.getWin().GetcDato().getOwner());
		  	guis.getRecords().addFilter("id_formato", this.getWin().GetcDato().getFormatoInterfaz());
	  	} else {
		  	guis.getRecords().addFilter("company", this.findControl("company").getValue());
//		  	guis.getRecords().addFilter("owner", this.findControl("owner").getValue());
	  	}
	  	return guis;
	 
	  }
	  private JWins getTipoInterfaz() throws Exception {
	  	return JWins.createVirtualWinsFromMap(BizInterfazBO.getTiposInterfaz());
	  }
	}  //  @jve:decl-index=0:visual-constraint="10,10" 
