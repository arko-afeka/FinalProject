package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.SpecialChar;
import org.afeka.project.validation.plugin.xss.util.StringReader;

public class DoubleQuoteAttributeNameState extends State {
  public DoubleQuoteAttributeNameState(StringReader data) {
    super(data);
  }

  protected DoubleQuoteAttributeNameState(State previousState) {
    super(previousState);
  }

  @Override
  public void run() {
    setFromState(new AttributeValueQuoteState(this, SpecialChar.DOUBLE));
  }
}
