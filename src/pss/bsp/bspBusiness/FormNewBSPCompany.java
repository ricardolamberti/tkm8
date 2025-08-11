package pss.bsp.bspBusiness;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import pss.common.regions.company.GuiCompanies;
import pss.common.regions.divitions.GuiPaisesLista;
import pss.common.security.license.typeLicense.GuiTypeLicenses;
import pss.core.ui.components.JPssCalendarEdit;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;

public class FormNewBSPCompany extends JBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -824914234250493196L;
	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	JPssLabel					ldescript		= new JPssLabel();
	JPssEdit						description	= new JPssEdit();
	JPssEdit						negocio			= new JPssEdit();
	private JTabbedPane jTabbedPane = null;
	private JPssLabel ldescript1 = null;
	private JPssEdit empresa = null;
	private JPssLabel ldescript2 = null;
	private JComboBox jModelo = null;
	private JLabel jLabel = null;
	private JComboBox jPais = null;
	private JPssLabel ldescript3 = null;
	private JPssEdit email = null;
	JPssLabel llicencias = new JPssLabel();
	JPssEdit licencias = new JPssEdit  ();
	JPssLabel lfecha_incio = new JPssLabel();
	JPssCalendarEdit fecha_incio = new JPssCalendarEdit();
	JPssLabel lfecha_hasta = new JPssLabel();
	JPssCalendarEdit fecha_hasta = new JPssCalendarEdit();
	JPssLabel lrenovaciones = new JPssLabel();
	JPssEdit renovaciones = new JPssEdit  ();
	JPssLabel ltipo_licencia = new JPssLabel();
	JComboBox tipo_licencia = new JComboBox  ();


	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormNewBSPCompany() throws Exception {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public GuiNewBSPCompany GetWin() {
		return (GuiNewBSPCompany) GetBaseWin();
	}

	// -------------------------------------------------------------------------//
	// Inicializacion Grafica
	// -------------------------------------------------------------------------//
	protected void jbInit() throws Exception {
		ldescript3 = new JPssLabel();
		ldescript3.setBounds(new Rectangle(12, 108, 123, 22));
		ldescript3.setText("Email");
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(12, 85, 123, 22));
		jLabel.setText("País");
		ldescript2 = new JPssLabel();
		ldescript2.setBounds(new Rectangle(12, 61, 123, 22));
		ldescript2.setText("Modelo");
		ldescript1 = new JPssLabel();
		ldescript1.setBounds(new Rectangle(12, 13, 123, 22));
		ldescript1.setText("Sigla");
		setLayout(null);
		setSize(new Dimension(714, 279));

		ldescript.setText("Descripción");
		ldescript.setBounds(new Rectangle(12, 37, 123, 22));
		description.setBounds(new Rectangle(140, 38, 282, 22));
		add(ldescript, null);
		add(description, null);

    llicencias.setText("Cantidad Licencias");
    llicencias.setBounds(new Rectangle(12, 132, 123, 22)); 
    licencias.setBounds(new Rectangle(140, 132, 73, 22)); 
    add(llicencias, null);
    add(licencias , null);


    lfecha_incio.setText("Fecha inicio");
    lfecha_incio.setBounds(new Rectangle(12, 159, 123, 22)); 
    fecha_incio.setBounds(new Rectangle(140, 159, 143, 22)); 
    add(lfecha_incio, null);
    add(fecha_incio , null);


    lfecha_hasta.setText( "Fecha hasta" );
    lfecha_hasta.setBounds(new Rectangle(12, 186, 123, 22)); 
    fecha_hasta.setBounds(new Rectangle(140, 186, 143, 22)); 
    add(lfecha_hasta, null);
    add(fecha_hasta , null);



    lrenovaciones.setText( "Renovaciones" );
    lrenovaciones.setBounds(new Rectangle(12, 211, 123, 22)); 
    renovaciones.setBounds(new Rectangle(140, 211, 73, 22)); 
    add(lrenovaciones, null);
    add(renovaciones , null);


    ltipo_licencia.setText( "Tipo licencia" );
    ltipo_licencia.setBounds(new Rectangle(12, 238, 123, 22)); 
    tipo_licencia.setBounds(new Rectangle(140, 238, 201, 22)); 
    add(ltipo_licencia, null);
    add(tipo_licencia , null);
		this.add(ldescript1, null);
		this.add(getEmpresa(), null);
		this.add(ldescript2, null);
		this.add(getJModelo(), null);
		this.add(jLabel, null);
		this.add(getJPais(), null);
		this.add(ldescript3, null);
		this.add(getEmail(), null);
	}

	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		AddItem(getEmpresa(), CHAR, REQ, "company");
		AddItem(description, CHAR, REQ, "description");
		AddItem(negocio, CHAR, OPT, "business").SetValorDefault(GetWin().GetcDato().getBusiness());
		AddItem(getJModelo(), CHAR, OPT, "modelo", new JControlCombo() {@Override
		public JWins getRecords(boolean one) throws Exception {
			return getModelos(one);
		} });
		AddItem(getJPais(), CHAR, REQ, "pais", new GuiPaisesLista().addOrderAdHoc("descripcion", "ASC"));
		AddItem(getEmail(), CHAR, REQ, "email");
    AddItem( licencias, UINT, OPT, "licencias" );
    AddItem( fecha_incio, DATE, OPT, "fecha_incio" );
    AddItem( fecha_hasta, DATE, OPT, "fecha_hasta" );
    AddItem( renovaciones, UINT, OPT, "renovaciones" ).SetValorDefault(0);
    AddItem( tipo_licencia, CHAR, REQ, "tipo_licencia", new GuiTypeLicenses() ).setFirstOcur(true);

	}
	
	public JWins getModelos(boolean one) throws Exception {
		GuiCompanies comp = new GuiCompanies();
		if (one) {
			comp.getRecords().addFilter("company", GetWin().GetcDato().getModelo());
		}
		comp.getRecords().addFilter("company", "_","like");
		return comp;
	}
	

	

	/**
	 * This method initializes empresa	
	 * 	
	 * @return pss.core.ui.components.JPssEdit	
	 */
	private JPssEdit getEmpresa() {
		if (empresa == null) {
			empresa = new JPssEdit();
			empresa.setBounds(new Rectangle(140, 14, 95, 22));
		}
		return empresa;
	}

	/**
	 * This method initializes jModelo	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJModelo() {
		if (jModelo == null) {
			jModelo = new JComboBox();
			jModelo.setBounds(new Rectangle(140, 62, 281, 22));
		}
		return jModelo;
	}

	/**
	 * This method initializes jPais	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJPais() {
		if (jPais == null) {
			jPais = new JComboBox();
			jPais.setBounds(new Rectangle(140, 86, 143, 22));
		}
		return jPais;
	}

	/**
	 * This method initializes email	
	 * 	
	 * @return pss.core.ui.components.JPssEdit	
	 */
	private JPssEdit getEmail() {
		if (email == null) {
			email = new JPssEdit();
			email.setBounds(new Rectangle(140, 110, 281, 22));
		}
		return email;
	}

}  //  @jve:decl-index=0:visual-constraint="40,6"
