package pss.core.winUI.lists;

import pss.common.regions.multilanguage.JLanguage;

public class JGrupoColumnaLista implements Comparable<JGrupoColumnaLista>{

	// alignment costants
	//
	public static final int ALIGNMENT_LEFT=0;
	public static final int ALIGNMENT_CENTER=1;
	public static final int ALIGNMENT_RIGHT=2;


	private String sTitulo;
	private int alignment=-1;
	private boolean translated;
	JGrupoColumnaLista padre=null;

	// public void SetCampo(String zValue) { sCampo = zValue; }
	public void aetTitulo(String zValue) {
		sTitulo=zValue;
	}

	public String getTitulo() {
		return sTitulo;
	}


	public int getAlignment() {
		if (alignment==-1) {
			return ALIGNMENT_CENTER;
		}
		return alignment;
	}

	public void setAlignment(int zAlignment) {
		if (zAlignment!=ALIGNMENT_LEFT&&zAlignment!=ALIGNMENT_CENTER&&zAlignment!=ALIGNMENT_RIGHT) {
			throw new RuntimeException("Invalid column alignment");
		}
		this.alignment=zAlignment;
	}

	public boolean hasAlignment() {
		return this.alignment!=-1;
	}


	public JGrupoColumnaLista getPadre() {
		return padre;
	}

	public void setPadre(JGrupoColumnaLista padre) {
		this.padre = padre;
	}


	public JGrupoColumnaLista(String titulo) {
		// sCampo = null;
		sTitulo=titulo;
		this.alignment=ALIGNMENT_CENTER;
	}


	public String GetGrupoColumnaTitulo() {
		try {
			return JLanguage.translate(this.getTitulo());
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isTranslated() {
		return translated;
	}

	public void setTranslated(boolean b) {
		translated=b;
	}

	@Override
	public int compareTo(JGrupoColumnaLista o) {
		if (o.getTitulo().equals("SINGRUPOCOLUMNA") && !getTitulo().equals("SINGRUPOCOLUMNA")) 
			return 1;
		return getTitulo().compareTo(getTitulo());
	}


}
