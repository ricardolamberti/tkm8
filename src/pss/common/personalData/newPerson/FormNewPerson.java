package  pss.common.personalData.newPerson;

import pss.common.personalData.BizPersona;
import pss.common.personalData.FormBaseDomicilio;
import pss.common.personalData.GuiEstadosCiviles;
import pss.common.personalData.GuiTiposDoc;
import pss.common.personalData.tipoCui.GuiTiposCui;
import pss.common.regions.divitions.BizPaisLista;
import pss.common.regions.divitions.GuiPaisesLista;
import pss.common.security.BizUsuario;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.responsiveControls.JFormButtonResponsive;
import pss.core.winUI.responsiveControls.JFormComboResponsive;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTextAreaResponsive;
import pss.www.ui.JWebIcon;

public abstract class FormNewPerson extends FormBaseDomicilio {
	
//	protected int getPanelDatosPersonalesTop() {
//		return 10;
//	}
//	
//	public int getPanelTelefonoTop() {
//		return this.getPanelDomicilio().getButtomY()+panelOffset;
//	}
//	public int getPanelContableTop() {
//		return this.getPanelDomicilio().getButtomY()+panelOffset;
//	}
//	protected int getPanelDatosFiscalesTop() {
//		return this.getPanelDatosPersonalesPersonaFisica().getButtomY()+panelOffset;
//	}
//	public int getPanelDatosRegistroTop() {
//		return this.getPanelDatosFiscales().getButtomY()+panelOffset;
//	}


//	// -------------------------------------------------------------------------//
//	// Propiedades de la Clase
//	// -------------------------------------------------------------------------//
//	JPssEdit Company=new JPssEdit();
//	protected JPssEdit idPersona=new JPssEdit();
//	// datos personales
//	protected TitledBorder borderPersonales;
//	private JPssPanel panelDatosFiscales=null;
//	private JPssPanel panelDatosRegistros=null;
//	private JPssPanel panelDatosRegistrosFisico=null;
//	protected JPssPanel panelDatosPersonales=null;
//	protected JPssLabel jPssLabel2=null;
//	private JPssLabel lIdentAdic=null;
//	protected JPssLabel lblContacto=null;
//	protected JPssLabel lMat=null;
//	protected JPssEdit Apellido=null;
//	protected JPssEdit Contacto=null;
//	protected JPssEdit EMail=null;
//	protected JPssEdit Profecion=null;
//	protected JPssEdit Matricula=null;
//	protected JPssEdit Titulo=null;
//	protected JPssEdit Conyuge=null;
//	protected JPssEdit Nupcias=null;
//	protected JPssEdit Web=null;
//	protected JScrollableTextArea jObserv=null;
//	protected JPssEdit jMat=new JPssEdit();
//	private ButtonGroup Sexo=null;
//	private JRadioButton rSexoM=null;
//	private JRadioButton rSexoF=null;
//	protected JPssLabel jPssLabel3=null;
//	protected JPssEdit Nombre=null;
//	protected JPssEdit IdentAdic=null;
//	protected JPssLabel jPssLabel4=null;
//	private JComboBox jComboBox2=null;
//	protected JPssLabel jPssLabel5=null;
//	private JPssCalendarEdit fechaNac=null;
//	protected JPssLabel jPssLabel6=null;
//	protected JPssLabel lNroDoc=null;
//	private JPssLabel lTipoCui=null;
//	private JPssLabel lNroCui=null;
//	private JComboBox jTipoCui=null;
//	private JPssEdit jNroCui=null;
//	protected JPssLabel lblIdSistemaExterno=null;
//	protected JPssEdit IdSistemaExterno=null;
//	private JComboBox tipoDoc=null;
//	private JPssEdit nroDoc=null;
//	protected JPssLabel lPasaporte=null;
//	private JPssEdit pasaporte=null;
//	protected JPssLabel lVtoPasaporte=null;
//	protected JPssLabel lSexo=null;
//	protected JPssLabel lEMail=null;
//	protected JPssLabel lProfecion=null;
//	protected JPssLabel lTitulo=null;
//	protected JPssLabel lConyuge=null;
//	protected JPssLabel lNupcias=null;
//	protected JPssLabel lWeb=null;
//	protected JPssLabel lObserv=null;
//	private JPssCalendarEdit vtoPasaporte=null;
//  protected JPssLabel  lestado_civil = null;
//  protected  JComboBox estado_civil = null;
//
//
//	// registro
//	private JPssLabel lRegId=null;
//	private JPssLabelWinLov jRegId=null;
//	private JPssLabel lRegOrigen=null;
//	private JPssEdit jRegOrigen=null;
//	private JComboBox jRegTipoDoc=null;
//	private JPssLabel lRegTomo=null;
//	private JPssEdit jRegTomo=null;
//	private JPssLabel lRegFolio=null;
//	private JPssEdit jRegFolio=null;
//	private JPssLabel lRegNro=null;
//	private JPssEdit jRegNro=null;
//	private JPssLabel lRegLetra=null;
//	private JPssEdit jRegLetra=null;
//	private JPssLabel lRegFecha=null;
//	private JPssCalendarEdit jRegFecha=null;
//
//	private JPssLabel lRegIdoneo=null;
//	private JPssEdit jRegIdoneo=null;

