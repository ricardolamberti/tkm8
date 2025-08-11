package pss.common.terminals.messages.requires;

import pss.common.terminals.messages.answer.Answer;

public interface ISender {
  public Answer send(String macAddress, String message) throws Exception;
}
