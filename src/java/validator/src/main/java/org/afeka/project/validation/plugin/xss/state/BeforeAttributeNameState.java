package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.SpecialChar;
import org.afeka.project.validation.plugin.xss.model.Token;
import org.afeka.project.validation.plugin.xss.model.TokenType;
import org.afeka.project.validation.plugin.xss.util.H5Utils;
import org.afeka.project.validation.plugin.xss.util.StringReader;

public class BeforeAttributeNameState extends State {
  public BeforeAttributeNameState(StringReader data) {
    super(data);
  }

  protected BeforeAttributeNameState(State state) {
    super(state);
  }

  @Override
  public void run() {
    char cur = H5Utils.skipWhiteSpace(data);
    switch (cur) {
      case (SpecialChar.EOF):
        {
          shouldContinue = false;
          token = null;
          break;
        }
      case (SpecialChar.SLASH):
        {
          data.move(1);
          setFromState(new SelfClosingStartTagState(this));
          return;
        }
      case (SpecialChar.GT):
        {
          shouldContinue = true;
          token = new Token(String.valueOf(cur), TokenType.TAG_NAME_CLOSE);
          nextState = new DataState(this);
          data.move(1);
          return;
        }
      default:
        {
          setFromState(new AttributeNameState(this));
          return;
        }
    }
  }
}