	// -------------------------------------------------------------------------//
	// Inicializacion Grafica
	// -------------------------------------------------------------------------//
	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//
	public void InicializarCompany() throws Exception {
		this.getMainPanel().AddItemEdit("Company", CHAR, REQ, "company").hide();
		this.getMainPanel().AddItemEdit("Tipo Persona", CHAR, REQ, "tipo_persona").hide();
	}

//	@Override
//	public JWins ObtenerPaises() throws Exception {
//		GetControles().findControl("tipo_doc").clear();
//		return super.ObtenerPaises();
//	}

//	public JWins getCentrosCostos() throws Exception {
//		GuiCentroCostos c = new GuiCentroCostos();
//		String company = this.findControl("company").getValue();
//		String pais = this.findControl("pais").getValue();
//		c.getRecords().addFilter("company", company);
//		c.getRecords().addFilter("pais", pais);
//		return c;
//	}

	public JWins ObtenerTiposDocs() throws Exception {
		GuiTiposDoc oTiposDoc=new GuiTiposDoc();
//		oTiposDoc.getRecords().addFilter("pais", GetControles().findControl("nacionalidad").getValue());
		oTiposDoc.getRecords().addFilter("pais", BizUsuario.getUsr().getCountry()); // si es extranjero tiene dni arg sino poner pasaporte
		return oTiposDoc;
	}
	
	public JWins ObtenerEstadosCiviles() throws Exception {
		GuiEstadosCiviles oEstados=new GuiEstadosCiviles();
		return oEstados;
	}
	
	public void InicializarPanelPersonaFisica() throws Exception {
		JFormPanelResponsive panel = this.getMainPanel().AddItemFieldset("Persona Física");
		JFormPanelResponsive row1 = panel.AddDivPanel(); 
		row1.AddItemEdit("Persona", CHAR, OPT, "id_persona").hide();
		row1.AddItemEdit("Apellido", CHAR, REQ, "apellido", 6);
		row1.AddItemEdit("Nombre", CHAR, OPT, "nombre", 6);
		JFormComboResponsive c=row1.AddItemCombo("Nacionalidad", CHAR, OPT, "nacionalidad", new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return getPaisesNacionalidad();
			}
		});
		c.SetValorDefault(this.findPaisDefault());
		c.setSizeColumns(3);
		c=row1.AddItemCombo("Tipo Doc", CHAR, OPT, "tipo_doc", new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return ObtenerTiposDocs();
			}
		});
		row1.AddItemEdit("Nro Doc", CHAR, OPT, "nro_doc", 4);
		c.setSizeColumns(2);
		row1.AddItemDateTime("Nacimiento", DATE, OPT, "fecha_nac", 3);
		c=row1.AddItemCombo("Sexo", CHAR, REQ, "sexo", new JControlCombo() {
			public JWins getRecords(boolean one) throws Exception {
				return JWins.createVirtualWinsFromMap(getMapSexo());
			};
		}, 3);
		c.SetValorDefault(BizPersona.SEXO_MASCULINO);
