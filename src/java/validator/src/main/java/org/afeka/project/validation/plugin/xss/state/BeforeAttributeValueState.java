package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.SpecialChar;
import org.afeka.project.validation.plugin.xss.util.H5Utils;
import org.afeka.project.validation.plugin.xss.util.StringReader;

public class BeforeAttributeValueState extends State {
  public BeforeAttributeValueState(StringReader data) {
    super(data);
  }

  protected BeforeAttributeValueState(State state) {
    super(state);
  }

  @Override
  public void run() {
    char cur = H5Utils.skipWhiteSpace(data);

    switch (cur) {
      case (SpecialChar.EOF):
        {
          nextState = new EOFState(this);
          shouldContinue = false;
          token = null;
          break;
        }
      case (SpecialChar.DOUBLE):
        {
          setFromState(new DoubleQuoteAttributeNameState(this));
          break;
        }
      case (SpecialChar.SINGLE):
        {
          setFromState(new SingleQuoteAttributeNameState(this));
          break;
        }
      case (SpecialChar.TICK):
        {
          setFromState(new BackQuoteAttributeNameState(this));
          break;
        }
      default:
        {
          setFromState(new AttributeValueNoQuoteState(this));
        }
    }
  }
}
