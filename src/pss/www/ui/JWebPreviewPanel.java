package pss.www.ui;

import java.awt.Dimension;

import pss.core.tools.collections.JIterator;
import pss.www.platform.content.generators.JXMLContent;

public class JWebPreviewPanel extends JWebPanel {

	
	public JWebPreviewPanel() {
	}
	
	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		super.componentToXML(zContent);
	}
	
	@Override
	public Dimension getDefaultSize() throws Exception {
		JWebViewComponent c = this.findBrother();
		if (c.getSize()==null) return null;
		double w=this.getParent().getSize().getWidth()-c.getSize().getWidth();
		return new Dimension((int)w, -1);
	}
	
	private JWebViewComponent findBrother() throws Exception {
		JIterator<JWebViewComponent> iter =  this.getParent().getChildren();
		while (iter.hasMoreElements()) {
			JWebViewComponent c = iter.nextElement();
			if (c==this) continue;
			return c;
		}
		return null;
	}
}
