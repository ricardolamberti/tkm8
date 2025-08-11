package pss.core.winUI.responsiveControls;

import pss.core.services.fields.JObject;
import pss.core.win.actions.BizAction;
import pss.www.ui.JWebIcon;

public class JFormInfoCardResponsive extends JFormControlResponsive {

	String oDato;
	String labelLink;
	String directLink;
	String dataparent;
	String classHeaderImage;
	String classHeaderText;


	boolean submit;


	public boolean isSubmit() {
		return submit;
	}

	public JFormInfoCardResponsive setSubmit(boolean submit) {
		this.submit = submit;
		return this;
	}


	public String getClassHeaderImage() {
		return classHeaderImage;
	}

	public JFormInfoCardResponsive setClassHeaderImage(String classHeaderImage) {
		this.classHeaderImage = classHeaderImage;
		return this;
	}

	public String getClassHeaderText() {
		return classHeaderText;
	}

	public JFormInfoCardResponsive setClassHeaderText(String classHeaderText) {
		this.classHeaderText = classHeaderText;
		return this;
	}

	@Override
	public void initialize() throws Exception {
		setClassHeaderImage("col-xs-3");
		setClassHeaderText("col-xs-9 text-right");
		super.initialize();
	}
	BizAction action;
	JWebIcon imagen;

	public JWebIcon getImagen() {
		return imagen;
	}

	public JFormInfoCardResponsive setImagen(JWebIcon imagen) {
		this.imagen = imagen;
		return this;
	}

	public BizAction getAction() {
		return action;
	}
	public String getDirectLink() {
		return directLink;
	}

	public void setDirectLink(String directLink) {
		this.directLink = directLink;
	}
	public JFormInfoCardResponsive setAction(BizAction action) {
		this.action = action;
		return this;
	}
	
	public String getLabelLink() {
		return labelLink;
	}

	public JFormInfoCardResponsive setLabelLink(String labelLink) {
		this.labelLink = labelLink;
		return this;
	}

	public String getDataparent() {
		return dataparent;
	}

	public JFormInfoCardResponsive setDataparent(String dataparent) {
		this.dataparent = dataparent;
		return this;
	}

	// -------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------//
	public JFormInfoCardResponsive() {
	}

	public String getTipoDatoConvert() { // ver de unificar para no hacer estas
																				// negradas
		if (GetTipoDato().equals("CHAR"))
			return JObject.JSTRING;
		return this.GetTipoDato();
	}

//	@Override
//	public void BaseToControl(String zModo, boolean userRequest) throws Exception {
//		if (this.getProp() == null)
//			return;
//		if (zModo.equals(JBaseForm.MODO_CONSULTA)/* || this.ifReadOnly() */)
//			setValue(this.getProp().toFormattedString());
//		else
//			setValue(this.getProp().toInputString());
//	}

	// -------------------------------------------------------------------------//
	// Obtengo el valor del campo
	// -------------------------------------------------------------------------//
	@Override
	public String getSpecificValue() throws Exception {
		if (!hasValue())
			return "";
		if (showFormatted())
			return (this.getProp().toFormattedString());
		else
			return (this.getProp().toInputString());
	}

	@Override
	public void clear() throws Exception {
		getProp().setNull();
	}

	// -------------------------------------------------------------------------//
	// Obtengo el valor del campo
	// -------------------------------------------------------------------------//
	@Override
	public boolean hasValue() throws Exception {
		if (getProp()==null) return false;
		if (getProp().isNull())
			return false;
		if (getProp().toString().trim().equals(""))
			return false;
		return true;
	}

	@Override
	public void setValue(String zVal) throws Exception {
		getProp().setValue(zVal);;
	}

}