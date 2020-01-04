package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.SpecialChar;
import org.afeka.project.validation.plugin.xss.model.Token;
import org.afeka.project.validation.plugin.xss.model.TokenType;
import org.afeka.project.validation.plugin.xss.util.H5Utils;
import org.afeka.project.validation.plugin.xss.util.StringReader;

public class AttributeNameState extends State {
  public AttributeNameState(StringReader data) {
    super(data);
  }

  protected AttributeNameState(State state) {
    super(state);
  }

  @Override
  public void run() {
    int pos = data.position() + 1;
    while (pos < data.size()) {
      char cur = data.peek(pos);

      switch (cur) {
        case SpecialChar.SLASH: {
          token = new Token(data.subString(pos - data.position()), TokenType.ATTR_NAME);
          shouldContinue = true;
          nextState = new SelfClosingStartTagState(this);
          data.move(1);
          return;
        }
        case SpecialChar.EQUALS: {
          token = new Token(data.subString(pos - data.position()), TokenType.ATTR_NAME);
          shouldContinue = true;
          nextState = new BeforeAttributeValueState(this);
          data.move(1);
          return;
        }
        case SpecialChar.GT: {
          token = new Token(data.subString(pos - data.position()), TokenType.ATTR_NAME);
          shouldContinue = true;
          nextState = new TagNameCloseState(this);
          return;
        }
        default: {
          if (H5Utils.isWhite(cur)) {
            token = new Token(data.subString(pos - data.position()), TokenType.ATTR_NAME);
            shouldContinue = true;
            nextState = new AfterAttributeNameState(this);
            data.move(1);
            return;
          } else {
            pos++;
          }
        }
      }
    }

    token = new Token(data.subString(data.size()), TokenType.ATTR_NAME);
    shouldContinue = true;
    nextState = new EOFState(this);
  }
}
