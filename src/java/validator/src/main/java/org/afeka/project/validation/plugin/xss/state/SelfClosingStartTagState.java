package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.SpecialChar;
import org.afeka.project.validation.plugin.xss.model.Token;
import org.afeka.project.validation.plugin.xss.model.TokenType;
import org.afeka.project.validation.plugin.xss.util.StringReader;

public class SelfClosingStartTagState extends State {

  public SelfClosingStartTagState(StringReader data) {
    super(data);
  }

  protected SelfClosingStartTagState(State state) {
    super(state);
  }

  @Override
  public void run() {
    if (data.size() <= 0) {
      shouldContinue = false;
      token = null;
      return;
    }

    if (data.peek() == SpecialChar.GT) {
      data.move(-1);
      token = new Token(data.subString(2), TokenType.TAG_NAME_SELFCLOSE);
      nextState = new DataState(this);
    } else {
      setFromState(new BeforeAttributeNameState(this));
    }
  }
}
