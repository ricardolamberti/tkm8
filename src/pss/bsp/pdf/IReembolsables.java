package pss.bsp.pdf;

import java.util.Date;

public interface IReembolsables {
	public boolean isRefund() throws Exception;

	public Date getCreationDate() throws Exception;
	
	public String getBoletoRfnd() throws Exception;
	public String getBoleto() throws Exception;

	public String getCarrier() throws Exception;

	public String getFormaPago() throws Exception;

	public String getFolio() throws Exception;

	public String getTipo() throws Exception;

	public double getTarifa() throws Exception;

	public double getTax() throws Exception;

	public double getTotalReembolso() throws Exception;

	public String getOrigen() throws Exception;

}
