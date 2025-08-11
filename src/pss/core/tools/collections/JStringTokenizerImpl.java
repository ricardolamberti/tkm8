package pss.core.tools.collections;

/**
 * <p>
 * The default implementation for a String tokenizer.<br>
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Gilbarko Latin America</p>
 * @author Leonardo Pronzolino
 * @version 1.0.0
 */

class JStringTokenizerImpl implements JStringTokenizer {

  //////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////
  private String string;
  private char delimiter;
  private int lastDelimiterIndex;
  private int charCount;
  private int tokenCount;
  private String nextToken;
  private boolean bSkipEmptyTokens = true;

  //////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////
  JStringTokenizerImpl(String string, char delimiter) {
    this.reset(string, delimiter);
  }
  JStringTokenizerImpl(String string) {
    this(string, ' ');
  }

  //////////////////////////////////////////////////////////////////////////////
  //
  //   METHODS
  //
  //////////////////////////////////////////////////////////////////////////////
  //
  //  API
  //
  public boolean hasMoreTokens() {
    return nextToken != null;
  }
  public String nextToken() {
    String token = nextToken;
    this.parseNextToken();
    return token;
  }
  public int countTokens() {
    if (this.tokenCount != -1) return this.tokenCount;
    if (charCount==0) return 0;

    this.tokenCount = 0;
    int last = -1;
    int next = -1;
    while (true) {
      if (last==-2) {
        break;
      }
      next = this.string.indexOf(this.delimiter, last + 1);
      boolean skip = false;
      if (next == -1) {
        if (last != -1) {
          // desde (lastDelimiterIndex + 1) hasta el final
          skip = last + 1 == charCount;
        } else {
          // el string completo
          skip = this.string.length() == 0;
        }
        last = -2;
      } else {
        if (last != -1) {
          // desde (lastDelimiterIndex + 1) hasta (nextDelimiterIndex)
          skip = last + 1 == next;
        } else {
          // desde 0 hasta (nextDelimiterIndex)
          skip = next == 0;
        }
        last = next;
      }
      if (!skip || !bSkipEmptyTokens) this.tokenCount++;
    }
    return this.tokenCount;
  }
  public void reset() {
    this.tokenCount = -1;
    this.lastDelimiterIndex = -1;
    this.parseNextToken();
  }
  public void reset(String string) {
    this.string = string;
    this.charCount = string.length();
    this.reset();
  }
  public void reset(String string, char delimiter) {
    this.delimiter = delimiter;
    this.reset(string);
  }
  //
  //   token parsing
  //
  private void parseNextToken() {
    if (lastDelimiterIndex==-2) {
      nextToken = null;
      return;
    }

    int nextDelimiterIndex = this.string.indexOf(this.delimiter, lastDelimiterIndex + 1);
    boolean skip = false;
    if (nextDelimiterIndex == -1) {
      if (lastDelimiterIndex != -1) {
        // desde (lastDelimiterIndex + 1) hasta el final
        skip = lastDelimiterIndex + 1 == charCount;
        if (!skip) {
          nextToken = this.string.substring(lastDelimiterIndex + 1);
        }
      } else {
        // el string completo
        skip = this.string.length() == 0;
        if (!skip) {
          nextToken = this.string;
        }
      }
      lastDelimiterIndex = -2;
    } else {
      if (lastDelimiterIndex != -1) {
        // desde (lastDelimiterIndex + 1) hasta (nextDelimiterIndex)
        skip = lastDelimiterIndex + 1 == nextDelimiterIndex;
        if (!skip) {
          nextToken = this.string.substring(lastDelimiterIndex + 1, nextDelimiterIndex);
        }
      } else {
        // desde 0 hasta (nextDelimiterIndex)
        skip = nextDelimiterIndex == 0;
        if (!skip) {
          nextToken = this.string.substring(0, nextDelimiterIndex);
        }
      }
      lastDelimiterIndex = nextDelimiterIndex;
    }
    if (skip) {
      if( bSkipEmptyTokens ) {
        this.parseNextToken();
      } else {
        nextToken = "";
      }
    }
  }

  public JList<String> asList() {
    JList<String> oList = JCollectionFactory.createList(this.countTokens());
    while (this.hasMoreTokens()) {
      oList.addElement(this.nextToken());
    }
    return oList;
  }

  public void skipEmptyTokens(boolean bSkip) {
    bSkipEmptyTokens = bSkip;
  }

}
