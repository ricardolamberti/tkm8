package pss.core.connectivity.messageManager.common.message;


public class ReqMessage extends MessageContent {

  /**
   * 
   */
  private static final long serialVersionUID = -8857296908345710799L;
  public char getMessageType() {
    return MessageEnvelope.MSG_TYPE_REQUEST;
  }
}
