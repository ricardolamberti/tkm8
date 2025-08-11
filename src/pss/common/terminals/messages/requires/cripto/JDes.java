package pss.common.terminals.messages.requires.cripto;

import pss.common.terminals.core.JTerminal;
import pss.common.terminals.interfaces.JDataEncriptInterface;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.requires.JRequire;

/**
 * Description:
 *
 * @author Ricardo Lamberti, Iván Rubín
 *
 */

public class JDes extends JRequire {
	
	private String dataBlock;
	private String wKey;
	private int mKeyIndex;

  public JDes() {
  }

	public void setDataBlock(String value) { this.dataBlock=value; }
	public void setWorkingKey(String value) { this.wKey=value; }
	public void setMasterKeyIndex(int value) { this.mKeyIndex=value; }
	
	public String getDataBlock() { return this.dataBlock; }
	public String getWorkingKey() { return this.wKey; }
	public int getMasterKeyIndex() { return this.mKeyIndex; }
	
	@Override
	public Answer execute(JTerminal terminal) throws Exception {
		JDataEncriptInterface dataEncrypt = (JDataEncriptInterface) terminal;
		return dataEncrypt.dataEncrypt(this);
	}
	  
}