//		AddItem(getIdSistemaExterno(), CHAR, OPT, "id_sistema_externo");
		row1.AddItemEdit("Pasaporte", CHAR, OPT, "pasaporte", 2);
		row1.AddItemDateTime("Vto.Pasap.", CHAR, OPT, "vto_pasaporte", 3);
		row1.AddItemEdit("Profesión", CHAR, OPT, "profesion", 4);
		
		JFormPanelResponsive row2 = panel.AddDivPanel(); 
		row2.setLabelLeft(1);
		row2.AddItemEdit("eMail", CHAR, OPT, "e_mail", 12);
		JFormPanelResponsive row3 = panel.AddItemRow(); 
		JFormTextAreaResponsive r = row3.AddItemArea("", CHAR, OPT, "observaciones");
		r.setSizeColumns(12);
		//		panel.AddItemArea("Matricula", CHAR, OPT, "matricula", 2);
	}

	private JMap<String, String> getMapSexo() throws Exception {
		JMap<String, String> map = JCollectionFactory.createMap();
		map.addElement(BizPersona.SEXO_MASCULINO, "Masculino");
		map.addElement(BizPersona.SEXO_FEMENINO, "Femenino");
		return map;
	}

	public void InicializarPanelPersonaJuridica() throws Exception {
		JFormControlResponsive c;
		JFormPanelResponsive panel = this.getMainPanel().AddItemFieldset("Identificación");
		panel.AddItemEdit("Persona", CHAR, OPT, "id_persona").hide();
		JFormPanelResponsive row1 = panel.AddDivPanel();
		c=row1.AddItemEdit("Razón Social", CHAR, REQ, "apellido", 6);
		c=row1.AddItemEdit("Nombre Fantasía", CHAR, REQ, "nombre", 6);
		JFormPanelResponsive row2 = panel.AddDivPanel();
//		AddItem(getIdSistemaExterno(), CHAR, OPT, "id_sistema_externo");
		c=row2.AddItemEdit("Contacto", CHAR, OPT, "contacto", 4);
		c=row2.AddItemEdit("Ident.Adic", CHAR, OPT, "ident_adicional", 4);
		c=row2.AddItemEdit("Pag.Web", CHAR, OPT, "web", 4);
		c=row2.AddItemEdit("eMail", CHAR, OPT, "e_mail", 12);
//		c=row2.AddItemEdit("Cod.", CHAR, OPT, "prestador", 1);
//		AddItem(getFechaNac(), DATE, OPT, "fecha_nac");
		c=panel.AddItemArea("Observaciones", CHAR, OPT, "observaciones");
		c.setSizeColumns(12);
	}

	public void InicializarPanelDatosFiscales() throws Exception {
		JFormPanelResponsive panel = this.getMainPanel().AddItemFieldset("Datos Fiscales");
		panel.AddItemCombo("Tipo Cui", CHAR, OPT, "tipo_cui", new JControlCombo() {
			public JWins getRecords(boolean one) throws Exception {
				return getTiposCui();
			}
		}).SetValorDefault("1");
		panel.AddItemEdit("Nro Cui", CHAR, OPT, "nro_cui");
	}
	
	public GuiNewPerson getWin() {
		return (GuiNewPerson) this.getBaseWin();
	}
	
	private JWins getTiposCui() throws Exception {
		GuiTiposCui g=new GuiTiposCui();
		g.getRecords().addFilter("pais", BizUsuario.getUsr().getCountry());
		g.getRecords().addFilter("tipo_persona", this.getWin().GetcDato().getTipoPersona());
		return g;
	}	


	private JWins getTipoDocs() throws Exception {
		return new GuiTiposDoc();
	}
	
	private JWins getPaisesNacionalidad() throws Exception {
		GuiPaisesLista g=new GuiPaisesLista();
//		g.SetVision(GuiPaisLista.VISION_NACIONALIDAD);
		return g;
	}

	public void makePersonaFlat() throws Exception {
		if (this.getPerson().isPersonaJuridica())
			this.makePersonaJuridicaFlat();
		if (this.getPerson().isPersonaFisica())
			this.makePersonaFisicaFlat();
	}
	
	public String findActionEdit() {
		return "";
	}
	
	public int findIcono() throws Exception {
		if (this.getPerson().isPersonaFisica())
			return this.getPerson().getSexo().equals(BizPersona.SEXO_MASCULINO)?745:806;
		else 
			return 5000;
	}
	
	public String findPais() throws Exception {
		if (this.getPerson().isPersonaFisica())
			return this.getPerson().getNacionalidad();
		else
			return this.getPerson().getPais();
	}

	public void makeIconColumn(JFormPanelResponsive panel) throws Exception {
		if (this.getWin().hasDropListener()) {
			panel.addButton(807, 4, 2);
			panel.AddItemColumn(3);
		}
		JFormPanelResponsive col=panel.AddItemColumn(7);
		JFormButtonResponsive b=col.addButton(this.findIcono(), null, false);
		b.setImageForeground(this.findIconColor());
		b.setImageFontSize(26);
		if (this.getWin().hasDropListener()) b.markMainICon();
		String pais = this.findPais();
		if (!pais.isEmpty())
			col.AddItemButton(null, JWebIcon.getPssIcon(BizPaisLista.findPais(this.findPais()).GetIcono()), null, false, null, null, false);

	}
	
	public String findIconColor() throws Exception {
		return "#007400";
	}


	public void makePersonaFisicaFlat() throws Exception {
		JFormButtonResponsive b;
		JFormPanelResponsive all = this.getDatosFlat().AddItemColumn(8);
		
		JFormPanelResponsive col=all.AddItemColumn(6);
		JFormPanelResponsive line = col.AddInLinePanel();
//		if (this.getWin().hasDropListener()) {
//			line.addButton(807, 4, 0);line.addGap(6);
//		}
		line.addButton(JTools.capitalizeAll(this.getPerson().getNombre().toLowerCase() +" " +this.getPerson().getApellido().toLowerCase()), this.findActionEdit(), 0).bold().size(14).color("#a92c2c");
		line = col.AddInLinePanel();
		StringBuffer s = new StringBuffer();
		if(this.getPerson().hasFechaNac()) {
			s.append(this.getPerson().getEdad() + " años - ");			
		}
		s.append(JDateTools.DateToString(this.getPerson().getFechaInicio(), "dd//MM/yyyy"));
		line.AddItemLabel(s.toString(), 0);
//		line.addGap(4);
//		b=line.AddItemButton(null, JWebIcon.getPssIcon(BizPaisLista.findPais(this.getPerson().getNacionalidad()).GetIcono()), 0, false, null, false);
//		b.setSizeColumns(0);
		String nac = this.getPerson().getNacionalidad();
		if (nac.isEmpty()) nac = this.getPerson().getPais();
		line.AddItemLabel(" - "+BizPaisLista.findPais(nac).GetDescrip(), 0);
		
		col=all.AddItemColumn(5);
		line = col.AddInLinePanel();
		line.AddItemLabel("Doc: ", 0).setForeground("#007400");
		line.AddItemLabel(this.getPerson().getNroDoc(),0);
		line = col.AddInLinePanel();
		line.AddItemLabel("Pasaporte: ",0).setForeground("#007400");
		line.AddItemLabel(this.getPerson().getPasaporte() + " ("+JDateTools.DateToString(this.getPerson().getVtoPasaporte(), "MM/yy")+")",0);
	}
	

	public void makePersonaJuridicaFlat() throws Exception {
		JFormButtonResponsive b; JFormControlResponsive c;
		JFormPanelResponsive all = this.getDatosFlat().AddItemColumn(8);

		JFormPanelResponsive col=all.AddItemColumn(11);
		JFormPanelResponsive line = col.AddInLinePanel();
//		if (this.getWin().hasDropListener()) {
//			line.addButton(807, 4, 0);line.addGap(6);
//		}
		line.addButton(JTools.capitalizeAll(this.getPerson().getNombre().toLowerCase()), this.findActionEdit(), 0).bold().size(14).color("#a92c2c");
		line = col.AddInLinePanel();
		c=line.AddItemLabel(JTools.capitalizeAll(this.getPerson().getApellido().toLowerCase()), 0).italic().color("#007400");
		
		col=all.AddItemColumn(5);
		line = col.AddInLinePanel();
	}

}
