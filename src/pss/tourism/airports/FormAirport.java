package pss.tourism.airports;

import pss.common.regions.divitions.GuiPaises;
import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssGoogleMap;
import pss.core.ui.components.JPssLabel;
import pss.core.ui.components.JPssLabelFormLov;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import java.awt.Rectangle;
import java.awt.Dimension;

public class FormAirport extends JBaseForm {

	private static final long serialVersionUID=1L;
	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	JPssLabel lcode=new JPssLabel();
	JPssEdit jcode=new JPssEdit();
	JPssEdit descricpion=new JPssEdit();
	JPssLabel lDescripcion=new JPssLabel();
	private JPssLabel jPssLabel=null;
	// private JPssSearch country=null;
	private JPssLabelFormLov country=null;
	private JPssLabel jPssLabel1=null;
	private JPssEdit city=null;
	JPssEdit pssEdit = new JPssEdit();
	
	private JPssGoogleMap geoposicion = new JPssGoogleMap();
	// private JPssEdit calculado=null;

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormAirport() throws Exception {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public GuiAirport GetWin() {
		return (GuiAirport) GetBaseWin();
	}

	// -------------------------------------------------------------------------//
	// Inicializacion Grafica
	// -------------------------------------------------------------------------//
	protected void jbInit() throws Exception {
		jPssLabel1=new JPssLabel();
		jPssLabel1.setBounds(new java.awt.Rectangle(14, 127, 86, 22));
		jPssLabel1.setText("Ciudad");
		jPssLabel=new JPssLabel();
		jPssLabel.setBounds(new java.awt.Rectangle(14, 92, 68, 22));
		jPssLabel.setText("País");
		setLayout(null);
		setSize(new Dimension(957, 416));

		lcode.setText("Código");
		lcode.setBounds(new java.awt.Rectangle(14, 17, 49, 22));
		jcode.setBounds(new java.awt.Rectangle(103, 17, 64, 22));
		jcode.setName("");

		descricpion.setBounds(new java.awt.Rectangle(103, 54, 262, 22));

		lDescripcion.setText("Descripción");
		lDescripcion.setBounds(new java.awt.Rectangle(14, 56, 72, 22));
		geoposicion.setBounds(new Rectangle(389, 10, 557, 395));

		// calculado=new JPssEdit();
		// calculado.setBounds(new java.awt.Rectangle(300, 17, 262, 22));

		this.add(jcode, null);
		this.add(descricpion, null);
		this.add(lcode, null);
		this.add(lDescripcion, null);
		this.add(jPssLabel, null);
		this.add(getCountry(), null);
		this.add(jPssLabel1, null);
		this.add(getCity(), null);
		this.add(geoposicion, null);
		
		JPssLabel pslblIataAreaMetrolpolitan = new JPssLabel();
		pslblIataAreaMetrolpolitan.setText("Iata Area Metrolpolitan");
		pslblIataAreaMetrolpolitan.setBounds(new Rectangle(14, 127, 86, 22));
		pslblIataAreaMetrolpolitan.setBounds(14, 161, 153, 22);
		add(pslblIataAreaMetrolpolitan);
		
		pssEdit.setBounds(new Rectangle(103, 127, 262, 22));
		pssEdit.setBounds(173, 160, 192, 22);
		add(pssEdit);
		// this.add(calculado, null);

	}

	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		SetExitOnOk(true);
		AddItem(jcode, CHAR, REQ, "code");
		AddItem(descricpion, CHAR, REQ, "description");
		AddItem(country, CHAR, REQ, "country", new JControlWin() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return getPaises(zOneRow);
			}
		});
		AddItem(city, CHAR, OPT, "city");
		AddItem(geoposicion, CHAR, OPT, "geo_position");
		AddItem(pssEdit, CHAR, OPT, "iata_area");
		// AddItem(calculado, CHAR, OPT, "calculado");
	}

	/**
	 * This method initializes country
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JPssLabelFormLov getCountry() throws Exception {
		if (country==null) {
			country=new JPssLabelFormLov();
			country.setBounds(new Rectangle(103, 92, 207, 22));
		}
		return country;
	}

	public JWins getCountries(boolean zOneRow) throws Exception {
		GuiPaises oWins=new GuiPaises();
		if (zOneRow) {
			oWins.getRecords().addFilter("pais", GetWin().GetcDato().getCountry());
		}
		return oWins;
	}

	/**
	 * This method initializes jPssEdit
	 * 
	 * @return pss.core.ui.components.JPssEdit
	 */
	private JPssEdit getCity() {
		if (city==null) {
			city=new JPssEdit();
			city.setBounds(new Rectangle(103, 127, 262, 22));
		}
		return city;
	}

	protected JWins getPaises(boolean zOneRow) throws Exception {
		GuiPaisesLista wins=new GuiPaisesLista();
		if (zOneRow) {
			wins.getRecords().addFilter("pais", GetWin().GetcDato().getCountry());
		}
		return wins;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
