package pss.core.win.submits;



public class JActExternalLink extends JActSubmit {


	public JActExternalLink(String url) {
		super(null);
		this.url = url;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8671110613347096282L;
	private String url;
	
	public String getUrl() {
		return url;
	}

	@Override
	public void submit() throws Exception {
		this.url = this.generate();
	}
	
	public String generate() throws Exception { return url;}

  @Override
	public boolean isOnlySubmit() { return true;}

}
