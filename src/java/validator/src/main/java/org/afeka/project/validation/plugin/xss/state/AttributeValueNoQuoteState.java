package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.SpecialChar;
import org.afeka.project.validation.plugin.xss.model.Token;
import org.afeka.project.validation.plugin.xss.model.TokenType;
import org.afeka.project.validation.plugin.xss.util.H5Utils;
import org.afeka.project.validation.plugin.xss.util.StringReader;

public class AttributeValueNoQuoteState extends State {
  public AttributeValueNoQuoteState(StringReader data) {
    super(data);
  }

  protected AttributeValueNoQuoteState(State state) {
    super(state);
  }

  @Override
  public void run() {
    int pos = data.position();
    while (pos < data.size()) {
      char cur = data.peek(pos);
      if (H5Utils.isWhite(cur)) {
        shouldContinue = true;
        token = new Token(data.subString(pos - data.position()), TokenType.ATTR_VALUE);
        data.move(1);
        nextState = new BeforeAttributeNameState(this);
        return;
      } else if (cur == SpecialChar.GT) {
        shouldContinue = true;
        token = new Token(data.subString(pos - data.position()), TokenType.ATTR_VALUE);
        nextState = new TagNameCloseState(this);
        return;
      }

      pos++;
    }

    nextState = new EOFState(this);
    shouldContinue = true;
    token = new Token(data.subString(data.size()), TokenType.ATTR_VALUE);
  }
}
