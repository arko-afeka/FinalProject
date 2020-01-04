package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.SpecialChar;
import org.afeka.project.validation.plugin.xss.util.H5Utils;
import org.afeka.project.validation.plugin.xss.util.StringReader;

public class AfterAttributeNameState extends State {

  public AfterAttributeNameState(StringReader data) {
    super(data);
  }

  protected AfterAttributeNameState(State previousState) {
    super(previousState);
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
          break;
        }
      case (SpecialChar.EQUALS):
        {
          data.move(1);
          setFromState(new BeforeAttributeValueState(this));
          break;
        }
      case (SpecialChar.GT):
        {
          setFromState(new TagNameCloseState(this));
          break;
        }
      default:
        {
          setFromState(new AttributeNameState(this));
        }
    }
  }
}
