	package pss.core.winUI.responsiveControls;

import java.io.Serializable;

import pss.core.win.actions.BizAction;
import pss.www.ui.JWebIcon;

public class JFormButtonResponsive  extends JFormControlResponsive  {

  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  BizAction oAction;
  boolean submit;
	String descripcion;
  boolean back;
  private Serializable data; //datos libre para enviar dentro de un submit

	public Serializable getData() {
		return data;
	}

	public JFormButtonResponsive setData(Serializable data) {
		this.data = data;
		return this;
	}
 
  
  public boolean isBack() {
		return back;
	}
  private String row;

	public JFormButtonResponsive setBack(boolean back) {
		this.back = back;
		return this;
	}


	public String getDescripcion() {
		return descripcion;
	}

	public JFormButtonResponsive setDescripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}

	public boolean isSubmit() {
		return submit;
	}

	public JFormButtonResponsive setSubmit(boolean submit) {
		this.submit = submit;
		return this;
	}

	public BizAction getAction() {
		return oAction;
	}

	public JFormButtonResponsive setAction(BizAction oAction) {
		this.oAction = oAction;
		return this;
	}
	
	JWebIcon imagen;

	public JWebIcon getImagen() {
		return imagen;
	}

	public JFormButtonResponsive setImagen(JWebIcon imagen) {
		this.imagen = imagen;
		return this;
	}
	
	public JFormControlResponsive color(String color) throws Exception {
		this.setImageForeground(color);
		return super.color(color);
	}
	
	public void setImageForeground(String color) {
		if (this.imagen==null) return;
		this.imagen.setStyleImage("color:"+color);
	}
	public void setImageFontSize(int size) {
		if (this.imagen==null) return;
		this.imagen.setStyleImage(this.imagen.getStyleImage()+"; font-size:"+size );
	}
	public void setImagePadding(int t, int l, int b, int r) {
		if (this.imagen==null) return;
		this.imagen.setStyleImage(this.imagen.getStyleImage()+"; padding:"+t+"px "+ r+"px "+ b+"px "+ l+"px " );
	}
	public JFormControlResponsive size(int size) {
		this.setImageFontSize(size);
		return super.size(size);
	}


	//-------------------------------------------------------------------------//
  // Constructor
  //-------------------------------------------------------------------------//
  public JFormButtonResponsive() {
  }



  //-------------------------------------------------------------------------//
  // Blanqueo el campo
  //-------------------------------------------------------------------------//
  @Override
	public void clear() {
  }

  //-------------------------------------------------------------------------//
  // Protejo el campo
  //-------------------------------------------------------------------------//
  @Override
	public void disable() throws Exception {
//		setVisible(false); // HGK: lo saco porque en modo consulta siempre esta hidden 
  }

	public boolean isEnteristab() {
		return this.enteristab;
	}

  @Override
	public void hide() throws Exception {
  	setVisible(false);
  }
  @Override
	public void show() throws Exception {
  	setVisible(true);
  }

  
  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public String getSpecificValue() {
    return getDescripcion();
  }

  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public boolean hasValue() {
     return false;
  }

  @Override
	public void setValue( String zVal ) throws Exception {
  }

  @Override
	protected boolean isComponentEditable() {
    return false;
  }

	public JFormButtonResponsive setRow(String v) {
		this.row = v;
		return this;
	}
	public JFormButtonResponsive setRow(int v) {
		this.row = ""+v;
		return this;
	}
	public JFormButtonResponsive setRow(long v) {
		this.row = ""+v;
		return this;
	}

	public String findRow() throws Exception {
		if (this.row!=null) return this.row;;
		if (this.getAction()!=null)
			return this.getAction().getRow();
		return null;
	}
	
	public JFormButtonResponsive check() throws Exception {
		if (this.oAction==null) return this;
		if (this.oAction.isOkAll()) return this;
		this.oAction=null;
		return this;
	}

}
