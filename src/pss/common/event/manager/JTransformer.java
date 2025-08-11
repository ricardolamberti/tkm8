package pss.common.event.manager;

public class JTransformer {

	public static final String TRANS_E3 = "E3";
	public static final String TRANS_SAP = "SAP";

	public JTransformer() {
	}

	public void processEvent(BizRegister r, BizEvent e) throws Exception {
		r.processAction(e);
	}

}
