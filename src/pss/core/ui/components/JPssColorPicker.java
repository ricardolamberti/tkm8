package pss.core.ui.components;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import pss.common.regions.multilanguage.JLanguage;
import pss.core.services.fields.JObject;

public class JPssColorPicker extends JTextField {

	// -------------------------------------------------------------------------- //
	// Propiedades privadas de la clase
	// -------------------------------------------------------------------------- //
	private JPssEditBase pPssBase=new JPssEditBase();
	private String pEditType=JObject.JSTRING;
	private String pEditAtributo=null;
	private int pEditSize=Integer.MAX_VALUE;
	private int pEditPrecision=0;
	private boolean translated;

	// -------------------------------------------------------------------------- //
	// Manejo publico de las Propiedades privadas de la clase
	// -------------------------------------------------------------------------- //
	public void SetType(String zVal) {
		pEditType=zVal;
	}

	public void SetAtributo(String zVal) {
		pEditAtributo=zVal;
	}

	public void SetSize(int zVal) {
		pEditSize=zVal;
	}

	public void SetPrecision(int zVal) {
		pEditPrecision=zVal;
	}

	public String GetType() {
		return pEditType;
	}

	public String GetAtributo() {
		return pEditAtributo;
	}

	public int GetSize() {
		return pEditSize;
	}

	public int GetPrecision() {
		return pEditPrecision;
	}

	// -------------------------------------------------------------------------- //
	// Constructores
	// -------------------------------------------------------------------------- //
	public JPssColorPicker() {
		super();
		PssEditInit(JObject.JSTRING, Integer.MAX_VALUE, 0, null);
	}

	public JPssColorPicker(String zType) {
		super();
		PssEditInit(zType, Integer.MAX_VALUE, 0, null);
	}

	public JPssColorPicker(int zMaxSize) {
		super();
		PssEditInit(JObject.JSTRING, zMaxSize, 0, null);
	}

	public JPssColorPicker(String zType, int zMaxSize) {
		super();
		PssEditInit(zType, zMaxSize, 0, null);
	}

	public JPssColorPicker(String zType, int zMaxSize, String zAtributo) {
		super();
		PssEditInit(zType, zMaxSize, 0, zAtributo);
	}

	public JPssColorPicker(String zType, int zMaxSize, int zPrecision, String zAtributo) {
		super();
		PssEditInit(zType, zMaxSize, zPrecision, zAtributo);
	}

	private void PssEditInit(String zType, int zMaxSize, int zPrecision, String zAtributo) {
		pEditType=zType;
		pEditSize=zMaxSize;
		pEditAtributo=zAtributo;
		pEditPrecision=zPrecision;
		JPssEditBase.SetBaseType(zType);
		JPssEditBase.SetBaseSize(zMaxSize);
		JPssEditBase.SetBasePrecision(zPrecision);
		JPssEditBase.SetBaseAtributo(zAtributo);
	}

	public void resetDefaultRestrictions() {
		PssEditInit(JObject.JSTRING, Integer.MAX_VALUE, 0, null);
	}

	@Override
	public void setText(String text) {
		if (this.translated) {
			text=JLanguage.translate(text);
		}
		super.setText(text);
	}

	// -------------------------------------------------------------------------- //
	// Metodos de la Clase
	// -------------------------------------------------------------------------- //
	@Override
	protected Document createDefaultModel() {
		pPssBase=new EditField();
//		this.addKeyListener(pPssBase);
		return pPssBase;
	}

	class EditField extends JPssEditBase {

		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			SetBaseType(pEditType);
			SetBaseAtributo(pEditAtributo);
			SetBaseSize(pEditSize);
			SetBasePrecision(pEditPrecision);
			super.insertString(offs, str, a);

		}

		@Override
		public void setTextCorrection(String zNewText) {
			setText(zNewText);
		}
	}

	public boolean isTranslated() {
		return translated;
	}

	public void setTranslated(boolean b) {
		translated=b;
	}

	public int getButtomY() {
		return this.getY()+this.getHeight();
	}

}
