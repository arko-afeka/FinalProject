package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.SpecialChar;
import org.afeka.project.validation.plugin.xss.util.StringReader;

public class SingleQuoteAttributeNameState extends State {
  public SingleQuoteAttributeNameState(StringReader data) {
    super(data);
  }

  protected SingleQuoteAttributeNameState(State previousState) {
    super(previousState);
  }

  @Override
  public void run() {
    setFromState(new AttributeValueQuoteState(this, SpecialChar.SINGLE));
  }
}
