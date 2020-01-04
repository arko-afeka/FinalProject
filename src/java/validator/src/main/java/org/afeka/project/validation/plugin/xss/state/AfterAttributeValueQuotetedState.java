package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.SpecialChar;
import org.afeka.project.validation.plugin.xss.model.Token;
import org.afeka.project.validation.plugin.xss.model.TokenType;
import org.afeka.project.validation.plugin.xss.util.H5Utils;
import org.afeka.project.validation.plugin.xss.util.StringReader;

public class AfterAttributeValueQuotetedState extends State {
  public AfterAttributeValueQuotetedState(StringReader data) {
    super(data);
  }

  protected AfterAttributeValueQuotetedState(State state) {
    super(state);
  }

  @Override
  public void run() {
    if (data.size() <= 0) {
      shouldContinue = false;
      token = null;
      return;
    }

    char cur = data.peek();

    if (H5Utils.isWhite(cur)) {
      data.move(1);
      setFromState(new BeforeAttributeNameState(this));
    } else if (cur == SpecialChar.SLASH) {
      data.move(1);
      setFromState(new SelfClosingStartTagState(this));
    } else if (cur == SpecialChar.GT) {
      shouldContinue = true;
      token = new Token(data.subString(1), TokenType.TAG_NAME_CLOSE);
      nextState = new DataState(this);
    } else {
      setFromState(new BeforeAttributeNameState(this));
    }
  }
}
