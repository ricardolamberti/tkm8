package pss.www.ui;

public class JWebHelpBox extends JWebHelp {
	public JWebHelpBox() {
		super();
	}

	// tipo de recurso warning o step
	String type;

	// como leo la posicion? relative, absolute o inner
	String typePosition;

	// si es relative o inner con que id de html
	String resource;

	String stringHelp;

	public String getStringHelp() {
		return stringHelp;
	}

	public void setStringHelp(String help) {
		this.stringHelp = help;
	}

	@Override
	public String getComponentType() {
		return "helpbox";
	}

	static int uniqueId = 0;

	@Override
	public String getName() {
		return "help_box_" + (uniqueId++);
	}

	@Override
	protected void build() throws Exception {
		this.setSize(200, 100);
//		this.setLayout(null);

		JWebPanel oPanel = new JWebPanel();
//		oPanel.setLayout(null);
		oPanel.setSkinStereotype(this.getSkinStereotypeView());
		oPanel.setMargins(0, 0, 0, 0);
		this.add("help_panel", oPanel);

		JWebLabel oLabel = new JWebLabel(stringHelp);
		oLabel.setSize(180, 160);
		oLabel.setLocation(10, 10);
		oPanel.add("help_label", oLabel);
	}

	@Override
	protected String getSkinStereotypeView() throws Exception {
		if (type.equals("STEP"))
			return "help_box";
		else
			return "help_box_warning";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypePosition() {
		return typePosition;
	}

	public void setTypePosition(String typePosition) {
		this.typePosition = typePosition;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

}
