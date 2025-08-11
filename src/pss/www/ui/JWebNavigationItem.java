package pss.www.ui;

import pss.common.regions.multilanguage.JLanguage;
import pss.www.platform.content.generators.JXMLContent;

public class JWebNavigationItem extends JWebAbstractActionView implements JWebIconHolder {

	private boolean bOpensNewWindow;
	private long lImportant = 0;
	private String sLabel;
	private String sIdAction;
	private String sConfirmation;
	private String styleImage = "float:left;";
	private boolean submit = false;
	private boolean cancel = false;
	
	public boolean isSubmit() {
		return submit;
	}

	public void setSubmit(boolean submit) {
		this.submit = submit;
	}

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	public String getConfirmation() {
		return sConfirmation;
	}

	public void setConfirmation(String sConfirmation) {
		this.sConfirmation = sConfirmation;
	}

	public String getStyleImage() {
		return styleImage;
	}

	public void setStyleImage(String style) {
		styleImage = style;
	}

	public String getIdAction() {
		return sIdAction;
	}

	public void setIdAction(String sIdAction) {
		this.sIdAction = sIdAction;
	}

	public long getImportant() {
		return lImportant;
	}

	@Override
	public String getComponentType() {
		return "navigation_item";
	}

	public void setImportant(long lImportant) {
		this.lImportant = lImportant;
	}

	public String getLabel() {
		return sLabel;
	}

	public void setLabel(String sTitle) {
		this.sLabel = sTitle;
	}

	public JWebNavigationItem() {

	}

	public JWebNavigationItem(String zLabel) {
		super();
		this.setLabel(zLabel);
	}

	public boolean isOpensNewWindow() {
		return this.bOpensNewWindow;
	}

	public void setOpensNewWindow(boolean b) {
		this.bOpensNewWindow = b;
	}


	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		zContent.setAttribute("opens_new_window", this.isOpensNewWindow());
		zContent.setAttribute("is_submit", this.isSubmit());
		zContent.setAttribute("is_cancel", this.isCancel());
		if (sLabel != null)
			zContent.setAttribute("label", getLabel());
		if (this.getImportant() != 0)
			zContent.setAttribute("important", this.getImportant());
		if (styleImage != null)
			zContent.setAttribute("style_image", styleImage);
		if (sIdAction != null)
			zContent.setAttribute("action", sIdAction);
		if (sConfirmation != null)
			zContent.setAttribute("confirmation", JLanguage.translate(sConfirmation));
	}

	private JWebIcon oIcon;

	public JWebIcon getIcon() {
		return this.oIcon;
	}

	public void setIcon(JWebIcon icon) {
		this.oIcon = icon;
	}

}
