package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.SpecialChar;
import org.afeka.project.validation.plugin.xss.model.Token;
import org.afeka.project.validation.plugin.xss.model.TokenType;
import org.afeka.project.validation.plugin.xss.util.StringReader;

public class DataState extends State {
  public DataState(StringReader data) {
    super(data);
  }

  protected DataState(State state) {
    super(state);
  }

  @Override
  public void run() {
    int idx = data.indexOf(SpecialChar.LT);

    if (idx == -1) {
      token = new Token(data.subString(data.size()), TokenType.DATA_TEXT);
      nextState = new EOFState(this);

      if (token.getToken().length() == 0) {
        shouldContinue = false;
        token = null;
        return;
      }
    } else {
      token = new Token(data.subString(idx), TokenType.DATA_TEXT);
      data.move(1);
      if (token.getToken().length() == 0) {
        setFromState(new TagOpenState(this));
        return;
      }

      nextState = new TagOpenState(this);
    }

    shouldContinue = true;
  }
}
