package pss.core.winUI.responsiveControls;

import java.io.Serializable;

public class JFormImageCardResponsive extends JFormImageResponsive {

	String labelLink;
	boolean content;
  private Serializable data; //datos libre para enviar dentro de un submit

	public Serializable getData() {
		return data;
	}

	public JFormImageCardResponsive setData(Serializable data) {
		this.data = data;
		return this;
	}
	
	public boolean isContent() {
		return content;
	}

	public void setContent(boolean content) {
		this.content = content;
	}

	public String getLabelLink() {
		return labelLink;
	}

	public JFormImageCardResponsive setLabelLink(String labelLink) {
		this.labelLink = labelLink;
		return this;
	}

	// -------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------//
	public JFormImageCardResponsive() {
		setResponsiveClass("border-left-primary shadow");
	}



}
