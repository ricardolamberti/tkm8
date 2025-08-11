package  pss.bsp.consola.typesLicense;

import java.util.Date;

import pss.core.win.JWins;


public interface ITKM {
	public void requestMaxUsosPorMes() throws Exception;
	public boolean testMaxUsosPorMes() throws Exception;
	public void checkIATA(String zIata) throws Exception;
	public void startCheck(Date op) throws Exception;
	public JWins getIATAs() throws Exception;
	public boolean canUpload();
}
