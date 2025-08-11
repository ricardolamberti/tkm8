package pss.core.tools.collections;

/**
 * <p>
 * An interface representing a String tokenizer.<br>
 * It can be reused by calling its reset method, avoiding have to create many instances if thats not necessary.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Gilbarko Latin America
 * </p>
 * 
 * @author Leonardo Pronzolino
 * @version 1.0.0
 */

public interface JStringTokenizer {

	public boolean hasMoreTokens();

	public String nextToken();

	public int countTokens();

	public JList<String> asList();

	public void reset();

	public void reset(String string);

	public void reset(String string, char delimiter);

	public void skipEmptyTokens(boolean bSkip);

}
