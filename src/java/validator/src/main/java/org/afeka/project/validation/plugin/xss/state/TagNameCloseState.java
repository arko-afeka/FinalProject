package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.Token;
import org.afeka.project.validation.plugin.xss.model.TokenType;
import org.afeka.project.validation.plugin.xss.util.StringReader;

public class TagNameCloseState extends State {
  public TagNameCloseState(StringReader data) {
    super(data);
  }

  protected TagNameCloseState(State state) {
    super(state);
  }

  @Override
  public void run() {
    isClose = false;
    token = new Token(data.subString(1), TokenType.TAG_NAME_CLOSE);
    shouldContinue = true;
    if (data.size() > 0) {
      nextState = new DataState(this);
    } else {
      nextState = new EOFState(this);
    }
  }
}
