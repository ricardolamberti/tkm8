package pss.www.platform.content.generators;

import pss.common.event.device.BizQueueMessage;
import pss.core.JAplicacion;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.www.ui.JWebPage;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewsConstants;

public class JPushNotificationPageGenerator extends JFrontDoorPageGenerator {


	@Override
	protected String getBaseContentName() {
		return "pushNotificaion";
	}

	@Override
	protected String getPageLayoutStereotype() {
		return JWebViewsConstants.PAGE_LAYOUT_APP_FRONT_DOOR;
	}

	@Override
	protected boolean isSessionDependent() {
		return false;
	}
	
	
	@Override
	protected JWebView createView() throws Exception {

		return null;
	}

	@Override
	protected JWebPage createPage() throws Exception {
		JWebPage oPage=new JWebPage();
		oPage.setLayoutStereotype(this.getPageLayoutStereotype());
		return oPage;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();
	}
	
	@Override
	protected void doGenerate() throws Exception {
		try {
			JAplicacion.GetApp().openSession();
			JAplicacion.GetApp().openApp("push notifications", "Web", true);
			Long deviceId = JTools.getLongNumberEmbedded(this.parameters.getParameter("deviceid"));
			JRecords<BizQueueMessage> queues = new JRecords<BizQueueMessage>(BizQueueMessage.class);
			queues.addFilter("id_device", deviceId);
			queues.addFilter("sended", false);
		  this.startNode("messages");
		 	if (deviceId!=-1) {
			  JIterator<BizQueueMessage> it = queues.getStaticIterator();
			  while (it.hasMoreElements()) {
			  	BizQueueMessage queue = it.nextElement();
			  	queue.execProcessSended();
		 	    this.startNode("message");
					this.setAttribute("type", queue.getType());
					this.setAttribute("title", queue.getTitle());
					this.setAttribute("info", queue.getInfo());
					this.setAttribute("image", queue.getImage());
					this.setAttribute("link", queue.getLink());
				 	this.endNode("message");
			  }
		 	}
		 	this.endNode("messages");
		 	JAplicacion.GetApp().closeApp();
			JAplicacion.GetApp().closeSession();
		} catch (Exception e) {
			JAplicacion.GetApp().closeApp();
			JAplicacion.GetApp().closeSession();
			throw e;
		}

	}

	}