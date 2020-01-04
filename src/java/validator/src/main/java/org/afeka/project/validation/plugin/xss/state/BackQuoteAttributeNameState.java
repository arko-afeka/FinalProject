package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.SpecialChar;
import org.afeka.project.validation.plugin.xss.util.StringReader;

public class BackQuoteAttributeNameState extends State {
  public BackQuoteAttributeNameState(StringReader data) {
    super(data);
  }

  protected BackQuoteAttributeNameState(State previousState) {
    super(previousState);
  }

  @Override
  public void run() {
    setFromState(new AttributeValueQuoteState(this, SpecialChar.TICK));
  }
}
