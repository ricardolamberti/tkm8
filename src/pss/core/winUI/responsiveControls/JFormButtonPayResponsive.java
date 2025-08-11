package pss.core.winUI.responsiveControls;

public class JFormButtonPayResponsive  extends JFormEditResponsive  {

	private String country;
	private String currency;
	private String status;
	private double price;
	
	public static final String MODE_GOOGLEPAY = "GOOGLEPAY"; 
	public static final String MODE_MERCADOPAGO = "MERCADOPAGO"; 
	private String mode;
	
	public String getMode() {
		return mode;
	}

	public JFormButtonPayResponsive setMode(String mode) {
		this.mode = mode;
		return this;
		
	}

	public String getCountry() {
		return country;
	}

	public JFormButtonPayResponsive setCountry(String country) {
		this.country = country;
		return this;
	}

	public String getCurrency() {
		return currency;
	}

	public JFormButtonPayResponsive setCurrency(String currency) {
		this.currency = currency;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public JFormButtonPayResponsive setStatus(String status) {
		this.status = status;
		return this;
	}

	public double getPrice() {
		return price;
	}

	public JFormButtonPayResponsive setPrice(double price) {
		this.price = price;
		return this;
	}

  //-------------------------------------------------------------------------//
  // Constructor
  //-------------------------------------------------------------------------//
  public JFormButtonPayResponsive() {
  	setHeight(60);
  }
  
	public boolean isAcceptURL() {
		return true;
	}
}
