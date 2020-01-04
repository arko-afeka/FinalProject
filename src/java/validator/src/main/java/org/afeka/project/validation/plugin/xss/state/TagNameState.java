package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.SpecialChar;
import org.afeka.project.validation.plugin.xss.model.Token;
import org.afeka.project.validation.plugin.xss.model.TokenType;
import org.afeka.project.validation.plugin.xss.util.H5Utils;
import org.afeka.project.validation.plugin.xss.util.StringReader;

public class TagNameState extends State {
  public TagNameState(StringReader data) {
    super(data);
  }

  protected TagNameState(State state) {
    super(state);
  }

  @Override
  public void run() {
    int pos = data.position();

    while (pos < data.size()) {
      char cur = data.peek(pos);

      switch (cur) {
        case (SpecialChar.NULL):
          {
            pos += 1;
            break;
          }
        case (SpecialChar.SLASH):
          {
            token = new Token(data.subString(pos - data.position()), TokenType.TAG_NAME_OPEN);
            nextState = new SelfClosingStartTagState(this);
            data.move(1);
            shouldContinue = true;
            return;
          }
        case (SpecialChar.GT):
          {
            shouldContinue = true;
            if (isClose) {
              isClose = false;
              token = new Token(data.subString(pos - data.position()), TokenType.TAG_CLOSE);
              nextState = new DataState(this);
              data.move(1);
            } else {
              token = new Token(data.subString(pos - data.position()), TokenType.TAG_NAME_OPEN);
              nextState = new TagNameCloseState(this);
            }
            return;
          }
        default:
          {
            if (H5Utils.isWhite(cur)) {
              shouldContinue = true;
              nextState = new BeforeAttributeNameState(this);
              token = new Token(data.subString(pos - data.position()), TokenType.TAG_NAME_OPEN);
              return;
            } else {
              pos += 1;
            }
          }
      }
    }

    token = new Token(data.subString(data.size()), TokenType.TAG_NAME_OPEN);
    nextState = new EOFState(this);
    shouldContinue = true;
  }
}
