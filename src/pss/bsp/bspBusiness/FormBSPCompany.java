package  pss.bsp.bspBusiness;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JTabbedPane;

import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssImage;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.winUI.controls.JFormImage;
import pss.core.winUI.forms.JBaseForm;

public class FormBSPCompany extends JBaseForm {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	JPssLabel					ldescript		= new JPssLabel();
	JPssEdit						description	= new JPssEdit();
	JPssEdit						negocio			= new JPssEdit();
	JPssImage imagen = new JPssImage();

	private JTabbedPane jTabbedPane = null;
	private JPssLabel ldescript1 = null;
	private JPssEdit empresa = null;
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormBSPCompany() throws Exception {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public GuiBSPCompany GetWin() {
		return (GuiBSPCompany) GetBaseWin();
	}

	// -------------------------------------------------------------------------//
	// Inicializacion Grafica
	// -------------------------------------------------------------------------//
	protected void jbInit() throws Exception {
		ldescript1 = new JPssLabel();
		ldescript1.setBounds(new Rectangle(11, 13, 95, 22));
		ldescript1.setText("Sigla");
		setLayout(null);
		setSize(new Dimension(714, 417));

		ldescript.setText("Descripción");
		ldescript.setBounds(new Rectangle(11, 38, 95, 22));
		description.setBounds(new Rectangle(118, 37, 282, 22));
		add(ldescript, null);
		add(description, null);

		this.add(getJTabbedPane(), null);
		this.add(ldescript1, null);
		this.add(getEmpresa(), null);
		
		imagen.setBounds(430, 17, 261, 71);
		add(imagen);
	}

	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		AddItem(getEmpresa(), CHAR, OPT, "company");
		AddItem(description, CHAR, REQ, "description");
		AddItem(negocio, CHAR, OPT, "business").SetValorDefault(GetWin().GetcDato().getBusiness());
		JFormImage m = AddItem(imagen, "logo_img");
		m.setSource(JPssImage.SOURCE_PSSDATA);
		AddItem(getJTabbedPane(), 10);
		AddItem(getJTabbedPane(), 20);
		AddItem(getJTabbedPane(), 60);
	}

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane==null) {
			jTabbedPane=new JTabbedPane();
			jTabbedPane.setBounds(new Rectangle(13, 99, 688, 308));
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes empresa	
	 * 	
	 * @return pss.core.ui.components.JPssEdit	
	 */
	private JPssEdit getEmpresa() {
		if (empresa == null) {
			empresa = new JPssEdit();
			empresa.setBounds(new Rectangle(118, 13, 137, 22));
		}
		return empresa;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
