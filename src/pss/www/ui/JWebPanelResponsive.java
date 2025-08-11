package pss.www.ui;

import pss.core.tools.collections.JIterator;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormLocalForm;
import pss.core.winUI.controls.JFormRow;
import pss.core.winUI.responsiveControls.JFormDropDownWinLovResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;

public class JWebPanelResponsive extends JWebViewInternalComposite implements JWebIconHolder,JWebLabelHolder,JWebLabelRightHolder,JWebControlInterface {

		private String sLabel;
		private String sLabelRight;
		private boolean gutter;
		private boolean tobottom;



		private long zoomtofit;
		private String dataparent;
		private boolean equals;
		
		public boolean isEquals() {
			return equals;
		}

		public void setEquals(boolean equals) {
			this.equals = equals;
		}

		public String getDataparent() {
			return dataparent;
		}

		public void setDataparent(String dataparent) {
			this.dataparent = dataparent;
		}
		public boolean isZoomtofit() {
			return zoomtofit>0;
		}

		public void setZoomtofit(long zoomtofit) {
			this.zoomtofit = zoomtofit;
		}
		public long getZoomtofit() {
			return this.zoomtofit;
		}

		public boolean isGutter() {
			return gutter;
		}

		public void setGutter(boolean gutter) {
			this.gutter = gutter;
		}
		public boolean isToBottom() {
			return tobottom;
		}

		public void setToBottom(boolean tobottom) {
			this.tobottom = tobottom;
		}

		@Override
		public String getComponentType() {
			return "panel_responsive";
		}


		public String getLabel() {
			return sLabel;
		}

		public void setLabel(String sTitle) {
			this.sLabel = sTitle;
		}

		public String getLabelRight() {
			return sLabelRight;
		}

		public void setLabelRight(String sTitle) {
			this.sLabelRight = sTitle;
		}
		public JWebPanelResponsive() {

		}

		public JWebPanelResponsive(String zLabel) {
			super();
			this.setLabel(zLabel);
		}


		private JWebIcon oIcon;

		public JWebIcon getIcon() {
			return this.oIcon;
		}

		public void setIcon(JWebIcon icon) {
			this.oIcon = icon;
		}

	  public JWebIcon getMaximizeIcon() {
			return null;
		}
		public static JWebPanelResponsive create(JWebViewComposite parent,  JFormPanelResponsive zPanel, String onlyThisControl) throws Exception {
			JWebPanelResponsive webPanel=new JWebPanelResponsive();
			webPanel.takeAttributesFormControlResponsive(zPanel);
			webPanel.setLabel(zPanel.getTitle());
			webPanel.setLabelRight(zPanel.getTitleRight());
			webPanel.setWidth(zPanel.getWidth());
			webPanel.setHeight(zPanel.getHeight());
			webPanel.setGutter(zPanel.isGutter());
			webPanel.setZoomtofit(zPanel.getZoomtofit());
			webPanel.setEquals(zPanel.isEqualHieghtPanels());
			webPanel.setDataparent(zPanel.getDataparent());
			if (parent!=null) 
				parent.addChild( webPanel);
			webPanel.addAllComponentes(zPanel,onlyThisControl);
			return webPanel;
		}
		
		

		public void addAllComponentes(JFormPanelResponsive zPanel,String onlyThisControl) throws Exception {
			JIterator<JFormControl> it=zPanel.getControles().getAllItems();
			JFormControl ctrl;
			while (it.hasMoreElements()) {
				ctrl=it.nextElement();
				if (ctrl instanceof JFormRow) continue;//JFormrow solo esta por compatibilidad, no se usa en responsive
				if (onlyThisControl!=null && !(ctrl instanceof JFormPanelResponsive) && !(ctrl instanceof JFormLocalForm)&& !(ctrl instanceof JFormDropDownWinLovResponsive)) {
					if (ctrl.getFixedProp()==null) continue;
					if (!ctrl.getFixedProp().GetCampo().equals(onlyThisControl)) continue;
				}
				JWebFormControl webctrl = this.createWebControl(ctrl,onlyThisControl);
				if (webctrl!=null)
					webctrl.SetControles(zPanel.getControles());

			}
			
			
		}

	
		@Override
		public String getSizeResponsive() throws Exception {
			if (isGutter()) return "row gutter-0";
			return super.getSizeResponsive();
		}
		@Override
		protected void containerToXML(JXMLContent zContent) throws Exception {
			if (isZoomtofit()) {
				zContent.setAttribute("zoomtofit", "zoom_"+getName());
				zContent.setAttribute("zoomtofit_width", getZoomtofit());
			}
			if (getDataparent()!=null)
					zContent.setAttribute("dataparent", getDataparent());
			zContent.setAttribute("equals", isEquals());
			if (isToBottom()) {
				zContent.setAttribute("tobottom", true);
			}
		}


		@Override
		public void setEditable(boolean editable) throws Exception {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void clear() throws Exception {
			// TODO Auto-generated method stub
			
		}


		@Override
		public String getSpecificValue() throws Exception {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public String getDisplayValue() throws Exception {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public void setValue(String zVal) throws Exception {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void setValueFromUIString(String zVal) throws Exception {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void setController(JWebFormControl control) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void onShow(String modo) throws Exception {
			// TODO Auto-generated method stub
			
		}

}