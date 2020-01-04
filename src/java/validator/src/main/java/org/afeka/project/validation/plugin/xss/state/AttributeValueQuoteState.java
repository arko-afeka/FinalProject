package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.Token;
import org.afeka.project.validation.plugin.xss.model.TokenType;
import org.afeka.project.validation.plugin.xss.util.StringReader;

public class AttributeValueQuoteState extends State {
  private char quoteChar;

  protected AttributeValueQuoteState(StringReader data, char quoteChar) {
    super(data);
    this.quoteChar = quoteChar;
  }

  protected AttributeValueQuoteState(State state, char quoteChar) {
    super(state);
    this.quoteChar = quoteChar;
  }

  @Override
  public void run() {
    if (!data.isBeginning()) {
      data.move(1);
    }

    int idx = data.indexOf(quoteChar);
    if (idx == -1) {
      token = new Token(data.subString(data.size()), TokenType.ATTR_VALUE);
      nextState = new EOFState(this);
    } else {
      token = new Token(data.subString(idx), TokenType.ATTR_VALUE);
      data.move(1);
      nextState = new AfterAttributeValueQuotetedState(this);
    }

    shouldContinue = true;
  }
}
